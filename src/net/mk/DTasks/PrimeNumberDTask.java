/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.DTasks;

import java.math.BigInteger;
import java.util.Vector;
import net.mk.dc.DistributedTask;

/**
 *
 * @author PDI
 */
public class PrimeNumberDTask extends DistributedTask{

    BigInteger FirstNumber,LastNumber;
 
/**
 * This will sort the array provided in input. Yet to be tested. 4/5/2013
 * @param XYZArray
 * @param ColumnToSort
 * @param Increasing 
 */
    public PrimeNumberDTask(BigInteger FirstNumber,BigInteger LastNumber) {
        this.FirstNumber = FirstNumber;
        this.LastNumber = LastNumber;
    }

    public PrimeNumberDTask() {
   
    }
    public Vector compute() {
        long begTest = new java.util.Date().getTime();
        Vector result=new Vector();
        BigInteger i=FirstNumber;
        while(!i.equals(LastNumber)){
        
            i=i.nextProbablePrime();
            result.add(i);
          }

        Double secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        //System.out.println("Manoj run time " + secs + " secs");
        return result;
    }
    
    boolean isPrime(BigInteger n) {
            //check if n is a multiple of 2
        System.out.println("# n % 2 = "+n.divideAndRemainder(new BigInteger("2"))[1]);
            if (n.divideAndRemainder(new BigInteger("2"))[1].compareTo(new BigInteger("0"))==0){ 
                
                return false;
            
            }
            //if not, then just check the odds
           
            for(BigInteger i=new BigInteger("3");i.multiply(i).compareTo(n)<=-1;i=i.add(new BigInteger("2"))) {
               //  System.out.println("."+n.divideAndRemainder(i)[1]);
                if(n.divideAndRemainder(i)[1]==new BigInteger("0"))
                {
                    return false;
                }
            //System.out.print("\b");
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
        
        long begTest = new java.util.Date().getTime();
         BigInteger i=new BigInteger("1000000000000");
        Double secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        i=i.nextProbablePrime();
        System.out.println("#PrimeNumberDTask Calculated in  " + secs + " secs\n Prime Number="+i+
                " Checking.."+new PrimeNumberDTask().isPrime(i));
        secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("#PrimeNumberDTask checked in  " + secs );
        // boolean isPrime(BigInteger n)
        
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
