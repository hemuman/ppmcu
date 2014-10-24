/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.os;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 *
 * @author PDI
 */
public class preloadSigar {
    
    private final static String sigar_x86_winnt = "sigar-x86-winnt";
    private final static String sigar_amd64_winnt = "sigar-amd64-winnt";
    private final static String sigar_universal_macosx = "sigar-universal-macosx";
    private final static String sigar_universal64_macosx= "sigar-universal64-macosx";
    private static final String LIB_BIN = "lib/";
    
    public  preloadSigar(String PathToExtract) throws IOException {
        String path =PathToExtract;// "ppmcu_" /* + new Date().getTime()*/;
        
        String arch = System.getProperty("os.arch");
        String libName;
        String ext;

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            
            if (arch.equalsIgnoreCase("x86")) 
            { libName = "sigar-x86-winnt";ext=".dll";}
            else
            {  libName = "sigar-amd64-winnt";ext=".dll";}
        } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            if (arch.startsWith("i") && arch.endsWith("86"))
            { libName = "libsigar-universal-macosx";ext=".dylib";}
            else
            { libName = "libsigar-universal64-macosx";ext=".dylib";}
        } else if (System.getProperty("os.name").toLowerCase().contains("solaris")) {
            if (arch.startsWith("i") && arch.endsWith("86"))
            { libName = "libsigar-x86-solaris";ext=".dylib";}
            else
            { libName = "libsigar-sparc64-solaris";ext=".dylib";}
        }else {
            throw new RuntimeException("Unrecognized platform!");

        }

//        System.out.println(System.getProperty("java.io.tmpdir") +libName+ext);
//        
//        loadLib(path, libName,ext); //Extract from jar
//          loadLib(path, libName,ext); //Extract from jar
          System.loadLibrary(libName);
//        libName = "libsigar-universal-macosx";ext=".dylib";
//      loadLib(path, libName,ext); //Extract from jar
//        System.setProperty("org.hyperic.sigar.path",path);    
//        
        

      }


/**
 * Puts library to temp dir and loads to memory
 */
private  static void loadLib(String path, String name,String ext) throws IOException {
	//name = name + ".dll";
////    try {
        // have to use a stream
       // File fileOut = File.createTempFile(System.getProperty("java.io.tmpdir")+"/"+name, ext);
        
        InputStream in = preloadSigar.class.getResourceAsStream(LIB_BIN + name+ext);
        // always write to different location
        //File fileOut = new File(System.getProperty("java.io.tmpdir") + "/" + path + LIB_BIN + name);
        
       // System.out.println("#loadLib"+fileOut.getCanonicalPath());
       // File targetFile = new File(System.getProperty("java.io.tmpdir") + path + LIB_BIN +name+ext);
         File targetFile = new File(path + "\\" +name+ext);
        File parent = targetFile.getParentFile();
        if(!parent.exists() && !parent.mkdirs()){
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
        System.out.println("#loadLib: "+targetFile.getCanonicalPath());
  try {     
try (       OutputStream os = new FileOutputStream(targetFile)) { 
        byte[] buffer = new byte[4096];
        int i=0;
        for (int n; (n = in.read(buffer)) != -1;) {
            os.write(buffer, 0, n);
            //System.out.print(i+++" ");
        } os.close();}
 finally {
    in.close();
    targetFile=null;
    parent=null;
//os=null;
in=null;
} } finally {//os.close(); 
   // in.close();
    targetFile=null;
    parent=null;
//os=null;
in=null;}
      
//       in.close();

}

}
