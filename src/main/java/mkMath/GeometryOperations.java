package mkMath;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import JAMA.Matrix;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import javax.swing.JDialog;
import javax.swing.JLabel;
import mkMath.TransformationMatrix;
import mkMath.grahamScan_;
//import mkprgbrs.Wavesprgbar;
import net.mk.FJTasks.ExtremeXYFJTask;
import net.mk.FJTasks.QuickSortFJTask;

/**
 *
 * @author PDI
 */
public class GeometryOperations {

    /**
     *
     * @param angle
     * @param amplitude
     * @return
     */
    public double[] sinXYZ(double angle, double amplitude) {

        return new double[]{angle, amplitude * Math.sin(Math.toRadians(angle))};
    }

    /**
     * Converts the array of double/numbers to String array
     *
     * @param doubleData
     * @return
     */
    public String[][] getStringsFor(double[][] doubleData, boolean print) {
        if (doubleData.length > 0) {
            String[][] result = new String[doubleData.length][doubleData[0].length];
            //System.out.println("Array Size for String[" + doubleData.length + "][" + (doubleData[0].length)+"]");
            //System.out.println("Array Size for String[" + result.length + "][" + (result[0].length)+"]");
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < 3; j++) {///For XYZ
                    //System.out.println(doubleData[i][j]);
                    result[i][j] = String.valueOf(doubleData[i][j]);
                    //System.out.println(result[i][j]);
                    if (print) {
                        System.out.print(result[i][j] + "\t");
                    }
                }
                if (print) {
                    System.out.println("\n");
                }
            }
            return result;
        }
        return null;
    }

    /**
     * Converts the data in string to double, will be used for sorting to save memory
     *
     * @param doubleStringData
     * @return
     */
    public double[][] getDoublesFor(String[][] doubleStringData) {
        if (doubleStringData.length > 0) {
            double[][] result = new double[doubleStringData.length][doubleStringData[0].length];
            //System.out.println("Array Size for String[" + doubleStringData.length + "][" + (doubleStringData[0].length)+"]");
            //System.out.println("Array Size for String[" + result.length + "][" + (result[0].length)+"]");
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < 3; j++) {///For XYZ
                    //System.out.println(doubleStringData[i][j]);
                    if (doubleStringData[i][j] != null) {
                        result[i][j] = Double.parseDouble(doubleStringData[i][j]);
                    }
                    //System.out.println(result[i][j]);
                }
            }
            return result;
        }
        return null;
    }

    public String getStrings(double[][] doubleData) {
        if (doubleData.length > 0) {
            String result = "";
            //System.out.println("Array Size for String[" + doubleData.length + "][" + (doubleData[0].length)+"]");
            //System.out.println("Array Size for String[" + result.length + "][" + (result[0].length)+"]");
            for (int i = 0; i < doubleData.length; i++) {
                for (int j = 0; j < 3; j++) {///For XYZ
                    //System.out.println(doubleData[i][j]);
                    result = result + "\t" + String.valueOf(doubleData[i][j]);
                    //System.out.println(result[i][j]);
                }
                result = result + "\n";
            }
            return result;
        }
        return null;
    }

    /**
     *
     * @param doubleData
     * @return
     */
    public String[][][] getStringsFor(double[][][] doubleData) {
        String[][][] result = new String[doubleData.length][doubleData[0].length][doubleData[0][0].length];

        for (int i = 0; i < doubleData.length; i++) {
            for (int j = 0; j < doubleData[0].length; j++) {
                for (int k = 0; k < doubleData[0][0].length; k++) {
                    result[i][j][k] = String.valueOf(doubleData[i][j][k]);
                    System.out.println(result[i][j][k]);
                }
            }
        }
        return result;
    }

    /**
     *
     * @param XYZCloud
     * @param toXYZ_alpha_beta_gama
     * @return
     */
    public double[][][] Transform(double[][][] XYZCloud, double toXYZ_alpha_beta_gama[][]) {
        /**
         * double[][] array = {{1.,2.,3},{4.,5.,6.},{7.,8.,10.}}; Matrix A = new Matrix(array); Matrix b = Matrix.random(3,1); Matrix x = A.solve(b); Matrix
         * Residual = A.times(x).minus(b); double rnorm = Residual.normInf();
         *
         */
        TransformationMatrix element1 = new TransformationMatrix();
        element1.xx = Math.cos(Math.toRadians(toXYZ_alpha_beta_gama[1][2]));
        element1.xy = Math.sin(Math.toRadians(toXYZ_alpha_beta_gama[1][2]));
        element1.xz = 0.0;
        element1.yx = -Math.sin(Math.toRadians(toXYZ_alpha_beta_gama[1][2]));
        element1.yy = Math.cos(Math.toRadians(toXYZ_alpha_beta_gama[1][2]));
        element1.yz = 0.0;
        element1.zx = 0.0;
        element1.zy = 0.0;
        element1.zz = 1.0;

        double[][] array = {{toXYZ_alpha_beta_gama[0][1], toXYZ_alpha_beta_gama[0][1], toXYZ_alpha_beta_gama[0][2]}};
        Matrix T3x3 = new Matrix(element1.getMatrix());
        Matrix T3x1 = new Matrix(toXYZ_alpha_beta_gama);

        //TODO Implementation
        return null;
    }

    public double[][][] CylinderSurface(double zStart, double zEnd, double zIncrement, double radius, double startXYZ[][][], double SwipeAngle, double increment, boolean atCurveEnd) {
        int loopCount = (int) (Math.abs(zEnd - zStart) / zIncrement) + 1;
        int loopCount2 = (int) Math.abs(SwipeAngle / increment);
        //JOptionPane.showConfirmDialog(null, loopCount + " Sections and Points on Circle" + loopCount2);
        double[][][] result = new double[loopCount][loopCount2][3];

        for (int i = 0; i < loopCount; i++) {
            if (!atCurveEnd) {
                result[i] = Circle2XYZ(radius, startXYZ[i][0], SwipeAngle, increment);
            } else {
                result[i] = Circle2XYZ(radius, startXYZ[i][loopCount2 - 1], SwipeAngle, increment);
            }
        }




        return result;
    }

    public double[][][] CylinderSurface2(double zStart, double zEnd, double zIncrement, double radius, double startXYZ[][][], double SwipeAngle, double increment, boolean atCurveEnd) {

        int loopCount = (int) (Math.abs(zEnd - zStart) / zIncrement) + 1;

        int loopCount2 = (int) Math.abs(SwipeAngle / increment);
        //JOptionPane.showConfirmDialog(null, loopCount + " Sections and Points on Circle" + loopCount2);
        double[][][] result = new double[loopCount][loopCount2][3];
        //JOptionPane.showConfirmDialog(null, (startXYZ[0].length - 1));
        for (int i = 0; i < loopCount; i++) {
            if (!atCurveEnd) {
                result[i] = Circle2XYZ(radius, startXYZ[i][startXYZ[i].length - 1], SwipeAngle, increment);
            } else {
                result[i] = Circle2XYZ(radius, startXYZ[i][startXYZ[i].length - 1], SwipeAngle, increment);
            }
        }

        return result;
    }

    /**
     *
     * @param radius
     * @param startXYZ
     * @param SwipeAngle
     * @param increment
     * @return
     */
    public double[][] CircleXYZ(double radius, double startXYZt[], double SwipeAngle, double increment) {

        double theta1 = Math.asin(startXYZt[0] / radius);
        int loopCount = (int) Math.abs(SwipeAngle / increment);
        double result[][] = new double[loopCount][3];
        //JOptionPane.showConfirmDialog(null, loopCount);
        for (int i = 0; i < loopCount; i++) {
            result[i][0] = radius * Math.sin(startXYZt[3] + increment * i);
            result[i][1] = radius * Math.cos(startXYZt[3] + increment * i);
            result[i][2] = startXYZt[2];
            //System.out.println("Cylinder Surface" + result[i][0] + ":" + result[i][1] + ":" + result[i][2] + ":" + startXYZt[3] + increment * i);
        }

        return result;
    }

    /**
     *
     * @param radius
     * @param startXYZt
     * @param SwipeAngle
     * @param increment
     * @return
     */
    public double[][] Circle2XYZ(double radius, double startXYZt[], double SwipeAngle, double increment) {
        double theta1 = Math.asin(startXYZt[0] / radius);
        int loopCount = (int) Math.abs(SwipeAngle / increment);//Check this for method
        double remainingAngle = 0;
        double result[][] = new double[loopCount][4];
        boolean processLastPoint = false;
        if ((loopCount * increment) > SwipeAngle) {
            loopCount = loopCount - 1;//Reduce one count
            remainingAngle = SwipeAngle - loopCount * increment;
            result = new double[loopCount + 1][4];
            processLastPoint = true;
            //System.out.println("One Point Extra");
        } else if ((loopCount * increment) < SwipeAngle) {
            remainingAngle = SwipeAngle - loopCount * increment;
            result = new double[loopCount + 1][4];
            processLastPoint = true;
            //System.out.println("One Point less");
        } else {
            result = new double[loopCount][4];
            processLastPoint = false;
            //System.out.println("Equal Number");
        }


        //JOptionPane.showConfirmDialog(null, loopCount);
        //JOptionPane.showConfirmDialog(null, startXYZt[3]+"Angle Start"+Math.toDegrees(startXYZt[3]));
        for (int i = 0; i < loopCount; i++) {
            result[i][0] = radius * Math.sin(startXYZt[3] + increment * i);
            result[i][1] = radius * Math.cos(startXYZt[3] + increment * i);
            result[i][2] = startXYZt[2];
            result[i][3] = startXYZt[3] + increment * i;
            //System.out.println("Cylinder Surface" + result[i][0] + ":" + result[i][1] + ":" + result[i][2] + ":Angle:" + startXYZt[3] + increment * i);
        }
        /*
         * Process the remaining length
         */

        if (processLastPoint) {
            result[loopCount][0] = radius * Math.sin(startXYZt[3] + SwipeAngle * Math.signum(increment));
            result[loopCount][1] = radius * Math.cos(startXYZt[3] + SwipeAngle * Math.signum(increment));
            result[loopCount][2] = startXYZt[2];
            result[loopCount][3] = startXYZt[3] + SwipeAngle * Math.signum(increment);
            //System.out.println("Processed Last Point");
            //System.out.println("Cylinder Surface" + result[loopCount][0] + ":" + result[loopCount][1] + ":" + result[loopCount][2] + ":Angle:" + startXYZt[3] + SwipeAngle * Math.signum(increment));
        }


        return result;
    }

    /**
     * Considering center at (0,0)
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public double getAngleBetween2Points(double x1, double y1, double x2, double y2, double Radius) {
        double dist = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        //Theta=2*sin-1(d/2*R)
        System.out.println("Angle between points" + 2 * Math.asin(dist / (2 * Radius)) + "Rad \tDeg:" + Math.toDegrees(2 * Math.asin(dist / (2 * Radius))));
        return 2 * Math.asin(dist / (2 * Radius));
    }

    /**
     * Joins the Curves in section i.e. returned array will have [fix][change][fix] dimension
     *
     * @param Curve3D1
     * @param Curve3D2
     * @return
     */
    public double[][][] JoinSectionCurves(double[][][] Curve3D1, double[][][] Curve3D2) {
        double[][][] result = new double[Curve3D1.length][Curve3D1[0].length + Curve3D2[0].length][Curve3D2[0][0].length];
        if (Curve3D1.length != Curve3D2.length) {
            System.out.println("Can not join because: Number of Sections are not equal");
        } else {
            // System.out.println("Array 1 Size[" + Curve3D1.length + "][" + (Curve3D1[0].length) + "][" + Curve3D1[0][0].length + "]");
            //System.out.println("Array 2 Size[" + Curve3D2.length + "][" + (Curve3D2[0].length) + "][" + Curve3D2[0][0].length + "]");
            //System.out.println("New Size[" + Curve3D1.length + "][" + (Curve3D1[0].length + Curve3D2[0].length) + "][" + Curve3D2[0][0].length + "]");
            for (int i = 0; i < result.length; i++) {
                /**
                 * First Curve
                 */
                for (int jlc = 0; jlc < Curve3D1[0].length; jlc++) {
                    result[i][jlc] = Curve3D1[i][jlc];
                }
                /**
                 * Second Curve
                 */
                for (int jlc = 0; jlc < Curve3D2[0].length; jlc++) {
                    result[i][Curve3D1[0].length + jlc] = Curve3D2[i][jlc];
                    //System.out.println("XYZ:" + (Curve3D1[0].length + jlc) + ">" + result[i][Curve3D1[0].length + jlc][0] + ":" + result[i][Curve3D1[0].length + jlc][1] + ":" + result[i][Curve3D1[0].length + jlc][2]);
                }


            }
        }

        return result;

    }

    /**
     * Joins the Curves in section i.e. returned array will have [change][fix] dimension
     *
     * @param Curve3D1
     * @param Curve3D2
     * @return
     */
    public double[][] JoinSectionCurves(double[][] Curve3D1, double[][] Curve3D2) {
        if (Curve3D1 == null) {
            if (Curve3D2 != null) {
                return Curve3D2;
            } else {
                return null;
            }
        }
        if (Curve3D2 == null) {
            if (Curve3D1 != null) {
                return Curve3D1;
            } else {
                return null;
            }
        }
        double[][] result = new double[Curve3D1.length + Curve3D2.length][Curve3D2[0].length];
        if (Curve3D2.length < 0) {
            System.out.println("Can not join because: Number of Sections are not equal");
            return Curve3D1;
        }
        if (Curve3D1.length < 0) {
            System.out.println("Can not join because: Number of Sections are not equal");
            return Curve3D2;
        } else {
            //System.out.println("Array 1 Size[" + Curve3D1.length + "][" + (Curve3D1[0].length) + "]");
            //System.out.println("Array 2 Size[" + Curve3D2.length + "][" + (Curve3D2[0].length) + "]");
            //System.out.println("New Size[" + Curve3D1.length + "][" + (Curve3D1.length + Curve3D2.length) + "]");

            System.arraycopy(Curve3D1, 0, result, 0, Curve3D1.length);
            System.arraycopy(Curve3D2, 0, result, Curve3D1.length, Curve3D2.length);

        }

        return result;

    }

    /**
     * Reverse the Curve direction in each curve
     *
     * @param Sections
     * @return
     */
    public double[][][] reverseSectionsCurve(double[][][] Sections) {
        //new ArrayUtils.reverse(int[] array);
        for (int i = 0; i < Sections.length; i++) {
            for (int j = 0; j < Sections[0].length / 2; j++) {
                double[] temp = Sections[i][j];
                Sections[i][j] = Sections[i][Sections[0].length - j - 1];
                Sections[i][Sections[0].length - j - 1] = temp;
            }
        }

        return Sections;

    }

    /**
     * Reverse the direction of a curve
     *
     * @param Sections
     * @return
     */
    public double[][] reverseCurve(double[][] Sections) {
        if (Sections != null) {
            for (int j = 0; j < Sections.length / 2; j++) {
                double[] temp = Sections[j];
                Sections[j] = Sections[Sections.length - j - 1];
                Sections[Sections.length - j - 1] = temp;
            }
        }

        return Sections;

    }

    /**
     *
     * @param CurveData
     * @return
     */
    public double[][] removeConsecutiveDuplicateXYZs(double[][] CurveData) {
        Vector result_ = new Vector();

        for (int i = 0; i < CurveData.length - 1; i++) {
            if ((CurveData[i][0] == CurveData[i + 1][0]) & (CurveData[i][1] == CurveData[i + 1][1]) & (CurveData[i][2] == CurveData[i + 1][2])) {
                //System.out.println("Found duplicate @" + i);
                //System.out.println("XYZ:" + CurveData[i][0] + ":" + CurveData[i][1] + ":" + CurveData[i][2]);
                continue;
            } else {
                result_.add(CurveData[i]);
            }
        }
        double[][] result = new double[result_.size()][CurveData[0].length];
        for (int i = 0; i < result_.size(); i++) {
            result[i] = (double[]) result_.get(i);
        }
        return result;
    }

    /**
     * Assuming each section has same number of points
     *
     * @param CurveData
     * @return
     */
    public double[][][] removeConsecutiveDuplicateXYZs(double[][][] CurveData) {
        int newSize = removeConsecutiveDuplicateXYZs(CurveData[0]).length;
        double[][][] result = new double[CurveData.length][newSize][CurveData[0][0].length];
        for (int i = 0; i < CurveData.length; i++) {
            result[i] = removeConsecutiveDuplicateXYZs(CurveData[i]);
        }

        return result;
    }

    /**
     * Get XYZ points for the line passing from all sections
     *
     * @param Sections
     * @param index
     * @return
     */
    public double[][] getGuideCurve(double[][][] Sections, int index) {

        if ((index > Sections[0].length) | (index < 0)) {
            return null;
        }

        double[][] result = new double[Sections.length][Sections[0][0].length];

        for (int i = 0; i < Sections.length; i++) {
            result[i] = Sections[i][index];
        }
        return result;
    }

    public double[][] getGuideCurve(Vector Sections, int index) {


        if ((index > ((double[][]) Sections.get(0)).length) | (index < 0)) {
            return null;
        }

        double[][] result = new double[Sections.size()][((double[][]) Sections.get(0))[0].length];

        for (int i = 0; i < Sections.size(); i++) {
            result[i] = ((double[][]) Sections.get(i))[index];
        }
        return result;
    }

    public double[][] getRackIntersectionPoints(double[][][] Pinionpoints, double bb_corner1[], double[] bb_corner2) {

        Vector Points_3D = new Vector();
        for (int i = 0; i < Pinionpoints.length; i++) {
            for (int j = 0; j < Pinionpoints[i].length; j++) {
                //System.out.println("Test Points in Rack: XYZ:\t" + Pinionpoints[i][j][0] + "\t" + Pinionpoints[i][j][1] + "\t" + Pinionpoints[i][j][2]);

                if (new StraightLine().pointInBoundingBox(bb_corner1, bb_corner2, Pinionpoints[i][j])) {
                    Points_3D.add(Pinionpoints[i][j]);
                    //System.out.println("Points in Rack: XYZ:\t" + Pinionpoints[i][j][0] + "\t" + Pinionpoints[i][j][1] + "\t" + Pinionpoints[i][j][2]);
                }
            }
        }

        if (Points_3D.size() == 0) {
            return null;
        }

        double[][] result = new double[Points_3D.size()][4];
        /**
         * Convert to the Array
         */
        for (int i = 0; i < Points_3D.size(); i++) {
            result[i] = (double[]) Points_3D.get(i);
        }
        System.out.println("Total Points in Rack: " + Points_3D.size() + "\t Out of: " + (Pinionpoints.length * Pinionpoints[0].length));


        return result;
    }

    public double[][] getRackIntersectionPoints(double[][] Pinionpoints, double bb_corner1[], double[] bb_corner2, boolean message) {

        Vector Points_3D = new Vector();

        for (int j = 0; j < Pinionpoints.length; j++) {
            //System.out.println("Test Points in Rack: XYZ:\t" + Pinionpoints[i][j][0] + "\t" + Pinionpoints[i][j][1] + "\t" + Pinionpoints[i][j][2]);

            if (new StraightLine().pointInBoundingBox(bb_corner1, bb_corner2, Pinionpoints[j])) {
                Points_3D.add(Pinionpoints[j]);
                //System.out.println("Points in Rack: XYZ:\t" + Pinionpoints[i][j][0] + "\t" + Pinionpoints[i][j][1] + "\t" + Pinionpoints[i][j][2]);
            }
        }

        if (Points_3D.size() == 0) {
            return null;
        }

        double[][] result = new double[Points_3D.size()][4];
        /**
         * Convert to the Array
         */
        for (int i = 0; i < Points_3D.size(); i++) {
            result[i] = (double[]) Points_3D.get(i);
        }
        if (message) {
            System.out.println("Total Points in Rack: " + Points_3D.size() + "\t Out of: " + (Pinionpoints.length));
        }


        return result;
    }

    /**
     * Get the points lying on given region
     *
     * @param Pinionpoints
     * @param bb_corner1
     * @param bb_corner2
     * @return
     */
    public static double[][] getBoundedPoints(double[][] Pinionpoints, double bb_corner1[], double[] bb_corner2) {

        Vector Points_3D = new Vector();
        for (int i = 0; i < Pinionpoints.length; i++) {

            if (new StraightLine().pointInBoundingBox(bb_corner1, bb_corner2, Pinionpoints[i])) {
                Points_3D.add(Pinionpoints[i]);
                //System.out.println("Points in Rack: XYZ:\t" + Pinionpoints[i][0] + "\t" + Pinionpoints[i][1] + "\t" + Pinionpoints[i][2]);
            } else {
                //System.out.println("Points in Rack: XYZ:\t" + Pinionpoints[i][0] + "\t" + Pinionpoints[i][1] + "\t" + Pinionpoints[i][2]);
            }
        }

        if (Points_3D.size() == 0) {
            System.out.println("No Points in this region:[x1,y1]: " + bb_corner1[0] + "\t" + bb_corner1[1] + "\t [x2,y2]" + bb_corner2[0] + "\t" + bb_corner2[1]);
            return null;
        }

        double[][] result = new double[Points_3D.size()][4];
        /**
         * Convert to the Array
         */
        for (int i = 0; i < Points_3D.size(); i++) {
            result[i] = (double[]) Points_3D.get(i);
        }
        System.out.println("Total Points in Region: " + Points_3D.size() + "\t Out of: " + (Pinionpoints.length * Pinionpoints[0].length));


        return result;
    }

    public static double[][] getBoundedPoints(double[][] Pinionpoints, Set<double[][]> BoundingBoxes) {
        double[][] Points_3D = new double[1][3];
        int pointCount = 0;
        for (double[][] bbx : BoundingBoxes) {
            double tempResult[][] = getBoundedPoints(Pinionpoints, bbx[0], bbx[1]);
            if (tempResult != null) {
                System.out.println("#getBoundedPoints::" + tempResult.length);
                for (int i = 0; i < tempResult.length; i++) {
                    if (Points_3D.length == pointCount) {
                        // expand list
                        Points_3D = Arrays.copyOf(Points_3D, Points_3D.length + 1);
                    }
                    Points_3D[pointCount] = tempResult[i];
                    pointCount++;
                }
            }
        }
        return Points_3D;
    }
    
    public double getArea(double[] point1, double point2[]) {

        return Math.abs((point1[0] - point2[0]) * (point1[1] - point2[1]));
    }

    /**
     * Transforms the array to XYZ
     *
     * @param PointsIn3D
     * @param TransformXYZ
     * @return
     */
    public double[][][] getTransform(double[][][] PointsIn3D, double[] TransformXYZ) {
        double[][][] result = PointsIn3D;
        for (int i = 0; i < PointsIn3D.length; i++) {
            for (int j = 0; j < PointsIn3D[i].length; j++) {
                //System.out.print("_ " + i + "," + j);
                result[i][j][0] = PointsIn3D[i][j][0] + TransformXYZ[0];
                result[i][j][1] = PointsIn3D[i][j][1] + TransformXYZ[1];
                result[i][j][2] = PointsIn3D[i][j][2] + TransformXYZ[2];

            }
        }
        //System.out.println("Total Points Transformed: " + result[0].length + "\t Out of: " + (PointsIn3D[0].length));

        return result;
    }

    public double[][] getTransform(double[][] PointsIn3D, double[] TransformXYZ) {
        double[][] result = PointsIn3D;
        if (PointsIn3D != null) {
            for (int j = 0; j < PointsIn3D.length; j++) {
                //System.out.print("_ " + i + "," + j);
                result[j][0] = PointsIn3D[j][0] + TransformXYZ[0];
                result[j][1] = PointsIn3D[j][1] + TransformXYZ[1];
                result[j][2] = PointsIn3D[j][2] + TransformXYZ[2];

            }
        }

        //System.out.println("Total Points Transformed: " + result[0].length + "\t Out of: " + (PointsIn3D[0].length));

        return result;
    }

    /**
     * Returns the given number of points
     *
     * @param PointsCluster
     * @param YAccuracy
     * @param YMax
     * @param Ymin
     * @param nPoints
     * @return
     */
    public Vector getNBoundaryPointsTBD(double[][] PointsCluster, double YAccuracy, double ScatterThreshold, double YMax, double Ymin, int nPoints) {
        Vector tempData = getBoundaryPoints(PointsCluster, YAccuracy, ScatterThreshold, YMax, Ymin, true);
        Vector result = new Vector();
        for (int pointsCount = 0; pointsCount < nPoints; pointsCount++) {
            result.add((double[][]) tempData.get(pointsCount * (tempData.size() / nPoints)));
        }
        //result.add(tempData.size());

        return result;
    }

    /**
     * [][] PointsCluster
     *
     * @param tempData
     * @param nPoints
     * @return
     */
    public Vector getNBoundaryPoints(double[][] BoundaryPoints, int nPoints,double YMax,double YMin) {
        Vector result = new Vector();
        double[][] newData = new double[nPoints][BoundaryPoints[0].length];
        newData[0] = BoundaryPoints[0];//First Point
        newData[0][1]=YMin;
        //ExtrapolateCurveByY(BoundaryPoints, YMin, BoundaryPoints.length - 2,0, false);
       // ExtrapolateCurveByY(BoundaryPoints, YMax, 3,BoundaryPoints.length - 2, false);
        
        newData[newData.length - 1]=BoundaryPoints[BoundaryPoints.length - 1];
        newData[newData.length - 1][1]=YMax;//ExtrapolateCurveByY(BoundaryPoints, YMax, 3,BoundaryPoints.length - 2, false);// BoundaryPoints[BoundaryPoints.length - 1];//Last Point
//        for (int pointsCount = 0; pointsCount < nPoints - 1; pointsCount++) {
//            newData[pointsCount] = BoundaryPoints[pointsCount * (BoundaryPoints.length / nPoints)];
//        }
//        result.add(newData);
        
        //Get Max y
        //Get Min y
        //Divide in n equal parts
        double MaxY=BoundaryPoints[BoundaryPoints.length - 1][1];
        double MinY=BoundaryPoints[0][1];
        double delta=Math.abs(MaxY-MinY)/nPoints;
        for (int pointsCount = 1; pointsCount < nPoints-1; pointsCount++) {
           newData[pointsCount][1] = delta*pointsCount;
           newData[pointsCount][0] = getXOnCurve(BoundaryPoints, delta*pointsCount);
           newData[pointsCount][2] = BoundaryPoints[0][2];//Constant Value
        
        }
        
        result.add(newData);
        return result;
    }

    /**
     *
     * @param CurveData
     * @param yi
     * @return
     */
    public double[][] getXOnCurve(double[][] CurveData, double[] yi) {
        double[][] xy = new double[yi.length][3];
        for (int i = 0; i < yi.length; i++) {
            xy[i][1] = yi[i];
            xy[i][0] = getXOnCurve(CurveData, yi[i]);
            xy[i][2] = CurveData[0][2];//Constant Value
            //System.out.println("X Y Z:" + xy[i][0] + "\t" + xy[i][1] + "\t" + xy[i][2]);
        }
        return xy;
    }

    public double[][] ExtrapolateXonCurve(double[][] xy) {
        //double[][] xyR=new double[xy.length][xy[0].length];
        /**
         * Check the X values for 0, i.e. when the data is not sufficient Apply the Extrapolation rule.
         */
        for (int i = 0; i < xy.length; i++) {

            if (xy[i][0] == 0) {
                xy[i][0] = ExtrapolateCurveByY(xy, xy[i][1], xy.length - 2, i, false);
            }
        }

        return xy;
    }

    /**
     *
     * @param CurveData
     * @param zi
     * @return xi value of the zi for given curve data of shell
     */
    public double getXOnCurve(double[][] CurveData, double yi) {
        //System.out.println("-------[Ladder Position Calculation]-------");
        double outerLengthCurve = 0;
        //double ladderLength = Double.parseDouble(LadderHeight.getText());
        double a = 0;
        double b = 0;
        double c = 0;
        double xi = 0;
        //double zi = Double.parseDouble(zStart.getText());//First Ladder Position
        StraightLine line1 = new StraightLine();
        StraightLine line2 = new StraightLine();
        EqnSolver eqs = new EqnSolver();
        boolean updateLine1 = true;
        boolean updateLine2 = true;
        // ladderData.setRowCount(0);
        /**
         * Add First Position
         */
        for (int i = 0; i < CurveData.length - 2; i++) {
            System.out.print("");
            double tempx = 0, tempz = 0;

            if (yi > CurveData[i + 1][ 1]) {
                //System.out.print("[EscLift" + i + " for Yi=" + yi + " Curvedata Y=" + CurveData[i + 1][ 1] + "]");
                continue;
            }
//& zi > Double.parseDouble(CurveData.getValueAt(i, 2).toString())
            if (updateLine1) {
                //System.out.print("#["+i+"\t Xi="+xi+" Zi="+zi);
                /**
                 * Step 1: Get Line Equation
                 */
                line1.setMXpC(CurveData[i][0],
                        CurveData[i][1],
                        CurveData[i + 1][0],
                        CurveData[i + 1][1], false);
                updateLine1 = false;
                /**
                 * Step 2: Find xi for given zi
                 */
                xi = line1.getX(yi);
                //System.out.println("#Finalized>>" + i + "\t [Xi=" + xi + ",Yi=" + yi + "]");

                updateLine2 = true;
                tempx = xi;
                tempz = yi;
                return xi;
            }
        }
        return 0;
    }

    /**
     * Added on Dec 31st 2012 to address issue of multiple point clouds in X Direction Boundary points based on X minimum and Maximum
     *
     * @param PointsCluster
     * @param YAccuracy
     * @return
     */
//    public Vector getBoundaryPoints(double[][] PointsCluster, double YAccuracy, double ScatterThresholdX, double ScatterThresholdZ, double YMax, double Ymin, boolean message) {
//
//        // Create a dialog that will display the progress.
//        final JDialog dlg = new JDialog(new Frame(), "#getBoundaryPoints", true);
//        Wavesprgbar dpb = new Wavesprgbar(0, (int) ((YMax - Ymin) / YAccuracy));
//
//        dlg.getContentPane().setLayout(new BorderLayout());
//        dlg.getContentPane().add(BorderLayout.CENTER, dpb);
//        dlg.getContentPane().add(BorderLayout.NORTH, new JLabel("Calculating..."));
//        dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
//        dlg.setSize(300, 100);
//        dlg.setLocation(0, 200);
//        // Create a new thread and call show within the thread.
//        Thread t = new Thread(new Runnable() {
//            public void run() {
//                dlg.show();
//            }
//        });
//
//        // Start the thread so that the dialog will show.
//        t.start();
//
//        Vector Points_2Dmax = new Vector();
//        Vector Points_2Dmin = new Vector();
//        Vector Points_Boundary = new Vector();
//
//        /**
//         * Separate the point cloud
//         */
//        /**
//         * Define regions based on YAccuracy Find Extreme Xs and Ys Going from Ystart to Yend, get Points with extreme Xs
//         */
//        //FilterCurvePoints(Points_2Dmin,ScatterThresholdZ);
//        double[][] bbx = getBoundingBox_bubbleSort(PointsCluster);
//        //double[][] bbx = getBoundingBox_QuickSort(PointsCluster);
//        System.out.print("Bbx Found.");
//        for (int regionCount = 1; regionCount < (YMax - Ymin) / YAccuracy; regionCount++) {
//
//            dpb.setValue(regionCount);
//
//            dlg.update(dlg.getGraphics());
//
//            double[][] tempCluster = getBoundedPoints(PointsCluster,
//                    new double[]{bbx[1][0], Ymin + YAccuracy * (regionCount - 1)},
//                    new double[]{bbx[0][0], Ymin + YAccuracy * regionCount});
//
//            //System.out.print(bbx[1][0]+"\t"+ (Ymin + YAccuracy * (regionCount - 1)));
//            //System.out.println("\t"+ bbx[0][0]+"\t"+ (Ymin + YAccuracy * (regionCount)));
//
//            if (tempCluster != null) {
//                /**
//                 * Descending order of sorting
//                 */
//                //bubble_srt(tempCluster, tempCluster[0].length, tempCluster.length, 0, false);
//                /*
//                 * Commented out to test other sorting algorithm
//                 */
//                Arrays.sort(tempCluster, new Comparator<double[]>() {
//                    @Override
//                    public int compare(final double[] entry1, final double[] entry2) {
//                        final double time1 = entry1[0];
//                        final double time2 = entry2[0];
//                        return time1 > time2 ? 0 : -1;
//                    }
//                });/*
//                 * /
//                 */
//
//                Points_2Dmin.add(tempCluster[tempCluster.length - 1]);//Minimum Point
//                Points_2Dmax.add(tempCluster[0]);//Maximum Point
//
//                /*
//                 * System.out.println("Maximum X,Theta:"+
//                 * tempCluster[tempCluster.length -
//                 * 1][0]+","+tempCluster[tempCluster.length - 1][3] +"\t Miniimum
//                 * X,Theta:"+ tempCluster[0][0]+","+ tempCluster[0][3] +"\nAngle
//                 * Difference="+(tempCluster[tempCluster.length - 1][3]-
//                 * tempCluster[0][3])
//                 * +"\tXmax-Xmin="+(tempCluster[tempCluster.length - 1][0]- tempCluster[0][0]));
//                 */
//            }
//
//            System.out.print("~");
//        }
//
//        /**
//         * Filter the scattered points
//         */
//        Points_2Dmin = FilterCurvePoints(Points_2Dmin, ScatterThresholdZ);
//        /**
//         * Add last point
//         */
//        //Points_2Dmin=ExtrapolateCurveByY(Points_2Dmin, YMax,3*Points_2Dmin.size()/4,true);
//        /**
//         * Convert from vector to Array
//         */
//        double[][] result = new double[Points_2Dmin.size()][4];
//        /**
//         * Convert to the Array
//         */
//        for (int i = 0; i < Points_2Dmin.size(); i++) {
//            result[i] = (double[]) Points_2Dmin.get(i);
//        }
//        Points_Boundary.add(result);
//
//        Points_2Dmax = FilterCurvePoints(Points_2Dmax, ScatterThresholdZ);
//        //Points_2Dmax=ExtrapolateCurveByY(Points_2Dmax, YMax,3*Points_2Dmax.size()/4,true);
//        result = new double[Points_2Dmax.size()][4];
//        /**
//         * Convert to the Array
//         */
//        for (int i = 0; i < Points_2Dmax.size(); i++) {
//            result[i] = (double[]) Points_2Dmax.get(i);
//        }
//        Points_Boundary.add(result);
//
//        if (message) {
//            System.out.println("Total Points in minm X: " + Points_2Dmin.size() + "\t Out of: " + (PointsCluster.length));
//            System.out.println("Total Points in maxm X: " + Points_2Dmax.size() + "\t Out of: " + (PointsCluster.length));
//        }
//        // Finished computation. Now hide the dialog. This will also stop the
//        // thread since the "run" method will return.
//        dlg.dispose();
//        return Points_Boundary;
//    }

    /**
     * Boundary points based on X minimum and Maximum. Bounding Box Method: 
     * This returns vector with size two containing 2D array of left and right curves
     * @param PointsCluster
     * @param YAccuracy
     * @return
     */
    public Vector getBoundaryPoints(double[][] PointsCluster, double YAccuracy, double ScatterThreshold, double YMax, double Ymin, boolean message) {
        long begTest = new java.util.Date().getTime();
        // Create a dialog that will display the progress.
//        final JDialog dlg = new JDialog(new Frame(), "#getBoundaryPoints", true);
//        Wavesprgbar dpb = new Wavesprgbar(0, (int)( (YMax - Ymin) / YAccuracy));
//
//        dlg.getContentPane().setLayout(new BorderLayout());
//        dlg.getContentPane().add(BorderLayout.CENTER, dpb);
//        dlg.getContentPane().add(BorderLayout.NORTH, new JLabel("Calculating..."));
//        dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
//        dlg.setSize(300, 100);
//        dlg.setLocation(0, 200);
        // Create a new thread and call show within the thread.
//        Thread t = new Thread(new Runnable() {
//
//            public void run() {
//                //dlg.show();
//            }
//        });

        // Start the thread so that the dialog will show.
//        t.start();

        Vector Points_2Dmax = new Vector();
        Vector Points_2Dmin = new Vector();
        Vector Points_Boundary = new Vector();
        /**
         * Define regions based on YAccuracy Find Extreme Xs and Ys Going from Ystart to Yend, get Points with extreme Xs
         */
        //double[][] bbx = getBoundingBox(PointsCluster);
        //double[][] bbx = getBoundingBox_Strings(PointsCluster);
        double[][] bbx = getBoundingBox_bubbleSort(PointsCluster);
        //double[][] bbx = getBoundingBox_QuickSort(PointsCluster);
        //System.out.print("Bbx Found.");

        Double secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        //System.out.println("getBoundingBox_bubbleSort run time " + secs + " secs");
        for (int regionCount = 1; regionCount < (YMax - Ymin) / YAccuracy; regionCount++) {

            //dpb.setValue(regionCount);

            // dlg.update(dlg.getGraphics());
            begTest = new java.util.Date().getTime();
            // This shape is needed for BBX
            //  
            //  _______________
            // |    _______    |
            // |___|       |___|
            //
            //
            //Create XMinBBX
            double XMinBBX[][]={{bbx[1][0], (Ymin + YAccuracy * (regionCount - 1))-YAccuracy},
                                {bbx[1][0]-YAccuracy, Ymin + YAccuracy * (regionCount)}};
            //Create XMaxBBX
            double XMaxBBX[][]={{bbx[0][0], (Ymin + YAccuracy * (regionCount - 1))-YAccuracy},
                                {bbx[0][0]+YAccuracy, Ymin + YAccuracy * (regionCount)}};
            //Create Usual BBX
            double UsualBBX[][]={{bbx[1][0], Ymin + YAccuracy * (regionCount - 1)},
                                 {bbx[0][0], Ymin + YAccuracy * regionCount}};
            
            Set<double[][]> BBXSet= new HashSet();
            BBXSet.add(XMinBBX);
            BBXSet.add(XMaxBBX);
            BBXSet.add(UsualBBX);
            double[][] tempCluster = getBoundedPoints(PointsCluster,BBXSet);
            
//            double[][] tempCluster = getBoundedPoints(PointsCluster,
//                    new double[]{bbx[1][0], Ymin + YAccuracy * (regionCount - 1)},
//                    new double[]{bbx[0][0], Ymin + YAccuracy * regionCount});
            
            
            secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
            
            
           // System.out.println("getBoundedPoints run time " + secs + " secs");
            begTest = new java.util.Date().getTime();
            //System.out.print(bbx[1][0]+"\t"+ (Ymin + YAccuracy * (regionCount - 1)));
            //System.out.println("\t"+ bbx[0][0]+"\t"+ (Ymin + YAccuracy * (regionCount)));

            if (tempCluster != null) {
                /**
                 * Descending order of sorting
                 */
                //bubble_srt(tempCluster, tempCluster[0].length, tempCluster.length, 0, false);
                /*
                 * Commented out to test other sorting algorithm
                 */
                Arrays.sort(tempCluster, new Comparator<double[]>() {
                    @Override
                    public int compare(final double[] entry1, final double[] entry2) {
                        final double time1 = entry1[0];
                        final double time2 = entry2[0];
                        return time1 > time2 ? 0 : -1;
                    }
                });/*
                 * /
                 */
                secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
                //System.out.println(" Arrays.sort run time " + secs + " secs");
                Points_2Dmin.add(tempCluster[tempCluster.length - 1]);//Minimum Point
                Points_2Dmax.add(tempCluster[0]);//Maximum Point


            }

            //System.out.print("~");

        }

        /**
         * Filter the scattered points
         */
        Points_2Dmin = FilterCurvePoints(Points_2Dmin, ScatterThreshold);
        secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("FilterCurvePoints run time " + secs + " secs");
        begTest = new java.util.Date().getTime();
        /**
         * Add last point
         */
        //Points_2Dmin=ExtrapolateCurveByY(Points_2Dmin, YMax,3*Points_2Dmin.size()/4,true);
        /**
         * Convert from vector to Array
         */
        double[][] result = new double[Points_2Dmin.size()][4];
        /**
         * Convert to the Array
         */
        for (int i = 0; i < Points_2Dmin.size(); i++) {
            result[i] = (double[]) Points_2Dmin.get(i);
        }
        Points_Boundary.add(result);

        Points_2Dmax = FilterCurvePoints(Points_2Dmax, ScatterThreshold);
        secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("FilterCurvePoints run time " + secs + " secs");
        begTest = new java.util.Date().getTime();
        //Points_2Dmax=ExtrapolateCurveByY(Points_2Dmax, YMax,3*Points_2Dmax.size()/4,true);
        result = new double[Points_2Dmax.size()][4];
        /**
         * Convert to the Array
         */
        for (int i = 0; i < Points_2Dmax.size(); i++) {
            result[i] = (double[]) Points_2Dmax.get(i);
        }
        Points_Boundary.add(result);

        if (message) {
            System.out.println("Total Points in minm X: " + Points_2Dmin.size() + "\t Out of: " + (PointsCluster.length));
            System.out.println("Total Points in maxm X: " + Points_2Dmax.size() + "\t Out of: " + (PointsCluster.length));
        }
        // Finished computation. Now hide the dialog. This will also stop the
        // thread since the "run" method will return.
        //dlg.dispose();

        return Points_Boundary;
    }

    /**
     * Boundary points based on X minimum and Maximum. Bounding Box Method: This returns vector with size two containing 2D array of left and right curves
     *
     * @param PointsCluster
     * @param YAccuracy
     * @return
     */
    public Vector getBoundaryPointsMCW(double[][] PointsCluster, double YAccuracy, double ScatterThreshold, double YMax, double Ymin, boolean message) {
        long begTest = new java.util.Date().getTime();
        // Create a dialog that will display the progress.
//        final JDialog dlg = new JDialog(new Frame(), "#getBoundaryPoints", true);
//        Wavesprgbar dpb = new Wavesprgbar(0, (int)( (YMax - Ymin) / YAccuracy));
//
//        dlg.getContentPane().setLayout(new BorderLayout());
//        dlg.getContentPane().add(BorderLayout.CENTER, dpb);
//        dlg.getContentPane().add(BorderLayout.NORTH, new JLabel("Calculating..."));
//        dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
//        dlg.setSize(300, 100);
//        dlg.setLocation(0, 200);
        // Create a new thread and call show within the thread.
//        Thread t = new Thread(new Runnable() {
//
//            public void run() {
//                //dlg.show();
//            }
//        });

        // Start the thread so that the dialog will show.
//        t.start();

        Vector Points_2Dmax = new Vector();
        Vector Points_2Dmin = new Vector();
        Vector Points_Boundary = new Vector();
        /**
         * Define regions based on YAccuracy Find Extreme Xs and Ys Going from Ystart to Yend, get Points with extreme Xs
         */
        //double[][] bbx = getBoundingBox(PointsCluster);
        //double[][] bbx = getBoundingBox_Strings(PointsCluster);
        double[][] bbx = getBoundingBox_bubbleSort(PointsCluster);
        //double[][] bbx = getBoundingBox_QuickSort(PointsCluster);
        System.out.print("Bbx Found.");

        Double secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("getBoundingBox_bubbleSort run time " + secs + " secs");

        List futuresList = new ArrayList();
        ForkJoinPool fjPool = new ForkJoinPool((int) ((YMax - Ymin) / YAccuracy));
        for (int regionCount = 1; regionCount < (YMax - Ymin) / YAccuracy; regionCount++) {

            //dpb.setValue(regionCount);

            // dlg.update(dlg.getGraphics());
            begTest = new java.util.Date().getTime();
            double[][] tempCluster = getBoundedPoints(PointsCluster,
                    new double[]{bbx[1][0], Ymin + YAccuracy * (regionCount - 1)},
                    new double[]{bbx[0][0], Ymin + YAccuracy * regionCount});
            secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
            System.out.println("getBoundedPoints run time " + secs + " secs");
            begTest = new java.util.Date().getTime();
            //System.out.print(bbx[1][0]+"\t"+ (Ymin + YAccuracy * (regionCount - 1)));
            //System.out.println("\t"+ bbx[0][0]+"\t"+ (Ymin + YAccuracy * (regionCount)));

            if (tempCluster != null) {
                /**
                 * Descending order of sorting
                 */
                futuresList.add(fjPool.submit(new ExtremeXYFJTask(tempCluster, message)));

            }

            System.out.print("~");

        }

        double[][] tempCluster;
        for (Object future : futuresList) { //Collect all the results
            try {
                tempCluster = (double[][]) ((Future) future).get();
                if (tempCluster != null) {
                    Points_2Dmin.add(tempCluster[tempCluster.length - 1]);//Minimum Point
                    Points_2Dmax.add(tempCluster[0]);//Maximum Point
                }

            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            }
        }

        /**
         * Filter the scattered points
         */
        Points_2Dmin = FilterCurvePoints(Points_2Dmin, ScatterThreshold);
        secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("FilterCurvePoints run time " + secs + " secs");
        begTest = new java.util.Date().getTime();
        /**
         * Add last point
         */
        //Points_2Dmin=ExtrapolateCurveByY(Points_2Dmin, YMax,3*Points_2Dmin.size()/4,true);
        /**
         * Convert from vector to Array
         */
        double[][] result = new double[Points_2Dmin.size()][4];
        /**
         * Convert to the Array
         */
        for (int i = 0; i < Points_2Dmin.size(); i++) {
            result[i] = (double[]) Points_2Dmin.get(i);
        }
        Points_Boundary.add(result);

        Points_2Dmax = FilterCurvePoints(Points_2Dmax, ScatterThreshold);
        secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("FilterCurvePoints run time " + secs + " secs");
        begTest = new java.util.Date().getTime();
        //Points_2Dmax=ExtrapolateCurveByY(Points_2Dmax, YMax,3*Points_2Dmax.size()/4,true);
        result = new double[Points_2Dmax.size()][4];
        /**
         * Convert to the Array
         */
        for (int i = 0; i < Points_2Dmax.size(); i++) {
            result[i] = (double[]) Points_2Dmax.get(i);
        }
        Points_Boundary.add(result);

        if (message) {
            System.out.println("Total Points in minm X: " + Points_2Dmin.size() + "\t Out of: " + (PointsCluster.length));
            System.out.println("Total Points in maxm X: " + Points_2Dmax.size() + "\t Out of: " + (PointsCluster.length));
        }
        // Finished computation. Now hide the dialog. This will also stop the
        // thread since the "run" method will return.
        //dlg.dispose();

        return Points_Boundary;
    }

    /**
     * Boundary point cloud based on X minimum and Maximum. Bounding Box Method: This returns vector with size two containing 2D array of left and right curves
     *
     * @param PointsCluster
     * @param YAccuracy
     * @param ScatterThreshold
     * @param YMax
     * @param Ymin
     * @param QtyOfPointsPerBBX Number of points per smallest bounding box
     * @param message
     * @return
     */
//    public Vector getBoundaryPointCloud(double[][] PointsCluster, double XAccuracy, double YAccuracy, double ScatterThreshold, double YMax, double Ymin, int QtyOfPointsPerBBX, boolean message) {
//
//        /**
//         * Define regions based on YAccuracy Find Extreme Xs and Ys Going from Ystart to Yend, get Points with extreme Xs
//         */
//        double[][] bbx = getBoundingBox_bubbleSort(PointsCluster);
//
//        // Create a dialog that will display the progress.
//        final JDialog dlg = new JDialog(new Frame(), "#getBoundaryPoints", true);
//        Wavesprgbar dpb = new Wavesprgbar(0, (int) ((YMax - Ymin) / YAccuracy));
//
//        dlg.getContentPane().setLayout(new BorderLayout());
//        dlg.getContentPane().add(BorderLayout.CENTER, dpb);
//        dlg.getContentPane().add(BorderLayout.NORTH, new JLabel("Calculating..."));
//        dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
//        dlg.setSize(300, 100);
//        dlg.setLocation(0, 200);
//        // Create a new thread and call show within the thread.
//        Thread t = new Thread(new Runnable() {
//            public void run() {
//                dlg.show();
//            }
//        });
//
//        // Start the thread so that the dialog will show.
//        t.start();
//
//        Vector Points_2Dmax = new Vector();
//        Vector Points_2Dmin = new Vector();
//        Vector Points_Boundary = new Vector();
//
//        //double[][] bbx = getBoundingBox_QuickSort(PointsCluster);
//        System.out.print("Bbx Found.");
//        for (int regionCount = 1; regionCount < (YMax - Ymin) / YAccuracy; regionCount++) {
//
//            dpb.setValue(regionCount);
//
//            dlg.update(dlg.getGraphics());
//
//            double[][] tempCluster = getBoundedPoints(PointsCluster,
//                    new double[]{bbx[1][0], Ymin + YAccuracy * (regionCount - 1)},
//                    new double[]{bbx[0][0], Ymin + YAccuracy * regionCount});
//
//            //System.out.print(bbx[1][0]+"\t"+ (Ymin + YAccuracy * (regionCount - 1)));
//            //System.out.println("\t"+ bbx[0][0]+"\t"+ (Ymin + YAccuracy * (regionCount)));
//
//            if (tempCluster != null) {
//                /**
//                 * Descending order of sorting
//                 */
//                //bubble_srt(tempCluster, tempCluster[0].length, tempCluster.length, 0, false);
//                /*
//                 * Commented out to test other sorting algorithm
//                 */
//                Arrays.sort(tempCluster, new Comparator<double[]>() {
//                    @Override
//                    public int compare(final double[] entry1, final double[] entry2) {
//                        final double time1 = entry1[0];
//                        final double time2 = entry2[0];
//                        return time1 > time2 ? 0 : -1;
//                    }
//                });/*
//                 * /
//                 */
//
//                /**
//                 * Based on max and min get the cloud
//                 *
//                 */
//                //Commented on 4/1/2013
////                if(tempCluster.length>QtyOfPointsPerBBX)// Make sure there are more points than needed
////                for(int ptQty=0;ptQty<QtyOfPointsPerBBX;ptQty++){
////                Points_2Dmin.add(tempCluster[tempCluster.length - 1-ptQty]);//Minimum Point
////                Points_2Dmax.add(tempCluster[0+ptQty]);//Maximum Point
////                }
//                /**
//                 * CREATE NEW BOUNDING BOX BASED ON EXTREME POINTS AND THEN EXTRACT ALL THE POINTS FROM THAT CLOUD AND RETURN
//                 */
//                double Xmin = tempCluster[tempCluster.length - 1][0];
//                double Xmax = tempCluster[0][0];
//
//                //For minimum
//                tempCluster = getBoundedPoints(PointsCluster,
//                        new double[]{Xmin + XAccuracy / 2.0, Ymin + YAccuracy * (regionCount - 1)},
//                        new double[]{Xmin - XAccuracy / 2.0, Ymin + YAccuracy * regionCount});
//                dlg.update(dlg.getGraphics());
//                if (tempCluster != null) {
//                    Arrays.sort(tempCluster, new Comparator<double[]>() {
//                        @Override
//                        public int compare(final double[] entry1, final double[] entry2) {
//                            final double time1 = entry1[0];
//                            final double time2 = entry2[0];
//                            return time1 > time2 ? 0 : -1;
//                        }
//                    });
//
//                    for (int ptQty = 0; ptQty < tempCluster.length; ptQty++) {
//                        Points_2Dmin.add(tempCluster[tempCluster.length - 1 - ptQty]);//Minimum Point
//                        //Points_2Dmax.add(tempCluster[0+ptQty]);//Maximum Point
//                    }
//
//                }
//
//                //For Maximum
//                tempCluster = getBoundedPoints(PointsCluster,
//                        new double[]{Xmax + XAccuracy / 2.0, Ymin + YAccuracy * (regionCount - 1)},
//                        new double[]{Xmax - XAccuracy / 2.0, Ymin + YAccuracy * regionCount});
//                dlg.update(dlg.getGraphics());
//                if (tempCluster != null) {
//                    Arrays.sort(tempCluster, new Comparator<double[]>() {
//                        @Override
//                        public int compare(final double[] entry1, final double[] entry2) {
//                            final double time1 = entry1[0];
//                            final double time2 = entry2[0];
//                            return time1 > time2 ? 0 : -1;
//                        }
//                    });
//
//                    for (int ptQty = 0; ptQty < tempCluster.length; ptQty++) {
//                        //Points_2Dmin.add(tempCluster[tempCluster.length - 1-ptQty]);//Minimum Point
//                        Points_2Dmax.add(tempCluster[0 + ptQty]);//Maximum Point
//                    }
//
//                }
//
//
//            }
//
//            System.out.print("~");
//        }
//
//        /**
//         * Filter the scattered points
//         */
//        //Points_2Dmin=FilterCurvePoints(Points_2Dmin,ScatterThreshold);
//        /**
//         * Add last point
//         */
//        //Points_2Dmin=ExtrapolateCurveByY(Points_2Dmin, YMax,3*Points_2Dmin.size()/4,true);
//        /**
//         * Convert from vector to Array
//         */
//        double[][] result = new double[Points_2Dmin.size()][4];
//        /**
//         * Convert to the Array
//         */
//        for (int i = 0; i < Points_2Dmin.size(); i++) {
//            result[i] = (double[]) Points_2Dmin.get(i);
//        }
//        Points_Boundary.add(result);
//
//        //Points_2Dmax=FilterCurvePoints(Points_2Dmax,ScatterThreshold);
//
//        //Points_2Dmax=ExtrapolateCurveByY(Points_2Dmax, YMax,3*Points_2Dmax.size()/4,true);
//        result = new double[Points_2Dmax.size()][4];
//        /**
//         * Convert to the Array
//         */
//        for (int i = 0; i < Points_2Dmax.size(); i++) {
//            result[i] = (double[]) Points_2Dmax.get(i);
//        }
//        Points_Boundary.add(result);
//
//        if (message) {
//            System.out.println("Total Points in minm X: " + Points_2Dmin.size() + "\t Out of: " + (PointsCluster.length));
//            System.out.println("Total Points in maxm X: " + Points_2Dmax.size() + "\t Out of: " + (PointsCluster.length));
//        }
//        // Finished computation. Now hide the dialog. This will also stop the
//        // thread since the "run" method will return.
//        dlg.dispose();
//        return Points_Boundary;
//    }

    /**
     *
     * @param PointsCluster
     * @param YAccuracy
     * @param YMax
     * @param Ymin
     * @return
     */
    public Vector getBoundaryPoints_(double[][] PointsCluster, double YAccuracy, double YMax, double Ymin) {
        Vector Points_2Dmax = new Vector();
        Vector Points_2Dmin = new Vector();
        Vector Points_Boundary = new Vector();
        /**
         * Define regions based on YAccuracy Find Extreme Xs and Ys Going from Ystart to Yend, get Points with extreme Xs
         */
        //double[][] bbx = getBoundingBox(PointsCluster);
        //double[][] bbx = getBoundingBox_Strings(PointsCluster);
        double[][] bbx = getBoundingBox_bubbleSort(PointsCluster);
        for (int regionCount = 1; regionCount < (YMax - Ymin) / YAccuracy; regionCount++) {

            double[][] tempCluster = getBoundedPoints(PointsCluster,
                    new double[]{bbx[1][0], Ymin + YAccuracy * (regionCount - 1)},
                    new double[]{bbx[0][0], Ymin + YAccuracy * regionCount});

            //System.out.print(bbx[1][0]+"\t"+ (Ymin + YAccuracy * (regionCount - 1)));
            //System.out.println("\t"+ bbx[0][0]+"\t"+ (Ymin + YAccuracy * (regionCount)));

            if (tempCluster != null) {
                /**
                 * Descending order of sorting
                 */
                Arrays.sort(tempCluster, new Comparator<double[]>() {
                    @Override
                    public int compare(final double[] entry1, final double[] entry2) {
                        final double time1 = entry1[0];
                        final double time2 = entry2[0];
                        return time1 > time2 ? 0 : -1;
                    }
                });
                Points_2Dmin.add(tempCluster[tempCluster.length - 1]);//Minimum Point
                Points_2Dmax.add(tempCluster[0]);//Maximum Point
                /*
                 * System.out.println("Maximum X,Theta:"+
                 * tempCluster[tempCluster.length -
                 * 1][0]+","+tempCluster[tempCluster.length - 1][3] +"\t Miniimum
                 * X,Theta:"+ tempCluster[0][0]+","+ tempCluster[0][3] +"\nAngle
                 * Difference="+(tempCluster[tempCluster.length - 1][3]-
                 * tempCluster[0][3])
                 * +"\tXmax-Xmin="+(tempCluster[tempCluster.length - 1][0]- tempCluster[0][0]));
                 */
            }
        }

        double[][] result = new double[Points_2Dmin.size()][4];
        /**
         * Convert to the Array
         */
        for (int i = 0; i < Points_2Dmin.size(); i++) {
            result[i] = (double[]) Points_2Dmin.get(i);
        }
        Points_Boundary.add(result);
        result = new double[Points_2Dmax.size()][4];
        /**
         * Convert to the Array
         */
        for (int i = 0; i < Points_2Dmax.size(); i++) {
            result[i] = (double[]) Points_2Dmax.get(i);
        }
        Points_Boundary.add(result);

        System.out.println("Total Points in minm X: " + Points_2Dmin.size() + "\t Out of: " + (PointsCluster.length));
        System.out.println("Total Points in maxm X: " + Points_2Dmax.size() + "\t Out of: " + (PointsCluster.length));
        return Points_Boundary;
    }

    public Vector getConvexHull(double[][] PointsCluster) {

        double[][] IntesectionData = new grahamScan_().doGraham(PointsCluster, PointsCluster[0][2]);
        Vector Result = new Vector();
        Result.add(IntesectionData);;

        return Result;
    }

    public Vector getBoundaryPointsBasedOnYSort(double[][] PointsCluster, int YAccuracy) {
        /**
         * Get sorted Point cluster Get size of cluster Divide the cluster in equal number of points Get Extreme Xs
         *
         */
        double[][] SortedPointsCluster = getSortedByY(PointsCluster, true);
        System.out.println("\t Ymin=" + SortedPointsCluster[0][0] + "\t Ymin=" + SortedPointsCluster[SortedPointsCluster.length - 1][0]);
        Vector Points_2Dmax = new Vector();
        Vector Points_2Dmin = new Vector();
        Vector Points_Boundary = new Vector();
        /**
         * Define regions based on YAccuracy Find Extreme Xs and Ys Going from Ystart to Yend, get Points with extreme Xs
         */
        double[][] bbx = getBoundingBox(PointsCluster);

        for (int regionCount = 0; regionCount < SortedPointsCluster.length / YAccuracy; regionCount++) {
            double[][] tempCluster = new double[YAccuracy][SortedPointsCluster[0].length];
            for (int i = 0; i < tempCluster.length; i++) {
                tempCluster[i] = SortedPointsCluster[regionCount * YAccuracy + i];
            }

            System.out.println("\t Ymin=" + tempCluster[0][0] + "\t Ymin=" + tempCluster[tempCluster.length - 1][0]);

            if (tempCluster != null) {
                /**
                 * Descending order of sorting
                 */
                Arrays.sort(tempCluster, new Comparator<double[]>() {
                    @Override
                    public int compare(final double[] entry1, final double[] entry2) {
                        final double time1 = entry1[0];
                        final double time2 = entry2[0];
                        return time1 > time2 ? 0 : -1;
                    }
                });
                Points_2Dmin.add(tempCluster[tempCluster.length - 1]);//Minimum Point
                Points_2Dmax.add(tempCluster[0]);//Maximum Point
                System.out.println("Minimum X:" + tempCluster[tempCluster.length - 1][0] + "\t Maximum X:" + tempCluster[0][0]);
            }
        }

        double[][] result = new double[Points_2Dmin.size()][4];
        /**
         * Convert to the Array
         */
        for (int i = 0; i < Points_2Dmin.size(); i++) {
            result[i] = (double[]) Points_2Dmin.get(i);
        }
        Points_Boundary.add(result);
        result = new double[Points_2Dmax.size()][4];
        /**
         * Convert to the Array
         */
        for (int i = 0; i < Points_2Dmax.size(); i++) {
            result[i] = (double[]) Points_2Dmax.get(i);
        }
        Points_Boundary.add(result);

        System.out.println("Total Points in minm X: " + Points_2Dmin.size() + "\t Out of: " + (PointsCluster.length));
        System.out.println("Total Points in maxm X: " + Points_2Dmax.size() + "\t Out of: " + (PointsCluster.length));
        return Points_Boundary;
    }

    public double[][] getNthSection(double[][][] PinionData, int NthPosition) {
        double[][] result = PinionData[NthPosition];
        return result;
    }

    /**
     * This should return multiple point clouds based on XThreshold Assumptions: 1. Number of sections are unknown 2. There is no mixup of the point cloud
     * points in terms of the sequence.
     *
     * @param PointsCluster
     * @param ZValue
     * @param XThreshold
     * @return
     */
    public Vector getPointsOnZPlane(double[][] PointsCluster, double ZValue, double XThreshold) {
        /**
         * 1. Number of sections are unknown 2. There is no mixup of the point cloud points in terms of the sequence.
         */
        Vector Points_2D = new Vector();
        Vector SeprationIndex = new Vector();//Must contain int values for the position where point cloud sepration happens.
        /**
         * Sort the Array based on Z
         */
        Arrays.sort(PointsCluster, new Comparator<double[]>() {
            @Override
            public int compare(final double[] entry1, final double[] entry2) {
                final double time1 = entry1[2];
                final double time2 = entry2[2];
                return time1 > time2 ? 0 : -1;
            }
        });
        //Get All the point on plane
        for (int i = 1; i < PointsCluster.length; i++) {
            //System.out.print(PointsCluster[i]);
            if (PointsCluster[i][2] == ZValue) {//Check if points are on same plane

                Points_2D.add(PointsCluster[i]);
                //Removed on 4/1/2013

            }
            //System.out.print("-");
        }

        //Added on 4/1/2013
        //Create array
        double[][] tempresult = new double[Points_2D.size()][4];
        /**
         * Convert to the Array
         */
        for (int i = 0; i < Points_2D.size(); i++) {
            tempresult[i] = (double[]) Points_2D.get(i);
        }
        //Sort the array based on X in decreasing order
        bubble_srt(tempresult, tempresult[0].length, tempresult.length, 0, false);
        //Re-initialize the vector
        Points_2D = new Vector();
        for (int i = 1; i < tempresult.length; i++) {
            Points_2D.add(tempresult[i]);
            //Record the position where the cloud sepration is hapenning.
            if (new ProximityOperations().getDistanceBetween2Points(tempresult[i - 1], tempresult[i]) > XThreshold) {
                SeprationIndex.add(Points_2D.size());//P.S. its ith point, so while retrieving one cloud exclude this.
                System.out.println("#getPointsOnZPlane SeprationIndex.size()=" + SeprationIndex.size() + "\t Points_2D.size()=" + Points_2D.size());
            }

        }
        System.out.println("#getPointsOnZPlane SeprationIndex.size()=" + SeprationIndex.size() + "\t Points_2D.size()=" + Points_2D.size());

        SeprationIndex.add(Points_2D.size());//Add the last index
        if (Points_2D.size() < 1) {
            return null;
        }

        Vector PointClouds = new Vector();

        for (int cloudSepIndex = 0; cloudSepIndex < SeprationIndex.size() - 1; cloudSepIndex++) {//Exclude last one refer to few lines up "Add the last index"
            int size = (int) SeprationIndex.get(cloudSepIndex + 1) - (int) SeprationIndex.get(cloudSepIndex);
            System.out.println("#getPointsOnZPlane cloudSepIndex=" + cloudSepIndex + "\tsize=" + size + "\t Points_2D.size()=" + Points_2D.size());
            double[][] result = new double[size][4];
            /**
             * Convert to the Array
             */
            for (int i = 0; i < size; i++) {
                result[i] = (double[]) Points_2D.get((int) SeprationIndex.get(cloudSepIndex) + i);
            }
            System.out.println("Total Points in this cluster of Z Plane: " + size + "\t Out of: " + (PointsCluster.length));

            //Put in vector
            PointClouds.add(result);
        }

        //Special case for no scattering
        if (SeprationIndex.size() - 1 == 0) {
            PointClouds.add(getPointsOnZPlane(PointsCluster, ZValue));
        }

        return PointClouds;
    }

    /**
     *
     * @param PointsCluster
     * @param ZValue
     * @return
     */
    public double[][] getPointsOnZPlane(double[][] PointsCluster, double ZValue) {
        Vector Points_2D = new Vector();

        for (int i = 0; i < PointsCluster.length; i++) {
            //System.out.print(PointsCluster[i]);
            if (PointsCluster[i][2] == ZValue) {
                Points_2D.add(PointsCluster[i]);
            }
            //System.out.print("-");
        }
        if (Points_2D.size() < 1) {
            return null;
        }

        double[][] result = new double[Points_2D.size()][4];
        /**
         * Convert to the Array
         */
        for (int i = 0; i < Points_2D.size(); i++) {
            result[i] = (double[]) Points_2D.get(i);
        }
        System.out.println("Total Points in Z Plane: " + Points_2D.size() + "\t Out of: " + (PointsCluster.length));


        return result;
    }

    /**
     * Get Bounding box for a given cluster
     *
     * @param PointsCluster
     * @return
     */
    public static double[][] getBoundingBox(double[][] PointsCluster) {
        double[][] result = new double[2][3];
        /**
         * Find X Minima & Maxima
         */
        Arrays.sort(PointsCluster, new Comparator<double[]>() {
            @Override
            public int compare(final double[] entry1, final double[] entry2) {
                final double time1 = entry1[0];
                final double time2 = entry2[0];
                return time1 > time2 ? 0 : -1;
            }
        });
        result[0][0] = PointsCluster[PointsCluster.length - 1][0];//Minima
        result[1][0] = PointsCluster[0][0];//Maxima
        System.out.println("To Get X-max and X-min");
        Sort2D.printArr(PointsCluster);
        /**
         * Find Y Minima & Maxima
         */
        Arrays.sort(PointsCluster, new Comparator<double[]>() {
            @Override
            public int compare(final double[] entry1, final double[] entry2) {
                final double time1 = entry1[1];
                final double time2 = entry2[1];
                return time1 > time2 ? 0 : -1;
            }
        });
        result[0][1] = PointsCluster[PointsCluster.length - 1][1];//Minima
        result[1][1] = PointsCluster[0][1];//Maxima

        //System.out.println("To Get Y-max and Y-min");
        //Sort2D.printArr(PointsCluster);
        System.out.println("Bounding box: Minmm[" + result[0][0] + "," + result[0][1] + "]\t Maxmm[" + result[1][0] + "," + result[1][1] + "]");
        return result;
    }

    public static Double[][] getBoundingBox(Double[][] PointsCluster) {
        Double[][] result = new Double[2][3];
        /**
         * Find X Minima & Maxima
         */
        Arrays.sort(PointsCluster, new Comparator<Double[]>() {
            @Override
            public int compare(final Double[] entry1, final Double[] entry2) {
                final double time1 = entry1[0];
                final double time2 = entry2[0];
                return time1 > time2 ? 0 : -1;
            }
        });
        result[0][0] = PointsCluster[PointsCluster.length - 1][0];//Minima
        result[1][0] = PointsCluster[0][0];//Maxima
        //System.out.println("To Get X-max and X-min");
       // Sort2D.printArr(PointsCluster);
        /**
         * Find Y Minima & Maxima
         */
        Arrays.sort(PointsCluster, new Comparator<Double[]>() {
            @Override
            public int compare(final Double[] entry1, final Double[] entry2) {
                final double time1 = entry1[1];
                final double time2 = entry2[1];
                return time1 > time2 ? 0 : -1;
            }
        });
        result[0][1] = PointsCluster[PointsCluster.length - 1][1];//Minima
        result[1][1] = PointsCluster[0][1];//Maxima

        //System.out.println("To Get Y-max and Y-min");
        //Sort2D.printArr(PointsCluster);
        //System.out.println("Bounding box: Minmm[" + result[0][0] + "," + result[0][1] + "]\t Maxmm[" + result[1][0] + "," + result[1][1] + "]");
        return result;
    }

    /**
     * Get Bounding box of points
     * result[0][0] = PointsCluster[PointsCluster.length - 1][0];//Minima
     * result[1][0] = PointsCluster[0][0];//Maxima
     * result[0][1] = PointsCluster[PointsCluster.length - 1][1];//Minima
     * result[1][1] = PointsCluster[0][1];//Maxima
     *
     * @param PointsCluster
     * @return
     */
    public double[][] getBoundingBox_bubbleSort(double[][] PointsCluster) {

        double[][] result = new double[2][3];
        /**
         * Find X Minima & Maxima
         */
        bubble_srt(PointsCluster, PointsCluster[0].length, PointsCluster.length, 0, false);

        result[0][0] = PointsCluster[PointsCluster.length - 1][0];//Minima
        result[1][0] = PointsCluster[0][0];//Maxima
        //System.out.println("To Get X-max and X-min");
        //Sort2D.printArr(PointsCluster);
        /**
         * Find Y Minima & Maxima
         */
        bubble_srt(PointsCluster, PointsCluster[0].length, PointsCluster.length, 1, false);
        result[0][1] = PointsCluster[PointsCluster.length - 1][1];//Minima
        result[1][1] = PointsCluster[0][1];//Maxima

        //System.out.println("To Get Y-max and Y-min");
        //Sort2D.printArr(PointsCluster);
        //System.out.println("Bounding box: Minmm["+result[0][0]+","+result[0][1]+"]\t Maxmm["+result[1][0]+","+result[1][1]+"]");


        /**
         * Sort it back to X Minima and Maxima, as this will be used for point cloud separation.
         */
        bubble_srt(PointsCluster, PointsCluster[0].length, PointsCluster.length, 0, false);


        return result;
    }

    public double[][] getBoundingBox_QuickSort(double[][] PointsCluster) {
        double[][] result = new double[2][3];
        /**
         * Find X Minima & Maxima
         */
        // bubble_srt(PointsCluster, PointsCluster[0].length, PointsCluster.length, 0, false);
        System.out.println("QS" + PointsCluster.length);
        long sysmilliSec=System.currentTimeMillis();
        quick_srt(PointsCluster, 0, PointsCluster.length - 1, 0);
        System.out.println("Single Core Quick Sort for\t "+PointsCluster.length+"\t is \t"+(System.currentTimeMillis()-sysmilliSec)+" ms.");
        sysmilliSec=System.currentTimeMillis();
        //Parallel Core 
        quick_srt_parallel(PointsCluster,  0);
        System.out.println("Multi Core Quick Sort for\t "+PointsCluster.length+"\t is \t"+(System.currentTimeMillis()-sysmilliSec)+" ms.");
        sysmilliSec=System.currentTimeMillis();
        
        result[1][0] = PointsCluster[PointsCluster.length - 1][0];//Minima
        result[0][0] = PointsCluster[0][0];//Maxima
        System.out.print("QS-Done 1");
        //System.out.println("To Get X-max and X-min");
        //Sort2D.printArr(PointsCluster);
        /**
         * Find Y Minima & Maxima
         */
        //bubble_srt(PointsCluster, PointsCluster[0].length, PointsCluster.length, 1, false);
        quick_srt(PointsCluster, 0, PointsCluster.length - 1, 1);
        //Parallel Core 
        quick_srt_parallel(PointsCluster,  1);
        
        result[1][1] = PointsCluster[PointsCluster.length - 1][1];//Minima
        result[0][1] = PointsCluster[0][1];//Maxima
        System.out.print("QS-Done 2");
        //System.out.println("To Get Y-max and Y-min");
        //Sort2D.printArr(PointsCluster);
        //System.out.println("Bounding box: Minmm["+result[0][0]+","+result[0][1]+"]\t Maxmm["+result[1][0]+","+result[1][1]+"]");
        return result;
    }

    public static void quick_srt_parallel(double array[][], int columnToSort){
        
        QuickSortFJTask quickSort = new QuickSortFJTask(array,columnToSort);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(quickSort);

    }
    public static void quick_srt(double array[][], int low, int n, int columnToSort) {
        int lo = low;
        int hi = n;
        if (lo >= n) {
            return;
        }
        double mid = array[(lo + hi) / 2][columnToSort];
        while (lo < hi) {
            while (lo < hi && array[lo][columnToSort] < mid) {
                lo++;
            }
            while (lo < hi && array[hi][columnToSort] > mid) {
                hi--;
            }
            if (lo < hi) {
                double T = array[lo][columnToSort];
                array[lo][columnToSort] = array[hi][columnToSort];
                array[hi][columnToSort] = T;
            }

            System.out.print(".\b");
        }
        if (hi < lo) {
            int T = hi;
            hi = lo;
            lo = T;
        }
        quick_srt(array, low, lo, columnToSort);
        quick_srt(array, lo == low ? lo + 1 : lo, n, columnToSort);
    }

    
    public double[][] getBoundingBox_Strings(double[][] PointsCluster) {
        double[][] result = new double[2][3];
        String[][] StringPoints = new String[PointsCluster.length][PointsCluster[0].length];
        /**
         * Find X Minima & Maxima
         */
        StringPoints = getStringsFor(PointsCluster, false);//Convert for sorting

        Arrays.sort(StringPoints, new Comparator<String[]>() {
            @Override
            public int compare(final String[] entry1, final String[] entry2) {
                final String time1 = entry1[0];
                final String time2 = entry2[0];
                return time2.compareTo(time1);
            }
        });
        PointsCluster = getDoublesFor(StringPoints);
        result[0][0] = PointsCluster[PointsCluster.length - 1][0];//Minima
        result[1][0] = PointsCluster[0][0];//Maxima
        System.out.println("To Get X-max and X-min");
        Sort2D.printArr(getDoublesFor(StringPoints));

        /**
         * Find Y Minima & Maxima
         */
        Arrays.sort(PointsCluster, new Comparator<double[]>() {
            @Override
            public int compare(final double[] entry1, final double[] entry2) {
                final double time1 = entry1[1];
                final double time2 = entry2[1];
                return time1 > time2 ? 0 : -1;
            }
        });
        result[0][1] = PointsCluster[PointsCluster.length - 1][1];//Minima
        result[1][1] = PointsCluster[0][1];//Maxima

        //System.out.println("To Get Y-max and Y-min");
        //Sort2D.printArr(PointsCluster);
        System.out.println("Bounding box: Minmm[" + result[0][0] + "," + result[0][1] + "]\t Maxmm[" + result[1][0] + "," + result[1][1] + "]");
        return result;
    }

    public double[][] getSortedByY(double[][] PointsCluster, boolean Increasing) {
        //double[][] result = new double[2][3];
        /**
         * Sort by Y-Data
         */
        if (Increasing) {
            Arrays.sort(PointsCluster, new Comparator<double[]>() {
                @Override
                public int compare(final double[] entry1, final double[] entry2) {
                    final double time1 = entry1[1];
                    final double time2 = entry2[1];
                    return time1 < time2 ? 0 : -1;
                }
            });
        }
        if (!Increasing) {
            Arrays.sort(PointsCluster, new Comparator<double[]>() {
                @Override
                public int compare(final double[] entry1, final double[] entry2) {
                    final double time1 = entry1[1];
                    final double time2 = entry2[1];
                    return time1 > time2 ? 0 : -1;
                }
            });
        }

        //result[0][1] = PointsCluster[PointsCluster.length - 1][1];//Minima
        //result[1][1] = PointsCluster[0][1];//Maxima
        //System.out.println("Bounding box: Minmm["+result[0][0]+","+result[0][1]+"]\t Maxmm["+result[1][0]+","+result[1][1]+"]");
        return PointsCluster;
    }

    /**
     * Divides the points in 3D space to different clusters based on Threshold
     *
     * @param Sections
     * @param Threshold
     * @return
     */
    public Vector getLocalSectionCluster(Vector Sections, double Threshold, boolean debug) {

        /**
         * Get distance between points on curve, if it is greater that Threshold separate them from there
         */
        Vector sectionCluster = new Vector();
        Vector tempSection = new Vector();
        for (int sectionCount = 1; sectionCount < Sections.size(); sectionCount++) {
            if (((double[][]) Sections.get(sectionCount - 1) != null) & ((double[][]) Sections.get(sectionCount) != null) & debug) {
                System.out.println("#getLocalSectionCluster\t" + sectionCount + "\t" + ((double[][]) Sections.get(sectionCount)).length);
                System.out.println("#getLocalSectionCluster\t" + (sectionCount - 1) + "\t" + ((double[][]) Sections.get(sectionCount - 1)).length);
            } else {
                System.out.println("#getLocalSectionCluster\t" + (sectionCount));
            }
            if (Sections.get(sectionCount - 1) != null) {
                if (Sections.get(sectionCount) != null) {
                    if (((double[][]) Sections.get(sectionCount - 1)).length > 1) {
                        if (((double[][]) Sections.get(sectionCount)).length > 1) {

                            System.out.println("Got in..");
                            tempSection.add(Sections.get(sectionCount - 1));//Store temporarly
                            double[] point1 = ((double[][]) Sections.get(sectionCount - 1))[0];
                            double[] point2 = ((double[][]) Sections.get(sectionCount))[0];
                            if (debug) {
                                System.out.println("XYZ1:" + point1[0] + ":" + point1[1] + ":" + point1[2]);
                                System.out.println("XYZ2:" + point2[0] + ":" + point2[1] + ":" + point2[2]);
                            }
                            double distance = new ProximityOperations().getDistanceBetween2Points(point1, point2);
                            if (debug) {
                                System.out.println("Distance=" + distance + "\t Threshold=" + Threshold);
                            }
                            if (distance > Threshold) {
                                sectionCluster.add(tempSection);//Add to cluster
                                tempSection = new Vector();//initiate temporary storage
                            }
                        }
                    }
                }
            }

        }
        /**
         * Special Consideration for the last section if its not a scattered one
         */
        // if(!isScatteredCluster(Sections,Threshold)){}
        tempSection.add(Sections.get(Sections.size() - 1));
        sectionCluster.add(tempSection);//Add last to cluster

        System.out.println("#getLocalSectionCluster\t sectionCluster.size()=" + sectionCluster.size());
        return sectionCluster;

    }

    /**
     * Bubble sort implementation
     *
     * @param XYZArray
     * @param SubLen
     * @param ArrayLen
     * @param ColumnToSort
     * @param Increasing
     */
    public static void bubble_srt(double XYZArray[][], int SubLen, int ArrayLen, int ColumnToSort, boolean Increasing) {
        // Create a dialog that will display the progress.
       /* final JDialog dlg = new JDialog(new Frame(), "#bubble_srt", true);
         Progressbar2 dpb = new Progressbar2(0, ArrayLen);

         dlg.getContentPane().setLayout(new BorderLayout());
         dlg.getContentPane().add(BorderLayout.CENTER, dpb);
         dlg.getContentPane().add(BorderLayout.NORTH, new JLabel("Sorting..."));
         dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
         dlg.setSize(500, 75);
         dlg.setLocation(0, 300);
        
        
         // Create a new thread and call show within the thread.
         Thread t = new Thread(new Runnable() {

         public void run() {
         dlg.show();
         }
         });

         // Start the thread so that the dialog will show.
         t.start();*/

        System.out.print("[Bubble Sort Started..");
        int i, j;
        double temXYZ[] = new double[SubLen];
        for (i = 0; i < ArrayLen; i++) {
            //dpb.setValue(i);
            //dlg.update(dlg.getGraphics());
            for (j = 1; j < (ArrayLen - i); j++) {


                if (Increasing) {
                    if (XYZArray[j - 1][ColumnToSort] > XYZArray[j][ColumnToSort]) {
                        temXYZ = XYZArray[j - 1];
                        XYZArray[j - 1] = XYZArray[j];
                        XYZArray[j] = temXYZ;
                    }
                } else {
                    if (XYZArray[j - 1][ColumnToSort] < XYZArray[j][ColumnToSort]) {
                        temXYZ = XYZArray[j - 1];
                        XYZArray[j - 1] = XYZArray[j];
                        XYZArray[j] = temXYZ;
                    }
                }

            }
        }

        /*dlg.dispose();*/
        System.out.print(".End]");
    }

    /**
     * Bubble sort implementation
     *
     * @param XYZArray
     * @param ColumnToSort
     * @param Increasing
     */
    public static void bubble_srt(double XYZArray[][], int ColumnToSort, boolean Increasing) {
        int i, j;
        double temXYZ[] = new double[XYZArray[0].length];
        for (i = 0; i < XYZArray.length; i++) {
            for (j = 1; j < (XYZArray.length - i); j++) {
                if (Increasing) {
                    if (XYZArray[j - 1][ColumnToSort] > XYZArray[j][ColumnToSort]) {
                        temXYZ = XYZArray[j - 1];
                        XYZArray[j - 1] = XYZArray[j];
                        XYZArray[j] = temXYZ;
                    }
                } else {
                    if (XYZArray[j - 1][ColumnToSort] < XYZArray[j][ColumnToSort]) {
                        temXYZ = XYZArray[j - 1];
                        XYZArray[j - 1] = XYZArray[j];
                        XYZArray[j] = temXYZ;
                    }
                }
            }
        }
    }

    public double[][] createShells(double[][] Points, int startLocation) {

        double[][] tempBbx;
        double[] bbxXYZOffset = {.4, .4, .4};
        double[][] tempResult = null;
        boolean deleteElement = false;
        boolean done = false;
        //System.out.print(Points.length + "\t");
        // if(!done)

        tempBbx = getBoundingBox(Points[startLocation], bbxXYZOffset);//Get the local bounding box
        //Sort2D.printArr(Points[pointCount]);
        double[][] tempBBxData = getBoundedPoints(Points, tempBbx[0], tempBbx[1]);//Get points in bounding box
        if (tempBBxData != null) {//Check if its not empty--Its should never be the case
            //tempBBxData = getBoundedPoints(Points, tempBbx[0], Points[startLocation]);//Get points in bounding box Left to this point for shifting to minimum
            //if (tempBBxData != null)if (tempBBxData.length > 1) {deleteElement=true;}else{deleteElement=false;};
            tempBBxData = getBoundedPoints(Points, Points[startLocation], tempBbx[1]);//Get points in bounding box Right to this point for shifting to maximum
            if (tempBBxData != null) {
                if (tempBBxData.length > 1) {
                    deleteElement = false;
                } else {
                    deleteElement = true;
                }
            };

            if (deleteElement) {//If there are more than 1 points
                Points = removeElements(Points, startLocation);//Remove this element
                System.out.print("Ponits Remaining:" + Points.length + "\t Deleting:" + startLocation + "\t\n");
                if (createShells(Points, startLocation) == null) {
                    done = true;
                }
            }
        } else {
            System.out.print("\n Null Detected.");
        }
        return Points;
    }

    public double[][] removeElements(double[][] Points, int deleteIndex) {
        Vector array = new Vector();


        for (int i = 0; i < Points.length; i++) {
            if (deleteIndex != i) {
                array.add(Points[i]);
            }
        }

        double[][] result = new double[array.size()][Points[0].length];
        for (int j = 0; j < array.size(); j++) {
            result[j] = (double[]) array.get(j);
        }

        return result;
    }

    /**
     * Creates 2D array from 2 1D arrray.
     *
     * @param PointXYZ
     * @param BbxXYZOffset
     * @return
     */
    public double[][] getBoundingBox(double[] PointXYZ, double[] BbxXYZOffset) {
        double result[][] = new double[2][3];
        result[0][0] = PointXYZ[0] - BbxXYZOffset[0];//Xmin
        result[0][1] = PointXYZ[1] - BbxXYZOffset[1];//Ymin
        result[0][2] = PointXYZ[2] - BbxXYZOffset[2];//Zmin
        result[1][0] = PointXYZ[0] + BbxXYZOffset[0];//Xmax
        result[1][1] = PointXYZ[1] + BbxXYZOffset[1];//Ymax
        result[1][2] = PointXYZ[2] + BbxXYZOffset[2];//Zmax

        return result;
    }

    public double[][] getXminumums(double[][] XYZs) {
        bubble_srt(XYZs, 0, true);
        double tempArr[][] = new double[XYZs.length / 2][XYZs[0].length];
        System.arraycopy(XYZs, 0, tempArr, 0, tempArr.length);
        System.out.println("Initial array length=" + XYZs.length + "\t Half length=" + tempArr.length);
        Vector resultVect = new Vector();
        double tempSlope = 0;
        for (int pointCount = 0; pointCount < tempArr.length - 1; pointCount++) {
            StraightLine line = new StraightLine();
            line.setMXpC(tempArr[0][0], tempArr[0][1], tempArr[pointCount + 1][0], tempArr[pointCount + 1][1], true);
            if (line.getSlopeAngle() < 0) {
                continue;
            }
            if (pointCount > 0) {
                // Math.round(tempDirn * 1000000) / 1000000
                if ((tempSlope - line.getSlopeAngle()) <= 0) {

                    System.out.println((tempSlope - line.getSlopeAngle()) + "--" + (Math.round((tempSlope - line.getSlopeAngle()) * 100000) / 100000));
                    resultVect.add(tempArr[pointCount]);
                }
            }
            tempSlope = line.getSlopeAngle();
        }

        double[][] result = new double[resultVect.size()][XYZs[0].length];
        for (int j = 0; j < resultVect.size(); j++) {
            result[j] = (double[]) resultVect.get(j);
        }
        return result;
    }

    /**
     * This function will delete points increasing the ThresholdDistance values.
     *
     * @param XYZs
     * @param ThresholdDistance
     * @return Vector containing curve data.
     */
    public Vector FilterCurvePoints(Vector XYZs, double ThresholdDistance) {
        /**
         * Psudo Code <br>1. Get all points on curve <br>2. Create line between the points and compare slope with privious <br>3. If slope deviation exceeds the
         * given value eleminate the point and use next point. <br>4. If in the end there is only one point then eleminate the curve.<br> Or<br> Use the
         * Smallest triangle perpendicular principal
         */
        Vector result = new Vector();
        result.addElement((double[]) XYZs.get(0));
        for (int j = 0; j < XYZs.size() - 2; j++) {
            if (Math.abs(new ProximityOperations().PerpDistance((double[]) XYZs.get(j), (double[]) XYZs.get(j + 1), (double[]) XYZs.get(j + 2), true)) < ThresholdDistance) {
                result.addElement((double[]) XYZs.get(j + 1));
            }
        }
        return result;
    }

    /**
     * This function will delete points exceeding the ThresholdDistance values.
     *
     * @param XYZs
     * @param ThresholdDistance
     * @return Vector containing curve data.
     */
    public Vector FilterCurvesPoints(Vector XYZs, double ThresholdDistance) {
        /**
         * Psudo Code <br>1. Get all points on curve <br>2. Create line between the points and compare slope with privious <br>3. If slope deviation exceeds the
         * given value eleminate the point and use next point. <br>4. If in the end there is only one point then eleminate the curve.<br> Or<br> Use the
         * Smallest triangle perpendicular principal
         */
        Vector result = new Vector();
        for (int j = 0; j < XYZs.size(); j++) {
            double XYZ[][] = (double[][]) XYZs.get(j);
            Vector tempCurveData = new Vector();
            tempCurveData.addElement(XYZ[0]);
            for (int i = 0; i < XYZ.length - 1; i++) {
                if (Math.abs(new ProximityOperations().PerpDistance(XYZ[i], XYZ[i + 1], XYZ[i + 2], true)) < ThresholdDistance) {
                    tempCurveData.addElement(XYZ[i + 1]);
                }
            }
            double[][] tempResult = new double[tempCurveData.size()][4];
            /**
             * Convert to the Array
             */
            for (int i = 0; i < tempCurveData.size(); i++) {
                tempResult[i] = (double[]) tempCurveData.get(i);
            }

            result.addElement(tempResult);
        }
        return result;
    }

    /**
     * Length of perpendicular to a line.<br>
     * |\ <--Point 1<br> |_\ Point 2<br>
     * |P/<br>
     * |/ <--Point 3<br>
     * @p
     *
     * aram xyz1
     * @param xyz2
     * @param xyz3
     * @return Length of perpendicular to a line
     */
    public double ExtrapolateCurveByY(double[][] XYZs, double Y_value, int refPoints, int StartPos, boolean debug) {
        /**
         * 1. Get Average slope of neighbouring points 
         * 2. Create line from last point and slope 
         * 3. Get value of X by substituting Y_value.
         */
        // double[][] result=new double[XYZs.length+1][XYZs[0].length];
        // System.arraycopy(XYZs, 0, result, 0, XYZs.length);
        StraightLine line = new StraightLine();
        line.setMC(XYZs[StartPos - 1][0], XYZs[StartPos - 1][1], getAverageSlopeFrom1stPoint(XYZs, refPoints, false, debug));
        //line.setMC(((double[])XYZs.lastElement())[0], ((double[])XYZs.lastElement())[1], getAverageSlope(XYZs,refPoints,false,debug));

        //result[XYZs.length]=(new double[]{line.getX(Y_value),Y_value,XYZs[XYZs.length-1][2]});

        System.out.println("Extrapolated value of X: " + line.getX(Y_value) + "\t Based on Y: " + Y_value);
        return line.getX(Y_value);
    }

    /**
     * Extrapolate curve based on average slope value and given Y value by refering refPoints.
     *
     * @param XYZs
     * @param Y_value
     * @param refPoints
     * @return A vector containing points.
     */
    public Vector ExtrapolateCurveByY(Vector XYZs, double Y_value, int refPoints, boolean debug) {
        /**
         * 1. Get Average slope of neighbouring points 2. Create line from last point and slope 3. Get value of X by substituting Y_value.
         */
        StraightLine line = new StraightLine();
        line.setMC(((double[]) XYZs.lastElement())[0], ((double[]) XYZs.lastElement())[1], getAverageSlopeFrom1stPoint(XYZs, refPoints, false, debug));
        //line.setMC(((double[])XYZs.lastElement())[0], ((double[])XYZs.lastElement())[1], getAverageSlope(XYZs,refPoints,false,debug));

        XYZs.addElement(new double[]{line.getX(Y_value), Y_value, ((double[]) XYZs.lastElement())[2]});
        System.out.println("Extrapolated value of X: " + line.getX(Y_value) + "\t Based on Y: " + Y_value);
        return XYZs;
    }

    /**
     *
     * @param XYZs
     * @param nPoints
     * @param fromStart
     * @param debug
     * @return
     */
    public double getAverageSlope(Vector XYZs, int nPoints, boolean fromStart, boolean debug) {
        double slope = 0;
        int count = 0;
        StraightLine line = new StraightLine();
        if (fromStart) {
            for (int j = 0; j < nPoints; j++) {

                line.setMXpC(((double[]) XYZs.get(j))[0], ((double[]) XYZs.get(j))[1], ((double[]) XYZs.get(j + 1))[0], ((double[]) XYZs.get(j + 1))[1], debug);
                slope = slope + line.m;
            }
        } else {
            for (int j = XYZs.size() - 1; j > XYZs.size() - nPoints; j--) {
                line.setMXpC(((double[]) XYZs.get(j))[0], ((double[]) XYZs.get(j))[1], ((double[]) XYZs.get(j - 1))[0], ((double[]) XYZs.get(j - 1))[1], debug);
                slope = slope + line.m;
            }
        }

        return slope / (nPoints - 1);
    }

    /**
     *
     * @param XYZs
     * @param nPoints
     * @param fromStart
     * @param debug
     * @return
     */
    public double getAverageSlopeFrom1stPoint(Vector XYZs, int nPoints, boolean fromStart, boolean debug) {
        double slope = 0;
        int count = 0;
        StraightLine line = new StraightLine();
        if (fromStart) {
            for (int j = 0; j < nPoints; j++) {
                line.setMXpC(((double[]) XYZs.get(j))[0], ((double[]) XYZs.get(j))[1], ((double[]) XYZs.get(j + 1))[0], ((double[]) XYZs.get(j + 1))[1], debug);
                slope = slope + line.m;
            }
        } else {
            for (int j = XYZs.size() - 1; j > XYZs.size() - nPoints; j--) {
                line.setMXpC(((double[]) XYZs.get(0))[0], ((double[]) XYZs.get(0))[1], ((double[]) XYZs.get(j - 1))[0], ((double[]) XYZs.get(j - 1))[1], debug);
                slope = slope + line.m;
            }
        }
        return slope / (nPoints - 1);
    }

    public double getAverageSlopeFrom1stPoint(double[][] XYZs, int nPoints, boolean fromStart, boolean debug) {
        double slope = 0;
        int count = 0;
        StraightLine line = new StraightLine();
        if (fromStart) {
            for (int j = 0; j < nPoints; j++) {

                line.setMXpC(XYZs[j][0], XYZs[j][1], XYZs[j + 1][0], XYZs[j + 1][1], debug);
                slope = slope + line.m;
            }
        } else {
            for (int j = XYZs.length - 1; j > XYZs.length - nPoints; j--) {
                line.setMXpC(XYZs[0][0], XYZs[0][1], XYZs[j - 1][0], XYZs[j - 1][1], debug);
                slope = slope + line.m;
            }
        }
        if (debug) {
            System.out.println("Average Slope=" + (slope / (nPoints - 1)));
        }
        return slope / (nPoints - 1);
    }
}

class Sort2D {

    public static void main(String[] args) {
        double[][] testcase = {{1, 3, 4, 5}, {9, 8, 7, 6}, {6, 10, 60, 50}, {-1, 555, 12, 11}, {4, 4, 4, 6}};
        printArr(testcase);
        System.out.println();
        sort(testcase);
        printArr(testcase);
    }

    static void printArr(double[][] A) {
        int i, j;
        for (i = 0; i < A.length; i++) {
            for (j = 0; j < A[i].length; j++) {
                System.out.print(A[i][j] + "\t");
            }
            System.out.println();
        }
    }

    static void printArr(double[] A) {
        int i, j;
        for (i = 0; i < A.length; i++) {

            System.out.print(A[i] + "\t");


        }
        System.out.println();
    }

    static void sort(double[][] A) {
        int i, j, m = A.length, n = A[0].length;
        double B[] = new double[m * n];
        for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) {
                B[i * n + j] = A[i][j];
            }
        }
        java.util.Arrays.sort(B);
        for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) {
                A[i][j] = B[i * n + j];
            }
        }
    }
}
