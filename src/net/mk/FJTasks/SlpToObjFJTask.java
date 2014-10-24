/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.FJTasks;

import java.util.concurrent.RecursiveTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONArray;
import json.JSONException;
import json.mkJSON;

/**
 *
 * @author PDI
 */
public class SlpToObjFJTask extends RecursiveTask{
    String[] DataToConvert;
    
    public SlpToObjFJTask(String[] DataToConvert){
    
        this.DataToConvert=DataToConvert;
    }

    @Override
    protected String[] compute() {
        try {
            JSONArray tempResult=new JSONArray();
            int face_num=0;
            System.out.println("#SlpToObjFJTask "+DataToConvert.length);
             for(int i=0;i<DataToConvert.length;i++){
                 String temp_ =DataToConvert[i];
                 String[] temp;
                 if (temp_.contains("normal")) {
                                   temp = temp_.split(" ");
                                   tempResult.put( "\nvn " + temp[temp.length - 3] + " " + temp[temp.length - 2] + " " + temp[temp.length - 1]);
                                   continue;
                               }
                               if (temp_.contains("vertex")) {
                                   temp = temp_.split(" ");
                                   tempResult.put("\nv " + temp[temp.length - 3] + " " + temp[temp.length - 2] + " " + temp[temp.length - 1]);

                                   continue;
                               }
                               if (temp_.contains("endfacet")) {
                                   tempResult.put( "\nf " + (face_num++) + " " + (face_num++) + " " + (face_num++));
                               }
             }
             return mkJSON.getStringArrayFromJSONArray(tempResult);
        } catch (JSONException ex) {
            Logger.getLogger(SlpToObjFJTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new String[1];
        
    }
    
}
