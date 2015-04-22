/**
 * 
 */
package mdb.core.ui.client.app;

import java.util.logging.Logger;

import mdb.core.shared.auth.IUserInfo;
import mdb.core.ui.client.view.IMainView;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

/**
 * @author azhuk
 * Creation date: Feb 3, 2014
 *
 */
public class AppController implements ValueChangeHandler<String>, IAppController {
	
	private static final Logger _logger = Logger.getLogger(AppController.class
			.getName());

	private final boolean DEBUG_MODE = false;
	
	private static IAppController _instance;
	
	private IMainView  _mainView;
	
	private IUserInfo _currentUser;
	
	private IAppContext _appContext;
	
	public AppController () {
		setAppContext(new AppContext());
	}
	
	
	public static IAppController getInstance () {		
		if (_instance == null) {
			_instance = new AppController();
		}		
		return _instance;			
	}
	
	public static void  setInstance (IAppController value) {
		_instance = value;
	}
	
	public void initialAppContext() {
		
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
	 *  Assume that your url as
	 *   http://www.myPageName/myproject.html?#orderId:99999
	 */
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		
	}

	@Override
	public IMainView getMainView() {
		return _mainView;
	}

	@Override
	public void setMainView(IMainView _mainView) {
		this._mainView = _mainView;
	}
	@Override
	public IUserInfo getCurrentUser() {
		return _currentUser;
	}
	@Override
	public void setCurrentUser(IUserInfo _currentUser) {
		this._currentUser = _currentUser;
	}
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.IApplicationController#getAppContext()
	 */
	@Override
	public IAppContext getAppContext() {
		return _appContext;
	}
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.IApplicationController#setAppContext(mdb.core.ui.client.IAppContext)
	 */
	@Override
	public void setAppContext(IAppContext value) {
		_appContext = value;		
	}
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.IAppController#getValueChangeHandler()
	 */
	@Override
	public ValueChangeHandler<String> getValueChangeHandler() {
		return this;
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.app.IAppController#isDebugMode()
	 */
	@Override
	public boolean isDebugMode() {
		return DEBUG_MODE;
	}
}
