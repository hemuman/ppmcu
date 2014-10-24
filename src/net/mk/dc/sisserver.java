package net.mk.dc;

import java.net.*;
import java.io.*;

/********************************************************************************************************************************************/
// MANOJ KUMAR MECH ENGG NIT SILCHAR manoj_nits@yahoo.com//
/******************************************************************/
class sisprotocol {
    private static final int WAITING = 0;
    private static final int SENTKNOCKKNOCK = 2,ginfo=1;
    private int state = WAITING;
    
    public String processInput(String theInput) throws IOException {
        String theOutput = null;
        String s2=null;
        String p1,p2="REF",p3="null";
        int ind=0;
        
        
        
        /********************client ID:***************************/
        if (state == WAITING) {
            
            theOutput = "Connected via Protocol:BYTE";
            state = ginfo;
            
        }
        else if(state==ginfo)                     //Registration
        {
            try{
                
                RandomAccessFile r4= new RandomAccessFile("clientcon.man","rw");
                //System.err.println("Co");
                r4.seek(r4.length());
                r4.writeBytes(theInput+";");
                System.err.println(theInput);
                r4.close();
            } catch(Exception w) {
                
            }
            state = SENTKNOCKKNOCK;
            theOutput = "Registered:1";
            return theOutput;
            
        } else if (state == SENTKNOCKKNOCK) {
            String s3=" ";
            /**************file handling*****************/
            try{
                
                RandomAccessFile r1= new RandomAccessFile("answer.man","rw");
                //System.err.println("Co");
                r1.seek(r1.length());
                r1.writeBytes(theInput);
                
                //System.err.println("Cou");
                try{
                    Thread.sleep(3000);
                }catch(Exception w){}
                /*-----------------------------------cH3ARA3CTECA3TCHER----------------------------------*/
                System.out.println("Server respond to :"+theInput);
                
                ind=theInput.indexOf(":");
                p1=theInput.substring(0,ind);
                p2=theInput.substring(ind+1);
                if(theInput.lastIndexOf(":")>ind  ) {  //for WORKNOW
                    System.err.println("S"+p2.substring(0)+" in mode"+p2+" & I am in "+ind+"here"+theInput.lastIndexOf(":"));
                    p3=p2.substring(0,7);
                    
                }
                System.err.println(p3);
                /*---------------------------------------------------------------------------------------*/
                if(p3.equals("BYEBYTE")) {
                    // System.err.println("Super Client:"+p1+" in mode"+p2+" & I am in here"+2);
                    try{
                        File f= new File("input.man");
                        f.delete();
                        RandomAccessFile r4= new RandomAccessFile("input.man","rw");
                        //System.err.println("Co");
                        
                        r4.seek(0);
                        r4.writeBytes(p3);
                    } catch(Exception w){}
                    return "0:0";
                }
                
                if(p2.equals("SU1")) {
                    System.err.println("Super Client:"+p1+" in mode"+p2+" & I am in here"+2);
                    
                }else if(p2.equals("SU2")) {
                    r1.seek(r1.length());
                    r1.writeBytes("??");
                }
                
                
                if (p2.equals("CGINFO"))         //Client Info request from Super cliebt
                {
                    FileReader fi=new FileReader("clientcon.man");
                    int read;
                    while((read=fi.read())!=-1)
                        s3=s3+(char)read;
                    
                    fi.close();
                    theOutput = "CGINFO:"+s3;
                    return theOutput;
                    
                }else if (p2.equals("SYSTEM_INFO"))         //Client Info request from Super cliebt
                {
                    FileReader fi=new FileReader("clientcon.man");
                    int read;
                    while((read=fi.read())!=-1)
                        s3=s3+(char)read;
                    
                    fi.close();
                    theOutput = "CGINFO:"+s3;
                    return theOutput;
                    
                }else  if (p2.equals("PLUG"))         //Plugin Info request from Super cliebt
                {
                    FileReader fi=new FileReader("plugin.man");
                    int read;
                    while((read=fi.read())!=-1)
                        s3=s3+(char)read;
                    fi.close();
                    theOutput = "PLUG"+s3;
                    return theOutput;
                    
                }else if (p3.equals("WORKNOW"))         //Input to the clients
                {
                    File f= new File("input.man");
                    f.delete();
                    try{
                        RandomAccessFile r2= new RandomAccessFile("input.man","rw");
                        //System.err.println("Co");
                        
                        r2.seek(r2.length());
                        r2.writeBytes(p2);
                    } catch(Exception w){}
                    theOutput = "BROADCASTED:"+2;
                    p2="3";
                    p3="m";
                    return theOutput;
                    
                }
                //-----------------------------------------Dealing with sisclients--------------------------------------//
                else if(p2.equals("1")) {
                    System.err.println("Client:"+p1+" in mode"+p2+" & I am in here"+2);
                    theOutput= "Client"+p1+"Is Connected and Idle";
                    return theOutput;
                }else if(p2.equals("3")) {
                    
                    
                    File f= new File("input.man");
                    if(f.exists() && f.isFile()) {
                        /*-------------------------------------------Finding Client ID------------------------------------------*/
                        int ind2,ind3=0,NC=0;
                        int i,ii;
                        FileReader fi1=new FileReader("clientcon.man");
                        int read;
                        while((read=fi1.read())!=-1)
                            s3=s3+(char)read;
                        
                        fi1.close();
                        i=s3.lastIndexOf(";");
                        ind=0;
                        ii=0;
                        ind3=0;
                        NC=0;
                        while(ind<i) {
                            ind=s3.indexOf(";",ind+1);
                            p2=s3.substring(ind3+1,ind);
                            ind3=ind;
                            ind2=0;
                            if(NC==0)
                                ind2=s3.indexOf("");
                            if(p1.length()<=(p2.substring(ind2)).length()) {
                                if(p1.equals(p2.substring(ind2,p1.length()))) {
                                    System.out.println(p1+"Your ID: "+NC+"Matched to:" + p2.substring(ind2));
                                    
                                    break;
                                }
                            }
                            
                            System.out.println(p1+"CLIENT NO.: "+NC+" IS:" + p2.substring(ind2));
                            NC=NC+1;
                        }
                        
                        
                        
                        FileReader fi=new FileReader("input.man");
                        s3="";
                        while((read=fi.read())!=-1)
                            s3=s3+(char)read;
                        i=s3.lastIndexOf(";");
                        System.err.println("i s3aw"+s3);
                        ind3=0;
                        ii=0;
                        ind=0;
                        while(ii!=(NC+1)) {                   //find its input in file
                            ind3=ind;
                            ind=s3.indexOf(";",ind+1);
                            ii++;
                            System.err.println(ind+"i s3aw"+s3+ind3);
                            
                        }
                        p2=s3.substring(ind3+1,ind);
                        
                        theOutput= "WORKNOW:"+p2;
                        //System.err.println("i s3aw"+s3);
                        fi.close();
                    }else {
                        theOutput= ":3";
                        System.err.println("Client:"+p1+" in Idle mode"+p2+" & I am in"+0);
                    }
                    
                    return theOutput;
                    /*---------------------------------TASK SEND TO SISCLIENT-----------------------------------------------*/
                }else if (p2.equals("5")) {
                    System.err.println("Client:"+p1+" in mode"+p2+" & processing mode & I am in"+4+" waiting mode");
                    theOutput= ":5";
                    return theOutput;
                }else if (p2.equals("7")) {
                    System.err.println("Client:"+p1+" in mode"+p2+" & Data is processed & I am in"+6+" waiting mode");
                }
                
                r1.close();
                
            }catch (IOException e) {
                System.err.println("Couldn't  I/O connection to: plugin.man");
                System.exit(1);
            }
            /******************-------------------------------------------------------------*************************/
            if (p2.equals("REF"))         //Client Info request from Super cliebt
            {

                theOutput = "REF:Refreshed";
                return theOutput;
                
            }
            if (p3.equals("JOBDONE"))         //Input to the clients
            {
                
                System.err.println(p3);
                try{
                    RandomAccessFile r2= new RandomAccessFile("answers.man","rw");
                    
                    r2.seek(r2.length());
                    r2.writeBytes(p2);
                } catch(Exception w){}
                theOutput = ":3";
                return theOutput;
                
            }
            if (p3.equals("RESULTM"))         //Results
            {
                
                System.err.println(p3);
                FileReader fi1=new FileReader("answers.man");
                int read;
                while((read=fi1.read())!=-1)
                    s3=s3+(char)read;
                theOutput = "RESULTM:"+s3.substring(9);
                File f1= new File("answers.man");
                f1.delete();
                return theOutput;
                
            }
            theOutput ="BYTE:Bad Request";
            
        } else {
            theOutput = "Bye.";
            state = WAITING;
        }
        
        
        
        return theOutput;
    }
}
/******************************************************************/

class sismulthread extends Thread {
    private Socket socket = null;
    
    
    public sismulthread(Socket socket) {
        super("KKMultiServerThread");
        this.socket = socket;
    }
    
    public void run() {
        
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //Message to Client
            BufferedReader in = new BufferedReader(
            new InputStreamReader(
            socket.getInputStream()));          //read from client
            
            String inputLine, outputLine;
            sisprotocol kkp = new sisprotocol();
            outputLine = kkp.processInput(null);
            out.println(outputLine);
            
            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
            }
            out.close();
            in.close();
            socket.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
/*****************************************interface*******************************************/
/****************************main server****************************/
public class sisserver {
    
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;
        
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("             THIS#IS#M");
        System.out.println("             MADE$BY$M");
        System.out.println("             ANOJ%KUM%");
        System.out.println("             AR^03120^");
        System.out.println("             26&MECH&&");
        System.out.println("             --BYTE---");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
        
        System.out.println("----------------------------------------------------");
        System.out.println("Semi-Server Commands:");
        System.out.println("Add Plugin->           ADPLG");
        System.out.println("Edit Plugins->         EDPLG");
        System.out.println("Show Plugins->         SHPLG");
        System.out.println("Start Semi-Server ->   SMSR");
        System.out.println("----------------------------------------------------");
        InputStreamReader  ir = new  InputStreamReader(System.in);
        BufferedReader stdIn = new BufferedReader(ir);
        System.out.print("BYTE/:>");
        String comm=stdIn.readLine();
        String s3;
        int read;
        
        if (comm.equals("SHPLG") ) {
            FileReader fsrvr=new FileReader("plugsrvr.man");
            s3="";
            System.out.println("Plugins Avilable:");
            while((read=fsrvr.read())!=-1) {
                s3=s3+(char)read;
                if((char)read==':') {
                    System.out.println(s3);
                    s3="";
                }
            }
        }
        if(comm.equals("ADPLG"))
        {
             try{
                 comm=stdIn.readLine();
                RandomAccessFile r4= new RandomAccessFile("plugsrvr.man","rw");
                //System.err.println("Co");
                r4.seek(r4.length());
                r4.writeBytes(comm+":");
                System.err.println("");
                r4.close();
            } catch(Exception w) {
                
            }
        }
        File  f= new File("clientcon.man");
        f.delete();
        File f1= new File("input.man");
        f1.delete();
        File f2= new File("answers.man");
        f2.delete();
        try{
            RandomAccessFile r2= new RandomAccessFile("answers.man","rw");
            r2.seek(r2.length());
            r2.writeBytes(";");
            r2.close();
        } catch(Exception w){
        System.out.println("Exception:");
        }
        System.out.println("<--Server Started Now-->");
        while (listening) {
            new sismulthread(serverSocket.accept()).start();
                    }
        
        serverSocket.close();
    }

    
    public static void start(int PortNumber,String Command) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;
        
        try {
            serverSocket = new ServerSocket(PortNumber);//4444
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("             THIS#IS#M");
        System.out.println("             MADE$BY$M");
        System.out.println("             ANOJ%KUM%");
        System.out.println("             AR^03120^");
        System.out.println("             26&MECH&&");
        System.out.println("             --BYTE---");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("  ");
        
        System.out.println("----------------------------------------------------");
        System.out.println("Semi-Server Commands:");
        System.out.println("Add Plugin->           ADPLG");
        System.out.println("Edit Plugins->         EDPLG");
        System.out.println("Show Plugins->         SHPLG");
        System.out.println("Start Semi-Server ->   SMSR");
        System.out.println("----------------------------------------------------");
        InputStreamReader  ir = new  InputStreamReader(System.in);
        BufferedReader stdIn = new BufferedReader(ir);
        System.out.print("BYTE/:>");
        
        try{
            RandomAccessFile r2= new RandomAccessFile("answers.man","rw");
            r2.seek(r2.length());
            r2.writeBytes(";");
            r2.close();
        } catch(Exception w){
        System.out.println("Exception:");
        }
        System.out.println("<--Server Started Now-->");
        while (listening) {
            new sismulthread(serverSocket.accept()).start();
        }
        
        serverSocket.close();
    }

}

