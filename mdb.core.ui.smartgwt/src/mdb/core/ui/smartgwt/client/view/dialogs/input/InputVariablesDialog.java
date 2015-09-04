/**
 * 
 */
package mdb.core.ui.smartgwt.client.view.dialogs.input;

import java.util.ArrayList;

import mdb.core.ui.client.data.fields.IDataSourceFields;
import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.view.dialogs.input.IInputDialogs;
import mdb.core.ui.smartgwt.client.data.MdbDataSource;
import mdb.core.ui.smartgwt.client.data.ViewDataConverter;
import mdb.core.ui.smartgwt.client.data.fields.DataSourceFields;
import mdb.core.ui.smartgwt.client.view.dialogs.edit.EditDialog;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

/**
 * @author azhuk Creation date: Oct 2, 2012
 * @param <T>
 * 
 */
public class InputVariablesDialog extends EditDialog implements IInputDialogs{	
	/**
	 * @param callbackEvent
	 */
	public InputVariablesDialog(ICallbackEvent<Record> callbackEvent) {
		super(callbackEvent);
		
	}


	/* (non-Javadoc)
	 * @see mdb.ui.client.view.dialogs.BaseDialogs#okBtnClickEvent()
	 */
	@Override
	public void okBtnClickEvent() {
		
		if (getDataForm().validate()) {			
			getDataForm().saveData();			
			Record rec = getDataForm().getValuesAsRecord();			
			getCallbackEvent().doWork(rec);
			
			 hideWnd();
		}		
	}	
	
	
	@Override
	public String getCaption() {		
		return "Set variables value";
	}
	
	public static void viewSetVariableDlg(String[]  arrFields,  ICallbackEvent<Record> callbackEvent) {
	
		final MdbDataSource ds = new MdbDataSource();		
		
		final ArrayList<DataSourceField> fields = new ArrayList<DataSourceField>();
		final Record[] dataList = new Record[1];
		final Record rec = new Record();
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

	
	public static void viewSetVariableDlg(DataSourceFields fields,  ICallbackEvent<Record> callbackEvent) {
		InputVariablesDialog variableDlg = new InputVariablesDialog( callbackEvent);
		
		final MdbDataSource ds = new MdbDataSource();
		
		ds.setDataComponent(variableDlg);
		ds.setDataSourceFields(fields);		
		ds.createEmptyRecord();				

		variableDlg.setDataSource(ds);		
		tryShow(variableDlg);
	}	

	
	public static void viewSetVariableDlg(DataSourceFields fields,  Record  record, ICallbackEvent<Record> callbackEvent) {
	
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
	
	
	public static void viewSetVariableDlg(DataSourceFields fields,  String  record, ICallbackEvent<Record> callbackEvent) {
		
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
	
	
	public static void viewSetVariableDlg(ArrayList<DataSourceField> fields,  ICallbackEvent<Record> callbackEvent) {
					
		
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
	
	 
	private static void tryShow(InputVariablesDialog variableDlg) {

		if ( isVisibleFiels(variableDlg.getDataForm().getFields()) ) {
			variableDlg.view();
			
		} else {
			
			variableDlg._callbackEvent.doWork(variableDlg.getDataForm().getValuesAsRecord());
		}
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.dialogs.input.IInputDialogs#show(mdb.core.ui.client.data.fields.IDataSourceFields, java.lang.String, mdb.core.ui.client.events.ICallbackEvent)
	 */
	@Override
	public void show(IDataSourceFields value, String selectedRecordJSON,
			ICallbackEvent<String> iCallbackEvent) {

		
	}
	
}



