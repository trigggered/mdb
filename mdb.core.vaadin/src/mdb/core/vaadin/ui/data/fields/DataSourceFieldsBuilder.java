/** 
 * 
 */
package mdb.core.vaadin.ui.data.fields;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import mdb.core.shared.data.EMdbBooleanType;
import mdb.core.shared.data.EMdbFieldType;
import mdb.core.shared.data.Field;
import mdb.core.shared.data.Fields;
import mdb.core.shared.transformation.json.JSONTransformation;
import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.utils.DataConverter;
import mdb.core.ui.client.communication.IDataProvider;
import mdb.core.ui.client.data.fields.IDataSourceFieldsBuilder;
import mdb.core.ui.client.data.fields.LookUpFld;
import mdb.core.vaadin.ui.data.VaadinDataWraper;
import mdb.core.vaadin.ui.data.fields.ColumnGenerators.ComboBoxGeneratedColumn;
import mdb.core.vaadin.ui.data.fields.ColumnGenerators.FieldOptions;
import mdb.core.vaadin.ui.data.fields.ColumnGenerators.TextFieldColumnGenerator;
import mdb.core.vaadin.ui.data.fields.ColumnGenerators.DateFieldGeneratedColumn;
import mdb.core.vaadin.ui.data.fields.ColumnGenerators.TextFieldWithSelectedButtonsColumnGenerator;



/**
 * @author azhuk
 * Creation date: Jan 28, 2013
 *
 */
public class DataSourceFieldsBuilder  implements IDataSourceFieldsBuilder {

	private static final Logger _logger = Logger.getLogger(DataSourceFieldsBuilder.class.getName());		
	
	private static IDataSourceFieldsBuilder _instance;
	
	public static IDataSourceFieldsBuilder instance() {
		if (_instance == null) {
			_instance = new DataSourceFieldsBuilder();
		}
		return _instance;
	}
	
	
	
	@Override
	public   void createFields (IDataProvider dataProvider, IRequestData requstData) {
		
		DataSourceFields  fieldsList = null;		
	    	
	    	if (requstData!= null && requstData.getName() != null && requstData.getName().contains(IDataSourceFieldsBuilder.FLD_ENTITY_DESC_KEY) )  
	    	{
	    		String entityId =  requstData.getName().substring(IDataSourceFieldsBuilder.FLD_ENTITY_DESC_KEY.length());
	    		
	    		fieldsList = new DataSourceFields( Integer.parseInt(entityId ) );
	    		
	    		List<String> keys = null;
	    		if ( requstData.getKeys() !=null ){
	    			keys = Arrays.asList(requstData.getKeys());
	    		}    		
	        		
	    		String data = requstData.getData();
	    		
	    		if (data != null && !data.equals("[null]")) {
	    			VaadinDataWraper wraper = new VaadinDataWraper(data);
	    			
	    			while  ( wraper.hasNext() ) {   					    			
					    			    	 
			    	
			    		
			    		MdbField mdbFld =createField(dataProvider, wraper.next());
		    		 
			    		if (keys!= null && keys.contains(mdbFld.getName().toUpperCase())  ) {
			    			mdbFld.setPrimaryKey(true);
			    		 }		    		 			    		 			    		 			    		 
			    		 			    		 
						fieldsList.addField(mdbFld);		       	 	
			    	}
			    	
	    		} else {
	    			_logger.severe("Returend data from serve is null or equals [null]");
	    		}
			    dataProvider.getDataSourceFieldsMap().put(Integer.parseInt(entityId), fieldsList);			    
			    dataProvider.getDataSourceKeysMap().put(Integer.parseInt(entityId), requstData.getKeys());
	    	}
    	//return fieldsList;
	    	
    }
    
   
    
	private static   MdbField createField (IDataProvider dataProvider, final Map<String,String> record) {	
    	
      	final String NULL = "";
      	final String fldNum = "num".toUpperCase();
    	final String fldName = "name".toUpperCase();
    	final String fldCaption = "caption".toUpperCase();
    	final String fldReadOnly = "readonly".toUpperCase();
    	final String fldVisible = "visible".toUpperCase();
    	final String fldType = "id_fld_type".toUpperCase();
    	final String fldLookUpSrc = "look_up_src".toUpperCase();
    	final String fldLookUpKey = "look_up_key".toUpperCase();
    	final String fldLookUpFlds = "look_up_flds".toUpperCase();
    	final String fldLength = "len".toUpperCase();
    	final String fldMandatoty ="MANDATORY";
    	final String fldMasterFld ="MASTER_FLD";

    	EMdbFieldType mdbFieldType = EMdbFieldType.VARCHAR;
    	
    	
    	if ( DataConverter.isInteger(record.get(fldType) ) ) {
    		mdbFieldType = EMdbFieldType.fromInt(Integer.parseInt(record.get(fldType) ));    		
    	}
    	
    	String colName = record.get(fldName);  
    	String colTitle = record.get(fldCaption);
    	String colNum =record.get(fldNum);
    	
    	MdbField toReturn = new MdbField(colName);
    	
    	toReturn.setPosition(colNum!=null&&colNum.length()>0?Integer.parseInt(colNum):0);
    	toReturn.setMdbFieldType(mdbFieldType);
    	toReturn.setTitle(colTitle);
    	
    	boolean isRequired= false;
		String mandatory =  record.get(fldMandatoty);
		if ( mandatory !=null && mandatory.length() > 0) {			
			isRequired= Integer.valueOf(mandatory).intValue()==1?true:false;	
		}
		
    	switch (mdbFieldType) {
    		case NUMBER:   			    			
    		case FLOAT: { 	
    			toReturn.setDataSourceField(
    					ColumnGenerators.instance().new NumberFieldColumnGenerator(colName,  colTitle ));
    			
    			break;
    		}
    		case VARCHAR: {
    			toReturn.setDataSourceField(
    					ColumnGenerators.instance().new TextFieldColumnGenerator(colName,  colTitle ));    			
    			break;
    		}
    		case DATE: {    			
    			DateFieldGeneratedColumn  dateItem = ColumnGenerators.instance().new DateFieldGeneratedColumn (colName,  colTitle );  			    			
    			toReturn.setDataSourceField( dateItem );    			
    			break;
    		}    		
    		
    		case BOOLEAN: {
    			ComboBoxGeneratedColumn fld =  ColumnGenerators.instance().new ComboBoxGeneratedColumn (colName, colTitle);
    			fld.setValueMap( EMdbBooleanType.getMap() );   			    			   			    			
    			toReturn.setDataSourceField(fld);    			
    		}
    			break;	
    		case CURRENCY:    			
    			break;    		
    		case LOOK_UP:   {
    			
    			ComboBoxGeneratedColumn fld = ColumnGenerators.instance().new ComboBoxGeneratedColumn(colName,  colTitle );
    			
    			String lookUpSrc =  record.get(fldLookUpSrc);    		
    			String lookUpKey =  record.get(fldLookUpKey);
				String lookUpFlds = record.get(fldLookUpFlds);			    
				
    			Map<String, String> data = null;
    			
    				Integer idLookUpSrc = null;    				
    				
	    				if ( DataConverter.isInteger(lookUpSrc) ) {    					
	    					 idLookUpSrc = Integer.parseInt(lookUpSrc);	    				
	    				} 
	    				else 
	    				{    					
	    					idLookUpSrc = (colName + lookUpSrc+lookUpFlds+lookUpKey).hashCode();
	    					_logger.info("EntityId (Hash) for " +colName+","+ lookUpSrc+","+lookUpFlds+","+ lookUpKey +" is "+ idLookUpSrc);
	    				}	    						 
    					 
	    				if ( dataProvider.getDataMap() != null && dataProvider.getDataMap().containsKey(idLookUpSrc) ) {			
	    					
	    					data =buildComboBoxValues( dataProvider.getDataMap().get(idLookUpSrc),	
	    									lookUpKey,lookUpFlds) ;
	    				}
	    				
	    				else if ( dataProvider.getResponse().existEntity(lookUpSrc)) {
	    					data =buildComboBoxValues( dataProvider.getResponse().get(lookUpSrc).getData(),
	    							lookUpKey,lookUpFlds) ;
	    				}
	    				else {
	    					_logger.info("Find LookUp src: "+idLookUpSrc+" is not found" );
	    				}	    				
    				    			
    			
	    				
	    			if (data != null) {
	    				fld.setValueMap (data);
	    			}   
	    			toReturn.setDataSourceField(fld);
    			}
    			break; 
    		case FORM: {
    			
	    			String lookUpSrc =  record.get(fldLookUpSrc);
	    			String lookUpKey =  record.get(fldLookUpKey);
					String lookUpFlds = record.get(fldLookUpFlds);
					String masterFld = record.get(fldMasterFld);
					
					
					TextFieldWithSelectedButtonsColumnGenerator dsTextField =ColumnGenerators.instance().new TextFieldWithSelectedButtonsColumnGenerator(colName, colTitle );
					
					
					toReturn.setLookUpFld(new LookUpFld(toReturn, lookUpSrc, lookUpKey, lookUpFlds, masterFld));					
					
					
					//ButtonItemSelectEditor editor = new ButtonItemSelectEditor(Integer.parseInt(lookUpSrc) );
					
	    			
/*	    			toReturn.setItemEditor(editor);	    			
	    			
	    			dsTextField.setEditorProperties(editor);    				    			
	    			toReturn.setDataSourceField(dsTextField);*/
	    			
    		}
    			break;
    		case CONTEXT_VALUE: {
    			String lookUpKey =  record.get(fldLookUpKey);
    			TextFieldColumnGenerator dsTextField = ColumnGenerators.instance().new TextFieldColumnGenerator(colName, colTitle );
    			toReturn.setDataSourceField(dsTextField);
    			//toReturn.setLookUpFld(new LookUpFld(toReturn, null, lookUpKey, null, null));		
    			
    			//toReturn.setItemEditor(new TextItemEditor( ));			    			
			}
			break;	
    		case EDIT_BUTTON:    			
    		case CHECK_COMBO_BOX:    			
    		case INN:    			
    		case FLOAT_E8:    							 
    		case FLOAT_FORMAT:
    			toReturn.setDataSourceField(ColumnGenerators.instance().new TextFieldColumnGenerator(colName,  colTitle ));
    			break;
    		case MEMO:
    			toReturn.setDataSourceField(ColumnGenerators.instance().new TextFieldColumnGenerator(colName,  colTitle ));
    			break;
    		default :
    			toReturn.setDataSourceField(ColumnGenerators.instance().new TextFieldColumnGenerator(colName,  colTitle ));
    	}
    	    	
    	FieldOptions options = new FieldOptions();
    	
    	String readOly = record.get(fldReadOnly);
    	if (toReturn!= null) {
    		
    		
    	//toReturn.getDataSourceField().setCanEdit(readOly!=null &&  !readOly.equalsIgnoreCase(NULL)  &&  Integer.parseInt(readOly) == 1?false:true);

    	boolean readOnly = readOly!=null &&  !readOly.equalsIgnoreCase(NULL)  &&  Integer.parseInt(readOly) == 1?true:false;
    	toReturn.setReadOnly (readOnly) ;
    	options.setReadOnly(readOnly);
		
		String vis = record.get(fldVisible);
		//toReturn.getDataSourceField().setHidden(vis!=null && !vis.equalsIgnoreCase(NULL) &&  Integer.parseInt(vis) == 1?false:true );
		boolean visible = vis!=null && !vis.equalsIgnoreCase(NULL) &&  Integer.parseInt(vis) == 1?true:false ;
		options.setVisible(visible);;
		toReturn.setVisivble(visible);
		
		
		String sLength =  record.get(fldLength);
		
			if ( sLength !=null && sLength.length() > 0) {		
					
				options.setLength(Integer.valueOf(sLength).intValue());
			}						
			
			options.setRequired(isRequired);
			//DataValidators.setDefault(toReturn.getDataSourceField(), mdbFieldType);
    	}
    	
    	return toReturn;
    }    
      
    
	@Override
    public   void  createDefaultFields ( IDataProvider  dataProvider, IRequestData entity ) {    	
    	DataSourceFields fieldsList =null;  	 
       	 if (entity != null) {
       		
       		 fieldsList = new DataSourceFields(entity.getEntityId());	 
       		
	       	 Fields fields= entity.getFields();
	       	 if (fields!=null) {
		       	 for (Field fld : fields) {
    		 
		       		
		       		TextFieldColumnGenerator gen = ColumnGenerators.instance().new TextFieldColumnGenerator(fld.getColumnName(),fld.getColumnName());
		       		
		       		 MdbField mdbfld = new MdbField(gen);
		       		 mdbfld.setDataSourceField(gen);
		       		 mdbfld.setVisivble(true);
		       		 mdbfld.setReadOnly(false);
		       		 fld.setMdbFieldType(EMdbFieldType.VARCHAR);
		       		
		       		 fieldsList.addField(mdbfld);
		       	  }       
	       	 }
	       	dataProvider.getDataSourceFieldsMap().put(entity.getEntityId(), fieldsList);
       	 }
       	 
    }        
    
    
    public static LinkedHashMap<String, String> buildComboBoxValues(String  jsonString, String keyField, String titleFields ) {
    	List<Map<String, String>>  dataList = JSONTransformation.json2ListMap(jsonString);
		 
		LinkedHashMap<String, String>  toReturn = new LinkedHashMap<String, String>();		
		
		String[] arrTitleFields = null;				
		
		if ( titleFields.contains(";") ) {
			arrTitleFields = titleFields.split(";");
		}
		else {
			arrTitleFields  = new String[1];
			arrTitleFields[0] = titleFields;
		}
		
		for (Map<String, String> rec : dataList) {
			
			StringBuilder out=new StringBuilder();
			boolean isFirst  = true;
			for (String title :  arrTitleFields) {
				String titleVal = rec.get(title) ;
				 if (!isFirst) {
					 out.append(" ");
				 }
				 out.append(titleVal);
				 isFirst = false;
			}
			 toReturn.put(	rec.get(keyField), out.toString() );
		}
		
		return toReturn;
	}
}
