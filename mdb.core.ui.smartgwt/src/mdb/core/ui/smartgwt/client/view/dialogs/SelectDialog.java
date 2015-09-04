/**
 * 
 */
package mdb.core.ui.smartgwt.client.view.dialogs;

import java.util.logging.Logger;

import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.events.IDataEditHandler;
import mdb.core.ui.client.resources.locales.Captions;
import mdb.core.ui.client.view.components.menu.IMenuFactory;
import mdb.core.ui.smartgwt.client.view.data.grid.GridView;

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

	private GridView _dataListView;
	
	private boolean _isMultiSelect = false;
	private int  _entityId;	
	
	
	protected void createMainLayout() {
		super.createMainLayout();
		
		_dataListView = new GridView();
	}
	
	public SelectDialog(int entityId, boolean isCanMultiSelect, ICallbackEvent<Record[]> callbackEvent) {
	 _entityId = entityId;	
	 _dataListView.setMainEntityId(_entityId);
	 _dataListView.setFilteringEnable(false);
	 _dataListView.setSortingEnable(false);
	 _callbackEvent = callbackEvent;
	 setIsMultiSelect(isCanMultiSelect);
	 
	 _dataListView.addEditEvent(new IDataEditHandler<Record>() {

		@Override
		public void onEdit(Record record) {
			if (_callbackEvent != null)  {
				_callbackEvent.doWork(new Record[]{record});
			}			
			hideWnd();
			
		}
	});
	 
	 _dataListView.addEditEvent(new IDataEditHandler<Record>(			 ) {
	 	
		@Override
		public void onEdit(Record record) {			
			if (_callbackEvent != null)  {
				_callbackEvent.doWork(new Record[]{record});
			}			
			hideWnd();
		}
	});	 
	}
	
	@Override
	protected void setWindowsSize() {
		getViewPanel().setWidth("50%");
		getViewPanel().setHeight("90%");
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
	
	protected GridView   createListDataView() {
		return new GridView();
	}
	
	@Override
	protected void createContextLayout() {		
		_dataListView = createListDataView();
		_dataListView.setCreateMenuNavigator(false);
		_dataListView.setMainEntityId(_entityId);
		setIsMultiSelect(true);	
		
		getViewPanel().addItem(_dataListView.getMainLayout());	      
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

	public GridView getGrid() {
		return _dataListView;
	}

	@Override
	protected  void initSize() {		    
	      super.initSize();
	      getViewPanel().setTitle(getCaption());  
	      getViewPanel().setShowMinimizeButton(false);	      
	      getViewPanel().setIsModal(true);  
	      getViewPanel().setShowModalMask(true);  
	      getViewPanel().setCanDragResize(true);
	      getViewPanel().centerInPage();
	}


}
