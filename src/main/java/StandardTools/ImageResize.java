/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StandardTools;

/**
 *
 * @author PDI
 */
import java.awt.*;
import java.awt.image.*;
public class ImageResize {
  //  Image bgimage, objimage, objimage2;   
   String bgfile, objfile;
   int width, height;
   MediaTracker tracker;
   int mousex = 0, mousey = 0;
   int xl = 0, yl = 0;
     public Image ImageResize( Image objimage ,float scale)
      {



      // Transform the the object image by scaling
      //float scale = 0.8f;
      int oldwidth = objimage.getWidth(null);
      int oldheight = objimage.getHeight(null); 
      int newwidth = (int)(scale * oldwidth);
      int newheight = (int)(scale * oldheight); 
      int oldcx = oldwidth / 2;
      int oldcy = oldheight / 2;   
      int newcx = newwidth / 2;
      int newcy = newheight / 2;
      int [] olddata = new int[oldheight * oldwidth];
      int [] newdata = new int[newheight * newwidth];
      PixelGrabber imagegrabber = new PixelGrabber(objimage, 0, 0, 
         oldwidth, oldheight, olddata, 0, oldwidth);
      try
         {
         imagegrabber.grabPixels();
         }
      catch(InterruptedException e) {}

      int A = 0, R = 0, G = 0, B = 0;
      int oldX = 0, oldY = 0;
      for (int y = 0; y < newheight; y++) {
              for (int x = 0; x < newwidth; x++)
                 {
                 oldX = (int)((x - newcx) / scale + oldcx);
                 oldY = (int)((y - newcy) / scale + oldcy);
                 A = (olddata[oldY * oldwidth + oldX] & 0xFF000000) >> 24;
                 if (oldX >= 0 && oldX < oldwidth && oldY >= 0 && oldY < oldheight)
                    {               
                    R = ((olddata[oldY * oldwidth + oldX] & 0x00FF0000) >> 16) * 8 / 10;
                    G = ((olddata[oldY * oldwidth + oldX] & 0x0000FF00) >> 8) * 8 / 10;
                    B = (olddata[oldY * oldwidth + oldX] & 0x000000FF) * 8 / 10;                 
                    }
                 else
                    {
                    R = G = B = 0;
                    }
                 newdata[y * newwidth + x] = B & 0x000000FF;
                 newdata[y * newwidth + x] |= ((G & 0x000000FF) << 8);
                 newdata[y * newwidth + x] |= ((R & 0x000000FF) << 16);
                 newdata[y * newwidth + x] |= ((A & 0x000000FF) << 24);
                 }
          }    
     Toolkit tk=Toolkit.getDefaultToolkit ();;
     
     return tk.createImage(new MemoryImageSource(newwidth, newheight,newdata, 0, newwidth));
      }
}
