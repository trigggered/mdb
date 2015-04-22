/**
 * 
 */
package test.hash;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import com.usb.security.Hash3410;

/**
 * @author azhuk
 * Creation date: Nov 17, 2014
 *
 */
public class TestHash3410 {
	private static final Logger _logger = Logger.getLogger(TestHash3410.class
			.getName());
	//10.20.141.101/security/getHash3410.jsp?strDocument=1234
	
	@Test
	public void testHash()  {
		
		String hashEtalon = "8a19de2e756035a3ece48cd01260b89ec36a510d9e18066e64ffc4d379c6e457";
		String data = "1234";
		
		String hashResult = null;
		try {
			hashResult = Hash3410.hashMessage(data, "Cp1251");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(hashResult, hashEtalon);		
	}
	
}
