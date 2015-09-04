/**
 * 
 */
package mdb.core.shared.transformation.sql;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Logger;

import mdb.core.shared.data.Fields;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author azhuk
 * Creation date: Jul 28, 2015
 *
 */
public class ResultSetJacksonSerializerImpl  extends JsonSerializer<ResultSet>  
implements IResultSetSerializer  {
	private static final Logger _logger = Logger
			.getLogger(ResultSetJacksonSerializerImpl.class.getName());

	public static class ResultSetSerializerException extends JsonProcessingException{
        private static final long serialVersionUID = -914957626413580734L;

        public ResultSetSerializerException(Throwable cause){
            super(cause);
        }
    }
	
	
	@Override
    public Class<ResultSet> handledType() {
        return ResultSet.class;
    }

	

	/* (non-Javadoc)
	 * @see mdb.core.shared.transformation.sql.IResultSetSerializer#make(mdb.core.shared.data.Fields, java.sql.ResultSet)
	 */
	@Override
	public String make(Fields fields, ResultSet resultSet) {
		 ResultSetFieldsExtractor.extract(resultSet);
		 return make(resultSet);
	}

	
	/* (non-Javadoc)
	 * @see mdb.core.shared.transformation.IDataTransformation#make(java.sql.ResultSet)
	 */
	@Override
	public String make(ResultSet resultSet) {
		
		SimpleModule module = new SimpleModule();
		module.addSerializer(new ResultSetJacksonSerializerImpl());

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(module);
		
		//objectMapper.writeValueAsString(resultSet);
		// Use the DataBind Api here
		ObjectNode objectNode = objectMapper.createObjectNode();

		// put the resultset in a containing structure
		objectNode.putPOJO("results", resultSet);
	
		
		

		// generate all
		StringWriter  stringWriter = new StringWriter();
		try {
			objectMapper.writeValue(stringWriter, objectNode);
		} catch (JsonGenerationException e) {
			_logger.severe(e.getMessage());
			_logger.severe(e.getStackTrace().toString());
		} catch (JsonMappingException e) {
			_logger.severe(e.getMessage());
			_logger.severe(e.getStackTrace().toString());
		} catch (IOException e) {
			_logger.severe(e.getMessage());
			_logger.severe(e.getStackTrace().toString());
		}		
		
		return stringWriter.toString();
	}
	
	
	@Override
    public void serialize(ResultSet rs, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();
            String[] columnNames = new String[numColumns];
            int[] columnTypes = new int[numColumns];

            for (int i = 0; i < columnNames.length; i++) {
                columnNames[i] = rsmd.getColumnLabel(i + 1);
                columnTypes[i] = rsmd.getColumnType(i + 1);
            }

            jgen.writeStartArray();

            while (rs.next()) {

                boolean b;
                long l;
                double d;

                jgen.writeStartObject();

                for (int i = 0; i < columnNames.length; i++) {

                    jgen.writeFieldName(columnNames[i]);
                    switch (columnTypes[i]) {

                    case Types.INTEGER:
                        l = rs.getInt(i + 1);
                        if (rs.wasNull()) {
                            jgen.writeNull();
                        } else {
                            jgen.writeNumber(l);
                        }
                        break;

                    case Types.BIGINT:
                        l = rs.getLong(i + 1);
                        if (rs.wasNull()) {
                            jgen.writeNull();
                        } else {
                            jgen.writeNumber(l);
                        }
                        break;

                    case Types.DECIMAL:
                    case Types.NUMERIC:
                        jgen.writeNumber(rs.getBigDecimal(i + 1));
                        break;

                    case Types.FLOAT:
                    case Types.REAL:
                    case Types.DOUBLE:
                        d = rs.getDouble(i + 1);
                        if (rs.wasNull()) {
                            jgen.writeNull();
                        } else {
                            jgen.writeNumber(d);
                        }
                        break;

                    case Types.NVARCHAR:
                    case Types.VARCHAR:
                    case Types.LONGNVARCHAR:
                    case Types.LONGVARCHAR:
                        jgen.writeString(rs.getString(i + 1));
                        break;

                    case Types.BOOLEAN:
                    case Types.BIT:
                        b = rs.getBoolean(i + 1);
                        if (rs.wasNull()) {
                            jgen.writeNull();
                        } else {
                            jgen.writeBoolean(b);
                        }
                        break;

                    case Types.BINARY:
                    case Types.VARBINARY:
                    case Types.LONGVARBINARY:
                        jgen.writeBinary(rs.getBytes(i + 1));
                        break;

                    case Types.TINYINT:
                    case Types.SMALLINT:
                        l = rs.getShort(i + 1);
                        if (rs.wasNull()) {
                            jgen.writeNull();
                        } else {
                            jgen.writeNumber(l);
                        }
                        break;

                    case Types.DATE:
                        provider.defaultSerializeDateValue(rs.getDate(i + 1), jgen);
                        break;

                    case Types.TIMESTAMP:
                        provider.defaultSerializeDateValue(rs.getTime(i + 1), jgen);
                        break;

                    case Types.BLOB:
                        Blob blob = rs.getBlob(i);
                        provider.defaultSerializeValue(blob.getBinaryStream(), jgen);
                        blob.free();
                        break;

                    case Types.CLOB:
                        Clob clob = rs.getClob(i);
                        provider.defaultSerializeValue(clob.getCharacterStream(), jgen);
                        clob.free();
                        break;

                    case Types.ARRAY:
                        throw new RuntimeException("ResultSetSerializer not yet implemented for SQL type ARRAY");

                    case Types.STRUCT:
                        throw new RuntimeException("ResultSetSerializer not yet implemented for SQL type STRUCT");

                    case Types.DISTINCT:
                        throw new RuntimeException("ResultSetSerializer not yet implemented for SQL type DISTINCT");

                    case Types.REF:
                        throw new RuntimeException("ResultSetSerializer not yet implemented for SQL type REF");

                    case Types.JAVA_OBJECT:
                    default:
                        provider.defaultSerializeValue(rs.getObject(i + 1), jgen);
                        break;
                    }
                }

                jgen.writeEndObject();
            }

            jgen.writeEndArray();

        } catch (SQLException e) {
            throw new ResultSetSerializerException(e);
        }
    }


}
