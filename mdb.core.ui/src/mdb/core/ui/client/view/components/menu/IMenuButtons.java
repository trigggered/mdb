package mdb.core.ui.client.view.components.menu;

import mdb.core.ui.client.command.ICommand;

public interface IMenuButtons {
	public enum Buttons   {
		/*data modify*/
		 dataInsert,
		 dataEdit,
		 dataDelete,
		 dataSave,
		 dataCancel,
		 dataRefresh,
		 dataFilter,
		 dataPrint,
		 
		 /*data paging*/
		 dataFirstPage,
		 dataPreviousPage,
		 dataInputPageNumber,
		 dataNextPage,
		 dataLastPage,
		 /*data action*/
		 /*Tree*/
		 treeCollapse,
		 treeExpand
		 /*Tree*/, 
		 download
   }
	
	public ICommand<IMenuItem> getButtonCommand (Buttons button);
	public IMenuItem  getButton (Buttons button);
}
