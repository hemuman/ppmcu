/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkfs66o96;

/**
 *
 * @author PDI
 */
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class HTTPServer {

    public static String StaticResponse = "HTTPServer : Not used";
    public static String START_DIR = "/";
    public static String WAREHOUSE_DIR = "./";

    public static void main(String[] args) throws Exception {
//        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
//        server.createContext("/test", new MyHandler());
//        server.setExecutor(null); // creates a default executor
//        server.start();
        RunServer(4444, "");
    }

    public static void RunServer(int port, String Folder) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/" + Folder, new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Listen to http://localhost:" + port + "/" + Folder);
        System.out.println("For File download http://localhost:" + port + "/" + Folder+"/getfile=hs_err_pid11416.log");
        //http://localhost:4444/

    }

    static class MyHandler implements HttpHandler {

        public void handle(HttpExchange t) throws IOException {

            System.out.println("#HTTPServer request from:\t" + t.getRemoteAddress().getHostString()
                    + "\n ReqURI:" + t.getRequestURI()
                    + "\n ReqURI:" + t.getRequestMethod()
                    + "\t StaticResponse.length=" + StaticResponse.length());

            if (t.getRequestURI().toString().contains("getfile")) {
                String[] split = t.getRequestURI().toString().split("=");
                //byte[] myFile = new String("....................").getBytes(); //string about 8M
                //headers.add("filename","MyFileName.ZIP");
                //t.sendResponseHeaders(200, 0);

                if (new File(WAREHOUSE_DIR + "/" + split[1]).exists()) {
                    Headers headers = t.getResponseHeaders();
                    headers.add("Content-Type", "application/octet-stream");
                    headers.add("Content-Disposition", "attachment; filename=\"" + split[1] + "\"");
                    RandomAccessFile aFile = new RandomAccessFile(WAREHOUSE_DIR + "/" + split[1], "r");
                    t.sendResponseHeaders(200, new File(WAREHOUSE_DIR + "/" + split[1]).length());
                    OutputStream responseBody = t.getResponseBody();
                    
                    FileChannel inChannel = aFile.getChannel();
                    MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
                    System.out.println("#HTTPServer FileFound "+split[1]+ " in "+ System.getProperty("user.dir")+" length="+inChannel.size());
                    buffer.load();
                    byte[] myFile=new byte[buffer.limit()];
                    for (int i = 0; i < buffer.limit(); i++) {
                        
                        myFile[i]=buffer.get();
                       // System.out.print((char)myFile[i]);

                    }
                    responseBody.write(myFile);
                    responseBody.close();
                    buffer.clear(); // do something with the data and clear/compact it.
                    inChannel.close();
                    aFile.close();
                    
                } else {
                    System.out.println("#HTTPServer FileNotFound "+split[1]+ " in "+ System.getProperty("user.dir"));
                    t.sendResponseHeaders(400, 0);
                    t.getResponseBody().close();
                }

            } else {
                //System.out.println("#HTTPServer response:\t"+StaticResponse);
                //byte[] Bytes=response.getBytes("UTF-8");
                //response=Bytes.toString();
                t.sendResponseHeaders(200, StaticResponse.length());

                OutputStream os = t.getResponseBody();
                os.write(StaticResponse.getBytes());
                os.close();
            }
            /**
             * String response = "This is the response"; t.sendResponseHeaders(200, response.length()); OutputStream os = t.getResponseBody();
             * os.write(response.getBytes()); os.close();
             */

        }

    }

}
