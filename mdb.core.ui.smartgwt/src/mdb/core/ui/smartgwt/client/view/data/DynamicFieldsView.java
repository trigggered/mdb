/**
 * 
 */
package mdb.core.ui.smartgwt.client.view.data;

import java.util.Map;
import java.util.logging.Logger;

import mdb.core.ui.client.communication.IRemoteDataSave;
import mdb.core.ui.client.data.IBaseDataSource;
import mdb.core.ui.client.data.IDataComponent;
import mdb.core.ui.client.data.bind.DataBindException;
import mdb.core.ui.client.data.fields.IMdbField;
import mdb.core.ui.client.view.components.menu.IMenuFactory;
import mdb.core.ui.client.view.data.IDataView;
import mdb.core.ui.smartgwt.client.data.IDataSource;
import mdb.core.ui.smartgwt.client.view.BaseView;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.VLayout;



/**
 * @author azhuk
 * Creation date: Jan 29, 2014
 *
 */
public class DynamicFieldsView extends DataView implements IRemoteDataSave{
	
	private static final Logger _logger = Logger.getLogger(DynamicFieldsView.class.getName());

	 public class DynamicFields extends BaseView implements IDataComponent {

		private DynamicForm _dataForm;
		 IBaseDataSource _dataSource; 
		 
		public DynamicFields() {
		
		}
		
		public DynamicForm getDataForm() {
			return _dataForm;
		}
		
		@Override
		protected void createComponents() {
			_dataForm = new DynamicForm();		
			_dataForm.setHeight100();;  
			_dataForm.setWidth100();  
			_dataForm.setPadding(5);
			//_dataForm.setLayoutAlign(VerticalAlignment.BOTTOM);
			//_dataForm.setAlign(Alignment.LEFT);
			//_dataForm.setColWidths("*","300");						
			
			getMainLayout().addMember(_dataForm);
		}
		
		@Override
		public void setDataSource(IBaseDataSource value ) {
			setDataSource(value,true);
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.client.data.IDataComponent#getDataSource()
		 */
		@Override
		public IBaseDataSource getDataSource() {
			return _dataSource;
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.client.data.IDataComponent#setDataSource(mdb.core.ui.client.data.IDataSource, boolean)
		 */
		@Override
		public void setDataSource(IBaseDataSource value, boolean isCanEdit) {
			if (value != null) {
				IDataSource  ds = (IDataSource)value;
				
				_dataSource = value;
				value.setDataComponent(this);
				if (isCanEdit) {
					bindToDataComponent(ds, getDataForm());
				}else {
					bindToDataComponent(ds, getDataForm(), false);
				}
			}
			
		}

		 
		private void bindToDataComponent(IDataSource ds, DynamicForm dataForm) {
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
			
			
		} 	
		
		
		private void bindToDataComponent(IDataSource ds, DynamicForm dataForm, boolean isCanEdit) {		
			if (dataForm != null) {			
				dataForm.setDataSource(ds.getDataSource());			
				dataForm.fetchData();
				
					for (FormItem item : dataForm.getFields() ) {
						item.setCanEdit(isCanEdit);					 
					}
					
			ds.initFieldToDefValue();
			}	
			
		} 	
		
		
		/* (non-Javadoc)
		 * @see mdb.core.ui.client.data.IDataComponent#setFieldValue(java.lang.String, java.lang.String)
		 */
		@Override
		public void setFieldValue(String fieldName, String value) {
			
			FormItem item = getDataForm().getField(fieldName);	
			if (item != null) {
				item.setValue(value);
			}			
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.client.data.IDataComponent#setDataSource(mdb.core.ui.client.data.IBaseDataSource)
		 */

		/* (non-Javadoc)
		 * @see mdb.core.ui.client.view.ABaseView#createViewPanel()
		 */
		@Override
		protected void createMainLayout() {
			setMainLayout(new VLayout());
			
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.client.view.ABaseView#createMenuContainer()
		 */
		@Override
		protected void createMenuContainer() {						
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.client.view.ABaseView#getMenuFactory()
		 */
		@Override
		protected IMenuFactory getMenuFactory() {
			// TODO Auto-generated method stub
			return null;
		}		
	};
	
		
		
	private DynamicFields _dynamicFields;		
	
	
	/**
	 * @return the _dynamicFields
	 */
	public DynamicFields getDynamicFields() {
		return _dynamicFields;
	}



	@Override
	protected void createMenuContainer () {		
	}
			
	

	@Override
	protected  void createComponents() {
		super.createComponents();		
		
		_dynamicFields = new DynamicFields();
		//setShowEdges(true);		
		getMainLayout().addMember(_dynamicFields.getMainLayout());
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.IDataView#getSelectedRecord()
	 */

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.IDataView#isSelectedRecord()
	 */
	@Override
	public boolean isSelectedRecord() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.DataView#bindDataComponents()
	 */
	@Override
	public void bindDataComponents() throws DataBindException {
		IBaseDataSource ds =  getMainDataSource();
		ds.setLocalKeyGen(false);		
		_dynamicFields.setDataSource(ds);				
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.BaseView#createMenu()
	 */
	@Override
	protected void createMenu() {
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 */
	@Override
	public void save() {		
	}
	
	public void cancel() {
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataSave#isValidate()
	 */
	@Override
	public boolean isValidate() {
		return _dynamicFields.getDataForm().validate();		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataSave#prepareSavedData()
	 */
	@Override
	public void prepareSavedData() {
		_dynamicFields.getDataForm().saveData();
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataSave#putSavedDataToQueue()
	 */
	@Override
	public void putSavedDataToQueue() {		
		putRequestToQueue();		
	}



	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.DataView#prepareRequestData()
	 */
	@Override
	public void prepareRequestData() {
		// TODO Auto-generated method stub
		
	}



	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.IDataView#callDeleteEvent()
	 */
	@Override
	public void callDeleteEvent() {
		// TODO Auto-generated method stub
		
	}



	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.IDataView#getSelectedRecordJSON()
	 */
	@Override
	public String getSelectedRecordJSON() {
		// TODO Auto-generated method stub
		return null;
	}





	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.ADataView#clearEvents(mdb.core.ui.client.view.data.IDataView)
	 */
	@Override
	protected void clearEvents(IDataView<Record> view) {
		// TODO Auto-generated method stub
		
	}



	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.ABaseView#initSize()
	 */
	@Override
	protected void initSize() {
		// TODO Auto-generated method stub
		
	}



	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.ABaseView#createViewPanel()
	 */
	@Override
	protected void createMainLayout() {
		// TODO Auto-generated method stub
		
	}


}
