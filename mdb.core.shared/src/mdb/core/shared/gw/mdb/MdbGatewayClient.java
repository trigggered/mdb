package mdb.core.shared.gw.mdb;


import java.io.IOException;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import mdb.core.shared.gw.WebServiceClient;
import mdb.core.shared.transformation.mdbrequest.IRequestSerialiser;
import mdb.core.shared.transformation.mdbrequest.JSONRequestSerialiser;
import mdb.core.shared.transport.Request;
import mdb.core.shared.utils.zip.GZipHelper;




public class MdbGatewayClient  extends WebServiceClient<IMdbGatewayServiceRemote> 
	implements IMdbGatewayServiceRemote {
	
	/**
	 * @param serviceClass
	 * @param webSrvName
	 */
	public MdbGatewayClient(Class<IMdbGatewayServiceRemote> serviceClass,
			String webSrvName) {
		super(serviceClass, webSrvName);
	}



	private static final Logger _logger = Logger
			.getLogger(MdbGatewayClient.class.getName());

	private static IMdbGatewayServiceRemote _instance;
 	
	
	public static IMdbGatewayServiceRemote instance() {
		if (_instance== null) {
			_instance = new MdbGatewayClient(IMdbGatewayServiceRemote.class, "EjbGateway");
		}
 		return _instance; 
 	}
	
	
	@Override
	protected String getNamespace() {
		return "http://impl.gateway.mdb/";
	}
	
	


	public static IMdbGatewayServiceRemote createEjbGatewayServiceRemote () {
		IMdbGatewayServiceRemote gatewayService = null;
		try {
			_logger.info( "##########################################################################");
			_logger.info( "################### start InitialContext().lookup ###################################");
			//gatewayService  = (IMdbGatewayServiceRemote)new InitialContext().lookup("java:global/mdb.core/EjbGateway");
			
			gatewayService  = (IMdbGatewayServiceRemote)new InitialContext().lookup("java:global/mdb.core/EjbGateway!mdb.gateway.impl.EjbGateway");
			
			_logger.info( "################### end InitialContext().lookup ###################################");
			
			
		} catch (NamingException e) {
			_logger.severe(e.getMessage());			
		}
		
		return gatewayService;
	}
	
	
	@Override
	public Request call (Request request) {
		Request response = null;
		
		IRequestSerialiser serialiser = new JSONRequestSerialiser();
		
		byte[] inBytes = null;
		try {
			inBytes = GZipHelper.compress(serialiser.serialize(request));
		} catch (IOException e) {
			_logger.severe(e.getMessage());			
		}		
		
		byte[] outBytes =  getWebClientProxy().binaryCall( inBytes);
		String jsonReaponse = new String (GZipHelper.uncompress(outBytes));
		//_logger.info(jsonReaponse);
		response = serialiser.deserialize(jsonReaponse);	
		return response;
		
	}


	/* (non-Javadoc)
	 * @see mdb.core.shared.gw.mdb.IMdbGatewayServiceRemote#binaryCall(byte[])
	 */
	@Override	
	public byte[] binaryCall(byte[] inBytes) {
		
		return getWebClientProxy().binaryCall( inBytes);
	}


	/* (non-Javadoc)
	 * @see mdb.core.shared.gw.mdb.IMdbGatewayServiceRemote#call(java.util.List)
	 */
	@Override
	public String  batchJsonCall(String value) {
		return getWebClientProxy().batchJsonCall( value);
	}
}
