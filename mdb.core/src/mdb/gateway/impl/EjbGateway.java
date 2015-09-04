package mdb.gateway.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebService;

import mdb.core.shared.gw.mdb.IMdbGatewayServiceRemote;
import mdb.core.shared.logger.MdbLogger;
import mdb.core.shared.transformation.mdbrequest.IRequestSerialiser;
import mdb.core.shared.transformation.mdbrequest.JSONRequestSerialiser;
import mdb.core.shared.transport.Request;
import mdb.core.shared.utils.comparator.ComparatorByPosition;
import mdb.core.shared.utils.zip.GZipHelper;
import mdb.gateway.IRequestAnalyzer;


@Stateless
@LocalBean
@WebService
public class EjbGateway  implements IMdbGatewayServiceRemote{

	private static final Logger _logger = Logger.getLogger(EjbGateway.class.getCanonicalName());	
	
	@EJB
	private IRequestAnalyzer _requestAnalyzer;		
	
	private IRequestSerialiser _serialiser = new JSONRequestSerialiser(); 
	
	/* (non-Javadoc)
	 * @see mdb.core.shared.gw.IGatewayService#main(mdb.core.shared.transport.Request)
	 */
	@Override
	@WebMethod
	@Interceptors (MdbLogger.class)	
	public Request call(Request value) {		
		return _requestAnalyzer.make(value);	
	}
	
	@Override
	@WebMethod	
	public String batchJsonCall(String input) {
		
		List<Request>  listRequest =  _serialiser.listDeserialize(input);
		List<Request>  listResponse = new  ArrayList<Request>();
		
		String out = "\n";
		for (Request inReq : listRequest) {			
			 out =out +"RequestID="+  inReq.getRequestId()+"\n";			
		}
		_logger.info(out);
		
		Collections.sort(listRequest, new ComparatorByPosition());
		
		for (Request req  : listRequest) {
			_logger.info("Start analyse RequestId = "+ req.getRequestId());
			
			Request  response = _requestAnalyzer.make(req);					
			
			_logger.info("After analyse RequestId = "+ response.getRequestId());
			
			listResponse.add(response);	
		}		
		String toReturn = _serialiser.listSerialize(listResponse);
		return toReturn;
		//return _serialiser.listSerialize(listResponse);
	}
	

	/* (non-Javadoc)
	 * @see mdb.gateway.IGatewayService#main(byte[])
	 */
	@Override
	@WebMethod
	@Interceptors (MdbLogger.class)	
	public byte[] binaryCall(byte[] request) {		
		//_logger.log(Level.INFO, "Start call");		
		Request  response  = _requestAnalyzer.make(request);	
		
		
		//_logger.log(Level.INFO, strResponse);
		String strResponse = _requestAnalyzer.getSerialiser().serialize(response);
		
		byte[] bResponse = null;
		try {
			bResponse = GZipHelper.compress(strResponse);
		} catch (IOException e) {
			_logger.log(Level.FINE, "IOException in call ZipHelper.compress");
		} 		
		//_logger.log(Level.INFO, "End   call");		
		return bResponse; 		
	}	

}
