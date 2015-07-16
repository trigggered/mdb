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
import mdb.core.ui.client.data.bind.IDataBinder.State;
import mdb.core.ui.client.data.fields.IDataSourceFields;
import mdb.core.ui.client.data.fields.IDataSourceFieldsBuilder;
import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.view.data.IDataView;
import mdb.core.ui.client.view.dialogs.input.IInputDialogs;
import mdb.core.ui.client.view.dialogs.message.Dialogs;

/**
 * @author azhuk
 * Creation date: Jan 30, 2013
 *
 */


public abstract class AMdbActionDataProvider extends ADataProvider implements IDataProvider  {		
	

	enum EExecuteActionState {
		None,
		RequestActionParams,
		ExecuteActon
	}
	
	private static final Logger _logger = Logger.getLogger(AMdbActionDataProvider.class.getName());
	
	private IDataView _view;
	private EExecuteActionState _state = EExecuteActionState.None;	
	
	private final int ENTITY_FLDS_DESCR = 1275;	
	private Integer _executeActionId = -1;
	private Integer _entityId;	
	private HashMap<Integer, IDataSourceFields> _actionReqFields = new HashMap<Integer, IDataSourceFields>();
	private IDataSourceFieldsBuilder _fieldsBuilder;	
	
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
				
				IDataSourceFields fields =  _actionReqFields.get(_executeActionId);
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
		
		IRequestData re =  getRequest().add(new RequestEntity(ENTITY_FLDS_DESCR, IDataSourceFieldsBuilder.FLD_ENTITY_DESC_KEY+ENTITY_FLDS_DESCR, false ));		

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
	
	public IDataSourceFieldsBuilder getDataSourceFieldsBuilder() {
		return _fieldsBuilder; 
	}
	
	public void setDataSourceFieldsBuilder(IDataSourceFieldsBuilder value) {
		_fieldsBuilder = value;
	}
	
	protected void afterRequestActionParams() {
		IRequestData rq =	getResponse().get(IDataSourceFieldsBuilder.FLD_ENTITY_DESC_KEY+ENTITY_FLDS_DESCR );		
		
		if (rq != null) {	
			
			getDataSourceFieldsBuilder().createFields(this, rq);			
	
			String entityId =  rq.getName().substring(IDataSourceFieldsBuilder.FLD_ENTITY_DESC_KEY.length());			
			IDataSourceFields fields = this.getDataSourceFieldsMap().get(Integer.parseInt(entityId));
			
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
	
	protected abstract IInputDialogs getInputDialogs();
	
	private void viewActionReqSetVar (IDataSourceFields fields ) {				
		
		getInputDialogs().show(fields, getOwnerView() != null?getOwnerView().getSelectedRecordJSON():null, 
				new ICallbackEvent<String>() {						
			@Override
			public void doWork(String data) {
				if (data != null ) {
					
					if (getOwnerView()!= null) {
						getOwnerView().getMainDataSource().updateData(data);
					}
					
					
					if ( isCanExecAction () ) 
					{
						executeAction (data) ;
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
		if ( getOwnerView() != null ) {				
			toReturn = getOwnerView().getSelectedRecordJSON();   
					
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
	 * @see mdb.core.ui.communications.IDataCommunication#setAfterInvokeEvent(mdb.core.ui.events.ICallbackEvent)
	 */
	@Override
	public void setAfterInvokeEvent(ICallbackEvent<State> callbackEvent) {		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IDataProvider#getDataMap()
	 */
	@Override
	public HashMap<Integer, String> getDataMap() {	
		return null;
	}
}	