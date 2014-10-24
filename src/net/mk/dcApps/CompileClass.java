/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dcApps;

/**
 *
 * @author PDI
 */
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONException;
import net.mk.ppmcuGUI.MCWConfClass;

/*

A CompilingClassLoader compiles your Java source on-the-fly.  It
checks for nonexistent .class files, or .class files that are older
than their corresponding source code.

*/

public class CompileClass extends ClassLoader
{
    boolean debug=false;
  // Given a filename, read the entirety of that file from disk
  // and return it as a byte array.
  private byte[] getBytes( String filename ) throws IOException {
    // Find out the length of the file
    File file = new File( filename );
    long len = file.length();

    // Create an array that's just the right size for the file's
    // contents
    byte raw[] = new byte[(int)len];

    // Open the file
    FileInputStream fin = new FileInputStream( file );

    // Read all of it into the array; if we don't get all,
    // then it's an error.
    int r = fin.read( raw );
    if (r != len)
      throw new IOException( "Can't read all, "+r+" != "+len );

    // Don't forget to close the file!
    fin.close();

    // And finally return the file contents as an array
    return raw;
  }

  // Spawn a process to compile the java source code file
  // specified in the 'javaFile' parameter.  Return a true if
  // the compilation worked, false otherwise.
  private boolean compile( String javaFile ) throws IOException, JSONException {
      // Let the user know what's going on
      if(debug)
      System.out.println("CC: command \t " + "javac -cp \"PPMCU.jar;lib/JSON.jar;lib/MKFS66O96.jar;\" " + javaFile);

      //Windows= -cp \"PPMCU.jar;lib/JSON.jar;lib/MKFS66O96.jar;\"
      //MAC = -cp ".:PPMCU.jar:lib/JSON.jar:lib/MKFS66O96.jar" 
      String LibraryPath="";
      
      if(System.getProperty("os.name").toLowerCase().contains("win")) LibraryPath="-cp \"PPMCU.jar;lib/JSON.jar;lib/MKFS66O96.jar;\" ";
      if(System.getProperty("os.name").toLowerCase().contains("mac")) LibraryPath="-cp \".:PPMCU.jar:lib/JSON.jar:lib/MKFS66O96.jar\" ";
      // Start up the compiler
      Process p = Runtime.getRuntime().exec("javac "+LibraryPath + javaFile);

      // Wait for it to finish running
      try {
          p.waitFor();
      } catch (InterruptedException ie) {
          System.out.println("Exception : " + ie);
      }

      //p = Runtime.getRuntime().exec("dir");
      BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String line = null;
      while ((line = in.readLine()) != null) {
          System.out.println(line);
      }
      //System.out.println(  p.getErrorStream());
      // System.out.println(  p.getInputStream());
      // Check the return code, in case of a compilation error
      int ret = p.exitValue();

      // Tell whether the compilation worked
      return ret == 0;
  }
 
  private boolean compileCustomDir( String javaFile ) throws IOException, JSONException {
    // Let the user know what's going on
    System.out.println( "CC: Compiling "+javaFile+"...in "+System.getProperty("user.dir") +" s/b "+ MCWConfClass.SysSpec.getString("UserDevelopedAppsDir"));
    System.out.println( "CC: command \t "+"javac -cp \"lib/PPMCU.jar;lib/JSON.jar;lib/MKFS66O96.jar;"+MCWConfClass.SysSpec.getString("UserDevelopedAppsDir")+"\" -d \""+MCWConfClass.SysSpec.getString("UserDevelopedAppsDir")+"\" "+javaFile );

    // Start up the compiler
    Process p = Runtime.getRuntime().exec( "javac -cp \"lib/PPMCU.jar;lib/JSON.jar;lib/MKFS66O96.jar;"+MCWConfClass.SysSpec.getString("UserDevelopedAppsDir")+"\" -d \""+MCWConfClass.SysSpec.getString("UserDevelopedAppsDir")+"\" "+javaFile );

    // Wait for it to finish running
    try {
      p.waitFor();
    } catch( InterruptedException ie ) { System.out.println("Exception : "+ ie ); }

    p = Runtime.getRuntime().exec( "dir" );
    BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
    String line = null;
    while((line = in.readLine()) != null) {
      System.out.println(line);
    }
    //System.out.println(  p.getErrorStream());
   // System.out.println(  p.getInputStream());
    // Check the return code, in case of a compilation error
    int ret = p.exitValue();

    // Tell whether the compilation worked
    return ret==0;
  }

  // The heart of the ClassLoader -- automatically compile
  // source as necessary when looking for class files
  @Override
  public Class loadClass( String name, boolean resolve )throws ClassNotFoundException {

    // Our goal is to get a Class object
    Class clas = null;

    // First, see if we've already dealt with this one
    clas = findLoadedClass( name );

    //System.out.println( "findLoadedClass: "+clas );

    // Create a pathname from the class name
    // E.g. java.lang.Object => java/lang/Object
    String fileStub = name.replace( '.', '/' );
    
    // Build objects pointing to the source code (.java) and object
    // code (.class)
    String javaFilename = fileStub+".java";
    String classFilename = fileStub+".class";
    
    

    File javaFile = new File( System.getProperty("user.dir")+"/"+javaFilename );
    File classFile = new File( System.getProperty("user.dir")+"/"+classFilename );
    if(debug)
    System.out.println( "Look for Compiling "+javaFilename+"...in "+System.getProperty("user.dir")+" With "+javaFile.exists());
    //System.out.println( "j "+javaFile.lastModified()+" c "+
    //  classFile.lastModified() );

    // First, see if we want to try compiling.  We do if (a) there
    // is source code, and either (b0) there is no object code,
    // or (b1) there is object code, but it's older than the source
    if (javaFile.exists() &&
         (!classFile.exists() ||
          javaFile.lastModified() > classFile.lastModified())) {

      try {
        // Try to compile it.  If this doesn't work, then
        // we must declare failure.  (It's not good enough to use
        // and already-existing, but out-of-date, classfile)
        if (!compile( javaFilename ) || !classFile.exists()) {
          throw new ClassNotFoundException( "Compile failed for: "+javaFilename );
        }
      } catch( IOException ie ) {

        // Another place where we might come to if we fail
        // to compile
        throw new ClassNotFoundException( ie.toString() );
      } catch (JSONException ex) {
            Logger.getLogger(CompileClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Let's try to load up the raw bytes, assuming they were
    // properly compiled, or didn't need to be compiled
    try {

      // read the bytes
      byte raw[] = getBytes( classFilename );

      // try to turn them into a class
      clas = defineClass( name, raw, 0, raw.length );
    } catch( IOException ie ) {
      // This is not a failure!  If we reach here, it might
      // mean that we are dealing with a class in a library,
      // such as java.lang.Object
    }

    //System.out.println( "defineClass: "+clas );

    // Maybe the class is in a library -- try loading
    // the normal way
    if (clas==null) {
      clas = findSystemClass( name );
    }

    //System.out.println( "findSystemClass: "+clas );

    // Resolve the class, if any, but only if the "resolve"
    // flag is set to true
    if (resolve && clas != null)
      resolveClass( clas );

    // If we still don't have a class, it's an error
    if (clas == null)
      throw new ClassNotFoundException( name );

    // Otherwise, return the class
    return clas;
  }


  // convert InputStream to String
	public static String getStringFromInputStream(InputStream is) {
 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return sb.toString();
 
	}
}

