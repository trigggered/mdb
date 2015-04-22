/**
 * 
 */
package mdb.core.ui.client.app;


/**
 * @author azhuk
 * Creation date: Jun 26, 2014
 *
 */
public interface IAppContext {

	public void setValue(String name, String value);
	
	public String getValue(String name);
}
