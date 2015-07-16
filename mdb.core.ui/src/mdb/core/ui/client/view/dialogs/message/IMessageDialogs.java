/**
 * 
 */
package mdb.core.ui.client.view.dialogs.message;

import java.util.logging.Level;
import java.util.logging.Logger;

import mdb.core.ui.client.resources.locales.Captions;
import mdb.core.ui.client.util.BooleanCallback;


/**
 * @author azhuk
 * Creation date: Jul 17, 2015
 *
 */
public interface IMessageDialogs {	
	
	public void ShowMessage(String message);

	public  void ShowMessage(String title, String message);
	
	public  void ShowWarnMessage(String message);
	
	public  void AskDialog2(String message, final BooleanCallback callback);

	public 	void AskDialog(String message, final BooleanCallback callback);

	public 	void ValidatonWarning();

	public void Message(String message,
			final BooleanCallback booleanCallback);

}
