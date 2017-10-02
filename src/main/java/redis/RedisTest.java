///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package redis;
//
//import com.lambdaworks.redis.RedisClient;
//import com.lambdaworks.redis.RedisConnection;
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
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
//public class RedisTest {
//     static RedisClient client = new RedisClient("10.120.10.33");
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
//        
//        //Redis latency test
//        StringBuilder sb=new StringBuilder();
//        long timePrev=System.currentTimeMillis();
//        for(int i=0;i<100000;i++){ //Infinite
//            //sb=new StringBuilder();
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);sb.append(randomValue);
//            
//            setValue("TestKey"+i,sb.toString());
//            getValue("TestKey"+i);
//            series1.add(System.currentTimeMillis()-timePrev, 1.0*sb.toString().length()/1024.0);
//            
//            timePrev=System.currentTimeMillis();
//            
//            //store a value for one hour(synchronously).
//             memclient.set("TestKey"+i,3600,sb.toString());
//             memclient.get("TestKey"+i,2000);
//             series2.add(System.currentTimeMillis()-timePrev, 1.0*sb.toString().length()/1024.0);
//             timePrev=System.currentTimeMillis();
//             
//             //if(i>=1000){series1.remove(i-1000); series2.remove(i-1000); }
//        }
//        //System.exit(0);
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
//    
//}
