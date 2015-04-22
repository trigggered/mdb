/**
 * 
 */
package mdb.core.shared.utils.comparator;

import java.util.Comparator;

/**
 * @author azhuk
 * Creation date: Mar 28, 2014
 *
 */
public class ComparatorByPosition implements Comparator<IPosition> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(IPosition o1, IPosition o2) {
		return o1.getPosition() -  o2.getPosition();
	}
	
}
