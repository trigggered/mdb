/**
 * 
 */
package test.mdb.core.util.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author azhuk
 * Creation date: Aug 3, 2012
 *
 */
public class TestJsonGoogleImpl extends TestCase {
	
	class Event {
		  private String name;
		  private String source;
		  private Event(String name, String source) {
		    this.name = name;
		    this.source = source;
		  }
		}
	
	private static final Logger _logger = Logger
			.getLogger(TestJsonGoogleImpl.class.getCanonicalName());
	
	@Test
	public void testJsonSerialize() {
	
		Collection collToJson = new ArrayList();
		collToJson.add("hello");
		collToJson.add(5);
		collToJson.add(new Event("GREETINGS", "guest"));
		
		Gson gson = new Gson();
		String strJson = gson.toJson(collToJson);
		assertNotNull(strJson);
	
		_logger.log(Level.INFO, strJson);
		
		Collection collFromJson =  gson.fromJson(strJson, Collection.class);
		
		assertNotNull(collFromJson);
		
		_logger.log(Level.INFO, "collToJson.toArray()[0]=" +collToJson.toArray()[0]);
		_logger.log(Level.INFO, "collFromJson.toArray()[0]=" +collFromJson.toArray()[0]);
		
		_logger.log(Level.INFO, "collFromJson.toArray()[0]=" +collFromJson.toArray()[2]);
		
		
		assertEquals(collFromJson.toArray()[0], collToJson.toArray()[0]);

	}
	
	public void testJsonDeserialize() {
		
		String strJson = "{\"CLID\":11,\"ID_SEG\":\"3\", \"ID\" : \"999\"  }";	
		
		strJson = strJson.replaceAll("\\{|}", "");
		String[]  arr = strJson.toString().split(",");
		for (String str : arr) {
			 String[] pairKeyVal = str.split(":");
			 _logger.log(Level.INFO, pairKeyVal[0]);
		}				
		
	}
	
	public void testJsonMapDeserializeSample() {							
		String  easyString= "{ \"data\" : { \"field1\" : \"value1\", \"field2\" : \"value2\"}}";
		Type mapType = new TypeToken<Map<String, Map<String,String>>>(){}.getType();  
		
		Map<String, Map<String,String>> son = new Gson().fromJson(easyString, mapType);
		
		 for ( Entry<String, Map<String,String>>  entry : son.entrySet()) {
			 assertEquals("data", entry.getKey());
			 Map<String, String> map  = entry.getValue();
			 assertEquals(2, map.size());
			 
			 assertTrue(map.containsKey("field1"));
			 assertEquals("value2", map.get("field2"));						 
		 }		
	}
	
	
	public void testJsonMapDeserialize() {			
		
		String  jsonStr= 		"{"+
									"	\"CLID\":2,"+ 
									"	\"USRID\":\"400\","+ 
									"	\"ID_SEG\":\"1\","+ 
									"	\"INN\":\"\","+ 
									"	\"NAME\":\"Аватар\","+ 
									"	\"DTA_OPEN\":\"\","+ 
									"	\"DTA_CLOSE\":\"\" "+
									"}";							
		
		Type mapType = new TypeToken<Map<String,String>>(){}.getType();  		
		Map<String,String> map = new Gson().fromJson(jsonStr, mapType);
		
		 
		
		assertEquals(7, map.size());
		assertTrue( map.containsKey("CLID"));
		assertTrue( map.containsKey("INN"));
		
		assertEquals("2", map.get("CLID"));
		assertEquals("400", map.get("USRID"));
		
		assertEquals("Аватар", map.get("NAME"));			 				
	}
	


	public void testMergeJson () {
		String  jsonStr1= 		"{"+
				"	\"CLID\":2,"+ 
				"	\"USRID\":\"400\","+ 
				"	\"ID_SEG\":\"1\","+ 
				"	\"INN\":\"\","+ 
				"	\"NAME\":\"Аватар\","+ 
				"	\"DTA_OPEN\":\"\","+ 
				"	\"DTA_CLOSE\":\"\","+
				"	\"IS_TRUE\":\"true\","+
				"	\"IS_FALSE\":\"\" "+
				"}";
		
		String  jsonStr2= 		"{"+
				"	\"CLID\":2,"+ 
				"	\"USRID\":\"500\","+ 
				"	\"ID_SEG\":\"1\","+ 
				"	\"INN\":\"\","+ 
				"	\"NAME\":\"Аватар\","+ 
				"	\"DTA_OPEN\":\"\","+ 
				"	\"DTA_CLOSE\":\"01.01.2012\","+
				"	\"IS_TRUE\":\"true\","+
				"	\"IS_FALSE\":\"false\" "+
				"}";
		
		Type mapType = new TypeToken<Map<String,String>>(){}.getType();  		
		
	//
		
		
		Map<String,String> map1 = new Gson().fromJson(jsonStr1.replaceAll("true", "1").replaceAll("false", "0"), mapType);
		Map<String,String> map2 = new Gson().fromJson(jsonStr2.replaceAll("true", "1").replaceAll("false", "0"), mapType);
		
		
		//Map<String,String> map1 = JsonHelper.getGson().fromJson(jsonStr1, mapType);
		//Map<String,String> map2 = JsonHelper.getGson().fromJson(jsonStr2, mapType);
		map1.putAll(map2);
		
		
		assertEquals("500", map1.get("USRID"));
		assertEquals("01.01.2012", map1.get("DTA_CLOSE"));
		assertEquals("2", map1.get("CLID"));
		assertEquals( "1", map1.get("IS_TRUE"));
		assertEquals( "0", map1.get("IS_FALSE"));
		
	}
	
}
