/**
 * 
 */
package mdb.core.ui.client.view.data.tree;


/**
 * @author azhuk
 * Creation date: Dec 20, 2013
 *
 */
public interface ITreeView {

	public void expandTree();	
	
	public void collapseTree();
	
	public void findNodeById(String id);
	
	public void refreshTree();
}
