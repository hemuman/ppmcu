/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

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
import json.JSONException;
import json.JSONObject;
import net.mk.ppmcu2D.UIToolKit;

/**
 * http://192.168.1.106:8000/unlimited/0cf3df01-8b3d-47f5-a55a-4441ed957e84?filter=secondCall&id=13
 * @author Manoj
 */
public class UnlimitedImageFilterHandler extends CustomHandler {

    String fileExt = "";

    public static enum callTypes {
        firstCall, secondCall
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
                    int number = (int) (24.0 * Math.random());
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
                        bi = UIToolKit.scaleImage(bi, 300, 300);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(bi, "png", baos);
                        //System.out.println("#3");
                        baos.flush();
                        byte[] imageInByte = baos.toByteArray();
                        baos.close();
                        result = imageInByte;
                        he.getResponseHeaders().set("Content-Type", "image/jpg");
                        he.getResponseHeaders().set("Content-disposition", "attachment; filename=\"MyFirstGearImage.jpg\"");
                        //System.out.println("#4");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

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

        }

        return bi;
    }

    public static String getFilterName(int number) throws IOException {

        String result = "None";
        // System.out.println("#1 :: "+number);
        switch (number) {
            case 0:
                result = "Edge Filter";
                break;
            case 1:
                result = "Convolve Op Filter";
                break;
            case 2:
                result = "Rays Filter";
                break;
            case 3:
                result = "Oil Filter";
                break;
            case 4:
                result = "Smart Blur Filter";
                break;
            case 5:
                result = "Marble Filter";
                break;
            case 6:
                result = "Noise Filter";
                break;
            case 7:
                result = "Polar Filter";
                break;
            case 8:
                result = "Water Filter";
                break;
            case 9:
                result = "Variable Blur Filter";
                break;
            case 10:
                result = "Unsharp Filter";
                break;
            case 11:
                result = "Twirl Filter";
                break;
            case 12:
                result = "Tile Image Filter";
                break;
            case 13:
                result = "Stamp Filter";
                break;
            case 14:
                result = "Sphere Filter";
                break;

            case 15:
                result = "Glow Filter";
                break;

            case 16:
                result = "Curl Filter";
                break;

            case 17:
                result = "Crystallize Filter";
                break;

            case 18:
                result = "Diffusion Filter";
                break;

            case 19:
                result = "Dither Filter";
                break;

            case 20:
                result = "Invert Filter";
                break;

            case 21:
                result = "Lookup Filter";
                break;

            case 22:
                result = "Kaleidoscope Filter";
                break;

            case 23:
                result = "Chrome Filter";
                break;

            case 24:
                result = "Emboss Filter";
                break;

//                    case 15:
//                        bi = ImageProcessingHelper.SkyFilter(theMainImage);
//                        break;
        }

        return result;
    }

}
