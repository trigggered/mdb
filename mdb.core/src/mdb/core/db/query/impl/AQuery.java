/*
 * Created on 07.02.2005
 *
 */
package mdb.core.db.query.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;

import mdb.core.db.connection.IConnectionManager;
import mdb.core.db.query.IDBSequenceGenerator;
import mdb.core.db.query.IQuery;
import mdb.core.session.User;
import mdb.core.shared.data.EMdbEntityActionType;
import mdb.core.shared.data.Params;


import com.google.inject.Inject;

/**
 * @author Zhuk
 * 
 */
public abstract class AQuery  implements IQuery{	
	
	private Hashtable<EMdbEntityActionType,ExecSql>		_htQueryMethods = new Hashtable<EMdbEntityActionType, ExecSql> ();;	
	private Hashtable<Integer, ExecSql>		_htQueryActions = new Hashtable<Integer, ExecSql>();
	
	protected int 				_queryID;
	protected int 				_masterQueryID;	
	protected boolean 			_fCanSetRefresh;
	protected boolean 			_IsArhive;
	protected User 			_ExecuteUser;	
	protected ArrayList<String>	_DetailQuerysList;
	protected ArrayList<IDBSequenceGenerator>	_KeysList;
	private boolean _dataCaching;
	private String _data;
	
	
	 
	protected Hashtable<String,String> _htRefMasterDetailKeys;	
	protected Params	_params;
	private   IConnectionManager _connectionManager;
	

	public AQuery(int anQueryID, IConnectionManager conn) {
		this(conn);		
		_queryID = anQueryID;									
	}

	
	@Inject
	public AQuery(IConnectionManager conn) {
		this();
		setConnectionManager(conn);									
	}
	
	
	
	public AQuery() {			
		_fCanSetRefresh = false;			
		_KeysList = new ArrayList<IDBSequenceGenerator>();
		_DetailQuerysList = new ArrayList<String>();
		_htRefMasterDetailKeys = new Hashtable<String, String>();					
	}
	
	
	public int getQueryID() {
		return _queryID;
	}
	
	public void setQueryID(int value) {
		_queryID = value;
	}

	
	public void close() {
		if (_params != null) {
			_params.clear();	
		}				
		
		for (ExecSql element :  getQueryMethods().values()) {
			element.close();			
		}
		
		for (ExecSql element :  _htQueryActions.values()) {
			element.close();			
		}
		
	}
	
	private ExecSql getCustomQuery (EMdbEntityActionType queryMethodType) {
		return (getQueryMethods().containsKey(queryMethodType)?getQueryMethods().get(queryMethodType)
						:new ExecSql(this, queryMethodType, null));	
	}
	
	private Hashtable<EMdbEntityActionType, ExecSql> getQueryMethods() {
		return _htQueryMethods;
	}
	
	private String getPreparedSql (EMdbEntityActionType queryMethods) {
		ExecSql	customQuery = getCustomQuery (queryMethods);
		return customQuery!=null?customQuery.getPreparedSql():null;
	}
	
	
	private boolean setCustomSQL(EMdbEntityActionType queryMethodType, String aSQL ) {		
			ExecSql cq = getCustomQuery (queryMethodType);		
			cq.setQuery (this);
			_htQueryMethods.put(queryMethodType, cq);			
			return cq.setMethodText(aSQL,false);		

	}
	

	public boolean setSelect(String aValue) {			
		return  setCustomSQL(EMdbEntityActionType.SELECT, aValue);
		
	}
	
	public String getSelect() {
		return getPreparedSql(EMdbEntityActionType.SELECT);		
	}
	
	
	@Override
	public boolean setAction(EMdbEntityActionType actionType, int methodId,  String value) {
		ExecSql sql = new ExecSql(this, actionType, null);
		sql.setMethodText(value,false);
		_htQueryActions.put(Integer.valueOf(methodId), sql);
		return true;		
	}


	
	public ExecSql getAction(int actionId) {
		return _htQueryActions.get(Integer.valueOf(actionId));				
	}
	
	

	public boolean setInsert(String aValue) {
		return setCustomSQL(EMdbEntityActionType.INSERT, aValue);
	}

	public String getInsert() {
		return getPreparedSql(EMdbEntityActionType.INSERT);
	}

	public boolean setUpdate(String aValue) {
		return setCustomSQL(EMdbEntityActionType.UPDATE, aValue);
	}

	public String getUpdate() {
		return getPreparedSql(EMdbEntityActionType.UPDATE);
	}

	public boolean setDelete(String aValue) {
		return setCustomSQL(EMdbEntityActionType.DELETE, aValue);
	}

	public String getDelete() {
		return getPreparedSql(EMdbEntityActionType.DELETE);
	}

	
	
	public boolean setRefresh(String aRefresh) {
		boolean fRefresh =  false;
		ExecSql qSelect = getCustomQuery(EMdbEntityActionType.SELECT);
		if (qSelect!=null) {
			ExecSql qRefresh = getCustomQuery(EMdbEntityActionType.REFRESH);			
			
			 fRefresh = setCustomSQL(EMdbEntityActionType.REFRESH
					 , qRefresh.getBuildFilteredSQL(qSelect.getMethodText(),aRefresh) );
			//qRefresh.setMethodTXT(qRefresh.getBuildFilteredSQL(qSelect.getMethodTXT(),_RefreshCondition) , true);
		}
		return fRefresh;
	}

	public String getRefresh() {
		return getPreparedSql(EMdbEntityActionType.REFRESH);
	}

	/*
	 * 
	 * */

	public ExecSql getQSelect() {		
		return getCustomQuery(EMdbEntityActionType.SELECT);		
	}

	public ExecSql getQInsert() {
		return getCustomQuery(EMdbEntityActionType.INSERT);
	}

	public ExecSql getQUpdate() {
		return getCustomQuery(EMdbEntityActionType.UPDATE);
	}

	public ExecSql getQDelete() {
		return getCustomQuery(EMdbEntityActionType.DELETE);
	}

	public ExecSql getQRefresh() {
		return getCustomQuery(EMdbEntityActionType.REFRESH);
	}


	public void setIsArchive(boolean value) {
		_IsArhive = value;		

		for (Entry<EMdbEntityActionType, ExecSql> entry: _htQueryMethods.entrySet())  {
			 entry.getValue().setIsArhive(value);			 
		 }
		 
		for (Entry<Integer, ExecSql> entry: _htQueryActions.entrySet())  {
			 entry.getValue().setIsArhive(value); 			 
		 }		
	}

	public boolean getIsArchive() {
		return _IsArhive;		
	}



	public User getExecuteUser() {
		if (_ExecuteUser == null)
			_ExecuteUser = new User();
	
		return _ExecuteUser;
	}

	public void setExecuteUser(User value) {
		_ExecuteUser = value; 		  		 		
	}
		

	/**
	 * @return
	 */
	public ResultSet getResultSet() {
		return getQSelect().getResultSet(null);
	}

	public ResultSet getResultSet(Params aParamsData) {
		
		if (aParamsData != null) {
			return getQSelect().getResultSet(aParamsData);
		}
		return getResultSet();
	}



	
	public ResultSet getResultSet(Params aParamsData, String aSQLFilter) {
		if (getQSelect().isReady()) {			
			return getQSelect().getResultSet(aParamsData, aSQLFilter);
		} 
		return null;
	}
	
	public ResultSet getResultSet(int actionId,  Params inParams) {				
			return getAction(actionId).getResultSet(inParams);		
	}
	
	

	public IConnectionManager getConnectionManager () {
		return _connectionManager;
	}
	
	@Inject
	public void setConnectionManager ( IConnectionManager connectionManager) {
		_connectionManager = connectionManager;
		 for (Entry<EMdbEntityActionType, ExecSql> entry: _htQueryMethods.entrySet())  {
 			 entry.getValue().setConnectionManager(_connectionManager); 			 
 		 }
 		 
 		for (Entry<Integer, ExecSql> entry: _htQueryActions.entrySet())  {
			 entry.getValue().setConnectionManager(_connectionManager);			 
		 } 	
	}
	
	public void setDataCaching(boolean value) {
		_dataCaching = value;
	}
	
	public boolean isDataCaching() {
		return _dataCaching;
	}


	public String getData() {
		return _data;
	}


	public void setData(String _data) {
		this._data = _data;
	}	
	
	

}

