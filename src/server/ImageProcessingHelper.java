/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jhlabs.image.ChromeFilter;
import com.jhlabs.image.CrystallizeFilter;
import com.jhlabs.image.CurlFilter;
import com.jhlabs.image.DiffusionFilter;
import com.jhlabs.image.DitherFilter;
import com.jhlabs.image.EdgeFilter;
import com.jhlabs.image.EmbossFilter;
import com.jhlabs.image.GlowFilter;
import com.jhlabs.image.InvertFilter;
import com.jhlabs.image.KaleidoscopeFilter;
import com.jhlabs.image.LookupFilter;
import com.jhlabs.image.MarbleFilter;
import com.jhlabs.image.MotionBlurOp;
import com.jhlabs.image.NoiseFilter;
import com.jhlabs.image.OilFilter;
import com.jhlabs.image.PolarFilter;
import com.jhlabs.image.RaysFilter;
import com.jhlabs.image.ScaleFilter;
import com.jhlabs.image.SkyFilter;
import com.jhlabs.image.SmartBlurFilter;
import com.jhlabs.image.SphereFilter;
import com.jhlabs.image.StampFilter;
import com.jhlabs.image.TileImageFilter;
import com.jhlabs.image.TwirlFilter;
import com.jhlabs.image.UnsharpFilter;
import com.jhlabs.image.VariableBlurFilter;
import com.jhlabs.image.WaterFilter;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
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
    
    public static BufferedImage ConvolveOpFilter( BufferedImage theMainImage) throws IOException {
        float[] matrix = {
            0.111f, 0.111f, 0.111f,
            0.111f, 0.111f, 0.111f,
            0.111f, 0.111f, 0.111f,};
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrix));
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        return StampImageBG;
    }
    
    public static BufferedImage EdgeFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new EdgeFilter();
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        return StampImageBG;
    }
    
    public static BufferedImage RaysFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new RaysFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
    
    public static BufferedImage OilFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new OilFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
    
    public static BufferedImage SmartBlurFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new SmartBlurFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
    
    public static BufferedImage MarbleFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new MarbleFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
    
    public static BufferedImage NoiseFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new NoiseFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
    
    public static BufferedImage PolarFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new PolarFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
    
    public static BufferedImage WaterFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new WaterFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
    
    public static BufferedImage VariableBlurFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new VariableBlurFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
    
    public static BufferedImage UnsharpFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new UnsharpFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
    
     public static BufferedImage TwirlFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new TwirlFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
    
     public static BufferedImage TileImageFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new TileImageFilter(50,50);
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
     
     public static BufferedImage GlowFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new GlowFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
     
             
     /**
      * 
    float radius - The blur radius 0...
    float threshold - The threshold level 0..1.
    float softness - The threshold softness 0..1.
    int black - The "black" color.
    int white - The "white" color.

      * @param theMainImage
      * @return
      * @throws IOException 
      */
     public static BufferedImage StampFilter( BufferedImage theMainImage) throws IOException {
         StampFilter sf=new StampFilter();
         sf.setRadius(theMainImage.getWidth()/10);sf.setSoftness(0.5f);sf.setThreshold(0.5f);
         sf.setBlack(0);sf.setWhite(1);
          BufferedImageOp op = sf;
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
    
     public static BufferedImage SphereFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new SphereFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
     
     public static BufferedImage CurlFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new CurlFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
     
     public static BufferedImage CrystallizeFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new CrystallizeFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
     
     public static BufferedImage DiffusionFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new DiffusionFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
     
     /**
      * Here's the simplest 2x2 dither matrix:

                0 2
                3 1

And here's a 4x4 dither matrix:

                0 14  3 13
               11  5  8  6
               12  2 15  1
                7  9  4 10

      * @param theMainImage
      * @return
      * @throws IOException 
      */
     public static BufferedImage DitherFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new DitherFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
     
     public static BufferedImage InvertFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new InvertFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
     
     /**
      * Colormap Colormap - The colormap to use to do the mapping
      * @param theMainImage
      * @return
      * @throws IOException 
      */
     public static BufferedImage LookupFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new LookupFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
     
      public static BufferedImage KaleidoscopeFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new KaleidoscopeFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
      
      public static BufferedImage ChromeFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new ChromeFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
      
       public static BufferedImage EmbossFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new EmbossFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
       
     
     
     /**
      * Doesn't work, causes application crash.
      * @param theMainImage
      * @return
      * @throws IOException 
      */
      public static BufferedImage SkyFilter( BufferedImage theMainImage) throws IOException {
          BufferedImageOp op = new SkyFilter();
          
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        
        return StampImageBG;
    }
            
    public static BufferedImage ScaleFilter(BufferedImage theMainImage,int width,int height) throws IOException {
          BufferedImageOp op = new ScaleFilter(width,height);
        ////BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        return StampImageBG;
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
    
    public static void main(String... args){
        
    }
}
