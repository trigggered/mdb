package mdb.core.ui.client.data.bind;

public class DataBindException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataBindException (Exception ex) {				
			super(ex.getMessage(), ex.getCause());
			super.setStackTrace(ex.getStackTrace());
	}
	
	public DataBindException (String message) {
		super (message);
	}
}
