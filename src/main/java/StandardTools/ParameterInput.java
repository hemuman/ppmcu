/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StandardTools;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author PDI
 */
public class ParameterInput  {
  
   public static void main(String[] args) {
    JPanel myPanel = new JPanel();
    JTextField field1 = new JTextField(10);
    JTextField field2 = new JTextField(10);
    myPanel.add(field1);
    myPanel.add(field2);
    JOptionPane.showMessageDialog(null, myPanel);
    System.out.println(field1.getText() + field2.getText());
}

    
    
}
