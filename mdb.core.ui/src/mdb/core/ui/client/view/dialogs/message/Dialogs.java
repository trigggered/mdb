/**
 * 
 */
package mdb.core.ui.client.view.dialogs.message;

import mdb.core.ui.client.resources.locales.Captions;
import  mdb.core.ui.client.util.BooleanCallback;

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
	
	public static void AskDialog2(String message, final BooleanCallback callback) {
	
		Dialog dialogProperties = new Dialog();
		dialogProperties.setShowModalMask(true);
		dialogProperties.setButtons(Dialog.YES,Dialog.NO, Dialog.CANCEL);
		
		SC.ask(Captions.ALARM, message, new com.smartgwt.client.util.BooleanCallback() {
			
			@Override
			public void execute(Boolean value) {
				callback.execute(value);
				
			}
		}, dialogProperties);
				
	}
	

	public static void AskDialog(String message, final BooleanCallback callback) {	
	     	
		SC.ask(message, new com.smartgwt.client.util.BooleanCallback() {
			
			@Override
			public void execute(Boolean value) {
				callback.execute(value);
				
			}
		});				
		
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
			final BooleanCallback booleanCallback) {
		
		SC.say(message, new com.smartgwt.client.util.BooleanCallback() {
			
			@Override
			public void execute(Boolean value) {
				booleanCallback.execute(value);
				
			}
		});
		
	}

	
}
