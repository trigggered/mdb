/**
 * 
 */
package mdb.core.ui.client.communication.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;

import mdb.core.shared.transport.Request;
import mdb.core.ui.client.communication.IDataProvider;
import mdb.core.ui.client.communication.IQueue;
import mdb.core.ui.client.communication.IQueueProcessing;
import mdb.core.ui.client.view.dialogs.waiting.WaitPopup;

/**
 * @author azhuk
 * Creation date: Feb 1, 2013
 *
 */
public class GatewayQueue  implements IQueue<IDataProvider, Request> {


	private static final Logger _logger = Logger.getLogger(GatewayQueue.class.getName());
	
	private EQueueState  _queueState = EQueueState.Waiting;
	//private boolean _useDelyedQueue = true;
	private HashMap<Integer,IDataProvider> _queue = new HashMap<Integer,IDataProvider>();
	private HashMap<Integer,IDataProvider> _delayedQueue = new HashMap<Integer,IDataProvider>();	
	
	private Iterator<Entry<Integer, IDataProvider>> _iteratorQueue;
	private IQueueProcessing<IDataProvider, Request> _processor;
	
	private static  IQueue<IDataProvider, Request>  _instance;
	
	private WaitPopup _waitPopup = new WaitPopup(); 
	public static IQueue<IDataProvider, Request>  instance() {
		if (_instance == null) {
			_instance = new GatewayQueue();
		}		
		return _instance;
	}
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.rpc.gateway.IQueue#put(java.lang.Object)
	 */
	@Override
	public void put(IDataProvider value) {		
		
		if (_queueState == EQueueState.Waiting) {				
			_logger.info("Put RequestId = "+value.getRequest().getRequestId()+" item to Queue ");
			_queue.put(Integer.valueOf(value.getRequest().getRequestId()), value);		
			_iteratorQueue = _queue.entrySet().iterator();
		}
		else 
		{
			
			_logger.info("Put RequestId"+value.getRequest().getRequestId()+" item to DelayedQueue ");
			_delayedQueue.put(Integer.valueOf(value.getRequest().getRequestId()), value);
		}
	}	
	
	@Override
	public Entry<Integer, IDataProvider>  next() {
		
		Iterator<Entry<Integer, IDataProvider>> iterator = getIterator();
		
		if (iterator.hasNext()) {
			return iterator.next();	
		}
		else {
			return null;
		}				
	}
	
	/**
	 * 
	 */
	private Iterator<Entry<Integer, IDataProvider>> getIterator() {		
		return _iteratorQueue;
	}
	
	@Override
	public  List<Request> toValueList() {				
		return queueToValueList(_queue);		
	}
		
	
	public   List<Request> queueToValueList(HashMap<Integer,IDataProvider> value) {
		
		ArrayList<Request> toReturn = new ArrayList<Request>();
		for (IDataProvider provider : value.values()) {
			toReturn.add(provider.getRequest());
		}
		return toReturn;
	}
		
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.rpc.gateway.IQueue#clear()
	 */
	@Override
	public void clear() {
		_queue.clear();
		_iteratorQueue = null;
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.rpc.gateway.IQueue#getQueueContainer()
	 */
	@Override
	public HashMap<Integer, IDataProvider> getContainer() {
		return _queue;
	}

	
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.communications.IQueue#setExecutor(mdb.core.ui.communications.IQueueRunner)
	 */
	@Override
	public void setProcessor(IQueueProcessing<IDataProvider, Request> value) {
		_processor = value;
		_processor.setQueue(this);
		
	}
	/* (non-Javadoc)
	 * @see mdb.core.ui.communications.IQueue#getExecutor()
	 */
	@Override
	public IQueueProcessing<IDataProvider, Request> getProcessor() {
		return _processor;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.communications.IQueue#beginProcess()
	 */
	@Override
	public void beginProcess() {
		_queueState = EQueueState.Processing;
		_logger.info("Change Queue state to Processing");
		_waitPopup.show("");
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.communications.IQueue#endProcess()
	 */
	@Override
	public void endProcess() {		
		//clear();		
		_queueState = EQueueState.Waiting;
		_logger.info("Change Q state to Waiting");
		runDelayed();
		_waitPopup.hide();
	}	

	
	private void runDelayed() {
		if ( _delayedQueue.size() >0 ) {
			_logger.info("Begin processed _delayed Queue size"+ _delayedQueue.size());
			_queue.putAll(_delayedQueue);
			_iteratorQueue = _queue.entrySet().iterator();
			_delayedQueue.clear();
			_processor.run();
		}
	}
	
	public  void printQueue() {
		String out = "Queue in state: "+_queueState.toString()+"\n";		
		out = out+"Info for Main Queue \n"+ getQueueData(queueToValueList(_queue))+"\n";		
		out = out +"Info fo Delayed Queue \n" + getQueueData(queueToValueList(_delayedQueue));
		_logger.info(out);
		
	}
	
	
	
	public    String   getQueueData (List<Request> values) {
		String out = "";
	 if ( values != null && values.size() >0) {
		 
		for (Request req : values) {
				/*
				ArrayList<IRequestData> ard =req.getArrayListValues();				
				int[] aeid = new int[ard.size()];
				for (int i=0; i<ard.size(); i++ ) {
					aeid[i]= ard.get(i).getEntityId();									
				}
				*/
			
				//out=out+"RequestId= "+req.getRequestId()+" contains  entitys such:" + Arrays.toString(aeid) + "\n";
				out=out+"RequestId= "+req.getRequestId()+" Position="+req.getPosition()+ " contains  entitys such:" + Arrays.toString(req.getArrayKeys()) + "\n";
				
		}
		
		out = "Queue  size is: "+values.size()+"\n"+ out;		
	 }
	 else {
		 out = ("Queue is Empty");
	 }
	 return out;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.communications.IQueue#getState()
	 */
	@Override
	public mdb.core.ui.client.communication.IQueue.EQueueState getState() {
		return _queueState;		
	}

	
}
