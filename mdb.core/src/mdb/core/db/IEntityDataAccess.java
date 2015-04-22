package mdb.core.db;

import java.sql.ResultSet;

import javax.ejb.Local;

import mdb.core.db.query.IQuery;
import mdb.core.shared.data.Params;
import mdb.core.shared.transport.RequestEntity;

@Local
public interface IEntityDataAccess {
	 public ResultSet getResultSet(Integer aEntityId);
	 public ResultSet getResultSet(Integer aEntityId, Params aParams);
	 public ResultSet getResultSet(RequestEntity entity);
	 public void changeData(RequestEntity entity);
	 
	 
	 public String getTransformedData(Integer aEntityId, Params aParams);
	 public String getTransformedData(Integer aEntityId);
	/**
	 * @param requestEntity
	 * @return 
	 */
	 public void execAction(RequestEntity entity);
	 public ResultSet applyFilter(IQuery query, RequestEntity requestEntity);
	 
	 public IQuery getQuery(int entityId);	 
	 public IQuery prepareQuery(RequestEntity requestEntity);
	 
	 public IQueryPool getQueryPool ();

}