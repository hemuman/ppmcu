/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.sun.net.httpserver.HttpExchange;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.imageio.ImageIO;
import net.mk.FJTasks.SendEmail;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Manoj
 */
public class GeneriUploadHandler extends CustomHandler {

    public static Map<String, String> _uuids = new HashMap();
    String fileExt = "";

    public GeneriUploadHandler(String fileExt) {
        this.fileExt = fileExt;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {

        Map queryMap = he.getRequestURI().getQuery() != null ? queryToMap(he.getRequestURI().getQuery()) : new HashMap<String, String>();
        byte[] result = new byte[0];
        if (!queryMap.containsKey("commKey")) {//Check if UUID exists
            try {
                String response = UUID.randomUUID().toString();//.fromString((queryMap.get("deviceId").toString()+queryMap.get("email").toString())).toString();
                _uuids.put(response, queryMap.get("email").toString());
                System.out.println(response.toString());
                result = response.toString().getBytes();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else { //If UUID exists then read the data from stream.
            String email = _uuids.get(queryMap.get("commKey"));
            _uuids.remove(queryMap.get("commKey"));//Also purge Key.
            System.out.print(email);

            InputStream inputStream = null;
            BufferedReader br = null;
            try {
                inputStream = he.getRequestBody();

                // br = new BufferedReader(new InputStreamReader(inputStream));
                String tempFileName = "delete/" + queryMap.get("commKey") + "." + fileExt;
                //65533
                //PNG First 8 Byte : 137 80 78 71 13 10 10 10
                //int seqPNG[]={ 137, 80 ,78, 71 ,13 ,10 ,10, 10};
                String seqPNG = 137 + "_" + 80 + "_" + 78 + "_" + 71 + "_" + 13 + "_";

                int binaryData = -1;
                boolean allowWrite = false;
                int counter = 0;
                StringBuilder seqMatch = new StringBuilder();
                while ((binaryData = inputStream.read()) != -1) {
                    seqMatch.append((char) binaryData);
                }
                BufferedImage image = decodeToImage(seqMatch.toString());

                try {
    // retrieve image

                    File outputfile = new File(tempFileName);
                    ImageIO.write(image, "png", outputfile);
                    System.out.println("File written to disk!");
                } catch (IOException e) {

                }

                //Finally send email. Try to submit it to a FJP Pool.
                SendEmail.sendAsyncEmail("QiChik | " + queryMap.get("commKey"), email, SendEmail.getHTMLEmbdEmail("Hello There!", tempFileName));
                result = "Submitted".getBytes();

            } catch (Exception e) {
                result = e.getMessage().getBytes();
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //Send out the message
        he.sendResponseHeaders(200, result.length);
        OutputStream oshe = he.getResponseBody();
        oshe.write(result);
        oshe.close();

    }

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

}
