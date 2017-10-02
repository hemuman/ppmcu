/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelprocessingmulticoreutilization;

/**
 *
 * @author PDI
 */
public class SerialTest {
    private static int NUM_OF_TASKS = 50;
public SerialTest () {}
public void run() {
long begTest = new java.util.Date().getTime();
Object taskResult;
for(int i=0;i < NUM_OF_TASKS;i++) {
Task task = new Task(i);
taskResult = task.call();
System.out.println("result "+taskResult);
}
Double secs = new Double((new java.util.Date().getTime() - begTest)*0.001);
System.out.println("run time " + secs + " secs");
}
public static void main(String[] args) {
new SerialTest().run();
System.exit(0);
}
}
