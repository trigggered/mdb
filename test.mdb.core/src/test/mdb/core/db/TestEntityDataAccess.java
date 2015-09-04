package test.mdb.core.db;

import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.TestCase;
import mdb.core.db.IEntityDataAccess;
import mdb.core.shared.data.Params;
import mdb.injector.SingletonInjector;
import test.mdb.environment.TestInjectConfiguration;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class TestEntityDataAccess extends TestCase {
	
	private IEntityDataAccess _entityDataAccess;
	private static final Logger logger = Logger.getLogger(TestEntityDataAccess.class.getCanonicalName());
	
	public TestEntityDataAccess() {
		
		Injector injector = Guice.createInjector(new TestInjectConfiguration());
		SingletonInjector.setInjector(injector);
		_entityDataAccess = injector.getInstance(IEntityDataAccess.class);     
	}
	
	public void testOpen () {
		
		logger.log(Level.INFO, "Start call");
		int entityId = 1428;	
		//MdbEntryPoint mdbEntryPoint = new MdbEntryPoint();		
		
		Params params  = new Params();
		params.add("TaskId", "1");
		ResultSet rs = _entityDataAccess.getResultSet(entityId, params);
		assertNotNull(rs);
		String xmlData = null;
		//String xmlData = RowSetHelper.serialization(rs);
		logger.log(Level.INFO, xmlData)
		;
		
		assertNotNull(xmlData);
		
		logger.log(Level.INFO, "End call");
	}	
	

}
