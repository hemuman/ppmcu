/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkfs66o96;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONException;
import json.JSONObject;
import json.mkJSON;

/**
 *
 * @author PDI
 */
public class SetUpAction {

    /**
     *
     * @param ProjectPath
     * @return
     */
    public String CreateProjectData(String ProjectPath) {

        ProjectDirectory.setPath(ProjectPath);
        String messages = "System message:";
        messages = messages + "\n Created Folder :\t" + ProjectPath + new File(ProjectPath).mkdir();
        messages = messages + "\n Created Folder /Images:\t" + new File(ProjectPath + ProjectDirectory.getImageFolderPath()).mkdir();
        messages = messages + "\n Created Folder /PDData:\t" + new File(ProjectPath + ProjectDirectory.getPDDataPath()).mkdir();
        messages = messages + "\n Created Folder /CADData:\t" + new File(ProjectPath + ProjectDirectory.getCADDataPath()).mkdir();
        messages = messages + "\n Created Folder /MiscData:\t" + new File(ProjectPath + ProjectDirectory.getMiscDataPath()).mkdir();
        messages = messages + "\n Created Folder /MiscData:\t" + new File(ProjectPath + ProjectDirectory.getRenderingDataPath()).mkdir();
        return messages;
    }

    /**
     *
     * @param ProjectName
     * @param ProjectData
     * @return
     */
    public String updateUserProfile(String ProjectName, JSONObject ProjectData) {

        try {
            String UserProfile = ProjectDirectory.getUserProfilePath() + "/" + ProjectDirectory.getUserProfile();
            JSONObject userProfile = new File(UserProfile).exists()? new JSONObject(ReadWriteTextFile.getContents(new File(UserProfile))):new JSONObject();
            userProfile.put(ProjectName, ProjectData);
            System.out.println("UserProfile="+UserProfile);
            ReadWriteTextFile.setContents(UserProfile, userProfile.toString());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SetUpAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SetUpAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {

            Logger.getLogger(SetUpAction.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
        return "Project " + ProjectName + " is created.";

    }

    /**
     *
     * @return
     */
    public String[] getProjectList() {
        try {
            String UserProfile = ProjectDirectory.getUserProfilePath() + "/" + ProjectDirectory.getUserProfile();
            JSONObject userProfile = new JSONObject(ReadWriteTextFile.getContents(new File(UserProfile)));
            if(userProfile.names()!=null)
            return mkJSON.getStringArrayFromJSONArray(userProfile.names());
        } catch (JSONException ex) {
            Logger.getLogger(SetUpAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getProjectPath(String ProjectName) throws JSONException {
        String UserProfile = ProjectDirectory.getUserProfilePath() + "/" + ProjectDirectory.getUserProfile();
        return (new JSONObject(ReadWriteTextFile.getContents(new File(UserProfile)))).getJSONObject(ProjectName).getString("ProjectDirectory");
    }

    /**
     * Delete the Project directory and its entry in user.profile
     *
     * @param ProjectName
     * @return
     * @throws JSONException
     */
    public boolean DeleteAProject(String ProjectName) throws JSONException, FileNotFoundException, IOException {
        String UserProfile = ProjectDirectory.getUserProfilePath() + "/" + ProjectDirectory.getUserProfile();
        ProjectDirectory.setPath(getProjectPath(ProjectName));
        JSONObject userProfile = new JSONObject(ReadWriteTextFile.getContents(new File(UserProfile)));
        userProfile .remove(ProjectName);
        ReadWriteTextFile.setContents(UserProfile, userProfile.toString());
        
        return deleteDir(new File(ProjectDirectory.getPath()));


    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }
}
