/**
 * 
 */
package mdb.core.shared.gw.mail;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author azhuk
 * Creation date: Oct 8, 2014
 *
 */
@WebService
public interface IMailGatewayServiceRemote {

	@WebMethod
	 public void send(String addressFrom, String[] addressTo  ,String subject, String content) ;	
}
