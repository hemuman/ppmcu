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

public class ExecutorServiceTest {

    private static int NUM_OF_TASKS = 50;

    public ExecutorServiceTest() {
    }

    public void run() {
        long begTest = new java.util.Date().getTime();
        List futuresList = new ArrayList();
        int nrOfProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService eservice = Executors.newFixedThreadPool(nrOfProcessors);
        for (int index = 0; index < NUM_OF_TASKS; index++) {
            futuresList.add(eservice.submit(new Task(index)));
        }
        Object taskResult;
        for (Object future : futuresList) {
            try {
                taskResult = ((Future)future).get();
                System.out.println("result " + taskResult);
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            }
        }
        Double secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("run time " + secs + " secs");
    }

    public static void main(String[] args) {
        new ExecutorServiceTest().run();
        System.exit(0);
    }
}