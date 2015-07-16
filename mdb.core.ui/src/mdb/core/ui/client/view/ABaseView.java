package mdb.core.ui.client.view;

import java.util.HashMap;

import mdb.core.ui.client.app.AppController;
import mdb.core.ui.client.view.IView.EViewShowType;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuFactory;
import mdb.core.ui.client.util.BooleanCallback;


public abstract class ABaseView implements IView {
	
	

	private boolean _isSingleInstance = false;
	private IMainView _mainView;
	protected EViewPanelType _viewPanelType;
    private IMenuContainer _menuContainer;    
    
    
    private String _title;
    private HashMap<String,Object> _property = new HashMap<String, Object>();
	private String _imgDescription;
	private IOwnerWnd _ownerWindow;
	private boolean _isCanEdit = true;
	private String _containerID;    
	private boolean _isCreateMenuContainer =true;
 
	
    public ABaseView() {
    	_viewPanelType = EViewPanelType.HLayout;
    	createComponents();    	
    }
    
    public ABaseView(EViewPanelType viewPanelType) {
    	_viewPanelType = viewPanelType;
    	createComponents();    	
    }
    
    public EViewShowType  getViewShowType()  {
    	return EViewShowType.TabWindow;
    }
    
	public void show() {
		
	}
	
    protected abstract void initSize();
    
    protected void createComponents () {   	
    	createMainLayout();
        
        if (isCreateMenuContainer()) {        	
	        createMenuContainer();        	        		        	
	        createMenu ();	                
        }        
                
        initSize();       
        
    }           
    

	public boolean isCreateMenuContainer() {
		return _isCreateMenuContainer;
	}

	public void setCreateMenuContainer(boolean value) {
		_isCreateMenuContainer =value;
	}   

    
	protected abstract void createMenuContainer ();		
	
	protected abstract IMenuFactory getMenuFactory ();		
	

	protected void createMenu () {		
	}


    protected abstract void createMainLayout();   
    

    public IMenuContainer getMenuContainer() {    	
    	return _menuContainer;
    }
    
    public void setMenuContainer(IMenuContainer  container) {    	
    	_menuContainer = container;
    	if (_menuContainer !=null) {
    		_menuContainer.addToView(this);
    	}
    }
    
    @Override
    public Object getCustomProperty(String name) {		
		return _property.containsKey(name)?_property.get(name):null; 
	}
	
    @Override
	public void  addCustomProperty (String name, Object value) {
		_property.put(name, value);
	}
	
	@Override
	public void initialize() {
		
	}
	
	public void setMainView (IMainView mainView) {
		_mainView = mainView;
	}
	
	public IMainView getMainView () {
		return _mainView;		
	}
	
	@Override
	public String getCaption() {
		return _title;
	}
	
	@Override
	public  String getImgCaption() {
		return _imgDescription;
	}
	
	@Override
	public  void setImgCaption(String value) {
		_imgDescription = value;
	}	
	
	@Override
	public void  setCaption(String value) {
		_title = value;
	}
	
	
	@Override
	public void setOwnerWindow(IOwnerWnd ownerWnd) {
		_ownerWindow = ownerWnd;		
	}
	
	@Override
	public IOwnerWnd getOwnerWindow()  {		
		return _ownerWindow;		
	}
	
	@Override
	public void setSingleInstance (boolean value) {
		_isSingleInstance = value;
	}
	
	@Override
	public boolean  isSingleInstance()  {
		return _isSingleInstance;
	}
	
	@Override
	public void IsCanClose(BooleanCallback callback) {
		if (callback != null) {
			callback.execute(true);
		}		
	}
		
	public void close() {
		
		IsCanClose(new BooleanCallback() {			
			@Override
			public void execute(Boolean value) {
				if (value) {
					AppController.getInstance().getMainView().closeCurrentTab();
				}
				
			}
		});				
	}
	
	@Override
	public void redraw() {
		
	}
	
	@Override
	public void setCanEdit(boolean value)  {
		_isCanEdit = value;		
	}
	
	@Override
	public boolean isCanEdit() {		
		return _isCanEdit;
	}
	
	public void setViewContainerID(String id) {
		 _containerID = id;
	}
	
	public String getViewContainerID( ) {
		return _containerID;
	}
	
	@Override
	public void print() {			
				
	}
}
