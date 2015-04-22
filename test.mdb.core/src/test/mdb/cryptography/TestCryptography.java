/**
 * 
 */
package test.mdb.cryptography;

import static org.junit.Assert.*;

import java.security.KeyPair;
import java.util.logging.Logger;

import mdb.cryptography.ICryptography;
import mdb.cryptography.impl.Cryptography;
import mdb.cryptography.impl.KeysGenerator;

import org.junit.Test;

/**
 * @author azhuk
 * Creation date: Jan 20, 2014
 *
 */
public class TestCryptography {
	private static final Logger _logger = Logger.getLogger(TestCryptography.class
			.getName());	
	
	@Test
	public void testRSAKeyGenerators() {
		 KeyPair keyPair = KeysGenerator.generate(ICryptography.EAlgorithm.RSA, 1024);
		 Cryptography cryptography = new Cryptography(ICryptography.EAlgorithm.RSA);		 
		
		 try {
 
		      final String originalText = "Text to be encrypted ";
		      
		      String publicKey = KeysGenerator.keyToString(keyPair.getPublic() );
		      System.out.println("Public Key: " + publicKey);		      
		      
		      String privateKey = KeysGenerator.keyToString(keyPair.getPrivate() );
		      System.out.println("Private Key: " + privateKey);
		      
		      assertNotEquals(publicKey, privateKey);
		      
		      final byte[] cipherText = cryptography.encrypt(originalText.getBytes(), keyPair.getPrivate());
		      
		      //final String plainText = new String( cryptography.decrypt(cipherText, keyPair.getPublic() ) ) ;
		      final String plainText = new String( cryptography.decrypt(cipherText, KeysGenerator.keyFromString(publicKey) ) ) ;
		      
		      // Printing the Original, Encrypted and Decrypted Text
		      System.out.println("Original Text: " + originalText);
		      System.out.println("Encrypted Text: " + new String(cipherText) );
		      System.out.println("Decrypted Text: " + plainText);
		      
		      
		      assertEquals(originalText, plainText);
		      
		      
		      KeyPair keyPair2 = KeysGenerator.generate(ICryptography.EAlgorithm.RSA, 1024);
		      final String plainText2 = new String( cryptography.decrypt(cipherText, keyPair2.getPublic() ) ) ;
		      System.out.println("Decrypted Text2: " + plainText2);
		      assertNotEquals(originalText, plainText2);

		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	}
}
