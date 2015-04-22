package mdb.injector;

import mdb.core.config.IAppConfig;
import mdb.core.config.OraJBossConfig;
import mdb.core.db.EntityDataAccess;
import mdb.core.db.IEntityDataAccess;
import mdb.core.db.IQueryPool;
import mdb.core.db.QueryPool;
import mdb.core.db.connection.IConnectionManager;
import mdb.core.db.connection.ServerConectionManagerImpl;
import mdb.core.db.query.IQuery;
import mdb.core.db.query.impl.Query;
import mdb.core.db.query.paging.IQueryPaging;
import mdb.core.db.query.paging.impl.OraQueryPaging;
import mdb.core.shared.transformation.IDataTransformation;
import mdb.core.shared.transformation.IRequestSerialiser;
import mdb.core.shared.transformation.impl.JSONRequestSerialiser;
import mdb.core.shared.transformation.impl.JSONTransformation;
import mdb.gateway.IRequestAnalyzer;
import mdb.gateway.RequestAnalyzer;

import com.google.inject.Binder;
import com.google.inject.Module;

public class InjectConfiguration implements Module, IInjectConfiguration  {

	@Override	
	public void configure(Binder binder) {
		
		binder.bind(IAppConfig.class)
		.to(OraJBossConfig.class);
		//.to(AppConfig.class);
		//.in(Scopes.SINGLETON);		
		
		binder.bind(IInjectConfiguration.class)
		.to(InjectConfiguration.class);
		//.in(Scopes.SINGLETON);
		
		binder.bind(IConnectionManager.class)
		.to(ServerConectionManagerImpl.class);
		//.in(Scopes.SINGLETON);
		
		binder.bind(IQueryPool.class)
		.to(QueryPool.class);
		//.in(Scopes.SINGLETON);
		
		binder.bind(IEntityDataAccess.class)
		.to(EntityDataAccess.class);
		//.in(Scopes.SINGLETON);
		
		binder.bind(IDataTransformation.class)
		.to(JSONTransformation.class);
		//.in(Scopes.SINGLETON);		
		
		binder.bind(IRequestSerialiser.class)
		.to(JSONRequestSerialiser.class);
		//.in(Scopes.SINGLETON);
		
		binder.bind(IRequestAnalyzer.class)
		.to(RequestAnalyzer.class);
		//.in(Scopes.SINGLETON);
		
		binder.bind(IQueryPaging.class)
		.to(OraQueryPaging.class);
		//.in(Scopes.SINGLETON);		
		
		binder.bind(IQuery.class)
		.to(Query.class);				
		
	}

	/* (non-Javadoc)
	 * @see mdb.environment.IConfiguration#getModule()
	 */
	@Override
	public Module getModule() {		
		return this;
	}

}
