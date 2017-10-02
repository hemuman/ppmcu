package net.mk.dc;

import java.io.*;
import java.net.*;
import java.util.*;
import net.mk.ppmcu.GlobalMCUTest;

public class sisclient {
    public static void main(String[] args) throws IOException {
        
        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        InetAddress ia=InetAddress.getLocalHost();
        FileReader fi1=new FileReader("get.add");   //server address
        String add=" ";
        int read1;
        Runtime rt = Runtime.getRuntime();
        Process p1=null;
        while((read1=fi1.read())!=-1) {
            add=add+(char)read1;
        }
        fi1.close();
  
        try {
            kkSocket = new Socket(add.substring(1), 4444);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            System.out.println("Client:ID: "+ia);
            out.println(ia+":"+"1");
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't  I/O connection to: manoj");
            System.exit(1);
        }
        
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;
        /********LOGO**************/
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("         THIS#IS#M");
        System.out.println("         MADE$BY$M");
        System.out.println("         ANOJ%KUM%");
        System.out.println("         AR^03120^");
        System.out.println("         26&MECH&&");
        System.out.println("         --BYTE---");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
  
        /****************************************/
        int ind=0;
        String p11,p2,p3="ma";
         clientplugin p=new clientplugin();
         InputStreamReader  ir = new  InputStreamReader(System.in); 
        BufferedReader stdIn1 = new BufferedReader(ir);
        //System.out.print("BYTE/:>");
        String comm;//=stdIn.readLine();
        String comm1 =null,comm2,comm3;
        
        while ((fromServer = in.readLine()) != null) {
            System.out.println("Server: " + fromServer);
            ind=fromServer.indexOf(":");
            p11=fromServer.substring(0,ind);
            p2=fromServer.substring(ind+1);
            if(fromServer.lastIndexOf(":")>ind  ) {  //for WORKNOW
                // System.err.println("S"+p2.substring(0,7)+" in mode"+p2+" & I am in "+ind+"here"+fromServer.lastIndexOf(":"));
                p3=p11.substring(0,7);
            }
            System.out.println("Server: " +p3);
            if (p3.equals("BYEBYTE")) {
                    out.close();
                    in.close();
                    stdIn.close();
                    kkSocket.close();
                out.println(ia+":"+"3");                         //-waiting mode
                continue;
            }
            if (p2.equals("1")) {
                out.println(ia+":"+"3");                         //-waiting mode
                continue;
            }
            if (p2.equals("3")) {                                 //connected and -Idle
                out.println(ia+":"+"3");
                continue;
            }
            if (p3.equals("WORKNOW")) {                            //-processing
                System.out.println("Server: u  " + p2);
                System.out.println("BYTE/WORKING/:>");
                    //make classes now for different functions
                    comm1=p.processInput(p2);
                    System.out.println(comm1);
                    out.println(ia +":JOBDONE:;"+comm1);
                    continue;
            }
            if (p11.equals("5")) {                                   //-Done
                System.out.println("  " + p2);
                out.println(ia+":"+"5");
                continue;
            }

        }
        
        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();
        
        
    }
public static void start(String Server, int port) throws IOException {
        
        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        InetAddress ia=InetAddress.getLocalHost();
       // FileReader fi1=new FileReader("get.add");   //server address
        String add=" ";
        int read1;
        Runtime rt = Runtime.getRuntime();
        Process p1=null;
//        while((read1=fi1.read())!=-1) {
//            add=add+(char)read1;
//        }
//        fi1.close();
  
        try {
            kkSocket = new Socket(Server, port);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            System.out.println("Client:ID: "+ia);
            out.println(ia+":"+"1");
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't  I/O connection to: manoj");
            System.exit(1);
        }
        
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;
        /********LOGO**************/
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("         THIS#IS#M");
        System.out.println("         MADE$BY$M");
        System.out.println("         ANOJ%KUM%");
        System.out.println("         AR^03120^");
        System.out.println("         26&MECH&&");
        System.out.println("         --BYTE---");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
  
        /****************************************/
        int ind=0;
        String p11,p2,p3="ma";
         clientplugin p=new clientplugin();
         InputStreamReader  ir = new  InputStreamReader(System.in); 
        BufferedReader stdIn1 = new BufferedReader(ir);
        //System.out.print("BYTE/:>");
       // String comm;//=stdIn.readLine();
        String comm1 =null,comm2,comm3;
        
        while ((fromServer = in.readLine()) != null) {
            System.out.println("#sisclient.start Server: " + fromServer);
            ind=fromServer.indexOf(":");
            p11=fromServer.substring(0,ind);
            p2=fromServer.substring(ind+1);
            if(fromServer.lastIndexOf(":")>ind  ) {  //for WORKNOW
                // System.err.println("S"+p2.substring(0,7)+" in mode"+p2+" & I am in "+ind+"here"+fromServer.lastIndexOf(":"));
                p3=p11.substring(0,7);
            }
            // System.out.println("Server: " +p3);
            if (p3.equals("BYEBYTE")) {
                    out.close();
                    in.close();
                    stdIn.close();
                    kkSocket.close();
                out.println(ia+":"+"3");                         //-waiting mode
                continue;
            }
            if (p2.equals("1")) {
                out.println(ia+":"+"3");                         //-waiting mode
                continue;
            }
            if (p2.equals("3")) {                                 //connected and -Idle
                out.println(ia+":"+"3");
                continue;
            }
            if (p3.equals("WORKNOW")) {                            //-processing
                System.out.println("Server: u  " + p2);
                System.out.println("BYTE/WORKING/:>");
                    //make classes now for different functions
                    comm1=p.processInput(p2);
                    System.out.println(comm1);
                    out.println(ia +":JOBDONE:;"+comm1);
                    continue;
            }
            
              if (p3.equals("SYSTEM_INFO")) {                            //-processing
                System.out.println("Server: u  " + p2);
                System.out.println("BYTE/WORKING/:>");
                    //make classes now for different functions
                    comm1=new GlobalMCUTest().printMachineInfo(true, true).toString();
                    //System.out.println(comm1);
                    out.println(ia +":JOBDONE:;"+comm1);
                    continue;
            }
              
            if (p11.equals("5")) {                                   //-Done
                System.out.println("  " + p2);
                out.println(ia+":"+"5");
                continue;
            }

        }
        
        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();
        
        
    }
}

