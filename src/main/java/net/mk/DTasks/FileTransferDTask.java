/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.DTasks;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.mk.dc.DistributedTask;

/**
 *
 * @author PDI
 */
public class FileTransferDTask extends DistributedTask {

    String FileName;
    byte[] FileData;
    boolean GET_true_SEND_False;

    /**
     * Create file on remote machine a put the data in
     *
     * @param FileName
     * @param FileData
     */
    public FileTransferDTask(String FileName, byte[] FileData, boolean GET_true_SEND_False) {
        this.FileName = FileName;
        this.FileData = FileData;
        this.GET_true_SEND_False = GET_true_SEND_False;

    }

    @Override
    public byte[] compute() {
        FileOutputStream out = null;
        try {
            if (!GET_true_SEND_False) // Send file
            {
                File f = new File(FileName);
                //(works for both Windows and Linux)
                //f.mkdirs(); 
                f.createNewFile();
                out = new FileOutputStream(FileName);

                out.write(FileData);
                out.close();
                return Boolean.TRUE.toString().getBytes();
            } else {
                Path path = Paths.get(FileName);
                return Files.readAllBytes(path);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileTransferDTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileTransferDTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void copy(InputStream input, OutputStream output, int bufferSize)
            throws IOException {
        byte[] buf = new byte[bufferSize];
        int bytesRead = input.read(buf);
        while (bytesRead != -1) {
            output.write(buf, 0, bytesRead);
            bytesRead = input.read(buf);
        }
        output.flush();
    }
}
