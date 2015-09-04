/**
 * 
 */
package mdb.core.ui.smartgwt.client.data.validators;

import mdb.core.shared.data.EMdbFieldType;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.widgets.form.validator.IsFloatValidator;
import com.smartgwt.client.widgets.form.validator.IsIntegerValidator;
import com.smartgwt.client.widgets.form.validator.IsStringValidator;

/**
 * @author azhuk
 * Creation date: Jan 30, 2014
 *
 */
public class DataValidators {	
	
	
	public static void setDefault(DataSourceField field, EMdbFieldType fieldType) {		
		
		switch (fieldType) {
			case NUMBER:   			    			
					field.setValidators(new IsIntegerValidator());
				break;				
			case FLOAT:
			case CURRENCY:
					field.setValidators(new IsFloatValidator());
				break; 
			case VARCHAR:
					field.setValidators(new IsStringValidator());
				break;	
			case DATE:
					//field.setValidators(new IsDa());
				break;
			case BOOLEAN:			    			
					field.setValidators(new IsMdbBooleanValidator());
				break;		   	
			case LOOK_UP:
				break; 
			case FORM:
				break;
			case EDIT_BUTTON:
				break;
			case CHECK_COMBO_BOX:
				break;
			case INN:    			
			case FLOAT_E8:
				break;
			case FLOAT_FORMAT:			
				break;
			case MEMO:
			
				break;
			default :
			
		}
	}
	
	public static void setCustom(DataSourceField field, EMdbFieldType fieldType) {
	
	}
}
