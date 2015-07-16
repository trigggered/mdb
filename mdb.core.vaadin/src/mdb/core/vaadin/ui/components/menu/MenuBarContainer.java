/**
 * 
 */
package mdb.core.vaadin.ui.components.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import mdb.core.ui.client.view.IView;
import mdb.core.ui.client.view.components.menu.IMenu;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuFactory;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.data.IDataView;
import mdb.core.vaadin.ui.view.IVaadinComponnet;

import com.vaadin.server.Responsive;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;


/**
 * @author azhuk
 * Creation date: Dec 20, 2012
 *
 */

public class MenuBarContainer implements IMenuContainer, IVaadinComponnet{	

	
	private static final Logger _logger = Logger.getLogger(MenuBarContainer.class.getName());
	
	private Layout _mainLayout;
	private Component _container;
	
	
	private List<String> _boundMenuNames = new ArrayList<String>();

	private IDataView _view;			
	
	EContaynerOriented _contaynerOriented = EContaynerOriented.Horizontal;
	
	public MenuBarContainer(EContaynerOriented contaynerOriented) {
		_contaynerOriented = contaynerOriented;
		createMainLayout();
		createContainer();			
	}
	
	public MenuBarContainer() {
		createMainLayout();
		createContainer();			
	}
	
	
	public MenuBarContainer(IMenuFactory factory) {
		createContainer();			
	}
	
	protected void createMainLayout() {
		
		AbstractOrderedLayout header = null;
		
		switch (_contaynerOriented ) {
		case Vertical:
			header = new VerticalLayout();
			break;
		case Horizontal:
			header = new HorizontalLayout();
			break;
		}
		
		//HorizontalLayout header = new HorizontalLayout();
		setMainLayout(header);		
		header.setSpacing(false);
		header.setSizeFull();
		Responsive.makeResponsive(header);

	}
	

	protected  void createContainer() {
		setContainer(new MenuBar());
		getContainer().addStyleName("borderless");
		getMainLayout().addComponent(getContainer());
		
		getContainer().setWidth("100%");
	}
	
	protected Component getContainer(){
		return _container;
	}
	
	protected void setContainer(Component value){
		 _container = value;
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.view.common.components.menu.IMenuContainer#bind(mdb.ui.client.view.common.components.menu.IMenu)
	 */
	@Override
	public void bind(IMenu menu) {
		bind(null, menu);
	}
	
	
	private  void bind(MenuItem parentItem, IMenu menu) {
		
			menu.setContainer(this);
			_boundMenuNames.add(menu.getName());
			_logger.info("Bind menu to container. Menu name:"+menu.getName());				
			
			List<IMenuItem> menuItems = menu.getSortedItems();
			
			for (IMenuItem item : menuItems) {
								
				MenuItem newItem = addItem(parentItem, item);																
				
				
				if (item.getChildMenu() != null) {
					bind(newItem, item.getChildMenu());
				}
			}			
	}
	
	
	 protected MenuItem addItem(MenuItem parentItem, final IMenuItem item) {
		 
		 _logger.info( "Create menu:"+ item.getCaption());
		 MenuBar menuBar = (MenuBar)getContainer();			 
		 
		 MenuItem newItem = null;		 
		 
		 switch (item.getItemType() ) {			
			case Menu: /*Parent Menu */
				if ( item.isHasParent() && parentItem != null)
					newItem = parentItem.addItem(item.getCaption(), null);					
				
				else {					
					newItem = menuBar.addItem(item.getCaption(), null);					
				}				
			
				break;
			case ToolButton:
			case MenuItem:			
				Command cmd = new Command() {					
					@Override
					public void menuSelected(MenuItem selectedItem) {
						if ( item.getCommand() != null) {
							item.getCommand().execute(item);	
						}						
					}
				};
				
				if ( item.isHasParent() && parentItem != null) {
					newItem = parentItem.addItem(item.getCaption(), cmd);
					
				}else  {
					newItem = menuBar.addItem(item.getCaption(), null, cmd);
				}
				break;	
				
			case Label:
				break;	
			case Separator:							
				break;				
			case Combobox:{							
				break;
			}			
			case DateItem:{				
				break;
			}
			case DateRange:{				
				break;
			}
			case NumberItem:{													
				break;				
			}		
			case ButtonDataItem:
				break;				
			default:
								
		}					 
		 item.setId(newItem.getId());	
		 return newItem;
	 }
	
	
	public void addMemberToContainer(Component component) {
		//getContainer(). add(canvas);				
	}
	

	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.components.menu.IMenuContainer#getView()
	 */
	@Override
	public IDataView getView() {		
		return _view;
	}


	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.components.menu.IMenuContainer#setView(mdb.ui.client.common.view.data.IDataView)
	 */
	@Override
	public void setView(IDataView value) {
		_view = value;		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.view.components.menu.IMenuContainer#isMenuExist(java.lang.String)
	 */
	@Override
	public boolean isMenuExist(IMenu menu) {
		return _boundMenuNames.contains(menu.getName());
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.components.menu.IMenuContainer#addToView(mdb.core.ui.client.view.IView)
	 */
	@Override
	public void addToView(IView view) {
		
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.view.ISmartGwtView#getViewPanel()
	 */
	@Override
	public Layout getMainLayout() {
		return _mainLayout;
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.view.ISmartGwtView#setViewPanel(com.smartgwt.client.widgets.layout.Layout)
	 */
	@Override
	public void setMainLayout(Layout value) {
		_mainLayout = value;		
	}
}
