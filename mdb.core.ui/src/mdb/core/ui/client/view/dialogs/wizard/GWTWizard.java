package mdb.core.ui.client.view.dialogs.wizard;

import java.util.List;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public abstract class GWTWizard extends Window implements IWizard {

	private IButton nextButton;
	private IButton backButton;
	private IButton finishButton;
	private IButton cancelButton;
	private List<WizardPage> pages;
	private int currentPage = 0;
	private Canvas pageHost;
	private Label lblPageDescription;

	public GWTWizard(String title) {
		setTitle(title);
		init();
	}

	protected void init() {
		setAutoSize(false);
		setAutoCenter(true);
		setShowMinimizeButton(false);
		setShowMaximizeButton(false);
		resizeTo(500, 400);
		setMinWidth(500);
		setMinHeight(400);
		setCanDragResize(true);
		setShowResizer(true);
		createButtons();
		createContentArea();
		pages = createPages();
	}

	private void createContentArea() {
		VLayout layout = new VLayout();
		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		lblPageDescription = new Label("Page title goes here");
		lblPageDescription.setWidth100();
		lblPageDescription.setHeight("50px");
		lblPageDescription.setTitleStyle("bold");
		lblPageDescription.setPadding(10);
		toolStrip.addMember(lblPageDescription);
		layout.addMember(toolStrip);

		pageHost = new Canvas();
		pageHost.setSize("100%", "100%");
		pageHost.setBorder("solid 1px");
		layout.addMember(pageHost);
		addItem(layout);

		HStack hStack = new HStack();
		hStack.setHeight("25");
		hStack.setMembersMargin(4);
		hStack.setPadding(10);
		hStack.setAlign(Alignment.RIGHT);

		hStack.addMember(backButton);
		hStack.addMember(nextButton);
		hStack.addMember(finishButton);
		hStack.addMember(cancelButton);
		addItem(hStack);
	}

	protected void createButtons() {
		nextButton = createNavigationButton("Next >");
		nextButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				next();
			}
		});
		backButton = createNavigationButton("< Back");
		backButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				back();
			}
		});
		finishButton = createNavigationButton("Finish");
		finishButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (performFinish()) {
					destroy();
				}

			}
		});
		cancelButton = createNavigationButton("Cancel");
		cancelButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				cancel();
			}
		});
	}

	@Override
	public void show() {
		super.show();
		currentPage = 0;
		loadPage(true);
	}

	protected void cancel() {
		destroy();
	}

	protected void next() {
		WizardPage wizardPage = pages.get(currentPage);
		if (  wizardPage.isCanLeavePage() ) { 
		currentPage++;
		realizeButoonsState();
		loadPage(true);
		}
	}

	protected void back() {
		currentPage--;
		realizeButoonsState();
		loadPage(false);
	}

	protected void realizeButoonsState() {
		getNextButton().setDisabled(isLastPage());
		getBackButton().setDisabled(currentPage <= 0);
		WizardPage wizardPage = pages.get(currentPage);
		getFinishButton().setDisabled(!wizardPage.canFinish() && !isLastPage());
	}

	private boolean isLastPage() {
		return currentPage >= pages.size() - 1;
	}

	private void loadPage(boolean goNext) {
		realizeButoonsState();
		WizardPage wizardPage = pages.get(currentPage);
		if (!wizardPage.shouldDisplayPage()) {
			if (goNext) {
				next();
			} else {
				back();
			}
			return;
		}
		Canvas[] children = pageHost.getChildren();
		for (Canvas child : children) {
			pageHost.removeChild(child);
		}
		pageHost.addChild(wizardPage);
		wizardPage.setVisible(true);
		wizardPage.setWidth100();
		wizardPage.setHeight100();
		lblPageDescription.setContents(wizardPage.getTitle());
		wizardPage.onPageDisplayed();
	}

	abstract protected List<WizardPage> createPages();

	abstract protected boolean performFinish();

	protected IButton createNavigationButton(String title) {
		IButton btn = new IButton(title);
		return btn;
	}

	public IButton getNextButton() {
		return nextButton;
	}

	public IButton getBackButton() {
		return backButton;
	}

	public IButton getFinishButton() {
		return finishButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

	public List<WizardPage> getPages() {
		return pages;
	}
}
