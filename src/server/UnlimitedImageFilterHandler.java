/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jhlabs.image.MinimumFilter;
import com.sun.net.httpserver.HttpExchange;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import net.mk.ppmcu2D.UIToolKit;

/**
 * http://192.168.1.106:8000/unlimited/0cf3df01-8b3d-47f5-a55a-4441ed957e84?filter=secondCall&id=13
 * @author Manoj
 */
public class UnlimitedImageFilterHandler extends CustomHandler {

    String fileExt = "";
    static String[] FILTERS= {"Edge Filter","Convolve Op Filter","Rays Filter","Oil Filter","Smart Blur Filter","Marble Filter",
        "Noise Filter","Polar Filter","Water Filter","Variable Blur Filter","Unsharp Filter","Twirl Filter","Tile Image Filter",
        "Stamp Filter","Sphere Filter","Glow Filter","Curl Filter","Crystallize Filter","Diffusion Filter","Dither Filter",
        "Invert Filter","Lookup Filter","Kaleidoscope Filter","Chrome Filter","Emboss Filter","Circle Filter","Pinch Filter",
        "Swim Filter","Halftone Filter","Light Filter","Pointillize Filter","Weave Filter","Cellular Filter","LensBlur Filter",
        "Maximum Filter","Minimum Filter","Median Filter","ChannelMix Filter","Contrast Filter","Gain Filter","Grayscale Filter",
        "Solarize Filter","Threshold Filter","Displace Filter","Dissolve Filter","Mirror Filter","Block Filter","Feedback Filter",
        "Gaussian Filter","MotionBlur Filter","RotationBlur Filter","ZoomBlur Filter","Smear Filter","Sparkle Filter","Rescale Filter"};
    
    public static enum callTypes {
        firstCall, secondCall,thirdCall
    };

    //static Map<String, BufferedImage> imagefileCache=new HashMap<>();
    public UnlimitedImageFilterHandler(String fileExt) {
        this.fileExt = fileExt;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        Map queryMap = he.getRequestURI().getQuery() != null ? queryToMap(he.getRequestURI().getQuery()) : new HashMap<String, String>();
        byte[] result = new byte[1];

        String theMainfile = he.getRequestURI().getPath().substring(11);

        if (queryMap.containsKey("filter")) {

            switch ((String) queryMap.get("filter")) {
                case "firstCall":
                    int number = (int) (FILTERS.length * Math.random());
                    JSONObject resultObject = new JSONObject();
                     {
                        try {
                            resultObject.put("id", number);
                            resultObject.put("name", getFilterName(number));
                            resultObject.put("imgURL", "/unlimited/" + theMainfile + "?filter="
                                    + UnlimitedImageFilterHandler.callTypes.secondCall + "&id=" + number);
                            result = resultObject.toString().getBytes();
                        } catch (JSONException ex) {
                            Logger.getLogger(UnlimitedImageFilterHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    break;

                case "secondCall":
                    try {

                        BufferedImage bi = null;
                        BufferedImage theMainImage = null;
                        theMainfile = GenericUploadHandler.defaultSave + theMainfile + "." + fileExt;
                        //  synchronized(imagefileCache){
                        // if (imagefileCache.containsKey(theMainfile)) {
                        //     theMainImage = imagefileCache.get(theMainfile);
                        //} else {
                        theMainImage = ImageIO.read(new File(theMainfile));
                        //  }
                        //  }

                        bi = getFilteredImage(Integer.parseInt(queryMap.get("id").toString()), theMainImage);
                        //System.out.println("#2");
                        bi = UIToolKit.scaleImage(bi, 250, 250);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(bi, "png", baos);
                        //System.out.println("#3");
                        baos.flush();
                        byte[] imageInByte = baos.toByteArray();
                        baos.close();
                        result = imageInByte;
                        he.getResponseHeaders().set("Content-Type", "image/jpg");
                        he.getResponseHeaders().set("Content-disposition", "attachment; filename=\""+getFilterName(Integer.parseInt(queryMap.get("id").toString()))+".jpg\"");
                        //System.out.println("#4");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                    
                case "thirdCall":
                    JSONObject ja=new JSONObject();
                    try {
                        for(int i=0;i<FILTERS.length;i++){
                            ja.put(FILTERS[i], i);
                            ja.put(String.valueOf(i),FILTERS[i]);
                        }
                        
                        result=ja.toString().getBytes();
                    } catch (JSONException ex) {
                        Logger.getLogger(UnlimitedImageFilterHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                    

            }

        }

        //Send out the message
        he.sendResponseHeaders(200, result.length);
        OutputStream oshe = he.getResponseBody();
        oshe.write(result);
        oshe.close();
    }

    public static BufferedImage getFilteredImage(int number, BufferedImage theMainImage) throws IOException {

        BufferedImage bi = null;
        // System.out.println("#1 :: "+number);
        switch (number) {
            case 0:
                bi = ImageProcessingHelper.EdgeFilter(theMainImage);
                break;
            case 1:
                bi = ImageProcessingHelper.ConvolveOpFilter(theMainImage);
                break;
            case 2:
                bi = ImageProcessingHelper.RaysFilter(theMainImage);
                break;
            case 3:
                bi = ImageProcessingHelper.OilFilter(theMainImage);
                break;
            case 4:
                bi = ImageProcessingHelper.SmartBlurFilter(theMainImage);
                break;
            case 5:
                bi = ImageProcessingHelper.MarbleFilter(theMainImage);
                break;
            case 6:
                bi = ImageProcessingHelper.NoiseFilter(theMainImage);
                break;
            case 7:
                bi = ImageProcessingHelper.PolarFilter(theMainImage);
                break;
            case 8:
                bi = ImageProcessingHelper.WaterFilter(theMainImage);
                break;
            case 9:
                bi = ImageProcessingHelper.VariableBlurFilter(theMainImage);
                break;
            case 10:
                bi = ImageProcessingHelper.UnsharpFilter(theMainImage);
                break;
            case 11:
                bi = ImageProcessingHelper.TwirlFilter(theMainImage);
                break;
            case 12:
                bi = ImageProcessingHelper.TileImageFilter(theMainImage);
                break;
            case 13:
                bi = ImageProcessingHelper.StampFilter(theMainImage);
                break;
            case 14:
                bi = ImageProcessingHelper.SphereFilter(theMainImage);
                break;
            case 15:
                bi = ImageProcessingHelper.GlowFilter(theMainImage);
                break;
            case 16:
                bi = ImageProcessingHelper.CurlFilter(theMainImage);
                break;
            case 17:
                bi = ImageProcessingHelper.CrystallizeFilter(theMainImage);
                break;

            case 18:
                bi = ImageProcessingHelper.DiffusionFilter(theMainImage);
                break;

            case 19:
                bi = ImageProcessingHelper.DitherFilter(theMainImage);
                break;

            case 20:
                bi = ImageProcessingHelper.InvertFilter(theMainImage);
                break;

            case 21:
                bi = ImageProcessingHelper.LookupFilter(theMainImage);
                break;

            case 22:
                bi = ImageProcessingHelper.KaleidoscopeFilter(theMainImage);
                break;

            case 23:
                bi = ImageProcessingHelper.ChromeFilter(theMainImage);
                break;

            case 24:
                bi = ImageProcessingHelper.EmbossFilter(theMainImage);
                break;
            case 25:
            bi = ImageProcessingHelper.CircleFilter(theMainImage);
            break;
              
            case 26:
            bi = ImageProcessingHelper.PinchFilter(theMainImage);
            break;
            
            case 27:
            bi = ImageProcessingHelper.SwimFilter(theMainImage);
            break;
            
            
            case 28:
            bi = ImageProcessingHelper.HalftoneFilter(theMainImage);
            break;
            
            case 29:
            bi = ImageProcessingHelper.LightFilter(theMainImage);
            break;
            
            case 30:
            bi = ImageProcessingHelper.PointillizeFilter(theMainImage);
            break;
            
            case 31:
            bi = ImageProcessingHelper.WeaveFilter(theMainImage);
            break;
            
            
            case 32:
            bi = ImageProcessingHelper.CellularFilter(theMainImage);
            break;
            
            
            case 33:
            bi = ImageProcessingHelper.LensBlurFilter(theMainImage);
            break;
            
            case 34:
            bi = ImageProcessingHelper.MaximumFilter(theMainImage);
            break;
            
            case 35:
            bi = ImageProcessingHelper.MinimumFilter(theMainImage);
            break;
            
            case 36:
            bi = ImageProcessingHelper.MedianFilter(theMainImage);
            break;
            
            case 37:
            bi = ImageProcessingHelper.ChannelMixFilter(theMainImage);
            break;
            
            case 38:
            bi = ImageProcessingHelper.ContrastFilter(theMainImage);
            break;
            
            case 39:
            bi = ImageProcessingHelper.GainFilter(theMainImage);
            break;
            
            case 40:
            bi = ImageProcessingHelper.GrayscaleFilter(theMainImage);
            break;
            
            case 41:
            bi = ImageProcessingHelper.SolarizeFilter(theMainImage);
            break;
            
            
            case 42:
            bi = ImageProcessingHelper.ThresholdFilter(theMainImage);
            break;
            
            
            case 43:
            bi = ImageProcessingHelper.DisplaceFilter(theMainImage);
            break;
            
            case 44:
            bi = ImageProcessingHelper.DissolveFilter(theMainImage);
            break;
            
            
            case 45:
            bi = ImageProcessingHelper.MirrorFilter(theMainImage);
            break;
            
            
            case 46:
            bi = ImageProcessingHelper.BlockFilter(theMainImage);
            break;
            
            case 47:
            bi = ImageProcessingHelper.FeedbackFilter(theMainImage);
            break;
            
            case 48:
            bi = ImageProcessingHelper.GaussianFilter(theMainImage);
            break;
            
            case 49:
            bi = ImageProcessingHelper.MotionBlurFilter(theMainImage);
            break;
            
            case 50:
            bi = ImageProcessingHelper.RotationBlurFilter(theMainImage);
            break;
            
            case 51:
            bi = ImageProcessingHelper.ZoomBlurFilter(theMainImage);
            break;
            
            case 52:
            bi = ImageProcessingHelper.SmearFilter(theMainImage);
            break;
            
            case 53:
            bi = ImageProcessingHelper.SparkleFilter(theMainImage);
            break;
            
            
            case 54:
            bi = ImageProcessingHelper.RescaleFilter(theMainImage);
            break;
            
            
            
        }

        return bi;
    }

    public static String getFilterName(int number) throws IOException {
        if(number<FILTERS.length) return FILTERS[number]; else return  "None";
        // System.out.println("#1 :: "+number);
//        switch (number) {
//            case 0:
//                result = "Edge Filter";
//                break;
//            case 1:
//                result = "Convolve Op Filter";
//                break;
//            case 2:
//                result = "Rays Filter";
//                break;
//            case 3:
//                result = "Oil Filter";
//                break;
//            case 4:
//                result = "Smart Blur Filter";
//                break;
//            case 5:
//                result = "Marble Filter";
//                break;
//            case 6:
//                result = "Noise Filter";
//                break;
//            case 7:
//                result = "Polar Filter";
//                break;
//            case 8:
//                result = "Water Filter";
//                break;
//            case 9:
//                result = "Variable Blur Filter";
//                break;
//            case 10:
//                result = "Unsharp Filter";
//                break;
//            case 11:
//                result = "Twirl Filter";
//                break;
//            case 12:
//                result = "Tile Image Filter";
//                break;
//            case 13:
//                result = "Stamp Filter";
//                break;
//            case 14:
//                result = "Sphere Filter";
//                break;
//
//            case 15:
//                result = "Glow Filter";
//                break;
//
//            case 16:
//                result = "Curl Filter";
//                break;
//
//            case 17:
//                result = "Crystallize Filter";
//                break;
//
//            case 18:
//                result = "Diffusion Filter";
//                break;
//
//            case 19:
//                result = "Dither Filter";
//                break;
//
//            case 20:
//                result = "Invert Filter";
//                break;
//
//            case 21:
//                result = "Lookup Filter";
//                break;
//
//            case 22:
//                result = "Kaleidoscope Filter";
//                break;
//
//            case 23:
//                result = "Chrome Filter";
//                break;
//
//            case 24:
//                result = "Emboss Filter";
//                break;
//             
//                case 25:
//                result = "Circle Filter";
//                break;
//            case 26:
//                result = "Pinch Filter";
//                break;  
//             case 27:
//                result = "Swim Filter";
//                break;   
//              
//                case 28:
//                result = "Halftone Filter";
//                break; 
//             
//                case 29:
//                result = "Light Filter";
//                break; 
//                
//                case 30:
//                result = "Pointillize Filter";
//                break;
//                
//                case 31:
//                result = "Weave Filter";
//                break;
//                
//                case 32:
//                result = "Cellular Filter";
//                break;
//                
//                case 33:
//                result = "LensBlur Filter";
//                break;
//                
//                case 34:
//                result = "Maximum Filter";
//                break;
//                
//                
//                case 35:
//                result = "Minimum Filter";
//                break;
//                
//                
//                case 36:
//                result = "Median Filter";
//                break;
//                
//                
//                case 37:
//                result = "ChannelMix Filter";
//                break;
//                
//                case 38:
//                result = "Contrast Filter";
//                break;
//                
//                case 39:
//                result = "Gain Filter";
//                break;
//                
//                case 40:
//                result = "Grayscale Filter";
//                break;
//                
//                
//                case 41:
//                result = "Solarize Filter";
//                break;
//                
//                
//                case 42:
//                result = "Threshold Filter";
//                break;
//                
//                case 43:
//                result = "Displace Filter";
//                break;
//                
//                
//                case 44:
//                result = "Dissolve Filter";
//                break;
//                
//                case 45:
//                result = "Mirror Filter";
//                break;
//                
//                case 46:
//                result = "Block Filter";
//                break;
//                
//                case 47:
//                result = "Feedback Filter";
//                break;
//                
//                case 48:
//                result = "Gaussian Filter";
//                break;
//                
//                
//                case 49:
//                result = "MotionBlur Filter";
//                break;
//                
//                case 50:
//                result = "RotationBlur Filter";
//                break;
//                
//                case 51:
//                result = "ZoomBlur Filter";
//                break;
//                
//                case 52:
//                result = "Smear Filter";
//                break;
//                
//                case 53:
//                result = "Sparkle Filter";
//                break;
//
//                case 54:
//                result = "Rescale Filter";
//                break;
//                
//                
//                
//
////                    case 15:
////                        bi = ImageProcessingHelper.SkyFilter(theMainImage);
////                        break;
//        }
//
//        return result;
    }

}
