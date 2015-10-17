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

/**
 *
 * @author Manoj
 */
public class GeneralInageProcessingHandler  extends CustomHandler {
    
    static String baseDir="delete/";

    @Override
    public void handle(HttpExchange he) throws IOException {
        Map queryMap = he.getRequestURI().getQuery() != null ? queryToMap(he.getRequestURI().getQuery()) : new HashMap<String, String>();
        byte[] result = new byte[0];
        
        if (queryMap.containsKey("imgNmae")) {
        
        }
        
        //Send out the message
        he.sendResponseHeaders(200, result.length);
        OutputStream oshe = he.getResponseBody();
        oshe.write(result);
        oshe.close();
    }
    
}
