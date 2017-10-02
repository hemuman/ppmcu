/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcuGUI;

/**
 * Implemented by Manoj. Keeping original authors name
 * @author PDI
 */

import com.sun.net.httpserver.HttpServer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import mkfs66o96.HTTPServer;
import mkfs66o96.ReadWriteTextFile;
import net.mk.dc.DistributedTaskProcessor;
import net.mk.dcApps.CompileClass;
import net.mk.os.preloadSigar;
import net.mk.ppmcu.GlobalMCUTest;

public class WSTFrame2 extends JFrame{
    TrayIcon trayIcon;
    SystemTray tray;
    //protected String WebServerName="127.0.0.1:8888/multicoreworld";
    protected final String WebServerName="multicoreworld.manojky.net";
    protected int ScreenWidth=1024;
    private final net.mk.ppmcuGUI.WorldSpeedTestGUI worldSpeedTestGUI1;
    String Version;
   public WSTFrame2(String LicenseKey, String Version){
        super("Multi Core App");
        this.Version=Version;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        setUndecorated(true);
        //System.out.println("creating instance");
        //        try{
        //            System.out.println("setting look and feel");
        //            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //        }catch(Exception e){
        //            System.out.println("Unable to set LookAndFeel");
        //        }
        switch (Version) {
            case "Evaluate":
                setSize(1024, height);
                height=700;
                break;
            case "Commercial":
                System.out.println("#screenSize Commercial W="+width+"\t H="+height);
                setSize(width-5, height);
                break;
            case "Free":
                setSize(600, 500);
                height=600;
                break;
            case "HalfScreen":
                setSize(width/2, height);;
                setUndecorated(false);
                break;
        }
        
        addComponentListener(new ComponentAdapter() {
            
            
            // Give the window an elliptical shape.
            // If the window is resized, the shape is recalculated here.
            @Override
            public void componentResized(ComponentEvent e) {
                // setShape(new Ellipse2D.Double(0,0,getWidth(),getHeight()));
                
                  if(System.getProperty("os.name").toLowerCase().contains("win")){
                      
                setShape(new RoundRectangle2D.Double(0, 0, getWidth() - 3, getHeight(), 50, 50));
                  }
            }
        });
        
        

        validate();
        repaint();
        
        //System Tray implementation
        if (SystemTray.isSupported()) {
            System.out.println("SystemTray : Allowed");
            tray = SystemTray.getSystemTray();

            Image image = null;
            try {
                image = Toolkit.getDefaultToolkit().getImage(new URL("http://" + WebServerName + "/MCW.png"));
            } catch (MalformedURLException ex) {
                Logger.getLogger(WSTFrame2.class.getName()).log(Level.SEVERE, null, ex);
            }
            ActionListener exitListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                    System.out.println("Shutting down...");
                    
                        System.exit(0);
                    
                }
            };
            PopupMenu popup = new PopupMenu();
            MenuItem defaultItem = new MenuItem("Open");
            defaultItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setVisible(true);
                    setExtendedState(JFrame.NORMAL);
                }
            });
            popup.add(defaultItem);

            //Run test from tray
            defaultItem = new MenuItem("Run Test");
            defaultItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    worldSpeedTestGUI1.runtest();
                }
            });
            popup.add(defaultItem);

            defaultItem = new MenuItem("Exit");
            defaultItem.addActionListener(exitListener);
            popup.add(defaultItem);
            
            setTitle("Multi Core Apps [ManojKy.net]");
            trayIcon = new TrayIcon(image, "Multi Core World App", popup);
            trayIcon.setImageAutoSize(true);

            MouseListener ml;
            ml = new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    // System.out.println("Tray icon: Mouse clicked");
                    setVisible(true);
                    setExtendedState(JFrame.NORMAL);
                }

                public void mouseEntered(MouseEvent e) {
                    //System.out.println("Tray icon: Mouse entered");
                    trayIcon.setToolTip("MCW Rank : " + worldSpeedTestGUI1.CURRENT_RANK_RATIO + "\n Run test to update rank");
                    // JOptionPane.showMessageDialog(rootPane, "System Rank:"+worldSpeedTestGUI1.CURRENT_RANK_RATIO);
                }

                public void mouseExited(MouseEvent e) {
                    // System.out.println("Tray icon: Mouse exited");
                }

                public void mousePressed(MouseEvent e) {
                    // System.out.println("Tray icon: Mouse pressed");
                }

                public void mouseReleased(MouseEvent e) {
                    // System.out.println("Tray icon: Mouse released");
                }
            };
            trayIcon.addMouseListener(ml);

            MouseMotionListener mml;
            mml = new MouseMotionListener() {
                public void mouseDragged(MouseEvent e) {
                    System.out.println("Tray icon: Mouse dragged");
                }

                public void mouseMoved(MouseEvent e) {
                    // System.out.println("Tray icon: Mouse moved");
                    // worldSpeedTestGUI1.getAllRankRatio();
                    trayIcon.setToolTip("MCW Rank : " + worldSpeedTestGUI1.CURRENT_RANK_RATIO + "\nRun test to update rank");
                }
            };
            trayIcon.addMouseMotionListener(mml);

        } else {
            System.out.println("SystemTray : Not Allowed");
        }
        addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent e) {
                if (e.getNewState() == ICONIFIED) {
                    try {
                        tray.add(trayIcon);
                        setVisible(false);
                        // System.out.println("Application added to system tray");
                    } catch (AWTException ex) {
                        System.out.println("Application can not be added to system tray");
                    }
                }
                if (e.getNewState() == 7) {
                    try {
                        tray.add(trayIcon);
                        setVisible(false);
                        // System.out.println("Application added to system tray");
                    } catch (AWTException ex) {
                        System.out.println("Application can not be added to system tray");
                    }
                }
                if (e.getNewState() == MAXIMIZED_BOTH) {
                    tray.remove(trayIcon);
                    setVisible(true);
                    // System.out.println("Removed: Tray icon");
                }
                if (e.getNewState() == NORMAL) {
                    tray.remove(trayIcon);
                    setVisible(true);
                    // System.out.println("Removed: Tray icon");
                }
            }
        });
        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage(new URL("http://" + WebServerName + "/MCW.png")));//.getImage("/MCW_Round24x23px.png"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(WSTFrame2.class.getName()).log(Level.SEVERE, null, ex);
        }

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        //setTitle("Multi Core Speed Test");
        setLocationByPlatform(true);
        setResizable(false);
        switch (Version) {
            case "Evaluate":
                worldSpeedTestGUI1 = new net.mk.ppmcuGUI.WorldSpeedTestGUI(WebServerName, LicenseKey,"1024",true);/*height=800*/
                setTitle("Multi Core Apps");
                System.out.println("#Evaluation Mode -Main");
                setSize(width-100,height);
                //setMaximu,mSize(width-100,height-100) ;
                setBounds(0, 0, 1024,768);
                setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );
                setResizable(true);
                break;
            case "Commercial":
                setTitle("Multi Core Apps");
                worldSpeedTestGUI1 = new net.mk.ppmcuGUI.WorldSpeedTestGUI(WebServerName, LicenseKey,String.valueOf(width),true);/*height=800*/
                System.out.println("#Commercial Mode -Main "+width);
                setSize(width-100,height);
                //setMaximu,mSize(width-100,height-100) ;
                setBounds(0, 0, width-100,height-100);
                setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );
                setResizable(true);
                break;
                
             case "HalfScreen":
                 setTitle("Multi Core Apps");
                worldSpeedTestGUI1 = new net.mk.ppmcuGUI.WorldSpeedTestGUI(WebServerName, LicenseKey,"HalfScreen",true);/*height=800*/
                System.out.println("#HalfScreen Mode -Main "+width/2);
                setSize(width/2,height);
                //setMaximu,mSize(width-100,height-100) ;
                setBounds(0, 0, width/2,height-100);
                //setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );
                setResizable(true);
                break;
                 
            default:
                setTitle("Multi Core Apps");
                worldSpeedTestGUI1 = new net.mk.ppmcuGUI.WorldSpeedTestGUI(WebServerName, LicenseKey,"Free",true);
                System.out.println("#Free Mode -Main");
                break;
        }
       
      
        JPanel bgPanel = new BgPanel();
        bgPanel.setLayout(new BorderLayout());
        bgPanel.add(worldSpeedTestGUI1, BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addComponent(bgPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(bgPanel, javax.swing.GroupLayout.DEFAULT_SIZE, height, Short.MAX_VALUE));

        pack();
        
        repaint();
    }//[589, 445]
    
    int RectRound=50;
     public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
         g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
           g2.setStroke(new BasicStroke(3));
       g2.drawRoundRect(5, 5, getHeight()-10, getWidth()-10, RectRound, RectRound);
       g2.fillRoundRect(5, 5, getHeight()-10, getWidth()-10, RectRound, RectRound);
       
       
    }
    
    public static void main(String[] args) throws IOException, JSONException{
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WSTFrame2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WSTFrame2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WSTFrame2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WSTFrame2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /**
         * Yes gvreeebsep pditestprofile52413 10-8-4-14-10-12-12-12-4-7 Commercial Yes AllUsersAllPass AllUsers AllPass Commercial - Mac Yes gvreeebsep
         * pditestprofile52413 10-8-4-14-10-12-12-12-4-7 Commercial Yes gvreeebsep AllUsers 10-8-4-14-10-12-12-12-4-7 Commercial Yes gvreeebsep AllUsers
         * 10-8-4-14-10-12-12-12-4-7 Commercial NoGraphics-StartDTaskProcessor
         *
         * //To implement new convention 6/27/2013: #WebExec Yes #LicKy gvreeebsep #AccInfo AllUsers #LicKeySpec 10-8-4-14-10-12-12-12-4-7 #Mode Commercial
         * #task NoGraphics-StartDTaskProcessor #AppLib ./ #AppLibFile MCWAppsList OR #WebExec Yes #LicKy gvreeebsep #AccInfo AllUsers #LicKeySpec
         * 10-8-4-14-10-12-12-12-4-7 #Mode Commercial #task NoGraphics-StartDTaskProcessor#AppLib ./ #AppLibFile MCWAppsList //Concat all and the Use split
         * string and prepare JSONObject for input
         */
        String ALL_INPUT = "";
        //Check if arguments have been supplied.
        //If no arguments are supplied then read from argumnet file named ppmcu_arguments
        if(args.length>10)
        {
            for (int i = 0; i < args.length; i++) {
            ALL_INPUT = ALL_INPUT + "#" + args[i];
            System.out.println(args[i]);
        }
        }else{
           
            JOptionPane.showMessageDialog(null, "Reading arguments from ppmcu_arguments.txt "+new File("ppmcu_arguments.txt").exists()
                    +"\n In App Dir="+System.getProperty("user.dir"));
            ALL_INPUT=ReadWriteTextFile.getContents(new File("ppmcu_arguments.txt"));
            ALL_INPUT=ALL_INPUT.replaceAll(" ", "#");
            JOptionPane.showMessageDialog(null, "Supplied Args: "+ALL_INPUT);
        }
        
        
        System.out.println(ALL_INPUT);

        //Split Strings
        String[] ALL_INPUT_PARAMS = ALL_INPUT.split("_");
        JSONObject KeyVals = new JSONObject();
        //Loop through
        for (int i = 1; i < ALL_INPUT_PARAMS.length; i++) {
            System.out.println(ALL_INPUT_PARAMS[i]);
            String[] KeyValPair = ALL_INPUT_PARAMS[i].split("#");// Split by Space
            KeyVals.put(KeyValPair[0], KeyValPair[1]);
        }

        //Store it in MCWAppVars so that it can be accessed when needed
        MCWConfClass.MCWAppVars = KeyVals;

        WSTFrame2 wSTFrame2 = null;

        MCWConfClass.WEB_EXECUTION = Boolean.parseBoolean(KeyVals.getString(MCWConfClass.WebExec));
        if(MCWConfClass.MCWAppVars.has("USESIGARAPI"))// To avoid DLL/lib/so conflicts.
            MCWConfClass.setUSE_SIGAR_API(MCWConfClass.MCWAppVars.getBoolean("USESIGARAPI"));
        else
            MCWConfClass.setUSE_SIGAR_API(true);
        
        /*HTTP Server***/
        try {
            HTTPServer.RunServer(MCWConfClass.HTTPServerPort,"");
        } catch (Exception ex) {
            Logger.getLogger(WSTFrame2.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*HTTP Server End****/
        
        //CodeBase codeBase = new MCWConfClass(args[1], args[2], args[3]); //Check license Key and set values //Only for windows

        //No-Graphics Mode
        if (MCWConfClass.NO_GRAPHICS_RUN_DTProcessor.equalsIgnoreCase(MCWConfClass.MCWAppVars.getString(MCWConfClass.task))) { // Only to run the task processor server.
            System.out.println("#DistributedTaskProcessor..");
            JSONObject ThisSysSpec = new GlobalMCUTest().printMachineInfo(false, true);
            HTTPServer.StaticResponse=ThisSysSpec.toString();
            try {

                //Read configuration file
                InputStream in = null;
                if (!MCWConfClass.has(MCWConfClass.AppLibFile)) {
                    in = WorldSpeedTestGUI.class.getClass().getResourceAsStream("/net/mk/ppmcuGUI/MCWAppsList");
                } else {
                    //ReadWriteTextFile.getContents(new File(MCWConfClass.getValue(MCWConfClass.AppLibFile)));
                    System.out.println(MCWConfClass.getValue(MCWConfClass.AppLibFile) + " exists : " + new File(MCWConfClass.getValue(MCWConfClass.AppLibFile)).exists());
                    in = new ByteArrayInputStream(ReadWriteTextFile.getContents(new File(MCWConfClass.getValue(MCWConfClass.AppLibFile))).getBytes());
                }
                if (in != null) { //System.out.println(CompileClass.getStringFromInputStream(in));
                    String data = CompileClass.getStringFromInputStream(in);
                    System.out.println("MCWAppsList: \n" + data);
                    JSONObject AppList = new JSONObject(data);
                    JSONArray userAppnames = AppList.names();
                    String[] AllApps = new String[userAppnames.length()];
                    for (int i = 0; i < userAppnames.length(); i++) {
                        //Get all custom classes
                        AllApps[i] = AppList.getJSONObject(userAppnames.getString(i)).getString("ClassName");
                    }
                    new DistributedTaskProcessor(MCWConfClass.MCWAppVars.getString(MCWConfClass.AccInfo), ThisSysSpec, 5559, AllApps).startServer();
                } else {
                    new DistributedTaskProcessor(MCWConfClass.MCWAppVars.getString(MCWConfClass.AccInfo), ThisSysSpec, 5559).startServer();
                }

                System.out.println("#DistributedTaskProcessor..Ended");
            } catch (Exception ex) {
                Logger.getLogger(WSTFrame2.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (System.getProperty("os.name").toLowerCase().contains("win") | (!MCWConfClass.WEB_EXECUTION)) { //OR Local Execution, assuming all the dlls/so/lib are in same folder.

            if (System.getProperty("os.name").toLowerCase().contains("win"))//for Mac local execution
            {
                new preloadSigar(System.getProperty("user.home") + "\\ppmcu\\");
            }
    
            if (!MCWConfClass.isACTIVATE_SSC_SCREEN()) {
                wSTFrame2 = new WSTFrame2(MCWConfClass.MCWAppVars.getString(MCWConfClass.AccInfo), "Evaluate");///All Evaluate now Free only for web
            }
            if (MCWConfClass.isACTIVATE_SSC_SCREEN()) {
                wSTFrame2 = new WSTFrame2(MCWConfClass.MCWAppVars.getString(MCWConfClass.AccInfo), "Commercial");//Commercial
            }
            
            if (MCWConfClass.MCWAppVars.getString(MCWConfClass.Mode).contentEquals("HalfScreen")) {
                wSTFrame2 = new WSTFrame2(MCWConfClass.MCWAppVars.getString(MCWConfClass.AccInfo), "HalfScreen");//Commercial
            }

            if (MCWConfClass.isACTIVATE_DISTRIBUTED_COMPUTING_NODE()) {// wSTFrame2=new WSTFrame2(MCWConfClass.MCWAppVars.getString(MCWConfClass.AccInfo),"Commercial"); wSTFrame2.setExtendedState( wSTFrame2.getExtendedState()|JFrame.MAXIMIZED_BOTH );
            }

            wSTFrame2.setLocation(0, 0);
            wSTFrame2.setVisible(true);

        } else {
            //JOptionPane.showMessageDialog(null, "Invalid Software License Key");
            if (!MCWConfClass.isACTIVATE_SSC_SCREEN()) {
                new WSTFrame2(MCWConfClass.MCWAppVars.getString(MCWConfClass.AccInfo), "Evaluate").setVisible(true);
            } else {
                new WSTFrame2(MCWConfClass.MCWAppVars.getString(MCWConfClass.AccInfo), "Evaluate").setVisible(true);
            }

        }

    }

}

class BgPanel extends JPanel {
    Image bg = new ImageIcon(getClass().getResource("/net/mk/icons/Control16x16g.png")).getImage();//getClass().getResource("/net/mk/icons/Control16x16g.png"))
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
}

/**
 * TO KILL A PROCESS
 *  Runtime rt = Runtime.getRuntime();
  if (System.getProperty("os.name").toLowerCase().indexOf("windows") > -1) 
     rt.exec("taskkill " +....);
   else
     rt.exec("kill -9 " +....);
 */