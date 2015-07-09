/**
 * 
 */
package mdb.core.ui.client.view.dialogs.message;

import mdb.core.ui.client.resources.locales.Captions;

import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Dialog;


/**
 * @author azhuk
 * Creation date: Nov 26, 2014
 *
 */
public class Dialogs {	
	
	public static void ShowMessage(String message) {
		
		SC.say(message);
		
	}
	
	public static void ShowMessage(String title, String message) {
		
		SC.say(title, message);
		
	}

	
	public static void ShowWarnMessage(String message) {
		
		SC.warn(message);
		
	}
	
	public static void AskDialog2(String message, BooleanCallback callback) {
	
		Dialog dialogProperties = new Dialog();
		dialogProperties.setShowModalMask(true);
		dialogProperties.setButtons(Dialog.YES,Dialog.NO, Dialog.CANCEL);     	    		
		SC.ask(Captions.ALARM, message, callback, dialogProperties);		
	}
	

	public static void AskDialog(String message, BooleanCallback callback) {	
	     	    		
		SC.ask(message, callback);		
		
	}

	/**
	 * 
	 */
	public static void ValidatonWarning() {
		
		SC.warn(Captions.ERROR_VALIDATION , Captions.ERROR_REQUIRED);
		
	}

	/**
	 * @param saveCorrectResult
	 * @param booleanCallback
	 */
	public static void Message(String message,
			BooleanCallback booleanCallback) {
		SC.say(message, booleanCallback);
		
	}
	
}
