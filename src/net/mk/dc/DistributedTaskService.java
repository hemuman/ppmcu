/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dc;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONException;
import json.JSONObject;
import net.mk.DTasks.DTaskGetPCName;
import net.mk.DTasks.DTaskProcessorAdmin;
import net.mk.DTasks.DTaskTest;
import net.mk.ppmcu.GlobalMCUTest;
import net.mk.ppmcuGUI.MCWConfClass;

/**
 *
 * @author PDI
 */
public class DistributedTaskService {

    /**
     * Should accept Distributed Task and send to connected machine (s - future work) then collect the result and return.
     */
    /**
     * Connect to server Convert the DistributedTask to byte and send it across
     *
     * @param args
     */
    String LICENSE_KEY;
    DistributedTask DISTRIBUTED_TASK;
    public static final int STATUS = 100;
    public static final int CONNECTED = 1;
    public static final int LICENSE_ACCEPTED = 2;
    public static final int READY_FOR_COMPUTING = LICENSE_ACCEPTED;
    public static final int LICENSE_DECLINED = 3;
    public static final int SEND_TASK = 4;
    public static final int EXIT = 0;
    public static final int SERVER_NOT_LIVE = -1;
    private boolean IS_CONNECTED_TO_SERVER = false;
    boolean debug = false;

    public static final int CONNECTION_CHECK = 786;
    JSONObject RemoteSysSpec;
    InetAddress host;
    int _portNumber = 5559; //Arbitrary port number

    public Queue<DistributedTask> DISTRIBUTED_TASK_QUEUE = new LinkedList<DistributedTask>();
    Socket socket_ = null;
    PrintWriter out_ = null;
    BufferedReader in_ = null;
    //InetAddress host = null;
    BufferedReader stdIn_ = null;

    public DistributedTaskService(String LICENSE_KEY, JSONObject RemoteSysSpec, int _portNumber, DistributedTask DISTRIBUTED_TASK) {

        if (MCWConfClass.isACTIVATE_DISTRIBUTED_COMPUTING_NODE()) {
            this.LICENSE_KEY = LICENSE_KEY;
            this.RemoteSysSpec = RemoteSysSpec;
            this._portNumber = _portNumber;
            this.DISTRIBUTED_TASK = DISTRIBUTED_TASK;
            try {
                if (debug) {
                    System.out.println("#RemoteSysSpec.getString(\"IP_ADDRESS\")" + RemoteSysSpec.getString("IP_ADDRESS"));
                }
                host = InetAddress.getByName(RemoteSysSpec.getString("IP_ADDRESS"));
                // host = InetAddress.getLocalHost();
            } catch (JSONException ex) {
                Logger.getLogger(DistributedTaskService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex) {
                Logger.getLogger(DistributedTaskService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Usage; 1. Call constructor 2. Call createConnection() 3. Call executeOnPersistantConn(DistributedTask) 4. Call closeConnection()
     *
     * @param LICENSE_KEY
     * @param RemoteSysSpec
     * @param _portNumber
     */
    public DistributedTaskService(String LICENSE_KEY, JSONObject RemoteSysSpec, int _portNumber) {

        if (MCWConfClass.isACTIVATE_DISTRIBUTED_COMPUTING_NODE()) {
            this.LICENSE_KEY = LICENSE_KEY;
            this.RemoteSysSpec = RemoteSysSpec;
            this._portNumber = _portNumber;
            try {

                if (debug) {
                    System.out.println("#RemoteSysSpec.getString(\"IP_ADDRESS\")" + RemoteSysSpec.getString("IP_ADDRESS"));
                }
                host = InetAddress.getByName(RemoteSysSpec.getString("IP_ADDRESS"));
                // host = InetAddress.getLocalHost();
            } catch (JSONException ex) {
                Logger.getLogger(DistributedTaskService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex) {
                Logger.getLogger(DistributedTaskService.class.getName()).log(Level.SEVERE, null, ex);

            }
        }

    }

    public static void main(String[] args) {
        try {
            JSONObject RemoteSysSpec = new GlobalMCUTest().printMachineInfo(Boolean.TRUE, true);
            InetAddress host = InetAddress.getLocalHost();
            //Please run DistributedTaskProcessor before running this test.
            //Usual tests
            System.out.println("Client Got result:" + new DistributedTaskService("TEST", RemoteSysSpec, 5559, new DTaskTest(1)).execute());
            System.out.println("Client Got result2:" + new DistributedTaskService("TEST", RemoteSysSpec, 5559, new DTaskTest(2)).execute());
            System.out.println("Client Got result3:" + new DistributedTaskService("TEST", RemoteSysSpec, 5559, new DTaskTest(3)).execute());
      
            
            //Test for persistant connections:
            DistributedTaskService dts = new DistributedTaskService("TEST", RemoteSysSpec, 5559);
            dts.createConnection();
            System.out.println("Client Got executeOnPersistantConn result:" + dts.executeOnPersistantConn(new DTaskTest(1)));
            System.out.println("Client Got executeOnPersistantConn result2:" + dts.executeOnPersistantConn(new DTaskTest(1)));
            System.out.println("Client Got executeOnPersistantConn result3:" + dts.executeOnPersistantConn(new DTaskTest(1)));
            dts.closeConnection();

         } catch (Exception e) {
            System.out.println("Something falied: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createConnection() {
        try {
            socket_ = new Socket(host.getHostName(), _portNumber);
            socket_.setSoTimeout(0);//Wait for infinite amount of time is it is set to 0

            out_ = new PrintWriter(socket_.getOutputStream(), true);
            in_ = new BufferedReader(new InputStreamReader(socket_.getInputStream()));

            stdIn_ = new BufferedReader(new InputStreamReader(System.in));
            boolean PERSIST_CONNECTION = true;

            while (true) {
                String fromServer = in_.readLine();

                //UNCOMMENT FOR DEBUGGING 3
                if (debug) {
                    System.out.println("#DistributedTaskService Server : " + fromServer);
                }

                /*Terminate the Client Application*/
                if (Integer.parseInt(fromServer) == EXIT) {
                    break;
                }
                if (Integer.parseInt(fromServer) == LICENSE_DECLINED) {
                    break;
                }

                 /**
                 * Send the Key
                 */
                else if (Integer.parseInt(fromServer) == CONNECTED) {
                    if (debug) {
                    System.out.println("#DistributedTaskService createConnection: Sending LICENSE_KEY=" + LICENSE_KEY);
                }
                    out_.println(LICENSE_KEY); //SEND THE LICENSE KEY
                } /**
                 * Send the task
                 */
                else if (Integer.parseInt(fromServer) == LICENSE_ACCEPTED) { // Connected
                    if (debug) {
                        System.out.println("#DistributedTaskService createConnection: Sending Task=" + DTaskProcessorAdmin.class.getSimpleName());
                    }
                    //Send instructions to keep connection live
                    OutputStream os = socket_.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    oos.writeObject(new DTaskProcessorAdmin(PERSIST_CONNECTION)); //Sent for processing
                    /**
                     * Wait and Collect the result
                     */
                    ObjectInputStream ois = new ObjectInputStream(socket_.getInputStream());
                    Object temp=ois.readObject(); //Recieved
                    if (debug) {
                        System.out.println("#DistributedTaskService createConnection: Sent Task=" + DTaskProcessorAdmin.class.getSimpleName());
                    }
                    IS_CONNECTED_TO_SERVER = true;
                    System.out.println("#DistributedTaskService createConnection IS_CONNECTED_TO_SERVER= " + IS_CONNECTED_TO_SERVER);
                    break;
                } else {

                }
            }

        } catch (IOException ex) {
            Logger.getLogger(DistributedTaskService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("#DistributedTaskService createConnection IS_CONNECTED_TO_SERVER= " + IS_CONNECTED_TO_SERVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DistributedTaskService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("#DistributedTaskService createConnection IS_CONNECTED_TO_SERVER= " + IS_CONNECTED_TO_SERVER);
        }
    }

    /**
     * As name suggests executeOnPersistantConn will make use of existing connection.
     * @param DISTRIBUTED_TASK
     * @return
     * @throws IOException
     */
    public Object executeOnPersistantConn(DistributedTask DISTRIBUTED_TASK) throws IOException {
        Object result = new Object();
        //UNCOMMENT FOR DEBUGGING 1
        //System.out.println("#DistributedTaskService execute()..try"+host.getHostName()+"port="+_portNumber);
        if (IS_CONNECTED_TO_SERVER) {
            try {
                if (debug) {
                        System.out.println("#DistributedTaskService executeOnPersistantConn: Sending Task=" + DISTRIBUTED_TASK.getClass().getSimpleName());
                    }
                OutputStream os = socket_.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(DISTRIBUTED_TASK); //Send for processing.
                /**
                 * Wait and Collect the result
                 */
                ObjectInputStream ois = new ObjectInputStream(socket_.getInputStream());
                result = ois.readObject(); //Recieved
                
                os=null;
                oos=null;
                ois=null;

            } catch (UnknownHostException e) {
                System.err.println("Cannot find the host: " + host.getHostName());
                //System.exit(1);
            } catch (IOException e) {
                System.err.println("#DistributedTaskService " + host.getHostName() + " Couldn't read/write from the connection: " + e.getMessage());
                e.printStackTrace();
                //System.exit(1);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DistributedTaskService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("#DistributedTaskService executeOnPersistantConn IS_CONNECTED_TO_SERVER= " + IS_CONNECTED_TO_SERVER);
        }
        return result;
    }

    /**
     * Sends message to remote server to close connection. Also disposes local resources.
     */
    public void closeConnection() {
        try {
            //Send instructions to close connection
            OutputStream os = socket_.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(new DTaskProcessorAdmin(false)); //Send for processing
            /**
                     * Wait and Collect the result
                     */
                    ObjectInputStream ois = new ObjectInputStream(socket_.getInputStream());
                    Object temp=ois.readObject(); //Recieved
            out_.close();
            in_.close();
            stdIn_.close();
            socket_.close();
            IS_CONNECTED_TO_SERVER = false;
            System.out.println("#DistributedTaskService closeConnection IS_CONNECTED_TO_SERVER= " + IS_CONNECTED_TO_SERVER);
        } catch (IOException ex) {
            Logger.getLogger(DistributedTaskService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DistributedTaskService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Object execute() throws IOException {

        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        //InetAddress host = null;
        BufferedReader stdIn = null;
        Object result = new Object();

        //UNCOMMENT FOR DEBUGGING 1
        //System.out.println("#DistributedTaskService execute()..try"+host.getHostName()+"port="+_portNumber);
        try {
            //host = InetAddress.getLocalHost();
            socket = new Socket(host.getHostName(), _portNumber);
            socket.setSoTimeout(0);//Wait for infinite amount of time is it is set to 0

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            //UNCOMMENT FOR DEBUGGING 2
            //System.out.println("#DistributedTaskService ready to communicate..");
            //Read from socket and write back the response to server. 
            while (true) {

                fromServer = in.readLine();

                //UNCOMMENT FOR DEBUGGING 3
                if (debug) {
                    System.out.println("Server - " + fromServer);
                }

                /*Terminate the Client Application*/
                if (Integer.parseInt(fromServer) == EXIT) {
                    break;
                }
                if (Integer.parseInt(fromServer) == LICENSE_DECLINED) {
                    break;
                } /**
                 * Send the Key
                 */
                else if (Integer.parseInt(fromServer) == CONNECTED) {
                    out.println(LICENSE_KEY); //SEND THE LICENSE KEY
                } /**
                 * Send the task
                 */
                else if (Integer.parseInt(fromServer) == LICENSE_ACCEPTED) { // Connected
                    OutputStream os = socket.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    oos.writeObject(DISTRIBUTED_TASK); //Sent for processing
                    /**
                     * Collect the result
                     */
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    result = ois.readObject(); //Recieved
                    break;
                }

            }

        } catch (UnknownHostException e) {
            System.err.println("Cannot find the host: " + host.getHostName());
            //System.exit(1);
        } catch (IOException e) {
            System.err.println("#DistributedTaskService " + host.getHostName() + " Couldn't read/write from the connection: " + e.getMessage());
            //System.exit(1);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DistributedTaskService.class.getName()).log(Level.SEVERE, null, ex);
        } finally { //Make sure we always clean up
//            out.close();
//            in.close();
//            stdIn.close();
//            socket.close();
        }

        return result;
    }

    public int isServerLive(int TIME_OUT) throws IOException {

        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        //InetAddress host = null;
        BufferedReader stdIn = null;
        Object result = new Object();

        System.out.println("#DistributedTaskService isServerLive()..trying: " + host.getHostName() + " @ port= " + _portNumber);

        try {
            //host = InetAddress.getLocalHost();
            socket = new Socket(host.getHostName(), _portNumber);
            socket.setSoTimeout(TIME_OUT);//Wait for infinite amount of time.

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            //System.out.println("#DistributedTaskService ready to communicate..");
            //Read from socket and write back the response to server. 
            while (true) {

                fromServer = in.readLine();
                System.out.println("Server - " + fromServer);

                /*Terminate the Client Application*/
                if (Integer.parseInt(fromServer) == EXIT) {
                    socket.close();
                    return EXIT;
                }
                if (Integer.parseInt(fromServer) == LICENSE_DECLINED) {
                    socket.close();
                    return LICENSE_DECLINED;
                } /**
                 * Send the Key
                 */
                else if (Integer.parseInt(fromServer) == CONNECTED) {
                    out.println(LICENSE_KEY); //SEND THE LICENSE KEY
                } /**
                 * Send the task
                 */
                else if (Integer.parseInt(fromServer) == LICENSE_ACCEPTED) { // Connected
                    OutputStream os = socket.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    oos.writeObject(new DTaskGetPCName()); //Sent for processing
                    /**
                     * Collect the result
                     */
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Object readObject = ois.readObject(); //Recieved

                    socket.close();
                    return LICENSE_ACCEPTED;
                }

            }

        } catch (UnknownHostException e) {
            System.err.println("Cannot find the host: " + host.getHostName());
            //System.exit(1);
        } catch (IOException e) {
            System.err.println("#DistributedTaskService " + host.getHostName() + " Couldn't read/write from the connection: " + e.getMessage());
            //System.exit(1);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DistributedTaskService.class.getName()).log(Level.SEVERE, null, ex);
        } finally { //Make sure we always clean up
//			out.close();
//			in.close();
//			stdIn.close();
//			socket.close();
        }

        return SERVER_NOT_LIVE;
    }
}
