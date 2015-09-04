package test.mdb.environment;

import mdb.core.config.IAppConfig;
import mdb.core.config.LocalAppConfig;
import mdb.core.db.EntityDataAccess;
import mdb.core.db.IEntityDataAccess;
import mdb.core.db.IMdbQueryPreparator;
import mdb.core.db.IQueryPool;
import mdb.core.db.MdbQueryManualPreparator;
import mdb.core.db.QueryPool;
import mdb.core.db.connection.IConnectionManager;
import mdb.core.db.connection.LocalConnectionManagerImpl;
import mdb.core.db.query.IQuery;
import mdb.core.db.query.impl.Query;
import mdb.core.db.query.paging.IQueryPaging;
import mdb.core.db.query.paging.impl.MySqlQueryPaging;
import mdb.core.shared.transformation.mdbrequest.IRequestSerialiser;
import mdb.core.shared.transformation.mdbrequest.JSONRequestSerialiser;
import mdb.core.shared.transformation.sql.IResultSetSerializer;
import mdb.core.shared.transformation.sql.ResultSetManualSerializerImpl;
import mdb.gateway.IRequestAnalyzer;
import mdb.gateway.RequestAnalyzer;
import mdb.injector.IInjectConfiguration;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;



public class TestInjectConfiguration implements Module, IInjectConfiguration  {

	@Override
	public void configure(Binder binder) {				
		
		binder.bind(IAppConfig.class)
		.to(LocalAppConfig.class)
		.in(Scopes.SINGLETON);	
		
		binder.bind(IInjectConfiguration.class)
		.to(TestInjectConfiguration.class)
		.in(Scopes.SINGLETON);
		

		binder.bind(IConnectionManager.class)
		.to(LocalConnectionManagerImpl.class)
		.in(Scopes.SINGLETON);		
	
		binder.bind(IMdbQueryPreparator.class)
		.to(MdbQueryManualPreparator.class);
		
		binder.bind(IQueryPool.class)
		.to(QueryPool.class)
		.in(Scopes.SINGLETON);
		
		binder.bind(IEntityDataAccess.class)
		.to(EntityDataAccess.class)
		.in(Scopes.SINGLETON);
		
		binder.bind(IResultSetSerializer.class)
		.to(ResultSetManualSerializerImpl.class)
		.in(Scopes.SINGLETON);	
		
		binder.bind(IRequestSerialiser.class)
		.to(JSONRequestSerialiser.class)
		.in(Scopes.SINGLETON);
		
		binder.bind(IRequestAnalyzer.class)
		.to(RequestAnalyzer.class)
		.in(Scopes.SINGLETON);
		
		binder.bind(IQueryPaging.class)
		.to(MySqlQueryPaging.class)
		.in(Scopes.SINGLETON);
		
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
