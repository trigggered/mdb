package mdb.core.db;	
	
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import mdb.core.db.query.IQuery;
import mdb.core.db.query.paging.IQueryPaging;
import mdb.core.shared.data.Params;
import mdb.core.shared.transformation.json.JSONTransformation;
import mdb.core.shared.transport.ChangedData;
import mdb.core.shared.transport.RequestEntity;
import mdb.injector.InjectingInterceptor;



	
@Stateless
@Interceptors({InjectingInterceptor.class})
	public class EntityDataAccess implements IEntityDataAccess {

	  private static final Logger _logger = Logger.getLogger(EntityDataAccess.class.getCanonicalName());			  
	  
	  @EJB
	  private IQueryPool _queryPool;	  
	
	  
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



	/* (non-Javadoc)
	 * @see mdb.core.db.IEntityDataAccess#getQuery(int)
	 */
	@Override
	public IQuery getQuery(int entityId) {		
		return getQueryPool().getQuery(entityId);		
	}
	
	
	public IQuery prepareQuery(RequestEntity entity) {
		
	
			IQuery query = getQuery(entity.getEntityId());
			IQueryPaging paging = query.getQueryPaging();
			
			if (paging != null) {
				entity.setPagingUsage(query.getQueryPaging().getPagingUsage());
				query.getQueryPaging().setPage(entity.getPage());						
			}			
		return query;		
	}	
	
}
