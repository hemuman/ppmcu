/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.ppmcuGUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.table.TableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import json.mkJSON;
import mkfs66o96.ReadWriteTextFile;
//import net.manojky.bitcoinminer.mkDiabloMinerGUI;
import net.mk.dcApps.DCGUI;
import net.mk.dc.SisClientPanel;
import net.mk.dc.SisServerPanel;
import net.mk.dc.SupClientPanel;
import net.mk.dcApps.CompileClass;
import net.mk.dcApps.DevelopDCApp;
import net.mk.dcApps.GetAppGUI;
import net.mk.dcApps.SolSys.SolarSysGUI;
import net.mk.dcApps.UD_DCApps;
import net.mk.os.OSAppGraphics;
import net.mk.os.RunningAppsPanel;
import net.mk.ppmc.apps.ImageStampPanel;
import net.mk.ppmcu.GlobalMCUTest;
import net.mk.ppmcu2D.MultiCore2DPanel;
import net.mk.ppmcu2D.ScreenShareClient;
import net.mk.ppmcu2D.ScreenShareClient1;
import net.mk.ppmcu2D.UIToolKit;
import org.javadev.AnimatingCardLayout;
import org.javadev.effects.CubeAnimation;
import org.javadev.effects.DashboardAnimation;
import org.javadev.effects.FadeAnimation;
import org.javadev.effects.IrisAnimation;
import org.javadev.effects.RadialAnimation;
import org.javadev.effects.SlideAnimation;

/**
 *
 * @author PDI
 */
public class WorldSpeedTestGUI extends javax.swing.JPanel {

    //protected String WebServerName="127.0.0.1:8888/multicoreworld";
    protected String WebServerName = "multicoreworld.manojky.net";
    static boolean hasInternet = false;
    JSONObject address;
    public JSONObject SysSpec;
    
    ProfilePage profilepage, profilepage1, profilepage2;
    RunningAppsPanel RAP;

    EmptyJPanel EJP;
    int CURRENT_RANK = 0;
    String CURRENT_RANK_RATIO = "0/0";
    //ThreeD threeD;
    MultiCore2DPanel jcthreeD;
    protected String LICENSE_KEY;
    ItemHandler handler = new ItemHandler();
    boolean ANIMATE = true;
    Thread AnimationThread;
    boolean ProfilesLoaded = false;
    Double secs;
    long StartTest;
    //Layout
    CardLayout cardLayout;
    CardLayout TopCardLayout;
    protected BufferedImage AppImage, AppTableImage;
    //Panel for Cardlayout
    JPanel CardLayoutPanel;
    BufferedImage StampImage;
    //JPopupMenu
    public String[] MCWAppsList = {"First Page", "Remote Screen", "SystemOutput", "CPUProfile", "2D Graphics", "Running Application Test",
        "Image Stamping", "MPP Client Monitor", "MPP SolarSytem App", "User Developed Apps", "Create App"/*,"PPMCU Server","PPMCU Client", "PPMCU SupClient"*/};
    private JMenuItem[] SysOpItem = new JMenuItem[MCWAppsList.length];
    public JPopupMenu menuPopup = new JPopupMenu();
    public static boolean LOAD_SYSTEM_SPEC=true;

    static {
        try {

            final URL url = new URL("http://manojky.net");
            final URLConnection conn = url.openConnection();
            conn.connect();
            hasInternet = true;
            //InetAddress ia= InetAddress.getByName("www.google.com");
            //hasInternet=ia.isReachable(0);


        } catch (UnknownHostException ex) {
            hasInternet = false;
            //Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            hasInternet = false;
            //Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        MCWConfClass.hasInternet = hasInternet;
        System.out.println("Connect to internet : " + hasInternet);
    }

    /**
     * 4/21/2013 Add download speed next week. 4/21/2013 Whole Day testing and Rank graph 4/21/2013 Performance tune up data-- What programs are running
     * 4/21/2013 Performance tune up Experts--Create Profile 4/21/2013 OS Compare for Speed -- Rank 4/21/2013 General Purpose Application to demonstrate Multi
     * Core Utilization -? Not sure of application 4/21/2013 Class Ranking of CPU based on number of cores 4/21/2013 Spherical data points 4/22/2013 Show user
     * data in public if user set's so. 4/22/2013 Add PIE Chart on the wallpaper for ranking 4/28/2013 Image Stamping application 4/28/2013 Image tessellation
     * application 5/2/2013 Drop a script option. A cloud bin for OS Scripts, batch for Windows and sh for Linux shell 5/2/2013 Two text file comparison
     * 5/2/2013 Self-Ranking to keep track of user system slow down. 5/3/2013 FOR ITEM ABOVE RUN AND KILL PROCESS TO UNDERSTAND ITS AFFECT ON COMPUTATION POWER
     * 5/4/2013 High speed Zip and extract 5/6/2013 LoadSharing machine. Parallel machines to share the calculation load.
     */
    /**
     * Creates new form WorldSpeedTestGUI
     */
    public WorldSpeedTestGUI(String WEBServerName, String LicenseKey, String ScreenSize, boolean GraphicsMode) {
        StartTest = new Date().getTime();

        //1. Initiate global variables
        this.LICENSE_KEY = LicenseKey;
        this.WebServerName = WEBServerName;
        CardLayoutPanel = new JPanel();


        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    try {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }
     
        //</editor-fold>

        //2. Initiate CARD LAYOUT
        String mode = /*(!wasApplication)?getParameter("mode"):*/ "slide";
        //System.out.println("mode "+mode);
        if (mode != null && mode.equalsIgnoreCase("cube")) {
            cardLayout = new AnimatingCardLayout(new CubeAnimation());
            TopCardLayout = new AnimatingCardLayout(new CubeAnimation());
        } else if (mode != null && mode.equalsIgnoreCase("slide")) {
            cardLayout = new AnimatingCardLayout(new SlideAnimation());
            TopCardLayout = new AnimatingCardLayout(new SlideAnimation());
        } else if (mode != null && mode.equalsIgnoreCase("radial")) {
            cardLayout = new AnimatingCardLayout(new RadialAnimation());
        } else if (mode != null && mode.equalsIgnoreCase("fade")) {
            cardLayout = new AnimatingCardLayout(new FadeAnimation());
        } else if (mode != null && mode.equalsIgnoreCase("iris")) {
            cardLayout = new AnimatingCardLayout(new IrisAnimation());
        } else {
            cardLayout = new AnimatingCardLayout(new DashboardAnimation());
        }
        TopCardLayout = new AnimatingCardLayout(new CubeAnimation());

        //3. Initiate components
        initComponents();
        jTable1.setTableHeader(null);
        
        //jLabel6  jLabel5
        StampImage = new BufferedImage(
                jLabel5.getIcon().getIconWidth(),
                jLabel5.getIcon().getIconHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = StampImage.createGraphics();
        jLabel5.getIcon().paintIcon(null, g, 0, 0);
        g.dispose();

        MoveMouseListenerBG mml = new MoveMouseListenerBG(this);

        //4. Set application header and footer images
        if (ScreenSize.contains("1024")) {
            header1.set1024();
            System.out.println("#Evaluation Mode");
        } else if (ScreenSize.contains("Free")) {
            //header1.set1024(); //Keep default
            System.out.println("#Free Mode");
        } else if (ScreenSize.contains("HalfScreen")) {
            header1.setHeader(" XYZ Org");
            System.out.println("#HalfScreen Mode");
        }else { //Commercial
            header1.setHeader(" XYZ Org");
            System.out.println("#Commercial Mode");
        }

        secs = new Double((new Date().getTime() - StartTest) * 0.001);
        System.out.println("Init Components: " + secs + "sec(s)");

        //4. Initiate JPopup menu items
        /**
         * JPopup Menu
         */
        //this.add(new JLabel("Right-click for popup menu."));
        for (int i = 0; i < MCWAppsList.length; i++) {
            SysOpItem[i] = new JMenuItem(MCWAppsList[i]);
            SysOpItem[i].addActionListener(handler);

            menuPopup.add(SysOpItem[i]);
            this.setComponentPopupMenu(menuPopup);
        }

        menuPopup.setLocation(getLocation());

        //5. Get data from web
        //Redirected JPanel
        //rdjp = new RedirectedJPanel(true, true, "log.txt", 500, 200, JFrame.DO_NOTHING_ON_CLOSE);
        System.out.println("--------------");
        if(LOAD_SYSTEM_SPEC)
        SysSpec = new GlobalMCUTest().printMachineInfo(false, true);
        MCWConfClass.SysSpec = SysSpec;// Assign SysSpec to Codebase which will be used by multiple applications
        //frontpage = new FrontPage(LicenseKey, WebServerName, SysSpec, false);

        secs = new Double((new Date().getTime() - StartTest) * 0.001);
        System.out.println("Init Profile: " + secs + "sec(s)");


        LoadDataFromWeb();

        //Get the images
        //JPanelSnapshot.getSnapshots(new JPanel[]{frontpage,ISP,SSC,SSGUI,udApps,DDCAPP});
        //CreateCardLayout();
        //Get the images
        //JPanelSnapshot.getSnapshots(SystemOutput.getComponents());
        //CreateCardLayout();
        // this.repaint();
        PrepareTable();

    }

    /**
     * Load system data in background using swing worker.
     */
    public void LoadSystemData() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {

                runtest();
                return null;

            }

            @Override
            public void done() {
                RunTestButton.setEnabled(true);
                validate();
                repaint();
            }
        };

        worker.execute();
    }

    /**
     * Load data in background from Web
     */
    public void LoadDataFromWeb() {

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {

                if (hasInternet) {

                    //System.out.println("Your Location:");
                    try {
                        address = getUserLocation();
                        //System.out.println(address.names());

                        CITY.setText(address.getString("city"));
                        COUNTRY.setText(address.getString("country_name"));
                        // ExistingRank.setText(""+getBoostPossibleRankRatio("multicoreworld.manojky.net"));
                    } catch (JSONException ex) {
                        Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    secs = new Double((new Date().getTime() - StartTest) * 0.001);
                    System.out.println("Init Location: " + secs + "sec(s)");

                    int bpRank = getBoostPossibleRank(WebServerName);
                    String brRankRatio = getBoostPossibleRankRatio(WebServerName);
                    int sctRank = getSCTPossibleRank(WebServerName);
                    String sctRankRatio = getSCTPossibleRatio(WebServerName);
                    int mctRank = getMCTPossibleRank(WebServerName);
                    String mctRankRatio = getMCTPossibleRatio(WebServerName);
                    double[] result = null;
                    try {

                        JSONArray allRatio = new JSONArray(new Web().SendPOST("http://" + WebServerName + "/mcst/GetAllRatio.php", new String[]{"LICENSE_KEY"}, new String[]{LICENSE_KEY}, false));
                        result = mkJSON.getDoubleArrayFromJSONArray(allRatio);

                        secs = new Double((new Date().getTime() - StartTest) * 0.001);
                        System.out.println("Init Ranks: " + secs + "sec(s)");
                    } catch (JSONException ex) {
                        Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    /**
                     * Print result
                     */
                    String rankInfo = "\n\tBoost Possible Rank:\t" + brRankRatio
                            + "\n\tSingle Core Rank:\t" + sctRankRatio
                            + "\n\tMulti Core Rank:   \t" + mctRankRatio
                            + "\n\t------------------------------------------"
                            + "\n\tAverage Rank:      \t" + ((bpRank + sctRank + mctRank) / 3) + "/" + result.length
                            + "\n\t------------------------------------------";
                    System.out.println(rankInfo);

                    //6. Pass data from web
                    ExistingRank.setText("" + ((bpRank + sctRank + mctRank) / 3) + "/" + result.length);
                    //frontpage.brRankRatio = brRankRatio;
                    //frontpage.sctRankRatio = sctRankRatio;
                    //frontpage.mctRankRatio = mctRankRatio;

                    /**
                     * *RANK INFO**
                     */
                    CURRENT_RANK = ((bpRank + sctRank + mctRank) / 3);
                    CURRENT_RANK_RATIO = "" + ((bpRank + sctRank + mctRank) / 3) + "/" + result.length;
                    /**
                     * ***********
                     */
                    System.out.println("*PLEASE RUN THE TEST TO UPDATE YOUR RANK");
                    JSONObject UserProfile = null;
                    try {
                        /**
                         * User profile data
                         */
                        if (hasInternet) {
                            UserProfile = new JSONObject(new Web().SendPOST("http://" + WebServerName + "/mcst/GetUserProfile.php", new String[]{"LICENSE_KEY", "USER_KEY"}, new String[]{LICENSE_KEY, SysSpec.getString("MAC_ADDRESS")}, false));
                        }
                        if (UserProfile.has("UserDevelopedApps")) {

                            MCWConfClass.SysSpec = UserProfile;

                        }
                    } catch (JSONException ex) {
                        Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    //If offline..then set status as offline
                    ExistingRank.setText("Offline");
                    //frontpage.brRankRatio = "Offline";
                    //frontpage.sctRankRatio = "Offline";
                    //frontpage.mctRankRatio = "Offline";
                }
                return null;

            }

            @Override
            public void done() {
                validate();
                repaint();
            }
        };

        worker.execute();
    }

  
    //Preserve icon graphics for repainting
    public void getIconGraphics() {

        AppImage = new BufferedImage(
                jButton7.getIcon().getIconWidth(),
                jButton7.getIcon().getIconHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = AppImage.createGraphics();
        // paint the Icon to the BufferedImage.
        jLabel2.getIcon().paintIcon(null, g, 0, 0);
        g.dispose();
    }
    DefaultTableModel dtm = new DefaultTableModel();

    /**
     * Function to create the User Apps. Also reads data from custom file MCWAppsList.
     */
    public void PrepareTable() {
        //Init panel Layouts
        SystemOutput.removeAll();
        CardLayoutPanel.removeAll();
        ((AnimatingCardLayout) cardLayout).setAnimationDuration(200);
        ((AnimatingCardLayout) TopCardLayout).setAnimationDuration(200);

        SystemOutput.setLayout(TopCardLayout);
        CardLayoutPanel.setLayout(cardLayout);

        JSONArray tempList = new JSONArray();
        try {

            /**
             * 1. Check if MCWConfClass has MCWAppsList file pointing to an out side location, if not then use system specified.
             */
            // From Class, the path is relative to the package of the class unless
            // you include a leading slash, so if you don't want to use the current
            // package, include a slash like this:
            InputStream in = null;
            //InputStream is = new ByteArrayInputStream(inputString.getBytes());  
            if (!MCWConfClass.has(MCWConfClass.AppLibFile)) { // Check if this env variable is set
                in = WorldSpeedTestGUI.class.getClass().getResourceAsStream("/net/mk/ppmcuGUI/MCWAppsList");
            } else { // If not then read from default loaction
                //ReadWriteTextFile.getContents(new File(MCWConfClass.getValue(MCWConfClass.AppLibFile)));
                System.out.println(MCWConfClass.getValue(MCWConfClass.AppLibFile) + " exists : " + new File(MCWConfClass.getValue(MCWConfClass.AppLibFile)).exists());
                in = new ByteArrayInputStream(ReadWriteTextFile.getContents(new File(MCWConfClass.getValue(MCWConfClass.AppLibFile))).getBytes());
            }
            //System.out.println(in != null);

            //If the data read is successful
            if (in != null) { //System.out.println(CompileClass.getStringFromInputStream(in));
                String data = CompileClass.getStringFromInputStream(in);
                //System.out.println("MCWAppsList: \n"+data);
                JSONObject AppList = new JSONObject(data);
                //TODO: Add the file format verifier for MCWAppsList
                
                //System.out.println(AppList.toString(1));
                //tempList = mkJSON.getJSONArrayFromStringArray(MCWAppsList);
                // JSONArray userAppnames=MCWConfClass.SysSpec.getJSONObject("UserDevelopedApps").names();
                JSONArray userAppnames = AppList.names();
                mkJSON.sortJSONArray(userAppnames);
                for (int i = 0; i < userAppnames.length(); i++) { // Loop through each loop
                    Object[] Inputs = {WebServerName, LICENSE_KEY, SysSpec};

                    // If Load parameter is set true
                    if (AppList.getJSONObject(userAppnames.getString(i)).getBoolean("Load"))// Load is it is set to
                    { //Get the name of app
                        tempList.put(AppList.getJSONObject(userAppnames.getString(i)).getString("AppName"));
                        //Create panel and add to CardLayout //Jared
                        CardLayoutPanel.add(
                                GetAppGUI.getAppGUI(AppList.getJSONObject(userAppnames.getString(i)).getString("ClassName"),
                                Inputs, AppList.getJSONObject(userAppnames.getString(i)).getBoolean("Jared"), //If the application is in Jar
                                AppList.getJSONObject(userAppnames.getString(i)).getString("JARPATH")),//Get Path to JAR, must be in local directory
                                AppList.getJSONObject(userAppnames.getString(i)).getString("AppName"));
                    }
                }
            }

            MCWAppsList = mkJSON.getStringArrayFromJSONArray(tempList);
        } catch (JSONException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        /**
         * GridLayout Layout test, to display the Apps
         */
        AppTable.setOpaque(false);
        AppTable.removeAll();
        int row_col[] = getBestVisualArrangement(MCWAppsList.length, true);
        AppTable.setLayout(new GridLayout(row_col[0], row_col[1], 5, 5));
        //AppTable.setLayout(new GridBagLayout());
        for (int i = 0; i < MCWAppsList.length; i++) {
            PosButton button = new PosButton(new String[]{"" + (i + 1), MCWAppsList[i]}, TopCardLayout, cardLayout, SystemOutput, CardLayoutPanel);//JPopupMenuItem[i]
            //button.setOpaque(false);
            button.setContentAreaFilled(false);
            AppTable.add(button);

        }

        //Set the User CardLayout Panel
        SystemOutput.add(CardLayoutPanel, "Active Apps");
        // Set the AppTablePanel
        SystemOutput.add(AppTable, "Apps Table");
        cardLayout.show(CardLayoutPanel, "Front Page");

    }

 
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        header1 = new net.mk.ppmcuGUI.Header();
        SystemOutput = new javax.swing.JPanel();
        AppTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        COUNTRY = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ExistingRank = new javax.swing.JLabel();
        CITY = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton7 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        ActiveApp = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        RunTestButton = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        background1 = new net.mk.ppmcuGUI.Background();

        jMenu1.setText("jMenu1");

        jMenu2.setText("File");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar1.add(jMenu3);

        setBackground(new java.awt.Color(128, 128, 128));
        setPreferredSize(new java.awt.Dimension(594, 500));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(header1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        SystemOutput.setBackground(new java.awt.Color(29, 31, 52));
        SystemOutput.setOpaque(false);

        jScrollPane1.setOpaque(false);

        jTable1.setModel(dtm);
        jTable1.setOpaque(false);
        jScrollPane1.setViewportView(jTable1);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/mk/icons/BGDots1.PNG"))); // NOI18N
        jLabel5.setText("jLabel5");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/mk/icons/CoarseGrid.png"))); // NOI18N
        jLabel6.setText("jLabel6");

        javax.swing.GroupLayout AppTableLayout = new javax.swing.GroupLayout(AppTable);
        AppTable.setLayout(AppTableLayout);
        AppTableLayout.setHorizontalGroup(
            AppTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(AppTableLayout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        AppTableLayout.setVerticalGroup(
            AppTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AppTableLayout.createSequentialGroup()
                .addGroup(AppTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 287, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout SystemOutputLayout = new javax.swing.GroupLayout(SystemOutput);
        SystemOutput.setLayout(SystemOutputLayout);
        SystemOutputLayout.setHorizontalGroup(
            SystemOutputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AppTable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        SystemOutputLayout.setVerticalGroup(
            SystemOutputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SystemOutputLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AppTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(128, 128, 128));

        jLabel3.setText("Your Machine's Current Rank:");

        jLabel1.setText("City:");

        COUNTRY.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        COUNTRY.setText("India");

        jLabel2.setText("Country: ");

        ExistingRank.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ExistingRank.setText("NA");

        CITY.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        CITY.setText("Azamgarh");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(COUNTRY)
                .addGap(49, 49, 49)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CITY)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ExistingRank)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(jLabel2)
                .addComponent(jLabel3)
                .addComponent(ExistingRank)
                .addComponent(COUNTRY)
                .addComponent(CITY))
        );

        jPanel3.setBackground(new java.awt.Color(128, 128, 128));

        jToolBar2.setBackground(new java.awt.Color(128, 128, 128));
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setMaximumSize(new java.awt.Dimension(172, 20));
        jToolBar2.setMinimumSize(new java.awt.Dimension(172, 20));

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 204));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/mk/icons/MCWApps16x16BW.png"))); // NOI18N
        jButton7.setText("All Apps");
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton7.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton7);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/mk/icons/MCWApps16x16BW.png"))); // NOI18N
        jButton1.setText(" Multicore Apps");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setEnabled(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton1);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/mk/icons/MCW16x16.png"))); // NOI18N
        jLabel4.setText("              ");
        jLabel4.setEnabled(false);
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jToolBar2.add(jLabel4);

        ActiveApp.setBackground(new java.awt.Color(204, 204, 204));
        ActiveApp.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        ActiveApp.setForeground(new java.awt.Color(255, 255, 204));
        ActiveApp.setText("Reload All Apps");
        ActiveApp.setFocusable(false);
        ActiveApp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ActiveApp.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ActiveApp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ActiveApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActiveAppActionPerformed(evt);
            }
        });
        jToolBar2.add(ActiveApp);

        jButton6.setText("<Prev.");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton6);

        jButton5.setText("Next>");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton5);

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);
        jToolBar3.setMaximumSize(new java.awt.Dimension(111, 31));
        jToolBar3.setMinimumSize(new java.awt.Dimension(111, 31));
        jToolBar3.setPreferredSize(new java.awt.Dimension(111, 31));

        RunTestButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        RunTestButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/mk/icons/ExecuteTest.png"))); // NOI18N
        RunTestButton.setText(" Run Test");
        RunTestButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RunTestButton.setFocusable(false);
        RunTestButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        RunTestButton.setIconTextGap(-4);
        RunTestButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        RunTestButton.setSelected(true);
        RunTestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RunTestButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(RunTestButton);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/mk/icons/88-16.png"))); // NOI18N
        jButton4.setText("Manager");
        jButton4.setToolTipText("Comes with license version");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton4);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/mk/icons/ExitRW24X.png"))); // NOI18N
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jToolBar3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(SystemOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(background1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(SystemOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(background1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Point p = jButton1.getLocationOnScreen();
        p.y = p.y + 20;
        menuPopup.setLocation(p);

        menuPopup.setVisible(true);

//        if(jButton1.getText().contentEquals("Advanced Setup"))
//        {SystemOutput.removeAll();
//        //Commented out to print System message on terminal
//        SystemOutput.add(asp);
////         SystemOutput.add(threeD);
////        threeD.init();
////        threeD.run();
//       // SystemOutput.setLayout(new BoxLayout(SystemOutput, BoxLayout.PAGE_AXIS));
//        jButton1.setText("Save");
//    }else{
//             try {
//            address.put("HAS_DETAILS", true);
//            address.put("USERNAME",asp.USERNAME.getText());
//            address.put("USER_TYPE", asp.Type1.getSelectedItem().toString());
//            address.put("CPU_TYPE",asp.CPU_TYPE.getSelectedItem().toString());
//            address.put("DATA_PUBLIC", asp.DATA_PUBLIC.isSelected());
//            address.put("RUN_CLASS_TEST", asp.RUN_CLASS_TEST.isSelected());
//            
//        } catch (JSONException ex) {
//            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
//             
//        SystemOutput.removeAll();
//        //Commented out to print System message on terminal
//        SystemOutput.add(rdjp);
//        jButton1.setText("Advanced Setup");
//        }
        validate();
        repaint();

        /**
         * Download the batch file to run system script.
         */
    }//GEN-LAST:event_jButton1ActionPerformed

    private void RunTestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RunTestButtonActionPerformed
        RunTestButton.setEnabled(false);
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {

                runtest();
                return null;

            }

            @Override
            public void done() {
                RunTestButton.setEnabled(true);
                validate();
                repaint();
            }
        };

        worker.execute();

    }//GEN-LAST:event_RunTestButtonActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        cardLayout.show(SystemOutput, "Profile Page");

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        cardLayout.next(CardLayoutPanel);
        //ActiveApp.setText(SystemOutput.getComponent(0).getName());

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        cardLayout.previous(CardLayoutPanel);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

        ANIMATE = false;
//        AnimationThread.interrupt();
    }//GEN-LAST:event_formMouseClicked

    private void ActiveAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActiveAppActionPerformed

        PrepareTable();
       // cardLayout.show(SystemOutput, ActiveApp.getText());
        //     ActiveApp.setText(SysOpItem[i].getText());
    }//GEN-LAST:event_ActiveAppActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        
//        if(jButton6.isVisible()){
//            jButton6.setVisible(false);
//            jButton5.setVisible(false);
//        }else{
//             jButton6.setVisible(true);
//            jButton5.setVisible(true);
//        }
        
        TopCardLayout.next(SystemOutput);
        // validate();
        //repaint();
    }//GEN-LAST:event_jButton7ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ActiveApp;
    private javax.swing.JPanel AppTable;
    private javax.swing.JLabel CITY;
    private javax.swing.JLabel COUNTRY;
    private javax.swing.JLabel ExistingRank;
    private javax.swing.JButton RunTestButton;
    private javax.swing.JPanel SystemOutput;
    private net.mk.ppmcuGUI.Background background1;
    private net.mk.ppmcuGUI.Header header1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    // End of variables declaration//GEN-END:variables

    protected void paintComponent(Graphics g) {

        if (!isOpaque()) {
            super.paintComponent(g);
            return;
        }
  
      g.drawImage(UIToolKit.getTessilatedImage(getWidth(), getHeight(), 1.0f, StampImage, true), 0, 0, this);
 
    }

    public void runtest() {
        //startComponentToSystemOutput();
        //wst(WebServerName);//Local machine
        JSONObject DataToPost = wst(WebServerName);// Web server

        System.out.println("Test Completed.");
        cardLayout.show(CardLayoutPanel, "First Page");
        // jComboBox1.setSelectedIndex(0);
        // startPreferencesToCPUProfile();
        //startComponentToSystemOutput();
    }

    protected JSONObject wst(String WebServerName) {

        //frontpage.brRankRatio = "Waiting...";
        //frontpage.sctRankRatio = "Waiting...";
        //frontpage.mctRankRatio = "Waiting...";
        repaint();
        JSONObject datatoPost = new GlobalMCUTest().getPrimeNumberTestData();
        new GlobalMCUTest().getRandomNumTimeSingleCore();
        new GlobalMCUTest().getRandomNumTimeMultiCore();
        System.out.println("Waiting for result from new Web()...");
        //new Web().SendPOST("http://multicoreworld.manojky.net/mcst/mcst.php", new String []{"VerificationKey","data","filename"}, new String[] {"wKdfL665Mkdfg54ksd",datatoPost.toString(),datatoPost.getString("MAC_ADDRESS")});
        try {
            // String USER_ADDRESS=new Web().SendPOST("http://"+WebServerName+"/mcst/GetAddress.php", new String []{"data"}, new String[] {datatoPost.toString()});
            // System.out.println(USER_ADDRESS);
            // JSONObject address=new JSONObject(USER_ADDRESS);


            datatoPost.put("USER_ADDRESS", address);

            mkJSON.mergeJSONObjects(datatoPost, MCWConfClass.SysSpec); // Make sure data other then testing is preserverd.
            datatoPost.put("UserDevelopedApps", MCWConfClass.SysSpec.getJSONObject("UserDevelopedApps"));//Always use the list stored in code base

            new Web().SendPOST("http://" + WebServerName + "/mcst/mcst.php", new String[]{"data", "LICENSE_KEY"}, new String[]{datatoPost.toString(), LICENSE_KEY}, false);

            JSONArray allRatio = new JSONArray(new Web().SendPOST("http://" + WebServerName + "/mcst/GetAllRatio.php", new String[]{"LICENSE_KEY"}, new String[]{LICENSE_KEY}, false));

            double[] result = mkJSON.getDoubleArrayFromJSONArray(allRatio);

            //System.out.println("Your machine stands at rank "+getRank(datatoPost.getDouble("BOOST_RATIO"),result)+" among all "+result.length+" machines tested so far.");
            int bpRank = getBoostPossibleRank(WebServerName);
            String brRankRatio = getBoostPossibleRankRatio(WebServerName);
           // frontpage.brRankRatio = brRankRatio;
            repaint();
            int sctRank = getSCTPossibleRank(WebServerName);
            String sctRankRatio = getSCTPossibleRatio(WebServerName);
            //frontpage.sctRankRatio = sctRankRatio;
            repaint();
            int mctRank = getMCTPossibleRank(WebServerName);
            String mctRankRatio = getMCTPossibleRatio(WebServerName);
            //frontpage.mctRankRatio = mctRankRatio;
            repaint();
            /**
             * Print result
             */
            String PrintResult = "\n\tBoost Possible Rank:\t" + brRankRatio
                    + "\n\tSingle Core Rank:\t" + sctRankRatio
                    + "\n\tMulti Core Rank:   \t" + mctRankRatio
                    + "\n\t------------------------------------------"
                    + "\n\tAverage Rank:      \t" + ((bpRank + sctRank + mctRank) / 3) + "/" + result.length
                    + "\n\t------------------------------------------";
            System.out.println(PrintResult);

            ExistingRank.setText("" + ((bpRank + sctRank + mctRank) / 3) + "/" + result.length);
            datatoPost.put("GLOBAL_TEST COUNT", result.length);
            /**
             * *RANK INFO**
             */
            CURRENT_RANK = ((bpRank + sctRank + mctRank) / 3);
            CURRENT_RANK_RATIO = "" + ((bpRank + sctRank + mctRank) / 3) + "/" + result.length;

            /**
             * ***********
             */
            //asp.RANK_OUTPUT.setText(PrintResult);

            //String rankRatio=getBoostPossibleRankRatio(WebServerName);
            datatoPost.remove("USER_ADDRESS");
            //ExistingRank.setText(""+rankRatio);

            String COPYRIGHT = "\u00a9";

            try {

                if (!datatoPost.getString("OPERATING_SYSTEM").toUpperCase().contains("MAC")) //4/21/2013 Check issue with MAC OS Next week
                {
                    System.out.println("Getting wallpaper...");
                    System.out.println("Check Wallpaper__" + generateWallPaper(((bpRank + sctRank + mctRank) / 3) + "", datatoPost, "", COPYRIGHT + " http://multicoreworld.manojky.net") + "__Stored on your DESKTOP or Download Folder");
                }
            } catch (JSONException ex) {
                Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.gc();
        return datatoPost;
    }

    /**
     * Optimize this for reduced time. Or cache the results..check if there has been an update
     *
     * @return
     */
    public String getAllRankRatio() {
        int result = getDataCount(WebServerName);
        int bpRank = getBoostPossibleRank(WebServerName);
        //  String brRankRatio= getBoostPossibleRankRatio(WebServerName);
        int sctRank = getSCTPossibleRank(WebServerName);
        //   String sctRankRatio= getSCTPossibleRatio(WebServerName);
        int mctRank = getMCTPossibleRank(WebServerName);
        //  String mctRankRatio= getMCTPossibleRatio(WebServerName);

        /**
         * Print result
         */
        System.out.println("\n\tBoost Possible Rank:\t" + bpRank + "/" + result
                + "\n\tSingle Core Rank:\t" + sctRank + "/" + result
                + "\n\tMulti Core Rank:   \t" + mctRank + "/" + result
                + "\n\t------------------------------------------"
                + "\n\tAverage Rank:      \t" + ((bpRank + sctRank + mctRank) / 3) + "/" + result
                + "\n\t------------------------------------------");

        ExistingRank.setText("" + ((bpRank + sctRank + mctRank) / 3) + "/" + result);

        /**
         * *RANK INFO**
         */
        CURRENT_RANK = ((bpRank + sctRank + mctRank) / 3);
        CURRENT_RANK_RATIO = "" + ((bpRank + sctRank + mctRank) / 3) + "/" + result;
        /**
         * ***********
         */
        return CURRENT_RANK_RATIO;
    }

    /**
     * Rank
     *
     * @param BoostRatio
     * @param BoostRatioList
     * @return
     */
    protected int getRank(double BoostRatio, double[] BoostRatioList) {

        Arrays.sort(BoostRatioList); // Upload sorted data to cache...do this using high end machine only i.e. monster class..store 3 compare and use 1
        for (int i = 0; i < BoostRatioList.length; i++) {
            if (BoostRatioList[i] >= BoostRatio) {
                System.out.println("Your machine stands at rank " + (BoostRatioList.length - i) + " among all the " + BoostRatioList.length + " machines tested so far.");
                return BoostRatioList.length - i;

            } else if (BoostRatioList[BoostRatioList.length - 1] <= BoostRatio) {
                System.out.println("Your machine stands at TOP");
                return 1;
            }
        }
        return BoostRatioList.length;
    }

    protected int getDataCount(String WebServerName) {
        try {
            JSONArray allRatio = new JSONArray(new Web().SendPOST("http://" + WebServerName + "/mcst/GetAllRatio.php", new String[]{"LICENSE_KEY"}, new String[]{LICENSE_KEY}, false));
            double[] BoostRatioList = mkJSON.getDoubleArrayFromJSONArray(allRatio);
            return BoostRatioList.length;
        } catch (JSONException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    protected int getBoostPossibleRank(String WebServerName) {
        try {
            //JSONObject datatoPost=new GlobalMCUTest().printMachineInfo(false);
            //JSONArray allRatio=new JSONArray(new Web().SendPOST("http://"+WebServerName+"/mcst/GetAllRatio.php", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false));
            JSONArray allRatio = new Web().readJsonArrayFromUrl("http://" + WebServerName + "/mcst/mcstAllBOOST", false);
            double BoostRatio = Double.parseDouble(new Web().SendPOST("http://" + WebServerName + "/mcst/GetBoostRatio.php", new String[]{"LICENSE_KEY", "USER_KEY", "BOOST_RATIO"}, new String[]{LICENSE_KEY, SysSpec.getString("MAC_ADDRESS"), ""}, false));
            //System.out.println(allRatio.toString(1));
            double[] BoostRatioList = mkJSON.getDoubleArrayFromJSONArray(allRatio);

            Arrays.sort(BoostRatioList);
            for (int i = 0; i < BoostRatioList.length; i++) {
                if (BoostRatioList[i] >= BoostRatio) { //System.out.println("Your machine stands at rank "+(BoostRatioList.length-i)+" among all "+BoostRatioList.length+" machines tested so far.");
                    return BoostRatioList.length - i;

                } else if (BoostRatioList[BoostRatioList.length - 1] <= BoostRatio) {
                    System.out.println("#getBoostPossibleRank: Your machine stands at TOP in Boost Possible");
                    return 1;
                }
            }
            return BoostRatioList.length;
        } catch (JSONException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    protected String getBoostPossibleRankRatio(String WebServerName) {
        try {
            //JSONObject datatoPost=new GlobalMCUTest().printMachineInfo(false);

            //JSONArray allRatio=new JSONArray(new Web().SendPOST("http://"+WebServerName+"/mcst/GetAllRatio.php", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false));
            JSONArray allRatio = new Web().readJsonArrayFromUrl("http://" + WebServerName + "/mcst/mcstAllBOOST", false);
            double BoostRatio = Double.parseDouble(new Web().SendPOST("http://" + WebServerName + "/mcst/GetBoostRatio.php", new String[]{"LICENSE_KEY", "USER_KEY", "BOOST_RATIO"}, new String[]{LICENSE_KEY, SysSpec.getString("MAC_ADDRESS"), " "}, false));
            double[] BoostRatioList = mkJSON.getDoubleArrayFromJSONArray(allRatio);
//            double WorldAvg= new JSONObject(new Web().SendPOST("http://"+WebServerName+"/mcst/mcst", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false)).getDouble("WA_BOOST_POSSIBLE");
            Arrays.sort(BoostRatioList);
            for (int i = 0; i < BoostRatioList.length; i++) {
                if (BoostRatioList[i] > BoostRatio) {
                    //System.out.println("Your machine stands at rank "+(BoostRatioList.length-i)+" among all "+BoostRatioList.length+" machines tested so far.");
//                            if(BoostRatio>WorldAvg){System.out.println("\n Machine rating:___ABOVE AVERAGE___, it can give you__BitCoins__ :)\n ");}
//                            else{System.out.println("\n Machine rating:___BELOW AVERAGE___.Time to upgrade :) \n DO NOT THINK ABOUT __BitCoins__!");}
//                      
                    return BoostRatioList.length - i + "/" + BoostRatioList.length;

                } else if (BoostRatioList[BoostRatioList.length - 1] <= BoostRatio) {
                    System.out.println("#getBoostPossibleRankRatio: Your machine stands at TOP in Boost Possible");
                    return 1 + "/" + BoostRatioList.length;
                }
            }
            return BoostRatioList.length + "/" + BoostRatioList.length;
        } catch (JSONException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "NA";
    }

    /**
     * Single Core Timing Ranks
     *
     * @param WebServerName
     * @return
     */
    protected int getSCTPossibleRank(String WebServerName) {
        try {
            //JSONObject datatoPost=new GlobalMCUTest().printMachineInfo(false);
            //JSONArray allRatio=new JSONArray(new Web().SendPOST("http://"+WebServerName+"/mcst/GetAllSCT.php", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false));
            JSONArray allRatio = new Web().readJsonArrayFromUrl("http://" + WebServerName + "/mcst/mcstAllSCT", false);
            double BoostRatio = Double.parseDouble(new Web().SendPOST("http://" + WebServerName + "/mcst/GetBoostRatio.php", new String[]{"LICENSE_KEY", "USER_KEY", "SINGLE_CORE_TIME"}, new String[]{LICENSE_KEY, SysSpec.getString("MAC_ADDRESS"), ""}, false));
            double[] BoostRatioList = mkJSON.getDoubleArrayFromJSONArray(allRatio);
            Arrays.sort(BoostRatioList);
            for (int i = 0; i < BoostRatioList.length; i++) {
                if (BoostRatioList[i] >= BoostRatio) { //System.out.println("Your machine stands at rank "+(BoostRatioList.length-i)+" among all "+BoostRatioList.length+" machines tested so far.");
                    return i;

                } else if (BoostRatioList[0] > BoostRatio) {
                    System.out.println("#getSCTPossibleRank: Your machine stands at TOP in Single Core Timing");
                    return 1;
                }
            }
            return BoostRatioList.length;
        } catch (JSONException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    protected String getSCTPossibleRatio(String WebServerName) {
        try {
            // JSONObject datatoPost=new GlobalMCUTest().printMachineInfo(false);


            //JSONArray allRatio=new JSONArray(new Web().SendPOST("http://"+WebServerName+"/mcst/GetAllSCT.php", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false));
            JSONArray allRatio = new Web().readJsonArrayFromUrl("http://" + WebServerName + "/mcst/mcstAllSCT", false);
            double BoostRatio = Double.parseDouble(new Web().SendPOST("http://" + WebServerName + "/mcst/GetBoostRatio.php", new String[]{"LICENSE_KEY", "USER_KEY", "SINGLE_CORE_TIME"}, new String[]{LICENSE_KEY, SysSpec.getString("MAC_ADDRESS"), ""}, false));
            double[] BoostRatioList = mkJSON.getDoubleArrayFromJSONArray(allRatio);
            double WorldAvg = new JSONObject(new Web().SendPOST("http://" + WebServerName + "/mcst/mcst", new String[]{"LICENSE_KEY"}, new String[]{LICENSE_KEY}, false)).getDouble("WA_SINGLE_CORE_TIME_POSSIBLE");
            Arrays.sort(BoostRatioList);
            for (int i = 0; i < BoostRatioList.length; i++) {
                if (BoostRatioList[i] > BoostRatio) {
                    //System.out.println("Your machine stands at rank "+(BoostRatioList.length-i)+" among all "+BoostRatioList.length+" machines tested so far.");
//                            if(BoostRatio>WorldAvg){System.out.println("\n Machine rating:___ABOVE AVERAGE___, it can give you__BitCoins__ :)\n ");}
//                            else{System.out.println("\n Machine rating:___BELOW AVERAGE___.Time to upgrade :) \n DO NOT THINK ABOUT __BitCoins__!");}
//                      
                    return i + "/" + BoostRatioList.length;

                } else if (BoostRatioList[0] > BoostRatio) {//Minimum is top
                    System.out.println("#getSCTPossibleRatio: Your machine stands at TOP in Single Core Timing");
                    return 1 + "/" + BoostRatioList.length;
                }
            }
            return BoostRatioList.length + "/" + BoostRatioList.length;
        } catch (JSONException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "NA";
    }

    /**
     * Multi Core Timing Ranks
     *
     * @param WebServerName
     * @return
     */
    protected int getMCTPossibleRank(String WebServerName) {
        try {
            //JSONObject datatoPost=new GlobalMCUTest().printMachineInfo(false);
            //JSONArray allRatio=new JSONArray(new Web().SendPOST("http://"+WebServerName+"/mcst/GetAllMCT.php", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false));
            JSONArray allRatio = new Web().readJsonArrayFromUrl("http://" + WebServerName + "/mcst/mcstAllMCT", false);
            double BoostRatio = Double.parseDouble(new Web().SendPOST("http://" + WebServerName + "/mcst/GetBoostRatio.php", new String[]{"LICENSE_KEY", "USER_KEY", "MULTI_CORE_TIME"}, new String[]{LICENSE_KEY, SysSpec.getString("MAC_ADDRESS"), ""}, false));
            double[] BoostRatioList = mkJSON.getDoubleArrayFromJSONArray(allRatio);
            Arrays.sort(BoostRatioList);
            for (int i = 0; i < BoostRatioList.length; i++) {
                if (BoostRatioList[i] >= BoostRatio) { //System.out.println("Your machine stands at rank "+(BoostRatioList.length-i)+" among all "+BoostRatioList.length+" machines tested so far.");
                    return i;

                } else if (BoostRatioList[0] > BoostRatio) {
                    System.out.println("#getMCTPossibleRank: Your machine stands at TOP in Multi Core Timing");
                    return 1;
                }
            }
            return BoostRatioList.length;
        } catch (JSONException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    protected String getMCTPossibleRatio(String WebServerName) {
        try {
            //JSONObject datatoPost=new GlobalMCUTest().printMachineInfo(false);


            //JSONArray allRatio=new JSONArray(new Web().SendPOST("http://"+WebServerName+"/mcst/GetAllMCT.php", new String []{"LICENSE_KEY"}, new String[] {LICENSE_KEY}, false));
            JSONArray allRatio = new Web().readJsonArrayFromUrl("http://" + WebServerName + "/mcst/mcstAllMCT", false);
            double BoostRatio = Double.parseDouble(new Web().SendPOST("http://" + WebServerName + "/mcst/GetBoostRatio.php", new String[]{"LICENSE_KEY", "USER_KEY", "MULTI_CORE_TIME"}, new String[]{LICENSE_KEY, SysSpec.getString("MAC_ADDRESS"), ""}, false));
            double[] BoostRatioList = mkJSON.getDoubleArrayFromJSONArray(allRatio);
            double WorldAvg = new JSONObject(new Web().SendPOST("http://" + WebServerName + "/mcst/mcst", new String[]{"LICENSE_KEY"}, new String[]{LICENSE_KEY}, false)).getDouble("WA_MULTI_CORE_TIME_POSSIBLE");
            Arrays.sort(BoostRatioList);
            for (int i = 0; i < BoostRatioList.length; i++) {
                if (BoostRatioList[i] > BoostRatio) {
                    //System.out.println("Your machine stands at rank "+(BoostRatioList.length-i)+" among all "+BoostRatioList.length+" machines tested so far.");
//                            if(BoostRatio>WorldAvg){System.out.println("\n Machine rating:___ABOVE AVERAGE___, it can give you__BitCoins__ :)\n ");}
//                            else{System.out.println("\n Machine rating:___BELOW AVERAGE___.Time to upgrade :) \n DO NOT THINK ABOUT __BitCoins__!");}
//                      
                    return i + "/" + BoostRatioList.length;

                } else if (BoostRatioList[0] > BoostRatio) {
                    System.out.println("#getMCTPossibleRatio: Your machine stands at TOP in Multi Core Timing");
                    return 1 + "/" + BoostRatioList.length;
                }
            }
            return BoostRatioList.length + "/" + BoostRatioList.length;
        } catch (JSONException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "NA";
    }

    /**
     * Limited to 1000 hits per hour
     *
     * @return
     * @throws JSONException
     */
    private JSONObject getUserLocation() {


        if (hasInternet) {
            return new Web().readJsonFromUrl("http://freegeoip.net/json/", true);
        } else {
            JSONObject json = new JSONObject();
            try {
                json.put("city", "NA");
                json.put("country_name", "NA");
            } catch (JSONException ex) {
                Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            return json;
        }

        //System.out.println(address);
        //System.out.println("#getUserLocation()..Problem");
        //return new JSONObject(new Web().SendGET("http://freegeoip.net/json/", new String []{}, new String[] {}, true));
        // return null;

    }

    protected String generateWallPaperOld(String Rank, JSONObject SysSpec, String Md5Key, String footer) {
        try {
            // BufferedImage image= new Web().getImage("http://"+WebServerName+"/Wallpapers/MCW%20(40).jpeg");
            BufferedImage image = new Web().getImage("http://" + WebServerName + "/GetWallpaper.php?LICENSE_KEY=TESTKEY");

            Graphics g = image.getGraphics();

            /**
             * Draw a circle
             */
//             g.fillOval(image.getWidth()-250, 200-150, 200, 200);
//             g.setColor(Color.DARK_GRAY);
//             g.fillOval(image.getWidth()-250+5, 200-150+5, 190, 190);
//             g.setColor(Color.WHITE);
            /**
             * Draw round corner rectangle
             */
            g.setColor(Color.DARK_GRAY);
            g.fillRoundRect(image.getWidth() - 250 - 2, 200 - 150 - 2, 500, 204, 30, 30);
            g.setColor(Color.WHITE);
            g.fillRoundRect(image.getWidth() - 250, 200 - 150, 500, 200, 30, 30);
            g.setColor(Color.DARK_GRAY);
            g.fillRoundRect(image.getWidth() - 250 + 5, 200 - 150 + 5, 490, 190, 30, 30);


            Font font = new Font("League-Gothic", Font.ROMAN_BASELINE, 40);

            g.setFont(getFont(5, Font.ROMAN_BASELINE, 40));
            // g.setFont(g.getFont().deriveFont(100f));
            g.setColor(Color.DARK_GRAY);
            // g.drawString("MCW", image.getWidth()-330, 200);

            //Set Rank
            font = new Font("League-Gothic", Font.BOLD, 150);
            g.setFont(font);
            // g.setFont(g.getFont().deriveFont(100f));
            g.setColor(Color.WHITE);
            g.drawString(Rank, image.getWidth() - 200, 200);

            //g.setFont(getFont(5,Font.BOLD,22));
            font = new Font("League-Gothic", Font.BOLD, 15);
            g.setFont(font);
            g.setColor(Color.DARK_GRAY);
            JSONArray names = SysSpec.names();
            try {
                mkJSON.sortJSONArray(names);
                for (int i = 0; i < names.length(); i++) {

                    g.drawString(names.getString(i) + " : " + SysSpec.getString(names.getString(i)), image.getWidth() - 500, image.getHeight() - 35 - 35 * i);

                }
            } catch (JSONException ex) {
                Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.drawString(footer, 10, image.getHeight() - 20);

            ImageIO.write(image, "png", new File("MCW_Rank.png"));
            g.dispose();
        } catch (IOException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "MCW_Rank.png";
    }

    protected String generateWallPaperOld2(String Rank, JSONObject SysSpec, String Md5Key, String footer) {
        try {
            // BufferedImage image= new Web().getImage("http://"+WebServerName+"/Wallpapers/MCW%20(40).jpeg");
            BufferedImage image = new Web().getImage("http://" + WebServerName + "/GetWallpaper.php?LICENSE_KEY=TESTKEY");

            Graphics g = image.getGraphics();

            /**
             * Draw a circle
             */
//             g.fillOval(image.getWidth()-250, 200-150, 200, 200);
//             g.setColor(Color.DARK_GRAY);
//             g.fillOval(image.getWidth()-250+5, 200-150+5, 190, 190);
//             g.setColor(Color.WHITE);
            /**
             * Draw round corner rectangle
             */
            g.setColor(Color.DARK_GRAY);
            g.fillRoundRect(image.getWidth() - 250 - 2, 200 - 150 - 2, 500, 204, 30, 30);
            g.setColor(Color.WHITE);
            g.fillRoundRect(image.getWidth() - 250, 200 - 150, 500, 200, 30, 30);
            g.setColor(Color.DARK_GRAY);
            g.fillRoundRect(image.getWidth() - 250 + 5, 200 - 150 + 5, 490, 190, 30, 30);


            Font font = new Font("League-Gothic", Font.ROMAN_BASELINE, 40);

            g.setFont(getFont(5, Font.ROMAN_BASELINE, 40));
            // g.setFont(g.getFont().deriveFont(100f));
            g.setColor(Color.DARK_GRAY);
            // g.drawString("MCW", image.getWidth()-330, 200);

            //Set Rank
            font = new Font("League-Gothic", Font.BOLD, 150);
            g.setFont(font);
            // g.setFont(g.getFont().deriveFont(100f));
            g.setColor(Color.WHITE);
            g.drawString(Rank, image.getWidth() - 200, 200);

            //g.setFont(getFont(5,Font.BOLD,22));
            font = new Font("League-Gothic", Font.BOLD, 15);
            g.setFont(font);
            g.setColor(Color.DARK_GRAY);

            //Print information
            try {
                JSONObject OS = SysSpec.getJSONObject("OS");
                JSONArray names = OS.names();
                mkJSON.sortJSONArray(names);
                for (int i = 0; i < names.length(); i++) {

                    g.drawString(names.getString(i) + " : " + OS.getString(names.getString(i)), image.getWidth() - 300, image.getHeight() - 35 - 35 * i);

                }
                //Print Header
                font = new Font("League-Gothic", Font.BOLD, 25);
                g.setFont(font);
                g.setColor(Color.DARK_GRAY);
                g.drawString("Operating System", image.getWidth() - 300, image.getHeight() - 40 - 35 * names.length());
                g.drawLine(image.getWidth() - 300, image.getHeight() - 35 - 35 * names.length(), image.getWidth() - 50, image.getHeight() - 35 - 35 * names.length());
                font = new Font("League-Gothic", Font.BOLD, 15);
                g.setFont(font);
                g.setColor(Color.DARK_GRAY);

                //Print Info
                OS = SysSpec.getJSONObject("RAM");
                names = OS.names();
                mkJSON.sortJSONArray(names);
                for (int i = 0; i < names.length(); i++) {

                    g.drawString(names.getString(i) + " : " + OS.getString(names.getString(i)), image.getWidth() - 700, image.getHeight() - 35 - 35 * i);

                }

                //Print Header
                font = new Font("League-Gothic", Font.BOLD, 25);
                g.setFont(font);
                g.setColor(Color.DARK_GRAY);
                g.drawString("Processing Hardware", image.getWidth() - 700, image.getHeight() - 40 - 35 * names.length());
                g.drawLine(image.getWidth() - 700, image.getHeight() - 35 - 35 * names.length(), image.getWidth() - 450, image.getHeight() - 35 - 35 * names.length());
                font = new Font("League-Gothic", Font.BOLD, 15);
                g.setFont(font);
                g.setColor(Color.DARK_GRAY);

                OS = SysSpec.getJSONObject("ROM");
                names = OS.names();
                mkJSON.sortJSONArray(names);
                for (int i = 0; i < names.length(); i++) {

                    g.drawString(names.getString(i) + ":"
                            + " T: " + OS.getJSONObject(names.getString(i)).getString("TOTAL_SPACE_GB") + " GB"
                            + " A: " + OS.getJSONObject(names.getString(i)).getString("AVAILABLE_GB") + " GB"
                            + " U: " + OS.getJSONObject(names.getString(i)).getString("USED_PERCENT")
                            + " V: " + OS.getJSONObject(names.getString(i)).getString("TPYE"), image.getWidth() - 1100, image.getHeight() - 35 - 35 * i);

                }

                //Print Header
                font = new Font("League-Gothic", Font.BOLD, 25);
                g.setFont(font);
                g.setColor(Color.DARK_GRAY);
                g.drawString("Storage", image.getWidth() - 1100, image.getHeight() - 40 - 35 * names.length());
                g.drawLine(image.getWidth() - 1100, image.getHeight() - 35 - 35 * names.length(), image.getWidth() - 850, image.getHeight() - 35 - 35 * names.length());
                font = new Font("League-Gothic", Font.BOLD, 15);
                g.setFont(font);
                g.setColor(Color.DARK_GRAY);

                //NETWORK INTERFACE
//             OS=SysSpec.getJSONObject("NET");
//             names=OS.names();
//             mkJSON.sortJSONArray(names);
//             for(int i=0;i<names.length();i++){
//               
//                    g.drawString(names.getString(i)+":"+
//                                        "\t"+ OS.getJSONObject(names.getString(i)).getString("Desc"), image.getWidth()-1600,image.getHeight()-27-35*i);
//                
//                     g.drawString("\tMAC: "+ OS.getJSONObject(names.getString(i)).getString("HWaddr")
//                                    +"\tIP: "+ OS.getJSONObject(names.getString(i)).getString("inet_addr")
//                                    +"\tType: "+ OS.getJSONObject(names.getString(i)).getString("Link_encap"), image.getWidth()-1600,image.getHeight()-10-35*i);
//             }
//             
//             //Print Header
//             font = new Font("League-Gothic", Font.BOLD, 25);
//             g.setFont(font);
//             g.setColor(Color.DARK_GRAY);
//             g.drawString("Network Interface", image.getWidth()-1600,image.getHeight()-35-35*names.length());
//             g.drawLine(image.getWidth()-1600,image.getHeight()-27-35*names.length(), image.getWidth()-1350,image.getHeight()-27-35*names.length());
//             font = new Font("League-Gothic", Font.BOLD, 15);
//             g.setFont(font);
//             g.setColor(Color.DARK_GRAY);



            } catch (JSONException ex) {
                Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.drawString(footer, 10, image.getHeight() - 20);

            ImageIO.write(image, "png", new File("MCW_Rank.png"));
            g.dispose();
        } catch (IOException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "MCW_Rank.png";
    }

    
    protected String generateWallPaper(String Rank, JSONObject SysSpec, String Md5Key, String footer) {
        try {
            // BufferedImage image= new Web().getImage("http://"+WebServerName+"/Wallpapers/MCW%20(40).jpeg");
            BufferedImage image = new Web().getImage("http://" + WebServerName + "/GetWallpaper.php?LICENSE_KEY=TESTKEY");

            Graphics g = image.getGraphics();

            /**
             * Draw a circle
             */
//             g.fillOval(image.getWidth()-250, 200-150, 200, 200);
//             g.setColor(Color.DARK_GRAY);
//             g.fillOval(image.getWidth()-250+5, 200-150+5, 190, 190);
//             g.setColor(Color.WHITE);
            /**
             * Draw round corner rectangle
             */
            g.setColor(Color.DARK_GRAY);
            g.fillRoundRect(image.getWidth() - 250 - 2, 200 - 150 - 2, 500, 204, 30, 30);
            g.setColor(Color.WHITE);
            g.fillRoundRect(image.getWidth() - 250, 200 - 150, 500, 200, 30, 30);
            g.setColor(Color.DARK_GRAY);
            g.fillRoundRect(image.getWidth() - 250 + 5, 200 - 150 + 5, 490, 190, 30, 30);


            Font font = new Font("League-Gothic", Font.ROMAN_BASELINE, 40);

            g.setFont(getFont(5, Font.ROMAN_BASELINE, 40));
            // g.setFont(g.getFont().deriveFont(100f));
            g.setColor(Color.DARK_GRAY);
            // g.drawString("MCW", image.getWidth()-330, 200);

            //Set Rank
            font = new Font("League-Gothic", Font.BOLD, 150);
            g.setFont(font);
            // g.setFont(g.getFont().deriveFont(100f));
            g.setColor(Color.WHITE);
            g.drawString(Rank, image.getWidth() - 200, 200);

            //g.setFont(getFont(5,Font.BOLD,22));
            font = new Font("League-Gothic", Font.BOLD, 15);
            g.setFont(font);
            g.setColor(Color.DARK_GRAY);

            //Print information
            try {
             JSONObject OS = SysSpec.getJSONObject("OS");
                JSONArray names = OS.names();
                names = OS.names();
                g.drawImage(OSAppGraphics.getJSONDataKhakee(185, OS, "Operating System", false), image.getWidth() - 300, image.getHeight() - 40 - 35 * names.length(), this);

                font = new Font("League-Gothic", Font.BOLD, 10);
                g.setFont(font);
                g.setColor(Color.LIGHT_GRAY);
                g.setColor(new Color(6, 112, 154)); //News Papaer Ink
                int OS_ENDPOS = getHeight() - 35 - 12 * names.length();
                //Print Info
                OS = SysSpec.getJSONObject("RAM");
                names = OS.names();
                names = OS.names();
                g.drawImage(OSAppGraphics.getJSONDataKhakee(185, OS, "Processing Hardware", false),image.getWidth() - 700, image.getHeight() - 40 - 35 * names.length(), this);

                //  START

                OS = SysSpec.getJSONObject("ROM");
                names = OS.names();
                g.drawImage(OSAppGraphics.getROMDataKhakee(500 - 195, OS, false), image.getWidth() - 1100, image.getHeight() - 40 - 35 * names.length(), this);

            } catch (JSONException ex) {
                Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.drawString(footer, 10, image.getHeight() - 20);

            ImageIO.write(image, "png", new File("MCW_Rank.png"));
            g.dispose();
        } catch (IOException ex) {
            Logger.getLogger(WorldSpeedTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "MCW_Rank.png";
    }

    /**
     *
     * @param Family
     * @param Style
     * @param Size
     * @return
     */
    public Font getFont(int Family, int Style, int Size) {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontFamilies = ge.getAvailableFontFamilyNames();
//    fontFamilies[Family]= (String) JOptionPane.showInputDialog( new Frame(),
//                "Font selection:",
//                "Select",
//                JOptionPane.PLAIN_MESSAGE,
//                null,//Was icon
//                fontFamilies,
//                fontFamilies[0]);
        Font font = new Font(fontFamilies[Family], Style, Size);
        return font;
    }

    /**
     *
     * @param BlocksToDisplay
     * @param debug
     * @return
     */
    public static int[] getBestVisualArrangement(int BlocksToDisplay, boolean debug) {
        int rows = 1;
        int columns = 1;
        boolean incrementRow = true;
        while (true) {
            if (rows * columns >= BlocksToDisplay) {
                if (debug) {
                    System.out.println("#getBestVisualArrangement BlocksToDisplay=" + BlocksToDisplay + " rows=" + rows + "\t columns=" + columns + "\t scale= 1/" + Math.max(rows, columns));
                }
                return new int[]{rows, columns, Math.max(rows, columns)};
            }
            if (incrementRow) // Start with row increment
            {
                rows++;
                incrementRow = false;
            } else {
                columns++;
                incrementRow = true;
            }

        }

    }

    /**
     *
     * @param BlocksToDisplay
     * @param BlockToDisplay
     * @param debug
     * @return
     */
    public static int[] getBestVisualPosition(int[] BlocksToDisplay, int BlockToDisplay, boolean debug) {
        int rows = BlocksToDisplay[0]; //rows--;
        int columns = BlocksToDisplay[1];//columns--;
        int iRow = 0;
        int iCol = 0;
        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows; i++) {
                iRow = i;
                iCol = j;
                if (BlockToDisplay == (i + j * columns)) {
                    //System.out.println("#getBestVisualPosition i="+i+" j="+j+" i*rows+j="+(i+j*columns) +"\t i*j="+(i*j));
                    //System.out.println("#");
                    return new int[]{i, j, Math.max(i, j)};
                }
            }
        }
        //  System.out.println("#---");
        // System.out.println("#getBestVisualPosition iRow="+iRow+" columns="+iCol+" iRow*rows+j="+(iRow*rows+iCol) +"\t iRow*j="+(iRow*iCol));
        //System.out.println("#---");

        if (BlockToDisplay == (iRow + iCol * columns)) {// System.out.println("#getBestVisualPosition iRow="+iRow+" iCol="+iCol+" i*rows+j="+(iRow*rows+iCol) +"\t i*j="+(iRow*iCol));
            return new int[]{iRow, iCol, Math.max(iRow, iCol)};
        }

        return new int[]{rows, columns, Math.max(rows, columns)};
    }

    private static String getRepeats(String chars, int Qty) {
        String result = "";
        for (int i = 0; i < Qty; i++) {
            result = result + chars;
        }
        return result;
    }

    public void startComponentToSystemOutput() {
        cardLayout.show(SystemOutput, "SystemOutput");
    }

    public void startPreferencesToCPUProfile() {
        cardLayout.show(SystemOutput, "CPUProfile");
    }

    public void startPreferencesTo2DGraphics() {
        cardLayout.show(SystemOutput, "2DGraphics");
    }

    private class ItemHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            menuPopup.setVisible(false);
            //  System.out.println("e.getSource() ="+e.getSource() );
            // determine which menu item was selected
            for (int i = 0; i < SysOpItem.length; i++) {
                if (e.getSource() == SysOpItem[ i]) {

                    cardLayout.show(CardLayoutPanel, SysOpItem[i].getText());
                    ActiveApp.setText(SysOpItem[i].getText());
                    // repaint();
                    return;
                }
            }
        }
    }

    class MCAWICOItemListener implements ItemListener {

        public void itemStateChanged(ItemEvent e) {
            Object source = e.getSource();
            if (source instanceof AbstractButton == false) {
                return;
            }
            boolean checked = e.getStateChange() == ItemEvent.SELECTED;
//      for(int x = 0, y = table.getRowCount(); x < y; x++)  
//      {  
//        table.setValueAt(new Boolean(checked),x,0);  
//      }  
        }
    }
}

/**
 * @version 1.0 11/09/98
 */
class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setSize(100, 100);
        setOpaque(true);
    }
    public static final Border focusedCellBorder = UIManager.getBorder("Table.focusCellHighlightBorder");
    public static final Border unfocusedCellBorder = createEmptyBorder();

    private static Border createEmptyBorder() {
        Insets i = focusedCellBorder.getBorderInsets(new JLabel());
        return BorderFactory.createEmptyBorder(i.top, i.left, i.bottom, i.right);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column)
    {

        Color background;
        Color foreground;

        if (isSelected | hasFocus) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }

        // label.setBorder(hasFocus ? focusedCellBorder : unfocusedCellBorder);

        setText((value == null) ? "" : value.toString());
        return this;
    }
}
