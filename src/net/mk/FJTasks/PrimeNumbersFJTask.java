/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.FJTasks;

import java.math.BigInteger;
import java.util.Vector;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author PDI
 */
public class PrimeNumbersFJTask extends RecursiveTask {
    int FirstNumber,LastNumber;
 
/**
 * This will sort the array provided in input. Yet to be tested. 4/5/2013
 * @param XYZArray
 * @param ColumnToSort
 * @param Increasing 
 */
    public PrimeNumbersFJTask(int FirstNumber,int LastNumber) {
        this.FirstNumber = FirstNumber;
        this.LastNumber = LastNumber;
    }

    public Vector compute() {
        long begTest = new java.util.Date().getTime();
        Vector result=new Vector();
        for(int i=FirstNumber;i<=LastNumber;i++){
            if(isPrime(i)){
                result.add(i);
            }
        }
        Double secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        //System.out.println("Manoj run time " + secs + " secs");
        return result;
    }
    
       //checks whether an int is prime or not.
        boolean isPrime(int n) {
            //check if n is a multiple of 2
            if (n%2==0) return false;
            //if not, then just check the odds
            for(int i=3;i*i<=n;i+=2) {
                if(n%i==0)
                    return false;
            }
            return true;
        }
        
        public static void main(String... args) {
        int place = args.length > 0 ? Integer.parseInt(args[0]) : 250 * 1000;
        long start = System.nanoTime();
        BigInteger fibNumber = fib(place);
        long time = System.nanoTime() - start;

        System.out.println(place + "th fib # is: " + fibNumber);
        System.out.printf("Time to compute: %5.1f seconds.%n", time / 1.0e9);
    }

    private static BigInteger fib(int place) {
        BigInteger a = new BigInteger("0");
        BigInteger b = new BigInteger("1");
        while (place-- > 1) {
            BigInteger t = b;
            b = a.add(b);
            a = t;
        }
        return b;
    }
}
