/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkfs66o96;

import BinaryDataProcessing.ReadWriteTextFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import json.JSONException;
import json.JSONObject;

/**
 *
 * @author PDI
 */
public class JSONModel {
    
    public static JSONObject ModelData;

    /**
     * Get the value of ModelData
     *
     * @return the value of ModelData
     */
    public static JSONObject getConfigData() {
        return ModelData;
    }

    /**
     * Set the value of ModelData
     *
     * @param ModelData new value of ModelData
     */
    public static  void setConfigData(JSONObject ConfigData) {
        JSONModel.ModelData = ConfigData;
    }
    private static String Filename = "ModelData.pd";

    /**
     * Get the value of Filename
     *
     * @return the value of Filename
     */
    public static String getFilename() {
        return Filename;
    }

    /**
     * Set the value of Filename
     *
     * @param Filename new value of Filename
     */
    public void setFilename(String Filename) {
        JSONModel.Filename = Filename;
    }

    private static String ProjectDirectory = "C:/BEM/wizard/F600work";

    /**
     * Get the value of ProjectDirectory
     *
     * @return the value of ProjectDirectory
     */
    public static String getProjectDirectory() {
        return ProjectDirectory;
    }
    
    /**
     * Set the value of ProjectDirectory
     *
     * @param ProjectDirectory new value of ProjectDirectory
     */
    public static void setProjectDirectory(String ProjectDirectory) {
        JSONModel.ProjectDirectory = ProjectDirectory;
    }

    /**
     * 
     * @param Directory
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void Save(String Directory) throws FileNotFoundException, IOException{
        ReadWriteTextFile.setContents(Directory+"/"+getFilename(),ModelData.toString());
    }

    /**
     * 
     * @param Directory
     * @throws JSONException 
     */
    public static void Load(String Directory) throws JSONException{
            setConfigData(new JSONObject(ReadWriteTextFile.getContents(new File(Directory+"/ModelData.pd"))));
      }
        
    private static String REPORT="mkfs66ox.JSONModel";

    /**
     * Get the value of REPORT
     *
     * @return the value of REPORT
     */
    public static String getREPORT() {
        return REPORT;
    }

    /**
     * Set the value of REPORT
     *
     * @param REPORT new value of REPORT
     */
    public static void setREPORT(String REPORT) {
        JSONModel.REPORT = REPORT;
    }

}
