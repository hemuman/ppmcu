/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu2D;

import Compression.DataCompression;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Paint;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.AWTEventListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import json.mkJSON;
import net.mk.DTasks.DTaskTest;
import net.mk.DTasks.GetProcDCTask;
import net.mk.dc.DistributedTaskPool;
import net.mk.dc.DistributedTaskProcessor;
import net.mk.dc.DistributedTaskService;
import net.mk.os.OSApp;
import net.mk.DTasks.OSAppAsDCTask;
import net.mk.os.RunningAppsPanel;
import net.mk.ppmcu.GlobalMCUTest;
import net.mk.ppmcuGUI.MCWConfClass;
import net.mk.ppmcuGUI.FrontPage;
import net.mk.ppmcuGUI.Web;

/**
 *
 * @author PDI
 */
public class ScreenShareClient extends javax.swing.JPanel {

    DefaultComboBoxModel models = new DefaultComboBoxModel(new String[]{"A1", "A2"});
    protected String LICENSE_KEY = "NOTSET";
    int[] mouseEvent;
    boolean CAPTURE_MOUSE_EVENT = true;
    double imageScaleRatio = 1;
    int width = 640;
    int height = 480;
    File DataFile;
    String WebServerName;
    String MAC_ADDRESS;
    JSONObject RemoteSysSpec;
    boolean IS_REGISTERED = false;
    String tempRemoteTestMessage = "NotPerformed Yet!";
    String RAM_INFO = "NOT CONNECTED";
    String SWAP_INFO = "NOT CONNECTED";
    String CPU_INFO = "NOT CONNECTED";
    String CPU_USAGE_INFO = "NOT CONNECTED";

    /**
     * Creates new form ScreenShareClient
     */
    public ScreenShareClient(String WebServerName, String LICENSE_KEY, String[] ListOfClients, int width, int height) {

        this.LICENSE_KEY = LICENSE_KEY;
        this.WebServerName = WebServerName;

        this.width = getWidth();
        this.height = getHeight();

        try {
//Refactoring is needed here

            //create a temp file
            File temp = File.createTempFile("ppmcuScreenShareClient", ".test");

            File temp2 = new File(System.getProperty("java.io.tmpdir") + "/ppmcuScreenShareClient.array");
            if (temp2.exists()) {
                System.out.println("Temp file : " + temp2.getAbsolutePath());
                DataFile = temp2;
                Charset charset = Charset.forName("US-ASCII");
                String allData = "";
                try (BufferedReader reader = Files.newBufferedReader(DataFile.toPath(), charset)) {
                    String line = null;
                    ;
                    while ((line = reader.readLine()) != null) {
                        //  System.out.println(line);
                        allData = allData + line;
                    }
                } catch (IOException x) {
                    System.err.format("IOException: %s%n", x);
                }
                JSONObject fileData = new JSONObject(allData);

                //  System.out.println(fileData.getString("LICENSE_KEY")+"-"+LICENSE_KEY);
                if (fileData.getString("LICENSE_KEY").contentEquals(LICENSE_KEY)) { //Have same License
                    JSONArray data = fileData.getJSONArray("data");
                    ListOfClients = mkJSON.getStringArrayFromJSONArray(data);
                    System.out.println(data.toString(1));
                } else {
                    createDatafile();
                }

            } else {

                createDatafile();
            }

        } catch (IOException e) {

            e.printStackTrace();

        } catch (JSONException ex) {
            Logger.getLogger(ScreenShareClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        mouseEvent = new int[5];
        models = new DefaultComboBoxModel(ListOfClients);

        RemoteSysSpec = new JSONObject();
        try {
            RemoteSysSpec.put("PC_NAME", "REMOTE_PC");
            RemoteSysSpec.put("NUMBER_OF_PROCESSORS", 0);
            RemoteSysSpec.put("IP_ADDRESS", "IP_ADDRESS");
            RemoteSysSpec.put("OPERATING_SYSTEM", "OS NAME");
            //IP_ADDRESS
        } catch (JSONException ex) {
            Logger.getLogger(ScreenShareClient.class.getName()).log(Level.SEVERE, null, ex);
        }


        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        StartPos = new javax.swing.JLabel();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(153, 153, 255));
        jButton1.setText("Connect");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jComboBox1.setModel(models);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jComboBox1);

        jTextField1.setText("greenmonster.local");
        jToolBar1.add(jTextField1);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setForeground(new java.awt.Color(153, 153, 255));
        jButton2.setText("Run Test");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        StartPos.setText(".");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(StartPos)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(StartPos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 261, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        initGUI(jTextField1.getText(), MCWConfClass.ScreenServerPort);

        System.out.println("Client Got result");



    }//GEN-LAST:event_jButton1ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

        if (CAPTURE_MOUSE_EVENT) {
            mouseEvent[0] = evt.getX();
            mouseEvent[1] = evt.getY();
            mouseEvent[2] = evt.getClickCount();
            mouseEvent[3] = evt.getButton();
        }

        //MouseEvent

    }//GEN-LAST:event_formMouseClicked

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered

        CAPTURE_MOUSE_EVENT = true;
        mouseEvent[4] = 1;

    }//GEN-LAST:event_formMouseEntered

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited

        CAPTURE_MOUSE_EVENT = false;
        mouseEvent[4] = 0;

    }//GEN-LAST:event_formMouseExited

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

        jTextField1.setText(jComboBox1.getSelectedItem().toString());

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed




        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {

//                    RemoteSysSpec.put("IP_ADDRESS", jTextField1.getText()); // Very important, this keeps changing.
//                            
//                    DistributedTaskPool dtp=new DistributedTaskPool(LICENSE_KEY,5);
//                    dtp.submit(RemoteSysSpec, 5559, new DTaskTest(1), 0);
//                    dtp.submit(RemoteSysSpec, 5559, new DTaskTest(1), 1);
//                   // RemoteSysSpec.put("IP_ADDRESS", "127.0.0.1"); // Very important, this keeps changing.
//                    dtp.submit(RemoteSysSpec, 5559, new DTaskTest(1), 2);
//                    dtp.submit(RemoteSysSpec, 5559, new DTaskTest(1), 4);
//                    
//                    while(dtp.NumberOfTasksFinished!=(dtp.TotalNumberOfTask-1)){
//                     // System.out.println("Waiting..."+dtp.NumberOfTasksFinished);
//                        tempRemoteTestMessage=""+(dtp.NumberOfTasksFinished+1);
//                    
//                    }
//                    System.out.println("Done...");
                    InetAddress host2 = InetAddress.getByName(jTextField1.getText());
                    RemoteSysSpec.put("IP_ADDRESS", jTextField1.getText()); // Very important, this keeps changing.

                    tempRemoteTestMessage = (String) new DistributedTaskService(LICENSE_KEY, RemoteSysSpec, 5559, new DTaskTest(1)).execute();
                    System.out.println("Client Got result:" + tempRemoteTestMessage);


                } catch (IOException ex) {
                    Logger.getLogger(FrontPage.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(FrontPage.class.getName()).log(Level.SEVERE, null, ex);
                }


            }
        });
        t2.start();


    }//GEN-LAST:event_jButton2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel StartPos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables

    private void StartCollectingData() {

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("#StartCollectingData ...start");
                    while (IS_REGISTERED) {
                        RemoteSysSpec.put("IP_ADDRESS", jTextField1.getText()); // Very important, this keeps changing.
                        //tempRemoteTestMessage=(String) new DistributedTaskService(LICENSE_KEY,RemoteSysSpec,5559,new DTaskTest(1)).execute();
                        //System.out.println("Client Got result:"+tempRemoteTestMessage);
                        RemoteSysSpec = new JSONObject((String) new DistributedTaskService(LICENSE_KEY, RemoteSysSpec, 5559, new OSAppAsDCTask(0)).execute());

//                     if(MCWConfClass.REMOTE_DATA_MODE){
//                        RemoteSysSpec.put("IP_ADDRESS", jTextField1.getText()); // Very important, this keeps changing.
//                        RemoteSysSpec.put("ALL_PROC", new JSONObject((String) new DistributedTaskService(LICENSE_KEY,RemoteSysSpec,5559,new GetProcDCTask()).execute())) ;
//                        RunningAppsPanel.RemoteSysSpec=RemoteSysSpec;
//                     }

                        if (RemoteSysSpec.has("RAM")) {
                            RAM_INFO = "RAM : " + RemoteSysSpec.getJSONObject("RAM").getString("RAMFREE_GB")
                                    + "GB /" + RemoteSysSpec.getJSONObject("RAM").getString("RAM_GB") + " GB";
                            SWAP_INFO = "SWAP : " + RemoteSysSpec.getJSONObject("RAM").getString("SWAPFREE_GB")
                                    + "GB /" + RemoteSysSpec.getJSONObject("RAM").getString("SWAP_GB") + " GB";
                            CPU_INFO = "Model : " + RemoteSysSpec.getJSONObject("RAM").getString("Model")
                                    + "  Total_CPUs : " + RemoteSysSpec.getJSONObject("RAM").getString("Total_CPUs");
                            CPU_USAGE_INFO = "Good(%) : " + RemoteSysSpec.getJSONObject("RAM").getJSONObject("CPU_Usage").getDouble("Usage") * 100
                                    + "  Wasting(%) : " + RemoteSysSpec.getJSONObject("RAM").getJSONObject("CPU_Usage").getDouble("Idle_Time") * 100
                                    + "  UP Time : " + RemoteSysSpec.getJSONObject("RAM").getString("CPU_UPTime");
                            //CPU_UPTime
                        }
                        Thread.sleep(3000);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(FrontPage.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(FrontPage.class.getName()).log(Level.SEVERE, null, ex);
                }


            }
        });

        t2.start();
    }

    private boolean isRegistered() {

        try {
            writeDatafile(jTextField1.getText());
            //{'ERROR':'Not Registered yet'}
            //Check MAC address first.... 
            // String MAC_ADDRESS=OSApp.MACAddress(jTextField1.getText());
            //Check if this isregistered at the mcst
            System.out.println("#isRegistered  :" + MAC_ADDRESS);
            RemoteSysSpec = new JSONObject(new Web().SendPOST("http://" + WebServerName + "/mcst/GetUserProfile.php", new String[]{"LICENSE_KEY", "USER_KEY"}, new String[]{LICENSE_KEY, MAC_ADDRESS}, false));
            if (RemoteSysSpec.has("ERROR") | RemoteSysSpec.has("INFO")) {
                JOptionPane.showMessageDialog(jComboBox1, RemoteSysSpec.toString(1));
                return false;
            }

        } catch (JSONException ex) {
            Logger.getLogger(ScreenShareClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ScreenShareClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        jButton1.setVisible(!true);
        jComboBox1.setVisible(!true);
        jTextField1.setVisible(!true);

        MCWConfClass.REMOTE_DATA_MODE = true;
        IS_REGISTERED = true;
        System.out.println("#isRegistered IS_REGISTERED ="+IS_REGISTERED);
        StartCollectingData();
        return true;
    }
    /**
     * ScreeShare Client
     */
    private static final long serialVersionUID = 1L;
    BufferedImage img;
    private static Socket socket, mouseSocket;
    private static String host = "127.0.0.1";
    private static int port = 6666;

    /**
     *
     * @param host
     * @param port
     */
    private void initGUI(String host, int port) {
        ScreenShareClient.host = host;
        ScreenShareClient.port = port;

        Thread cgiT = new Thread(new ScreenShareClient.GetCameraImage(), "Camera 3");
        cgiT.start();
    }

    /**
     *
     */
    protected class GetCameraImage implements Runnable {

        @Override
        public void run() {
            try {
                socket = new Socket(host, port);
                mouseSocket = new Socket(host, port + 1);
                System.out.println("Connection to host established.");
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host: " + host);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                //GetMacAddress at first entry
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                byte[] byteImage = (byte[]) ois.readObject();


                //ois = new ObjectInputStream(mouseSocket.getInputStream());
                //int[] mousexy = (int[])ois.readObject();

                // convert byte array back to BufferedImage
                InputStream in = new ByteArrayInputStream(byteImage);
                //String s = new String(bytes);
                MAC_ADDRESS = new String(byteImage);
                System.out.println("#SSC : " + MAC_ADDRESS);
                IS_REGISTERED = isRegistered();
                System.out.println("#IS_REGISTERED :: "+IS_REGISTERED);
                int count = 1;

                while ((count != 0) & (IS_REGISTERED)) {
                    ois = new ObjectInputStream(socket.getInputStream());
                    
                    //Uncompressed bytes
                    byteImage = (byte[]) ois.readObject();

                    //Decompress the bytes
                    //byteImage = DataCompression.decompress((byte[]) ois.readObject());
                    
                    //ois = new ObjectInputStream(mouseSocket.getInputStream());
                    //int[] mousexy = (int[])ois.readObject();

                    // convert byte array back to BufferedImage
                    in = new ByteArrayInputStream(byteImage);
                    //BufferedImage bImageFromConvert = ImageIO.read(in);

                    BufferedImage bufferedImage = ImageIO.read(in);//= ByteArrayConversion.fromByteArray(byteImage);
                    //System.out.println(bufferedImage.toString());					

                    ScreenShareClient.ScaleJPG scale = new ScreenShareClient.ScaleJPG();

                    try {
                        scale.scale("camera3", bufferedImage, width, height);
                        repaint();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    /**
                     * Send mouse Position
                     */
                    PointerInfo a = MouseInfo.getPointerInfo();
                    Point b = a.getLocation();


                    SwingUtilities.convertPointFromScreen(b, StartPos);
                    mouseEvent[0] = (int) (b.getX() / imageScaleRatio);
                    mouseEvent[1] = (int) (b.getY() / imageScaleRatio);

                    //if(CAPTURE_MOUSE_EVENT){
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(mouseEvent);
                    // }
                    mouseEvent[2] = 0;

                    // System.out.println("ScreenSize getWidth()="+getWidth()+"\t getHeight()="+getHeight());

                }
                System.out.println("#ScreenShareClient :: Session Ended 1");
                count++;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
//            catch (DataFormatException ex) {
//                Logger.getLogger(ScreenShareClient.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
            System.out.println("#ScreenShareClient :: Session Ended 2");
        }
    }

    /**
     *
     */
    public class ScaleJPG {

        public void scale(String cameraName, BufferedImage bufferedImage, int width, int height) throws IOException {

            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            Graphics2D g = img.createGraphics();
            double xRatio = (double) width / bufferedImage.getWidth();
            double yRatio = (double) height / bufferedImage.getHeight();
            imageScaleRatio = Math.min(xRatio, yRatio);
            AffineTransform trans = AffineTransform.getScaleInstance(imageScaleRatio, imageScaleRatio);

            g.drawRenderedImage(bufferedImage, trans);

        }
    }

    /**
     *
     * @param g
     */
    protected void paintComponent(Graphics g) {
        try {
            this.width = getWidth();
            this.height = getHeight();

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setPaint(Color.gray);
            int x = 0;
            int y = 0;
            // fill RoundRectangle2D.Double
            GradientPaint redtowhite = new GradientPaint(x, y, Color.LIGHT_GRAY, x, getHeight() / 2,
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
            g2.fill(new Rectangle2D.Double(x, y, this.getWidth(), this.getHeight()));

            g2.drawImage(img, 0, 0, this);
            Font font = new Font("League-Gothic", Font.BOLD, 40);

            //Create Core
            int core = RemoteSysSpec.getInt("NUMBER_OF_PROCESSORS");
            int Size = 15;
            int StartX = 12;//this.getWidth()-core*(Size+2);
            int StartY = 5;
            g2.setPaint(Color.DARK_GRAY);
            g2.fill(new Rectangle2D.Double(StartX - 10, StartY, core * (Size + 2) + 5, Size + 5));
            g2.setPaint(Color.WHITE);
            g2.fill(new Rectangle2D.Double(StartX - 9, StartY + 1, core * (Size + 2) + 3, Size + 3));

            g2.setPaint(Color.DARK_GRAY);

            for (int i = 0; i < core; i++) {
                g2.setPaint(Color.DARK_GRAY);
                int thisStart = StartX + (Size + 2) * i - 7;
                g2.fill(new Rectangle2D.Double(thisStart, StartY + 3, Size, Size));
                font = new Font("League-Gothic", Font.ROMAN_BASELINE, 12);
                g.setFont(font);
                g2.setPaint(Color.GREEN);
                g2.drawString("" + (i + 1), thisStart + 3, StartY + 15);
            }

            font = new Font("League-Gothic", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.LIGHT_GRAY);
            g.drawString(CPU_USAGE_INFO, getWidth() - (CPU_USAGE_INFO.length() * 7), getHeight() - 45);
            g.setColor(Color.GRAY);
            g.drawString(CPU_INFO, getWidth() - (CPU_USAGE_INFO.length() * 7), getHeight() - 35);
            g.setColor(Color.LIGHT_GRAY);
            g.drawString(RAM_INFO, getWidth() - (CPU_USAGE_INFO.length() * 7), getHeight() - 25);
            g.setColor(Color.GRAY);
            g.drawString(SWAP_INFO, getWidth() - (CPU_USAGE_INFO.length() * 7), getHeight() - 15);
            g.setColor(Color.LIGHT_GRAY);
            String toPrintNameandIP = tempRemoteTestMessage + "/" + RemoteSysSpec.getString("OPERATING_SYSTEM") + "/" + RemoteSysSpec.getString("PC_NAME") + "/" + RemoteSysSpec.getString("IP_ADDRESS");
            g.drawString(toPrintNameandIP, getWidth() - (toPrintNameandIP.length() * 7), getHeight() - 5);

            //  
            //SysInfo-End
        } catch (JSONException ex) {
            Logger.getLogger(ScreenShareClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @throws JSONException
     * @throws IOException
     */
    private void createDatafile() throws JSONException, IOException {

        DataFile = new File(System.getProperty("java.io.tmpdir") + "ppmcuScreenShareClient.array");
        // System.out.println("Temp file : " + temp2.getAbsolutePath());
        //Write empty array
        FileWriter fw = null;
        try {
            fw = new FileWriter(DataFile.getAbsoluteFile());
        } catch (IOException ex) {
            Logger.getLogger(ScreenShareClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(new JSONObject("{'LICENSE_KEY':" + LICENSE_KEY + ",'data':[]}").toString());
        bw.close();

    }

    /**
     *
     * @param Data
     * @throws JSONException
     * @throws IOException
     */
    private void writeDatafile(String Data) throws JSONException, IOException {

        Charset charset = Charset.forName("US-ASCII");
        String allData = "";
        try (BufferedReader reader = Files.newBufferedReader(DataFile.toPath(), charset)) {
            String line = null;
            ;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                allData = allData + line;
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        JSONObject fileData = new JSONObject(allData);

        if (fileData.getString("LICENSE_KEY").contentEquals(LICENSE_KEY)) {
            JSONArray data = fileData.getJSONArray("data");


            if (data.length() > 3) {
                data.remove(2); //Allow only 3 connections storage
            }
            data.put(Data);

            FileWriter fw = null;
            fw = new FileWriter(DataFile.getAbsoluteFile());

            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fileData.toString());
            bw.close();



        } else {
            createDatafile();
            writeDatafile(Data);
        }



    }
}
