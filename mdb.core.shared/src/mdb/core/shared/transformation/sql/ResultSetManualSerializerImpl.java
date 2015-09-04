/**
 * 
 */
package mdb.core.shared.transformation.sql;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mdb.core.shared.data.Field;
import mdb.core.shared.data.Fields;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author azhuk
 * Creation date: Sep 6, 2012
 *
 */
public class ResultSetManualSerializerImpl  implements IResultSetSerializer{
		
	private static final Logger _logger = Logger
			.getLogger(ResultSetManualSerializerImpl.class.getName());
	
	private static DateFormat _dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	
	

	@Override
	public  String make (Fields fields, ResultSet resultSet) {
		fields = prepareFieldsName (fields);
		
		String toReturn = null;
		try {		
		
			Boolean exit = true;
			
			long recCount = 0;
			while (resultSet.next() && exit ) {
				recCount++;
				String row = "";				
 
				
				if (toReturn != null) {
					 toReturn = toReturn +",";
				}				
				
				for (int columnIndex=1; columnIndex<= fields.size(); columnIndex++) {										
					
					Field fld = fields.get(columnIndex-1);
					switch (fld.getSqlType()) {
						case java.sql.Types.DATE:
						case java.sql.Types.TIMESTAMP:
							Date dateVal = resultSet.getDate(columnIndex);
							row  =dateVal!=null?
										row + "\""+ fld.getColumnName() +"\" : \"" +parseFieldValue(_dateFormat.format(resultSet.getDate(columnIndex)))+"\"":
											row + "\""+ fld.getColumnName() +"\" : \"" +parseFieldValue(null)+"\"";
							//_logger.info("Date field value is:"+resultSet.getString(columnIndex) +" Convert to value format:" +_dateFormat.format(resultSet.getDate(columnIndex)));
							break;
						default:
							row  =row + "\""+ fld.getColumnName() +"\" : \"" +parseFieldValue(resultSet.getString(columnIndex))+"\"";
							break;
					}					
										
					if (columnIndex != fields.size() )
						row = row +",\n";
					else
						row = row +" \n";
				}				
		
				
				if (toReturn != null) {
					toReturn= toReturn+"{"+row+"}";
				}
				else
					{
						toReturn= "{"+row+"}";				
					}
						
			}			
			_logger.info( "ResultSet success transformation into json. Record count: "+recCount );
		} catch (SQLException e) {			
			_logger.severe(e.getMessage() );
			
		}		
	return "["+toReturn+"]";
	}

	private  String parseFieldValue(String value) {	
		if (value != null) {						
			value = value.replaceAll("\r\n", "\\\\r\\\\n");
			value = value.replaceAll("\"","\\\\\"");
			value = value.replaceAll("\n", "\\\\n");
			
			value = value.replaceAll("(\\r|\\n)", "");
			
		}
		else {
			value ="";
		}
		return value;
	}
	
	private  Fields prepareFieldsName(Fields fields) {
		for (Field field : fields) {
			field.setColumnName(field.getColumnName().toUpperCase());
		}
		return fields;
	}	

	
	@Override
	public  String make(ResultSet resultSet) {		
		return make (ResultSetFieldsExtractor.extract(resultSet), resultSet);
	}
	


}
