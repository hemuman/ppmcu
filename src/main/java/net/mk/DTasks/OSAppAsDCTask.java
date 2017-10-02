/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.DTasks;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.mk.dc.DistributedTask;
import net.mk.os.OSApp;
import net.mk.ppmcu.GlobalMCUTest;
import org.hyperic.sigar.SigarException;

/**
 *
 * @author PDI
 */
public class OSAppAsDCTask extends DistributedTask {

    public static final int ALL = 0;
    public static final int RAM_BASED = 1;
    public static final int AVAILABLE_RAM_BASED = 2;
    public static final int PROCESSOR_SPEED_BASED = 3;
    public static final int AVAILABLE_PROCESSOR_BASED = 4;
    public static final int HDD_SPACE_BASED = 5;
    public static final int AVAILABLE_HDD_SPACE_BASED = 6;
    public static final int NUMBER_OF_PROCESSOR_BASED = 7;
    public static final int TOTAL_RAM = 11;
    public static final int PROCESSOR_SPEED = 33;
    public static final int TOTAL_HDD_SPACE = 55;
    public static final int TOTAL_NUMBER_OF_PROCESSOR = 77;
    public static final int ALL_PROCESSESS = 100;

    private int BASED_ON;

    public OSAppAsDCTask(int BASED_ON) {
        this.BASED_ON = BASED_ON;
    }

    @Override
    public String compute() {

        switch (BASED_ON) {

            case AVAILABLE_PROCESSOR_BASED:
                return String.valueOf(OSApp.getAvailableCPU());
            case ALL:
                return new GlobalMCUTest().printMachineInfo(false, true).toString();
            case TOTAL_RAM:
                return String.valueOf(OSApp.getRAM());//Long
            case PROCESSOR_SPEED:
                return String.valueOf(OSApp.getCPUSpeed());//Double
            case TOTAL_NUMBER_OF_PROCESSOR:
                return String.valueOf(OSApp.getCPUCount());//Integer
            case ALL_PROCESSESS:
                try {
                    OSApp.getProcesses().toString();//Integer
                } catch (SigarException ex) {
                    Logger.getLogger(OSAppAsDCTask.class.getName()).log(Level.SEVERE, null, ex);
                }
            default:
                return new GlobalMCUTest().printMachineInfo(false, true).toString();
        }

    }

}
