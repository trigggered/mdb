/**
 * 
 */
package mdb.core.shared.transport;

import mdb.core.shared.data.EMdbEntityActionType;
import mdb.core.shared.data.Fields;
import mdb.core.shared.data.Params;
import mdb.core.shared.utils.comparator.IPosition;
/**
 * @author azhukE
 * Creation date: Jul 22, 2013
 *
 */
public interface IRequestData extends IPosition, java.io.Serializable {

	public enum ExecuteType {
		None,
		GetData,
		ChangeData,
		ExecAction,
		ApplyFilter
	}
	
	public ExecuteType getExecuteType();
	public void setExecuteType(ExecuteType value);
	public int getEntityId();
	public String getData ();
	public void setData (String  value);
	public Fields getFields ();
	public String getName();
	public String[] getKeys();
	public Params getParams();
	public void setParams(Params value);
	public void setExecActionData (Integer actionCode, String originValue, String chagetValue);
	public boolean isHasChanges();
	public void loseChanges();
	
	public boolean isRequestFieldsDescription();

	public void setRequestFieldsDescription(boolean value);
	/**
	 * @param actionType
	 * @param data
	 */
	void addtDataForChange(EMdbEntityActionType actionType, String data);
}
