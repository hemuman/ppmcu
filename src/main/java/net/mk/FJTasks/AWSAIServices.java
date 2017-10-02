/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.FJTasks;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import server.ImageProcessingHelper;

/**
 *
 * @author Manoj
 */
public class AWSAIServices {
    AmazonRekognition rekognitionClient;
    public AWSAIServices(){
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIKTN3K5RVANKV63Q", "5X/Mgs7QjhiootmgQLwI7p+abUMSTJNn3GZbiadj");
        AWSCredentials credentials;
        
         rekognitionClient = AmazonRekognitionClientBuilder
                .standard()
                .withRegion(Regions.US_WEST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
         
    
    }
    
    /**
     * 
     * @param imageURL
     * @return
     * @throws IOException 
     */
    public Map<String, Object> getFaces(String imageURL) throws IOException{
        File imgPath = new File(imageURL);
        BufferedImage bufferedImage = ImageIO.read(imgPath);
        return getFaces( bufferedImage);
    }
    
    
    public Map<String, Object> getFaces(BufferedImage bufferedImage) throws IOException{
        HashMap<String, Object> thefacesData=new HashMap<>();
        DetectFacesRequest dfr= new DetectFacesRequest().withImage(getAWSFormatImage(bufferedImage));
         DetectFacesResult result2 = rekognitionClient.detectFaces(dfr);
             List<FaceDetail> labels2 = result2.getFaceDetails();

            // System.out.println("Detected labels for " + photo);
            
            Set<Rectangle> bbxs=new HashSet<>();
            for (FaceDetail label : labels2) {
               // System.out.println("Smile"+label.getSmile().getValue());
               //Bounding boxes
                 BoundingBox bbx=label.getBoundingBox();
                 //System.out.println("BoundingBox At"+ bbx.toString());
                 int x1=(int) (bufferedImage.getWidth()*bbx.getLeft());
                 int y1=(int) (bufferedImage.getHeight()*bbx.getTop());
                 
                 int width=(int) (bufferedImage.getWidth()*bbx.getWidth());
                 int height=(int) (bufferedImage.getHeight()*bbx.getHeight());
                 
                 double x2=x1+bufferedImage.getWidth()*bbx.getWidth();
                 double y2=y1+bufferedImage.getHeight()*bbx.getHeight();
                 
                 Rectangle rect=new Rectangle(x1, y1, width, height);
                 bbxs.add(rect);
                 
                 
                 
            }
            
            thefacesData.put("BoundingBox", bbxs);
            
            
            //
            
            return thefacesData;

    }
    
    
    public Map<String, Object> getLabels(String imageURL) throws IOException{
        File imgPath = new File(imageURL);
        BufferedImage bufferedImage = ImageIO.read(imgPath);
        return getLabels( bufferedImage);
    }

    public Map<String, Object> getLabels(BufferedImage bufferedImage) throws IOException{
        HashMap<String, Object> theLabels=new HashMap<>();
        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(getAWSFormatImage(bufferedImage))
                .withMaxLabels(10)
                .withMinConfidence(77F);
        
        DetectLabelsResult result = rekognitionClient.detectLabels(request);
            List<Label> labels = result.getLabels();
            //System.out.println("Detected labels for " + photo);
            for (Label label : labels) {
                //System.out.println(label.getName() + ": " + label.getConfidence().toString());
                theLabels.put(label.getName(), label.getConfidence().toString());
            }
            
            return theLabels;
    }
    public static void main(String[] args) throws Exception {

        String photo = "photo.jpg";
        String bucket = "S3bucket";
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIKTN3K5RVANKV63Q", "5X/Mgs7QjhiootmgQLwI7p+abUMSTJNn3GZbiadj");
        AWSCredentials credentials;
//      try {
//          credentials = new ProfileCredentialsProvider("AdminUser").getCredentials();
//      } catch(Exception e) {
//         throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
//          + "Please make sure that your credentials file is at the correct "
//          + "location (/Users/userid/.aws/credentials), and is in a valid format.", e);
//      }

        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
                .standard()
                .withRegion(Regions.US_WEST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();

        //ByteBuffer buf = ByteBuffer.wrap(ImageProcessingHelper.extractBytes("G:/delete/images/best-home-interior-designer-06.jpg"));
        Image testImage = new Image();//testImage.setBytes(buf);
        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(getAWSFormatImage("G:/delete/images/Top-Scuba-Diving-Movies.jpg"))
                .withMaxLabels(10)
                .withMinConfidence(77F);
        
        DetectFacesRequest dfr= new DetectFacesRequest().withImage(getAWSFormatImage("G:/delete/images/Top-Scuba-Diving-Movies.jpg"));

        try {
            DetectLabelsResult result = rekognitionClient.detectLabels(request);
            List<Label> labels = result.getLabels();
            System.out.println("Detected labels for " + photo);
            for (Label label : labels) {
                System.out.println(label.getName() + ": " + label.getConfidence().toString());
            }
            
            DetectFacesResult result2 = rekognitionClient.detectFaces(dfr);
             List<FaceDetail> labels2 = result2.getFaceDetails();

             System.out.println("Detected labels for " + photo);
            for (FaceDetail label : labels2) {
               // System.out.println("Smile"+label.getSmile().getValue());
                 BoundingBox bbx=label.getBoundingBox();
                 //System.out.println("BoundingBox At"+ bbx.toString());
                 
                 System.out.println(label.toString());
            }
            
        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }
    }

    public static Image getAWSFormatImage(String ImageName) throws IOException {
        File imgPath = new File(ImageName);
        BufferedImage bufferedImage = ImageIO.read(imgPath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        ByteBuffer byteBuffer = ByteBuffer.wrap(baos.toByteArray());
        return new Image().withBytes(byteBuffer);
    }
    
    public static Image getAWSFormatImage(BufferedImage bufferedImage) throws IOException {
        //File imgPath = new File(ImageName);
        //BufferedImage bufferedImage = ImageIO.read(imgPath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        ByteBuffer byteBuffer = ByteBuffer.wrap(baos.toByteArray());
        return new Image().withBytes(byteBuffer);
    }
}
