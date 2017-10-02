/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcu2D;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONException;
import json.JSONObject;
import net.mk.DTasks.FileBrowserDTask;

/**
 *
 * @author PDI
 */
public class FilePreviewBox extends Rectangle{
    
    public JSONObject FileInfo;
    public  String Name="Name";
    public  String ShortName="Name";
    public  String AbsolutePath="AbsolutePath";
    public  String CanonicalPath="CanonicalPath";
    public  String ParentPath="ParentPath";
    public  String FileType="FileType";
    public  String MachineName="MachineName";
    public  long length=0;
    public  boolean isDirectory=false;
    public  boolean isFile=false;
    public boolean canExecute=false;
    public boolean canRead=false;
    public boolean canWrite=false;
    public boolean isHidden=false;
    public BufferedImage PreviewImage;
    
    public void setFileInfo(JSONObject FileInfo){
        this.FileInfo=FileInfo;
        try {
            Name=FileInfo.getString(FileBrowserDTask.Name);
            ShortName= Name;
            AbsolutePath=FileInfo.getString(FileBrowserDTask.AbsolutePath);
            CanonicalPath=FileInfo.getString(FileBrowserDTask.CanonicalPath);
            ParentPath=FileInfo.getString(FileBrowserDTask.ParentPath);
            MachineName=FileInfo.getString(FileBrowserDTask.MachineName);
            length=FileInfo.getLong(FileBrowserDTask.length);
            isDirectory=FileInfo.getBoolean(FileBrowserDTask.isDirectory);
            isFile=FileInfo.getBoolean(FileBrowserDTask.isFile);
            canExecute=FileInfo.getBoolean(FileBrowserDTask.canExecute);
            canRead=FileInfo.getBoolean(FileBrowserDTask.canRead);
            canWrite=FileInfo.getBoolean(FileBrowserDTask.canWrite);
            isHidden=FileInfo.getBoolean(FileBrowserDTask.isHidden);
            PreviewImage=null;//Load it afterwords
            
            if(isDirectory)FileType="Folder";
            if(isFile)FileType="File";
            if(hasRestrictions())FileType="Restricted";
            if(Name.length()>15) ShortName= Name.substring(0, 12)+"...";
            
        } catch (JSONException ex) {
            Logger.getLogger(FilePreviewBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean hasRestrictions(){
        
        return (canExecute & canRead & canWrite & isHidden);
    }
    
    public JSONObject getFileInfo(){
        return FileInfo;
    }
    
   
    public boolean Contains(int X, int Y, int width,int height) {
//        System.out.println("---------------------------------");
//        System.out.println("(x < X)"+(x < X)+" >"+width);
//        System.out.println("(x+width > X))"+(x+width > X));
//        System.out.println("(y < Y) "+(y < Y) +" >"+height);
//        System.out.println("(y+height > Y)"+(y+height > Y));
        boolean result=((x < X) & (x+width> X) & (y < Y) & (y+height> Y));
        
         return result;
       
       
        
    }
}
