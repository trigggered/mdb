/**
 * 
 */
package mdb.core.ui.client.view.data.card.section;

import java.util.Map;
import java.util.logging.Logger;

import mdb.core.ui.client.communication.IRemoteDataSave;
import mdb.core.ui.client.data.IDataComponent;
import mdb.core.ui.client.data.IDataSource;
import mdb.core.ui.client.data.bind.DataBindException;
import mdb.core.ui.client.view.BaseView;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.data.DataView;
import mdb.core.ui.client.view.data.card.ICard;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;


/**
 * @author azhuk
 * Creation date: Jan 29, 2014
 *
 */
public  class ADataFieldsSection extends DataView implements IDataSection, IRemoteDataSave{
	
	private static final Logger _logger = Logger.getLogger(ADataFieldsSection.class.getName());
	
	
	private class DynamicFieldsSection extends BaseView implements IDataComponent {

		private DynamicForm _dataForm;
		 IDataSource _dataSource; 
		 
		public DynamicFieldsSection() {
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
		
		@Override
		public void setCanEdit(boolean value) {
			super.setCanEdit(value);
			
			_dataForm.setCanEdit(value);								
							
		}		
	};
	
	//
		
		
	private DynamicFieldsSection _editDialog;
	private ICard _card;
	private String _sectionId;
	
		
	public ADataFieldsSection(ICard card) {
		_card = card;			
		setMainEntityId(_card.getCardEntityId());
	}	
	
	
	@Override
	protected IMenuContainer createMenuContainer () {
		return null;
	}			
	

	@Override
	protected  void createComponents() {
		super.createComponents();		
		
		_editDialog = new DynamicFieldsSection();
		//setShowEdges(true);		
		getViewPanel().addMember(_editDialog);
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.IDataView#getSelectedRecord()
	 */
	@Override
	public Record getSelectedRecord() {
		return _editDialog.getDataForm().getValuesAsRecord();		
	}
	
	@Override
	public Record[] getSelectedRecords() {
		Record[] toReturn = new Record[1];
		toReturn[0] = _editDialog.getDataForm().getValuesAsRecord();
		return toReturn;
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
	 * @see mdb.core.ui.client.view.data.DataView#prepareRequestData()
	 */
	@Override
	public void prepareRequestData() {
				
	}

	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.DataView#bindDataComponents()
	 */
	@Override
	public void bindDataComponents() throws DataBindException {
		IDataSource ds =  getMainDataSource();
		ds.setLocalKeyGen(false);		
		_editDialog.setDataSource(ds);
		
		
		
		switch (_card.getViewState())  {
			case  New:
				
				Record newRecord = new Record();				
				
				
				_editDialog.getDataForm().editNewRecord(newRecord);			
				
				break;
			default:				
				
				break;
				
		}		
		//_editDialog.getDataForm().validate();
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
	
	
	public void loseChange() {
		_editDialog.getDataForm().resetValues();
		_editDialog.getDataSource().getRequestEntity().loseChanges();
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataSave#isValidate()
	 */
	@Override
	public boolean isValidate() {
		return _editDialog.getDataForm().validate();		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataSave#prepareSavedData()
	 */
	@Override
	public void prepareSavedData() {			  
		
		FormItem item = _editDialog.getDataForm().getItem("ID_DOC");
		if (item != null) {
			Object val = item.getValue();
			if (val == null) {
				item.setValue(_card.getId());
			}
		}
		
		if ( isHaseChanges() ) {
			  _editDialog.getDataForm().saveData();
		}
	}

	@Override
	public boolean isHaseChanges() {		
		@SuppressWarnings("rawtypes")
		Map changetValues = _editDialog.getDataForm().getChangedValues();
		return changetValues == null || changetValues.size() >0;
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataSave#putSavedDataToQueue()
	 */
	@Override
	public void putSavedDataToQueue() {		
		putRequestToQueue();		
	}


	/**
	 * @param string
	 * @return
	 */
	public String getFieldValue(String fieldName) {		
		Record rec = getSelectedRecord();		
		String toReturn = rec.getAttributeAsString(fieldName);		
		
		return toReturn;
	}


	/**
	 * @param string
	 * @param valueOf
	 */
	public void setFieldValue(String fieldName, String value) {		
		FormItem item = _editDialog.getDataForm().getItem(fieldName);
		if (item != null) {
			item.setValue(value);			
		}			
	}
	
	@Override
	public void setCanEdit(boolean value) {
		_editDialog.setCanEdit(value);
		
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.card.section.IDataSection#setSectionId(java.lang.String)
	 */
	@Override
	public void setSectionId(String sectionid) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.card.section.IDataSection#getSectionId()
	 */
	@Override
	public String getSectionId() {
		
		return _sectionId;
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.card.section.IDataSection#setCard(mdb.core.ui.client.view.data.card.ICard)
	 */
	@Override
	public void setCard(ICard card) {
		_card = card;
		
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.card.section.IDataSection#getCard()
	 */
	@Override
	public ICard getCard() {		
		return _card;
	}

}
