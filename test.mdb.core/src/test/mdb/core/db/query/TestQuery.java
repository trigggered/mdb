/**
 * 
 */
package test.mdb.core.db.query;

import java.util.logging.Logger;

import junit.framework.TestCase;
import mdb.core.db.query.IQuery;
import mdb.core.db.query.paging.IQueryPaging;
import mdb.core.shared.data.EMdbEntityActionType;
import test.mdb.environment.TestInjectConfiguration;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author azhuk
 * Creation date: Nov 1, 2012
 *
 */
public class TestQuery extends TestCase {
	private static final Logger _logger = Logger.getLogger(TestQuery.class
			.getCanonicalName());

	public TestQuery() {
		//Injector injector = Guice.createInjector(new Configuration());
		//IQueryPaging  q = injector.getInstance(IQueryPaging.class);
	}
	
	
	public void testEnumQueryAction() {
		EMdbEntityActionType action = EMdbEntityActionType.SELECT;

		assertEquals(EMdbEntityActionType.SELECT, EMdbEntityActionType.valueOf("SELECT"));
		
		assertEquals(1, action.getValue());
		
		EMdbEntityActionType action2 = EMdbEntityActionType.fromInt(5);
		assertEquals(EMdbEntityActionType.REFRESH, action2);
		
		
	}
	
	public void testQuery() {
		
		Injector injector = Guice.createInjector(new TestInjectConfiguration());
		IQueryPaging  qp = injector.getInstance(IQueryPaging.class);
		
		IQuery  q = injector.getInstance(IQuery.class);						
		
	
		assertNotNull(qp);
		
		assertNotNull(q.getQueryPaging());
		assertNotNull(q.getConnectionManager());
		
		q.setQueryID(1);
		
		IQuery  q2 = injector.getInstance(IQuery.class);
		
		assertNotSame(q, q2);
	}
}
