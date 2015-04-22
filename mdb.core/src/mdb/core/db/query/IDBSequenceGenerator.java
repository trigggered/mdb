/**
 * 
 */
package mdb.core.db.query;

import mdb.core.db.connection.IConnectionManager;
import mdb.core.shared.utils.ISequenceGenerator;

/**
 * @author azhuk
 * Creation date: Jan 22, 2013
 *
 */
public interface IDBSequenceGenerator extends ISequenceGenerator {

	public  String getKeyName();

	public  void setKeyName(String value);

	public  String getSeqName();

	public  void setSeqName(String value);

	public  void setConnectionManager(IConnectionManager value);

	public boolean isCanGenerate();
}