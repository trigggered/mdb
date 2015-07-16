package mdb.core.vaadin.ui.view;


import java.util.HashMap;
import java.util.logging.Logger;

import mdb.core.ui.client.app.AppController;
import mdb.core.ui.client.communication.impl.GatewayQueue;
import mdb.core.ui.client.view.IMainView;
import mdb.core.ui.client.view.IOwnerWnd;
import mdb.core.ui.client.view.IView;
import mdb.core.ui.client.view.components.menu.IMenu;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuFactory;
import mdb.core.ui.client.view.components.menu.Menu;
import mdb.core.ui.client.view.components.menu.mdb.IMdbMainMenuAction;
import mdb.core.ui.client.view.components.menu.mdb.MdbMainMenu;
import mdb.core.ui.client.view.components.menu.mdb.MdbMainMenuCommand;
import mdb.core.ui.client.view.data.IDataView;
import mdb.core.vaadin.ui.components.menu.MainMenuBarFactory;
import mdb.core.vaadin.ui.data.VaadinDataWraper;
import mdb.core.vaadin.ui.view.data.DataView;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;



public abstract class AMainView extends  DataView  implements  IMainView  {
	    

	private static final long serialVersionUID = 1L;

	private static final Logger _logger = Logger.getLogger(AMainView.class.getName());
    
    private TabSheet _mainTabSet;    
    private mdb.core.ui.client.view.components.menu.Menu _tabContextMenu;
    private HashMap<String,IView> _mapView = new HashMap<String, IView>();
    
    public AMainView() {    	
    }
    
   
    protected abstract void registerGatewayQueue();
  
    protected void setupEvents() {       	
		AppController.getInstance().setMainView(this);	
    }
  
    
    @Override
	protected void createMainLayout() {
    	//AbsoluteLayout   layout= new AbsoluteLayout();
		VerticalLayout   layout= new VerticalLayout();
    	
		layout.addStyleName("view-content");
		layout.setSizeFull();
		
		
		//layout.setSpacing(false);
		layout.setMargin(true);
		
		layout.setResponsive(true);
		setMainLayout((Layout)layout);		
    }
	
		
    @Override
	protected void createComponents () {    
    	super.createComponents ();
    	_logger.info("Start Load module Main");
    	
    	
//    	 _stockStore = Storage.getLocalStorageIfSupported();    	 
//    	 _stockStore.setItem("Ket", "sdfdsf");	 
    	retrieveUserInfoFromServer();
    	registerGatewayQueue();
    	setupEvents();    	    	
    	
/*    	
    	createMenuContainer ();
    	createMenu();*/

        
        
    	VerticalLayout  mainLayout= (VerticalLayout)getMainLayout(); 
    	
        _mainTabSet = new TabSheet();        
        _mainTabSet.setSizeFull();

        Layout menu = ((IVaadinComponnet) getMenuContainer()).getMainLayout();

        mainLayout.addComponent( menu);
        mainLayout.setExpandRatio(menu, 0.1f);
        mainLayout.addComponent(_mainTabSet);
        mainLayout.setExpandRatio(_mainTabSet, 0.9f);
       
        callRequestData();
        _logger.info("Sucsses Loaded module Main");
    }    

    protected abstract IView getDefaultView();
    
    
    
    /**
	 * 
	 */
	protected abstract  void retrieveUserInfoFromServer() ;	
 
    
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

 
    public void openView(IView view) {
	   if (view  == null) return;
	   
	   switch ( view.getViewShowType() ) {
	   case TabWindow:
		   openViewInTab(view);
		   break;
	   case Dialog:
		   openDialog(view);
	   }
   }
    
	    /**
	 * @param view
	 */
	private void openDialog(IView view) {
		view.show();		
	}


	public void openViewInTab (IView view) {
    	if(view == null) {
    		return;
    	}
    	
    	if ( view.isSingleInstance())  {
    		String tabId = checkExistView(view);
    		if ( tabId != null) {
    			 			
    			
    			if (_mapView.containsKey(tabId) )  {
					IView existView =_mapView.get(tabId);
					if (existView!= null) {
						existView.redraw();
					}
    			}
    			
    			//_mainTabSet.setSelectedTab(existTab);
    			view = null;
    			return;
    		}
    	}
    	
    	view.initialize();
    	view.setMainView(this);
    	//Layout tabLay = new VerticalLayout();
    	//Layout tabLay = ;
    	final Tab  tab = _mainTabSet.addTab(((IVaadinComponnet)view).getMainLayout(), view.getCaption());
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
        view.setOwnerWindow(new IOwnerWnd() {
			
			@Override
			public void setCaption(String caption) {
				tab.setCaption(caption);				
			}
			
		});
        //view.setOwnerWindow(tab);
        //tabLay.addComponent( ((IVaadinComponnet)view).getMainLayout());
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
		/* getMenuContainer().bind(new AuthorizeMenu(AppController.getInstance().getCurrentUser().getName()));
		 if (AppController.getInstance().isDebugMode()) {				
				 getMenuContainer().bind(new DebugMenu());
		 }	
		 */
		 _logger.info("End bin data to view components");
	}			

	@Override
	protected IMenuFactory getMenuFactory() {
		return MainMenuBarFactory.instance();		
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

	
	protected  abstract void loadTopBarLogo();


	protected void registerDynamicMenu() {
		
		IMenu mainMenu = new MdbMainMenu(new VaadinDataWraper(),
				getAppId(), getMenuContainer(), new MdbMainMenuCommand(this, getMdbMainMenuAction() ));
		mainMenu.setPosition(0);
		
	}

	/**
	 * @return
	 */
	protected abstract  IMdbMainMenuAction getMdbMainMenuAction();


	
	
	@Override
	public void closeCurrentTab() {				
		_mapView.remove(_mainTabSet.getSelectedTab().getId());
		
        _mainTabSet.removeTab(_mainTabSet.getTab(_mainTabSet.getSelectedTab()));
        
	}
	
	@Override
	public void closeAllTab() {
		/*
		Tab[] tabs = _mainTabSet.getTabs();
		
        int[] tabsToRemove = new int[tabs.length-1];
        int cnt = 0;
        for (int i = 1; i < tabs.length; i++) {            
                tabsToRemove[cnt] = i;
                cnt++;            
        }
        _mainTabSet.removeTabs(tabsToRemove);
        */
        _mainTabSet.removeAllComponents();        
	}
	
	
	public String  checkExistView(IView viewForOpen) {
		
		for ( int i=0; i<_mainTabSet.getComponentCount(); i++ ) {
			Tab tab = _mainTabSet.getTab(i);
			if (tab.getCaption().equals( viewForOpen.getCaption() ) ) {			 			 
				return tab.getId();
			}
		}
		return null;				             		
	}
	
	@Override
	public void closeAllTabButCurrent() {
		
	}
	
		
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.IView#getMainView()
	 */
	@Override
	public IMainView getMainView() {
		return this;
	}

	

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataRequest#putRequestToQueue()
	 */
	@Override
	public void putRequestToQueue() {
		GatewayQueue.instance().put(getDataBinder().getDataProvider());
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataRequest#callRequestData()
	 */
	@Override
	public void callRequestData() {
		prepareRequestData();
		putRequestToQueue();
		GatewayQueue.instance().getProcessor().run();	
		
	}

    
    
	@Override
    protected void createMenuContainer () {
    	super.createMenuContainer(); //setMenuContainer(getMenuFactory().createMenuContainer(IMenuContainer.EContaynerOriented.Vertical));
    }


	
	
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.ADataView#clearEvents(mdb.core.ui.client.view.data.IDataView)
	 */
	@Override
	protected void clearEvents(IDataView<Object> view) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.ABaseView#initSize()
	 */
	@Override
	protected void initSize() {
		// TODO Auto-generated method stub
		
	}

	
}
