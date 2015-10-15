/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.sun.net.httpserver.HttpExchange;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.imageio.ImageIO;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import net.mk.FJTasks.SendEmail;
import net.mk.ppmcu2D.UIToolKit;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Manoj
 */
public class GenericUploadHandler extends CustomHandler {

    public static Map<String, String> _uuids = new HashMap();
    String fileExt = "";
    //String defaultSave="c:/delete_"; //Change here for local testing
    String defaultSave="delete/";
    public static Logger logger = Logger.getLogger("GeneriUploadHandler");  
    FileHandler fh;  

    public GenericUploadHandler(String fileExt) {
        this.fileExt = fileExt;
        
         try {  

        // This block configure the logger with handler and formatter  
        fh = new FileHandler("log/GUH"+System.currentTimeMillis()+".log");  
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  

        // the following statement is used to log any messages  
        //logger.info("My first log");  

    } catch (SecurityException e) {  
        e.printStackTrace();  
    } catch (IOException e) {  
        e.printStackTrace();  
    }  
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        
        logger.info(he.getRequestURI().toString());  

        Map queryMap = he.getRequestURI().getQuery() != null ? queryToMap(he.getRequestURI().getQuery()) : new HashMap<String, String>();
        byte[] result = new byte[0];
        if (queryMap.containsKey("multi-commKey")) {//Check if UUID exists
            try {
                int requestCount=Integer.parseInt(queryMap.get("multi-commKey").toString());
                requestCount=requestCount>20?20:requestCount;//max 20 allowed
                JSONArray jsArray=new JSONArray();
                for(int i=0;i<requestCount;i++)
                {
                    String response = UUID.randomUUID().toString();//.fromString((queryMap.get("deviceId").toString()+queryMap.get("email").toString())).toString();
                    _uuids.put(response, queryMap.get("email").toString());
                    System.out.println(response.toString());
                    jsArray.put(response);
                }
                result = jsArray.toString().getBytes();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (queryMap.containsKey("multi-Send")) {
            
            try {
                JSONArray imageUUIDs=new JSONArray(java.net.URLDecoder.decode(queryMap.get("multi-Send").toString(), "UTF-8"));
                String[] imageURLs=new String[imageUUIDs.length()];
                String email ="azmechatech@gmail.com";//= _uuids.get(queryMap.get("commKey"));
                for(int i=0;i<imageUUIDs.length();i++){
                    imageURLs[i]=defaultSave+ imageUUIDs.getString(i) + "." + fileExt;
                    email= _uuids.get(imageUUIDs.getString(i));
                    _uuids.remove(imageUUIDs.getString(i));//Also purge Key.
                }
                
                String[] emails;
                if(email.contains(",")){
                emails=email.split(",");
                }else{ emails=new String[]{email};}
                //logger.info(email+"");  
                //Send array of images.
                SendEmail.sendAsyncEmail("QiChik | " + (imageURLs.length)+" Attachments.", emails, "Hello There!", imageURLs);
                
            } catch (JSONException ex) {
                Logger.getLogger(GenericUploadHandler.class.getName()).log(Level.SEVERE, null, ex);
            } 
            
        } else
            if (!queryMap.containsKey("commKey")) {//Check if UUID exists
            try {
                String response = UUID.randomUUID().toString();//.fromString((queryMap.get("deviceId").toString()+queryMap.get("email").toString())).toString();
                _uuids.put(response, queryMap.get("email").toString());
                System.out.println(response.toString());
                result = response.toString().getBytes();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else { //If UUID exists then read the data from stream.
            String email = _uuids.get(queryMap.get("commKey"));
            
            System.out.print(email);

            InputStream inputStream = null;
            BufferedReader br = null;
            try {
                inputStream = he.getRequestBody();

                // br = new BufferedReader(new InputStreamReader(inputStream));
                String tempFileName = defaultSave + queryMap.get("commKey") + "." + fileExt;
                //65533
                //PNG First 8 Byte : 137 80 78 71 13 10 10 10
                //int seqPNG[]={ 137, 80 ,78, 71 ,13 ,10 ,10, 10};
                String seqPNG = 137 + "_" + 80 + "_" + 78 + "_" + 71 + "_" + 13 + "_";

                int binaryData = -1;
                boolean allowWrite = false;
                int counter = 0;
                StringBuilder seqMatch = new StringBuilder();
                while ((binaryData = inputStream.read()) != -1) {
                    seqMatch.append((char) binaryData);
                }
                //Apply filtering of "data:image/jpeg;base64,"
                String cordovaDefaultString="data:image/jpeg;base64,";
                if(seqMatch.indexOf(cordovaDefaultString)>=0)
                { 
                    seqMatch.delete(0, cordovaDefaultString.length());
                
                }
                BufferedImage image = decodeToImage(seqMatch.toString());

                try {
                    // retrieve image and save.

                    File outputfile = new File(tempFileName);
                    ImageIO.write(image, "png", outputfile);
                    
                    System.out.println("File written to disk!");
                } catch (IOException e) {

                }
                //Prepare to send thumbnail
                image =UIToolKit.scaleImage(image, 100, 100);
                String bas64Thumbnail=encodeToString(image, "png");
                boolean sendEmailFlag=true;
                if(queryMap.containsKey("doSendEmail")) 
                    sendEmailFlag=Boolean.parseBoolean(queryMap.get("doSendEmail").toString());
                
                
                if (sendEmailFlag) { //Finally send email. Try to submit it to a FJP Pool.
                    _uuids.remove(queryMap.get("commKey"));//Also purge Key if email sent allowed.
                    String[] emails;
                if(email.contains(",")){
                emails=email.split(",");
                }else{ emails=new String[]{email};}
                    SendEmail.sendAsyncEmail("QiChik | Photo is here." , emails, "Hello There!", tempFileName);
                    //Copy to the admin if debugging needed.
                    //SendEmail.sendAsyncEmail("QiChik | " + queryMap.get("commKey"), "azmechatech@gmail.com", SendEmail.getHTMLEmbdEmail("To: " + email, tempFileName));
                    JSONObject jsob=new JSONObject();
                    jsob.put("result", "success");
                    jsob.put("thumbnail", bas64Thumbnail);
                    jsob.put("thumbnailSize",240);
                    result = jsob.toString().getBytes();
                }

            } catch (Exception e) {
                result = e.getMessage().getBytes();
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //Send out the message
        he.sendResponseHeaders(200, result.length);
        OutputStream oshe = he.getResponseBody();
        oshe.write(result);
        oshe.close();

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

}
