/**
 * 
 */
package mdb.core.vaadin.ui.data;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.vaadin.data.Container;

/**
 * @author azhuk
 * Creation date: Jul 23, 2015
 *
 */
public interface JsonContainer 	extends Container.Indexed, Container.Sortable,
    Container.Filterable, Container.SimpleFilterable {

	public static	class Factory {
		
		static JsonParser parser = new JsonParser();
    /**
     * Creates a new instance of JsonContainer for the given JSON string.
     * 
     * @throws IllegalArgumentException
     *             if the given JSON string cannot be parsed.
     * @param json
     *            JSON data for populating the container.
     * @return new JsonContainer instance populated with the given data.
     * 
     */
    public static JsonContainer newInstance(String json) {
        
        try {
            return new IndexedJsonContainer(parseJson(json));
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException(
                    "Cannot parse the given JSON: \"" + json + "\"");
        }
    }
    


    public static JsonElement parseJson (String json) {    	
    	return parser.parse(json);
    }
}


}
