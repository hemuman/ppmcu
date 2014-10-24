/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelprocessingmulticoreutilization;

/**
 *
 * @author PDI
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import javax.swing.JOptionPane;

public class ForkJoinPoolTest {

    public ForkJoinPoolTest() {
    }
    private static int numOfTasks = 100;

    public void run() {
        long begTest = new java.util.Date().getTime();
        List futuresList = new ArrayList();
        ForkJoinPool fjPool = new ForkJoinPool(numOfTasks);
        for (int index = 0; index < numOfTasks; index++) {
            futuresList.add(fjPool.submit(new FJTask(index)));
        }
        Object taskResult;
        for (Object future : futuresList) {
            try {
                taskResult = ((Future)future).get();
                System.out.println("result ForkJoin " + taskResult);
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            }
        }
        Double secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("run time " + secs + " secs");
    }

    
    
    public static void main(String[] args) {
        System.out.println("Test utility by Manoj kumar");
        numOfTasks=Integer.parseInt(JOptionPane.showInputDialog("Number of tasks to perform:"));
        
        new ForkJoinPoolTest().run();
        //System.exit(0);
    }
}
