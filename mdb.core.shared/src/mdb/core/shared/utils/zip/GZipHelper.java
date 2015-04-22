package mdb.core.shared.utils.zip;

import java.io.*;
import java.util.zip.*;

//import org.apache.commons.logging.*;

/**
 * <code>GZipCompression</code> a CompressionStrategy implementation
 * using the GZip library included in the JDK java.util.zip.
 * This is the default CompressionStrategy used by the CompressionHelper
 * discovery when no other implementation is discovered
 *
 * @author Zhuk A.L.
 * @version 1
 *
 */
public class GZipHelper {
  /** The logger for this class */
  //private static final transient Log log = LogFactory.getLog(GZipCompression.class);

  /**
   * Determines if a byte array is compressed. The java.util.zip GZip implementaiton does not expose
   * the GZip header so it is difficult to determine if a string is compressed.
   * @param bytes an array of bytes
   * @return true if the array is compressed or faluse otherwise
   * @throws java.io.IOException if the byte array couldn't be read
   */

  public static boolean isCompressed(byte[] bytes) throws IOException {
    //We only need the first 2 bytes
    ByteArrayInputStream is = new ByteArrayInputStream(bytes, 0, 2);
    int b = readByte(is);
    b = (readByte(is) << 8) | b;

    return (b == GZIPInputStream.GZIP_MAGIC);
  }

  /**
   * @param in The InputStream to read the byte from
   * @return The byte value
   * @throws java.io.IOException if a byte could not be read
   */
  private static int readByte(InputStream in) throws IOException {
    int b = in.read();
    if (b == -1) {
      throw new EOFException();
    }
    return b;
  }

  
  public static byte[] compress (String value) throws IOException {
	 return  compress(value.getBytes());
  }
  /**
   * Used for compressing  a byte array into a new byte array using GZIP
   * @param bytes  An array of bytes to compress
   * @return a compressed byte array
   * @throws java.io.IOException if it fails to write to a GZIPOutputStream
   * @see java.util.zip.GZIPOutputStream
   */
  public static byte[] compress(byte[] bytes) throws IOException {
    //log.debug("Compressing message of size: " + bytes.length);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    GZIPOutputStream gos = new GZIPOutputStream(baos);
    gos.write(bytes, 0, bytes.length);
    gos.finish();
    gos.close();
    byte[] compressedByteArray = baos.toByteArray();
    //log.debug("Compressed message to size: " + compressedByteArray.length);
    return compressedByteArray;
  }
  
  
  public static void compressByteInFileByGZip(byte[] aByteArray,	String sFileName) throws IOException {
	try {
			ByteArrayInputStream bais = new ByteArrayInputStream(aByteArray);
			BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(
					new	FileOutputStream(sFileName)));
			int c;
			while ( (c = bais.read()) != -1) {
				out.write(c);
			}
			bais.close();
			out.close();
		
		}
		catch (Exception ex) {
		throw new java.io.IOException(ex.getMessage());
		}
	}

  /**
   * Used for uncompressing a byte array into a uncompressed byte array using GZIP
   * @param bytes  An array of bytes to uncompress
   * @return an uncompressed byte array
   * @throws java.io.IOException if it fails to read from a GZIPInputStream
   * @see java.util.zip.GZIPInputStream
   */
  public static byte[] uncompress(byte[] bytes) { //throws IOException
    try {

      //log.debug("Uncompressing message of size: " + bytes.length);
      if (!isCompressed(bytes)) {
        //log.warn("data is not of type GZIP compressed. The data may not have been compressed in the first place");
        return bytes;
      }

      ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
      GZIPInputStream gis = new GZIPInputStream(bais);
      return unCompressInputStream(gis);
    }
    catch (Exception ex) {
      return bytes;
    }

  }

  private static byte[] unCompressInputStream(GZIPInputStream aGZipInput) { //throws IOException
    try {

      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      byte[] buf = new byte[2048];
      int len;

      while ( (len = aGZipInput.read(buf)) != -1) {
        baos.write(buf, 0, len);
      }

      return  baos.toByteArray();
      
    }
    catch (Exception ex) {
      byte[] buf = new byte[1];
      return buf;
    }
  }



  public static void decompresFileInFileByGZip(String aCompressFileName,
                                               String aDecompresFileName) {
    try {

      BufferedReader in = new BufferedReader(new InputStreamReader(new
          GZIPInputStream(new FileInputStream(aCompressFileName))));
      readInputToOutpuStream(in,
                             new BufferedOutputStream(new FileOutputStream(aDecompresFileName)));
    }
    catch (Exception ex) {

    }
  }

  public static byte[] decompresFileInByteByGZip(String aCompressFileName) {
    try {

      GZIPInputStream gis = new GZIPInputStream(new FileInputStream(
          aCompressFileName));
      return unCompressInputStream(gis);
    }
    catch (Exception ex) {
      return null;
    }
  }

  private static OutputStream readInputToOutpuStream(Reader in,
      OutputStream out) {
    try {
      int c;
      while ( (c = in.read()) != -1) {
        out.write(c);
      }
      return out;
    }
    catch (Exception ex) {
      return out;
    }

  }
}
