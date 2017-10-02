/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkfs66o96;

import java.util.ArrayList;
 
public class TestJNIObjectArray {
   static {
      System.loadLibrary("libmcwprofile"); // myjni.dll (Windows) or libmyjni.so (Unixes)
   }
   // Native method that receives an Integer[] and
   //  returns a Double[2] with [0] as sum and [1] as average
   private native Double[] sumAndAverage(Integer[] numbers);
 
   public static void main(String args[]) {
      Integer[] numbers = {11, 22, 32};  // auto-box
      Double[] results = new TestJNIObjectArray().sumAndAverage(numbers);
      System.out.println("In Java, the sum is " + results[0]);  // auto-unbox
      System.out.println("In Java, the average is " + results[1]);
   }
}