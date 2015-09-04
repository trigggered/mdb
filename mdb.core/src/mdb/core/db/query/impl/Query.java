package mdb.core.db.query.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import mdb.core.db.EntityDataAccess;
import mdb.core.db.query.IDBSequenceGenerator;
import mdb.core.db.query.paging.IQueryPaging;
import mdb.core.shared.data.Params;
import mdb.core.shared.transformation.json.JSONTransformation;

import com.google.inject.Inject;

/**
 * @author Zhuk
*/
public class Query extends AQuery {

	private static final Logger _logger = Logger.getLogger(EntityDataAccess.class.getCanonicalName());
	
	private IQueryPaging _queryPaging;


	public void setEntityKey(String keyName, String seqName) {
		if (keyName != null ) {
			IDBSequenceGenerator sg = SequenceGeneratorFactory.create();
			sg.setKeyName(keyName);
			sg.setSeqName(seqName);			
			sg.setConnectionManager(getConnectionManager());			
			_KeysList.add(sg);
		}
	}

	public String[] getEntityKeys() {
				
		String[] toReturn =new String[_KeysList.size()];
		ArrayList<String> al= new ArrayList<String>();
		for (IDBSequenceGenerator seq : _KeysList) {
			al.add(seq.getKeyName());
		}		
		
		toReturn = al.toArray(toReturn);		
		return toReturn; 
	}

	public boolean isPrimaryKey(String aFieldName) {
		for (IDBSequenceGenerator seq : _KeysList) 
		{			
			if (seq.getKeyName().equalsIgnoreCase(aFieldName)) {
				return true;
			}
		}
		return false;
	}

	private boolean prepareForUpdate(ArrayList<Params>  aInsData, ArrayList<Params>  aUpdData,  ArrayList<Params>  aDelData) {		
		clearUpdateData();
		try {			
			getQInsert().setDataForChange(aInsData);			
			getQUpdate().setDataForChange(aUpdData);
			getQDelete().setDataForChange(aDelData);
			return true;
		} catch (Exception ex) {			
			_logger.severe(ex.getMessage());
			return false;
		}
	}

	private void clearUpdateData() {		
		getQInsert().setDataForChange(null);
		getQUpdate().setDataForChange(null);
		getQDelete().setDataForChange(null);
	}


	private void prepareInsertData() {

		if (getQInsert().getDataForChange() == null)
			return;
		
		for ( Params params :  getQInsert().getDataForChange() ) {			
			for(IDBSequenceGenerator sg : _KeysList) {
				if (sg.isCanGenerate()) {
					String KeyFieldValueOld = params.paramValueByName(sg.getKeyName());					
					params.add(sg.getKeyName()+ "_OLD_VAL", KeyFieldValueOld);						
					params.setValue(sg.getKeyName(), sg.nextVal() );
				}
			}
		}
	}

	
	
	public void  update(ArrayList<Params>  aInsData, ArrayList<Params>  aUpdData,  ArrayList<Params>  aDelData) {		
		 if ( prepareForUpdate(aInsData, aUpdData,  aDelData) )
			 update();
	}
	
			
	private void update() {				
		// Insert
		prepareInsertData();
		getQInsert().сhangeData();		
		
		setData( getChangetDataToReturn(getQInsert().getDataForChange() ));

		// Update		
		getQUpdate().сhangeData();		
		
		// Delete
		getQDelete().сhangeData();
	}

	private String getChangetDataToReturn(ArrayList<Params> data) {
		if (data == null) {
			return null;
		}
		for (Params params : data) {			
			return JSONTransformation.map2json(params.toMap());
		}
		return null;
	}
	

	public int getMasterQueryID() {
		return _masterQueryID;
	}

	public void setMasterQueryID(int aValue) {
		_masterQueryID = aValue;
	}

	public void addXmlQueryDetail(String aDetailXml) {
		_DetailQuerysList.add(aDetailXml);
	}


	public void setRefMasterDetailKeys(String aMasterKey, String aDetaileKey) {
		_htRefMasterDetailKeys.put(aMasterKey, aDetaileKey);
	}

	public String getRefMasterDetailKeys(String aMasterKey) {
		return  _htRefMasterDetailKeys.get(aMasterKey);
	}
	
	public void clearKeys () {
		_KeysList.clear();
	}
	
	public void rollback() {
		try {
			getConnectionManager().getConnection().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
			_logger.severe(e.getMessage());
		}
	}
	
	public void commit() {
		try {
			getConnectionManager().getConnection().commit();
		} catch (SQLException e) {
			_logger.severe(e.getMessage());			
		}
	}

	public IQueryPaging getQueryPaging() {
		return _queryPaging;
	}
	
	@Inject
	public void setQueryPaging(IQueryPaging value) {
		_queryPaging = value;
	}


	/* (non-Javadoc)
	 * @see mdb.core.db.query.IQuery#execute(int, mdb.core.shared.data.Params)
	 */
	
	@Override
	public boolean execute(int actionId, final Params params) {		
		
		_logger.info(String.format("Start exectute actionId %s from entity ID %s", actionId,getQueryID()));		
		 ExecSql action =getAction(actionId);
		 if ( action != null) {
			 action.execute(params);
			 _logger.info(String.format("Success Exectuted actionId %s from entity ID %s", actionId,getQueryID()));
			 return true;
		 }
		 else {
			 _logger.warning(String.format("Action Id %s from entity ID %s not found for execute", actionId,getQueryID()));		
			 return false;
		 }
	}	

}