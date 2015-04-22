/**
 * 
 */
package mdb.core.ui.client.view.dialogs;

import java.util.logging.Logger;

import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.events.IDataEditHandler;
import mdb.core.ui.client.resources.locales.Captions;
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
	private GridView _grid;		
	private boolean _isMultiSelect = false;
	private int  _entityId;	
	
	public SelectDialog(int entityId, boolean isCanMultiSelect, ICallbackEvent<Record[]> callbackEvent) {
	 _entityId = entityId;	
	 _grid.setMainEntityId(_entityId);
	 _grid.setFilteringEnable(false);
	 _grid.setSortingEnable(false);
	 _callbackEvent = callbackEvent;
	 setIsMultiSelect(isCanMultiSelect);
	 _grid.addEditEvent(new IDataEditHandler(			 ) {
	 	
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
	protected void setDefaultSize() {
		setWidth(500);  
	    setHeight(600);
	}
	
	@Override
	public void okBtnClickEvent() {
		if (_callbackEvent != null)  {
			Record[] records = _grid.getListGrid().getSelectedRecords();
			_callbackEvent.doWork(records);
			_grid.getListGrid().selectRecords(records, false);
		}			
		super.okBtnClickEvent();	
	}
	
	public void callRequestData() {
		_grid.callRequestData();
	}
	
	public ICallbackEvent<Record[]> getCallbackEvent() {
		return _callbackEvent;
	}

	public void setCallbackEvent(ICallbackEvent<Record[]> _callbackEvent) {
		this._callbackEvent = _callbackEvent;
	}
	
	@Override
	protected void createContextLayout() {		
		_grid = new GridView();
		_grid.setCreateMenuNavigator(false);
		_grid.setMainEntityId(_entityId);
		setIsMultiSelect(true);	
		
		addItem(_grid);	      
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
			_grid.getListGrid().setSelectionType(SelectionStyle.SIMPLE);
			_grid.getListGrid().setSelectionAppearance(SelectionAppearance.CHECKBOX);
		} else {			
			_grid.getListGrid().setSelectionType(SelectionStyle.SINGLE);
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
		return _grid;
	}

	
}
