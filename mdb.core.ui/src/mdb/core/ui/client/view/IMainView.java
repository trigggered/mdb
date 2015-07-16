package mdb.core.ui.client.view;

public interface IMainView extends IView, IManagingView {
	
	public int getAppId();
	

	public abstract String getCaption();

	public abstract void closeCurrentTab();

	public abstract void closeAllTab();

	public abstract String checkExistView(IView viewForOpen);

	public abstract void closeAllTabButCurrent();

	/**
	 * @param view
	 */
	public void openView(IView view);

	/**
	 * @return
	 */
	
}
