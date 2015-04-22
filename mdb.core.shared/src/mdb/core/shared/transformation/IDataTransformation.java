/**
 * 
 */
package mdb.core.shared.transformation;

import java.sql.ResultSet;

import mdb.core.shared.data.Rows;



/**
 * @author azhuk
 * Creation date: Aug 1, 2012
 *
 */
public interface IDataTransformation {
	
	public String make (ResultSet resultSet);
	
	public Rows make (String  value);
	public String make (Rows  value);
	
}
