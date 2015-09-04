/**
 * 
 */
package mdb.core.ui.smartgwt.client.data.fields.editors;


import mdb.core.ui.client.data.fields.editors.ICustomItemEditor;
import mdb.core.ui.client.events.IButtonClickEvent;
import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.events.IChangeEvent;
import mdb.core.ui.client.events.IChangeHandler;
import mdb.core.ui.smartgwt.client.view.dialogs.SelectDialog;

import com.smartgwt.client.data.Record;

/**
 * @author azhuk
 * Creation date: Feb 13, 2014
 *
 */
public class ButtonItemSelectEditor  extends ButtonItemEditor implements ICustomItemEditor {
	
	private IChangeHandler _changeHandler;
	
	int _entityId;	

	
	public ButtonItemSelectEditor(int entityId) {
		_entityId = entityId;	
		
		addButtonClickEvent(new IButtonClickEvent() {    			
    				
					@Override
					public void onClickEvent() {
						
						SelectDialog.view(_entityId, false, new ICallbackEvent<Record[]>() {
							
							@Override
							public void doWork(Record[] data) {
								
								getMdbField().getLookUpFld().setLookUpKeyValue( data[0].getAttribute(getMdbField().getLookUpFld().getLookUpKeyName() ));
								getMdbField().getLookUpFld().setLookUpFldsValue( data[0].getAttribute(getMdbField().getLookUpFld().getlookUpFldsName() ));
								
								if (_changeHandler!=null) {
									
									_changeHandler.onChange(new IChangeEvent() {
										
										@Override
										public Object getValue() {											
											return getMdbField();
										}
									});
								}
							}
						});						
					}
				});
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.impl.editors.ICustomItemEditor#addOnValueChangeEvent(mdb.core.ui.client.events.IChangeHandler)
	 */
	@Override
	public void addOnValueChangeEvent(IChangeHandler value) {
		_changeHandler = value;		
	}
	
	@Override
	public IChangeHandler getOnValueChangeEvent() {
		return _changeHandler;
	}
	

}
