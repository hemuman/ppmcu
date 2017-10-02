/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkfs66o96;

import javax.swing.JOptionPane;

/**
 *
 * @author PDI
 */
public class Loginator {
    
    public static boolean SuperuserLogin(String ValidationKey){
        //String UserName= JOptionPane.showInputDialog(null, "Enter #UserName", "#UserName",1);
        String Password= JOptionPane.showInputDialog(null, "Enter #Password", "#Password", 1);
 
        return ValidateSuperuser(ValidationKey,Password);
    }
    
    private static boolean ValidateSuperuser(String ValidationKey, String UserInput){
        //TODO: Write function for validation
        return true;
    }
    
    public static boolean PublicLogin(String ValidationKey){
        
        String Password= JOptionPane.showInputDialog(null, "Enter #Password", "#Password", 1);
 
        return ValidatePublic(ValidationKey,Password);
    }
    private static boolean ValidatePublic(String ValidationKey, String UserInput){
        //TODO: Write function for validation
        if(ValidationKey.contentEquals(UserInput)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public static boolean KeyLogin(){
        
        return false;
    }
    
    public static boolean CertificateLogin(){
        
        return false;
    }
    
    public static boolean DllLogin(){
        
        return false;
    }
    
    public static boolean SoLogin(){
        
        return false;
    }
    
    public static boolean ServerLogin(){
        
        return false;
    }
}
