///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package cui;
//
//import Network.NetworkPing;
//import rabbitmq.Recv;
//import rabbitmq.Send;
//
///**
// *
// * @author manoj.kumar
// */
//public class CUI {
//    
//    public static void main(String... args) throws Exception{
//        System.out.println("Benchmark Commands:"
//                +"\n sendRabbit" 
//                +"\n recieveRabbit"
//                +"\n networkping");
//        if(args[0].equalsIgnoreCase("sendRabbit")){
//            Send.main(new String[] {});
//        }
//        if(args[0].equalsIgnoreCase("recieveRabbit")){
//            Recv.main(new String[] {});
//        }
//        
//        if(args[0].equalsIgnoreCase("networkping")){
//            NetworkPing.main(new String[] {});
//        }
//    }
//    
//}
