package mkfs66o96;



import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.JFileChooser;
import json.JSONArray;
import json.JSONException;


public class Zip {
    //pfazip://CADUtilities/META-INF/
/**
 * 
 * @param ZipFileName 
 */
    public static void printFileList(String ZipFileName) {
        try {
            ZipFile zf = new ZipFile(ZipFileName);
            Enumeration entries = zf.entries();

            /*BufferedReader input = new BufferedReader(new InputStreamReader(
             System.in));*/
            while (entries.hasMoreElements()) {
                ZipEntry ze = (ZipEntry) entries.nextElement();
                System.out.println("File: " + ze.getName() + " | "+ze.getSize());
                
                long size = ze.getSize();
               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void printFileListInternet(String ZipFileName) {
        try {
            
            ZipFile zf = new ZipFile(ZipFileName);
            Enumeration entries = zf.entries();

            /*BufferedReader input = new BufferedReader(new InputStreamReader(
             System.in));*/
            while (entries.hasMoreElements()) {
                ZipEntry ze = (ZipEntry) entries.nextElement();
                System.out.println("File: " + ze.getName() + " | "+ze.getSize());
                
                long size = ze.getSize();
               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 
     * @param ZipFileName
     * @return 
     */
     public static JSONArray getFileList(String ZipFileName) {
         JSONArray jsonArray = null;
         try {
            ZipFile zf = new ZipFile(ZipFileName);
            Enumeration entries = zf.entries();
            jsonArray=new JSONArray();
            /*BufferedReader input = new BufferedReader(new InputStreamReader(
             System.in));*/
            while (entries.hasMoreElements()) {
                ZipEntry ze = (ZipEntry) entries.nextElement();
                //System.out.println("File: " + ze.getName() + " | "+ze.getSize());
                
                //long size = ze.getSize();
                jsonArray.put(ze.getName());
               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
    
     public static JSONArray getFileList(File ZipFile) {
         JSONArray jsonArray = null;
         try {
            ZipFile zf = new ZipFile(ZipFile);
            Enumeration entries = zf.entries();
            jsonArray=new JSONArray();
            /*BufferedReader input = new BufferedReader(new InputStreamReader(
             System.in));*/
            while (entries.hasMoreElements()) {
                ZipEntry ze = (ZipEntry) entries.nextElement();
                //System.out.println("File: " + ze.getName() + " | "+ze.getSize());
                
                //long size = ze.getSize();
                jsonArray.put(ze.getName());
               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
     
     public static JSONArray getFileList(File ZipFile,String FileExtIncludingDot) {
         JSONArray jsonArray = null;
         try {
            ZipFile zf = new ZipFile(ZipFile);
            Enumeration entries = zf.entries();
            jsonArray=new JSONArray();
            /*BufferedReader input = new BufferedReader(new InputStreamReader(
             System.in));*/
            while (entries.hasMoreElements()) {
                ZipEntry ze = (ZipEntry) entries.nextElement();
                //System.out.println("File: " + ze.getName() + " | "+ze.getSize());
                
                //long size = ze.getSize();
                if(ze.getName().contains(FileExtIncludingDot)) {
                    jsonArray.put(ze.getName());
                }
               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
     public static InputStream getInputStream(String ZipFileName,String FileName) {
        try {
            ZipFile zf = new ZipFile(ZipFileName);
            Enumeration entries = zf.entries();
           
            while (entries.hasMoreElements()) {
                ZipEntry ze = (ZipEntry) entries.nextElement();
                if(ze.getName().toUpperCase().contentEquals(FileName.toUpperCase())){
                    
                InputStream input=zf.getInputStream(ze);
               // BufferedReader br=new BufferedReader()
                //System.out.println("File: " + ze.getName() + " | "+ze.getSize());
                
                return input;
                }
               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
      
     public static InputStream getInputStream(File ZipFile,String FileName) {
        try {
            ZipFile zf = new ZipFile(ZipFile);
            Enumeration entries = zf.entries();
           
            while (entries.hasMoreElements()) {
                ZipEntry ze = (ZipEntry) entries.nextElement();
                if(ze.getName().toUpperCase().contentEquals(FileName.toUpperCase())){
                    
                InputStream input=zf.getInputStream(ze);
               // BufferedReader br=new BufferedReader()
                //System.out.println("File: " + ze.getName() + " | "+ze.getSize());
                
                return input;
                }
               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
      
/**
 * 
 * @param zipFile
 * @param files
 * @throws IOException 
 */
    public static void addFilesToExistingZip(String zipFileName,String directory, File[] files) throws IOException {
        // get a temp file
       // File tempFile = File.createTempFile(zipFile.getName(), null);
        String tempName=System.currentTimeMillis()+".tmp";//zipFile.getParent()+"/"+
        File tempFile = new File(directory+tempName); // Added by Manoj on 9/1/2013
        File zipFile = new File(zipFileName); // Added by Manoj on 9/1/2013
        // delete it, otherwise you cannot rename your existing zip to it.
        //tempFile.delete();

        boolean renameOk = zipFile.renameTo(tempFile);
        if (!renameOk) {
            throw new RuntimeException("#addFilesToExistingZip Could not rename the file " + zipFile.getAbsolutePath() + " to " + tempFile.getAbsolutePath());
        }
        byte[] buf = new byte[1024];

        ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));

        ZipEntry entry = zin.getNextEntry();
        while (entry != null) {
            String name = entry.getName();
            boolean notInFiles = true;
            for (File f : files) {
                if (f.getName().equals(name)) {
                    notInFiles = false;
                    break;
                }
            }
            if (notInFiles) {
                // Add ZIP entry to output stream.
                out.putNextEntry(new ZipEntry(name));
                // Transfer bytes from the ZIP file to the output file
                int len;
                while ((len = zin.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
            entry = zin.getNextEntry();
        }
        // Close the streams        
        zin.close();
        // Compress the files
        for (int i = 0; i < files.length; i++) {
            InputStream in = new FileInputStream(files[i]);
            // Add ZIP entry to output stream.
            out.putNextEntry(new ZipEntry(files[i].getName()));
            // Transfer bytes from the file to the ZIP file
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            // Complete the entry
            out.closeEntry();
            in.close();
        }
        // Complete the ZIP file
        out.close();
        tempFile.delete();
    }
/**
 * Handle all exceptions when used
 * @param zipFileName
 * @param directory
 * @param files
 * @throws FileNotFoundException
 * @throws JSONException
 * @throws IOException 
 */
     public static String addFilesToNewZip(String zipFileName,String directory ,JSONArray  files,boolean DeleteSource) throws FileNotFoundException, JSONException, IOException {
         
         ZipOutputStream zos= new ZipOutputStream(new FileOutputStream(zipFileName));
         byte[] buf = new byte[1024];
         
         for(int i=0;i<files.length();i++)
         {
             System.out.println("#addFilesToExistingZip "+files.getString(i));
             FileInputStream fis=new  FileInputStream(SearchCanonicalFileName(directory, files.getString(i)));
             zos.putNextEntry(new ZipEntry(files.getString(i)));
             System.out.println("#adding"+SearchCanonicalFileName(directory, files.getString(i)));
            int len;
            while ((len = fis.read(buf)) > 0) {
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            
            fis.close();
            //Delete the files
            if(DeleteSource) {
                 new File(SearchCanonicalFileName(directory, files.getString(i))).delete();
             }
            System.out.print("#done.");
         }
         
         zos.close();
         return zipFileName;
     }
     
     public static void addFilesToExistingZip_(File zipFile, File[] files) throws IOException {
        // get a temp file
    File tempFile = File.createTempFile(zipFile.getName(), null);
        // delete it, otherwise you cannot rename your existing zip to it.
    tempFile.delete();

    boolean renameOk=zipFile.renameTo(tempFile);
    if (!renameOk)
    {
        throw new RuntimeException("could not rename the file "+zipFile.getAbsolutePath()+" to "+tempFile.getAbsolutePath());
    }
    byte[] buf = new byte[1024];

    ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));

    ZipEntry entry = zin.getNextEntry();
    while (entry != null) {
        String name = entry.getName();
        boolean notInFiles = true;
        for (File f : files) {
            if (f.getName().equals(name)) {
                notInFiles = false;
                break;
            }
        }
        if (notInFiles) {
            // Add ZIP entry to output stream.
            out.putNextEntry(new ZipEntry(name));
            // Transfer bytes from the ZIP file to the output file
            int len;
            while ((len = zin.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        }
        entry = zin.getNextEntry();
    }
    // Close the streams        
    zin.close();
    // Compress the files
    for (int i = 0; i < files.length; i++) {
        InputStream in = new FileInputStream(files[i]);
        // Add ZIP entry to output stream.
        out.putNextEntry(new ZipEntry(files[i].getName()));
        // Transfer bytes from the file to the ZIP file
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        // Complete the entry
        out.closeEntry();
        in.close();
    }
    // Complete the ZIP file
    out.close();
    tempFile.delete();
}
    
     public static String SearchCanonicalFileName(String srcPath, String NameHas) throws IOException {

        File myDir = new File(srcPath);
        File[] files = myDir.listFiles();
        String list[] = null;
        System.out.println("#SearchCanonicalFileName Path="+srcPath+" Size="+ files.length+" Searching for: "+NameHas);
        JSONArray js = new JSONArray();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().toUpperCase().contains(NameHas.toUpperCase())) {
                return files[i].getCanonicalPath();
            }
        
        }

        return "NA";
    }
    
         
    public static void main(String args[]) {
        try {

            /**
             * Test File list print
             */
            JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(new Frame());
            File selFile = fc.getSelectedFile();
            //FeatureReport = new JSONArray(ReadWriteTextFile.getContents(selFile));

            System.out.println(selFile.getCanonicalPath());
            Zip.printFileList(selFile.getCanonicalPath());


            /**
             * Test Adding file to Zip.
             */
            System.out.println("Select a zip file");
            fc.showOpenDialog(new Frame());
            selFile = fc.getSelectedFile();

            System.out.println("Select files to add");
            JFileChooser fc2 = new JFileChooser();
            fc2.setMultiSelectionEnabled(true);
            fc2.showOpenDialog(new Frame());
            addFilesToExistingZip(selFile.getName(),selFile.getPath(), fc2.getSelectedFiles());

            Zip.printFileList(selFile.getCanonicalPath());

            /**
             * ZipFile zf = new ZipFile(selFile.getCanonicalPath()); Enumeration entries = zf.entries();
             *
             * // BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); while (entries.hasMoreElements()) { ZipEntry ze = (ZipEntry)
             * entries.nextElement(); System.out.println("Read " + ze.getName() + "?"); //String inputLine = input.readLine();
             *
             * // if (inputLine.equalsIgnoreCase("yes")) { long size = ze.getSize(); if (size > 0) { System.out.println("Length is " + size); BufferedReader br
             * = new BufferedReader( new InputStreamReader(zf.getInputStream(ze))); String line; while ((line = br.readLine()) != null) {
             * System.out.println(line); } br.close(); } // }
            }
             */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}