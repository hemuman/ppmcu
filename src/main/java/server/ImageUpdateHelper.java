/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Set;

/**
 *
 * @author Manoj
 */
public class ImageUpdateHelper {

    public static BufferedImage drawRectangle(BufferedImage bi, Set<Rectangle> setOfRectangles) {
        //BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();
        for (Rectangle rect : setOfRectangles) {
            g2.drawRoundRect(rect.x, rect.y, rect.height, rect.width, 5, 5);
        }
        //ImageIO.write(bi, "PNG", new File("myPicture.png"));
        return bi;
    }
    
    public static BufferedImage drawString(BufferedImage img, Set<String> setOfTexts){
        Graphics2D g2d = img.createGraphics();
        //g2d.drawImage(old, 0, 0, w, h, this);
        g2d.setPaint(Color.red);
        g2d.setFont(new Font("Serif", Font.BOLD, 20));
        //String s = "Hello, world!";
        FontMetrics fm = g2d.getFontMetrics();
        int counter=0;
        for (String s : setOfTexts) {
            int x = img.getWidth() - fm.stringWidth(s) - 5;
            int y = 20*counter++;
            g2d.drawString(s, x, y);
        }
        
        g2d.dispose();
        return img;
    }

}
