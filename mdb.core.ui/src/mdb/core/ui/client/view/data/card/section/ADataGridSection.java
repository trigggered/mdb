/**
 * 
 */
package mdb.core.ui.client.view.data.card.section;



import java.util.logging.Logger;

import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.communication.IRemoteDataSave;
import mdb.core.ui.client.view.data.card.ICard;
import mdb.core.ui.client.view.data.grid.GridView;
/**
 * @author azhuk
 * Creation date: Mar 20, 2014
 *
 */
public  class  ADataGridSection extends GridView implements IDataSection, IRemoteDataSave{
	private static final Logger _logger = Logger
			.getLogger(ADataGridSection.class.getName());
	
	
	  
	private ICard _card;



	private String _sectionId;	
	
	
	public ADataGridSection (ICard card) {		
		_card = card;						
	}		
	
	
	@Override
	public void setSectionId(String sectionid) {
		_sectionId = sectionid;
	}
	
	@Override
	public String  getSectionId() {
		return _sectionId;
	}
	
	
	@Override
	public void prepareRequestData() {		
		
		_logger.info("################ Start prepareRequestData id for Grid Section: "+getSectionId()+" ######################");
		_logger.info("Put parametr ID_DOC= "+String.valueOf(_card.getId()));
		 getDataBinder().getDataProvider().getRequest().setPosition(2);
    	IRequestData entity = getDataBinder().getDataProvider().getRequest().add(new RequestEntity (getMainEntityId()));
    	
    	entity.getParams().putAll(getParams());
    	entity.getParams().add("ID_DOC", String.valueOf(_card.getId()));
    	
    	entity.setExecuteType(ExecuteType.GetData);
    	super.prepareRequestData();
    	_logger.info("################# End prepareRequestData  #####################");
	}


/*	
	@Override
	public void createMenu() {				 	
	}		
*/
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataSave#isValidate()
	 */
	@Override
	public boolean isValidate() {		
		return true;
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataSave#prepareSavedData()
	 */
	@Override
	public void prepareSavedData() {
		
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataSave#putSavedDataToQueue()
	 */
	@Override
	public void putSavedDataToQueue() {
		putRequestToQueue();				
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataSave#save()
	 */
	@Override
	public void save() {
	}
	
	
	
	@Override
	public ICard getCard() {
		return _card;
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.card.section.IDataSection#setCard(mdb.core.ui.client.view.data.card.ICard)
	 */
	@Override
	public void setCard(ICard card) {
		_card = card;
		
	}	

	
}
