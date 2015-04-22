/**
 * 
 */
package test.mdb.core.util.json;

import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


import org.junit.Test;


import com.google.gson.reflect.TypeToken;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
 
/**
 * @author azhuk
 * Creation date: Aug 15, 2013
 *
 */
public class TestJsonGwtGoogleImpl {

	
	
	public static Map<String, String> jsonToMap(String jsonStr) {
	    Map<String, String> map = new HashMap<String, String>();

	    JSONValue parsed = JSONParser.parseStrict(jsonStr);
	    JSONObject jsonObj = parsed.isObject();
	    if (jsonObj != null) {
	        for (String key : jsonObj.keySet()) {	        	
	            map.put(key, jsonObj.get(key).isString().stringValue());
	        }
	    }

	    return map;
	}
	

	@Test
	public void testJsonMapDeserialize2() {			
		
		String  jsonStr="{\"ID_DENTITY\":\"4989\",\"ID_DENTITY_OLD_VAL\":\"-1\"}";
		Type mapType = new TypeToken<Map<String,String>>(){}.getType();  		
		Map<String,String> map = jsonToMap(jsonStr);
		assertEquals("4989", map.get("ID_DENTITY"));
	}

	
}
