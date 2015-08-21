/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONException;
import json.JSONObject;

/**
 *
 * @author Manoj
 */
public class HealthCheck extends CustomHandler  {

    @Override
    public void handle(HttpExchange t) throws IOException {
        JSONObject response=new JSONObject();
        try {
            response.put("title", "Gear Server");
        } catch (JSONException ex) {
            Logger.getLogger(CustomHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(response.toString());
        byte[] result=response.toString().getBytes();
         t.sendResponseHeaders(200, result.length);
        OutputStream os = t.getResponseBody();
        os.write(result);
        os.close();
    }
    
}
