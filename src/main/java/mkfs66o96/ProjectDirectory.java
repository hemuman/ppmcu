/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkfs66o96;

/**
 *
 * @author PDI
 */
public class ProjectDirectory {

    private static String Path = "C:/tmp";
    private static String ImageFolderPath = "/Images";
    private static String PDDataPath = "/PDData";
    private static String CADDataPath = "/CADData";
    private static String MiscDataPath = "/MiscData";
    private static String RenderingData= "/RenderingData";
    private static String UserProfile = System.getProperty("user.name") + ".profile";
    private static String UserProfilePath = "C:/tmp";

    public ProjectDirectory(String Path ){
        this.Path=Path;
    }
    
    public String[] getProjectList(){
        return new SetUpAction().getProjectList();
    }
    
    /**
     * Get the value of UserProfilePath
     *
     * @return the value of UserProfilePath
     */
    public static String getUserProfilePath() {
        return UserProfilePath;
    }

    /**
     * Set the value of UserProfilePath
     *
     * @param UserProfilePath new value of UserProfilePath
     */
    public static void setUserProfilePath(String UserProfilePath) {
        ProjectDirectory.UserProfilePath = UserProfilePath;
    }


    /**
     * Get the value of UserProfile
     *
     * @return the value of UserProfile
     */
    public static String getUserProfile() {
        return UserProfile;
    }

    /**
     * Set the value of UserProfile
     *
     * @param UserProfile new value of UserProfile
     */
    public static void setUserProfile(String UserProfile) {
        ProjectDirectory.UserProfile = UserProfile;
    }


    /**
     * Get the value of MiscDataPath
     *
     * @return the value of MiscDataPath
     */
    public static String getMiscDataPath() {
        return MiscDataPath;
    }

    /**
     * Set the value of MiscDataPath
     *
     * @param MiscDataPath new value of MiscDataPath
     */
    public static void setMiscDataPath(String MiscDataPath) {
        ProjectDirectory.MiscDataPath = MiscDataPath;
    }


    /**
     * Get the value of CADDataPath
     *
     * @return the value of CADDataPath
     */
    public static String getCADDataPath() {
        return CADDataPath;
    }

    /**
     * Set the value of CADDataPath
     *
     * @param CADDataPath new value of CADDataPath
     */
    public static void setCADDataPath(String CADDataPath) {
        ProjectDirectory.CADDataPath = CADDataPath;
    }

    

    /**
     * Get the value of PDDataPath
     *
     * @return the value of PDDataPath
     */
    public static String getPDDataPath() {
        return PDDataPath;
    }

    /**
     * Set the value of PDDataPath
     *
     * @param PDDataPath new value of PDDataPath
     */
    public static void setPDDataPath(String PDDataPath) {
        ProjectDirectory.PDDataPath = PDDataPath;
    }


    /**
     * Get the value of ImageFolderPath
     *
     * @return the value of ImageFolderPath
     */
    public static String getImageFolderPath() {
        return ImageFolderPath;
    }

    /**
     * Set the value of ImageFolderPath
     *
     * @param ImageFolderPath new value of ImageFolderPath
     */
    public static void setImageFolderPath(String ImageFolderPath) {
        ProjectDirectory.ImageFolderPath = ImageFolderPath;
    }


    /**
     * Get the value of Path
     *
     * @return the value of Path
     */
    public static String getPath() {
        return Path;
    }

    /**
     * Set the value of Path
     *
     * @param Path new value of Path
     */
    public static void setPath(String Path) {
        ProjectDirectory.Path = Path;
    }

    

    /**
     * Get the value of RenderingData
     *
     * @return the value of RenderingData
     */
    public static String getRenderingDataPath() {
        return RenderingData;
    }

    /**
     * Set the value of RenderingData
     *
     * @param RenderingData new value of RenderingData
     */
    public static void setRenderingData(String RenderingData) {
        ProjectDirectory.RenderingData = RenderingData;
    }

}
