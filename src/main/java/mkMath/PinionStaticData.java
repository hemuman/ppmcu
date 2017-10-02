package mkMath;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author PDI
 */
public class PinionStaticData {
    public static double normalPressureAngle;
    public static double transversePressureAngle;//at pitch point
    public static double circularModule;
    public static double normalModule;
    public static double refrencePitch_NP;
    public static double basePitch_NP;//refrencePitch*cos(pressureAngle)
    public static double refrencePitch_TP;
    public static double basePitch_TP;//refrencePitch*cos(transversepressureAngle)
    public static double BaseDiametralPitch_NP;//Number of teeth/Pitch Diameter
    public static double BaseDiametralPitch_TP;
    public static double PCDDiametralPitch_NP;//Number of teeth/Pitch Diameter
    public static double PCDDiametralPitch_TP;
    public static double AngularPitch_TP;
    public static double TipAngle_TP;
    public static double TipCircleRadius_TP;
    public static double CylsurfAngle_TP;
    public static double pitchCircleRadius_NP;
    public static double pitchCircleRadius_TP;
    public static double rootCircleRadius;
    public static double ToothWidthAtPitchCircle_NP;
    public static double ToothWidthAtPitchCircle_TP;
    public static double ToothWidthAtBaseCircle_NP;
    public static double ToothWidthAtBaseCircle_TP;
    public static double ToothChordAtBaseCircle_NP;
    public static double ToothChordAtBaseCircle_TP;
    public static double ToothWidthAtTipCircle_NP;
    public static double ToothWidthAtTipCircle_TP;
    public static double ToothChordAtTipCircle_NP;
    public static double ToothChordAtTipCircle_TP;
    public static double ToothProfileCorrectionAngle_TP;
    public static double numberOfTeeth;
    public static double baseCircleRadius_NP;
    public static double baseCircleRadius_TP;
    public static double angleSai;
    public static double angleSaiIncrement;
    public static double HelixAngle;
    public static double HelixLead;
    public static double BC_HalfToothAngle_TP;
    public static double BC_HalfToothAngle_NP;
    public static double ToothSections;
    public static double PinionLength;
    public static double PinionSectionAccuracy = 1;//mm
    public static double PinionInvAccuracy = 0.1;//mm
    public static double PinionAngleAccuracy = 1;//deg
    
      public double involute(double angle, boolean AngleInRadian) {
        if (AngleInRadian) //if angle is expressed in Radians then do following
        {
            return Math.tan(angle) - Math.toRadians(angle);
        }

        //Convert angle to Radians if expressed in Degrees
        return Math.tan(Math.toRadians(angle)) - Math.toRadians(angle);
    }

       public double getProfileCorrectionAngle(double BaseCircleRadius) {

        double[] point1 = involuteXYZInterSectPoint(BaseCircleRadius, 0, false);
        double[] point2 = involuteXYZInterSectPoint(BaseCircleRadius, 0, true);

        return getAngleBetween2Points(point1[0], point1[1], point2[0], point2[1], BaseCircleRadius);

    }

       
    public double getTipAngle(double TipCircleRadius) {

        double[] point1 = involuteXYZ(TipCircleRadius, 0, false);
        double[] point2 = involuteXYZ(TipCircleRadius, 0, true);

        return getAngleBetween2Points(point1[0], point1[1], point2[0], point2[1], TipCircleRadius);

    }
     public double[] involuteXYZInterSectPoint(double varRadius/**
             * For Involute
             */
            , double Zdist, boolean Mirror) {

        double mirror = 1;
        if (Mirror) {
            mirror = -1;
        }
        //Get the Radius for Calulation of (X,Y) points on involute
        //baseCircleRadius= pitchCircleRadius_NP*Math.cos(Math.toRadians(transversePressureAngle));////
        double phiAngle = Math.acos(baseCircleRadius_TP / varRadius);
        double involuteFuncConstant = Math.tan(Math.toRadians(transversePressureAngle)) - Math.toRadians(transversePressureAngle);
        double involuteFuncConstant2 = Math.tan(phiAngle) - phiAngle;
        //Halftooth at R1
        //double HalfToothAtR = 2 * radiusAtInvolute * (ToothWidthAtPitchCircle_NP / (2 * pitchCircleRadius_NP)) + involuteFuncConstant - involuteFuncConstant2;
        /**
         * Below line is commented to test the tooth width data at base circle
         * on 25th July 2011
         */
        //double vectorialAngle = (Math.PI / numberOfTeeth) - ((ToothWidthAtPitchCircle_NP / (2 * pitchCircleRadius_NP)) + involuteFuncConstant2 - involuteFuncConstant);
        double vectorialAngle = (Math.PI / numberOfTeeth) - ((ToothChordAtBaseCircle_TP / (2 * baseCircleRadius_TP)) + involuteFuncConstant2 - involuteFuncConstant);
        double x;
        double y;
        if (Mirror) {
            return involuteXYZInterSectPoint_Mirror(varRadius, Zdist);
        } else {
            //angleSai=angleSai-angleSaiIncrement*mirror;
            x = varRadius * Math.sin(vectorialAngle + angleSai);
            y = varRadius * Math.cos(vectorialAngle + angleSai);
        }

        //System.out.println("AngleSai[" + angleSai + ":" + Math.toDegrees(angleSai) + "]\t vectorialAngle:[" + (vectorialAngle) + ":" + Math.toDegrees(vectorialAngle) + "]\tvarRad:" + varRadius + "\tX:" + x + "\tY:" + y + "\tZ:" + Zdist);

        return new double[]{x, y, Zdist, vectorialAngle + angleSai};
    }

 public double[] involuteXYZ(double varRadius/**
             * For Involute
             */
            , double Zdist, boolean Mirror) {

        double mirror = 1;
        if (Mirror) {
            mirror = -1;
        }
        //Get the Radius for Calulation of (X,Y) points on involute
        //baseCircleRadius= pitchCircleRadius_NP*Math.cos(Math.toRadians(transversePressureAngle));////
        double phiAngle = Math.acos(baseCircleRadius_TP / varRadius);
        double involuteFuncConstant = Math.tan(Math.toRadians(transversePressureAngle)) - Math.toRadians(transversePressureAngle);
        double involuteFuncConstant2 = Math.tan(phiAngle) - phiAngle;
        //Halftooth at R1
        //double HalfToothAtR = 2 * radiusAtInvolute * (ToothWidthAtPitchCircle_NP / (2 * pitchCircleRadius_NP)) + involuteFuncConstant - involuteFuncConstant2;
        /**
         * Below line is commented to test the tooth width data at base circle
         * on 25th July 2011
         */
        //double vectorialAngle = (Math.PI / numberOfTeeth) - ((ToothWidthAtPitchCircle_NP / (2 * pitchCircleRadius_NP)) + involuteFuncConstant2 - involuteFuncConstant);
        //(Math.PI / numberOfTeeth) - 
        double vectorialAngle = (Math.PI / numberOfTeeth) - ((ToothChordAtBaseCircle_TP / (2 * baseCircleRadius_TP)) + involuteFuncConstant2 - involuteFuncConstant);
        double x;
        double y;
        double reqAngle = 2 * Math.toRadians(BC_HalfToothAngle_TP) - ToothProfileCorrectionAngle_TP;
        if (Mirror) {
            return involuteXYZ_Mirror(varRadius, Zdist);
        } else {
            //angleSai=angleSai-angleSaiIncrement*mirror;

            x = varRadius * Math.sin(vectorialAngle + angleSai + reqAngle / 2);
            y = varRadius * Math.cos(vectorialAngle + angleSai + reqAngle / 2);
        }

        //System.out.println("AngleSai[" + angleSai + ":" + Math.toDegrees(angleSai) + "]\t vectorialAngle:[" + (vectorialAngle) + ":" + Math.toDegrees(vectorialAngle) + "]\tvarRad:" + varRadius + "\tX:" + x + "\tY:" + y + "\tZ:" + Zdist);

        return new double[]{x, y, Zdist, vectorialAngle + angleSai + reqAngle / 2};
    }

 
    /**
     * One Point on Involute Curve,
     * @param varRadius
     * @param Zdist
     * @param Mirror
     * @return
     */
    public double[] involuteXYZ_Mirror(double varRadius/**
             * For Involute
             */
            , double Zdist) {


        //Get the Radius for Calulation of (X,Y) points on involute
        //baseCircleRadius= pitchCircleRadius_NP*Math.cos(Math.toRadians(transversePressureAngle));////
        double phiAngle = Math.acos(baseCircleRadius_TP / varRadius);
        double involuteFuncConstant = Math.tan(Math.toRadians(transversePressureAngle)) - Math.toRadians(transversePressureAngle);
        double involuteFuncConstant2 = Math.tan(phiAngle) - phiAngle;
        //Halftooth at R1
        //double HalfToothAtR = 2 * radiusAtInvolute * (ToothWidthAtPitchCircle_NP / (2 * pitchCircleRadius_NP)) + involuteFuncConstant - involuteFuncConstant2;
        /**
         * Below line is commented to test the tooth width data at base circle
         * on 25th July 2011
         */
        //double vectorialAngle = (Math.PI / numberOfTeeth) - ((ToothWidthAtPitchCircle_NP / (2 * pitchCircleRadius_NP)) + involuteFuncConstant2 - involuteFuncConstant);
        //(Math.PI / numberOfTeeth) - 
        double vectorialAngle = (Math.PI / numberOfTeeth) - ((ToothChordAtBaseCircle_TP / (2 * baseCircleRadius_TP)) + involuteFuncConstant2 - involuteFuncConstant);
        double x;
        double y;

        //+ToothProfileCorrectionAngle_TP- Math.toRadians( BC_HalfToothAngle_TP)
        double reqAngle = 2 * Math.toRadians(BC_HalfToothAngle_TP) - ToothProfileCorrectionAngle_TP;
        x = varRadius * Math.sin(-vectorialAngle + angleSai - reqAngle / 2);
        y = varRadius * Math.cos(-vectorialAngle + angleSai - reqAngle / 2);


        //System.out.println("AngleSai[" + angleSai + ":" + Math.toDegrees(angleSai) + "]\t vectorialAngle:[" + (vectorialAngle) + ":" + Math.toDegrees(vectorialAngle) + "]\tvarRad:" + varRadius + "\tX:" + x + "\tY:" + y + "\tZ:" + Zdist);

        return new double[]{x, y, Zdist, -vectorialAngle + angleSai - reqAngle / 2};
    }

    public double[] involuteXYZInterSectPoint_Mirror(double varRadius/**
             * For Involute
             */
            , double Zdist) {


        //Get the Radius for Calulation of (X,Y) points on involute
        //baseCircleRadius= pitchCircleRadius_NP*Math.cos(Math.toRadians(transversePressureAngle));////
        double phiAngle = Math.acos(baseCircleRadius_TP / varRadius);
        double involuteFuncConstant = Math.tan(Math.toRadians(transversePressureAngle)) - Math.toRadians(transversePressureAngle);
        double involuteFuncConstant2 = Math.tan(phiAngle) - phiAngle;
        //Halftooth at R1
        //double HalfToothAtR = 2 * radiusAtInvolute * (ToothWidthAtPitchCircle_NP / (2 * pitchCircleRadius_NP)) + involuteFuncConstant - involuteFuncConstant2;
        /**
         * Below line is commented to test the tooth width data at base circle
         * on 25th July 2011
         */
        //double vectorialAngle = (Math.PI / numberOfTeeth) - ((ToothWidthAtPitchCircle_NP / (2 * pitchCircleRadius_NP)) + involuteFuncConstant2 - involuteFuncConstant);
        double vectorialAngle = (Math.PI / numberOfTeeth) - ((ToothChordAtBaseCircle_TP / (2 * baseCircleRadius_TP)) + involuteFuncConstant2 - involuteFuncConstant);
        double x;
        double y;


        x = varRadius * Math.sin(-vectorialAngle + angleSai);
        y = varRadius * Math.cos(-vectorialAngle + angleSai);


        //System.out.println("AngleSai[" + angleSai + ":" + Math.toDegrees(angleSai) + "]\t vectorialAngle:[" + (vectorialAngle) + ":" + Math.toDegrees(vectorialAngle) + "]\tvarRad:" + varRadius + "\tX:" + x + "\tY:" + y + "\tZ:" + Zdist);

        return new double[]{x, y, Zdist, vectorialAngle + angleSai - Math.toRadians(2 * BC_HalfToothAngle_TP)};
    }
public double getAngleBetween2Points(double x1, double y1, double x2, double y2, double Radius) {
        double dist = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        //Theta=2*sin-1(d/2*R)
        System.out.println("Angle between points" + 2 * Math.asin(dist / (2 * Radius)) + "Rad \tDeg:" + Math.toDegrees(2 * Math.asin(dist / (2 * Radius))));
        return 2 * Math.asin(dist / (2 * Radius));
    }

}
