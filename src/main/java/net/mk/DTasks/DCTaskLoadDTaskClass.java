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
public class DCTaskLoadDTaskClass extends DistributedTask{
    File DataFile;
    String LICENSE_KEY;String udAppName; String SourceCode=null; Object[] Inputs;boolean Jared;
    byte[] ByteCode=null;
    
    /**
     * Task based on source code, this will ensure that application has all the necessary libraries.
     * @param LICENSE_KEY
     * @param NameOfFile
     * @param SourceCode
     * @param Inputs
     * @param debug 
     */
    public DCTaskLoadDTaskClass(String LICENSE_KEY,String NameOfFile,boolean Jared){
        this.LICENSE_KEY=LICENSE_KEY;
        this.udAppName=NameOfFile;;
        this.Jared=Jared;
    }
    @Override
    public Boolean compute() {
        try {
            CompileClass ccl = new CompileClass();
            Class clas;
             
               if(!Jared)
                    clas = ccl.loadClass(udAppName); // Search externally and load internally
                   else // Search internally and load internally from JAR loaded at application initialization
                    clas = Class.forName(udAppName);// ClassLoader.getSystemClassLoader().loadClass(App);

       return true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DCTaskLoadDTaskClass.class.getName()).log(Level.SEVERE, null, ex);
        }
         return false;
    }
    
   
}
