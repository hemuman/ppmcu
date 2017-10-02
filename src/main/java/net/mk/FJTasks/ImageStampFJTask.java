/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.FJTasks;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author PDI
 */
public class ImageStampFJTask extends RecursiveTask {
     protected BufferedImage StampImage;
     String ImagePathToStamp;
     int x;
    public ImageStampFJTask( BufferedImage StampImage,String ImagePathToStamp, int location,int toBeUsed) {
        this.StampImage = StampImage;
        this.ImagePathToStamp=ImagePathToStamp;
        this.x=location;
    }

    public Double compute() {
        long begTest = new java.util.Date().getTime();
        Double secs;
         try {
               BufferedImage ImageToStamp=ImageIO.read(new File(ImagePathToStamp));
               Graphics g = ImageToStamp.getGraphics();
               if(x==1)//"Left-Top"
                    g.drawImage(StampImage, 0,0, new Frame());
                    if(x==2)// "Top-Center"
                    g.drawImage(StampImage, (ImageToStamp.getWidth()/2)-(StampImage.getWidth()/2),0, new Frame());
                    if(x==3)//"Top-Right",
                    g.drawImage(StampImage, ImageToStamp.getWidth()-StampImage.getWidth(),0, new Frame());
                    if(x==4)//"Left-Center",
                    g.drawImage(StampImage, 0,(ImageToStamp.getHeight()/2)-(StampImage.getHeight()/2), new Frame());
                    if(x==5)//"Center-Center",
                    g.drawImage(StampImage, (ImageToStamp.getWidth()/2)-(StampImage.getWidth()/2),(ImageToStamp.getHeight()/2)-(StampImage.getHeight()/2), new Frame());
                    if(x==6)//"Center-Right",
                    g.drawImage(StampImage, ImageToStamp.getWidth()-StampImage.getWidth(),(ImageToStamp.getHeight()/2)-(StampImage.getHeight()/2), new Frame());
                    if(x==7)//"Left-Bottom",
                    g.drawImage(StampImage, 0,ImageToStamp.getHeight()-StampImage.getHeight(), new Frame());
                    if(x==8)//"Bottom-Center",
                    g.drawImage(StampImage, (ImageToStamp.getWidth()/2)-(StampImage.getWidth()/2),ImageToStamp.getHeight()-StampImage.getHeight(), new Frame());
                    if(x==9)//"Bottom-Right"
                    {
                       // System.out.println((ImageToStamp.getWidth()-StampImage.getWidth())+"\t"+(ImageToStamp.getHeight()-StampImage.getHeight()));
                        g.drawImage(StampImage, ImageToStamp.getWidth()-StampImage.getWidth(),ImageToStamp.getHeight()-StampImage.getHeight(), new Frame());
                    }
               ImageIO.write(ImageToStamp, "png", new File(ImagePathToStamp));
               g.dispose();
               
         } catch (IOException ex) {
             System.out.println(ex);
             Logger.getLogger(ImageStampFJTask.class.getName()).log(Level.SEVERE, null, ex);
         }
         secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
         return secs;
    }
    
}