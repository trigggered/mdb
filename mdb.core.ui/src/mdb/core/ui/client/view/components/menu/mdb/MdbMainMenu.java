/**
 * 
 */
package mdb.core.ui.client.view.components.menu.mdb;

import mdb.core.shared.data.Params;
import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.data.IDataSpecification;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.components.menu.data.MenuDynamic;


/**
 * @author 
 * Creation date: Mar 26, 2013
 *
 */
public class MdbMainMenu  extends MenuDynamic {	
	
	private class MenuActionSpec implements IDataSpecification {				
		@Override
		public String getId() {
			return "id_menu".toUpperCase();
		}

		@Override
		public String getParentId() {
			return "id_menu_parent".toUpperCase();
		}

		@Override
		public String getTitle() {
			return "name".toUpperCase();
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.data.IDataSpecification#getImg()
		 */
		@Override
		public String getImg() {
			return "img_path".toUpperCase();
		}

		@Override
		public String getAction() {
			return "action".toUpperCase();
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.client.data.IDataSpecification#getPosition()
		 */
		@Override
		public String getPosition() {			
			return "POSITION";
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.client.data.IDataSpecification#getShortcut()
		 */
		@Override
		public String getShortcut() {
			return "SHORTCUT_KEY";
		}		
	}	
	
	
	private final static int  MAIN_MENU = 1426;
	
	private ICommand<IMenuItem> _command;	
	
	
	public MdbMainMenu (int appId, IMenuContainer container,  ICommand<IMenuItem> command) {
		this(MAIN_MENU, appId, container,  command, null);		
	}
	
	public MdbMainMenu (int menuEntityId, int appId, IMenuContainer container,  ICommand<IMenuItem> command, Params qParams) {
		super ("Main menu");
		setEntityId(menuEntityId);
		setDataSpecification(new MenuActionSpec () );		
		_command = command;						
		setContainer(container);
		
		Params params = new Params();
		params.add("ID_APP",String.valueOf(appId) );		
		params.copyFrom(qParams);		
		build(getEntityId(), params);
	}
	
	@Override
	protected  void setCommand(IMenuItem item ) {		
		item.setCommand( _command );
	}
	
}
