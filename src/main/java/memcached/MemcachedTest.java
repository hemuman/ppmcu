///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package memcached;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.util.Map;
//import java.util.concurrent.TimeoutException;
//import net.rubyeye.xmemcached.KeyIterator;
//import net.rubyeye.xmemcached.MemcachedClient;
//import net.rubyeye.xmemcached.XMemcachedClient;
//import net.rubyeye.xmemcached.exception.MemcachedException;
//
///**
// *
// * @author manoj.kumar
// */
//public class MemcachedTest {
//     public static void main(String[] args) throws IOException, TimeoutException, InterruptedException, MemcachedException{
//        MemcachedClient client=new XMemcachedClient("10.120.10.33",11211);
//
//        //store a value for one hour(synchronously).
//        client.set("key",3600,"SerializedObject");
//        //Retrieve a value.(synchronously).
//        Object someObject=client.get("key");
//        //Retrieve a value.(synchronously),operation timeout two seconds.
//        someObject=client.get("key",2000);
//        someObject=client.get("LIS93929CDC3D908D685FFA1A7B6E7BB0F3",2000);
//         System.out.println("success="+someObject);
//        //Touch cache item ,update it's expire time to 10 seconds.
//        boolean success=client.touch("key",10);
//        System.out.println("success="+success);
//        //delete value
//        client.delete("key");
//        
//        //Stats
//        InetSocketAddress isa=new InetSocketAddress ("10.120.10.33",11211);
//        Map<String, String> KeyDumps= client.stats(isa);
//
//        //then you just access the reversedMap however you like...
//        for (Map.Entry entry : KeyDumps.entrySet()) {
//            System.out.println(entry.getKey() + ", " + entry.getValue());
//        }
//
//        KeyIterator ki=client.getKeyIterator(isa);
//        
//        while(ki.hasNext()){
//            String key=ki.next();
//            System.out.println("#Memcached Keys\t: "+key+" \n "+client.get(key));
//            
//        }
//    }
//}
