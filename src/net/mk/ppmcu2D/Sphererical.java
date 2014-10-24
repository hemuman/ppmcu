/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu2D;

import mkMath.Line;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.UIManager;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;

/**
 *
 * @author PDI
 */
public class Sphererical extends JComponent {

    public Sphererical() {
        setFont(UIManager.getFont("Label.font").deriveFont(10.0f));
        setPreferredSize(new Dimension(360, 360));

        Angles = new Vector();
        GraphicShapes = new JSONArray();
    }
    public Vector Angles;
 
    private int QUADRANT = 14;

    /**
     * Get the value of QUADRANT
     *
     * @return the value of QUADRANT
     */
    public int getQUADRANT() {
        return QUADRANT;
    }

    /**
     * Set the value of QUADRANT
     *
     * @param QUADRANT new value of QUADRANT
     */
    public void setQUADRANT(int QUADRANT) {
        this.QUADRANT = QUADRANT;
    }

    private int MarginPxl = 5;

    /**
     * Get the value of MarginPxl
     *
     * @return the value of MarginPxl
     */
    public int getMarginPxl() {
        return MarginPxl;
    }

    /**
     * Set the value of MarginPxl
     *
     * @param MarginPxl new value of MarginPxl
     */
    public void setMarginPxl(int MarginPxl) {
        this.MarginPxl = MarginPxl;
    }

    private double scalingfactor = .5;

    /**
     * Get the value of scalingfactor
     *
     * @return the value of scalingfactor
     */
    public double getScalingfactor() {
        return scalingfactor;
    }

    /**
     * Set the value of scalingfactor
     *
     * @param scalingfactor new value of scalingfactor
     */
    public void setScalingfactor(double scalingfactor) {
        this.scalingfactor = scalingfactor;
    }

    private boolean AutomaticScaling = true;

    /**
     * Get the value of AutomaticScaling
     *
     * @return the value of AutomaticScaling
     */
    public boolean isAutomaticScaling() {
        return AutomaticScaling;
    }

    /**
     * Set the value of AutomaticScaling
     *
     * @param AutomaticScaling new value of AutomaticScaling
     */
    public void setAutomaticScaling(boolean AutomaticScaling) {
        this.AutomaticScaling = AutomaticScaling;
    }

    protected double RealCenterX = 0;

    /**
     * Get the value of CenterX
     *
     * @return the value of CenterX
     */
    public double getRealCenterX() {
        return RealCenterX;
    }

    /**
     * Set the value of CenterX
     *
     * @param CenterX new value of CenterX
     */
    public void setRealCenterX(double CenterX) {
        this.RealCenterX = CenterX;
    }
    public double RealCenterY = 0;

    /**
     * Get the value of CenterY
     *
     * @return the value of CenterY
     */
    public double getRealCenterY() {
        return RealCenterY;
    }

    /**
     * Set the value of CenterY
     *
     * @param CenterY new value of CenterY
     */
    public void setRealCenterY(double CenterY) {
        this.RealCenterY = CenterY;
    }

    /**
     * Get the value of Angles
     *
     * @return the value of Angles
     */
    public Vector getAngles() {
        return Angles;
    }

    /**
     * Set the value of Angles
     *
     * @param Angles new value of Angles
     */
    public void setAngles(Vector Angles) {
        this.Angles = Angles;
    }
    public double GraphicCircleRadius = 150;

    /**
     * Get the value of GraphicCircleRadius
     *
     * @return the value of GraphicCircleRadius
     */
    public double getGraphicCircleRadius() {
        return GraphicCircleRadius;
    }

    /**
     * Set the value of GraphicCircleRadius
     *
     * @param GraphicCircleRadius new value of GraphicCircleRadius
     */
    public void setGraphicCircleRadius(double GraphicCircleRadius) {
        this.GraphicCircleRadius = GraphicCircleRadius;
    }
    public double RealCircleRadius = 8000;

    /**
     * Get the value of RealCircleRadius
     *
     * @return the value of RealCircleRadius
     */
    public double getRealCircleRadius() {
        return RealCircleRadius;
    }

    /**
     * Set the value of RealCircleRadius
     *
     * @param RealCircleRadius new value of RealCircleRadius
     */
    public void setRealCircleRadius(double RealCircleRadius) {
        this.RealCircleRadius = RealCircleRadius;
    }
    public JSONArray GraphicShapes;

    /**
     * Get the value of GraphicShapes
     *
     * @return the value of GraphicShapes
     */
    public JSONArray getGraphicShapes() {
        return GraphicShapes;
    }

    /**
     * Set the value of GraphicShapes
     *
     * @param GraphicShapes new value of GraphicShapes
     */
    public void setGraphicShapes(JSONArray GraphicShapes) {
        this.GraphicShapes = GraphicShapes;
    }
    private boolean SHOW_XAXIS_DIMENSIONS = true;

    /**
     * Get the value of SHOW_XAXIS_DIMENSIONS
     *
     * @return the value of SHOW_XAXIS_DIMENSIONS
     */
    public boolean isSHOW_XAXIS_DIMENSIONS() {
        return SHOW_XAXIS_DIMENSIONS;
    }

    /**
     * Set the value of SHOW_XAXIS_DIMENSIONS
     *
     * @param SHOW_XAXIS_DIMENSIONS new value of SHOW_XAXIS_DIMENSIONS
     */
    public void setSHOW_XAXIS_DIMENSIONS(boolean SHOW_XAXIS_DIMENSIONS) {
        this.SHOW_XAXIS_DIMENSIONS = SHOW_XAXIS_DIMENSIONS;
    }
    private boolean SHOW_YAXIS_DIMENSIONS = true;

    /**
     * Get the value of SHOW_YAXIS_DIMENSIONS
     *
     * @return the value of SHOW_YAXIS_DIMENSIONS
     */
    public boolean isSHOW_YAXIS_DIMENSIONS() {
        return SHOW_YAXIS_DIMENSIONS;
    }

    /**
     * Set the value of SHOW_YAXIS_DIMENSIONS
     *
     * @param SHOW_YAXIS_DIMENSIONS new value of SHOW_YAXIS_DIMENSIONS
     */
    public void setSHOW_YAXIS_DIMENSIONS(boolean SHOW_YAXIS_DIMENSIONS) {
        this.SHOW_YAXIS_DIMENSIONS = SHOW_YAXIS_DIMENSIONS;
    }

    @Override
    public void paintComponent(Graphics g) {
        // setGraphicCircleRadius(this.getWidth()/2);
        //Apply scaling factor only if Automatic Scaling is true
        if(AutomaticScaling) scalingfactor = getGraphicCircleRadius() / getRealCircleRadius();
        
        if (QUADRANT == 0) {//All 4 Qaudrant
            setRealCenterX( this.getWidth() / 2);
            setRealCenterY( this.getHeight() / 2);
        }
        if (QUADRANT == 14) {//1st and 4th Quadrant
            setRealCenterX( MarginPxl);
            setRealCenterY( this.getHeight() / 2);
        }
        if (QUADRANT == 12) {//1st and 2nd Quadrant
            setRealCenterX( this.getWidth() / 2);
            setRealCenterY( this.getHeight()-MarginPxl);
        }
        if (QUADRANT == 23) {//2nd and 3rd Quadrant
            setRealCenterX( this.getWidth()-MarginPxl );
            setRealCenterY( this.getHeight()/2);
        }
        if (QUADRANT == 34) {//3rd and 4th Quadrant
            setRealCenterX( this.getWidth()/2 );
            setRealCenterY( this.getHeight()-MarginPxl);
        }

        System.out.println("Scaling Factor:" + scalingfactor + "\tCenter:[" + getRealCenterX() + ":" + getRealCenterY() + "]");

        Graphics2D g2 = (Graphics2D) g;
        instrumentedPaintComponent(g2);
             //g2.fillRect(0, 0, getWidth()/2, getHeight()/2);
             
        //g2.scale(scalingfactor, scalingfactor);
        //Line2D line = new Line2D.Double(10, 10, 40, 40);
        float[] dash1 = {2f, 0f, 2f};
        BasicStroke bs1 = new BasicStroke(1,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND,
                1.0f,
                dash1,
                2f);
        //g2.transform(AffineTransform.getTranslateInstance(-50+(getWidth()-300)/2, 10+(getHeight()-130)/2));
        //g2.transform(AffineTransform.getScaleInstance(1, 1));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /**
         * Draw FP Circle
         */
        Shape FPCircle = new Ellipse2D.Double(getRealCenterX() - GraphicCircleRadius, getRealCenterY() - GraphicCircleRadius, GraphicCircleRadius * 2, GraphicCircleRadius * 2);
        //g2.drawOval(0,0, (int)GraphicCircleRadius*2, (int)GraphicCircleRadius*2);
        g2.setStroke(bs1);
        //g2.draw(FPCircle);
        g2.drawLine((int) getRealCenterX(), 5, (int) getRealCenterX(), this.getHeight());
        g2.setColor(Color.BLACK);
        g2.drawString(getYAXIS_NAME(), (int) getRealCenterX(), 9); // Label the cap style
        g2.drawLine(0, (int) getRealCenterY(), this.getWidth() - 15, (int) getRealCenterY());
        g2.setColor(Color.BLACK);
        g2.drawString(getXAXIS_NAME(), 2 * (int) getRealCenterX() - 30, (int) getRealCenterY()); // Label the cap style*/
        
        
        g2.setComposite(makeComposite(.4F));
        /**
         * Showing Dimensions X-Ordinate
         */
        if(SHOW_XAXIS_DIMENSIONS)
        for(int ix=0;ix<=(0.5*this.getWidth()/(10/scalingfactor));ix++){
            int ratioColor=(int)(255*ix/(0.5*this.getWidth()/(10/scalingfactor)));
            g2.setColor(new Color(0,0,ratioColor));
            g2.fillOval((int)(ix*(10/scalingfactor)), (int) getRealCenterY()-2,4,4);
            g2.drawString(""+(int)(ix*(10/scalingfactor)-getRealCenterX()) , (int)(ix*(10/scalingfactor)), (int) getRealCenterY()+10); // Label the cap style*/
            g2.fillOval((int)(this.getWidth()-ix*(10/scalingfactor))-2, (int) getRealCenterY(), 4,4);
            g2.drawString(""+(int)(-ix*(10/scalingfactor)+getRealCenterX()) , (int)(this.getWidth()-ix*(10/scalingfactor)), (int) getRealCenterY()+10); // Label the cap style*/
            
            g2.setColor(new Color(190,190,190));
            g2.drawLine((int)(ix*(10/scalingfactor)), 5, (int)(ix*(10/scalingfactor)), this.getHeight());
            g2.drawLine((int)(this.getWidth()-ix*(10/scalingfactor)), 5,(int)(this.getWidth()-ix*(10/scalingfactor)), this.getHeight());
        }
        
         /**
         * Showing Dimensions Y-Ordinate
         */
        if(SHOW_YAXIS_DIMENSIONS)
        for(int iy=0;iy<=(0.5*this.getHeight()/(10/scalingfactor));iy++){
            int ratioColor=(int)(255*iy/(0.5*this.getHeight()/(10/scalingfactor)));
            g2.setColor(new Color(0,ratioColor,0));
            g2.fillOval((int) getRealCenterX()-2,(int)(this.getHeight()-iy*(10/scalingfactor)),4,4);
            g2.drawString(""+(int)(iy*(10/scalingfactor)-getRealCenterY()),  (int) getRealCenterX()+10,(int)(this.getHeight()-iy*(10/scalingfactor))-5); // Label the cap style*/
            g2.fillOval( (int) getRealCenterX()-2, (int)(iy*(10/scalingfactor)), 4,4);
            g2.drawString(""+(int)(-iy*(10/scalingfactor)+getRealCenterY()) , (int) getRealCenterX()+10 , (int)(iy*(10/scalingfactor))-5); // Label the cap style*/
       
           g2.setColor(new Color(190,190,190));
            g2.drawLine(0, (int)(this.getHeight()-iy*(10/scalingfactor)), this.getWidth() - 15, (int)(this.getHeight()-iy*(10/scalingfactor)));
            g2.drawLine(0, (int)(iy*(10/scalingfactor)), this.getWidth() - 15, (int)(iy*(10/scalingfactor)));
        }
        
        
       
   //  int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
   // int fontSize = (int)Math.round(12.0 * screenRes / 72.0);
   // Font font = new Font("Arial", Font.PLAIN, fontSize);
   // g2.setFont(font);
        g2.setComposite(makeComposite(.1F));
        g2.setColor(new Color(0,0,0));
       // g2.setStroke(new BasicStroke(8)); // 8-pixel wide pen
        g2.drawString("[PDI-Preview 2D]", 20, getHeight()-20); // Label the cap style*/
        g2.setComposite(makeComposite(1F));
        
       /* try {
            createCustomRect(new double[]{90,90,90,90},7200,2400);
            GraphicShapes.put(getPolygon(0, 0, 10, 5000, 0, false, true, ""));
        } catch (JSONException ex) {
            Logger.getLogger(Sphererical.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        createCustom(g2);
        
  
    }

    /**
     *
     * @param g2
     */
    public void createCustom(Graphics2D g2) {
          
           
        // setGraphicCircleRadius(this.getWidth()/2);
        //Apply scaling factor only if Automatic Scaling is true
        if(AutomaticScaling) scalingfactor = getGraphicCircleRadius() / getRealCircleRadius();
       /* if (QUADRANT == 0) {
            setRealCenterX(scalingfactor * getRealCenterX() + this.getWidth() / 2);
            setRealCenterY(scalingfactor * getRealCenterY() + this.getHeight() / 2);
        }*/

        for (int i = 0; i < GraphicShapes.length(); i++) {
            try {
                if (GraphicShapes.getJSONObject(i).getString("Type").equalsIgnoreCase("Circle")) {
                    // paint(new Graphics());

                    Ellipse2D e = new Ellipse2D.Double(GraphicShapes.getJSONObject(i).getDouble("x-rad"),
                            GraphicShapes.getJSONObject(i).getDouble("y-rad"),
                            getRealCenterX() - GraphicCircleRadius + GraphicShapes.getJSONObject(i).getDouble("x"),
                            getRealCenterY() - GraphicCircleRadius + GraphicShapes.getJSONObject(i).getDouble("y"));
                    AffineTransform at = AffineTransform.getScaleInstance(scalingfactor, scalingfactor);
                    // at.scale(scalingfactor, scalingfactor);
                    g2.draw(at.createTransformedShape(e));
                }
                if (GraphicShapes.getJSONObject(i).getString("Type").equalsIgnoreCase("polygon")) {
                    g2.setColor(Color.MAGENTA);
                    
                    
                    int x = (int) (RealCenterX + scalingfactor * GraphicShapes.getJSONObject(i).getDouble("x"));
                    int y = (int) (RealCenterY - scalingfactor * GraphicShapes.getJSONObject(i).getDouble("y"));/*
                     * Look for -Ve Sign
                     */
                    if (GraphicShapes.getJSONObject(i).getBoolean("xyAtCenter")) {
                        x = x - (int) (scalingfactor * GraphicShapes.getJSONObject(i).getDouble("halfDiagLen") );
                        y = y - (int) (scalingfactor * GraphicShapes.getJSONObject(i).getDouble("halfDiagLen") );
                    }
                    
                    /**
                     * Calculate Xi and Yi for xPoints[] and yPoints[]
                     **/
                    int xPoints[]=new int[(int)GraphicShapes.getJSONObject(i).getDouble("numberOfSide")];
                    int yPoints[]=new int[(int)GraphicShapes.getJSONObject(i).getDouble("numberOfSide")];
                    
                    for(int sideCount=0;sideCount<xPoints.length;sideCount++){
                        xPoints[sideCount]=(int) (x+GraphicShapes.getJSONObject(i).getDouble("halfDiagLen")*Math.sin(sideCount*2*Math.PI/xPoints.length));
                        yPoints[sideCount]=(int) (y+GraphicShapes.getJSONObject(i).getDouble("halfDiagLen")*Math.cos(sideCount*2*Math.PI/xPoints.length));
                   
                    }
                    Polygon polygon2d=new Polygon(xPoints,yPoints,xPoints.length);
                    
                    /**
                     * Width of the Rectangle
                     */
                    AffineTransform at = AffineTransform.getTranslateInstance(x, y);
                    at.rotate(Math.toRadians(GraphicShapes.getJSONObject(i).getDouble("rot")));
                    at.scale(scalingfactor, scalingfactor);
                    g2.draw(at.createTransformedShape(polygon2d));

                    if (GraphicShapes.getJSONObject(i).getBoolean("showInfo")) {
                        g2.setColor(Color.BLUE);
                        g2.drawString("x:" + GraphicShapes.getJSONObject(i).getDouble("x"), x, y + 10); // Label the cap style
                        g2.drawString("y:" + GraphicShapes.getJSONObject(i).getDouble("y"), x, y + 20); // Label the cap style
                        g2.setColor(Color.BLACK);
                    }
                    if (GraphicShapes.getJSONObject(i).getString("Message") != null) {
                        g2.setColor(Color.MAGENTA);
                        g2.drawString(GraphicShapes.getJSONObject(i).getString("Message"), x + 10, y + (int) (scalingfactor * GraphicShapes.getJSONObject(i).getDouble("halfDiagLen") / 2)); // Label the cap style
                        g2.setColor(Color.BLACK);
                    }
                }
                
                if (GraphicShapes.getJSONObject(i).getString("Type").equalsIgnoreCase("rectangle")) {
                    g2.setColor(Color.BLACK);
                    int x = (int) (RealCenterX + scalingfactor * GraphicShapes.getJSONObject(i).getDouble("x"));
                    int y = (int) (RealCenterY - scalingfactor * GraphicShapes.getJSONObject(i).getDouble("y"));/*
                     * Look for -Ve Sign
                     */
                    if (GraphicShapes.getJSONObject(i).getBoolean("xyAtCenter")) {
                        x = x - (int) (scalingfactor * GraphicShapes.getJSONObject(i).getDouble("xlen") / 2);
                        y = y - (int) (scalingfactor * GraphicShapes.getJSONObject(i).getDouble("ylen") / 2);
                    }
                    Rectangle2D rectangle = new Rectangle2D.Double(0, 0,/**
                             * Keep 0,0 as default value
                             */
                            GraphicShapes.getJSONObject(i).getDouble("xlen"),/**
                             * Length of the Rectangle
                             */
                            GraphicShapes.getJSONObject(i).getDouble("ylen"));
                    /**
                     * Width of the Rectangle
                     */
                    AffineTransform at = AffineTransform.getTranslateInstance(x, y);
                    at.rotate(Math.toRadians(GraphicShapes.getJSONObject(i).getDouble("rot")));
                    at.scale(scalingfactor, scalingfactor);
                   // g2.draw(at.createTransformedShape(rectangle));
                    if(GraphicShapes.getJSONObject(i).getBoolean("fill"))
                     g2.fill(at.createTransformedShape(rectangle));
                    else
                     g2.draw(at.createTransformedShape(rectangle));   

                    if (GraphicShapes.getJSONObject(i).getBoolean("showInfo")) {
                        g2.setColor(Color.BLUE);
                        g2.drawString("x:" + GraphicShapes.getJSONObject(i).getDouble("x"), x, y + 10); // Label the cap style
                        g2.drawString("y:" + GraphicShapes.getJSONObject(i).getDouble("y"), x, y + 20); // Label the cap style
                        g2.setColor(Color.BLACK);
                    }
                    if (GraphicShapes.getJSONObject(i).getString("Message") != null) {
                        g2.setColor(Color.MAGENTA);
                        g2.drawString(GraphicShapes.getJSONObject(i).getString("Message"), x + 10, y + (int) (scalingfactor * GraphicShapes.getJSONObject(i).getDouble("ylen") / 2)); // Label the cap style
                        g2.setColor(Color.BLACK);
                    }
                }

                if (GraphicShapes.getJSONObject(i).getString("Type").equalsIgnoreCase("Message")) {
                    int x = (int) (RealCenterX + scalingfactor * GraphicShapes.getJSONObject(i).getDouble("x"));
                    int y = (int) (RealCenterY - scalingfactor * GraphicShapes.getJSONObject(i).getDouble("y"));/*
                     * Look for -Ve Sign
                     */
                    g2.setColor(Color.MAGENTA);
                    g2.drawString(GraphicShapes.getJSONObject(i).getString("message"), x, y); // 
                    g2.setColor(Color.BLACK);
                }

                if (GraphicShapes.getJSONObject(i).getString("Type").equalsIgnoreCase("Line")) {
                    // paint(new Graphics());

                    Line2D line;
                    line = new Line2D.Double();
                    line.setLine(RealCenterX + scalingfactor * GraphicShapes.getJSONObject(i).getDouble("x_1"),
                            RealCenterY - scalingfactor * GraphicShapes.getJSONObject(i).getDouble("y_1"),
                            RealCenterX + scalingfactor * GraphicShapes.getJSONObject(i).getDouble("x_2"),
                            RealCenterY - scalingfactor * GraphicShapes.getJSONObject(i).getDouble("y_2"));
                    g2.draw(line);
                }
                if (GraphicShapes.getJSONObject(i).getString("Type").equalsIgnoreCase("CrossHair")) {

                    drawCrossHair(g2,
                            RealCenterX + scalingfactor * GraphicShapes.getJSONObject(i).getDouble("x"),
                            RealCenterY - scalingfactor * GraphicShapes.getJSONObject(i).getDouble("y"),
                            scalingfactor * GraphicShapes.getJSONObject(i).getDouble("xlen"),
                            scalingfactor * GraphicShapes.getJSONObject(i).getDouble("ylen"),
                            GraphicShapes.getJSONObject(i).getDouble("rot"));
                }

            } catch (JSONException ex) {
                Logger.getLogger(Sphererical.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
     
    }

//  Image cache;
    protected void instrumentedPaintComponent(Graphics2D g2) {
    
           GradientPaint paint = new GradientPaint(0, 0, new Color(240,240,240),0, getHeight(),new Color(150,150,150));
        g2.setPaint(paint);
            g2.fillRect(0, 0, getWidth(), getHeight());
             g2.setPaint(Color.black);
    }
     
    private AlphaComposite makeComposite(float alpha) {
    int type = AlphaComposite.SRC_OVER;
    return(AlphaComposite.getInstance(type, alpha));
  }
    
    /**
     *
     * @param angle
     * @param length
     * @param width
     */
    public void createCustomRect(double[] angle, double length, double width) {

        try {
            //GraphicShapes.put( getEllipse(1000,1000, 0, 0));
            //double[] angle=new double[]{90,90,90,90};
            //double length=7200;
            //double width=2400;

            GraphicShapes.put(getLine(0, 0, length * Math.cos(Math.toRadians(angle[0])), length * Math.sin(Math.toRadians(angle[0]))));
            GraphicShapes.put(getMessage(0 + length * Math.cos(Math.toRadians(angle[0])) / 2, 0 + length * Math.sin(Math.toRadians(angle[0])) / 2, "#" + angle[0], 10));
            Line line1 = new Line();
            line1.setThetaC(length * Math.cos(Math.toRadians(angle[0])), length * Math.sin(Math.toRadians(angle[0])), Math.PI - Math.toRadians(angle[1] - angle[0]));
            GraphicShapes.put(getLine(width * Math.cos(Math.toRadians(angle[3] + angle[0])), width * Math.sin(Math.toRadians(angle[3] + angle[0])), 0, 0));
            GraphicShapes.put(getMessage(0 + width * Math.cos(Math.toRadians(angle[3] + angle[0])) / 2, 0 + width * Math.sin(Math.toRadians(angle[3] + angle[0])) / 2, "#" + angle[3], 10));

            Line line2 = new Line();
            line2.setThetaC(width * Math.cos(Math.toRadians(angle[3] + angle[0])), width * Math.sin(Math.toRadians(angle[3] + angle[0])), -Math.PI + Math.toRadians(angle[0] + angle[2] + angle[3]));

            double intersectionPoint[] = line2.intersectionPointBeteenLines(line1, line2);

            GraphicShapes.put(getLine(length * Math.cos(Math.toRadians(angle[0])), length * Math.sin(Math.toRadians(angle[0])), intersectionPoint[0], intersectionPoint[1]));
            GraphicShapes.put(getMessage((intersectionPoint[0] + length * Math.cos(Math.toRadians(angle[0]))) / 2, (intersectionPoint[1] + length * Math.sin(Math.toRadians(angle[0]))) / 2, "#" + angle[1], 10));


            GraphicShapes.put(getLine(width * Math.cos(Math.toRadians(angle[3] + angle[0])), width * Math.sin(Math.toRadians(angle[3] + angle[0])), intersectionPoint[0], intersectionPoint[1]));
            GraphicShapes.put(getMessage((intersectionPoint[0] + width * Math.cos(Math.toRadians(angle[3] + angle[0]))) / 2, (intersectionPoint[1] + width * Math.sin(Math.toRadians(angle[3] + angle[0]))) / 2, "#" + angle[2], 10));


        } catch (JSONException ex) {
            Logger.getLogger(Sphererical.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates the pattern of the rectangle
     *
     * @param length
     * @param width
     * @param qtyX
     * @param qtyY
     * @param zRot
     * @param Message
     */
    public void createCustomRectPattern(double length, double width, double qtyX, double qtyY, double zRot, String Message) {

        try {
            for (int i = 0; i < qtyX; i++) {
                for (int j = 0; j < qtyY; j++) {
                    GraphicShapes.put(getRectangle(i * length, width + j * width, length, width, zRot, false, false, Message));
                    //Note: Hey Manoj! Check rason for width+j*width,length

                }
            }

        } catch (JSONException ex) {
            Logger.getLogger(Sphererical.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JSONObject getPolygon(double x, double y,double numberOfSide,  double halfDiagLen, double zRot, boolean showInfo, boolean xyAtCenter, String Message) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("Type", "polygon");
        result.put("x", x);
        result.put("y", y);
        result.put("numberOfSide", numberOfSide);
        result.put("halfDiagLen", halfDiagLen);
        result.put("rot", zRot);
        result.put("showInfo", showInfo);
        result.put("xyAtCenter", xyAtCenter);
        result.put("Message", Message);

        return result;
    }

    /**
     * new
     * JSONObject("{'Type':'rectangle','x':'0','y':'0','xlen':'190','ylen':'110','rot':'0.0'}");
     *
     * @param x
     * @param y
     * @param xLen
     * @param yLen
     * @param zRot
     * @return
     * @throws JSONException
     */
    public JSONObject getRectangle(double x, double y, double xLen, double yLen, double zRot, boolean showInfo, boolean xyAtCenter, String Message) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("Type", "rectangle");
        result.put("fill", "true");
        result.put("x", x);
        result.put("y", y);
        result.put("xlen", xLen);
        result.put("ylen", yLen);
        result.put("rot", zRot);
        result.put("showInfo", showInfo);
        result.put("xyAtCenter", xyAtCenter);
        result.put("Message", Message);

        return result;
    }

    public JSONObject getRectangle(String x, String y, String xLen, String yLen, String zRot, boolean showInfo, boolean xyAtCenter, String Message) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("Type", "rectangle");
        result.put("fill", "true");
        result.put("x", x);
        result.put("y", y);
        result.put("xlen", xLen);
        result.put("ylen", yLen);
        result.put("rot", zRot);
        result.put("showInfo", showInfo);
        result.put("xyAtCenter", xyAtCenter);
        result.put("Message", Message);

        return result;
    }

    /**
     * new
     * JSONObject("{'Type':'circle','x-rad':'10','y-rad':'10','x':'100','y':'100'}");
     *
     * @param x
     * @param y
     * @param xLen
     * @param yLen
     * @param zRot
     * @return
     * @throws JSONException
     */
    public JSONObject getEllipse(double x_rad, double y_rad, double x, double y) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("Type", "Circle");
        result.put("x", x);
        result.put("y", y);
        result.put("x-rad", x_rad);
        result.put("y-rad", y_rad);

        return result;
    }

    /**
     *
     * @param x_1
     * @param y_1
     * @param x_2
     * @param y_2
     * @return
     * @throws JSONException
     */
    public JSONObject getLine(double x_1, double y_1, double x_2, double y_2) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("Type", "Line");
        result.put("x_1", x_1);
        result.put("y_1", y_1);
        result.put("x_2", x_2);
        result.put("y_2", y_2);
        return result;

    }

    /**
     *
     * @param x
     * @param y
     * @param xLen
     * @param yLen
     * @param zRot
     * @return
     * @throws JSONException
     */
    public JSONObject getCrossHair(double x, double y, double xLen, double yLen, double zRot) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("Type", "CrossHair");
        result.put("x", x);
        result.put("y", y);
        result.put("xlen", xLen);
        result.put("ylen", yLen);
        result.put("rot", zRot);
        return result;

    }

    public JSONObject getMessage(double x, double y, String Message, int size) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("Type", "Message");
        result.put("x", x);
        result.put("y", y);
        result.put("message", Message);
        result.put("size", size);
        return result;

    }

    /**
     *
     * @param g2
     * @param x
     * @param y
     * @param xLen
     * @param yLen
     * @param zRot in degrees
     */
    public void drawCrossHair(Graphics2D g2, double x, double y, double xLen, double yLen, double zRot) {
        g2.setColor(Color.BLUE);
        Line2D line;
        line = new Line2D.Double();
        line.setLine(x, y - yLen / 2, x, y + yLen / 2);
        AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(zRot));
        g2.draw(at.createTransformedShape(line));
        AffineTransform at2 = AffineTransform.getRotateInstance(Math.toRadians(zRot));
        Line2D line2 = new Line2D.Double();
        line2.setLine(x - xLen / 2, y, x + xLen / 2, y);
        g2.draw(at2.createTransformedShape(line2));
        g2.setColor(Color.BLUE);
    }

    public JSONObject getSphere(double x, double y, double z, double radius) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("Type", "Sphere");
        result.put("x", x);
        result.put("y", y);
        result.put("z", z);
        result.put("radius", radius);
        return result;
    }

    public JSONObject getSpinCenter(double x, double y, double z, double radius) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("Type", "Sphere");
        result.put("x", x);
        result.put("y", y);
        result.put("z", z);
        result.put("radius", radius);
        return result;
    }

    public void drawSpinCenter(Graphics2D g2, double x, double y, double xLen, double yLen, double xRot, double yRot, double zRot) {
        g2.setColor(Color.BLUE);
        Line2D line;
        line = new Line2D.Double();
        line.setLine(x, y - yLen / 2, x, y + yLen / 2);
        AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(zRot));
        g2.draw(at.createTransformedShape(line));
        AffineTransform at2 = AffineTransform.getRotateInstance(Math.toRadians(zRot));
        Line2D line2 = new Line2D.Double();
        line2.setLine(x - xLen / 2, y, x + xLen / 2, y);
        g2.draw(at2.createTransformedShape(line2));
        g2.setColor(Color.BLUE);
    }
    
    private String XAXIS_NAME = "X-Axis";

    /**
     * Get the value of XAXIS_NAME
     *
     * @return the value of XAXIS_NAME
     */
    public String getXAXIS_NAME() {
        return XAXIS_NAME;
    }

    /**
     * Set the value of XAXIS_NAME
     *
     * @param XAXIS_NAME new value of XAXIS_NAME
     */
    public void setXAXIS_NAME(String XAXIS_NAME) {
        this.XAXIS_NAME = XAXIS_NAME;
    }
    private String YAXIS_NAME = "Y-Axis";

    /**
     * Get the value of YAXIS_NAME
     *
     * @return the value of YAXIS_NAME
     */
    public String getYAXIS_NAME() {
        return YAXIS_NAME;
    }

    /**
     * Set the value of YAXIS_NAME
     *
     * @param YAXIS_NAME new value of YAXIS_NAME
     */
    public void setYAXIS_NAME(String YAXIS_NAME) {
        this.YAXIS_NAME = YAXIS_NAME;
    }

}
