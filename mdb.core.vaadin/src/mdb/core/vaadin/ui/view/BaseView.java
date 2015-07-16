/**
 * 
 */
package mdb.core.vaadin.ui.view;

import java.util.logging.Logger;

import mdb.core.ui.client.view.ABaseView;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

/**
 * @author azhuk
 * Creation date: Jul 16, 2015
 *
 */
public abstract class BaseView  extends ABaseView implements IVaadinComponnet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger _logger = Logger.getLogger(BaseView.class
			.getName());
	
	Layout _viewPanel; 
	
	@Override
	  protected void createMainLayout() {
	    	
	    	switch (_viewPanelType) {
	    	case HLayout: 
	    		_viewPanel = new HorizontalLayout();
	    		break;
	    	case VLayout:
	    		_viewPanel = new VerticalLayout();
	    		break;
	    	default :
	    		_viewPanel = new HorizontalLayout();    			
	    	}
	    	
	    	_viewPanel.setSizeFull();	    		
	         	        	        
	        
	    }


	/* (non-Javadoc)
	 * @see mdb.core.ui.vaadin.client.view.ABaseView#initSize()
	 */
	@Override
	protected void initSize() {		
		_viewPanel.setSizeFull();      		
	}


	/**
	 * @return
	 */
	public Layout getMainLayout() {		
		return _viewPanel;
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.vaadin.client.view.IVaadinVeiw#setMainLayout(com.vaadin.ui.Component)
	 */
	@Override
	public void setMainLayout(Layout layout) {
		_viewPanel = layout;
		
	}


	  
}
