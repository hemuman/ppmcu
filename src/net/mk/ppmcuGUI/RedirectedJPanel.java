/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcuGUI;

/**
 *
 * @author PDI
 */
import java.awt.*;
import javax.swing.*;
import java.io.*;
import json.JSONObject;


/**
 * http://tanksoftware.com/juk/developer/src/com/
 *     tanksoftware/util/RedirectedFrame.java
 * A Java Swing class that captures output to the command line 
 ** (eg, System.out.println)
 * RedirectedJPanel
 * <p>
 * This class was downloaded from:
 * Java CodeGuru (http://codeguru.earthweb.com/java/articles/382.shtml) <br>
 * The origional author was Real Gagnon (real.gagnon@tactika.com);
 * William Denniss has edited the code, improving its customizability
 *
 * In breif, this class captures all output to the system and prints it in
 * a frame. You can choose weither or not you want to catch errors, log 
 * them to a file and more.
 * For more details, read the constructor method description
 */

public class RedirectedJPanel extends JPanel {

    // Class information
    public static final String PROGRAM_NAME = "Redirect Frame";
    public static final String VERSION_NUMBER = "1.2";
    public static final String DATE_UPDATED = "13 April 2001";
    public static final String AUTHOR = 
       "Real Gagnon - edited by William Denniss-Implemented by Manoj Kumar";


    private boolean catchErrors;
    private boolean logFile;
    private String fileName;
    private int width;
    private int height;
    private int closeOperation;

    public RedirectedJPanel(String WebServer, String License, JSONObject SysSpec){
       // RedirectedJPanel redirectedJPanel = new RedirectedJPanel(true, true, "log.txt", 500, 200, JFrame.DO_NOTHING_ON_CLOSE);
        
        this.catchErrors = true;
        this.logFile = true;
        this.fileName = "log.txt";
        this.width = 500;
        this.height = 200;
        this.closeOperation = JFrame.DO_NOTHING_ON_CLOSE;

        aTextArea.setBackground(Color.BLACK);
        aTextArea.setForeground(Color.GREEN);
        //Container c = getContentPane();

       // setTitle("Output Frame");
        setSize(width,height);
       setLayout(new BorderLayout());
        add("Center" , aTextArea);
        displayLog();

        this.logFile = true;

        System.setOut(aPrintStream); // catches System.out messages
        if (catchErrors)
            System.setErr(aPrintStream); // catches error messages

        // set the default closing operation to the one given
        //setDefaultCloseOperation(closeOperation);

        Toolkit tk = Toolkit.getDefaultToolkit();
        //Image im = tk.getImage("myicon.gif");
        repaint();
    }

    TextArea aTextArea = new TextArea();
    PrintStream aPrintStream  =
       new PrintStream(
         new FilteredStream(
           new ByteArrayOutputStream()));

    /** Creates a new RedirectFrame.
     *  From the moment it is created,
     *  all System.out messages and error messages (if requested)
     *  are diverted to this frame and appended to the log file 
     *  (if requested)
     *
     * for example:
     *  RedirectedJPanel outputFrame =
     *       new RedirectedJPanel
                (false, false, null, 700, 600, JFrame.DO_NOTHING_ON_CLOSE);
     * this will create a new RedirectedJPanel that doesn't catch errors,
     * nor logs to the file, with the dimentions 700x600 and it doesn't 
     * close this frame can be toggled to visible, hidden by a controlling 
     * class by(using the example) outputFrame.setVisible(true|false)
     *  @param catchErrors set this to true if you want the errors to 
     *         also be caught
     *  @param logFile set this to true if you want the output logged
     *  @param fileName the name of the file it is to be logged to
     *  @param width the width of the frame
     *  @param height the height of the frame
     *  @param closeOperation the default close operation
     *        (this must be one of the WindowConstants)
     */
    public RedirectedJPanel(boolean catchErrors, boolean logFile, String fileName, int width,
         int height, int closeOperation) {

        this.catchErrors = catchErrors;
        this.logFile = logFile;
        this.fileName = fileName;
        this.width = width;
        this.height = height;
        this.closeOperation = closeOperation;

        aTextArea.setBackground(Color.BLACK);
        aTextArea.setForeground(Color.GREEN);
        //Container c = getContentPane();

       // setTitle("Output Frame");
        setSize(width,height);
       setLayout(new BorderLayout());
        add("Center" , aTextArea);
        displayLog();

        this.logFile = logFile;

        System.setOut(aPrintStream); // catches System.out messages
        if (catchErrors)
            System.setErr(aPrintStream); // catches error messages

        // set the default closing operation to the one given
        //setDefaultCloseOperation(closeOperation);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Image im = tk.getImage("myicon.gif");
        //setIconImage(im);
    }



    class FilteredStream extends FilterOutputStream {
        public FilteredStream(OutputStream aStream) {
            super(aStream);
          }

        public void write(byte b[]) throws IOException {
            String aString = new String(b);
            if(aTextArea.getText().length()>5000){
                aTextArea.setText(aTextArea.getText().substring(100));
            }
            aTextArea.append(aString);
        }

        public void write(byte b[], int off, int len) throws IOException {
            String aString = new String(b , off , len);
            if(aTextArea.getText().length()>5000){
                aTextArea.setText(aTextArea.getText().substring(100));
            }
            aTextArea.append(aString);
            
            if (logFile) {
                FileWriter aWriter = new FileWriter(fileName, true);
                aWriter.write(aString);
                aWriter.close();
            }
        }
    }

    private void displayLog() {
        Dimension dim = getToolkit().getScreenSize();
        Rectangle abounds = getBounds();
        Dimension dd = getSize();
        setLocation((dim.width - abounds.width) / 2,
                    (dim.height - abounds.height) / 2);
        setVisible(true);
        requestFocus();
    }

}