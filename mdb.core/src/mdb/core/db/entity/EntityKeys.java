/**
 * 
 */
package mdb.core.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
/**
 * @author azhuk
 * Creation date: May 31, 2013
 *
 */

@Entity
@Table(name ="MDB.VIEW_KEYS_DENTITY")
public class EntityKeys {
	 
	@Column(name = "ID_DENTITY")
	private  int _idEntity;
	
	@Column(name = "SEQ_NAME")
	private String _seqName;
	
	
	@Column(name = "KEY_FIELD")
	private String _keyField;
	
	@Column(name = "ID_DENTITY_KEY")
	@Id
	private int _Id;
	
	
	public String getSeqName() {
		return _seqName;
	}	

	
	public String getKeyField() {
		return _keyField;
	}

	public int getId() {
		return _Id;
	}
	
}
