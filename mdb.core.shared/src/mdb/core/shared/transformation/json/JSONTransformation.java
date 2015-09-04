/**
 * 
 */
package mdb.core.shared.transformation.json;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import mdb.core.shared.data.Params;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author azhuk
 * Creation date: Jul 28, 2015
 *
 */
public class JSONTransformation {
	private static final Logger _logger = Logger.getLogger(JSONTransformation.class
			.getName());
	
	
	 public static Map<String,String>  json2Map(String value) {		
			Map<String,String> toReturn ;
			if (value !=null) {
				Type mapType = new TypeToken<Map<String,String>>(){}.getType();  		
				toReturn = new Gson().fromJson(value, mapType);
			}		
			else toReturn = new HashMap<String, String>();
			return toReturn;
		}
		 

			
		 
		 public static String  map2json(Map<String,String> value) {
			 String toReturn = null;
			if (value !=null) {
				Type mapType = new TypeToken<Map<String,String>>(){}.getType();  		
				toReturn = new Gson().toJson(value, mapType);
			}	

			return toReturn;
		}
		 
			public  static Params json2Params(String value) {
				Params toReturn =  new Params();	
				  		
				Map<String,String> map =  json2Map (value);
				
				for (Entry<String, String>  entry : map.entrySet()) {
					 toReturn.add(entry.getKey(), entry.getValue());
				}				
				return toReturn;
			}
			
			
			public static List<Map<String, String>> json2ListMap (String jsonStr) {
				List<Map<String, String>> toReturn = null ;
				if (jsonStr == null || jsonStr.length() < 1 || jsonStr.equalsIgnoreCase("[null]") ){
				    return null;
				 }					
				
				Type listType = new TypeToken<List<HashMap<String, String>>>(){}.getType();
				toReturn = new Gson().fromJson(jsonStr, listType);		
						
				
				return toReturn;		
				
			}
}
