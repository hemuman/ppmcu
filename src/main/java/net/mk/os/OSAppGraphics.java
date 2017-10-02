/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.os;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import json.mkJSON;
import static net.mk.os.OSApp.getAvailableCPU;
import static net.mk.os.OSApp.getCPUCount;
import static net.mk.os.OSApp.getCPUSpeed;
import static net.mk.os.OSApp.getMACAddress;
import static net.mk.os.OSApp.getProcessCount;
import static net.mk.os.OSApp.getRAMInfo;
import net.mk.ppmcu2D.UIToolKit;
import net.mk.ppmcuGUI.MCWConfClass;
import net.mk.ppmcuGUI.WorldSpeedTestGUI;

/**
 *
 * @author PDI
 */
public class OSAppGraphics extends OSApp {

    /**
     * Minimum Width, Height =100,70
     *
     * @param Width
     * @param Height
     * @return
     */
    public static BufferedImage getCPUUsageGraphics(int Width, int Height, boolean debug) {
        if (Width < 100) {
            Width = 100;
        } //Check and set Threshold to 100px
        if (Height < 70) {
            Height = 70;
        } //Check and set Threshold to 70px
//        One could use a BufferedImage with an image type that supports transparency such as BufferedImage.TYPE_INT_ARGB:

//BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
//One can draw on the BufferedImage by calling BufferedImage.createGraphics to obtain a Graphics2D object, then perform some drawing:

        BufferedImage img = new BufferedImage(Width, Height, BufferedImage.TYPE_4BYTE_ABGR_PRE);

        Graphics2D g = img.createGraphics();
        g.setColor(Color.GREEN);
        // fill Ellipse2D.Double
        GradientPaint redtowhite = new GradientPaint(0, 0, Color.DARK_GRAY, 0, Height, Color.BLACK);
        g.setPaint(redtowhite);

        Paint oldPaint = g.getPaint();

        LinearGradientPaint p;

        p = new LinearGradientPaint(0.0f, 0.0f, Height / 2, Height,
                new float[]{0.001f, 0.02f, 0.5f, 0.7f, 1.0f},
                new Color[]{new Color(0x909090),
            new Color(0x808080),
            new Color(0x505050),
            new Color(0x101010),
            new Color(0x000000)});
        g.setPaint(p);
        // g.fillRect(0, 0, 100,21); 

        //g.setPaint(oldPaint); 

        g.fill(new RoundRectangle2D.Double(1, 1,
                Width - 2,
                Height - 2,
                15, 15));

       // g.drawImage(UIToolKit.getNewsPrintBG(Width, Height, 1.0f, debug), null, null);
        Font font = new Font("League-Gothic", Font.BOLD, 11);

        DecimalFormat df = new DecimalFormat("#.#");
        double data = getAvailableCPU();
        g.setFont(font);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("CPU : " + df.format(getCPUSpeed()) + " Ghz x" + getCPUCount(), 5, 49);
        g.drawString("Free: " + df.format(100 * data) + "%", 110, 49);
        g.drawString("RAM : " + getRAMInfo(), 5, 63);
        // g.setColor(Color.GREEN);
        //g.drawString(""+df.format(100*data)+"%", 3, 50);
        g.setColor(Color.LIGHT_GRAY);
        g.drawRoundRect(2, 2, Width - 4, Height - 4, 14, 14);
        g.setColor(Color.LIGHT_GRAY);
        g.drawRoundRect(1, 1, Width - 2, Height - 2, 15, 15);



        font = new Font("League-Gothic", Font.PLAIN, 10);
        g.setFont(font);
        g.drawString("Processes: " + getProcessCount(), 5, 75);
        font = new Font("League-Gothic", Font.PLAIN, 9);
        g.setFont(font);
        g.drawString("Ver: " + MCWConfClass.VERSION, 5, 87);

        font = new Font("League-Gothic", Font.PLAIN, 10);
        g.setFont(font);
        //CodeBase

        //getProcessCount()

        g.setColor(Color.LIGHT_GRAY);
        try {
            g.drawString(InetAddress.getLocalHost().getHostName(), 5, 13);
            g.drawString(System.getProperty("os.name"), 5, 24);
            g.drawString(System.getProperty("user.name"), 110, 24);
        } catch (UnknownHostException ex) {
            Logger.getLogger(OSAppGraphics.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.drawString(getMACAddress(debug), 5, 35);


        // ImageIcon icon = new ImageIcon();
        //icon.setImage( getCPUUsageGraphics(10));
        //JOptionPane.showMessageDialog(null, icon);
        //getMACAddress


//Then, since BufferedImage is a subclass of Image that can be used to draw onto another Image using one of the Graphics.drawImage that accepts an Image.
        g.dispose();
        return img;
    }

    public static BufferedImage getCPUUsageGraphicsKhakee(int Width, int Height, boolean debug) {
        if (Width < 100) {
            Width = 100;
        } //Check and set Threshold to 100px
        if (Height < 70) {
            Height = 70;
        } //Check and set Threshold to 70px
//        One could use a BufferedImage with an image type that supports transparency such as BufferedImage.TYPE_INT_ARGB:

//BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
//One can draw on the BufferedImage by calling BufferedImage.createGraphics to obtain a Graphics2D object, then perform some drawing:

        BufferedImage img = new BufferedImage(Width, Height, BufferedImage.TYPE_4BYTE_ABGR_PRE);

        Graphics2D g = img.createGraphics();
        g.setColor(Color.GREEN);
        // fill Ellipse2D.Double
        GradientPaint redtowhite = new GradientPaint(0, 0, Color.DARK_GRAY, 0, Height, Color.BLACK);
        g.setPaint(redtowhite);

        Paint oldPaint = g.getPaint();

        LinearGradientPaint p;

        p = new LinearGradientPaint(0.0f, 0.0f, Height / 2, Height,
                new float[]{0.001f, 0.02f, 0.5f, 0.7f, 1.0f},
                new Color[]{new Color(0x909090),
            new Color(0x808080),
            new Color(0x505050),
            new Color(0x101010),
            new Color(0x000000)});
        g.setPaint(p);
        // g.fillRect(0, 0, 100,21); 

        //g.setPaint(oldPaint); 

        g.fill(new RoundRectangle2D.Double(1, 1,
                Width - 2,
                Height - 2,
                15, 15));

        g.drawImage(UIToolKit.getNewsPrintBG(Width, Height, 1, 1.0f, debug), null, null);
        Font font = new Font("League-Gothic", Font.BOLD, 11);

        DecimalFormat df = new DecimalFormat("#.#");
        double data = getAvailableCPU();
        g.setFont(font);
        g.setColor(new Color(6,112,154));
        g.drawString("CPU : " + df.format(getCPUSpeed()) + " Ghz x" + getCPUCount(), 5, 49);
        g.drawString("Free: " + df.format(100 * data) + "%", 110, 49);
        g.drawString("RAM : " + getRAMInfo(), 5, 63);
       
        font = new Font("League-Gothic", Font.PLAIN, 10);
        g.setFont(font);
        g.drawString("Processes: " + getProcessCount(), 5, 75);
        font = new Font("League-Gothic", Font.PLAIN, 9);
        g.setFont(font);
        g.drawString("Ver: " + MCWConfClass.VERSION, 5, 87);

        font = new Font("League-Gothic", Font.PLAIN, 10);
        g.setFont(font);
        //CodeBase

        //getProcessCount()

        g.setColor(new Color(6,112,154));
        try {
            g.drawString(InetAddress.getLocalHost().getHostName(), 5, 13);
            g.drawString(System.getProperty("os.name"), 5, 24);
            g.drawString(System.getProperty("user.name"), 110, 24);
        } catch (UnknownHostException ex) {
            Logger.getLogger(OSAppGraphics.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.drawString(getMACAddress(debug), 5, 35);
//Then, since BufferedImage is a subclass of Image that can be used to draw onto another Image using one of the Graphics.drawImage that accepts an Image.
        g.dispose();
        return img;
    }
    
    public static BufferedImage getROMDataKhakee(int Width,JSONObject DataToPrint, boolean debug) {
        try {
            JSONArray names = DataToPrint.names();
            mkJSON.sortJSONArray(names);
            int Height = 28 + 12 * names.length();

            if (Width < 100) {
                Width = 100;
            } //Check and set Threshold to 100px
            if (Height < 70) {
                Height = 70;
            } //        One could use a BufferedImage with an image type that supports transparency such as BufferedImage.TYPE_INT_ARGB:
            //        One could use a BufferedImage with an image type that supports transparency such as BufferedImage.TYPE_INT_ARGB:

            //BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            //One can draw on the BufferedImage by calling BufferedImage.createGraphics to obtain a Graphics2D object, then perform some drawing:

            BufferedImage img = new BufferedImage(Width, Height, BufferedImage.TYPE_4BYTE_ABGR_PRE);

            Graphics2D g = img.createGraphics();
            g.setColor(Color.GREEN);
            // fill Ellipse2D.Double
            GradientPaint redtowhite = new GradientPaint(0, 0, Color.DARK_GRAY, 0, Height, Color.BLACK);
            g.setPaint(redtowhite);
            g.drawImage(UIToolKit.getNewsPrintBG(Width, Height, 1, 1.0f, debug), null, null);
            Font font = new Font("League-Gothic", Font.BOLD, 10);
            g.setFont(font);
            g.setColor(new Color(6, 112, 154)); //News Papaer Ink

            for (int i = 0; i < names.length(); i++) {

                g.drawString(names.getString(i) + ":"
                        + "   T: " + DataToPrint.getJSONObject(names.getString(i)).getString("TOTAL_SPACE_GB") + " GB"
                        + "   A: " + DataToPrint.getJSONObject(names.getString(i)).getString("AVAILABLE_GB") + " GB"
                        + "   U: " + DataToPrint.getJSONObject(names.getString(i)).getString("USED_PERCENT")
                        + "   V: " + DataToPrint.getJSONObject(names.getString(i)).getString("TPYE"), 10, Height - 10 - 12 * i);

            }

            //Print Header
            font = new Font("League-Gothic", Font.BOLD, 13);
            g.setFont(font);
            g.setColor(Color.DARK_GRAY);
            g.setColor(new Color(14, 16, 93));
            g.drawString("Storage", 10, Height - 12 - 12 * names.length());
            g.drawLine(0, Height - 10 - 12 * names.length(), 305, Height - 10 - 12 * names.length());
            //Then, since BufferedImage is a subclass of Image that can be used to draw onto another Image using one of the Graphics.drawImage that accepts an Image.
            g.dispose();
            return UIToolKit.getWithShadow(img, 1.0f, debug);
        } catch (JSONException ex) {
            Logger.getLogger(OSAppGraphics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static BufferedImage getJSONDataKhakee(int Width,JSONObject DataToPrint,String HeadingText, boolean debug) {
        try {
            JSONArray names = DataToPrint.names();
            mkJSON.sortJSONArray(names);
            int Height = 28 + 12 * names.length();

            if (Width < 100) {
                Width = 100;
            } //Check and set Threshold to 100px
            if (Height < 70) {
                Height = 70;
            } //        One could use a BufferedImage with an image type that supports transparency such as BufferedImage.TYPE_INT_ARGB:
            //        One could use a BufferedImage with an image type that supports transparency such as BufferedImage.TYPE_INT_ARGB:

            //BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            //One can draw on the BufferedImage by calling BufferedImage.createGraphics to obtain a Graphics2D object, then perform some drawing:

            BufferedImage img = new BufferedImage(Width, Height, BufferedImage.TYPE_4BYTE_ABGR_PRE);

            Graphics2D g = img.createGraphics();
            g.setColor(Color.GREEN);
            // fill Ellipse2D.Double
            GradientPaint redtowhite = new GradientPaint(0, 0, Color.DARK_GRAY, 0, Height, Color.BLACK);
            g.setPaint(redtowhite);
            g.drawImage(UIToolKit.getNewsPrintBG(Width, Height, 1, 1.0f, debug), null, null);
            Font font = new Font("League-Gothic", Font.BOLD, 10);
            g.setFont(font);
            g.setColor(new Color(6, 112, 154)); //News Papaer Ink

            for (int i = 0; i < names.length(); i++) {

                 g.drawString(names.getString(i),10,Height-10-12*i);
                            g.drawString(" : "+DataToPrint.getString(names.getString(i)),80,Height-10-12*i);

            }

            //Print Header
            font = new Font("League-Gothic", Font.BOLD, 13);
            g.setFont(font);
            g.setColor(Color.DARK_GRAY);
            g.setColor(new Color(14, 16, 93));
            g.drawString(HeadingText, 10, Height - 12 - 12 * names.length());
            g.drawLine(0, Height - 10 - 12 * names.length(), Width, Height - 10 - 12 * names.length());
            //Then, since BufferedImage is a subclass of Image that can be used to draw onto another Image using one of the Graphics.drawImage that accepts an Image.
            g.dispose();
            return UIToolKit.getWithShadow(img, 1.0f, debug);
        } catch (JSONException ex) {
            Logger.getLogger(OSAppGraphics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public static BufferedImage getGraphics(int Width, int Height, String[] Message, boolean debug) {
        if (Width < 100) {
            Width = 100;
        } //Check and set Threshold to 100px
        if (Height < 70) {
            Height = 70;
        } //Check and set Threshold to 70px
//        One could use a BufferedImage with an image type that supports transparency such as BufferedImage.TYPE_INT_ARGB:

//BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
//One can draw on the BufferedImage by calling BufferedImage.createGraphics to obtain a Graphics2D object, then perform some drawing:

        BufferedImage img = new BufferedImage(Width, Height, BufferedImage.TYPE_4BYTE_ABGR_PRE);

        Graphics2D g = img.createGraphics();
        g.setColor(Color.GREEN);
        // fill Ellipse2D.Double
        GradientPaint redtowhite = new GradientPaint(0, 0, Color.DARK_GRAY, 0, Height, Color.BLACK);
        g.setPaint(redtowhite);

        Paint oldPaint = g.getPaint();

        LinearGradientPaint p;

        p = new LinearGradientPaint(0.0f, 0.0f, Height / 2, Height,
                new float[]{0.001f, 0.02f, 0.5f, 0.7f, 1.0f},
                new Color[]{new Color(0x909090),
            new Color(0x808080),
            new Color(0x505050),
            new Color(0x101010),
            new Color(0x000000)});
        g.setPaint(p);
        // g.fillRect(0, 0, 100,21); 

        //g.setPaint(oldPaint); 

        g.fill(new RoundRectangle2D.Double(1, 1,
                Width - 2,
                Height - 2,
                15, 15));

        // g.setColor(Color.GREEN);
        //g.drawString(""+df.format(100*data)+"%", 3, 50);
        g.setColor(Color.LIGHT_GRAY);
        g.drawRoundRect(2, 2, Width - 4, Height - 4, 14, 14);
        g.setColor(Color.LIGHT_GRAY);
        g.drawRoundRect(1, 1, Width - 2, Height - 2, 15, 15);


        Font font = new Font("League-Gothic", Font.BOLD, 40);

        DecimalFormat df = new DecimalFormat("#.#");
        double data = getAvailableCPU();
        g.setFont(font);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(Message[0], 5, 50);

        font = new Font("League-Gothic", Font.PLAIN, 11);
        g.setFont(font);
        g.drawString(Message[1], 10, 13);
        g.drawString("Ver: " + MCWConfClass.VERSION, 5, 87);

        // ImageIcon icon = new ImageIcon();
        //icon.setImage( getCPUUsageGraphics(10));
        //JOptionPane.showMessageDialog(null, icon);
        //getMACAddress


//Then, since BufferedImage is a subclass of Image that can be used to draw onto another Image using one of the Graphics.drawImage that accepts an Image.
        g.dispose();
        return img;
    }

    public static BufferedImage getIconGraphics(int Width, int Height, String[] Message,Color ChangeColor, boolean debug) {
        if (Width < 100) {
            Width = 100;
        } //Check and set Threshold to 100px
        if (Height < 70) {
            Height = 70;
        } //Check and set Threshold to 70px

        BufferedImage img = new BufferedImage(Width, Height, BufferedImage.TYPE_4BYTE_ABGR_PRE);

        Graphics2D g = img.createGraphics();
        g.setColor(Color.GREEN);
        // fill Ellipse2D.Double
        GradientPaint redtowhite = new GradientPaint(0, 0, Color.DARK_GRAY, 0, Height, ChangeColor);
        g.setPaint(redtowhite);

        LinearGradientPaint p;

        p = new LinearGradientPaint(0.0f, 0.0f, Height / 2, Height,
                new float[]{0.001f, 0.02f, 0.5f, 0.7f, 1.0f},
                new Color[]{new Color(0x909090),
            new Color(0x808080),
            new Color(0x505050),
            new Color(0x101010),
            ChangeColor});// Color(0x000000)
        g.setPaint(p);
        //Color clrHi = new Color(0, 229, 0);
        //Color clrLo = new Color(0, 0, 0);
        // g.setPaint(new GradientPaint(0, 0, clrHi, 0, 200, ChangeColor));

        Shape rect = new RoundRectangle2D.Double(1, 1, Width - 2, Height - 2, 15, 15);
        //Shape rect=createClipShape(Width,Height);
        g.fill(rect);
//g.drawImage(getWin7StyleRect(Width, Height,1.0f,  debug) , 0, 0, null);

        Font font = new Font("League-Gothic", Font.BOLD, 40);

        DecimalFormat df = new DecimalFormat("#.#");
        double data = getAvailableCPU();
        g.setFont(font);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(Message[0], 5, 50);


        font = new Font("League-Gothic", Font.PLAIN, 11);
        g.setFont(font);
        g.setColor(Color.GREEN);
        g.drawString(Message[1], 10, 13);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Ver: " + MCWConfClass.VERSION, 5, 87);

        // Apply the border glow effect
        //paintBorderGlow(g, 1, Width, Height, rect);
// Apply the border shadow before we paint the rest of the shape
        // paintBorderShadow(g, 5, rect);



//Then, since BufferedImage is a subclass of Image that can be used to draw onto another Image using one of the Graphics.drawImage that accepts an Image.
        //g.dispose();
        return img;
    }
    
    public static BufferedImage getGlowingRect(int Width, int Height,float Transparency, boolean debug) {
        if (Width < 100) {
            Width = 100;
        } //Check and set Threshold to 100px
        if (Height < 70) {
            Height = 70;
        } //Check and set Threshold to 70px

        // Color clrHi = new Color(0, 229, 0);
         Color clrHi = new Color(10,10, 10);
 Color clrLo = new Color(0, 0, 0);
   Color clrGlowInnerHi = new Color(253, 239, 175, 148);
   Color clrGlowInnerLo = new Color(255, 209, 0);
Color clrGlowOuterHi = new Color(253, 239, 175, 124);
   Color clrGlowOuterLo = new Color(255, 179, 0);
    
        BufferedImage img = new BufferedImage(Width, Height, BufferedImage.TYPE_4BYTE_ABGR_PRE);

        Graphics2D g = img.createGraphics();
        g.setColor(Color.GREEN);

      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,Transparency));
        //g.setPaint(new GradientPaint(0, 0, clrHi, 0, 200, clrLo));
        // g.fillRect(0, 0, 100,21); 
g.setPaint(new GradientPaint(0, 0, clrHi, 0, 200, clrLo));
        //g.setPaint(oldPaint); 
        //g.setColor(ChangeColor);
        Shape rect=new RoundRectangle2D.Double(1, 1,  Width - 2,   Height - 2,       15, 15);
        g.fill(rect);
        g.draw(rect);

        // Apply the border glow effect
        //paintBorderGlow(g, 4, Width, Height, rect);
// Apply the border shadow before we paint the rest of the shape
        paintBorderShadow(g, 1, rect);

//Then, since BufferedImage is a subclass of Image that can be used to draw onto another Image using one of the Graphics.drawImage that accepts an Image.
        g.dispose();
        return img;
    }
    
    public static BufferedImage getWin7StyleRect(int Width, int Height,float Transparency, boolean debug) {
//        if (Width < 100) {
//            Width = 100;
//        } //Check and set Threshold to 100px
//        if (Height < 70) {
//            Height = 70;
//        } //Check and set Threshold to 70px

        // Color clrHi = new Color(0, 229, 0);
        Color border = new Color(50, 50,73);
        Color blue = new Color(133, 137, 192);
        Color blue2 = new Color(157, 166, 254);
        Color blue3 = new Color(150, 156, 254);
        Color blue4 = new Color(200, 206, 254);
        Color orange = new Color(255, 229, 63);
        
        BufferedImage img = new BufferedImage(Width, Height, BufferedImage.TYPE_4BYTE_ABGR_PRE);

        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,Transparency));
        GradientPaint gp = new GradientPaint(0, 0, blue2, Height/2, Height,blue, true);
        //Here we are setting our Gradient Paint as the color we want to use
        g.setPaint(gp);
        /* Here we are creating a rectangle and filling it
        * with our GradientPaint, this will serve as the background to our JPanel
        */
        //g.fillRect(0, 0, Width, Height);
        g.fillPolygon(new int[]{0,0,Width/10,Width/5,Width/10}, new int[]{0,Height,Height,Height/2,0}, 5);
        g.fillRoundRect(0, 0, Width, Height, 10, 10);
        gp = new GradientPaint(0, 0, blue4, Height-Height/10, Height,blue3, false);
        g.setPaint(gp);
        g.fillRoundRect(0, Height/2, Width, Height, 0, 10);
        gp = new GradientPaint(0, 0, blue, Height/2, Height,blue2, true);
        g.setPaint(gp);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,Transparency));
        
        g.fillPolygon(new int[]{Width,Width,Width-Width/5,Width-Width/10}, new int[]{0,Height,Height,0}, 4);
        
        g.setColor(border);//orange
        //g.setColor(orange);//orange
        //g.drawRect(0, 0, Width-1, Height-1);
        g.drawRoundRect(0, 0, Width-1, Height-1, 10, 10);
        g.setColor(Color.GREEN);
        return img;
    }
    
    public static BufferedImage getWin7StyleRect2(int Width, int Height,float Transparency, boolean debug) {
//        if (Width < 100) {
//            Width = 100;
//        } //Check and set Threshold to 100px
//        if (Height < 70) {
//            Height = 70;
//        } //Check and set Threshold to 70px

        // Color clrHi = new Color(0, 229, 0);
        Color border = new Color(50, 73,50);
        Color blue = new Color(133, 192, 137);
        Color blue2 = new Color(157, 254, 166);
        Color blue3 = new Color(150, 254, 156);
        Color blue4 = new Color(200, 254, 206);
        Color orange = new Color(255, 63, 229);
        
        BufferedImage img = new BufferedImage(Width, Height, BufferedImage.TYPE_4BYTE_ABGR_PRE);

        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,Transparency));
        GradientPaint gp = new GradientPaint(0, 0, blue2, Height/2, Height,blue, true);
        //Here we are setting our Gradient Paint as the color we want to use
        g.setPaint(gp);
        /* Here we are creating a rectangle and filling it
        * with our GradientPaint, this will serve as the background to our JPanel
        */
        //g.fillRect(0, 0, Width, Height);
        g.fillPolygon(new int[]{0,0,Width/10,Width/5,Width/10}, new int[]{0,Height,Height,Height/2,0}, 5);
        g.fillRoundRect(0, 0, Width, Height, 10, 10);
        gp = new GradientPaint(0, 0, blue4, Height-Height/10, Height,blue3, false);
        g.setPaint(gp);
        g.fillRoundRect(0, Height/2, Width, Height, 0, 10);
        gp = new GradientPaint(0, 0, blue, Height/2, Height,blue2, true);
        g.setPaint(gp);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,Transparency));
        
        g.fillPolygon(new int[]{Width,Width,Width-Width/5,Width-Width/10}, new int[]{0,Height,Height,0}, 4);
        
        g.setColor(border);//orange
        //g.setColor(orange);//orange
        //g.drawRect(0, 0, Width-1, Height-1);
        g.drawRoundRect(0, 0, Width-1, Height-1, 10, 10);
        g.setColor(Color.GREEN);
        return img;
    }
    
     public static BufferedImage getWin7StyleRect3(int Width, int Height,float Transparency, boolean debug) {
//        if (Width < 100) {
//            Width = 100;
//        } //Check and set Threshold to 100px
//        if (Height < 70) {
//            Height = 70;
//        } //Check and set Threshold to 70px

        // Color clrHi = new Color(0, 229, 0);
        Color border = new Color(73,50,50);
        Color blue = new Color(192,133,137);
        Color blue2 = new Color(254,157,166);
        Color blue3 = new Color(254,150,156);
        Color blue4 = new Color(254,200,206);
        Color orange = new Color(63,255,229);
        
        BufferedImage img = new BufferedImage(Width, Height, BufferedImage.TYPE_4BYTE_ABGR_PRE);

        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,Transparency));
        GradientPaint gp = new GradientPaint(0, 0, blue2, Height/2, Height,blue, true);
        //Here we are setting our Gradient Paint as the color we want to use
        g.setPaint(gp);
        /* Here we are creating a rectangle and filling it
        * with our GradientPaint, this will serve as the background to our JPanel
        */
        //g.fillRect(0, 0, Width, Height);
        g.fillPolygon(new int[]{0,0,Width/10,Width/5,Width/10}, new int[]{0,Height,Height,Height/2,0}, 5);
        g.fillRoundRect(0, 0, Width, Height, 10, 10);
        gp = new GradientPaint(0, 0, blue4, Height-Height/10, Height,blue3, false);
        g.setPaint(gp);
        g.fillRoundRect(0, Height/2, Width, Height, 0, 10);
        gp = new GradientPaint(0, 0, blue, Height/2, Height,blue2, true);
        g.setPaint(gp);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,Transparency));
        
        g.fillPolygon(new int[]{Width,Width,Width-Width/5,Width-Width/10}, new int[]{0,Height,Height,0}, 4);
        
        g.setColor(border);//orange
        //g.setColor(orange);//orange
        //g.drawRect(0, 0, Width-1, Height-1);
        g.drawRoundRect(0, 0, Width-1, Height-1, 10, 10);
        g.setColor(Color.GREEN);
        return img;
    }
    
    /**
     * More Advanced effects
     *
     */
    private static final Color clrHi = new Color(255, 229, 63);
    private static final Color clrLo = new Color(255, 105, 0);
    private static final Color clrGlowInnerHi = new Color(253, 239, 175, 148);
    private static final Color clrGlowInnerLo = new Color(255, 209, 0);
    private static final Color clrGlowOuterHi = new Color(253, 239, 175, 124);
    private static final Color clrGlowOuterLo = new Color(255, 179, 0);

    public static Shape createClipShape(int Width, int Height) {
        float border = 10.0f;

        float x1 = border;
        float y1 = border;
        float x2 = Width - border;
        float y2 = Height - border;

        float adj = 3.0f; // helps round out the sharp corners
        float arc = 8.0f;
        float dcx = 0.18f * Width;
        float cx1 = x1 - dcx;
        float cy1 = 0.40f * Height;
        float cx2 = x1 + dcx;
        float cy2 = 0.50f * Height;

        GeneralPath gp = new GeneralPath();
        gp.moveTo(x1 - adj, y1 + adj);
        gp.quadTo(x1, y1, x1 + adj, y1);
        gp.lineTo(x2 - arc, y1);
        gp.quadTo(x2, y1, x2, y1 + arc);
        gp.lineTo(x2, y2 - arc);
        gp.quadTo(x2, y2, x2 - arc, y2);
        gp.lineTo(x1 + adj, y2);
        gp.quadTo(x1, y2, x1, y2 - adj);
        gp.curveTo(cx2, cy2, cx1, cy1, x1 - adj, y1 + adj);
        gp.closePath();
        return gp;
    }
//Shape clipShape = createClipShape();
    public static Color getMixedColor(Color c1, float pct1, Color c2, float pct2) {
        float[] clr1 = c1.getComponents(null);
        float[] clr2 = c2.getComponents(null);
        for (int i = 0; i < clr1.length; i++) {
            clr1[i] = (clr1[i] * pct1) + (clr2[i] * pct2);
        }
        return new Color(clr1[0], clr1[1], clr1[2], clr1[3]);
    }

    public static void paintBorderGlow(Graphics2D g2, int glowWidth, int Width, int Height, Shape clipShape) {
        int gw = glowWidth * 2;
        for (int i = gw; i >= 2; i -= 2) {
            float pct = (float) (gw - i) / (gw - 1);

            Color mixHi = getMixedColor(clrGlowInnerHi, pct,
                    clrGlowOuterHi, 1.0f - pct);
            Color mixLo = getMixedColor(clrGlowInnerLo, pct,
                    clrGlowOuterLo, 1.0f - pct);
            g2.setPaint(new GradientPaint(0.0f, Height * 0.25f, mixHi,
                    0.0f, Height, mixLo));
            //g2.setColor(Color.WHITE);

            // See my "Java 2D Trickery: Soft Clipping" entry for more
            // on why we use SRC_ATOP here
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, pct));
            g2.setStroke(new BasicStroke(i));
            g2.draw(clipShape);
        }
    }

    public static void paintBorderShadow(Graphics2D g2, int shadowWidth, Shape clipShape) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int sw = shadowWidth * 2;
        for (int i = sw; i >= 2; i -= 2) {
            float pct = (float) (sw - i) / (sw - 1);
            g2.setColor(getMixedColor(Color.LIGHT_GRAY, pct,
                    Color.WHITE, 1.0f - pct));
            g2.setStroke(new BasicStroke(i));
            g2.draw(clipShape);
        }
    }

    public static BufferedImage createClipImage(Shape s, Graphics2D g, int Width, int Height) {
        // Create a translucent intermediate image in which we can perform
        // the soft clipping
        GraphicsConfiguration gc = g.getDeviceConfiguration();
        BufferedImage img = gc.createCompatibleImage(Width, Height, Transparency.TRANSLUCENT);
        Graphics2D g2 = img.createGraphics();

        // Clear the image so all pixels have zero alpha
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, Width, Height);

        // Render our clip shape into the image.  Note that we enable
        // antialiasing to achieve the soft clipping effect.  Try
        // commenting out the line that enables antialiasing, and
        // you will see that you end up with the usual hard clipping.
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(s);
        g2.dispose();

        return img;
    }

    public static BufferedImage getInfoWallpaper(BufferedImage BGImage,BufferedImage IconImage, JSONObject SysSpec){
        BufferedImage img = new BufferedImage(BGImage.getWidth(), BGImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE);
         Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
           g2.setPaint(Color.gray);
        int x = 0;
        int y = 0;
        // fill RoundRectangle2D.Double
        GradientPaint redtowhite = new GradientPaint(x, y, Color.LIGHT_GRAY, x, BGImage.getHeight()/2,
            Color.DARK_GRAY);
        g2.setPaint(redtowhite);
        //g2.fill(new RoundRectangle2D.Double(x, y, this.getWidth(), this.getHeight(), 10, 10));
        g2.fill(new Rectangle2D.Double(x, y, BGImage.getWidth(), BGImage.getHeight()));
        
        
        g2.setPaint(Color.GRAY);
        
        Paint p;
        p = new RadialGradientPaint(new Point2D.Double(BGImage.getWidth() / 2.0,
                BGImage.getHeight() / 2.0), BGImage.getWidth() / 2.0f,
                new float[] { 0.0f, 1.0f },
                new Color[] { new Color(6, 76, 160, 127),
                    new Color(0.0f, 0.0f, 0.0f, 0.8f) });
        g2.setPaint(p);
        
        g2.setColor(new Color(242,242,192)); // News Paper Color/ Khakee
        g2.fill(new Rectangle2D.Double(x, y, BGImage.getWidth(), BGImage.getHeight()));
        
        
        if(BGImage!=null) g2.drawImage(BGImage, 0, 0, null);
        
        g2.drawImage(UIToolKit.getParametricCurve(BGImage.getWidth(), BGImage.getHeight(),0.01f, false), null, null);
        Font font = new Font("League-Gothic", Font.ROMAN_BASELINE, 40);
            
            if(IconImage!=null) {
                //BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR_PRE);
                //image.createGraphics().drawImage(IconImage, 0, 0, this);
                //UIToolKit.applyShadow(image);
                g2.drawImage(IconImage, BGImage.getWidth() - IconImage.getWidth()-100, 20, null);}
            else{
                /**
             * Draw round corner rectangle
             */
            g2.setColor(Color.DARK_GRAY);
            g2.fillRoundRect(BGImage.getWidth() - 250 - 2, 200 - 150 - 2, 500, 204, 30, 30);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(BGImage.getWidth() - 250, 200 - 150, 500, 200, 30, 30);
            g2.setColor(Color.DARK_GRAY);
            g2.fillRoundRect(BGImage.getWidth() - 250 + 5, 200 - 150 + 5, 490, 190, 30, 30);

            g2.setFont(MCWConfClass.getFont(5, Font.ROMAN_BASELINE, 40));
            // g.setFont(g.getFont().deriveFont(100f));
            g2.setColor(Color.DARK_GRAY);
            // g.drawString("MCW", image.getWidth()-330, 200);

            //Set Rank
            font = new Font("League-Gothic", Font.BOLD, 100);
            g2.setFont(font);
            // g.setFont(g.getFont().deriveFont(100f));
            g2.setColor(Color.WHITE);
            g2.drawString("MCW", BGImage.getWidth() - 200, 200);
            }

            //g.setFont(getFont(5,Font.BOLD,22));
            font = new Font("League-Gothic", Font.BOLD, 15);
            g2.setFont(font);
            g2.setColor(Color.DARK_GRAY);

            BufferedImage temp=null,temp2=null;
            //Print information
            try {
                JSONObject OS = SysSpec.getJSONObject("OS");
                JSONArray names = OS.names();
                names = OS.names();
                temp=OSAppGraphics.getJSONDataKhakee(185, OS, "Operating System", false);
                g2.drawImage(temp, BGImage.getWidth() - temp.getWidth()-50, BGImage.getHeight() - 2*temp.getHeight() - 10, null);

                font = new Font("League-Gothic", Font.BOLD, 10);
                g2.setFont(font);
                g2.setColor(Color.LIGHT_GRAY);
                g2.setColor(new Color(6, 112, 154)); //News Papaer Ink
                
                //Print Info
                OS = SysSpec.getJSONObject("RAM");
                names = OS.names();
                temp2=OSAppGraphics.getJSONDataKhakee(185, OS, "Processing Hardware", false);
                g2.drawImage(temp2,BGImage.getWidth() - temp2.getWidth()-50, BGImage.getHeight() - temp2.getHeight() - 10, null);

                //  START

                OS = SysSpec.getJSONObject("ROM");
                names = OS.names();
                temp=OSAppGraphics.getROMDataKhakee(500 - 195, OS, false);
                g2.drawImage(temp, BGImage.getWidth() - temp2.getWidth()-temp.getWidth()-60, BGImage.getHeight() - temp.getHeight() - 10, null);

            } catch (JSONException ex) {
                Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            g2.drawString(MCWConfClass.COPYRIGHT+" http://multicoreworld.manojky.net", 10, BGImage.getHeight() - 20);

           // ImageIO.write(image, "png", new File("MCW_Rank.png"));
            //g.dispose();
            
            return img;
    }
    
    public static BufferedImage getMCWDefaultWallpaper(){
      Dimension screenDim=Toolkit.getDefaultToolkit().getScreenSize();
         BufferedImage img = new BufferedImage(screenDim.width, screenDim.height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
         Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
           g2.setPaint(Color.gray);
        int x = 0;
        int y = 0;
        // fill RoundRectangle2D.Double
        GradientPaint redtowhite = new GradientPaint(x, y, Color.LIGHT_GRAY, x, img.getHeight()/2,
            Color.DARK_GRAY);
        g2.setPaint(redtowhite);
        //g2.fill(new RoundRectangle2D.Double(x, y, this.getWidth(), this.getHeight(), 10, 10));
        g2.fill(new Rectangle2D.Double(x, y, img.getWidth(), img.getHeight()));
        
        
        g2.setPaint(Color.GRAY);
        
        Paint p;
        p = new RadialGradientPaint(new Point2D.Double(img.getWidth() / 2.0,
                img.getHeight() / 2.0), img.getWidth() / 2.0f,
                new float[] { 0.0f, 1.0f },
                new Color[] { new Color(6, 76, 160, 127),
                    new Color(0.0f, 0.0f, 0.0f, 0.8f) });
        g2.setPaint(p);
        
        g2.setColor(new Color(242,242,192)); // News Paper Color/ Khakee
        g2.fill(new Rectangle2D.Double(x, y, img.getWidth(), img.getHeight()));

        g2.drawImage(UIToolKit.getParametricCurve(img.getWidth(), img.getHeight(),0.01f, false), null, null);
        
        return img;
    }
    public static void main(String[] args) {

        //Tests
        ImageIcon icon = new ImageIcon();
        icon.setImage(getCPUUsageGraphicsKhakee(180, 100, true));
        JOptionPane.showMessageDialog(null, icon);
        icon.setImage(getWin7StyleRect2(300, 180,0.5f, true));
        //getWin7StyleRect

        JOptionPane.showMessageDialog(null, icon);
        icon.setImage(getWin7StyleRect(300, 180,0.5f, true));
        //getWin7StyleRect

        JOptionPane.showMessageDialog(null, icon);

        icon.setImage(getIconGraphics(300, 200, new String[]{"Offline", "IP: 192.168.1.22"},Color.black, true));
        icon.setImage(getGlowingRect(300, 200,.02f, true));

        //
        JOptionPane.showMessageDialog(null, icon);

        /**
         * Advanced Graphics test
         */
        //Shape clipShape = createClipShape(180, 200);
        Shape clipShape=new RoundRectangle2D.Double(1, 1,
                180 - 2,
                160 - 2,
                15, 15);
        
        // Set the clip shape
        BufferedImage clipImage = createClipImage(clipShape, getCPUUsageGraphics(180, 200, true).createGraphics(), 180, 200);
        Graphics2D g2 = clipImage.createGraphics();
        //getGraphics
        // Fill the shape with a gradient
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.setPaint(new GradientPaint(0, 0, clrHi, 0, 200, clrLo));
        g2.fill(clipShape);

// Apply the border glow effect
        paintBorderGlow(g2, 8, 180, 200, clipShape);
// Apply the border shadow before we paint the rest of the shape
        paintBorderShadow(g2, 6, clipShape);

        g2.dispose();
        icon = new ImageIcon();
        icon.setImage(clipImage);

        JOptionPane.showMessageDialog(null, icon);
//g.drawImage(clipImage, 0, 0, null);
    }
}
