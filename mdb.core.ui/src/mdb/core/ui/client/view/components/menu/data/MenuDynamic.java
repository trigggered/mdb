/**
 * 
 */
package mdb.core.ui.client.view.components.menu.data;

import java.util.HashMap;
import java.util.logging.Logger;

import mdb.core.shared.data.Params;
import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.shared.transport.Request;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.communication.IDataProvider;
import mdb.core.ui.client.communication.IRemoteDataRequest;
import mdb.core.ui.client.communication.impl.GatewayQueue;
import mdb.core.ui.client.data.IDataSpecification;
import mdb.core.ui.client.data.bind.IViewDataBinder.State;
import mdb.core.ui.client.data.impl.ViewDataConverter;
import mdb.core.ui.client.data.impl.fields.DataSourceFields;
import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.view.components.menu.IMenu;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.components.menu.Menu;

import com.smartgwt.client.data.Record;

/**
 * @author azhuk
 * Creation date: Dec 11, 2012
 *
 */
public abstract  class MenuDynamic extends Menu	implements  IDataProvider, IRemoteDataRequest{
	
	private int _entityId ; 
	protected Params _params;
	private Request _request = new Request();
	private Request _responce;
	
	

	private HashMap<String, IMenuItem> _mapItems  = new HashMap<String, IMenuItem>();	
	
	private static final Logger _logger = Logger.getLogger(MenuDynamic.class.getName());
	private IDataSpecification _dataSpecification;				
	private Record[] _records;	
	
	
	/**
	 * @param name
	 */
	public MenuDynamic(String name) {
		super(name);
	}
	
	protected  HashMap<String, IMenuItem> getMapItems() {
		return _mapItems;
	}

	protected void setParentIdValue(IMenuItem item, String parentId) {
		item.setAttribute("parentID",parentId);
	}
	
	protected void buildMenu(Record[] records) {
		_logger.info("Start build menu");
		
		if (records != null && records.length > 0) {				
						
		    for (Record rec : records) {
		    	
		    	String id = rec.getAttributeAsString(getDataSpecification().getId());
		    	String parentId = rec.getAttributeAsString(getDataSpecification().getParentId());
		    	String title = rec.getAttributeAsString(getDataSpecification().getTitle());
		    	String img =  rec.getAttributeAsString(getDataSpecification().getImg());
		    	String action = rec.getAttributeAsString(getDataSpecification().getAction());
		    	
		    	String position = rec.getAttributeAsString(getDataSpecification().getPosition());
		    	String shortcut = rec.getAttributeAsString(getDataSpecification().getShortcut());
		    	
		    	
		    	IMenuItem item  = createMenuItem(IMenuItem.ItemType.MenuItem);
		    	item.setCaption(title);
		    	item.setHint("id:"+id+" Action:"+action );
		    	item.setImg(img);
		    	item.setAttribute("ID",id);
		    	item.setAttribute("IMG",img);		    	
		    	item.setAttribute("ACTION",action);
		    	item.setAttribute("SHORTCUT",shortcut);
		    	if (position!=null && !position.isEmpty()) {
		    		item.setPosition(Integer.parseInt(position));
		    	}
		    	
		    	setParentIdValue(item, parentId);		    	
		    	
		    	setCommand(item);
		    	_mapItems.put(id, item);
		    	
			}
		    
		    
		    for ( IMenuItem item : _mapItems.values() ) {
		    	String parentId = item.getAttribute("parentID"); 
		    	if ( parentId!=null && _mapItems.containsKey(parentId) ) {
		    		 
		    			IMenuItem parentItem = _mapItems.get(parentId);
		    			parentItem.setItemType(IMenuItem.ItemType.Menu);
		    			IMenu childMenu = parentItem.getChildMenu()!=null?parentItem.getChildMenu():new Menu("childMenu");
		    			parentItem.setChildMenu(childMenu);
		    			childMenu.addItem(item);		    	
		    	}
		    	else {
		    		addItem(item);
		    	}			    	
		    }		
		} else {
			getItems().clear();
		}
		
		_logger.info("End build menu");
		containerBind();	    
	}	
	

	
	protected  abstract void setCommand(IMenuItem item );
	
	public  void addCommand(ICommand<IMenuItem> command) {
		
	}
		
	
		
	
	protected IDataSpecification getDataSpecification () {
		return _dataSpecification;
	}
	
	protected void setDataSpecification (IDataSpecification  value) {
		 _dataSpecification = value;
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.view.common.menu.IMenuBuilder#build(int)
	 */
	
	public void  build(int entityId, Params params ) {		
		setEntityId(entityId);
		build(params);
	}
	
	public void  build( Params params ) {		
		_params = params; 
		prepareRequestData();
		putRequestToQueue();
	}
	
	
	
	@Override
	public void callRequestData() {
		prepareRequestData();
		putRequestToQueue();
	}
	
	
	
	@Override
	public void putRequestToQueue() {
		GatewayQueue.instance().put(this);
	}
	
	@Override
	public void prepareRequestData() {		
		 IRequestData  re = _request.add(new RequestEntity(getEntityId()));
		 re.setRequestFieldsDescription(false);
		 re.setPosition(getPosition()*10);		 
		 re.setParams(_params);		 
		 
	}			

	
	@Override
	public Request getRequest() {
		return _request;
	}


	
	@Override
	public Request getResponse() {
		return _responce;
	}	

	
	@Override
	public void onFailure(Throwable caught) {		
		_logger.severe("RPC communication Failure" + caught.getMessage());
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	@Override
	public void onSuccess(Request result) {
		_logger.info("Success RPC Communication");
		_responce = result;
		IRequestData rq =	_responce.get(String.valueOf(getEntityId()));
			if (rq != null) {
				((RequestEntity) rq).setExecuteType(ExecuteType.GetData);
				 _records = ViewDataConverter.jSon2RecordArray(rq.getData());
				buildMenu(_records);
			}			
	}

	
	public IMenu getSelf() {
		return this;
	}

	
	public void containerBind() {
		if (getContainer() !=null) {					
			getContainer().bind(getSelf());			
		}
	}

	public int getEntityId() {
		return _entityId;
	}

	public void setEntityId(int entityId) {
		_entityId = entityId;
	}

	@Override
	public HashMap<Integer, Record[]> getDataMap() {
		return null;
	}

	
	@Override
	public void setAfterInvokeEvent(ICallbackEvent<State> callbackEvent) {
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IDataProvider#getDataSourceFieldsMap()
	 */
	@Override
	public HashMap<Integer, DataSourceFields> getDataSourceFieldsMap() {
		return null;
	}
	
	@Override
	public HashMap<Integer, String[]> getDataSourceKeysMap() {
		return null;
	}
	
}
