/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.FJTasks;

/**
 *
 * @author manoj kumar
 */
import java.util.List;
import static java.util.concurrent.ForkJoinTask.invokeAll;
import java.util.concurrent.RecursiveAction;

public class QuickSortFJTask<T extends Comparable> extends RecursiveAction {

    private List<T> data;
    private int left;
    private int right;
    private double[][] DataToSort;
    private int ColumnToSort = 0;

    public QuickSortFJTask(double[][] DataToSort, int ColumnToSort) {
        this.DataToSort = DataToSort;
        this.left = 0;
        this.ColumnToSort = ColumnToSort;
        this.right = DataToSort.length - 1;
    }

    public QuickSortFJTask(double[][] DataToSort, int ColumnToSort, int left, int right) {
        this.DataToSort = DataToSort;
        this.ColumnToSort = ColumnToSort;
        this.left = left;
        this.right = right;
    }

    public QuickSortFJTask(List<T> data) {
        this.data = data;
        this.left = 0;
        this.right = data.size() - 1;
    }

    public QuickSortFJTask(List<T> data, int left, int right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {

        if (data != null) {
            if (left < right) {
                int pivotIndex = left + ((right - left) / 2);

                pivotIndex = partition(pivotIndex);

                invokeAll(new QuickSortFJTask(data, left, pivotIndex - 1),
                        new QuickSortFJTask(data, pivotIndex + 1, right));
            }
        }

        if (DataToSort != null) {
            if (left < right) {
                int pivotIndex = left + ((right - left) / 2);

                pivotIndex = partition(pivotIndex);

                invokeAll(new QuickSortFJTask(DataToSort, ColumnToSort, left, pivotIndex - 1),
                        new QuickSortFJTask(DataToSort, ColumnToSort, pivotIndex + 1, right));
            }
        }

    }

    private int partition(int pivotIndex) {
        int storeIndex = 0;
        if (data != null) {
            T pivotValue = data.get(pivotIndex);

            swap(pivotIndex, right);

            storeIndex = left;
            for (int i = left; i < right; i++) {
                if (data.get(i).compareTo(pivotValue) < 0) {
                    swap(i, storeIndex);
                    storeIndex++;
                }
            }

            swap(storeIndex, right);
        }

        if (DataToSort != null) {
            double pivotValue = DataToSort[pivotIndex][ColumnToSort];
            swap(pivotIndex, right);
            storeIndex = left;
            for (int i = left; i < right; i++) {
                if (DataToSort[i][ColumnToSort] < pivotValue) {
                    swap(i, storeIndex);
                    storeIndex++;
                }
            }
            swap(storeIndex, right);
        }
        return storeIndex;
    }

    private void swap(int i, int j) {
        if (data != null) {
            if (i != j) {
                T iValue = data.get(i);

                data.set(i, data.get(j));
                data.set(j, iValue);
            }
        }
        if (DataToSort != null) {
            if (i != j) {
                double iValue = DataToSort[i][ColumnToSort];
                DataToSort[i][ColumnToSort] = DataToSort[j][ColumnToSort];
                DataToSort[j][ColumnToSort] = iValue;
            }
        }
    }
}
