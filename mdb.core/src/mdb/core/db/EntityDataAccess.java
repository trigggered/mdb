package mdb.core.db;	
	
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import mdb.core.db.query.IQuery;
import mdb.core.db.query.paging.IQueryPaging;
import mdb.core.shared.data.Params;
import mdb.core.shared.transformation.IDataTransformation;
import mdb.core.shared.transformation.impl.JSONTransformation;
import mdb.core.shared.transport.ChangedData;
import mdb.core.shared.transport.RequestEntity;
import mdb.injector.InjectingInterceptor;

import com.google.inject.Inject;


	
@Stateless
@Interceptors({InjectingInterceptor.class})
	public class EntityDataAccess implements IEntityDataAccess {

	  private static final Logger _logger = Logger.getLogger(EntityDataAccess.class.getCanonicalName());			  
	  
	  @EJB
	  private IQueryPool _queryPool;	  
	  
	  private IDataTransformation _dataTransformation;
	  
	  @Inject
	  public void setDataTransformation (IDataTransformation value) {
		  _dataTransformation = value;
	  }	
	  
	  
	  public IQueryPool getQueryPool () {
		  return _queryPool;
	  } 
	
	
	  /* (non-Javadoc)
		 * @see mdb.core.db.IEntityDataAccess#getResultSet(java.lang.Integer)
		 */
    @Override 
	public  ResultSet getResultSet ( Integer aEntityId,  Params  aParams) {
    	
		  IQuery query = getQueryPool().getQuery(aEntityId.intValue());		  		  
		  ResultSet Result = query.getResultSet(aParams, null);		      	 			     
			     //query.close();
			     //query = null;			     		  
		  return  Result;		  		
	}
	        
	
	/* (non-Javadoc)
	 * @see mdb.core.db.IEntityDataAccess#getResultSet(java.lang.Integer)
	 */
	@Override
	public ResultSet getResultSet(Integer aEntityId) {		
		return getResultSet ( aEntityId,  null);
	}	 


	/* (non-Javadoc)
	 * @see mdb.core.db.IDataAccess#getTransformedData(java.lang.Integer, mdb.core.db.params.Params)
	 */
	@Override
	public String getTransformedData(Integer aEntityId, Params aParams) {
		ResultSet rs = getResultSet(aEntityId, aParams);
		_logger.log(Level.INFO, "ResultSet receive");
		String result = _dataTransformation.make(rs);
		_logger.log(Level.INFO, "ResultSet converted ");
		try {
			rs.close();
			_logger.log(Level.INFO, "ResultSet closed");
		} catch (SQLException e) {
			_logger.log(Level.INFO, "Error till close ResultSet");
			e.printStackTrace();
		}
		
		return result;
	}


	/* (non-Javadoc)
	 * @see mdb.core.db.IDataAccess#getTransformedData(java.lang.Integer)
	 */
	@Override
	public String getTransformedData(Integer aEntityId) {
		return getTransformedData(aEntityId,  null);
	}

	/* (non-Javadoc)
	 * @see mdb.core.db.IEntityDataAccess#getResultSet(mdb.core.shared.transport.RequestEntity)
	 */
	@Override
	public ResultSet getResultSet(RequestEntity entity) {		
		IQuery query = prepareQuery(entity);				
    	return  query.getResultSet(entity.getParams(), null);    	 			     		  		
	}



	/* (non-Javadoc)
	 * @see mdb.core.db.IEntityDataAccess#changeData(mdb.core.shared.transport.RequestEntity)
	 */
	@Override
	public void changeData(RequestEntity entity) {

		if ( entity.isHasChanges() ) {
			
			ArrayList<Params> alInsert = new ArrayList<Params>();
			for (String dataForChange :  entity.getForInsert().values()) {			
				alInsert.add(JSONTransformation.json2Params(dataForChange));
			}
			
			ArrayList<Params> alUpdate = new ArrayList<Params>();			
			for (String dataForChange :  entity.getForUpdate().values()) {			
				alUpdate.add(JSONTransformation.json2Params(dataForChange));
			}
			
			ArrayList<Params> alDelete = new ArrayList<Params>();
			for (String dataForChange :  entity.getForDelete()) {		
				alDelete.add(JSONTransformation.json2Params(dataForChange));
			}
			
			IQuery query = getQueryPool().getQuery(entity.getEntityId());
			query.update(alInsert, alUpdate, alDelete);
			query.close();
			
			entity.setData(query.getData());
		}
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.core.db.IEntityDataAccess#execAction(mdb.core.shared.transport.RequestEntity)
	 */
	@Override
	public void execAction( RequestEntity entity) {
		if ( entity.isExecAction() ) {						
			
			for  ( Entry<Integer, ChangedData> entry : entity.getExecActions().entrySet() ) {

				Map<String,String> mapOriginValue = JSONTransformation.json2Map(entry.getValue().getOriginValue());
				Map<String,String> mapChangedValue =JSONTransformation.json2Map(entry.getValue().getChangedValue());					
					
				
				mapOriginValue.putAll(mapChangedValue);
				
				IQuery query = getQuery(entity.getEntityId());
				
				Params params = new Params();
				
				for (Entry<String, String>  parEntry : mapOriginValue.entrySet()) {
					params.add(parEntry.getKey(), parEntry.getValue());
				}
				
				query.execute(entry.getKey().intValue(), params);														
				query.close();
			}				
		}
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.core.db.IEntityDataAccess#applyFilter(mdb.core.shared.transport.RequestEntity)
	 */
	@Override
	public ResultSet applyFilter(IQuery query,  RequestEntity entity) {
		if ( entity.isExecAction() ) {						
			
			for  ( Entry<Integer, ChangedData> entry : entity.getExecActions().entrySet() ) {

				Map<String,String> mapOriginValue = JSONTransformation.json2Map(entry.getValue().getOriginValue());
				Map<String,String> mapChangedValue =JSONTransformation.json2Map(entry.getValue().getChangedValue());					
					
				if (mapChangedValue!= null) {
					mapOriginValue.putAll(mapChangedValue);
				}								
				//IQuery query = getQuery(entity.getEntityId());//getQueryPool().getQuery(entity.getEntityId());
				
				Params params = new Params();
				
				for (Entry<String, String>  parEntry : mapOriginValue.entrySet()) {
					params.add(parEntry.getKey(), parEntry.getValue());
				}
				 
				_logger.info(String.format("Apply filter by action id = %s for entity ID = %s",  entry.getKey().intValue(), query.getQueryID()));
				return query.getResultSet(entry.getKey().intValue(), params );				
			}				
		}
		return null;		
	}
	
	
/*
	private static Params json2Params(String value) {
		Params toReturn =  new Params();	
		  		
		Map<String,String> map =  json2Map (value);
		
		for (Entry<String, String>  entry : map.entrySet()) {
			 toReturn.add(entry.getKey(), entry.getValue());
		}				
		return toReturn;
	}
	
	
	private static Map<String,String>  json2Map(String value) {		
		Map<String,String> toReturn ;
		if (value !=null) {
			Type mapType = new TypeToken<Map<String,String>>(){}.getType();				 
			toReturn = new Gson().fromJson(value.replaceAll("true", "1").replaceAll("false", "0"), mapType);			
		}		
		else toReturn = new HashMap<String, String>();
		return toReturn;
	}
*/


	/* (non-Javadoc)
	 * @see mdb.core.db.IEntityDataAccess#getQuery(int)
	 */
	@Override
	public IQuery getQuery(int entityId) {		
		return getQueryPool().getQuery(entityId);		
	}
	
	
	public IQuery prepareQuery(RequestEntity entity) {
		
		 if ( _dataTransformation ==null) {
			_logger.severe("NULL"); 
		 }
		 
			IQuery query = getQuery(entity.getEntityId());
			IQueryPaging paging = query.getQueryPaging();
			
			if (paging != null) {
				entity.setPagingUsage(query.getQueryPaging().getPagingUsage());
				query.getQueryPaging().setPage(entity.getPage());						
			}			
		return query;		
	}	
	
}
