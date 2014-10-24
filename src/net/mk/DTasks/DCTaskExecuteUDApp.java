/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.DTasks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONException;
import net.mk.dc.DistributedTask;
import net.mk.dcApps.CompileClass;
import net.mk.ppmcu2D.ScreenShareClient;

/**
 * Accept and Load/Register class file for execution.
 * @author PDI
 */
public class DCTaskExecuteUDApp extends DistributedTask{
    File DataFile;
    String LICENSE_KEY;String udAppName; String SourceCode=null; Object[] Inputs;boolean debug;
    String SupportingClasses[]=null;
    byte[] ByteCode=null;
    
    /**
     * Task based on source code, this will ensure that application has all the necessary libraries.
     * @param LICENSE_KEY
     * @param NameOfFile
     * @param SourceCode
     * @param Inputs
     * @param debug 
     */
    public DCTaskExecuteUDApp(String LICENSE_KEY,String NameOfFile, String SourceCode, Object[] Inputs,boolean debug){
        this.LICENSE_KEY=LICENSE_KEY;
        this.udAppName=NameOfFile;
        this.SourceCode=SourceCode;
        this.Inputs=Inputs;
        this.debug=debug;
    }
    
    /**
     * Task based on ByteCode. No compilation needed.
     * @param LICENSE_KEY
     * @param NameOfFile
     * @param ByteCode
     * @param Inputs
     * @param debug 
     */
    public DCTaskExecuteUDApp(String LICENSE_KEY,String NameOfFile, byte[] ByteCode, Object[] Inputs,boolean debug){
        this.LICENSE_KEY=LICENSE_KEY;
        this.udAppName=NameOfFile;
        this.ByteCode=ByteCode;
        this.Inputs=Inputs;
        this.debug=debug;
    }
    
    /**
     * Assumes that the class file exists at the given location.
     * @param LICENSE_KEY
     * @param NameOfFile
     * @param Inputs
     * @param debug 
     */
    public DCTaskExecuteUDApp(String LICENSE_KEY,String NameOfFile, Object[] Inputs,boolean debug){
        this.LICENSE_KEY=LICENSE_KEY;
        this.udAppName=NameOfFile;
        
        this.Inputs=Inputs;
        this.debug=debug;
    }
    
     public DCTaskExecuteUDApp(String LICENSE_KEY,String NameOfFile,String SupportingClasses[], Object[] Inputs,boolean debug){
        this.LICENSE_KEY=LICENSE_KEY;
        this.udAppName=NameOfFile;
        this.SupportingClasses=SupportingClasses;
        this.Inputs=Inputs;
        this.debug=debug;
    }
    
    @Override
    public Object compute() {
        try {
            if(debug)
            System.out.println("#DCTaskExecuteUDApp Start...");
            if(SourceCode!=null)
            createDatafile(udAppName.replace(".", "/")+".java", SourceCode);
            
            if(ByteCode!=null)
            createDatafile(udAppName.replace(".", "/")+".class", SourceCode);
            
            
            
            // Create a CompilingClassLoader
            CompileClass ccl = new CompileClass();

            // Load the main class through our CCL
            Class clas = ccl.loadClass("net.mk.dc.DistributedTask");
            
            if(SupportingClasses!=null)
                for(int i=0;i<SupportingClasses.length;i++){
                    ccl.loadClass(SupportingClasses[i]);
                }
            
            clas = ccl.loadClass(udAppName);
            
/**
 *     Class c = Class.forName("javax.swing.JLabel");
    Constructor ctor = c.getDeclaredConstructor(String.class);
    Object o = ctor.newInstance("some string");
    assert "some string".equals(o.getClass()
                                 .getDeclaredMethod("getText", new Class[0])
                                 .invoke(o)); 
 */

            DistributedTask dt=(DistributedTask)clas.getConstructors()[0].newInstance(Inputs);
            if(debug)
            System.out.println("#DCTaskExecuteUDApp Working...");

            // Call the method
            return dt.compute();
        } catch (JSONException ex) {
            Logger.getLogger(DCTaskExecuteUDApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DCTaskExecuteUDApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(DCTaskExecuteUDApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DCTaskExecuteUDApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(DCTaskExecuteUDApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(DCTaskExecuteUDApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DCTaskExecuteUDApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DCTaskExecuteUDApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
   return null;
       
    }
    
    private boolean createDatafile(String AppName, String Content) throws JSONException, IOException {

        boolean result=false;
        System.out.println("#createDatafile for "+AppName);
        DataFile = new File(AppName);
        File parent = DataFile.getParentFile();
        if(!parent.exists() && !parent.mkdirs()){
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
        // System.out.println("Temp file : " + temp2.getAbsolutePath());
        //Write empty array
        FileWriter fw = null;
        try {
            fw = new FileWriter(DataFile.getAbsoluteFile());
        } catch (IOException ex) {
            Logger.getLogger(ScreenShareClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(Content);
        bw.close();
        
        result=true;
        return result;
    }
 
}
