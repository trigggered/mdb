package mdb.core.ui.client.view.dialogs.wizard;

import com.smartgwt.client.widgets.Canvas;

public abstract class WizardPage extends Canvas {

	private IWizard wizard;

	public WizardPage(IWizard wizard) {
		this.wizard = wizard;
		addChild(createContent());
	}

	public boolean isNextButtonDisabled() {
		return wizard.getNextButton().isDisabled();
	}

	public void setNextButtonDisabled(boolean disabled) {
		wizard.getNextButton().setDisabled(disabled);
	}

	public boolean isBackButtonDisabled() {
		return wizard.getBackButton().isDisabled();
	}

	public void setBackButtonEnabled(boolean disabled) {
		wizard.getBackButton().setDisabled(disabled);
	}

	public boolean isFinishButtonDisabled() {
		return wizard.getFinishButton().isDisabled();
	}

	public void setFinishButtonDisabled(boolean disabled) {
		wizard.getFinishButton().setDisabled(disabled);
	}

	public boolean isCancelButtonDisabled() {
		return wizard.getCancelButton().isDisabled();
	}

	public void setCancelButtonDisabled(boolean disabled) {
		wizard.getCancelButton().setDisabled(disabled);
	}
	
	public  IWizard getWizard() {
		return wizard;
	}

	/**
	 * if true, this page can finish and the finish button is enabled.
	 */
	abstract public boolean canFinish();

	/**
	 * Returns the page title.
	 */
	abstract public String getTitle();

	/**
	 * Returns the content of this page.
	 */
	abstract protected Canvas createContent();

	/**
	 * if false, this page will be skipped. Default value is true.
	 */
	public boolean shouldDisplayPage() {
		return true;
	}

	/**
	 * Invoked after a page is displayed. Default implementation does nothing.
	 */
	public void onPageDisplayed() {
	}
	
	
	
	public abstract boolean isCanLeavePage();
}
