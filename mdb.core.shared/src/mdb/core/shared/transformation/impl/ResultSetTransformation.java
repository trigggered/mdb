/**
 * 
 */
package mdb.core.shared.transformation.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Logger;

import mdb.core.shared.data.Field;
import mdb.core.shared.data.Fields;
import mdb.core.shared.data.Rows;

/**
 * @author azhuk
 * Creation date: Sep 6, 2012
 *
 */
public class ResultSetTransformation  {
	
	private static final Logger _logger = Logger.getLogger(ResultSetTransformation.class.getName());
	
	private  static Fields getFields (ResultSet resultSet) {
		
		Fields  toReturn = new Fields();		
		
		try {
			ResultSetMetaData metaData =  resultSet.getMetaData();
			int i=1;
			for (;i<= metaData.getColumnCount(); i++) {
		
					
			 Field field = toReturn.addField();
			 
			 field.setColumnName(metaData.getColumnName(i));
			 field.setCaption(metaData.getColumnLabel(i));
			 
			 field.setPrecision(metaData.getPrecision(i));
			 field.setScale(metaData.getScale(i));
			 field.setDisplaySize(metaData.getColumnDisplaySize(i));			 
			 
			 field.setSqlType(metaData.getColumnType(i));
			 field.setSQlTypeName(metaData.getColumnTypeName(i));			 			 
			}
			
			_logger.info("Field list success generated. Field count:" +i);
			
		} catch (SQLException e) {			
			_logger.severe("Can not getMetaData from ResultSet");			
		}
		return toReturn;
		
	}


	public static Rows make(ResultSet resultSet) {
		
		Rows data = new Rows(getFields(resultSet));
		/*
		try {
			resultSet.first();
			Boolean b = true;
			while (resultSet.next() && b) {
			
				Rows.Row row = data.addRow();
				
				for (int columnIndex=1; columnIndex<= data.getFields().size(); columnIndex++) {					
					row.add(resultSet.getString(columnIndex));
				}
				b = false;
			}			
		} catch (SQLException e) {			
			//_logger.log(Level.INFO, "SQLException");
			e.printStackTrace();
		}
		*/
	return data;
	}

}
