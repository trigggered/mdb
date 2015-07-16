/**
 * 
 */
package mdb.core.vaadin.ui.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import mdb.core.shared.transformation.json.JSONTransformation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

/**
 * @author azhuk
 * Creation date: Jul 23, 2015
 *
 */
public class IndexedJsonContainer extends IndexedContainer implements JsonContainer  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger _logger = Logger
			.getLogger(IndexedJsonContainer.class.getCanonicalName());
	

	public IndexedJsonContainer() {
		
	}
	
	
	
    public IndexedJsonContainer(JsonElement parsedJsonData) {
        if (parsedJsonData.isJsonArray()) {
            populateContainer(parsedJsonData.getAsJsonArray());
        } else if (parsedJsonData.isJsonObject()) {
            addJsonObject(parsedJsonData.getAsJsonObject());
        }
    }

    protected void populateContainer(JsonArray parsedJsonArray) {
        for (JsonElement j : parsedJsonArray) {
            if (j.isJsonObject()) {
                addJsonObject(j.getAsJsonObject());
            }
        }
       
    }

    protected Item addJsonObject(JsonObject jsonObject) {
        // use itemId generated by IndexedContainer
        Object itemId = addItem();
        Item i = getItem(itemId);

        for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            addContainerProperty(entry.getKey(), String.class, null);
            i.getItemProperty(entry.getKey()).setValue(
                    entry.getValue().getAsString());
        }
        return i;
    }
    
        
    protected void addField(String name) {
    	
    	addContainerProperty(name, String.class, null);
    }
    
    public String serialize() {
    	
    	String toReturn = null;
    	
    	for (Object propertyId : this.getContainerPropertyIds()) {    		    
    	
    	  for (Object itemId : this.getItemIds()) {
              Object ob = ((Item) this.getItem(itemId)).getItemProperty(propertyId).getValue();
          
    	  }
    }
    	
    	 JsonObject series = new JsonObject();
         JsonArray data = new JsonArray();
         
         Gson gson = new Gson();
         toReturn = gson.toJson(this);
		return toReturn;
         
    }
    
    public static String serialize(Item item) {    	
    	//element.getAsString()
    	String toReturn = null;
    	Map<String, String> map = new HashMap<String, String>();
    	
    	for ( Object itemId :  	item.getItemPropertyIds()) {
    		
    		map.put((String)itemId, (String)item.getItemProperty(itemId).getValue());
    	}
    	
    	toReturn = JSONTransformation.map2json(map);
         /*Gson gson = new Gson();
         toReturn = gson.toJson(item);*/
		return toReturn;
         
    }

}
