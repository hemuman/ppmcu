/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.os;

/**
 *
 * @author PDI
 */
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

//declare the CPUInfo class
public class CPUInfoApp {

    /**
     * declare a constructor for CPUINFO
     *     
* Remember that a constructor normally is meant to set the class up...in this case we are bending the rules just to make the example clearer. Really the
     * constructor should be empty and we should be doing this in a method.
     */
    public CPUInfoApp() {
// declare the Sigar class (we need this because it gathers the
// statistics)
        Sigar sigar = new Sigar();

// the output string is just going to hold our output string
        String output = "";

// an array of CPU Info classes. The CPUInfo class is a blank data
// holder class. Just like string it can hold any data.
        CpuInfo[] cpuInfoList = null;

// the try catch block means that if we get an error we are notified
        try {
// get the CPU information from the sigar library
            cpuInfoList = sigar.getCpuInfoList();

// if something foes wrong
        } catch (SigarException e) {
// write a description of the problem to the output
            e.printStackTrace();

// exit the constructor
            return;
        }

// for each item in the cpu info array
        for (CpuInfo info : cpuInfoList) {
// add the data to the output ( output += "something" means add
// "something" to the end of output)
            output += "\nCPU\n";
            output += "Vendor: " + info.getVendor() + "\n";
            output += "Clock: " + info.getMhz() + "Mhz\n";
            output += "Model: " + info.getModel() + "\n";
            output += "Cores: " + info.getCoresPerSocket()+ "\n";
            output += "Sockets: " + info.getTotalSockets()+ "\n";
        }

// finally, print the data to the output
        System.out.println(output);
    }

    public static JSONObject getCPUStaticDetails(boolean print){
        try {
            JSONObject result=new JSONObject();
            
           //  Sigar sigar;
             SigarForJNLP sigar;
            CpuInfo[] infos;
            CpuPerc[] cpus;
            Mem mem  ;
            Swap swap ;
            try {
                //sigar = new Sigar();
                 sigar = new SigarForJNLP();
                infos = sigar.getCpuInfoList();
                cpus =  sigar.getCpuPercList();
                mem   = sigar.getMem();
                swap = sigar.getSwap();
            } catch (SigarException ex) {
                 throw new RuntimeException("NewException");
              //  Logger.getLogger(CPUInfoApp.class.getName()).log(Level.SEVERE, null, ex);
            }

            //result.put("TOTAL_RAM", mem.getTotal());
               DecimalFormat df = new DecimalFormat("#.##");
            result.put("RAM_GB", df.format(mem.getRam()/1024.0));
            result.put("SWAP_GB", df.format((swap.getTotal() / (1024.0*1024.0*1024.0))));

            org.hyperic.sigar.CpuInfo info = infos[0];
            long cacheSize = info.getCacheSize();
            
            
            result.put("Vendor", info.getVendor());
            result.put("Model", info.getModel());
            result.put("Speed_(GHz)", ((double)info.getMhz())/1000);
            result.put("Total_CPUs", info.getTotalCores());
          
            if ((info.getTotalCores() != info.getTotalSockets()) ||
                (info.getCoresPerSocket() > info.getTotalCores()))
            {
                result.put("Physical_CPUs", info.getTotalSockets());
                result.put("Cores_per_CPU",info.getCoresPerSocket());
                
            }

            if (cacheSize != Sigar.FIELD_NOTIMPL) {
                result.put("Cache_size",info.getCoresPerSocket());
                
            }

            if(print){ 
                   System.out.println("\n------------------Processing Hardware Info------------------");
                   
                   JSONArray ja=result.names();
                   for(int i=0;i<ja.length();i++){
                   System.out.println(ja.getString(i)+":"+ result.getString(ja.getString(i)));
                   
                   }
                //   System.out.println("------------");
                }
            return result;
     } catch (JSONException ex) {
            //Logger.getLogger(CPUInfoApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new JSONObject();
    }
    public static void main(String[] args) {
        try {        
            // CPUInfoApp main = new CPUInfoApp();

             System.out.println(getCPUStaticDetails(false).toString(1));
        } catch (JSONException ex) {
            Logger.getLogger(CPUInfoApp.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
}
