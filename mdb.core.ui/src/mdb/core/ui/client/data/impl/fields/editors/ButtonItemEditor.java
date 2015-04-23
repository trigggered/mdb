/**
 * 
 */
package mdb.core.ui.client.data.impl.fields.editors;

import mdb.core.ui.client.events.IButtonClickEvent;
import mdb.core.ui.client.events.IChangeEvent;

import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

/**
 * @author azhuk
 * Creation date: Feb 13, 2014
 *
 */

 public  class ButtonItemEditor extends TextItemEditor {
	 
	private IButtonClickEvent _buttonClickEvent;
	    
	    
	    public ButtonItemEditor() {  
	            
	            FormItemIcon formItemIcon = new FormItemIcon();  
	            setIcons(formItemIcon);  
	            formItemIcon.addFormItemClickHandler(new FormItemClickHandler() {
					
					@Override
					public void onFormItemClick(FormItemIconClickEvent event) {
						// TODO Auto-generated method stub
						 onButtonClickEvent();    
					}
				});
	            
	  	    		PickerIcon clearPicker = new PickerIcon(PickerIcon.CLEAR, new FormItemClickHandler() {
	    			
	    			@Override
	    			public void onFormItemClick(FormItemIconClickEvent event) {	    				
	    				
	    				getMdbField().getLookUpFld().clear();
	    				
	    				if (getOnValueChangeEvent()!=null) {
	    					
	    					getOnValueChangeEvent().onChange(new IChangeEvent() {
	    						
	    						@Override
	    						public Object getValue() {											
	    							return getMdbField();
	    						}
	    					});
	    				}
	    				
	    			}
	    		});
	    				 
	    		    
	    		  
	    		setIcons(clearPicker, formItemIcon);	    			    		
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
