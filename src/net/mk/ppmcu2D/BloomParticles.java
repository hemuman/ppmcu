/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu2D;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author PDI
 */
public class BloomParticles {
    
    public  BufferedImage getStars(int Width, int Height,double startTime,double endTime, boolean debug){
         
        //screen dimensions
   int WIDE = Width;
    int HIGH = Height;
   //particles
  int MAX_PARTICLES = 500;//6000;
    int MAX_FRAMES = 50;
   
     BufferedImage screen = new BufferedImage(Width, Height, 1);
     //BufferedImage screen = new BufferedImage(Width, Height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
         int[]  pixl = ((DataBufferInt) screen.getRaster().getDataBuffer()).getData();
         Graphics2D g2 = screen.createGraphics();
         g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
         Random ran = new Random();

         //prep particle lightarray
         //x,y,fade-frames
         int[][][] particleFrame = new int[10][10][MAX_FRAMES];

         for (int i = 0; i < MAX_FRAMES; i++)
         {

            for (int x = 0; x < 10; x++)
            {
               for (int y = 0; y < 10; y++)
               {
                  double dist = Math.sqrt((x - 5d) * (x - 5d) + (y - 5d) * (y - 5d));

                  dist = 255 - dist * 60 - i;

                  dist = dist < 0 ? 0 : dist;
                  dist = dist > 255 ? 255 : dist;

                  particleFrame[x][y][i] = (int) dist;
               }
            }
         }

         int[] particlesFrame = new int[MAX_PARTICLES]; //frame
         float[][] particlesBase = new float[MAX_PARTICLES][4]; //x,y , velocity x,y

         for (int i = 0; i < MAX_PARTICLES; i++)
         {
            particlesBase[i][0] = ran.nextInt(WIDE*90/100) ;
            particlesBase[i][1] =  ran.nextInt(HIGH*90/100);

            particlesBase[i][2] = ran.nextFloat()*2 - 1f;
            particlesBase[i][3] = ran.nextFloat()*2 - 1f;

            particlesFrame[i] = ran.nextInt(MAX_FRAMES);
         }
         
         
           for (int i = 0; i < MAX_PARTICLES; i++)
            {

               particlesFrame[i]++;
               if (particlesFrame[i] >= MAX_FRAMES) particlesFrame[i] = 0;

               if (particlesBase[i][2] < 0d && particlesBase[i][0] < 10) particlesBase[i][2] = -particlesBase[i][2];
               else if (particlesBase[i][2] > 0d && particlesBase[i][0] > WIDE - 15) particlesBase[i][2] = -particlesBase[i][2];

               if (particlesBase[i][3] < 0d && particlesBase[i][1] < 10) particlesBase[i][3] = -particlesBase[i][3];
               else if (particlesBase[i][3] > 0d && particlesBase[i][1] > HIGH - 15) particlesBase[i][3] = -particlesBase[i][3];

               particlesBase[i][0] += particlesBase[i][2];
               particlesBase[i][1] += particlesBase[i][3];

               int px = (int) particlesBase[i][0];
               int py = (int) particlesBase[i][1];

               for (int x = 0; x <10; x++)
               {
                  for (int y = 0; y <10; y++)
                  {

                     int pc = (pixl[WIDE * (y + py) + x + px]) & 0xFF;
                     pc += particleFrame[x][y][particlesFrame[i]];
                     pc = pc > 255 ? 255 : pc;
                     int pc2 = (pc > 230) ? pc : 0;

                     pixl[WIDE * (y + py) + x + px] = pc | (pc << 8) | (pc2 << 16);

                  }
               }
            }


         return screen;
}
    
    public  BufferedImage getStarsFixed(int Width, int Height,double startTime,double endTime, boolean debug){
         
        //screen dimensions
   int WIDE = Width;
    int HIGH = Height;
   //particles
  int MAX_PARTICLES = 500;//6000;
    int MAX_FRAMES = 50;
   
     BufferedImage screen = new BufferedImage(Width, Height, 1);
     //BufferedImage screen = new BufferedImage(Width, Height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
         int[]  pixl = ((DataBufferInt) screen.getRaster().getDataBuffer()).getData();
         Graphics2D g2 = screen.createGraphics();
         g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
         Random ran = new Random();

         //prep particle lightarray
         //x,y,fade-frames
         int[][][] particleFrame = new int[10][10][MAX_FRAMES];

         for (int i = 0; i < MAX_FRAMES; i++)
         {

            for (int x = 0; x < 10; x++)
            {
               for (int y = 0; y < 10; y++)
               {
                  double dist = Math.sqrt((x - 5d) * (x - 5d) + (y - 5d) * (y - 5d));

                  dist = 255 - dist * 60 - i;

                  dist = dist < 0 ? 0 : dist;
                  dist = dist > 255 ? 255 : dist;

                  particleFrame[x][y][i] = (int) dist;
               }
            }
         }

         int[] particlesFrame = new int[MAX_PARTICLES]; //frame
         float[][] particlesBase = new float[MAX_PARTICLES][4]; //x,y , velocity x,y

         for (int i = 0; i < MAX_PARTICLES; i++)
         {
            particlesBase[i][0] = WIDE/100;//ran.nextInt(WIDE*90/100) ;
            particlesBase[i][1] = HIGH/100;//ran.nextInt(HIGH*90/100);

            particlesBase[i][2] = ran.nextFloat()*2 - 1f;
            particlesBase[i][3] = ran.nextFloat()*2 - 1f;

            particlesFrame[i] = ran.nextInt(MAX_FRAMES);
         }
         
         
           for (int i = 0; i < MAX_PARTICLES; i++)
            {

               particlesFrame[i]++;
               if (particlesFrame[i] >= MAX_FRAMES) particlesFrame[i] = 0;

               if (particlesBase[i][2] < 0d && particlesBase[i][0] < 10) particlesBase[i][2] = -particlesBase[i][2];
               else if (particlesBase[i][2] > 0d && particlesBase[i][0] > WIDE - 15) particlesBase[i][2] = -particlesBase[i][2];

               if (particlesBase[i][3] < 0d && particlesBase[i][1] < 10) particlesBase[i][3] = -particlesBase[i][3];
               else if (particlesBase[i][3] > 0d && particlesBase[i][1] > HIGH - 15) particlesBase[i][3] = -particlesBase[i][3];

               //Use velocity
              // particlesBase[i][0] += particlesBase[i][2];
              // particlesBase[i][1] += particlesBase[i][3];

               int px = (int) particlesBase[i][0];
               int py = (int) particlesBase[i][1];

               for (int x = 0; x <10; x++)
               {
                  for (int y = 0; y <10; y++)
                  {

                     int pc = (pixl[WIDE * (y + py) + x + px]) & 0xFF;
                     pc += particleFrame[x][y][particlesFrame[i]];
                     pc = pc > 255 ? 255 : pc;
                     int pc2 = (pc > 230) ? pc : 0;

                     pixl[WIDE * (y + py) + x + px] = pc | (pc << 8) | (pc2 << 16);

                  }
               }
            }


         return screen;
}
    
    
    public  BufferedImage getStarsNext(BufferedImage screen,int Width, int Height,double startTime,double endTime, boolean debug){
         
        //screen dimensions
   int WIDE = Width;
    int HIGH = Height;
   //particles
  int MAX_PARTICLES = 500;//6000;
    int MAX_FRAMES = 50;
   
     //BufferedImage screen = new BufferedImage(Width, Height, 1);
     //BufferedImage screen = new BufferedImage(Width, Height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
         int[]  pixl = ((DataBufferInt) screen.getRaster().getDataBuffer()).getData();
         Graphics2D g2 = screen.createGraphics();
         g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
         Random ran = new Random();

         //prep particle lightarray
         //x,y,fade-frames
         int[][][] particleFrame = new int[10][10][MAX_FRAMES];

         for (int i = 0; i < MAX_FRAMES; i++)
         {

            for (int x = 0; x < 10; x++)
            {
               for (int y = 0; y < 10; y++)
               {
                  double dist = Math.sqrt((x - 5d) * (x - 5d) + (y - 5d) * (y - 5d));

                  dist = 255 - dist * 60 - i;

                  dist = dist < 0 ? 0 : dist;
                  dist = dist > 255 ? 255 : dist;

                  particleFrame[x][y][i] = (int) dist;
               }
            }
         }

         int[] particlesFrame = new int[MAX_PARTICLES]; //frame
         float[][] particlesBase = new float[MAX_PARTICLES][4]; //x,y , velocity x,y

         for (int i = 0; i < MAX_PARTICLES; i++)
         {
            particlesBase[i][0] = WIDE/2+ ran.nextInt(WIDE/4) - WIDE/8;
            particlesBase[i][1] = HIGH/2+ ran.nextInt(HIGH/4)- HIGH/8;

            particlesBase[i][2] = ran.nextFloat()*2 - 1f;
            particlesBase[i][3] = ran.nextFloat()*2 - 1f;

            particlesFrame[i] = ran.nextInt(MAX_FRAMES);
         }
         
         
           for (int i = 0; i < MAX_PARTICLES; i++)
            {

               particlesFrame[i]++;
               if (particlesFrame[i] >= MAX_FRAMES) particlesFrame[i] = 0;

               if (particlesBase[i][2] < 0d && particlesBase[i][0] < 10) particlesBase[i][2] = -particlesBase[i][2];
               else if (particlesBase[i][2] > 0d && particlesBase[i][0] > WIDE - 15) particlesBase[i][2] = -particlesBase[i][2];

               if (particlesBase[i][3] < 0d && particlesBase[i][1] < 10) particlesBase[i][3] = -particlesBase[i][3];
               else if (particlesBase[i][3] > 0d && particlesBase[i][1] > HIGH - 15) particlesBase[i][3] = -particlesBase[i][3];

               particlesBase[i][0] += particlesBase[i][2];
               particlesBase[i][1] += particlesBase[i][3];

               int px = (int) particlesBase[i][0];
               int py = (int) particlesBase[i][1];

               for (int x = 0; x <10; x++)
               {
                  for (int y = 0; y <10; y++)
                  {

                     int pc = (pixl[WIDE * (y + py) + x + px]) & 0xFF;
                     pc += particleFrame[x][y][particlesFrame[i]];
                     pc = pc > 255 ? 255 : pc;
                     int pc2 = (pc > 230) ? pc : 0;

                     pixl[WIDE * (y + py) + x + px] = pc | (pc << 8) | (pc2 << 16);

                  }
               }
            }


         return screen;
}
    
    public static void main(String args[]){
     ImageIcon icon = new ImageIcon();
        icon.setImage(new BloomParticles().getStarsFixed(800, 800,1.1,1.1, true));

        JOptionPane.showMessageDialog(null, icon);}
}
