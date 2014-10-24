/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONException;
import json.JSONObject;
import json.mkJSON;
import net.mk.os.CPUInfoApp;
import net.mk.os.DiskApp;
import net.mk.os.OSApp;
import net.mk.ppmcu2D.MeshCreater3D;
import net.mk.ppmcuGUI.MCWConfClass;

/**
 *
 * @author PDI
 */
public class GlobalMCUTest {
    
    public double getPrimaryNumberCalcTimeMultiCore()
    {
    
        
        Double secs;
        int nrOfProcessors = Runtime.getRuntime().availableProcessors();
        //message=message+ "\n\nNumber of Processors found = "+nrOfProcessors;
        System.out.println("[TEST START]=====getPrimeNumbers Multiple Task Time======");
        long StartTest = new Date().getTime();
        new PrimeNumbersFinder().getPrimeNumbers(1, 15000000,10000);
        System.out.println("[TEST]                   =====END======");
        secs = new Double((new Date().getTime() - StartTest) * 0.001);
        System.out.println("[Using Multiple Cores] Prime Number calculation done in : "+secs+"sec(s)");
        return secs;
    }
     public double getPrimaryNumberCalcTimeSingleCore()
    {
    
        long StartTest = new Date().getTime();
        System.out.println("[TEST START]=====getPrimeNumbers Single Task Time======");
        new PrimeNumbersFinder().getPrimeNumbers(1, 15000000,14999999);
        System.out.println("[TEST]                   =====END======");
        Double secs = new Double((new Date().getTime() - StartTest) * 0.001);
        System.out.println("[Using Single Core] Prime Number calculation done in : "+secs+"sec(s)");
        
        return secs;
    }
    
    public JSONObject printMachineInfo(java.lang.Boolean PRINT, boolean WIN_ONLY)
    { 
        try {
            JSONObject result = new JSONObject();

            if (PRINT) {
                System.out.println("Your machine info:");
                System.out.println("PROCESSOR_IDENTIFIER :" + System.getenv("PROCESSOR_IDENTIFIER"));
                System.out.println("PROCESSOR_ARCHITECTURE :" + System.getenv("PROCESSOR_ARCHITECTURE"));
                System.out.println("PROCESSOR_ARCHITEW6432 :" + System.getenv("PROCESSOR_ARCHITEW6432"));
                System.out.println("NUMBER_OF_PROCESSORS :" + Runtime.getRuntime().availableProcessors());
                System.out.println("PCBRAND :" + System.getenv("PCBRAND"));
                System.out.println("PROCESSOR_LEVEL :" + System.getenv("PROCESSOR_LEVEL"));
                System.out.println("PROCESSOR_REVISION :" + System.getenv("PROCESSOR_REVISION"));
                System.out.println("OPERATING SYSTEM: " + System.getProperty("os.name"));
                //System.out.println("FREE MEMORY :"+(Runtime.getRuntime().freeMemory()/(8*1024*1024))+" MB");
                //System.out.println("FREE MEMORY :"+(Runtime.getRuntime().freeMemory()/(8*1024*1024))+" MB");
            }
            result.put("PROCESSOR_IDENTIFIER", System.getenv("PROCESSOR_IDENTIFIER"));
            result.put("PROCESSOR_ARCHITECTURE", System.getenv("PROCESSOR_ARCHITECTURE"));
            result.put("PROCESSOR_ARCHITEW6432", System.getenv("PROCESSOR_ARCHITEW6432"));
            result.put("PROCESSOR_LEVEL", System.getenv("PROCESSOR_LEVEL"));
            result.put("PROCESSOR_REVISION", System.getenv("PROCESSOR_REVISION"));
            result.put("NUMBER_OF_PROCESSORS", Runtime.getRuntime().availableProcessors());
            result.put("OPERATING_SYSTEM", System.getProperty("os.name"));
            result.put("PCBRAND", System.getenv("PCBRAND"));
            result.put("SHARE_COMPUTING_POWER", true);
            
            if(MCWConfClass.isUSE_SIGAR_API())
            { if(System.getProperty("os.name").toLowerCase().contains("win")| (!MCWConfClass.WEB_EXECUTION)){
            result.put("ROM", OSApp.getDiskStaticDetails(PRINT));
            result.put("RAM", OSApp.getCPUStaticDetails(PRINT));
            result.put("OS", OSApp.getOSInfo(PRINT));
            result.put("NET", OSApp.getNetStaticDetails(PRINT));
            }else{
            result.put("ROM",new JSONObject());
            result.put("RAM", new JSONObject());
            result.put("OS", new JSONObject());
            result.put("NET", new JSONObject());
            }
            }
            //Create space for Apps
            result.put("UserDevelopedApps",new JSONObject());
            result.put("UserDevelopedAppsDir",System.getProperty("user.home")+"/MCWApps");
            
            
            //mkJSON.addJSONObjects(result, CPUInfoApp.getCPUStaticDetails(PRINT));
            //mkJSON.addJSONObjects(result, DiskApp.getDiskStaticDetails(PRINT));

            InetAddress ip;
            try {
                //Get info from Web
                //            Locale l = InetAddressLocator.getLocale(request.getRemoteAddr());
                //String countryName = l.getCountry();
                //String language = l.getDisplayLanguage();
                //String host = request.getRemoteHost();
                //System.out.println("your IP address: "+InetAddressLocator.getLocale(request.getRemoteAddr()));   
                //System.out.println("your country name: "+countryName);  
                //    System.out.println("your language name: "+language);  
                //    System.out.println("your host name: "+host);
                //

                ip = InetAddress.getLocalHost();
                String computername = InetAddress.getLocalHost().getHostName();



                if (PRINT) {
                    System.out.println("Current IP address : " + ip.getHostAddress());
                    System.out.println("PC_NAME:"+computername);
                }
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);

                byte[] mac = network.getHardwareAddress();
                if (PRINT) {
                    System.out.print("Current MAC address : ");
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                if (PRINT) {
                    System.out.println(sb.toString());
                }

                result.put("PC_NAME", computername);
                result.put("IP_ADDRESS", ip.getHostAddress());
                result.put("MAC_ADDRESS", sb.toString());

            } catch (UnknownHostException e) {

                e.printStackTrace();

            } catch (SocketException e) {

                e.printStackTrace();

            }
            if (PRINT) {
                System.out.println("------------");
            }

            /**
             * More System info, if OS is Windows
             */
            return result;
        } catch (JSONException ex) {
            Logger.getLogger(GlobalMCUTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public JSONObject getPrimeNumberTestData(){
        try {
            JSONObject testinfo=printMachineInfo(true, true);
            double singleCoreTime=getPrimaryNumberCalcTimeSingleCore();
            double multiCoreTime=getPrimaryNumberCalcTimeMultiCore();
            testinfo.put("SINGLE_CORE_TIME",singleCoreTime );
            testinfo.put("MULTI_CORE_TIME",multiCoreTime);
            testinfo.put("BOOST_RATIO",roundTwoDecimals(singleCoreTime/multiCoreTime));
            testinfo.put("TEST_DATE",formatDate());
            
            System.out.println("----------------");
            System.out.println("Result: ");
            System.out.println("Single CPU, processing time: "+singleCoreTime+"secs");
            System.out.println("Multiple CPU, processing time: "+multiCoreTime+"secs");
            System.out.println("\nPerformance Boost Possible by switching to ppmcu API: " +roundTwoDecimals(singleCoreTime/multiCoreTime) +" times");
            System.out.println("----------------");

            testinfo.put("VerificationKey","Erd565jg4Vtsg" );
            
            return testinfo;
        } catch (JSONException ex) {
            Logger.getLogger(GlobalMCUTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new JSONObject();
    }
    
    public Vector getPrimeNumberTestData_(){
        try {
            JSONObject data= getPrimeNumberTestData();
            String result1[]=new String[data.length()+3];
            String result2[]=new String[data.length()+3];
            for(int i=0;i<data.length();i++){
                
                    result1[i]=data.names().getString(i);
                    result2[i]=data.getString(result1[i]);
                
            }
            //"VerificationKey","data","filename"
            result1[data.length()]="VerificationKey";
            result2[data.length()]="Erd565jg4Vtsg";
            result1[data.length()+1]="data";
            result2[data.length()+1]=data.toString();
            result1[data.length()+2]="filename";
            result2[data.length()+2]=data.getString("MAC_ADDRESS");
            
            Vector result=new Vector();
            result.add(result1);
            result.add(result2);
            
            return result;
        } catch (JSONException ex) {
            Logger.getLogger(GlobalMCUTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public double getRandomNumTimeMultiCore(){
       Double secs;
       long StartTest = new Date().getTime();
       new MeshCreater3D().getRandomLinesppmcu(1000000, new int[]{100,1}, new int[]{100,1}, new int[]{100,1});
       secs = new Double((new Date().getTime() - StartTest) * 0.001);
       System.out.println("[Using Multiple Cores] Random Number generation done in : "+secs+"sec(s)");
       return secs;
       
    }
     public double getRandomNumTimeSingleCore(){
       Double secs;
       long StartTest = new Date().getTime();
       new MeshCreater3D().getRandomLines(1000000, new int[]{100,1}, new int[]{100,1}, new int[]{100,1});
       secs = new Double((new Date().getTime() - StartTest) * 0.001);
       System.out.println("[Using Single Core] Random Number generation done in : "+secs+"sec(s)");
       return secs;
       
    }
    double roundTwoDecimals(double d) {
        	DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
}
private String formatDate(){
//    DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss.SSS Z");
//    DateFormat outputFormat = new SimpleDateFormat("EEEEE', 'MMMMM' 'dd', 'yyyy' 'h:mm:a");
//    Date date = null;
//        try {
//            date = inputFormat.parse("20110913T100702.631 GMT ");
//        } catch (ParseException ex) {
//            Logger.getLogger(GlobalMCUTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        DateFormat dateFormat = new SimpleDateFormat("EEEEE', 'MMMMM' 'dd', 'yyyy' 'h:mm:a");//new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
Date date = new Date();
//System.out.println("Test Date:"+dateFormat.format(date));
    return dateFormat.format(date);
}


public static void main(String[] args){
    
    new GlobalMCUTest().getPrimaryNumberCalcTimeMultiCore();
    
}

}
