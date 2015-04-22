/**
 * 
 */
package mdb.core.ui.client.view.dialogs.input;

import mdb.core.ui.client.events.ICallbackEvent;

import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
/**
 * @author azhuk
 * Creation date: Aug 12, 2013
 *
 */
public class InputValueDialog {

	
	private ICallbackEvent<String> _callbackEvent;
	
	public InputValueDialog (ICallbackEvent<String> callbackEvent) {
		_callbackEvent = callbackEvent;
	}
	
	public void show() {
		 show("Enter value:", "Value");
	}
	
	public void  show(String title, String message) {		
		SC.askforValue(title, message, new ValueCallback() {  
            @Override  
            public void execute(String value) {
            	if (value != null && !value.equals("")) {  
            		_callbackEvent.doWork(value);               
            	}
            }  
        });
		  
	}
}
