package mdb.core.ui.client.view;

import java.util.HashMap;

import mdb.core.ui.client.app.AppController;
import mdb.core.ui.client.view.components.menu.IMenuContainer;

import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public abstract class BaseView extends VLayout implements IView {
	
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
	private Tab _ownerWindow;
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
    	setWidth100();
        setHeight100();    
        setLayoutMargin(5);
        
        if (isCreateMenuContainer()) {
	        _menuContainer = createMenuContainer();
	        if (_menuContainer!=null) {
	        	addMember( _menuContainer.getLayout() );
	        	createMenu ();
	        }
        
        }
        _viewPanel = createViewPanel();        
        if (_viewPanel != null) {
        	addMember( _viewPanel );
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
    		mainPanel = new HLayout();
    		break;
    	case VLayout:
    		mainPanel = new VLayout();
    		break;
    	default :
    			mainPanel = new HLayout();    			
    	}
    				    
    	mainPanel.setHeight100();
    	mainPanel.setWidth100();
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
	public void setOwnerWindow(Tab tab) {
		_ownerWindow = tab;		
	}
	
	@Override
	public Tab getOwnerWindow()  {
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
		Canvas.showPrintPreview(getCanvas());		
	}
}
