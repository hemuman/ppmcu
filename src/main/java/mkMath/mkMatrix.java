/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkMath;

/**
 *
 * @author PDI
 */
public class mkMatrix {
/**
 * This is a simple java program that teaches you for multiplying two matrix to each other.
 * Here providing you Java source code with understanding the Java developing application
 * program. We are going to make a simple program that will multiply two matrix.
 * Two dimensional array represents the matrix.
 * Now, make this program, you have to declare two multidimensional array of type
 * integer. Program uses two for loops to get number of rows and columns by using
 * the array1.length. After getting both matrix then multiply to it. Both matrix will be
 * multiplied to each other by using 'for' loop. So the output will be displayed on the screen
 * command prompt by using the println() method.
 * @param Matrix1
 * @param Matrix2
 * @return
 */
    public double[][] Multiply2D(double[][] Matrix1, double[][] Matrix2) {
        double Result[][] = new double[Matrix1[0].length][Matrix2.length];
        double x = Matrix1.length;
        System.out.println("Matrix 1 : ");
        for (int i = 0; i < x; i++) {
            for (int j = 0; j <= x; j++) {
                System.out.print(" " + Matrix1[i][j]);
            }
            System.out.println();
        }
        int y = Matrix2.length;
        System.out.println("Matrix 2 : ");
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < y - 1; j++) {
                System.out.print(" " + Matrix2[i][j]);
            }
            System.out.println();
        }

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y - 1; j++) {
                for (int k = 0; k < y; k++) {

                    Result[i][j] += Matrix1[i][k] * Matrix2[k][j];
                }
            }
        }
        System.out.println("Multiply of both matrix : ");
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y - 1; j++) {
                System.out.print(" " + Result[i][j]);
            }
            System.out.println();
        }
        return Result;
    }

    /**
     * An Example to display the result
     * @return
     */
    public double[][] Multiply2DExample() {
        double Matrix1[][] = {{5, 6, 7}, {4, 8, 9}};
        double Matrix2[][] = {{6, 4}, {5, 7}, {1, 1}};
        double Result[][] = new double[3][3];
        double x = Matrix1.length;
        System.out.println("Matrix 1 : ");
        for (int i = 0; i < x; i++) {
            for (int j = 0; j <= x; j++) {
                System.out.print(" " + Matrix1[i][j]);
            }
            System.out.println();
        }
        int y = Matrix2.length;
        System.out.println("Matrix 2 : ");
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < y - 1; j++) {
                System.out.print(" " + Matrix2[i][j]);
            }
            System.out.println();
        }

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y - 1; j++) {
                for (int k = 0; k < y; k++) {

                    Result[i][j] += Matrix1[i][k] * Matrix2[k][j];
                }
            }
        }
        System.out.println("Multiply of both matrix : ");
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y - 1; j++) {
                System.out.print(" " + Result[i][j]);
            }
            System.out.println();
        }
        return Result;
    }
    
    /**
     * In this section, we are going to calculate the sum of two matrix and containing its rows and columns.
     * See below for better understanding to this.In this program we are going to calculate the sum of two
     * matrix. To make this program, we need to declare two dimensional array of type integer. Firstly it
     * calculates the length of the both the arrays. Now we need to make a matrix out of it. To make the matrix we
     * will use the for loop. By making use of the for loop the rows and column will get divide. This process will
     * be performed again for creating the second matrix.
     * After getting both the matrix with us, we need to sum both the matrix. The both matrix will be added by using
     * the for loop with array[i][j]+array1[i][j]. The output will be displayed by using the println() method.
     */
    public void Add2DExample(){
      int array[][]= {{4,5,6},{6,8,9}};
    int array1[][]= {{5,4,6},{5,6,7}};
    System.out.println("Number of Row= " + array.length);
    System.out.println("Number of Column= " + array[1].length);
    int l= array.length;
    System.out.println("Matrix 1 : ");
      for(int i = 0; i < l; i++) {
      for(int j = 0; j <= l; j++) {
        System.out.print(" "+ array[i][j]);
      }
    System.out.println();
        }
    int m= array1.length;
    System.out.println("Matrix 2 : ");
      for(int i = 0; i < m; i++) {
      for(int j = 0; j <= m; j++) {
        System.out.print(" "+array1[i][j]);
      }
    System.out.println();
        }
    System.out.println("Addition of both matrix : ");
      for(int i = 0; i < m; i++) {
      for(int j = 0; j <= m; j++) {
        System.out.print(" "+(array[i][j]+array1[i][j]));
      }
    System.out.println();
        }
    }
}
