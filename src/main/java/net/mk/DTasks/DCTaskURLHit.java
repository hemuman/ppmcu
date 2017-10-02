/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.DTasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import net.mk.dc.DistributedTask;
import net.mk.ppmcu.GlobalMCUTest;

/**
 *
 * @author PDI
 */
public class DCTaskURLHit extends DistributedTask {

    int counter = 0;
    // String URL = "http://manojky.net";
    String[] URLs;
    //boolean MultiURL = false;

    public DCTaskURLHit(String URL, int Counter) {
        //this.URL = URL;
        URLs = new String[Counter];

        for (int i = 0; i < Counter; i++) {
            URLs[i] = URL;
        }

        this.counter = Counter;
    }

    public DCTaskURLHit(String[] URLs) {
        this.URLs = URLs;
        this.counter = URLs.length;
    }

    @Override
    public byte[] compute() {
        StringBuilder sb = new StringBuilder("#DCTaskURLHit Nothing Set Yet");
        try {
            for (int i = 0; i < counter; i++) {
                URL oracle = new URL(URLs[i]);
                URLConnection yc = oracle.openConnection();
                System.out.println("Print#"+yc.getContentEncoding()); //gzip
                System.out.println("#DCTaskURLHit Counter=" + counter);
                
                BufferedReader in ;
                
                if(yc.getContentEncoding().equalsIgnoreCase("gzip")){
                in = new BufferedReader(new InputStreamReader(
                        new GZIPInputStream(yc.getInputStream())));}else{
                in = new BufferedReader(new InputStreamReader(
                        yc.getInputStream()));
                }
                String inputLine;
                sb = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    //System.out.println(inputLine);
                    sb.append(inputLine);
                }
                in.close();

            }
            //return "On this machine Primary Numbers Calculated in: "+new GlobalMCUTest().getPrimaryNumberCalcTimeMultiCore()+" sec(s)";
        } catch (MalformedURLException ex) {
            Logger.getLogger(DCTaskURLHit.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DCTaskURLHit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString().getBytes();
    }

    public static void main(String[] args) throws Exception {

        DCTaskURLHit dctuh = new DCTaskURLHit("http://manojky.net", 21);
        String s = new String(dctuh.compute());

        System.out.println(s);
//        URL oracle = new URL("http://www.oracle.com/");
//        URLConnection yc = oracle.openConnection();
//        BufferedReader in = new BufferedReader(new InputStreamReader(
//                yc.getInputStream()));
//        String inputLine;
//        while ((inputLine = in.readLine()) != null) {
//            System.out.println(inputLine);
//        }
//        in.close();
    }
}
