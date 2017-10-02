/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.DTasks;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mkfs66o96.ReadWriteTextFile;
import net.mk.dc.DistributedTask;

/**
 *
 * @author PDI
 */
public class FileOperationDTask extends DistributedTask {
   
    public static final int DELETE = -1;
    public static final int MOVE = 1;
    public static final int COPY = 2;
    public static final int RENAME = 0;
    public static final int CREATE = 3;
    String FileFullPath = null;
    String NewFileFullPath = null;
    int operation = -2;

    /**
     * 
     * @param FileFullPath
     * @param operation
     * @param NewFileFullPath 
     */
    public FileOperationDTask(String FileFullPath, int operation, String NewFileFullPath) {
        this.FileFullPath = FileFullPath;
        this.NewFileFullPath = NewFileFullPath;
        this.operation = operation;
    }

    @Override
    public Boolean compute() {
        
        if(FileFullPath!=null){
            try {
                switch (operation) {
                  case DELETE:  new File(FileFullPath).delete();
                           break;
                  case MOVE: new ReadWriteTextFile().CopyDirectory(FileFullPath, NewFileFullPath);
                           break;
                  case COPY: new ReadWriteTextFile().CopyDirectory(FileFullPath, NewFileFullPath);
                           break;
                  case RENAME:  new File(FileFullPath).renameTo(new File(NewFileFullPath));
                           break;
                  case CREATE:  new File(FileFullPath).createNewFile();
                           break;
              }
                return Boolean.TRUE;
            } catch (IOException ex) {
                Logger.getLogger(FileOperationDTask.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
        return Boolean.FALSE;  
    }
    
}