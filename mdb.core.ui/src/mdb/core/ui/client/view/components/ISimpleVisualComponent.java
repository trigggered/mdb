/**
 * 
 */
package mdb.core.ui.client.view.components;



/**
 * @author azhuk
 * Creation date: Dec 25, 2012
 *
 */
public interface ISimpleVisualComponent extends IComponent {	
		
	String getCaption();
	void setCaption(String value);
	
	String getImg();
	void setImg(String value);
	
	String getHint();
	void  setHint(String value);		
}
