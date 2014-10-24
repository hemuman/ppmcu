/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.os;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import json.mkJSON;
import static net.mk.os.Ps.getCpuTime;
import static net.mk.os.Ps.getInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NfsFileSystem;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcCredName;
import org.hyperic.sigar.ProcExe;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.ProcTime;
import org.hyperic.sigar.ProcUtil;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarLoader;
import org.hyperic.sigar.SigarNotImplementedException;
import org.hyperic.sigar.SigarProxy;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.util.PrintfFormat;

/**
 *
 * @author PDI
 */
public class OSApp {
   static Sigar sigar;
    static {
        sigar=new Sigar();
    }
     
     static int PRINTLEN=30;
     public static JSONObject getDiskStaticDetails(boolean print) {
        try {
           JSONObject result=new JSONObject();
          // sigar=new Sigar();
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
                   new mkJSON().sortJSONArray(ja);
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
     
     public static String getRAMInfo(){
       try {
           Mem mem   = sigar.getMem();
           DecimalFormat df = new DecimalFormat("#.##");
           return df.format(mem.getFree()/(1024.0*1024.0*1024.0))+"/"+df.format(mem.getRam()/1024.0)+" GB";
       } catch (SigarException ex) {
           Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
       }
       return "NO_INFO";
         
     }
     
     public static long  getRAM(){
       try {
           Mem mem   = sigar.getMem();
           return mem.getRam()/1024;
       } catch (SigarException ex) {
           Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
       }
       return 1;
         
     }
     
     public static int getCPUCount(){
       try {
           org.hyperic.sigar.CpuInfo[] infos = sigar.getCpuInfoList();
           org.hyperic.sigar.CpuInfo info = infos[0];
           return  infos.length;
       } catch (SigarException ex) {
           Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
       }
       return 1; 
     }
      
     public static double getCPUSpeed(){
       try {
           org.hyperic.sigar.CpuInfo[] infos = sigar.getCpuInfoList();
           org.hyperic.sigar.CpuInfo info = infos[0];
           return  ((double)info.getMhz())/1000;
       } catch (SigarException ex) {
           Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
       }
       return 0.1; 
     }

     public static JSONObject getCPUStaticDetails(boolean print){
        try {
            JSONObject result=new JSONObject();
            
           //  Sigar sigar;
             //SigarForJNLP sigar;
            org.hyperic.sigar.CpuInfo[] infos;
            CpuPerc[] cpus;
            Mem mem  ;
            Swap swap ;
            try {
                sigar = new Sigar();
                // sigar = new SigarForJNLP();
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
            result.put("RAMFREE_GB", df.format(mem.getFree()/(1024.0*1024.0*1024.0)));
            result.put("SWAP_GB", df.format((swap.getTotal() / (1024.0*1024.0*1024.0))));
            result.put("SWAPFREE_GB", df.format((swap.getFree() / (1024.0*1024.0*1024.0))));
            
            org.hyperic.sigar.CpuInfo info = infos[0];
            long cacheSize = info.getCacheSize();
            
            
            result.put("Vendor", info.getVendor());
            result.put("Model", info.getModel());
            result.put("Speed_(GHz)", ((double)info.getMhz())/1000);
            result.put("Total_CPUs", info.getTotalCores());
            result.put("CPU_UPTime", getCPUUPTimeInfo(sigar));
            
          
            if ((info.getTotalCores() != info.getTotalSockets()) ||
                (info.getCoresPerSocket() > info.getTotalCores()))
            {
                result.put("Physical_CPUs", info.getTotalSockets());
                result.put("Cores_per_CPU",info.getCoresPerSocket());
                
            }

            if (cacheSize != Sigar.FIELD_NOTIMPL) {
                result.put("Cache_size",info.getCoresPerSocket());
            }
            
            CpuPerc cpuPerc = sigar.getCpuPerc();
            JSONObject cpuPercj=new JSONObject();
           cpuPercj.put("User_Time" , df.format(cpuPerc.getUser()));
           cpuPercj.put("Sys_Time", df.format(cpuPerc.getSys()));
           cpuPercj.put("Idle_Time", df.format(cpuPerc.getIdle()));
          // cpuPercj.put("Wait_Time" , CpuPerc.format(cpuPerc.getWait()));
           //cpuPercj.put("Nice_Time" ,CpuPerc.format(cpuPerc.getNice()));
           cpuPercj.put("Usage" , df.format( cpuPerc.getCombined()));
           //cpuPercj.put("Irq_Time" , CpuPerc.format(cpuPerc.getIrq()));
           if (SigarLoader.IS_LINUX) {
               cpuPercj.put("SoftIrq Time" ,CpuPerc.format(cpuPerc.getSoftIrq()));
               cpuPercj.put("Stolen Time" , CpuPerc.format(cpuPerc.getStolen()));
           }
            result.put("CPU_Usage",cpuPercj);
        //println("");
            
             InetAddress ip=InetAddress.getLocalHost();
             NetworkInterface network = NetworkInterface.getByInetAddress(ip);

                byte[] mac = network.getHardwareAddress();
               
                 //   System.out.print("Current MAC address : ");
                
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                result.put("MAC_ID",sb.toString());

            if(print){ 
                   System.out.println("\n------------------Processing Hardware Info------------------");
                   
                   JSONArray ja=result.names();
                   for(int i=0;i<ja.length();i++){
                       
                   System.out.println(ja.getString(i)+getRepeats(" ",PRINTLEN-ja.getString(i).length())+"\t:"+ result.getString(ja.getString(i)));
                   
                   }
                //   System.out.println("------------");
                }
            return result;
     } catch (JSONException ex) {
            //Logger.getLogger(CPUInfoApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
             Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
         } catch (UnknownHostException ex) {
             Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SigarException ex) {
             Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
         }
        return new JSONObject();
    }
     public static JSONObject getOSInfo(boolean print) {
         try {
             //        try {
             //            printNativeInfo(os);
             //        } catch (UnsatisfiedLinkError e) {
             //        }
             //        }

                     JSONObject result=new JSONObject();
                     //System.out.println("Current user........" +  System.getProperty("user.name"));
                     result.put("USER",  System.getProperty("user.name"));
                    // System.out.println("");
                     
                     OperatingSystem sys = OperatingSystem.getInstance();
                     //System.out.println("OS description......" + sys.getDescription());
                     result.put("DESC",  sys.getDescription());
                     //System.out.println("OS name............." + sys.getName());
                     result.put("NAME",  sys.getName());
                     //System.out.println("OS arch............." + sys.getArch());
                     result.put("ARCH", sys.getArch());
                     //System.out.println("OS machine.........." + sys.getMachine());
                     result.put("MACHINE",  sys.getMachine());
                     //System.out.println("OS version.........." + sys.getVersion());
                     result.put("VERSION",  sys.getVersion());
                     //System.out.println("OS patch level......" + sys.getPatchLevel());
                     result.put("PATCH",  sys.getPatchLevel());
                     //System.out.println("OS vendor..........." + sys.getVendor());
                     result.put("VENDOR",  sys.getVendor());
                     //System.out.println("OS vendor version..." + sys.getVendorVersion());
                     result.put("VENDOR_VER",  sys.getVendorVersion());
                     if (sys.getVendorCodeName() != null) {
                        // System.out.println("OS code name........" + sys.getVendorCodeName());
                         result.put("CODE_NAME",  sys.getVendorCodeName());
                     }
                     //System.out.println("OS data model......." + sys.getDataModel());
                     result.put("DATA_MODEL",  sys.getDataModel());
                     //System.out.println("OS cpu endian......." + sys.getCpuEndian());
                     result.put("CPU_ENDIAN",  sys.getCpuEndian());
                     
                      InetAddress ip=InetAddress.getLocalHost();
                      result.put("IP_ADDRESS", ip.getHostAddress());
                      String computername = InetAddress.getLocalHost().getHostName();
                      result.put("PC_NAME", computername);
                      
                      


                     //System.out.println("Java vm version....." + System.getProperty("java.vm.version"));
                     
                     //System.out.println("Java vm vendor......" + System.getProperty("java.vm.vendor"));
                     //System.out.println("Java home..........." + System.getProperty("java.home"));
                     if(print){ 
                   System.out.println("\n------------------OS Info------------------");
                   
                   JSONArray ja=result.names();
                   for(int i=0;i<ja.length();i++){
                   System.out.println(ja.getString(i)+getRepeats(" ",PRINTLEN-ja.getString(i).length())+"\t:"+result.getString(ja.getString(i)));
                   
                   }
                //   System.out.println("------------");
                }
                     
                     return result;
         } catch (JSONException ex) {
             Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
         } catch (UnknownHostException ex) {
             Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
         }
         return new JSONObject();
    }
     private static String getRepeats(String chars, int Qty){
         String result="";
         for(int i=0;i<Qty;i++){
         result=result+chars;
         }
         return result;
     }

     public static JSONObject  getNetStaticDetails(boolean print){
         try {
             String[] ifNames;
              //sigar=new Sigar();
             JSONObject result=new JSONObject();
           
            
                 ifNames = sigar.getNetInterfaceList();
        
             

             for (int i=0; i<ifNames.length; i++) {
                 try {
                     JSONObject temp= getNetInfo(ifNames[i]);
                    result.put(temp.getString("NAME"), temp);
                 } catch (JSONException ex) {
                     Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
             
              if(print){ 
                 try {
                     System.out.println("\n------------------Network Hardware--------------------");
                            JSONArray ja=result.names();
                            mkJSON.sortJSONArray(ja);
                            for(int i=0;i<ja.length();i++){
                            
                                System.out.println(ja.getString(i)+":"+
                                        "\t"+ result.getJSONObject(ja.getString(i)).getString("Desc"));
                                System.out.println("\tMAC: "+ result.getJSONObject(ja.getString(i)).getString("HWaddr")
                                    +"\tIP: "+ result.getJSONObject(ja.getString(i)).getString("inet_addr")
                                    +"\tType: "+ result.getJSONObject(ja.getString(i)).getString("Link_encap"));
                            
                            }
                          //  System.out.println("------------");
                 } catch (JSONException ex) {
                     Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
                 }
                     }
             
             return result;
         } catch (SigarException ex) {
             Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
         }
         return new JSONObject();
    }

     public static JSONObject  getNetInfo(String name) throws SigarException {
         try {
             NetInterfaceConfig ifconfig =
                 sigar.getNetInterfaceConfig(name);
             long flags = ifconfig.getFlags();
             JSONObject result=new JSONObject();
             String hwaddr = "";
             if (!NetFlags.NULL_HWADDR.equals(ifconfig.getHwaddr())) {
                 hwaddr = " HWaddr " + ifconfig.getHwaddr();
                 result.put("HWaddr",  ifconfig.getHwaddr());
             }else result.put("HWaddr",  "NULL_HWADDR");

             if (!ifconfig.getName().equals(ifconfig.getDescription())) {
                // println(ifconfig.getDescription());
                 result.put("Desc", ifconfig.getDescription());
             }

             //println(ifconfig.getName() + "\t" + "Link encap:" + ifconfig.getType() +  hwaddr);
             result.put("NAME", ifconfig.getName());
             result.put("Link_encap", ifconfig.getType());

             
             result.put("inet_addr", ifconfig.getAddress());
              result.put("Mask", ifconfig.getNetmask());
              
              return result;
         } catch (JSONException ex) {
             Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
         }
         return new JSONObject(); 
    }

     public static String MACAddress(String OfLocalIP){
         try {
             NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getByName(OfLocalIP));
     
             InetAddress inet = InetAddress.getByName(OfLocalIP);
             try {
                 System.out.println("Is Up =  "+inet.isReachable(3000));
                 System.out.println("getCanonicalHostName =  "+inet.getCanonicalHostName());
                 System.out.println("isAnyLocalAddress =  "+inet.isAnyLocalAddress());
                 System.out.println("isLinkLocalAddress =  "+inet.isLinkLocalAddress());
                 System.out.println("isMCGlobal =  "+inet.isMCGlobal());
                 System.out.println("isMulticastAddress =  "+inet.isMulticastAddress());
                 System.out.println("isSiteLocalAddress =  "+inet.isSiteLocalAddress());
                 
             } catch (IOException ex) {
                 Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
             }
              
             
                    byte[] mac = network.getHardwareAddress();
     
                    System.out.print("Current MAC address : ");
     
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
                    }
                    //System.out.println(sb.toString());
                    return sb.toString();
         } catch (SocketException | NullPointerException |UnknownHostException ex) {
           
             Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
               return "NO:TF:OU:ND";
         } 
       
     
     }
     
     public static String getMACAddress(boolean debug){
         try {
             NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
                    byte[] mac = network.getHardwareAddress();
     
                    if(debug)
                    System.out.print("Current MAC address : ");
     
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
                    }
                    if(debug)
                    System.out.println(sb.toString());
                    return sb.toString();
         } catch (SocketException | NullPointerException |UnknownHostException ex) {
           
             Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
               return "NO:TF:OU:ND";
         } 
       
     
     }
     
     private static Long format(long value) {
        return new Long(value / 1024);
    }

      public static String getCPUUPTimeInfo(SigarProxy sigar) throws SigarException {
        double uptime = sigar.getUptime().getUptime();
        Object[] loadAvg = new Object[3];
        String loadAverage;
        PrintfFormat formatter =
        new PrintfFormat("%.2f, %.2f, %.2f");
        try {
            double[] avg = sigar.getLoadAverage();
            loadAvg[0] = new Double(avg[0]);
            loadAvg[1] = new Double(avg[1]);
            loadAvg[2] = new Double(avg[2]);
            loadAverage = "load average: " +
                formatter.sprintf(loadAvg);

        } catch (SigarNotImplementedException e) {
            loadAverage = "(...)";
        }

        return
            "  " + getCurrentTime() + 
            "  up " + formatUptime(uptime) +
            ", " + loadAverage;
    }

    private static String formatUptime(double uptime) {
        String retval = "";

        int days = (int)uptime / (60*60*24);
        int minutes, hours;

        if (days != 0) {
            retval += days + " " + ((days > 1) ? "days" : "day") + ", ";
        }

        minutes = (int)uptime / 60;
        hours = minutes / 60;
        hours %= 24;
        minutes %= 60;

        if (hours != 0) {
            retval += hours + ":" + minutes;
        }
        else {
            retval += minutes + " min";
        }

        return retval;
    }

    private static String getCurrentTime() {
        return new SimpleDateFormat("h:mm a").format(new Date());
    }
     
    /**
     * Returns available CPUs, i.e. Idle time.
     * @return 
     */
    public static double getAvailableCPU(){
        try {
            
            CpuPerc cpuPerc = sigar.getCpuPerc();
            return cpuPerc.getIdle();
            
        } catch (SigarException ex) {
            Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    
    }
    
     public  static JSONObject getProcesses() throws SigarException {
        //sigar=new Sigar();
        long[] pids;
        JSONArray temp=new JSONArray();
        JSONObject result=new JSONObject();
         pids = sigar.getProcList();
        for (int i=0; i<pids.length; i++) {
            long pid = pids[i];
            try {
                temp=output(pid);
                result.put(temp.getString(0),temp );
                
            } catch (SigarException e) {
                System.err.println("Exception getting process info for " +
                                 pid + ": " + e.getMessage());
            } catch (JSONException ex) {
                Logger.getLogger(Ps.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

     public static int getProcessCount(){
       try {
           return sigar.getProcList().length;
       } catch (SigarException ex) {
           Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
       }
       return -1;
     }
    public static JSONArray join(List info) {
        StringBuffer buf = new StringBuffer();
        Iterator i = info.iterator();
        boolean hasNext = i.hasNext();
        JSONArray result=new JSONArray();
        while (hasNext) {
            //buf.append((String)i.next());
            result.put((String)i.next());
            hasNext = i.hasNext();
            if (hasNext)
                buf.append("\t");
        }

        return result;
    }

    public static final String[] ProcessColumns = {"PID", "User", "Start Date", 
        "Assigned Memory", "Used Memory", "-", "-", "UP Time",
        "Process Name","Threads","Priority","Processor","Tty"};
    public static List getInfo(SigarProxy sigar, long pid)
        throws SigarException {

        ProcState state = sigar.getProcState(pid);
        ProcTime time = null;
        String unknown = "???";
        
      ProcCpu procCpu=sigar.getProcCpu(pid);
      
      ProcExe procExe=sigar.getProcExe(pid);
     
        

        List info = new ArrayList();
        info.add(String.valueOf(pid));

        try {
            ProcCredName cred = sigar.getProcCredName(pid);
            info.add(cred.getUser());
        } catch (SigarException e) {
            info.add(unknown);
        }

        try {
            time = sigar.getProcTime(pid);
            info.add(getStartTime(time.getStartTime()));
        } catch (SigarException e) {
            info.add(unknown);
        }

        try {
            ProcMem mem = sigar.getProcMem(pid);
            info.add(Sigar.formatSize(mem.getSize()));
            info.add(Sigar.formatSize(mem.getRss()));
            info.add(Sigar.formatSize(mem.getShare()));
        } catch (SigarException e) {
            info.add(unknown);
        }

        info.add(String.valueOf(state.getState()));

        if (time != null) {
            info.add(getCpuTime(time));
        }
        else {
            info.add(unknown);
        }

        String name = ProcUtil.getDescription(sigar, pid);
        info.add(name);
        
        info.add(String.valueOf(state.getThreads()));
        info.add(String.valueOf(state.getPriority()));
        info.add(String.valueOf(state.getProcessor()));
        info.add(String.valueOf(state.getTty()));

        return info;
    }

    public static JSONArray output(long pid) throws SigarException {
        return join(getInfo(sigar, pid));
    }

    public static String getCpuTime(long total) {
        long t = total / 1000;
        return t/60 + ":" + t%60;
    }

    public static String getCpuTime(ProcTime time) {
        return getCpuTime(time.getTotal());
    }

    private static String getStartTime(long time) {
        if (time == 0) {
            return "00:00";
        }
        long timeNow = System.currentTimeMillis();
        String fmt = "MMMd";

        if ((timeNow - time) < ((60*60*24) * 1000)) {
            fmt = "HH:mm";
        }

        return new SimpleDateFormat(fmt).format(new Date(time));
    }
     public static void main(String vals[]){
         MACAddress("10.29.246.137");
         getMACAddress(true);
     }

     
     public static String getPCName(){
       try {
           InetAddress  ip = InetAddress.getLocalHost();
                    String computername = InetAddress.getLocalHost().getHostName();

    return computername;
       } catch (UnknownHostException ex) {
           Logger.getLogger(OSApp.class.getName()).log(Level.SEVERE, null, ex);
       }
       return "NoNAME";
     }
}
