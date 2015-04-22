/**
 * 
 */
package mdb.core.ui.client.view.dialogs.edit;

import java.util.Map;

import mdb.core.ui.client.data.IDataComponent;
import mdb.core.ui.client.data.IDataSource;
import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.view.dialogs.BaseDataDialog;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.ListGrid;


/**
 * @author azhuk
 * Creation date: Jan 9, 2013
 *
 */
public class EditDialog extends BaseDataDialog implements IDataComponent  {
	
	protected ICallbackEvent<Record> _callbackEvent;
	private DynamicForm _dataForm;
	 
	
	public EditDialog  (ICallbackEvent<Record> callbackEvent) {
		_callbackEvent = callbackEvent;
	}
	
	public ICallbackEvent<Record> getCallbackEvent() {
		return _callbackEvent;
	}

	public void setCallbackEvent(ICallbackEvent<Record> callbackEvent) {
		this._callbackEvent = callbackEvent;
	}
	
	
	public DynamicForm getDataForm() {
		return _dataForm;
	}

	
	@Override
	public void setDataSource(IDataSource value, boolean isCanEdit ) {
		if (value != null) {
			super.setDataSource(value);
			value.setDataComponent(this);
			if (isCanEdit) {
				value.bindToDataComponent(getDataForm());
			}else {
				value.bindToDataComponent(getDataForm(), false);
			}
		}
	}
	
	@Override
	public void setDataSource(IDataSource value) {
		setDataSource(value, true );		
	}
	
	@Override
	protected void createContextLayout() {
		_dataForm = new DynamicForm();		
		_dataForm.setHeight("90%");  
		_dataForm.setWidth100();  
		_dataForm.setPadding(5);
		_dataForm.setLayoutAlign(VerticalAlignment.BOTTOM);      
		addItem(_dataForm);	      
	}
	
	@Override
	protected boolean validate() {
		return getDataForm().validate();
	}
	
	
	@Override
	public boolean isHaseChanges() {	
		Map changetValues = getDataForm().getChangedValues();
		return changetValues == null || changetValues.size() >0;	
	}
	
	@Override	
	protected void save() {
		if (isHaseChanges() ) {
			getDataForm().saveData();		
			saveChangesToServer();
		}

	}
	
	public void setEditGrid(ListGrid grid) {			
			getDataForm().editSelectedData(grid);		
	}
	
	@Override
	public void okBtnClickEvent() {		
		if (_callbackEvent != null)  {
			_callbackEvent.doWork(getDataForm().getValuesAsRecord());
		}
		super.okBtnClickEvent();
	}
	
	public static void view (IDataSource dataSource,  ListGrid grid) {		
		view (dataSource, grid, false, false);		
	}
	
	public static void viewForEdit (IDataSource dataSource,  ListGrid grid) {		
		view (dataSource, grid, true, true);		
	}
	
	public static void viewForEdit (IDataSource dataSource,  ListGrid grid,  boolean isCanRemouteSave) {		
		view (dataSource, grid, true,  isCanRemouteSave);		
	}
	
	public static void view (IDataSource dataSource,  ListGrid grid, boolean isCanEdit, boolean isCanRemouteSave) {		
		EditDialog dlg = new EditDialog(null);
		
		dlg.setDataSource(dataSource, isCanEdit);		
		dlg.setEditGrid(grid);
		dlg.setIsRemouteSave(isCanRemouteSave);
		
		dlg.show();		
	}
	
	public static void viewForNewRecord (IDataSource dataSource, Record initRecord,  ICallbackEvent<Record>  callackEvent) {
		EditDialog dlg = new EditDialog(callackEvent);
		dlg.setDataSource(dataSource);
		if (initRecord!=null) {
			dlg.getDataForm().editNewRecord(initRecord);
		}
		else {
			dlg.getDataForm().editNewRecord();			
		}		
		dlg.show();		
	}	
	
	@Override
	public String getCaption() {		
		return "Data edit";
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.dialogs.edit.IDataComponent#setFieldValue(java.lang.String, java.lang.String)
	 */
	@Override
	public void setFieldValue(String fieldName, String value) {
		FormItem item = getDataForm().getField(fieldName);	
		if (item != null) {
			item.setValue(value);
		}		
	}	
	
	protected static boolean isVisibleFiels(FormItem[] items) {
		 for (FormItem item : items) {
			 if  (item.getVisible() ) {
				 return true;
			 }
		 }
		 return false;
	 }
	
	@Override
	public void setCanEdit(boolean value) {
		super.setCanEdit(value);
		for (FormItem item : getDataForm().getFields()) {
			 item.setCanEdit(value);
		}
	}
	
}

