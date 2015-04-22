/**
 * 
 */
package mdb.core.shared.data;

import java.util.ArrayList;


/**
 * @author azhuk
 * Creation date: Aug 9, 2012
 *
 */
public class Rows implements  java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public class Row  extends ArrayList<Object>  implements  java.io.Serializable {
		private static final long serialVersionUID = 1L;		
		
		public Row() {
			
		}
	}

	private Fields _fields; 
	private ArrayList<Rows.Row> _rows; 
	
	public Rows (Fields fields) {
		_fields = fields;
		_rows = new ArrayList<Rows.Row>();
	}
	
	public Rows () {
		_fields = new Fields();
		_rows = new ArrayList<Rows.Row>();
	}
	
	public Row addRow () {
		Row row = new Row();
		_rows.add(row);
		return row;
	}
	
	public Row addRow (Row row) {		
		_rows.add(row);
		return row;
	}
	
	public ArrayList<Rows.Row> getRows() {
		return _rows;
	}
	
	public Fields getFields() {
		return _fields;
	}	
	
	
	
}
