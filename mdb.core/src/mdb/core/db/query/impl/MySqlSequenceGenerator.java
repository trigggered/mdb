/**
 * 
 */
package mdb.core.db.query.impl;

import mdb.core.db.connection.IConnectionManager;
import mdb.core.db.query.IDBSequenceGenerator;

import com.google.inject.Inject;

/**
 * @author azhuk
 * Creation date: Jan 22, 2013
 *
 */
public class MySqlSequenceGenerator implements IDBSequenceGenerator {	
	
	private String _keyName;
	private  String _seqName;
	private IConnectionManager _conn;
	
	public MySqlSequenceGenerator (){
		
	}
			
	@Inject
	public MySqlSequenceGenerator (String keyName, String seqName) {		
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
		if (value != null ) {
			_keyName = value.toUpperCase();
		}
		else _keyName = null;
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
		if (value!=null) {
		_seqName = value.toUpperCase();
		}
		else {
			_seqName = null;
		}
	}
	

	public IConnectionManager getConnectionManager() {
		return _conn;
	}

	/* (non-Javadoc)
	 * @see mdb.core.db.query.ISequenceGenerator#setConnectionManager(mdb.core.db.connection.IConnectionManager)
	 */
	@Override
	public void setConnectionManager(IConnectionManager value) {
		this._conn = value;
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.db.query.ISequenceGenerator#nextVal()
	 */
	@Override
	public String nextVal () {
		String Result = null;
		return Result;
	}

	/* (non-Javadoc)
	 * @see mdb.core.db.query.IDBSequenceGenerator#isCanGenerate()
	 */
	@Override
	public boolean isCanGenerate() {
		return getKeyName()!=null && getSeqName()!=null;		
	}

}
