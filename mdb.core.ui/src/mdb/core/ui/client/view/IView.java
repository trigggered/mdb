/**
 * 
 */
package mdb.core.ui.client.view;


import mdb.core.ui.client.util.BooleanCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.tab.Tab;

/**
 * @author azhuk
 * Creation date: Aug 7, 2013
 *
 */
public interface IView extends ISingleInstanceOnly {	
	public  String getCaption();
	public  void  setCaption(String value);
	
	public  String getImgCaption();
	public  void setImgCaption(String value);	
		
	public Canvas getCanvas();
	public void initialize();
	public void setMainView (IMainView mainView);
	public IMainView getMainView ();
	
	public void addCustomProperty(String name, Object value);
	public Object getCustomProperty(String name);
	/**
	 * @param tab
	 */
	public void setOwnerWindow(IOwnerWnd tab);
	/**
	 * @return
	 */
	public IOwnerWnd getOwnerWindow();
	/**
	 * @return
	 */
	void IsCanClose(BooleanCallback callback);
	public void close();
	/**
	 * 
	 */
	public void redraw();
	
	public void setCanEdit(boolean value);
	
	boolean isCanEdit();
	
	public void setViewContainerID(String id);
	
	public String getViewContainerID( );
	public void print();
}
