/**
 * 
 */
package mdb.cryptography.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import mdb.cryptography.ICryptography;


/**
 * @author azhuk
 * Creation date: Jan 21, 2014
 *
 */
public class KeysGenerator {
	private static final Logger _logger = Logger.getLogger(KeysGenerator.class
			.getName());
	
	public static KeyPair  generate(ICryptography.EAlgorithm  algorithm, int keysizes)  {
		
		KeyPairGenerator keyPairGenerator = null;
		KeyPair keyPair = null;
		try {
			keyPairGenerator = KeyPairGenerator.getInstance(algorithm.toString());
			keyPairGenerator.initialize(keysizes);
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			_logger.severe(e.getMessage());
		}

		
		return keyPair;		
	}
	
	
	@SuppressWarnings("finally")
	public static String keyToString (Key key) {
		String toReturn = null;					      
		try {
			ByteArrayOutputStream  outs = new ByteArrayOutputStream();
			ObjectOutputStream  publicKeyOS = new ObjectOutputStream(outs);
			publicKeyOS.writeObject(key);			      
		    publicKeyOS.close();
		    toReturn = DatatypeConverter.printBase64Binary(outs.toByteArray());    
		} catch (IOException e) {			
			_logger.severe(e.getMessage());			
		}	      
		finally {
			return toReturn;
		} 
	      
	}
	
	
	@SuppressWarnings("finally")
	public static Key keyFromString (String key) {
		Key  toReturn = null;					      
		try {
			byte[] keyBytes = DatatypeConverter.parseBase64Binary(key);
			
			ObjectInputStream ins = new ObjectInputStream(new ByteArrayInputStream(keyBytes) );			
			toReturn =  (Key)ins.readObject();		      
		    
		        
		} catch (IOException e) {			
			_logger.severe(e.getMessage());			
		}	      
		finally {
			return toReturn;
		} 
	      
	}
	
}
