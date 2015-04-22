/**
 * 
 */
package mdb.core.shared.transformation.impl;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import mdb.core.shared.data.Field;
import mdb.core.shared.data.Fields;

/**
 * @author azhuk
 * Creation date: Sep 6, 2012
 *
 */
public class ResultSetToJSONTransformation  {
		
	private static final Logger _logger = Logger
			.getLogger(ResultSetToJSONTransformation.class.getName());
	
	private static DateFormat _dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	
	
	public  static Fields getFields (ResultSet resultSet) {
		
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

	public static String make (Fields fields, ResultSet resultSet) {
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

	private static String parseFieldValue(String value) {	
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
	
	private static Fields prepareFieldsName(Fields fields) {
		for (Field field : fields) {
			field.setColumnName(field.getColumnName().toUpperCase());
		}
		return fields;
	}	

	
	public static String make(ResultSet resultSet) {		
		return make (getFields(resultSet), resultSet);
	}
	
	public static List<HashMap<String, String>> deserialise(String jsonStr) {
		List<HashMap<String, String>> toReturn = null ;
		if (jsonStr == null || jsonStr.length() < 1 || jsonStr.equalsIgnoreCase("[null]") ){
		    return null;
		 }					
		
		Type listType = new TypeToken<List<HashMap<String, String>>>(){}.getType();
		toReturn = new Gson().fromJson(jsonStr, listType);		
				
		
		return toReturn;
		
		
	}

}
