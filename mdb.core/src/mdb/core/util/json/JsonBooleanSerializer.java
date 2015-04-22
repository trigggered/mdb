/**
 * 
 */
package mdb.core.util.json;


import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @author azhuk
 * Creation date: May 13, 2013
 *
 */
public class JsonBooleanSerializer   implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {

	/* (non-Javadoc)
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public Boolean deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {		
		return arg0.getAsInt() == 1 ? true : false;
	}

	/* (non-Javadoc)
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(Boolean arg0, Type arg1,
			JsonSerializationContext arg2) {
		 return new JsonPrimitive(arg0 ? 1 : 0);		
	}
}
