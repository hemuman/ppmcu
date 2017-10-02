/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dcApps;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import json.JSONException;
import json.JSONObject;
import net.mk.DTasks.FileBrowserDTask;
import net.mk.DTasks.FilePreviewDTask;
import net.mk.DTasks.FileTransferDTask;
import net.mk.dc.DistributedTaskService;
import net.mk.ppmcu2D.FilePreviewBox;
import net.mk.ppmcu2D.ScreenCapture;
import net.mk.ppmcu2D.UIToolKit;
import net.mk.ppmcuGUI.MCWConfClass;

/**
 *
 * @author PDI
 */
public class FSExplorer extends javax.swing.JPanel {

    /**
     * Creates new form FSExplorer
     */
    String LICENSE_KEY;
    File[] files;
    JSONObject RemoteFile;
     FilePreviewBox[] filePreviewBox;
    int UNITBLOCK = 500;//pixel
    int TOTAL_HZ_PXL;
    int TOTAL_VT_PXL;
    int TOTAL_FILE_SIZE;
    int[] VisualArrangement;
    double LocXScale = 0.01;
    double LocYScale = 0.01;
    int Animdelay = 50;
    int BlockWidth = 0;
    int BlockHeight = 0;
    int BlockMinWidth = 300;
    int BlocakMinHieght = 200;
    int PreviewAreaXLOffset=0;
    int PreviewAreaXROffset=0;
    int PreviewAreaYOffset=0;
    public static boolean REMOTE_BROWSING = false;
    AtomicBoolean RegenerateThumbNails = new AtomicBoolean(true);
    public  JSONObject CONNECTED_MACHINE;
    public static boolean RUN_BG_SERVICES=true;

    public FSExplorer() {
        initComponents();
    }

    public FSExplorer(String WebServer, String License, JSONObject SysSpec) {
        this.LICENSE_KEY = License;
        initComponents();
        LocXScale = 1;
        LocYScale = 1;
        CONNECTED_MACHINE=SysSpec;
        OpenFolder();
        repaint();
        RemoteMachines.execute();
        //AddPreviewImage.execute();
    }

    public void setFiles(JSONObject Files) {
        this.RemoteFile = Files;
        RegenerateThumbNails.set(true);// Regen Thumbnalis once loaded.
        LocXScale = 0.01;
        LocYScale = 0.01;
        VisualArrangement = UIToolKit.getBestVisualArrangement(Files.length(), false);

        TOTAL_HZ_PXL = getWidth();
        TOTAL_VT_PXL = getHeight() - 50;
        BlockWidth = TOTAL_HZ_PXL / VisualArrangement[0];
        BlockHeight = TOTAL_VT_PXL / VisualArrangement[1];

        TOTAL_FILE_SIZE = getAllSizes(Files) / UNITBLOCK;
        //Fetch data in background:
        AddPreviewImage = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {

                System.out.println("#AddPreviewImage Discovering..." + RegenerateThumbNails.get());
                if (filePreviewBox != null) {
                    for (int i = 0; i < filePreviewBox.length; i++) {
                        System.out.println("#AddPreviewImage Looping..." + RegenerateThumbNails.get());
                        if (RegenerateThumbNails.get()) {
                            if (filePreviewBox[i].Name.toLowerCase().contains(".png") | filePreviewBox[i].Name.toLowerCase().contains(".jpg")) {

                                System.out.println("#AddPreviewImage Realtime Preview " + filePreviewBox[i].Name);

                                if (REMOTE_BROWSING) {
                                    if (DCGUI.getRemoteSysSpecs() != null) {
                                        if (DCGUI.getRemoteSysSpecs().length > 0) {
                                            try {
                                                filePreviewBox[i].PreviewImage = ScreenCapture.getByteAsImage(
                                                        (byte[]) new DistributedTaskService(LICENSE_KEY, CONNECTED_MACHINE, MCWConfClass.DCTPort,
                                                                new FilePreviewDTask(filePreviewBox[i].AbsolutePath, BlockWidth, BlockHeight)).execute());
                                            } catch (IOException ex) {
                                                Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
                                            }

                                        }
                                    }
                                } else {
                                    filePreviewBox[i].PreviewImage = ScreenCapture.getByteAsImage(new FilePreviewDTask(filePreviewBox[i].AbsolutePath, BlockWidth, BlockHeight).compute());
                                }

                            }
                        } else {
                            //RegenerateThumbNails.set(false);
                            break;
                        }

                        repaint();
//            try {
//                Thread.sleep(500); //In every 5 secs
//            } catch (InterruptedException ex) {
//                Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
//            }

                    }
                }
                repaint();

                return null;

            }

            @Override
            public void done() {

            }
        };
        AddPreviewImage.execute();

        Thread GetSysInfoServicen = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; LocXScale <= 1; i++) {
                    try {
                        Thread.sleep(Animdelay);

                        Animdelay = Animdelay - (int) (Animdelay * Math.cos(LocXScale * Math.PI / 2));
                        //System.out.println("Animdelay="+Animdelay);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    LocXScale = LocXScale + 0.01;
                    LocYScale = LocYScale + 0.01;
                    repaint();
                }

            }
        });

        GetSysInfoServicen.start();

        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        MachineList = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        FoldersVisited = new javax.swing.JComboBox();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setOpaque(false);

        jButton5.setForeground(new java.awt.Color(51, 51, 51));
        jButton5.setText("<<");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        jButton4.setForeground(new java.awt.Color(51, 51, 51));
        jButton4.setText("Go");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jLabel3.setText("|");
        jToolBar1.add(jLabel3);

        jButton2.setForeground(new java.awt.Color(51, 51, 51));
        jButton2.setText("Browse Local");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton3.setForeground(new java.awt.Color(51, 51, 51));
        jButton3.setText("Browse");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jLabel2.setText("|");
        jToolBar1.add(jLabel2);

        MachineList.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        MachineList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MachineListActionPerformed(evt);
            }
        });
        jToolBar1.add(MachineList);

        jLabel1.setText("|");
        jToolBar1.add(jLabel1);

        jButton6.setText("Play");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jButton1.setForeground(new java.awt.Color(51, 51, 51));
        jButton1.setText("ReSet");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        FoldersVisited.setEditable(true);
        FoldersVisited.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "/"}));
        FoldersVisited.setMaximumSize(new java.awt.Dimension(200, 32767));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(FoldersVisited, 0, 508, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(411, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FoldersVisited, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        initComponents();
        LocXScale = 1;
        LocYScale = 1;
        REMOTE_BROWSING = false;
        OpenFolder();
        repaint();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfc.setMultiSelectionEnabled(false);
        jfc.showOpenDialog(jfc);
        jfc.getSelectedFile();
        FoldersVisited.insertItemAt(jfc.getSelectedFile().getAbsolutePath(), 0);
        FoldersVisited.setSelectedItem(jfc.getSelectedFile().getAbsolutePath());
        FileBrowserDTask fbdt = new FileBrowserDTask(FoldersVisited.getSelectedItem().toString());
        try {
            setFiles(new JSONObject((String) fbdt.compute()));
            REMOTE_BROWSING = false;
        } catch (JSONException ex) {
            Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        if (DCGUI.getRemoteSysSpecs() != null) {
            if (DCGUI.getRemoteSysSpecs().length > 0) {

                FileBrowserDTask fbdt = new FileBrowserDTask((FoldersVisited.getSelectedItem().toString()));
                try {
                    RemoteFile = new JSONObject((String) new DistributedTaskService(LICENSE_KEY, DCGUI.getRemoteSysSpecs()[0], MCWConfClass.DCTPort, fbdt).execute());
                    setFiles(RemoteFile);
                    REMOTE_BROWSING = true;

                } catch (IOException ex) {
                    Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

        System.out.println(evt.getX() + " " + evt.getY());
        if (evt.getButton() == MouseEvent.BUTTON1) {
            for (int i = 0; i < filePreviewBox.length; i++) {
                //System.out.println(filePreviewBox[i].x+" R "+filePreviewBox[i].y+" Result="+filePreviewBox[i].Contains(evt.getX(),evt.getY(),BlockWidth,BlockHeight));
                if (filePreviewBox[i].Contains(evt.getX(), evt.getY(), BlockWidth, BlockHeight)) {

                    System.out.println("Found a match " + filePreviewBox[i].Name);
                    FileBrowserDTask fbdt = new FileBrowserDTask(filePreviewBox[i].AbsolutePath);
                    if (filePreviewBox[i].isDirectory) {

                        //AddressBar.setText(filePreviewBox[i].AbsolutePath);
                        FoldersVisited.insertItemAt(filePreviewBox[i].AbsolutePath, 0);
                        FoldersVisited.setSelectedItem(filePreviewBox[i].AbsolutePath);
                        try {
                            if (REMOTE_BROWSING) {
                                if (DCGUI.getRemoteSysSpecs() != null) {
                                    if (DCGUI.getRemoteSysSpecs().length > 0) {
                                        
                                        if(RUN_BG_SERVICES)
                                        { CONNECTED_MACHINE = DCGUI.getRemoteSysSpecs()[MachineList.getSelectedIndex()];}
                                        try {
                                            RemoteFile = new JSONObject((String) new DistributedTaskService(LICENSE_KEY, CONNECTED_MACHINE,  MCWConfClass.DCTPort, fbdt).execute());
                                            setFiles(RemoteFile);

                                            REMOTE_BROWSING = true;

                                        } catch (IOException ex) {
                                            Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (JSONException ex) {
                                            Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
                            } else {
                                setFiles(new JSONObject((String) fbdt.compute()));
                            }
                        } catch (JSONException ex) {
                            Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else { // File Transfer

                        if (REMOTE_BROWSING) {
                            if (filePreviewBox[i].isFile) {
                                if (filePreviewBox[i].length < 30 * 1024 * 1024) {
                                    if (evt.getClickCount() == 2) {
                                        try {
                                            JFileChooser jfc = new JFileChooser();
                                            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                                            jfc.setMultiSelectionEnabled(false);
                                            jfc.showSaveDialog(jfc);
                                            if (jfc.getSelectedFile() != null) {
                                                String FileData = jfc.getSelectedFile().getAbsolutePath();

                                                //Download
                                                byte[] fileData = (byte[]) new DistributedTaskService(LICENSE_KEY, CONNECTED_MACHINE,  MCWConfClass.DCTPort, new FileTransferDTask(filePreviewBox[i].AbsolutePath, new byte[]{}, true)).execute();
                                                //Save locally
                                                new FileTransferDTask(FileData + File.separator + filePreviewBox[i].Name, fileData, false).compute();
                                            }

                                        } catch (IOException ex) {
                                            Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    } else {
                                        try {
                                            if (filePreviewBox[i].length < 30 * 1024 * 1024) {
                                                if (filePreviewBox[i].Name.toLowerCase().contains(".png") | filePreviewBox[i].Name.toLowerCase().contains(".jpg")) {
                                                    UIToolKit.PreviewImage((byte[]) new DistributedTaskService(LICENSE_KEY, CONNECTED_MACHINE, 5559, new FileTransferDTask(filePreviewBox[i].AbsolutePath, new byte[]{}, true)).execute(), getWidth(), getHeight());
                                                }
                                            }

                                        } catch (IOException ex) {
                                            Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    }

                                } else {
                                    JOptionPane.showMessageDialog(jButton1, "Can't Download! \n Too big to transfer... Limit=" + 30 * 1024 * 1024);
                                }
                            }
                        } else {
                            System.out.println("Local File " + filePreviewBox[i].AbsolutePath);
                            //Show if this is lacal file
                            if (filePreviewBox[i].Name.toLowerCase().contains(".png") | filePreviewBox[i].Name.toLowerCase().contains(".jpg")) {

                                if (filePreviewBox[i].length < 30 * 1024 * 1024) {
                                    UIToolKit.PreviewImage((byte[]) new FileTransferDTask(filePreviewBox[i].AbsolutePath, new byte[]{}, true).compute(), getWidth(), getHeight());
                                }

                            }
                        }
                    }
                    break;
                    // System.out.println("-"+filePreviewBox[i].getFileInfo().getString(FileBrowserDTask.Name));
                }
            }
        }


    }//GEN-LAST:event_formMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        FileBrowserDTask fbdt = new FileBrowserDTask(FoldersVisited.getSelectedItem().toString());
        try {
            if (REMOTE_BROWSING) {
                if (DCGUI.getRemoteSysSpecs() != null) {
                    if (DCGUI.getRemoteSysSpecs().length > 0) {

                        try {
                            RemoteFile = new JSONObject((String) new DistributedTaskService(LICENSE_KEY, DCGUI.getRemoteSysSpecs()[0], 5559, fbdt).execute());
                            setFiles(RemoteFile);
                            REMOTE_BROWSING = true;

                        } catch (IOException ex) {
                            Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (JSONException ex) {
                            Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } else {
                setFiles(new JSONObject((String) fbdt.compute()));
            }
        } catch (JSONException ex) {
            Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        if (FoldersVisited.getSelectedIndex() >= 0) {
            if (FoldersVisited.getSelectedIndex() + 1 < FoldersVisited.getItemCount()) {
                FoldersVisited.setSelectedIndex(FoldersVisited.getSelectedIndex() + 1);
            }
        }

        FileBrowserDTask fbdt = new FileBrowserDTask(FoldersVisited.getSelectedItem().toString());
        try {
            if (REMOTE_BROWSING) {
                if (DCGUI.getRemoteSysSpecs() != null) {
                    if (DCGUI.getRemoteSysSpecs().length > 0) {

                        try {
                            RemoteFile = new JSONObject((String) new DistributedTaskService(LICENSE_KEY, DCGUI.getRemoteSysSpecs()[0], 5559, fbdt).execute());
                            setFiles(RemoteFile);
                            REMOTE_BROWSING = true;

                        } catch (IOException ex) {
                            Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (JSONException ex) {
                            Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } else {
                setFiles(new JSONObject((String) fbdt.compute()));
            }
        } catch (JSONException ex) {
            Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved

          //System.out.println(evt.getX() + " " + evt.getY());
        //if(evt.getButton()==MouseEvent.BUTTON1)
//        for (int i = 0; i < filePreviewBox.length; i++) {
//            //System.out.println(filePreviewBox[i].x+" R "+filePreviewBox[i].y+" Result="+filePreviewBox[i].Contains(evt.getX(),evt.getY(),BlockWidth,BlockHeight));
//            if (filePreviewBox[i].Contains(evt.getX(), evt.getY(), BlockWidth, BlockHeight)) {
//                 System.out.println("#Hovering on: "+filePreviewBox[i].Name);
//                    
//                 filePreviewBox[i].setLocation(filePreviewBox[i].x-2, filePreviewBox[i].y-2);
//                 repaint();
//                 break;
//            }}

    }//GEN-LAST:event_formMouseMoved

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        UIToolKit.PlaySlideShow(filePreviewBox);

    }//GEN-LAST:event_jButton6ActionPerformed

    private void MachineListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MachineListActionPerformed

        CONNECTED_MACHINE = DCGUI.getRemoteSysSpecs()[MachineList.getSelectedIndex()];

    }//GEN-LAST:event_MachineListActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox FoldersVisited;
    private javax.swing.JComboBox MachineList;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
int CheckLiveSysDelay = 5000;//ms

    SwingWorker<Void, Void> RemoteMachines = new SwingWorker<Void, Void>() {
        @Override
        public Void doInBackground() {

            while (RUN_BG_SERVICES) {
                //System.out.println("#RemoteMachines Discovering...");
                if (DCGUI.getRemoteSysSpecs() != null) {
                    if (DCGUI.getRemoteSysSpecs().length > 0) {
                            //MachineList.removeAllItems();
                        //MachineList.addItem("Local");
                        for (int i = 0; i < DCGUI.getRemoteSysSpecs().length; i++) {
                            try {
                                for (int j = 0; j < MachineList.getItemCount(); j++) {
                                    if (MachineList.getItemAt(j).toString().contentEquals(DCGUI.getRemoteSysSpecs()[i].getString("IP_ADDRESS"))) {
                                        MachineList.remove(j); // Avoid duplication
                                        break;
                                    }
                                }
                                //System.out.println(" Machine #"+i);

                                MachineList.addItem(DCGUI.getRemoteSysSpecs()[i].getString("IP_ADDRESS"));
                            } catch (JSONException ex) {
                                Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(CheckLiveSysDelay); //In every 5 secs
                } catch (InterruptedException ex) {
                    Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            return null;
        }

        @Override
        public void done() {

        }
    };

    SwingWorker<Void, Void> AddPreviewImage = new SwingWorker<Void, Void>() {
        @Override
        public Void doInBackground() {

            System.out.println("#AddPreviewImage Discovering..." + RegenerateThumbNails.get());
            if (filePreviewBox != null) {
                for (int i = 0; i < filePreviewBox.length; i++) {
                    System.out.println("#AddPreviewImage Looping..." + RegenerateThumbNails.get());
                    if (RegenerateThumbNails.get()) {
                        if (filePreviewBox[i].Name.toLowerCase().contains(".png") | filePreviewBox[i].Name.toLowerCase().contains(".jpg")) {
                            System.out.println("#AddPreviewImage Realtime Preview" + RegenerateThumbNails.get());
                            BufferedImage originalImage = ScreenCapture.getByteAsImage(new FileTransferDTask(filePreviewBox[i].AbsolutePath, new byte[]{}, true).compute());

                            filePreviewBox[i].PreviewImage = new BufferedImage(TOTAL_HZ_PXL / VisualArrangement[0], TOTAL_VT_PXL / VisualArrangement[1], BufferedImage.TYPE_INT_RGB);
                            Graphics2D g = filePreviewBox[i].PreviewImage.createGraphics();
                            g.drawImage(originalImage, 0, 0, BlockWidth, BlockHeight, null);
                            g.dispose();
                            g.setComposite(AlphaComposite.Src);
                            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        }
                    } else {
                        //RegenerateThumbNails.set(false);
                        break;
                    }

                    repaint();
//            try {
//                Thread.sleep(500); //In every 5 secs
//            } catch (InterruptedException ex) {
//                Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
//            }

                }
            }

            return null;

        }

        @Override
        public void done() {

        }
    };

    protected void paintComponent(Graphics g) {

        if (!isOpaque()) {
            super.paintComponent(g);
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) LocXScale));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        // g.setColor(new Color(242,242,192));
        g2.fillRect(0, 0, getWidth(), getHeight());
        if (VisualArrangement != null) {
            TOTAL_HZ_PXL = getWidth()-PreviewAreaXROffset;
            TOTAL_VT_PXL = getHeight() - 50;
            BlockWidth = TOTAL_HZ_PXL / VisualArrangement[0];
            BlockHeight = TOTAL_VT_PXL / VisualArrangement[1];
            BufferedImage imgDir = UIToolKit.getWin7StyleRectWC(TOTAL_HZ_PXL / VisualArrangement[0], TOTAL_VT_PXL / VisualArrangement[1], 0.2f, true);
            BufferedImage imgFile = UIToolKit.getWin7StyleRect2(TOTAL_HZ_PXL / VisualArrangement[0], TOTAL_VT_PXL / VisualArrangement[1], 0.2f, true);
            BufferedImage restrictedFile = UIToolKit.getWin7StyleRect3(TOTAL_HZ_PXL / VisualArrangement[0], TOTAL_VT_PXL / VisualArrangement[1], 0.2f, true);
            //System.out.println("HZ= "+VisualArrangement[0]/TOTAL_HZ_PXL);
            //System.out.println("VT= "+VisualArrangement[1]/TOTAL_VT_PXL);
            TextLayout textLayout;
            Font font = new Font("Georgia", Font.PLAIN, 11);
            for (int i = 0; i < VisualArrangement[0]; i++) {
                for (int j = 0; j < VisualArrangement[1]; j++) {
                    int x = (int) (LocXScale * i * TOTAL_HZ_PXL / VisualArrangement[0]);
                    int y = (int) (LocYScale * j * TOTAL_VT_PXL / VisualArrangement[1]);
                    //g.setColor(new Color(255 * i / VisualArrangement[0], 255 * j / VisualArrangement[1], 120, 250));

                    //g2.fillRect(x, y, TOTAL_HZ_PXL/VisualArrangement[0], TOTAL_VT_PXL/VisualArrangement[1]);
                    //g2.fill3DRect(x, y, TOTAL_HZ_PXL/VisualArrangement[0], TOTAL_VT_PXL/VisualArrangement[1],true);
                    g.setColor(Color.LIGHT_GRAY);
                    g2.drawRect(x, y, TOTAL_HZ_PXL / VisualArrangement[0], TOTAL_VT_PXL / VisualArrangement[1]);
                    //System.out.println("files.length="+files.length+" (j+i*VisualArrangement[0])="+(j+i*VisualArrangement[0])+" (files.length<(j+i*VisualArrangement[0]))="+(files.length<(j+i*VisualArrangement[0])));
                    if (RemoteFile.length() > (j + i * VisualArrangement[0])) {

                        //Render different graphics for Files
                        if (filePreviewBox[j + i * VisualArrangement[0]].hasRestrictions()) {
                            g2.drawImage(restrictedFile, null, x, y);
                        } else if (filePreviewBox[j + i * VisualArrangement[0]].isDirectory) {
                            g2.drawImage(imgDir, null, x, y);
                        } else if (filePreviewBox[j + i * VisualArrangement[0]].isFile) {
                            g2.drawImage(imgFile, null, x, y);
                        }
                        if (filePreviewBox[j + i * VisualArrangement[0]].PreviewImage != null) {
                            g2.drawImage(filePreviewBox[j + i * VisualArrangement[0]].PreviewImage, null, x, y);
                        }

                        filePreviewBox[j + i * VisualArrangement[0]].setLocation(x, y);
                        filePreviewBox[j + i * VisualArrangement[0]].x = x;
                        filePreviewBox[j + i * VisualArrangement[0]].y = y;
                        //filePreviewBox[j + i * VisualArrangement[0]].width = TOTAL_HZ_PXL / VisualArrangement[0];
                        //filePreviewBox[j + i * VisualArrangement[0]].height= TOTAL_VT_PXL / VisualArrangement[1];
                        //filePreviewBox[i].PreviewImage
                        g2.fill(filePreviewBox[j + i * VisualArrangement[0]]);//Draw file rect
                        //if(files[j+i*VisualArrangement[0]]!=null)
                        //g2.drawString("[ "+i+" , "+j+" ]",5+ i*TOTAL_HZ_PXL/VisualArrangement[0], 15+j*TOTAL_VT_PXL/VisualArrangement[1]);

                        //g.setColor(Color.LIGHT_GRAY);
                        if (filePreviewBox[j + i * VisualArrangement[0]].isFile) {
                            g.setColor(Color.DARK_GRAY);
                        }

                        g.setColor(Color.LIGHT_GRAY);
                        textLayout = new TextLayout(filePreviewBox[j + i * VisualArrangement[0]].ShortName, font, g2.getFontRenderContext());
                        g2.setPaint(new Color(150, 150, 150));
                        textLayout.draw(g2, 5 + x, 12 + y);

                        g2.setPaint(Color.DARK_GRAY);
                        textLayout.draw(g2, 4 + x, 11 + y);

                        g.setColor(Color.LIGHT_GRAY);
                        textLayout = new TextLayout(filePreviewBox[j + i * VisualArrangement[0]].length / 1024 + "(KB)", font, g2.getFontRenderContext());
                        g2.setPaint(new Color(150, 150, 150));
                        //textLayout.draw(g2,  5 + x, 24 + y);

                        g2.setPaint(Color.DARK_GRAY);
                        textLayout.draw(g2, 60 + x, 23 + y);

                        g.setColor(Color.LIGHT_GRAY);
                        textLayout = new TextLayout(filePreviewBox[j + i * VisualArrangement[0]].FileType, font, g2.getFontRenderContext());
                        g2.setPaint(new Color(150, 150, 150));
                        //textLayout.draw(g2,  5 + x, 36 + y);

                        g2.setPaint(Color.DARK_GRAY);
                        textLayout.draw(g2, 4 + x, 23 + y);

                        g.setColor(Color.LIGHT_GRAY);
                        textLayout = new TextLayout(filePreviewBox[j + i * VisualArrangement[0]].MachineName, font, g2.getFontRenderContext());
                        g2.setPaint(new Color(150, 150, 150));
                        //textLayout.draw(g2,  5 + x, 36 + y);

                        g2.setPaint(Color.LIGHT_GRAY);
                        textLayout.draw(g2, 4 + x, 35 + y);
                            //g2.drawString("[ "+i+" , "+j+" ]",5+ i*TOTAL_HZ_PXL/VisualArrangement[0], 15+j*TOTAL_VT_PXL/VisualArrangement[1]);
                        //g2.drawString(filePreviewBox[j + i * VisualArrangement[0]].Name, 5 + x, 12 + y);
                        //g2.drawString(filePreviewBox[j + i * VisualArrangement[0]].length+" (B)", 5 + x, 24 + y);

                    } else {
                        g2.drawImage(imgDir, null, x, y);
                    }
                }
            }
        }

    }

    public static int getAllSizes(File[] files) {
        int result = 0;

        for (int i = 0; i < files.length; i++) {
            result = (int) (result + files[i].length());
        }
        //System.out.println("#getAllSizes files.length= " + files.length + " Size= " + result);
        return result;
    }

    public int getAllSizes(JSONObject files) {
        int result = 0;
        Iterator itr = files.keys();
        filePreviewBox = new FilePreviewBox[files.length()];
        int count = 0;
        while (itr.hasNext()) {

            try {
                JSONObject temp = files.getJSONObject((String) itr.next());
                result = (int) (result + temp.getLong(FileBrowserDTask.length));
                filePreviewBox[count] = new FilePreviewBox();
                filePreviewBox[count].setFileInfo(temp);
            } catch (JSONException ex) {
                Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
            }
            count++;
        }

        //System.out.println("#getAllSizes files.length= " + files.length() + " Size= " + result);
        return result;
    }

    public void OpenFolder() {
        FileBrowserDTask fbdt = new FileBrowserDTask(FoldersVisited.getSelectedItem().toString());
        try {
            if (REMOTE_BROWSING) {
                if (DCGUI.getRemoteSysSpecs() != null) {
                    if (DCGUI.getRemoteSysSpecs().length > 0) {

                        try {
                            //RemoteFile = new JSONObject((String) new DistributedTaskService(LICENSE_KEY, DCGUI.getRemoteSysSpecs()[0], MCWConfClass.DCTPort, fbdt).execute());
                            RemoteFile = new JSONObject((String) new DistributedTaskService(LICENSE_KEY, CONNECTED_MACHINE, MCWConfClass.DCTPort, fbdt).execute());
                            //CONNECTED_MACHINE
                            setFiles(RemoteFile);
                            REMOTE_BROWSING = true;

                        } catch (IOException ex) {
                            Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (JSONException ex) {
                            Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } else {
                setFiles(new JSONObject((String) fbdt.compute()));
            }
        } catch (JSONException ex) {
            Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            FSExplorer fse = new FSExplorer("", "TestLic", new JSONObject());
            FileBrowserDTask fbdt = new FileBrowserDTask("C:\\Users\\PDI\\Downloads");
            fse.setFiles(new JSONObject((String) fbdt.compute()));
            JOptionPane.showMessageDialog(null, fse);
        } catch (JSONException ex) {
            Logger.getLogger(FSExplorer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
