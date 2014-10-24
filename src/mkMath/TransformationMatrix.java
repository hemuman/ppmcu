/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mkMath;

/**
 *
 * @author PDI
 */
public class TransformationMatrix {

                public double xx = 1.0;
                public double xy = 0.0;
                public double xz = 0.0;
                public double yx = 0.0;
                public double yy = 1.0;
                public double yz = 0.0;
                public double zx = 0.0;
                public double zy = 0.0;
                public double zz = 1.0;

                public double[][] getMatrix(){
                double[][] result=new double[3][3];
                result[0][0]=xx;
                result[0][1]=xy;
                result[0][2]=xz;
                result[1][0]=yx;
                result[1][1]=yy;
                result[1][2]=yz;
                result[2][0]=zx;
                result[2][1]=zy;
                result[2][2]=zz;
                    return result;
                }


}
