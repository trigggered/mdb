/**
 * 
 */
package mdb.core.ui.client.view.components.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mdb.core.shared.utils.comparator.ComparatorByPosition;
import mdb.core.ui.client.view.components.AVisualComponent;

/**
 * @author azhuk
 * Creation date: Dec 20, 2012
 *
 */
public  class Menu extends AVisualComponent implements IMenu {

	private List<IMenuItem>  _items = new ArrayList<IMenuItem>();
	private IMenuContainer _menuContainer;	
	private int _position=0;
	
	public Menu(String name) {				
		setShowCaption(true);
		setShowHint(false);
		setName(name);		
	}
	
		
	public IMenuItem addItem (String title, String icon, IMenuItem.ItemType itemType, int position) {
		IMenuItem item = addItem (itemType, position);		
		item.setCaption(title);		
		item.setHint(title);
		item.setImg(icon);
		item.setShowCaption(isShowCaption());
		item.setMenu(this);
		return item;
	}
	

	
	public IMenuItem addItem (IMenuItem item) {
		_items.add(item);
		
		item.setMenu(this);
		//item.setPosition(_items.size());
		return item;
	}
	
	public IMenuItem addItem (IMenuItem.ItemType itemType) {
		IMenuItem item = createMenuItem ( itemType);
		addItem(item);
		return item;
	}
	
	public IMenuItem addItem (IMenuItem.ItemType itemType, int position) {
		IMenuItem item = createMenuItem ( itemType);
		item.setPosition(position);
		addItem(item);
		return item;
	}
	

	public static IMenuItem createMenuItem (IMenuItem.ItemType itemType) {
		return new MenuItem (itemType);		
	}
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.view.common.components.menu.IMenu#getItems()
	 */
	@Override
	public List<IMenuItem> getItems() {
		return _items;
	}

	@Override
	public List<IMenuItem> getSortedItems() {
		Collections.sort(_items,new ComparatorByPosition());			
		
		return _items;
	}
	
	
	@Override
	public void addItems(List<IMenuItem> values) {
		_items.addAll(values);
	}
		

	public void addSpacer (int space) {
		mdb.core.ui.client.view.components.menu.MenuItem item = 
				new mdb.core.ui.client.view.components.menu.MenuItem (this, IMenuItem.ItemType.Spacer);
		
		item.setAttribute("ExtraSpace", String.valueOf(space));
		
		_items.add(item);
		
	}
	
	public void addFill() {
		addItem(IMenuItem.ItemType.Fill);	
	}
	
	public void addSeparator() {
		_items.add( new mdb.core.ui.client.view.components.menu.MenuItem (this, IMenuItem.ItemType.Separator) );	
	}


	@Override
	public IMenuContainer getContainer() {
		return _menuContainer;
	}


	@Override
	public void setContainer(IMenuContainer menuContainer) {
		_menuContainer = menuContainer;
	}
	
	
	@Override
	public void setShowCaption(boolean value) {
		super.setShowCaption(value);
		for (IMenuItem item :  getItems() ) {
			item.setShowCaption(value);
		}
	}
	
	@Override
	public void setShowHint(boolean value) {
		super.setShowHint(value);
		for (IMenuItem item :  getItems() ) {
			item.setShowHint(value);
		}
	}


	/* (non-Javadoc)
	 * @see mdb.core.shared.utils.comparator.IPosition#getPosition()
	 */
	@Override
	public int getPosition() {
		return _position;
	}


	/* (non-Javadoc)
	 * @see mdb.core.shared.utils.comparator.IPosition#setPosition(int)
	 */
	@Override
	public void setPosition(int value) {
		_position=value;
		
	}
	
}
