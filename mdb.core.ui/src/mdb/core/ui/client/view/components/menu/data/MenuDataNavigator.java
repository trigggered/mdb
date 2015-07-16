/**
 * 
 */
package mdb.core.ui.client.view.components.menu.data;

import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.data.IBaseDataSource;
import mdb.core.ui.client.data.IDataNavigator;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.data.IDataView;


/**
 * @author azhuk
 * Creation date: Dec 12, 2012
 *
 */
public class MenuDataNavigator extends  AMenuData  implements IDataNavigator {



	public MenuDataNavigator(String name, IBaseDataSource dataSource, IDataView view) {
		super(name, dataSource,view);		
	}	
	
		
	
	public IDataNavigator getDataNavigator() {		
		return this;
	}

	
	protected void createDefaultButtons() {
		setShowCaption(false);
		setShowHint(true);
		
		addSeparator();	
		
		createButton(IDataNavigator.Buttons.dataInsert);
		createButton(IDataNavigator.Buttons.dataEdit);
		createButton(IDataNavigator.Buttons.dataDelete);
		addSeparator();
		createButton(IDataNavigator.Buttons.dataSave);
		createButton(IDataNavigator.Buttons.dataCancel);
		createButton(IDataNavigator.Buttons.dataRefresh);
		
		addSeparator();
		createButton(IDataNavigator.Buttons.dataFilter);
		addSeparator();
		createButton(IDataNavigator.Buttons.dataPrint);
		
	}			

	
	public static void initiaiseNavigationButton (IMenuItem item, IDataNavigator.Buttons button) {				
		item.setCaption(getButtonInitialiser().get(button).getCaption());
		item.setImg(getButtonInitialiser().get(button).getImg());		
	}

		
	public ICommand<IMenuItem> getButtonCommand(Buttons button) {		
		
		switch (button) {	
					
			case dataInsert:return new ICommand<IMenuItem>() {
												public void execute(IMenuItem sender) {
													 insert();
												}
										};
					
			case dataEdit:return new ICommand<IMenuItem>() {
												public void execute(IMenuItem sender) {
													 edit();
												}
										};
					
			case dataDelete:return new ICommand<IMenuItem>() {
												public void execute(IMenuItem sender) {
													 delete();
												}
										};
					
			case dataSave:return new ICommand<IMenuItem>() {
												public void execute(IMenuItem sender) {
													 save();
												}
										};				
			case dataCancel:return new ICommand<IMenuItem>() {
												public void execute(IMenuItem sender) {
													 cancel();
												}
										};				
			case dataFilter:return new ICommand<IMenuItem>() {
												public void execute(IMenuItem sender) {
													 filter();
												}
										};		
			case dataRefresh: return new ICommand<IMenuItem>() {
												public void execute(IMenuItem sender) {
													 refresh();
												}
										};
			case dataPrint: return new ICommand<IMenuItem>() {
				public void execute(IMenuItem sender) {
					 print();
				}
				
		};
		
		default:
			break; 								
		}
		return null;
	}
	private void print() {
		getView().print();
		
	}
	

	/* (non-Javadoc)
	 * @see mdb.core.ui.data.IDataNavigator#insert()
	 */
	@Override
	public void insert() {
		getView().callInsertEvent();
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.data.IDataNavigator#edit()
	 */
	@Override
	public void edit() {
		
		getView().callEditEvent();		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.data.IDataNavigator#delete()
	 */
	@Override
	public void delete() {					
		getView().callDeleteEvent();
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.data.IDataNavigator#save()
	 */
	@Override
	public void save() {
		
		getDataSource().save();		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.data.IDataNavigator#cancel()
	 */
	@Override
	public void cancel() {
		// TODO Ask user about lose changes
				RequestEntity entity =	getDataSource().getRequestEntity();
				if (entity != null) {
					entity.loseChanges();					
					//getDataSource().getViewDataBinder().getBindSource().bindDataComponents();
				}		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.data.IDataNavigator#refresh()
	 */
	@Override
	public void refresh() {
		getDataSource().refresh();
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.data.IDataNavigator#filter()
	 */
	@Override
	public void filter() {
		getView().showFilterView();		
	}	
	
}
