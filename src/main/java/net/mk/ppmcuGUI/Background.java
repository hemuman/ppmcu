/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcuGUI;

import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JFrame;
import net.mk.ppmcu2D.UIToolKit;
import static net.mk.ppmcuGUI.MoveMouseListener1024.getFrame;

/**
 *
 * @author PDI
 */
public class Background extends javax.swing.JPanel {

    /**
     * Creates new form Background
     */
    BufferedImage StampImage;
    public Background() {
        initComponents();
        MoveMouseListenerBG mml = new MoveMouseListenerBG(this);
        addMouseListener(mml);
        addMouseMotionListener(mml);
          StampImage= new BufferedImage(
         jLabel1.getIcon().getIconWidth(),
        jLabel1.getIcon().getIconHeight(),
         BufferedImage.TYPE_INT_RGB);
        Graphics g = StampImage.createGraphics();
        jLabel1.getIcon().paintIcon(null, g, 0,0);
        g.dispose();
        
    }
  protected void paintComponent(Graphics g) {
    
      
      g.drawImage(UIToolKit.getTessilatedImage(getWidth(), getHeight(), 1.0f, StampImage, true), 0, 0, this);
//        int imageWidth=StampImage.getWidth();
//        int componentWidth=getWidth();
//        
//        int ImageRepeat=componentWidth/imageWidth;
//        //System.out.println("#OldBackground "+ImageRepeat);
//        if(ImageRepeat>=1)
//            for(int i=0;i<=ImageRepeat;i++){
//               // System.out.println("#OldBackground Drawing="+i*imageWidth);
//                g.drawImage(StampImage, i*imageWidth, 0, this);
//            } 
        
    }
  
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/mk/icons/ClothFiber3.jpeg"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 598, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
