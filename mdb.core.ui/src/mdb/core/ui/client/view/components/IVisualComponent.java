/**
 * 
 */
package mdb.core.ui.client.view.components;


/**
 * @author azhuk
 * Creation date: Dec 25, 2012
 *
 */
public interface IVisualComponent extends ISimpleVisualComponent {		
	
	public String getAttribute(String name);
	public void  setAttribute(String name, String value);
	public boolean isAttributeExist(String name);
	
	boolean isShowCaption();
	void setShowCaption(boolean value);
	
	boolean isShowHint();
	void setShowHint(boolean value);
	
	String getStyleName();
	void setStyleName(String value);
	
	int getWidth();
	void setWidth(int value);
}
