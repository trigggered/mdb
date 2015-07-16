package mdb.core.vaadin.ui.communication.mdbgw;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import mdb.core.shared.gw.MdbGatewayClientBuilder;
import mdb.core.shared.gw.RemoteServiceClientType;
import mdb.core.shared.gw.mdb.IMdbGatewayServiceRemote;
import mdb.core.shared.transformation.mdbrequest.IRequestSerialiser;
import mdb.core.shared.transformation.mdbrequest.JSONRequestSerialiser;
import mdb.core.shared.transport.Request;



public class MdbGatewayServiceImpl   implements MdbGatewayService{

	private static final Logger _logger = Logger.getLogger(MdbGatewayServiceImpl.class.getName());
	
	private static final long serialVersionUID = 1L;

	IRequestSerialiser _serialiser = new JSONRequestSerialiser ();
		
	public MdbGatewayServiceImpl () {
		//HttpServletRequest request = this.getThreadLocalRequest();
	}


	/* (non-Javadoc)
	 * @see mdb.ui.client.rpc.gateway.GatewayAccessService#Invoke(mdb.core.transport.IRequest)
	 */
	@Override
	public Request Invoke(Request input) throws IllegalArgumentException {
		_logger.info("input request size is: "+ input.size() );
		_logger.info("Invoke WebGatewayInvoke.Call");
		
		
		IMdbGatewayServiceRemote gwInvoke = getClientProxy();
		  Request toReturn = gwInvoke.call(input);
		_logger.info("Success invoke WebGatewayInvoke.Call");
		_logger.info("#############################################");
		_logger.info("#############################################");
		_logger.info("Output response size is: "+toReturn.size());
		
		return  toReturn;
	}

	public  static IMdbGatewayServiceRemote getClientProxy() {
		//IMdbGatewayServiceRemoute clientProxy = GatewayInvokeBuilder.create(RemouteServiceInvokeType.EjbInvoke);
		//return MdbGatewayClientBuilder.create(RemoteServiceClientType.WebSrvInvoke);
		return MdbGatewayClientBuilder.create(RemoteServiceClientType.EjbInvoke);
	}

	/* (non-Javadoc)
	 * @see mdb.ui.client.rpc.gateway.GatewayAccessService#BatchInvoke(java.util.List)
	 */
	@Override
	public List<Request> batchInvoke(List<Request> inputList) throws Exception {
		
		//IUserInfo userInfo =  SessionValidateChecker.checkSessionValid();
		
		_logger.info(String.format("User is=%s. Batch invoke WebGatewayInvoke.Call input List<Request>.size =%s", "", inputList.size()));
		IMdbGatewayServiceRemote clientProxy = getClientProxy();		

		String out = null;
		for (Request inReq : inputList) {			
			 out =out +"RequestID="+  inReq.getRequestId()+"\n";			
		}
		_logger.info(out);					
		
		
		String jsonOutput = clientProxy.batchJsonCall(_serialiser.listSerialize(inputList));

		List<Request> toReturn =  _serialiser.listDeserialize(jsonOutput);
		
		_logger.info("Success Batch invoke WebGatewayInvoke.Call. Return output List<Request>.size: " + toReturn.size());
		return  toReturn;
	}
	
	
	
	public List<Request> batchInvoke2(List<Request> inputList) throws Exception {
		
		//IUserInfo userInfo =  SessionValidateChecker.checkSessionValid();
		
		_logger.info(String.format("User is=%s. Batch invoke WebGatewayInvoke.Call input List<Request>.size =%s", "", inputList.size()));
		IMdbGatewayServiceRemote clientProxy = getClientProxy();
		
		
		List<Request> toReturn = new ArrayList<Request>();		

		for (Request inReq : inputList) {					
			toReturn.add(clientProxy.call(inReq));
		}			
		
		_logger.info("Success Batch invoke WebGatewayInvoke.Call. Return output List<Request>.size: " + toReturn.size());
		return  toReturn;
	}
}
