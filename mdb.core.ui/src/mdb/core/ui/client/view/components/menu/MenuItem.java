/**
 * 
 */
package mdb.core.ui.client.view.components.menu;

import java.util.LinkedHashMap;

import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.events.IChangeEvent;
import mdb.core.ui.client.events.IChangeHandler;
import mdb.core.ui.client.view.components.AVisualComponent;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

/**
 * @author azhuk
 * Creation date: Dec 20, 2012
 *
 */
public class MenuItem extends AVisualComponent implements IMenuItem, ClickHandler {
	
	private ItemType _itemType;
	private ICommand<IMenuItem> _command;	
	private int _positon;
	private IMenu _menu;
	private IMenu _childMenu;
	private Object _value;
	private LinkedHashMap<String,Object> _valueMap;
	private int _memberNumber =-1;
	
	private IChangeHandler _changeHandler;
	
	public MenuItem(IMenu menu, ItemType itemType) {
		setItemType(itemType);
		setMenu(menu);		
	}
	
	public MenuItem(ItemType itemType) {
		setItemType(itemType);
	}
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.view.common.components.menu.IMenuItem#getItemType()
	 */
	@Override
	public ItemType getItemType() {
		return _itemType;
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.components.menu.IMenuItem#setItemType(mdb.ui.client.common.view.components.menu.IMenuItem.ItemType)
	 */
	@Override
	public void setItemType(ItemType value) {
		_itemType = value;		
	}
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.view.common.components.menu.IMenuItem#setCommand(mdb.ui.client.view.command.ICommand)
	 */
	@Override
	public void setCommand(ICommand<IMenuItem> command) {
		_command = command;	
	}

	/* (non-Javadoc)
	 * @see mdb.ui.client.view.common.components.menu.IMenuItem#getComand()
	 */
	@Override
	public ICommand<IMenuItem> getComand() {
		return _command;
	}
	

	/* (non-Javadoc)
	 * @see com.smartgwt.client.widgets.events.ClickHandler#onClick(com.smartgwt.client.widgets.events.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		
		if (assignCommand() ) {
			_command.execute(this);
		}
	}

	protected Boolean assignCommand() {
		return _command != null;
	}	
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.view.common.components.menu.IMenuItem#getPosition()
	 */
	@Override
	public int getPosition() {	
		return _positon;
	}
	/* (non-Javadoc)
	 * @see mdb.ui.client.view.common.components.menu.IMenuItem#setPosition(int)
	 */
	@Override
	public void setPosition(int position) {
		_positon = position;		
	}		
	
	@Override
	public IMenu getMenu() {
		return _menu;
	}
	@Override
	
	public void setMenu(IMenu _menu) {
		this._menu = _menu;
	}
	@Override
	public IMenu getChildMenu() {
		return _childMenu;
	}
	
	@Override
	public void setChildMenu(IMenu childMenu) {
		_childMenu = childMenu;
	}
	/* (non-Javadoc)
	 * @see mdb.core.ui.view.components.menu.IMenuItem#getvalue()
	 */
	@Override
	public Object getValue() {
		return _value;
	}
	/* (non-Javadoc)
	 * @see mdb.core.ui.view.components.menu.IMenuItem#setValue(java.lang.String)
	 */
	@Override
	public void setValue(Object value) {
		_value = value;
		if ( _changeHandler != null) {
			_changeHandler.onChange( new IChangeEvent() {
				
				@Override
				public Object getValue() {				
					return _value;
				}
			});
		}
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.HasHandlers#fireEvent(com.google.gwt.event.shared.GwtEvent)
	 */
	@Override
	public void fireEvent(GwtEvent<?> event) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see mdb.core.ui.events.IHasChangeHandlers#addChangeHandler(mdb.core.ui.events.IChangeHandler)
	 */
	@Override
	public HandlerRegistration addChangeHandler(IChangeHandler handler) {
		_changeHandler = handler;
		return null;
	}
	/* (non-Javadoc)
	 * @see mdb.core.ui.view.components.menu.IMenuItem#getValueMap()
	 */
	@Override
	public LinkedHashMap<String,Object> getValueMap() {
		return _valueMap;
	}
	/* (non-Javadoc)
	 * @see mdb.core.ui.view.components.menu.IMenuItem#setValueMap(java.util.LinkedHashMap)
	 */
	@Override
	public void setValueMap( LinkedHashMap<String,Object> value) {
		_valueMap = value;		
	}
	/* (non-Javadoc)
	 * @see mdb.core.ui.view.components.menu.IMenuItem#setMemberNumber(int)
	 */
	@Override
	public void setMemberNumber(int memberNumber) {
		_memberNumber = memberNumber;		
	}
	/* (non-Javadoc)
	 * @see mdb.core.ui.view.components.menu.IMenuItem#getMemberNumber()
	 */
	@Override
	public int getMemberNumber() {
		return _memberNumber;
	}
	/* (non-Javadoc)
	 * @see mdb.core.ui.view.components.menu.IMenuItem#isAlreadyBound()
	 */
	@Override
	public boolean isAlreadyBound() {
		return getMemberNumber() >=0;
	}
	
	
}