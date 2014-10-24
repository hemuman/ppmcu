/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dcApps;

import BinaryDataProcessing.ReadWriteTextFile;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import json.JSONException;
import json.JSONObject;
import net.mk.DTasks.DTaskMaths;
import net.mk.DTasks.OSAppAsDCTask;
import net.mk.DTasks.OSAppGraphicsDCTask;
import net.mk.dc.DistributedTaskPool;
import net.mk.dc.DistributedTaskService;
import net.mk.os.OSAppGraphics;
import net.mk.os.RunningAppsPanel;
import net.mk.ppmcu2D.ScreenCapture;
import net.mk.ppmcu2D.UIToolKit;
import net.mk.ppmcuGUI.ButtonTabComponent;
import net.mk.ppmcuGUI.MCWConfClass;

/**
 *
 * @author PDI
 */
public class DCGUI extends javax.swing.JPanel {

    private static JSONObject[] RemoteSysSpecs;

    //Make sure no one else can modify this.
    public static JSONObject[] getRemoteSysSpecs() {
        return RemoteSysSpecs;
    }

    public static void setRemoteSysSpecs(JSONObject[] RemoteSysSpecs_, String _LICENSE_KEY) {
        // if()
        RemoteSysSpecs = RemoteSysSpecs_;
    }

    public static JSONObject getRemoteSystemSpec(String SystemName) {

        for (int i = 0; i < RemoteSysSpecs.length; i++) {
            if (RemoteSysSpecs[i].has("IP_ADDRESS")) {
                try {
                    if (RemoteSysSpecs[i].getString("IP_ADDRESS").contentEquals(SystemName)) {
                        return RemoteSysSpecs[i];
                    }
                } catch (JSONException ex) {
                    Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("#DCGUI RemoteSysSpecs doesn't have IP_ADDRESS for " + i);
            }
        }

        System.out.println("#DCGUI RemoteSysSpecs doesn't have" + SystemName);
        return new JSONObject() {
        };

    }

    Thread GetSysInfoService;
    BufferedImage[] CPUUsages;
    double[] CPUSpeeds;
    double totalCPUSpeed = 0;
    double AvgCPUSpeed = 0;
    long[] RAMSizes;
    long totalRAM = 0;
    long AvgRAM = 0;
    int[] NumberOfCores;
    int TotalProcessors = 0;
    int AvgProcessors = 0;
    String LICENSE_KEY;
    double tempVar0360 = 0;
    int SpecCount = 0;
    int ONLINE_SYSTEM = -1;
    int UPDATE_MPP_CLIENT_LIST_IN = 300000;
    int ACTIVE_MACHINES = 0;
    public boolean MONITOR_ALL = false;
    DefaultListModel dlm;
    boolean RunGraphicsThread = true;

    /**
     * Creates new form DCGUI The MCW Project is designed for: MPP (massively parallel processing) is the coordinated processing of a program by multiple
     * processor s that work on different parts of the program, with each processor using its own operating system and memory . Typically, MPP processors
     * communicate using some messaging interface. In some implementations, up to 200 or more processors can work
     *
     * same application. An "interconnect" arrangement of data paths allows messages to be sent processors. Typically, the setup for MPP is more complicated,
     * requiring thought about how tion a common database among processors and how to assign work among the processors. An M m is also known as a "loosely
     * coupled" or "shared nothing" system.
     *
     * SMP (symmetric
     *
     * ) is the processing of programs by multiple processors that share a common operating system and memo ymmetric (or "tightly coupled") multiprocessing, the
     * processors share memory and the I/O bus path. A single copy of the operating system is in charge of all the processors. SMP, also kn "shared everything"
     * system, does not usually exceed 16 processors. SMP systems are conside er than MPP systems for onl saction processing (OTP) in which many users access
     * the same database in a relatively simple set ctions. An advantage of SMP for this purpose is the ability to dynamically balance the workload a uters (and
     * as a result serve more users faster).
     *
     * Historically, researchers movCOLLECTED_RESULTS e-core to many-core      *
     * break their calculations into smaller problems that could be parceled out separately to different processing an approach referred to as parallel
     * computing.
     */
    public DCGUI(String _LICENSE_KEY, JSONObject[] _RemoteSysSpecs, boolean _MONITOR_ALL) {
        this.RemoteSysSpecs = _RemoteSysSpecs;
        this.LICENSE_KEY = _LICENSE_KEY;
        CPUUsages = new BufferedImage[RemoteSysSpecs.length];
        CPUSpeeds = new double[RemoteSysSpecs.length];
        RAMSizes = new long[RemoteSysSpecs.length];
        NumberOfCores = new int[RemoteSysSpecs.length];
        this.MONITOR_ALL = _MONITOR_ALL;
        dlm = new DefaultListModel();

        initComponents();

        /**
         * Gather Data
         */
//        getClusterPreview();
//        
//        Thread GetMPPCLIENTInfoService = new Thread(new Runnable() {
//            public void run() 
//            {
//                try {
//                    while(true)
//                    { 
//                        if(MONITOR_ALL)
//                        { Thread.sleep(UPDATE_MPP_CLIENT_LIST_IN);
//                          getClusterPreview();
//                        }
//                    
//                    }
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                
//                } 
//            
//        });
//           
//      GetMPPCLIENTInfoService.start();
//        
        /**
         * Read and populate status
         */
        //runGraphicsThread();
        /**
         * Add initial MPP Clients
         */
        for (int i = 0; i < RemoteSysSpecs.length; i++) {
            try {
                dlm.addElement(RemoteSysSpecs[i].getString("IP_ADDRESS"));
            } catch (JSONException ex) {
                Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public DCGUI(String WebServerName, String _LICENSE_KEY, JSONObject _RemoteSysSpec) {
        this.RemoteSysSpecs = new JSONObject[]{};
        this.LICENSE_KEY = _LICENSE_KEY;
        CPUUsages = new BufferedImage[RemoteSysSpecs.length];
        CPUSpeeds = new double[RemoteSysSpecs.length];
        RAMSizes = new long[RemoteSysSpecs.length];
        NumberOfCores = new int[RemoteSysSpecs.length];
        this.MONITOR_ALL = true;
        dlm = new DefaultListModel();

        initComponents();

        /**
         * Add initial MPP Clients
         */
        for (int i = 0; i < RemoteSysSpecs.length; i++) {
            try {
                dlm.addElement(RemoteSysSpecs[i].getString("IP_ADDRESS"));
            } catch (JSONException ex) {
                Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        DCAppLogger.updateServerRespose(RemoteSysSpecs);

    }

    /**
     * In a thread: For each RemoteSysSpec 1. Check is alive, 2. Get Processor Speeds 3. Get RAM 7. Get Processor Availability Graphics
     */
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ScanNetwork = new javax.swing.JButton();
        IP_ADDRESS_RANGE = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        AddRemoteMachines = new javax.swing.JButton();
        StartMonitor = new javax.swing.JButton();
        Message = new javax.swing.JLabel();
        LogTheHealth = new javax.swing.JCheckBox();
        AccessWindows = new javax.swing.JTabbedPane();
        ConnectedMachinesPanel = new javax.swing.JPanel();
        SHOW_PROCESS_DETAILS = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();

        ScanNetwork.setText("1a. Scan Network");
        ScanNetwork.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ScanNetworkActionPerformed(evt);
            }
        });

        IP_ADDRESS_RANGE.setText("192.168.1");

        jList1.setModel(dlm);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        AddRemoteMachines.setText("1b. Add");
        AddRemoteMachines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddRemoteMachinesActionPerformed(evt);
            }
        });

        StartMonitor.setText("2. Start Monitor");
        StartMonitor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartMonitorActionPerformed(evt);
            }
        });

        Message.setForeground(new java.awt.Color(6, 112, 154));
        Message.setText("Message:");

        LogTheHealth.setText("Log Health");

        ConnectedMachinesPanel.setOpaque(false);
        ConnectedMachinesPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ConnectedMachinesPanelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout ConnectedMachinesPanelLayout = new javax.swing.GroupLayout(ConnectedMachinesPanel);
        ConnectedMachinesPanel.setLayout(ConnectedMachinesPanelLayout);
        ConnectedMachinesPanelLayout.setHorizontalGroup(
            ConnectedMachinesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        ConnectedMachinesPanelLayout.setVerticalGroup(
            ConnectedMachinesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        AccessWindows.addTab("Status", ConnectedMachinesPanel);

        SHOW_PROCESS_DETAILS.setText("Process Details");

        jButton1.setText("Reload List");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(StartMonitor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ScanNetwork, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(IP_ADDRESS_RANGE)
                    .addComponent(AddRemoteMachines, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LogTheHealth)
                            .addComponent(SHOW_PROCESS_DETAILS)))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Message, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(137, Short.MAX_VALUE))
                    .addComponent(AccessWindows)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(SHOW_PROCESS_DETAILS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LogTheHealth)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ScanNetwork)
                        .addGap(5, 5, 5)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(IP_ADDRESS_RANGE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AddRemoteMachines))
                    .addComponent(AccessWindows, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StartMonitor)
                    .addComponent(Message)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ScanNetworkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ScanNetworkActionPerformed
        try {
            //InetAddress.getLocalHost().
            RemoteSysSpecs = DistributedTaskPool.ScanLocalNetwork(IP_ADDRESS_RANGE.getText());
            dlm.removeAllElements();//Empty the list
            for (int i = 0; i < RemoteSysSpecs.length; i++) {
                dlm.addElement(RemoteSysSpecs[i].getString("IP_ADDRESS"));
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_ScanNetworkActionPerformed

    private void AddRemoteMachinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddRemoteMachinesActionPerformed
        try {
            JSONObject testMachine = new JSONObject();
            testMachine.put("IP_ADDRESS", IP_ADDRESS_RANGE.getText());
            testMachine.put("PC_NAME", IP_ADDRESS_RANGE.getText());

            if (new DistributedTaskService(LICENSE_KEY, testMachine, 5559).isServerLive(1000) == DistributedTaskService.READY_FOR_COMPUTING) {

                dlm.addElement(IP_ADDRESS_RANGE.getText());
                Message.setText(IP_ADDRESS_RANGE.getText() + " Added to list!");
            } else {
                Message.setText(IP_ADDRESS_RANGE.getText() + " is not ready!");
            }
        } catch (IOException ex) {
            Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_AddRemoteMachinesActionPerformed

    private void StartMonitorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartMonitorActionPerformed

        RemoteSysSpecs = new JSONObject[dlm.size()];
        for (int i = 0; i < dlm.size(); i++) {
            try {
                RemoteSysSpecs[i] = new JSONObject();
                RemoteSysSpecs[i].put("IP_ADDRESS", dlm.get(i).toString());
            } catch (JSONException ex) {
                Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        DCAppLogger.updateServerRespose(RemoteSysSpecs);
        ConnectedMachinesPanel.removeAll();
        //dlm.removeAllElements(); //Commented on 1/18/2014
        /**
         * Initialize information arrays
         */
        CPUUsages = new BufferedImage[RemoteSysSpecs.length];
        CPUSpeeds = new double[RemoteSysSpecs.length];
        RAMSizes = new long[RemoteSysSpecs.length];
        NumberOfCores = new int[RemoteSysSpecs.length];

        getClusterPreview();
        runGraphicsThread();

        //Also set data for pool
        DistributedTaskPool.RemoteSysSpecs = RemoteSysSpecs;
    }//GEN-LAST:event_StartMonitorActionPerformed

    private void ConnectedMachinesPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ConnectedMachinesPanelMouseClicked

        // TODO add your handling code here:

    }//GEN-LAST:event_ConnectedMachinesPanelMouseClicked

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked

        //Preview the remote machine file system
        FSExplorer.RUN_BG_SERVICES = false;
        FSExplorer.REMOTE_BROWSING = true;
        //FSExplorer.CONNECTED_MACHINE=getRemoteSystemSpec(jList1.getSelectedValue().toString());
        FSExplorer _FSExplorer = new FSExplorer("", LICENSE_KEY, getRemoteSystemSpec(jList1.getSelectedValue().toString()));

//        ConnectedMachinesPanel.removeAll();
//        ConnectedMachinesPanel.setLayout(new BoxLayout(ConnectedMachinesPanel, BoxLayout.X_AXIS));
//        ConnectedMachinesPanel.add(_FSExplorer);
        //AccessWindows.
        AccessWindows.add(jList1.getSelectedValue().toString(), _FSExplorer);
        initTabComponent(AccessWindows.getTabCount() - 1);

        if (SHOW_PROCESS_DETAILS.isSelected()) {
            RunningAppsPanel rap = new RunningAppsPanel("", LICENSE_KEY, getRemoteSystemSpec(jList1.getSelectedValue().toString()));
            AccessWindows.add(jList1.getSelectedValue().toString(), rap);
            initTabComponent(AccessWindows.getTabCount() - 1);
        }

        //ConnectedMachinesPanel

    }//GEN-LAST:event_jList1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (RemoteSysSpecs != null) {

            DCAppLogger.updateServerRespose(RemoteSysSpecs);
            ConnectedMachinesPanel.removeAll();
            //dlm.removeAllElements(); //Commented on 1/18/2014
            /**
             * Initialize information arrays
             */
            CPUUsages = new BufferedImage[RemoteSysSpecs.length];
            CPUSpeeds = new double[RemoteSysSpecs.length];
            RAMSizes = new long[RemoteSysSpecs.length];
            NumberOfCores = new int[RemoteSysSpecs.length];

            getClusterPreview();
            runGraphicsThread();

            //Also set data for pool
            DistributedTaskPool.RemoteSysSpecs = RemoteSysSpecs;

            for (int i = 0; i < RemoteSysSpecs.length; i++) {
                try {
                    dlm.addElement(RemoteSysSpecs[i].getString("IP_ADDRESS"));
                } catch (JSONException ex) {
                    Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }


    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane AccessWindows;
    private javax.swing.JButton AddRemoteMachines;
    private javax.swing.JPanel ConnectedMachinesPanel;
    private javax.swing.JTextField IP_ADDRESS_RANGE;
    private javax.swing.JCheckBox LogTheHealth;
    private javax.swing.JLabel Message;
    private javax.swing.JCheckBox SHOW_PROCESS_DETAILS;
    private javax.swing.JButton ScanNetwork;
    private javax.swing.JButton StartMonitor;
    private javax.swing.JButton jButton1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public void getClusterPreview() {

        ACTIVE_MACHINES = 0;
        for (int i = 0; i < RemoteSysSpecs.length; i++) {
            try {
                if (new DistributedTaskService(LICENSE_KEY, RemoteSysSpecs[i], MCWConfClass.DCTPort).isServerLive(1000) == DistributedTaskService.READY_FOR_COMPUTING) {
                    CPUSpeeds[i] = Double.parseDouble((String) new DistributedTaskService(LICENSE_KEY, RemoteSysSpecs[i], MCWConfClass.DCTPort, new OSAppAsDCTask(DistributedTaskPool.PROCESSOR_SPEED)).execute());
                    RAMSizes[i] = Long.parseLong((String) new DistributedTaskService(LICENSE_KEY, RemoteSysSpecs[i], MCWConfClass.DCTPort, new OSAppAsDCTask(DistributedTaskPool.TOTAL_RAM)).execute());
                    NumberOfCores[i] = Integer.parseInt((String) new DistributedTaskService(LICENSE_KEY, RemoteSysSpecs[i], MCWConfClass.DCTPort, new OSAppAsDCTask(DistributedTaskPool.TOTAL_NUMBER_OF_PROCESSOR)).execute());
                    ONLINE_SYSTEM = i;
                    ACTIVE_MACHINES++;
                    RemoteSysSpecs[i].put("SKIP", false);
                    //System.out.println("#LAST ONLINE_SYSTEM="+ONLINE_SYSTEM);
                } else {
                    CPUSpeeds[i] = 0.0;
                    RAMSizes[i] = 0;
                    NumberOfCores[i] = 0;
                    RemoteSysSpecs[i].put("SKIP", true);
                    System.out.println("#LAST OFFLIENE_SYSTEM" + RemoteSysSpecs[i]);
                }
                repaint();
            } catch (IOException ex) {
                Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("#LAST ONLINE_SYSTEM=" + ONLINE_SYSTEM);
        System.out.println("#TOTAL ONLINE_SYSTEM=" + ACTIVE_MACHINES);

        if (ACTIVE_MACHINES > 0) {
            if (ONLINE_SYSTEM >= 0) {
                try {
                    /**
                     * Populate Data
                     */

                    totalCPUSpeed = (Double) new DistributedTaskService(LICENSE_KEY, RemoteSysSpecs[ONLINE_SYSTEM], MCWConfClass.DCTPort, new DTaskMaths(CPUSpeeds, DTaskMaths.ADD_NUMBERS)).execute();
                    AvgCPUSpeed = totalCPUSpeed / ACTIVE_MACHINES;//(Double)new DistributedTaskService(LICENSE_KEY,RemoteSysSpecs[ONLINE_SYSTEM],5559,new DTaskMaths(CPUSpeeds,DTaskMaths.AVERAGE_OF_NUMBERS)).execute();
                    totalRAM = (long) new DistributedTaskService(LICENSE_KEY, RemoteSysSpecs[ONLINE_SYSTEM], MCWConfClass.DCTPort, new DTaskMaths(RAMSizes, DTaskMaths.ADD_NUMBERS)).execute();
                    TotalProcessors = (Integer) new DistributedTaskService(LICENSE_KEY, RemoteSysSpecs[ONLINE_SYSTEM], MCWConfClass.DCTPort, new DTaskMaths(NumberOfCores, DTaskMaths.ADD_NUMBERS)).execute();
                    totalCPUSpeed = AvgCPUSpeed * TotalProcessors;
                } catch (IOException ex) {
                    Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            CPUUsages[0] = OSAppGraphics.getGraphics(250, 90, new String[]{"Fail", "Minimum 2 Active Machines needed"}, false);
        }

    }

    public void runGraphicsThread() {
        for (int i = 0; i < RemoteSysSpecs.length; i++) {
            Thread GetSysInfoServicen = new Thread(new Runnable() {
                public void run() {

                    int thisCount = -1;
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();

                    while (RunGraphicsThread) {  //

                        if (MONITOR_ALL) {
                            if (thisCount < 0) //i.e. Not initiated already
                            {
                                if (SpecCount >= CPUUsages.length) {
                                    SpecCount = 0; //Set if it is first entry only
                                    DCAppLogger.updateServerRespose(sb2.toString());//Publish this data to HTTPServer Log
                                    //System.out.println("#DCGUI sb2.toString()="+sb2.toString());
                                    sb2 = new StringBuilder();

                                }
                            }
                            try {
                                if (!RemoteSysSpecs[SpecCount].getBoolean("SKIP")) {

                                    if (ACTIVE_MACHINES < 1) {
                                        Thread.sleep(MCWConfClass.DCAppLoggingInterval);
                                    }
                                    CPUUsages[SpecCount] = ScreenCapture.getByteAsImage((byte[]) new DistributedTaskService(LICENSE_KEY, RemoteSysSpecs[SpecCount], MCWConfClass.DCTPort, new OSAppGraphicsDCTask(DistributedTaskPool.AVAILABLE_PROCESSOR_BASED, 180, 90, false)).execute());
                                    sb = new StringBuilder((String) new DistributedTaskService(LICENSE_KEY, RemoteSysSpecs[SpecCount], MCWConfClass.DCTPort, new OSAppAsDCTask(OSAppAsDCTask.ALL)).execute());
                                    //System.out.println("#DCGUI sb.toString()="+sb.toString());
                                    sb2.append(sb.toString());
                                    // }
                                } else//Assign OFFLINE IMAGE
                                {
                                    CPUUsages[SpecCount] = OSAppGraphics.getGraphics(180, 90, new String[]{"Offline", RemoteSysSpecs[SpecCount].getString("IP_ADDRESS")}, false);

                                }

                                Thread.sleep(MCWConfClass.DCAppLoggingInterval);

                            } catch (JSONException ex) {
                                Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            tempVar0360++;
                            repaint();

                            System.out.println("#GetSysInfoServicen SpecCount=" + SpecCount + " thisCount=" + thisCount + " Logfile Bytes added " + sb.length());
                            SpecCount++;

                            if (LogTheHealth.isSelected()) {
                                try {
                                    //Logging option
                                    ReadWriteTextFile.appendContents(MCWConfClass.DCGUILogFile, sb.toString());

                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(DCGUI.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                        }
                    }

                }
            });

            GetSysInfoServicen.start();

        }
    }

    protected void paintComponent(Graphics g) {

        if (!isOpaque()) {
            super.paintComponent(g);
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(Color.gray);
        int x = 0;
        int y = 0;
        // fill RoundRectangle2D.Double
        GradientPaint redtowhite = new GradientPaint(x, y, Color.LIGHT_GRAY, x, this.getHeight() / 2,
                Color.DARK_GRAY);
        g2.setPaint(redtowhite);
        //g2.fill(new RoundRectangle2D.Double(x, y, this.getWidth(), this.getHeight(), 10, 10));
        g2.fill(new Rectangle2D.Double(x, y, this.getWidth(), this.getHeight()));

        g2.setPaint(Color.GRAY);

        Paint p;
        p = new RadialGradientPaint(new Point2D.Double(getWidth() / 2.0,
                getHeight() / 2.0), getWidth() / 2.0f,
                new float[]{0.0f, 1.0f},
                new Color[]{new Color(6, 76, 160, 127),
                    new Color(0.0f, 0.0f, 0.0f, 0.8f)});
        g2.setPaint(p);

        g.setColor(new Color(242, 242, 192)); // News Paper Color/ Khakee
        g2.fill(new Rectangle2D.Double(x, y, this.getWidth(), this.getHeight()));
        g2.drawImage(UIToolKit.getParametricCurve(getWidth(), getHeight(), 0.01f, false), null, this);

        Font font = new Font("League-Gothic", Font.BOLD, 20);
        g.setColor(Color.GREEN);
        g.setColor(new Color(6, 112, 154)); //News Papaer Ink
        g.setFont(font);
        //MPP (massively parallel processing)
        g.drawString("MPP (massively parallel processing) Clients", 5, 25);
        g.drawRoundRect(2, 2, 430, 28, 20, 20);
        font = new Font("League-Gothic", Font.PLAIN, 11);
        g.setColor(Color.GREEN);
        g.setColor(new Color(6, 112, 154)); //News Papaer Ink
        g.setFont(font);
        g.drawString("Total Speed : " + totalCPUSpeed + " Ghz", 5, 50);
        g.drawString("Average Speed : " + AvgCPUSpeed + " Ghz", 5, 65);
        g.drawString("Total Processors : " + TotalProcessors + "x", 5, 80);
        g.drawString("Total RAM : " + totalRAM + " GB", 5, 95);

        //Some animation
        if (tempVar0360 == 360) {
            tempVar0360 = 0;
        }
        g.drawArc(20, 110, 20, 20, (int) tempVar0360, 270);
        g.drawArc(18, 108, 24, 24, -(int) tempVar0360, 270);

        x = 160;
        y = 35;
        for (int i = 0; i < CPUUsages.length; i++) {
            if (CPUUsages[i] != null) {
                if (y > getHeight() - (CPUUsages[i].getHeight() - 5)) {
                    y = 35;
                    x = x + CPUUsages[i].getWidth();
                }
                g2.drawImage(CPUUsages[i], null, x, y);
                //g2.drawImage(CPUUsages[i],x,y, null);
                y = y + CPUUsages[i].getHeight();
            }

        }

    }

    private void initTabComponent(int i) {
        AccessWindows.setTabComponentAt(i,
                new ButtonTabComponent(AccessWindows));
    }
}
