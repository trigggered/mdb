/**
 * 
 */
package mdb.core.shared.gw;

import mdb.core.shared.gw.mail.IMailGatewayServiceRemote;
import mdb.core.shared.gw.mail.MailGatewayClient;

/**
 * @author azhuk
 * Creation date: Jul 2, 2013
 *
 */
public class MailGatewayClientBuilder {
	public static IMailGatewayServiceRemote create(RemoteServiceClientType value) {
		
		switch (value) {
		case EjbInvoke :
		case WebSrvInvoke:
			return new MailGatewayClient(IMailGatewayServiceRemote.class, "MailGateway");			
		}
		return null;
		
	}
	
}
