/**
 * 
 */
package mdb.core.ui.smartgwt.client.view.data.card.section;

import java.util.Map;
import java.util.logging.Logger;

import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.communication.IRemoteDataSave;
import mdb.core.ui.client.data.bind.IDataBinder;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.data.IDataView;
import mdb.core.ui.client.view.data.card.ICard;
import mdb.core.ui.client.view.data.card.section.IDataSection;
import mdb.core.ui.smartgwt.client.view.data.DynamicFieldsView;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.fields.FormItem;




/**
 * @author azhuk
 * Creation date: Jan 29, 2014
 *
 */
public  class DataFieldsSection extends DynamicFieldsView implements IDataSection<Record>, IRemoteDataSave{
	
	private static final Logger _logger = Logger.getLogger(DataFieldsSection.class.getName());
	
	
	private ICard _card;
	private int _sectionId;
	
		
	public DataFieldsSection(ICard card) {
		_card = card;			
		setMainEntityId(_card.getCardEntityId());
	}	
	
	
	@Override
	protected void createMenuContainer () {	
	}			
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.DataView#prepareRequestData()
	 */
	@Override
	public void prepareRequestData() {
		_logger.info("################ Start prepareRequestData  for Fields Section ######################");		
		IRequestData re = getDataBinder().getDataProvider().getRequest().add(new RequestEntity(getMainEntityId()));				
		re.getParams().copyFrom(getParams());			
		re.setExecuteType(mdb.core.shared.transport.IRequestData.ExecuteType.GetData);	
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
		getDynamicFields().getDataForm().resetValues();
		getDynamicFields().getDataSource().getRequestEntity().loseChanges();
	}



	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataSave#prepareSavedData()
	 */
	@Override
	public void prepareSavedData() {			  
		
		FormItem item = getDynamicFields().getDataForm().getItem("ID_DOC");
		if (item != null) {
			Object val = item.getValue();
			if (val == null) {
				item.setValue(_card.getCardId());
			}
		}
		
		if ( isHaseChanges() ) {
			getDynamicFields().getDataForm().saveData();
		}
	}

	@Override
	public boolean isHaseChanges() {		
		@SuppressWarnings("rawtypes")
		Map changetValues = getDynamicFields().getDataForm().getChangedValues();
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
		/*Record rec = getSelectedRecord();		
		String toReturn = rec.getAttributeAsString(fieldName);		
		
		return toReturn;
		*/
		return null;
	}



	@Override
	public void setCanEdit(boolean value) {
		getDynamicFields().setCanEdit(value);
		
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.card.section.IDataSection#setSectionId(java.lang.String)
	 */
	@Override
	public void setSectionId(int sectionid) {
		_sectionId = sectionid;
		
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.card.section.IDataSection#getSectionId()
	 */
	@Override
	public int getSectionId() {
		
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
	 * @see mdb.core.ui.client.view.data.ADataView#createDataBinder()
	 */
	@Override
	protected IDataBinder createDataBinder() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.ADataView#clearEvents(mdb.core.ui.client.view.data.IDataView)
	 */
	@Override
	protected void clearEvents(IDataView view) {
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
