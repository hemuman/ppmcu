/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu2D;

import Compression.DataCompression;
import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;

import javax.imageio.ImageIO;
import net.mk.DTasks.OSAppGraphicsDCTask;
import net.mk.dc.DistributedTaskPool;
import net.mk.dc.DistributedTaskService;
import net.mk.ppmcu.PrimeNumbersFinder;
import net.mk.ppmcuGUI.MCWConfClass;

/**
 *
 * @author PDI
 */
public class ScreenCapture {

    static Robot robot;

    public static void main(String[] args) throws Exception {
        //Robot robot = new Robot();
        //BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        //ImageIO.write(getScreenStream(), "JPG", new File("screenShot.jpg"));
    }

    public static BufferedImage getScreenStream() {
        // Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(ScreenCapture.class.getName()).log(Level.SEVERE, null, ex);
        }
        return robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    }

    public static BufferedImage getScreenStream(double Scale) {

        BufferedImage screenData = getScreenStream();
        BufferedImage img = new BufferedImage((int) (screenData.getWidth() * Scale), (int) (screenData.getHeight() * Scale), BufferedImage.TYPE_INT_RGB);

        Graphics2D g = img.createGraphics();
        AffineTransform trans = AffineTransform.getScaleInstance(Scale, Scale);
        g.drawRenderedImage(screenData, trans);
        return img;

    }

    public static byte[] getScreenStreamAsByte(double Scale) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(getScreenStream(Scale), "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            //checkByteArrayCompresion(imageInByte);
            return imageInByte;
        } catch (IOException ex) {
            Logger.getLogger(ScreenCapture.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static byte[] getImageAsByte(BufferedImage buff_image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(buff_image, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            //checkByteArrayCompresion(imageInByte);
            return imageInByte;
        } catch (IOException ex) {
            Logger.getLogger(ScreenCapture.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static BufferedImage getByteAsImage(byte[] ByteArray) {
        try {
            InputStream in = new ByteArrayInputStream(ByteArray);
            //BufferedImage bImageFromConvert = ImageIO.read(in);

            BufferedImage bufferedImage = ImageIO.read(in);//= ByteArrayConversion.fromByteArray(byteImage);
//			
            return bufferedImage;
        } catch (IOException ex) {
            Logger.getLogger(ScreenCapture.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static BufferedImage getCompressedByteAsImage(byte[] ByteArray) {
        try {
            InputStream in = new ByteArrayInputStream(DataCompression.decompress(ByteArray));
            //BufferedImage bImageFromConvert = ImageIO.read(in);

            BufferedImage bufferedImage = ImageIO.read(in);//= ByteArrayConversion.fromByteArray(byteImage);
//			
            return bufferedImage;
        } catch (IOException ex) {
            Logger.getLogger(ScreenCapture.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataFormatException ex) {
            Logger.getLogger(ScreenCapture.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static byte[] getCompressedScreenStreamAsByte(double Scale) {
        try {
          
            //checkByteArrayCompresion(imageInByte);
            return DataCompression.compress(getScreenStreamAsByte(Scale), MCWConfClass.ObjectCompressionLevel);
        } catch (IOException ex) {
            Logger.getLogger(ScreenCapture.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static void checkByteArrayCompresion(byte[] ByteArray) {

        System.out.println("#checkByteArrayCompresion before compression ByteArray Size\t=" + ByteArray.length);
        int compressedSize = 0;
        long begTest = new java.util.Date().getTime();
        Double secs;
        Vector compressedData = new Vector();
        byte temp = 0;
        for (int i = 0; i < ByteArray.length; i++) {

            int compression_count = 0;
            if (i == 0) { //Entry Point
                temp = ByteArray[i];

            } else if (temp == ByteArray[i]) {
                compressedSize = compressedSize + 2;
                compression_count++;

            } else {
                compressedData.add(temp); //Byte value
                compressedData.add(compression_count);//Byte count
                temp = ByteArray[i];
                compression_count = 0;
            }


        }
        secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("#checkByteArrayCompresion done in \t=" + secs + "sec(s)");
        System.out.println("#checkByteArrayCompresion Aster compression ByteArray Size\t=" + compressedData.size());
        System.out.println("#checkByteArrayCompresion Compression ratio=" + (double) compressedData.size() / ByteArray.length);

    }

    public static int[] getCompressedByteArray(byte[] ByteArray) {

        System.out.println("#checkByteArrayCompresion before compression ByteArray Size\t=" + ByteArray.length);
        int compressedSize = 0;
        long begTest = new java.util.Date().getTime();
        Double secs;
        Vector compressedData = new Vector();
        byte temp = 0;
        for (int i = 0; i < ByteArray.length; i++) {

            int compression_count = 0;
            if (i == 0) { //Entry Point
                temp = ByteArray[i];
                System.out.println("Entry Point");

            } else if (temp == ByteArray[i]) {
                compressedSize = compressedSize + 2;
                compression_count++;
                System.out.print("#" + temp);

            } else {
                compressedData.add((int) temp); //Byte value
                compressedData.add(compression_count);//Byte count
                temp = ByteArray[i];
                compression_count = 0;
                System.out.println("_" + temp);
            }


        }
        secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("#checkByteArrayCompresion done in \t=" + secs + "sec(s)");
        System.out.println("#checkByteArrayCompresion After compression ByteArray Size\t=" + compressedData.size());
        System.out.println("#checkByteArrayCompresion Compression ratio=" + (double) compressedData.size() / ByteArray.length);

        return new PrimeNumbersFinder().convertTo1DIntArray(compressedData);
    }

    public static byte[] getInfaltedByteArray(int[] IntArray) {

        System.out.println("#checkByteArrayCompresion before Inflation ByteArray Size\t=" + IntArray.length);
        long begTest = new java.util.Date().getTime();
        Double secs;
        Vector InfaltedData = new Vector();


        for (int i = 0, k = 1; i < IntArray.length; i = i + 2, k = k + 2) {//i= Byte K= Count


            for (int j = 0; j < IntArray[k]; j++) {
                InfaltedData.add((byte) IntArray[i]);
            }


        }
        secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("#checkByteArrayCompresion done in \t=" + secs + "sec(s)");
        System.out.println("#checkByteArrayCompresion After Inflation ByteArray Size\t=" + InfaltedData.size());
        System.out.println("#checkByteArrayCompresion Inflation ratio=" + (double) InfaltedData.size() / IntArray.length);

        return VectorToByteArray(InfaltedData);
    }

    public static byte[] VectorToByteArray(Vector v) {

        byte[] bytearray = new byte[v.size()];

        for (int i = 0; i < bytearray.length; i++) {
            bytearray[i] = (byte) v.get(i);
        }
        return bytearray;
    }
}
