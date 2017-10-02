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
public class DTaskProcessorAdmin extends DistributedTask {

    int counter = 0;
    public boolean PERSIST_CONNECTION = false;

    public DTaskProcessorAdmin(boolean PERSIST_CONNECTION) {
        this.PERSIST_CONNECTION = PERSIST_CONNECTION;
    }

    @Override
    public Object compute() {

        return "#DTaskProcessorAdmin Executed!";//"On this machine Primary Numbers Calculated in: "+new GlobalMCUTest().getPrimaryNumberCalcTimeMultiCore()+" sec(s)";
    }

}
