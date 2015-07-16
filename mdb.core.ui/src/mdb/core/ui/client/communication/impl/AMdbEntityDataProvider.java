/**
 * 
 */
package mdb.core.ui.client.communication.impl;

import java.util.HashMap;

import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.ui.client.data.bind.IDataBinder;
import mdb.core.ui.client.data.fields.IDataSourceFieldsBuilder;
import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.view.dialogs.message.Dialogs;

/**
 * @author azhuk
 * Creation date: Jan 28, 2013
 *
 */
public abstract class AMdbEntityDataProvider	extends ADataProvider {	
	
//	private static final Logger _logger = Logger.getLogger(MdbEntityDataProvider.class.getName());
	
			
	private ICallbackEvent<IDataBinder.State> _callbackEvent;
	private IDataBinder.State _viewBindingState;
	
	private HashMap<Integer, String> _mapData = new HashMap<Integer, String>();
	
    
    public void  setAfterInvokeEvent(ICallbackEvent<IDataBinder.State> callbackEvent) {
    	_callbackEvent =  callbackEvent;
    }
    
   
	    
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
	 */
	@Override
	public void onFailure(Throwable caught) {
		super.onFailure(caught);
		Dialogs.ShowWarnMessage("Failure get remote data");		
	}	

	@Override
	protected void afterInvoke() {
		
		analizeResponse();
		
		for (IRequestData entity : getRequest().getArrayListValues() ) {
			entity.setExecuteType(ExecuteType.None);
		}
		
		if (_callbackEvent != null) {
			_callbackEvent.doWork(_viewBindingState);
		}
		
	}		
	
	
	@Override
	protected void analizeResponse() {
   	
		_viewBindingState = IDataBinder.State.notBind;
    	_mapData.clear();
    	//Arrays.binarySearch(getResponse().getArrayKeys(), "");
    	 
    	for(IRequestData entity : getResponse().getArrayListValues() ) {

    		switch ( entity.getExecuteType() ) {
    			case GetData: 
    			case ApplyFilter:	
    				_viewBindingState = IDataBinder.State.bindAllData;
    				afterGetData(entity);
    				break;    				
    			case ChangeData:
    				_viewBindingState = IDataBinder.State.bindChangeData;
    				afterChangedData(entity); 
    				break;
			default:
				break;
    		} 
    		entity.setExecuteType(ExecuteType.None);
    	}    	
    }

	protected abstract IDataSourceFieldsBuilder getDataSourceFieldsBuilder();
	
	protected void afterGetData(IRequestData entity) {
		if (entity.getName().contains(IDataSourceFieldsBuilder.FLD_ENTITY_DESC_KEY) ) {
			getDataSourceFieldsBuilder().createFields(this, entity);
		}
		else {			 			    		
			
		if (!entity.getName().contains(IDataSourceFieldsBuilder.FLD_ENTITY_DESC_KEY) ) {
			
			    if ( !getResponse().getRequestContainer().containsKey(IDataSourceFieldsBuilder.FLD_ENTITY_DESC_KEY+entity.getEntityId() ) ) {
			    	getDataSourceFieldsBuilder().createDefaultFields(this, entity);
			    }
	
			    getDataSourceFieldsBuilder().createFields(this, entity);
				
		    	_mapData.put(entity.getEntityId(), entity.getData());
	    		
			}   
		}
	}
	
	protected void afterChangedData(IRequestData entity) {
		entity.loseChanges();
	}

	@Override
	public HashMap<Integer, String> getDataMap() {
		return _mapData;
	}
	

}
