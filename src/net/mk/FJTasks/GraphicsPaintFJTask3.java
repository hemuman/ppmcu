/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.FJTasks;

import java.awt.Graphics;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author PDI
 */
public class GraphicsPaintFJTask3  extends RecursiveTask {
    Graphics g;
    int c[];
    int v[];
    int lg = 0;
    public GraphicsPaintFJTask3(/*int c[],int v[]*/) {
        //this.g = g;
        this.c = c;
        this.v = v; 
    }

    public int[][] compute() {
        long begTest = new java.util.Date().getTime();
        int[][] result=new int[v.length][5];
        for (int i = 0; i <v.length; i++) {
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
            result[i][0]=v[p1];
            result[i][1]=v[p1+1];
            result[i][2]=v[p2];
            result[i][3]=v[p2+1];
            result[i][4]=grey;

        }
        System.out.println("\n GraphicsPaintFJTask3 result.size="+result.length);
        return result;
    }
    
}
