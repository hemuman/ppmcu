/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.mk.FJTasks.AWSAIServices;

/**
 *
 * @author Manoj
 */
public class ImageAIHelper {
    
    public static BufferedImage getFacesDaetailed(BufferedImage theMainImage){
        try {
            AWSAIServices awsImageAI= new AWSAIServices();
            
            Map<String, Object> facesData=awsImageAI.getFaces(theMainImage);
            
            Set rectangles=(Set) facesData.get("BoundingBox");
            
            return ImageUpdateHelper.drawRectangle(theMainImage, rectangles);
            
        } catch (IOException ex) {
            Logger.getLogger(ImageAIHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return theMainImage;
    }
    
    public static BufferedImage getLabelsDetails(BufferedImage theMainImage){
        try {
            AWSAIServices awsImageAI= new AWSAIServices();
            
            Map<String, Object> facesData=awsImageAI.getLabels(theMainImage);
            
            
            return ImageUpdateHelper.drawString(theMainImage, facesData);
            
        } catch (IOException ex) {
            Logger.getLogger(ImageAIHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return theMainImage;
    }
    
    public static void main (String... args) throws IOException{
        File imgPath = new File("G:/delete/images/Top-Scuba-Diving-Movies.jpg");
        BufferedImage bufferedImage = ImageIO.read(imgPath);
        
        bufferedImage =ImageAIHelper.getFacesDaetailed(bufferedImage);
        
        bufferedImage =ImageAIHelper.getLabelsDetails(bufferedImage);
        
        ImageIO.write(bufferedImage, "PNG", new File("G:/delete/AwsFaceMarked.png"));
        
    }
}
