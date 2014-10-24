/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.DTasks;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import net.mk.dc.DistributedTask;
import net.mk.ppmcu2D.ScreenCapture;

/**
 *
 * @author Manoj Kumar
 */
public class FilePreviewDTask extends DistributedTask {

    String AbsolutePath;
    int Height=100;
    int Width=100;
    
    public FilePreviewDTask(String AbsolutePath,int Width,int Height){
        this.AbsolutePath=AbsolutePath;
        if(Width>0){this.Width=Width;}
        if(Height>0) {this.Height=Height;}
        System.out.println("#FilePreviewDTask\t Width="+Width +"\t Height="+Height);
    }
    
    @Override
        public byte[] compute() {
        //Resize Image and send back
        BufferedImage originalImage = ScreenCapture.getByteAsImage(new FileTransferDTask(AbsolutePath, new byte[]{}, true).compute());
        BufferedImage previewImage = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = previewImage.createGraphics();
        g.drawImage(originalImage, 0, 0, Width, Height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return ScreenCapture.getImageAsByte(previewImage);
        
    }
    
}
