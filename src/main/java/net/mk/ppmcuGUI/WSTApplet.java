/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcuGUI;


import javax.swing.JApplet;
import javax.swing.SwingUtilities;
import json.JSONObject;
import net.mk.ppmcu.GlobalMCUTest;
        
/**
 *
 * @author PDI
 */
public class WSTApplet extends JApplet {
    //protected String WebServerName="127.0.0.1:8888/multicoreworld";
    protected final String WebServerName="multicoreworld.manojky.net";
    //Called when this applet is loaded into the browser.
    public void init() {
           /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WSTApplet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WSTApplet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WSTApplet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WSTApplet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
       /**
        * Yes gvreeebsep pditestprofile52413 10-8-4-14-10-12-12-12-4-7 Commercial
          Yes AllUsersAllPass AllUsers AllPass Commercial - Mac
        */
        //Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                }
            });
        } catch (Exception e) { 
            System.err.println("createGUI didn't complete successfully");
        }
    }
    
    private void createGUI() {
        //Create and set up the content pane.
        //DynamicTreePanel newContentPane = new DynamicTreePanel();
       // newContentPane.setOpaque(true); 
        setContentPane(new net.mk.ppmcuGUI.FrontPage(WebServerName,WebServerName, new GlobalMCUTest().printMachineInfo(true, true),true));        
    }        
}