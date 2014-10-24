/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dcApps;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import json.JSONObject;

/**
 *
 * @author PDI
 */
public class GetAppGUI {
    
    public static JPanel getAppGUI(String App,Object[] Inputs,boolean Jared, String JARPath) {
        System.out.println("#GetAppGUI "+App);
        try {
            //Object[] Inputs={LICENSE_KEY,RemoteSysSpecs,true};
           Class[] InputClasses={java.lang.String.class,java.lang.String.class,json.JSONObject.class};
            // Create a CompilingClassLoader
               CompileClass ccl = new CompileClass();
               Class clas = null;
               
               // Load the main class through our CCL
               if(!Jared)
                clas = ccl.loadClass(App); // Search externally and load internally
               else  {// Search internally and load internally from JAR loaded at application initialization
                   
                   //Ignore objects in this classe
                   if(JARPath.equalsIgnoreCase("this")) clas = Class.forName(App);
                   else{
               URL myJarFile = null;
               File jarFile=null;
               try {
                   myJarFile = new URL("file://"+System.getProperty("user.dir")+JARPath); // Load the specified JAR
                   jarFile = new File(JARPath);  
                   System.out.println("JARPATH = "+jarFile.toURI().toURL());
               
               
                   URLClassLoader child = new URLClassLoader (new URL[]{jarFile.toURI().toURL()}, Thread.currentThread().getContextClassLoader());
                
                   clas = Class.forName (App, true, child);
                    //Method method = classToLoad.getDeclaredMethod ("myMethod");
                    //Object instance = classToLoad.newInstance ();
                    //Object result = method.invoke (instance);
                   } catch (MalformedURLException ex) {
                       System.out.println("#Not loaded "+App +" from" + JARPath+" becasue of "+ex);
                   Logger.getLogger(GetAppGUI.class.getName()).log(Level.SEVERE, null, ex);
               }
                   }
               }
               //For debugging
               for(int i=0;i<clas.getConstructors().length;i++){
                   System.out.println("#UDAPP Constructor ["+i+"] name\t"+clas.getConstructors()[i].getName());
                   System.out.println("#UDAPP Constructor getParameterTypes\t"+clas.getConstructors()[i].getParameterTypes().length);
                   for (int j = 0; j < clas.getConstructors()[i].getParameterTypes().length; j++) {
                       System.out.println("#UDAPP getParameterTypes name\t" + clas.getConstructors()[i].getParameterTypes()[j].getName());
                       System.out.println("#UDAPP getParameterTypes getSimpleName\t" + clas.getConstructors()[i].getParameterTypes()[j].getSimpleName());
                   }
               }
              
               //return (JPanel)clas.getConstructor(InputClasses).newInstance(Inputs); //Doesn't work in JNA Interface
               return (JPanel)clas.getDeclaredConstructor(InputClasses).newInstance(Inputs);
        } catch (ClassNotFoundException ex) {
            System.out.println("#Not loaded "+App +" from" + JARPath+" becasue of "+ex);
            Logger.getLogger(GetAppGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            System.out.println("#Not loaded "+App +" from" + JARPath+" becasue of "+ex);
            Logger.getLogger(GetAppGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            System.out.println("#Not loaded "+App +" from" + JARPath+" becasue of "+ex);
            Logger.getLogger(GetAppGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            System.out.println("#Not loaded "+App +" from" + JARPath+" becasue of "+ex);
            Logger.getLogger(GetAppGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            System.out.println("#Not loaded "+App +" from" + JARPath+" becasue of "+ex);
            Logger.getLogger(GetAppGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            System.out.println("#Not loaded "+App +" from" + JARPath+" becasue of "+ex);
            Logger.getLogger(GetAppGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            System.out.println("#Not loaded "+App +" from" + JARPath+" becasue of "+ex);
            Logger.getLogger(GetAppGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("#Not loaded "+App +" from" + JARPath);
            return new JPanel();
    }

    public static Class getAppGUI(String App,boolean Jared) {
        System.out.println("#GetAppGUI "+App);
        try {
            //Object[] Inputs={LICENSE_KEY,RemoteSysSpecs,true};
           Class[] InputClasses={String.class,String.class,JSONObject.class};
            // Create a CompilingClassLoader
               CompileClass ccl = new CompileClass();
               Class clas;
               
               // Load the main class through our CCL
               if(!Jared)
                clas = ccl.loadClass(App); // Search externally and load internally
               else // Search internally and load internally from JAR loaded at application initialization
                clas = Class.forName(App);// ClassLoader.getSystemClassLoader().loadClass(App);
               
               //For debugging
//               for(int i=0;i<clas.getConstructors().length;i++){
//                   System.out.println("#UDAPP Constructor name\t"+clas.getConstructors()[i].getName());
//                   System.out.println("#UDAPP Constructor getParameterTypes\t"+clas.getConstructors()[i].getParameterTypes().length);
//                   for(int j=0;j<clas.getConstructors()[i].getParameterTypes().length;j++){
//                       System.out.println("#UDAPP getParameterTypes name\t"+clas.getConstructors()[i].getParameterTypes()[j].getName());
//                   System.out.println("#UDAPP getParameterTypes getSimpleName\t"+clas.getConstructors()[i].getParameterTypes()[j].getSimpleName());
//                   }
//               }
               
               return clas;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetAppGUI.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (SecurityException ex) {
            Logger.getLogger(GetAppGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(GetAppGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }

}
