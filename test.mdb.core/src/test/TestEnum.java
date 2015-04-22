/**
 * 
 */
package test;

import static org.junit.Assert.assertEquals;

import java.util.logging.Logger;

import org.junit.Test;

/**
 * @author azhuk
 * Creation date: Feb 3, 2014
 *
 */
public class TestEnum {
	private static final Logger _logger = Logger.getLogger(TestEnum.class
			.getCanonicalName());

	enum ETest {
		e1,
		e2,
	
	}
	
	@Test
	public void testEnumBadValueOf() {
		
		ETest e1 = ETest.valueOf("e1");
		assertEquals(ETest.e1, e1);

		ETest  badEnum = null;
		try {
			badEnum = ETest.valueOf("e3");
		
		}
		catch (IllegalArgumentException e) {
			badEnum = null;
		}
		//ETest  badEnum = null;
		assertEquals(null, badEnum);
		
	}
}
