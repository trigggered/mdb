/**
 * 
 */
package mdb.core.ui.client.view.components;

import java.util.HashMap;

/**
 * @author azhuk
 * Creation date: Dec 25, 2012
 *
 */
public abstract class  AVisualComponent extends Component implements IVisualComponent {	
	
	public AVisualComponent () {
		super("", "");
	}
	/*
	public AComponent (String caption, String img) {
		super(caption, img);	
	}*/
	
	private String _styleName;
	private int 	_width;

	
	private boolean _showCaption = true;
	private boolean _showHint 	   = false; 
	
	private  HashMap<String, String> _attributes = new HashMap<String, String>();
	
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.components.IComponent#getAttribute(java.lang.String)
	 */
	@Override
	public String getAttribute(String name) {
		if ( isAttributeExist(name) ) {
			return _attributes.get(name);
		} else {
			return null;
		}		
	}
	
	@Override
	public boolean isAttributeExist(String name) {
		return  _attributes.containsKey(name);					
	}
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.components.IComponent#setAttribute(java.lang.String, java.lang.String)
	 */
	@Override
	public void setAttribute(String name, String value) {
		_attributes.put(name, value);		
	}
	
	@Override
	public boolean isShowCaption() {
		return _showCaption;
	}
	
	@Override
	public void setShowCaption(boolean value) {
		this._showCaption = value;
	}
	
	@Override
	public boolean isShowHint() {
		return _showHint;
	}
	
	@Override
	public void setShowHint(boolean value) {
		this._showHint = value;
	}
	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.components.IVisualComponent#getStyleName()
	 */
	@Override
	public String getStyleName() {
		return _styleName;
	}
	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.components.IVisualComponent#setStyleName(java.lang.String)
	 */
	@Override
	public void setStyleName(String value) {
		_styleName = value;
		
	}
	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.components.IVisualComponent#getWidth()
	 */
	@Override
	public int getWidth() {
		return _width;
	}

	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.components.IVisualComponent#setWidth(int)
	 */
	@Override
	public void setWidth(int value) {
		_width = value;
	}
	
	
}
