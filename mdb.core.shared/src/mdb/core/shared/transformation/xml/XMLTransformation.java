/**
 * 
 */
package mdb.core.shared.transformation.xml;

import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.sql.rowset.WebRowSet;


/**
 * @author azhuk
 * Creation date: Aug 1, 2012
 *
 */
public class XMLTransformation  {
	private static final Logger _logger = Logger
			.getLogger(XMLTransformation.class.getCanonicalName());

	@Deprecated
	public static WebRowSet resultSet2WebRowSet(ResultSet ars) {
		
		if (ars ==null) {
			_logger.info( "Input argument ResultSet is NULL");
			return null;
		}
		
		WebRowSet wrs = createWebRowSet();
		try {			
			wrs.populate(ars);
			ars.close();			
		} catch (Exception ex) {
			_logger.severe(ex.getMessage());			
		}
		return wrs;
	}
	
	@Deprecated
	private  static WebRowSet createWebRowSet() {		
		return null;
	}
	
	private  static String serialization (WebRowSet rs)  {		
		_logger.info("Start serialization");
		if (rs == null) {
			_logger.info("Input argument WebRowSet is NULL");
			return null;
		}
		
		StringWriter stringWriter = new StringWriter();
		try {
			rs.writeXml(stringWriter);
		} catch (SQLException e) {
			_logger.severe("SQLException ");
			
		}
		_logger.info("End serialization");
		return  stringWriter.toString();
	}
	

}
