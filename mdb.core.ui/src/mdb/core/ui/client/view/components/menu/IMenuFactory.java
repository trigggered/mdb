/**
 * 
 */
package mdb.core.ui.client.view.components.menu;




/**
 * @author azhuk
 * Creation date: Dec 25, 2012
 *
 */
public interface IMenuFactory {
    
	public IMenuContainer createMenuContainer();
	
	public IMenuContainer createMenuContainer(IMenuContainer.EContaynerOriented contaynerOriented);
	
}
