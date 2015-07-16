/**
 * 
 */
package mdb.core.vaadin.ui.components.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import mdb.core.shared.utils.comparator.ComparatorByPosition;
import mdb.core.ui.client.data.fields.IMdbField;
import mdb.core.vaadin.ui.data.IDataSource;
import mdb.core.vaadin.ui.data.fields.MdbField;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;

/**
 * @author azhuk
 * Creation date: Sep 2, 2015
 *
 */
public class DataForm  extends FormLayout {
	private static final Logger _logger = Logger.getLogger(DataForm.class
			.getCanonicalName());
	
	
	
	private  FieldGroup _binder; 
	
	public DataForm () {
		
		this.setSizeFull();
		this.setWidth("100%");
		this.setMargin(true);
		this.setSpacing(true);		
	}
	
	public boolean validate() {
		return _binder.isValid();
	}
	
	public boolean isHaseChanges() {
		
		return _binder.isModified();
	}
	
	public void save ()  {
		try {
			_binder.commit();
		} catch (CommitException e) {
			_logger.severe(e.getMessage() );
		}
		
	}
	
	
	public void bindData(IDataSource ds ) {
		Item data = null;
		bindData(ds, data);
		
	}
	
	public void bindData(IDataSource ds, Item data) {
		_binder = new FieldGroup(data);
		
		
		List<IMdbField> fields = new ArrayList<IMdbField> (ds.getDataSourceFields().getMdbFieldMap().values());
		Collections.sort(fields, new ComparatorByPosition());		
	
		for (IMdbField   field :  fields ) {
			
			 MdbField mdbFld = (MdbField)field;						 

 			if ( mdbFld.isVisivble() )  {

 				Field<?>  fld = (Field<?>) mdbFld.getDataSourceField().generateCell(null, null, mdbFld.getName());
 				fld.setCaption(field.getTitle());
 				fld.setSizeFull();
 				_binder.bind(fld, mdbFld.getName()); 
				addComponent(fld);
			}
		}		
	 					
	}
	
	public Item getItem() {
		return _binder.getItemDataSource();
	}

	/**
	 * 
	 */
	public void loseChange() {
		_binder.discard();
		
	}
	
	
}
