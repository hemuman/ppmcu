/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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
    
    public static BufferedImage getStampedImage(String ImageToStamp,String badgePath,float opacity ,int xPos,int yPos){
        
        try {
            BufferedImage theMainImage = ImageIO.read(new File(ImageToStamp));
            BufferedImage theBadgeImage = ImageIO.read(new File(badgePath));
            BufferedImage StampImageBG=new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = StampImageBG.createGraphics();
            g.drawImage(theMainImage, Math.abs(StampImageBG.getWidth()-theMainImage.getWidth())/2,
                    0, null);
            //Math.abs(StampImageBG.getHeight()-ImageToStamp.getHeight())/2
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g.drawImage(theBadgeImage, xPos,yPos, null);
            g.dispose();
            return StampImageBG; 
            
        } catch (IOException ex) {
            Logger.getLogger(ImageProcessingHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
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
    
    
    public static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    
     /**
     * Encode image to string
     * @param image The image to encode
     * @param type jpeg, bmp, ...
     * @return encoded string
     */
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public static String encodeToString(byte[] imageBytes, String type) {
        String imageString = null;

        BASE64Encoder encoder = new BASE64Encoder();
        imageString = encoder.encode(imageBytes);

        return imageString;
    }
}
