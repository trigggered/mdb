/**
 * 
 */
package mdb.core.vaadin.ui.components.menu;

import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuFactory;
import mdb.core.ui.client.view.components.menu.IMenuContainer.EContaynerOriented;

/**
 * @author azhuk
 * Creation date: Dec 25, 2012
 *
 */
public class MenuBarFactory implements IMenuFactory {

	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.components.menu.IMenuItemFactory#Create(mdb.ui.client.common.view.components.menu.IMenuItem)
	 */
	
	static IMenuFactory _factory; 
	
	public static IMenuFactory instance() {
		if (_factory == null) {
			_factory = new MenuBarFactory();
		}
		return _factory;
	}	
	
	
	
	
	@Override
	public IMenuContainer createMenuContainer() {
		return new MenuBarContainer();
	}




	/* (non-Javadoc)
	 * @see mdb.core.vaadin.ui.components.menu.IMenuFactory#createMenuContainer(mdb.core.ui.client.view.components.menu.IMenuContainer.EContaynerOriented)
	 */
	@Override
	public IMenuContainer createMenuContainer(
			EContaynerOriented contaynerOriented) {
		
		return  new MenuBarContainer(contaynerOriented);
	}
	
}
