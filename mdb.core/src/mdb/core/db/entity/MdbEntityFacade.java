/**
 * 
 */
package mdb.core.db.entity;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author azhuk
 * Creation date: May 31, 2013
 *
 */
@Stateless
public class MdbEntityFacade extends EntityManagerHelper {
	

	
	public Collection<EntityMethods> findMethods(int entityId) {				
		
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EntityMethods> criteriaQuery = criteriaBuilder.createQuery(EntityMethods.class);
		Root<EntityMethods> mdbEntityMethodsRoot = criteriaQuery.from(EntityMethods.class);

		
		criteriaQuery.where(criteriaBuilder.equal(mdbEntityMethodsRoot.get("_idEntity"), entityId));
		return	getEntityManager().createQuery(criteriaQuery).getResultList();			
		
	}
	
	
	public Collection<EntityKeys> findKeys(int entityId) {	
		
		
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EntityKeys> criteriaQuery = criteriaBuilder.createQuery(EntityKeys.class);
		Root<EntityKeys> mdbEntityMethodsRoot = criteriaQuery.from(EntityKeys.class);

		
		criteriaQuery.where(criteriaBuilder.equal(mdbEntityMethodsRoot.get("_idEntity"), entityId));
		return	getEntityManager().createQuery(criteriaQuery).getResultList();				
		
	}	
	
	
	
	public Collection<EntityMethodDef> findMethoddDefs(int entityId) {				
		
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EntityMethodDef> criteriaQuery = criteriaBuilder.createQuery(EntityMethodDef.class);
		Root<EntityMethodDef> mdbEntityMethodsRoot = criteriaQuery.from(EntityMethodDef.class);

		
		criteriaQuery.where(criteriaBuilder.equal(mdbEntityMethodsRoot.get("_idEntity"), entityId));
		return	getEntityManager().createQuery(criteriaQuery).getResultList();				
		
	}
	
	
}
