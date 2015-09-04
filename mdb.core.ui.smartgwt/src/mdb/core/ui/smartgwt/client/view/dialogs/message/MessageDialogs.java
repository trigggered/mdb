/**
 * 
 */
package mdb.core.ui.smartgwt.client.view.dialogs.message;

import mdb.core.ui.client.resources.locales.Captions;
import  mdb.core.ui.client.util.BooleanCallback;
import mdb.core.ui.client.view.dialogs.message.IMessageDialogs;

import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Dialog;


/**
 * @author azhuk
 * Creation date: Nov 26, 2014
 *
 */
public class MessageDialogs implements IMessageDialogs {	
	
	static IMessageDialogs  _instance;
	
	public static IMessageDialogs instance() {
		if (_instance == null) {
			_instance = new MessageDialogs();
		}
		
		return _instance;
	}
	
	public  void ShowMessage(String message) {
		
		SC.say(message);
		
	}
	
	public  void ShowMessage(String title, String message) {
		
		SC.say(title, message);
		
	}

	
	public  void ShowWarnMessage(String message) {
		
		SC.warn(message);
		
	}
	
	public  void AskDialog2(String message, final BooleanCallback callback) {
	
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
	

	public  void AskDialog(String message, final BooleanCallback callback) {	
	     	
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
	public  void ValidatonWarning() {
		
		SC.warn(Captions.ERROR_VALIDATION , Captions.ERROR_REQUIRED);
		
	}

	/**
	 * @param saveCorrectResult
	 * @param booleanCallback
	 */
	public  void Message(String message,
			final BooleanCallback booleanCallback) {
		
		SC.say(message, new com.smartgwt.client.util.BooleanCallback() {
			
			@Override
			public void execute(Boolean value) {
				booleanCallback.execute(value);
				
			}
		});		
	}

	
}
