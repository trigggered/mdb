/**
 * 
 */
package mdb.core.ui.client.view.dialogs.message;

import mdb.core.ui.client.util.BooleanCallback;


/**
 * @author azhuk
 * Creation date: Nov 26, 2014
 *
 */
public class Dialogs  {	
	
	static IMessageDialogs _messDialogs; 
	
	public static void regiserMessageDialogs(IMessageDialogs  messageDialogs) {
		_messDialogs = messageDialogs;
	}
	
	
	public static void ShowMessage(String message) {
		_messDialogs.ShowMessage(message);		
	}
	
	public static void ShowMessage(String title, String message) {		
		_messDialogs.ShowMessage(title, message);		
	}

	
	public static void ShowWarnMessage(String message) {
		
		_messDialogs.ShowWarnMessage(message);
		
	}
	
	public static void AskDialog2(String message, final BooleanCallback callback) {
	 _messDialogs.AskDialog2(message, callback);			
	}
	

	public static void AskDialog(String message, final BooleanCallback callback) {	
		_messDialogs.AskDialog(message, callback);
	}

	/**
	 * 
	 */
	public static void ValidatonWarning() {
		_messDialogs.ValidatonWarning();
	}

	/**
	 * @param saveCorrectResult
	 * @param booleanCallback
	 */
	public static void Message(String message,
			final BooleanCallback booleanCallback) {
		_messDialogs.Message(message, booleanCallback);		
	}

	
}
