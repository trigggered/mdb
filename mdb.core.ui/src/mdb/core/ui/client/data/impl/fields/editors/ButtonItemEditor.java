/**
 * 
 */
package mdb.core.ui.client.data.impl.fields.editors;

import java.util.logging.Logger;

import mdb.core.ui.client.events.IButtonClickEvent;

import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

/**
 * @author azhuk
 * Creation date: Feb 13, 2014
 *
 */

 public  class ButtonItemEditor extends TextItemEditor {
	 
	private static final Logger _logger = Logger.getLogger(ButtonItemEditor.class
			.getName());  		  
	          
	    private IButtonClickEvent _buttonClickEvent;
	    
	    
	    public ButtonItemEditor() {  
	            
	            FormItemIcon formItemIcon = new FormItemIcon();  
	            setIcons(formItemIcon);  
	  
	            addIconClickHandler(new IconClickHandler() {  
	                public void onIconClick(IconClickEvent event) {	  
	                      onButtonClickEvent();                      
	                }					
	            });  
	        }  
	        
	    
	        protected void onButtonClickEvent() {
	        	if ( _buttonClickEvent!= null) {
	        		_buttonClickEvent.onClickEvent();
	        	}
	        }
	
	        public void addButtonClickEvent (IButtonClickEvent buttonClickEvent) {
	        	 _buttonClickEvent=buttonClickEvent;
	        }	        	            
	
}
