/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkfs66o96;

import json.JSONArray;
import json.JSONException;
import json.JSONObject;



/**
 *
 * @author PDI
 */
public class Profile {
    
public static JSONObject USER_PROFILE_DATA; 
public static ProjectDirectory projectDirectory;

public Profile(String Path){
    projectDirectory=new ProjectDirectory(Path);
}

public static void createProfile() throws JSONException{
    USER_PROFILE_DATA=new JSONObject();
    JSONObject userCredential=new JSONObject();
    userCredential.put("name", "ViofeedUser");
    userCredential.put("password", "ViofeedPass");
    USER_PROFILE_DATA.put("userCredential", userCredential);
    JSONObject projectCredentials=new JSONObject();
    USER_PROFILE_DATA.put("projectCredentials", projectCredentials);

}

public static void UpdateProfile(String ProjectDirectory,String ProjectName) throws JSONException{
            JSONObject jObject=new JSONObject();
            jObject.put("ProjectDirectory", ProjectDirectory);
            jObject.put("PDIServerEmail", "hemuman@gmail.com");
            new SetUpAction().updateUserProfile(ProjectName,jObject );
            projectDirectory.setPath(ProjectDirectory+"/"+ProjectName);
}

public static void createProfile(JSONObject USER_PROFILE_DATA){ 
    Profile.USER_PROFILE_DATA=USER_PROFILE_DATA;


}
public static String getUserName(){return "Manoj";}
public static String getPassWord(int UserKey){return "Manoj's Password";}
public static String getWorkingDirectory(int UserKey){
    ProjectDirectory.setUserProfilePath(ProjectDirectory.getPath());
    return ProjectDirectory.getUserProfilePath();}
public static JSONArray getProjectList(){ return null;}
public static JSONObject getProjectData(int ProjectKey){return null;}
public static JSONArray getProjectImages(int ProjectKey){return null;}
public static JSONObject getImageData(int ImageKey){return null;}
public static JSONObject getGPSData(int ImageKey){return null;}
public static JSONObject getCompassData(int ImageKey){return null;}
public static JSONObject getOrientationData(int ImageKey){return null;}
public static JSONObject getCameraSpecData(int ImageKey){return null;}

}
