/**
 * 
 */
package mdb.core.ui.client.view.debug;

import java.util.logging.Logger;

import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.communication.impl.GatewayQueue;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.components.menu.Menu;

/**
 * @author azhuk
 * Creation date: Nov 19, 2013
 *
 */
public class DebugMenu extends mdb.core.ui.client.view.components.menu.Menu {
	private static final Logger _logger = Logger.getLogger(DebugMenu.class
			.getName());
	/**
	 * @param name
	 */
	public DebugMenu() {
		super("Debug menu");
		setPosition(200);
		create();		
	}

	/**
	 * 
	 */
	private void create() {
		
		addFill();
		/*
		IMenuItem item = addItem("Developer Console","silk/bug.png", IMenuItem.ItemType.ToolButton,0);
		item.setCommand(new ICommand<IMenuItem>() {
			
			@Override
			public void execute(IMenuItem sender) {
				 SC.showConsole();							
			}
		});
		*/
		
		IMenuItem  item = addItem("Debug", "", IMenuItem.ItemType.Menu,0);
		item.setImg("silk/bug.png");
		Menu menu = new Menu("");
		item.setChildMenu(menu);		 
		
		item = menu.addItem("Print Queue", "", IMenuItem.ItemType.ToolButton,0);
		item.setCommand(new ICommand<IMenuItem>() {						
			@Override
			public void execute(IMenuItem sender) {
				_logger.info("Invoke GatewayQueue.instance().printQueue()");
				GatewayQueue.instance().printQueue();
			}
		});
		
		item = menu.addItem("Processing Queue", "", IMenuItem.ItemType.ToolButton,0);
		item.setCommand(new ICommand<IMenuItem>() {						
			@Override
			public void execute(IMenuItem sender) {
				_logger.info("Invoke GatewayQueue.instance().getProcessor().run()");
				GatewayQueue.instance().getProcessor().run();							
			}
		});
		
		
		item = menu.addItem("Set  Queue state to Waiting", "", IMenuItem.ItemType.ToolButton,0);
		item.setCommand(new ICommand<IMenuItem>() {						
			@Override
			public void execute(IMenuItem sender) {				
				GatewayQueue.instance().endProcess();							
			}
		});		
		
		item = menu.addItem("Clear Queue", "", IMenuItem.ItemType.ToolButton,0);
		item.setCommand(new ICommand<IMenuItem>() {						
			@Override
			public void execute(IMenuItem sender) {				
				GatewayQueue.instance().clear();							
			}
		});
		
	}
	
			

}
