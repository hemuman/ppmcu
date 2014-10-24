/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dcApps;

/**
 *
 * @author PDI
 */
import java.lang.reflect.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*

CCLRun executes a Java program by loading it through a
CompilingClassLoader.

*/

public class RunClass
{
    static boolean compileClasses(String args[]){
        int SUCCESS_COUNTER=0;
        for(int i=0;i<args.length;i++)
        {// Create a CompilingClassLoader
            CompileClass ccl = new CompileClass();
            try {
                // Load the main class through our CCL
                Class clas = ccl.loadClass( args[i] );
                SUCCESS_COUNTER++;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RunClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(SUCCESS_COUNTER==args.length) return true;
        else
        return false;
        
}
  static public void main( String args[] ) throws Exception {

    // The first argument is the Java program (class) the user
    // wants to run
    String progClass = args[0];

    // And the arguments to that program are just
    // arguments 1..n, so separate those out into
    // their own array
    String progArgs[] = new String[args.length-1];
    System.arraycopy( args, 1, progArgs, 0, progArgs.length );

    // Create a CompilingClassLoader
    CompileClass ccl = new CompileClass();

    // Load the main class through our CCL
    Class clas = ccl.loadClass( "net.mk.dc.DistributedTask" );
    
    clas = ccl.loadClass( progClass );

    //if(clas.getConstructors()[0] instanceof String){}
    // Use reflection to call its main() method, and to
    // pass the arguments in.

    // Get a class representing the type of the main method's argument
    Class mainArgType[] = { (new String[0]).getClass() };

    // Find the standard main method in the class
    Method main = clas.getMethod( "main", mainArgType );

    // Create a list containing the arguments -- in this case,
    // an array of strings
    Object argsArray[] = { progArgs };

    // Call the method
    main.invoke( null, argsArray );
  }
}

