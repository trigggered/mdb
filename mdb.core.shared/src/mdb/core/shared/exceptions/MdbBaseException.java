/**
 * 
 */
package mdb.core.shared.exceptions;

import java.util.logging.Logger;

/**
 * @author azhuk
 * Creation date: Sep 7, 2012
 *
 */
public class MdbBaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger _logger = Logger
			.getLogger(MdbBaseException.class.getName());
	
	
	public MdbBaseException() {
		super();		
    }

    /**
     * Constructs an <code>IllegalAccessException</code> with a detail message. 
     *
     * @param   s   the detail message.
     */
    public MdbBaseException(String s) {
    	super(s);    	
    	_logger.severe(s);
    }
    
}
