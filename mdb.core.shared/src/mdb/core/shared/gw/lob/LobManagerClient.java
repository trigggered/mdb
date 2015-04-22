/**
 * 
 */
package mdb.core.shared.gw.lob;

import java.util.logging.Logger;

import javax.jws.WebMethod;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mdb.core.shared.gw.WebServiceClient;
import mdb.core.shared.transport.LobStoredResult;


/**
 * @author azhuk
 * Creation date: Aug 30, 2013
 *
 */
public class LobManagerClient extends WebServiceClient<ILobManagerRemote> implements  ILobManagerRemote {
	/**
	 * @param serviceClass
	 * @param webSrvName
	 */
	public LobManagerClient(Class<ILobManagerRemote> serviceClass,
			String webSrvName) {
		super(ILobManagerRemote.class, "LobManager");
	}


	private static ILobManagerRemote _instance;
 	
	
	public static ILobManagerRemote instance() {
		if (_instance== null) {
			_instance = new LobManagerClient(ILobManagerRemote.class, "LobManager");
		}
 		return _instance; 
 	}

	private static final Logger _logger = Logger
			.getLogger(LobManagerClient.class.getName());
	
	@SuppressWarnings("unused")
	private  static ILobManagerRemote getEjbLobManager() {
		
		ILobManagerRemote toReturn = null;
		
			_logger.info( "##########################################################################");
			_logger.info( "################### start InitialContext().lookup ###################################");
			try {
				toReturn  = (ILobManagerRemote)new InitialContext().lookup("java:global/mdb.core/LobManager");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				_logger.severe(e.getMessage());
			}			
			
			_logger.info( "################### end InitialContext().lookup ###################################");
			
			return toReturn;
	}


	protected  String getNamespace() {
		return "http://storage.lob/";
	}		
	

	/* (non-Javadoc)
	 * @see mdb.core.shared.gw.lob.ILobManagerRemote#putLob(java.lang.String, java.lang.String, long, byte[])
	 */
	@Override
	public LobStoredResult  putLob(String lobName, String author, long contentType,
			byte[] lob) {
		ILobManagerRemote client = getWebClientProxy();
		return client.putLob(lobName, author, contentType, lob);
		
	}


	/* (non-Javadoc)
	 * @see mdb.core.shared.gw.lob.ILobManagerRemote#getLob(long)
	 */
	@Override
	@WebMethod
	public LobStoredResult getLob(long idLob) {
		ILobManagerRemote client = getWebClientProxy();
		return  client.getLob(idLob);		
	}


	/* (non-Javadoc)
	 * @see mdb.core.shared.gw.lob.ILobManagerRemote#remove(long)
	 */
	@Override
	@WebMethod
	public boolean remove(long idLob) {
		return getWebClientProxy().remove(idLob);
	}
}
