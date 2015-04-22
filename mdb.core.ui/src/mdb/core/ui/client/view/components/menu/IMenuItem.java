/**
 * 
 */
package mdb.core.ui.client.view.components.menu;

import java.util.LinkedHashMap;

import mdb.core.shared.utils.comparator.IPosition;
import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.events.IHasChangeHandlers;
import mdb.core.ui.client.view.components.IVisualComponent;


/**
 * @author azhuk
 * Creation date: Dec 20, 2012
 *
 */
public interface IMenuItem extends IPosition, IVisualComponent, IHasChangeHandlers {
	
	public enum ItemType {
		Separator,
		Menu,
		MenuItem,
		ToolButton,
		Combobox,	
		NumberItem,
		Label,
		Spacer,
		Fill,
		DateItem,
		DateRange, 
		ButtonDataItem
	}
	
	public ItemType getItemType();
	public void  setItemType(ItemType value);
	
	public ICommand<IMenuItem>  getComand ();
	public void  setCommand(ICommand<IMenuItem> command);				
	
	public int  getPosition();
	public void setPosition (int position);			
	
	public IMenu getChildMenu();	
	public void setChildMenu(IMenu childMenu);
	
	
	public Object getValue();
	public void setValue(Object value);
	
	public LinkedHashMap<String, Object> getValueMap();
	public void setValueMap(LinkedHashMap<String,Object>  value);
	/**
	 * @param memberNumber
	 */
	public void setMemberNumber(int memberNumber);
	public int getMemberNumber();
	public boolean isAlreadyBound();
	public IMenu getMenu();
	public void setMenu(IMenu menu);
}
