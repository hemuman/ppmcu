/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.stof;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.Tool;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import json.mkJSON;
import net.mk.DTasks.DCTaskURLHit;

/**
 *
 * @author Manoj
 */
public class StofHelper {
    // static String defaultSave="c:/stof/";
    static String defaultSave="cache/";
     public static void cacheAllSites(boolean andUsers,boolean andTags){  
         PrintWriter writer = null;
         try {
             DCTaskURLHit dctuh = new DCTaskURLHit(new String[]{"https://api.stackexchange.com/2.2/"
                     + "sites?key=fZqxUcMtZcuBDixJoifKkw((&filter=default&page=1&pagesize=100"});
             String s = new String(dctuh.compute());
             JSONObject jsob=new JSONObject(s);
             JSONArray allList=jsob.getJSONArray("items");
             int pageCount=2;
             while(jsob.getBoolean("has_more")){
                 
                 dctuh = new DCTaskURLHit(new String[]{"https://api.stackexchange.com/2.2/"
                     + "sites?key=fZqxUcMtZcuBDixJoifKkw((&filter=default&page="+pageCount+"&pagesize=100"});
                 pageCount++;
                   s = new String(dctuh.compute());
                   jsob=new JSONObject(s);
                   
                   mkJSON.mergeJSONArray(allList, jsob.getJSONArray("items"));
                   System.out.println(allList.length());
                   Thread.sleep((long) (3000+5000*Math.random()));
                   
             }
             
             File file=new File(defaultSave+"StofAllSites.json");
             if(file.exists()){ file.createNewFile();}
             
             writer = new PrintWriter(defaultSave+"StofAllSites.json", "UTF-8");
             writer.println(allList.toString());
             writer.close();
             
             
             if(andTags){
                 //https://api.stackexchange.com/2.2/tags?key=U4DMV*8nvpm3EOpvf69Rxw((&site=stackoverflow&page=100&order=desc&sort=popular&filter=default
                 String api="https://api.stackexchange.com/2.2/tags?key=fZqxUcMtZcuBDixJoifKkw((";
                 String data_type="tags";
                 String Filter="!9YdnSCY8Q";
                 String sort="&order=desc&sort=popular";
                 for(int i=1;i<=allList.length();i++){
                     
                     JSONObject jobj=allList.getJSONObject(allList.length()-i);
                     String site=jobj.getString("api_site_parameter");
                     cacheGiventype(api,site,data_type,Filter,sort);

                    }
             }
             
             if(andUsers){
                 for(int i=1;i==allList.length();i++){
                     JSONObject jobj=allList.getJSONObject(allList.length()-i);
                     cacheAllUsers(jobj.getString("api_site_parameter"));

                    }
             }
             
             

             
         } catch (FileNotFoundException ex) {
             Logger.getLogger(StofHelper.class.getName()).log(Level.SEVERE, null, ex);
         } catch (UnsupportedEncodingException ex) {
             Logger.getLogger(StofHelper.class.getName()).log(Level.SEVERE, null, ex);
         } catch (JSONException ex) {
             Logger.getLogger(StofHelper.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(StofHelper.class.getName()).log(Level.SEVERE, null, ex);
         } catch (InterruptedException ex) {
             Logger.getLogger(StofHelper.class.getName()).log(Level.SEVERE, null, ex);
         } finally {
              if(writer!=null)
             writer.close();
         }
     }
    
     public static void cacheAllUsers(String site){
         
         String url="https://api.stackexchange.com/2.2/users?key=fZqxUcMtZcuBDixJoifKkw((&"
                 + "site="+site+"&order=desc&sort=reputation&filter=!bZPpy-6D)zV(.w&page=1&pagesize=100";
         
         PrintWriter writer = null;
         try {
             DCTaskURLHit dctuh = new DCTaskURLHit(new String[]{"https://api.stackexchange.com/2.2/users?key=fZqxUcMtZcuBDixJoifKkw((&"
                 + "site="+site+"&order=desc&sort=reputation&filter=!bZPpy-6D)zV(.w&page=1&pagesize=100"});
             String s = new String(dctuh.compute());
             JSONObject jsob=new JSONObject(s);
             JSONArray allList=jsob.getJSONArray("items");
             int pageCount=2;
             while(jsob.getBoolean("has_more")){
                 
                 File file=new File(defaultSave+site+"StofAllSites.json"+pageCount);//Skip if already pulled
                    if(!file.exists()){ file.createNewFile();} else {pageCount++; continue;}
                 
                 dctuh = new DCTaskURLHit(new String[]{"https://api.stackexchange.com/2.2/users?key=fZqxUcMtZcuBDixJoifKkw((&"
                     + "site="+site+"&order=desc&sort=reputation&filter=!bZPpy-6D)zV(.w&page="+pageCount+"&pagesize=100"});
                 pageCount++;
                   s = new String(dctuh.compute());
                   jsob=new JSONObject(s);
                   
                   mkJSON.mergeJSONArray(allList, jsob.getJSONArray("items"));
                   System.out.println(allList.length());
                   
                   Thread.sleep((long) (1000+3000*Math.random()));
                   

                    writer = new PrintWriter(defaultSave+site+"StofAllSites.json"+pageCount, "UTF-8");
                    writer.println(allList.toString());
                    writer.close();
             
                    allList=new JSONArray();
             }
             
              //Capture lastone
              File file=new File(defaultSave+site+"StofAllSites.json"+pageCount);//Skip if already pulled
                    if(!file.exists()){ file.createNewFile();
                        writer = new PrintWriter(defaultSave+site+"StofAllSites.json"+pageCount, "UTF-8");
                        writer.println(allList.toString());
                        writer.close();
              } 
             
             
             System.out.println();
         } catch (FileNotFoundException ex) {
             Logger.getLogger(StofHelper.class.getName()).log(Level.SEVERE, null, ex);
         } catch (UnsupportedEncodingException ex) {
             Logger.getLogger(StofHelper.class.getName()).log(Level.SEVERE, null, ex);
         } catch (JSONException ex) {
             Logger.getLogger(StofHelper.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(StofHelper.class.getName()).log(Level.SEVERE, null, ex);
         } catch (InterruptedException ex) {
             Logger.getLogger(StofHelper.class.getName()).log(Level.SEVERE, null, ex);
         } finally {
             if(writer!=null)
             writer.close();
         }
         
         
     }
    
     public static void cacheGiventype(String api,String site,String data_type,String Filter,String sort){
         String url=api+ "&site="+site+sort+"&filter="+Filter+"&page=1&pagesize=100";
         
         PrintWriter writer = null;
         try {
             DCTaskURLHit dctuh = new DCTaskURLHit(new String[]{url});
             String s = new String(dctuh.compute());
             JSONObject jsob=new JSONObject(s);
             JSONArray allList=jsob.getJSONArray("items");
             int pageCount=2;
             while(jsob.getBoolean("has_more")){
                 
                 File file=new File(defaultSave+site+data_type+".json"+pageCount);//Skip if already pulled
                    if(!file.exists()){ file.createNewFile();} else {pageCount++; continue;}
                 
                 dctuh = new DCTaskURLHit(new String[]{api+ "&site="+site+sort+"&filter="+Filter+"&page="+pageCount+"&pagesize=100"});
                 pageCount++;
                   s = new String(dctuh.compute());
                   jsob=new JSONObject(s);
                   
                   mkJSON.mergeJSONArray(allList, jsob.getJSONArray("items"));
                   System.out.println(allList.length());
                   
                   Thread.sleep((long) (1000+3000*Math.random()));

                    writer = new PrintWriter(defaultSave+site+data_type+".json"+pageCount, "UTF-8");
                    writer.println(allList.toString());
                    writer.close();
             
                    allList=new JSONArray();
             }
             
             //Capture lastone
              File file=new File(defaultSave+site+data_type+".json"+pageCount);//Skip if already pulled
                    if(!file.exists()){ file.createNewFile();
                        writer = new PrintWriter(defaultSave+site+data_type+".json"+pageCount, "UTF-8");
                        writer.println(allList.toString());
                        writer.close();
              } 
                    
             
             
             System.out.println();
         } catch (FileNotFoundException ex) {
             Logger.getLogger(StofHelper.class.getName()).log(Level.SEVERE, null, ex);
         } catch (UnsupportedEncodingException ex) {
             Logger.getLogger(StofHelper.class.getName()).log(Level.SEVERE, null, ex);
         } catch (JSONException ex) {
             Logger.getLogger(StofHelper.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(StofHelper.class.getName()).log(Level.SEVERE, null, ex);
         } catch (InterruptedException ex) {
             Logger.getLogger(StofHelper.class.getName()).log(Level.SEVERE, null, ex);
         } finally {
              if(writer!=null)
             writer.close();
         }
         
         
     }
     
     
     public static void main(String[] args) throws Exception {
        
        cacheAllSites(true,true);
        
        //Cache tags
        
        //Read all the data of sites.

        DCTaskURLHit dctuh = new DCTaskURLHit(new String[]{"https://api.stackexchange.com/2.2/"
                + "sites?key=fZqxUcMtZcuBDixJoifKkw((&filter=default&page=3&pagesize=100"});
        String s = new String(dctuh.compute());
        
        JSONObject jsob=new JSONObject(s);
        JSONArray ja=new JSONArray();
        //Get sites
        //Get all users of these sites
        //find matches/interests
        
        
        System.out.println();
        
        //api_site_parameter audience name
//        URL oracle = new URL("http://www.oracle.com/");
//        URLConnection yc = oracle.openConnection();
//        BufferedReader in = new BufferedReader(new InputStreamReader(
//                yc.getInputStream()));
//        String inputLine;
//        while ((inputLine = in.readLine()) != null) {
//            System.out.println(inputLine);
//        }
//        in.close();
    }
     
}
