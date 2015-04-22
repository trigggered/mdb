/**
 * 
 */
package mdb.core.db.query.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.google.inject.Inject;

import mdb.core.db.connection.IConnectionManager;
import mdb.core.db.query.IDBSequenceGenerator;
import mdb.core.db.sql.SQLTemplates;

/**
 * @author azhuk
 * Creation date: Jan 22, 2013
 *
 */
public class OraSequenceGenerator implements IDBSequenceGenerator {
	private static final Logger _logger = Logger
			.getLogger(OraSequenceGenerator.class.getName());
	
	private String _keyName;
	private  String _seqName;
	private IConnectionManager _conn;
	
	
	public OraSequenceGenerator () {		
	}
	
	public OraSequenceGenerator ( String keyName, String seqName) {	
		setKeyName(keyName);
		setSeqName(seqName);		
	}

	/* (non-Javadoc)
	 * @see mdb.core.db.query.ISequenceGenerator#getKeyName()
	 */
	@Override
	public String getKeyName() {
		return _keyName;
	}

	/* (non-Javadoc)
	 * @see mdb.core.db.query.ISequenceGenerator#setKeyName(java.lang.String)
	 */
	@Override
	public void setKeyName(String value) {
		this._keyName = value;
	}

	/* (non-Javadoc)
	 * @see mdb.core.db.query.ISequenceGenerator#getSeqName()
	 */
	@Override
	public String getSeqName() {
		return _seqName;
	}

	/* (non-Javadoc)
	 * @see mdb.core.db.query.ISequenceGenerator#setSeqName(java.lang.String)
	 */
	@Override
	public void setSeqName(String value) {
		this._seqName = value;
	}
	

	public IConnectionManager getConnectionManager() {
		return _conn;
	}

	/* (non-Javadoc)
	 * @see mdb.core.db.query.ISequenceGenerator#setConnectionManager(mdb.core.db.connection.IConnectionManager)
	 */
	@Inject
	@Override
	public void setConnectionManager(IConnectionManager value) {
		this._conn = value;
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.db.query.ISequenceGenerator#nextVal()
	 */
	@Override
	public String nextVal () {
		String toReturn = null;			 		
			
				try (Connection conn = getConnectionManager().getConnection() ) {

					PreparedStatement pstSeqGen = conn.prepareStatement(SQLTemplates.getSeqSql(getSeqName())); 
					ResultSet rs = pstSeqGen.executeQuery();
					
					if (rs.next()) {
						toReturn = rs.getString(1);
					}					
					rs.close();
					pstSeqGen.close();
					//conn.close();
				} catch (SQLException ex) {					
					_logger.severe(ex.getMessage());					
				}
					
		return toReturn;
	}

	/* (non-Javadoc)
	 * @see mdb.core.db.query.IDBSequenceGenerator#isCanGenerate()
	 */
	@Override
	public boolean isCanGenerate() {
		return getKeyName()!=null && getSeqName()!=null;		
	}

}
