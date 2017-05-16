/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jjil.algorithm.RgbAvgGray;
import jjil.core.Rect;
import jjil.core.RgbImage;
import jjil.j2se.RgbImageJ2se;
import net.mk.JJIL.Gray8DetectHaarMultiScale;
import net.mk.JJIL.Main;


/**
 *
 * @author Manoj
 */


 
public class FaceDetectionHelpers {
 
    public static void main(String[] args) {
 
       
    }
    
     /**
     * 
     * @param bi
     * @param minScale
     * @param maxScale
     * @return 
     */
    public static List<Rect> findFaces(BufferedImage bi, int minScale, int maxScale) {
        try {
            InputStream is  = Main.class.getResourceAsStream("/net/mk/JJIL/HCSB.txt");
            Gray8DetectHaarMultiScale detectHaar = new Gray8DetectHaarMultiScale(is, minScale, maxScale);
            RgbImage im = RgbImageJ2se.toRgbImage(bi);
            RgbAvgGray toGray = new RgbAvgGray();
            toGray.push(im);
            List<Rect> results = detectHaar.pushAndReturn(toGray.getFront());
            for(Rect rect:results){
               // System.out.println(rect.getTopLeft().getX()+","+rect.getTopLeft().getY()+","+rect.getBottomRight().getX()+","+rect.getBottomRight().getY());
            }
            //System.out.println("Found "+results.size()+" faces");
            
            return results;
//            Image i = detectHaar.getFront();
//            Gray8Rgb g2rgb = new Gray8Rgb();
//            g2rgb.push(i);
//            RgbImageJ2se conv = new RgbImageJ2se();
//            conv.toFile((RgbImage)g2rgb.getFront(), output.getCanonicalPath());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } catch (jjil.core.Error ex) {
            Logger.getLogger(ImageProcessingHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    /**
     * 
     * @param results
     * @return 
     */
    public static double[] getBBXCenterAndRadius(List<Rect> results) {
        double[] result = new double[3];
        double maxX = 0, maxY = 0, minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        for (Rect rect : results) {
            maxX = rect.getBottomRight().getX() > maxX ? rect.getBottomRight().getX() : maxX;
            maxY = rect.getBottomRight().getY() > maxX ? rect.getBottomRight().getY() : maxY;
            minX = rect.getTopLeft().getX() < minX ? rect.getTopLeft().getX() : minX;
            minY = rect.getTopLeft().getY() < minY ? rect.getTopLeft().getY() : minY;
        }

        result[0] = minX + (maxX - minX) / 2;
        result[1] = minY + (maxY - minY) / 2;
        result[2] = Math.sqrt(((maxX - minX) / 2) * ((maxX - minX) / 2) + ((maxY - minY) / 2) * ((maxY - minY) / 2));
        return result;
    }
    
    public static int [][] getCenterAndRadius(List<Rect> results) {
        int[][] result = new int[results.size()][3];
        //double maxX = 0, maxY = 0, minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        int i=0;
        for (Rect rect : results) {
            double maxX = rect.getBottomRight().getX();
            double maxY = rect.getBottomRight().getY();
            double minX = rect.getTopLeft().getX();
            double minY = rect.getTopLeft().getY();
            result[i][0] = (int) (minX + (maxX - minX) / 2);
            result[i][1] = (int) (minY + (maxY - minY) / 2);
            result[i][2] = (int) Math.sqrt(((maxX - minX) / 2) * ((maxX - minX) / 2) + ((maxY - minY) / 2) * ((maxY - minY) / 2));
            //System.out.println("#getCenterAndRadius "+result[i][0]+","+result[i][1]+","+result[i][2]);
            
            i++;
        }

        
        return result;
    }
    
    public static int [][] getULXYAndRadius(List<Rect> results) {
        int[][] result = new int[results.size()][3];
        //double maxX = 0, maxY = 0, minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        int i=0;
        for (Rect rect : results) {
            double maxX = rect.getBottomRight().getX();
            double maxY = rect.getBottomRight().getY();
            double minX = rect.getTopLeft().getX();
            double minY = rect.getTopLeft().getY();
            result[i][0] = (int) minX ;
            result[i][1] = (int) minY;
            result[i][2] = (int) Math.sqrt(((maxX - minX) / 2) * ((maxX - minX) / 2) + ((maxY - minY) / 2) * ((maxY - minY) / 2));
            //System.out.println("#getCenterAndRadius "+result[i][0]+","+result[i][1]+","+result[i][2]);
            
            i++;
        }

        
        return result;
    }
    
        public static int[][] getRectXYAndWL(List<Rect> results) {
        int[][] result = new int[results.size()][4];
        //double maxX = 0, maxY = 0, minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        int i=0;
        for (Rect rect : results) {
            double maxX = rect.getBottomRight().getX();
            double maxY = rect.getBottomRight().getY();
            double minX = rect.getTopLeft().getX();
            double minY = rect.getTopLeft().getY();
            result[i][0] = (int) (minX );
            result[i][1] = (int) (minY );
            result[i][2] = (int) Math.abs(maxX-minX);
            result[i][3] = (int) Math.abs(maxY-minY);
            //System.out.println("#getRectXYAndWL "+result[i][0]+","+result[i][1]+","+result[i][2]+","+result[i][3]);
            i++;
             }

        
        return result;
    }
}