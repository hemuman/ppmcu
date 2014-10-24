/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu;

import net.mk.FJTasks.IsPrimeNumbersFJTask;
import net.mk.FJTasks.PrimeNumbersFJTask;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import javax.swing.JOptionPane;
import parallelprocessingmulticoreutilization.ForkJoinPoolTest;

/**
 *
 * @author PDI
 */
public class PrimeNumbersFinder {
    
    public int[] getPrimeNumbers(int FirstNumer,int LastNumber,int interval){
        long begTest = new java.util.Date().getTime();
        int difference=LastNumber-FirstNumer;
        int nrOfProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("#PrimeNumbersFinder.getPrimeNumbers NumberOf Prcessors= "+nrOfProcessors+"\nNumber of task="+(difference/interval));
        Vector result=new Vector();
        List futuresList = new ArrayList();
        ForkJoinPool fjPool = new ForkJoinPool(difference/interval);
        for (int numCount = 0; numCount <(difference/interval); numCount++) {
            
            futuresList.add(fjPool.submit(new PrimeNumbersFJTask(FirstNumer+interval*numCount, FirstNumer+interval*(numCount+1))));
        }
        Vector taskResult;
        System.out.println("#PrimeNumbersFinder.getPrimeNumbers Start collecting results");
        for (Object future : futuresList) {
            try {
                taskResult = (Vector) ((Future)future).get();
                for(int i=0;i<taskResult.size();i++){
                    result.add((int)taskResult.get(i));
                    //System.out.println("Prime number="+(int)taskResult.get(i));
                }
                
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            }
        }
        Double secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("#PrimeNumbersFinder.getPrimeNumbers  run time " + secs + " secs");
        
        return convertTo1DIntArray(result);
    }
    
    public boolean IsPrimeNumber(int IsPrimeNumber,int interval){
        long begTest = new java.util.Date().getTime();
        
        int nrOfProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("#PrimeNumbersFinder.getPrimeNumbers NumberOf Prcessors= "+nrOfProcessors+"\nNumber of task="+(IsPrimeNumber/interval));
        Vector result=new Vector();
        List futuresList = new ArrayList();
        ForkJoinPool fjPool = new ForkJoinPool(IsPrimeNumber/interval);
        for (int numCount = 0; numCount <(IsPrimeNumber/interval); numCount++) {
            futuresList.add(fjPool.submit(new IsPrimeNumbersFJTask(interval*numCount, interval*(numCount+1),IsPrimeNumber)));
        }
        
        System.out.println("#PrimeNumbersFinder.getPrimeNumbers Start collecting results");
        for (Object future : futuresList) {
            try {
                    if(!(Boolean)((Future)future).get()) return false;

            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            }
        }
        Double secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("#PrimeNumbersFinder.getPrimeNumbers  run time " + secs + " secs");
        
        return true;
    }

    public int[] convertTo1DIntArray(Vector vectorContainingInt){
        int[] result =new int[vectorContainingInt.size()];
        for(int i=0;i<result.length;i++){
        result[i]=(int) vectorContainingInt.get(i);
        }
        return result;
    }

    
    public static void main(String[] args) {
        System.out.println("Prime Number PPMCU utility by Manoj kumar");
       // numOfTasks=Integer.parseInt(JOptionPane.showInputDialog("Number of tasks to perform:"));
        
        System.out.println("[TEST START]=====getPrimeNumbers Single Task Time======");
        new PrimeNumbersFinder().getPrimeNumbers(1, 15000000,14999999);
        System.out.println("[TEST]                   =====END======");
        System.out.println("[TEST START]=====getPrimeNumbers Multiple Task Time======");
        new PrimeNumbersFinder().getPrimeNumbers(1, 15000000,1000);
        System.out.println("[TEST]                   =====END======");
        
        System.out.println("[TEST START]=====IsPrimeNumber Single Task Time======");
        System.out.println("Is 15000247 a prime number = "+new PrimeNumbersFinder().IsPrimeNumber(15000247,15000242));
        System.out.println("[TEST]                   =====END======");
        System.out.println("[TEST START]=====IsPrimeNumber Multiple Task Time======");
        System.out.println("Is 15000247 a prime number = "+new PrimeNumbersFinder().IsPrimeNumber(15000247,1000));
        System.out.println("[TEST]                   =====END======");
        
        //System.exit(0);
    }
}
