/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.DTasks;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.mk.dc.DistributedTask;

/**
 *
 * @author PDI
 */
public class DTaskGetPCName extends DistributedTask {

    @Override
    public String compute() {
        try {
            InetAddress ip;
                   ip = InetAddress.getLocalHost();
                   String computername = InetAddress.getLocalHost().getHostName();
           return computername;
        } catch (UnknownHostException ex) {
            Logger.getLogger(DTaskGetPCName.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "NotFound";
    }
    
}
