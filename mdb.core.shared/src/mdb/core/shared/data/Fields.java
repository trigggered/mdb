/**
 * 
 */
package mdb.core.shared.data;

import java.util.ArrayList;

import mdb.core.shared.exceptions.FieldNotFoundException;

/**
 * @author azhuk
 * Creation date: Aug 9, 2012
 *
 */
public class Fields extends  ArrayList<Field> implements IData.IFields,  java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public Field addField() {
		Field toReturn = new Field();
		add(toReturn);
		return toReturn;		
	}
	
	
	
	public int getColumnIndexByName(String colName) throws FieldNotFoundException {
		
		for (int i = 0; i< this.size(); i++ ) {
			 if (this.get(i).getColumnName().equalsIgnoreCase(colName)) {
				 return i;
			 }
		}
		throw new FieldNotFoundException(colName);		
	}
	
	public Field getFieldByName(String colName) throws FieldNotFoundException {
		
		for (int i = 0; i< this.size(); i++ ) {
			 String collName = this.get(i).getColumnName();
			 if (collName.equalsIgnoreCase(colName)) {
				 return this.get(i);
			 }
		}

		throw new FieldNotFoundException(colName);		
	}

	
}
