/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONObject;

/**
 *
 * @author Manoj
 */
public class StringGeneHandler  extends CustomHandler  {
    HashMap memoryDict;

    public StringGeneHandler() {
        
        try {
            //Load from text file
            memoryDict=readFile("c:/wordsEn.txt");
        } catch (IOException ex) {
            Logger.getLogger(StringGeneHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        
        Map queryMap = he.getRequestURI().getQuery() != null ? queryToMap(he.getRequestURI().getQuery()) : new HashMap<String, String>();
        byte[] result = new byte[0];
        InputStream inputStream = null;
         if (queryMap.containsKey("codify")) {
             inputStream = he.getRequestBody();
             int binaryData = -1;
                boolean allowWrite = false;
                int counter = 0;
                StringBuilder seqMatch = new StringBuilder();
                while ((binaryData = inputStream.read()) != -1) {
                    seqMatch.append((char) binaryData);
                }
                
                StringBuilder codedString=new StringBuilder();
                StringBuilder word=new StringBuilder();
                char space=' ';
                for(int i=0;i<seqMatch.length();i++){
                    if(seqMatch.charAt(i)==space){
                        String dictResult=getAdaptiveIndex(word.toString().toLowerCase());
                        //Accept which ever is shorter
                        if(dictResult!=null)
                        codedString.append(dictResult.length()>word.length()+1?word.toString()+" ":dictResult);
                        else codedString.append(word+" ");
                        word=new StringBuilder();
                    }else{
                        word.append(seqMatch.charAt(i));
                    }
                }
                
                //Handle last word
                String dictResult=getAdaptiveIndex(word.toString().toLowerCase());
                //Accept which ever is shorter
                if(dictResult!=null)
                codedString.append(dictResult.length()>word.length()+1?word.toString()+" ":dictResult);
                else codedString.append(word);
                
                result=codedString.toString().getBytes();
        
        } 
        
        //Send out the message
        he.sendResponseHeaders(200, result.length);
        OutputStream oshe = he.getResponseBody();
        oshe.write(result);
        oshe.close();
    }
    
    /**
     * More needs to be done here. Take help of redis and see performance.
     * @param word
     * @return 
     */
    private String getAdaptiveIndex(String word) {
        String dictResult = (String) memoryDict.get(word.toLowerCase());
        if (dictResult != null) {
            return dictResult;
        } else {
            memoryDict.put(word, Integer.toString(memoryDict.size(), Character.MAX_RADIX) + " ");
            return getAdaptiveIndex(word);
        }
    }
    
    private static HashMap<String,String> readFile(String fileName) throws IOException {
	FileInputStream fis = new FileInputStream(new File(fileName));
	//Construct BufferedReader from InputStreamReader
	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        HashMap result=new HashMap<String,String>();
	String line = null;
        
	while ((line = br.readLine()) != null) {
            
            result.put(line,Integer.toString(result.size(), Character.MAX_RADIX)+" ");
		//System.out.println(line);
	}
 
	br.close();
        return result;
}
    
    public static String getBaseXvalue(long index){
        String chars="1234567890-=!@#$%^&*()_+qwertyuiop[]asdfghjkl;zxcvbnm,./QWERTYUIOP{}|ASDFGHJKL:ZXCVBNM<>?;";
        int base=chars.length();
        long remainder= index%base;
        long multiplier=index/base;
        
        return "To Be Implemented.";
        
    }
    
}
