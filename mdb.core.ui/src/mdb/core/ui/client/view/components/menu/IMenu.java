/**
 * 
 */
package mdb.core.ui.client.view.components.menu;

import java.util.List;

import mdb.core.shared.utils.comparator.IPosition;
import mdb.core.ui.client.view.components.IVisualComponent;


/**
 * @author azhuk
 * Creation date: Dec 12, 2012
 *
 */
public interface IMenu extends IVisualComponent, IPosition {
	
	public IMenuItem addItem (String title, String icon, IMenuItem.ItemType itemType, int position);
	public IMenuItem addItem (IMenuItem.ItemType itemType);	
	public IMenuItem addItem (IMenuItem iteem);
	
	public List<IMenuItem> getItems ();
	public void addItems(List<IMenuItem> values);
	
	public void setContainer (IMenuContainer menuContainer);
	public IMenuContainer getContainer();
	
	public List<IMenuItem> getSortedItems();	
}
