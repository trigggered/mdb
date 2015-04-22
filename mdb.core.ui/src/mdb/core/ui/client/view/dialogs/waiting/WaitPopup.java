/**
 * 
 */
package mdb.core.ui.client.view.dialogs.waiting;

import java.util.logging.Logger;

import com.smartgwt.client.types.ImageStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

import java.util.Stack;

/**
 * @author azhuk
 * Creation date: Dec 5, 2014
 *
 */
public class WaitPopup {
	private static final Logger _logger = Logger.getLogger(WaitPopup.class
			.getName());
	
	private final Stack<String> stack = new Stack<String>();
	private final Canvas canvas = createModalMessage();
	private Label label;

	public void show(String actionName) {
		if (actionName == null) {
			actionName = "";
		}
		
		stack.push(actionName);
		setMessage();
		canvas.show();
	}

	private void setMessage() {
	String msg = "Loading…";
	for (String action : stack) {
	if (!action.equals("")) {
	msg = action;
	break;
	}
	}
	msg = msg + " Please wait…";
	label.setContents(msg);
	}

	public void hide() {
	if (!stack.isEmpty()) {
	stack.pop();
	if (stack.isEmpty())
	canvas.hide();
	}
	}

	public void hideFinal() {
	stack.removeAllElements();
	canvas.hide();
	}

	private Canvas createModalMessage() {
	//Img loadingIcon = new Img("[SKIN]/" + "wait", 16, 16);
	Img loadingIcon = new Img("waiting/ajax-loader.gif", 16, 16);
	
	loadingIcon.setShowEdges(false);
	loadingIcon.setImageType(ImageStyle.NORMAL);

	label = new Label();
	label.setWidth(200);
	label.setHeight100();
	label.setValign(VerticalAlignment.CENTER);

	HLayout hLayout = new HLayout();
	hLayout.setLayoutMargin(20);
	hLayout.setMembersMargin(5);

	VLayout vLayout = new VLayout();
	vLayout.setMembers(new LayoutSpacer(), loadingIcon, new LayoutSpacer());
	hLayout.setMembers(new LayoutSpacer(), vLayout, label, new LayoutSpacer());

	Window window = new Window();
	window.setShowHeader(false);
	window.setShowHeaderBackground(false);
	window.setShowHeaderIcon(false);
	window.setIsModal(true);
	window.setShowModalMask(true);
	window.setWidth(300);
	window.setHeight(120);
	window.addItem(hLayout);
	window.centerInPage();
	return window;
	}
}
