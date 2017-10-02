/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dc;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONException;
import json.JSONObject;
import net.mk.DTasks.OSAppAsDCTask;
import static net.mk.dc.DistributedTaskPool.LICENSE_KEY;
import static net.mk.dc.DistributedTaskPool.RemoteSysSpecs;

/**
 * WIP
 * @author Manoj Kumar
 */
public class DynamicCluster {

    static int COUNT = 0;
    static List<DistributedTask> Requests = new CopyOnWriteArrayList<DistributedTask>();
    static Iterator<DistributedTask> iter;
    static JSONObject[] RemoteSpecs;
    static int ReqLot = 0;
    static String LICENSE_KEY;
    static int portNumber = 5559; //Arbitrary LOCAL_IP number

    /**
     * Injects 100 requests in list.
     */
    static void inject100Request(DistributedTask DTask) {
           synchronized (DynamicCluster.class) {
                Requests.add(DTask);
            }
    }

    /**
     * Thread to inject 100 requests.
     */
    static void RequestInjectingThread() {

        Thread requestInjectingThread = new Thread(new Runnable() {
            public void run() {
                while (true) //Inject for Eternity 
                {
                    inject100Request(new OSAppAsDCTask(0) );
                    ReqLot++;
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DynamicCluster.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });

        requestInjectingThread.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ExecutorService es = Executors.newCachedThreadPool();// ExecutorService
        RemoteSpecs = new JSONObject[10]; // 10 Resources available for processing the request

        for (int i = 0; i < RemoteSpecs.length; i++) { // Init all the threads to process requests

            RemoteSpecs[i] = new JSONObject();
            try {
                RemoteSpecs[i].put("Thread#" + i, " "); // Give an identification to each thread
            } catch (JSONException ex) {
                Logger.getLogger(DynamicCluster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Keeps injecting requests in every 3 secs
        RequestInjectingThread();

        //Process request
        while (true) { //Run for Eternity 

            iter = Requests.iterator();
            //System.out.println(" True is still true with "+ Requests.size()+ " Requests");
            while (iter.hasNext()) { // While there is a request pending
                //System.out.println(" True is still true with "+ Requests.size()+ " Requests");

                es.execute(new Runnable() { // Find a processor/thread available for processing
                    public void run() {

                        boolean SCHEDULED = false;

                        while (!SCHEDULED)//Try scheduling the task until it is scheduled for execution,
                        {
                            //System.out.println("#Stuck in scheduling.. >COUNT "+ COUNT + " >SCHEDULED "+SCHEDULED);
                            for (int i = 0; i < RemoteSpecs.length; i++) {
                                try {
                                    //Loop through to find available resource for computation,

                                    if (RemoteSpecs[i].getBoolean("IS_AVAILABLE")) //Check if it is available for computing,
                                    {
//System.out.println("#"+ requestProcessor[i].getName()+ " Availablity: "+  requestProcessor[i].getIsAvailable());

                                        if (iter.hasNext()) // While there is still next request left,
                                        {
                                            try {
                                                synchronized (this) {
                                                    DistributedTask req = iter.next(); //Get the request object to be processed,
                                                    RemoteSysSpecs[i].put("IS_AVAILABLE", false);// Reserve it for our work,
                                                    
                                                    Object result=new DistributedTaskService(LICENSE_KEY, RemoteSysSpecs[i], portNumber, req).execute();
                                                    //requestProcessor[i].SubmitRequest(req, COUNT); // Process the request,

                                                    Requests.remove(req); //remove the request , the one processed already,
                                                }
                                            } catch (Exception e) {
                                            }
                                            SCHEDULED = true;
                                            COUNT++;
                                            //System.out.println("#QueueSystem.Task"+ COUNT++);
                                        } else {
                                            SCHEDULED = true; // Either way exit while loop,
                                        }  // System.out.println("#QueueSystem.Task"+thisCount + " Processed by "+ i);

                                        break; //Break the loop as this is task is scheduled for execution.
                                    } else {
//System.out.println("#"+ requestProcessor[i].getName()+ " Availablity: "+  requestProcessor[i].getIsAvailable());
                                    }
                                } catch (JSONException ex) {
                                    Logger.getLogger(DynamicCluster.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                        //System.out.println("FINISHED #This lot is processed >COUNT "+ COUNT);
                        COUNT = 0;// Just to count the number of items processed.

                    }
                });


            }

            if (Requests.size() == 0) { // If there are no requests
                try {
                    Thread.sleep(2000); //Take a nap for 2 secs
                } catch (InterruptedException ex) {
                    Logger.getLogger(DynamicCluster.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            //System.out.println(" True is still true with Processed "+ Requests.size()+ " Requests");
        }//Loop for All task

        // es.shutdown();
    }
}
