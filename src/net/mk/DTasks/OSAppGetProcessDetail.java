/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.mk.DTasks;

import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import net.mk.dc.DistributedTask;
import net.mk.os.OSApp;
import org.hyperic.sigar.SigarException;

/**
 *
 * @author PDI
 */
public class OSAppGetProcessDetail  extends DistributedTask {
    String ProcessName="";
    public OSAppGetProcessDetail (String ProcessName){
        this.ProcessName=ProcessName;
    }

    @Override
    public String compute() {
        try {
            JSONObject allProcess=OSApp.getProcesses();
            JSONArray names=allProcess.names();
            for(int i=0;i<names.length();i++){   
                if(allProcess.getJSONArray(names.getString(i)).toString().contains(ProcessName))
                    return allProcess.getJSONArray(names.getString(i)).toString();
            }
        } catch (SigarException ex) {
            Logger.getLogger(OSAppGetProcessDetail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(OSAppGetProcessDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new JSONArray().toString();
    }
   
}
