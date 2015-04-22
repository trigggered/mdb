package mdb.core.ui.client.view;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import mdb.core.ui.client.app.AppController;
import mdb.core.ui.client.view.components.menu.AuthorizeMenu;
import mdb.core.ui.client.view.components.menu.IMenu;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuItemFactory;
import mdb.core.ui.client.view.components.menu.impl.smart.ToolStripMenuItemFactory;
import mdb.core.ui.client.view.components.menu.mdb.IMdbMainMenuAction;
import mdb.core.ui.client.view.components.menu.mdb.MdbMainMenu;
import mdb.core.ui.client.view.components.menu.mdb.MdbMainMenuCommand;
import mdb.core.ui.client.view.data.DataView;
import mdb.core.ui.client.view.debug.DebugMenu;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TabBarControls;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;



public abstract class AMainView extends DataView  implements  IMainView  {
	

    private static final Logger _logger = Logger.getLogger(AMainView.class.getName());
    
    private TabSet _mainTabSet;    
    private Menu _tabContextMenu;
    private HashMap<String,IView> _mapView = new HashMap<String, IView>();
    
    public AMainView() {
    	super(EViewPanelType.HLayout);
    }
    
   
    protected abstract void registerGatewayQueue();
  
    protected void setupEvents() {   
    	
    	AppController.setInstance(new AppController());
		AppController.getInstance().setMainView(this);		
		
		History.addValueChangeHandler(AppController.getInstance().getValueChangeHandler());				
		History.fireCurrentHistoryState();
		
    }
  
    public void onModuleLoad() {    

    	_logger.info("Start Load module Main");
    	
    	
//    	 _stockStore = Storage.getLocalStorageIfSupported();    	 
//    	 _stockStore.setItem("Ket", "sdfdsf");	 
    	
    	registerGatewayQueue();
    	setupEvents();
    	
        this.setStyleName("tabSetContainer");
        setLayoutMargin(5);
        getViewPanel().setLayoutMargin(5);                    

        Layout paneContainerProperties = new Layout();
        paneContainerProperties.setLayoutMargin(0);
        paneContainerProperties.setLayoutTopMargin(1);
        
        _mainTabSet = new TabSet();
        _mainTabSet.setPaneContainerProperties(paneContainerProperties);
        _mainTabSet.setWidth100();
        _mainTabSet.setHeight100();
        /*_mainTabSet.addCloseClickHandler(new CloseClickHandler() {
			
			@Override
			public void onCloseClick(TabCloseClickEvent event) {
				
				String tabId = event.getTab().getID();
				if (_mapView.containsKey(tabId) )  {
					IView view =_mapView.get(tabId);
					if (view!= null) {
						event.cancel();		
						view.close();
					}														
				}
			}
		});
        */
        
        LayoutSpacer layoutSpacer = new LayoutSpacer();
        layoutSpacer.setWidth(5);    
        _mainTabSet.setTabBarControls(TabBarControls.TAB_SCROLLER, TabBarControls.TAB_PICKER, layoutSpacer, getSkinControlComponent());        
       
        
        getViewPanel().addMember(_mainTabSet);


        if (SC.hasFirebug()) {
            Label label = new Label();
            label.setWidth100();
            label.setHeight(50);
            label.setValign(VerticalAlignment.CENTER);
            label.setAlign(Alignment.CENTER);
            label.setContents("Firebug can make the Showcase run slow. For the best performance, we suggest disabling Firebug for this site.");

            Window fbWindow = new Window();
            fbWindow.setTitle("Firebug Detected");
            fbWindow.setWidth100();
            fbWindow.setHeight(80);
            fbWindow.addItem(label);
            fbWindow.setRedrawOnResize(true);
            this.addMember(fbWindow);
        }

        this.draw();
        
        RootPanel p = RootPanel.get("loadingWrapper");
        if (p != null) RootPanel.getBodyElement().removeChild(p.getElement());                    
        
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
    
    protected DynamicForm  getSkinControlComponent() {
        SelectItem selectItem = new SelectItem();
        selectItem.setHeight(21);
        selectItem.setWidth(130);    


        selectItem.setValueMap(getSkinMapValues());

        final String skinCookieName = "skin_name_2_4";
        String currentSkin = Cookies.getCookie(skinCookieName);
        
        if (currentSkin == null) {
            currentSkin = getCurrentSkinName();
        }
        selectItem.setDefaultValue(currentSkin);
        selectItem.setShowTitle(false);
        selectItem.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent event) {
                Cookies.setCookie(skinCookieName, (String) event.getValue());
                com.google.gwt.user.client.Window.Location.reload();
            }
        });        
        
        DynamicForm form = new DynamicForm();
        form.setPadding(0);
        form.setMargin(0);
        form.setCellPadding(1);
        form.setNumCols(1);
        form.setFields(selectItem);        
        return form;
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
    }

	
    @Override
    public void openViewInTab (IView view) {
    	if(view == null) {
    		return;
    	}
    	
    	if ( view.isSingleInstance())  {
    		Tab existTab = checkExistView(view);
    		if ( existTab != null) {
    			
    			String tabId = existTab.getID(); 
    			if (_mapView.containsKey(tabId) )  {
					IView existView =_mapView.get(tabId);
					if (existView!= null) {
						existView.redraw();
					}
    			}
    			_mainTabSet.selectTab(existTab);
    			view = null;
    			return;
    		}
    	}
    	
    	view.initialize();
    	view.setMainView(this);
    	Tab tab = new Tab();
        tab.setContextMenu(getTabContextMenu());        
        tab.setTitle(view.getCaption());        
        tab.setPane(view.getCanvas() );
        
        _mapView.put(tab.getID(), view);
        //tab.setAttribute(property, value);
        tab.setCanClose(true);
        //view.setWindow()
        _logger.info("Tab icon ="+view.getImgCaption());
        if ( view.getImgCaption()!=null && !view.getImgCaption().isEmpty() ) {
        	tab.setIcon(view.getImgCaption());
        	
        }
        view.setOwnerWindow(tab);
        _mainTabSet.addTab(tab);
        _logger.info("Tab icon ="+tab.getIcon());
        _mainTabSet.selectTab(tab);        
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
