/**
 * 
 */
package mdb.core.shared.gw;

import mdb.core.shared.gw.mdb.IMdbGatewayServiceRemote;
import mdb.core.shared.gw.mdb.MdbGatewayClient;

/**
 * @author azhuk
 * Creation date: Jul 2, 2013
 *
 */
public class MdbGatewayClientBuilder {

	public static IMdbGatewayServiceRemote create(RemoteServiceClientType value) {
		
		switch (value) {
		case EjbInvoke :			
			return new MdbGatewayClient(IMdbGatewayServiceRemote.class, "EjbGateway");
			//return  MdbGatewayClient.createEjbGatewayServiceRemote(); 
		case WebSrvInvoke:	 
			IMdbGatewayServiceRemote clientProxy = MdbGatewayClient.instance();		
			return  clientProxy;
			
		}
		return null;
		
	}
	
	
}
