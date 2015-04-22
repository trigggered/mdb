/**
 * 
 */
package mdb.cryptography.impl;

import java.security.Key;
import java.util.logging.Logger;

import javax.crypto.Cipher;

import mdb.cryptography.ICryptography;

/**
 * @author azhuk
 * Creation date: Jan 20, 2014
 *
 */
public class Cryptography implements ICryptography {
	private static final Logger _logger = Logger
			.getLogger(Cryptography.class.getName());

	
	private EAlgorithm _algorithm;
	
	public Cryptography(EAlgorithm algorithm) {
		_algorithm =algorithm;
	}
	
	public EAlgorithm getAlgorithm() {
		return _algorithm;
	}
	
	/* (non-Javadoc)
	 * @see mdb.cryptography.ICryptography#init(java.lang.String)
	 */
	@Override
	public void init(String privateKeyFile) {
	
	}

	/* (non-Javadoc)
	 * @see mdb.cryptography.ICryptography#decrypt(byte[])
	 */
	@Override
	public byte[] decrypt(byte[] data, Key key) {
		byte[] dectyptedData = null;
	    try {
	      // get an RSA cipher object and print the provider
	      final Cipher cipher = Cipher.getInstance(_algorithm.toString());

	      // decrypt the text using the private key
	      cipher.init(Cipher.DECRYPT_MODE, key);
	      dectyptedData = cipher.doFinal(data);

	    } catch (Exception ex) {
	      _logger.severe(ex.getMessage());
	    }

	    return dectyptedData;
	}

	/* (non-Javadoc)
	 * @see mdb.cryptography.ICryptography#dcrypt(byte[])
	 */
	@Override
	public byte[] encrypt(byte[] data, Key key) {

		 byte[] cipherText = null;
		    try {
		      // get an RSA cipher object and print the provider
		      final Cipher cipher = Cipher.getInstance(_algorithm.toString());
		      // encrypt the plain text using the public key
		      cipher.init(Cipher.ENCRYPT_MODE, key);
		      cipherText = cipher.doFinal(data);
		    } catch (Exception e) {
		    	_logger.severe(e.getMessage());
		    }
		    return cipherText;		    
		
	}

	/* (non-Javadoc)
	 * @see mdb.cryptography.ICryptography#sign(byte[])
	 */
	@Override
	public byte[] sign(byte[] data) {
		// TODO Auto-generated method stub
		return null;
	}
}
