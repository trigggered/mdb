/**
 * 
 */
package mdb.core.ui.client.view.data;

import java.util.HashMap;

import mdb.core.shared.data.Params;
import mdb.core.ui.client.communication.IDataProvider;
import mdb.core.ui.client.communication.IRemoteDataRequest;
import mdb.core.ui.client.communication.impl.GatewayQueue;
import mdb.core.ui.client.data.IDataSource;
import mdb.core.ui.client.data.bind.DataBindException;
import mdb.core.ui.client.data.bind.IViewDataBinder;
import mdb.core.ui.client.data.bind.impl.MdbViewDataBinder;
import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.events.IDataEditHandler;
import mdb.core.ui.client.resources.locales.Captions;
import mdb.core.ui.client.view.BaseView;
import mdb.core.ui.client.view.components.menu.IMenu;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuItemFactory;
import mdb.core.ui.client.view.components.menu.data.MenuDataNavigator;
import mdb.core.ui.client.view.components.menu.data.MenuDataPaging;
import mdb.core.ui.client.view.components.menu.impl.smart.ToolStripMenuContainer;
import mdb.core.ui.client.view.components.menu.impl.smart.ToolStripMenuItemFactory;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.layout.Layout;

/**
 * @author azhuk
 * Creation date: Oct 16, 2012
 *
 */
public abstract class DataView extends BaseView implements IDataView {
	
	protected HashMap<Integer, IDataSource> _lstDataSources = new  HashMap<Integer, IDataSource>();
	private Layout _dataLayout;
	private IViewDataBinder _viewDataBinder;
	
	private IDataProvider _dataProvider;	
	private int _entityId =-1;
	private Params _params = new Params();

	private boolean _isCreateMenuPaging   = true;
	private boolean _isAutoSaveAfterEdit = false;  	


	protected IDataEditHandler _editHandler;
	protected IDataEditHandler _insertHandler;
	protected IDataEditHandler _deleteHandler;
	protected IDataEditHandler _viewHandler;
	 
	private ICallbackEvent<Void>  _saveAfterEvent = new ICallbackEvent<Void>() {			
		@Override
		public void doWork(Void data) {
			if (_isAutoSaveAfterEdit ) {
				save();						
			}
		}	
	};
	
	private ICallbackEvent<Void> _afterDeleteEvent = _saveAfterEvent;
	private ICallbackEvent<Void> _afterEditEvent 	= _saveAfterEvent;
	private ICallbackEvent<Void> _afterInsertEvent = _saveAfterEvent;
	
	/**
	 * @return the _isCreateMenuPaging
	 */
	public boolean isCreateMenuPaging() {
		return _isCreateMenuPaging;
	}

	/**
	 * @return the _isCreateMenuNavigator
	 */
	public boolean isCreateMenuNavigator() {
		return _isCreateMenuNavigator;
	}

	/**
	 * @param _isCreateMenuPaging the _isCreateMenuPaging to set
	 */
	public void setCreateMenuPaging(boolean value) {
		this._isCreateMenuPaging = value;
	}

	/**
	 * @param _isCreateMenuNavigator the _isCreateMenuNavigator to set
	 */
	public void setCreateMenuNavigator(boolean value) {
		this._isCreateMenuNavigator = value;
	}

	private boolean _isCreateMenuNavigator = true;
	
	
		
	public DataView(EViewPanelType viewPanelType) {
    	super(viewPanelType);    	
    }
	
	public DataView() {
    	super();
    }
	
	public DataView(int entityId) {
    	super(EViewPanelType.HLayout);
    	setMainEntityId(entityId);
    }			
				
		
	protected Layout getDataLayout () {
		return _dataLayout;
	}
	
	protected void setDataLayout (Layout value) {
		_dataLayout = value;
	}
	
		
	@Override
	public void initialize() {		
		callRequestData();
	}
	
	@Override
	public abstract void prepareRequestData();	
	
	@Override
	public void putRequestToQueue() {
		GatewayQueue.instance().put(getDataBinder().getDataProvider());
	}
	
	
	
	public  void prepareRequestData(IRemoteDataRequest ... arr) {
	
		for (IRemoteDataRequest dataRequestor : arr ) {
			if (dataRequestor!= null)
				dataRequestor.prepareRequestData();			 
		 }
	}
	
	
	public void putRequestToQueue(IDataView ... arr) {
		 for (IDataView dataRequestor : arr ) {
			 if (dataRequestor!= null) {
				 GatewayQueue.instance().put(dataRequestor.getDataBinder().getDataProvider());
			 }
		 }		
	}
	
	
	@Override
	public void callRequestData() {
		prepareRequestData();
		putRequestToQueue();
		GatewayQueue.instance().getProcessor().run(); 	
	}	
	

	@Override
	public void setDataBinder( IViewDataBinder value) {
		_viewDataBinder = value;
	}
	
	@Override
	public IViewDataBinder getDataBinder()  {
		if (_viewDataBinder == null) {
			_viewDataBinder = new MdbViewDataBinder(this);
		}
		return _viewDataBinder;
	}
	
	
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.data.bind.IBindSource#setDataProvider(mdb.core.ui.communications.IDataProvider)
	 */
	@Override
	public void setDataProvider(IDataProvider dataProvider) {
		_dataProvider =dataProvider;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.data.bind.IBindSource#getDataProvider()
	 */
	@Override
	public IDataProvider getDataProvider() {
		return _dataProvider;
	}
	
	
	@Override
	public void setDataSources(HashMap<Integer, IDataSource> value) { 
		_lstDataSources = value;
	}
	
	@Override
	public HashMap<Integer, IDataSource> getDataSources() {
		return _lstDataSources;
	}
	
	
	public IDataSource getDataSource(Integer key) {		
		if  (_lstDataSources.containsKey(key) ) {
				return _lstDataSources.get(key);
		}
		return null;
	}
	
	
	@Override
	public IDataSource getMainDataSource() {		
		return getDataSource(getMainEntityId());					
	}
	
	

	protected boolean isDataSourceExists() {
		return _lstDataSources!=null && _lstDataSources.size() > 0;
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.view.common.IDataView#view()
	 */
	@Override
	public abstract void bindDataComponents() throws DataBindException;	
	
	
	protected void bindMenuDataNavigator() {
		bindMenuDataNavigator(getMainEntityId());
	}

	protected void bindMenuDataNavigator(int entityId) {  		
				
			 IMenuContainer container =  getMenuContainer();
			 IDataSource ds = getDataSource(entityId);
				if (container != null && ds!= null) {
					if (_isCreateMenuPaging) {
						IMenu menuPaging = new MenuDataPaging("MenuPaging", ds,this);
						menuPaging.setPosition(0);
						container.bind(menuPaging);
					}
					
					if (_isCreateMenuNavigator) {
						IMenu menuNavigation = new MenuDataNavigator("MenuDataNavigator", ds,this);
						menuNavigation.setPosition(1);
						container.bind(menuNavigation);
					}
				}			
	}
	
	
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.view.common.IDataView#getCanvas()
	 */
	@Override
	public Canvas getCanvas() {
		return this;
	}	

	@Override
	protected IMenuContainer createMenuContainer () {
		IMenuContainer toReturn = new ToolStripMenuContainer(getMenuFactory());
		toReturn.setView(this);
		return toReturn;		
	}
	
	protected IMenuItemFactory getMenuFactory() {
		return new ToolStripMenuItemFactory();
	}
	
	@Override
	public  void callEditEvent() {
		callAfterEditEvent();
	}
	
	@Override
	public  void callViewEvent() {
		
	}
	
	/*Edit Events*/
	 @Override
	 public void addEditEvent (IDataEditHandler handler) {
		 _editHandler = handler; 
	 }
	 
	 @Override
	 public void addViewEvent (IDataEditHandler handler) {
		 _viewHandler = handler; 
	 }
	 
	 @Override
	 public void addInsertEvent (IDataEditHandler handler) {
		 _insertHandler = handler; 
	 }
	 

	@Override
	public void addDeleteEvent (IDataEditHandler handler) {
		_deleteHandler = handler;
	}
	
	public void addAfterDeleteEvent (ICallbackEvent<Void> event) {
		 _afterDeleteEvent =event;		
	}
	
	
	@Override
	public  void callInsertEvent() {
		callAfterInsertEvent();
	}
	
	@Override
	public void callDeleteEvent() {	
		
		if (isSelectedRecord()) {		
			
			SC.ask( Captions.Q_DELETE_REC, new BooleanCallback() {
				
				@Override
				public void execute(Boolean value) {
					if (value) {									 						 						
						for  (Record rec : getSelectedRecords()) {
							removeRecord(rec);
							callAfterDeleteEvent();
						}								
					}				
				}
			});				
		}		
	}
	
	/**
	 * 
	 */
	protected void callAfterDeleteEvent() {
		if (_afterDeleteEvent != null) {
			_afterDeleteEvent.doWork(null);
		}
		
	}

	protected void callAfterEditEvent() {
		if (_afterEditEvent != null) {
			_afterEditEvent.doWork(null);
		}
		
	}
	
	protected void callAfterInsertEvent() {
		if (_afterInsertEvent != null) {
			_afterInsertEvent.doWork(null);
		}
		
	}
	
	protected void removeRecord(Record rec) {
		getMainDataSource().getDataSource().removeData(rec);
	}
	
	
	@Override
	public void showFilterView() {
		
	}
	
	protected void clearEvents (IDataView view ) {
		IDataEditHandler emptyEvent = new IDataEditHandler() {			
			@Override
			public void onEdit(Record record) {
				// TODO Auto-generated method stub				
			}
		};
		
		view.addEditEvent(emptyEvent);
		view.addViewEvent(emptyEvent);
		view.addInsertEvent(emptyEvent);
		view.addDeleteEvent(emptyEvent);
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.view.data.IDataView#removeData()
	 */
	@Override
	public void removeData() {
		// TODO Auto-generated method stub		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.data.bind.IBindSource#bindDataComponentsAfterChange()
	 */
	@Override
	public void bindDataComponentsAfterChange() {		
		loseChange();
	}
	
	@Override
	public int getMainEntityId() {
		return _entityId;		
	}
	
	@Override
	public void  setMainEntityId(int entityId) {
		 _entityId = entityId;		
	}
	
	public Params getParams() {
		return _params;
	}
		
	@Override
	public boolean isHaseChanges() {
		IDataSource ds = getMainDataSource();
		if (ds !=null) {
			return ds.isHasChanges();
		}
		else return false;
	}
	
	@Override
	public void loseChange() {
		IDataSource ds = getMainDataSource();
		if (ds !=null && ds.getRequestEntity()!= null ) {
			ds.getRequestEntity().loseChanges();		
		}
	}

	@Override
	public void IsCanClose(final BooleanCallback callback) {
		if ( isHaseChanges() ) {
			Dialog dialog = new Dialog();
		     dialog.setShowModalMask(true);
		     dialog.setButtons(Dialog.YES,Dialog.NO, Dialog.CANCEL);     
		     
			SC.ask(Captions.ALARM, Captions.Q_SAVE_CHANGES, new BooleanCallback() {
				
				@Override
				public void execute(Boolean value) {
					Boolean toReturn = true;
					if (value != null) {
						if (value) {							
							save();

						} else {
							loseChange();		
						}				
						
					} else toReturn = false;
					
					if (callback != null) {
						callback.execute(toReturn);
					}						
				}
			},dialog);
		} else {
			callback.execute(true);
		}
	}
	
	public void save() {
		getMainDataSource().save();
	}
	
	@Override
	public boolean isValidate() {
		return true;
	}
	
	@Override
	public void redraw() {
		callRequestData();
	}
	
	/**
	 * @return the _isAutoSaveAfterEdit
	 */
	public boolean isAutoSave() {
		return _isAutoSaveAfterEdit;
	}

	/**
	 * @param _isAutoSaveAfterEdit the _isAutoSaveAfterEdit to set
	 */
	public void setAutoSave(boolean _isAutoSaveAfterEdit) {
		this._isAutoSaveAfterEdit = _isAutoSaveAfterEdit;
	}
	
}