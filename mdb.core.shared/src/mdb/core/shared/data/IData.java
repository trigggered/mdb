/**
 * 
 */
package mdb.core.shared.data;

import java.util.ArrayList;

import mdb.core.shared.data.Rows.Row;


/**
 * @author azhuk
 * Creation date: Sep 6, 2012
 *
 */
public interface IData {
	
	public interface IRows {
		public Row addRow ();		
		public Row addRow (Row row);
		public ArrayList<Rows.Row> getRows();
		public Fields getFields();
	}
	
	public interface IRow {

	}
	
	public interface IFields {
		public Field addField();
	}
	
	
	
	public IRows getRows();
	public IFields getFields();
}
