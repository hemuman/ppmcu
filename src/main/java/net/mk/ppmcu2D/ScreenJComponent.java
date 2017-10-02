/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu2D;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import json.mkJSON;
import net.mk.ppmcuGUI.FrontPage;

/**
 *
 * @author PDI
 */
public class ScreenJComponent extends JComponent{
    private static final long serialVersionUID = 1L;
	private JPanel jPanelBack;
	private JLabel jLabelCamera3;
        BufferedImage img;
 
	private static Socket socket;
	private static String host = "127.0.0.1";
        
        
	public ScreenJComponent() {
		super();
		initGUI();
	}
 
	private void initGUI() {
		Thread cgiT = new Thread(new ScreenJComponent.GetCameraImage(), "Camera 3");
		cgiT.start();
	}
 
	protected class GetCameraImage implements Runnable {
 
		@Override
		public void run() {
			try {
				socket = new Socket(host, 6666);
				System.out.println("Connection to host established.");
			} catch (UnknownHostException e) {
				System.err.println("Don't know about host: " + host);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
			try {
				int count = 1;
				while (count != 0) {
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					byte[] byteImage = (byte[])ois.readObject();
                                        
                                        // convert byte array back to BufferedImage
			InputStream in = new ByteArrayInputStream(byteImage);
			//BufferedImage bImageFromConvert = ImageIO.read(in);
                        
					BufferedImage bufferedImage = ImageIO.read(in);//= ByteArrayConversion.fromByteArray(byteImage);
//					System.out.println(bufferedImage.toString());					
 
					ScreenJComponent.ScaleJPG scale = new ScreenJComponent.ScaleJPG();
 
					try {
						scale.scale("camera3", bufferedImage, 640, 480);
                                                repaint();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} count++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
 
	}
 
	/**
	 * ScaleJPG class will be used to scale the camera image which comes in as a 640X480 pixel 
	 * image to half the size on the client. As a camera image is received from the server
	 * the image is passed into this class to be scaled.  First a new graphics 2d graphic is created 
	 * to draw into the buffered image.  Then AffineTransform performs a linear mapping of the image
	 * to preserve the straightness and parallelness of the lines.  It will then set the icon of the
	 * specific camera based on the cameraName.
	 * 
	 */
	public class ScaleJPG {
 
		public void  scale(String cameraName, BufferedImage bufferedImage, int width, int height) throws IOException {
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
 
			Graphics2D g = img.createGraphics();			
 
			AffineTransform trans = AffineTransform.getScaleInstance((double)width/bufferedImage.getWidth(), 
					(double)height/bufferedImage.getHeight());
 
			g.drawRenderedImage(bufferedImage, trans);
 
                        
			//ImageIcon icon = new ImageIcon(img);
 
			//jLabelCamera3.setIcon(icon);
		}
	}
        

        protected void paintComponent( Graphics g ) 
{
   
       

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(Color.gray);
        int x = 0;
        int y = 0;
        // fill RoundRectangle2D.Double
        GradientPaint redtowhite = new GradientPaint(x, y, Color.LIGHT_GRAY, x,getHeight() / 2,
                Color.DARK_GRAY);
        g2.setPaint(redtowhite);
        //g2.fill(new RoundRectangle2D.Double(x, y, this.getWidth(), this.getHeight(), 10, 10));
        g2.fill(new Rectangle2D.Double(x, y, this.getWidth(), this.getHeight()));

        g2.setPaint(Color.GRAY);

        Paint p;
        p = new RadialGradientPaint(new Point2D.Double(getWidth() / 2.0,
                getHeight() / 2.0), getWidth() / 2.0f,
                new float[]{0.0f, 1.0f},
                new Color[]{new Color(6, 76, 160, 127),
            new Color(0.0f, 0.0f, 0.0f, 0.8f)});
        g2.setPaint(p);
        g2.fill(new Rectangle2D.Double(x, y, this.getWidth(), this.getHeight()));


        Font font = new Font("League-Gothic", Font.BOLD, 40);
       
        //Create Core
        int core = Runtime.getRuntime().availableProcessors();
        int Size = 15;
        int StartX = 12;//this.getWidth()-core*(Size+2);
        int StartY = 5;
        g2.setPaint(Color.DARK_GRAY);
        g2.fill(new Rectangle2D.Double(StartX - 10, StartY, core * (Size + 2) + 5, Size + 5));
        g2.setPaint(Color.WHITE);
        g2.fill(new Rectangle2D.Double(StartX - 9, StartY + 1, core * (Size + 2) + 3, Size + 3));

        g2.setPaint(Color.DARK_GRAY);

        for (int i = 0; i < core; i++) {
            g2.setPaint(Color.DARK_GRAY);
            int thisStart = StartX + (Size + 2) * i - 7;
            g2.fill(new Rectangle2D.Double(thisStart, StartY + 3, Size, Size));
            font = new Font("League-Gothic", Font.ROMAN_BASELINE, 12);
            g.setFont(font);
            g2.setPaint(Color.GREEN);
            g2.drawString("" + (i + 1), thisStart + 3, StartY + 15);
        }

        g2.drawImage(img, 0, 0, this);

            //SysInfo-End
        }

}
 
    

