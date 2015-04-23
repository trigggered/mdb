/** 
 * 
 */
package mdb.core.ui.client.data.impl.fields;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;

import mdb.core.shared.data.EMdbBooleanType;
import mdb.core.shared.data.EMdbFieldType;
import mdb.core.shared.data.Field;
import mdb.core.shared.data.Fields;
import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.utils.DataConverter;
import mdb.core.ui.client.communication.IDataProvider;
import mdb.core.ui.client.data.impl.ViewDataConverter;
import mdb.core.ui.client.data.impl.fields.editors.ButtonItemSelectEditor;
import mdb.core.ui.client.data.impl.fields.editors.TextItemEditor;
import mdb.core.ui.client.data.impl.validators.DataValidators;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.FormErrorOrientation;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * @author azhuk
 * Creation date: Jan 28, 2013
 *
 */
public class DataSourceFieldsBuilder {

	private static final Logger _logger = Logger.getLogger(DataSourceFieldsBuilder.class.getName());
	
	public final static String FLD_ENTITY_DESC_KEY = "FLD_ENTITY_DESC_KEY_";	
	
	
	public  static DataSourceFields createFields (IDataProvider dataProvider, IRequestData entity) {
		
		DataSourceFields  fieldsList = null;		
	    	
	    	if (entity!= null && entity.getName() != null && entity.getName().contains(DataSourceFieldsBuilder.FLD_ENTITY_DESC_KEY) )  
	    	{
	    		String entityId =  entity.getName().substring(FLD_ENTITY_DESC_KEY.length());
	    		
	    		fieldsList = new DataSourceFields( Integer.parseInt(entityId ) );
	    		
	    		List<String> keys = null;
	    		if ( entity.getKeys() !=null ){
	    			keys = Arrays.asList(entity.getKeys());
	    		}    		
	        		
	    		String data = entity.getData();
	    		
	    		if (data != null && !data.equals("[null]")) {
					JavaScriptObject obj =  JSON.decode(entity.getData());		    	
			    	Record[] record = ListGridRecord.convertToRecordArray(obj);    			    	 
			    	
			    	for (int i = 0; i <record.length; i++) {    	
			    		
			    		MdbField mdbFld =createField(dataProvider, record[i]);
			    		DataSourceField dsField = mdbFld.getDataSourceField(); 
			    		 
			    		if (keys!= null && keys.contains(dsField.getName().toUpperCase())  ) {
			    			 dsField.setPrimaryKey(true);
			    		 }		    		 			    		 			    		 			    		 
			    		 			    		 
						fieldsList.addField(mdbFld);		       	 	
			    	}
	    		} else {
	    			_logger.severe("Returend data from serve is null or equals [null]");
	    		}
			    dataProvider.getDataSourceFieldsMap().put(Integer.parseInt(entityId), fieldsList);			    
			    dataProvider.getDataSourceKeysMap().put(Integer.parseInt(entityId), entity.getKeys());
	    	}
    	return fieldsList;
    }
    
   
    
	private static   MdbField createField (IDataProvider dataProvider, final Record record) {	
    	
      	final String NULL = "";
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
    	
    	if ( DataConverter.isInteger(record.getAttributeAsString(fldType) ) ) {
    		mdbFieldType = EMdbFieldType.fromInt(Integer.parseInt(record.getAttributeAsString(fldType) ));    		
    	}
    	
    	String colName = record.getAttributeAsString(fldName);  
    	String colTitle = record.getAttributeAsString(fldCaption);
    	MdbField toReturn = new MdbField(colName);
    	toReturn.setMdbFieldType(mdbFieldType);
    	
    	boolean isRequired= false;
		String mandatory =  record.getAttributeAsString(fldMandatoty);
		if ( mandatory !=null && mandatory.length() > 0) {			
			isRequired= Integer.valueOf(mandatory).intValue()==1?true:false;	
		}
		
    	switch (mdbFieldType) {
    		case NUMBER:   			    			
    		case FLOAT:  			    			   			
    			toReturn.setDataSourceField(new DataSourceFloatField(colName,  colTitle ));
    			break; 
    		case VARCHAR:
    			toReturn.setDataSourceField(new DataSourceTextField(colName,  colTitle ));
    			break;	
    		case DATE: {    			
    			DataSourceDateField dateItem = new DataSourceDateField(colName,  colTitle );
    			
    			//dateItem.setDateFormatter(DateDisplayFormat.TOSERIALIZEABLEDATE);
    			dateItem.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
    			DateItem editor = new DateItem();
    			
    			editor.setUseTextField(true);   			    			
    			
    			//editor.setSelectorFormat(DateItemSelectorFormat.DAY_MONTH_YEAR);
    			
    			editor.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
    			editor.setEnforceDate(true);
    			
    			
    			editor.setErrorOrientation(FormErrorOrientation.BOTTOM);
    			editor.setShowErrorText(true);
    			editor.setInvalidDateStringMessage("Invalid date. Format: DD/MM/YYYY ");
    
    			
    			dateItem.setEditorProperties(editor);
    			toReturn.setDataSourceField( dateItem );
    			
    			break;
    		}
    		
    		
    		case BOOLEAN:
    			DataSourceTextField fld = new DataSourceTextField(colName,  colTitle );   			
    			fld.setValueMap ( EMdbBooleanType.getMap() );
    			toReturn.setDataSourceField(fld);
    			break;	
    		case CURRENCY:    			
    			break;    		
    		case LOOK_UP:   {    			
    			fld = new DataSourceTextField(colName,  colTitle );
    			
    			String lookUpSrc =  record.getAttributeAsString(fldLookUpSrc);    		
    			String lookUpKey =  record.getAttributeAsString(fldLookUpKey);
				String lookUpFlds = record.getAttributeAsString(fldLookUpFlds);			    
				
    			LinkedHashMap<String, String> data = null;
    			
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
	    					
	    					data =ViewDataConverter.jSon2LinkedHashMap( dataProvider.getDataMap().get(idLookUpSrc),	
	    									lookUpKey,lookUpFlds) ;
	    				}
	    				
	    				else if ( dataProvider.getResponse().existEntity(lookUpSrc)) {
	    					data =ViewDataConverter.jSon2LinkedHashMap( dataProvider.getResponse().get(lookUpSrc).getData(),
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
    			
	    			String lookUpSrc =  record.getAttributeAsString(fldLookUpSrc);
	    			String lookUpKey =  record.getAttributeAsString(fldLookUpKey);
					String lookUpFlds = record.getAttributeAsString(fldLookUpFlds);
					String masterFld = record.getAttributeAsString(fldMasterFld);
					
					DataSourceTextField dsTextField = new DataSourceTextField(colName, colTitle );
					
					
					toReturn.setLookUpFld(toReturn.new LookUpFld(lookUpSrc, lookUpKey, lookUpFlds, masterFld));					
					
					
					ButtonItemSelectEditor editor = new ButtonItemSelectEditor(Integer.parseInt(lookUpSrc) );
					
	    			
	    			toReturn.setItemEditor(editor);	    			
	    			
	    			dsTextField.setEditorProperties(editor);    				    			
	    			toReturn.setDataSourceField(dsTextField);
	    			
    		}
    			break;
    		case CONTEXT_VALUE: {
    			String lookUpKey =  record.getAttributeAsString(fldLookUpKey);
    			DataSourceTextField dsTextField = new DataSourceTextField(colName, colTitle );
    			toReturn.setDataSourceField(dsTextField);
    			toReturn.setLookUpFld(toReturn.new LookUpFld(null, lookUpKey, null, null));		
    			
    			toReturn.setItemEditor(new TextItemEditor( ));			    			
			}
			break;	
    		case EDIT_BUTTON:    			
    		case CHECK_COMBO_BOX:    			
    		case INN:    			
    		case FLOAT_E8:    							 
    		case FLOAT_FORMAT:
    			toReturn.setDataSourceField(new DataSourceTextField(colName,  colTitle ));
    			break;
    		case MEMO:
    			toReturn.setDataSourceField(new DataSourceTextField(colName,  colTitle ));
    			break;
    		default :
    			toReturn.setDataSourceField(new DataSourceTextField(colName,  colTitle ));
    	}
    	    	
    	
    	String readOly = record.getAttributeAsString(fldReadOnly);
    	if (toReturn!= null) {
    		toReturn.getDataSourceField().setCanEdit(readOly!=null &&  !readOly.equalsIgnoreCase(NULL)  &&  Integer.parseInt(readOly) == 1?false:true);

    	
    	toReturn.setReadOnly (readOly!=null &&  !readOly.equalsIgnoreCase(NULL)  &&  Integer.parseInt(readOly) == 1?true:false) ;
    		
		
		String vis = record.getAttributeAsString(fldVisible);
		//toReturn.getDataSourceField().setHidden(vis!=null && !vis.equalsIgnoreCase(NULL) &&  Integer.parseInt(vis) == 1?false:true );
		toReturn.setVisivble(vis!=null && !vis.equalsIgnoreCase(NULL) &&  Integer.parseInt(vis) == 1?true:false );
		
		
		String sLength =  record.getAttributeAsString(fldLength);
			if ( sLength !=null && sLength.length() > 0) {			
				toReturn.getDataSourceField().setLength(Integer.valueOf(sLength).intValue());	
			}						
			
			toReturn.getDataSourceField().setRequired(isRequired);
			DataValidators.setDefault(toReturn.getDataSourceField(), mdbFieldType);
    	}
    	
    	return toReturn;
    }    
      
    
    public static  DataSourceFields  createDefaultFields ( IDataProvider  dataProvider, IRequestData entity ) {    	
    	DataSourceFields fieldsList =null;  	 
       	 if (entity != null) {
       		
       		 fieldsList = new DataSourceFields(entity.getEntityId());	 
       		
	       	 Fields fields= entity.getFields();
	       	 if (fields!=null) {
		       	 for (Field fld : fields) {
		       		 MdbField mdbfld = new MdbField(new DataSourceTextField(fld.getColumnName(),fld.getColumnName()));
		       		 mdbfld.setVisivble(true);
		       		 mdbfld.setReadOnly(false);
		       		 fld.setMdbFieldType(EMdbFieldType.VARCHAR);
		       		
		       		 fieldsList.addField(mdbfld);
		       	  }       
	       	 }
	       	dataProvider.getDataSourceFieldsMap().put(entity.getEntityId(), fieldsList);
       	 }
       	 return fieldsList;       	
    }    

    
    
    public static EMdbFieldType convertFieldTypeJs2Mdb (FieldType value ) {
    	    	
    	switch (value ) {
    	
	    	case INTEGER:
	    		return EMdbFieldType.NUMBER;
	    	case FLOAT:
	    		return EMdbFieldType.FLOAT;    		 	
	    	case TEXT:
	    		return EMdbFieldType.VARCHAR;
	    	case DATE:
	    	case DATETIME:
	    		return EMdbFieldType.DATE;
	    	case BOOLEAN:
	    		return EMdbFieldType.BOOLEAN;
	    	default:
	    		return EMdbFieldType.VARCHAR;    			
    	}
    }
}
