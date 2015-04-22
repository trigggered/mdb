package test.mdb.core.util.zip;

import java.io.IOException;

import junit.framework.TestCase;
import mdb.core.shared.utils.zip.GZipHelper;


public class TestZipHelper extends TestCase {
	
		
	public void testStrCompress() {
		
		String msgTo = "Hello world !!!";
		 try {
			byte[] compressedBytes =  GZipHelper.compress(msgTo.getBytes());
			assertTrue(compressedBytes.length > 0);
			
			assertTrue(GZipHelper.isCompressed(compressedBytes));
			String msgFrom = new String( GZipHelper.uncompress(compressedBytes));
			
			assertTrue(msgTo.equalsIgnoreCase(msgFrom));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	}
}
