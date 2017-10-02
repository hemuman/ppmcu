/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu2D;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author PDI
 */
public class JPanelSnapshot {
   
     public static BufferedImage[] getSnapshots(Component[] jpanels){
        BufferedImage[] BufferedImages=new BufferedImage[jpanels.length];
        Dimension size=new Dimension(1024,768);
        for(int i=0;i<jpanels.length;i++){
            JFrame frame = new JFrame ();
        frame.setContentPane ((Container) jpanels[i]);
        frame.pack ();
              jpanels[i].setPreferredSize(size);//.getPreferredSize ();
              jpanels[i].validate();
              jpanels[i].repaint();
              BufferedImage image = new BufferedImage (size.width, size.height, BufferedImage.TYPE_INT_RGB);
              jpanels[i].paint (image.createGraphics ());
              BufferedImages[i]=image;
              ImageIcon ico=new ImageIcon(image);
              JOptionPane.showMessageDialog(null,ico );
              frame.removeAll();
              frame.dispose ();
        //ImageIO.write (image, "PNG", new File ("test.png"));
        }
        
        return BufferedImages;
    }
    public static BufferedImage[] getSnapshots(JPanel[] jpanels){
        BufferedImage[] BufferedImages=new BufferedImage[jpanels.length];
        Dimension size=new Dimension(1024,768);
        for(int i=0;i<jpanels.length;i++){
            JFrame frame = new JFrame ();
        frame.setContentPane (jpanels[i]);
        frame.pack ();
              jpanels[i].setPreferredSize(size);//.getPreferredSize ();
              jpanels[i].validate();
              jpanels[i].repaint();
              BufferedImage image = new BufferedImage (size.width, size.height, BufferedImage.TYPE_INT_RGB);
              jpanels[i].paint (image.createGraphics ());
              BufferedImages[i]=image;
              //ImageIcon ico=new ImageIcon(image);
              //JOptionPane.showMessageDialog(null,ico );
              frame.removeAll();
              frame.dispose ();
        //ImageIO.write (image, "PNG", new File ("test.png"));
        }
        
        return BufferedImages;
    }
}
