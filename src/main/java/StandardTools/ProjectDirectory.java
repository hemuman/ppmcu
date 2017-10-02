/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StandardTools;

/**
 *
 * @author PDI
 */
public class ProjectDirectory {

    private static String Path = "C:/BBS";
    private static String ImageFolderPath = "/Images";
    private static String PDDataPath = "/PDData";
    private static String CADDataPath = "/CADData";
    private static String MiscDataPath = "/MiscData";
    private static String PDVariantPath = "/PDVariant";
    private static String UserProfile = System.getProperty("user.name") + ".profile";
    private static String UserProfilePath = "C:/BBS";
    private static String VariantName;
       private static String FilePath1;
    private static String FilePath2;
    /**
     * Get the value of UserProfilePath
     *
     * @return the value of UserProfilePath
     */
    public static String getUserProfilePath() {
        return UserProfilePath;
    }
   /*
     * Added by Punam 12th Jan 2013
     * Start
     */
    public static void setFilePath(String FilePath1,String FilePath2)
    {
        System.out.println("FilePAthtttttttttt:"+FilePath1);
         ProjectDirectory.FilePath1=FilePath1;
          ProjectDirectory.FilePath2=FilePath2;
       
    }
     public static String getFilePath1()
    {
        System.out.println("FilePAthtttttttttt:"+FilePath1);
         return ProjectDirectory.FilePath1;
        
    }
     public static String getFilePath2()
    {
        System.out.println("FilePAthtttttttttt:"+FilePath1);
         return ProjectDirectory.FilePath2;
       
    }
     /*
      * End
      */
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
    public static void SetVariantName(String VariantName)
    {
       ProjectDirectory.VariantName=VariantName;
    }
    public static String getVariantName()
    {
        return ProjectDirectory.VariantName;
    }

    /**
     * Get the value of MiscDataPath
     *
     * @return the value of MiscDataPath
     */
    public static String getMiscDataPath() {
        return MiscDataPath;
    }
 public static String getPDVariantPath() {
        return PDVariantPath;
    }
 public static void setPDVariantPath(String PDVariantPath) {
        ProjectDirectory.PDVariantPath = PDVariantPath;
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

    
}
