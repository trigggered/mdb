package mdb.core.session;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import java.util.UUID;




public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _nUserDep;
	private int _nUSRID;
	
	private String _sUName;
	private String _sUPassword;	
	private String _strFIO;
	private String _nMfo;
	private String _strHashName;
	private String _strHashPassword;	
	private String _strUserGRP;
	private String _strUserTel;
	private String _strUserPath;
	private String _strUserRight;
	private String _nID_Key;
	private String _TimeUserCreate;
	private String _strRunAppName;
	private String _strHashRunAppName;
	
	private boolean _fUseHashUser;
	// ���������� ��� ����������������� ������������
	private UUID _UserUUID;

	private String _UserKey;

	public User(String aUserName, String aUserPassword, String aMFO,
			String aRunAppName,  boolean afUseHashUser) {
		_fUseHashUser = afUseHashUser;
		_nMfo = aMFO;
		_nUSRID = -1;
		_strFIO = "";
		
		 Calendar c = new GregorianCalendar();
		_TimeUserCreate = c.getTime().toString();
		_UserKey = _UserUUID.toString();		
								
		setRunAppName ((aUserName!=null&&aUserPassword!=null)?aRunAppName.toUpperCase():"NAVIGATOR.EXE");
		setUserName(aUserName!=null?aUserName:"ZHUK");
		setPassword(aUserPassword!=null?aUserPassword:"ABSfahjlbnf");		
		
	}

	public User() {
		
		
		_UserKey = UUID.randomUUID().toString();

	}

	public String getUserKey() {
		return _UserKey;
	}

	public void setUserKey(String aUserKey) {
		_UserKey = aUserKey;
	}

	public String getUserName() {
		return _sUName;
	}
	
	public void setUserName(String aUserName) {
		 _sUName =  aUserName;
		 //_strHashName = _sUName!=null?CHashFunctions.nativeStrToHash32(1024, _sUName).toUpperCase():null;
		 _strHashName = aUserName;
		 
	}


	public String getPassword() {
		return _sUPassword;
	}
	
	public void setPassword(String aPassword) {
		_sUPassword = aPassword;
		//_strHashPassword = _sUPassword!=null?CHashFunctions.nativeStrToHash32(1024, _sUPassword).toUpperCase():null;
		_strHashPassword = _sUPassword;
	}

	
	public String getTimeUserCreate() {
		return _TimeUserCreate;

	}

	public String getFIO() {
		return _strFIO;
	}

	public void setFIO(String aFIO) {
		_strFIO = aFIO;
	}

	public Integer getUSRID() {
		return new Integer(_nUSRID);
	}

	public void setUSRID(int anID_USR) {
		_nUSRID = anID_USR;
	}
	
	public void setID_USR(String aID_USR) {
		_nUSRID = aID_USR!=null?Integer.parseInt(aID_USR):0;
	}

	public String getMfo() {
		return _nMfo;
	}

	public void setMfo(String anMfo) {
		_nMfo = anMfo;
	}
	

	public String getHashUserName32() {
		return _strHashName;
	}

	public String getHashUserPassword32() {
		return _strHashPassword;
	}
	
	private String getHashUserName30() {
		return getHashUserName32()!=null?getHashUserName32().substring(0,30):null;
	}

	private String getHashUserPassword30() {
		return getHashUserPassword32()!=null?getHashUserPassword32().substring(0,30):null;
	}
	

	public String getRestHashUserPassword() {
		return _strHashPassword.substring(30, 32);
	}

	public String getRunAppName() {
		return _strRunAppName.toUpperCase();
	}

	public String getHashRunAppName() {
		return _strHashRunAppName;
	}

	

	public int getUserDep() {
		return _nUserDep;
	}

	public void setUserDep(int aValue) {
		_nUserDep = aValue;
	}
	
	public void setUserDep(String aValue) {
		_nUserDep = aValue!=null?Integer.parseInt(aValue):0;
	}

	public String getUserGRP() {
		return _strUserGRP;
	}

	public void setUserGRP(String aValue) {
		_strUserGRP = aValue;
	}

	public String getUserTel() {
		return _strUserTel;
	}

	public void setUserTel(String aValue) {
		_strUserTel = aValue;
	}

	public String getUserPath() {
		return _strUserPath;
	}

	public void setUserPath(String aValue) {
		if (aValue != null && aValue.length() > 0) {
			_strUserPath = aValue.substring(0, 1) + ":";
			
		}
	}


	public String getUserID_Key() {
		if (_nID_Key == null || _nID_Key.length() == 0)
			return "0";
		else
			return _nID_Key;
	}

	public void setUserID_Key(String aValue) {
		_nID_Key = aValue;
	}

	public void setUserRight(String aValue) {
		_strUserRight = aValue;
	}
	
	private int _nUserTOBO;
	public void setUserTOBO(int aValue) {
		_nUserTOBO =  aValue;		
	}
	
	public int getUserTOBO () {
		return _nUserTOBO;		
	}

	public String getUserRight() {
		if (_strUserRight == null)
			return "";
		else
			return _strUserRight;
	}

	/**
	 * @param nodeValue
	 */
	public void setHashUserName(String nodeValue) {
		_strHashName = nodeValue;		
	}

	public void setRunAppName (String aValue) {
		_strRunAppName = aValue;
		//_strHashRunAppName =_strRunAppName!=null?CHashFunctions.nativeStrToHash32(2048,_strRunAppName).toUpperCase():null;
		_strHashRunAppName = aValue;
	}
	
		
	public String getLoginName() {
		return _fUseHashUser?getHashUserName30():getUserName();
	}
	
	public String getLoginPassword() {
		return 	_fUseHashUser?getHashUserPassword30():getPassword();
	}
	
	
	
}
