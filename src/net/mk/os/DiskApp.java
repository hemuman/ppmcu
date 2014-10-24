/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.os;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import json.mkJSON;
import static net.mk.os.CPUInfoApp.getCPUStaticDetails;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemMap;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.NfsFileSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.shell.FileCompleter;

/**
 *
 * @author PDI
 */
public class DiskApp {
    static Sigar sigar;
     public static JSONObject getDiskStaticDetails(boolean print) {
        try {
           JSONObject result=new JSONObject();
           sigar=new Sigar();
           ArrayList sys = new ArrayList();

            //FileSystemMap mounts = sigar.getFileSystemMap();//.proxy.getFileSystemMap();
        
        
       // if (sys.size() == 0) {
            FileSystem[] fslist = sigar.getFileSystemList();
            for (int i=0; i<fslist.length; i++) {
                sys.add(fslist[i]);
            }
       // }

       // printHeader();
        for (int i=0; i<sys.size(); i++) {
            JSONObject temp= getDiskInfo((FileSystem)sys.get(i));
            result.put(temp.getString("NAME"), temp);
           
        }
        
        if(print){ 
            System.out.println("\n------------------Sotrage Info--------------------");
                   JSONArray ja=result.names();
                   mkJSON.sortJSONArray(ja);
                   for(int i=0;i<ja.length();i++){
                   System.out.println(ja.getString(i)+":"
                           +"\tT: "+ result.getJSONObject(ja.getString(i)).getString("TOTAL_SPACE_GB")+" GB"
                           +"\tA: "+ result.getJSONObject(ja.getString(i)).getString("AVAILABLE_GB")+" GB"
                           +"\tU: "+ result.getJSONObject(ja.getString(i)).getString("USED_PERCENT")
                           +"\tV: "+ result.getJSONObject(ja.getString(i)).getString("TPYE"));
                   
                   }
                 //  System.out.println("------------");
                }
         
            return result;
     } catch (JSONException ex) {
            //Logger.getLogger(CPUInfoApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SigarException ex) {
            Logger.getLogger(DiskApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new JSONObject();
    
     }   
     public static JSONObject getDiskInfo(FileSystem fs) throws SigarException {
        try {
            long used, avail, total, pct;
            JSONObject result=new JSONObject();

            try {
                FileSystemUsage usage;
                if (fs instanceof NfsFileSystem) {
                    NfsFileSystem nfs = (NfsFileSystem)fs;
                    if (!nfs.ping()) {
                        System.out.println(nfs.getUnreachableMessage());
                        return new JSONObject();
                    }
                }
                usage = sigar.getFileSystemUsage(fs.getDirName());

                    used = usage.getTotal() - usage.getFree();
                    avail = usage.getAvail();
                    total = usage.getTotal();

                    pct = (long)(usage.getUsePercent() * 100);
             //   }
            } catch (SigarException e) {
                //e.g. on win32 D:\ fails with "Device not ready"
                //if there is no cd in the drive.
                used = avail = total = pct = 0;
            }

            String usePct;
            if (pct == 0) {
                usePct = "-";
            }
            else {
                usePct = pct + "%";
            }
            
    //        ArrayList items = new ArrayList();
            DecimalFormat df = new DecimalFormat("#.##");
            result.put("NAME", fs.getDevName());
            result.put("TOTAL_SPACE_GB", df.format(total/(1024.0*1024.0)));
            result.put("USED_SPACE_GB",df.format( used/(1024.0*1024.0)));
            result.put("AVAILABLE_GB",df.format( avail/(1024.0*1024.0)));
            result.put("USED_PERCENT", usePct);
            result.put("MOUNTED_ON", fs.getDirName());
            result.put("TPYE", fs.getSysTypeName() + "/" + fs.getTypeName());
            
    //        items.add(fs.getDevName());
    //        items.add(formatSize(total));
    //        items.add(formatSize(used));
    //        items.add(formatSize(avail));
    //        items.add(usePct);
    //        items.add(fs.getDirName());
    //        items.add(fs.getSysTypeName() + "/" + fs.getTypeName());

           // printf(items);
            
            return result;
        } catch (JSONException ex) {
            Logger.getLogger(DiskApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new JSONObject();
    }

     public static void main(String[] args) {
        try {        
            // CPUInfoApp main = new CPUInfoApp();

             System.out.println(getDiskStaticDetails(true).toString(1));
             
        } catch (JSONException ex) {
            Logger.getLogger(CPUInfoApp.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
        
    }
}


