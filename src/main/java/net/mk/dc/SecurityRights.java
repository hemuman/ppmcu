/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dc;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.JSONException;
import json.JSONObject;

/**
 *
 * @author PDI
 */
public class SecurityRights {

    private static JSONObject DTExceptionList = new JSONObject();

    /**
     * Get the value of DTExceptionList[]
     *
     * @return the value of DTExceptionList[]
     */
    public static JSONObject getDTExceptionList() {
        return DTExceptionList;
    }

    /**
     * Set the value of DTExceptionList[]
     *
     * @param DTExceptionList[] new value of DTExceptionList[]
     */
    public static void setDTExceptionList(JSONObject DTExceptionList
    ) {
        SecurityRights.DTExceptionList = DTExceptionList;
    }

    public static final boolean ExecutionAllowedFor(String DistributedTask) {

        if (DTExceptionList.has(DistributedTask)) {
            try {
                return DTExceptionList.getBoolean(DistributedTask);
            } catch (JSONException ex) {
                Logger.getLogger(SecurityRights.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return true;
    }

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public static void main(String... args) {

    }
}
