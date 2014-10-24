/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.DTasks;

import net.mk.dc.DistributedTask;
import net.mk.ppmcu.GlobalMCUTest;

/**
 *
 * @author PDI
 */
public class DTaskTest extends DistributedTask{
    int counter=0;
    public DTaskTest(int Counter)
    {
        this.counter=Counter;
    }
    @Override
    public Object compute() {
       
        return "On this machine Primary Numbers Calculated in: "+new GlobalMCUTest().getPrimaryNumberCalcTimeMultiCore()+" sec(s)";
    }
    
}
