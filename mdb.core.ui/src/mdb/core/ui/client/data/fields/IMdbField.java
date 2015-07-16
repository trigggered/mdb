/**
 * 
 */
package mdb.core.ui.client.data.fields;

import mdb.core.shared.data.EMdbFieldType;
import mdb.core.shared.utils.comparator.IPosition;
import mdb.core.ui.client.data.fields.editors.ICustomItemEditor;

/**
 * @author azhuk
 * Creation date: Jul 16, 2015
 *
 */
public interface IMdbField extends IPosition {

	public abstract String getName();

	public abstract void setName(String _name);


	public abstract String getTitle();

	public abstract void setTitle(String value);
	
	
	public abstract EMdbFieldType getMdbFieldType();

	public abstract void setMdbFieldType(EMdbFieldType _mdbFieldType);

	public abstract LookUpFld getLookUpFld();

	public abstract void setLookUpFld(LookUpFld _lookUpFld);

	public abstract ICustomItemEditor getItemEditor();

	public abstract void setItemEditor(ICustomItemEditor value);

	public abstract boolean isVisivble();

	public abstract void setVisivble(boolean _fVisivble);

	public abstract boolean isReadOnly();

	public abstract void setReadOnly(boolean _fReadOnly);
	
	public boolean isPrimaryKey();

	public void setPrimaryKey(boolean value);
	

	

}