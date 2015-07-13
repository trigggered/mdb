/**
 * 
 */
package mdb.core.ui.client.view.data.card.section;

import mdb.core.ui.client.view.data.IDataView;
import mdb.core.ui.client.view.data.card.ICard;

/**
 * @author azhuk
 * Creation date: Jul 10, 2015
 *
 */
public interface IDataSection  extends IDataView{
	
	public enum ESectionType {
		Grid,
		VerticalGrid,
		Fields		
		
	}
	/**
	 * @param sectionid
	 */
	void setSectionId(int sectionid);
	
	int  getSectionId();

	/**
	 * @param aCard
	 */
	void setCard(ICard aCard);			
	ICard getCard(); 

}
