/**
 * 
 */
package mdb.core.ui.client.data.checkers;

import java.util.logging.Logger;

import mdb.core.shared.data.Params;
import mdb.core.ui.client.communication.impl.SimpleMdbDataRequester;
import mdb.core.ui.client.events.ICallbackEvent;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.BooleanCallback;

/**
 * @author azhuk
 * Creation date: Oct 16, 2014
 *
 */
public class SimpleChecker implements IChecker {
	@SuppressWarnings("unused")
	private static final Logger _logger = Logger.getLogger(SimpleChecker.class
			.getName());
	
	SimpleMdbDataRequester _dataRequester; 
	ICallbackEvent<Record[]> _callBack;
	Params _params = new Params();
	
	/**
	 * @return the _params
	 */
	public Params getParams() {
		return _params;
	}

	public SimpleChecker(){
		_dataRequester = new SimpleMdbDataRequester(new ICallbackEvent<Record[]>() {
			
			@Override
			public void doWork(Record[] data) {
				_callBack.doWork(data);				
			}
		});		
	}
	
	protected  void check(int entityId,   ICallbackEvent<Record[]> callBack) {
		_callBack = callBack;		
		_dataRequester.request(entityId, getParams());
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.checkers.IChecker#check(com.smartgwt.client.util.BooleanCallback)
	 */
	@Override
	public void check(BooleanCallback callback) {
		// TODO Auto-generated method stub
		
	}
	
}
