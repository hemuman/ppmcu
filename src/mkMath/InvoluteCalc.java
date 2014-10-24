package mkMath;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.concurrent.RecursiveTask;
import json.JSONArray;

/**
 *
 * @author PDI
 */
public class InvoluteCalc  extends RecursiveTask  {
    
    private int seq;
    int somedata;
    double angle;
    boolean AngleInRadian;
     
    double startRadius; double endRadius; double increment; double Zdist; boolean Mirror;
    public InvoluteCalc(double startRadius, double endRadius, double increment, double Zdist, boolean Mirror) {
      this.startRadius=startRadius;
      this.endRadius=endRadius;
      this.increment= increment;
       this.Zdist= Zdist;
        this.Mirror= Mirror;
    }

    public Double[][] compute() {
        
       int loopCount = (int) Math.abs((endRadius - startRadius) / increment);
        double remainingDia = 0;
        boolean processLastPoint = false;
        Double[][] result = new Double[loopCount][3];
        if ((loopCount * increment) > (endRadius - startRadius)) {
            loopCount = loopCount - 1;//Reduce one count
            remainingDia = (endRadius - startRadius) - loopCount * increment;
            result = new Double[loopCount + 1][3];
            processLastPoint = true;
            //System.out.println("One Point Extra");
        } else if ((loopCount * increment) < (endRadius - startRadius)) {
            remainingDia = (endRadius - startRadius) - loopCount * increment;
            result = new Double[loopCount + 1][3];
            processLastPoint = true;
            //System.out.println("One Point less");
        } else {
            result = new Double[loopCount][3];
            processLastPoint = false;
            //System.out.println("Equal Number");
        }

        for (int i = 0; i < loopCount; i++) {

            result[i] = involuteXYZ(startRadius + increment * i, Zdist, Mirror);
            //result[i]=sinXYZ(startAngle+increment*i,10);
            //result[i]=new double[]{startAngle*i,endAngle*i,0};
        }
         String str = "";
        for (int index = 0; index < 20000; index++) {
                str = str + 't';
            }

        /*
         * Process the remaining length
         */
        if (processLastPoint) {
            result[loopCount] = involuteXYZ(startRadius + (endRadius - startRadius), Zdist, Mirror);
            //System.out.println("Processed Last Point");
            //System.out.println("Cylinder Surface" + result[loopCount][0] + ":" + result[loopCount][1] + ":" + result[loopCount][2]);
        }

        return result;
        
        
    }

    
   
    public Double involute(double angle, boolean AngleInRadian) {
        if (AngleInRadian) //if angle is expressed in Radians then do following
        {
            return Math.tan(angle) - Math.toRadians(angle);
        }

        //Convert angle to Radians if expressed in Degrees
        return Math.tan(Math.toRadians(angle)) - Math.toRadians(angle);
    }
    
        /**
     * One Point on Involute Curve,
     * @param varRadius
     * @param Zdist
     * @param Mirror
     * @return
     */
    public Double[] involuteXYZ(double varRadius/**
             * For Involute
             */
            , double Zdist, boolean Mirror) {

        double mirror = 1;
        if (Mirror) {
            mirror = -1;
        }
        //Get the Radius for Calulation of (X,Y) points on involute
        //baseCircleRadius= pitchCircleRadius_NP*Math.cos(Math.toRadians(transversePressureAngle));////
        double phiAngle = Math.acos(PinionStaticData.baseCircleRadius_TP / varRadius);
        double involuteFuncConstant = Math.tan(Math.toRadians(PinionStaticData.transversePressureAngle)) - Math.toRadians(PinionStaticData.transversePressureAngle);
        double involuteFuncConstant2 = Math.tan(phiAngle) - phiAngle;
        //Halftooth at R1
        //double HalfToothAtR = 2 * radiusAtInvolute * (ToothWidthAtPitchCircle_NP / (2 * pitchCircleRadius_NP)) + involuteFuncConstant - involuteFuncConstant2;
        /**
         * Below line is commented to test the tooth width data at base circle
         * on 25th July 2011
         */
        //double vectorialAngle = (Math.PI / numberOfTeeth) - ((ToothWidthAtPitchCircle_NP / (2 * pitchCircleRadius_NP)) + involuteFuncConstant2 - involuteFuncConstant);
        //(Math.PI / numberOfTeeth) - 
        double vectorialAngle = (Math.PI / PinionStaticData.numberOfTeeth) - ((PinionStaticData.ToothChordAtBaseCircle_TP / (2 * PinionStaticData.baseCircleRadius_TP)) + involuteFuncConstant2 - involuteFuncConstant);
        double x;
        double y;
        double reqAngle = 2 * Math.toRadians(PinionStaticData.BC_HalfToothAngle_TP) - PinionStaticData.ToothProfileCorrectionAngle_TP;
        if (Mirror) {
            return involuteXYZ_Mirror(varRadius, Zdist);
        } else {
            //angleSai=angleSai-angleSaiIncrement*mirror;

            x = varRadius * Math.sin(vectorialAngle + PinionStaticData.angleSai + reqAngle / 2);
            y = varRadius * Math.cos(vectorialAngle + PinionStaticData.angleSai + reqAngle / 2);
        }

        //System.out.println("AngleSai[" + angleSai + ":" + Math.toDegrees(angleSai) + "]\t vectorialAngle:[" + (vectorialAngle) + ":" + Math.toDegrees(vectorialAngle) + "]\tvarRad:" + varRadius + "\tX:" + x + "\tY:" + y + "\tZ:" + Zdist);

        return new Double[]{x, y, Zdist, vectorialAngle + PinionStaticData.angleSai + reqAngle / 2};
    }

    
    /**
     * One Point on Involute Curve,
     * @param varRadius
     * @param Zdist
     * @param Mirror
     * @return
     */
    public Double[] involuteXYZ_Mirror(double varRadius/**
             * For Involute
             */
            , double Zdist) {


        //Get the Radius for Calulation of (X,Y) points on involute
        //baseCircleRadius= pitchCircleRadius_NP*Math.cos(Math.toRadians(transversePressureAngle));////
        double phiAngle = Math.acos(PinionStaticData.baseCircleRadius_TP / varRadius);
        double involuteFuncConstant = Math.tan(Math.toRadians(PinionStaticData.transversePressureAngle)) - Math.toRadians(PinionStaticData.transversePressureAngle);
        double involuteFuncConstant2 = Math.tan(phiAngle) - phiAngle;
        //Halftooth at R1
        //double HalfToothAtR = 2 * radiusAtInvolute * (ToothWidthAtPitchCircle_NP / (2 * pitchCircleRadius_NP)) + involuteFuncConstant - involuteFuncConstant2;
        /**
         * Below line is commented to test the tooth width data at base circle
         * on 25th July 2011
         */
        //double vectorialAngle = (Math.PI / numberOfTeeth) - ((ToothWidthAtPitchCircle_NP / (2 * pitchCircleRadius_NP)) + involuteFuncConstant2 - involuteFuncConstant);
        //(Math.PI / numberOfTeeth) - 
        double vectorialAngle = (Math.PI / PinionStaticData.numberOfTeeth) - ((PinionStaticData.ToothChordAtBaseCircle_TP / (2 * PinionStaticData.baseCircleRadius_TP)) + involuteFuncConstant2 - involuteFuncConstant);
        double x;
        double y;

        //+ToothProfileCorrectionAngle_TP- Math.toRadians( BC_HalfToothAngle_TP)
        double reqAngle = 2 * Math.toRadians(PinionStaticData.BC_HalfToothAngle_TP) - PinionStaticData.ToothProfileCorrectionAngle_TP;
        x = varRadius * Math.sin(-vectorialAngle + PinionStaticData.angleSai - reqAngle / 2);
        y = varRadius * Math.cos(-vectorialAngle + PinionStaticData.angleSai - reqAngle / 2);


        //System.out.println("AngleSai[" + angleSai + ":" + Math.toDegrees(angleSai) + "]\t vectorialAngle:[" + (vectorialAngle) + ":" + Math.toDegrees(vectorialAngle) + "]\tvarRad:" + varRadius + "\tX:" + x + "\tY:" + y + "\tZ:" + Zdist);

        return new Double[]{x, y, Zdist, -vectorialAngle + PinionStaticData.angleSai - reqAngle / 2};
    }
}
