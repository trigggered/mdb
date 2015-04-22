/**
 * 
 */
package mdb.core.ui.client.data.impl;

import java.util.logging.Logger;

import mdb.core.shared.utils.ISequenceGenerator;

/**
 * @author azhuk
 * Creation date: Mar 1, 2013
 *
 */
public class SequenceGenerator implements ISequenceGenerator {
	private static final Logger _logger = Logger
			.getLogger(SequenceGenerator.class.getName());

	private int _value = 0;
	
	/* (non-Javadoc)
	 * @see mdb.core.shared.utils.ISequenceGenerator#nextVal()
	 */
	@Override
	public String nextVal() {
		String toReturn = String.valueOf(_value--);
		_logger.info("New generate value is: "+toReturn);
		return toReturn;  		
	}
}
