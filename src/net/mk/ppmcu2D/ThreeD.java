package net.mk.ppmcu2D;

/*
 * Copyright (c) 1995, 2011, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * This source code is provided to illustrate the usage of a given feature
 * or technique and has been deliberately simplified. Additional steps
 * required for a production-quality application, such as security checks,
 * input validation and proper error handling, might not be present in
 * this sample code.
 */



import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import net.mk.FJTasks.PrimeNumbersFJTask;


/* A set of classes to parse, represent and display 3D wireframe models
represented in Wavefront .obj format. */
@SuppressWarnings("serial")
class FileFormatException extends Exception {

    public FileFormatException(String s) {
        super(s);
    }
}


///** The representation of a 3D model */
//final class Model3D {
//
//   
//}


/** An applet to put a 3D model into a page */
@SuppressWarnings("serial")
public class ThreeD extends Applet
        implements Runnable, MouseListener, MouseMotionListener {

    Model3D md;
    boolean painted = true;
    float xfac;
    int prevx, prevy;
    float scalefudge = 1;
    Matrix3D amat = new Matrix3D(), tmat = new Matrix3D();
    String mdname = null;
    String message = null;

    @Override
    public void init() {
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
                  mdname = "net/mk/ppmcu2d/models/dinasaur.obj";
            //mdname = "net/mk/ppmcu2d/models/mcw_extrude.obj";
            // mdname = "net/mk/ppmcu2d/models/cube.obj";
        }
        resize(getSize().width <= 20 ? 800 :500,
                getSize().height <= 20 ? 800 :500);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void destroy() {
        removeMouseListener(this);
        removeMouseMotionListener(this);
    }

    @Override
    public void run() {
        InputStream is = null;
        try {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            //is = new URL(getDocumentBase(), mdname).openStream();
            is = getClass().getClassLoader().getResource(mdname).openStream();
            Model3D m = new Model3D(is);
            md = m;
            m.findBB();
            m.compress();
            float xw = m.xmax - m.xmin;
            float yw = m.ymax - m.ymin;
            float zw = m.zmax - m.zmin;
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

    @Override
    public void start() {
        if (md == null && message == null) {
            new Thread(this).start();
        }
    }

    @Override
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

    @Override
    public String getAppletInfo() {
        return "Title: ThreeD \nAuthor: James Gosling? \n"
                + "An applet to put a 3D model into a page.";
    }

    @Override
    public String[][] getParameterInfo() {
        String[][] info = {
            { "model", "path string", "The path to the model to be displayed." },
            { "scale", "float", "The scale of the model.  Default is 1." }
        };
        return info;
    }
}
