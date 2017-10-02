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
public class GraphicsPaintFJTask2  extends RecursiveTask {
    Graphics g;
    int c[];
    int v[];
    int Start;
    int end;
    int lg = 0;
    public GraphicsPaintFJTask2(int c[],int v[],int Start,int end) {
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
            int T = c[i];
            int p1 = ((T >> 16) & 0xFFFF) * 3;
            int p2 = (T & 0xFFFF) * 3;
            int grey = v[p1 + 2] + v[p2 + 2];
            if (grey < 0) {
                grey = 0;
            }
            if (grey > 15) {
                grey = 15;
            }
            int result2[]=new int[5];
           result2[0]=v[p1];
          result2[1]=v[p1+1];
            result2[2]=v[p2];
            result2[3]=v[p2+1];
           result2[4]=grey;
            result.add(result2);
//            if (grey != lg) {
//                lg = grey;
//                g.setColor(gr[grey]);
//            }
//            g.setColor(Color.BLACK);
//            g.drawLine(v[p1], v[p1 + 1],
//                    v[p2], v[p2 + 1]);
            //System.out.print("_"+i);
        }
       //System.out.println("\n GraphicsPaintFJTask2 result.size="+result.size() +" Start point="+Start);
        return result;
    }
    
}
