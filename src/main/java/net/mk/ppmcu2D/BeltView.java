/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu2D;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import json.JSONObject;
import mkMath.ParametricCurve;

/**
 *
 * @author PDI
 */
public class BeltView extends javax.swing.JPanel {
BufferedImage bgImage;
CircularLinkedList cll=new CircularLinkedList();
    /**
     * Creates new form BeltView
     */
public BeltView() {
        
        initComponents();
    }
    public BeltView(String WebServerName,String LicenseKey, JSONObject SysSpec) {
        initCLL();
        initComponents();
        repaint();
    }

    public void initCLL(){
        
        cll.insertFirst(UIToolKit.getWin7StyleRect(100, 100, 1.0f, true));
        for(int i=0;i<100;i++){
           
            cll.insertFirst(UIToolKit.getWithShadow(UIToolKit.getWin7StyleRect(10, 10, 1.0f, true), 1.0f, true));
        }
        for(int i=0;i<100;i++){
           
            cll.insertFirst(UIToolKit.getWithShadow(UIToolKit.getWin7StyleRect2(10, 10, 1.0f, true), 1.0f, true));
        }
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        jButton1.setText("Play Forever and Ever");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 584, Short.MAX_VALUE)
                .addComponent(jButton1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 391, Short.MAX_VALUE)
                .addComponent(jButton1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Thread animThread=new Thread(new Runnable() {
            public void run() {
                while(true){
                
                    BeltStartLoc++;
                     int[] delay=UIToolKit.getFastMotionDelay(10000);
                     repaint();
                for(int i=0;i<delay.length;i++){

                  repaint();
                    try {
                        Thread.sleep(delay[i]);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BeltView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BeltView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }});
    
        animThread.start();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables

    int positionX=100;
    int positionY=200;
    int BeltStartLoc;
    float transparency=1.0f;
    
     protected void paintComponent(Graphics g) {
         positionX=getWidth()/2;
         positionY=getHeight()/2;
    
         //Important, to refresh graphics page
        g.drawImage(UIToolKit.getNewsPrintBG2(getWidth(), getHeight(), 1, 1.0f, false), 0, 0, this);
        
        double StartFrom=Math.random();
         for(int i=0;i<100;i++){
            //double[] pos=ParametricCurve.Circle(StartFrom+i/10.0, getHeight()/3);
             int[] pos=ParametricCurve.ButterflyCurve(StartFrom+i/10.0, 20,3);
            
            if(cll.goToNextElement())
            g.drawImage((BufferedImage) cll.getActualElementData(), positionX+(int)pos[0], positionY+(int)pos[1], null);
         }
     }
    
     public static void main(String[] args) {

            JOptionPane.showMessageDialog(null, new BeltView("","",new JSONObject()));
       }

}
