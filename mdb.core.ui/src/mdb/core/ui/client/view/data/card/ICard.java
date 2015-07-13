/**
 * 
 */
package mdb.core.ui.client.view.data.card;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author azhuk
 * Creation date: Jul 10, 2015
 *
 */
public interface ICard {
	
	public enum EViewState {
		 New,
		 Edit 
		}
	
	
	public enum EAfterSaveAction {
		None, 
		Refresh,
		Close
	}
	
	
	public long getCardId();

	/**
	 * @return
	 */
	public int getCardEntityId();

	/**
	 * @return
	 */
	public EViewState getViewState();

	/**
	 * @param value
	 */
	void setCardId(long value);
}
