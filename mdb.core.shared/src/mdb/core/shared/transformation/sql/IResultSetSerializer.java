/**
 * 
 */
package mdb.core.shared.transformation.sql;

import java.sql.ResultSet;

import mdb.core.shared.data.Fields;

/**
 * @author azhuk
 * Creation date: Jul 28, 2015
 *
 */
public interface IResultSetSerializer {

	public String make(ResultSet resultSet);

	public String make(Fields fields, ResultSet resultSet);

	
}
