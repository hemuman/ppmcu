/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkMath;

import javax.swing.table.DefaultTableModel;
import net.mk.ppmcu2D.EqnSolver;

/**
 *
 * @author PDI
 */
public class LineCurve {
    /**
     * 
     * @param CurveData
     * @param zi
     * @return xi value of the zi for given curve data of shell
     */
    public double getXOnCurve(DefaultTableModel CurveData,double zi)
    {
        System.out.println("-------[Ladder Position Calculation]-------");
        double outerLengthCurve = 0;
        //double ladderLength = Double.parseDouble(LadderHeight.getText());
        double a = 0;
        double b = 0;
        double c = 0;
        double xi = 0;
        //double zi = Double.parseDouble(zStart.getText());//First Ladder Position
        Line line1 = new Line();
        Line line2 = new Line();
        EqnSolver eqs = new EqnSolver();
        boolean updateLine1 = true;
        boolean updateLine2 = true;
       // ladderData.setRowCount(0);
        /**Add First Position*/
        

        for (int i = 0; i < CurveData.getRowCount() - 2; i++) {
            System.out.print("");
            double tempx = 0, tempz = 0;
            
            if (zi > Double.parseDouble(CurveData.getValueAt(i + 1, 2).toString())) {
                System.out.print("[EscLift"+i+"]");
                continue;
            }
//& zi > Double.parseDouble(CurveData.getValueAt(i, 2).toString())
            if (updateLine1 ) {
                //System.out.print("#["+i+"\t Xi="+xi+" Zi="+zi);
                /**Step 1: Get Line Equation*/
                line1.setMXpC(
                        Double.parseDouble(CurveData.getValueAt(i, 0).toString()),
                        Double.parseDouble(CurveData.getValueAt(i, 2).toString()),
                        Double.parseDouble(CurveData.getValueAt(i + 1, 0).toString()),
                        Double.parseDouble(CurveData.getValueAt(i + 1, 2).toString()));
                updateLine1 = false;
                /**Step 2: Find xi for given zi*/
                xi = line1.getX(zi);
                System.out.println("#Finalized Lift>>"+i+"\t [Xi="+xi+",Zi="+zi+"]");
                //ladderData.addRow(new String[]{"1"});
                //ladderData.setValueAt(xi, ladderData.getRowCount() - 1, 0);
               /* if((ladderData.getRowCount() - 1)%2==0)
                    ladderData.setValueAt(FirstOffset.getText(), ladderData.getRowCount() - 1, 1);
                else
                    ladderData.setValueAt(SecondOffset.getText(), ladderData.getRowCount() - 1, 1);

                ladderData.setValueAt(zi, ladderData.getRowCount() - 1, 2);*/
                updateLine2 = true;
                tempx = xi;
                tempz = zi;
                return xi;
            }

            //Check if zi+LadderLength is less than the z2 value of the line.
            //& zi + ladderLength < Double.parseDouble(CurveData.getValueAt(i + 2, 2).toString())
          /*  if (updateLine2 ) {
                line2.setMXpC(
                        Double.parseDouble(CurveData.getValueAt(i + 1, 0).toString()),
                        Double.parseDouble(CurveData.getValueAt(i + 1, 2).toString()),
                        Double.parseDouble(CurveData.getValueAt(i + 2, 0).toString()),
                        Double.parseDouble(CurveData.getValueAt(i + 2, 2).toString()));
                updateLine2 = false;
                a = (1 + line2.m * line2.m);
                b = -2 * (xi - line2.c * line2.m + zi * line2.m);
                c = (line2.c - zi) * (line2.c - zi) - ladderLength * ladderLength + xi * xi;
                //New xi
                xi = eqs.QuadSolv(a, b, c);
                zi = line2.getY(xi);

                /**Test Root Selection*/
            /*    if (line2.m > 0) {
                    System.out.println("Is bigRoot.");
                    xi = eqs.bigRoot;
                    zi = line2.getY(xi);
                    /**
                     * Result is between the points i.e. in the Bounding Box,
                     * conditions are sensitive and will respond to values in first Quadrant only
                     */
         /*           if (zi > Double.parseDouble(CurveData.getValueAt(i + 1, 2).toString())
                            & zi < Double.parseDouble(CurveData.getValueAt(i + 2, 2).toString())
                            & xi >Double.parseDouble(CurveData.getValueAt(i + 1, 0).toString())
                            & xi <Double.parseDouble(CurveData.getValueAt(i + 2, 0).toString())) {
                        System.out.println("In Bounding Box.");
                        //Do Nothing
                    } else {
                        System.out.println("Was bigRoot but Changed to smallRoot");
                        if (line2.m < 0) {
                            xi = eqs.smallRoot;
                            zi = line2.getY(xi);
                        }
                    }
                }
                if (line2.m < 0) {
                    System.out.println("Is smallRoot.");
                    xi = eqs.smallRoot;
                    zi = line2.getY(xi);
                     /**
                     * Result is between the points i.e. in the Bounding Box,
                     * conditions are sensitive and will respond to values in first Quadrant only
                     */
           /*         if (zi > Double.parseDouble(CurveData.getValueAt(i + 1, 2).toString())
                            & zi < Double.parseDouble(CurveData.getValueAt(i + 2, 2).toString())
                            & xi <Double.parseDouble(CurveData.getValueAt(i + 1, 0).toString())
                            & xi >Double.parseDouble(CurveData.getValueAt(i + 2, 0).toString())) {
                        System.out.println("In Bounding Box.");
                        //Do Nothing
                    } else {
                        if (line2.m > 0) {
                            System.out.println("Was smallRoot but Changed to bigRoot");
                            xi = eqs.bigRoot;
                            zi = line2.getY(xi);
                        }
                    }
                }
                //New zi
                //zi=line2.getY(xi);

                //System.out.print("# Xn="+xi+" Zn="+zi);
                updateLine1 = true;
                
                System.out.println("#Temp Ladder-Lift->" + i+"[ Xn="+xi+",Zn="+zi+"]");
            }

            //outerLengthCurve=outerLengthCurve+line1.length;
            //System.out.println("Calculating Length:"+outerLengthCurve);*/
        }
        return 0;
    }
    
    // lineIntersect.pde
// Marius Watz - http://workshop.evolutionzone.com

// calculates valid intersection between two lines,
// so that the intersection will lie on the specified
// line segment.

Point p[];
int num;

//<editor-fold defaultstate="collapsed" desc="comment">
/*void setup() {
    size(500,250);
    
    num=12;
    p=new Point[num*2];
    for(int i=0; i< num*2; i++) p[i]=new Point(0,0);
    initPt();
}

void draw() {
    background(200);
    
    p[num-1].set(mouseX,mouseY);
    
    // check intersections with all lines
    for(int j=0; j< num; j++) {
        line(p[j*2].x,p[j*2].y, p[j*2+1].x,p[j*2+1].y);
        for(int i=0; i< num; i++) if(i!=j) {
            Point pt=findIntersection(
                    p[i*2],p[i*2+1], p[j*2],p[j*2+1]);
            if(pt!=null) ellipse(pt.x,pt.y, 14,14);
        }
    }

}

void initPt() {
    for(int i=0; i< num; i++) {
        if(random(100)>50) {
            p[i*2].set(20,random(20,height-20));
            p[i*2+1].set(width-20,random(20,height-20));
        }
        else {
            p[i*2].set(random(20,width-20),20);
            p[i*2+1].set(random(20,width-20),height-20);
        }
    }
}

void mousePressed() {
    initPt();
    p[num-2].set(mouseX,mouseY);
}*/
//</editor-fold>

// calculates intersection and checks for parallel lines.
// also checks that the intersection point is actually on
// the line segment p1-p2
public Point findIntersection(Point p1,Point p2, Point p3,Point p4) {
  float xD1,yD1,xD2,yD2,xD3,yD3;
  float dot,deg,len1,len2;
  float segmentLen1,segmentLen2;
  float ua,ub,div;

  // calculate differences
  xD1=p2.x-p1.x;
  xD2=p4.x-p3.x;
  yD1=p2.y-p1.y;
  yD2=p4.y-p3.y;
  xD3=p1.x-p3.x;
  yD3=p1.y-p3.y;  

  // calculate the lengths of the two lines
  len1= (float) Math.sqrt((double)(xD1*xD1+yD1*yD1));
  len2=(float) Math.sqrt((double)(xD2*xD2+yD2*yD2));

  // calculate angle between the two lines.
  dot=(xD1*xD2+yD1*yD2); // dot product
  deg=dot/(len1*len2);

  // if abs(angle)==1 then the lines are parallell,
  // so no intersection is possible
  if(Math.abs(deg)==1) return null;

  // find intersection Pt between two lines
  Point pt=new Point(0,0);
  div=yD2*xD1-xD2*yD1;
  ua=(xD2*yD3-yD2*xD3)/div;
  ub=(xD1*yD3-yD1*xD3)/div;
  pt.x=p1.x+ua*xD1;
  pt.y=p1.y+ua*yD1;

  // calculate the combined length of the two segments
  // between Pt-p1 and Pt-p2
  xD1=pt.x-p1.x;
  xD2=pt.x-p2.x;
  yD1=pt.y-p1.y;
  yD2=pt.y-p2.y;
  segmentLen1=(float) Math.sqrt((double)(xD1*xD1+yD1*yD1))+(float) Math.sqrt((double)(xD2*xD2+yD2*yD2));

  // calculate the combined length of the two segments
  // between Pt-p3 and Pt-p4
  xD1=pt.x-p3.x;
  xD2=pt.x-p4.x;
  yD1=pt.y-p3.y;
  yD2=pt.y-p4.y;
  segmentLen2=(float) Math.sqrt((double)(xD1*xD1+yD1*yD1))+(float) Math.sqrt((double)(xD2*xD2+yD2*yD2));

  // if the lengths of both sets of segments are the same as
  // the lenghts of the two lines the point is actually
  // on the line segment.

  // if the point isn't on the line, return null
  if(Math.abs(len1-segmentLen1)>0.01 || Math.abs(len2-segmentLen2)>0.01)
    return null;

  // return the valid intersection
  return pt;
}

class Point{
  float x,y;
  Point(float x, float y){
    this.x = x;
    this.y = y;
  }

  void set(float x, float y){
    this.x = x;
    this.y = y;
  }
}
    
    
}
