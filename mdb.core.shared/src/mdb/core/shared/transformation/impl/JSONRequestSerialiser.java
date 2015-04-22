/**
 * 
 */
package mdb.core.shared.transformation.impl;

import java.lang.reflect.Type;
import java.util.List;

import mdb.core.shared.transformation.IRequestSerialiser;
import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.Request;
import mdb.core.shared.transport.RequestEntity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

/**
 * @author azhuk
 * Creation date: Aug 3, 2012
 *
 */
public class JSONRequestSerialiser implements IRequestSerialiser{	
	
	private final GsonBuilder _gsonBuilder;
	private Gson _gsonForDeserialise;
	private Gson _gsonForSerialise = new Gson();
	private Type _listOfRequestObject = new TypeToken<List<Request>>(){}.getType();
	
	public class RequestDataJsonDeserializer implements JsonDeserializer<IRequestData> {
	

		private Gson _gson = new Gson();
		/* (non-Javadoc)
		 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
		 */
		@Override
		public IRequestData deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			return _gson.fromJson(json, RequestEntity.class);			
		}		
	}
	
	
	public JSONRequestSerialiser() {
		_gsonBuilder = new GsonBuilder();
		_gsonBuilder.registerTypeAdapter(IRequestData.class, new RequestDataJsonDeserializer());
		_gsonForDeserialise = _gsonBuilder.create();
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.gateway.IRequestSerialiser#serialize(mdb.gateway.IRequest)
	 */
	@Override
	public String serialize(Request value) {
		String toReturn = _gsonForSerialise.toJson(value);
		//_logger.log(Level.INFO, toReturn);
		return toReturn;
	}

	/* (non-Javadoc)
	 * @see mdb.gateway.IRequestSerialiser#deserialize(java.lang.String)
	 */
	@Override
	public Request deserialize(String value) {
		return _gsonForDeserialise.fromJson(value, Request.class);		
	}


	/* (non-Javadoc)
	 * @see mdb.core.shared.transformation.IRequestSerialiser#listSerialize(java.util.List)
	 */
	@Override
	public String listSerialize(List<Request> list) {				
		return  _gsonForSerialise.toJson(list, _listOfRequestObject );
		
	}


	/* (non-Javadoc)
	 * @see mdb.core.shared.transformation.IRequestSerialiser#listDeserialize(java.lang.String)
	 */
	@Override
	public List<Request> listDeserialize(String value) {				
		List<Request> toReturn = _gsonForDeserialise.fromJson(value, _listOfRequestObject);		
		return toReturn;
	}	
	
}
