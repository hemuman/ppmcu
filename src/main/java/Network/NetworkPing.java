/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manoj.kumar
 */
public class NetworkPing {

    public static void main(String... args) {
        ForkJoinPool fjp = new ForkJoinPool(100);
        //Do in loop
        for (int i = 0; i < 1000; i++) {
            System.out.println("#NetworkPing " + i);
            CallTheUrl task = new CallTheUrl("http://ec2-175-41-133-216.ap-southeast-1.compute.amazonaws.com:8080/publish/SeatLayout?routeId=1234&doj=26-Nov-2013&seats=48", i + "");
            Future f = fjp.submit(task);
        }
        
        while(!fjp.isShutdown()){
            
            if(fjp.isQuiescent()) break;
            
        }        
    }
}

class CallTheUrl extends RecursiveTask {

    String URL = "http://www.google.com";
    String TaskAlias = "Not Set";

    public CallTheUrl(String URL, String TaskAlias) {
        this.URL = URL;
        this.TaskAlias = TaskAlias;

    }

    @Override
    protected Void compute() {
        //System.out.println("#CallTheUrl " + TaskAlias);
        
        if(pingUrl(URL))
        try {
            for (int i = 0; i < 1000; i++) {
                //System.out.println("#CallTheUrl " + TaskAlias);
                URL URLToPing = new URL(URL);
                final long startTime = System.currentTimeMillis();
                URLConnection yc = URLToPing.openConnection();
                yc.setConnectTimeout(1000 * 5); // mTimeout is in seconds
                //authToken = r3dbus@in
                yc.setRequestProperty("authToken", "r3dbus@in");
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        yc.getInputStream()));
                final long endTime = System.currentTimeMillis();
                
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                   // System.out.println(inputLine);
                }
                System.out.println("#CallTheUrl Time (ms) : " + (endTime - startTime));
                in.close();
            }
        } catch (IOException ex) {
            System.out.println("Exception!!");
            Logger.getLogger(NetworkPing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean pingUrl(final String address) {
        try {
            final URL url = new URL( address);
            final HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(1000 * 5); // mTimeout is in seconds
            urlConn.setRequestProperty("authToken", "r3dbus@in");
            final long startTime = System.currentTimeMillis();
            urlConn.connect();
            final long endTime = System.currentTimeMillis();
            
            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                
                System.out.println("#pingUrl Time (ms) : " + (endTime - startTime));
                System.out.println("#pingUrl Ping to " + address + " was success");
                System.out.println("#pingUrl ResponseMessage " + urlConn.getResponseMessage());
                return true;
            }
        } catch (final MalformedURLException e1) {
            e1.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}