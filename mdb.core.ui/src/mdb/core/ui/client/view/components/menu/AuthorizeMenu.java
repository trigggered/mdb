/**
 * 
 */
package mdb.core.ui.client.view.components.menu;

import java.util.logging.Logger;

import mdb.core.ui.client.command.ICommand;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window.Location;

/**
 * @author azhuk
 * Creation date: Nov 19, 2013
 *
 */
public class AuthorizeMenu extends mdb.core.ui.client.view.components.menu.Menu {
	private static final Logger _logger = Logger.getLogger(AuthorizeMenu.class
			.getName());
	
	
	final String LOGIN_ACTION_URL = GWT.getHostPageBaseURL() + "login";	
	final String LOGOUT_ACTION_URL = GWT.getHostPageBaseURL()+ "logout";
	final String LBL_LOG_OUT = "LogOut";
	
	private String _lableName = null;
	

	public AuthorizeMenu() {
		super("Authorize");		
		setPosition(1);
		create();		
	}
	
	/**
	 * @param string 
	 * @param name
	 */
	public AuthorizeMenu(String lableName) {
		super("Authorize");
		_lableName = lableName;
		setPosition(1);
		create();		
	}

	/**
	 * 
	 */
	private void create() {
		addFill();
		//IMenuItem item = addItem("", "", IMenuItem.ItemType.Fill,0);
		/*item = addItem("LogIn", "", IMenuItem.ItemType.ToolButton,0);
		item.setCommand(new ICommand<IMenuItem>() {						
			@Override
			public void execute(IMenuItem sender) {
				_logger.info("LogIn");				
				sendRequest(LOGIN_ACTION_URL);				
			}
		});*/
		
		IMenuItem item = addItem(_lableName==null?LBL_LOG_OUT:_lableName, "", IMenuItem.ItemType.ToolButton,0);
		item.setHint("Выход из системы");
		item.setShowHint(true);
		item.setCommand(new ICommand<IMenuItem>() {						
			@Override
			public void execute(IMenuItem sender) {
				_logger.info("LogOut");
				sendRequest(LOGOUT_ACTION_URL);							
			}
		});		
		
	}	
	
	
	
	private void sendRequest (String url) {		
		Location.replace(url);
		/*
		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, url);
		try {
		    rb.sendRequest(null, new RequestCallback() {
		    	@Override
		        public void onResponseReceived(Request request, Response response) {
		            _logger.info(response.getStatusText());
		        }
		        @Override
		        public void onError(Request request, Throwable caught) {
		            // try to recover somehow
		        	_logger.severe(caught.getMessage());
		        }
		    });
		} catch (RequestException re) {
		    _logger.severe(re.getMessage());
		}
		*/
	}

}
