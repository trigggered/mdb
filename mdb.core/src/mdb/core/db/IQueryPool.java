/*
 * Created on  11.10.2005 by Andrey L. Zhuk (andr.zhuk@ua.fm)
 */
package mdb.core.db;

import javax.ejb.Local;

import mdb.core.db.query.IQuery;


@Local
public interface IQueryPool {		
	
	public boolean existQuery(int entityID);

	public IQuery getQuery(int entityID);	
	
	public IQuery getEmptyQuery(int anDEntityID);

	/**
	 * @param entityID
	 * @return
	 */
	IQuery getQueryFromPool(int entityID);

	/**
	 * @param entityId
	 * @return
	 */
	IQuery createQuery(int entityId);

	/**
	 * 
	 */
	void closeAll();
	
}
