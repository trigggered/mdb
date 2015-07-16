/**
 * 
 */
package mdb.core.vaadin.ui.view.dialogs;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import mdb.core.shared.utils.comparator.ComparatorByPosition;
import mdb.core.ui.client.data.IBaseDataSource;
import mdb.core.ui.client.data.IDataComponent;
import mdb.core.ui.client.data.fields.IMdbField;
import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.vaadin.ui.components.data.DataForm;
import mdb.core.vaadin.ui.data.IDataSource;
import mdb.core.vaadin.ui.data.MdbDataSource;
import mdb.core.vaadin.ui.data.fields.MdbField;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;


/**
 * @author azhuk
 * Creation date: Jan 9, 2013
 *
 */
public class FormDialog extends BaseDataDialog implements IDataComponent  {	

	private static final Logger _logger = Logger.getLogger(FormDialog.class.getName());
	
	
	private ICallbackEvent<Item> _callbackEvent;	
	
	protected DataForm _dataForm;
			
	public FormDialog  () {
	}
	
	public FormDialog  (ICallbackEvent<Item> callbackEvent) {
		_callbackEvent = callbackEvent;
	}
	
	public ICallbackEvent<Item> getCallbackEvent() {
		return _callbackEvent;
	}

	public void setCallbackEvent(ICallbackEvent<Item> callbackEvent) {
	this._callbackEvent = callbackEvent;
	}
	
	
	public FormLayout getDataForm() {		
		return _dataForm;
	}		
	
	@Override
	public void setDataSource(IBaseDataSource ds, boolean isCanEdit ) {		

		
		/*if (ds != null) {
			IDataSource  ds2 = (IDataSource)ds; 
			//super.setDataSource(ds);
			ds2.setDataComponent(this);
			if (isCanEdit) {
				bindToDataComponent(ds2,getDataForm());
			}else {
				bindToDataComponent(ds2,getDataForm(), false);
			}
		}*/
	}
	
	/*private void bindToDataComponent(IDataSource ds, DynamicForm dataForm) {
		if (dataForm != null) {
			dataForm.setDataSource(ds.getDataSource());
			dataForm.fetchData();			
			
			if (ds.getDataSourceFields()!= null) {
				for (FormItem item : dataForm.getFields() ) {
					
					Map<String, IMdbField>  mapMdbField = ds.getDataSourceFields().getMdbFieldMap(); 
					
					 if ( mapMdbField.containsKey(item.getName()) ) {
						  
						  item.setVisible(mapMdbField.get(item.getName()).isVisivble());
						  item.setCanEdit(!mapMdbField.get(item.getName()).isReadOnly());						  				
					 }
				}
			}
			
			ds.initFieldToDefValue();	
		}	
		
	} 	*/
	
	
	/*private void bindToDataComponent(IDataSource ds, DynamicForm dataForm, boolean isCanEdit) {		
		if (dataForm != null) {			
			dataForm.setDataSource(ds.getDataSource());			
			dataForm.fetchData();
			
				for (FormItem item : dataForm.getFields() ) {
					item.setCanEdit(isCanEdit);					 
				}
				
		ds.initFieldToDefValue();
		}	
		
	} 	*/	
	
	
	protected void createTopLayout() {
		/*Layout top = new HorizontalLayout();
		top.setCaption("TopLayout");
		getMainLayout().addComponent(top);*/
	}
	
	@Override
	protected void createContextLayout() {
		 TabSheet tabSheet = new TabSheet();
		 
		_dataForm = new DataForm();
		
		
		Tab tab = tabSheet.addTab(_dataForm, getCaption());
		tab.setClosable(false);		

		//Panel panel = new Panel();	
		//panel.setContent(_dataForm);
		getMainLayout().addComponent(_dataForm);
		//getMainLayout().addComponent(tabSheet);
		//getMainLayout().addComponent(panel);
		//((AbstractOrderedLayout)getMainLayout()).setExpandRatio(tabSheet, 0.9f);
	}
	
	@Override
	protected boolean validate() {
		return _dataForm.validate();
		
	}
	
	
	@Override
	public boolean isHaseChanges() {
		
		return _dataForm.isHaseChanges();
		
	}
	
	@Override	
	protected void save() {
		if (isHaseChanges() ) {		
			
			_dataForm.save();			
			
			saveChangesToServer();
		}

	}
	
	
	@Override
	public void okBtnClickEvent() {
		super.okBtnClickEvent();
		if (_callbackEvent != null)  {
			_callbackEvent.doWork(_dataForm.getItem());
		}			
		
	}	
	
	
	@Override
	public String getCaption() {		
		return "Data edit";
	}

	
	@Override
	public void setCanEdit(boolean value) {
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.IDataComponent#setDataSource(mdb.core.ui.client.data.IBaseDataSource)
	 */
	@Override
	public void setDataSource(IBaseDataSource ds) {
		setDataSource(ds, false);		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.IDataComponent#setFieldValue(java.lang.String, java.lang.String)
	 */
	@Override
	public void setFieldValue(String fieldName, String value) {
		// TODO Auto-generated method stub		
	}	
	
	public static void viewForEdit(IDataSource ds, Item data) {
		
		FormDialog dlg = new FormDialog();
		dlg._dataForm.bindData(ds, data);
		dlg.show();
	}

		
}

