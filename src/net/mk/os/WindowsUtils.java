/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.os;

/**
 *
 * @author PDI
 */
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;

public class WindowsUtils {
public static List<String> listRunningProcesses() {
    List<String> processes = new ArrayList<String>();
    try {
      String line;
      Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
      BufferedReader input = new BufferedReader
          (new InputStreamReader(p.getInputStream()));
      while ((line = input.readLine()) != null) {
          if (!line.trim().equals("")) {
              // keep only the process name
              line = line.substring(1);
              processes.add(line.substring(0, line.indexOf("\"")));
          }

      }
      input.close();
    }
    catch (Exception err) {
      err.printStackTrace();
    }
    return processes;
  }

public static JSONObject RunningProcesses( boolean PrintResult) {
      JSONArray temp=new JSONArray();
      JSONObject result=new JSONObject();
    List<String> processes = new ArrayList<String>();
    try {
      String line;
      Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
      BufferedReader input = new BufferedReader
          (new InputStreamReader(p.getInputStream()));
      while ((line = input.readLine()) != null) {
          if (!line.trim().equals("")) {
              // keep only the process name
              temp=new JSONArray("["+line+"]");
              
              line = line.substring(1);
              processes.add(line.substring(0, line.indexOf("\"")));
              result.put(line.substring(0, line.indexOf("\"")), temp);
          }

      }
      input.close();
    }
    catch (Exception err) {
      err.printStackTrace();
    }
    if(PrintResult)
      try {
          System.out.println(result.toString(1));
      } catch (JSONException ex) {
          Logger.getLogger(WindowsUtils.class.getName()).log(Level.SEVERE, null, ex);
      }
    
    return result;
  }

  
  public static void main(String[] args){
      
      
      List<String> processes = listRunningProcesses();
      String result = "";

      // display the result 
      Iterator<String> it = processes.iterator();
      int i = 0;
      while (it.hasNext()) {
         result += it.next() +",";
         i++;
         if (i==10) {
             result += "\n";
             i = 0;
         }
      }
      System.out.println(result);
     // msgBox("Running processes : " + result);
      
      RunningProcesses(true);
  }

  public static void msgBox(String msg) {
    javax.swing.JOptionPane.showConfirmDialog((java.awt.Component)
       null, msg, "WindowsUtils", javax.swing.JOptionPane.DEFAULT_OPTION);
  }
}