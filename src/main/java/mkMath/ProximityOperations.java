package mkMath;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Vector;

/**
 *
 * @author PDI
 */
public class ProximityOperations {
       /**
     * Returns true if the data is scattered
     * @param Sections
     * @param Threshold
     * @return 
     */
    public boolean isScatteredCluster(Vector Sections, double Threshold) {

        /**
         * Get distance between points on curve, if it is greater that Threshold
         * separate them from there
         */
        for (int sectionCount = 1; sectionCount < Sections.size(); sectionCount++) {
            double[] point1 = ((double[][]) Sections.get(sectionCount - 1))[0];
            double[] point2 = ((double[][]) Sections.get(sectionCount))[0];
            //System.out.println("XYZ1:" + point1[0] + ":" + point1[1] + ":" + point1[2]);
            //System.out.println("XYZ2:" + point2[0] + ":" + point2[1] + ":" + point2[2]);
            double distance = getDistanceBetween2Points(point1, point2);
            //System.out.println("Distance=" + distance + "\t Threshold=" + Threshold);
            if (distance > Threshold) {
                return true;
            }
        }
        return false;
    }

    public boolean isScatteredCluster(double[][] Points, double Threshold) {

        /**
         * Get distance between points on curve, if it is greater that Threshold
         * separate them from there
         */
        for (int pointsCount = 1; pointsCount < Points.length; pointsCount++) {
            double[] point1 = Points[pointsCount - 1];
            double[] point2 = Points[pointsCount];
            //System.out.println("XYZ1:" + point1[0] + ":" + point1[1] + ":" + point1[2]);
            //System.out.println("XYZ2:" + point2[0] + ":" + point2[1] + ":" + point2[2]);
            double distance = getDistanceBetween2Points(point1, point2);
            //System.out.println("Distance=" + distance + "\t Threshold=" + Threshold);
            if (distance > Threshold) {
                System.out.println("@isScatteredCluster =true Distance=" + distance + "\t Threshold=" + Threshold);
                System.out.println("XYZ1:" + point1[0] + ":" + point1[1] + ":" + point1[2]);
                System.out.println("XYZ2:" + point2[0] + ":" + point2[1] + ":" + point2[2]);
                return true;
            }
        }
        return false;
    }

     public double PerpDistance(double[] xyz1, double[] xyz2, double[] xyz3,boolean debug) {
        StraightLine line1 = new StraightLine();
        line1.setMXpC(xyz1[0], xyz1[1], xyz3[0], xyz3[1],true);
        if(debug)System.out.println("Perpendicular Distance: "+line1.LengthOfPerp(xyz2));
        return line1.LengthOfPerp(xyz2);
    }
     
      public double getDistanceBetween2Points(double[] XYZ1, double[] XYZ2) {
        return Math.sqrt((XYZ1[0] - XYZ2[0]) * (XYZ1[0] - XYZ2[0]) + (XYZ1[1] - XYZ2[1]) * (XYZ1[1] - XYZ2[1]) + (XYZ1[2] - XYZ2[2]) * (XYZ1[2] - XYZ2[2]));

    }

}
