package mdb.core.ui.client.view.components.menu.mdb;

import java.util.logging.Logger;

import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.ui.client.communication.impl.ADataProvider;
import mdb.core.ui.client.data.IBaseDataSource;
import mdb.core.ui.client.data.IDataWraper;
import mdb.core.ui.client.data.bind.DataBindException;
import mdb.core.ui.client.data.fields.IDataSourceFieldsBuilder;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.dialogs.input.IInputDialogs;

/**
 * @author azhuk
 * Creation date: Dec 20, 2012
 *
 */
public class MenuPrepareFilters extends  MenuActions { 	

	class ActionCommand  extends MenuActions.ActionCommand {		
		
		private  final Logger _logger = Logger.getLogger(ADataProvider.class.getName());
		 
		
		public ActionCommand () {
		}
		
		@Override
		public ExecuteType getExecuteType() {
			return ExecuteType.ApplyFilter;
		}
		
		@Override
		protected boolean isCanExecAction() {
			return true;
		}
		
		@Override
		protected boolean isExecutePossibility() {
			return  true;
		}
		
		@Override
		protected void afterExecuteAction() {
			
			String data = getResponse().get(String.valueOf(getActionEntityId())).getData();
			
			IBaseDataSource ds = getOwnerView().getDataSources().get(Integer.valueOf(getActionEntityId()));
			ds.setData(data);
			getResponse().clear();
			try {
				getOwnerView().bindDataComponents();
			} catch (DataBindException e) {
				_logger.severe(e.getMessage());	
				
			}
		}		
	}	
	
	private final int PREPARE_FILTER_ACTION_ID = 8;
	
	/**
	 * @param container
	 */
	public MenuPrepareFilters(String name, 	IMenuContainer container, 
											IDataWraper dataWraper,  
											IDataSourceFieldsBuilder fieldsBuilder,
											IInputDialogs dialog) {
		this(name,dataWraper, fieldsBuilder,dialog);
		setContainer(container);
	}


	public MenuPrepareFilters(String name, IDataWraper dataWraper, 
										   IDataSourceFieldsBuilder fieldsBuilder,
										   IInputDialogs dialog) {
		super(name, dataWraper, fieldsBuilder, dialog);
		getOwnerMenuItem().setCaption(getOwnerMenuTittle());
	}		


	
	@Override
	protected MenuActions.ActionCommand createActionComand() {
		ActionCommand command = new MenuPrepareFilters.ActionCommand();
		command.setDataSourceFieldsBuilder(getDataSourceFieldsBuilder());
		return   	command;
	}
	
	@Override
	protected String getOwnerMenuTittle() {
		return "Поиск";
	}
	
	
	@Override	
	protected int getActionId () {
		return PREPARE_FILTER_ACTION_ID;
	}
	
}
