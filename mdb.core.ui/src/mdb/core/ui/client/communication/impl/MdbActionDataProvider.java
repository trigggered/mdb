/**
 * 
 */
package mdb.core.ui.client.communication.impl;

import java.util.HashMap;
import java.util.logging.Logger;

import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.communication.IDataProvider;
import mdb.core.ui.client.data.bind.IViewDataBinder.State;
import mdb.core.ui.client.data.impl.ViewDataConverter;
import mdb.core.ui.client.data.impl.fields.DataSourceFields;
import mdb.core.ui.client.data.impl.fields.DataSourceFieldsBuilder;
import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.view.data.IDataView;
import mdb.core.ui.client.view.dialogs.input.InputVariablesDialog;
import mdb.core.ui.client.view.dialogs.message.Dialogs;

import com.smartgwt.client.data.Record;

/**
 * @author azhuk
 * Creation date: Jan 30, 2013
 *
 */


public class MdbActionDataProvider extends ADataProvider implements IDataProvider  {		
	

	enum EExecuteActionState {
		None,
		RequestActionParams,
		ExecuteActon
	}
	
	private static final Logger _logger = Logger.getLogger(MdbActionDataProvider.class.getName());
	
	private IDataView _view;
	private EExecuteActionState _state = EExecuteActionState.None;	
	
	private final int ENTITY_FLDS_DESCR = 1275;	

	private Integer _executeActionId = -1;
	private Integer _entityId;
	
	
	private HashMap<Integer, DataSourceFields> _actionReqFields = new HashMap<Integer, DataSourceFields>();

	
	
	protected boolean isExecutePossibility() {
		//		return  getView().isSelectedRecord();
		return true;
	}
	
	public void execute(Integer entityId, Integer actionId) {
		
		_logger.info("Execute action id is:"+actionId +" in Entity :"+entityId);
		_entityId = entityId;
		_executeActionId = actionId;		
		
		if ( isExecutePossibility() ) {
			if (_actionReqFields.containsKey( _executeActionId) ) {				
				
				DataSourceFields fields =  _actionReqFields.get(_executeActionId);
				if (fields!= null) {
					viewActionReqSetVar (_actionReqFields.get(_executeActionId) );
				}
				else {
 					executeAction(null);
				}
			}
			else {				
				requestActionParams();
			}
		} else {
			Dialogs.ShowWarnMessage("Please, for execute action,  selected record");			
		}
	}
	

	private void requestActionParams() {
		_state = EExecuteActionState.RequestActionParams;
		
		IRequestData re =  getRequest().add(new RequestEntity(ENTITY_FLDS_DESCR, DataSourceFieldsBuilder.FLD_ENTITY_DESC_KEY+ENTITY_FLDS_DESCR, false ));		

		re.getParams().add("ID_DENTITY", _entityId.toString() );
		re.getParams().add("ID_EXT_METHOD", _executeActionId.toString() );
		
		GatewayQueue.instance().getProcessor().call(getRequest(), this);
	}
	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
	 */
	@Override
	public void onFailure(Throwable caught) {
		super.onFailure(caught);
		_state = EExecuteActionState.None;		
	}



	protected void analizeResponse() {		
		switch (_state )  {
		case RequestActionParams:
 			  afterRequestActionParams();			  
			break;
		case ExecuteActon:
			afterExecuteAction();
			_state = EExecuteActionState.None;
			break;
		default:
			break;
		}										
	}
	
	protected void afterRequestActionParams() {
		IRequestData rq =	getResponse().get(DataSourceFieldsBuilder.FLD_ENTITY_DESC_KEY+ENTITY_FLDS_DESCR );		
		
		if (rq != null) {	
			
			DataSourceFields  fields = DataSourceFieldsBuilder.createFields(this, rq);			
	
			if (fields.count()>0 ) {
				_actionReqFields.put(_executeActionId, fields);
				_state = EExecuteActionState.None;
				viewActionReqSetVar (fields);
			}
			else {
				_actionReqFields.put(_executeActionId, null);
			executeAction(null);
			}
		}	
		getResponse().clear();
	}
	
	
	protected void afterExecuteAction() {
		
	}
	
	
	private void viewActionReqSetVar (DataSourceFields value ) {		
		
		InputVariablesDialog.viewSetVariableDlg(value, getOwnerView().getSelectedRecord(), 
				new ICallbackEvent<Record>() {						
			@Override
			public void doWork(Record data) {
				if (data != null ) {					
					getOwnerView().getMainDataSource().getDataSource().updateData(data);
					
					
					if ( isCanExecAction () ) 
					{
						executeAction (ViewDataConverter.record2Json(data) );
					}
				}							
			}
		});
	}
	
	protected boolean isCanExecAction () {
		return getOwnerView().isAutoSave();
	}
	
	protected void executeAction (String value) {				
		_state = EExecuteActionState.ExecuteActon;			
		
		IRequestData re =  getRequest().add(new RequestEntity(_entityId ));							
		re.setExecuteType(getExecuteType());
		
		re.setExecActionData(_executeActionId, getOriginValue(), value);
		    	
    	GatewayQueue.instance().getProcessor().call(getRequest(), this);
	}
	
	protected String getOriginValue() {
		String toReturn = null;
		if ( getOwnerView() != null && getOwnerView().getSelectedRecord() != null) {				
			toReturn =   ViewDataConverter.record2Json(getOwnerView().getSelectedRecord());
			_logger.info("Original data: " +toReturn);
		}		
		return toReturn;		
	}
	
	public  ExecuteType getExecuteType() {
		return ExecuteType.ExecAction;
	}
	
	
	public IDataView getOwnerView() {
		return _view;
	}

	public void setView(IDataView _view) {
		this._view = _view;
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.communications.IDataCommunication#getData()
	 */
	@Override
	public HashMap<Integer, Record[]> getDataMap() {
		return null;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.communications.IDataCommunication#setAfterInvokeEvent(mdb.core.ui.events.ICallbackEvent)
	 */
	@Override
	public void setAfterInvokeEvent(ICallbackEvent<State> callbackEvent) {		
	}

}	