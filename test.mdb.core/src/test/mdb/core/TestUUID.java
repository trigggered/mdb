/**
 * 
 */
package test.mdb.core;

import java.util.UUID;
import junit.framework.TestCase;

/**
 * @author azhuk
 * Creation date: Oct 5, 2012
 *
 */
public class TestUUID extends TestCase {

	
	public void testUid() {
		
		UUID  uid = java.util.UUID.randomUUID();
		System.out.println(uid.toString());		
		
	}
}
