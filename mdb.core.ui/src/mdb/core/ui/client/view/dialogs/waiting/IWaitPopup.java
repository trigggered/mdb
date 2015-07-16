/**
 * 
 */
package mdb.core.ui.client.view.dialogs.waiting;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author azhuk
 * Creation date: Jul 17, 2015
 *
 */
public interface IWaitPopup {
	public void show(String actionName);
	public void hide();
	public void hideFinal();
}
