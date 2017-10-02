/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.DTasks;

import mkfs66o96.ServletCom;
import net.mk.dc.DistributedTask;

/**
 *
 * @author PDI
 */
public class DCTaskURLFileDownload extends DistributedTask {

    int counter = 0;
    // String URL = "http://manojky.net";
    String[] URLs;
    //boolean MultiURL = false;

    public DCTaskURLFileDownload(String URL, int Counter) {
        //this.URL = URL;
        URLs = new String[Counter];

        for (int i = 0; i < Counter; i++) {
            URLs[i] = URL;
        }

        this.counter = Counter;
    }

    public DCTaskURLFileDownload(String[] URLs) {
        this.URLs = URLs;
        this.counter = URLs.length;
    }

    @Override
    public String[] compute() {
        String[] downloadedFiles = new String[counter];

        for (int i = 0; i < counter; i++) {
            downloadedFiles[i] = ServletCom.getTempFile(URLs[i]).getAbsolutePath();

        }
            //return "On this machine Primary Numbers Calculated in: "+new GlobalMCUTest().getPrimaryNumberCalcTimeMultiCore()+" sec(s)";

        return downloadedFiles;
    }

    public static void main(String[] args) throws Exception {

        DCTaskURLFileDownload dctuh = new DCTaskURLFileDownload("http://manojky.net", 21);
        //String s = new String(dctuh.compute());

        // System.out.println(s);
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
