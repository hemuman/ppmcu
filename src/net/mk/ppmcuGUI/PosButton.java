/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcuGUI;

/**
 *
 * @author PDI
 */
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import net.mk.os.OSAppGraphics;
 
 
public class PosButton extends JButton implements MouseListener {
 
 
//instance variables
 
private Color defaultColor;
private Color mouseOverColor;
CardLayout Layout1,Layout2;
JPanel Panel1,Panel2;
String[] text;
 
//class constructor
 public PosButton(String[] text, CardLayout Layout1, CardLayout Layout2,JPanel Panel1,JPanel Panel2) {
 
super("");
this.text=text;
this.Layout1=Layout1;
this.Layout2=Layout2;
this.Panel1=Panel1;
this.Panel2=Panel2;

 ImageIcon imgicon=new ImageIcon(OSAppGraphics.getIconGraphics(170, 100, text,Color.BLACK, true));
            setIcon(imgicon);
setBackground(defaultColor);
 
this.defaultColor = defaultColor;
 
this.mouseOverColor = mouseOverColor;
 
addMouseListener(this);
 
}
public PosButton(String text, Color defaultColor, Color mouseOverColor) {
 
super(text);
 
setBackground(defaultColor);
 
this.defaultColor = defaultColor;
 
this.mouseOverColor = mouseOverColor;
 
addMouseListener(this);
 
}
 
 
//override the methods of implemented MouseListener
 
public void mouseClicked(MouseEvent e) { 
Layout1.next(Panel1);
Layout2.show(Panel2,text[1]);
}
 
public void mousePressed(MouseEvent e) { }
 
public void mouseReleased(MouseEvent e) { }
 
 
 
public void mouseEntered(MouseEvent e) {
 
if(e.getSource()==this) { 
   ImageIcon  imgicon=new ImageIcon(OSAppGraphics.getIconGraphics(165, 95, text,Color.GRAY, true));
            setIcon(imgicon);

    this.setBackground(this.mouseOverColor); 
}
 
}
 
 
public void mouseExited(MouseEvent e) {
 
if(e.getSource()==this) { 
     ImageIcon  imgicon=new ImageIcon(OSAppGraphics.getIconGraphics(170, 100, text,Color.BLACK, true));
            setIcon(imgicon);
    this.setBackground(this.defaultColor); }
 
}
 
 
 
}
