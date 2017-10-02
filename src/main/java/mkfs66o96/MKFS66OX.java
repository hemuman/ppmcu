/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkfs66o96;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONArray;
import json.JSONObject;

/**
 *
 * @author PDI
 */
public class MKFS66OX {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    class ClientServiceThread extends Thread {

    Socket clientSocket;
    int clientID = -1;
    boolean running = true;


    ClientServiceThread(Socket s, int i) {
        clientSocket = s;
        clientID = i;
       
    }
    /**
 * Data from client will be a JSON Stream.
 * JSON Stream will have batch command like, Create,Delete,load,Rename,Duplicate, Assemble etc.
 * 'COMMAND':'Create'
 * Next item will be the list of components to be acted upon this command
 * 'ITEMS':['partA.prt','AssemblyB.asm','BlockC.drw','Clip.csv']
 * The Items can be different for different commands.
 * Command List:
 * CREATE
 * _FROM
 * _WITH_PARAM
 * _WITH_REL
 * COMPONENT
 * _RENAME
 * _DELETE
 * _DUPLICATE_PART
 * _DUPLICATE_ASSY
 * _DUPLICATE_DRAWING
 * _ASSEMBLE
 * _PACKAGE
 * _REPLACE_IN_ASY
 * _ADD_UDF
 * _DELETE_FEATURE
 * _SAVE
 * _LOAD
 * UPDATE_PARAM
 * UPDATE_REL
 * DELETE_PARAM
 * DELETE_REL
 * 
 * Data Request Commands:
 * MODEL_TREE
 * FEATURE_TREE
 * COMPONENT
 * _BBX
 * _CS000
 * _CSXYZ
 * _INTERSECT_LIST
 * _CLEARANCE
 * _RELLIST
 * _PARAM_LIST
 * _EVALREL
 * _PATH
 * _REGEN_STATUS
 * _SIMPREPLIST
 * _SLP
 * 
 */
    public void run() {
       
            System.out.println("Accepted Client : ID - " + clientID + " : Address - "
                    + clientSocket.getInetAddress().getHostName());
          
           // cursession.CreatePart("newPart");
            //cursession.DrawText2D(p3D, "Some Text from...");
            try {
                System.out.println("Creating in.");
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println("Creating Out.");
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
              //  System.out.println("Running in."+in.readLine());
                while (running) {
                    String clientResponse = in.readLine();
                    System.out.println("Client Says :" + clientResponse);
                    //out.write("Yup!! Have it.");
                    clientResponse=clientResponse.substring(clientResponse.indexOf("{"), clientResponse.lastIndexOf("}")+1);
                    System.out.println("Client Says :" + clientResponse);
                    JSONObject clientCommand=new JSONObject(clientResponse);
                    if(clientCommand.getString("COMMAND").equalsIgnoreCase("CREATE")){
                        JSONArray listOfParts=clientCommand.getJSONArray("ITEMS");
                        for(int i=0;i<listOfParts.length();i++){
                         
                        }
                        
                    }
                    if(clientCommand.getString("COMMAND").equalsIgnoreCase("CREATE")){
                        JSONArray listOfParts=clientCommand.getJSONArray("ITEMS");
                        for(int i=0;i<listOfParts.length();i++){
                        
                        }
                        
                    }
                    if(clientCommand.getString("COMMAND").equalsIgnoreCase("MODEL_TREE")){
                        JSONArray listOfParts=clientCommand.getJSONArray("ITEMS");
                        String AssemblyName="";
                        if(listOfParts.getString(0).contains("CURRENT_MODEL")){}
                     
                        
                    }
                    
                    if(clientCommand.getString("COMMAND").equalsIgnoreCase("VIEW_SENSOR")){
                        JSONArray listOfParts=clientCommand.getJSONArray("ITEMS");
                      
                        for(int i=0;i<3;i++){
                         // new ViewOperation().AnimateRotationAbout(coordAxis[i], listOfParts.getInt(i));
                          Thread.sleep(1000);
                         }
                    }
                    
                    
                    
                    
                   /*if (clientCommand.equalsIgnoreCase("quit")) {
                        running = false;
                        System.out.print("Stopping client thread for client : " + clientID);
                    } else if (clientCommand.equalsIgnoreCase("Load")) {
                        cursession.ChangeDirectory("C:/");

                        new ModelOperation().LoadAndGetModel("basin.prt", true, true);
                        out.println(clientCommand);
                        out.flush();
                    }*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        
    }}
}
