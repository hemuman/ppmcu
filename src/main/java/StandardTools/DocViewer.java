/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StandardTools;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author PDI
 */
public class DocViewer extends javax.swing.JPanel {
ImageIcon myImage;
    /**
     * Creates new form DocViewer
     */
    public DocViewer() {
        initComponents();
    }
    
    public void SetImage(String ImageFile){
        try {
            BufferedImage originalImage = ImageIO.read(new File(ImageFile));
            originalImage = createResizedCopy(originalImage, RefImage.getHeight(), RefImage.getWidth(), true);
            myImage = new ImageIcon(originalImage);
            RefImage.setOpaque(true);
            RefImage.setIcon(myImage);
            
        } catch (IOException ex) {
            Logger.getLogger(DocViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public void AddImage(String ImageFile){
            jComboBox1.addItem(ImageFile);
            SetImage(ImageFile);
    }
    
       public void setImageList(String[] imageList) {
       
        jComboBox1.removeAllItems();
        if (imageList != null) {
            for (int i = 0; i < imageList.length; i++) {
                jComboBox1.addItem(imageList[i]);
             // System.out.println("Displaying RImage"+imageList[i]);
                

            }
        }
      
       }
       
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane5 = new javax.swing.JScrollPane();
        RefImage = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();

        setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane5.setBackground(new java.awt.Color(255, 255, 255));

        RefImage.setBackground(new java.awt.Color(255, 255, 255));
        RefImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RefImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/1270941244_3d_modelling.png"))); // NOI18N
        RefImage.setOpaque(true);
        jScrollPane5.setViewportView(RefImage);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (jComboBox1.getItemCount() > 0) {
                SetImage(jComboBox1.getSelectedItem().toString());
         }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel RefImage;
    public javax.swing.JComboBox jComboBox1;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables
/**
 * Utility functions
 */
    BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha) {
        System.out.println("resizing...");
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    /**
     *
     * @param csImagesForSelected
     */
    
}
