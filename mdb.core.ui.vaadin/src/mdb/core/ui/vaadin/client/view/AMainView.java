package mdb.core.ui.vaadin.client.view;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import mdb.core.ui.client.app.AppController;
import mdb.core.ui.client.view.BaseView.EViewPanelType;
import mdb.core.ui.client.view.IMainView;
import mdb.core.ui.client.view.IView;
import mdb.core.ui.client.view.components.menu.AuthorizeMenu;
import mdb.core.ui.client.view.components.menu.IMenu;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuItemFactory;
import mdb.core.ui.client.view.components.menu.Menu;
import mdb.core.ui.client.view.components.menu.impl.smart.ToolStripMenuItemFactory;
import mdb.core.ui.client.view.components.menu.mdb.IMdbMainMenuAction;
import mdb.core.ui.client.view.components.menu.mdb.MdbMainMenu;
import mdb.core.ui.client.view.components.menu.mdb.MdbMainMenuCommand;
import mdb.core.ui.client.view.data.DataView;
import mdb.core.ui.client.view.data.IDataView;
import mdb.core.ui.client.view.debug.DebugMenu;

import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;



public abstract class AMainView extends UI  implements  IMainView, IDataView {
	

    private static final Logger _logger = Logger.getLogger(AMainView.class.getName());
    
    private TabSheet _mainTabSet;    
    private mdb.core.ui.client.view.components.menu.Menu _tabContextMenu;
    private HashMap<String,IView> _mapView = new HashMap<String, IView>();
    
    public AMainView() {
    	
    }
    
   
    protected abstract void registerGatewayQueue();
  
    protected void setupEvents() {   
    	
    	AppController.setInstance(new AppController());
		AppController.getInstance().setMainView(this);		
		
	
		
    }
  
    @Override
	protected void init(VaadinRequest request) {    

    	_logger.info("Start Load module Main");
    	
    	
//    	 _stockStore = Storage.getLocalStorageIfSupported();    	 
//    	 _stockStore.setItem("Ket", "sdfdsf");	 
    	
    	registerGatewayQueue();
    	setupEvents();
    	
        this.setStyleName("tabSetContainer");
        
                            

        

        _mainTabSet = new TabSheet();
        _mainTabSet.setSizeFull();

        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
		layout.setMargin(true);
		setContent(layout);
		
		layout.addComponent(_mainTabSet);
        
        _logger.info("Sucsses Loaded module Main");
    }    
    
    protected LinkedHashMap<String, String>  getSkinMapValues() {
    	
    	LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();        
        valueMap.put("EnterpriseBlue", "Enterprise Blue");
        valueMap.put("Enterprise", "Enterprise Gray");
        valueMap.put("Graphite", "Graphite");
        valueMap.put("Simplicity", "Simplicity");
        return valueMap;
    }
 
    
    protected String  getCurrentSkinName() {
    	return "Enterprise";
    }
    
	
    private Menu getTabContextMenu() {
    	if (_tabContextMenu == null) {
    		_tabContextMenu = createTabContextMenu();
    	}
    	return _tabContextMenu;
    }
    
    private Menu createTabContextMenu() {
    	return null;
    	/*
        Menu menu = new Menu();
        menu.setWidth(140);

        MenuItemIfFunction enableCondition = new MenuItemIfFunction() {
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                int selectedTab = _mainTabSet.getSelectedTabNumber();
                return selectedTab != 0;
            }
        };

        MenuItem closeItem = new MenuItem("<u>C</u>lose");
        closeItem.setEnableIfCondition(enableCondition);
        closeItem.setKeyTitle("Alt+C");
        KeyIdentifier closeKey = new KeyIdentifier();
        closeKey.setAltKey(true);
        closeKey.setKeyName("C");
        closeItem.setKeys(closeKey);
        closeItem.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent event) {
            	closeCurrentTab();                
            }
        });

        MenuItem closeAllButCurrent = new MenuItem("Close All But Current");
        closeAllButCurrent.setEnableIfCondition(enableCondition);
        closeAllButCurrent.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent event) {
            	closeAllTabButCurrent();
            }
        });

        MenuItem closeAll = new MenuItem("Close All");
        closeAll.setEnableIfCondition(enableCondition);
        closeAll.addClickHandler(new ClickHandler() {
        
        	
            public void onClick(MenuItemClickEvent event) {
            	closeAllTab();                
            }
        });

        menu.setItems(closeItem, closeAllButCurrent, closeAll);
        return menu;
        */
    }

	
    @Override
    public void openViewInTab (IView view) {
    	if(view == null) {
    		return;
    	}
    	
    	if ( view.isSingleInstance())  {
    		Tab existTab = checkExistView(view);
    		if ( existTab != null) {
    			
    			
    			String tabId = existTab.getId(); 
    			if (_mapView.containsKey(tabId) )  {
					IView existView =_mapView.get(tabId);
					if (existView!= null) {
						existView.redraw();
					}
    			}
    			_mainTabSet.setSelectedTab(existTab);
    			view = null;
    			return;
    		}
    	}
    	
    	view.initialize();
    	view.setMainView(this);
    	Tab tab = _mainTabSet.addTab(null, view.getCaption());
    	tab.setId( String.valueOf(_mainTabSet.getTabPosition(tab)) );
    	tab.setClosable(true);
    	
    	//tab.setContextMenu(getTabContextMenu());        
        
        
        _mapView.put(tab.getId(), view);
        //tab.setAttribute(property, value);
        
        //view.setWindow()
        _logger.info("Tab icon ="+view.getImgCaption());
        if ( view.getImgCaption()!=null && !view.getImgCaption().isEmpty() ) {        
        	tab.setIcon(new ThemeResource(view.getImgCaption()));        	
        	
        }
        view.setOwnerWindow(tab);
        _logger.info("Tab icon ="+tab.getIcon());
        _mainTabSet.setSelectedTab(tab);        
    }
	
    @Override
	public String getCaption() {
		return "Main";
	}        
    
	/* (non-Javadoc)
	 * @see mdb.ui.client.view.common.IDataView#view()
	 */
	@Override
	public void bindDataComponents() {
		
		 _logger.info("Start bin data to view components");
		 getMenuContainer().bind(new AuthorizeMenu(AppController.getInstance().getCurrentUser().getName()));
		 if (AppController.getInstance().isDebugMode()) {				
				 getMenuContainer().bind(new DebugMenu());
		 }	
		 
		 _logger.info("End bin data to view components");
	}			

	
	@Override
	protected IMenuItemFactory getMenuFactory() {
		//return new RibbonMenuItemFactory();
		return new ToolStripMenuItemFactory();
	}
	
	

	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.data.IDataView#getSelectedRecord()
	 */
	@Override
	public Record getSelectedRecord() {		
		return null;
	} 
		

	@Override
	public Record[] getSelectedRecords() {		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.view.data.IDataView#isSelectedRecord()
	 */
	@Override
	public boolean isSelectedRecord() {		
		return getSelectedRecord() != null;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.view.data.IDataView#showFilterView()
	 */
	@Override
	public void showFilterView() {
		// TODO Auto-generated method stub		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.view.data.IDataView#removeData()
	 */
	@Override
	public void removeData() {
		// TODO Auto-generated method stub		
	}


	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.BaseView#createMenu()
	 */
	@Override
	protected void createMenu() {		
		IMenuContainer container =  getMenuContainer();				
		if (container != null) {
			loadTopBarLogo();
			registerDynamicMenu();			
		}	
	}

	protected void registerDynamicMenu() {
		IMenu mainMenu = new MdbMainMenu(getAppId(), getMenuContainer(), new MdbMainMenuCommand(this, getMdbMainMenuActionImpl() ));
		mainMenu.setPosition(0);
	}

	@Override
	public void closeCurrentTab() {
		int selectedTab = _mainTabSet.getSelectedTabNumber();
		_mapView.remove(_mainTabSet.getSelectedTab().getID());
		
        _mainTabSet.removeTab(selectedTab);
        _mainTabSet.selectTab(selectedTab - 1);
	}
	
	@Override
	public void closeAllTab() {

		Tab[] tabs = _mainTabSet.getTabs();
		
        int[] tabsToRemove = new int[tabs.length-1];
        int cnt = 0;
        for (int i = 1; i < tabs.length; i++) {            
                tabsToRemove[cnt] = i;
                cnt++;            
        }
        _mainTabSet.removeTabs(tabsToRemove);
        
	}
	
	//@Override
	public Tab  checkExistView(IView viewForOpen) {		
		for (Tab tab : _mainTabSet.getTabs()) {			
			if ( tab.getTitle().equals( viewForOpen.getCaption() ) ) {			 			 
				return tab;				
			}
		}		             
		return null;
	}
	
	@Override
	public void closeAllTabButCurrent() {
		int selected = _mainTabSet.getSelectedTabNumber();
        Tab[] tabs = _mainTabSet.getTabs();
        int[] tabsToRemove = new int[tabs.length - 2];
        int cnt = 0;
        for (int i = 1; i < tabs.length; i++) {
            if (i != selected) {
                tabsToRemove[cnt] = i;
                cnt++;
            }
        }
        
        _mainTabSet.removeTabs(tabsToRemove);		
	}
	
	
	protected abstract void loadTopBarLogo();
	protected abstract IMdbMainMenuAction getMdbMainMenuActionImpl();
	
}
