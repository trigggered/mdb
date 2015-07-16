/**
 * 
 */
package mdb.core.vaadin.ui.view.dialogs.input;

import java.util.ArrayList;

import mdb.core.ui.client.data.fields.IDataSourceFields;
import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.view.dialogs.input.IInputDialogs;
import mdb.core.vaadin.ui.data.MdbDataSource;
import mdb.core.vaadin.ui.data.bind.MdbViewDataBinder;
import mdb.core.vaadin.ui.data.fields.DataSourceFields;
import mdb.core.vaadin.ui.view.dialogs.FormDialog;

import com.vaadin.data.Item;
import com.vaadin.ui.FormLayout;

/**
 * @author azhuk Creation date: Oct 2, 2012
 * @param <T>
 * 
 */
public class InputVariablesDialog extends FormDialog implements IInputDialogs{	
	/**
	 * @param callbackEvent
	 */
	public InputVariablesDialog(ICallbackEvent<Item> callbackEvent) {
		super(callbackEvent);
		
	}


	/* (non-Javadoc)
	 * @see mdb.ui.client.view.dialogs.BaseDialogs#okBtnClickEvent()
	 */
	@Override
	public void okBtnClickEvent() {
		super.okBtnClickEvent();
		/*if (getDataForm().validate()) {			
			getDataForm().saveData();			
			Record rec = getDataForm().getValuesAsRecord();			
			getCallbackEvent().doWork(rec);
			
			 hideWnd();
		}*/		
	}	
	
	
	@Override
	public String getCaption() {		
		return "Set variables value";
	}
	
/*	public static void viewSetVariableDlg(String[]  arrFields,  ICallbackEvent<Item> callbackEvent) {
	
		
		final MdbDataSource ds = new MdbDataSource();		
		
		final ArrayList<DataSourceField> fields = new ArrayList<DataSourceField>();
		final Record[] dataList = new Record[1];
		final Item rec = new Item();
		dataList[0] = rec;
		
		for (int i = 0; i <arrFields.length; i++)	 {					 
			fields.add(new DataSourceField(arrFields[i], FieldType.TEXT));
		 	rec.setAttribute(arrFields[i], "");
		 }		 								 			
		
		InputVariablesDialog variableDlg = new InputVariablesDialog( callbackEvent);
			ds.setDataComponent(variableDlg);
			ds.setFields(fields);
			ds.setData(dataList);					
			
			variableDlg.setDataSource(ds);
			tryShow(variableDlg);		
	}
*/
	
	/*public static void viewSetVariableDlg(DataSourceFields fields,  ICallbackEvent<Record> callbackEvent) {
		InputVariablesDialog variableDlg = new InputVariablesDialog( callbackEvent);
		
		final MdbDataSource ds = new MdbDataSource();
		
		ds.setDataComponent(variableDlg);
		ds.setDataSourceFields(fields);		
		ds.createEmptyRecord();				

		variableDlg.setDataSource(ds);		
		tryShow(variableDlg);
	}	
*/
	
	/*public static void viewSetVariableDlg(DataSourceFields fields,  Record  record, ICallbackEvent<Record> callbackEvent) {
	
		InputVariablesDialog variableDlg = new InputVariablesDialog( callbackEvent);				
		
		MdbDataSource ds = new MdbDataSource();
		
		ds.setDataComponent(variableDlg);
		ds.setDataSourceFields(fields);				
		if (record != null) {			
			ds.setData(new Record[]{record});
		}
		else {
			ds.createEmptyRecord();
		}
		
		variableDlg.setDataSource(ds);		
		tryShow(variableDlg);
	}		
*/	
	
	/*public static void viewSetVariableDlg(DataSourceFields fields,  String  record, ICallbackEvent<Record> callbackEvent) {
		
		InputVariablesDialog variableDlg = new InputVariablesDialog( callbackEvent);				
		
		MdbDataSource ds = new MdbDataSource();
		
		ds.setDataComponent(variableDlg);
		ds.setDataSourceFields(fields);				
		
		
		if (record != null) {			
			ds.setData(ViewDataConverter.jSon2RecordArray(record));
		}
		else {
			ds.createEmptyRecord();
		}
		
		variableDlg.setDataSource(ds);		
		tryShow(variableDlg);
	}		
	*/
	
/*	public static void viewSetVariableDlg(ArrayList<DataSourceField> fields,  ICallbackEvent<Record> callbackEvent) {
					
		
		final Record[] dataList = new Record[1];
		final Record rec = new Record();
		dataList[0] = rec;
		
		for (DataSourceField fld : fields) {
			rec.setAttribute(fld.getName() , "");
		}
		
		InputVariablesDialog variableDlg = new InputVariablesDialog( callbackEvent);
		final MdbDataSource ds = new MdbDataSource();
		ds.setDataComponent(variableDlg);
		
			ds.setFields(fields);
			ds.setData(dataList);				

			
			variableDlg.setDataSource(ds);
			tryShow(variableDlg);	
	}
	
*/	 
/*	private static void tryShow(InputVariablesDialog variableDlg) {

		if ( isVisibleFiels(variableDlg.getDataForm().getFields()) ) {
			variableDlg.view();
			
		} else {
			
			variableDlg._callbackEvent.doWork(variableDlg.getDataForm().getValuesAsRecord());
		}
	}
*/

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.dialogs.input.IInputDialogs#show(mdb.core.ui.client.data.fields.IDataSourceFields, java.lang.String, mdb.core.ui.client.events.ICallbackEvent)
	 */
	@Override
	public void show(IDataSourceFields value, String selectedRecordJSON,
			final ICallbackEvent<String> iCallbackEvent) {

		Item rec = null;
		MdbDataSource ds = new MdbDataSource();
		if(selectedRecordJSON!= null && selectedRecordJSON.length() > 0) {
			ds.setData(selectedRecordJSON);
		}
		else {
		
			ds.setFields(value);
			rec = ds.createEmptyRecord();
		}
		InputVariablesDialog dlg = new InputVariablesDialog(new ICallbackEvent<Item>() {
			
			@Override
			public void doWork(Item data) {
				
				iCallbackEvent.doWork(MdbDataSource.serialize(data));
				
			}
		});
		dlg._dataForm.bindData(ds, rec);
		dlg.show();			
		
	}
	
}



