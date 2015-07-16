/**
 * 
 */
package mdb.core.ui.client.view.components.menu.mdb;


import java.util.logging.Logger;

import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.view.IMainView;
import mdb.core.ui.client.view.components.menu.IMenuItem;

/**
 * @author azhuk
 * Creation date: Apr 4, 2013
 *
 */
public class MdbMainMenuCommand implements ICommand<IMenuItem> {
	
	private static final Logger _logger = Logger
			.getLogger(MdbMainMenuCommand.class.getName());
	
	private IMainView _boundView;
	private IMdbMainMenuAction _mdbMainMenuAction;
	
	public MdbMainMenuCommand (IMainView boundView, IMdbMainMenuAction mdbMainMenuAction) {
		_boundView = boundView;
		_mdbMainMenuAction =mdbMainMenuAction;
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.command.ICommand#execute(java.lang.Object)
	 */
	@Override
	public void execute(IMenuItem sender) {
		
		String id = sender.getAttribute("ID");
		String action = sender.getAttribute("ACTION");
		
		_logger.info("Menu ID is "+ id + " Menu Action is "+action );		
		
		/*
		private IView getView() {
		EViewMenuIdent viewMenuIdent  = EViewMenuIdent.valueOf(action);		
		IView view = ViewFactory.create(viewMenuIdent);				
		return view
		}		
		*/				
		//_boundView.openViewInTab( _mdbMainMenuAction.getView(action));
		_boundView.openView( _mdbMainMenuAction.getView(action));
	}
}
