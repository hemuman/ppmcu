/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dcApps;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONException;
import json.JSONObject;
import net.mk.DTasks.FileBrowserDTask;
import net.mk.DTasks.FileOperationDTask;
import net.mk.DTasks.FileTransferDTask;
import net.mk.dc.DistributedTaskService;
import net.mk.dcApps.FSExplorer;
import net.mk.ppmcuGUI.MCWConfClass;

/**
 *
 * @author Manoj Kumar hemuman@gmail.com
 */
public class DataLibrary {
    
    static String LICENSE_KEY;
    public DataLibrary(String LicenseKey, String LibraryPath)
    {
        DataLibrary.LICENSE_KEY=LicenseKey;
        FileBrowserDTask fbdt = new FileBrowserDTask(LibraryPath,true); // All the sub directory and files
        try {
            ObjectList=new JSONObject((String) fbdt.compute());
        } catch (JSONException ex) {
            Logger.getLogger(DataLibrary.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    private  static JSONObject ObjectList =new JSONObject();

    /**
     * Get the value of ObjectList
     *
     * @return the value of ObjectList
     */
    public static JSONObject getObjectList() {
        return ObjectList;
    }

    /**
     * 
     * @param FileName
     * @return
     * @throws JSONException 
     */
    public File getFile(String FileName) throws JSONException{
        return new File(ObjectList.getString(FileName));
    }
    
    /**
     * 
     * @param FileName
     * @param tobeCopiedFileName
     * @return
     * @throws JSONException 
     */
    public static boolean createCopy(String FileName,String tobeCopiedFileName) throws JSONException{
        return  new FileOperationDTask(ObjectList.getString(FileName), FileOperationDTask.COPY, tobeCopiedFileName).compute(); 
    }
    
    /**
     * Creates copy on remote machine from remote machine.
     * @param FileName
     * @param tobeCopiedFileName
     * @param RemoteMachineSpec
     * @return
     * @throws JSONException
     * @throws IOException 
     */
    public static boolean createCopy(String FileName,String tobeCopiedFileName, JSONObject RemoteMachineSpec) throws JSONException, IOException{
        return (boolean) new DistributedTaskService(LICENSE_KEY, RemoteMachineSpec, MCWConfClass.DCTPort, new FileOperationDTask(ObjectList.getString(FileName), FileOperationDTask.COPY, tobeCopiedFileName)).execute();
    }

    /**
     * Creates local copy from remote machine.
     * RL -> Download form Remote to Local.
     * @param FileName
     * @param tobeCopiedFileName
     * @param RemoteMachineSpec
     * @return
     * @throws JSONException
     * @throws IOException 
     */
     public static boolean createCopyRL(String FileName,String tobeCopiedFileName, JSONObject RemoteMachineSpec) throws JSONException, IOException{
        //Get/Download File data from remote machine,
        //Write on local machine
         //Download
         byte[] fileData=(byte[]) new DistributedTaskService(LICENSE_KEY, RemoteMachineSpec, MCWConfClass.DCTPort, new FileTransferDTask(ObjectList.getString(FileName),new byte[]{},true)).execute();
         new FileTransferDTask(tobeCopiedFileName,fileData,false).compute();             
         return true;
    }
     
     /**
      * Creates remote copy from local machine.
      * LR-> Upload from Local to remote.
      * @param FileName
      * @param tobeCopiedFileName
      * @param RemoteMachineSpec
      * @return
      * @throws JSONException
      * @throws IOException 
      */
      public static boolean createCopyLR(String FileName,String tobeCopiedFileName, JSONObject RemoteMachineSpec) throws JSONException, IOException{
        //Get/Download File data from remote machine,
        //Write on local machine
         //Download
         byte[] fileData=new FileTransferDTask(ObjectList.getString(FileName),new byte[]{},true).compute(); ;//ObjectList.getString(FileName),new byte[]{},true
         new DistributedTaskService(LICENSE_KEY, RemoteMachineSpec, MCWConfClass.DCTPort, new FileTransferDTask(tobeCopiedFileName,fileData,false)).execute();
                     
         return true;
    }
}
