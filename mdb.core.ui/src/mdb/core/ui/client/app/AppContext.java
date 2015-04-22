/**
 * 
 */
package mdb.core.ui.client.app;

import java.util.HashMap;





/**
 * @author azhuk
 * Creation date: Jun 26, 2014
 *
 */
public class AppContext implements IAppContext {

	private HashMap<String, String> _context = new HashMap<String,String>();
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.app.IAppContext#setValue(java.lang.String, java.lang.String)
	 */
	@Override
	public void setValue(String name, String value) {
		_context.put(name, value);
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.app.IAppContext#getValue(java.lang.String)
	 */
	@Override
	public String getValue(String name) {
		if(	_context.containsKey(name) ) {
			return _context.get(name);
		}
		return null;
	}
	
}
