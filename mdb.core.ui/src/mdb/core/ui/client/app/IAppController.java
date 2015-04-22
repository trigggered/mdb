/**
 * 
 */
package mdb.core.ui.client.app;

import com.google.gwt.event.logical.shared.ValueChangeHandler;

import mdb.core.shared.auth.IUserInfo;
import mdb.core.ui.client.view.IMainView;

/**
 * @author azhuk
 * Creation date: Jun 26, 2014
 *
 */
public interface IAppController {
	
	public ValueChangeHandler<String> getValueChangeHandler();

	public abstract IMainView getMainView();

	public abstract void setMainView(IMainView _mainView);

	public abstract IUserInfo getCurrentUser();

	public abstract void setCurrentUser(IUserInfo _currentUser);
	
	public IAppContext getAppContext();
	
	public void setAppContext(IAppContext  value);	

	public void initialAppContext();
	
	public boolean isDebugMode();
	

}