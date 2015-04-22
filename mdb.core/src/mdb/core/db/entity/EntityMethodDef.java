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
 * Creation date: Nov 7, 2013
 *
 */

@Entity
@Table(name = "MDB.V_METHOD_DEFINITION")
public class EntityMethodDef {

	
	@Id
	@Column (name ="ID_FLD")
	private int _idFld;
	
	@Column (name ="ID_FLD_TYPE")
	private int _idFldType;
	
	@Column (name ="ID_DENTITY")
	private int _idEntity;	
	
	@Column (name ="ID_FLD_PARENT")
	private int _idFldParent;
	
	@Column (name ="NUM")
	private int _num;
	
	@Column (name ="NAME")
	private String _name;
	
	@Column (name ="CAPTION")	
	private String _caption;
	
	@Column (name ="LEN")
	private int _len;
	
	@Column (name ="FLD_TNAME")
	private String _fldTypeName; 
	
	@Column (name ="LOOK_UP_SRC")
	private String _lookUpSrc; 
	
	@Column (name ="LOOK_UP_KEY")
	private String _lookUpKey; 
	
	@Column (name ="LOOK_UP_FLDS")
	private String _looUpFlds;	
	
	@Column (name ="MASTER_FLD")
	private String _fldMaster; 
	
	@Column (name ="VISIBLE")
	private int _visible;
	
	@Column (name ="READONLY")
	private int _readOnly;
	
	@Column (name ="ID_EXT_METHOD")
	private int _idExtMethod;
	
	@Column (name ="ID_DENTITY_ACTION")
	private int _idEntityAction;
	
	@Column (name ="ID_SUMM_KIND")
	private int _idSumKind;
	
	@Column (name ="ID_GRP_SUM_KIND")
	private int _idGrpKind;
	
	
	
	/**
	 * @return the _idFld
	 */
	public int getidFld() {
		return _idFld;
	}

	/**
	 * @return the _idFldType
	 */
	public int getidFldType() {
		return _idFldType;
	}

	/**
	 * @return the _idEntity
	 */
	public int getidEntity() {
		return _idEntity;
	}

	/**
	 * @return the _idFldParent
	 */
	public int getidFldParent() {
		return _idFldParent;
	}

	/**
	 * @return the _num
	 */
	public int getnum() {
		return _num;
	}

	/**
	 * @return the _name
	 */
	public String getname() {
		return _name;
	}

	/**
	 * @return the _caption
	 */
	public String getcaption() {
		return _caption;
	}

	/**
	 * @return the _len
	 */
	public int getlen() {
		return _len;
	}

	/**
	 * @return the _fldTypeName
	 */
	public String getfldTypeName() {
		return _fldTypeName;
	}

	/**
	 * @return the _lookUpSrc
	 */
	public String getlookUpSrc() {
		return _lookUpSrc;
	}

	/**
	 * @return the _lookUpKey
	 */
	public String getlookUpKey() {
		return _lookUpKey;
	}

	/**
	 * @return the _looUpFlds
	 */
	public String getlooUpFlds() {
		return _looUpFlds;
	}

	/**
	 * @return the _fldMaster
	 */
	public String getfldMaster() {
		return _fldMaster;
	}

	/**
	 * @return the _visible
	 */
	public int getvisible() {
		return _visible;
	}

	/**
	 * @return the _readOnly
	 */
	public int getreadOnly() {
		return _readOnly;
	}

	/**
	 * @return the _idExtMethod
	 */
	public int getidExtMethod() {
		return _idExtMethod;
	}

	/**
	 * @return the _idEntityAction
	 */
	public int getidEntityAction() {
		return _idEntityAction;
	}

	/**
	 * @return the _idSumKind
	 */
	public int getidSumKind() {
		return _idSumKind;
	}

	/**
	 * @return the _idGrpKind
	 */
	public int getidGrpKind() {
		return _idGrpKind;
	}

	/**
	 * @param _idFld the _idFld to set
	 */
	public void setidFld(int _idFld) {
		this._idFld = _idFld;
	}

	/**
	 * @param _idFldType the _idFldType to set
	 */
	public void setidFldType(int _idFldType) {
		this._idFldType = _idFldType;
	}

	/**
	 * @param _idEntity the _idEntity to set
	 */
	public void setidEntity(int _idEntity) {
		this._idEntity = _idEntity;
	}

	/**
	 * @param _idFldParent the _idFldParent to set
	 */
	public void setidFldParent(int _idFldParent) {
		this._idFldParent = _idFldParent;
	}

	/**
	 * @param _num the _num to set
	 */
	public void setnum(int _num) {
		this._num = _num;
	}

	/**
	 * @param _name the _name to set
	 */
	public void setname(String _name) {
		this._name = _name;
	}

	/**
	 * @param _caption the _caption to set
	 */
	public void setcaption(String _caption) {
		this._caption = _caption;
	}

	/**
	 * @param _len the _len to set
	 */
	public void setlen(int _len) {
		this._len = _len;
	}

	/**
	 * @param _fldTypeName the _fldTypeName to set
	 */
	public void setfldTypeName(String _fldTypeName) {
		this._fldTypeName = _fldTypeName;
	}

	/**
	 * @param _lookUpSrc the _lookUpSrc to set
	 */
	public void setlookUpSrc(String _lookUpSrc) {
		this._lookUpSrc = _lookUpSrc;
	}

	/**
	 * @param _lookUpKey the _lookUpKey to set
	 */
	public void setlookUpKey(String _lookUpKey) {
		this._lookUpKey = _lookUpKey;
	}

	/**
	 * @param _looUpFlds the _looUpFlds to set
	 */
	public void setlooUpFlds(String _looUpFlds) {
		this._looUpFlds = _looUpFlds;
	}

	/**
	 * @param _fldMaster the _fldMaster to set
	 */
	public void setfldMaster(String _fldMaster) {
		this._fldMaster = _fldMaster;
	}

	/**
	 * @param _visible the _visible to set
	 */
	public void setvisible(int _visible) {
		this._visible = _visible;
	}

	/**
	 * @param _readOnly the _readOnly to set
	 */
	public void setreadOnly(int _readOnly) {
		this._readOnly = _readOnly;
	}

	/**
	 * @param _idExtMethod the _idExtMethod to set
	 */
	public void setidExtMethod(int _idExtMethod) {
		this._idExtMethod = _idExtMethod;
	}

	/**
	 * @param _idEntityAction the _idEntityAction to set
	 */
	public void setidEntityAction(int _idEntityAction) {
		this._idEntityAction = _idEntityAction;
	}

	/**
	 * @param _idSumKind the _idSumKind to set
	 */
	public void setidSumKind(int _idSumKind) {
		this._idSumKind = _idSumKind;
	}

	/**
	 * @param _idGrpKind the _idGrpKind to set
	 */
	public void setidGrpKind(int _idGrpKind) {
		this._idGrpKind = _idGrpKind;
	}

	
    
}
