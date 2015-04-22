/**
 * 
 */
package mdb.core.shared.gw;

import mdb.core.shared.gw.lob.ILobManagerRemote;
import mdb.core.shared.gw.lob.LobManagerClient;



/**
 * @author azhuk
 * Creation date: Aug 30, 2013
 *
 */
public class LobManagerClientBuilder {
	
	public static ILobManagerRemote  create (RemoteServiceClientType value) {
		 switch (value) {
		 case EjbInvoke:
			 return LobManagerClient.instance();							 
		 case WebSrvInvoke:
			 return LobManagerClient.instance();			 
		 }
		return null;
	}
}
