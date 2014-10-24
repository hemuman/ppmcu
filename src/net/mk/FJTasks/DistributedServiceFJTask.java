/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.FJTasks;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONException;
import json.JSONObject;
import net.mk.dc.DistributedTask;
import net.mk.dc.DistributedTaskService;
import static net.mk.dc.DistributedTaskService.LICENSE_ACCEPTED;
import net.mk.ppmcuGUI.MCWConfClass;

/**
 *
 * @author PDI
 */
public class DistributedServiceFJTask   extends RecursiveTask  {

    String LICENSE_KEY;
    DistributedTask DISTRIBUTED_TASK;
    JSONObject RemoteSysSpec;
    InetAddress host;
    int _portNumber = 5559; //Arbitrary port number
    
    public  DistributedServiceFJTask(String LICENSE_KEY,JSONObject RemoteSysSpec,int _portNumber,DistributedTask DISTRIBUTED_TASK){
      
        if(MCWConfClass.isACTIVATE_DISTRIBUTED_COMPUTING_NODE()){
        this.LICENSE_KEY=LICENSE_KEY;
        this.RemoteSysSpec=RemoteSysSpec;
        this._portNumber=_portNumber;
        this.DISTRIBUTED_TASK=DISTRIBUTED_TASK;
        try {
            System.out.println("#RemoteSysSpec.getString(\"IP_ADDRESS\")"+RemoteSysSpec.getString("IP_ADDRESS"));
            host=InetAddress.getByName(RemoteSysSpec.getString("IP_ADDRESS"));
           // host = InetAddress.getLocalHost();
        } catch (JSONException ex) {
            Logger.getLogger(DistributedTaskService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(DistributedTaskService.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
    }

    public  DistributedServiceFJTask(String LICENSE_KEY,JSONObject RemoteSysSpec,int _portNumber){
      
        if(MCWConfClass.isACTIVATE_DISTRIBUTED_COMPUTING_NODE()){
        this.LICENSE_KEY=LICENSE_KEY;
        this.RemoteSysSpec=RemoteSysSpec;
        this._portNumber=_portNumber;
        try {
            //System.out.println("#RemoteSysSpec.getString(\"IP_ADDRESS\")"+RemoteSysSpec.getString("IP_ADDRESS"));
            host=InetAddress.getByName(RemoteSysSpec.getString("IP_ADDRESS"));
           // host = InetAddress.getLocalHost();
        } catch (JSONException ex) {
            Logger.getLogger(DistributedTaskService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(DistributedTaskService.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        }
        
    }

    @Override
    protected Object compute() {
            try {
                return new DistributedTaskService(LICENSE_KEY,RemoteSysSpec,_portNumber,DISTRIBUTED_TASK).execute();
            } catch (IOException ex) {
                Logger.getLogger(DistributedServiceFJTask.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        
    }
    
}
