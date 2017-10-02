///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package redis;
//
//import com.lambdaworks.redis.RedisClient;
//import com.lambdaworks.redis.RedisConnection;
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.util.concurrent.TimeoutException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import net.rubyeye.xmemcached.KeyIterator;
//import net.rubyeye.xmemcached.MemcachedClient;
//import net.rubyeye.xmemcached.XMemcachedClient;
//import net.rubyeye.xmemcached.exception.MemcachedException;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartFrame;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//
///**
// *
// * @author manoj.kumar
// */
//public class BackupMemCachedToRedis {
//    static RedisClient client = new RedisClient("10.120.10.33");
//     static RedisConnection<String, String> connection = client.connect();
//     static MemcachedClient memclient;
//    public static void main(String... args) throws IOException, TimeoutException, InterruptedException, MemcachedException{
//        XYSeries series1 = new XYSeries("Redis");
//        XYSeries series2 = new XYSeries("Memcached");
//        System.out.println("Setting Key=TestKey & Value=TestValue");
//        setValue("TestKey","TestValue");
//        memclient=new XMemcachedClient("10.120.10.33",11211);
//        
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(RedisTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        System.out.println("Getting value for TestKey="+ getValue("TestKey"));
//        
//         XYSeriesCollection dataset = new XYSeriesCollection(series1);
//         dataset.addSeries(series2);
//        
//        //JFreeChart cfact=ChartFactory.createXYLineChart("Redis Performace Chart", "Time(ms)", "DataSize(KB)", dataset,PlotOrientation.HORIZONTAL,true,true, true);
//         JFreeChart cfact=ChartFactory.createScatterPlot("Redis Vs Memcached Performace Chart", "Time(ms)", "DataSize(KB)", dataset,PlotOrientation.HORIZONTAL,true,true, true);
//       
//         ChartFrame cf=new ChartFrame("Test",cfact);
//         String randomValue="RandomTestValue";
//        cf.setVisible(true);
//      //Stats
//        InetSocketAddress isa=new InetSocketAddress ("10.120.10.33",11211);
//       
//        KeyIterator ki=memclient.getKeyIterator(isa);
//        long keys=0;
//        while(ki.hasNext()){
//            String key=ki.next();
//           // System.out.println("#Memcached Keys\t: "+key+" \n "+memclient.get(key));
//            System.out.println(key);
//            if(memclient.get(key)!=null){
//             setValue(key,memclient.get(key).toString());
//            keys++;
//        }else{
//           // System.out.println("#Memcached Null Keys\t: ");
//          //  memclient.delete(key);
//            }
//            
//             
//            
//        }
//        
//        System.out.println("Saved memcached keys to Redis: "+ keys);
//        //Redis latency test
//        StringBuilder sb=new StringBuilder();
//        long timePrev=System.currentTimeMillis();
//        //connection.save();
//        System.exit(0);
//    }
//    
//    
//    public static void setValue(String Key,String Value){
//       
//        connection.set(Key, Value);
//    }
//    
//    public static String getValue(String Key){
//
//        return connection.get(Key);
//    }
//    
//}
