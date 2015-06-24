package mdb.core.ui.client.view.components.menu.mdb;

import java.util.logging.Logger;

import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.ui.client.communication.impl.ADataProvider;
import mdb.core.ui.client.data.IDataSource;
import mdb.core.ui.client.data.bind.DataBindException;
import mdb.core.ui.client.data.impl.ViewDataConverter;
import mdb.core.ui.client.view.components.menu.IMenuContainer;

/**
 * @author azhuk
 * Creation date: Dec 20, 2012
 *
 */
public class MenuPrepareFilters extends  MenuActions { 	

	class ActionCommand  extends MenuActions.ActionCommand {		
		
		private  final Logger _logger = Logger.getLogger(ADataProvider.class.getName());
		 
				
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
			
			IDataSource ds = getOwnerView().getDataSources().get(Integer.valueOf(getActionEntityId()));
			ds.setData(ViewDataConverter.jSon2RecordArray(data));
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
	public MenuPrepareFilters(String name, IMenuContainer container) {
		this(name);
		setContainer(container);
	}


	public MenuPrepareFilters(String name) {
		super(name);
		getOwnerMenuItem().setCaption(getOwnerMenuTittle());
	}
	
	


	
	@Override
	protected MenuActions.ActionCommand createActionComand() {
		return   new MenuPrepareFilters.ActionCommand();	
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
