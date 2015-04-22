/**
 * 
 */
package mdb.core.shared.gw.lob;

import javax.jws.WebMethod;
import javax.jws.WebService;

import mdb.core.shared.transport.LobStoredResult;


/**
 * @author azhuk
 * Creation date: Aug 30, 2013
 *
 */
@WebService
public interface ILobManagerRemote {

	@WebMethod
	public LobStoredResult putLob(String lobName, String  author, long contentType,  byte[]  lob );
	
	@WebMethod
	public LobStoredResult getLob(long idLob);
	
	@WebMethod
	public boolean remove(long idLob);

}
