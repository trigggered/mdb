/**
 * 
 */
package mdb.core.ui.client.communication.impl;

import java.util.HashMap;

import mdb.core.shared.data.EMdbEntityActionType;
import mdb.core.shared.data.Params;
import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.communication.IRemoteDataRequest;
import mdb.core.ui.client.data.bind.IViewDataBinder.State;
import mdb.core.ui.client.data.impl.ViewDataConverter;
import mdb.core.ui.client.data.impl.fields.DataSourceFields;
import mdb.core.ui.client.events.ICallbackEvent;

import com.smartgwt.client.data.Record;

/**
 * @author azhuk
 * Creation date: Jun 11, 2014
 *
 */
public  class SimpleMdbDataRequester extends ADataProvider implements  IRemoteDataRequest{
	
	
	private int _entityId ; 
	
	protected Params _params = new Params();				
	private ICallbackEvent<Record[]> _callbackEvent;
	
	public SimpleMdbDataRequester() {
		
	}
	
	public SimpleMdbDataRequester(ICallbackEvent<Record[]> callbackEvent) {
		_callbackEvent = callbackEvent;
	}

	public void request(int entityId, Params params) {
		_entityId = entityId;
		_params = params;
		callRequestData();
		
	}
	
	public static void call (EMdbEntityActionType actionType, int entityId, Params params, ICallbackEvent<Record[]> callbackEvent ) {
		SimpleMdbDataRequester requester = new SimpleMdbDataRequester();
		requester.setEntityId(entityId);
		requester._callbackEvent =callbackEvent;
		
		 IRequestData  re = requester.getRequest().add(new RequestEntity(entityId));
		 re.setRequestFieldsDescription(false);
		 String data = ViewDataConverter.map2Json(params.toMap());
		 re.addtDataForChange(actionType, data);
		 re.setExecuteType(ExecuteType.ChangeData);

	
		 GatewayQueue.instance().getProcessor().call(requester.getRequest(),requester);
		 
	}
	
	public static void callAction (int entityId, int actionId, Params params, ICallbackEvent<Record[]> callbackEvent ) {
		SimpleMdbDataRequester requester = new SimpleMdbDataRequester();
		requester.setEntityId(entityId);
		requester._callbackEvent =callbackEvent;
		
		 IRequestData  re = requester.getRequest().add(new RequestEntity(entityId));
		 re.setRequestFieldsDescription(false);
		 String data = ViewDataConverter.map2Json(params.toMap());
		 
		 
		 re.setExecActionData(actionId, data, null);
		 re.setExecuteType(ExecuteType.ExecAction);

	
		 GatewayQueue.instance().getProcessor().call(requester.getRequest(),requester);
		 
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataRequest#prepareRequestData()
	 */
	@Override
	public void prepareRequestData() {
		 IRequestData  re = getRequest().add(new RequestEntity(_entityId));
		 re.setRequestFieldsDescription(false);		 		 
		 re.setParams(_params);
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataRequest#putRequestToQueue()
	 */
	@Override
	public void putRequestToQueue() {
		GatewayQueue.instance().put(this);				
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataRequest#callRequestData()
	 */
	@Override
	public void callRequestData() {
		prepareRequestData();
		putRequestToQueue();
		GatewayQueue.instance().getProcessor().run(); 	
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IDataProvider#getDataMap()
	 */
	@Override
	public HashMap<Integer, Record[]> getDataMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IDataProvider#getDataSourceFieldsMap()
	 */
	@Override
	public HashMap<Integer, DataSourceFields> getDataSourceFieldsMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IDataProvider#setAfterInvokeEvent(mdb.core.ui.client.events.ICallbackEvent)
	 */
	@Override
	public void setAfterInvokeEvent(ICallbackEvent<State> callbackEvent) {
		// TODO Auto-generated method stub		
	}


	/**
	 * @return the _entityId
	 */
	public int getEntityId() {
		return _entityId;
	}

	/**
	 * @param _entityId the _entityId to set
	 */
	public void setEntityId(int _entityId) {
		this._entityId = _entityId;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.impl.ADataProvider#analizeResponse()
	 */
	@Override
	protected void analizeResponse() {
		IRequestData rq =	getResponse().get(String.valueOf(_entityId));
		if (rq != null) {
			((RequestEntity) rq).setExecuteType(ExecuteType.GetData);
			if (_callbackEvent!= null) {
				_callbackEvent.doWork( ViewDataConverter.jSon2RecordArray(rq.getData() ) );
			}
		}		
	}	
}
