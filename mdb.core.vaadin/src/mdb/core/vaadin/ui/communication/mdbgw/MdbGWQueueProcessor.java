/**
 * 
 */
package mdb.core.vaadin.ui.communication.mdbgw;

import java.util.List;

import mdb.core.shared.exceptions.SessionExpiredException;
import mdb.core.shared.transport.Request;
import mdb.core.ui.client.app.AppController;
import mdb.core.ui.client.communication.IDataProvider;
import mdb.core.ui.client.communication.IQueue;
import mdb.core.ui.client.communication.IQueueProcessing;
import mdb.core.ui.client.communication.impl.AQueueProcessing;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author azhuk
 * Creation date: Feb 7, 2013
 *
 */
public class MdbGWQueueProcessor extends AQueueProcessing {
	

	private static  IQueueProcessing<IDataProvider, Request> _instance;
	
	private MdbGatewayService  _asyncGatewayService = new MdbGatewayServiceImpl();
	
	 
			
	public IQueueProcessing<IDataProvider, Request>  instance(){
		if (_instance == null) {
			_instance =  new MdbGWQueueProcessor();			
		}
		return _instance;
	}

	@Override
	public void run() {
		if (getQueue() == null) {
			return;
		}
		if ( IQueue.EQueueState.Waiting ==  getQueue().getState() ) {
			getQueue().beginProcess();
		List<Request>  listReq = getQueue().toValueList();
		listReq =  assignExecuteUser( listReq);
		printRequests();
		
		
		try {
			List<Request> list =  _asyncGatewayService.batchInvoke( listReq);
			
			getCallBack().onSuccess(list);
		} catch (SessionExpiredException e) {
			getCallBack().onFailure(e.getCause());
			e.printStackTrace();
		} catch (Exception e) {
			getCallBack().onFailure(e.getCause());
			e.printStackTrace();
		}
		}
	}
	
	private List<Request> assignExecuteUser(List<Request> listReq) {
		for (Request req : listReq) {
			req.setUser(AppController.getInstance().getCurrentUser());
		}
		return listReq;
	}

	
	@Override
	public void call(Request value, AsyncCallback<Request> asyncCallback) {
		Request  response = _asyncGatewayService.Invoke(value);
		asyncCallback.onSuccess(response);		
	}
	
	
}
