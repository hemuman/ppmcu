/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkfs66o96;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author PDI
 */
public class Encrypt {
     static {
      System.loadLibrary("libmcwprofile"); // myjni.dll (Windows) or libmyjni.so (Unixes)
   }
   // Native method that receives filename and password and 
   //returns true on successful encryption or decryption

   private native int coder(String file,String password); // 
   private native int decoder(String file,String password);
   
   private native String code(String StringToEncrypt,String password);
   private native String decode(String StringToDecrypt,String password);
   
   private native byte[] bytecode(byte[] StringToEncrypt,String password);
   private native byte[] bytedecode(byte[] StringToDecrypt,String password);
   
   private native byte[] read(String file,String password);//Return file data
   private native String write(String file,byte[] DataToWrite,String password);//Return file name
 
   //private native int isValidKey(String Key,String UserName, String Password); 
   
   private native int isValidKey(String Key,String UserName, int[] PassKey); 
   ///  Chars->Qty is a prime number and sum is prime number(Private Key) and ints are even and multiple of prime numbers(Public Key)
   ///  Place them randomly at any sequence of int and char but maintain seq of numbers
   ///  License file will be an image containing key
   
   public boolean isValidKeyPair(String Key,String UserName, String Password){
       System.out.println(Password);
       String[] KeyAtrings=Password.split("-");
       int[] Keys=new int[KeyAtrings.length];
      // System.out.println( "Key length ="+ Keys.length);
       for(int i=0;i<Keys.length;i++) {
           Keys[i]=Integer.parseInt(KeyAtrings[i]);
          // System.out.println(  Keys[i]);
       }
       
       Integer res = new Encrypt().isValidKey(Key,UserName,Keys); //Key, User,Pass
       if(res==0) return true;
       else return false;
   }

      /** Read the given binary file, and return its contents as a byte array.*/ 
  byte[] read(String aInputFileName){
    log("Reading in binary file named : " + aInputFileName);
    File file = new File(aInputFileName);
    log("File size: " + file.length());
    byte[] result = new byte[(int)file.length()];
    try {
      InputStream input = null;
      try {
        int totalBytesRead = 0;
        input = new BufferedInputStream(new FileInputStream(file));
        while(totalBytesRead < result.length){
          int bytesRemaining = result.length - totalBytesRead;
          //input.read() returns -1, 0, or more :
          int bytesRead = input.read(result, totalBytesRead, bytesRemaining); 
          if (bytesRead > 0){
            totalBytesRead = totalBytesRead + bytesRead;
          }
        }
        /*
         the above style is a bit tricky: it places bytes into the 'result' array; 
         'result' is an output parameter;
         the while loop usually has a single iteration only.
        */
        log("Num bytes read: " + totalBytesRead);
      }
      finally {
        log("Closing input stream.");
        input.close();
      }
    }
    catch (FileNotFoundException ex) {
      log("File not found.");
    }
    catch (IOException ex) {
      log(ex);
    }
    return result;
  }
  
  private String readEncryptedFile (String FileName,String Password){
        return bytedecode(read(FileName),Password).toString();
  }
  
  private void writeEncryptedFile (String FileName,String dataToEncryptAndWrite,String Password){
       write(bytecode(dataToEncryptAndWrite.getBytes(),Password),FileName);
  }
    /**
   Write a byte array to the given file. 
   Writing binary data is significantly simpler than reading it. 
  */
  void write(byte[] aInput, String aOutputFileName){
    log("Writing binary file...");
    try {
      OutputStream output = null;
      try {
        output = new BufferedOutputStream(new FileOutputStream(aOutputFileName));
        output.write(aInput);
      }
      finally {
        output.close();
      }
    }
    catch(FileNotFoundException ex){
      log("File not found.");
    }
    catch(IOException ex){
      log(ex);
    }
  }
  
  /** Read the given binary file, and return its contents as a byte array.*/ 
  byte[] readAlternateImpl(String aInputFileName){
    log("Reading in binary file named : " + aInputFileName);
    File file = new File(aInputFileName);
    log("File size: " + file.length());
    byte[] result = null;
    try {
      InputStream input =  new BufferedInputStream(new FileInputStream(file));
      result = readAndClose(input);
    }
    catch (FileNotFoundException ex){
      log(ex);
    }
    return result;
  }
  
  /**
   Read an input stream, and return it as a byte array.  
   Sometimes the source of bytes is an input stream instead of a file. 
   This implementation closes aInput after it's read.
  */
  byte[] readAndClose(InputStream aInput){
    //carries the data from input to output :    
    byte[] bucket = new byte[32*1024]; 
    ByteArrayOutputStream result = null; 
    try  {
      try {
        //Use buffering? No. Buffering avoids costly access to disk or network;
        //buffering to an in-memory stream makes no sense.
        result = new ByteArrayOutputStream(bucket.length);
        int bytesRead = 0;
        while(bytesRead != -1){
          //aInput.read() returns -1, 0, or more :
          bytesRead = aInput.read(bucket);
          if(bytesRead > 0){
            result.write(bucket, 0, bytesRead);
          }
        }
      }
      finally {
        aInput.close();
        //result.close(); this is a no-operation for ByteArrayOutputStream
      }
    }
    catch (IOException ex){
      log(ex);
    }
    return result.toByteArray();
  }
  
  private static void log(Object aThing){
    System.out.println(String.valueOf(aThing));
  }

  public static void main(String args[]) {
       //Integer[] numbers = {11, 22, 32};  // auto-box
      System.out.println("#main test isValidKeyPair result1 is:  " );  // auto-unbox
      boolean res = new Encrypt().isValidKeyPair("gvreeebsep","Manoj","10-8-4-14-10-12-12-12-4-7"); //Key, User,Pass
      //boolean res = new Encrypt().isValidKeyPair("gvreeebse0","Manoj","10-8-4-14-10-12-12-12-4-7"); //Key, User,Pass
       System.out.println("isValidKeyPair result1 is " + res);  // auto-unbox
//      res = new Encrypt().isValidKey("KumarManoj","Manoj1","Kumar"); //Key, User,Pass
//      System.out.println("isValidKey result2 is " + res);  // auto-unbox
     // System.out.println("In Java, the average is " + results[1]);
   }
}
