/**
 * 
 */
package mdb.ui.client.view.editor;

import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.data.IDataSpecification;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.components.menu.IMenuItem.ItemType;
import mdb.core.ui.client.view.components.menu.data.MenuDynamic;




/**
 * @author azhuk
 * Creation date: Dec 20, 2012
 *
 */
public class MenuCreateNewEntityMethod extends MenuDynamic {

	private class MenuActionSpec implements IDataSpecification {				
		@Override
		public String getId() {
			return "id_dentity_action".toUpperCase();
		}

		@Override
		public String getParentId() {
			return null;
		}

		@Override
		public String getTitle() {
			return "name_action".toUpperCase();
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
			return "POSITION";
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
			
	private final int  MAIN_MENU = 1151;
	
	private ICommand<IMenuItem> _command;
	
	public MenuCreateNewEntityMethod (IMenuContainer container,  ICommand<IMenuItem> command) {
		super (null);
		setDataSpecification(new MenuActionSpec () );
		
		_command = command;			
		getMapItems().put("0", addItem("New  method", "silk/actions.png", ItemType.Menu, 0));
	
		setContainer(container);
		build(MAIN_MENU, null);		
	}
	
	@Override
	protected void setParentIdValue(IMenuItem item, String parentId) {
		item.setAttribute("parentID", "0");	
	}

	@Override
	protected  void setCommand(IMenuItem item ) {		
		item.setCommand( _command );
	}

}
