package mkMath;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author PDI
 */
public class StraightLine {
//slope intercept form: y=mx+c
    public double y;
    public double m;
    public double x;
    public double c;
    public double length;
    public boolean eqnSet=false;
    public boolean parallelToX=false;
    public double Y_const;
    public boolean parallelToY=false;
    public double X_const;
    public boolean m_infinite=false;
/**
 *
 * @param x1
 * @param y1
 * @param x2
 * @param y2
 */
public void setMXpC(double x1,double y1,double x2, double y2,boolean debug ){
    m=(y1-y2)/(x1-x2);
    if(m==0){ System.out.println("#parallelToX y= "+y1);parallelToX=true; Y_const=y1; }
    if((x1-x2)==0){System.out.println("#parallelToY x= "+x1);parallelToY=true; X_const=x1;m_infinite=true;}
    c=y1-m*x1;
    eqnSet=true;
    length=Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    if(debug)
    {System.out.println("# y= "+m+" x + "+c);
    System.out.println("\t Len=" +length+"\t Slope Angle"+Math.toDegrees(Math.atan(m)));}
}
/**
 * 
 * @param x1
 * @param y1
 * @param m
 */
public void setMC(double x1,double y1,double m){
    this.m=m;
    System.out.println("The Slope Angle"+Math.toDegrees(Math.atan(m)));
    if(Math.atan(m)==0){ System.out.println("#parallelToX y= "+y1);parallelToX=true; Y_const=y1; }
    if(Math.atan(m)==Math.PI/2){System.out.println("#parallelToY x= "+x1);parallelToY=true; X_const=x1;}
    c=y1-m*x1;
    eqnSet=true;
    System.out.println("# y= "+this.m+" x + "+c);
}
/**
 *
 * @param y
 * @return
 */
public double getX(double y){
    if(eqnSet){
        //if()
        
    if(parallelToY){ return X_const;}

        return (y-c)/m;
    }
    System.out.println("Equation not set, call setMxpC()");
    return 0;
}
/**
 * 
 * @param x
 * @return
 */
public double getY(double x){
    if(eqnSet){
        if(parallelToX){return Y_const; }
        return m*x+c;
    }
    System.out.println("Equation not set, call setMxpC()");
    return 0;
}
/**
 *
 * @param m
 * @param x
 * @param c
 * @return
 */
public double getPointOnLine(double m,double x,double c){
    return m*x+c;
}
/**
 *
 * @param x1
 * @param y1
 * @param x2
 * @param y2
 * @return
 */
public static double getLength(double x1,double y1,double x2, double y2){
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
}
/**
 * 
 * @param x1
 * @param y1
 * @param atDistance
 * @return 
 */
public double[] getPointsOnLine(double x1,double y1,double atDistance){
    double result[]=new double[4];
    if(eqnSet){
        double a = (1 + m * m);
        double b = -2 * (x1 - c * m +y1 * m);
        double cons = x1*x1+(c - y1) * (c - y1) - atDistance* atDistance ;

        EqnSolver eqs = new EqnSolver();
        eqs.QuadSolv(a, b, cons);
        result[0] = eqs.bigRoot;
        result[1] = getY(result[0]);
        result[2] = eqs.smallRoot;
        result[3] = getY(result[2]);
        System.out.println("Big Root Length"+getLength(x1,y1,result[0],result[1]));
        System.out.println("Small Root Length"+getLength(x1,y1,result[2],result[3]));
    }
        return result;
}

/**
 * Added on Aug 6th 2012
 * For 2D Only
 * @param corner1
 * @param corner2
 * @param point
 * @return 
 */
public boolean pointInBoundingBox(double corner1[],double corner2[],double point[]){
    //if(corner1.length==2){
        double xi=point[0];
        double yi=point[1];
       
        if((Math.abs(corner1[0]-xi)+Math.abs(corner2[0]-xi))==Math.abs(corner1[0]-corner2[0])
                &(Math.abs(corner1[1]-yi)+Math.abs(corner2[1]-yi))==Math.abs(corner1[1]-corner2[1])){
            return true;
        }
        
    return false;
}

/**
 * For 2D Only
 * @param corner1
 * @param corner2
 * @param point
 * @return 
 */
public boolean pointInBoundingBox_(double corner1[],double corner2[],double point[]){
    //if(corner1.length==2){
        double xi=point[0];
        double zi=point[1];
        setMXpC( corner1[0], corner1[1], corner2[0], corner2[1],false);
        if (m > 0) {
                   /**
                     * Result is between the points i.e. in the Bounding Box,
                     * conditions are sensitive and will respond to values in first Quadrant only
                     */
                    if (zi > corner1[1]
                            & zi < corner2[1]
                            & xi >corner1[0]
                            & xi <corner2[0]) {
                        return true;
                    } else {
                        return false;
                    }
                }
                if (m < 0 ) {
                   /**
                     * Result is between the points i.e. in the Bounding Box,
                     * conditions are sensitive and will respond to values in first Quadrant only
                     */
                    if(corner1[1]<corner2[1])
                    {
                        if (zi > corner1[1]
                            & zi < corner2[1]
                            & xi <corner1[0]
                            & xi >corner2[0]) {
                        return true;
                    } else {
                        return false;
                    }}

                    if(corner1[1]>corner2[1]);
                    if (    zi < corner1[1]
                            & zi > corner2[1]
                            & xi >corner1[0]
                            & xi <corner2[0]) {
                        return true;
                    } else {
                        return false;
                    }
                }
    //}
    return false;
}
/**
 * To be Implemented
 * @param x
 * @param y
 * @return
 */
public int getQuadrant(double x,double y){
    return 0;
}
/**
 * 
 * @return 
 */
public double getSlopeAngle(){
    return Math.toDegrees(Math.atan(m));
}
/**
 * 
 * @param line1
 * @param line2
 * @return 
 */
public double angleBeteenLines(StraightLine line1,StraightLine line2){
    return Math.atan((line2.m-line1.m)/(1+line1.m*line2.m));

}
/**
 * 
 * @param xyz
 * @return 
 */
public double LengthOfPerp(double[] xyz){
    /**
     * Convert y=mx+c eqn to ax+by+c=0 form=> y-mx-c=0, so a=-m, b=1 and c=-c
     * Then use formula (ax1+by1+c)/(Sqrt(a*a+b*b))
     */
    return (-m*xyz[0]+xyz[1]-c)/Math.sqrt(m*m+1*1);
}
}
