/**
 * 
 */
package mdb.core.ui.smartgwt.client.components.menu;

import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuItem;

import com.smartgwt.client.widgets.Canvas;

/**
 * @author azhuk
 * Creation date: Dec 25, 2012
 *
 */
public interface IMenuItemFactory {
  public Canvas create (IMenuItem item);
  public Canvas createAndRefSubMenu(IMenuItem item);

  
}
