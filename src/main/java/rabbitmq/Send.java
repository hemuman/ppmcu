///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package rabbitmq;
//
///**
// *
// * @author manoj.kumar
// */
//import com.rabbitmq.client.ConnectionFactory;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.Channel;
//import java.io.IOException;
//import java.util.concurrent.ForkJoinPool;
//import java.util.concurrent.Future;
//import java.util.concurrent.RecursiveTask;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class Send {
//
//    private final static String QUEUE_NAME = "hello";
//
//    public static void main(String[] argv) throws Exception {
//
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//        
//        ForkJoinPool fjp = new ForkJoinPool(100);
//
//    //Do in loop
//        for (int i = 0; i < 100; i++) {
//           GetVendorDataAndSave task = new GetVendorDataAndSave();
//            Future f = fjp.submit(task);
//        }
//        channel.close();
//        connection.close();
//    }
//    
//    
//    
//}
// class GetVendorDataAndSave extends RecursiveTask {
//     String QUEUE_NAME = "hello";
//    ConnectionFactory factory = new ConnectionFactory();
//       Channel channel;
//       Connection connection;
//    public GetVendorDataAndSave() {
//        try {
//            factory.setHost("localhost");
//            connection = factory.newConnection();
//            channel = connection.createChannel();
//        } catch (IOException ex) {
//            Logger.getLogger(GetVendorDataAndSave.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    @Override
//    protected Void compute() {
//         try {
//             for (int i = 0; i < 100000; i++) {
//                 try {
//                     channel.queueDeclare(QUEUE_NAME , false, false, false, null);
//                     String message = "Hello World! # " + i;
//                     channel.basicPublish("", QUEUE_NAME , null, message.getBytes());
//                     System.out.println(" [x] Sent '" + message + "'");
//                     
//                     Thread.sleep(10);
//                 } catch (IOException ex) {
//                     Logger.getLogger(GetVendorDataAndSave.class.getName()).log(Level.SEVERE, null, ex);
//                 } catch (InterruptedException ex) {
//                     Logger.getLogger(GetVendorDataAndSave.class.getName()).log(Level.SEVERE, null, ex);
//                 }
//             }
//             channel.close();
//             connection.close();
//            
//         } catch (IOException ex) {
//             Logger.getLogger(GetVendorDataAndSave.class.getName()).log(Level.SEVERE, null, ex);
//         }
//         return null;
//    }}