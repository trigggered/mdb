package mdb.core.ui.client.view.dialogs.wizard;

import java.util.List;

import com.smartgwt.client.widgets.IButton;

public interface IWizard {

	IButton getNextButton();

	IButton getBackButton();

	IButton getFinishButton();

	IButton getCancelButton();

	/**
	 * @return a list of <code>WizardPage</code>.
	 */
	List<WizardPage> getPages();

	/**
	 * Shows the wizard.
	 */
	void show();
}
