/**
 * 
 */
package mdb.core.shared.transformation.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mdb.core.shared.data.Field;
import mdb.core.shared.data.Fields;

/**
 * @author azhuk
 * Creation date: Jul 28, 2015
 *
 */
public class ResultSetFieldsExtractor {
	private static final Logger _logger = Logger
			.getLogger(ResultSetFieldsExtractor.class.getName());
	
	
	public static  Fields extract (ResultSet resultSet) {
		
		Fields  toReturn = new Fields();		
		
		try {
			ResultSetMetaData metaData =  resultSet.getMetaData();
			
			int i=1;
			for (; i<= metaData.getColumnCount(); i++) {		
					
			 Field field = toReturn.addField();
			 
			 field.setColumnName(metaData.getColumnName(i));
			 field.setCaption(metaData.getColumnLabel(i));
			 
			 field.setPrecision(metaData.getPrecision(i));
			 field.setScale(metaData.getScale(i));
			 field.setDisplaySize(metaData.getColumnDisplaySize(i));			 
			 
			 field.setSqlType(metaData.getColumnType(i));
			 field.setSQlTypeName(metaData.getColumnTypeName(i));
			 //_logger.info("Column name:" + metaData.getColumnName(i) +" Columnt Type name:"+metaData.getColumnTypeName(i)+" column type is:"+ metaData.getColumnType(i));
			}
			_logger.info("Field list success generated. Field count:" +i);
			
		} catch (SQLException e) {			
			_logger.log(Level.FINE, "Can not getMetaData from ResultSet " + e.getMessage() );			
		}
		return toReturn;
		
	}
}
