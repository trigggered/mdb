/**
 * 
 */
package mdb.core.db;

import javax.ejb.Local;

import mdb.core.db.query.IQuery;

/**
 * @author azhuk
 * Creation date: Jul 28, 2015
 *
 */
@Local
public interface IMdbQueryPreparator {

	/**
	 * @param entityId
	 * @return
	 */
	IQuery create(int entityId);

}
