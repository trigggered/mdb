/**
 * 
 */
package mdb.core.vaadin.ui.view.dialogs.messages;

import java.util.logging.Logger;

import mdb.core.ui.client.resources.locales.Captions;
import mdb.core.ui.client.util.BooleanCallback;
import mdb.core.ui.client.view.dialogs.message.IMessageDialogs;
import de.steinwedel.messagebox.ButtonId;
import de.steinwedel.messagebox.Icon;
import de.steinwedel.messagebox.MessageBox;
import de.steinwedel.messagebox.MessageBoxListener;
/**
 * @author azhuk
 * Creation date: Jul 22, 2015
 *
 */
public class MessageDialogs implements IMessageDialogs  {
	private static final Logger _logger = Logger.getLogger(MessageDialogs.class
			.getName());

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.dialogs.message.IMessageDialogs#ShowMessage(java.lang.String)
	 */
	@Override
	public void ShowMessage(String message) {
		MessageBox.showPlain(Icon.INFO, Captions.NONE,message, ButtonId.OK);
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.dialogs.message.IMessageDialogs#ShowMessage(java.lang.String, java.lang.String)
	 */
	@Override
	public void ShowMessage(String title, String message) {
		MessageBox.showPlain(Icon.INFO, 
				title, 
			    message, ButtonId.OK);
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.dialogs.message.IMessageDialogs#ShowWarnMessage(java.lang.String)
	 */
	@Override
	public void ShowWarnMessage(String message) {
		
		MessageBox.showPlain(Icon.WARN, 
			    Captions.ALARM, 
			    message,
			     
			    ButtonId.YES, 
			    ButtonId.NO,
			    ButtonId.CANCEL); 
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.dialogs.message.IMessageDialogs#AskDialog2(java.lang.String, mdb.core.ui.client.util.BooleanCallback)
	 */
	@Override
	public void AskDialog2(String message, final BooleanCallback callback) {
		MessageBox.showPlain(Icon.QUESTION, 
			    Captions.Q_ASK, 
			    message, 
			    new MessageBoxListener() {
			                    
			        @Override
			        public void buttonClicked(ButtonId buttonId) {			        	
			        		callback.execute(buttonId == ButtonId.YES);			        				            
			        }
			    }, 
			    ButtonId.YES, 
			    ButtonId.NO,
			    ButtonId.CANCEL);   
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.dialogs.message.IMessageDialogs#AskDialog(java.lang.String, mdb.core.ui.client.util.BooleanCallback)
	 */
	@Override
	public void AskDialog(String message, final BooleanCallback callback) {
		MessageBox.showPlain(Icon.QUESTION, 
			    Captions.Q_ASK, 
			    message, 
			    new MessageBoxListener() {
			                    
			        @Override
			        public void buttonClicked(ButtonId buttonId) {			        	
			        		callback.execute(buttonId == ButtonId.YES);			        				            
			        }
			    }, 
			    ButtonId.YES, 
			    ButtonId.NO); 
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.dialogs.message.IMessageDialogs#ValidatonWarning()
	 */
	@Override
	public void ValidatonWarning() {
		MessageBox.showPlain(Icon.ERROR, 
			    Captions.ERROR_VALIDATION, 
			    Captions.ERROR_REQUIRED,			     
			    ButtonId.OK);				
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.dialogs.message.IMessageDialogs#Message(java.lang.String, mdb.core.ui.client.util.BooleanCallback)
	 */
	@Override
	public void Message(String message, final BooleanCallback callback) {
		
		MessageBox.showPlain(Icon.INFO, 
			    Captions.NONE, 
			    message, 
			    new MessageBoxListener() {
			                    
			        @Override
			        public void buttonClicked(ButtonId buttonId) {			        	
			        		callback.execute(true);			        				            
			        }
			    });
	}
}
