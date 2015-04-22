/**
 * 
 */
package mdb.ui.client.communication.rpc.mdbgw;

import java.util.List;

import mdb.core.shared.transport.Request;
import mdb.core.ui.client.communication.IDataProvider;
import mdb.core.ui.client.communication.IQueue;
import mdb.core.ui.client.communication.IQueueProcessing;
import mdb.core.ui.client.communication.impl.AQueueProcessing;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author azhuk
 * Creation date: Feb 7, 2013
 *
 */
public class MdbGWQueueProcessor extends AQueueProcessing {
	

	private static  IQueueProcessing<IDataProvider, Request> _instance;
	
	private static final MdbGatewayServiceAsync _asyncGatewayService = GWT.create(MdbGatewayService.class);
	
	 
			
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
			printRequests();
			_asyncGatewayService.batchInvoke( listReq, getCallBack());		
		}
	}


	@Override
	public void call(Request value, AsyncCallback<Request> asyncCallback) {
		_asyncGatewayService.Invoke( value, asyncCallback);		
	}
	
	
}
