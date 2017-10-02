package Compression;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author manoj.kumar
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import javax.swing.JFileChooser;
import jxl.common.Logger;

public class DataCompression {

    private static final Logger LOG = Logger.getLogger(DataCompression.class);

    public static byte[] compress(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.setLevel(9);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index  
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();

        LOG.debug("Original: " + data.length / 1024 + " Kb");
        LOG.debug("Compressed: " + output.length / 1024 + " Kb");
        LOG.debug("%: " + (100.0 - 100.0 * (output.length / 1024.0) / (data.length / 1024.0)) + " Kb");
        return output;
    }

    public static byte[] compress(byte[] data, int level) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setInput(data);

        if (level < 9) {
            deflater.setLevel(level);
        } else {
            deflater.setLevel(9);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index  
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();

        LOG.debug("Original: " + data.length / 1024 + " Kb");
        LOG.debug("Compressed: " + output.length / 1024 + " Kb");
        LOG.debug("%: " + (100.0 - 100.0 * (output.length / 1024.0) / (data.length / 1024.0)) + "");
        return output;
    }

    public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();

        LOG.debug("Original: " + data.length);
        LOG.debug("Compressed: " + output.length);
        return output;
    }

    public static StringBuilder getRandomString(int loopLimit) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < loopLimit; i++) {
            sb.append(StringTool.random(100));
        }

        return sb;
    }

    public static byte[] compressFile(File afile) {

        FileInputStream fin = null;
        try {
            // create FileInputStream object
            fin = new FileInputStream(afile);

            byte fileContent[] = new byte[(int) afile.length()];

            // Reads up to certain bytes of data from this input stream into an array of bytes.
            fin.read(fileContent);

            LOG.debug("# First Compression");
            //Compress
            return compress(fileContent, 9);

            //Tested on JSON File
            //LOG.debug("# Second Compression - No-effect");
            //Compress
            //fileContent=compress(fileContent,9);

            //create string from byte array
            //String s = new String(fileContent);
            //System.out.println("File content: " + s);
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading file " + ioe);
        } finally {
            // close the streams using close method
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }
        }

        return new byte[0];
    }

    public static void test_compressFile() {
        LOG.debug("#Select file..");
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.showOpenDialog(jfc);
        if (jfc.getSelectedFile() != null) {

            compressFile(jfc.getSelectedFile());
        }
        LOG.debug("#File compression test finished.");

    }

    public static void main(String... args) {

        test_compressFile();

        try {
            StringBuilder sb = getRandomString(100000);
            for (int i = 0; i < 10; i++) {
                System.out.println("#DataCompression Level=" + i);
                byte[] compressedData = compress(sb.toString().getBytes(), i);
                decompress(compressedData);
            }

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(DataCompression.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataFormatException ex) {
            java.util.logging.Logger.getLogger(DataCompression.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
