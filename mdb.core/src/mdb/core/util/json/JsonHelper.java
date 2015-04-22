/**
 * 
 */
package mdb.core.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author azhuk
 * Creation date: May 13, 2013
 *
 */
public class JsonHelper {

	
	private static GsonBuilder _builder;
	
	static {
		_builder = new GsonBuilder();
		_builder.registerTypeAdapter(Boolean.class, new JsonBooleanSerializer());
	}
	
	public static  Gson  getGson() {
		return _builder.create();
	}
}
