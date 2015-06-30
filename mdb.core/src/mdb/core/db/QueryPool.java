package mdb.core.db;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import mdb.core.config.IAppConfig;
import mdb.core.config.JBossConfig;
import mdb.core.db.connection.IConnectionManager;
import mdb.core.db.entity.EntityKeys;
import mdb.core.db.entity.EntityMethods;
import mdb.core.db.entity.MdbEntityFacade;
import mdb.core.db.query.IQuery;
import mdb.core.db.sql.SQLTemplates;
import mdb.core.shared.data.EMdbEntityActionType;
import mdb.core.shared.data.Params;
import mdb.injector.SingletonInjector;

@Stateless
public class QueryPool implements IQueryPool {
	
	@EJB
	private MdbEntityFacade _mdbEntityFacade;		
	
	
	private static final Logger _logger = Logger
			.getLogger(QueryPool.class.getCanonicalName());
	
	protected static final int Q_XSL = 208;
	// Members	
	protected final int DENTITY_METHODS 		= -1;
	protected final int DENTITY_KEYS 			= 22;

	private Hashtable<Integer, IQuery>	_htQuerysPool = new Hashtable<Integer, IQuery>();


	private IQuery createQuery (int entityId) {						
		IQuery  q = SingletonInjector.getInjector().getInstance(IQuery.class);			
		q.setQueryID(entityId);
	
		return q;
	}
	
	@PostConstruct
	private void Intialise() {
		//prepareSystemQuerys();
	}
		
	
	private boolean prepareSystemQuerys() {
		
		 closeAll();						 
		
		IQuery aQuery = createQuery(DENTITY_METHODS);
		aQuery.setSelect(SQLTemplates.QUERY_METHOD);
		putQueryIntoPool(aQuery.getQueryID(), aQuery);

		generateQuery(DENTITY_KEYS);

		return true;
	}
	
	protected String getQueryMethod() {
		return SQLTemplates.QUERY_METHOD;
	}
	

	protected String getQueryUserRight() {
		return SQLTemplates.QUERY_USER_RIGHT; 
	}

	private IQuery createQueryWithMethods2(int entityId) {
			
		IQuery newQuery = createQuery(entityId);		
		
		
		
		boolean fSetCustomConfiguration = false;
		for (EntityMethods method : _mdbEntityFacade.findMethods(entityId) ) {	
		
			final String dataSourceName = method.getDsName();						
			
			if (dataSourceName != null && dataSourceName.length() > 0 && !fSetCustomConfiguration) {
				fSetCustomConfiguration = true;
				_logger.info("Data Source name = "+dataSourceName);	
				IConnectionManager connManager = SingletonInjector.getInjector().getInstance(IConnectionManager.class);
				
				connManager.setAppConfig(	new JBossConfig() {
					@Override
					public String getJdbcDataSourceName() {	
						return dataSourceName;
					}
				});
				newQuery.setConnectionManager(connManager);
			}			
			
			newQuery.setQueryID(entityId);
			newQuery.getQueryPaging().setPagingUsage(method.isPaging());			
			
			EMdbEntityActionType actionType = EMdbEntityActionType.fromInt(method.getIdAction() ); 
			switch ( actionType )  {
				case SELECT :						
					newQuery.setSelect(method.getBody());
					break;
				case INSERT : 
					newQuery.setInsert(method.getBody());
					break;
				case UPDATE : 
					newQuery.setUpdate(method.getBody());
					break;
				case DELETE : 	
					newQuery.setDelete(method.getBody());
					break;
				case REFRESH :	
					newQuery.setRefresh(method.getBody());
					break;			
				 
				case STORED_PROC:
				case PREPERE_FILTER: 
							newQuery.setAction(actionType, method.getIdExtMethod(), method.getBody()); //Stored Proc
						 break;
			default:
				break;
						 
			}								
		}				
	
		if (newQuery != null ) {
			for (EntityKeys keys : _mdbEntityFacade.findKeys(entityId) ) {
				newQuery.setEntityKey(keys.getKeyField(), keys.getSeqName());				
			}
		}
		
		 return newQuery;
	}
	
	private IQuery createQueryWithMethods(int entityId) {
		
		final int METHOD_BODY_COL_INDEX = 4;// METHOD_BODY
		
		IQuery qDEntityMethods = getQueryFromPool(DENTITY_METHODS);
		if (qDEntityMethods == null ) {
			return null;
		}

		Params params = new Params();
		
		params.add("ID_DENTITY", String.valueOf(entityId));
		ResultSet rs = qDEntityMethods.getResultSet(params, null);				
		
		if (rs != null) {
			try {
				ResultSetMetaData rsmd = rs.getMetaData();
												
				IQuery newQuery = createQuery(entityId);
								
				while (rs.next()) {

					//newQuery.setIsArchive(rs.getInt("IS_ARCHIVE") > 0);
					String methodBody = null;					
					if (rsmd.getColumnType(METHOD_BODY_COL_INDEX) == java.sql.Types.CLOB) {	
						Clob clMethodBody = rs.getClob(METHOD_BODY_COL_INDEX);
						methodBody = clMethodBody.getSubString(1, (int) clMethodBody.length());
					} 
					else  {
						methodBody = rs.getString(METHOD_BODY_COL_INDEX);
					}										
					
					EMdbEntityActionType actionType = EMdbEntityActionType.fromInt(rs.getInt("ID_DENTITY_ACTION") );
					switch ( actionType )  {
						case SELECT :						
							newQuery.setSelect(methodBody);
							break;
						case INSERT : 
							newQuery.setInsert(methodBody);
							break;
						case UPDATE : 
							newQuery.setUpdate(methodBody);
							break;
						case DELETE : 	
							newQuery.setDelete(methodBody);
							break;
						case REFRESH :	
							newQuery.setRefresh(methodBody);
							break;			
						 
						case STORED_PROC:
						case PREPERE_FILTER: 
									newQuery.setAction(actionType, rs.getInt("ID_EXT_METHOD"), methodBody); //Stored Proc
								 break;
					default:
						break;
								 
					}
				}
				rs.close();
				qDEntityMethods.close();

				
				if (entityId != DENTITY_KEYS ) {
					IQuery qDEntityKeys = getQueryFromPool(DENTITY_KEYS);	
					
					if (qDEntityKeys != null ) {
						Params p = new Params();						
						p.add("pID_DENTITY", String.valueOf(entityId));
						rs = qDEntityKeys.getResultSet(p, null);
						if (rs != null) {
							while (rs.next()) {
								newQuery.setEntityKey(rs.getString("KEY_FIELD"), rs.getString("SEQ_NAME"));
							}
							rs.close();
							qDEntityKeys.close();
						}
					}
				}				
				return newQuery;
			}

			catch (SQLException ex) {
				_logger.severe(ex.getMessage());				
				return null;
			} finally {

			}
		}
		return null;
	}

	public ResultSet getQueryKeys(int entityID) {
		ResultSet Result = null;
		IQuery qDEntityKeys = getQueryFromPool(DENTITY_KEYS);
		if (qDEntityKeys != null) {
			Params ParamsData = new Params();			
			ParamsData.add("ID_DENTITY", String.valueOf(entityID) );
			Result = qDEntityKeys.getResultSet(ParamsData, null);
		}
		return Result;
	}


	private IQuery getQueryFromPool(int entityID) {
		IQuery q = null;
		if (existQuery(entityID)) {
			q = _htQuerysPool.get( Integer.valueOf(entityID) );		
		}
		return q;		
	}

	public boolean existQuery(int entityID) {		
		return _htQuerysPool.containsKey( Integer.valueOf(entityID));
	}
	
	private void putQueryIntoPool(int nDEntityID, IQuery aQuery) {
		if (aQuery != null && this.getQueryFromPool(nDEntityID) == null) {
			_htQuerysPool.put(Integer.valueOf (nDEntityID), aQuery);
		}
	}



	@SuppressWarnings("unused")
	private IQuery generateQuery(int entityID) {

		IQuery toReturn = null;
		
		//if (_appConfig.IsUsingQueryPool())	
		if (false)
		{
			toReturn = getQueryFromPool(entityID);
			if (toReturn == null) {
				toReturn = createQueryWithMethods2(entityID);
				//toReturn = createQueryWithMethods(entityID);
				if (toReturn != null) {
					this.putQueryIntoPool(entityID, toReturn);
				}
			}			
		} else
			toReturn = createQueryWithMethods2(entityID);
		
		return toReturn;
	}

	
	public IQuery getQuery(int entityID) {
		return generateQuery(entityID);
	}
	
	public IQuery getEmptyQuery(int entityID) {
		IQuery toReturn = createQuery(entityID);
		putQueryIntoPool(entityID, toReturn);
		return toReturn;
	}
	


	private void closeAll() {
		if (_htQuerysPool == null)
			return;
		
		for (Map.Entry<Integer,IQuery> entry : _htQuerysPool.entrySet()) {
			IQuery q  = entry.getValue();
			if (q != null) {				
				q.close();
			}
		}
	}
	
}