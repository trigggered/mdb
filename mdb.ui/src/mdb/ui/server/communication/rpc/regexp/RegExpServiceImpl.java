/**
 * 
 */
package mdb.ui.server.communication.rpc.regexp;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mdb.ui.client.communication.rpc.regexp.RegExpService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author azhuk
 * Creation date: Oct 5, 2012
 *
 */
public class RegExpServiceImpl  extends RemoteServiceServlet implements RegExpService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.rpc.regexp.RegexpService#Parse(java.lang.String, java.lang.String)
	 */
	@Override
	public String[] Parse(String value, String paternStr) {		
		
		ArrayList<String> toReturn = new ArrayList<String>();
		Pattern p = Pattern.compile(paternStr);
		Matcher m = p.matcher(value);				
			
			while (m.find()) {
				toReturn.add(m.group());	
			}		
		
		String sa[] = new String[toReturn.size()];
	    sa = toReturn.toArray(sa);
	    
		return sa;
	}
	
}
