/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.FJTasks;

import java.util.Vector;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author PDI
 */
public class IsPrimeNumbersFJTask extends RecursiveTask {
    int IsPrimeNumber,FirstNumber,LastNumber;
 
/**
 * This will sort the array provided in input. Yet to be tested. 4/5/2013
 * @param XYZArray
 * @param ColumnToSort
 * @param Increasing 
 */
    public IsPrimeNumbersFJTask(int FirstNumber,int LastNumber,int IsPrimeNumber) {
        this.IsPrimeNumber = FirstNumber;
        this.LastNumber = LastNumber;
        this.IsPrimeNumber = IsPrimeNumber;
    }

    public Boolean compute() {
        long begTest = new java.util.Date().getTime();
       
      
            if(!isPrime(IsPrimeNumber,FirstNumber,LastNumber)){
                return false;
            }
        
        Double secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        //System.out.println("Manoj run time " + secs + " secs");
        return true;
    }
          
        //checks whether an int is prime or not.
        boolean isPrime(int n,int FirstNumber,int LastNumber) {
            //check if n is a multiple of 2
            if (n%2==0) return false;
            //if not, then just check the odds
            for(int i=FirstNumber;i*i<=LastNumber;i+=2) {
                if(n%i==0)
                    return false;
            }
            return true;
        }
}
