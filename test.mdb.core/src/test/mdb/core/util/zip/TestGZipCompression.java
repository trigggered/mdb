package test.mdb.core.util.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import mdb.core.shared.utils.zip.GZipHelper;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TestGZipCompression extends TestCase {
  //private GZipCompression gZipCompression = null;



  public void testCompressByteInFileByGZip() throws IOException {
    byte[] aByteArray = null;
    String sFileName = "";
    GZipHelper.compressByteInFileByGZip(aByteArray, sFileName);
    /**@todo fill in the test code*/
  }

 
  public void testDecompresFileInByteByGZip() {
//    String aCompressFileName = "d:\\FromServer.gz";
    String aCompressFileName = "d:\\Test2.gz";
    byte[] actualReturn = GZipHelper.decompresFileInByteByGZip(aCompressFileName);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ByteArrayInputStream bis = new ByteArrayInputStream(actualReturn);

     int b;
     while ( (b = bis.read()) != -1) {
       baos.write(b);
     }
      //bis.close();
      System.out.println(baos.toString());
   }



  public void TestDecompresFileInFileByGZip() {
    String l_CompressFileName = "D:\\CRF Files\\300614kv01300080202805082010085416.crf";
    //String l_DecompresFileName = "D:\\CRF Files\\300614kv01300080202805082010085416.xml";
    String l_DecompresFileName = "D:\\CRF Files\\qwer.xml";
    GZipHelper.decompresFileInFileByGZip(l_CompressFileName, l_DecompresFileName);
    java.io.File unzipedFile = new File(l_DecompresFileName);
    Assert.assertTrue(unzipedFile.exists());
   
  }

  public void testGZipCompression() {
    //gZipCompression = new GZipCompression();
    /**@todo fill in the test code*/
  }

  public void testCompressByteArray() throws IOException {
    byte[] bytes = null;
    byte[] expectedReturn = null;
    byte[] actualReturn = GZipHelper.compress(bytes);
    assertEquals("return value", expectedReturn, actualReturn);
    /**@todo fill in the test code*/
  }

  public void testIsCompressed() throws IOException {
    byte[] bytes = null;
    boolean expectedReturn = false;
    boolean actualReturn = GZipHelper.isCompressed(bytes);
    assertEquals("return value", expectedReturn, actualReturn);
    /**@todo fill in the test code*/
  }

  public void testUncompressByteArray() {
    byte[] bytes = null;
    byte[] expectedReturn = null;
    byte[] actualReturn = GZipHelper.uncompress(bytes);
    assertEquals("return value", expectedReturn, actualReturn);
    /**@todo fill in the test code*/
  }
  
  public void testByteConcat() {
    String aCompressFileName = "d:\\Test2.gz";
    String aNewCompressFileName = "d:\\NewTest2.gz";
    String Key = "1234567890";
    
    byte[] bKey =  Key.getBytes();
    int nKeyLength = bKey.length;
    
    byte[] bData = GZipHelper.decompresFileInByteByGZip(aCompressFileName);
    int nDataLength = bData.length;
    
    ByteArrayInputStream bisKey = new ByteArrayInputStream(bKey);
    assertEquals(Key, new String(bKey));
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();        
    
    baos.write(bKey, 0, nKeyLength);
    assertEquals(baos.size(),nKeyLength);    
    
    baos.write(bData, 0, bData.length);
    assertEquals(baos.size(),nKeyLength+nDataLength);
    
    ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());
    
    byte[] newKey =new byte[nKeyLength];
    byte[] newData =new byte[nDataLength];   
    
    bis.read(newKey, 0,  nKeyLength );
    assertEquals(new String(newKey), new String(bKey));        

    bis.read(newData, 0, nDataLength );
    assertEquals(new String(newData), new String(bData));
  }

}

 

