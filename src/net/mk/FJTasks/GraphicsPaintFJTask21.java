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
public class GraphicsPaintFJTask21  extends RecursiveTask {
    Graphics g;
    int c[];
    int v[];
    int Start;
    int end;
    int lg = 0;
    public GraphicsPaintFJTask21(int c[],int v[],int Start,int end) {
        //this.g = g;
        this.c = c;
        this.v = v;
        this.Start = Start;
        this.end = end;
          
    }

    public Vector compute() {
        long begTest = new java.util.Date().getTime();
        Vector result=new Vector();
        for (int i = Start; i <end; i++) {
           int grey =Math.min(v[i+1], v[i+5]);
            if (grey < 0) {
                grey = 0;
            }
            if (grey >= 0) {
                grey = 15;
            }
            
            if (grey != lg) {
                lg = grey;
             }
             //System.out.println("ncon= "+ncon+" con="+v.length+" tvert="+v.length);
             int result2[]=new int[5];
            result2[0]=v[i];
            result2[1]=v[i+1];
            result2[2]=v[i+3];
            result2[3]=v[i+4];
            result2[4]=grey;
             result.add(result2);
        }
       //System.out.println("\n GraphicsPaintFJTask2 result.size="+result.size() +" Start point="+Start);
        return result;
    }
    
}
