/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.sun.net.httpserver.HttpExchange;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import net.mk.FJTasks.SendEmail;
import static server.GenericUploadHandler._uuids;


/**
 *
 * @author Manoj
 */
public class GeneralImageProcessingHandler  extends CustomHandler {
    
    //static String baseDir="delete/";
    String fileExt = "";
    
    public GeneralImageProcessingHandler(String fileExt) {
        this.fileExt = fileExt;}

    @Override
    public void handle(HttpExchange he) throws IOException {
        Map queryMap = he.getRequestURI().getQuery() != null ? queryToMap(he.getRequestURI().getQuery()) : new HashMap<String, String>();
        byte[] result = new byte[0];
        
        if (queryMap.containsKey("imgNmae")) {
        
        } else if (queryMap.containsKey("gifmakerName")) {
            JSONArray imageUUIDs = null;
            try {
                imageUUIDs = new JSONArray(java.net.URLDecoder.decode(queryMap.get("multi-Send").toString(), "UTF-8"));
                String[] imageURLs=new String[imageUUIDs.length()];
                String email ="azmechatech@gmail.com";//= _uuids.get(queryMap.get("commKey"));
                for(int i=0;i<imageUUIDs.length();i++){
                    imageURLs[i]=GenericUploadHandler.defaultSave+ imageUUIDs.getString(i) + "." + fileExt;
                    email= _uuids.get(imageUUIDs.getString(i));
                    _uuids.remove(imageUUIDs.getString(i));//Also purge Key.
                }
                String gifName=GenericUploadHandler.defaultSave+ queryMap.get("gifmakerName").toString()+".gif";
                ImageProcessingHelper.generateGIF(imageURLs, gifName , 200);
                 String[] emails;
                if(email.contains(",")){
                emails=email.split(",");
                }else{ emails=new String[]{email};}
                //logger.info(email+"");  
                //Send array of images.
                SendEmail.sendAsyncEmail("QiChik | Mofie" , emails, "Hello There!", new String[]{gifName});
                String bas64Thumbnail=ImageProcessingHelper.encodeToString(ReadFileIntoByteArray.getBytesFromFile(new File(gifName)), "gif");
                 JSONObject jsob=new JSONObject();
                    jsob.put("result", "success");
                    jsob.put("imgURL", "qichik/preview");
                    jsob.put("colorTheme", "#FFF");
                    jsob.put("thumbnail", bas64Thumbnail);
                    jsob.put("fileName", gifName);
                    jsob.put("comment", "Real nice QiChik!");
                    jsob.put("thumbnailSize",500);
                    result = jsob.toString().getBytes();
                
            } catch (JSONException ex) {
                Logger.getLogger(GeneralImageProcessingHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }else if (queryMap.containsKey("badgeMaker")) {
            
            String email ="azmechatech@gmail.com";
            String theMainfile=queryMap.get("badgeMakerImage").toString();
            String theBadgeFile=queryMap.get("badgeMakerBadge").toString();
            float opacity=(float) queryMap.get("opacity");
            int xPos=(int) queryMap.get("xPos");
            int yPos=(int) queryMap.get("yPos");
            email= _uuids.get(theMainfile);
            _uuids.remove(theMainfile);//Also purge Key.
            _uuids.remove(theBadgeFile);//Also purge Key.
            theMainfile=GenericUploadHandler.defaultSave+ theMainfile + "." + fileExt;
            theBadgeFile=GenericUploadHandler.badgeSaveLocation+ theBadgeFile + "." + fileExt;
            
            BufferedImage bi=ImageProcessingHelper.getStampedImage(theMainfile, theBadgeFile, opacity, xPos, yPos);
            File outputfile = new File(theMainfile);
            ImageIO.write(bi, "png", outputfile);
            
            try {
                
                String[] emails;
                if(email.contains(",")){
                emails=email.split(",");
                }else{ emails=new String[]{email};}
                //logger.info(email+"");  
                //Send array of images.
                SendEmail.sendAsyncEmail("QiChik | Badged Image" , emails, "Hello There!", new String[]{theMainfile});
                String bas64Thumbnail=ImageProcessingHelper.encodeToString(bi, fileExt);
                 JSONObject jsob=new JSONObject();
                    jsob.put("result", "success");
                    jsob.put("imgURL", "qichik/preview");
                    jsob.put("colorTheme", "#FFF");
                    jsob.put("thumbnail", bas64Thumbnail);
                    jsob.put("fileName", System.currentTimeMillis()+"."+fileExt);
                    jsob.put("comment", "Real nice badged QiChik!");
                    //jsob.put("thumbnailSize",500);
                    result = jsob.toString().getBytes();
                
            } catch (JSONException ex) {
                Logger.getLogger(GeneralImageProcessingHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
        //Send out the message
        he.sendResponseHeaders(200, result.length);
        OutputStream oshe = he.getResponseBody();
        oshe.write(result);
        oshe.close();
    }
    
}
