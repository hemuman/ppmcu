/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.DTasks;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import static net.mk.DTasks.IsDivisibleByNumberDTask.IsDivisible;
import net.mk.dc.DistributedTask;

/**
 *
 * @author PDI
 */
public class FileBrowserDTask extends DistributedTask{
    
    String FolderName;
    public static final String Name="Name";
    public static final String AbsolutePath="AbsolutePath";
    public static final String CanonicalPath="CanonicalPath";
    public static final String length="length";
    public static final String isDirectory="isDirectory";
    public static final String isFile="isFile";
    public static final String ParentPath="ParentPath";
    public static final String canExecute="canExecute";
    public static final String canRead="canRead";
    public static final String canWrite="canWrite";
    public static final String isHidden="isHidden";
    public static final String MachineName="MachineName";
    int SortBy=2;//0-None, 1- Name, 2- Type, 3- Modified
    public static final int SortByNone=0;
    public static final int SortByName=1;
    public static final int SortByType=2;
    public static final int SortByModified=3;
    public  boolean Recursive=false;
    
    
    
    public FileBrowserDTask(String FolderName){
        this.FolderName=FolderName;   
    }
    
    public FileBrowserDTask(String FolderName,boolean Recursive){
        this.FolderName=FolderName; 
        this.Recursive=Recursive;
    }
    
    
    public FileBrowserDTask(String FolderName, int SortBy){
        this.FolderName=FolderName;
        this.SortBy=SortBy;
    }
    
    @Override
    public String compute() {
        
        //If all the content is requested.
        if(Recursive){try {
                return walk(FolderName).toString();
            } catch (JSONException ex) {
                Logger.getLogger(FileBrowserDTask.class.getName()).log(Level.SEVERE, null, ex);
            }
}
        
       JSONObject Result=new JSONObject();
       
       
       
       File folder = new File(FolderName);
        File[] listOfFiles = folder.listFiles();
        
        //Implement some sorting
        if(listOfFiles!=null)
        if(SortBy==SortByType){
        Arrays.sort(listOfFiles, new Comparator<File>(){
                public int compare(File f1, File f2) {
                    return Boolean.valueOf(f1.isFile()).compareTo(f2.isFile());
                }
            });
        }

        //Implement some sorting
        if(listOfFiles!=null)
        if (SortBy == SortByModified) {
            Arrays.sort(listOfFiles, new Comparator<File>(){
                public int compare(File f1, File f2) {
                    return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
                }
            });
        }
        
        if(listOfFiles!=null)
        for (int i = 0; i < listOfFiles.length; i++) {
            try {
                
                JSONObject temp=new JSONObject();
                temp.put("Name", listOfFiles[i].getName());
                temp.put("AbsolutePath", listOfFiles[i].getAbsolutePath());
                temp.put("CanonicalPath", listOfFiles[i].getCanonicalPath());
                temp.put("length", listOfFiles[i].length());
                temp.put("isDirectory", listOfFiles[i].isDirectory());
                temp.put("isFile", listOfFiles[i].isFile());
                temp.put("ParentPath",FolderName);
                temp.put("canExecute", listOfFiles[i].canExecute());
                temp.put("canRead", listOfFiles[i].canRead());
                temp.put("canWrite", listOfFiles[i].canWrite());
                temp.put("isHidden", listOfFiles[i].isHidden());
                temp.put("MachineName", InetAddress.getLocalHost().getHostName());
                Result.put(listOfFiles[i].getName(), temp);
                
            } catch (JSONException ex) {
                Logger.getLogger(FileBrowserDTask.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FileBrowserDTask.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return Result.toString();
    }
    
    public JSONObject walk( String path ) throws JSONException {

        File root = new File( path );
        File[] list = root.listFiles();
        JSONObject result=new JSONObject();
        if (list == null) return result;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walk( f.getAbsolutePath() );
                result.put(f.getName(), f.getAbsoluteFile());
                System.out.println( "Dir:" + f.getAbsoluteFile() );
            }
            else {
                result.put(f.getName(), f.getAbsoluteFile());
                System.out.println( "File:" + f.getAbsoluteFile() );
            }
        }
        
        return result;
    }
    
     public static void main(String[] args){
    
//         File[] npath=new FileBrowserDTask("C:\\BEM\\wizard\\F600work\\NewPartOrientation\\BentConsolidatedTC3NEO").compute();
//         
//         for(int i=0;i<npath.length;i++){
//             System.out.println(npath[i]);
//         }
     
      
    }
    
}
