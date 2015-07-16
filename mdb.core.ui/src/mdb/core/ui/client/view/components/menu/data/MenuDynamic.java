/**
 * 
 */
package mdb.core.ui.client.view.components.menu.data;

import java.util.HashMap;
import java.util.Map;
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
import mdb.core.ui.client.data.IDataWraper;
import mdb.core.ui.client.data.bind.IDataBinder.State;
import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.view.components.menu.IMenu;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.components.menu.Menu;


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
	//private Record[] _records;	
	private IDataWraper _dataWraper; 
	
	
	public MenuDynamic (String name, IDataWraper dataWraper) {
		super(name);
		setDataWraper(dataWraper);		
	}	
	
	
	/**
	 * @param dataWraper
	 */
	private void setDataWraper(IDataWraper dataWraper) {
		_dataWraper = dataWraper;		
	}
	
	protected IDataWraper getDataWraper() {
		return _dataWraper;
	}

	
	protected  HashMap<String, IMenuItem> getMapItems() {
		return _mapItems;
	}

	
	
	protected void buildMenu() {
		_logger.info("Start build menu");
		
		
		if (_dataWraper.isDataExists()) {				
			
			while(_dataWraper.hasNext()) {
		    	
				Map<String, String> rec = _dataWraper.next();
			
		    	String id = rec.get(getDataSpecification().getId());
		    	String parentId = rec.get(getDataSpecification().getParentId());
		    	String title = rec.get(getDataSpecification().getTitle());
		    	String img =  rec.get(getDataSpecification().getImg());
		    	String action = rec.get(getDataSpecification().getAction());
		    	
		    	String position = rec.get(getDataSpecification().getPosition());
		    	String shortcut = rec.get(getDataSpecification().getShortcut());
		    	
		    	
		    	IMenuItem item  = createMenuItem(IMenuItem.ItemType.MenuItem);
	    	
		    	if (parentId != null && !parentId.isEmpty()) {
		    		item.setParentId(Integer.parseInt(parentId) );
		    	} else {
		    		item.setParentId(-1);
		    	}
		    	
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
		    	
		    	//setParentIdValue(item, parentId);		    	
		    	
		    	setCommand(item);
		    	_mapItems.put(id, item);
		    	
			}
		    
		    
		    for ( IMenuItem item : _mapItems.values() ) {
		    	//String parentId = item.getAttribute("parentID"); 
		    	String parentId = String.valueOf(item.getParentId());
		    	if (item.isHasParent() && _mapItems.containsKey(parentId) ) {
		    		 
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
				_dataWraper.setData(rq.getData());				
				buildMenu();
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
	public HashMap<Integer, String> getDataMap() {
		return null;
	}

	
	@Override
	public void setAfterInvokeEvent(ICallbackEvent<State> callbackEvent) {
	}
	
	
	
}
