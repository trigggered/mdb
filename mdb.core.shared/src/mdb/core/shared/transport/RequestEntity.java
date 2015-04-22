/**
 * 
 */
package mdb.core.shared.transport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.IsSerializable;

import mdb.core.shared.data.Fields;
import mdb.core.shared.data.EMdbEntityActionType;
import mdb.core.shared.data.Params;
import mdb.core.shared.utils.comparator.IPosition;

/**
 * @author azhuk
 * Creation date: Aug 10, 2012
 *
 */
public class RequestEntity implements  IRequestData, IPosition, IsSerializable
{		
	private static final Logger _logger = Logger.getLogger(RequestEntity.class.getName());
	
	private static final long serialVersionUID = 1L;
	private boolean isRequestFieldsDescription = true;

	  private int _entityId;
	  private int _methodId;
	  private ExecuteType _executeType = ExecuteType.GetData;

	  
	  private Params _params = new Params();
	  //private String _result;

	  private String _data;
	  private Fields _fields;
	  private String _name;
	  private String[] _keys;
	  
	  private boolean _pagingUsage = false;
	  private int _page = 1;
	  private int _rowCountInPage = 50;	  
	  
	  
	  //private ArrayList<String>  _lstForInsert;
	  private HashMap<String, String>  _hmForInsert;
	  private HashMap<Integer, String>  _hmForUpdate;
	  private ArrayList<String>  _lstForDelete;	  
	  private HashMap<Integer, ChangedData> _execActions;
	  
	  private int _position;

	  public RequestEntity () {		  
	  }
	  
	  public RequestEntity (Integer entityId) {
		  this (entityId, null);
	  }
	  
	   
	  public RequestEntity (Integer entityId, String key) {		  			
			setEntityId(entityId);
			setName (key == null?entityId.toString():key);
	  }
	  
	  public RequestEntity (Integer entityId, String key, boolean requestFieldsDescription) {
		  this (entityId, key);
		  setRequestFieldsDescription(requestFieldsDescription);
	  }
	  
	  public void loseChanges() {
		  _logger.info("Clear changes for EntityId=" +getEntityId());
		  getForInsert().clear();
		  getForUpdate().clear();
		  getForDelete().clear();
		  
		  _logger.info("Check changes after clear for EntityId=" +getEntityId());
		  isHasChanges();
	  }
	  
	  public boolean isHasChanges() {
		  boolean toReturn = false;
		  
		  toReturn= (_hmForInsert!=null && _hmForInsert.size()>0) ||
				  (_hmForUpdate!=null && _hmForUpdate.size()>0) ||
				  (_lstForDelete!=null && _lstForDelete.size()>0);
		  
		  
		  _logger.info("Result for Check changes exist for entity id =" +getEntityId() +" is: " +toReturn);
		  return toReturn;
	  }
	  
	  public void setExecActionData (Integer actionCode, String originValue, String chagetValue) {
		  getExecActions().put(actionCode, new ChangedData(originValue, chagetValue) );
	  }
	  
	  public boolean isExecAction () {
		  return (getExecActions().size() >0 ) ;  
	  }
	  
	  public void setData (String value) {
		  _data = value;
	  }
	  
	  public String getData () {
		  return _data;
	  }
	  
	  public void setName (String value) {
		  _name = value;
	  }
	  
	  public String getName () {
		  return _name;
	  }
	  
	  
	  public Fields getFields () {
		  return _fields;
	  }
	  
	  
	  public void setFields  (Fields value) {
		  _fields = value;
	  }
	/**
	 * @return the _params
	 */
	public Params getParams() {
		return _params;
	}
	/**
	 * @param _params the _params to set
	 */
	public void setParams(Params params) {
		this._params = params;
	}
	
	/**
	 * @return the _entityId
	 */
	public int getEntityId() {
		return _entityId;
	}
	/**
	 * @param _entityId the _entityId to set
	 */
	public void setEntityId(int _entityId) {
		this._entityId = _entityId;
	}


	public int getPage() {
		return _page;
	}

	public void setPage(int _page) {
		this._page = _page;
	}

	public int getRowCountInPage() {
		return _rowCountInPage;
	}

	public void setRowCountInPage(int _rowCount) {
		this._rowCountInPage = _rowCount;
	}

	public HashMap<Integer, String>  getForUpdate() {
		if (_hmForUpdate == null) {
			_hmForUpdate = new HashMap<Integer, String> ();
		}
		return _hmForUpdate;
	}

	public void addForUpdate(int recIndx, String value) {
		getForUpdate().put(Integer.valueOf(recIndx), value);		
	}

	public HashMap<String, String> getForInsert() {
		if (_hmForInsert == null) {
			_hmForInsert = new HashMap<String, String>();
		}
		return _hmForInsert;
	}

	@Override
	public  void addtDataForChange( EMdbEntityActionType actionType , String data  )  {

		 switch (actionType ) {
		 case INSERT:			 
			 addForInsert(null, data);
			  break;
		 case UPDATE:
			 addForUpdate(0, data);
			 break;
		 case DELETE:
			 addForDelete(data);
			 break;
		default:
			break;
		 }		 
	}
	
	public void addForInsert(String key, String value) {
		if (key == null) {			
			 key = String.valueOf( getForInsert().size() + 1);
		}
	
		getForInsert().put(key, value);
	}

	public ArrayList<String> getForDelete() {
		if (_lstForDelete == null) {
			_lstForDelete = new ArrayList<String>();
		}
		return _lstForDelete;
	}

	public void addForDelete(String value) {
		getForDelete().add(value);		
	}
	
	public  HashMap<Integer, ChangedData> getExecActions() {
		if (_execActions == null) {
			_execActions = new HashMap<Integer, ChangedData>();
		}
		return _execActions;
	}

	public ExecuteType getExecuteType() {
		return _executeType;
	}

	public void setExecuteType(ExecuteType value) {
		this._executeType = value;
	}

	public int getMethodId() {
		return _methodId;
	}

	public void setMethodId(int value) {
		this._methodId = value;
	}

	@Override
	public boolean isRequestFieldsDescription() {
		return isRequestFieldsDescription;
	}

	@Override
	public void setRequestFieldsDescription(boolean isRequestFieldsDescription) {
		this.isRequestFieldsDescription = isRequestFieldsDescription;
	}


	public boolean isPagingUsage() {
		return _pagingUsage;
	}

	public void setPagingUsage(boolean pagingUsage) {
		_pagingUsage = pagingUsage;
	}

	public String[] getKeys() {
		return _keys;
	}

	public void setKeys(String[] value) {
		_keys = value;
	}

	/* (non-Javadoc)
	 * @see mdb.core.shared.transport.IPosition#getPosition()
	 */
	@Override
	public int getPosition() {
		return _position;
	}

	/* (non-Javadoc)
	 * @see mdb.core.shared.transport.IPosition#setPosition(int)
	 */
	@Override
	public void setPosition(int value) {
		_position = value;		
	}		
	
}
