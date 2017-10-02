///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package redis;
//
//import com.lambdaworks.redis.RedisClient;
//import com.lambdaworks.redis.RedisConnection;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import static redis.RedisTest.client;
//
///**
// *
// * @author manoj.kumar
// */
//public class FloodRedisTest {
//
//    static RedisClient client = new RedisClient("10.120.10.33");
//     static RedisConnection<String, String> connection = client.connect();
//     
//    public static void main(String... args) {
//        int numberOfTasks = 1000000000;
//        int batchSize = 1000000;
//        ExecutorService es = Executors.newFixedThreadPool(1000);
//        for (int i = 0; i < numberOfTasks; i ++) {
//            final int start = i;
//            final int last = Math.min(i + batchSize, numberOfTasks);
//            es.submit(new Runnable() {
//                @Override
//                public void run() {
//                    for (int j = start; j < last; j++) {
//                       
//                        
//                        if(start%2==0)
//                        {long startTime=System.nanoTime();
//                           
//                            connection.set("TestKey"+Math.random(), "TestValue"+Math.random());
//                            System.out.println("#Set Thread\t"+start+" ->"+j +" \t nanos"+(System.nanoTime()-startTime)); // do something with j
//                        }
//                        else
//                        {long startTime=System.nanoTime();
//                           // System.out.println("#Get Thread\t"+start+" ->"+j+" \t"+(start%2)); // do something with j
//                            connection.get("TestKey"+Math.random());
//                            System.out.println("#\t\tGet Thread\t"+start+" ->"+j +" \t nanos="+(System.nanoTime()-startTime)); // do something with j
//                        }
//                        
//                        
//                    }
//                }
//            });
//        }
//        //es.shutdown();
//        if(es.isShutdown()){
//            System.exit(0);
//        }
////if(){}
//
//    }
//}
