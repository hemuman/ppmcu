/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.image.BufferedImage;

/**
 *
 * @author Manoj
 */
public class HumanFaceVO {
    
    BufferedImage face;
    
    class BoundingBox{double Width,Height,Left,Top;}
    class FaceMesh{}
    class eyeLeft{double x,y;}
    class eyeRight{double x,y;}
    class nose{double x,y;}
    class mouthLeft{double x,y;}
    class mouthRight{double x,y;}
    class Pose{double Roll,Yaw,Pitch;}
    
    
}
