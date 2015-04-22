/**
 * 
 */
package mdb.cryptography;

import java.security.Key;


/**
 * @author azhuk
 * Creation date: Jan 20, 2014
 *
 */
public interface ICryptography {
	
	public enum EAlgorithm  {
		DiffieHellman, 
		DSA,
		RSA
	};
	

	
	public void init(String privateKeyFile);
	public byte[] decrypt(byte[] data, Key key);	
	public byte[] sign(byte[] data);	
	byte[] encrypt(byte[] data, Key key);

}
