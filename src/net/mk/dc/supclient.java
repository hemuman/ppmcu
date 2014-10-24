package net.mk.dc;

import java.io.*;
import java.net.*;
import java.util.*;

/**************************Plugin cls*************************************/
//can be written here
/*************************************************************************/
public class supclient {
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
	while((read1=fi1.read())!=-1)
	{
         add=add+(char)read1;
	}
	fi1.close();
       
       
        try {
            kkSocket = new Socket(add.substring(1), 4444);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
		System.out.println("Client:ID: "+ia);
                 out.println(ia+"SUPER:"+"SU1");
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't  I/O connection to: manoj");
            System.exit(1);
        }

        /***********************Command Avilable********************************/
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

         System.out.println("---------------------------------------------------------------");
        System.out.println("Login to semiserver:            SUPLOGIN "+1);
        System.out.println("Refresh Connection :            REF      "+2);
        System.out.println("Get Info of connected client:   CGINFO   "+3);
        System.out.println("Get Plugin Info:                PLUG     "+4);
        System.out.println("For Working On:                 WORKNOW  "+5);
        System.out.println("Get Result:                     RESULTM    "+6);
        System.out.println("Commands Avilable:              HELP     "+6);
        System.out.println("Disconnect:                     BYE      "+7);
        System.out.println("---------------------------------------------------------------");
        
        /*zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz*z*/
     
        String fromServer;
        String fromUser;
        InputStreamReader  ir = new  InputStreamReader(System.in); 
        BufferedReader stdIn = new BufferedReader(ir);
        System.out.print("BYTE/:>");
        String comm=stdIn.readLine();
        String comm1 =null,comm2,comm3;
        String p11,p2;
	int ind=0,ind2,ind3=0,NC=0; 
        plugin p=new plugin();

        while ((fromServer = in.readLine()) != null && comm.equals("SUPLOGIN")) {
            
              System.out.println("Semi-Server:" + fromServer);
       /////listening to server*zzzzzzzzzzzzzzzzzzzzz
            ind=fromServer.indexOf(":");                      //reading
            p11=fromServer.substring(0,ind);                  //header
            p2=fromServer.substring(ind+1);                   //information
       ////////////////////////////////////////////////
            if(p11.equals("CGINFO"))                 
            {
                int i,ii;
                i=fromServer.lastIndexOf(";");
                ind=0;
                ii=0;
                ind3=0;
                NC=0;
                System.out.println("------------------------Clients Online-------------------------");
                while(ind<i)
                {
                ind=fromServer.indexOf(";",ind+1);
                p2=fromServer.substring(ind3+1,ind);
                ind3=ind;
                ind2=0;
                if(NC==0)
                 ind2=fromServer.indexOf(":");
                
                System.out.println("CLIENT NO.: "+NC+" IS:" + p2.substring(ind2));  
                NC=NC+1;
                }
                System.out.println("TOTAL CONNECTED:  "+NC);
                System.out.println("---------------------------------------------------------------");
            }else if(p11.equals("SYSTEM_INFO"))                 
            {
                System.out.println("SYSTEM_INFO : "+fromServer);
                int i,ii;
                i=fromServer.lastIndexOf(";");
                ind=0;
                ii=0;
                ind3=0;
                NC=0;
                System.out.println("------------------------Clients Online-------------------------");
                while(ind<i)
                {
                ind=fromServer.indexOf(";",ind+1);
                p2=fromServer.substring(ind3+1,ind);
                ind3=ind;
                ind2=0;
                if(NC==0)
                 ind2=fromServer.indexOf(":");
                
                System.out.println("CLIENT NO.: "+NC+" IS:" + p2.substring(ind2));  
                NC=NC+1;
                }
                System.out.println("TOTAL CONNECTED:  "+NC);
                System.out.println("---------------------------------------------------------------");
            }else if(p11.equals("RESULTM"))                 
            {
                int i,ii;
                i=fromServer.lastIndexOf(";");
                ind=0;
                ii=0;
                ind3=0;
                NC=0;
                 System.out.println("---------------------------------------------------------------");
                 System.out.println("----------------------RESULT FROM CLIENTS----------------------");
                 System.out.println("---------------------------------------------------------------");
                while(ind<i)
                {
                ind=fromServer.indexOf(";",ind+1);
                p2=fromServer.substring(ind3+1,ind);
                ind3=ind;
                ind2=0;
                if(NC==0)
                 ind2=fromServer.indexOf(":");
                
                System.out.println("CLIENT NO.: "+NC+" RESPONSE:" + p2.substring(ind2));  
                NC=NC+1;
                }
                System.out.println("TOTAL RESPONSES:  "+NC);
                System.out.println("---------------------------------------------------------------");
            }
             
          
            /*zzzzz end listening to server zzzzzzzz*/
            System.out.print("BYTE/:>");
            comm1=stdIn.readLine();
            if (comm1.equals("CGINFO"))
                {      
                    out.println(ia +"SUPER:"+"CGINFO");                           //-waiting mode
                    comm1=null;
		    continue;
                }else if (comm1.equals("SYSTEM_INFO"))
                {      
                    out.println(ia +"SUPER:"+"SYSTEM_INFO");                           //-waiting mode
                    comm1=null;
		    continue;
                }else if (comm1.equals("REF"))  
                {                                                            //connected and -Idle
		    out.println(ia+"SUPER" +":"+"REF");
                    continue;
                }else if (comm1.equals("WORKNOW"))  
                {                                                            //-processing
		    System.out.println("BYTE/WORK/:>WORKTYPE");
                    p.processInput("PLGLIST:0");
                    System.out.print("BYTE/WORK/:>");
                    //make classes now for different functions
                    comm1=stdIn.readLine();
                    comm1=comm1+":"+NC;
                    comm1=p.processInput(comm1);
                    System.out.println(comm1);
                    out.println(ia+"SUPER" +":WORKNOW:;"+comm1);
                    continue;
                }else if (comm1.equals("PLUG"))  
                {                                                            //-GETTING PLUGIN
		    out.println(ia +":"+"PLUG");
                      System.out.println("---------------------------------------------------------------");
                      System.out.println("AVILABLE WORKTYPE"); 
                      System.out.println("PRIME");
                      System.out.println("MULPL");
                      System.out.println("PLGLIST");
                      System.out.println("---------------------------------------------------------------");
                    continue;
                }else if (comm1.equals("RESULTM"))  
                {                                                            //-GETTIN RESULT
		    out.println(ia +"SUPER"+":RESULTM:;");
                      System.out.println("---REQUEST SENDED----");
                      continue;
                }else if (comm1.equals("HELP"))  
                {                                                            //-GETTIN HELP
		  System.out.println("---------------------------------------------------------------");
                  System.out.println("Login to semiserver:            SUPLOGIN "+1);
                  System.out.println("Refresh Connection :            REF"+2);
                  System.out.println("Get Info of connected client:   CGINFO "+3);
                  System.out.println("Get Plugin Info:                PLUG "+4);
                  System.out.println("For Working On:                 WORKNOW"+5);
                  System.out.println("Commands Avilable:              HELP"+6);
                  System.out.println("Disconnect:                     BYE "+7);
                  System.out.println("---------------------------------------------------------------");
                   out.println(ia +"SUPER"+":"+"0");
                    continue;
                }else if (comm1.equals("BYE"))  
                {                                                            //-Done
		    out.println(ia);
                    out.close();
                    in.close();
                    stdIn.close();
                    kkSocket.close();
                    
                }

         
	      fromUser = ia +"SUPER:"+"SU2";
	    if (fromUser != null) 
            {
                 out.println(fromUser);
            }
        }

        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();
	
	
    }
        static Socket kkSocket = null;
        static PrintWriter out = null;
        public static String Command="NOT_SETYET";
        public static String WorkCommand="NOT_SETYET";
    
    public static void start(String Server, int port, String StartCommand) throws IOException {
        
        //Command=StartCommand;
        
        BufferedReader in = null;
	InetAddress ia=InetAddress.getLocalHost();
	//FileReader fi1=new FileReader("get.add");   //server address
	String add=" ";
	int read1;
         Runtime rt = Runtime.getRuntime();
        Process p1=null;
       
        try {
            kkSocket = new Socket(Server, port);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
		System.out.println("Client:ID: "+ia);
                 out.println(ia+"SUPER:"+"SU1");
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't  I/O connection to: manoj");
            System.exit(1);
        }

        /***********************Command Avilable********************************/
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

         System.out.println("---------------------------------------------------------------");
        System.out.println("Login to semiserver:            SUPLOGIN "+1);
        System.out.println("Refresh Connection :            REF      "+2);
        System.out.println("Get Info of connected client:   CGINFO   "+3);
        System.out.println("Get Plugin Info:                PLUG     "+4);
        System.out.println("For Working On:                 WORKNOW  "+5);
        System.out.println("Get Result:                     RESULTM    "+6);
        System.out.println("Commands Avilable:              HELP     "+6);
        System.out.println("Disconnect:                     BYE      "+7);
        System.out.println("---------------------------------------------------------------");
        
        /*zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz*z*/
     
        String fromServer;
        String fromUser;
        //InputStreamReader  ir = new  InputStreamReader(System.in); 
        //BufferedReader stdIn = new BufferedReader(ir);
        System.out.print("BYTE/:>");
       // String comm=stdIn.readLine();
        String comm1 =null,comm2,comm3;
        String p11,p2;
	int ind=0,ind2,ind3=0,NC=0; 
        plugin p=new plugin();

        while ((fromServer = in.readLine()) != null && StartCommand.equals("SUPLOGIN")) {
            
              System.out.println("Semi-Server:" + fromServer);
       /////listening to server*zzzzzzzzzzzzzzzzzzzzz
            ind=fromServer.indexOf(":");                      //reading
            p11=fromServer.substring(0,ind);                  //header
            p2=fromServer.substring(ind+1);                   //information
       ////////////////////////////////////////////////
            if(p11.equals("CGINFO"))                 
            {
                int i,ii;
                i=fromServer.lastIndexOf(";");
                ind=0;
                ii=0;
                ind3=0;
                NC=0;
                System.out.println("------------------------Clients Online-------------------------");
                while(ind<i)
                {
                ind=fromServer.indexOf(";",ind+1);
                p2=fromServer.substring(ind3+1,ind);
                ind3=ind;
                ind2=0;
                if(NC==0)
                 ind2=fromServer.indexOf(":");
                
                System.out.println("CLIENT NO.: "+NC+" IS:" + p2.substring(ind2));  
                NC=NC+1;
                }
                System.out.println("TOTAL CONNECTED:  "+NC);
                System.out.println("---------------------------------------------------------------");
                
            }else  if(p11.equals("RESULTM"))                 
            {
                int i,ii;
                i=fromServer.lastIndexOf(";");
                ind=0;
                ii=0;
                ind3=0;
                NC=0;
                 System.out.println("---------------------------------------------------------------");
                 System.out.println("----------------------RESULT FROM CLIENTS----------------------");
                 System.out.println("---------------------------------------------------------------");
                while(ind<i)
                {
                ind=fromServer.indexOf(";",ind+1);
                p2=fromServer.substring(ind3+1,ind);
                ind3=ind;
                ind2=0;
                if(NC==0)
                 ind2=fromServer.indexOf(":");
                
                System.out.println("CLIENT NO.: "+NC+" RESPONSE:" + p2.substring(ind2));  
                NC=NC+1;
                }
                System.out.println("TOTAL RESPONSES:  "+NC);
                System.out.println("---------------------------------------------------------------");
            }
             
          
            /*zzzzz end listening to server zzzzzzzz*/
            System.out.print("BYTE/:>");
            //comm1=stdIn.readLine();
            if (Command.equals("CGINFO"))
                {      
                    out.println(ia +"SUPER:"+"CGINFO");                           //-waiting mode
                    Command=null;
		    continue;
                }else if (Command.equals("SYSTEM_INFO"))
                {      
                    out.println(ia +"SUPER:"+"SYSTEM_INFO");                           //-waiting mode
                    comm1=null;
		    continue;
                }else if (Command.equals("REF"))  
                {                                                            //connected and -Idle
		    out.println(ia+"SUPER" +":"+"REF");
                    continue;
                }else if (Command.equals("WORKNOW"))  
                {                                                            //-processing
		    System.out.println("BYTE/WORK/:>WORKTYPE");
                    p.processInput("PLGLIST:0");
                    System.out.print("BYTE/WORK/:>");
                    //make classes now for different functions
                    //comm1=stdIn.readLine();
                    WorkCommand=WorkCommand+":"+NC;
                    WorkCommand=p.processInput(WorkCommand);
                    System.out.println(WorkCommand);
                    out.println(ia+"SUPER" +":WORKNOW:;"+WorkCommand);
                    continue;
                }else if (Command.equals("PLUG"))  
                {                                                            //-GETTING PLUGIN
		    out.println(ia +":"+"PLUG");
                      System.out.println("---------------------------------------------------------------");
                      System.out.println("AVILABLE WORKTYPE"); 
                      System.out.println("PRIME");
                      System.out.println("MULPL");
                      System.out.println("PLGLIST");
                      System.out.println("---------------------------------------------------------------");
                    continue;
                }else if (Command.equals("RESULTM"))  
                {                                                            //-GETTIN RESULT
		    out.println(ia +"SUPER"+":RESULTM:;");
                      System.out.println("---REQUEST SENDED----");
                      continue;
                }else if (Command.equals("HELP"))  
                {                                                            //-GETTIN HELP
		  System.out.println("---------------------------------------------------------------");
                  System.out.println("Login to semiserver:            SUPLOGIN "+1);
                  System.out.println("Refresh Connection :            REF"+2);
                  System.out.println("Get Info of connected client:   CGINFO "+3);
                  System.out.println("Get Plugin Info:                PLUG "+4);
                  System.out.println("For Working On:                 WORKNOW"+5);
                  System.out.println("Commands Avilable:              HELP"+6);
                  System.out.println("Disconnect:                     BYE "+7);
                  System.out.println("---------------------------------------------------------------");
                   out.println(ia +"SUPER"+":"+"0");
                    continue;
                }else if (Command.equals("BYE"))  
                {                                                            //-Done
		    out.println(ia);
                    out.close();
                    in.close();
                    //stdIn.close();
                    kkSocket.close();
                    
                }

         
	      fromUser = ia +"SUPER:"+"SU2";
	    if (fromUser != null) 
            {
                 out.println(fromUser);
            }
        }

        out.close();
        in.close();
       // stdIn.close();
        kkSocket.close();
	
	
    }

}

