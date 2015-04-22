/**
 * 
 */
package mdb.core.shared.transport;


/**
 * @author azhuk
 * Creation date: May 8, 2014
 *
 */
public class LobStoredResult {
	
	public enum EStoringStatus {
		Ok,
		Faile
	}
	
	public enum ELobStoringResultProp {
		
		AttributeId,
		VersionId,
		LobId,
		LobName,
		StoredStatus,
		InfoMsg;
	}
	
	
	long AttributeId;
	long VersionId;
	long LobId;
	byte[] data;
	boolean isZipped;
	String LobName;
	EStoringStatus StoredStatus;
	String InfoMsg;
	
	
	/**
	 * @return the _attributeId
	 */
	public long getAttributeId() {
		return AttributeId;
	}
	/**
	 * @return the _versionId
	 */
	public long getVersionId() {
		return VersionId;
	}
	/**
	 * @return the _lobId
	 */
	public long getLobId() {
		return LobId;
	}
	/**
	 * @return the _lobName
	 */
	public String getLobName() {
		return LobName;
	}
	/**
	 * @return the _storingStatus
	 */
	public EStoringStatus getStoredStatus() {
		return StoredStatus;
	}
	/**
	 * @return the infoMsg
	 */
	public String getInfoMsg() {
		return InfoMsg;
	}
	/**
	 * @param _attributeId the _attributeId to set
	 */
	public void setAttributeId(long _attributeId) {
		this.AttributeId = _attributeId;
	}
	/**
	 * @param _versionId the _versionId to set
	 */
	public void setVersionId(long _versionId) {
		this.VersionId = _versionId;
	}
	/**
	 * @param _lobId the _lobId to set
	 */
	public void setLobId(long _lobId) {
		this.LobId = _lobId;
	}
	/**
	 * @param _lobName the _lobName to set
	 */
	public void setLobName(String _lobName) {
		this.LobName = _lobName;
	}
	/**
	 * @param _storingStatus the _storingStatus to set
	 */
	public void setStoredStatus(EStoringStatus _storingStatus) {
		this.StoredStatus = _storingStatus;
	}
	/**
	 * @param infoMsg the infoMsg to set
	 */
	public void setInfoMsg(String infoMsg) {
		InfoMsg = infoMsg;
	}
	/**
	 * @param lobWithSign
	 */
	public void setData(byte[] value) {
		data = value;
		
	}
	/**
	 * @return
	 */
	public byte[] getData() {
		return data;
	}
	
	
}
