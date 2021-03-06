package mdb.core.ui.smartgwt.client.view.data.card;

import java.util.HashMap;
import java.util.logging.Logger;

import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.app.AppController;
import mdb.core.ui.client.communication.IRemoteDataSave;
import mdb.core.ui.client.communication.impl.GatewayQueue;
import mdb.core.ui.client.resources.locales.Captions;
import mdb.core.ui.client.util.BooleanCallback;
import mdb.core.ui.client.view.data.ADataView;
import mdb.core.ui.client.view.data.IDataView;
import mdb.core.ui.client.view.data.card.ICard;
import mdb.core.ui.client.view.data.card.section.IDataSection;
import mdb.core.ui.client.view.dialogs.BaseDialogs.EButtons;
import mdb.core.ui.client.view.dialogs.message.Dialogs;
import mdb.core.ui.smartgwt.client.view.ISGWTComponent;
import mdb.core.ui.smartgwt.client.view.data.card.section.DataFieldsSection;
import mdb.core.ui.smartgwt.client.view.data.card.section.DataGridSection;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;



public abstract class ACard extends ADataView<Record> implements ICard, IRemoteDataSave, ISGWTComponent {	

	
	private EAfterSaveAction  _afterSaveAction = EAfterSaveAction.Refresh;
	
	//private WaitPopup _waitPopup = new WaitPopup();  
	private static final Logger _logger = Logger.getLogger(ACard.class.getName());	
		
	Layout _mainLayout;
	private String _cardId;	
	
		
	private EViewState _viewState;
		

	/**
	 * @param _viewState the _viewState to set
	 */
	public void setViewState(EViewState _viewState) {
		this._viewState = _viewState;
	}



	protected  HashMap<Integer, IDataSection> _hmDataSections;		 


	protected  HashMap<String , Button> _buttons;
	
	
	public ACard() {
		super(EViewPanelType.VLayout);	
						
	}
		
	

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
	
	
	
	@Override
	protected void createComponents() {		
		super.createComponents();			
		
		_hmDataSections = new HashMap<Integer, IDataSection>();
		_buttons = new HashMap<String, Button>();		
		
		createCardLayouts();
		createFieldSections();
		createGridSections();		
	}
	
	protected abstract void createCardLayouts(); 
	
	protected abstract void createGridSections();	
	
	protected abstract void createFieldSections();
	
	protected abstract void addSection(IDataSection dataSection);
	
	
	protected ACard getSelf() {
		return this;
	}
	
	protected Layout createBottomLayout() {
		
		 Layout btnlayout = new HLayout();
	      
		 btnlayout.setAlign(Alignment.RIGHT);
		 btnlayout.setMargin(10);
		 btnlayout.setMembersMargin(10);	      
		 
		 Button btnSave = new Button();		  
		  btnSave.setTitle(Captions.SAVE);		  
	      btnSave.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {		
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				save();				
			}
		});	      
	      
	      _buttons.put(EButtons.Save.toString(), btnSave);
	      
	      Button  btnClose = new Button();
	      btnClose.setTitle(Captions.CLOSE);
	      btnClose.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {		
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {			
				close();
			}
		}); 
	      
	      _buttons.put(EButtons.Close.toString(), btnSave);
	      
	      
	      /*
	      Layout hLALayout = new HLayout();
	      LinkItem lblLink = new LinkItem(getLinkToDocument());
	      hLALayout.addMember(lblLink);	      
	      btnlayout.addMembers(hLALayout, _btnSave, _btnClose, _btnSendToApprove,_btnSendToSigne,_btnSign, _btnCancelSign);
	      */
	      
	      btnlayout.addMembers(btnSave, btnClose);
	      visibleButtons(new Boolean[]{ true,true });
	      return btnlayout;
	}
	
	
	
	public abstract void visibleButtons(Boolean[] visibles);
		
	
	
	
	public   HashMap<Integer, IDataSection>  getDataSections() {
		return _hmDataSections;	
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
	public String getCardId() {
		return _cardId;
	}
	
	@Override
	public void setCardId(String value) {
		this._cardId = value;				 
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
	
	public EViewState getViewState() {
		return _viewState;
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
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.ABaseView#createMainLayout()
	 */
	@Override
	protected void createMainLayout() {
		_mainLayout = new HLayout();		
	}
	
	public Layout getMainLayout() {
		return _mainLayout;
	}
	
	public void 	setMainLayout(Layout value) {
		_mainLayout = value;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.ABaseView#initSize()
	 */
	@Override
	protected void initSize() {
		getMainLayout().setWidth100();
		getMainLayout().setHeight100();
		
	}
	
}
