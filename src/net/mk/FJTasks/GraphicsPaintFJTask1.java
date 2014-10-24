/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.FJTasks;

import java.awt.Graphics;
import java.util.Vector;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author PDI
 */
public class GraphicsPaintFJTask1  extends RecursiveTask {
    Graphics g;
    int v[][];
    int Start;
    int Quantity;
    int lg = 0;
    int MaxX;
    int MinX;
    int MaxY;
    int MinY;
    int MaxZ;
    int MinZ;
    public GraphicsPaintFJTask1(int Quantity,int MaxX,int MinX,int MaxY,int MinY,int MaxZ,int MinZ) {
        this.Quantity = Quantity;
        this.MaxX=MaxX;
        this.MinX=MinX;
        this.MaxY=MaxY;
        this.MinY=MinY;
        this.MaxZ=MaxZ;
        this.MinZ=MinZ;
          
    }
    
     public GraphicsPaintFJTask1(int Quantity,int[] XRange,int[] YRange,int[] ZRange) {
        this.Quantity = Quantity;
        this.MaxX=XRange[0];
        this.MinX=XRange[1];
        this.MaxY=YRange[0];
        this.MinY=YRange[1];
        this.MaxZ=ZRange[0];
        this.MinZ=ZRange[1];
          
    }

    public Vector compute() {
        long begTest = new java.util.Date().getTime();
        Vector result=new Vector();
        for (int i = 0; i <Quantity; i++) {
           int result2[]=new int[5];
           result2[0]=(int)(Math.random()*(double)(MaxX-MinX));
          result2[1]=(int)(Math.random()*(double)(MaxY-MinY));
            result2[2]=(int)(Math.random()*(double)(MaxX-MinX));
            result2[3]=(int)(Math.random()*(double)(MaxY-MinY));
           result2[4]=(int)(Math.random()*(double)(MaxZ-MinZ));
            result.add(result2);
        }
        //System.out.print("\n");
        return result;
    }
    
}
