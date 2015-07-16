/**
 * 
 */
package mdb.core.vaadin.ui.view.data.card;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import mdb.core.shared.transport.RequestEntity;
import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.ui.client.app.AppController;
import mdb.core.ui.client.communication.IRemoteDataSave;
import mdb.core.ui.client.communication.impl.GatewayQueue;
import mdb.core.ui.client.data.bind.DataBindException;
import mdb.core.ui.client.resources.locales.Captions;
import mdb.core.ui.client.util.BooleanCallback;
import mdb.core.ui.client.view.data.IDataView;
import mdb.core.ui.client.view.data.card.ICard;
import mdb.core.ui.client.view.data.card.ICard.EAfterSaveAction;
import mdb.core.ui.client.view.data.card.ICard.EViewState;
import mdb.core.ui.client.view.data.card.section.IDataSection;
import mdb.core.ui.client.view.dialogs.BaseDialogs.EButtons;
import mdb.core.ui.client.view.dialogs.message.Dialogs;
import mdb.core.vaadin.ui.view.data.DataView;
import mdb.core.vaadin.ui.view.data.card.section.DataFieldsSection;
import mdb.core.vaadin.ui.view.data.card.section.DataGridSection;

/**
 * @author azhuk
 * Creation date: Sep 2, 2015
 *
 */
public  abstract class CardView extends DataView implements ICard, IRemoteDataSave{
	private static final Logger _logger = Logger.getLogger(CardView.class
			.getName());
	
	private EAfterSaveAction  _afterSaveAction = EAfterSaveAction.Refresh;
	
	private VerticalLayout headerLayout;
	private VerticalLayout footerLayout;
	protected Layout _contentLayout;

	private HashMap<EButtons, Button> _buttons;

	private String _cardId;
	private EViewState _viewState;
	
	protected TabSheet _mainTabs;
	
	/**
	 * @param _viewState the _viewState to set
	 */
	public void setViewState(EViewState _viewState) {
		this._viewState = _viewState;
	}



	protected  HashMap<Integer, IDataSection> _hmDataSections;		 

	
	protected IDataSection createDataSection(IDataSection.ESectionType sectionType) {
		IDataSection toReturn = null;
		switch (sectionType) {
		case Grid:
			toReturn = new DataGridSection(this);
			break;
		case Fields:
			toReturn = new DataFieldsSection(this);
			break;
		case VerticalGrid:
			break;
		}
		return toReturn;
	}		


	protected IDataSection createDataSection(int sectionid, IDataSection.ESectionType sectionType) {		
		IDataSection dataSection = createDataSection(sectionType);
		dataSection.setSectionId(sectionid);		
		_hmDataSections.put(sectionid, dataSection);
		return dataSection;	
	}
	
	protected abstract void createGridSections();	
	
	protected abstract void createFieldSections();
	
	protected abstract void addSection(IDataSection dataSection);
	
	public abstract void visibleButtons(Boolean[] visibles);
	
	protected void createComponents () {
		  createMainLayout();
		  
		  //createTopLayout();			
		  createContextLayout();		  
		  
		  createBottomLayout();	
		  
		  initSize();		  
	}
	
	/**
	 * 
	 */
	
	protected void createContextLayout() {
		
		 _mainTabs = new TabSheet();
		 _mainTabs.setSizeFull();		 		 
		 
		 _contentLayout.addComponent(_mainTabs);
		 
	}
	

	@Override
	protected void createMainLayout() {		
		
		headerLayout = new VerticalLayout();
		
		footerLayout = new VerticalLayout();
		footerLayout.setSpacing(true);
		footerLayout.setMargin(true);

		VerticalLayout contentLayout = new VerticalLayout();

        Panel contentPanel = new Panel(contentLayout);
        contentPanel.setSizeFull();
        _contentLayout = new VerticalLayout();
        
        contentPanel.setContent(_contentLayout);

        // XXX: add the panel instead of the layout
        VerticalLayout mainLayout = new VerticalLayout(headerLayout, contentPanel, footerLayout);
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(contentPanel, 1);
        
        setMainLayout(mainLayout);
	}
	
	
	protected void createBottomLayout() {
		HorizontalLayout btnlayout = new HorizontalLayout();
		//btnlayout.setSizeFull();
		btnlayout.addStyleName("wrapping");
		btnlayout.setSpacing(true);
		//btnlayout.setMargin(true);
		btnlayout.setSizeUndefined();
		btnlayout.setWidth("100%");
		
		btnlayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		
		Label spacer = new Label("");
		btnlayout.addComponent(spacer);
	    spacer.setWidth("100%");
	    btnlayout.setExpandRatio(spacer, 1f);
	        
		  
		 _buttons = new HashMap<EButtons, Button>();	     	      
	      
		  Button btnOk = addButton(EButtons.OK);
		  ;
		  btnOk.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				okBtnClickEvent();				
			}
		});
		  
	      
	      Button btnCancel = addButton(EButtons.Cancel);
	      btnCancel.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				closeBtnClickEvent();				
			}
		});
	      
	      for (Button btn : _buttons.values()) {
	    	  btn.setWidth("150");
	    	  btnlayout.addComponents(btn); 	    	  
	    	  //btnlayout.setComponentAlignment(btn, Alignment.BOTTOM_RIGHT);
	      }
	      btnOk.focus();
	      footerLayout.addComponent(btnlayout);
	      visibleButtons(new Boolean[]{ true,true });

	}
	
	protected abstract void  doBindDataIfNewState();	

	@Override
	public void bindDataComponents() {		
	_logger.info("call bindDataComponents");			
	
		if (_viewState == EViewState.New) {			
			doBindDataIfNewState();													
		}			
			
	}
	
	
	@Override
	public void bindDataComponentsAfterChange() {
		getDataBinder().getDataProvider().getRequest().clear();
		
	}
	
	

	protected   HashMap<Integer, IDataSection>  getDataSections() {
		return _hmDataSections;
	}

	/**
	 * @param isCan
	 */
	private void enableEditState(boolean isCanEdit) {
		_logger.info("Set component disable = "+isCanEdit);
		 for (IDataView view : getDataSections().values() )  {
			 view.setCanEdit(isCanEdit);
		 }		 
	}



	protected  abstract void doPrepareRequestDataIdNewState(); 

	@Override
	public void prepareRequestData() {				
		_logger.info("################ Start prepareRequestData ######################");
		
		if (_viewState == EViewState.New) {
			doPrepareRequestDataIdNewState();
		
		} else  {
		_logger.info("Request data for exists document");
			getDataBinder().getDataProvider().getRequest().setPosition(100);
		}		
		prepareRequestData(_hmDataSections.values().toArray(new IDataView[_hmDataSections.values().size()]));	
					 
	}
	
	
	@Override
	public void putRequestToQueue()  {
		putRequestToQueue(this);
		putRequestToQueue(_hmDataSections.values().toArray(new IDataView[_hmDataSections.values().size()]));
		
	}	
	
	
	@Override
	public void close() {		
		IsCanClose(null);
	}	
		
	@Override
	public void IsCanClose(final BooleanCallback callback) {		
			
		if ( isHaseChanges()) {					
		     
			Dialogs.AskDialog2(Captions.Q_HAS_CHANGE_SAVE_BEFORE_CLOSE , new BooleanCallback() {
				
				@Override
				public void execute(Boolean value) {					
						if (value == null) {
							return;
						}
						else {	
							
								if (value) {
									_afterSaveAction = EAfterSaveAction.Close;
									simpleSave(false);									
								} else {
									loseChange();
									closeWnd();
								}
						}		
				}
				
			});				
		}
		else {
			AppController.getInstance().getMainView().closeCurrentTab();			
		}
			
	}
	
	private void closeWnd() {
		AppController.getInstance().getMainView().closeCurrentTab();
	}

	
	@Override
	public void loseChange() {
		for ( IDataView view :  _hmDataSections.values()) {
			view.loseChange();	
		}
	}
	
	
		
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataSave#save()
	 */
	@Override
	public void save() {
		
		if (!isValidate() ) {
			
			Dialogs.ValidatonWarning();
			return; //false;
		}		
		if ( isHaseChanges() ) {			

			Dialogs.AskDialog(Captions.Q_SAVE_CHANGES , new BooleanCallback() {				
				@Override
				public void execute(Boolean value) {
					if (value) {
						simpleSave(false);
					}																				
				}
			});
			
		}		
		
	}		
	
	
	private void simpleSave(boolean checkHaseChanges) {
	
		if (!isValidate() ) {
			Dialogs.ValidatonWarning();			
			return;
		}
		
		if (checkHaseChanges) {		
			 if ( !isHaseChanges() ) {
				 return;
			 }
		}				
			
		saveCard();					
	}	
	
	public void saveCard() {
		_viewState = EViewState.Edit;
		//_waitPopup.show(Captions.SAVE_PROCESS );
		prepareSavedData();
		putSavedDataToQueue();
		GatewayQueue.instance().getProcessor().run();
	}
	

	private void callAfterSaveAction() {
		_logger.info("AfterSaveAction = "+_afterSaveAction);
			
		switch (_afterSaveAction) {
		case Refresh:
			
			Dialogs.Message(Captions.SAVE_CORRECT_RESULT, new BooleanCallback() {
					
					@Override
					public void execute(Boolean value) {
						_logger.info("Call refresh");						
						refresh();				
					}
				});
			break;
		case Close:
			closeWnd();
			break;
		default:
			break;
		}		
	}
	
	/**
	 * @return
	 */
	@Override
	public boolean isHaseChanges() {				
		
			for (IDataView view : _hmDataSections.values()) {		
				if ( view.isHaseChanges() )
				 return true;
			}		
		
		 return false;				
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataSave#isValidate()
	 */
	@Override
	public boolean isValidate() {
		boolean toReturn = true;
		for (IDataView view: _hmDataSections.values()) {			 
			
			 if(   !view.isValidate() ) {
				 toReturn = false;
			 }
		}
		
		return toReturn;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataSave#prepareSavedData()
	 */
	@Override
	public void prepareSavedData() {
		
		for (IDataSection section : _hmDataSections.values()) {
			section.prepareRequestData();
		}		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.IRemoteDataSave#putSavedDataToQueue()
	 */
	@Override
	public void putSavedDataToQueue() {
		
		for (IDataSection section : _hmDataSections.values()) {
			section.putRequestToQueue();;
		}
		
		
				
		RequestEntity rq = new RequestEntity();
		rq.setExecuteType(ExecuteType.ChangeData);
		getDataBinder().getDataProvider().getRequest().add(rq);
		GatewayQueue.instance().put(getDataBinder().getDataProvider());
	}		
	
	
	public void refresh() {
		callRequestData();
	}
	
	/**
	 * 
	 */
	protected void closeBtnClickEvent() {
		getMainView().closeCurrentTab();
		
	}


	/**
	 * 
	 */
	protected void okBtnClickEvent() {
		// TODO Auto-generated method stub
		
	}


	protected Button addButton(EButtons button)  {
		Button toReturn = new Button(button.toString());
	
      _buttons.put(button, toReturn);
    
      return toReturn;
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.IDataView#isSelectedRecord()
	 */
	@Override
	public boolean isSelectedRecord() {
		return true;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.IDataView#callDeleteEvent()
	 */
	@Override
	public void callDeleteEvent() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.IDataView#getSelectedRecordJSON()
	 */
	@Override
	public String getSelectedRecordJSON() {		
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.ADataView#clearEvents(mdb.core.ui.client.view.data.IDataView)
	 */
	@Override
	protected void clearEvents(IDataView<Object> view) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.ABaseView#initSize()
	 */
	@Override
	protected void initSize() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.card.ICard#getCardId()
	 */
	@Override
	public String getCardId() {
		return _cardId;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.card.ICard#getCardEntityId()
	 */
	@Override
	public int getCardEntityId() {
		
		return getMainEntityId();
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.card.ICard#getViewState()
	 */
	@Override
	public EViewState getViewState() {
		return _viewState;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.card.ICard#setCardId(long)
	 */
	@Override
	public void setCardId(String value) {
		_cardId = value;
		
	}
}
