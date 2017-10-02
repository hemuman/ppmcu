/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.FJTasks;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author PDI
 */
public class ExtremeXYFJTask  extends RecursiveTask{
    double[][] PointsCluster;
    double YAccuracy;
    double ScatterThreshold;
    double YMax;
    double Ymin;
    boolean message;
    public ExtremeXYFJTask(double[][] PointsCluster, boolean message){
        this.PointsCluster=PointsCluster;
        this.message=message;

    }
   
    @Override
    protected double[][] compute() {
        
        Arrays.sort(PointsCluster, new Comparator<double[]>() {
                    @Override
                    public int compare(final double[] entry1, final double[] entry2) {
                        final double time1 = entry1[0];
                        final double time2 = entry2[0];
                        return time1 > time2 ? 0 : -1;
                    }
                });
        
        return PointsCluster;
    
    }
    
}
