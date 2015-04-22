/**
 * 
 */
package mdb.core.shared.utils;

import java.util.logging.Logger;

/**
 * @author azhuk
 * Creation date: Mar 11, 2015
 *
 */
public class Clipboard {
	private static final Logger _logger = Logger.getLogger(Clipboard.class
			.getName());
	
    public static native void copy(String value) /*-{
    $wnd.window.clipboardData.setData("Text", value);
    }-*/;

    public static native String getClipboardContent () /*-{
    	return $wnd.window.clipboardData.getData("Text");
    }-*/;
}
