/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dcApps;

/**
 *
 * @author PDI
 */
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import json.JSONObject;
import mkfs66o96.ReadWriteTextFile;
import net.mk.DTasks.DCTaskExecuteUDApp;
import net.mk.dc.DistributedTask;
import net.mk.dc.DistributedTaskPool;

/**
 *
 * @author PDI
 */
public class DCApp1Interpreter extends JPanel {
    String LICENSE_KEY; // License key variable is must.
    public JSONObject[] RemoteSysSpecs;// RemoteSysSpecs variable is must.
    String TaskName="Package.DCApp1"; // Set the name of application.
    JTextArea resultArea; // Component to collect COLLECTED_RESULTS.
    boolean debug; // Its a good practice to use the debug mode for printing data.
    /**
     * This the default constructor that will be used, do not alter parameters.
     * @param LICENSE_KEY
     * @param RemoteSysSpecs 
     */
    public DCApp1Interpreter(String LICENSE_KEY, JSONObject[] RemoteSysSpecs,boolean debug) {
        this.LICENSE_KEY = LICENSE_KEY;
        this.RemoteSysSpecs = RemoteSysSpecs;
        this.debug=debug;
        JButton go = new JButton("Run App");
        resultArea=new JTextArea();
        go.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                execute();// Call this to execute the distributed task over connected nodes/MPP clients.
            }
        });
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.add(go);
        top.add(resultArea);
    }

    public boolean execute() {
        boolean result = false;
        int TaskQty=20;
        try {
            
            //1. Get the list of remote machines available,
            RemoteSysSpecs = DCGUI.getRemoteSysSpecs();

            //2. Initate the DistributedTaskPool,
            DistributedTaskPool dtp = new DistributedTaskPool(LICENSE_KEY, TaskQty, DCGUI.getRemoteSysSpecs());
            
            //3. Get source code to distribute
            // a. Read source
            //String TaskCode=ReadWriteTextFile.getContents(new File(TaskName.replace(".", "/") + ".java"));
            // b. Read Byte Code
            String TaskCode=ReadWriteTextFile.getContents(new File(TaskName.replace(".", "/") + ".class"));
            if(debug)System.out.println(TaskName+" "+TaskCode);
            
            //4. Initiate the distributed task,
            // a. Send Source
            //DCTaskExecuteUDApp dctexUDApp = new DCTaskExecuteUDApp(LICENSE_KEY,TaskName, TaskCode, null, true);
            // b. Send ByteCode
            DCTaskExecuteUDApp dctexUDApp = new DCTaskExecuteUDApp(LICENSE_KEY,TaskName, TaskCode.getBytes(), null, true);

            //Submit a test task.
            
            //5. Submit the task to DistributedTaskPool initiated in step 2,
            for (int i = 0; i < TaskQty; i++) {
                dtp.Isubmit(5559, dctexUDApp, i);
            }
            
            //6. Execute and wait for the task to be finished,
            boolean finshed = dtp.DTaskQeueProcess();
            
            //7. On successful execution,
            if (finshed) {
                
            //8. Collect the COLLECTED_RESULTS, and add code for visualization,
                for (int i = 0; i < DistributedTaskPool.COLLECTED_RESULTS.length; i++) {
                    System.out.println("Result of Task" + i + " is " + DistributedTaskPool.COLLECTED_RESULTS[i]);

                    resultArea.append(DistributedTaskPool.COLLECTED_RESULTS[i].toString()+"\n");
                    repaint();
                }
                
            } else {
                
                if(debug)
                System.out.println("Looks like not all the task were executed!"); //Print error message
                
                resultArea.append("Looks like not all the task were executed!"+"\n");//Display error message
                
            }
            
            //9. Set execution status true;
            result = true;
         } catch (SecurityException ex) {
            Logger.getLogger(DCApp1Interpreter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(DCApp1Interpreter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DCApp1Interpreter.class.getName()).log(Level.SEVERE, null, ex);
        }


        return result;
    }
}