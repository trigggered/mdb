/**
 * 
 */
package mdb.core.shared.transformation.impl;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import mdb.core.shared.data.Params;
import mdb.core.shared.data.Rows;
import mdb.core.shared.transformation.IDataTransformation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author azhuk
 * Creation date: Aug 1, 2012
 *
 */
public class JSONTransformation  implements IDataTransformation {


	@Override
	public String make(Rows data) {		
		Gson gson = new Gson();		
		return gson.toJson(data);
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.db.data.IDataTransformation#make(java.lang.String)
	 */
	@Override
	public Rows make(String value) {		
		Gson gson = new Gson();
		return  gson.fromJson(value, Rows.class);		
	}

	/* (non-Javadoc)
	 * @see mdb.core.shared.transformation.IDataTransformation#make(java.sql.ResultSet)
	 */
	@Override
	public String make(ResultSet resultSet) {
		return make(ResultSetTransformation.make(resultSet));		
	}
	
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
	
}
