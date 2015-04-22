/**
 * 
 */
package mdb.core.ui.client.communication.impl;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import mdb.core.shared.exceptions.SessionExpiredException;
import mdb.core.shared.transport.Request;
import mdb.core.shared.utils.comparator.ComparatorByPosition;
import mdb.core.ui.client.communication.IDataProvider;
import mdb.core.ui.client.communication.IQueue;
import mdb.core.ui.client.communication.IQueueProcessing;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author azhuk
 * Creation date: Feb 7, 2013
 *
 */
public abstract class AQueueProcessing implements IQueueProcessing<IDataProvider,Request>  {
	
	
	class BatchInvokeCallback implements  AsyncCallback<List<Request>> {
		
		@Override
		public void onSuccess(List<Request> result) {
			_logger.info("Success response !");
			_logger.info("Sent request:");
			
			Collections.sort(result, new ComparatorByPosition());		
			
			printRequests();
			
			_logger.info("Receive response:");
			printResponses(result);
			
			for ( Request response :   result ) {														
				IDataProvider dataProvider =  getQueue().getContainer().get( Integer.valueOf(response.getRequestId()));
						if (dataProvider!= null) {
							dataProvider.onSuccess(response);
							getQueue().getContainer().remove(Integer.valueOf(response.getRequestId()));
							_logger.info("Remove DataProvider from queue with RequestID = "+response.getRequestId());
						} else {
							_logger.severe("Not found DataProvider with RequestID = "+response.getRequestId());							
						}
						
			}
			getQueue().endProcess();	

		}
		
		@Override
		public void onFailure(Throwable caught) {
			_logger.severe("Failure RPC  response: \n" +caught.getMessage());
			getQueue().endProcess();
			if (caught instanceof SessionExpiredException) {
				String url = GWT.getHostPageBaseURL()+ "logout";
				//Windows.Location.
				Location.replace(url);
			}			
		}
	}	
	
	
	private final BatchInvokeCallback _batchInvokeCallback = new BatchInvokeCallback();	
	
	private static final Logger _logger = Logger.getLogger(AQueueProcessing.class.getName());	

	private IQueue<IDataProvider, Request> _queue;	 
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.communications.IQueueRunner#run()
	 */
	@Override
	public abstract  void run();

	
	/* (non-Javadoc)
	 * @see mdb.core.ui.communications.IQueueRunner#setQueue(mdb.core.ui.communications.IQueue)
	 */
	@Override
	public void setQueue(IQueue<IDataProvider, Request> value) {
		_queue = value;				
	}

	public IQueue<IDataProvider, Request> getQueue() {
		return _queue;
	}
	
	public AsyncCallback<List<Request>> getCallBack() {
		return _batchInvokeCallback;
	}
	
	protected void printRequests() {
		getQueue().printQueue();
	}
	
	protected void printResponses(List<Request> responseList ) {
		_logger.info("Response: \n" +
				getQueue().getQueueData (responseList));
	}
	
}
