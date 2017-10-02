/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkMath;

/**
 *
 * @author PDI
 */
// grahamScan.java
// 
// Mark F. Hulber
// May 1996
//
//
// grahamScan implements the Graham Scan convex hull algorithm.  Given
//   a vector containing points it will return a vector of points forming
//   the convex hull of the input.  This class relies on extensions to the
//   point class called newPoints.  grahamScan does not begin computing the
//   convex hull until three points have been provided.
//
//Modified by Manoj for double precision
//


import java.awt.*;
import java.util.EmptyStackException;
import java.util.Vector;


public class grahamScan_ {

   java.util.Vector lines = new java.util.Vector(100, 100);
   java.util.Stack stk = new java.util.Stack();
   java.util.Vector s = new java.util.Vector(100, 100);


   public java.util.Vector doGraham(java.util.Vector q) throws EmptyStackException{
     System.out.print("\tDoing..");
     int m = 0; 
     mkPoints temp, temp2;
     int n = q.size();
     int a, i;
     if (n > 2) {
        s.removeAllElements();
        s = (java.util.Vector)q.clone();
        for (i = 1; i < n; i++)
           if (((mkPoints)s.elementAt(i)).y < ((mkPoints)s.elementAt(m)).y || 
              ((((mkPoints)s.elementAt(i)).y == ((mkPoints)s.elementAt(m)).y)&&
              (((mkPoints)s.elementAt(i)).x < ((mkPoints)s.elementAt(m)).x)))
            m = i;
        temp = (mkPoints)s.elementAt(0);
        s.setElementAt((mkPoints)s.elementAt(m),0);
        s.setElementAt(temp, m);
        
      // stage 2
        temp2 = (mkPoints)s.elementAt(0);
	for (i = 2; i < n; i++) {
	  for (int j = n-1; j >= i; j --) {
	    if (temp2.polarCmp((mkPoints)s.elementAt(j-1), 
                               (mkPoints)s.elementAt(j)) == 1) {
               temp = (mkPoints)s.elementAt(j-1);
               s.setElementAt((mkPoints)s.elementAt(j),j-1);
               s.setElementAt(temp, j);
	    }
	  }
	}
        System.out.print("Doing2..");
        for (i = 1; ((mkPoints)s.elementAt(i+1)).classify(
                                  (mkPoints)s.elementAt(0),
                                  (mkPoints)s.elementAt(i)) == 3; i++); 
        stk.removeAllElements();
        stk.push((mkPoints)s.elementAt(0));
        stk.push((mkPoints)s.elementAt(i));
        System.out.print("Doing3.."+stk.size());       
        boolean blah;
	for (i = i+1; i < n; i++) {
          blah = true;
	  while ( blah ) {
              /**Added by Manoj*/
             //System.out.println("Doing3.."+stk.size());  
             if(stk.size()>1){
             /**Added till here*/
             temp2 = (mkPoints)stk.pop();
             
             if (((mkPoints)s.elementAt(i)).classify((mkPoints)stk.peek(),
                                                     temp2) == 0) {
                stk.push(temp2);
                blah = false;
	     }
             }else {blah = false;}
             
             /**Added till here*/
	  }
          stk.push((mkPoints)s.elementAt(i));
	}
        
       lines.removeAllElements();

       while (!stk.empty())
          lines.addElement((mkPoints)stk.pop());
       
       System.out.println("Done.");
       return lines;
       
       
     }
     return null;
   }
   
   
   public double[][] doGraham(java.util.Vector q,double ZPlane) {
       System.out.print("Convertin.."+q.size());
       Vector tempResult=doGraham( q);
       double[][] result=new double[tempResult.size()][3];
       for(int i=0;i<result.length;i++){
           result[i][0]=((mkPoints)tempResult.elementAt(i)).x;
           result[i][1]=((mkPoints)tempResult.elementAt(i)).y;
           result[i][2]=ZPlane;
       }
       return result;
   }
   
   public double[][] doGraham(double[][] q,double ZPlane) {
       System.out.print("Started..");
       Vector tempResult=new Vector();
       double[][] result;
       for(int i=0;i<q.length;i++){
           tempResult.addElement(new mkPoints(q[i][0], q[i][1]));
           }
       result=doGraham(tempResult,ZPlane);
       
       return result;
   }
   
   
   public Vector getPointCloud(double[][] XYZs){
       Vector result=new Vector();
       for(int i=0;i<XYZs.length;i++){
           result.addElement(new mkPoints(XYZs[i][0], XYZs[i][1]));
       }
       return result;       
   }
}

