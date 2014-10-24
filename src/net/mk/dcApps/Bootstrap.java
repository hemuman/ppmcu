/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.dcApps;

/**
 *
 * @author PDI
 */
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class Bootstrap {
/**
 * You can try this code out for yourself. Cut & paste the bootstrap code above into your favorite IDE,
 * put that single Bootstrap.class onto your classpath, and run it like so:
 * java -cp . Bootstrap sample.HelloWorldMain Hello!
 * 
 * @param args
 * @throws Exception 
 */
    public static void main(String[] args) throws Exception {

        /*
            Assume your application has a "home" directory
            with /classes and /lib beneath it, where you can put
            loose files and jars.

            Thus,

            /usr/local/src/APP
            /usr/local/src/APP/classes
            /usr/local/src/APP/lib
         */

        String HOME = "/usr/local/src/YOURAPP";
        String CLASSES = HOME + "/classes";
        String LIB = HOME + "/lib";

        // add the classes dir and each jar in lib to a List of URLs.
        List urls = new ArrayList();
        urls.add(new File(CLASSES).toURL());
        for (File f : new File(LIB).listFiles()) {
            urls.add(f.toURL());
        }

        // feed your URLs to a URLClassLoader!
        ClassLoader classloader =
                new URLClassLoader(
                        (URL[]) urls.toArray(new URL[0]),
                        ClassLoader.getSystemClassLoader().getParent());

        // relative to that classloader, find the main class
        // you want to bootstrap, which is the first cmd line arg
        Class mainClass = classloader.loadClass(args[0]);
        Method main = mainClass.getMethod("main",
                new Class[]{args.getClass()});

        // well-behaved Java packages work relative to the
        // context classloader.  Others don't (like commons-logging)
        Thread.currentThread().setContextClassLoader(classloader);

        // you want to prune the first arg because its your main class.
        // you want to pass the remaining args as the "real" args to your main
        String[] nextArgs = new String[args.length - 1];
        System.arraycopy(args, 1, nextArgs, 0, nextArgs.length);
        main.invoke(null, new Object[] { nextArgs });
    }

}