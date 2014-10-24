/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu2D;

/**
 *
 * @author PDI
 */

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.imageio.ImageIO;
 
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
 
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;
 
//import common.ByteArrayConversion;
 
public class ScreenViewer extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanelBack;
	private JLabel jLabelCamera3;
 
	private static Socket socket;
	private static String host = "127.0.0.1";
 
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ScreenViewer inst = new ScreenViewer();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
 
	public ScreenViewer() {
		super();
		initGUI();
	}
 
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanelBack = new JPanel();
				getContentPane().add(jPanelBack, BorderLayout.CENTER);
				jPanelBack.setLayout(null);
				{
					jLabelCamera3 = new JLabel();
					jPanelBack.add(jLabelCamera3);
					jLabelCamera3.setBounds(0, 0, 700, 600);
				}
			}
			pack();
 
			this.setSize(856, 494);
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		Thread cgiT = new Thread(new GetCameraImage(), "Camera 3");
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
 
					ScaleJPG scale = new ScaleJPG();
 
					try {
						scale.scale("camera3", bufferedImage, 640, 480);
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
			BufferedImage img = new BufferedImage(320, 240, BufferedImage.TYPE_INT_RGB);
 
			Graphics2D g = img.createGraphics();			
 
			AffineTransform trans = AffineTransform.getScaleInstance((double)320/bufferedImage.getWidth(), 
					(double)240/bufferedImage.getHeight());
 
			g.drawRenderedImage(bufferedImage, trans);
 
			ImageIcon icon = new ImageIcon(img);
 
			jLabelCamera3.setIcon(icon);
		}
	}
 
}