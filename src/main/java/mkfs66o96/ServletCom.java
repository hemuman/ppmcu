/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkfs66o96;

/**
 *
 * @author PDI
 */
import java.io.*;
import java.net.*;

public class ServletCom {

    public static void main(String[] args)
            throws Exception {

        HttpURLConnection conn = null;
        BufferedReader br = null;
        DataOutputStream dos = null;
        DataInputStream inStream = null;

        InputStream is = null;
        OutputStream os = null;
        boolean ret = false;
        String StrMessage = "";
        String exsistingFileName = "D:\\Matrix\\VTCinfo.txt";

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;

        byte[] buffer;

        int maxBufferSize = 1 * 1024 * 1024;

        String responseFromServer = "";

        String urlString = "http://127.0.0.1:8888//EDWeb/upload/ed_upload.php";

        try {
            //------------------ CLIENT REQUEST

            FileInputStream fileInputStream = new FileInputStream(new File(exsistingFileName));

            // open a URL connection to the Servlet 
            URL url = new URL(urlString);

            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();

            // Allow Inputs
            conn.setDoInput(true);

            // Allow Outputs
            conn.setDoOutput(true);

            // Don't use a cached copy.
            conn.setUseCaches(false);

            // Use a post method.
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Connection", "Keep-Alive");

            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";"
                    + " filename=\"" + exsistingFileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {
            System.out.println("From ServletCom CLIENT REQUEST:" + ex);
        } catch (IOException ioe) {
            System.out.println("From ServletCom CLIENT REQUEST:" + ioe);
        }

        //------------------ read the SERVER RESPONSE
        try {
            inStream = new DataInputStream(conn.getInputStream());
            String str;
            while ((str = inStream.readLine()) != null) {
                System.out.println("Server response is: " + str);
                System.out.println("");
            }
            inStream.close();

        } catch (IOException ioex) {
            System.out.println("From (ServerResponse): " + ioex);

        }

    }

    public static String Upload(String urlString, String exsistingFileName, String postVaiableName, int maxBufferSize) {
        System.out.println("#Upload " + postVaiableName + " File Uploading " + exsistingFileName);
        HttpURLConnection conn = null;
        BufferedReader br = null;
        DataOutputStream dos = null;
        DataInputStream inStream = null;

        InputStream is = null;
        OutputStream os = null;
        boolean ret = false;
        String StrMessage = "";
        //String exsistingFileName = "D:\\Matrix\\VTCinfo.txt";

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;

        byte[] buffer;

       // int maxBufferSize = 1 * 1024 * 1024;
        String responseFromServer = "";

        //String urlString = "http://127.0.0.1:8888//EDWeb/upload/ed_upload.php";
        try {
            //------------------ CLIENT REQUEST

            FileInputStream fileInputStream = new FileInputStream(new File(exsistingFileName));

            // open a URL connection to the Servlet 
            URL url = new URL(urlString);

            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();

            // Allow Inputs
            conn.setDoInput(true);

            // Allow Outputs
            conn.setDoOutput(true);

            // Don't use a cached copy.
            conn.setUseCaches(false);

            // Use a post method.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + postVaiableName + "\";"
                    + " filename=\"" + exsistingFileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {
            System.out.println("From ServletCom CLIENT REQUEST:" + ex);
        } catch (IOException ioe) {
            System.out.println("From ServletCom CLIENT REQUEST:" + ioe);
        }

        //------------------ read the SERVER RESPONSE
        String str = "No-Action";
        try {
            inStream = new DataInputStream(conn.getInputStream());
            //String str;
            while ((str = inStream.readLine()) != null) {
                System.out.println("Server response is: " + str);
                System.out.println("");
            }
            inStream.close();

        } catch (IOException ioex) {
            System.out.println("From (ServerResponse): " + ioex);

        }
        return str;
    }

    public static String getContentString(String URLString) {
        String content = "";
        try {
            URL url;
            URLConnection urlConn;
            DataInputStream dis;

            //url = new URL("http://webserver.our-intranet.com/ToDoList/ToDoList.txt");
            url = new URL(URLString);

    // Note:  a more portable URL: 
            //url = new URL(getCodeBase().toString() + "/ToDoList/ToDoList.txt");
            urlConn = url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setUseCaches(false);

            dis = new DataInputStream(urlConn.getInputStream());
            String s;

    //toDoList.clear(); 
            while ((s = dis.readLine()) != null) {
                content = content + s;
                //toDoList.addItem(s); 
            }
            dis.close();
        } catch (MalformedURLException mue) {
        } catch (IOException ioe) {
        }

        return content;
    }

    public static DataInputStream getDataInputStream(String URLString) {
        String content = "";
        DataInputStream dis = null;
        try {
            URL url;
            URLConnection urlConn;

            //url = new URL("http://webserver.our-intranet.com/ToDoList/ToDoList.txt");
            url = new URL(URLString);

    // Note:  a more portable URL: 
            //url = new URL(getCodeBase().toString() + "/ToDoList/ToDoList.txt");
            urlConn = url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setUseCaches(false);

            dis = new DataInputStream(urlConn.getInputStream());

        } catch (IOException ioe) {
        }

        return dis;
    }

    public static FileOutputStream getFileOutputStream(String URLString) {
        String content = "";
        FileOutputStream out = null;
        try {
            URL url;
            URLConnection urlConn;

            //url = new URL("http://webserver.our-intranet.com/ToDoList/ToDoList.txt");
            url = new URL(URLString);

    // Note:  a more portable URL: 
            //url = new URL(getCodeBase().toString() + "/ToDoList/ToDoList.txt");
            urlConn = url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setUseCaches(false);

            InputStream raw = urlConn.getInputStream();
            int ContentLength = urlConn.getContentLength();
            byte[] data = new byte[ContentLength];
            int byteRead = 0;
            int offset = 0;
            while (offset < ContentLength) {
                byteRead = raw.read(data, offset, data.length - offset);
                if (byteRead == -1) {
                    break;
                }
                offset += byteRead;
            }
            raw.close();

            out = new FileOutputStream("Temp.vdfs");
            out.write(data);

        } catch (IOException ioe) {
        }

        return out;
    }

    public static File getTempFile(String URLString) {
        String content = "";
        FileOutputStream out = null;
        File tempfile = null;
        System.out.println("#getTempFile " + URLString);
        try {
            URL url;
            URLConnection urlConn;

            //url = new URL("http://webserver.our-intranet.com/ToDoList/ToDoList.txt");
            url = new URL(URLString);

    // Note:  a more portable URL: 
            //url = new URL(getCodeBase().toString() + "/ToDoList/ToDoList.txt");
            urlConn = url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setUseCaches(false);

            InputStream raw = urlConn.getInputStream();
            int ContentLength = urlConn.getContentLength();
            byte[] data = new byte[ContentLength];
            int byteRead = 0;
            int offset = 0;
            while (offset < ContentLength) {
                byteRead = raw.read(data, offset, data.length - offset);
                if (byteRead == -1) {
                    break;
                }
                offset += byteRead;
            }
            raw.close();

            System.out.println("#getTempFile ContentLength=" + ContentLength);
            out = new FileOutputStream("Temp.vdfs", false);
            out.write(data);
            out.flush();
            out.close();

            tempfile = new File("Temp.vdfs");
            System.out.println("#getTempFile " + tempfile.getCanonicalPath());

        } catch (IOException ioe) {
        }

        return tempfile;
    }
    /*
     void postNewItem () {  
     try {

     URL                url; 
     URLConnection      urlConn; 
     DataOutputStream   dos; 
     DataInputStream    dis;

     url = new URL("http://webserver.our-intranet.com/cgi-bin/AddToDoItem"); 
     urlConn = url.openConnection(); 
     urlConn.setDoInput(true); 
     urlConn.setDoOutput(true); 
     urlConn.setUseCaches(false); 
     urlConn.setRequestProperty ("Content-Type", "application/x-www-form-urlencoded");

     dos = new DataOutputStream (urlConn.getOutputStream()); 
     String message = "NEW_ITEM=" + URLEncoder.encode(addTextField.getText()); 
     dos.writeBytes(message); 
     dos.flush(); 
     dos.close();

     // the server responds by saying 
     // "SUCCESS" or "FAILURE"

     dis = new DataInputStream(urlConn.getInputStream()); 
     String s = dis.readLine(); 
     dis.close(); 
  
     if (s.equals("SUCCESS")) { 
     toDoList.addItem(addTextField.getText()); 
     addTextField.setText(""); 
     } else { 
     addTextField.setText("Post Error!"); 
     }

     } // end of "try"

     catch (MalformedURLException mue) { 
     addTextField.setText("mue error"); 
     } 
     catch (IOException ioe) { 
     addTextField.setText("IO Exception"); 
     }

     }  // end of postNewItem() method 
     */
}
