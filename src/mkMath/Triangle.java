/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkMath;

/**
 *
 * @author PDI
 */
public class Triangle {
    
    public Double SideAB,SideBC,SideCA,MinLength;
    public Line LineAB,LineBC,LineCA;
    public Double AngleA,AngleB,AngleC,MinimumAngleDeg;
    public Double Area,AverageLength,AverageAngleRad,AverageAngleDeg, MinimumAngle,MaximumAngle;
    public Triangle(Double[][] TwoDTriangle){
    
        if(TwoDTriangle.length==3){ // If it is a triangle
            
            LineAB=new Line();
            LineAB.setMXpC(TwoDTriangle[0], TwoDTriangle[1]);SideAB=LineAB.Distance;
            LineBC=new Line();
            LineBC.setMXpC(TwoDTriangle[1], TwoDTriangle[2]);SideBC=LineBC.Distance;
            LineCA=new Line();
            LineCA.setMXpC(TwoDTriangle[2], TwoDTriangle[0]);SideCA=LineCA.Distance;
            AngleA=Line.angleBeteenLines(LineAB, LineCA);
            AngleB=Line.angleBeteenLines(LineAB, LineBC);
            AngleC=Line.angleBeteenLines(LineBC, LineCA);
            AverageLength=(SideAB+SideBC+SideCA)/3.0;
            AverageAngleRad=(AngleA+AngleB+AngleC)/3.0;
            AverageAngleDeg=Math.toDegrees(AverageAngleRad);
            MinimumAngle=Math.min(AngleA, AngleB);
            MinimumAngle=Math.min(MinimumAngle, AngleC);
            
            MinimumAngleDeg=Math.toDegrees(MinimumAngle);
            
            MinLength=Math.min(SideAB, SideBC);
            MinLength=Math.min(MinLength, SideCA);
            
        }
    }
    
    
}
