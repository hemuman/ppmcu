/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcuGUI;

/**
 *
 * @author PDI
 */
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
public class Web {
     
      public static void main(String[] args) {
        //  new GlobalMCUTest().getPrimeNumberTestData().toString();
       //   SendPOST("http://127.0.0.1:8888/multicoreworld/mcst/mcst.php",new String[] {"mac","cores"},new String[] {"00:15:e8","8"}, false) ;
      }
    
    public  String SendPOST(String URLString, String[] vars, String[] vals, boolean PRINT_RESULT) {
       // final String server = "127.0.0.1:8888";

       if(PRINT_RESULT){System.out.println("#SendPOST "+URLString);}
        
        URL url = null;
        try {
            url = new URL(URLString/*"http://" + server + "/multicoreworld/mcst/index.php"*/);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
        }

        HttpURLConnection urlConn = null;
        try {
            // URL connection channel.
            urlConn = (HttpURLConnection) url.openConnection();
        } catch (IOException ex) {
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Let the run-time system (RTS) know that we want input.
        urlConn.setDoInput (true);

        // Let the RTS know that we want to do output.
        urlConn.setDoOutput (true);

        // No caching, we want the real thing.
        urlConn.setUseCaches (false);
        urlConn.setConnectTimeout(0);
        // Specify the content type if needed.
       // urlConn.setRequestProperty("Content-Type",  "application/json");

        try {
            urlConn.setRequestMethod("POST");
        } catch (ProtocolException ex) {
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            urlConn.connect();
        } catch (IOException ex) {
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
            return "{}";
        }

        DataOutputStream output = null;
        DataInputStream input = null;

        try {
            output = new DataOutputStream(urlConn.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
        }

        

        // Construct the POST data.
        String content="";
          try {
              //content = "mac=" + URLEncoder.encode("Manoj", "utf-8")+ "&cores=" + URLEncoder.encode("27");
              for(int i=0;i<vars.length;i++){
            content=content+"&"+vars[i]+"="+URLEncoder.encode(vals[i], "utf-8");
        }
              
              if(PRINT_RESULT){System.out.println("#content "+content);}
          } catch (UnsupportedEncodingException ex) {
              Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
          }
        
        

        // Send the request data.
        try {
            output.writeBytes(content);
            output.flush();
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Get response data.
        String str = null;
        String result="";
        try {
            input = new DataInputStream (urlConn.getInputStream());
            while (null != ((str = input.readLine()))) {
                //System.out.println(str);
                result=result+str;
            }
            input.close ();
            if(PRINT_RESULT){System.out.println("#result "+result);}
            
        } catch (IOException ex) {
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
      public static String SendGET(String URLString, String[] vars, String[] vals, boolean PRINT_RESULT) {
        final String server = "127.0.0.1:8888";

       
        
        URL url = null;
        try {
            url = new URL(URLString/*"http://" + server + "/multicoreworld/mcst/index.php"*/);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
        }

        HttpURLConnection urlConn = null;
        try {
            // URL connection channel.
            urlConn = (HttpURLConnection) url.openConnection();
        } catch (IOException ex) {
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Let the run-time system (RTS) know that we want input.
        urlConn.setDoInput (true);

        // Let the RTS know that we want to do output.
        urlConn.setDoOutput (true);

        // No caching, we want the real thing.
        urlConn.setUseCaches (false);
        
        // Specify the content type if needed.
       // urlConn.setRequestProperty("Content-Type",  "application/json");

        try {
            urlConn.setRequestMethod("GET");
        } catch (ProtocolException ex) {
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            urlConn.connect();
        } catch (IOException ex) {
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
            return "{}";
        }

        DataOutputStream output = null;
        DataInputStream input = null;

        try {
            output = new DataOutputStream(urlConn.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
        }

        

        // Construct the POST data.
        String content="";
          try {
              //content = "mac=" + URLEncoder.encode("Manoj", "utf-8")+ "&cores=" + URLEncoder.encode("27");
              for(int i=0;i<vars.length;i++){
            content=content+"&"+vars[i]+"="+URLEncoder.encode(vals[i], "utf-8");
        }
          } catch (UnsupportedEncodingException ex) {
              Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
          }
        
        

        // Send the request data.
        try {
            output.writeBytes(content);
            output.flush();
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Get response data.
        String str = null;
        String result="";
        try {
            input = new DataInputStream (urlConn.getInputStream());
            while (null != ((str = input.readLine()))) {
                //System.out.println(str);
                result=result+str;
            }
            input.close ();
        } catch (IOException ex) {
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
      
      private static String readAll(Reader rd) {
          try {
              StringBuilder sb = new StringBuilder();
              int cp;
              while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
              }
              //System.out.print(sb.toString());
              return sb.toString();
          } catch (IOException ex) {
              Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
          }
          return null;
  }

  public static JSONObject readJsonFromUrl(String url, boolean PRINT) {
          InputStream is = null;
   
              try {
                  
                  is = new URL(url).openStream();
              } catch (MalformedURLException ex) {
                  Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
              } catch (IOException ex) {
                  Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
              }
           try {    
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                //System.out.println("#readJsonFromUrl Text:"+jsonText);
                JSONObject json=new JSONObject();
                json.put("city", "NA");
                json.put("country_name", "NA");
                if(jsonText.startsWith("{")&jsonText.endsWith("}"))
                json = new JSONObject(jsonText);
                
                if(PRINT)
                System.out.println("#readJsonFromUrl JSON:"+json.toString(1));
                
                return json;
             
    }      catch (JSONException ex) {
              Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
          } 
    return new JSONObject();
  }
  
  public JSONArray readJsonArrayFromUrl(String url, boolean PRINT) {
          InputStream is = null;
    try {
              is = new URL(url).openStream();
              
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                //System.out.println("#readJsonFromUrl Text:"+jsonText);
                JSONArray json = new JSONArray(jsonText);
                if(PRINT)
                System.out.println("#readJsonFromUrl JSON:"+json.toString(1));
                
                return json;
             
    }     catch (MalformedURLException ex) {
              Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
          } catch (IOException ex) {
              Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
          } catch (JSONException ex) {
              Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
          } finally {
              try {
                  is.close();
              } catch (IOException ex) {
                  Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
              }
    }
    return new JSONArray();
  }
  
   public static BufferedImage getImage(String URLString){
          try {
              //System.out.println(URLString);
              return ImageIO.read(new URL(URLString));
          } catch (MalformedURLException ex) {
              Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
          } catch (IOException ex) {
              Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
          }

          return null;//Add functionality to return NoImageFound
   } 
}
