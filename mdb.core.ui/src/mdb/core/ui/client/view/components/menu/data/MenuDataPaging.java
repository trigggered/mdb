/**
 * 
 */
package mdb.core.ui.client.view.components.menu.data;

import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.data.IDataPaging;
import mdb.core.ui.client.data.IDataSource;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.components.menu.IMenuItem.ItemType;
import mdb.core.ui.client.view.data.IDataView;


/**
 * @author azhuk
 * Creation date: Dec 12, 2012
 *
 */
public class MenuDataPaging extends  AMenuData implements IDataPaging {		
		
	public MenuDataPaging(String name, IDataSource dataSource,IDataView view ) {
		super(name,  dataSource, view );		
	}	
	

	@Override
	protected void createDefaultButtons() {
		setShowCaption(false);
		setShowHint(true);		
		
		if (getDataSource().getViewDataBinder()!= null && getDataSource().isPagingUsage()) {
			addSeparator();
			createButton(IDataPaging.Buttons.dataFirstPage);
			createButton(IDataPaging.Buttons.dataPreviousPage);
			
			IMenuItem item = addItem(ItemType.NumberItem);
			initPropertyMenuItem(item, IDataPaging.Buttons.dataInputPageNumber);
			
			item.setWidth(30);			
			item.setCommand(getButtonCommand(IDataPaging.Buttons.dataInputPageNumber));
			item.setValue("1");			
			getButtons().put(IDataPaging.Buttons.dataInputPageNumber, item);
			
			createButton(IDataPaging.Buttons.dataNextPage);
			createButton(IDataPaging.Buttons.dataLastPage);
		}			
	}
			
	/* (non-Javadoc)
	 * @see mdb.core.ui.data.IDataPaging#firstPage()
	 */
	@Override
	public void firstPage() {
		goToPageNumber (1);
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.data.IDataPaging#previousPage()
	 */
	@Override
	public void previousPage() {
		int currentPage =getDataSource().getRequestEntity().getPage();
		int nextPage = currentPage == 1?1:--currentPage;
		goToPageNumber (nextPage);		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.data.IDataPaging#goToPageNumber(int)
	 */
	@Override
	public void goToPageNumber(int pageNumber) {
		getDataSource().getRequestEntity().setRequestFieldsDescription(false);
		getDataSource().getRequestEntity().setPage(pageNumber);		
		getButton(Buttons.dataInputPageNumber).setValue(String.valueOf(pageNumber));
		getDataSource().refresh();
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.data.IDataPaging#nextPage()
	 */
	@Override
	public void nextPage() {
		goToPageNumber (getDataSource().getRequestEntity().getPage()+1);		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.data.IDataPaging#lastPage()
	 */
	@Override
	public void lastPage() {
		// TODO Auto-generated method stub
		
	}
	
	public ICommand<IMenuItem> getButtonCommand(Buttons button) {		
		
		switch (button) {
			case dataFirstPage: return new ICommand<IMenuItem>() {
				 							public void execute(IMenuItem sender) {
				 								firstPage();
				 							}
										};				
			case dataPreviousPage: return new ICommand<IMenuItem>() {
											public void execute(IMenuItem sender) {
													 previousPage();
												}
										};
					
			case dataInputPageNumber: return new ICommand<IMenuItem>() {
												public void execute(IMenuItem sender) {													
													goToPageNumber((Integer) sender.getValue());																			
												}
										};
			
			case dataNextPage: return new ICommand<IMenuItem>() {
												public void execute(IMenuItem sender) {
													nextPage();
												}
										};
					
			case dataLastPage: return new ICommand<IMenuItem>() {
												public void execute(IMenuItem sender) {
													lastPage();
												}
										};
		default:
			break;							
		}
		return null;
	}	
	
}
