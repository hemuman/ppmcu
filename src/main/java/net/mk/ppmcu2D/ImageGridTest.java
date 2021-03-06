/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu2D;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import json.JSONException;
import json.JSONObject;

/**
 *
 * @author PDI
 */
public class ImageGridTest extends javax.swing.JPanel {

    JFileChooser jfc;
    BufferedImage StampImage;
    /**
     * Creates new form ImageGridTest
     */
    public ImageGridTest(String WebserverName,String LicenseKey, JSONObject SysSpec) {
        jfc=new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setMultiSelectionEnabled(false);
        initComponents();
         //jLabel6  jLabel5
        StampImage = new BufferedImage(
                jButton1.getIcon().getIconWidth(),
                jButton1.getIcon().getIconHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = StampImage.createGraphics();
        jButton1.getIcon().paintIcon(null, g, 0, 0);
        g.dispose();
        
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/mk/icons/CamGrid.png"))); // NOI18N
        jButton1.setText("Browse");
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
                .addComponent(jButton1)
                .addGap(0, 578, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton1)
                .addContainerGap(373, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        jfc.showOpenDialog(this);
        try {
         StampImage= ImageIO.read(jfc.getSelectedFile());
        } catch (IOException ex) {
            Logger.getLogger(ImageGridTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        jButton1.setIcon(new ImageIcon(StampImage));
        repaint();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables

     protected void paintComponent(Graphics g) {

        if (!isOpaque()) {
            super.paintComponent(g);
            return;
        }
  
      g.drawImage(UIToolKit.getTessilatedImage(getWidth(), getHeight(), 1.0f, StampImage, true), 0, 0, this);
 
    }

      public static void main(String[] args) throws JSONException{
       /**
        * 
        * Systems GUIs
        */
        try {

            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    try {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    } catch (InstantiationException ex) {
                        Logger.getLogger(ImageGridTest.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(ImageGridTest.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(ImageGridTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ImageGridTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
    JOptionPane.showMessageDialog(null, new ImageGridTest("","",new JSONObject()));
}

}
