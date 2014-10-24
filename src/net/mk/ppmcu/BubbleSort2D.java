/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu;

/**
 *
 * @author PDI
 */

import java.util.concurrent.RecursiveTask;

public class BubbleSort2D extends RecursiveTask {

   double XYZArray[][];
   int ColumnToSort;
   boolean Increasing;
/**
 * This will sort the array provided in input. Yet to be tested. 4/5/2013
 * @param XYZArray
 * @param ColumnToSort
 * @param Increasing 
 */
    public BubbleSort2D(double XYZArray[][], int ColumnToSort, boolean Increasing) {
        this.XYZArray = XYZArray;
        this.ColumnToSort = ColumnToSort;
        this.Increasing = Increasing;
    }

    public Integer compute() {
        
        long begTest = new java.util.Date().getTime();
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
        Double secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("Manoj run time " + secs + " secs");
        return 0;
    }

}
