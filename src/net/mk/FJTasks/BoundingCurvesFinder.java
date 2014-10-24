/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.FJTasks;

/**
 *
 * @author PDI
 */
import java.util.Vector;
import java.util.concurrent.RecursiveTask;
import mkMath.GeometryOperations;
import mkMath.ProximityOperations;

public class BoundingCurvesFinder extends RecursiveTask {

    private int seq;
    private double[][] RackDimensions;
    private  double[][] IntesectionData;
    private double PinionSectionAccuracy;
    private int NumberOfPointsOnRack;
    private double YAccuracy;
    private double ScatterThreshold;
    GeometryOperations GO;
    public double CLUSETR_SCATTER_MAX = 10;//mm

    public BoundingCurvesFinder(double[][] RackDimensions, double[][] IntesectionData, double PinionSectionAccuracy, int NumberOfPointsOnRack, double YAccuracy, double ScatterThreshold) {
        System.out.println("#BoundingCurvesFinder Set.. ");
        this.RackDimensions=RackDimensions;
        this.IntesectionData=IntesectionData;
        this.PinionSectionAccuracy=PinionSectionAccuracy;
        this.NumberOfPointsOnRack=NumberOfPointsOnRack;
        this.YAccuracy=YAccuracy;
        this.ScatterThreshold=ScatterThreshold;
        GO=new GeometryOperations();
    }

    /**
     * getNBoundaryPoints
     * @return 
     */
    public Vector compute() {
       Vector result=new Vector();
        long begTest = new java.util.Date().getTime();
        System.out.println("BoundingCurvesFinder Start");
        double[][] tempIntesectionData = GO.getPointsOnZPlane(IntesectionData, PinionSectionAccuracy);
            //if(tempIntesectionData==null){continue;}
            /**
             * Bounding Box Method
             */
            Vector boundary = GO.getBoundaryPoints(tempIntesectionData, YAccuracy, ScatterThreshold, RackDimensions[1][1], RackDimensions[0][1], true);
            //Vector boundary = GO.getBoundaryPointCloud(tempIntesectionData, YAccuracy, ScatterThreshold, RackDimensions[1][1], RackDimensions[0][1],10 ,true);

            /**
             * Based on Y sort method-Has issues-Do not Use
             */
            //Vector boundary=Pinion.getBoundaryPointsBasedOnYSort(tempIntesectionData,100);
            /**
             * Accept points only if they are not scattered
             */
            if (!new ProximityOperations().isScatteredCluster((double[][]) boundary.get(0), CLUSETR_SCATTER_MAX)) {
                //System.out.println(Pinion.getStrings(tempIntesectionData));
                //N Boundary points
                result.add((double[][])GO.getNBoundaryPoints((double[][]) boundary.get(0), NumberOfPointsOnRack).get(0));
                //All Boundary points
               //Sections1.add((double[][]) boundary.get(0));
            }
            if (!new ProximityOperations().isScatteredCluster((double[][]) boundary.get(1), CLUSETR_SCATTER_MAX)) {
                //N Boundary points
                result.add((double[][]) GO.getNBoundaryPoints((double[][]) boundary.get(1), NumberOfPointsOnRack).get(0));
                //All Boundary points
               //Sections2.add((double[][]) boundary.get(1));
            }
        Double secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("BoundingCurvesFinder run time " + secs + " secs");
        return result;
    }


    

}
