/**
 * 
 */
package mdb.core.vaadin.ui.view.data;

import mdb.core.ui.client.data.bind.IDataBinder;
import mdb.core.ui.client.view.components.menu.IMenuFactory;
import mdb.core.ui.client.view.data.ADataView;
import mdb.core.vaadin.ui.components.menu.MenuBarFactory;
import mdb.core.vaadin.ui.data.bind.MdbViewDataBinder;
import mdb.core.vaadin.ui.view.IVaadinComponnet;

import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;


/**
 * @author azhuk
 * Creation date: Oct 16, 2012
 *
 */
public abstract class DataView extends ADataView<Object> implements IVaadinComponnet{

	
	private Layout _mainLayout;
	
	   
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.ADataView#createDataBinder()
	 */
	@Override
	protected IDataBinder createDataBinder() {
		return new MdbViewDataBinder(this);
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.ABaseView#createViewPanel()
	 */
	@Override
	protected void createMainLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		setMainLayout(layout);		
	}
	
	@Override
	protected void createMenuContainer() {				
		setMenuContainer(getMenuFactory().createMenuContainer());
		getMenuContainer().setView(this);
		getMainLayout().addComponent(((IVaadinComponnet)getMenuContainer()).getMainLayout());
		((VerticalLayout)getMainLayout()).setExpandRatio(((IVaadinComponnet)getMenuContainer()).getMainLayout(), 0.05f);		
	}
	
	protected IMenuFactory  getMenuFactory() {
		return MenuBarFactory.instance();
	}
	
	@Override
	public Layout getMainLayout() {
		   return _mainLayout;
	}
	
	@Override
	 public void 	setMainLayout(Layout value) {
		   _mainLayout = value;
	 }
		
	
}