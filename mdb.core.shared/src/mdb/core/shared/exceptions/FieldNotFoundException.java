/**
 * 
 */
package mdb.core.shared.exceptions;


/**
 * @author azhuk
 * Creation date: Sep 7, 2012
 *
 */
public class FieldNotFoundException extends MdbBaseException {
	
		
	 		/**
	 * 
	 */
	 		private static final long serialVersionUID = 1L;

			public FieldNotFoundException() {
			super();
		    }

		    /**
		     * Constructs an <code>IllegalAccessException</code> with a detail message. 
		     *
		     * @param   s   the detail message.
		     */
		    public FieldNotFoundException(String s) {
			super(s);
		    }
}
