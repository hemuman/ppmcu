/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.DTasks;

import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONObject;
import net.mk.dc.DistributedTask;
import net.mk.os.OSApp;
import net.mk.ppmcu.GlobalMCUTest;
import org.hyperic.sigar.SigarException;

/**
 *
 * @author PDI
 */
public class GetProcDCTask extends DistributedTask{

    @Override
    public String compute() {
        try {
            return new OSApp().getProcesses().toString();
        } catch (SigarException ex) {
            Logger.getLogger(GetProcDCTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new JSONObject().toString();
        
    }
    
}
