/** 
 * 
 */
package mdb.gateway;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import mdb.core.db.IEntityDataAccess;
import mdb.core.db.query.IQuery;
import mdb.core.shared.data.EMdbFieldType;
import mdb.core.shared.data.Params;
import mdb.core.shared.transformation.IRequestSerialiser;
import mdb.core.shared.transformation.impl.JSONTransformation;
import mdb.core.shared.transformation.impl.ResultSetToJSONTransformation;
import mdb.core.shared.transport.ChangedData;
import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.shared.transport.Request;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.shared.utils.DataConverter;
import mdb.core.shared.utils.zip.GZipHelper;
import mdb.injector.InjectingInterceptor;

import com.google.inject.Inject;

/**
 * @author azhuk
 * Creation date: Aug 2, 2012
 *
 */
@Stateless
@Interceptors({InjectingInterceptor.class})
public class RequestAnalyzer implements IRequestAnalyzer{
	private static final Logger _logger = Logger
			.getLogger(RequestAnalyzer.class.getCanonicalName());
	
	
	private final int ENTITY_FLDS_DESCR  = 1275;
	private final int TBL_BY_CONSTRAINT = 1353;
	
	public final static String FLD_ENTITY_DESC_KEY = "FLD_ENTITY_DESC_KEY_";
	
	
	private IRequestSerialiser _requestSerialiser;
	
	@Inject
	public void setRequestSerialiser(IRequestSerialiser  value) {
		_requestSerialiser = value;
	}	
	
	@EJB
	private IEntityDataAccess _entityDataAccess;
	
	
	/* (non-Javadoc)
	 * @see mdb.gateway.IRequestAnalyzer#make()	 */
	public Request  make (byte[] request) {
		_logger.info("Decompressed bytes");		
	
		byte[] bytes =  GZipHelper.uncompress(request);
		_logger.info( "Make string and cal make(string)");
		return make (new String (bytes));			
		
	}
	
	/* (non-Javadoc)
	 * @see mdb.gateway.IRequestAnalyzer#make(java.lang.String)
	 */
	@Override
	public Request make(String request) {				
		_logger.info("Deserialize String request -> Request and  Call  make(Request)");					
		 return make (_requestSerialiser.deserialize(request));
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.gateway.IRequestAnalyzer#make()	 */
	public Request make (Request request) {		
						
		_logger.info(String.format("Start Request Analyzing. UserId = %s,  RequestId = %s", request.getUser().getId(),  request.getRequestId()));				
		Request toReturn = analiseRequestEntitys(request);		
		//analiseBuisnessLogic();		
		_logger.info(String.format("End Request Analyzing. UserId = %s,  RequestId = %s",  request.getUser().getId(), request.getRequestId()));
		return toReturn;
	}
	

	

	private Request  analiseRequestEntitys(Request request) {
		
		_logger.info( "##################################################################");
		_logger.info("Start analise RequestEntitys in Requst:"+request.getRequestId());	
		Request entitysForLookUps = new Request();		
		
		for (IRequestData requestData : request.getArrayListValues() ) {	
			RequestEntity  requestEntity = (RequestEntity)requestData;			
			switch (requestEntity.getExecuteType()) {
				case GetData:
				case ApplyFilter:
					int entityId = requestEntity.getEntityId();
					_logger.info( "getResultSet for entityId = "+entityId + " in Requst:"+request.getRequestId());
					
					ResultSet resultSet =null;
					
					IQuery q = _entityDataAccess.prepareQuery(requestEntity);
					
					if (requestEntity.getExecuteType() ==  ExecuteType.GetData) { 
						resultSet = q.getResultSet(requestEntity.getParams(), null);
					}
					else{
						resultSet  = _entityDataAccess.applyFilter(q, requestEntity);
					}
					
					_logger.info( "Success getResultSet for entityId = "+entityId +" in Requst:"+request.getRequestId());
					
					if (resultSet != null) {						
						
						transformationResultSet(requestEntity, resultSet);							    		
				    		
				    	if (requestEntity.isRequestFieldsDescription()) {
				    		String viewNN = "1";
				    		if (requestEntity.getParams().containsKey("VIEW_NN") ) {
				    			viewNN = requestEntity.getParams().get("VIEW_NN").getValue();
				    		}
				    		generateColumnsDefinitions(request, entitysForLookUps, entityId, viewNN);
				    	}else {
				    		//If requsted meta data from clients
				    		 if ( requestEntity.getName().contains(FLD_ENTITY_DESC_KEY) )
				    			 	entitysForLookUps.add(prepareMetaData(resultSet));
				    	}
				    	
				    	try {
							resultSet.close();							
							q.close();
						} catch (SQLException e) {
								_logger.severe(e.getMessage());							
						}
					}
					 
					else {
						_logger.info( "ResiltSet is NULL for EntityId= "+entityId + "in Requst:"+request.getRequestId() );
					}
					_logger.info( "Success end analize GetData entityId = "+requestEntity.getEntityId() +" in Requst:"+request.getRequestId());				
					
					
					for ( IRequestData rd :  entitysForLookUps.getArrayListValues() ) {
						rd.getParams().copyFrom(requestEntity.getParams());
					}
					buildLookUpSrc(request, entitysForLookUps);
					
					break;
				case ChangeData:
					_logger.info( "Analise ChangeData entityId = "+requestEntity.getEntityId() + " in Requst:"+request.getRequestId());
					_entityDataAccess.changeData(requestEntity);
					
					_logger.info( "Success end analize ChangeData entityId = "+requestEntity.getEntityId()+ " in Requst:"+request.getRequestId());
					break;
				case ExecAction:
					_logger.info("ExecAction in Requst:"+request.getRequestId());
					_entityDataAccess.execAction(requestEntity);
					_logger.info( "Success exec action in Requst:"+request.getRequestId());
					break;
			default:
				break;				
			}													
			
		}						
				
		_logger.info( "End analize RequestEntitys in Requst:"+request.getRequestId());
		_logger.info( "##################################################################");
		return  request;
	}
	
	private void transformationResultSet(RequestEntity requestEntity, ResultSet resultSet) {
		
		requestEntity.setFields(
				ResultSetToJSONTransformation.getFields(resultSet));
		requestEntity.setData(
				ResultSetToJSONTransformation.make(requestEntity.getFields(), resultSet));
		
		//_logger.info(requestEntity.getData());
	}
	
	private Request prepareMetaData(ResultSet resultSet ) {
		
		IQuery qByConstraint = _entityDataAccess.getQuery(TBL_BY_CONSTRAINT);
		
		final String fldLookUpSrc 	= "LOOK_UP_SRC";
		final String fldLookUpKey 	= "LOOK_UP_KEY";
		final String fldLookUpFlds	= "LOOK_UP_FLDS";			
		final String fIdFldType	= "ID_FLD_TYPE";
		
		final String fldName = "name".toUpperCase();		
		
		Request toReturnReq = new Request();		
		
		try {
			resultSet.beforeFirst();
			int colIndxLookUpSrc = resultSet.findColumn(fldLookUpSrc);
			int colIndxLookUpKey = resultSet.findColumn(fldLookUpKey);
			int colIndxLookUpFlds = resultSet.findColumn(fldLookUpFlds);
			int colIndxIdFldType = resultSet.findColumn(fIdFldType);
			
			int colIndxName = resultSet.findColumn(fldName);

			
			while (resultSet.next()) {
								
				String lookUpSrcVal =  resultSet.getString(colIndxLookUpSrc);				
				String fldTypeVal   =  resultSet.getString(colIndxIdFldType);
				
				EMdbFieldType mdbFieldType = EMdbFieldType.UNKNOWN;
				
				if (DataConverter.isInteger(fldTypeVal)) {
					mdbFieldType = EMdbFieldType.fromInt(Integer.parseInt(fldTypeVal));					
				}
				
				
				if (mdbFieldType == EMdbFieldType.LOOK_UP && lookUpSrcVal!= null ) {
				
					if (DataConverter.isInteger(lookUpSrcVal) ) {				
						toReturnReq.add(new RequestEntity(Integer.parseInt(lookUpSrcVal)));
					}	else {
							String colName = resultSet.getString(colIndxName);
							_logger.info( "Find LookUp by table constrain for table:"+ lookUpSrcVal+ " and column "+ colName);
							Params params = new Params();										
							params.add("TBL", lookUpSrcVal);
							params.add("col", colName);
							
							ResultSet rsByConstraint = qByConstraint.getResultSet(params, null);
							
							while (rsByConstraint.next()) {
								
								String tblName = rsByConstraint.getString("FULL_TBL_NAME");
								
								String key =  resultSet.getString(colIndxLookUpKey);
								String fields =  resultSet.getString(colIndxLookUpFlds);
								
								int entityId = (colName + lookUpSrcVal+fields+key).hashCode();
								_logger.info( "Generate new Query for "+colName+","+ lookUpSrcVal+","+fields+","+ key  +" EntityId (Hash) is  :"+entityId);
								
								toReturnReq.add(new RequestEntity( entityId) );	
								
								if ( !_entityDataAccess.getQueryPool().existQuery(entityId) ) {
								
									IQuery dynamicQ =  _entityDataAccess.getQueryPool().getEmptyQuery(entityId);									
									
									ArrayList<String> list  = new  ArrayList<String>(Arrays.asList(fields.split(";"))) ;
									if (!list.contains(key) ) {
										list.add(key);
									}							
									
									fields = list.toString().replace("[", "").replace("]","");
									String sql = String.format("Select %s from %s", fields, tblName);
									
									dynamicQ.setSelect(sql);
								}
							}
							
							rsByConstraint.close();
							qByConstraint.close();
					}
				}
			}
		} catch (SQLException e) {
			_logger.severe(e.getMessage());
		}
		return toReturnReq;
	}
	

	/**
	 * @param entitysForLookUps
	 */
	private void buildLookUpSrc(Request  request, Request entitysForLookUps) {
		_logger.info( "				Start Get Data for LookUp source in Requst:"+request.getRequestId());
		for (IRequestData requestData: entitysForLookUps.getArrayListValues() ) {
			RequestEntity requestEntity = (RequestEntity) requestData;
			
			_logger.info( "					Get Data for entitId :"+requestEntity.getEntityId());				
			
			
			IQuery q = _entityDataAccess.prepareQuery(requestEntity);
			
			ResultSet resultSet = q.getResultSet(requestEntity.getParams(), null);

			if (resultSet != null) {
			
				if (requestEntity.getEntityId() != TBL_BY_CONSTRAINT) {
					_logger.info( "						Start Data transformation for entitId = "+requestEntity.getEntityId());
						
							transformationResultSet(requestEntity, resultSet);						
						
							request.add(requestEntity);
					_logger.info( "						Sucess Data transformation for entitId = "+requestEntity.getEntityId());	
						
				}
			
				try {
						resultSet.close();	
						q.close();
						_logger.info( "					Close data source for entitId = "+requestEntity.getEntityId());
				} catch (SQLException e) {						
							_logger.severe(e.getMessage());							
				}
			}
		}
		_logger.info( "				End  Get Data for LookUp source in Requst:"+request.getRequestId());
	}		


	/**
	 * @param entitysForLookUps
	 * @param requestEntity
	 * @param entityId
	 */
	private void generateColumnsDefinitions(Request request,  Request entitysForLookUps, int entityId, String viewNN) {		
			_logger.info( "Get columns definitions for entityId = "+entityId +" in Requst:"+request.getRequestId());
			
		   RequestEntity entFldsDescr =  (RequestEntity)request.add( new RequestEntity (ENTITY_FLDS_DESCR, FLD_ENTITY_DESC_KEY+entityId ) );
		   entFldsDescr.setExecuteType(ExecuteType.GetData);
		   entFldsDescr.getParams().add("ID_DENTITY", Integer.toString(entityId) );
		   entFldsDescr.getParams().add("VIEW_NN", viewNN);
		   
		   IQuery q = _entityDataAccess.prepareQuery(entFldsDescr);
		   ResultSet  resultSet = q.getResultSet(entFldsDescr.getParams(), null);
		   
		   if (resultSet != null) {
			   
			   	   entFldsDescr.setKeys(_entityDataAccess.getQuery(entityId).getEntityKeys());		
			   	   
			   	   transformationResultSet(entFldsDescr, resultSet);
			   	   //_logger.info(entFldsDescr.getData());
				   entitysForLookUps.add(prepareMetaData(resultSet));
				   _logger.info("Success get columns definitions for entityId = " + entityId +" in Requst:"+request.getRequestId());
				   
				   try {
					resultSet.close();
					q.close();
				} catch (SQLException e) {
					_logger.severe(e.getMessage());
				}
				   
		   }
		   else {
				   _logger.info( "Not  columns definitions for RequestEntiy = "+entityId +" in Requst:"+request.getRequestId());
		   }
	}
	
	
	@SuppressWarnings("unused")
	private ResultSet applyFilter(RequestEntity entity) {
		ResultSet toReturn  = null;
		if ( entity.isExecAction() ) {						
			
			for  ( Entry<Integer, ChangedData> entry : entity.getExecActions().entrySet() ) {
				
				Map<String,String> mapOriginValue = JSONTransformation.json2Map(entry.getValue().getOriginValue());
				Map<String,String> mapChangedValue =JSONTransformation.json2Map(entry.getValue().getChangedValue());					
					
				mapOriginValue.putAll(mapChangedValue);
				
				IQuery query = _entityDataAccess.getQuery(entity.getEntityId());
				
				Params params = new Params();				
				for (Entry<String, String>  parEntry : mapOriginValue.entrySet()) {
					params.add(parEntry.getKey(), parEntry.getValue());
				}								
				toReturn = query.getResultSet(entry.getKey().intValue(), params );				 
			}				
		}
		return toReturn;		
	}	
	
	
	@SuppressWarnings("unused")
	private void analiseBuisnessLogic() {
		_logger.info( "Start analize BuisnessLogic");
		_logger.info( "Not Implemented");
		_logger.info( "End analize BuisnessLogic");		
	}
	


	/* (non-Javadoc)
	 * @see mdb.gateway.IRequestAnalyzer#getSerialiser()
	 */
	@Override
	public IRequestSerialiser getSerialiser() {
		return _requestSerialiser;
	}

}
