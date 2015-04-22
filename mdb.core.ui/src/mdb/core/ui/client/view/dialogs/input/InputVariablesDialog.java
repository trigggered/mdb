/**
 * 
 */
package mdb.core.ui.client.view.dialogs.input;

import java.util.ArrayList;

import mdb.core.ui.client.data.impl.MdbDataSource;
import mdb.core.ui.client.data.impl.fields.DataSourceFields;
import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.view.dialogs.edit.EditDialog;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

/**
 * @author azhuk Creation date: Oct 2, 2012
 * @param <T>
 * 
 */
public class InputVariablesDialog extends EditDialog{	
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
			
			hide();
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
			canShow(variableDlg);		
	}

	
	public static void viewSetVariableDlg(DataSourceFields fields,  ICallbackEvent<Record> callbackEvent) {
		InputVariablesDialog variableDlg = new InputVariablesDialog( callbackEvent);
		
		final MdbDataSource ds = new MdbDataSource();
		
		ds.setDataComponent(variableDlg);
		ds.setDataSourceFields(fields);
		ds.createEmptyRecord();				

		variableDlg.setDataSource(ds);		
		canShow(variableDlg);
	}

	 
	private static void canShow(InputVariablesDialog variableDlg) {

		if ( isVisibleFiels(variableDlg.getDataForm().getFields()) ) {
			variableDlg.view();
			
		} else {
			
			variableDlg._callbackEvent.doWork(variableDlg.getDataForm().getValuesAsRecord());
		}
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
			canShow(variableDlg);	
	}
	
}



