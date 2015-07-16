/**
 * 
 */
package mdb.core.ui.client.view.data;

import java.util.Collection;
import java.util.HashMap;

import mdb.core.shared.data.Params;
import mdb.core.ui.client.communication.IDataProvider;
import mdb.core.ui.client.communication.IRemoteDataRequest;
import mdb.core.ui.client.communication.impl.GatewayQueue;
import mdb.core.ui.client.data.bind.DataBindException;
import mdb.core.ui.client.data.bind.IDataBinder;
import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.events.IDataEditHandler;
import mdb.core.ui.client.resources.locales.Captions;
import mdb.core.ui.client.view.ABaseView;
import mdb.core.ui.client.view.components.menu.IMenu;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.data.MenuDataNavigator;
import mdb.core.ui.client.view.components.menu.data.MenuDataPaging;
import mdb.core.ui.client.view.data.IDataView;
import mdb.core.ui.client.view.dialogs.message.Dialogs;
import mdb.core.ui.client.util.BooleanCallback;
import mdb.core.ui.client.data.IBaseDataSource;


/**
 * @author azhuk
 * Creation date: Oct 16, 2012
 *
 */
public abstract class ADataView<E> extends ABaseView implements IDataView<E> {
	
	protected HashMap<Integer, IBaseDataSource> _lstDataSources = new  HashMap<Integer, IBaseDataSource>();

	private IDataBinder _viewDataBinder;	
	private IDataProvider _dataProvider;
	
	private int _entityId =-1;
	private Params _params;

	private boolean _isCreateMenuPaging   = true;
	private boolean _isAutoSaveAfterEdit = false;  	


	protected IDataEditHandler<E> _editHandler;
	protected IDataEditHandler<E>  _insertHandler;
	protected IDataEditHandler<E>  _deleteHandler;
	protected IDataEditHandler<E>  _viewHandler;
	 
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
	
	@Override
	protected void createComponents() {
		super.createComponents();	
		_params = new Params();
	}
	
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
	@Override
	public void setCreateMenuPaging(boolean value) {
		this._isCreateMenuPaging = value;
	}

	/**
	 * @param _isCreateMenuNavigator the _isCreateMenuNavigator to set
	 */
	@Override
	public void setCreateMenuNavigator(boolean value) {
		this._isCreateMenuNavigator = value;
	}

	private boolean _isCreateMenuNavigator = true;
	
	
		
	public ADataView(EViewPanelType viewPanelType) {
    	super(viewPanelType);    	
    }
	
	public ADataView() {
    	super();
    }
	
	public ADataView(int entityId) {
    	super(EViewPanelType.HLayout);
    	setMainEntityId(entityId);
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
	
	
	
	public  void prepareRequestData(Collection<IDataView<E>> values) {	
		prepareRequestData(values.toArray(new IDataView[values.size()]));
	}
	
	
	public  void prepareRequestData(IRemoteDataRequest ... arr) {
		
		for (IRemoteDataRequest dataRequestor : arr ) {
			if (dataRequestor!= null)
				dataRequestor.prepareRequestData();			 
		 }
	}
	
	
	public void putRequestToQueue(IDataView<E> ... arr) {
		 for (IDataView<E> dataRequestor : arr ) {
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
	public void setDataBinder( IDataBinder value) {
		_viewDataBinder = value;
	}
	
	@Override
	public IDataBinder getDataBinder()  {
		if (_viewDataBinder == null) {
			_viewDataBinder = createDataBinder();
		}
		return _viewDataBinder;
	}
	
	protected abstract IDataBinder createDataBinder();
	
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
	
	
	public void setDataSources(HashMap<Integer, IBaseDataSource> value) { 
		_lstDataSources = value;
	}
	
	public HashMap<Integer, IBaseDataSource> getDataSources() {
		return _lstDataSources;
	}
	
	
	public IBaseDataSource getDataSource(Integer key) {		
		if  (_lstDataSources.containsKey(key) ) {
				return _lstDataSources.get(key);
		}
		
		return null;
	}
	
	
	@Override
	public IBaseDataSource getMainDataSource() {		
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
			 IBaseDataSource ds = getDataSource(entityId);
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

	
	protected abstract void createMenuContainer ();

	
		
	
	@Override
	public  void callEditEvent() {
		callAfterEditEvent();
	}
	
	@Override
	public  void callViewEvent() {
		
	}
	
	/*Edit Events*/
	 @Override
	 public void addEditEvent (IDataEditHandler<E> handler) {
		 _editHandler = handler; 
	 }
	 
	 @Override
	 public void addViewEvent (IDataEditHandler<E> handler) {
		 _viewHandler = handler; 
	 }
	 
	 @Override
	 public void addInsertEvent (IDataEditHandler<E> handler) {
		 _insertHandler = handler; 
	 }
	 

	@Override
	public void addDeleteEvent (IDataEditHandler<E> handler) {
		_deleteHandler = handler;
	}
	
	public void addAfterDeleteEvent (ICallbackEvent<Void> event) {
		 _afterDeleteEvent =event;		
	}
	
	
	@Override
	public  void callInsertEvent() {
		callAfterInsertEvent();
	}
	

	
	/**
	 * 
	 */
	public void callAfterDeleteEvent() {
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
	
	/*
	protected void removeRecord(Record rec) {
		getMainDataSource().getDataSource().removeData(rec);
	}	
	*/
	
	@Override
	public void showFilterView() {
		
	}
	
	protected abstract void clearEvents (IDataView<E> view ) ;
	

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
	
	@Override
	public Params getParams() {
		return _params;
	}
		
	@Override
	public boolean isHaseChanges() {
		IBaseDataSource ds = getMainDataSource();
		if (ds !=null) {
			return ds.isHasChanges();
		}
		else return false;
	}
	
	@Override
	public void loseChange() {
		IBaseDataSource ds = getMainDataSource();
		if (ds !=null && ds.getRequestEntity()!= null ) {
			ds.getRequestEntity().loseChanges();		
		}
	}

	@Override
	public void IsCanClose(final BooleanCallback callback) {
		if ( isHaseChanges() ) {
		  
		     
			Dialogs.AskDialog2( Captions.Q_SAVE_CHANGES, new BooleanCallback() {
				
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
			});
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
	@Override
	public boolean isAutoSave() {
		return _isAutoSaveAfterEdit;
	}

	/**
	 * @param _isAutoSaveAfterEdit the _isAutoSaveAfterEdit to set
	 */
	@Override
	public void setAutoSave(boolean value) {
		this._isAutoSaveAfterEdit = value;
	}
	
}