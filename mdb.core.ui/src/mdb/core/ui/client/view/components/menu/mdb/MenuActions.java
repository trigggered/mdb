package mdb.core.ui.client.view.components.menu.mdb;

import java.util.HashMap;

import org.eclipse.jdt.internal.compiler.parser.diagnose.DiagnoseParser;

import mdb.core.shared.data.Params;
import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.communication.impl.AMdbActionDataProvider;
import mdb.core.ui.client.data.IDataSpecification;
import mdb.core.ui.client.data.IDataWraper;
import mdb.core.ui.client.data.fields.IDataSourceFields;
import mdb.core.ui.client.data.fields.IDataSourceFieldsBuilder;
import mdb.core.ui.client.view.components.menu.IMenu;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.components.menu.IMenuItem.ItemType;
import mdb.core.ui.client.view.components.menu.data.MenuDynamic;
import mdb.core.ui.client.view.dialogs.input.IInputDialogs;

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
	
	class ActionCommand  extends AMdbActionDataProvider  implements ICommand<IMenuItem> {
		/**
		 * @param entittId
		 */
		private Integer _actionEntityId;
		private IInputDialogs _inputDialog;
		
		
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



		/* (non-Javadoc)
		 * @see mdb.core.ui.client.communication.impl.AMdbActionDataProvider#getInputDialogs()
		 */
		@Override
		protected IInputDialogs getInputDialogs() {
			return _inputDialog;
		}


		/**
		 * 
		 */
		public void setInputDialog(IInputDialogs dialog) {
			_inputDialog = dialog;
			
		}
	}		
	
	
	private final int STORE_PROC_ACTION_ID = 7;	
	private final int ENTITY_ID = 1091;	
	
	private  ActionCommand _actionCommand;
	private IMenuItem _ownerMenuItem;
	private int _ActionForEntityId;
	private IDataSourceFieldsBuilder _fieldsBuilder;
	
	protected  IDataSourceFieldsBuilder getDataSourceFieldsBuilder() {
		return _fieldsBuilder;
	}
	
	protected  void setDataSourceFieldsBuilder(IDataSourceFieldsBuilder value) {
		_fieldsBuilder = value;
	}
	
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

	public MenuActions (String name, IMenuContainer container, 
									  IDataWraper dataWraper,  
									  IDataSourceFieldsBuilder fieldsBuilder,
									  IInputDialogs dialog) {
		this(name,dataWraper, fieldsBuilder, dialog);
		setContainer(container);						
	}	
	
	public MenuActions ( String name, IDataWraper dataWraper, 
									  IDataSourceFieldsBuilder fieldsBuilder,
									  IInputDialogs dialog) {			
		super(name, dataWraper);
		
		setDataSourceFieldsBuilder(fieldsBuilder);
		setDataSpecification(new MenuActionSpec());		
		
		setEntityId(ENTITY_ID);	
		
		_actionCommand = createActionComand();	
		_actionCommand.setInputDialog(dialog);
		
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
		ActionCommand  toReturn = new ActionCommand();
		
		toReturn.setDataSourceFieldsBuilder(getDataSourceFieldsBuilder());
		return   	toReturn;
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
	public IMenu getSelf() {
		return this;
	}
	
	protected IMenuItem getOwnerMenuItem () {
		return _ownerMenuItem;
	}
	
	protected void setOwnerMenuItem (IMenuItem value) {
		_ownerMenuItem = value;	
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IDataProvider#getDataSourceFieldsMap()
	 */
	@Override
	public HashMap<Integer, IDataSourceFields> getDataSourceFieldsMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IDataProvider#getDataSourceKeysMap()
	 */
	@Override
	public HashMap<Integer, String[]> getDataSourceKeysMap() {
		// TODO Auto-generated method stub
		return null;
	}




}
