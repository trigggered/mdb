/**
 * 
 */
package mdb.core.ui.client.view.components.menu;

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
