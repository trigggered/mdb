/**
 * 
 */
package mdb.core.ui.client.view.dialogs;

import java.util.logging.Logger;

import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.events.IDataEditHandler;
import mdb.core.ui.client.resources.locales.Captions;
import mdb.core.ui.client.view.data.IListDataView;
import mdb.core.ui.client.view.data.grid.GridView;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;

/**
 * @author azhuk
 * Creation date: Feb 13, 2014
 *
 */
public class SelectDialog extends BaseDataDialog {
	private static final Logger _logger = Logger.getLogger(SelectDialog.class
			.getName());

	private ICallbackEvent<Record[]> _callbackEvent;

	private IListDataView _dataListView;
	private boolean _isMultiSelect = false;
	private int  _entityId;	
	
	public SelectDialog(int entityId, boolean isCanMultiSelect, ICallbackEvent<Record[]> callbackEvent) {
	 _entityId = entityId;	
	 _dataListView.setMainEntityId(_entityId);
	 _dataListView.setFilteringEnable(false);
	 _dataListView.setSortingEnable(false);
	 _callbackEvent = callbackEvent;
	 setIsMultiSelect(isCanMultiSelect);
	 _dataListView.addEditEvent(new IDataEditHandler(			 ) {
	 	
		@Override
		public void onEdit(Record record) {			
			if (_callbackEvent != null)  {
				_callbackEvent.doWork(new Record[]{record});
			}			
			hide();
		}
	});	 
	}
	
	@Override
	protected void setWindowsSize() {
		 setWidth("50%");
		 setHeight("90%");
	}
	
	@Override
	public void okBtnClickEvent() {
		if (_callbackEvent != null)  {
			Record[] records = _dataListView.getListGrid().getSelectedRecords();
			_callbackEvent.doWork(records);
			_dataListView.getListGrid().selectRecords(records, false);
		}			
		super.okBtnClickEvent();	
	}
	
	public void callRequestData() {
		_dataListView.callRequestData();
	}
	
	public ICallbackEvent<Record[]> getCallbackEvent() {
		return _callbackEvent;
	}

	public void setCallbackEvent(ICallbackEvent<Record[]> _callbackEvent) {
		this._callbackEvent = _callbackEvent;
	}
	
	protected IListDataView   createListDataView() {
		return new GridView();
	}
	
	@Override
	protected void createContextLayout() {		
		_dataListView = createListDataView();
		_dataListView.setCreateMenuNavigator(false);
		_dataListView.setMainEntityId(_entityId);
		setIsMultiSelect(true);	
		
		addItem(_dataListView.getCanvas());	      
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.dialogs.BaseDataDialog#validate()
	 */
	@Override
	protected boolean validate() {		
		return true;
	}
	

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.dialogs.BaseDataDialog#save()
	 */
	@Override
	protected void save() {		
	}
	
	@Override
	public boolean isHaseChanges() {
		return true;
	}

	public boolean isMultiSelect() {
		return _isMultiSelect;
	}

	public void setIsMultiSelect(boolean value) {
		this._isMultiSelect = value;
		if (_isMultiSelect) {
			_dataListView.getListGrid().setSelectionType(SelectionStyle.SIMPLE);
			_dataListView.getListGrid().setSelectionAppearance(SelectionAppearance.CHECKBOX);
		} else {			
			_dataListView.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		}
	}
	

	public static void view (int entityId, boolean isCanMultiSelect, ICallbackEvent<Record[]>  callbackEvent) {
		
		SelectDialog dlg = new SelectDialog(entityId, isCanMultiSelect, callbackEvent);
		dlg.callRequestData();
		
		dlg.show();		
	}
	
	
	
	@Override
	public  String getCaption() {		
		return  Captions.CHOSE_REC;
	}

	public IListDataView getGrid() {
		return _dataListView;
	}

	
}
