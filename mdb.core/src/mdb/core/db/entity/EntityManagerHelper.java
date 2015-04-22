/**
 * 
 */
package mdb.core.db.entity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * @author azhuk
 * Creation date: May 31, 2013
 *
 * @param <T>
 */
@Stateless
public class EntityManagerHelper {	

	@PersistenceContext(unitName = "OraDS" , type=PersistenceContextType.TRANSACTION )
	private EntityManager _entityManager;
	
	
	/* (non-Javadoc)
	 * @see mdb.core.db.entity.AbstractEntityFacade#getEntityManager()
	 */
	public EntityManager getEntityManager() {		
		return _entityManager;
	}	


}