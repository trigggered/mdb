/**
 * 
 */
package mdb.core.ui.client.view.data;

import java.util.logging.Logger;

import mdb.core.ui.client.communication.IRemoteDataSave;
import mdb.core.ui.client.data.IDataComponent;
import mdb.core.ui.client.data.IDataSource;
import mdb.core.ui.client.data.bind.DataBindException;
import mdb.core.ui.client.view.BaseView;
import mdb.core.ui.client.view.components.menu.IMenuContainer;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;



/**
 * @author azhuk
 * Creation date: Jan 29, 2014
 *
 */
public class DynamicFieldsView extends DataView implements IRemoteDataSave{
	
	private static final Logger _logger = Logger.getLogger(DynamicFieldsView.class.getName());

	 public class DynamicFields extends BaseView implements IDataComponent {

		private DynamicForm _dataForm;
		 IDataSource _dataSource; 
		 
		public DynamicFields() {
			super(EViewPanelType.VLayout);
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
			
			addMember(_dataForm);
		}
		
		@Override
		public void setDataSource(IDataSource value ) {
			setDataSource(value,true);
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.client.data.IDataComponent#getDataSource()
		 */
		@Override
		public IDataSource getDataSource() {
			return _dataSource;
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.client.data.IDataComponent#setDataSource(mdb.core.ui.client.data.IDataSource, boolean)
		 */
		@Override
		public void setDataSource(IDataSource value, boolean isCanEdit) {
			if (value != null) {
				_dataSource = value;
				value.setDataComponent(this);
				if (isCanEdit) {
					value.bindToDataComponent(getDataForm());
				}else {
					value.bindToDataComponent(getDataForm(), false);
				}
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
	};
	
		
		
	private DynamicFields _dynamicFields;		
	
	
	/**
	 * @return the _dynamicFields
	 */
	public DynamicFields getDynamicFields() {
		return _dynamicFields;
	}



	@Override
	protected IMenuContainer createMenuContainer () {
		return null;
	}
			
	

	@Override
	protected  void createComponents() {
		super.createComponents();		
		
		_dynamicFields = new DynamicFields();
		//setShowEdges(true);		
		getViewPanel().addMember(_dynamicFields);
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.IDataView#getSelectedRecord()
	 */
	@Override
	public Record getSelectedRecord() {
		return null;
	}

	
	@Override
	public Record[] getSelectedRecords() {
		return null;
	}
	
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
		IDataSource ds =  getMainDataSource();
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


}
