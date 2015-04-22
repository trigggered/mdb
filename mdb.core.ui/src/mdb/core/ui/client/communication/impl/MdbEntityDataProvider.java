/**
 * 
 */
package mdb.core.ui.client.communication.impl;

import java.util.HashMap;

import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.ui.client.data.bind.IViewDataBinder;
import mdb.core.ui.client.data.impl.ViewDataConverter;
import mdb.core.ui.client.data.impl.fields.DataSourceFieldsBuilder;
import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.view.dialogs.message.Dialogs;

import com.smartgwt.client.data.Record;

/**
 * @author azhuk
 * Creation date: Jan 28, 2013
 *
 */
public class MdbEntityDataProvider	extends ADataProvider {	
	
//	private static final Logger _logger = Logger.getLogger(MdbEntityDataProvider.class.getName());
	
			
	private ICallbackEvent<IViewDataBinder.State> _callbackEvent;
	private IViewDataBinder.State _viewBindingState;
	
	private HashMap<Integer, Record[]> _mapData = new HashMap<Integer, Record[]>();
	
    
    public void  setAfterInvokeEvent(ICallbackEvent<IViewDataBinder.State> callbackEvent) {
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
   	
		_viewBindingState = IViewDataBinder.State.notBind;
    	_mapData.clear();
    	//Arrays.binarySearch(getResponse().getArrayKeys(), "");
    	 
    	for(IRequestData entity : getResponse().getArrayListValues() ) {

    		switch ( entity.getExecuteType() ) {
    			case GetData: 
    			case ApplyFilter:	
    				_viewBindingState = IViewDataBinder.State.bindAllData;
    				afterGetData(entity);
    				break;    				
    			case ChangeData:
    				_viewBindingState = IViewDataBinder.State.bindChangeData;
    				afterChangedData(entity); 
    				break;
			default:
				break;
    		} 
    		entity.setExecuteType(ExecuteType.None);
    	}    	
    }

	
	protected void afterGetData(IRequestData entity) {
		if (entity.getName().contains(DataSourceFieldsBuilder.FLD_ENTITY_DESC_KEY) ) {
			 DataSourceFieldsBuilder.createFields(this, entity);
		}
		else {			 			    		
			
		if (!entity.getName().contains(DataSourceFieldsBuilder.FLD_ENTITY_DESC_KEY) ) {
			
			    if ( !getResponse().getRequestContainer().containsKey(DataSourceFieldsBuilder.FLD_ENTITY_DESC_KEY+entity.getEntityId() ) ) {
				  DataSourceFieldsBuilder.createDefaultFields(this, entity);
			    }
	
				DataSourceFieldsBuilder.createFields(this, entity);
	    		Record[] record = ViewDataConverter.jSon2RecordArray(entity.getData());
	    		if (record != null )
	    		{			    				    		
		    		_mapData.put(entity.getEntityId(), record);
	    		}
			}   
		}
	}
	
	protected void afterChangedData(IRequestData entity) {
		entity.loseChanges();
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.common.data.IRpcPossibility#getData()
	 */
	@Override
	public HashMap<Integer, Record[]> getDataMap() {		
		return _mapData;
	}
	
}
