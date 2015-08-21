
package server;

/**
 *
 * @author Administrator
 */

import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;


public class Server {

    public static void main(String[] args) throws Exception {
        int port=8000;
        
        if(args.length==1){port=Integer.parseInt(args[0]);}
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        server.createContext("/", new HealthCheck());
        server.createContext("/GetCommKey/", new GeneriUploadHandler(""));
        server.createContext("/SendEmail/", new GeneriUploadHandler("png"));
        server.setExecutor(null); // creates a default executor
        server.start();
        
        System.out.println("#Server Started");
    }

}
