/**
 * 
 */
package mdb.core.ui.smartgwt.client.data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import mdb.core.shared.data.Params;
import mdb.core.shared.transformation.json.JSONTransformation;
import mdb.core.ui.client.data.DataConverter;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.grid.ListGridRecord;
/**
 * @author azhuk
 * Creation date: Dec 11, 2012
 *
 */
public  class ViewDataConverter {
	
	private static final Logger _logger = Logger.getLogger(ViewDataConverter.class.getName());
	
	/*
	 * Sample string for convert
	String jsonString = "[{street:\"55 bob lane\", city:\"New York\", state:\"NY\", zip:\"10021\"}, {street:\"66 bob lane\", city:\"Kiev\", state:\"UA\", zip:\"01024\"}]";
	*/
	
	private static final String NULL = "[null]";
	
	public static Record[] jSon2RecordArray(String jsonString) {
		
		Record[] records = null;
		try {
			if (jsonString != null && !jsonString.equalsIgnoreCase(NULL) )
			{	
	    		JavaScriptObject obj =  JSON.decode(jsonString);		    		
	    		records = ListGridRecord.convertToRecordArray(obj);	    		
			}	
			else {
				records = new Record[]{};
			}
		}
		catch (Exception e) {
			_logger.severe(e.getMessage());
		}
		return records;
	}	
	
	public static LinkedHashMap<String, String> jSon2LinkedHashMap(String jsonString, String keyField, String titleField ) {	
		
		return jSon2LinkedHashMap(jSon2RecordArray(jsonString), keyField, titleField ) ;
	}
	

	public static LinkedHashMap<String, String> jSon2LinkedHashMap(Record[] records, String keyField, String titleFields ) {		 
		LinkedHashMap<String, String>  toReturn = new LinkedHashMap<String, String>();		
		
		String[] arrTitleFields = null;				
		
		if ( titleFields.contains(";") ) {
			arrTitleFields = titleFields.split(";");
		}
		else {
			arrTitleFields  = new String[1];
			arrTitleFields[0] = titleFields;
		}
		
		for (Record rec : records) {
			StringBuilder out=new StringBuilder();
			boolean isFirst  = true;
			for (String title :  arrTitleFields) {
				String titleVal = rec.getAttribute(title) ;
				 if (!isFirst) {
					 out.append(" ");
				 }
				 out.append(titleVal);
				 isFirst = false;
			}
			 toReturn.put(	rec.getAttribute(keyField), out.toString() );
		}
		
		return toReturn;
	}
	
	public static Params recordToParams (Record rec) {		
		Params toReturn = new Params();
		if (rec != null) {
			 for ( Object key :  rec.toMap().keySet() ) {				 
				String val =  rec.getAttributeAsString((String)key);
				toReturn.add((String)key, val);		
			 }	
	    	}	
		return toReturn;
	}
	
	public static  String record2Json (Record rec ) {
		if (rec != null) {
			return JavaScriptObject2Json(rec.getJsObj(),true );
		}
		return null;
	}
	
	
	

	public static String JavaScriptObject2Json (JavaScriptObject value, boolean isCheckAttrList) {		
		String jsonStr = JSON.encode(value);		
		Map<String,String> map = DataConverter.jsonToMap(jsonStr);
		
		return  DataConverter.map2Json(map);
	}
	
	
}
