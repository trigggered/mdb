package mdb.core.db;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mdb.core.db.entity.MdbEntityFacade;
import mdb.core.db.query.IQuery;
import mdb.core.db.sql.SQLTemplates;
import mdb.core.shared.data.Params;
import mdb.injector.SingletonInjector;

@Stateless
public class QueryPool implements IQueryPool {
	
	@EJB
	private MdbEntityFacade _mdbEntityFacade;		

	
	@EJB
	private IMdbQueryPreparator _queryPreparator;
	
	
	private static final Logger _logger = Logger
			.getLogger(QueryPool.class.getCanonicalName());
	
	protected static final int Q_XSL = 208;
	// Members	

	protected final int DENTITY_KEYS 			= 22;

	private Hashtable<Integer, IQuery>	_htQuerysPool = new Hashtable<Integer, IQuery>();

	@Override
	public  IQuery createQuery (int entityId) {						
		IQuery  q = SingletonInjector.getInjector().getInstance(IQuery.class);			
		q.setQueryID(entityId);
	
		putQueryIntoPool(q.getQueryID(), q);
		
		return q;
	}
		
	
	
	protected String getQueryMethod() {
		return SQLTemplates.QUERY_METHOD;
	}
	

	protected String getQueryUserRight() {
		return SQLTemplates.QUERY_USER_RIGHT; 
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


	@Override
	public IQuery getQueryFromPool(int entityID) {
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



	private IQuery generateQuery(int entityId) {

		IQuery toReturn = null;
		
		//if (_appConfig.IsUsingQueryPool())	
		if (false)
		{
			toReturn = getQueryFromPool(entityId);
			if (toReturn == null) {
				toReturn = _queryPreparator.create(entityId);
				//toReturn = createQueryWithMethods(entityID);
				if (toReturn != null) {
					this.putQueryIntoPool(entityId, toReturn);
				}
			}			
		} else
			toReturn = _queryPreparator.create(entityId);
		
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
	


	@Override
	public  void closeAll() {
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