/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkMath;

/**
 *
 * @author PDI
 */
public class mkPoints {
       private java.lang.Math mm;
public double x;
public double y;
   public mkPoints(double nx, double ny){
     x=nx;
     y=ny;
   } 
   
   public double length() {
      return mm.sqrt(x*x + y*y);
   } 
   public int classify(mkPoints p0, mkPoints p1) {

     mkPoints a = new mkPoints(p1.x-p0.x, p1.y-p0.y);
     mkPoints b = new mkPoints(x - p0.x, y - p0.y);
     
     double sa = a.x * b.y - b.x * a.y;

     if (sa > 0.0)
        return 0;  // LEFT
     if (sa < 0.0)
        return 1;  // RIGHT
     if ((a.x * b.x < 0.0) || (a.y * b.y < 0.0))
        return 2;  // BEHIND
     if (a.length() < b.length())
        return 3;  // BEYOND
     if (p0.equals(this))
        return 4;  // ORIGIN
     if (p1.equals(this))
        return 5;  // DESTINATION
     return 6;     // BETWEEN
  }

  public double polarAngle() {
     
     if ((x == 0.0) && (y == 0.0))
        return -1.0;
     if (x == 0.0)
       return ((y > 0.0) ? 90 : 270);
     double theta = mm.atan((double)y/x);
     theta *= 360/(2*mm.PI);
     if (x > 0.0)
       return ((y >= 0.0) ? theta : 360 + theta);
     else
       return (180+theta);
  }   



  public int polarCmp(mkPoints p, mkPoints q) {


     mkPoints vp = new mkPoints(p.x-this.x, p.y-this.y);
     mkPoints vq = new mkPoints(q.x-this.x, q.y-this.y);

     double pPolar = vp.polarAngle();
     double qPolar = vq.polarAngle();

     if (pPolar < qPolar) return -1;
     if (pPolar > qPolar) return 1;
     if (vp.length() < vq.length()) return -1;
     if (vp.length() > vq.length()) return 1;
     return 0;
  }

}
