/**
 * 
 */
package mdb.core.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author azhuk
 * Creation date: May 22, 2013
 *
 */

@Entity
@Table(name = "MDB.V_METHODS_FOR_DENTITY")
public class EntityMethods {
	
	
	@Column (name ="ID_METHODS_DENTITY")
	private int _idMethod;
	
	@Column(name = "ID_DENTITY")
	private int _idEntity;
	
	@Column(name = "NAME_ACTION")
	private String _nameAction;
	
	@Column (name ="METHOD_BODY")
	private String _body;
	
	
	@Column(name = "ID_DENTITY_ACTION")
	private int _idAction;	
	
	@Column(name = "IS_PAGING")
	private int _isPaging;
	
	@Id
	@Column(name = "ID_EXT_METHOD")
	private int _idExtMethod;
	
	
	/**
	 * @return the _idMethod
	 */
	public int getIdMethod() {
		return _idMethod;
	}
	/**
	 * @return the idEntity
	 */
	public int getIdEntity() {
		return _idEntity;
	}
	/**
	 * @return the _nameAction
	 */
	public String getNameAction() {
		return _nameAction;
	}
	/**
	 * @return the _body
	 */
	public String getBody() {
		return _body;
	}
	/**
	 * @return the _idBody
	 */
	public int getIdAction() {
		return _idAction;
	}
	/**
	 * @return the _idExtMethod
	 */
	public int getIdExtMethod() {
		return _idExtMethod;
	}
	
	public boolean isPaging() {
		return _isPaging==1?true:false;
	}
	public void setPaging(boolean value) {
		this._isPaging = value?1:0;
	}
	  
}
