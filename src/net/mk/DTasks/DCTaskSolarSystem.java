/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.DTasks;

import net.mk.dc.DistributedTask;
import net.mk.os.OSAppGraphics;
import net.mk.ppmcu2D.ScreenCapture;
import net.mk.ppmcu2D.SolarSystem;
import net.mk.ppmcu2D.SolarSystemImage;

/**
 *
 * @author PDI
 */
public class DCTaskSolarSystem extends DistributedTask{

    int Width;
    int Height;
    double startTime;
    double endTime;
    boolean debug;
    
    public DCTaskSolarSystem(int Width, int Height,double startTime,double endTime, boolean debug){
        this.Width=Width;
        this.Height=Height;
        this.startTime=startTime;
        this.endTime=endTime;
        this.debug=debug;
    }
    
    
            
    @Override
    public byte[]  compute() {
        return ScreenCapture.getImageAsByte(new SolarSystemImage(Width, Height, startTime, endTime, debug).getSolSysGraphics(Width, Height, startTime, endTime, debug));
    }
    
}
