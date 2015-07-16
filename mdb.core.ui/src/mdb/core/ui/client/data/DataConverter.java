/**
 * 
 */
package mdb.core.ui.client.data;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

/**
 * @author azhuk
 * Creation date: Jul 16, 2015
 *
 */
public class DataConverter {
	private static final Logger _logger = Logger.getLogger(DataConverter.class
			.getName());
	
	public static String map2Json(Map<String, String> map) {
	    String json = "";
	    if (map != null && !map.isEmpty()) {
	        JSONObject jsonObj = new JSONObject();
	        for (Map.Entry<String, String> entry: map.entrySet()) {
	            jsonObj.put(entry.getKey(), new JSONString(entry.getValue()));
	        }
	        json = jsonObj.toString();
	    }
	    return json;
	}
	
	public static Map<String, String> jsonToMap(String jsonStr) {
	    Map<String, String> map = new HashMap<String, String>();

	    JSONValue parsed = JSONParser.parseStrict(jsonStr);
	    JSONObject jsonObj = parsed.isObject();
	    if (jsonObj != null) {
	    	
	        for (String key : jsonObj.keySet()) {       		          
	            
	        	if ( !key.equals("__module") ) {
	        		
			            JSONValue jValue = jsonObj.get(key);
			            String value = null;
			            
			             if ( jValue.isString() != null) {
			            	 value = jValue.isString().stringValue();
			             }else if(jValue.isBoolean()!= null)  {
			            	 value = String.valueOf( jValue.isBoolean().booleanValue());
			             }else if (jValue.isNumber() !=null ) {
			            	 value=  String.valueOf(jValue.isNumber().doubleValue());	            	 
			             }else if (jValue.isNull()!=null) {
			            	 value="";
			             }            	            	 	             	            
			            map.put(key, value == null?"":value);
	        	}
	        }
	    }
	    return map;
	}
	
}
