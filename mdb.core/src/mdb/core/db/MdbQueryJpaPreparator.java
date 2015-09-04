/**
 * 
 */
package mdb.core.db;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mdb.core.config.JBossConfig;
import mdb.core.db.connection.IConnectionManager;
import mdb.core.db.entity.EntityKeys;
import mdb.core.db.entity.EntityMethods;
import mdb.core.db.entity.MdbEntityFacade;
import mdb.core.db.query.IQuery;
import mdb.core.shared.data.EMdbEntityActionType;
import mdb.injector.SingletonInjector;


/**
 * @author azhuk
 * Creation date: Jul 28, 2015
 *
 */
@Stateless
public class MdbQueryJpaPreparator  implements IMdbQueryPreparator {
	private static final Logger _logger = Logger
			.getLogger(MdbQueryJpaPreparator.class.getCanonicalName());

	@EJB
	MdbEntityFacade _mdbEntityFacade;
	
	@EJB
	IQueryPool _pool;
	
	
	/* (non-Javadoc)
	 * @see mdb.core.db.IMdbQueryPreparator#create(int)
	 */
	@Override
	public IQuery create(int entityId) {
		
		IQuery newQuery = _pool.createQuery(entityId);		
		
		boolean fSetCustomConfiguration = false;
		for (EntityMethods method : _mdbEntityFacade.findMethods(entityId) ) {	
		
			final String dataSourceName = method.getDsName();						
			
			if (dataSourceName != null && dataSourceName.length() > 0 && !fSetCustomConfiguration) {
				fSetCustomConfiguration = true;
				_logger.info("Data Source name = "+dataSourceName);	
				IConnectionManager connManager = SingletonInjector.getInjector().getInstance(IConnectionManager.class);
				
				connManager.setAppConfig(	new JBossConfig() {
					@Override
					public String getJdbcDataSourceName() {	
						return dataSourceName;
					}
				});
				newQuery.setConnectionManager(connManager);
			}			
			
			newQuery.setQueryID(entityId);
			newQuery.getQueryPaging().setPagingUsage(method.isPaging());			
			
			EMdbEntityActionType actionType = EMdbEntityActionType.fromInt(method.getIdAction() ); 
			switch ( actionType )  {
				case SELECT :						
					newQuery.setSelect(method.getBody());
					break;
				case INSERT : 
					newQuery.setInsert(method.getBody());
					break;
				case UPDATE : 
					newQuery.setUpdate(method.getBody());
					break;
				case DELETE : 	
					newQuery.setDelete(method.getBody());
					break;
				case REFRESH :	
					newQuery.setRefresh(method.getBody());
					break;			
				 
				case STORED_PROC:
				case PREPERE_FILTER: 
							newQuery.setAction(actionType, method.getIdExtMethod(), method.getBody()); //Stored Proc
						 break;
			default:
				break;
						 
			}								
		}				
	
		if (newQuery != null ) {
			for (EntityKeys keys : _mdbEntityFacade.findKeys(entityId) ) {
				newQuery.setEntityKey(keys.getKeyField(), keys.getSeqName());				
			}
		}
		
		 return newQuery;
	}
}
