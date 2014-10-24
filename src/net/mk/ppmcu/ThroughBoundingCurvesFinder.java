/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu;

/**
 *
 * @author PDI
 */
import java.util.Vector;
import java.util.concurrent.RecursiveTask;
import mkMath.GeometryOperations;
import mkMath.ProximityOperations;

public class ThroughBoundingCurvesFinder extends RecursiveTask {

    private int seq;
    private double[][] RackDimensions;
    private  double[][] IntesectionData;
    private double PinionSectionAccuracy;
    private int NumberOfPointsOnRack;
    private double YAccuracy;
    private double ScatterThreshold;
    GeometryOperations GO;
    public double CLUSETR_SCATTER_MAX = 10;//mm
    private int ToothSections;

    public ThroughBoundingCurvesFinder(double[][] RackDimensions, double[][] IntesectionData, double PinionSectionAccuracy, int NumberOfPointsOnRack, double YAccuracy, double ScatterThreshold,int ToothSections) {
        System.out.println("#BoundingCurvesFinder Set.. ");
        this.RackDimensions=RackDimensions;
        this.IntesectionData=IntesectionData;
        this.PinionSectionAccuracy=PinionSectionAccuracy;
        this.NumberOfPointsOnRack=NumberOfPointsOnRack;
        this.YAccuracy=YAccuracy;
        this.ScatterThreshold=ScatterThreshold;
        this.ToothSections=ToothSections;
        GO=new GeometryOperations();
    }

    /**
     * getNBoundaryPoints
     * @return 
     */
    public Vector compute() {
       Vector result=new Vector();
        long begTest = new java.util.Date().getTime();
        System.out.println("ThroughBoundingCurvesFinder Start");
        double[] yi = new double[NumberOfPointsOnRack];
        yi[0] = RackDimensions[0][1];
        yi[yi.length - 1] = RackDimensions[1][1];
        for (int i = 1; i < yi.length - 1; i++) {
            yi[i] = i * (RackDimensions[1][1] - RackDimensions[0][1]) / NumberOfPointsOnRack;
        }
        
        double[][] tempIntesectionData = GO.getPointsOnZPlane(IntesectionData, PinionSectionAccuracy);
            //if(tempIntesectionData==null){continue;}

            /**
             * Bounding Box Method
             */
            Vector boundary = GO.getBoundaryPoints(tempIntesectionData, YAccuracy, ScatterThreshold, RackDimensions[1][1], RackDimensions[0][1], false);

            /**
             * Based on Y sort method-Has issues-Do not Use
             */
            //Vector boundary=Pinion.getBoundaryPointsBasedOnYSort(tempIntesectionData,100);
            /**
             * Accept points only if they are not scattered
             */
            if (!new ProximityOperations().isScatteredCluster((double[][]) boundary.get(0), CLUSETR_SCATTER_MAX)) {
                //System.out.println(Pinion.getStrings(tempIntesectionData));
                //Sections1.add(Pinion.ExtrapolateXonCurve(Pinion.getXOnCurve((double[][]) boundary.get(0), yi)));
                if (((double[][]) boundary.get(0)).length > 3) {
                    result.add( GO.ExtrapolateXonCurve(GO.getXOnCurve((double[][]) boundary.get(0), yi)));
                }
            }
            if (!new ProximityOperations().isScatteredCluster((double[][]) boundary.get(1), CLUSETR_SCATTER_MAX)) {
                //Sections2.add(Pinion.ExtrapolateXonCurve(Pinion.getXOnCurve((double[][]) boundary.get(1), yi)));
                if (((double[][]) boundary.get(1)).length > 3) {
                    result.add( GO.ExtrapolateXonCurve(GO.getXOnCurve((double[][]) boundary.get(1), yi)));
                }
            }
        Double secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("ThroughBoundingCurvesFinder run time: " + secs + " secs");
        return result;
    }


    

}
