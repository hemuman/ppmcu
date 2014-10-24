/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcuGUI;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONException;
import json.JSONObject;
import mkfs66o96.Encrypt;

/**
 *
 * @author Manoj Kumar / hemuman@gmail.com
 */
public class MCWConfClass {
    
     public MCWConfClass(String MASTER_KEY,String USER_KEY,String NUMERIC_KEY) {
        
        if( new Encrypt().isValidKeyPair(MASTER_KEY, USER_KEY, NUMERIC_KEY)
               & (System.getProperty("os.name").toLowerCase().contains("wind"))) { //Run free on other OS
            System.out.println("License is valid.");
            System.out.println("Activating Paid modules...");
            setACTIVATE_HOME_SCREEN(true);
            setACTIVATE_SSC_SCREEN(true);
            setACTIVATE_MANAGER_SCREEN(true);
            setACTIVATE_IMAGESTAMP_SCREEN(true);
            setACTIVATE_DISTRIBUTED_COMPUTING_NODE(true);
            setACTIVATE_PROCESS_MONITORING(true);
            setACTIVATE_2DTEST_SCREEN(true);
            System.out.println("Done.");
        }else if(System.getProperty("os.name").toLowerCase().contains("wind")) {
            System.out.println("Activating Free modules");
            setACTIVATE_HOME_SCREEN(true);
            setACTIVATE_SSC_SCREEN(true);
            setACTIVATE_IMAGESTAMP_SCREEN(true);
            setACTIVATE_2DTEST_SCREEN(true);
            setACTIVATE_SSC_SCREEN(true);
            System.out.println("Done.");
        } else { //Run free on other OS except for Manager module
            System.out.println("Activating Free modules");
            setACTIVATE_HOME_SCREEN(true);
            setACTIVATE_SSC_SCREEN(true);
            setACTIVATE_IMAGESTAMP_SCREEN(true);
            setACTIVATE_PROCESS_MONITORING(true);
            setACTIVATE_2DTEST_SCREEN(true);
            setACTIVATE_DISTRIBUTED_COMPUTING_NODE(true);
            System.out.println("Done.");
        }
        
    }
    
    public static boolean hasInternet=false;
    public static boolean WEB_EXECUTION=true;
    public static boolean ShareLiveDetails=true;
    public static boolean REMOTE_DATA_MODE=false;
    private static String MASTER_KEY="";
    private static String USER_KEY="";
    private static String NUMERIC_KEY="";
    public static final String NO_GRAPHICS_RUN_DTProcessor="NoGraphics-StartDTaskProcessor";
    public static final String NO_GRAPHICS_RUN_TEST="NoGraphics-RUNTEST";
    public static final String VERSION="MCWV3.0.5-LICV0.1-DTV2.1";

    public static boolean isUSE_SIGAR_API() {
        return USE_SIGAR_API;
    }

    public static void setUSE_SIGAR_API(boolean USE_SIGAR_API) {
        MCWConfClass.USE_SIGAR_API = USE_SIGAR_API;
    }
    public static boolean USE_SIGAR_API=true;
    public static JSONObject SysSpec;
    public static JSONObject MCWAppVars;
    public static final int DCTPort=5559;
    public static final String COPYRIGHT = "\u00a9";
    public static String DCGUILogFile = "MCWDCGUIlog.log";
    public static int HTTPServerPort=4444;
    public static int DCAppLoggingInterval=2000;
    public static int ScreenServerPort=6666;
    public static final int ObjectCompressionLevel=9;
  
    public static final String WebExec="WebExec";// Yes /No
    public static final String LicKy="LicKy";// gvreeebsep 
    public static final String AccInfo="AccInfo";// AllUsers 
    public static final String LicKeySpec="LicKeySpec";// 10-8-4-14-10-12-12-12-4-7 
    public static final String Mode="Mode";// Commercial 
    public static final String task="task";// NoGraphics-StartDTaskProcessor
    public static final String AppLib="AppLib";// ./
    public static final String AppLibFile="AppLibFile";// MCWAppsList
    
    /**
     * List of modules that needs to be controlled through Licensing
     */
    private static  boolean ACTIVATE_HOME_SCREEN = true;
    private static  boolean ACTIVATE_SSC_SCREEN=true;
    private static  boolean ACTIVATE_MANAGER_SCREEN = false;
    private static  boolean ACTIVATE_IMAGESTAMP_SCREEN = true;
    private static  boolean ACTIVATE_DISTRIBUTED_COMPUTING_NODE = true;
    private static  boolean ACTIVATE_PROCESS_MONITORING = false;
    private static  boolean ACTIVATE_2DTEST_SCREEN = true;

    public static boolean has(String Key){
        return MCWAppVars.has(Key);
    }
    
    public static String getValue(String Key){
         try {
             return MCWAppVars.getString(Key);
         } catch (JSONException ex) {
             Logger.getLogger(MCWConfClass.class.getName()).log(Level.SEVERE, null, ex);
         }
         return "";
    }
    /**
     * Get the value of ACTIVATE_2DTEST_SCREEN
     *
     * @return the value of ACTIVATE_2DTEST_SCREEN
     */
    public  static boolean isACTIVATE_2DTEST_SCREEN() {
        return ACTIVATE_2DTEST_SCREEN;
    }

    /**
     * Set the value of ACTIVATE_2DTEST_SCREEN
     *
     * @param ACTIVATE_2DTEST_SCREEN new value of ACTIVATE_2DTEST_SCREEN
     */
    private void setACTIVATE_2DTEST_SCREEN(boolean ACTIVATE_2DTEST_SCREEN) {
        this.ACTIVATE_2DTEST_SCREEN = ACTIVATE_2DTEST_SCREEN;
    }

    /**
     * Get the value of ACTIVATE_PROCESS_MONITORING
     *
     * @return the value of ACTIVATE_PROCESS_MONITORING
     */
    public  static boolean isACTIVATE_PROCESS_MONITORING() {
        return ACTIVATE_PROCESS_MONITORING;
    }

    /**
     * Set the value of ACTIVATE_PROCESS_MONITORING
     *
     * @param ACTIVATE_PROCESS_MONITORING new value of ACTIVATE_PROCESS_MONITORING
     */
    private void setACTIVATE_PROCESS_MONITORING(boolean ACTIVATE_PROCESS_MONITORING) {
        this.ACTIVATE_PROCESS_MONITORING = ACTIVATE_PROCESS_MONITORING;
    }


    /**
     * Get the value of ACTIVATE_DISTRIBUTED_COMPUTING_NODE
     *
     * @return the value of ACTIVATE_DISTRIBUTED_COMPUTING_NODE
     */
    public  static boolean isACTIVATE_DISTRIBUTED_COMPUTING_NODE() {
        return ACTIVATE_DISTRIBUTED_COMPUTING_NODE;
    }

    /**
     * Set the value of ACTIVATE_DISTRIBUTED_COMPUTING_NODE
     *
     * @param ACTIVATE_DISTRIBUTED_COMPUTING_NODE new value of ACTIVATE_DISTRIBUTED_COMPUTING_NODE
     */
    private void setACTIVATE_DISTRIBUTED_COMPUTING_NODE(boolean ACTIVATE_DISTRIBUTED_COMPUTING_NODE) {
        this.ACTIVATE_DISTRIBUTED_COMPUTING_NODE = ACTIVATE_DISTRIBUTED_COMPUTING_NODE;
    }


    /**
     * Get the value of ACTIVATE_IMAGESTAMP_SCREEN
     *
     * @return the value of ACTIVATE_IMAGESTAMP_SCREEN
     */
    public  static boolean isACTIVATE_IMAGESTAMP_SCREEN() {
        return ACTIVATE_IMAGESTAMP_SCREEN;
    }

    /**
     * Set the value of ACTIVATE_IMAGESTAMP_SCREEN
     *
     * @param ACTIVATE_IMAGESTAMP_SCREEN new value of ACTIVATE_IMAGESTAMP_SCREEN
     */
    private void setACTIVATE_IMAGESTAMP_SCREEN(boolean ACTIVATE_IMAGESTAMP_SCREEN) {
        this.ACTIVATE_IMAGESTAMP_SCREEN = ACTIVATE_IMAGESTAMP_SCREEN;
    }


    /**
     * Get the value of ACTIVATE_MANAGER_SCREEN
     *
     * @return the value of ACTIVATE_MANAGER_SCREEN
     */
    public  static boolean isACTIVATE_MANAGER_SCREEN() {
        return ACTIVATE_MANAGER_SCREEN;
    }

    /**
     * Set the value of ACTIVATE_MANAGER_SCREEN
     *
     * @param ACTIVATE_MANAGER_SCREEN new value of ACTIVATE_MANAGER_SCREEN
     */
    private void setACTIVATE_MANAGER_SCREEN(boolean ACTIVATE_MANAGER_SCREEN) {
        this.ACTIVATE_MANAGER_SCREEN = ACTIVATE_MANAGER_SCREEN;
    }


    /**
     * Get the value of ACTIVATE_SSC_SCREEN
     *
     * @return the value of ACTIVATE_SSC_SCREEN
     */
    public  static boolean isACTIVATE_SSC_SCREEN() {
        return ACTIVATE_SSC_SCREEN;
    }

    /**
     * Set the value of ACTIVATE_SSC_SCREEN
     *
     * @param ACTIVATE_SSC_SCREEN new value of ACTIVATE_SSC_SCREEN
     */
    private void setACTIVATE_SSC_SCREEN(boolean ACTIVATE_SSC_SCREEN) {
        this.ACTIVATE_SSC_SCREEN = ACTIVATE_SSC_SCREEN;
    }


    /**
     * Get the value of ACTIVATE_HOME_SCREEN
     *
     * @return the value of ACTIVATE_HOME_SCREEN
     */
    public  static boolean isACTIVATE_HOME_SCREEN() {
        return ACTIVATE_HOME_SCREEN;
    }

    /**
     * Set the value of ACTIVATE_HOME_SCREEN
     *
     * @param ACTIVATE_HOME_SCREEN new value of ACTIVATE_HOME_SCREEN
     */
    private void setACTIVATE_HOME_SCREEN(boolean ACTIVATE_HOME_SCREEN) {
        this.ACTIVATE_HOME_SCREEN = ACTIVATE_HOME_SCREEN;
    }

    
    public static Font getFont(int Family, int Style, int Size) {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontFamilies = ge.getAvailableFontFamilyNames();
//    fontFamilies[Family]= (String) JOptionPane.showInputDialog( new Frame(),
//                "Font selection:",
//                "Select",
//                JOptionPane.PLAIN_MESSAGE,
//                null,//Was icon
//                fontFamilies,
//                fontFamilies[0]);
        Font font = new Font(fontFamilies[Family], Style, Size);
        return font;
    }


}
