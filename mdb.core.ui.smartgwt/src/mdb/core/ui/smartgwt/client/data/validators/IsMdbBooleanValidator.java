/**
 * 
 */
package mdb.core.ui.smartgwt.client.data.validators;

import mdb.core.shared.data.EMdbBooleanType;

import com.smartgwt.client.widgets.form.validator.CustomValidator;

/**
 * @author azhuk
 * Creation date: Jan 30, 2014
 *
 */
public class IsMdbBooleanValidator extends CustomValidator {
	
	@Override
    protected boolean condition(Object value) {
        
		if (value == null || value.toString().isEmpty() ) { 
			return true;
		}
		
		 Integer boolVal = Integer.valueOf(value.toString());
		
		return value == null || boolVal.intValue() == EMdbBooleanType.True.getValue() || 
					boolVal.intValue()== EMdbBooleanType.False.getValue();
    }
}
