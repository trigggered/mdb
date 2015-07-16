/**
 * 
 */
package mdb.core.vaadin.ui.data.fields;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import mdb.core.shared.data.EMdbFieldType;

import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Slider;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

/**
 * @author azhuk
 * Creation date: Aug 11, 2015
 *
 */
public class ColumnGenerators {
	private static final Logger _logger = Logger
			.getLogger(ColumnGenerators.class.getName());
	
	
	static ColumnGenerators _generators;
	
	public static ColumnGenerators instance() {
		if (_generators == null) {
			_generators = new ColumnGenerators();
		}
		return _generators;
	}
	
	public static BaseColumnGenerator create (EMdbFieldType  type, String containerPropertyName, String tittle) {
		
		switch (type) {
			case NUMBER:   			    			
			case FLOAT: 			    			   			
			case VARCHAR: 
				return instance().new TextFieldColumnGenerator(containerPropertyName, tittle);
		}
		
		return null;
		
	}
	
	public static class  FieldOptions {
		boolean Immediate;
        /**
		 * @return the immediate
		 */
		public boolean isImmediate() {
			return Immediate;
		}

		/**
		 * @param immediate the immediate to set
		 */
		public void setImmediate(boolean immediate) {
			Immediate = immediate;
		}

		/**
		 * @return the required
		 */
		public boolean isRequired() {
			return required;
		}

		/**
		 * @param required the required to set
		 */
		public void setRequired(boolean required) {
			this.required = required;
		}

		/**
		 * @return the requiredError
		 */
		public String getRequiredError() {
			return requiredError;
		}

		/**
		 * @param requiredError the requiredError to set
		 */
		public void setRequiredError(String requiredError) {
			this.requiredError = requiredError;
		}

		/**
		 * @return the validationVisible
		 */
		public boolean isValidationVisible() {
			return validationVisible;
		}

		/**
		 * @param validationVisible the validationVisible to set
		 */
		public void setValidationVisible(boolean validationVisible) {
			this.validationVisible = validationVisible;
		}

		/**
		 * @return the readOnly
		 */
		public boolean isReadOnly() {
			return readOnly;
		}

		/**
		 * @param readOnly the readOnly to set
		 */
		public void setReadOnly(boolean readOnly) {
			this.readOnly = readOnly;
		}

		/**
		 * @return the length
		 */
		public int getLength() {
			return length;
		}

		/**
		 * @param length the length to set
		 */
		public void setLength(int length) {
			this.length = length;
		}


		boolean required;
        String requiredError;
        boolean validationVisible;
        boolean readOnly;
        boolean visible;
        /**
		 * @return the visible
		 */
		public boolean isVisible() {
			return visible;
		}

		/**
		 * @param visible the visible to set
		 */
		public void setVisible(boolean visible) {
			this.visible = visible;
		}


		int length=-1;
    
        public FieldOptions() {
        	this(false,true,false,"", true);
        }
        
		public FieldOptions (	boolean readOnly,
								boolean Immediate,
						        boolean required,
						        String requiredError,
						        boolean validationVisible)
		{
			this.readOnly = readOnly;
			this.Immediate = Immediate;
	        this.required = required;
	        this.requiredError = requiredError;
	        this.validationVisible = validationVisible;
	        
		}
		

		public static AbstractField<?> applyOptions(FieldOptions options, AbstractField<?> field) {
			if ( options != null && field != null ) {
				
				field.setRequired(options.required);
				field.setRequiredError(options.requiredError);
				field.setImmediate(options.Immediate);
				field.setValidationVisible(options.validationVisible);
				field.setReadOnly(options.readOnly);
				field.setVisible(options.visible);
										
			}
			return field;
		}
		

		
	};
	
	public abstract class BaseColumnGenerator  implements  IColumnGenerator {
		
		
		FieldOptions _fieldOptions;
		private static final long serialVersionUID = 1L;
		private String _containerPropertyName;
		private String _title;
		private Class<?> _containerPropertyType;
	
		/**
		 * @return the _fiFieldOptions
		 */
		public FieldOptions getFieldOptions() {
			return _fieldOptions;
		}

		/**
		 * @param _fiFieldOptions the _fiFieldOptions to set
		 */
		public void setFieldOptions(FieldOptions value) {
			this._fieldOptions = value;
		}

		/**
		 * 
		 */
		public BaseColumnGenerator(String containerPropertyName, String title) {
			setContainerPropertyName(containerPropertyName); 
			_title = title;
		}
		
		
		
		
		/**
		 * @return the _containerPropertyName
		 */
		public String getContainerPropertyName() {
			return _containerPropertyName;
		}
		
		public void setContainerPropertyName(String value) {
			_containerPropertyName = value;
		}
		
		/**
		 * @return the _containerPropertyType
		 */
		public Class<?> getContainerPropertyType() {
			return _containerPropertyType;
		}
		
		public void setContainerPropertyType(Class<?>  value) {
			_containerPropertyType = value;
		}				
		
	
		 protected void addValidators(FieldOptions fieldOptions, AbstractField<?> field) {

			 if (fieldOptions ==  null)
				  return;
			 
			 if (fieldOptions.required) {
				 field.addValidator(new NullValidator("May not be null", false));
			 }
			 
			 if (fieldOptions.length > 0) {
				 field.addValidator(new StringLengthValidator(
				            "The name must be 1-10 letters (was {0})",
				            1, fieldOptions.length, true));	 
			 }
			 
		 }
		 
	}

         //table.addContainerProperty("textfield", TextField.class, null);
	public class TextFieldColumnGenerator extends  BaseColumnGenerator {

		private static final long serialVersionUID = 1L;

		public TextFieldColumnGenerator () {
			this("", "");
		}
		
		public TextFieldColumnGenerator (String containerPropertyName, String tittle) {
			super (containerPropertyName, tittle);			
			setContainerPropertyType (TextField.class);			
		}
		
        @Override
        public Object generateCell(Table source, Object itemId,
                     Object columnId) {
        	TextField field  = new TextField();
                 //tf.setInputPrompt("Type here…");
                 // tf.addStyleName("compact");
        	
           if (itemId!= null && (Integer) itemId % 2 == 0) {
               	 field.addStyleName("borderless");
           }               
               
           addValidators(_fieldOptions, field);
           return FieldOptions.applyOptions(_fieldOptions, field);                                  
        }        
	};
	
	public class NumberFieldColumnGenerator extends TextFieldColumnGenerator { 
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * @param colName
		 * @param colTitle
		 */
		public NumberFieldColumnGenerator(String colName, String colTitle) {
			super (colName, colTitle);
		}

		@Override
        public Object generateCell(Table source, Object itemId,
                     Object columnId) {
			
			
			final TextField field = (TextField) super.generateCell(source, itemId, columnId);
			
			StringToIntegerConverter plainIntegerConverter = new StringToIntegerConverter() {
			    protected java.text.NumberFormat getFormat(Locale locale) {
			        NumberFormat format = super.getFormat(locale);
			        format.setGroupingUsed(false);
			        return format;
			    };
			};
			// either set for the field or in your field factory for multiple fields
			field.setConverter(plainIntegerConverter);
			//field.addValidator(new IntegerRangeValidator("Fuck", null, null));
			
	
			field.addTextChangeListener(new TextChangeListener() {
				
				@Override
				public void textChange(TextChangeEvent event) {
					try {				
						
						Integer.parseInt(event.getText());
						
						field.setValue(event.getText());
						Object o =  field.getConvertedValue();
						field.validate();
					}
					catch (NumberFormatException e) {
						_logger.severe("Cannot be parsed as Integer: " + e);
					}
					
				}
			});
			
//			field.setConverter(Integer.class);
			//field.addValidator(StringLengthValidator);
			
			return field;
		}
	}
	
	
	public class TextFieldWithSelectedButtonsColumnGenerator extends  TextFieldColumnGenerator {

		private static final long serialVersionUID = 1L;

		public TextFieldWithSelectedButtonsColumnGenerator (String containerPropertyName, String tittle) {
			super (containerPropertyName, tittle);			
			setContainerPropertyType (TextField.class);			
		}
		
             @Override
             public Object generateCell(Table source, Object itemId,
                     Object columnId) {            	             	 
                 
            	 
            	 TextField tf = (TextField) super.generateCell(source, itemId, columnId);
            	                  
                 
                 HorizontalLayout hbox = new HorizontalLayout();
                 hbox.addComponent(tf);
                 Button btn = new Button("Click");
                 btn.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {						
						
					}
				});
                 hbox.addComponent(btn);
                 
                 return hbox;
             }        
	};
	

         //DateFieldContainerProperty("datefield", TextField.class, null);
		public class DateFieldGeneratedColumn extends BaseColumnGenerator {
			
			
			private static final long serialVersionUID = 1L;

			public DateFieldGeneratedColumn (String containerPropertyName, String tittle) {
				super (containerPropertyName, tittle);
				setContainerPropertyType (TextField.class);				
			}
		
		
             @Override
             public Object generateCell(Table source, Object itemId,
                     Object columnId) {
            	 DateField field = new DateField();
            	 field.addStyleName("compact");
                 if (itemId != null &&(Integer) itemId % 2 == 0) {
                	 field.addStyleName("borderless");
                 }
                 addValidators(_fieldOptions, field);
                 return FieldOptions.applyOptions(_fieldOptions, field);
             }
         
	};

         //table.addContainerProperty("combobox", TextField.class, null);
         public class ComboBoxGeneratedColumn extends BaseColumnGenerator {
 			
			private static final long serialVersionUID = 1L; 
     		
			Map<String, String> _map;
			
     		public ComboBoxGeneratedColumn (String containerPropertyName, String tittle) {
    			super (containerPropertyName, tittle);

				setContainerPropertyType (TextField.class);				
			}
     		
             @Override
             public Object generateCell(Table source, Object itemId,
                     Object columnId) {
                 ComboBox tf = new ComboBox();
                 
                 tf.setInputPrompt("Select");
                 tf.addStyleName("compact");
                 
                 
                 
                 if (itemId != null && (Integer) itemId % 2 == 0) {
                     tf.addStyleName("borderless");
                 }
                 
                 if (_map != null) {
                	  
                	 for (String key : _map.keySet()) {
                		 tf.addItem(key);
                		 tf.setItemCaption(key,  _map.get(key));
                	 }
                  
                 }
                 
                 addValidators(_fieldOptions, tf);
                 return FieldOptions.applyOptions(_fieldOptions, tf);
             }
             
             public void setValueMap(Map<String, String> map) {
            	 _map = map;
             }
         };

         //table.addContainerProperty("button", Button.class, null);
       public class ButtonGeneratedColumn extends BaseColumnGenerator {
         
		private static final long serialVersionUID = 1L;
		 
  		
  		public ButtonGeneratedColumn (String containerPropertyName, String tittle) {
			super (containerPropertyName, tittle);
			setContainerPropertyType (Button.class);				
		}
  		
             @Override
             public Object generateCell(Table source, Object itemId,
                     Object columnId) {
                 Button b = new Button("Button");
                 b.addStyleName("small");
                 return b;
             }
         };

         //table.addContainerProperty("label", TextField.class, null);
         public class LabelGeneratedColumn extends BaseColumnGenerator { 
        	 
			private static final long serialVersionUID = 1L;


			public   LabelGeneratedColumn (String containerPropertyName, String tittle) {
				super (containerPropertyName, tittle);
     			setContainerPropertyType (TextField.class);	 
        	 }
        	 
         
             public Object generateCell(Table source, Object itemId,
                     Object columnId) {
                 Label label = new Label("Label component");
                 label.setSizeUndefined();
                 label.addStyleName("bold");                 
                 return label;
             }
         };

         //table.addContainerProperty("checkbox", TextField.class, null);
         public class CheckBoxGeneratedColumn extends BaseColumnGenerator {
         
			private static final long serialVersionUID = 1L;

			public CheckBoxGeneratedColumn (String containerPropertyName, String tittle) {
 				super (containerPropertyName, tittle);        			
         			setContainerPropertyType (TextField.class);
        	 }
        	 
         
             @Override
             public Object generateCell(Table source, Object itemId,
                     Object columnId) {
            	 CheckBox field  = new CheckBox(null, true);
            	 addValidators(_fieldOptions, field);
                 return FieldOptions.applyOptions(_fieldOptions, field );
             }
         };

         //table.addContainerProperty("optiongroup", TextField.class, null);
         
         public class  OptionGroupColumnGenerator extends BaseColumnGenerator {
        	 
			private static final long serialVersionUID = 1L;

			List<String> _optionItems;
			
			public OptionGroupColumnGenerator (String containerPropertyName, List<String> optionItems, String tittle) {
  				super (containerPropertyName, tittle);
  				_optionItems = optionItems;
      			setContainerPropertyType (TextField.class); 
        	 }
        	 
             @Override
             public Object generateCell(Table source, Object itemId,
                     Object columnId) {
                 OptionGroup op = new OptionGroup();
                 op.addItems(_optionItems);
                 
                 /*op.addItem("Male");
                 op.addItem("Female");*/
                 
                 op.addStyleName("horizontal");
                 
                 return FieldOptions.applyOptions(_fieldOptions, op);
             }
         };

         //table.addContainerProperty("slider", TextField.class, null);
         public class SliderGeneratedColumn extends BaseColumnGenerator {
        	 
			private static final long serialVersionUID = 1L;

			public  SliderGeneratedColumn(String containerPropertyName, String tittle) {
   				super (containerPropertyName, tittle);        		
       			setContainerPropertyType (TextField.class); 
        	 }
        	 
             @Override
             public Object generateCell(Table source, Object itemId,
                     Object columnId) {
                 Slider s = new Slider();
                 s.setValue(30.0);
                 return FieldOptions.applyOptions(_fieldOptions, s);
             }
         };

         //table.addContainerProperty("progress", TextField.class, null);
         public class ProgressGeneratedColumn extends BaseColumnGenerator {
         
        	 private static final long serialVersionUID = 1L;
        	 
			/**
			 * @param containerPropertyName
			 */
			public ProgressGeneratedColumn(String containerPropertyName, String tittle) {
				super(containerPropertyName, tittle);
				setContainerPropertyType (TextField.class);
			}			

			//("progress", new ColumnGenerator() {
             @Override
             public Object generateCell(Table source, Object itemId,
                     Object columnId) {
                 ProgressBar bar = new ProgressBar();
                 bar.setValue(0.7f);
                 return FieldOptions.applyOptions(_fieldOptions, bar);
             }
         };        
    
     }


