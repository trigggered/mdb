/**
 * 
 */
package mdb.core.vaadin.ui.components.menu;

import java.util.LinkedHashMap;
import java.util.logging.Logger;

import mdb.core.ui.client.resources.locales.Captions;
import mdb.core.ui.client.util.BooleanCallback;
import mdb.core.ui.client.view.components.menu.IMenuContainer.EContaynerOriented;
import mdb.core.ui.client.view.dialogs.message.Dialogs;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.AlignmentInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component.Listener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.Component.Event;

/**
 * @author azhuk
 * Creation date: Aug 13, 2015
 *
 */
public class MainMenuBarContainer extends MenuBarContainer {
	private static final Logger _logger = Logger
			.getLogger(MainMenuBarContainer.class.getName());
	
	
	private static LinkedHashMap<String, String> themeVariants = new LinkedHashMap<String, String>();
    static {
    	
    	themeVariants.put("dashboard", "dashboard");
    	themeVariants.put("tests-valo", "Default");
        themeVariants.put("tests-valo-blueprint", "Blueprint");
        themeVariants.put("tests-valo-dark", "Dark");
        themeVariants.put("tests-valo-facebook", "Facebook");
        themeVariants.put("tests-valo-flatdark", "Flat dark");
        themeVariants.put("tests-valo-flat", "Flat");
        themeVariants.put("tests-valo-light", "Light");
        themeVariants.put("tests-valo-metro", "Metro");
        themeVariants.put("tests-valo-reindeer", "Migrate Reindeer");
    }
   
    
    public MainMenuBarContainer () {
    	super (EContaynerOriented.Vertical);
    }
    
	/**
	 * @param contaynerOriented
	 */
	public MainMenuBarContainer(EContaynerOriented contaynerOriented) {
		super (contaynerOriented);
	}

	
	
	@Override
	protected  void createContainer() {
		setContainer(new MenuBar());
		getContainer().addStyleName("borderless");
		HorizontalLayout top = new HorizontalLayout();
		top.setDefaultComponentAlignment(Alignment.TOP_RIGHT);
		top.addComponents(getLogOut(), getThemeSelect() );
		
		getMainLayout().addComponents(top, getContainer());
		getContainer().setWidth("100%");
	}

	/**
	 * @return
	 */
	private Component getLogOut() {
		Button link = new Button("LogOut");
		
        link.addStyleName("link");
        link.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
					Dialogs.AskDialog(Captions.Q_LOG_OUT, new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							// TODO Auto-generated method stub
							
						}
					});;				
			}
		});
        
		return link;
	}

	/**
	 * @return
	 */
	private Component getThemeSelect() {
		return createThemeSelect() ;
	}
	
	private Component createThemeSelect() {
        final NativeSelect ns = new NativeSelect();
        ns.setNullSelectionAllowed(false);
        ns.setId("themeSelect");
        ns.addContainerProperty("caption", String.class, "");
        ns.setItemCaptionPropertyId("caption");
        for (final String identifier : themeVariants.keySet()) {
            ns.addItem(identifier).getItemProperty("caption")
                    .setValue(themeVariants.get(identifier));
        }

 
        ns.select(UI.getCurrent().getTheme());
        
        ns.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(final ValueChangeEvent event) {
            	UI.getCurrent().setTheme((String) ns.getValue());
            }
        });
        return ns;
    }
	
	protected LinkedHashMap<String, String>  getSkinMapValues() {
    	return themeVariants;
    }
	
}
