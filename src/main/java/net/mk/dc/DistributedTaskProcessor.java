/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dc;

/**
 *
 * @author PDI
 */
/**
 * This is a multi threaded server which accepts the object and performs calculation.
 */
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONObject;
import net.mk.DTasks.DTaskProcessorAdmin;
import net.mk.dcApps.GetAppGUI;
import net.mk.ppmcu.GlobalMCUTest;
import net.mk.ppmcuGUI.MCWConfClass;

/**
 * Demo Server: Contains a multi-threaded socket server sample code.
 */
public class DistributedTaskProcessor extends Thread {

    int _portNumber = 5559; //Arbitrary port number
    String LICENSE_KEY;
    JSONObject ThisSysSpec;
    String DTaskList[];
    Class[] classes;
    boolean debug = false;

    public DistributedTaskProcessor(String LICENSE_KEY, JSONObject ThisSysSpec, int _portNumber) {

        if (MCWConfClass.isACTIVATE_DISTRIBUTED_COMPUTING_NODE()) {
            this.LICENSE_KEY = LICENSE_KEY;
            this.ThisSysSpec = ThisSysSpec;
            this._portNumber = _portNumber;
        }

    }

    /**
     * This solves issue of DTask not found
     *
     * @param LICENSE_KEY
     * @param ThisSysSpec
     * @param _portNumber
     * @param DTaskList
     */
    public DistributedTaskProcessor(String LICENSE_KEY, JSONObject ThisSysSpec, int _portNumber, String DTaskList[]) {

        if (MCWConfClass.isACTIVATE_DISTRIBUTED_COMPUTING_NODE()) {
            this.LICENSE_KEY = LICENSE_KEY;
            this.ThisSysSpec = ThisSysSpec;
            this._portNumber = _portNumber;
            this.DTaskList = DTaskList;
        }
        classes = new Class[DTaskList.length];
        for (int i = 0; i < DTaskList.length; i++) {
            classes[i] = GetAppGUI.getAppGUI(DTaskList[i], false);
            // DistributedTaskProcessor.class.getClassLoader();
        }

    }

    public static void main(String[] args) {
        try {
            JSONObject ThisSysSpec = new GlobalMCUTest().printMachineInfo(Boolean.TRUE, true);
            new DistributedTaskProcessor("TEST", ThisSysSpec, 5559).startServer();

        } catch (Exception e) {
            System.out.println("I/O failure: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void startServer() {
        // this.LICENSE_KEY=LICENSE_KEY;
        ServerSocket serverSocket = null;
        boolean listening = true;
        System.out.println("#DistributedTaskProcessor..startServer()");
        try {
            serverSocket = new ServerSocket(_portNumber);
        } catch (IOException e) {
            System.err.println("#DistributedTaskProcessor Port Busy : Could not listen on port: " + _portNumber);
            //System.exit(-1);
        }

        while (listening) {
            handleClientRequest(serverSocket);
        }
        try {
            serverSocket.close();
        } catch (IOException ex) {
            //startServer();//Start again forever
            Logger.getLogger(DistributedTaskProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleClientRequest(ServerSocket serverSocket) {
        try {
            new ConnectionRequestHandler(serverSocket.accept()).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles client connection requests.
     */
    public class ConnectionRequestHandler implements Runnable {

        private Socket _socket = null;
        private PrintWriter _out = null;
        private BufferedReader _in = null;

        DistributedTask DISTRIBUTED_TASK;
        int STATUS = 100;
        int CONNECTED = 1;
        int LICENSE_ACCEPTED = 2;
        int LICENSE_DECLINED = 3;
        int SEND_TASK = 4;
        int EXIT = 0;
        boolean PERSIST_CONNECTION=false;
        public static final int CONNECTION_CHECK = 786;

        public ConnectionRequestHandler(Socket socket) {
            _socket = socket;
        }

        public void run() {
            if (debug) {
                System.out.println("#DistributedTaskProcessor Client connected to socket: " + _socket.toString());
            }

            try {
                _out = new PrintWriter(_socket.getOutputStream(), true);
                _in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));

                String inputLine="", outputLine;
				//BusinessLogic businessLogic = new BusinessLogic();
                //outputLine = businessLogic.processInput(null);
                //_out.println(outputLine);

                //Read from socket and write back the response to client. 
                while (true) {
                    /**
                     * 1.
                     */
                    if(!PERSIST_CONNECTION) {_out.println(CONNECTED);}
                    if(!PERSIST_CONNECTION) {inputLine = _in.readLine();}//License Key

                    if (inputLine.contentEquals(LICENSE_KEY) | PERSIST_CONNECTION) {
                        if(!PERSIST_CONNECTION) {_out.println(LICENSE_ACCEPTED);}
                                         //GET THE OBJECT
                        //  if(!_socket.isClosed()){
                        ObjectInputStream ois = new ObjectInputStream(_socket.getInputStream());
                        DISTRIBUTED_TASK = (DistributedTask) ois.readObject();

                        //}else{break;}
                        OutputStream os = _socket.getOutputStream();
                                         //OutputStream osmouse = mouseSocekt.getOutputStream();

                        ObjectOutputStream oos = new ObjectOutputStream(os);

                        if (SecurityRights.ExecutionAllowedFor(DISTRIBUTED_TASK.getClass().getName())) {
                            oos.writeObject(DISTRIBUTED_TASK.compute());//Perform Calculation and send back the result
                            System.out.println("#DistributedTaskProcessor Computing: " + DISTRIBUTED_TASK.getClass().getName());
                        } else {
                            System.out.println("#DistributedTaskProcessor NOT Computing: " + DISTRIBUTED_TASK.getClass().getName());
                        }
                       
                        
                        //Set the PERSIST_CONNECTION value is requested
                        if(DISTRIBUTED_TASK instanceof DTaskProcessorAdmin)
                        {
                            
                            PERSIST_CONNECTION=((DTaskProcessorAdmin)DISTRIBUTED_TASK).PERSIST_CONNECTION;
                            System.out.println("#DistributedTaskProcessor PERSIST_CONNECTION="+PERSIST_CONNECTION);
                        }
                        
                        if(!PERSIST_CONNECTION) //Added for connection pooling.
                        { 
                             _out.println("0"); // Exit code
                            break;}

                    } else {
                        System.out.println("Invalid License key: s/b: " + LICENSE_KEY + " found: " + inputLine);
                        _out.println(LICENSE_DECLINED);
                        _socket.close();//Terminate Connection
                        break;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DistributedTaskProcessor.class.getName()).log(Level.SEVERE, null, ex);
            } finally { //In case anything goes wrong we need to close our I/O streams and sockets.
                try {
                    _out.close();
                    _in.close();
                    //_socket.close();
                } catch (Exception e) {
                    System.out.println("Couldn't close I/O streams");
                }
            }
        }

    }

    /**
     * Handles business logic of application.
     */
    public static class BusinessLogic {

        private static final int LoginUserName = 0;
        private static final int LoginPassword = 1;
        private static final int AuthenticateUser = 2;
        private static final int AuthSuccess = 3;

        private int state = LoginUserName;

        private String userName = null;
        private String userPassword = null;

        public String processInput(String clientRequest) {
            String reply = null;
            try {
                if (clientRequest != null && clientRequest.equalsIgnoreCase("login")) {
                    state = LoginPassword;
                }
                if (clientRequest != null && clientRequest.equalsIgnoreCase("exit")) {
                    return "exit";
                }

                if (state == LoginUserName) {
                    reply = "SEND ME LICENSEKEY";
                    state = LoginPassword;
                } else if (state == LoginPassword) {
                    userName = clientRequest;
                    reply = "Please Enter your password: ";
                    state = AuthenticateUser;
                } else if (state == AuthenticateUser) {
                    userPassword = clientRequest;
                    if (userName.equalsIgnoreCase("John") && userPassword.equals("doe")) {
                        reply = "Login Successful...";
                        state = AuthSuccess;
                    } else {
                        reply = "Invalid Credentials!!! Please try again. Enter you user name: ";
                        state = LoginPassword;
                    }
                } else {
                    reply = "Invalid Request!!!";
                }
            } catch (Exception e) {
                System.out.println("input process falied: " + e.getMessage());
                return "exit";
            }

            return reply;
        }
    }
}
