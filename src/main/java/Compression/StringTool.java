/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Compression;

/**
 *
 * @author manoj.kumar
 */
public class StringTool {

    public static void main(String args[]) {
        char ch;

        for (ch = 'A'; ch <= 'z'; ch++) {
            System.out.print(ch+"\",\"");
        }
        
        System.out.println("\n"+random(50));
        System.out.println("\n"+random(50));
        System.out.println("\n"+random(50));
    }

    public static String random(int charCount) {
        StringBuilder sb = new StringBuilder();
        int length=AllChars().length;
        for(int i=0;i<charCount;i++){
            sb.append(AllChars()[(int)(Math.random()*length)]);
        }
        return sb.toString();
    }
    
    public static final String[] AllChars(){
        String[] array=new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
            "[","\\","]","^","_","`","0","1","2","3","4","5","6","7","8","9",
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
         return array;
    }
}
