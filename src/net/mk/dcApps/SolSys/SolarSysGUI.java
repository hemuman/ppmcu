/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dcApps.SolSys;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONException;
import json.JSONObject;
import net.mk.DTasks.DCTaskSolarSystem;
import net.mk.dc.DistributedTaskService;
import net.mk.dcApps.DCGUI;
import net.mk.ppmcu2D.ScreenCapture;
import net.mk.ppmcu2D.UIToolKit;

/**
 *
 * @author PDI
 */
public class SolarSysGUI extends javax.swing.JPanel {
String LICENSE_KEY;
JSONObject RemoteSysSpec;
public JSONObject[] RemoteSysSpecs;
BufferedImage bufferedImage;
BufferedImage[] bufferedImages;
int FRAME_COUNT=0;
int[] FRAME_COUNTS;
double FPS=0.0;
double[] FPSs;
boolean MULTI_SYS=false;
int SpecCount=0;
boolean RUN_GRAPHICS=true;
    /**
     * Creates new form SolarSysGUI
     */
    public SolarSysGUI(String _LICENSE_KEY,JSONObject _RemoteSysSpec,boolean debug) {
        this.LICENSE_KEY=_LICENSE_KEY;
        this.RemoteSysSpec=_RemoteSysSpec;
        initComponents();

        final double StartTime=System.currentTimeMillis() / 1000.0;
         
           Thread GetMPPCLIENTInfoService = new Thread(new Runnable() {
            public void run() 
            {
                    while(true)
                    { 
                        try {
                            bufferedImage=ScreenCapture.getByteAsImage((byte[]) new DistributedTaskService(LICENSE_KEY,RemoteSysSpec,5559,new DCTaskSolarSystem(getWidth(), getHeight(),StartTime,100+System.currentTimeMillis() / 1000.0, true)).execute());
                            FRAME_COUNT++;
                            repaint();
                        } catch (IOException ex) {
                            Logger.getLogger(SolarSysGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                    }
                } 
        });
           
        GetMPPCLIENTInfoService.start();
        
        Thread GetFPS = new Thread(new Runnable() {
            public void run() 
            {
                    while(true)
                    { 
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SolarSysGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        FPS=FRAME_COUNT;
                       FRAME_COUNT=0;
                       
                    }
                } 
        });
           
        GetFPS.start();
    }
    
    public SolarSysGUI(String _LICENSE_KEY,JSONObject[] _RemoteSysSpec,boolean debug) {
        this.LICENSE_KEY=_LICENSE_KEY;
        this.RemoteSysSpecs=_RemoteSysSpec;
        bufferedImages=new BufferedImage[RemoteSysSpecs.length];
        FRAME_COUNTS= new int[RemoteSysSpecs.length];
        FPSs=new double[RemoteSysSpecs.length];
        MULTI_SYS=true;
        
        initComponents();

        
    }
    public SolarSysGUI(String WebServerName, String _LICENSE_KEY,JSONObject _RemoteSysSpec) {
        this.LICENSE_KEY=_LICENSE_KEY;
        this.RemoteSysSpecs=new JSONObject[]{};
        bufferedImages=new BufferedImage[RemoteSysSpecs.length];
        FRAME_COUNTS= new int[RemoteSysSpecs.length];
        FPSs=new double[RemoteSysSpecs.length];
        MULTI_SYS=true;
        
        initComponents();

        
    }
    
    public void startGraphics(){
        bufferedImages=new BufferedImage[RemoteSysSpecs.length];
        FRAME_COUNTS= new int[RemoteSysSpecs.length];
        FPSs=new double[RemoteSysSpecs.length];
        MULTI_SYS=true;
        final double StartTime=System.currentTimeMillis() / 1000.0;
         
        for(int i=0;i<RemoteSysSpecs.length;i++)
         {
            Thread GetSysInfoServicen = new Thread(new Runnable() {
            public void run() {
                int thisCount=-1;
            while(RUN_GRAPHICS)
                    {   if(thisCount<0) //i.e. Not initiated already
                        if(SpecCount>=RemoteSysSpecs.length)SpecCount=0; //Set if it is first entry only
                        try {
                            bufferedImages[SpecCount]=ScreenCapture.getByteAsImage((byte[]) new DistributedTaskService(LICENSE_KEY,RemoteSysSpecs[SpecCount],5559,new DCTaskSolarSystem(getWidth(), getHeight(),StartTime,100+System.currentTimeMillis() / 1000.0, true)).execute());
                            FRAME_COUNTS[SpecCount]++;
                            repaint();
                            SpecCount++;
                        } catch (IOException ex) {
                            Logger.getLogger(SolarSysGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                    }
                
            }
        });
        
        GetSysInfoServicen.start();            
                        
         }
        
        Thread GetFPS = new Thread(new Runnable() {
            public void run() 
            {
                    while(RUN_GRAPHICS)
                    { 
                         for(int i=0;i<RemoteSysSpecs.length;i++)
         {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SolarSysGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        FPSs[i]=FRAME_COUNTS[i];
                        FRAME_COUNTS[i]=0;
                        repaint();
         }
                       
                    }
                } 
        });
           
        GetFPS.start();
    }


    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();

        jToggleButton1.setText("Run Solar System");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToggleButton1)
                .addGap(0, 568, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 362, Short.MAX_VALUE)
                .addComponent(jToggleButton1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        
        if(jToggleButton1.isSelected()){
            RemoteSysSpecs=DCGUI.getRemoteSysSpecs();
        RUN_GRAPHICS=true;
        startGraphics();
        jToggleButton1.setText("Stop");
        }else{
            RUN_GRAPHICS=false;
            jToggleButton1.setText("Run Solar System");
        }
        
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables


    protected void paintComponent( Graphics g ) 
{
   
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
        
        Font font = new Font("League-Gothic", Font.BOLD,20);
        g.setColor(Color.GREEN);
        g.setFont(font);
        //MPP (massively parallel processing)
        g.drawString("MPP (massively parallel processing) Clients", 5,25);
        g.drawRoundRect(2, 2, 430, 28, 20, 20);
        font = new Font("League-Gothic", Font.PLAIN, 11);
        g.setColor(Color.GREEN);
        g.setFont(font);
        
        if(MULTI_SYS){
        // x=160;
        //y=35;
            int[] BestDisplayData=UIToolKit.getBestVisualArrangement(bufferedImages.length,true);
            double scale=1.0/BestDisplayData[2];
        AffineTransform trans ; Graphics2D gi;
        for(int i=0;i<bufferedImages.length;i++){
            
            
            if(bufferedImages[i]!=null)
            { 
                System.out.println("i="+i+" x="+bufferedImages[i].getWidth()+" y="+bufferedImages[i].getHeight());
                if(y>getHeight()-(bufferedImages[i].getHeight()*scale))
                { 
                    y=0; 
                    x=(int) (x+bufferedImages[i].getWidth()*scale);
                }
                BufferedImage img = new BufferedImage((int) (bufferedImages[i].getWidth()*scale), 
                        (int) (bufferedImages[i].getHeight()*scale), BufferedImage.TYPE_INT_RGB);
                
                trans = AffineTransform.getScaleInstance(scale,scale);
                
                gi =img.createGraphics();
		gi.drawRenderedImage(bufferedImages[i], trans);
                System.out.println("i-Scaled="+i+" Wd="+img.getWidth()+" Ht="+img.getHeight());
                 
                
                //FPSs[i]=
                g.drawImage(img, x, y, this);
                y=y+img.getHeight();
                try {
                    g.drawString(FPSs[i]+" fps @"+RemoteSysSpecs[i].getString("IP_ADDRESS"), x, y-5);
                } catch (JSONException ex) {
                    Logger.getLogger(SolarSysGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
 
        }
        
        }else{
                g.drawImage(bufferedImage, 0, 0, this);
                g.drawString(FPS+" fps", 5, this.getHeight()-15);
        }

}
    
    /**
     * 
     * @param BlocksToDisplay
     * @param debug
     * @return 
     */
  
    public static void main(String[] args){
        
        UIToolKit.getBestVisualArrangement(5,true);
    }
}

