/**
 * 
 */
package mdb.core.shared.gw.mdb;

import javax.jws.WebMethod;
import javax.jws.WebService;

import mdb.core.shared.transport.Request;

/**
 * @author azhuk
 * Creation date: Aug 2, 2012
 *
 */
@WebService
public interface IMdbGatewayServiceRemote {
	@WebMethod
	public Request call(Request value);
	@WebMethod
	public String batchJsonCall(String value);
	@WebMethod
	public byte[] binaryCall(byte[] request);
}
