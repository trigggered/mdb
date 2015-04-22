/**
 * 
 */
package test.others;

import java.util.logging.Logger;

import org.junit.Test;
import org.junit.Assert;




/**
 * @author azhuk
 * Creation date: Jul 17, 2013
 *
 */
public class TestString {
	
	enum enumForTest {
		enum1,
		enum2
	}
	
	private static final Logger _logger = Logger.getLogger(TestString.class
			.getCanonicalName());

	
	public void test() {
		String result = String.format("%sGet%sRequest%s", "<","CustomerAccounts", "/>");
		System.out.println(result);
		
		result = String.format("%sGet%sRequest%s", "<",null, "/>");
		System.out.println(result);
	}
	
	
	
	public void testEnumToStr () {	
		enumForTest e = enumForTest.enum1;		
		System.out.println(e.toString());		
	}
	
	
	@Test
	public void testReplase() {
		String FILE_NAME = "bloki.txt";
		String filePath = "D:\\My documents\\"+FILE_NAME;
		
		filePath = getFileNameFromPath(filePath);
			    
		Assert.assertEquals(FILE_NAME, filePath);
	}
	
	private String  getFileNameFromPath(String filePath) {
		String winPathSeparator = "\\";
		String linuxPathSeparator = "/";
		
		int idx = filePath.lastIndexOf(winPathSeparator);				
		
		if (  idx > 0 ) {
			filePath = idx >= 0 ? filePath.substring(idx + 1) : filePath;
		}		
		else {
			
			idx = filePath.lastIndexOf(linuxPathSeparator);			
			filePath = idx >= 0 ? filePath.substring(idx + 1) : filePath;
		}				
		
		return filePath;
	}
	
}
