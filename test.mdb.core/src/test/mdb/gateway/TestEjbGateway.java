/**
 * 
 */
package test.mdb.gateway;

import java.util.logging.Logger;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import junit.framework.TestCase;
import mdb.core.shared.gw.mdb.IMdbGatewayServiceRemote;


/**
 * @author azhuk
 * Creation date: May 17, 2013
 *
 */
public class TestEjbGateway extends TestCase {
	private static final Logger _logger = Logger.getLogger(TestEjbGateway.class
			.getCanonicalName());
	
	public void test1() {

		IMdbGatewayServiceRemote gatewayService = null;
		
		final Context context = EJBContainer.createEJBContainer().getContext();
		
		try {
			gatewayService  = (IMdbGatewayServiceRemote)context.lookup("java:global/mdb.core/EjbGateway");
		} catch (NamingException e) {
			
			_logger.severe(e.getMessage());
		}		
		assertNotNull(gatewayService);		
	}
}
