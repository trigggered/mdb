package mdb.core.shared.exceptions;

import java.io.Serializable;

public class SessionExpiredException extends MdbBaseException implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	public String getMessage() {
		return "Current web session is Expired";
	}
}
