package mdb.core.ui.client.view.components.menu.mdb;

import mdb.core.ui.client.view.IView;

public interface IMdbMainMenuAction {
	IView getView(String menuAction);
}
