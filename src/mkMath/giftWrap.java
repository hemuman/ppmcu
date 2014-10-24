/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkMath;

/**
 *
 * @author PDI
 */
// giftWrap.java
// 
// Mark F. Hulber
// May 1996
//
// giftWrap computes the convex hull of a set of points using the Gift Wrap
//    algorithm.  It begins to compute the convex hull from the first point.
//    Given a Vector of points it will return a Vector of points representing
//    the convex hull of the input points.
//

import java.awt.*;


public class giftWrap {

   java.util.Vector lines = new java.util.Vector(100, 100);
   java.util.Vector s = new java.util.Vector(100, 100);

   public java.util.Vector doWrap(java.util.Vector q) {

      
      newPoint temp;
      int n = q.size();
      int a, i;
      if (n > 0) {
      s.removeAllElements();
      s = (java.util.Vector)q.clone();
      for (a = 0, i = 1; i < n; i++)
         if (((newPoint)s.elementAt(i)).x < ((newPoint)s.elementAt(a)).x || 
           ((((newPoint)s.elementAt(i)).x == ((newPoint)s.elementAt(a)).x)&&   
              (((newPoint)s.elementAt(i)).y < ((newPoint)s.elementAt(a)).y)))
            a = i;
      s.addElement((newPoint)s.elementAt(a));
      lines.removeAllElements();

      for (int m = 0; m < n; m++) {
         temp = (newPoint)s.elementAt(a);
         s.setElementAt((newPoint)s.elementAt(m),a);
         s.setElementAt(temp, m);
         lines.addElement(new newPoint(((newPoint)s.elementAt(m)).x,
                                       ((newPoint)s.elementAt(m)).y));

         a = m + 1;

         for (i = m + 2; i <= n; i++) {
            int c = ((newPoint)s.elementAt(i)).classify(
                     (newPoint)s.elementAt(m), (newPoint)s.elementAt(a));
            if (c == 1 || c == 3)
               a = i;
         }

         if ( a == n)
            return lines;

      }
      }
      return null;
   }
}

