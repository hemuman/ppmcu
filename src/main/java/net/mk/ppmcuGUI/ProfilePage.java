/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcuGUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JToggleButton;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import json.mkJSON;
import net.mk.ppmcu.GlobalMCUTest;

/**
 *
 * @author PDI
 */
public class ProfilePage extends javax.swing.JPanel {
    protected String LICENSE_KEY;
    protected String WebServerName="multicoreworld.manojky.net";
    ItemHandler2 handler = new ItemHandler2();
    JToggleButton[] machineButton;
    JSONObject profilePage;
    JSONObject SysSpec;
    JSONObject cache;
    public String brRankRatio="0/0";
    public String sctRankRatio="0/0";
    public  String mctRankRatio="0/0";
    public String brRankRatio_local="0/0";
    public String sctRankRatio_local="0/0";
    public  String mctRankRatio_local="0/0";
    JList list;
    boolean DISPLAYLIST;
    int TOOLBARBUTTONLIMIT=30;
    DefaultListModel dlm;
    /**
     * Creates new form ProfilePage
     * TODO : Refactoring
     */
    public ProfilePage(String LICENSE_KEY,String WebServerName,int DefaultDataDisplay) {
        this.WebServerName=WebServerName;
        this.LICENSE_KEY=LICENSE_KEY;
        dlm=new DefaultListModel();
        cache=new JSONObject();
        initComponents();
        try {
            //For Licenced applications
        if(DefaultDataDisplay==0)profilePage=new JSONObject(new Web().SendPOST("http://"+WebServerName+"/GetProfile.php", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false));
        if(DefaultDataDisplay==1)profilePage=new JSONObject(new Web().SendPOST("http://"+WebServerName+"/mcst/mcstTOP9BOOST", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false));
        if(DefaultDataDisplay==2)profilePage=new JSONObject(new Web().SendPOST("http://"+WebServerName+"/mcst/mcstTOP9MCT", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false));
        if(DefaultDataDisplay==3)profilePage=new JSONObject(new Web().SendPOST("http://"+WebServerName+"/mcst/mcstTOP9SCT", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false));
        
        JSONArray machines=profilePage.names();
        mkJSON.sortJSONArray(machines);
        machineButton=new JToggleButton[machines.length()+1];
        
        
              if(machines.length()>TOOLBARBUTTONLIMIT)//Use list instead
        {
            DISPLAYLIST=true;
            jToolBar2.removeAll();
            jList1.removeAll();
            jList1.setCellRenderer(new MyCellRenderer());
            jToolBar2.add(jList1);
            
        } else jToolBar2.removeAll();
        
        for(int i=0;i<machines.length();i++){
            machineButton[i]=new JToggleButton(profilePage.getString(machines.getString(i)));
            machineButton[i].addActionListener(handler);
            machineButton[i].setToolTipText(machines.getString(i));
            machineButton[i].setFont(new java.awt.Font("Times New Roman", 1, 11)); // NOI18N
            machineButton[i].setForeground(new java.awt.Color(153, 153, 255));
            
            if(!DISPLAYLIST)
            jToolBar2.add(machineButton[i]);
            else
            {
                dlm.addElement(new String[]{profilePage.getString(machines.getString(i)),machines.getString(i)});
              //  jList1.((Component)(Object)new String[]{profilePage.getString(machines.getString(i)),machines.getString(i)});
            }
        }
//              if(DISPLAYLIST)//Use list instead
//        {
//            list = new JList(machineButton); //data has type Object[]           
//            //list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//            //list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
//            //list.setVisibleRowCount(-1);
//            list.setSize(100, 50);
//            list.setCellRenderer(new MyCellRenderer());
//           
//            jToolBar2.add(list);
//        } 
        /**Power of All*/
            machineButton[machines.length()]=new JToggleButton("CLUSTER");
            machineButton[machines.length()].addActionListener(handler);
            machineButton[machines.length()].setToolTipText(machines.getString(0));
            machineButton[machines.length()].setFont(new java.awt.Font("Times New Roman", 1, 11)); // NOI18N
            machineButton[machines.length()].setForeground(new java.awt.Color(153, 153, 255));
            jToolBar2.add(machineButton[machines.length()]);
        /****************/

        machineButton[0].setSelected(true);
        machineButton[0].setForeground(new java.awt.Color(10, 10, 10));
        //System.out.println(machineButton[0].getToolTipText());
        setCPUValues(machineButton[0].getToolTipText());
        
        
        } catch (JSONException ex) {
            Logger.getLogger(ProfilePage.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        jButton2 = new javax.swing.JButton();
        ListTag = new javax.swing.JLabel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        IsReachable = new javax.swing.JLabel();

        jToolBar1.setFloatable(false);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton1.setForeground(new java.awt.Color(153, 153, 255));
        jButton1.setText("Search & Add CPUs");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jToolBar1.add(jButton1);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton2.setForeground(new java.awt.Color(153, 153, 255));
        jButton2.setText("Generate Report");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jToolBar1.add(jButton2);

        ListTag.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ListTag.setForeground(new java.awt.Color(153, 153, 255));
        ListTag.setText("CPU's in your list:");

        jToolBar2.setBorder(null);
        jToolBar2.setFloatable(false);
        jToolBar2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar2.setRollover(true);

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 11)); // NOI18N
        jButton3.setForeground(new java.awt.Color(153, 153, 255));
        jButton3.setText("GreenMonster");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton3);

        jButton4.setText("jButton4");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton4);

        jButton5.setText("jButton5");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton5);

        jToggleButton1.setFont(new java.awt.Font("Times New Roman", 1, 11)); // NOI18N
        jToggleButton1.setForeground(new java.awt.Color(153, 153, 255));
        jToggleButton1.setText("GreenMonster");
        jToggleButton1.setFocusable(false);
        jToggleButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jToggleButton1);

        jList1.setModel(dlm);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jToolBar2.add(jScrollPane1);

        IsReachable.setForeground(new java.awt.Color(51, 255, 0));
        IsReachable.setText("...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(247, 247, 247)
                .addComponent(IsReachable, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                .addGap(129, 129, 129)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ListTag, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ListTag)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE))
                    .addComponent(IsReachable, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        
       String[] MACID=(String[])jList1.getSelectedValue();
       setCPUValues(MACID[1]);
               
                  repaint();
        
    }//GEN-LAST:event_jList1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IsReachable;
    public javax.swing.JLabel ListTag;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables

    /***
     * Copied from WSTGUI
     * @param g 
     */
   protected int getBoostPossibleRank(String WebServerName,String MAC_ADDRESS){
        try {
            //JSONObject datatoPost=new GlobalMCUTest().printMachineInfo(false);
            //JSONArray allRatio=new JSONArray(new Web().SendPOST("http://"+WebServerName+"/mcst/GetAllRatio.php", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false));
            JSONArray allRatio=new Web().readJsonArrayFromUrl("http://"+WebServerName+"/mcst/mcstAllBOOST", false);
            double BoostRatio=Double.parseDouble(new Web().SendPOST("http://"+WebServerName+"/mcst/GetBoostRatio.php", new String []{"LICENSE_KEY","USER_KEY","BOOST_RATIO"}, new String[] {LICENSE_KEY,MAC_ADDRESS,""}, false));
            double[] BoostRatioList=mkJSON.getDoubleArrayFromJSONArray(allRatio);
               
                Arrays.sort(BoostRatioList);
                    for(int i=0;i<BoostRatioList.length;i++){
                        if(BoostRatioList[i]>=BoostRatio)
                        { //System.out.println("Your machine stands at rank "+(BoostRatioList.length-i)+" among all "+BoostRatioList.length+" machines tested so far.");
                        return     BoostRatioList.length-i;
                        
                        } else if(BoostRatioList[BoostRatioList.length-1]<=BoostRatio){
                             System.out.println("#getBoostPossibleRank: Your machine stands at TOP in Boost Possible");
                            return 1;
                        }
                    }
                    return BoostRatioList.length;
        } catch (JSONException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
   }
   protected String getBoostPossibleRankRatio(String WebServerName,String MAC_ADDRESS){
        try {
           // JSONObject datatoPost=new GlobalMCUTest().printMachineInfo(false);
            
            
            //JSONArray allRatio=new JSONArray(new Web().SendPOST("http://"+WebServerName+"/mcst/GetAllRatio.php", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false));
            JSONArray allRatio=new Web().readJsonArrayFromUrl("http://"+WebServerName+"/mcst/mcstAllBOOST", false);
            double BoostRatio=Double.parseDouble(new Web().SendPOST("http://"+WebServerName+"/mcst/GetBoostRatio.php", new String []{"LICENSE_KEY","USER_KEY","BOOST_RATIO"}, new String[] {LICENSE_KEY,MAC_ADDRESS,""}, false));
            double[] BoostRatioList=mkJSON.getDoubleArrayFromJSONArray(allRatio);
            double WorldAvg= new JSONObject(new Web().SendPOST("http://"+WebServerName+"/mcst/mcst", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false)).getDouble("WA_BOOST_POSSIBLE");
                Arrays.sort(BoostRatioList);
                    for(int i=0;i<BoostRatioList.length;i++){
                        if(BoostRatioList[i]>BoostRatio)
                        { 
                            //System.out.println("Your machine stands at rank "+(BoostRatioList.length-i)+" among all "+BoostRatioList.length+" machines tested so far.");
//                            if(BoostRatio>WorldAvg){System.out.println("\n Machine rating:___ABOVE AVERAGE___, it can give you__BitCoins__ :)\n ");}
//                            else{System.out.println("\n Machine rating:___BELOW AVERAGE___.Time to upgrade :) \n DO NOT THINK ABOUT __BitCoins__!");}
//                      
                            return     BoostRatioList.length-i+"/"+BoostRatioList.length;
                        
                        }else if(BoostRatioList[BoostRatioList.length-1]<=BoostRatio){
                             System.out.println("#getBoostPossibleRankRatio: Your machine stands at TOP in Boost Possible");
                            return 1+"/"+BoostRatioList.length;
                        }
                    }
                    return BoostRatioList.length+"/"+BoostRatioList.length;
        } catch (JSONException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "NA";
   }

   /**
    * Single Core Timing Ranks
    * @param WebServerName
    * @return 
    */
   protected int getSCTPossibleRank(String WebServerName,String MAC_ADDRESS){
        try {
           // JSONObject datatoPost=new GlobalMCUTest().printMachineInfo(false);
            //JSONArray allRatio=new JSONArray(new Web().SendPOST("http://"+WebServerName+"/mcst/GetAllSCT.php", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false));
            JSONArray allRatio=new Web().readJsonArrayFromUrl("http://"+WebServerName+"/mcst/mcstAllSCT", false);
            double BoostRatio=Double.parseDouble(new Web().SendPOST("http://"+WebServerName+"/mcst/GetBoostRatio.php", new String []{"LICENSE_KEY","USER_KEY","SINGLE_CORE_TIME"}, new String[] {LICENSE_KEY,MAC_ADDRESS,""}, false));
            double[] BoostRatioList=mkJSON.getDoubleArrayFromJSONArray(allRatio);
                Arrays.sort(BoostRatioList);
                    for(int i=0;i<BoostRatioList.length;i++){
                        if(BoostRatioList[i]>=BoostRatio)
                        { //System.out.println("Your machine stands at rank "+(BoostRatioList.length-i)+" among all "+BoostRatioList.length+" machines tested so far.");
                        return    i;
                        
                        } else if(BoostRatioList[0]>BoostRatio){
                             System.out.println("#getSCTPossibleRank: Your machine stands at TOP in Single Core Timing");
                            return 1;
                        }
                    }
                    return BoostRatioList.length;
        } catch (JSONException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
   }
   protected String getSCTPossibleRatio(String WebServerName,String MAC_ADDRESS){
        try {
          //  JSONObject datatoPost=new GlobalMCUTest().printMachineInfo(false);
            
            
            //JSONArray allRatio=new JSONArray(new Web().SendPOST("http://"+WebServerName+"/mcst/GetAllSCT.php", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false));
            JSONArray allRatio=new Web().readJsonArrayFromUrl("http://"+WebServerName+"/mcst/mcstAllSCT", false);
            double BoostRatio=Double.parseDouble(new Web().SendPOST("http://"+WebServerName+"/mcst/GetBoostRatio.php", new String []{"LICENSE_KEY","USER_KEY","SINGLE_CORE_TIME"}, new String[] {LICENSE_KEY,MAC_ADDRESS,""}, false));
            double[] BoostRatioList=mkJSON.getDoubleArrayFromJSONArray(allRatio);
            double WorldAvg= new JSONObject(new Web().SendPOST("http://"+WebServerName+"/mcst/mcst", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false)).getDouble("WA_SINGLE_CORE_TIME_POSSIBLE");
                Arrays.sort(BoostRatioList);
                    for(int i=0;i<BoostRatioList.length;i++){
                        if(BoostRatioList[i]>BoostRatio)
                        { 
                            //System.out.println("Your machine stands at rank "+(BoostRatioList.length-i)+" among all "+BoostRatioList.length+" machines tested so far.");
//                            if(BoostRatio>WorldAvg){System.out.println("\n Machine rating:___ABOVE AVERAGE___, it can give you__BitCoins__ :)\n ");}
//                            else{System.out.println("\n Machine rating:___BELOW AVERAGE___.Time to upgrade :) \n DO NOT THINK ABOUT __BitCoins__!");}
//                      
                            return     i+"/"+BoostRatioList.length;
                        
                        }else if(BoostRatioList[0]>BoostRatio){//Minimum is top
                             System.out.println("#getSCTPossibleRatio: Your machine stands at TOP in Single Core Timing");
                            return 1+"/"+BoostRatioList.length;
                        }
                    }
                    return BoostRatioList.length+"/"+BoostRatioList.length;
        } catch (JSONException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "NA";
   }

   /**
    * Multi Core Timing Ranks
    * @param WebServerName
    * @return 
    */
   protected int getMCTPossibleRank(String WebServerName,String MAC_ADDRESS){
        try {
           // JSONObject datatoPost=new GlobalMCUTest().printMachineInfo(false);
            //JSONArray allRatio=new JSONArray(new Web().SendPOST("http://"+WebServerName+"/mcst/GetAllMCT.php", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false));
            JSONArray allRatio=new Web().readJsonArrayFromUrl("http://"+WebServerName+"/mcst/mcstAllMCT", false);
            double BoostRatio=Double.parseDouble(new Web().SendPOST("http://"+WebServerName+"/mcst/GetBoostRatio.php", new String []{"LICENSE_KEY","USER_KEY","MULTI_CORE_TIME"}, new String[] {LICENSE_KEY,MAC_ADDRESS,""}, false));
            double[] BoostRatioList=mkJSON.getDoubleArrayFromJSONArray(allRatio);
                Arrays.sort(BoostRatioList);
                    for(int i=0;i<BoostRatioList.length;i++){
                        if(BoostRatioList[i]>=BoostRatio)
                        { //System.out.println("Your machine stands at rank "+(BoostRatioList.length-i)+" among all "+BoostRatioList.length+" machines tested so far.");
                        return    i;
                        
                        } else if(BoostRatioList[0]>BoostRatio){
                             System.out.println("#getMCTPossibleRank: Your machine stands at TOP in Multi Core Timing");
                            return 1;
                        }
                    }
                    return BoostRatioList.length;
        } catch (JSONException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
   }
   protected String getMCTPossibleRatio(String WebServerName,String MAC_ADDRESS){
        try {
      
            //JSONArray allRatio=new JSONArray(new Web().SendPOST("http://"+WebServerName+"/mcst/GetAllMCT.php", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false));
            JSONArray allRatio=new Web().readJsonArrayFromUrl("http://"+WebServerName+"/mcst/mcstAllMCT", false);
            double BoostRatio=Double.parseDouble(new Web().SendPOST("http://"+WebServerName+"/mcst/GetBoostRatio.php", new String []{"LICENSE_KEY","USER_KEY","MULTI_CORE_TIME"}, new String[] {LICENSE_KEY,MAC_ADDRESS,""}, false));
            double[] BoostRatioList=mkJSON.getDoubleArrayFromJSONArray(allRatio);
            double WorldAvg= new JSONObject(new Web().SendPOST("http://"+WebServerName+"/mcst/mcst", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false)).getDouble("WA_MULTI_CORE_TIME_POSSIBLE");
                Arrays.sort(BoostRatioList);
                    for(int i=0;i<BoostRatioList.length;i++){
                        if(BoostRatioList[i]>BoostRatio)
                        { 
                            //System.out.println("Your machine stands at rank "+(BoostRatioList.length-i)+" among all "+BoostRatioList.length+" machines tested so far.");
//                            if(BoostRatio>WorldAvg){System.out.println("\n Machine rating:___ABOVE AVERAGE___, it can give you__BitCoins__ :)\n ");}
//                            else{System.out.println("\n Machine rating:___BELOW AVERAGE___.Time to upgrade :) \n DO NOT THINK ABOUT __BitCoins__!");}
//                      
                            return     i+"/"+BoostRatioList.length;
                        
                        }else if(BoostRatioList[0]>BoostRatio){
                             System.out.println("#getMCTPossibleRatio: Your machine stands at TOP in Multi Core Timing");
                            return 1+"/"+BoostRatioList.length;
                        }
                    }
                    return BoostRatioList.length+"/"+BoostRatioList.length;
        } catch (JSONException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "NA";
   }

   /**
    * Do refactoring work on this code.
    * @param g 
    */
protected void paintComponent( Graphics g ) 
{
    try {
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
        g2.fill(new Rectangle2D.Double(x, y, this.getWidth(), this.getHeight()));


        Font font = new Font("League-Gothic", Font.BOLD, 40);
       
        //Create Core
        int core = SysSpec.getInt("NUMBER_OF_PROCESSORS");
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


        StartX = 180;
        StartY = 15;
         //Print Header
            font = new Font("League-Gothic", Font.BOLD, 13);
            g.setFont(font);
            g.setColor(Color.lightGray);
            g.drawString("Analysis Results:", StartX + 11, StartY);
            g.setColor(Color.GREEN);
            g.drawLine(StartX + 6, StartY+2, StartX+200,  StartY+2);
            
        font = new Font("League-Gothic", Font.BOLD, 10);
        g.setFont(font);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Local Boost Rank", StartX + 11, StartY + 12);
        g.drawString("Local Single-Core Rank", StartX + 11, StartY + 24);
        g.drawString("Local Multi-Core Rank", StartX + 11, StartY + 36);
        
        g.drawString("Global Boost Rank", StartX + 11, StartY + 48);
        g.drawString("Global Single-Core Rank", StartX + 11, StartY + 60);
        g.drawString("Global Multi-Core Rank", StartX + 11, StartY + 72);


        font = new Font("League-Gothic", Font.BOLD, 10);
        g.setFont(font);
        g.setColor(Color.GREEN);
        g.drawString(":" + brRankRatio, StartX + 140, StartY + 12);
        g.drawString(":" + sctRankRatio, StartX + 140, StartY + 24);
        g.drawString(":" + mctRankRatio, StartX + 140, StartY + 36);
        g.drawString(":" + brRankRatio, StartX + 140, StartY + 48);
        g.drawString(":" + sctRankRatio, StartX + 140, StartY + 60);
        g.drawString(":" + mctRankRatio, StartX + 140, StartY + 72);

        //SysInfo-Start

        //g.setFont(getFont(5,Font.BOLD,22));
        font = new Font("League-Gothic", Font.BOLD, 10);
        g.setFont(font);
        g.setColor(Color.LIGHT_GRAY);

        //Print information

        JSONObject OS = new JSONObject();

        JSONArray names = new JSONArray();
        int BottomOffset = 25;
        if (SysSpec.has("OS")) {
            OS = SysSpec.getJSONObject("OS");
            names = OS.names();
            mkJSON.sortJSONArray(names);
            for (int i = 0; i < names.length(); i++) {

                g.drawString(names.getString(i), 10, getHeight() - BottomOffset - 12 * i);
                g.drawString(" : " + OS.getString(names.getString(i)), 80, getHeight() - BottomOffset - 12 * i);

            }
            //Print Header
            font = new Font("League-Gothic", Font.BOLD, 13);
            g.setFont(font);
            //g.setColor(Color.DARK_GRAY);
            g.drawString("Operating System", 5, getHeight() - (BottomOffset + 3) - 12 * names.length());
            g.drawLine(5, getHeight() - BottomOffset - 12 * names.length(), 120, getHeight() - BottomOffset - 12 * names.length());
          

        }

        font = new Font("League-Gothic", Font.BOLD, 10);
        g.setFont(font);
        g.setColor(Color.LIGHT_GRAY);

        int OS_ENDPOS = getHeight() - (BottomOffset + 20) - 12 * names.length();

        if (SysSpec.has("RAM")) {
            //Print Info
            OS = SysSpec.getJSONObject("RAM");
            names = OS.names();
            mkJSON.sortJSONArray(names);
            for (int i = 0; i < names.length(); i++) {
                g.drawString(names.getString(i), 10, OS_ENDPOS - 12 * i);
                g.drawString(" : " + OS.getString(names.getString(i)), 85, OS_ENDPOS - 12 * i);
            }

            //Print Header
            font = new Font("League-Gothic", Font.BOLD, 13);
            g.setFont(font);
            //g.setColor(Color.DARK_GRAY);
            g.drawString("Processing Hardware", 5, -1 + OS_ENDPOS - 12 * names.length());
            g.drawLine(5, OS_ENDPOS - 12 * names.length(), 150, OS_ENDPOS - 12 * names.length());
            font = new Font("League-Gothic", Font.BOLD, 10);
            g.setFont(font);
            g.setColor(Color.LIGHT_GRAY);
            
            OS_ENDPOS = OS_ENDPOS- 12 * names.length()-20;

        }

        if (SysSpec.has("ROM")) {
            //  START
            OS = SysSpec.getJSONObject("ROM");
            names = OS.names();
            mkJSON.sortJSONArray(names);
            for (int i = 0; i < names.length(); i++) {

                g.drawString(names.getString(i) + ":"
                        + "   T: " + OS.getJSONObject(names.getString(i)).getString("TOTAL_SPACE_GB") + " GB"
                        + "   A: " + OS.getJSONObject(names.getString(i)).getString("AVAILABLE_GB") + " GB"
                        + "   U: " + OS.getJSONObject(names.getString(i)).getString("USED_PERCENT")
                        + "   V: " + OS.getJSONObject(names.getString(i)).getString("TPYE"), 10, OS_ENDPOS - 12 * i);

            }

            //Print Header
            font = new Font("League-Gothic", Font.BOLD, 13);
            g.setFont(font);
           // g.setColor(Color.DARK_GRAY);
            g.drawString("Storage", 5, OS_ENDPOS- 13 * names.length());
            g.drawLine(5, OS_ENDPOS - 12 * names.length(), 65, OS_ENDPOS- 12 * names.length());
            font = new Font("League-Gothic", Font.BOLD, 15);
            g.setFont(font);
            //g.setColor(Color.DARK_GRAY);

            //SysInfo-End
        }
        
        //Is reachable
        //Print Header
            font = new Font("League-Gothic", Font.BOLD, 12);
            g.setFont(font);
            String PCNAME="NotGiven";
            if(SysSpec.has("PC_NAME"))PCNAME= SysSpec.getString("PC_NAME");
            g.drawString(SysSpec.getString("IP_ADDRESS")+"/"+PCNAME+" IsReachable:"+isReachable, 195, getHeight() - BottomOffset+20 - 13 * (names.length()+4));

        setOpaque(false);
        super.paintComponent(g);
        setOpaque(true);
    } catch (JSONException ex) {
        Logger.getLogger(FrontPage.class.getName()).log(Level.SEVERE, null, ex);
    }
}
boolean isReachable=false;
private void setCPUValues(String MAC_ADDRESS){
           
            
            if(cache.has(MAC_ADDRESS+"")){}else{
                
            }
                 try {
                     if(cache.has(MAC_ADDRESS)) //Check Cache first
                     {  
                            SysSpec=cache.getJSONObject(MAC_ADDRESS);
                            brRankRatio=SysSpec.getString("brRankRatio");
                            sctRankRatio=SysSpec.getString("sctRankRatio");
                            mctRankRatio=SysSpec.getString("mctRankRatio");
                     }
                     
                         else
                     {
                            brRankRatio= getBoostPossibleRankRatio(WebServerName,MAC_ADDRESS);
                            int sctRank=getSCTPossibleRank(WebServerName,MAC_ADDRESS);
                            sctRankRatio= getSCTPossibleRatio(WebServerName,MAC_ADDRESS);
                            int mctRank=getMCTPossibleRank(WebServerName,MAC_ADDRESS);
                            mctRankRatio= getMCTPossibleRatio(WebServerName,MAC_ADDRESS);
                             int bpRank=getBoostPossibleRank(WebServerName,MAC_ADDRESS);
                            SysSpec=new JSONObject(new Web().SendPOST("http://"+WebServerName+"/mcst/GetCPUProfile.php", new String []{"LICENSE_KEY","MAC_ADDRESS"}, new String[] {LICENSE_KEY,MAC_ADDRESS}, false));
                            SysSpec.put("brRankRatio", brRankRatio);
                            SysSpec.put("sctRankRatio", sctRankRatio);
                            SysSpec.put("mctRankRatio", mctRankRatio);
                            SysSpec.put("mctRank", mctRank);
                            SysSpec.put("sctRank", sctRank);
                            SysSpec.put("bpRank", bpRank);
                     cache.put(MAC_ADDRESS, SysSpec); //Add the data to cache for faster responses
                     }
                     InetAddress inet = InetAddress.getByName(SysSpec.getString("IP_ADDRESS"));
                     isReachable=inet.isReachable(0);
                     
                     //System.out.println(SysSpec.toString(1));
                 } catch (JSONException ex) {
                     Logger.getLogger(ProfilePage.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (UnknownHostException ex) {
            Logger.getLogger(ProfilePage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProfilePage.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    private class ItemHandler2 implements ActionListener
         {
         public void actionPerformed( ActionEvent e )
             {
                 // menuPopup.setVisible(false);
               //  System.out.println("e.getSource() ="+e.getSource() );
             // determine which menu item was selected
             for ( int i = 0; i < machineButton.length; i++ )
             if ( e.getSource() == machineButton[i ] )
                 {
                     machineButton[i].setForeground(new java.awt.Color(10, 10, 10));
                  System.out.println("Selected Machine:"+machineButton[i ].getToolTipText());
               // String MAC_ADDRESS=machineButton[i ].getToolTipText();
                setCPUValues(machineButton[i ].getToolTipText());
               
                  repaint();
                 //return;
             }else{ 
                 machineButton[i ].setSelected(false);
                 machineButton[i].setForeground(new java.awt.Color(153, 153, 255));
             }
         }
     }

    
    ///ADD listners for list
    private class ItemHandler3 implements MouseListener
         {
         public void actionPerformed( ActionEvent e )
             {
                 // menuPopup.setVisible(false);
               //  System.out.println("e.getSource() ="+e.getSource() );
             // determine which menu item was selected
             for ( int i = 0; i < machineButton.length; i++ )
             if ( e.getSource() == machineButton[i ] )
                 {
                     machineButton[i].setForeground(new java.awt.Color(10, 10, 10));
                  System.out.println("Selected Machine:"+machineButton[i ].getToolTipText());
               // String MAC_ADDRESS=machineButton[i ].getToolTipText();
                setCPUValues(machineButton[i ].getToolTipText());
               
                  repaint();
                 //return;
             }else{ 
                 machineButton[i ].setSelected(false);
                 machineButton[i].setForeground(new java.awt.Color(153, 153, 255));
             }
         }

        @Override
        public void mouseClicked(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mousePressed(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
     }
}



class MyCellRenderer extends JToggleButton implements ListCellRenderer<Object> {
     public MyCellRenderer() {
         setOpaque(true);
     }

     public Component getListCellRendererComponent(JList<?> list,
                                                   Object Data,
                                                   int index,
                                                   boolean isSelected,
                                                   boolean cellHasFocus) {

         String[] data=(String[]) Data;
         setText(data[0]);
         setToolTipText(data[1]);

         Color background;
         Color foreground;

         // check if this cell represents the current DnD drop location
         JList.DropLocation dropLocation = list.getDropLocation();
         if (dropLocation != null
                 && !dropLocation.isInsert()
                 && dropLocation.getIndex() == index) {

             background = Color.BLUE;
             foreground = Color.WHITE;

         // check if this cell is selected
         } else if (isSelected) {
             background = Color.RED;
             foreground = Color.WHITE;

         // unselected, and not the DnD drop location
         } else {
             background = Color.WHITE;
             foreground = Color.BLACK;
         };

         setBackground(background);
         setForeground(foreground);
        
         
        // public String getToo

         return this;
     }
 }