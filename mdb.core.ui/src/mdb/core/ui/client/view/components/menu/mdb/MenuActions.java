package mdb.core.ui.client.view.components.menu.mdb;

import mdb.core.shared.data.Params;
import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.communication.impl.MdbActionDataProvider;
import mdb.core.ui.client.data.IDataSpecification;
import mdb.core.ui.client.view.components.menu.IMenu;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.components.menu.IMenuItem.ItemType;
import mdb.core.ui.client.view.components.menu.data.MenuDynamic;

/**
 * @author azhuk
 * Creation date: Dec 20, 2012
 *
 */
public class MenuActions extends  MenuDynamic {  

	class MenuActionSpec implements IDataSpecification {				
		@Override
		public String getId() {
			return "ID_EXT_METHOD";
		}

		@Override
		public String getParentId() {
			return null;
		}

		@Override
		public String getTitle() {
			return "METHOD_NOTE";
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.data.IDataSpecification#getImg()
		 */
		@Override
		public String getImg() {
			// TODO Auto-generated method stub
			return null;
		}

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
	
	class ActionCommand  extends MdbActionDataProvider  implements ICommand<IMenuItem> {
		/**
		 * @param entittId
		 */
		private Integer _actionEntityId;
		
		
		@Override
		public  ExecuteType getExecuteType() {
			return ExecuteType.ExecAction;
		}
		
		
		/* (non-Javadoc)
		 * @see mdb.ui.client.view.command.ICommand#execute()
		 */
		@Override
		public void execute(IMenuItem sender) {
			execute(_actionEntityId, Integer.parseInt(sender.getAttribute("ID")));			
		}	
		
		public Integer getActionEntityId() {
			return _actionEntityId;
		}
		
		public void  setActionForEntityId(Integer value) {
			_actionEntityId = value;
		}
	}		
	
	
	private final int STORE_PROC_ACTION_ID = 7;	
	private final int ENTITY_ID = 1091;	
	
	private  ActionCommand _actionCommand;
	private IMenuItem _ownerMenuItem;
	private int _ActionForEntityId;
	
	/**
	 * @return the _ActionForEntityId
	 */
	public int getActionForEntityId() {
		return _ActionForEntityId;
	}

	/**
	 * @param _ActionForEntityId the _ActionForEntityId to set
	 */
	public void setActionForEntityId(int value) {
		this._ActionForEntityId = value;
		_actionCommand.setActionForEntityId(value);		
	}

	public MenuActions (String name, IMenuContainer container) {
		this(name);
		setContainer(container);						
	}	
	
	public MenuActions (String name) {			
		super(name);
		setDataSpecification(new MenuActionSpec());		
		
		setEntityId(ENTITY_ID);	
		
		_actionCommand = createActionComand();	
		
		setOwnerMenuItem( addItem(getOwnerMenuTittle(), "silk/actions.png", ItemType.Menu, 0)) ;
		getMapItems().put("0", getOwnerMenuItem());						
	}
	
	@Override
	public void setContainer(IMenuContainer menuContainer) {
		super.setContainer(menuContainer);
		if (menuContainer!=null) {
			_actionCommand.setView(menuContainer.getView());
		}
	}
	
	
	@Override
	public void prepareRequestData() {		
		_params = new Params();
		_params.add("ID_DENTITY", String.valueOf( _ActionForEntityId));
		_params.add("ID_DENTITY_ACTION", String.valueOf( getActionId()));
		super.prepareRequestData();
	}

	protected MenuActions.ActionCommand createActionComand() {
		return   new ActionCommand();	
	}
	
	protected  void setCommand(IMenuItem item ) {		
		item.setCommand( _actionCommand );
	}
	

	protected String getOwnerMenuTittle() {
		return "Действия";
	}
	
	
	protected int getActionId () {
		return STORE_PROC_ACTION_ID;
	}	

	
	@Override
	protected void setParentIdValue(IMenuItem item, String parentId) {
		item.setAttribute("parentID", "0");	
	}
	
	
	@Override
	public IMenu getSelf() {
		return this;
	}
	
	protected IMenuItem getOwnerMenuItem () {
		return _ownerMenuItem;
	}
	
	protected void setOwnerMenuItem (IMenuItem value) {
		_ownerMenuItem = value;	
	}


}
