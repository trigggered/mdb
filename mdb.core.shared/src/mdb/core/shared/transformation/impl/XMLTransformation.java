/**
 * 
 */
package mdb.core.shared.transformation.impl;

import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.sql.rowset.WebRowSet;
import mdb.core.shared.data.Rows;
import mdb.core.shared.transformation.IDataTransformation;


/**
 * @author azhuk
 * Creation date: Aug 1, 2012
 *
 */
public class XMLTransformation implements IDataTransformation {
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
	
	/* (non-Javadoc)
	 * @see mdb.core.db.data.IDataConversion#doing(java.sql.ResultSet)
	 */
	@Override
	public String make(ResultSet resultSet) {
		WebRowSet wrs = resultSet2WebRowSet(resultSet);
		return  serialization(wrs);
		
	}


	/* (non-Javadoc)
	 * @see mdb.core.db.data.IDataTransformation#make(java.lang.String)
	 */
	@Override
	public Rows make(String value) {
		return null;	
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.core.db.data.IDataTransformation#make(java.lang.String)
	 */
	@Override
	public String make(Rows value) {
		return null;	
	}
}
