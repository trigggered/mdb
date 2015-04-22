/**
 * 
 */
package mdb.core.ui.client.view.components.menu.impl.smart;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import mdb.core.ui.client.view.components.menu.IMenu;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.components.menu.IMenuItemFactory;
import mdb.core.ui.client.view.data.IDataView;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 * @author azhuk
 * Creation date: Dec 20, 2012
 *
 */

public class ToolStripMenuContainer implements IMenuContainer {	

	private static final Logger _logger = Logger.getLogger(ToolStripMenuContainer.class.getName());
	
	private Layout _container; 
	private IMenuItemFactory _menuItemFactory;
	private IDataView _view;
	private List<String> _boundMenuNames = new ArrayList<String>();			
	
	
	public ToolStripMenuContainer(IMenuItemFactory menuItemFactory) {
		_menuItemFactory = menuItemFactory;
		createContainer();			
	}
	
	protected  void createContainer() {
		setContainer(new ToolStrip());
		getContainer().setWidth100();		
	}
	
	protected Layout getContainer(){
		return _container;
	}
	
	protected void setContainer(Layout value){
		 _container = value;
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.view.common.components.menu.IMenuContainer#bind(mdb.ui.client.view.common.components.menu.IMenu)
	 */
	@Override
	public void bind(IMenu menu) {
		if (!isMenuExist(menu)) {
			menu.setContainer(this);
			_boundMenuNames.add(menu.getName());
			_logger.info("Bind menu to container. Menu name:"+menu.getName());				
			
			List<IMenuItem> menuItems = menu.getSortedItems();
			//List<Canvas> canvases  = new ArrayList<Canvas>();
			for (IMenuItem item : menuItems) {
				if (!item.isAlreadyBound())	{
					Canvas canvas = _menuItemFactory.createAndRefSubMenu(item);
					//canvases.add(canvas);
					addMemberToContainer(canvas);		
					
					item.setMemberNumber(getContainer().getMemberNumber(canvas));				
				}
				else {
					rebindItemProperty(item);
				}
			}	
			//getContainer().addMembers(canvases, menu.getPosition());
			getContainer().redraw();
			
		}
	}
	
	
	
	public void addMemberToContainer(Canvas canvas) {
		//getContainer().addM
		getContainer().addMember(canvas);				
	}
	
	public void rebindItemProperty(IMenuItem item) {
		
		Canvas canvas  = getContainer().getMember(item.getMemberNumber());
		switch (item.getItemType() ) {
			case Combobox:
				DynamicForm frm = (DynamicForm)canvas;
				SelectItem selItem = (SelectItem)frm.getFields()[0];
				if (item.getValueMap() != null) {
					selItem.setValueMap(item.getValueMap());
				}
				break;
			default:
				break;
		}
	}
	

	/* (non-Javadoc)
	 * @see mdb.ui.client.view.common.components.menu.IMenuContainer#getLayout()
	 */
	@Override
	public Layout getLayout() {
		return _container;
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
}
