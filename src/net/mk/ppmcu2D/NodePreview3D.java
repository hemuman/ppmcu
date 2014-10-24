/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import mkMath.ParametricCurve;
import net.mk.ppmcu.GlobalMCUTest;
import net.mk.ppmcu.PrimeNumbersFinder;

/**
 *
 * @author PDI
 */
public class NodePreview3D {
    float vert[];
    int tvert[];
    int nvert, maxvert;
    int con[];
    int ncon, maxcon;
    boolean transformed;
    Matrix3D mat;
    float xmin, xmax, ymin, ymax, zmin, zmax;
    public boolean MULTI_CORE_REGEN=true,SINGLE_CORE_REGEN=true;
    public boolean SHOW_CURVE=false;
    public int MultiCoreSwitch=900000;
double NodePoints=3000.0;
    //Images in node
    CircularLinkedList cll = new CircularLinkedList();

    public void initCLL() {

        //cll.insertFirst(UIToolKit.getWin7StyleRect(100, 100, 1.0f, true));
        for (int i = 0; i < 100; i++) {

            cll.insertFirst(UIToolKit.getWithShadow(UIToolKit.getWin7StyleRect2(50, 50, 1.0f, true), 1.0f, true));
        }
        for (int i = 0; i < 100; i++) {

            cll.insertFirst(UIToolKit.getWithShadow(UIToolKit.getWin7StyleRect2(50, 50, 1.0f, true), 1.0f, true));
        }

    }
    public NodePreview3D() {
        mat = new Matrix3D();
        mat.xrot(20);
        mat.yrot(30);
    }

    /** Create a 3D model by parsing an input stream */
   public NodePreview3D( int test) throws IOException, FileFormatException {
       this();
       initCLL();
       int[] result = ParametricCurve.RandomCurveEample(0, 200, 100,test);
       addVert((float) result[0], (float) result[1], (float) result[2]);
       add(0, 1);
       for (int i = 0; i < 10000; i++) {
           result = ParametricCurve.RandomCurveEample(i / 10000.0, 200, 100,test);
           addVert((float) result[0], (float) result[1], (float) result[2]);
           add(i , i+1);
       }

    }

    /** Add a vertex to this model */
    public int addVert(float x, float y, float z) {
        int i = nvert;
        if (i >= maxvert) {
            if (vert == null) {
                maxvert = 100;
                vert = new float[maxvert * 3];
            } else {
                maxvert *= 2;
                float nv[] = new float[maxvert * 3];
                System.arraycopy(vert, 0, nv, 0, vert.length);
                vert = nv;
            }
        }
        i *= 3;
        vert[i] = x;
        vert[i + 1] = y;
        vert[i + 2] = z;
        return nvert++;
    }

    /** Add a line from vertex p1 to vertex p2 */
    public void add(int p1, int p2) {
        int i = ncon;
        if (p1 >= nvert || p2 >= nvert) {
            return;
        }
        if (i >= maxcon) {
            if (con == null) {
                maxcon = 100;
                con = new int[maxcon];
            } else {
                maxcon *= 2;
                int nv[] = new int[maxcon];
                System.arraycopy(con, 0, nv, 0, con.length);
                con = nv;
            }
        }
        if (p1 > p2) {
            int t = p1;
            p1 = p2;
            p2 = t;
        }
        con[i] = ncon;//(p1 << 16) | p2;
        ncon = i + 1;
    }

    /** Transform all the points in this model */
    public void transform() {
        if (transformed || nvert <= 0) {
            return;
        }
        if (tvert == null || tvert.length < nvert * 3) {
            tvert = new int[nvert * 3];
        }
        mat.transform(vert, tvert, nvert);
        transformed = true;
    }

    /* Quick Sort implementation
     */
    private void quickSort(int a[], int left, int right) {
        int leftIndex = left;
        int rightIndex = right;
        int partionElement;
        if (right > left) {

            /* Arbitrarily establishing partition element as the midpoint of
             * the array.
             */
            partionElement = a[(left + right) / 2];

            // loop through the array until indices cross
            while (leftIndex <= rightIndex) {
                /* find the first element that is greater than or equal to
                 * the partionElement starting from the leftIndex.
                 */
                while ((leftIndex < right) && (a[leftIndex] < partionElement)) {
                    ++leftIndex;
                }

                /* find an element that is smaller than or equal to
                 * the partionElement starting from the rightIndex.
                 */
                while ((rightIndex > left) && (a[rightIndex] > partionElement)) {
                    --rightIndex;
                }

                // if the indexes have not crossed, swap
                if (leftIndex <= rightIndex) {
                    swap(a, leftIndex, rightIndex);
                    ++leftIndex;
                    --rightIndex;
                }
            }

            /* If the right index has not reached the left side of array
             * must now sort the left partition.
             */
            if (left < rightIndex) {
                quickSort(a, left, rightIndex);
            }

            /* If the left index has not reached the right side of array
             * must now sort the right partition.
             */
            if (leftIndex < right) {
                quickSort(a, leftIndex, right);
            }

        }
    }

    private void swap(int a[], int i, int j) {
        int T;
        T = a[i];
        a[i] = a[j];
        a[j] = T;
    }

    /** eliminate duplicate lines */
    public void compress() {
        int limit = ncon;
        int c[] = con;
        quickSort(con, 0, ncon - 1);
        int d = 0;
        int pp1 = -1;
        for (int i = 0; i < limit; i++) {
            int p1 = c[i];
            if (pp1 != p1) {
                c[d] = p1;
                d++;
            }
            pp1 = p1;
        }
        ncon = d;
        
        
//          getPoints();
//        getPointsppmcu();
    }
    static Color gr[];

    /** Paint this model to a graphics context.  It uses the matrix associated
    with this model to map from model space to screen space.
    The next version of the browser should have double buffering,
    which will make this *much* nicer */
    
    public int[][] getPoints(){
        int lg = 0;
        int lim = ncon;
        int c[] = con;
        int v[] = tvert;
        //System.out.println("ncon= "+ncon+" con="+con.length+" tvert="+v.length);
        int result[][]=new int[lim][5];
           for (int i = 0; i < lim-3; i++) {
            int T = c[i];
           int p1 = i*3;//((T >> 16) & 0xFFFF) * 3;
           int p2 = i*3+3;//(T & 0xFFFF) * 3;
           //System.out.println("T= "+T+" p1="+p1+" p2="+p2);
            int grey =Math.min(v[p1+2], v[p2+2]);
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
            result[i][0]=v[p1];
            result[i][1]=v[p1+1];
            result[i][2]=v[p2];
            result[i][3]=v[p2+1];
            result[i][4]=grey;

        }
           return result;
           
    }

    public void paint(Graphics g) {
        
        //new GlobalMCUTest().getPrimeNumberTestData();
        
        g.drawString("Rotate by Click and Drag",230, 10);
       
        if (vert == null || nvert <= 0) {
            return;
        }
        transform();
        if (gr == null) {
            gr = new Color[16];
            for (int i = 0; i < 16; i++) {
                int grey = (int) (170 * (1 - Math.pow(i / 15.0, 2.3)));
                gr[i] = new Color(grey, grey, grey);
            }
        }
        int lg = 0;
        int lim = ncon;
        int c[] = con;
        int v[] = tvert;
        
        //System.out.println(ncon+"\t"+con.length+"\t"+tvert.length);
        
        if (lim <= 0 || nvert <= 0) {
            return;
        }
        
        /**
         * PPMCU Implementation, NThread Task
         */
        long begTest = new java.util.Date().getTime();
        Double secs;
        int[][] result;
       // int[][] result=new MeshCreater3D().getRandomLinesppmcu(100000,new int[]{(int)xmax,(int)xmin+50},new int[]{(int)ymax,(int)ymin+20},new int[]{(int)zmax,(int)zmin});
         if(v.length>=MultiCoreSwitch){ // Use multiple processors
        result = new MeshCreater3D().getPointsppmcu2(c, v, v.length,8);
        
        for (int i = 0; i <result.length; i++) {
            int grey = result[i][4];
            g.setColor(gr[grey]);
            g.setColor(UIToolKit.KhakeeTheme()[1]);
           g.drawLine(result[i][0], result[i][1],result[i][2], result[i][3]);
           // g.fillOval(result[i][0], result[i][1], 10, 10);
            //g.fillOval(result[i][2], result[i][3], 10, 10);
            //System.out.print("_"+i);
        }
        secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        g.drawString("Multi Core Time "+secs+" secs"/* \t taskResult Size="+result.length */,10, 35);//+"Jump"+ncon/nrOfProcessors
        //System.out.println("#Model3D multi core paint time " + secs + " secs");
         } else {  //Single thread task
        begTest = new java.util.Date().getTime();
        result=getPoints();
        //result=new MeshCreater3D().getRandomLines(100000,new int[]{(int)xmax,(int)xmin+50},new int[]{(int)ymax,(int)ymin+20},new int[]{(int)zmax,(int)zmin});
        
          for (int i = 0; i <result.length; i++) {
            int grey = result[i][4];
           g.setColor(gr[grey]);
            g.setColor(UIToolKit.KhakeeTheme()[1]);
            if(SHOW_CURVE)
            g.drawLine(result[i][0], result[i][1],result[i][2], result[i][3]);
            
//Preview images
                if (i % 100 == 0) {
                    if (cll.goToNextElement()) {
                        
                        g.drawImage((BufferedImage) cll.getActualElementData(), result[i][0], result[i][1], null);
                        //g.drawString("#"+i, result[i][0], result[i][1]+15);
                    }
                }
            //System.out.print("_"+i);
        }

        secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        
        g.drawString("Single Core Time "+secs+" secs"/* \t result.Size="+result.length*/,10, 20);
       // System.out.println("#Model3D single core paint time " + secs + " secs");
          }
    }

    /** Find the bounding box of this model */
    public void findBB() {
        if (nvert <= 0) {
            return;
        }
        float v[] = vert;
        float _xmin = v[0], _xmax = _xmin;
        float _ymin = v[1], _ymax = _ymin;
        float _zmin = v[2], _zmax = _zmin;
        for (int i = nvert * 3; (i -= 3) > 0;) {
            float x = v[i];
            if (x < _xmin) {
                _xmin = x;
            }
            if (x > _xmax) {
                _xmax = x;
            }
            float y = v[i + 1];
            if (y < _ymin) {
                _ymin = y;
            }
            if (y > _ymax) {
                _ymax = y;
            }
            float z = v[i + 2];
            if (z < _zmin) {
                _zmin = z;
            }
            if (z > _zmax) {
                _zmax = z;
            }
        }
        this.xmax = _xmax;
        this.xmin = _xmin;
        this.ymax = _ymax;
        this.ymin = _ymin;
        this.zmax = _zmax;
        this.zmin = _zmin;
    }
}
