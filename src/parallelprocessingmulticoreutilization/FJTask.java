/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelprocessingmulticoreutilization;

/**
 *
 * @author PDI
 */
import java.util.concurrent.RecursiveTask;

class FJTask extends RecursiveTask {

    private int seq;

    public FJTask(int n) {
        this.seq = n;
    }

    public Integer compute() {
        String str = "";
        long begTest = new java.util.Date().getTime();
        System.out.println("start - Task " + seq);
        try {
            Thread.sleep(1000);
            for (int index = 0; index < 20000; index++) {
                str = str + 't';
            }
        } catch (InterruptedException e) {
        }
        Double secs = new Double((new java.util.Date().getTime() - begTest) * 0.001);
        System.out.println("Manoj run time " + secs + " secs");
        return seq;
    }

}
