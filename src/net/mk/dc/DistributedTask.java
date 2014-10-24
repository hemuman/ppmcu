/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dc;

/**
 *
 * @author Manoj Kumar
 */
public abstract class DistributedTask implements java.io.Serializable {
    /**
     * Initiate all the variables in constructor
     */
    
    /**
     * Add abstract class to compute the task
     */
    
    public abstract Object compute();
    
        public String TASK_NAME = "NOT_SET";

    /**
     * Get the value of TASK_NAME
     *
     * @return the value of TASK_NAME
     */
    public String getTASK_NAME() {
        return TASK_NAME;
    }

    /**
     * Set the value of TASK_NAME
     *
     * @param TASK_NAME new value of TASK_NAME
     */
    public void setTASK_NAME(String TASK_NAME) {
        this.TASK_NAME = TASK_NAME;
    }

   
}
