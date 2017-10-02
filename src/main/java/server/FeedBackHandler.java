/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONException;
import json.JSONObject;
import net.mk.FJTasks.SendEmail;

/**
 *
 * @author Manoj
 */
public class FeedBackHandler extends CustomHandler  {

    @Override
    public void handle(HttpExchange he) throws IOException {
        Map queryMap = he.getRequestURI().getQuery() != null ? queryToMap(he.getRequestURI().getQuery()) : new HashMap<String, String>();
        byte[] result = new byte[0];
        InputStream inputStream = null;
         if (queryMap.containsKey("feedback")) {
             inputStream = he.getRequestBody();
             int binaryData = -1;
                boolean allowWrite = false;
                int counter = 0;
                StringBuilder seqMatch = new StringBuilder();
                while ((binaryData = inputStream.read()) != -1) {
                    seqMatch.append((char) binaryData);
                }
                
                SendEmail.sendAsyncEmail(queryMap.get("feedback").toString() , new String[]{"azmechatech@gmail.com"}, seqMatch.toString(), "");
                
            try {
                result=new JSONObject().put("Status", "Submitted").toString().getBytes();
            } catch (JSONException ex) {
                Logger.getLogger(FeedBackHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        } 
        
         //Send out the message
        he.sendResponseHeaders(200, result.length);
        OutputStream oshe = he.getResponseBody();
        oshe.write(result);
        oshe.close();
    }
    
}
