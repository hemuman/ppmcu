//package net.mk.JJIL;
//
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.InputStream;
//import java.util.List;
//import javax.imageio.ImageIO;
//import jjil.algorithm.Gray8Rgb;
//import jjil.algorithm.RgbAvgGray;
//import jjil.core.Image;
//import jjil.core.Rect;
//import jjil.core.RgbImage;
//import jjil.j2se.RgbImageJ2se;
//
//public class Main {
//
//    public static void findFaces(BufferedImage bi, int minScale, int maxScale, File output) {
//        try {
//            InputStream is  = Main.class.getResourceAsStream("/net/mk/JJIL/HCSB.txt");
//            Gray8DetectHaarMultiScale detectHaar = new Gray8DetectHaarMultiScale(is, minScale, maxScale);
//            RgbImage im = RgbImageJ2se.toRgbImage(bi);
//            RgbAvgGray toGray = new RgbAvgGray();
//            toGray.push(im);
//            List<Rect> results = detectHaar.pushAndReturn(toGray.getFront());
//            for(Rect rect:results){
//                System.out.println(rect.getTopLeft().getX()+","+rect.getTopLeft().getY()+","+rect.getBottomRight().getX()+","+rect.getBottomRight().getY());
//            }
//            
//           double[] data= getBBXCenterAndRadius(results);
//           
//            System.out.println("Found "+results.size()+" faces" + " x="+data[0]+ " y="+data[1]+ " radius="+data[2]);
//            Image i = detectHaar.getFront();
//            Gray8Rgb g2rgb = new Gray8Rgb();
//            g2rgb.push(i);
//            RgbImageJ2se conv = new RgbImageJ2se();
//            conv.toFile((RgbImage)g2rgb.getFront(), output.getCanonicalPath());
//        } catch (Throwable e) {
//            throw new IllegalStateException(e);
//        }
//    }
//
//    public static void main(String[] args) throws Exception {        
//        BufferedImage bi = ImageIO.read(Main.class.getResourceAsStream("ff.jpg"));
//        findFaces(bi, 1, bi.getHeight()/60, new File("c:/Temp/result.jpg")); // change as needed
//    }
//    
//    public static double[] getBBXCenterAndRadius(List<Rect> results) {
//        double[] result = new double[3];
//        double maxX = 0, maxY = 0, minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
//        for (Rect rect : results) {
//            maxX = rect.getBottomRight().getX() > maxX ? rect.getBottomRight().getX() : maxX;
//            maxY = rect.getBottomRight().getY() > maxX ? rect.getBottomRight().getY() : maxY;
//            minX = rect.getTopLeft().getX() < minX ? rect.getTopLeft().getX() : minX;
//            minY = rect.getTopLeft().getY() < minY ? rect.getTopLeft().getY() : minY;
//        }
//
//        result[0] = minX + (maxX - minX) / 2;
//        result[1] = minY + (maxY - minY) / 2;
//        result[2] = Math.sqrt(((maxX - minX) / 2) * ((maxX - minX) / 2) + ((maxY - minY) / 2) * ((maxY - minY) / 2));
//        return result;
//    }
//
//}