/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.DTasks;

import java.math.BigInteger;
import net.mk.dc.DistributedTask;

/**
 *
 * @author PDI
 */
public class IsDivisibleByNumberDTask extends DistributedTask{
    
     BigInteger IsPrimeNumber,FirstNumber,LastNumber;
 
/**
 * This will sort the array provided in input. Yet to be tested. 4/5/2013
 * @param XYZArray
 * @param ColumnToSort
 * @param Increasing 
 */
 
    public IsDivisibleByNumberDTask(BigInteger FirstNumber,BigInteger LastNumber,BigInteger IsPrimeNumber) {
        this.FirstNumber = FirstNumber;
        this.LastNumber = LastNumber;
        this.IsPrimeNumber = IsPrimeNumber;
    }
    @Override
    public Boolean compute() {
         System.out.println("# Check  = "+FirstNumber+ " To "+LastNumber);
        return IsDivisible();
    }

    Boolean IsDivisible() {
            //check if n is a multiple of 2
        //System.out.println("# n % 2 = "+IsPrimeNumber.divideAndRemainder(new BigInteger("2"))[1]);
            if (IsPrimeNumber.divideAndRemainder(new BigInteger("2"))[1].compareTo(new BigInteger("0"))==0){ 
                
                return true;
            
            }
            //if not, then just check the odds
            //Convert FirstNumber to ODD number if it is even
            if (FirstNumber.divideAndRemainder(new BigInteger("2"))[1].compareTo(new BigInteger("0"))==0){   
                FirstNumber=FirstNumber.add(new BigInteger("1"));    
            }
            for(BigInteger i=FirstNumber;i.multiply(i).compareTo(LastNumber)<=-1;i=i.add(new BigInteger("2"))) {
               //  System.out.println("."+n.divideAndRemainder(i)[1]);
                if(IsPrimeNumber.divideAndRemainder(i)[1].compareTo(new BigInteger("0"))==0) //Always use compare to
                {
                    System.out.println(IsPrimeNumber+" Divisible by : "+i);
                    return true;
                }
            //System.out.println(IsPrimeNumber+" Dividing by : "+i);
            }
            return false;
        }
    
    static Boolean IsDivisible(BigInteger FirstNumber,BigInteger LastNumber,BigInteger IsPrimeNumber) {
            //check if n is a multiple of 2
        //System.out.println("# n % 2 = "+IsPrimeNumber.divideAndRemainder(new BigInteger("2"))[1]);
            if (IsPrimeNumber.divideAndRemainder(new BigInteger("2"))[1].compareTo(new BigInteger("0"))==0){ 
                
                return true;
            
            }
            //if not, then just check the odds
            //Convert FirstNumber to ODD number if it is even
            if (FirstNumber.divideAndRemainder(new BigInteger("2"))[1].compareTo(new BigInteger("0"))==0){   
                FirstNumber=FirstNumber.add(new BigInteger("1"));    
            }
            for(BigInteger i=FirstNumber;i.multiply(i).compareTo(LastNumber)<=-1;i=i.add(new BigInteger("2"))) {
                System.out.println(IsPrimeNumber+" Dividing by : "+i);
                 System.out.println("# "+IsPrimeNumber.divideAndRemainder(i)[1]);
                if(IsPrimeNumber.divideAndRemainder(i)[1].compareTo(new BigInteger("0"))==0)
                {
                    System.out.println(IsPrimeNumber+" Divisible by : "+i);
                    return true;
                }
            
            }
            return false;
        }
    
    public static void main(String[] args){
    
      BigInteger IsPrimeNumber=new BigInteger("1000005");
       BigInteger FirstNumber=new BigInteger("2");
        BigInteger LastNumber=new BigInteger("50");
        
        System.out.println(IsPrimeNumber+" Divisible by : "+IsDivisible(FirstNumber, LastNumber,IsPrimeNumber) );
      
    }
}
