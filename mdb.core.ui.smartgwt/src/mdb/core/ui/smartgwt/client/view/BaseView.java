package mdb.core.ui.smartgwt.client.view;

import mdb.core.ui.client.view.ABaseView;
import mdb.core.ui.client.view.IView;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.Layout;

public abstract class BaseView extends ABaseView implements IView, ISGWTComponent {

    
    private Layout _viewPanel;        
 
    public BaseView() {
    	createComponents();    	
    }

    @Override
    public Layout getMainLayout() {
    	return _viewPanel;	
    }
    
    public void setMainLayout(Layout value) {
    	_viewPanel = value;	
    }
    
    protected void initSize() {
    	getMainLayout().setWidth100();
    	getMainLayout().setHeight100();    
    	getMainLayout().setLayoutMargin(5);
    }           

	
	@Override
	public void print() {		
		Canvas.showPrintPreview(getMainLayout());		
	}
}
