/**
 * 
 */
package mdb.core.ui.smartgwt.client.components.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import mdb.core.ui.client.view.IView;
import mdb.core.ui.client.view.components.menu.IMenu;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.data.IDataView;
import mdb.core.ui.smartgwt.client.view.ISGWTComponent;

import com.smartgwt.client.data.Record;
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

public class ToolStripMenuContainer implements IMenuContainer, ISGWTComponent {	

	private static final Logger _logger = Logger.getLogger(ToolStripMenuContainer.class.getName());
	
	private Layout _container; 
	private ToolStripMenuItemFactory _menuItemFactory;
	private IDataView<Record> _view;
	private List<String> _boundMenuNames = new ArrayList<String>();			
	
	
	public ToolStripMenuContainer() {
		_menuItemFactory = new ToolStripMenuItemFactory();
		createContainer();			
	}
	
	
	public ToolStripMenuContainer(IMenuItemFactory factory) {
		_menuItemFactory =  new ToolStripMenuItemFactory();
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
					addMemberToContainer(canvas);		
					
					item.setMemberNumber(getContainer().getMemberNumber(canvas));
					item.setId(getContainer().getMemberNumber(canvas));
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
	 * @see mdb.ui.client.common.view.components.menu.IMenuContainer#getView()
	 */
	@Override
	public IDataView<Record> getView() {		
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
		return _container;
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.view.ISmartGwtView#setViewPanel(com.smartgwt.client.widgets.layout.Layout)
	 */
	@Override
	public void setMainLayout(Layout value) {
		_container = value;
		
	}
}
