/**
 * 
 */
package mdb.core.db;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.ejb.EJB;

import mdb.core.db.query.IQuery;
import mdb.core.db.sql.SQLTemplates;
import mdb.core.shared.data.EMdbEntityActionType;
import mdb.core.shared.data.Params;

import com.google.inject.Inject;

/**
 * @author azhuk
 * Creation date: Jul 28, 2015
 *
 */
public class MdbQueryManualPreparator implements IMdbQueryPreparator {
	private static final Logger _logger = Logger
			.getLogger(MdbQueryManualPreparator.class.getName());
	
	
	final int DENTITY_METHODS 		= -1;
	final int DENTITY_KEYS 			= 22;
	
	@EJB
	IQueryPool _pool;


	@Inject
	public MdbQueryManualPreparator(IQueryPool pool) {
		_pool = pool;
		prepareSystemQuerys();
	}
	
	
	private boolean prepareSystemQuerys() {
		
		_pool.closeAll();						 
		
		IQuery aQuery = _pool.createQuery(DENTITY_METHODS);
		aQuery.setSelect(SQLTemplates.QUERY_METHOD);	
	

		//_pool.generateQuery(DENTITY_KEYS);

		return true;
	}
	
	@Override
	public IQuery  create (int entityId) {

		final int METHOD_BODY_COL_INDEX = 4;// METHOD_BODY
		
		IQuery qDEntityMethods = _pool.getQueryFromPool(DENTITY_METHODS);
		if (qDEntityMethods == null ) {
			return null;
		}

		Params params = new Params();
		
		params.add("ID_DENTITY", String.valueOf(entityId));
		ResultSet rs = qDEntityMethods.getResultSet(params, null);				
		
		if (rs != null) {
			try {
				ResultSetMetaData rsmd = rs.getMetaData();
												
				IQuery newQuery = _pool.createQuery(entityId);
								
				while (rs.next()) {

					//newQuery.setIsArchive(rs.getInt("IS_ARCHIVE") > 0);
					String methodBody = null;					
					if (rsmd.getColumnType(METHOD_BODY_COL_INDEX) == java.sql.Types.CLOB) {	
						Clob clMethodBody = rs.getClob(METHOD_BODY_COL_INDEX);
						methodBody = clMethodBody.getSubString(1, (int) clMethodBody.length());
					} 
					else  {
						methodBody = rs.getString(METHOD_BODY_COL_INDEX);
					}										
					
					EMdbEntityActionType actionType = EMdbEntityActionType.fromInt(rs.getInt("ID_DENTITY_ACTION") );
					switch ( actionType )  {
						case SELECT :						
							newQuery.setSelect(methodBody);
							break;
						case INSERT : 
							newQuery.setInsert(methodBody);
							break;
						case UPDATE : 
							newQuery.setUpdate(methodBody);
							break;
						case DELETE : 	
							newQuery.setDelete(methodBody);
							break;
						case REFRESH :	
							newQuery.setRefresh(methodBody);
							break;			
						 
						case STORED_PROC:
						case PREPERE_FILTER: 
									newQuery.setAction(actionType, rs.getInt("ID_EXT_METHOD"), methodBody); //Stored Proc
								 break;
					default:
						break;
								 
					}
				}
				rs.close();
				qDEntityMethods.close();

				
				if (entityId != DENTITY_KEYS ) {
					IQuery qDEntityKeys = _pool.getQueryFromPool(DENTITY_KEYS);	
					
					if (qDEntityKeys != null ) {
						Params p = new Params();						
						p.add("pID_DENTITY", String.valueOf(entityId));
						rs = qDEntityKeys.getResultSet(p, null);
						if (rs != null) {
							while (rs.next()) {
								newQuery.setEntityKey(rs.getString("KEY_FIELD"), rs.getString("SEQ_NAME"));
							}
							rs.close();
							qDEntityKeys.close();
						}
					}
				}				
				return newQuery;
			}

			catch (SQLException ex) {
				_logger.severe(ex.getMessage());				
				return null;
			} finally {

			}
		}
		return null;
	}
}
