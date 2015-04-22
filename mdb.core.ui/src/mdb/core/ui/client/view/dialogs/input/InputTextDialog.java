/**
 * 
 */
package mdb.core.ui.client.view.dialogs.input;

import java.util.logging.Logger;

import mdb.core.ui.client.events.IDoubleValuesCallbackEvent;
import mdb.core.ui.client.view.dialogs.BaseDialogs;

import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;

/**
 * @author azhuk
 * Creation date: Aug 11, 2014
 *
 */
public class InputTextDialog extends BaseDialogs {
	private static final Logger _logger = Logger
			.getLogger(InputTextDialog.class.getName());

	DynamicForm _form;  
	TextAreaItem _textAreaItem;
	IDoubleValuesCallbackEvent<Boolean, String>  _callback;
	String _caption; 
	StaticTextItem _staticTextItem;
	

	 
	public InputTextDialog (String caption, IDoubleValuesCallbackEvent<Boolean, String> callback) {		
		
		_callback = callback;
		_caption = caption;
		_staticTextItem.setValue(_caption);
		setTitle("Введите краткое описание документа");		 
	}
	
	
	public InputTextDialog (String caption, String title, String editValue, IDoubleValuesCallbackEvent<Boolean, String> callback) {
		_callback = callback;
		_caption = caption;
		_staticTextItem.setValue(_caption);
		_textAreaItem.setTitle(title);
		_textAreaItem.setValue(editValue);
		setTitle(title);
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.dialogs.BaseDialogs#closeBtnClickEvent()
	 */
	@Override
	public void closeBtnClickEvent() {
		hide();
		_callback.execute(false, null);

	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.dialogs.BaseDialogs#okBtnClickEvent()
	 */
	@Override
	public void okBtnClickEvent() {
		if (validate()) {
			hide();
			_callback.execute(true, _textAreaItem.getValueAsString());
		}
	}
	
	
	public Boolean validate() {
		 return _form.validate();
	}
	 
	@Override
	protected void createContextLayout() {
		_form = new DynamicForm();							
		_form.setHeight("90%");		
		_form.setWidth100();  
		_form.setPadding(5);
		_form.setLayoutAlign(VerticalAlignment.BOTTOM);		
		
		_staticTextItem = new StaticTextItem ();
		_staticTextItem.setValue(_caption);		
		_staticTextItem.setTitle(null);
		
		_textAreaItem = new TextAreaItem();  
        _textAreaItem.setTitle("Краткое описание документа");		
        _textAreaItem.setRequired(true);
        
        _textAreaItem.setWidth(250);
        _textAreaItem.setHeight(250);
        
        _form.setItems(_staticTextItem, _textAreaItem);        
		
		addItem(_form);
	
	}
}
