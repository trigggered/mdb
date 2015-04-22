/**
 * 
 */
package mdb.core.ui.client.view.components.menu.mdb;

import java.util.LinkedHashMap;
import java.util.logging.Logger;

import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.data.IDataSpecification;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.components.menu.IMenuItem.ItemType;
import mdb.core.ui.client.view.components.menu.data.MenuDynamic;

import com.smartgwt.client.data.Record;



/**
 * @author azhuk
 * Creation date: Dec 12, 2012
 *
 */
public class MenuFieldViews extends  MenuDynamic   {	
	
	private static final Logger _logger = Logger.getLogger(MenuFieldViews.class.getName());
	
	private final int ENTITY_ID = 5093;			
	
	private IMenuItem _itemMenu;
	/*
	class Command implements ICommand<IMenuItem>  {							
			@Override
			public void execute(IMenuItem sender) {
				getContainer().getView().addCustomProperty("VIEW_NN",
						sender.getValue());				
			}
	}	
	*/
	class MenuSpecification implements IDataSpecification {				
		@Override
		public String getId() {
			return "VIEW_NN";
		}

		@Override
		public String getParentId() {
			return null;
		}

		@Override
		public String getTitle() {
			return "VIEW_NN";
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.data.IDataSpecification#getImg()
		 */
		@Override
		public String getImg() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.data.IDataSpecification#getAction()
		 */
		@Override
		public String getAction() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.client.data.IDataSpecification#getPosition()
		 */
		@Override
		public String getPosition() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.client.data.IDataSpecification#getShortcut()
		 */
		@Override
		public String getShortcut() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
	//private Command _command = new Command();
	
	public MenuFieldViews(IMenuContainer container ) {
		super (null);
		 _itemMenu = addItem(ItemType.Combobox);
		 _itemMenu.setWidth(100);
		 _itemMenu.setCaption("Views");
		 _itemMenu.setShowCaption(true);		
		 _itemMenu.setValue("1");
		 //setCommand(_itemMenu);
		 
		addSeparator();		
		
		setEntityId(ENTITY_ID);
		setDataSpecification(new MenuSpecification());		
		setContainer(container);							
	}
	
	
	@Override
	protected void buildMenu(Record[] records) {
		_logger.info("Start build menu");				
		
		if (records != null) {
			
			LinkedHashMap<String, Object>  map = new LinkedHashMap<String, Object>();	
		    for (Record rec : records) {
		    	map.put(rec.getAttributeAsString(getDataSpecification().getId()),
		    			rec.getAttributeAsString(getDataSpecification().getTitle()));
		    					
			}
		    _itemMenu.setValueMap(map);
		}	    		
		_logger.info("End build menu");
		containerBind();	    
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.view.components.menu.impl.MenuDynamic#setCommand(mdb.core.ui.view.components.menu.IMenuItem)
	 */
	@Override
	protected void setCommand(IMenuItem item) {
	//	item.setCommand(_command);		
	}
	
	public void addCommand(ICommand<IMenuItem> command) {
		_itemMenu.setCommand(command);	 	
	}
	
}
