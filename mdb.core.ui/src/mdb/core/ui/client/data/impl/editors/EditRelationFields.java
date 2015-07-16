/**
 * 
 */
package mdb.core.ui.client.data.impl.editors;

import mdb.core.ui.client.data.IBaseDataSource;
import mdb.core.ui.client.data.IDataSourceEditor;
import mdb.core.ui.client.data.fields.IMdbField;
import mdb.core.ui.client.events.IChangeEvent;
import mdb.core.ui.client.events.IChangeHandler;

/**
 * @author azhuk
 * Creation date: Mar 5, 2014
 *
 */
public class EditRelationFields implements IDataSourceEditor {

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.IDataSourceEditor#edit(mdb.core.ui.client.data.IDataSource, mdb.core.ui.client.data.impl.fields.MdbField)
	 */
	@Override
	public void edit(final IBaseDataSource ds, IMdbField field) {
		field.getItemEditor().addOnValueChangeEvent( new IChangeHandler() {
			
			@Override
			public void onChange(IChangeEvent event) {
					IMdbField changetField = (IMdbField) event.getValue() ;
				
				if (ds.isHasData()) {
					 ds.getDataComponent().setFieldValue(changetField.getLookUpFld().getMasterFldName(), changetField.getLookUpFld().getLookUpKeyValue() );
					 ds.getDataComponent().setFieldValue(changetField.getName(), changetField.getLookUpFld().getLookUpFldsValue() );				;					 					 
				}					
			}
		});		
	}	

}