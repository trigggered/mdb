/**
 * 
 */
package mdb.core.ui.smartgwt.client.view.data.card.section;



import java.util.logging.Logger;

import com.smartgwt.client.data.Record;

import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.communication.IRemoteDataSave;
import mdb.core.ui.client.view.data.card.ICard;
import mdb.core.ui.client.view.data.card.section.IDataSection;
import mdb.core.ui.smartgwt.client.view.data.grid.GridView;
/**
 * @author azhuk
 * Creation date: Mar 20, 2014
 *
 */
public  class  DataGridSection extends GridView implements IDataSection<Record>, IRemoteDataSave{
	private static final Logger _logger = Logger
			.getLogger(DataGridSection.class.getName());
	
	
	  
	private ICard _card;



	private int _sectionId;	
	
	
	public DataGridSection (ICard card) {		
		_card = card;						
	}		
	
	
	@Override
	public void setSectionId(int sectionid) {
		_sectionId = sectionid;
	}
	
	@Override
	public int  getSectionId() {
		return _sectionId;
	}
	
	
	@Override
	public void prepareRequestData() {			
		_logger.info("################ Start prepareRequestData id for Grid Section: "+getSectionId()+" ######################");				
    	IRequestData entity = getDataBinder().getDataProvider().getRequest().add(new RequestEntity (getMainEntityId()));
    	
    	_logger.info( getParams().get("CLIENTID").getValue());
    	
    	entity.getParams().copyFrom(getParams());    	
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
