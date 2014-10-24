/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dcApps;

import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONException;
import json.JSONObject;
import mkfs66o96.HTTPServer;

/**
 *
 * @author PDI
 */
public class DCAppLogger {
    
    /**
     * 
     * @param RemoteSysSpecs 
     */
    public static void updateServerRespose(JSONObject[] RemoteSysSpecs){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<RemoteSysSpecs.length;i++){
          
                sb.append(RemoteSysSpecs[i].toString()); 
       }
        HTTPServer.StaticResponse=sb.toString();
    }
    
    /**
     * 
     * @param RemoteSysSpecs 
     */
    public static void updateServerRespose(String RemoteSysSpecs ){

        HTTPServer.StaticResponse=RemoteSysSpecs;
    }
    
}
