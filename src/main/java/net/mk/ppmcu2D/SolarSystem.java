/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu2D;

/**
 *
 * @author PDI
 */
import java.applet.Applet;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONObject;
import net.mk.DTasks.OSAppAsDCTask;
import net.mk.dc.DistributedTaskService;
import net.mk.ppmcuGUI.FrontPage;

public class SolarSystem extends Applet implements Runnable
{
	static double startTime = getTime();
	int delay;
        public int frame;
        Thread animator;
	public void paint(Graphics g)
	{
             Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setPaint(Color.gray);
            int x = 0;
            int y = 0;
            // fill RoundRectangle2D.Double
            GradientPaint redtowhite = new GradientPaint(x, y, Color.LIGHT_GRAY, x, getHeight() / 2,
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
            
		double time = getTime() - startTime;

		int w = getWidth(); //width of applet frame
		int h = getHeight(); //height of applet frame
               g2.drawImage(getSolSysGraphics(w,h,startTime,getTime(), true), 0, 0, this); //Testing the buffered image function
		
//		g2.setColor(new Color(25,25,50));
//		//g.fillRect(0,0,w,h); //sets background color
//	
//
//		//sun:
//		g2.setColor(Color.yellow);
//		int sunSize = 100;
//		int sunRadius = sunSize / 2;
//		int sunX = w / 2 - sunRadius;
//		int sunY = h / 2 - sunRadius;
//		g2.fillOval(sunX, sunY, sunSize,sunSize);
//		int sunCenterX = sunX + sunRadius; // or just w/2
//		int sunCenterY = sunY + sunRadius; //or just h/2
//
//		//rays could be added later:
//		int raySize = sunSize / 2 + 10;
////		g.drawLine(sunCenterX, sunCenterY, sunCenterX + raySize, sunCenterY + raySize);
////		g.drawLine(sunCenterX, sunCenterY, sunCenterX - raySize - 5, sunCenterY - raySize - 5);
////		g.drawLine(sunCenterX, sunCenterY, sunCenterX + raySize, sunCenterY - raySize - 5);
////		g.drawLine(sunCenterX, sunCenterY, sunCenterX - raySize - 5, sunCenterY + raySize);
////		
////		g.drawLine(sunX, sunY, sunX-20, sunY-20);
////		g.drawLine(sunX + sunRadius, sunY, sunX + sunRadius - 20, sunY - 20);
//		
//
//		//mercury:
//		g2.setColor(Color.gray);
//		double mercX = sunCenterX - sunSize * Math.cos(time /0.24);
//		double mercY = sunCenterY- sunSize * Math.sin(time /0.24);
//		//double mercX2 = sunX - sunSize * Math.sin(3 * time);
//      		//double mercY2 = sunY + sunSize * Math.cos(3 * time);
//		int mercSize = 6;
//		int mercRadius = mercSize / 2;
//		//g.drawLine((int)mercX, (int)mercY, (int)mercX2, (int)mercY2);
//		g2.fillOval((int)mercX, (int)mercY, mercSize, mercSize);
//		//g.setColor(Color.white);
//		//g.drawOval(sunCenterX - sunSize, sunCenterY - sunSize, 2 * sunSize, 2 * sunSize);
//
//
//		//venus:
//		g2.setColor(new Color(150,90,30));
//		double venX = sunCenterX - 1.5 * sunSize * Math.cos(time / 0.62);
//		double venY = sunCenterY- 1.5 * sunSize * Math.sin(time / 0.62);
//		int venSize = 15;
//		g2.fillOval((int)venX, (int)venY, venSize, venSize);
//		//this is not correct (an attempt to draw the orbit):
//		//g.setColor(Color.white);
//		//g.drawOval(2 * sunCenterX - sunSize, 2 * sunCenterY - sunSize, 3 * sunSize, 3 * sunSize);
//		
//		//earth:
//		g2.setColor(new Color(25,25,250));
//		double earthX = sunCenterX - 2 * sunSize * Math.cos(time);
//		double earthY = sunCenterY- 2 * sunSize * Math.sin(time);
//		int earthSize = 16;
//		g2.fillOval((int)earthX, (int)earthY, earthSize, earthSize);
//		g2.setColor(new Color(30,80,20));
//		g2.fillOval((int)earthX + 3, (int)earthY+3, earthSize / 3, earthSize/3);
//		g2.fillOval((int)earthX + 7, (int)earthY+7, earthSize / 3, earthSize/3);
//
//		//mars
//		g2.setColor(new Color(180,30,20));
//		double marsX = sunCenterX - 2.5 * sunSize * Math.cos(time /1.88);
//		double marsY = sunCenterY - 2.5 * sunSize * Math.sin(time /1.88);
//		int marsSize = 8;
//		g2.fillOval((int)marsX, (int)marsY, marsSize, marsSize);		
//
//		//jupiter
//		g2.setColor(new Color(135,60,0));
//		double jupX = sunCenterX - 2.9 * sunSize * Math.cos(time /(11.87));
//		double jupY = sunCenterY - 2.9 * sunSize * Math.sin(time /(11.87));
//		int jupSize = 66; //3/8 of original
//		g2.fillOval((int)jupX, (int)jupY, jupSize, jupSize);		
//
//		//saturn
//		g2.setColor(new Color(200,160,130));
//		double satX = sunCenterX - 3.3 * sunSize * Math.cos(time /(29.48));
//		double satY = sunCenterY - 3.3 * sunSize * Math.sin(time /(29.48));
//		int satSize = 56;
//		g2.drawOval((int)(satX - 20), (int)satY + 10, (int)(satSize * 1.7), satSize / 2);
//		g2.drawOval((int)(satX - 26), (int)satY + 10, (int)(satSize * 2.0), satSize / 2);
//		g2.drawOval((int)(satX - 32), (int)satY + 10, (int)(satSize * 2.3), satSize / 2);
//		g2.drawOval((int)(satX - 38), (int)satY + 10, (int)(satSize * 2.6), satSize / 2);
//		g2.fillOval((int)satX, (int)satY, satSize, satSize);
//		
//	
//		//uranus
//		g2.setColor(new Color(180,203,254));
//		double uranX = sunCenterX - 3.7 * sunSize * Math.cos(time /(84.07));
//		double uranY = sunCenterY - 3.7 * sunSize * Math.sin(time /(84.07));
//		int uranSize = 24;
//		g2.fillOval((int)uranX, (int)uranY, uranSize, uranSize);
//		
//
//		//neptune
//		g2.setColor(new Color(89,121,248));
//		double nepX = sunCenterX - 4.1 * sunSize * Math.cos(time /(164.9));
//		double nepY = sunCenterY - 4.1 * sunSize * Math.sin(time /(164.9));
//		int nepSize = 23;
//		g2.fillOval((int)nepX, (int)nepY, nepSize, nepSize);
//		
//
//		//pluto
//		g2.setColor(Color.lightGray);
//		double plutX = sunCenterX - 4.5 * sunSize * Math.cos(time /247.85);
//		double plutY = sunCenterY - 4.5 * sunSize * Math.sin(time /247.85);
//		int plutSize = 3;
//		g2.fillOval((int)plutX, (int)plutY, plutSize, plutSize);

	}//render

	static double getTime() //converts time elapsed since 1/1/70 from milliseconds to seconds
	{
      		return System.currentTimeMillis() / 1000.0;
   	}//getTime

        public void init() {
	String str = getParameter("fps");
	int fps = (str != null) ? Integer.parseInt(str) : 10;
	delay = (fps > 0) ? (1000 / fps) : 100;
    }
 
        public void start() {
	
            animator = new Thread(this);
	    animator.start();
    }
            
   public void run() {
	// Remember the starting time
	long tm = System.currentTimeMillis();
	while (Thread.currentThread() == animator) {
	    

	    // Delay depending on how far we are behind.
//	    try {
//		//tm += delay;
//		Thread.sleep(1000/100);
//	    } catch (InterruptedException e) {
//		break;
//	    }
            // Display the next frame of animation.
	    repaint();

	    // Advance the frame
	    frame++;
	}
    }

   public BufferedImage getSolSysGraphics(int Width, int Height,double startTime,double endTime, boolean debug){
      
        if (Width < 100) {
            Width = 100;
        } //Check and set Threshold to 100px
        if (Height < 70) {
            Height = 70;
        } //Check and set Threshold to 70px
        
        BufferedImage img = new BufferedImage(Width, Height, BufferedImage.TYPE_4BYTE_ABGR_PRE);

        Graphics2D g2 = img.createGraphics();
        
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setPaint(Color.gray);
            int x = 0;
            int y = 0;
            // fill RoundRectangle2D.Double
            GradientPaint redtowhite = new GradientPaint(x, y, Color.LIGHT_GRAY, x, getHeight() / 2,
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
            
		double time = endTime- startTime;

		int w = getWidth(); //width of applet frame
		int h = getHeight(); //height of applet frame
		
		g2.setColor(new Color(25,25,50));
		//g.fillRect(0,0,w,h); //sets background color
	

		//sun:
		g2.setColor(Color.yellow);
		int sunSize = 100;
		int sunRadius = sunSize / 2;
		int sunX = w / 2 - sunRadius;
		int sunY = h / 2 - sunRadius;
		g2.fillOval(sunX, sunY, sunSize,sunSize);
		int sunCenterX = sunX + sunRadius; // or just w/2
		int sunCenterY = sunY + sunRadius; //or just h/2

		//rays could be added later:
		int raySize = sunSize / 2 + 10;
//		g.drawLine(sunCenterX, sunCenterY, sunCenterX + raySize, sunCenterY + raySize);
//		g.drawLine(sunCenterX, sunCenterY, sunCenterX - raySize - 5, sunCenterY - raySize - 5);
//		g.drawLine(sunCenterX, sunCenterY, sunCenterX + raySize, sunCenterY - raySize - 5);
//		g.drawLine(sunCenterX, sunCenterY, sunCenterX - raySize - 5, sunCenterY + raySize);
//		
//		g.drawLine(sunX, sunY, sunX-20, sunY-20);
//		g.drawLine(sunX + sunRadius, sunY, sunX + sunRadius - 20, sunY - 20);
		

		//mercury:
		g2.setColor(Color.gray);
		double mercX = sunCenterX - sunSize * Math.cos(time /0.24);
		double mercY = sunCenterY- sunSize * Math.sin(time /0.24);
		//double mercX2 = sunX - sunSize * Math.sin(3 * time);
      		//double mercY2 = sunY + sunSize * Math.cos(3 * time);
		int mercSize = 6;
		int mercRadius = mercSize / 2;
		//g.drawLine((int)mercX, (int)mercY, (int)mercX2, (int)mercY2);
		g2.fillOval((int)mercX, (int)mercY, mercSize, mercSize);
		//g.setColor(Color.white);
		//g.drawOval(sunCenterX - sunSize, sunCenterY - sunSize, 2 * sunSize, 2 * sunSize);


		//venus:
		g2.setColor(new Color(150,90,30));
		double venX = sunCenterX - 1.5 * sunSize * Math.cos(time / 0.62);
		double venY = sunCenterY- 1.5 * sunSize * Math.sin(time / 0.62);
		int venSize = 15;
		g2.fillOval((int)venX, (int)venY, venSize, venSize);
		//this is not correct (an attempt to draw the orbit):
		//g.setColor(Color.white);
		//g.drawOval(2 * sunCenterX - sunSize, 2 * sunCenterY - sunSize, 3 * sunSize, 3 * sunSize);
		
		//earth:
		g2.setColor(new Color(25,25,250));
		double earthX = sunCenterX - 2 * sunSize * Math.cos(time);
		double earthY = sunCenterY- 2 * sunSize * Math.sin(time);
		int earthSize = 16;
		g2.fillOval((int)earthX, (int)earthY, earthSize, earthSize);
		g2.setColor(new Color(30,80,20));
		g2.fillOval((int)earthX + 3, (int)earthY+3, earthSize / 3, earthSize/3);
		g2.fillOval((int)earthX + 7, (int)earthY+7, earthSize / 3, earthSize/3);

		//mars
		g2.setColor(new Color(180,30,20));
		double marsX = sunCenterX - 2.5 * sunSize * Math.cos(time /1.88);
		double marsY = sunCenterY - 2.5 * sunSize * Math.sin(time /1.88);
		int marsSize = 8;
		g2.fillOval((int)marsX, (int)marsY, marsSize, marsSize);		

		//jupiter
		g2.setColor(new Color(135,60,0));
		double jupX = sunCenterX - 2.9 * sunSize * Math.cos(time /(11.87));
		double jupY = sunCenterY - 2.9 * sunSize * Math.sin(time /(11.87));
		int jupSize = 66; //3/8 of original
		g2.fillOval((int)jupX, (int)jupY, jupSize, jupSize);		

		//saturn
		g2.setColor(new Color(200,160,130));
		double satX = sunCenterX - 3.3 * sunSize * Math.cos(time /(29.48));
		double satY = sunCenterY - 3.3 * sunSize * Math.sin(time /(29.48));
		int satSize = 56;
		g2.drawOval((int)(satX - 20), (int)satY + 10, (int)(satSize * 1.7), satSize / 2);
		g2.drawOval((int)(satX - 26), (int)satY + 10, (int)(satSize * 2.0), satSize / 2);
		g2.drawOval((int)(satX - 32), (int)satY + 10, (int)(satSize * 2.3), satSize / 2);
		g2.drawOval((int)(satX - 38), (int)satY + 10, (int)(satSize * 2.6), satSize / 2);
		g2.fillOval((int)satX, (int)satY, satSize, satSize);
		
	
		//uranus
		g2.setColor(new Color(180,203,254));
		double uranX = sunCenterX - 3.7 * sunSize * Math.cos(time /(84.07));
		double uranY = sunCenterY - 3.7 * sunSize * Math.sin(time /(84.07));
		int uranSize = 24;
		g2.fillOval((int)uranX, (int)uranY, uranSize, uranSize);
		

		//neptune
		g2.setColor(new Color(89,121,248));
		double nepX = sunCenterX - 4.1 * sunSize * Math.cos(time /(164.9));
		double nepY = sunCenterY - 4.1 * sunSize * Math.sin(time /(164.9));
		int nepSize = 23;
		g2.fillOval((int)nepX, (int)nepY, nepSize, nepSize);
		

		//pluto
		g2.setColor(Color.lightGray);
		double plutX = sunCenterX - 4.5 * sunSize * Math.cos(time /247.85);
		double plutY = sunCenterY - 4.5 * sunSize * Math.sin(time /247.85);
		int plutSize = 3;
		g2.fillOval((int)plutX, (int)plutY, plutSize, plutSize);
                
                return img;
}
}//MilkyWay
