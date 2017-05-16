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
        "Random Filters","Sphere Filter","Glow Filter","Curl Filter","Crystallize Filter","Diffusion Filter","Dither Filter",
        "Invert Filter","Lookup Filter","Kaleidoscope Filter","Chrome Filter","Emboss Filter","Circle Filter","Pinch Filter",
        "Swim Filter","Halftone Filter","Light Filter","Pointillize Filter","Weave Filter","Cellular Filter","LensBlur Filter",
        "Maximum Filter","Minimum Filter","Median Filter","ChannelMix Filter","Contrast Filter","Gain Filter","Grayscale Filter",
        "Solarize Filter","Threshold Filter","Displace Filter","Dissolve Filter","Mirror Filter","Block Filter","Feedback Filter",
        "Gaussian Filter","MotionBlur Filter","RotationBlur Filter","ZoomBlur Filter","Smear Filter","Sparkle Filter",
        "ParamCurve Filter","RandomCurve Filter","Win7StyleRectNewsInk Filter","ParticleEffects Filter",
        "ConnectedCircles Filter","ConnectedRect Filter"};
    
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
                            resultObject.put("photographyQuote",getPhotographyQuote((int) (QUOTES.length * Math.random())));
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
               // bi = ImageProcessingHelper.StampFilter(theMainImage);
                bi = getFilteredImage( (int) (Math.random()*54), getFilteredImage((int) (Math.random()*54), theMainImage));
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
            bi = ImageProcessingHelper.ParamCurveFilter(theMainImage);
            break;
            
            case 55:
            bi = ImageProcessingHelper.RandomCurveFilter(theMainImage);
            break;
            
            case 56:
            bi = ImageProcessingHelper.Win7StyleRectNewsInkFilter(theMainImage);
            break;
            
            
            case 57:
            bi = ImageProcessingHelper.ParticleEffectsFilter(theMainImage);
            break;
            
            case 58:
            bi = ImageProcessingHelper.ConnectedCirclesFilter(theMainImage);
            break;
            
            case 59:
            bi = ImageProcessingHelper.ConnectedRectFilter(theMainImage);
            break;
            
             
            default:
               bi = getFilteredImage( (int) (Math.random()*54), getFilteredImage((int) (Math.random()*54), theMainImage));
            break; 
            
            
            
        }

        return bi;
    }

    public static String getFilterName(int number) throws IOException {
        if(number<FILTERS.length) return FILTERS[number]; else return  "Random";
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

    static String QUOTES[]={"Photography is the story I fail to put into words. - Destin Sparks","When words become unclear, I shall focus with photographs. When images become inadequate, I shall be content with silence. - Ansel Adams","In photography there is a reality so subtle that it becomes more real than reality. - Alfred Stieglitz","There is one thing the photograph must contain, the humanity of the moment. - Robert Frank","Taking an image, freezing a moment, reveals how rich reality truly is. - Anonymous","Photography is a way of feeling, of touching, of loving. What you have caught on film is captured forever… It remembers little things, long after you have forgotten everything. - Aaron Siskind","We are making photographs to understand what our lives mean to us. - Ralph Hattersley","A thing that you see in my pictures is that I was not afraid to fall in love with these people. - Annie Leibovitz","You don’t take a photograph. You ask quietly to borrow it. - Unknown","Photography for me is not looking, it’s feeling. If you can’t feel what you’re looking at, then you’re never going to get others to feel anything when they look at your pictures. - Don McCullin","A portrait is not made in the camera but on either side of it. - Edward Steichen","It’s one thing to make a picture of what a person looks like, it’s another thing to make a portrait of who they are. - Paul Caponigro","The best thing about a picture is that it never changes, even when the people in it do. - Andy Warhol","Taking pictures is like tiptoeing into the kitchen late at night and stealing Oreo cookies. - Diane Arbus","To me, photography is an art of observation. It’s about finding something interesting in an ordinary place… I’ve found it has little to do with the things you see and everything to do with the way you see them. - Elliott Erwitt","The picture that you took with your camera is the imagination you want to create with reality. - Scott Lorenzo","If the photographer is interested in the people in front of his lens, and if he is compassionate, it’s already a lot. The instrument is not the camera but the photographer. - Eve Arnold","A tear contains an ocean. A photographer is aware of the tiny moments in a persons life that reveal greater truths. - Anonymous","The camera is an instrument that teaches people how to see without a camera. - Dorothea Lange","Essentially what photography is is life lit up. - Sam Abell","I don’t trust words. I trust pictures. - Gilles Peress","I really believe there are things nobody would see if I didn’t photograph them. - Diane Arbus","Taking pictures is savoring life intensely, every hundredth of a second. - Marc Riboud","Once you learn to care, you can record images with your mind or on film. There is no difference between the two. - Anonymous","Photograph: a picture painted by the sun without instruction in art. - Ambrose Bierce","Photography is truth. - Jean-Luc Godard","The camera makes you forget you’re there. It’s not like you are hiding but you forget, you are just looking so much. - Annie Leibovitz","If you see something that moves you, and then snap it, you keep a moment. - Linda McCartney","There are always two people in every picture: the photographer and the viewer. - Ansel Adams","A photograph is a secret about a secret. The more it tells you the less you know. - Diane Arbus","The whole point of taking pictures is so that you don’t have to explain things with words. - Elliott Erwitt","One doesn’t stop seeing. One doesn’t stop framing. It doesn’t turn off and turn on. It’s on all the time. - Annie Leibovitz","What I like about photographs is that they capture a moment that’s gone forever, impossible to reproduce. - Karl Lagerfeld","A good photograph is one that communicates a fact, touches the heart and leaves the viewer a changed person for having seen it. It is, in a word, effective. - Irving Penn","Beauty can be seen in all things, seeing and composing the beauty is what separates the snapshot from the photograph. - Matt Hardy","To me, photography is an art of observation. It’s about finding something interesting an ordinary place… I’ve found it has little to do with the things you see and everything to do with the way you see them. - Elliott Erwitt","You don’t take a photograph, you make it. - Ansel Adams","When people ask me what equipment I use – I tell them my eyes. - Anonymous","I wish that all of nature’s magnificence, the emotion of the land, the living energy of place could be photographed. - Annie Leibovitz","I never have taken a picture I’ve intended. They’re always better or worse. - Diane Arbus","All photographs are accurate. None of them is the truth. - Richard Avedon","Today everything exists to end in a photograph. - Susan Sontag","I think good dreaming is what leads to good photographs. - Wayne Miller","I love the people I photograph. I mean, they’re my friends. I’ve never met most of them or I don’t know them at all, yet through my images I live with them. - Bruce Gilden","If you want to be a better photographer, stand in front of more interesting stuff. - Jim Richardson","When I say I want to photograph someone, what it really means is that I’d like to know them. Anyone I know I photograph. - Annie Leibovitz","My life is shaped by the urgent need to wander and observe, and my camera is my passport. - Steve McCurry","Look and think before opening the shutter. The heart and mind are the true lens of the camera. - Yousuf Karsh","The camera is an excuse to be someplace you otherwise don’t belong. It gives me both a point of connection and a point of separation. - Susan Meiselas","Most things in life are moments of pleasure and a lifetime of embarrassment; photography is a moment of embarrassment and a lifetime of pleasure. - Tony Benn","It is more important to click with people than to click the shutter. - Alfred Eisenstaedt","I like to photograph anyone before they know what their best angles are. - Ellen Von Unwerth","Great photography is about depth of feeling, not depth of field. - Peter Adams","Life is like a camera. Just focus on what’s important and capture the good times, develop from the negatives and if things don’t work out, just take another shot. - Unknown","Only photograph what you love. - Tim Walker","In photography there are no shadows that cannot be illuminated. - August Sander","When I photograph, what I’m really doing is seeking answers to things. - Wynn Bullock","Character, like a photograph, develops in darkness. - Yousuf Karsh","It’s weird that photographers spend years or even a whole lifetime, trying to capture moments that added together, don’t even amount to a couple of hours. - James Lalropui Keivom","Once photography enters your bloodstream, it is like a disease. - Anonymous","Which of my photographs is my favorite? The one I’m going to take tomorrow. - Imogen Cunningham"}; 
    public static String getPhotographyQuote(int number){
        if(number <QUOTES.length) return QUOTES[number];
        else return QUOTES[1];
    }
}
