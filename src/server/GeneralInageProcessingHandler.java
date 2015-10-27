/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONArray;
import json.JSONException;
import net.mk.FJTasks.SendEmail;
import static server.GenericUploadHandler._uuids;

/**
 *
 * @author Manoj
 */
public class GeneralInageProcessingHandler  extends CustomHandler {
    
    static String baseDir="delete/";
    String fileExt = "";
    
    public GeneralInageProcessingHandler(String fileExt) {
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
                ImageProcessingHelper.generateGIF(imageURLs, gifName , 500);
                 String[] emails;
                if(email.contains(",")){
                emails=email.split(",");
                }else{ emails=new String[]{email};}
                //logger.info(email+"");  
                //Send array of images.
                SendEmail.sendAsyncEmail("QiChik | Mofie" , emails, "Hello There!", new String[]{gifName});
                
            } catch (JSONException ex) {
                Logger.getLogger(GeneralInageProcessingHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }
        
        //Send out the message
        he.sendResponseHeaders(200, result.length);
        OutputStream oshe = he.getResponseBody();
        oshe.write(result);
        oshe.close();
    }
    
}
