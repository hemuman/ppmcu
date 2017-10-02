package net.mk.os;

/*
 * Copyright (c) 2006 Hyperic, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import org.hyperic.sigar.SigarProxy;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarNotImplementedException;
import org.hyperic.sigar.util.PrintfFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.hyperic.sigar.cmd.Shell;
import org.hyperic.sigar.cmd.SigarCommandBase;

/**
 * Display how long the system has been running and the current load averages.
 */
public class Uptime extends SigarCommandBase {

    private static Object[] loadAvg = new Object[3];

    private static PrintfFormat formatter =
        new PrintfFormat("%.2f, %.2f, %.2f");

    public Uptime(Shell shell) {
        super(shell);
    }

    public Uptime() {
        super();
    }

    public String getUsageShort() {
        return "Display how long the system has been running";
    }

    public void output(String[] args) throws SigarException {
        System.out.println(getInfo(this.sigar));
    }

    public static String getInfo(SigarProxy sigar) throws SigarException {
        double uptime = sigar.getUptime().getUptime();

        String loadAverage;

        try {
            double[] avg = sigar.getLoadAverage();
            loadAvg[0] = new Double(avg[0]);
            loadAvg[1] = new Double(avg[1]);
            loadAvg[2] = new Double(avg[2]);

            loadAverage = "load average: " +
                formatter.sprintf(loadAvg);

        } catch (SigarNotImplementedException e) {
            loadAverage = "(load average unknown)";
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

    //pretty close to uptime command, but we don't output number of users yet
    public static void main(String[] args) throws Exception {
        
        Map<String, String> env = System.getenv();
for (String envName : env.keySet()) {
    // System.out.format("%s=%s%n", envName, env.get(envName));
}
Properties props = System.getProperties();
//props.list(System.out);

String dllPath=System.getProperty("user.home");
//String dllPath=System.getProperty("java.ext.dirs");
if(dllPath.contains(";"))
 dllPath=dllPath.substring(0, dllPath.indexOf(";"));

        //File file=new File(System.getProperty("user.home") + "\\ppmcu\\"+"sigar-amd64-winnt.dll");
        File file=new File(dllPath+"\\"+"sigar-amd64-winnt.dll");
        
        if(!file.exists())
        {
            System.out.println("#Uptime "+dllPath);
            preloadSigar preloadSigar = new  preloadSigar(dllPath);
         preloadSigar=null;
          JOptionPane.showMessageDialog(null, "Set-up complete..Rerun");
        }
    //  System.gc();
//       String s="_";
//       for(int i=0;i<100000;i++){
//           s=s+"_";
//       }
     
       //Thread.sleep(1000);
       new Uptime().processCommand(args);
    }
    
    
}
