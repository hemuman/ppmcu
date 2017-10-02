/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkMath;

/**
 *
 * @author PDI
 */
public class QuickSort {

    public static void main(String a[]) {
        int i;
        int array[] = {12, 9, 4, 99, 120, 1, 3, 10, 13};

        double array_[][]={{12, 9},{ 4, 99}, {120, 1},{ 3, 10, 13}};
        
        System.out.println(" Quick Sort\n\n");
        System.out.println("Values Before the sort:\n");
        for (i = 0; i < array.length; i++) {
            System.out.print(array[i] + "  ");
        }
        System.out.println();
        quick_srt(array_, 0, array_.length - 1,0);
        System.out.print("Values after the sort:\n");
        for (i = 0; i < array_.length; i++) {
            System.out.print(array_[i][0] + "  ");
        }
        System.out.println();
        System.out.println("PAUSE");
        
        
    }

    public static void quick_srt(int array[], int low, int n) {
        int lo = low;
        int hi = n;
        if (lo >= n) {
            return;
        }
        int mid = array[(lo + hi) / 2];
        while (lo < hi) {
            while (lo < hi && array[lo] < mid) {
                lo++;
            }
            while (lo < hi && array[hi] > mid) {
                hi--;
            }
            if (lo < hi) {
                int T = array[lo];
                array[lo] = array[hi];
                array[hi] = T;
            }
        }
        if (hi < lo) {
            int T = hi;
            hi = lo;
            lo = T;
        }
        quick_srt(array, low, lo);
        quick_srt(array, lo == low ? lo + 1 : lo, n);
    }

    
    public static void quick_srt(double array[][], int low, int n,int columnToSort) {
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
        }
        if (hi < lo) {
            int T = hi;
            hi = lo;
            lo = T;
        }
        quick_srt(array, low, lo,columnToSort);
        quick_srt(array, lo == low ? lo + 1 : lo, n,columnToSort);
    }

}