/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu2D;

import net.mk.FJTasks.GraphicsPaintFJTask2;
import net.mk.FJTasks.GraphicsPaintFJTask1;
import net.mk.FJTasks.GraphicsPaintFJTask3;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import net.mk.FJTasks.GraphicsPaintFJTask21;

/**
 *
 * @author PDI
 */
public class MeshCreater3D {
    
     public int[][] getPointsppmcu(int c[],int v[],int ncon ,int TaskQty){
       
       int nrOfProcessors = Runtime.getRuntime().availableProcessors();
        
        int[][] result = null;//=getPoints();
        int range;//nrOfProcessors;
        if(TaskQty>nrOfProcessors)
         range = ncon/TaskQty;
        else{
            range = ncon/nrOfProcessors;
        }

        List futuresList = new ArrayList();
        ForkJoinPool fjPool = new ForkJoinPool(nrOfProcessors);
        // System.out.println(ncon+" ncons");
        for (int numCount = 0; numCount <nrOfProcessors; numCount++) {
          //System.out.print(numCount+"_");
            futuresList.add(fjPool.submit( new GraphicsPaintFJTask2( c,  v,range*numCount, range*(numCount+1))));
           // System.out.println(range*(numCount+1)+" ncons");
        }

        Vector tempresult=new Vector();
       // System.out.println("#ThreeD.paint() Start collecting results");
        for (Object future : futuresList) {
            try {
                result =  convertTo2DIntArray((Vector)((Future)future).get());
                tempresult.addAll(Arrays.asList(result));
                
            } catch (    InterruptedException | ExecutionException e) {
            }
        }
        
        result=convertTo2DIntArray(tempresult);
           return result;
           
    }
    
     public int[][] getPointsppmcu2(int c[],int v[],int ncon ,int TaskQty){
       
       int nrOfProcessors = Runtime.getRuntime().availableProcessors();
        
        int[][] result = null;//=getPoints();
        int range;//nrOfProcessors;
        if(TaskQty>nrOfProcessors)
         range = ncon/TaskQty;
        else{
            range = ncon/nrOfProcessors;
        }

        List futuresList = new ArrayList();
        ForkJoinPool fjPool = new ForkJoinPool(nrOfProcessors);
         //System.out.println(ncon+" ncons");
        for (int numCount = 0; numCount <nrOfProcessors; numCount++) {
          //System.out.print(numCount+"_");
            futuresList.add(fjPool.submit( new GraphicsPaintFJTask21( c,  v,range*numCount, range*(numCount+1))));
            //System.out.println(range*(numCount+1)+" ncons");
        }

        Vector tempresult=new Vector();
       // System.out.println("#ThreeD.paint() Start collecting results");
        for (Object future : futuresList) {
            try {
                result =  convertTo2DIntArray((Vector)((Future)future).get());
                tempresult.addAll(Arrays.asList(result));
                
            } catch (    InterruptedException | ExecutionException e) {
            }
        }
        
        result=convertTo2DIntArray(tempresult);
           return result;
           
    }
    
     public int[][] getRandomLinesppmcu(int Quantity,int[] XRange,int[] YRange,int[] ZRange ){
       
         long begTest = new java.util.Date().getTime();
         Double secs;
       int nrOfProcessors = Runtime.getRuntime().availableProcessors();
        
        int[][] result = null;//=getPoints();
        int range=Quantity/1000;//nrOfProcessors;
        List futuresList = new ArrayList();
        ForkJoinPool fjPool = new ForkJoinPool(nrOfProcessors);
        for (int numCount = 0; numCount <nrOfProcessors; numCount++) {
          // System.out.print(numCount+"_");
            futuresList.add(fjPool.submit( new GraphicsPaintFJTask1( range, XRange,YRange,ZRange)));
        }
       
        int[] taskResult;
        Vector tempresult=new Vector();
       // System.out.println("#ThreeD.paint() Start collecting results");
        for (Object future : futuresList) {
            try {
                result =  convertTo2DIntArray((Vector)((Future)future).get());
                tempresult.addAll(Arrays.asList(result));
                
            } catch (    InterruptedException | ExecutionException e) {
            }
        }
        
        result=convertTo2DIntArray(tempresult);
        secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("#MeshCreater3D Multi core paint time " + secs + " secs");
           return result;
           
    }
     
      public int[][] getRandomLines(int Quantity,int[] XRange,int[] YRange,int[] ZRange ){
       
         long begTest = new java.util.Date().getTime();
         Double secs;
          int[][] result = null;//=getPoints();
          int MaxX;
          int MinX;
          int MaxY;
          int MinY;
          int MaxZ;
          int MinZ;
          MaxX = XRange[0];
          MinX = XRange[1];
          MaxY = YRange[0];
          MinY = YRange[1];
          MaxZ = ZRange[0];
          MinZ = ZRange[1];

//int[] taskResult;
          Vector taskResult = new Vector();
          for (int i = 0; i < Quantity; i++) {
              int result2[] = new int[5];
              result2[0] = (int) (Math.random() * (double) (MaxX - MinX));
              result2[1] = (int) (Math.random() * (double) (MaxY - MinY));
              result2[2] = (int) (Math.random() * (double) (MaxX - MinX));
              result2[3] = (int) (Math.random() * (double) (MaxY - MinY));
              result2[4] = (int) (Math.random() * (double) (MaxZ - MinZ));
              taskResult.add(result2);
          }

          result = convertTo2DIntArray(taskResult);
          secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("#MeshCreater3D Single core paint time " + secs + " secs");
          return result;
           
    }
     
     public int[][] getPointsppmcu3(int c[],int v[],int ncon ){
       
       int nrOfProcessors = Runtime.getRuntime().availableProcessors();
        
        int[][] result;//=getPoints();
        int range=ncon/nrOfProcessors;
        List futuresList = new ArrayList();
        ForkJoinPool fjPool = new ForkJoinPool(nrOfProcessors);
        for (int numCount = 0; numCount <nrOfProcessors; numCount++) {
           System.out.print(numCount+"_");
            futuresList.add(fjPool.submit( new GraphicsPaintFJTask3( )));
        }
       
        Vector taskResult=new Vector();
       System.out.println("#getPointsppmcu3 Start collecting results");
        for (Object future : futuresList) {
            try {
                result =  (int[][]) ((Future)future).get();
                taskResult.addAll(Arrays.asList(result));
                
            } catch (    InterruptedException | ExecutionException e) {
            }
        }
        
        result=convertTo2DIntArray(taskResult);
           return result;
           
    }
    
      public int[] convertTo1DIntArray(Vector vectorContainingInt){
        int[] result =new int[vectorContainingInt.size()];
        for(int i=0;i<result.length;i++){
        result[i]=(int) vectorContainingInt.get(i);
        }
        return result;
    }

       public int[][] convertTo2DIntArray(Vector vectorContainingInt){
        int[][] result =new int[vectorContainingInt.size()][5];
        for(int i=0;i<result.length;i++){
        result[i]=(int[]) vectorContainingInt.get(i);
        }
        return result;
    }
    
}
