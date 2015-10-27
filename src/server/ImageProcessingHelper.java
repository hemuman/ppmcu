/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Manoj
 */
public class ImageProcessingHelper {
    
    static BufferedImage StampImage;
    String StampImageUrl;
    
    public ImageProcessingHelper(String StampImageUrl){
        try {
            StampImage=ImageIO.read(new File(StampImageUrl)); //--Prod
             this.StampImageUrl=StampImageUrl;
            //  StampImage=ImageIO.read(new File("c:/resource_GearAppIcon.png")); //--Test
        } catch (IOException ex) {
            Logger.getLogger(ImageProcessingHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public BufferedImage getStampedImage(BufferedImage ImageToStamp,int xPos,int yPos){
        BufferedImage StampImageBG=new BufferedImage(StampImage.getWidth(), StampImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = StampImageBG.getGraphics();
        g.drawImage(ImageToStamp, Math.abs(StampImageBG.getWidth()-ImageToStamp.getWidth())/2,
                0, null);
        //Math.abs(StampImageBG.getHeight()-ImageToStamp.getHeight())/2
        g.drawImage(StampImage, xPos,yPos, null);
        g.dispose();
        return StampImageBG;
        
    }
    
    
    /**
     * Generate GIF on demand.
     * @param imageURLs
     * @param fileNameWithLocation
     * @param delay 
     */
    public static void generateGIF(String[] imageURLs, String fileNameWithLocation, int delay) {

        AnimatedGifEncoder e = new AnimatedGifEncoder();
        e.start(fileNameWithLocation);
        e.setDelay(delay);   // 1000=1 frame per sec
        e.setRepeat(0);
        
        for (String imageURL : imageURLs) {
            try {
                e.addFrame(ImageIO.read(new File(imageURL)));
            } catch (IOException ex) {
                Logger.getLogger(ImageProcessingHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        e.finish();
    }
}
