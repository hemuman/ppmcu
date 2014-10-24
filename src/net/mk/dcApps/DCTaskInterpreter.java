/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dcApps;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import json.JSONObject;
import mkfs66o96.ReadWriteTextFile;
import net.mk.DTasks.DCTaskExecuteUDApp;
import net.mk.dc.DistributedTaskPool;
import net.mk.ppmcu.PPMCUApp;

/**
 *
 * @author User
 */
public class DCTaskInterpreter extends PPMCUApp {
   

    JTextArea resultArea; // Component to collect COLLECTED_RESULTS.
    boolean debug; // Its a good practice to use the debug mode for printing data.
    /**
     * This the default constructor that will be used, do not alter parameters.
     * @param LICENSE_KEY
     * @param RemoteSysSpecs 
     */
   
    public DCTaskInterpreter(String WebServerName,String LICENSE_KEY, JSONObject RemoteSysSpec) {
        
        //Initialize global variables
        System.out.println("#DCTaskInterpreter Creating..");
        this.LICENSE_KEY = LICENSE_KEY;
        //this.RemoteSysSpecs = RemoteSysSpecs;
        this.debug=debug;
        
   
        //Create GUI
        JButton go = new JButton("Run App");
        resultArea=new JTextArea();
        resultArea.setColumns(10);
        resultArea.setRows(10);
        go.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                execute();// Call this to execute the distributed task over connected nodes/MPP clients.
            }
        });
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.PAGE_AXIS));
        JPanel panel1 = new JPanel(); 
        panel1.add(go);
        top.add(panel1);
        top.add(resultArea);
        add(top);
        System.out.print("#Done.");
    }

    public boolean execute() {
        boolean result = false;
        try {
            
            //1. Get the list of remote machines available,
            RemoteSysSpecs = DCGUI.getRemoteSysSpecs();
            //ExecuteApp, Create the pool
            
            //2. Initate the DistributedTaskPool,
            DistributedTaskPool dtp = new DistributedTaskPool(LICENSE_KEY, 10, DCGUI.getRemoteSysSpecs());
            
            //3. Initiate the distributed task,
            DCTaskExecuteUDApp dctexUDApp = new DCTaskExecuteUDApp(LICENSE_KEY,
                    TaskName,
                    ReadWriteTextFile.getContents(new File(TaskName + ".java")), null, true);

            //4. Submit the task to DistributedTaskPool initiated in step 2,
            for (int i = 0; i < 10; i++) {
                dtp.Isubmit(5559, dctexUDApp, i);
            }
            
            //5. Execute and wait for the task to be finished,
            boolean finshed = dtp.DTaskQeueProcess();
            
            //6. On successful execution,
            if (finshed) {
                
            //7. Collect the COLLECTED_RESULTS, and add code for visualization,
                for (int i = 0; i < DistributedTaskPool.COLLECTED_RESULTS.length; i++) {
                    System.out.println("Result of Task" + i + " is " + DistributedTaskPool.COLLECTED_RESULTS[i]);

                    resultArea.append(DistributedTaskPool.COLLECTED_RESULTS[i].toString()+"\n");
                    repaint();
                }
                
            } else {
                
                if(debug)
                System.out.println("Looks not all the task were executed!"); //Print error message
                
                resultArea.append("Looks not all the task were executed!"+"\n");//Display error message
                
            }
            
            //8. Set execution status true;
            result = true;
         } catch (SecurityException ex) {
            Logger.getLogger(UD_DCApps.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(UD_DCApps.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UD_DCApps.class.getName()).log(Level.SEVERE, null, ex);
        }


        return result;
    }
    
    public static void main(String args[]){
        
        JOptionPane.showMessageDialog(null, new DCTaskInterpreter("  ","", new JSONObject(){}));
    }
}
