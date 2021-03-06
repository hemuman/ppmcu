/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu2D;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JComponent;

/**
 *
 * @author PDI
 */
public class JComponentThreeD extends JComponent  implements ActionListener ,MouseListener, MouseMotionListener  {
    public Model3D md;
    boolean painted = true;
    float xfac;
    int prevx, prevy;
    float scalefudge = 1;
    Matrix3D amat = new Matrix3D(), tmat = new Matrix3D();
    public String mdname = null;
    String message = null;

   
    public JComponentThreeD() {
   //     mdname = getParameter("model");
//        try {
//            scalefudge = Float.valueOf(getParameter("scale")).floatValue();
//        } catch (Exception ignored) {
//            // fall back to default scalefudge = 1
//        }
        amat.yrot(20);
        amat.xrot(20);
        if (mdname == null) {
            mdname = "net/mk/ppmcu2d/models/Oni_general.obj";
               //   mdname = "net/mk/ppmcu2d/models/dinasaur.obj";
            //mdname = "net/mk/ppmcu2d/models/mcw_extrude.obj";
            // mdname = "net/mk/ppmcu2d/models/cube.obj";
        }
       
        

         //Load();
        
        setSize(getSize().width <= 20 ? 800 :500,
                getSize().height <= 20 ? 800 :500);
       addMouseListener(this);
       addMouseMotionListener(this);
       // run();
    }

   
    public void destroy() {
       removeMouseListener(this);
       removeMouseMotionListener(this);
    }

    public void Load(URL url) {
        InputStream is = null;
        try {
            //Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            is =url.openStream();
            //is = getClass().getClassLoader().getResource(mdname).openStream();
            //Model3D m = new Model3D(is);
            md = new Model3D(is);
            md.findBB();
            md.compress();
            float xw = md.xmax - md.xmin;
            float yw = md.ymax - md.ymin;
            float zw = md.zmax - md.zmin;
            if (yw > xw) {
                xw = yw;
            }
            if (zw > xw) {
                xw = zw;
            }
            float f1 = getSize().width / xw;
            float f2 = getSize().height / xw;
            xfac = 0.7f * (f1 < f2 ? f1 : f2) * scalefudge;
        } catch (Exception e) {
            md = null;
            message = e.toString();
            
        }
        try {
            if (is != null) {
                is.close();
            }
        } catch (Exception e) {
        }
        repaint();
    }
    
    public void Load(String FileData) {
        InputStream is = null;
        try {
            //Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            is = new ByteArrayInputStream(FileData.getBytes());
            //is = getClass().getClassLoader().getResource(mdname).openStream();
            //Model3D m = new Model3D(is);
            md = new Model3D(is);
            md.findBB();
            md.compress();
            float xw = md.xmax - md.xmin;
            float yw = md.ymax - md.ymin;
            float zw = md.zmax - md.zmin;
            if (yw > xw) {
                xw = yw;
            }
            if (zw > xw) {
                xw = zw;
            }
            float f1 = getSize().width / xw;
            float f2 = getSize().height / xw;
            xfac = 0.7f * (f1 < f2 ? f1 : f2) * scalefudge;
        } catch (Exception e) {
            md = null;
            message = e.toString();
            
        }
        try {
            if (is != null) {
                is.close();
            }
        } catch (Exception e) {
        }
        repaint();
    }
    
    
    public void Load() {
        InputStream is = null;
        try {
            //Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            //is = new URL(getDocumentBase(), mdname).openStream();
            is = getClass().getClassLoader().getResource(mdname).openStream();
            //Model3D m = new Model3D(is);
            md = new Model3D(is);
            md.findBB();
            md.compress();
            float xw = md.xmax - md.xmin;
            float yw = md.ymax - md.ymin;
            float zw = md.zmax - md.zmin;
            if (yw > xw) {
                xw = yw;
            }
            if (zw > xw) {
                xw = zw;
            }
            float f1 = getSize().width / xw;
            float f2 = getSize().height / xw;
            xfac = 0.7f * (f1 < f2 ? f1 : f2) * scalefudge;
        } catch (Exception e) {
            md = null;
            message = e.toString();
            
        }
        try {
            if (is != null) {
                is.close();
            }
        } catch (Exception e) {
        }
        repaint();
    }

    public void run() {
        Thread me = Thread.currentThread();

 

        try {
            

            while (true) {
               xtheta++;
               play();
               System.out.println(xtheta);
            }
        } finally {
            synchronized (this) {
               
            }
        }
    }
   
//    public void start() {
//        if (md == null && message == null) {
//            new Thread(this).start();
//        }
//    }

  
    public void stop() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevx = e.getX();
        prevy = e.getY();
        e.consume();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        tmat.unit();
        float xtheta = (prevy - y) * 360.0f / getSize().width;
        float ytheta = (x - prevx) * 360.0f / getSize().height;
        tmat.xrot(xtheta);
        tmat.yrot(ytheta);
        amat.mult(tmat);
        if (painted) {
            painted = false;
            repaint();
        }
        prevx = x;
        prevy = y;
        e.consume();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void paint(Graphics g) {
        if (md != null) {
            md.mat.unit();
            md.mat.translate(-(md.xmin + md.xmax) / 2,
                    -(md.ymin + md.ymax) / 2,
                    -(md.zmin + md.zmax) / 2);
            md.mat.mult(amat);
            md.mat.scale(xfac, -xfac, 16 * xfac / getSize().width);
            md.mat.translate(getSize().width / 2, getSize().height / 2, 8);
            md.transformed = false;
            md.paint(g);
            setPainted();
        } else if (message != null) {
            g.drawString("Error in model:", 3, 20);
            g.drawString(message, 10, 40);
        }
    }

    private synchronized void setPainted() {
        painted = true;
        notifyAll();
    }
 float xtheta;
 float ytheta;
   public void play(){
      

        tmat.unit();
        
        tmat.xrot(xtheta);
        tmat.yrot(ytheta);
        amat.mult(tmat);
        if (painted) {
            painted = false;
            repaint();
        }
    
        
   
   }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
