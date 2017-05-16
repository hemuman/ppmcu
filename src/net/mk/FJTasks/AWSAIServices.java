/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.FJTasks;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import javax.imageio.ImageIO;
import server.ImageProcessingHelper;
/**
 *
 * @author Manoj
 */
public class AWSAIServices {
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

      ByteBuffer buf = ByteBuffer.wrap(ImageProcessingHelper.extractBytes("G:/delete/images/25a5a62ecbd1c89877ab25dc7a5810df.jpg"));
      Image testImage=new Image();//testImage.setBytes(buf);
      DetectLabelsRequest request = new DetectLabelsRequest()
    		  .withImage(getAWSFormatImage("G:/delete/images/25a5a62ecbd1c89877ab25dc7a5810df.jpg"))
    		  .withMaxLabels(10)
    		  .withMinConfidence(77F);

      try {
         DetectLabelsResult result = rekognitionClient.detectLabels(request);
         List <Label> labels = result.getLabels();
         
         
         

         System.out.println("Detected labels for " + photo);
         for (Label label: labels) {
            System.out.println(label.getName() + ": " + label.getConfidence().toString());
         }
      } catch(AmazonRekognitionException e) {
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
}
