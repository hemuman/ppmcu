/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import json.JSONObject;

/**
 *
 * @author PDI
 */
public abstract class PPMCUApp extends JPanel {
    
    public String LICENSE_KEY; // License key variable is must.
    public JSONObject[] RemoteSysSpecs;// RemoteSysSpecs variable is must.
    public String TaskName="AppName"; // Set the name of application.
    
    //public PPMCUApp(String WebServerName,String LICENSE_KEY, JSONObject SysSpec){}
    
    public abstract boolean execute();
}
