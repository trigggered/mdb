/**
 * 
 */
package mdb.core.shared.exceptions;


/**
 * @author azhuk
 * Creation date: Sep 7, 2012
 *
 */
public class GatewayCallException extends MdbBaseException {
	
	private static final long serialVersionUID = 1L;

	public GatewayCallException() {
		super();
    }

    /**
     * Constructs an <code>IllegalAccessException</code> with a detail message. 
     *
     * @param   s   the detail message.
     */
    public GatewayCallException(String s) {
    	super(s);
    }
}
