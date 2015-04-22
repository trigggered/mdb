/*
 * Created on 23.02.2005
 *
 */
package mdb.core.shared.data;

import java.util.HashMap;
import java.util.Map;



public class Params extends HashMap<String, Param>  implements  java.io.Serializable {

	 private static final long serialVersionUID = 1L;  	  	
	  
	  
	  public void  add(String aParamName, String aParamaValue) {	  	
	  	add(aParamName.toUpperCase(), aParamaValue, 0, 0);	   
	  }	  

	  public void add(String aParamName, String aParamaValue, int aParamType, int anInOut) {
	  	Param Param = new Param(aParamName.toUpperCase(), aParamaValue, aParamType, anInOut);
	  	add(Param);
	  }
	  
	  public void add (Param value) { 	
	    	super.put(value.getName(),value);
	  }
	  
	  public void add(String aParamName, String aParamaValue, int aParamType) {
		  add(aParamName, aParamaValue, aParamType, 0);		  	
	  }	  
	  
	  public void deleteParam(String aParamaName) {
	  	super.remove(aParamaName);
	  }
	  

	  public int count() {
	  	return super.size();
	  }	  	  
	  

	  public void setValue(String aParamName, String aParamValue) {	  	
		  setValue(aParamName, aParamValue, 0); 
	  }
	  
	  
	  public void setValue(String name, String value, int aParamType) {	  	
	  	Param param = paramByName(name);
	  	if (param!=null ){	  		
	  		param.setValue(value);
	  		param.setType(aParamType);
	  	}  else   param = new Param (name.toUpperCase(), value, 0, 0);	  			  	
	  	super.put(name.toUpperCase(),param);
	  }

	  public String paramValueByName(String aName) {
		  Param param = paramByName(aName);
		  return param!=null?param.getValue():null;
	  }
	  
	  public boolean paramExist(String aName) {
	  	return  super.containsKey(aName.toUpperCase());
	  }

	  public Param  paramByName(String aName) {
		  	String sKey = aName.toUpperCase();		  	
		  	if (paramExist(sKey))
		  		return  ((Param)super.get(sKey));
		  	else return null;
	  }
	  
	  
	  public int paramDataTypeByName(String aName) {
		  Param param = paramByName(aName);
		  return param!=null?param.getType():0;
	  }
	  

	  public int paramInOutTypeByName(String aName) {
		  Param param = paramByName(aName);
		  return param!=null?param.getInOut():0;
	  }

	  	  
	  
	  public Map<String, String> toMap () {
		  Map<String, String> toReturn = new HashMap<String, String>();		  
		  
		   for ( String key : this.keySet() ) {			   
			 toReturn.put(key, this.get(key).getValue());  
		   }
		   
		  return toReturn;
	  }
	  

	  
	  
	  
	 /** 
	 * @param value
	 */
	  public void merge (Params value) {
		
			for (java.util.Map.Entry<String, Param>  entry : value.entrySet()) {						
				if (this.containsKey(entry.getKey()) ) {
				 this.paramByName(entry.getKey()).setValue((entry.getValue()).getValue());
			}			
		}										  	
	  }
	
	  
	public void copyFrom (Params value) {		
		if (value!= null) {
			for (java.util.Map.Entry<String, Param>  entry : value.entrySet()) {
				this.add(entry.getValue());
			}					
		}
	}
	
}
