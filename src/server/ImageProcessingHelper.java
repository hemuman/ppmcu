/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.amazonaws.util.Base64;
import com.jhlabs.image.BlockFilter;
import com.jhlabs.image.CellularFilter;
import com.jhlabs.image.ChannelMixFilter;
import com.jhlabs.image.ChromeFilter;
import com.jhlabs.image.CircleFilter;
import com.jhlabs.image.ContrastFilter;
import com.jhlabs.image.CrystallizeFilter;
import com.jhlabs.image.CurlFilter;
import com.jhlabs.image.DiffusionFilter;
import com.jhlabs.image.DisplaceFilter;
import com.jhlabs.image.DissolveFilter;
import com.jhlabs.image.DitherFilter;
import com.jhlabs.image.EdgeFilter;
import com.jhlabs.image.EmbossFilter;
import com.jhlabs.image.FeedbackFilter;
import com.jhlabs.image.GainFilter;
import com.jhlabs.image.GaussianFilter;
import com.jhlabs.image.GlowFilter;
import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.HalftoneFilter;
import com.jhlabs.image.InvertFilter;
import com.jhlabs.image.KaleidoscopeFilter;
import com.jhlabs.image.LensBlurFilter;
import com.jhlabs.image.LightFilter;
import com.jhlabs.image.LookupFilter;
import com.jhlabs.image.MarbleFilter;
import com.jhlabs.image.MaximumFilter;
import com.jhlabs.image.MedianFilter;
import com.jhlabs.image.MinimumFilter;
import com.jhlabs.image.MirrorFilter;
import com.jhlabs.image.MotionBlurFilter;
import com.jhlabs.image.MotionBlurOp;
import com.jhlabs.image.NoiseFilter;
import com.jhlabs.image.OilFilter;
import com.jhlabs.image.PinchFilter;
import com.jhlabs.image.PointillizeFilter;
import com.jhlabs.image.PolarFilter;
import com.jhlabs.image.RaysFilter;
import com.jhlabs.image.RescaleFilter;
import com.jhlabs.image.RippleFilter;
import com.jhlabs.image.ScaleFilter;
import com.jhlabs.image.ShearFilter;
import com.jhlabs.image.SkyFilter;
import com.jhlabs.image.SmartBlurFilter;
import com.jhlabs.image.SmearFilter;
import com.jhlabs.image.SolarizeFilter;
import com.jhlabs.image.SparkleFilter;
import com.jhlabs.image.SphereFilter;
import com.jhlabs.image.StampFilter;
import com.jhlabs.image.SwimFilter;
import com.jhlabs.image.ThresholdFilter;
import com.jhlabs.image.TileImageFilter;
import com.jhlabs.image.TwirlFilter;
import com.jhlabs.image.UnsharpFilter;
import com.jhlabs.image.VariableBlurFilter;
import com.jhlabs.image.WaterFilter;
import com.jhlabs.image.WeaveFilter;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBufferByte;
import java.awt.image.Kernel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import jjil.algorithm.Gray8Rgb;
import jjil.algorithm.RgbAvgGray;
import jjil.core.Error;
import jjil.core.Image;
import jjil.core.Rect;
import jjil.core.RgbImage;
import jjil.j2se.RgbImageJ2se;
import net.mk.JJIL.Gray8DetectHaarMultiScale;
import net.mk.JJIL.Main;
import net.mk.ppmcu2D.UIToolKit;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Manoj
 */
public class ImageProcessingHelper {

    static BufferedImage StampImage;
    String StampImageUrl;

    public ImageProcessingHelper(String StampImageUrl) {
        try {
            StampImage = ImageIO.read(new File(StampImageUrl)); //--Prod
            this.StampImageUrl = StampImageUrl;
            //  StampImage=ImageIO.read(new File("c:/resource_GearAppIcon.png")); //--Test
        } catch (IOException ex) {
            Logger.getLogger(ImageProcessingHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    
    public BufferedImage getStampedImage(BufferedImage ImageToStamp, int xPos, int yPos) {
        BufferedImage StampImageBG = new BufferedImage(StampImage.getWidth(), StampImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = StampImageBG.getGraphics();
        g.drawImage(ImageToStamp, Math.abs(StampImageBG.getWidth() - ImageToStamp.getWidth()) / 2,
                0, null);
        //Math.abs(StampImageBG.getHeight()-ImageToStamp.getHeight())/2
        g.drawImage(StampImage, xPos, yPos, null);
        g.dispose();
        return StampImageBG;
    }

    public static BufferedImage getStampedImage(String ImageToStamp, String badgePath, float opacity, int xPos, int yPos) {

        try {
            BufferedImage theMainImage = ImageIO.read(new File(ImageToStamp));
            BufferedImage theBadgeImage = ImageIO.read(new File(badgePath));
            BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = StampImageBG.createGraphics();
            g.drawImage(theMainImage, Math.abs(StampImageBG.getWidth() - theMainImage.getWidth()) / 2,
                    0, null);
            //Math.abs(StampImageBG.getHeight()-ImageToStamp.getHeight())/2
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g.drawImage(theBadgeImage, xPos, yPos, null);
            g.dispose();
            return StampImageBG;

        } catch (IOException ex) {
            Logger.getLogger(ImageProcessingHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Generate GIF on demand.
     *
     * @param imageURLs
     * @param fileNameWithLocation
     * @param delay
     */
    public static void generateGIF(String[] imageURLs, String fileNameWithLocation, int delay) {

        AnimatedGifEncoder e = new AnimatedGifEncoder();
        e.start(fileNameWithLocation);
        e.setDelay(delay);   // 1000=1 frame per sec
        e.setRepeat(0);

        for (String imageURL : imageURLs) {
            try {
                e.addFrame(ImageIO.read(new File(imageURL)));
            } catch (IOException ex) {
                Logger.getLogger(ImageProcessingHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        e.finish();
    }
    
 
    
    public static byte[] extractBytes (String ImageName) throws IOException {
        // open image
        File imgPath = new File(ImageName);
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage .getRaster();
        DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

        return ( data.getData() );
    }
    /**
     * 
     * @param bytes
     * @return 
     */
    public static String encodeImageToBase64(byte[] bytes){
        return new String(Base64.encode(bytes));
    }

    /**
     * 
     * @param imageString
     * @return 
     */
    public static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public static BufferedImage ConvolveOpFilter(BufferedImage theMainImage) throws IOException {
        float[] matrix = {
            0.111f, 0.111f, 0.111f,
            0.111f, 0.111f, 0.111f,
            0.111f, 0.111f, 0.111f,};
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrix));
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        return StampImageBG;
    }

    public static BufferedImage EdgeFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new EdgeFilter();
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        return StampImageBG;
    }

    public static BufferedImage RaysFilter(BufferedImage theMainImage) throws IOException {
        RaysFilter rf= new RaysFilter();
        rf.setDistance(theMainImage.getWidth()/10);
        rf.setAngle((float) (Math.PI/4f));
        BufferedImageOp op = new RaysFilter();
        
        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage OilFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new OilFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage SmartBlurFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new SmartBlurFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage MarbleFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new MarbleFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage NoiseFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new NoiseFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage PolarFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new PolarFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage WaterFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new WaterFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage VariableBlurFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new VariableBlurFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage UnsharpFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new UnsharpFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage TwirlFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new TwirlFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage TileImageFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new TileImageFilter(50, 50);

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage GlowFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new GlowFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    /**
     *
     * float radius - The blur radius 0... float threshold - The threshold level
     * 0..1. float softness - The threshold softness 0..1. int black - The
     * "black" color. int white - The "white" color.
     *
     * @param theMainImage
     * @return
     * @throws IOException
     */
    public static BufferedImage StampFilter(BufferedImage theMainImage) throws IOException {
        StampFilter sf = new StampFilter();
        sf.setRadius(theMainImage.getWidth() / 10);
        sf.setSoftness(0.5f);
        sf.setThreshold(0.5f);
        sf.setBlack(0);
        sf.setWhite(1);
        BufferedImageOp op = sf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage SphereFilter(BufferedImage theMainImage) throws IOException {
        SphereFilter sf=new SphereFilter();
        List<Rect> faces=FaceDetectionHelpers.findFaces(theMainImage, 1, 50);
        if (faces.size() > 0) {
            double[] dataXYC = FaceDetectionHelpers.getBBXCenterAndRadius(faces);
            sf.setCentreX((float) dataXYC[0]);
            sf.setCentreY((float) dataXYC[1]);
            sf.setRadius((float) dataXYC[2]*1.5f);
        }

        BufferedImageOp op = sf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage CurlFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new CurlFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage CrystallizeFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new CrystallizeFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage DiffusionFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new DiffusionFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    /**
     * Here's the simplest 2x2 dither matrix:
     *
     * 0 2
     * 3 1
     *
     * And here's a 4x4 dither matrix:
     *
     * 0 14 3 13 11 5 8 6 12 2 15 1 7 9 4 10
     *
     * @param theMainImage
     * @return
     * @throws IOException
     */
    public static BufferedImage DitherFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new DitherFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage InvertFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new InvertFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    /**
     * Colormap Colormap - The colormap to use to do the mapping
     *
     * @param theMainImage
     * @return
     * @throws IOException
     */
    public static BufferedImage LookupFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new LookupFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage KaleidoscopeFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new KaleidoscopeFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage ChromeFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new ChromeFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage EmbossFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new EmbossFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage CircleFilter(BufferedImage theMainImage) throws IOException {
        CircleFilter cf = new CircleFilter();
        cf.setRadius(theMainImage.getHeight() / 10);
        cf.setHeight(theMainImage.getHeight() / 5);
        cf.setSpreadAngle(15);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage PinchFilter(BufferedImage theMainImage) throws IOException {
        PinchFilter cf = new PinchFilter();
        List<Rect> faces=FaceDetectionHelpers.findFaces(theMainImage, 1, 50);
        if (faces!=null && faces.size() > 0) {
            double[] dataXYC = FaceDetectionHelpers.getBBXCenterAndRadius(faces);
            cf.setCentreX((float) dataXYC[0]);
            cf.setCentreY((float) dataXYC[1]);
            cf.setRadius((float) dataXYC[2]*1.5f);
        }

        
        //cf.setRadius((float) (theMainImage.getHeight() / 1.5));
       // cf.setCentreX(0);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage SwimFilter(BufferedImage theMainImage) throws IOException {
        SwimFilter cf = new SwimFilter();
        cf.setAmount(.5f);
        cf.setAngle((float) (Math.PI / 8f));
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage HalftoneFilter(BufferedImage theMainImage) throws IOException {
        HalftoneFilter cf = new HalftoneFilter();
        cf.setMask(EdgeFilter(theMainImage));

        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage LightFilter(BufferedImage theMainImage) throws IOException {
        LightFilter cf = new LightFilter();
        cf.setBumpShape(2);

        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage PointillizeFilter(BufferedImage theMainImage) throws IOException {
        PointillizeFilter cf = new PointillizeFilter();
        cf.setFadeEdges(false);

        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage WeaveFilter(BufferedImage theMainImage) throws IOException {
        WeaveFilter cf = new WeaveFilter();
        cf.setXWidth(theMainImage.getWidth() / 10);
        cf.setYWidth(theMainImage.getHeight() / 10);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage CellularFilter(BufferedImage theMainImage) throws IOException {
        CellularFilter cf = new CellularFilter();
        cf.setGridType(2);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage LensBlurFilter(BufferedImage theMainImage) throws IOException {
        LensBlurFilter cf = new LensBlurFilter();

        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage MaximumFilter(BufferedImage theMainImage) throws IOException {
        MaximumFilter cf = new MaximumFilter();

        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage MedianFilter(BufferedImage theMainImage) throws IOException {
        MedianFilter cf = new MedianFilter();

        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage MinimumFilter(BufferedImage theMainImage) throws IOException {
        MinimumFilter cf = new MinimumFilter();

        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage ChannelMixFilter(BufferedImage theMainImage) throws IOException {
        ChannelMixFilter cf = new ChannelMixFilter();
        cf.setIntoR(100);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage ContrastFilter(BufferedImage theMainImage) throws IOException {
        ContrastFilter cf = new ContrastFilter();
        cf.setContrast(1f);
        cf.setBrightness(1f);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage GainFilter(BufferedImage theMainImage) throws IOException {
        GainFilter cf = new GainFilter();
        cf.setBias(.5f);
        cf.setGain(0.8f);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage GrayscaleFilter(BufferedImage theMainImage) throws IOException {
        GrayscaleFilter cf = new GrayscaleFilter();

        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage SolarizeFilter(BufferedImage theMainImage) throws IOException {
        SolarizeFilter cf = new SolarizeFilter();

        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage ThresholdFilter(BufferedImage theMainImage) throws IOException {
        ThresholdFilter cf = new ThresholdFilter();
        cf.setLowerThreshold(50);
        cf.setUpperThreshold(150);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }
    
    
    public static BufferedImage DisplaceFilter(BufferedImage theMainImage) throws IOException {
        DisplaceFilter cf = new DisplaceFilter();
        cf.setDisplacementMap(PinchFilter(theMainImage));
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }
    
    public static BufferedImage DissolveFilter(BufferedImage theMainImage) throws IOException {
        DissolveFilter cf = new DissolveFilter();
        cf.setDensity(0.8f);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }
    
    public static BufferedImage MirrorFilter(BufferedImage theMainImage) throws IOException {
        MirrorFilter cf = new MirrorFilter();
        
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }
    
    public static BufferedImage BlockFilter(BufferedImage theMainImage) throws IOException {
        BlockFilter cf = new BlockFilter();
        cf.setBlockSize(theMainImage.getWidth()/20);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }
    
    
    public static BufferedImage FeedbackFilter(BufferedImage theMainImage) throws IOException {
        FeedbackFilter cf = new FeedbackFilter();
        cf.setZoom(.5f);cf.setAngle((float) (Math.PI/8f));
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }
    
    public static BufferedImage GaussianFilter(BufferedImage theMainImage) throws IOException {
        GaussianFilter cf = new GaussianFilter();
        cf.setRadius(theMainImage.getHeight()/20);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }
    
    
    public static BufferedImage MotionBlurFilter(BufferedImage theMainImage) throws IOException {
        MotionBlurFilter cf = new MotionBlurFilter();
        cf.setDistance(theMainImage.getWidth()/10);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }
    
    public static BufferedImage RotationBlurFilter(BufferedImage theMainImage) throws IOException {
        MotionBlurFilter cf = new MotionBlurFilter();
        
        cf.setRotation((float) (Math.PI/8f));
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }
    
    public static BufferedImage ZoomBlurFilter(BufferedImage theMainImage) throws IOException {
        MotionBlurFilter cf = new MotionBlurFilter();
        
        cf.setZoom(.2f);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }
    
    
    public static BufferedImage SmearFilter(BufferedImage theMainImage) throws IOException {
        SmearFilter cf = new SmearFilter();
        cf.setShape(3);
        //cf.setDistance(theMainImage.getWidth()/40);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }
    
    public static BufferedImage SparkleFilter(BufferedImage theMainImage) throws IOException {
        SparkleFilter cf = new SparkleFilter();
        cf.setRays(2);
        //cf.setRadius(theMainImage.getWidth()/10);
        cf.setRandomness(10);
        cf.setRays(10);
        
        //cf.setDistance(theMainImage.getWidth()/40);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }
    
    public static BufferedImage RescaleFilter(BufferedImage theMainImage) throws IOException {
        RescaleFilter cf = new RescaleFilter();
        cf.setScale(.01f);
        
        //cf.setDistance(theMainImage.getWidth()/40);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }
    

    /**
     * Doesn't work
     *
     * @param theMainImage
     * @return
     * @throws IOException
     */
    public static BufferedImage ShearFilter(BufferedImage theMainImage) throws IOException {
        ShearFilter cf = new ShearFilter();
        cf.setXAngle((float) (Math.PI / 4));
        cf.setYAngle((float) (Math.PI / 4));
        cf.setResize(false);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    /**
     * Doesn't work.
     *
     * @param theMainImage
     * @return
     * @throws IOException
     */
    public static BufferedImage RippleFilter(BufferedImage theMainImage) throws IOException {
        RippleFilter cf = new RippleFilter();
        cf.setWaveType(1);
        cf.setXAmplitude(theMainImage.getHeight() / 10);
        cf.setXWavelength(theMainImage.getHeight() / 10);
        cf.setYAmplitude(theMainImage.getHeight() / 10);
        cf.setYWavelength(theMainImage.getHeight() / 10);
        BufferedImageOp op = cf;

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    /**
     * Doesn't work, causes application crash.
     *
     * @param theMainImage
     * @return
     * @throws IOException
     */
    public static BufferedImage SkyFilter(BufferedImage theMainImage) throws IOException {
        BufferedImageOp op = new SkyFilter();

        //BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(theMainImage.getWidth(), theMainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);

        return StampImageBG;
    }

    public static BufferedImage ScaleFilter(BufferedImage theMainImage, int width, int height) throws IOException {
        BufferedImageOp op = new ScaleFilter(width, height);
        ////BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        op.filter(theMainImage, StampImageBG);
        return StampImageBG;
    }
    
    public static BufferedImage ParamCurveFilter(BufferedImage theMainImage) throws IOException {
        
        BufferedImage curves=UIToolKit.getParametricCurve(theMainImage.getWidth(), theMainImage.getHeight(), .1f, false);
        ////BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage curveBlur=GaussianFilter(curves);
        BufferedImage StampImagefg=UIToolKit.overlayImages( curves,curveBlur);
        BufferedImage StampImageBG = UIToolKit.overlayImages(theMainImage, StampImagefg);

        return StampImageBG;
    }

    public static BufferedImage RandomCurveFilter(BufferedImage theMainImage) throws IOException {
        
        BufferedImage curves=UIToolKit.getRandomCurve(theMainImage.getWidth(), theMainImage.getHeight(),100, .5f, false);
        ////BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = UIToolKit.overlayImages(theMainImage, curves);

        return StampImageBG;
    }
    
    public static BufferedImage Win7StyleRectNewsInkFilter(BufferedImage theMainImage) throws IOException {
        
        BufferedImage curves=UIToolKit.getWin7StyleRectNewsInk(theMainImage.getWidth(), theMainImage.getHeight(), .2f, false);
        ////BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = UIToolKit.overlayImages(theMainImage, curves);

        return StampImageBG;
    }
    
    
    public static BufferedImage ParticleEffectsFilter(BufferedImage theMainImage) throws IOException {
        
//        BufferedImage particles=UIToolKit.toBufferedImage(
//                UIToolKit.TransformColorToTransparency(
//                        UIToolKit.particleEffects(theMainImage.getWidth(), theMainImage.getHeight(),.3f),0x00000000));
        BufferedImage particles=
                        UIToolKit.particleEffects(theMainImage.getWidth(), theMainImage.getHeight()/8,.3f);
        
        ////BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = UIToolKit.overlayImages(theMainImage, particles);

        return StampImageBG;
    }
    
    
    public static BufferedImage ConnectedCirclesFilter(BufferedImage theMainImage) throws IOException {
        List<Rect> faces=FaceDetectionHelpers.findFaces(theMainImage, 1, 50);
        int circles[][]=new int[faces.size()][3];
        if (faces!=null && faces.size() > 0) {
            circles=FaceDetectionHelpers.getULXYAndRadius(faces);
        }
        BufferedImage connectedCircles=
                        UIToolKit.getConnectedCircles(circles,theMainImage.getWidth(), theMainImage.getHeight(),.3f,false);
        
        ////BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = UIToolKit.overlayImages(theMainImage, connectedCircles);

        return StampImageBG;
    }
    
    public static BufferedImage ConnectedRectFilter(BufferedImage theMainImage) throws IOException {
        List<Rect> faces=FaceDetectionHelpers.findFaces(theMainImage, 1, 50);

        int rects[][]=new int[faces.size()][4];
        if (faces!=null && faces.size() > 0) {

            rects=FaceDetectionHelpers.getRectXYAndWL(faces);
        }
  BufferedImage connectedRects=
                        UIToolKit.getConnectedRects(rects,theMainImage.getWidth(), theMainImage.getHeight(),.3f,false);
        
        ////BufferedImage theMainImage = ImageIO.read(new File(imagePath));
        BufferedImage StampImageBG = UIToolKit.overlayImages(theMainImage, connectedRects);

        return StampImageBG;
    }
    
    
    
    /**
     * Encode image to string
     *
     * @param image The image to encode
     * @param type jpeg, bmp, ...
     * @return encoded string
     */
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public static String encodeToString(byte[] imageBytes, String type) {
        String imageString = null;

        BASE64Encoder encoder = new BASE64Encoder();
        imageString = encoder.encode(imageBytes);

        return imageString;
    }

    public static void main(String... args) {

    }
}
