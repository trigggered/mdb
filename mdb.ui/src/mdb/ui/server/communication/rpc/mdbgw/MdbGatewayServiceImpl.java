package mdb.ui.server.communication.rpc.mdbgw;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import mdb.core.shared.gw.MdbGatewayClientBuilder;
import mdb.core.shared.gw.RemoteServiceClientType;
import mdb.core.shared.gw.mdb.IMdbGatewayServiceRemote;
import mdb.core.shared.transport.Request;
import mdb.ui.client.communication.rpc.mdbgw.MdbGatewayService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class MdbGatewayServiceImpl   extends RemoteServiceServlet implements MdbGatewayService{

	private static final Logger _logger = Logger.getLogger(MdbGatewayServiceImpl.class.getName());
	
	private static final long serialVersionUID = 1L;

		
	public MdbGatewayServiceImpl () {
		//HttpServletRequest request = this.getThreadLocalRequest();
	}


	/* (non-Javadoc)
	 * @see mdb.ui.client.rpc.gateway.GatewayAccessService#Invoke(mdb.core.transport.IRequest)
	 */
	@Override
	public Request Invoke(Request input) throws IllegalArgumentException {
		_logger.info("");
		_logger.info("Invoke WebGatewayInvoke.Call");
		
		IMdbGatewayServiceRemote gwInvoke = getClientProxy();
		  Request toReturn = gwInvoke.call(input);
		_logger.info("Success invoke WebGatewayInvoke.Call");
		_logger.info("#############################################");
		_logger.info("#############################################");
		_logger.info("");
		
		return  toReturn;
	}

	private IMdbGatewayServiceRemote getClientProxy() {
		//IMdbGatewayServiceRemoute clientProxy = GatewayInvokeBuilder.create(RemouteServiceInvokeType.EjbInvoke);
		return MdbGatewayClientBuilder.create(RemoteServiceClientType.WebSrvInvoke);

	}

	/* (non-Javadoc)
	 * @see mdb.ui.client.rpc.gateway.GatewayAccessService#BatchInvoke(java.util.List)
	 */
	@Override
	public List<Request> batchInvoke(List<Request> input) {
		_logger.info("Batch invoke WebGatewayInvoke.Call input List<Request>.size =" + input.size());
		
		List<Request> toReturn = new ArrayList<Request>();		

		IMdbGatewayServiceRemote clientProxy = getClientProxy();

		for (Request inReq : input) {
			toReturn.add(clientProxy.call(inReq));
		}		 
		
		_logger.info("Success Batch invoke WebGatewayInvoke.Call. Return output List<Request>.size: " + toReturn.size());
		return  toReturn;
	}
	
}
