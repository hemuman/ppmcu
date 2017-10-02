/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu2D;

/**
 *
 * @author Manoj Kumar
 */
import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import net.mk.os.OSApp;
import net.mk.ppmcuGUI.MCWConfClass;
//import common.ByteArrayConversion;

public class ScreenServer {

    private static final int port = MCWConfClass.ScreenServerPort;
    private static final String ONESHOTNAME = "oneshotimage.jpg";
    private static byte[] byteImage;

    /**
     * @param args
     * @param ONESHOTNAME
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        ServerSocket server = null;

        try {
            server = new ServerSocket(port);
            System.out.println("Server socket ready on port: " + port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(-1);
        }

        Socket socket = server.accept();

        /*
         * The following gets the image screen
         */
        while (true) {
            //URL url = new URL("http://camera3:8080/" + ONESHOTNAME);				
            //BufferedImage bufferedImage = ImageIO.read(url);		
            byteImage = ScreenCapture.getScreenStreamAsByte(1);//ByteArrayConversion.toByteArray(bufferedImage);	

            System.out.println(byteImage.toString());

            OutputStream os = socket.getOutputStream();

            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(byteImage);
        }
    }
    public static boolean KEEP_ALIVE = true;

    public static void run(String LicenseKey, int port, double Scale, boolean AllowMouseControl) throws IOException, AWTException {

        ServerSocket server = null;
        ServerSocket serverMouse = null;

        try {
            server = new ServerSocket(port);
            serverMouse = new ServerSocket(port + 1);
            System.out.println("Server socket ready on port: " + port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(-1);
        }

        Socket socket = server.accept();
        Socket mouseSocekt = serverMouse.accept();
        String MACAddress = OSApp.getMACAddress(true);

        OutputStream os = socket.getOutputStream();
        //OutputStream osmouse = mouseSocekt.getOutputStream();

        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(MACAddress.getBytes());
        System.out.println("#ScreenServer MACAddress:: " + MACAddress.getBytes());

        /*
         * The following gets the image screen
         */
        while (KEEP_ALIVE) {
            //URL url = new URL("http://camera3:8080/" + ONESHOTNAME);				
            //BufferedImage bufferedImage = ImageIO.read(url);		

            //No Compression
            //byteImage = ScreenCapture.getScreenStreamAsByte(Scale);//ByteArrayConversion.toByteArray(bufferedImage);	

            //Compressed one: 1/15/2014
            byteImage = ScreenCapture.getCompressedScreenStreamAsByte(Scale);

            //System.out.println(byteImage.toString());

            os = socket.getOutputStream();
            //OutputStream osmouse = mouseSocekt.getOutputStream();

            oos = new ObjectOutputStream(os);
            oos.writeObject(byteImage);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            int[] mousePoint = new int[]{10, 10};
            try {
                mousePoint = (int[]) ois.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ScreenServer.class.getName()).log(Level.SEVERE, null, ex);
            }


            //System.out.print(mousePoint[0] + "jjjjjjjjj");
            // System.out.print(mousePoint[1]);
            Robot r = new Robot();
            if (AllowMouseControl) {
                if (mousePoint[4] == 1) {
                    r.mouseMove((int) ((double) mousePoint[0] / Scale), (int) ((double) mousePoint[1] / Scale));
                    for (int i = 0; i < mousePoint[2]; i++) {

                        int action = MouseEvent.BUTTON1_DOWN_MASK; //Default action
                        if (mousePoint[3] == MouseEvent.BUTTON1) {
                            action = MouseEvent.BUTTON1_DOWN_MASK;
                        }
                        if (mousePoint[3] == MouseEvent.BUTTON2) {
                            action = MouseEvent.BUTTON2_DOWN_MASK;
                        }
                        if (mousePoint[3] == MouseEvent.BUTTON3) {
                            action = MouseEvent.BUTTON3_DOWN_MASK;
                        }

                        r.mousePress(action);
                        r.mouseRelease(action);
                    }
                }
            }
            if (mousePoint[5] == 0) {
                break;
            }

        }
    }

    /**
     * Moving mouse cursor
     */
    void moveCursor() {

        try {
            // These coordinates are screen coordinates
            int xCoord = 500;
            int yCoord = 500;

            // Move the cursor
            Robot robot = new Robot();
            robot.mouseMove(xCoord, yCoord);
            //robot.mousePress(yCoord);
            //robot.mouseRelease(yCoord);
            //robot.mouseWheel(port);
            //robot.keyPress(yCoord);
            //robot.keyRelease(yCoord);

        } catch (AWTException e) {
        }
    }
}
