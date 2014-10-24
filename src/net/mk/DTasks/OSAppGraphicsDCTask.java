/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.DTasks;

import net.mk.dc.DistributedTask;
import net.mk.os.OSAppGraphics;
import net.mk.ppmcu2D.ScreenCapture;

/**
 *
 * @author PDI
 */
public class OSAppGraphicsDCTask extends DistributedTask{
    final int ALL=0;
    final int RAM_BASED=1;
    final int AVAILABLE_RAM_BASED=2;
    final int PROCESSOR_SPEED_BASED=3;
    final int AVAILABLE_PROCESSOR_BASED=4;
    final int HDD_SPACE_BASED=5;
    final int AVAILABLE_HDD_SPACE_BASED=6;
    final int NUMBER_OF_PROCESSOR_BASED=7;
    int BASED_ON;
    int IMG_WIDTH=100;
    int IMG_HEIGHT=70;
    boolean debug=false;
    public OSAppGraphicsDCTask(int BASED_ON){
        this.BASED_ON=BASED_ON;
    }
     
     public OSAppGraphicsDCTask(int BASED_ON,int IMG_WIDTH,int IMG_HEIGHT,boolean debug){
        this.BASED_ON=BASED_ON;
        this.IMG_WIDTH=IMG_WIDTH;
        this.IMG_HEIGHT=IMG_HEIGHT;
        this.debug=debug;
    }
    
     
    @Override
    public byte[] compute() {
        
        switch (BASED_ON){
            case AVAILABLE_PROCESSOR_BASED:
                 return ScreenCapture.getImageAsByte(OSAppGraphics.getCPUUsageGraphicsKhakee(IMG_WIDTH,IMG_HEIGHT,debug));
            case ALL:
                  return ScreenCapture.getImageAsByte(OSAppGraphics.getCPUUsageGraphicsKhakee(IMG_WIDTH,IMG_HEIGHT,debug));
            case 1000:
                  return ScreenCapture.getImageAsByte(OSAppGraphics.getCPUUsageGraphics(IMG_WIDTH,IMG_HEIGHT,debug));
            default:
                 return ScreenCapture.getImageAsByte(OSAppGraphics.getCPUUsageGraphicsKhakee(IMG_WIDTH,IMG_HEIGHT,debug));
        }
    }
    //getCPUUsageGraphicsKhakee
}
