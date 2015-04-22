/**
 * 
 */
package test.others;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import junit.framework.TestCase;

/**
 * @author azhuk
 * Creation date: Mar 21, 2013
 *
 */
public class TestJoiner extends TestCase {
	private static final Logger _logger = Logger.getLogger(TestJoiner.class
			.getName());
	
	
	public void testJoin() {
		
		String table = "CLIENTS";
		String key = "K030";
		String fields ="K030;TXT";
		
		StringBuilder builder = new StringBuilder();
		builder.append("CLIENTS");
		builder.append(key);
		builder.append(fields);
		
		String s = builder.toString();
		
		ArrayList<String> list  = new  ArrayList<String>(Arrays.asList(fields.split(";"))) ;
		
		if (!list.contains(key) ) {
			list.add(key);
		}
		
		assertEquals(2,list.size());
		
		fields = list.toString().replace("[", "").replace("]","");
		
		String sql = String.format("Select %s from %s", fields, table);
		int hash = (table+fields+key).hashCode();
		
	}
	
	 
}
