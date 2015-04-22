/**
 * 
 */
package mdb.core.db.query.impl;


import mdb.core.db.query.IDBSequenceGenerator;

/**
 * @author azhuk
 * Creation date: Aug 15, 2013
 *
 */
public class SequenceGeneratorFactory {		
	
	public static IDBSequenceGenerator create () {
		return new OraSequenceGenerator();
	}
}
