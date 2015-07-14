package mdb.core.ui.vaadin.client.view;

import java.util.HashMap;

import mdb.core.ui.client.app.AppController;
import mdb.core.ui.client.view.IMainView;
import mdb.core.ui.client.view.IOwnerWnd;
import mdb.core.ui.client.view.IView;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.util.BooleanCallback;







import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;

public abstract class BaseView extends VerticalLayout implements IView {
	
	public enum EViewPanelType {
		HLayout,
		VLayout
	}

	private boolean _isSingleInstance = false;
	private IMainView _mainView;
	protected EViewPanelType _viewPanelType;
    private IMenuContainer _menuContainer;
    private Layout _viewPanel;
    private boolean _componentsCreated =false;
    private String _title;
    private HashMap<String,Object> _property = new HashMap<String, Object>();
	private String _imgDescription;
	private IOwnerWnd _ownerWindow;
	private boolean _isCanEdit = true;
	private String _containerID;    
	private boolean _isCreateMenuContainer =true;
 
    public BaseView() {
    	_viewPanelType = EViewPanelType.HLayout;
    	createComponents();    	
    }
    
    public BaseView(EViewPanelType viewPanelType) {
    	_viewPanelType = viewPanelType;
    	createComponents();    	
    }
    
    protected void createComponents () {    	
    	setWidth("100%");
        setHeight("100%");    
        
        if (isCreateMenuContainer()) {
	        _menuContainer = createMenuContainer();
	        if (_menuContainer!=null) {
	        	
	        	addComponent(_menuContainer.getLayout() );
	        	
	        	createMenu ();
	        }
        
        }
        
        _viewPanel = createViewPanel();        
        if (_viewPanel != null) {
        
        	
        	addComponent( _viewPanel );
        }
        _componentsCreated = true;
    }       
    
    

	public boolean isCreateMenuContainer() {
		return _isCreateMenuContainer;
	}

	public void setCreateMenuContainer(boolean value) {
		_isCreateMenuContainer =value;
	}
	
	protected boolean isComponentsCreated() {
    	return _componentsCreated;
    }
    
    protected Layout getViewPanel() {
    	return _viewPanel;	
    }
    
	protected IMenuContainer createMenuContainer () {		
		return null;
	}

	protected void createMenu () {		
	}


    protected Layout createViewPanel() {
    	Layout mainPanel;
    	switch (_viewPanelType) {
    	case HLayout: 
    		mainPanel = new HorizontalLayout();
    		break;
    	case VLayout:
    		mainPanel = new VerticalLayout();
    		break;
    	default :
    			mainPanel = new HorizontalLayout();    			
    	}
    				    
    	mainPanel.setHeight("100%");
    	mainPanel.setWidth("100%");
		return mainPanel;
    }

    

    public IMenuContainer getMenuContainer() {    	
    	return _menuContainer;
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
	public Canvas getCanvas() {
		return this;
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
