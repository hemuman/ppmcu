/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package StandardTools;

/**
 *
 * @author YadavM
 */
// Fri Oct 25 18:07:43 EST 2004
//
// Written by Sean R. Owens, sean at guild dot net, released to the
// public domain.  Share and enjoy.  Since some people argue that it is
// impossible to release software to the public domain, you are also free
// to use this code under any version of the GPL, LPGL, or BSD licenses,
// or contact me for use of another license.
// http://darksleep.com/player

// A very simple custom dialog that takes a string as a parameter,
// displays it in a JLabel, along with two Jbuttons, one labeled Yes,
// and one labeled No, and waits for the user to click one of them.


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class CustomDialog extends JDialog implements ActionListener {
    private JPanel myPanel = null;
    private JButton yesButton = null;
    private JButton noButton = null;
    private JButton button;
    private boolean answer = false;
    public double name=0.0;
    public boolean getAnswer() { return answer; }

    public CustomDialog(JFrame frame, boolean modal, String myMessage) {
        super(frame, modal);
     button = new JButton ( "-:[ Click Here to Hide This Window ]:-" );
        //cp.add(new RenderingTool(),BorderLayout.CENTER);
        //getContentPane().add(new RenderingTool(),BorderLayout.CENTER);
        getContentPane().add ( button,BorderLayout.SOUTH ) ;
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }
/**
 * Its not re-sizable
 * @param frame
 * @param jp
 * @param modal
 * @param myMessage
 */
    public CustomDialog(JFrame frame,JPanel jp, boolean modal, String myMessage) {
        super(frame, modal);

        //cp.add(new RenderingTool(),BorderLayout.CENTER);
         getContentPane().add(jp,BorderLayout.NORTH);
       // getContentPane().add ( button,BorderLayout.WEST ) ;
        //getContentPane().add ( yesButton,BorderLayout.EAST ) ;
        pack();
        setLocationRelativeTo(frame);
        setAlwaysOnTop(true);
        setVisible(true);
        setTitle(myMessage);
        setResizable(false);
        repaint();
    }
    
 /*   @Override
    public boolean action ( Event evt, Object arg )  {
     if  ( evt.target instanceof Button )  {
       dispose (  ) ;
      }
     if  ( evt.target instanceof JButton )  {
      captureActiveWindow();
      }
     return true;
    }*/

//Read more: http://kickjava.com/77.htm#ixzz0x62CDFex

    public CustomDialog(JFrame frame,JFrame jf, boolean modal, String myMessage) {
        super(frame, modal);
button = new JButton ( "-:[ Click Here to Preview ]:-" );
yesButton = new JButton ( "-:[ Click Here to Capture This Window ]:-" );


        //cp.add(new RenderingTool(),BorderLayout.CENTER);
        getContentPane().add(jf,new CardLayout());
        getContentPane().add ( button ) ;
        getContentPane().add ( yesButton ) ;
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(yesButton == e.getSource()) {
            //System.err.println("User chose yes.");
            //answer = true;
            //setVisible(false);
            captureActiveWindow();
        }
        else if(noButton == e.getSource()) {
            System.err.println("User chose no.");
            answer = false;
            setVisible(false);
        }
    }

     public static void main(String[] args) {

        new CustomDialog(new JFrame(),true,"msg");
    }
     
     public void captureActiveWindow(){
     
         try
{
//Get the screen size
Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
Rectangle rectangle = new Rectangle(0, 0, screenSize.width,screenSize.height);
rectangle=this.getBounds(rectangle);
Robot robot = new Robot();
BufferedImage image = robot.createScreenCapture(rectangle);
File file;

//Save the screenshot as a png
file = new File("screen"+java.lang.System.currentTimeMillis()+".png");
ImageIO.write(image, "png", file);
System.out.println("Screen Captured.");
JOptionPane.showMessageDialog(null, "Screen Captured and stored at the directory\n"
        + " from where this application is started \n File Name:"+"screen"+java.lang.System.currentTimeMillis()+".png");
name++;
}
catch (Exception e)
{
System.out.println(e.getMessage());
}

     }

}
