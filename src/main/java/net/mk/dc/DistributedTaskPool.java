/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dc;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import net.mk.DTasks.IsDivisibleByNumberDTask;
import net.mk.DTasks.OSAppAsDCTask;
import net.mk.dcApps.DCGUI;
import net.mk.dcApps.SolSys.SolarSysGUI;
import net.mk.dcApps.CheckPrime.VerifyPrimeNumberGUI;
import net.mk.ppmcuGUI.MCWConfClass;
import net.mk.ppmcuGUI.FrontPage;

/**
 *
 * @author PDI
 */
public class DistributedTaskPool {

    static String LICENSE_KEY;
    DistributedTask DISTRIBUTED_TASK;
    static DistributedTask[] DISTRIBUTED_TASKS;
    JSONObject RemoteSysSpec;
    JSONArray JOBPOOL;
    static int portNumber = 5559; //Arbitrary LOCAL_IP number
    public static Object[] COLLECTED_RESULTS;
    public static int TRACKER[];
    int COUNT;
    public int NumberOfTasksFinished=0;
    public int TotalNumberOfTask;
    static public JSONObject[] RemoteSysSpecs;
    private boolean POOL_IS_DEFINED = false;
    static ExecutorService es;

    public DistributedTaskPool(String LICENSE_KEY, int TaskQty) {

        if (MCWConfClass.isACTIVATE_DISTRIBUTED_COMPUTING_NODE()) {
            this.LICENSE_KEY = LICENSE_KEY;
            COLLECTED_RESULTS = new Object[TaskQty];
            this.TotalNumberOfTask = TaskQty;
        } else {
            System.out.println("#DTP No License found");
        }
    }

    /**
     * Usage:
     *  1. Call this constructor
        2. Call Isubmit to submit the tasks
        3. Call DTaskQeueProcess to distribute it to different machines and finish it all.
        Note: Result collection is present in COLLECTED_RESULTS
     * @param LICENSE_KEY
     * @param TaskQty
     * @param RemoteSysSpecs 
     */
    public DistributedTaskPool(String LICENSE_KEY, int TaskQty, JSONObject[] RemoteSysSpecs) {
        if (MCWConfClass.isACTIVATE_DISTRIBUTED_COMPUTING_NODE()) {
            this.LICENSE_KEY = LICENSE_KEY;
            DISTRIBUTED_TASKS= new DistributedTask[TaskQty];
            COLLECTED_RESULTS = new Object[TaskQty];
            TRACKER=new int[TaskQty];
            JOBPOOL=new JSONArray();
            this.TotalNumberOfTask = TaskQty;
            DistributedTaskPool.RemoteSysSpecs = RemoteSysSpecs;
            POOL_IS_DEFINED = true;//Set the pool defined parameter true
            es = Executors.newCachedThreadPool();
            
            //Put the variable to track resource availablity
            for(int i=0;i<DistributedTaskPool.RemoteSysSpecs.length;i++){
                try {
                    DistributedTaskPool.RemoteSysSpecs[i].put("IS_AVAILABLE", true);
                } catch (JSONException ex) {
                    Logger.getLogger(DistributedTaskPool.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("#DTP No License found");
        }
    }

    public void submit(JSONObject _RemoteSysSpec, int _portNumber, DistributedTask _DISTRIBUTED_TASK, int Count) {
        this.RemoteSysSpec = _RemoteSysSpec;
        this.portNumber = _portNumber;
        this.DISTRIBUTED_TASK = _DISTRIBUTED_TASK;
        this.COUNT = Count;
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    COLLECTED_RESULTS[COUNT] = new DistributedTaskService(LICENSE_KEY, RemoteSysSpec, portNumber, DISTRIBUTED_TASK).execute();
                    NumberOfTasksFinished++;
                } catch (IOException ex) {
                    Logger.getLogger(FrontPage.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(FrontPage.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        t2.start();
    }

    /**
     * Perform intelligent task assignment
     * @param _portNumber
     * @param _DISTRIBUTED_TASK
     * @param Count 
     */
    public void Isubmit(int _portNumber, DistributedTask _DISTRIBUTED_TASK, int Count) {
        this.portNumber = _portNumber;
        this.DISTRIBUTED_TASK = _DISTRIBUTED_TASK;
        DISTRIBUTED_TASKS[Count]=_DISTRIBUTED_TASK;
    }
    
    /**
     * Tested and works well
     * @param es
     * @param RemoteSysSpec1
     * @param _DISTRIBUTED_TASK
     * @return 
     */
    private  Future<Object> DTaskQeueItem(final ExecutorService es, final JSONObject RemoteSysSpec1, final DistributedTask _DISTRIBUTED_TASK) {
        return es.submit(new Callable<Object>() {
            @Override
            public Object call() {
                try {
                    //Check if connection is alive 
                    if (new DistributedTaskService(LICENSE_KEY, RemoteSysSpec1, 5559).isServerLive(1000) == DistributedTaskService.READY_FOR_COMPUTING) {
                        //RemoteSysSpecs[i].put("IS_AVAILABLE", true);
                        Object result=new DistributedTaskService(LICENSE_KEY, RemoteSysSpec1, portNumber, _DISTRIBUTED_TASK).execute();
                        RemoteSysSpec1.put("IS_AVAILABLE", true);
                        System.out.println("#DistributedTaskPool "+RemoteSysSpec1.getString("IP_ADDRESS")+" Finished Task "+NumberOfTasksFinished++);
                        return result;

                    }
                    RemoteSysSpec1.put("IS_ALIVE", false); 
                    return null;//On unsuccessful attempt
                } catch (Exception ex) {
                    return null;//On unsuccessful attempt
                }
            }
        });
    }

    
    /**
 * Tested and works well.
 * @return true if all the tasks get executed, else false. The COLLECTED_RESULTS is stored in public COLLECTED_RESULTS[]
 TODO : Handle the faults i.e. what if a machine goes offline
 TODO : Functionality to add and remove computing resources dynamically to handle dynamic addition of resources.
 */
    public Boolean DTaskQeueProcess()  {
        final List<Future<Object>> futures = new ArrayList<>();
        
          for(int l=0;l<DISTRIBUTED_TASKS.length;l++)
        {
              int thisCount=-1;
                if(thisCount<0) //i.e. Not initiated already
                if(COUNT<DISTRIBUTED_TASKS.length)thisCount=COUNT; //Set if it is first entry only
                COUNT++;
                    boolean SCHEDULED=false;
                    while(!SCHEDULED)//Try scheduling the task until it is scheduled for execution
                    { for(int i=0;i<RemoteSysSpecs.length;i++){ //Loop through to find available resource for computation
                        try {
                            // System.out.println("#DistributedTaskPool.Isubmit..Submitting Task"+NumberOfTasksFinished);
                             if(RemoteSysSpecs[i].getBoolean("IS_AVAILABLE")) //Check if it is available for computing
                             {   RemoteSysSpecs[i].put("IS_AVAILABLE", false);
                                 
                                 System.out.println("#DistributedTaskPool.Isubmit..Submitted Task"+thisCount+" To"+RemoteSysSpecs[i]);
                                 DISTRIBUTED_TASK=DISTRIBUTED_TASKS[thisCount];
                                 
                                 //Submit the task to valid machine
                                 futures.add(DTaskQeueItem(es, RemoteSysSpecs[i], DISTRIBUTED_TASK));
                                 SCHEDULED=true;
                                 
                                 //System.out.println("#DistributedTaskPool.Isubmit..Finished Task"+thisCount);
                                
                                 break; //Break the loop as this is task is scheduled for execution.
                             }
                        } catch (JSONException ex) {
                            Logger.getLogger(DistributedTaskPool.class.getName()).log(Level.SEVERE, null, ex);
                        }
                            
                     }
                      //System.out.println("#DistributedTaskPool.Isubmit..Task "+NumberOfTasksFinished +" SCHEDULED= "+SCHEDULED);
                    }
       
        }

        es.shutdown();
        int openPorts = 0;
        for (final Future<Object> f : futures) {
            try {
                if (f.get() != null) {
                    COLLECTED_RESULTS[openPorts]=f.get();
                    openPorts++;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(DistributedTaskPool.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(DistributedTaskPool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Collect last COLLECTED_RESULTS
        //result[openPorts]=f.get();
          
          if((openPorts )<DISTRIBUTED_TASKS.length)
          {
              System.out.println("Results Collected : " +(openPorts -1));
              
              return DTaskQeueProcess(); //Process everything  again , Ankur's suggestion 6/18/13, Yet to be tested.
          }
          System.out.println("Results Collected : " +(openPorts -1));
        return true;
    }
    
    public boolean AllTaskCompleted(){
        
        for(int l=0;l<DISTRIBUTED_TASKS.length;l++)
        {
            
            //COUNT
            es.execute(new Runnable() {
            public void run() {
                try {
                    
                    
                    int thisCount=-1;
                         if(thisCount<0) //i.e. Not initiated already
                         if(COUNT<DISTRIBUTED_TASKS.length)thisCount=COUNT; //Set if it is first entry only
                         COUNT++;

                    boolean SCHEDULED=false;
                    while(!SCHEDULED)//Try untill it is scheduled
                    { for(int i=0;i<RemoteSysSpecs.length;i++){
                        // System.out.println("#DistributedTaskPool.Isubmit..Submitting Task"+NumberOfTasksFinished);
                         if(RemoteSysSpecs[i].getBoolean("IS_AVAILABLE"))
                         {   RemoteSysSpecs[i].put("IS_AVAILABLE", false);
                             
                             System.out.println("#DistributedTaskPool.Isubmit..Submitted Task"+thisCount+" To"+RemoteSysSpecs[i]);
                             DISTRIBUTED_TASK=DISTRIBUTED_TASKS[thisCount];
                             COLLECTED_RESULTS[thisCount] = new DistributedTaskService(LICENSE_KEY, RemoteSysSpecs[i], portNumber, DISTRIBUTED_TASK).execute();
                             
                             SCHEDULED=true;
                             RemoteSysSpecs[i].put("IS_AVAILABLE", true);
                             System.out.println("#DistributedTaskPool.Isubmit..Finished Task"+thisCount);
                            
                             break;
                         }
                     }
                       // System.out.println("#DistributedTaskPool.Isubmit..Task "+NumberOfTasksFinished +" SCHEDULED= "+SCHEDULED);
                    }
                    
                } catch (IOException ex) {
                    Logger.getLogger(FrontPage.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(FrontPage.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        }
        
        boolean finshed =false;
         try {
            System.out.println("#DistributedTaskPool Isubmit() Time-out set as much as an year for result collection");
            es.shutdown();
            finshed = es.awaitTermination(365, TimeUnit.DAYS);//Wait for a day.
            System.out.println("#DistributedTaskPool Isubmit() Results stored in DistributedTaskPool.result[i]");
            //DistributedTaskPool
        } catch (InterruptedException ex) {
            Logger.getLogger(DistributedTaskPool.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         return finshed;
    }
    /**
     * Scan for IPs active for Distributed Computing
     *
     * @return
     */
    static JSONObject RemoteSysSpec1;
    static String IPV4_Firts3Nums;
    static int IP_INCREMENT = 0;
    static int REMOTE_MACHINES_ACTIVE_COUNT = 0;
    static JSONArray REMOTE_MACHINES_IP;

    public static String[] ScanNetwork(String _IPV4_Firts3Nums) {
        long begTest = new java.util.Date().getTime();
        Double secs;
        RemoteSysSpec1 = new JSONObject();
        REMOTE_MACHINES_IP = new JSONArray();
        IPV4_Firts3Nums = _IPV4_Firts3Nums;
        try {
            //RemoteSysSpec1.put("IP_ADDRESS","greenmonster.local");
            RemoteSysSpec1.put("IP_ADDRESS", IPV4_Firts3Nums + ".1");
        } catch (JSONException ex) {
            Logger.getLogger(DistributedTaskPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 1; i < 254; i++) {
            IP_INCREMENT = i;
            try {
                RemoteSysSpec1.put("IP_ADDRESS", IPV4_Firts3Nums + "." + IP_INCREMENT);
            } catch (JSONException ex) {
                Logger.getLogger(DistributedTaskPool.class.getName()).log(Level.SEVERE, null, ex);
            }

            es.execute(new Runnable() {
                public void run() {
                    try {

                        if (new DistributedTaskService(LIC_KEY, RemoteSysSpec1, 5559).isServerLive(1000) == DistributedTaskService.READY_FOR_COMPUTING) {
                            System.out.println("#ScanNetwork..Found: " + RemoteSysSpec1.getString("IP_ADDRESS"));
                            REMOTE_MACHINES_ACTIVE_COUNT++;
                            REMOTE_MACHINES_IP.put(IPV4_Firts3Nums + "." + IP_INCREMENT);
                        }
                    } catch (JSONException ex) {
                        Logger.getLogger(DistributedTaskPool.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(DistributedTaskPool.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });

        }

        secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("Task Submitted in: " + secs + " secs");
        es.shutdown();
        try {
            boolean finshed = es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException ex) {
            Logger.getLogger(DistributedTaskPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        // all tasks have finished or the time has been reached.
        secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("Task Finished in: " + secs + " secs");
        try {
            System.out.println("Active machines found: " + REMOTE_MACHINES_IP.length() + "\n List: \n" + REMOTE_MACHINES_IP.toString(1));

            /**
             * Now prepare the queue
             */
            RemoteSysSpecs = new JSONObject[REMOTE_MACHINES_IP.length()];
            for (int i = 0; i < RemoteSysSpecs.length; i++) {
                RemoteSysSpecs[i] = new JSONObject();
                RemoteSysSpecs[i].put("IP_ADDRESS", REMOTE_MACHINES_IP.getString(i));
            }

            //REMOTE_MACHINES_IP
        } catch (JSONException ex) {
            Logger.getLogger(DistributedTaskPool.class.getName()).log(Level.SEVERE, null, ex);
        }



        return null;
    }

    public static Future<JSONObject> portIsOpen(final ExecutorService es, final JSONObject RemoteSysSpec1, final int timeout) {
        return es.submit(new Callable<JSONObject>() {
            @Override
            public JSONObject call() {
                try {
                    //Socket socket = new Socket();
                    // socket.connect(new InetSocketAddress(ip, port), timeout);
                    //socket.close();
                    InetAddress ia = InetAddress.getByName(RemoteSysSpec1.getString("IP_ADDRESS"));

                    // if(ia.isReachable(timeout))
                    if (new DistributedTaskService(LIC_KEY, RemoteSysSpec1, 5559).isServerLive(1000) == DistributedTaskService.READY_FOR_COMPUTING) {

                        return RemoteSysSpec1;

                    }

                    return null;
                } catch (Exception ex) {
                    return null;
                }
            }
        });
    }

    public static JSONObject[] ScanLocalNetwork(String _IPV4_Firts3Nums) throws InterruptedException, ExecutionException {

        final ExecutorService es = Executors.newFixedThreadPool(20);
        final String ip = "127.0.0.1";
        final int timeout = 1000;
        final List<Future<JSONObject>> futures = new ArrayList<>();
        for (int LOCAL_IP = 1; LOCAL_IP <= 254; LOCAL_IP++) {
            JSONObject RemoteSysSpecstemp = new JSONObject();
            try {
                RemoteSysSpecstemp.put("IP_ADDRESS", _IPV4_Firts3Nums + "." + LOCAL_IP);
            } catch (JSONException ex) {
                Logger.getLogger(DistributedTaskPool.class.getName()).log(Level.SEVERE, null, ex);
            }
            futures.add(portIsOpen(es, RemoteSysSpecstemp, timeout));
        }
        es.shutdown();
        int openPorts = 0;
        for (final Future<JSONObject> f : futures) {
            if (f.get() != null) {
                openPorts++;
            }
        }

        //Re-assign the data to list of active machines
        RemoteSysSpecs = new JSONObject[openPorts];

        openPorts = 0;
        for (final Future<JSONObject> f : futures) {
            if (f.get() != null) {
                RemoteSysSpecs[openPorts] = f.get();
                try {
                    RemoteSysSpecs[openPorts].put("SKIP", false);
                } catch (JSONException ex) {
                    Logger.getLogger(DistributedTaskPool.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("#ScanLocalNetwork " + f.get());
                openPorts++;
            }
        }

        System.out.println("There are " + openPorts + " open ports on host " + ip + " (probed with a timeout of " + timeout + "ms)");
        return RemoteSysSpecs;
    }
    /**
     * final ExecutorService es = Executors.newFixedThreadPool(20); final String ip = "127.0.0.1"; final int timeout = 200; final List<Future<Boolean>> futures
     * = new ArrayList<>(); for (int LOCAL_IP = 1; LOCAL_IP <= 65535; LOCAL_IP++) { futures.add(portIsOpen(es, ip, LOCAL_IP, timeout)); } es.shutdown(); int
     * openPorts = 0; for (final Future<Boolean> f : futures) { if (f.get()) { openPorts++; } } System.out.println("There are " + openPorts + " open ports on
     * host " + ip + " (probed with a timeout of " + timeout + "ms)");
     *
     */
    public static final int RAM_BASED = 1;
    public static final int AVAILABLE_RAM_BASED = 2;
    public static final int PROCESSOR_SPEED_BASED = 3;
    public static final int AVAILABLE_PROCESSOR_BASED = 4;
    public static final int HDD_SPACE_BASED = 5;
    public static final int AVAILABLE_HDD_SPACE_BASED = 6;
    public static final int NUMBER_OF_PROCESSOR_BASED = 7;
    public static final int TOTAL_RAM = 11;
    public static final int PROCESSOR_SPEED = 33;
    public static final int TOTAL_HDD_SPACE = 55;
    public static final int TOTAL_NUMBER_OF_PROCESSOR = 77;

    protected String getFastestMachine(int BASED_ON) {

        if ((RemoteSysSpecs != null) & (RemoteSysSpecs.length > 0))//Check if it is initiated
        {
            double[][] ArrayOfData = new double[RemoteSysSpecs.length][2];
            for (int i = 0; i < RemoteSysSpecs.length; i++) {
                try {
                    new DistributedTaskService(LICENSE_KEY, RemoteSysSpec, portNumber, new OSAppAsDCTask(BASED_ON)).execute();
                } catch (IOException ex) {
                    Logger.getLogger(DistributedTaskPool.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } else {
            System.out.println("Node list not created.\n Refer to API Instructions.");
        }

        return null;
    }
    static JSONObject[] newJOb;
    static int SpecCount = 0;
    static String LIC_KEY = "AllUsers";
    static BigInteger IsPrimeNumber = new BigInteger("10000256589321");
    static BigInteger Interval;

    public static void main(String[] args) throws ExecutionException {
        try {

            //ScanNetwork("192.168.1");
          //  ScanLocalNetwork("192.168.1");
            //RemoteSysSpecs
            //TEST DCGUI
            // JOptionPane.showMessageDialog(null, new DCGUI(LIC_KEY, RemoteSysSpecs));
            JSONObject RemoteSysSpec1 = new JSONObject();
            String LIC_KEY_1 = "AllUsers";//"pditestprofile52413";//"NOKEY";
            RemoteSysSpec1.put("IP_ADDRESS", "greenmonster.local");//10.29.226.179
            //RemoteSysSpec1.put("IP_ADDRESS","10.29.226.179");//
            //RemoteSysSpec1.put("IP_ADDRESS", "192.168.1.20");
            // RemoteSysSpec1 = new JSONObject((String) new DistributedTaskService("NOKEY",RemoteSysSpec1,5559,new OSAppAsDCTask(0)).execute());

            JSONObject RemoteSysSpec2 = new JSONObject();
            String LIC_KEY_2 = "AllUsers";//"pditestprofile52413";
            RemoteSysSpec2.put("IP_ADDRESS", "127.0.0.1");

            // RemoteSysSpec2.put("IP_ADDRESS","192.168.1.20");
            // RemoteSysSpec2 = new JSONObject((String) new DistributedTaskService("pditestprofile52413",RemoteSysSpec2,5559,new OSAppAsDCTask(0)).execute());
            JSONObject RemoteSysSpec3 = new JSONObject();
            String LIC_KEY_3 = "AllUsers";//"pditestprofile52413";
            RemoteSysSpec3.put("IP_ADDRESS", "10.129.14.75");
            RemoteSysSpec3.put("IP_ADDRESS", "192.168.1.27");

            JSONObject RemoteSysSpec4 = new JSONObject();
            String LIC_KEY_4 = "AllUsers";//"pditestprofile52413";
            RemoteSysSpec4.put("IP_ADDRESS", "10.129.14.75");
            RemoteSysSpec4.put("IP_ADDRESS", "192.168.1.192");//192 -Lokesh //10-DataServer

            JSONObject RemoteSysSpec5 = new JSONObject();
            String LIC_KEY_5 = "AllUsers";//"pditestprofile52413";
            RemoteSysSpec5.put("IP_ADDRESS", "10.129.14.75");
            RemoteSysSpec5.put("IP_ADDRESS", "192.168.1.10");//192 -Lokesh //10-DataServer

            JSONObject RemoteSysSpec6 = new JSONObject();
            String LIC_KEY_6 = "AllUsers";//"pditestprofile52413";
            RemoteSysSpec6.put("IP_ADDRESS", "10.129.14.75");
            RemoteSysSpec6.put("IP_ADDRESS", "192.168.1.11");//192 -Lokesh //10-DataServer

            newJOb = new JSONObject[]{RemoteSysSpec2, RemoteSysSpec2};

            //TEST DCGUI - MPP Monitor
            JOptionPane.showMessageDialog(null, new DCGUI(LIC_KEY_1, RemoteSysSpecs, true));

            //Test Solar System
            JOptionPane.showMessageDialog(null, new SolarSysGUI(LIC_KEY_1, RemoteSysSpec1, true));
            JOptionPane.showMessageDialog(null, new SolarSysGUI(LIC_KEY_1, RemoteSysSpecs, true));

            //Test Prime Number Verification
            JOptionPane.showMessageDialog(null, new VerifyPrimeNumberGUI(LIC_KEY_1, RemoteSysSpecs, true));//not newJOB //RemoteSysSpecs
            //VerifyPrimeNumberGUI

            /**
             * Before this Launch application with correct License Key And , Click on Share screen
             */
//We want to start just 2 threads at the same time, but let's control that 
//timing from the main thread. That's why we have 3 "parties" instead of 2.
//final CyclicBarrier gate = new CyclicBarrier(newJOb.length);
            long begTest = new java.util.Date().getTime();
            Double secs;

            ExecutorService es = Executors.newCachedThreadPool();
            Interval = IsPrimeNumber.divide(new BigInteger(String.valueOf(newJOb.length)));
            IsPrimeNumber = new BigInteger("90000256545454464893200345000000002354432654423654426354426354462354465234465234465324465423654425000000000000000000000000000000000000000000000000000000000003531");
            COLLECTED_RESULTS = new Object[newJOb.length];
            for (int i = 0; i < newJOb.length; i++) {
//                    Thread GetSysInfoServicen = new Thread(new Runnable() {
                es.execute(new Runnable() {
                    public void run() {
                        try {
                            // gate.await();
                            int thisCount = -1;
                            if (thisCount < 0) //i.e. Not initiated already
                            {
                                if (SpecCount < newJOb.length) {
                                    thisCount = SpecCount; //Set if it is first entry only
                                }
                            }
                            SpecCount++;

                            COLLECTED_RESULTS[thisCount] = new DistributedTaskService(LIC_KEY, newJOb[thisCount], 5559, new IsDivisibleByNumberDTask(
                                    Interval.multiply(new BigInteger(String.valueOf(thisCount))),
                                    Interval.multiply(new BigInteger(String.valueOf((thisCount + 1)))), IsPrimeNumber)).execute();

                            System.out.println("#Test Prime Number Task on:" + COLLECTED_RESULTS[thisCount]);

                            //System.out.println("#Test Load on :"+newJOb[i].getString("IP_ADDRESS")+"\t Free CPU:"+new DistributedTaskService(LIC_KEY_1,newJOb[i],5559,new OSAppAsDCTask(DistributedTaskPool.AVAILABLE_PROCESSOR_BASED)).execute());
                            //System.out.println("#Test PC NAME on:"+newJOb[i].getString("IP_ADDRESS")+"\t PC Name="+(new DistributedTaskService(LIC_KEY_1,newJOb[i],5559,new DTaskGetPCName()).execute()).toString());
                            // System.out.println("#Test Prime Number Task on:"+newJOb[thisCount].getString("IP_ADDRESS")+"\t :"+(new DistributedTaskService(LIC_KEY,newJOb[thisCount],5559,new DTaskTest(0)).execute()).toString());
                            //System.out.println("#Test Result of sum on:"+newJOb[i].getString("IP_ADDRESS")+"\t Sum:"+(new DistributedTaskService(LIC_KEY_1,newJOb[i],5559,new DTaskMaths(new int[]{1000,30000},DTaskMaths.AVERAGE_OF_NUMBERS)).execute()).toString());
                        } catch (Exception ex) {
                            Logger.getLogger(FrontPage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

                //GetSysInfoServicen.start();   
            }

            secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
            System.out.println("Task Submitted in: " + secs + " secs");
            es.shutdown();
            boolean finshed = es.awaitTermination(1, TimeUnit.MINUTES);
            // all tasks have finished or the time has been reached.
            secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
            System.out.println("Task Finished in: " + secs + " secs");

            for (int i = 0; i < COLLECTED_RESULTS.length; i++) {
                if (!(Boolean) COLLECTED_RESULTS[i]) {
                    System.out.println("Not A prime number");
                    break;
                }
            }

        } catch (JSONException ex) {
            Logger.getLogger(DistributedTaskPool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DistributedTaskPool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
