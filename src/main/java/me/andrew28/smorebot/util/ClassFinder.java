package me.andrew28.smorebot.util;

import java.io.File;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Easily find classes in jars
 * @author Andrew Tran
 */
public class ClassFinder {
    /**
     * Find all classes that start with the package name
     * @param jarFile The jar file
     * @param packageName Start of package name
     * @return Classes which start with packageName in jarFile
     */
    public static Set<Class<?>> getClasses(File jarFile, String packageName) {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        try {
            JarFile file = new JarFile(jarFile);
            for (Enumeration<JarEntry> entry = file.entries(); entry.hasMoreElements();) {
                JarEntry jarEntry = entry.nextElement();
                String name = jarEntry.getName().replace("/", ".");
                if(name.startsWith(packageName) && name.endsWith(".class")){
                    classes.add(Class.forName(name.substring(0, name.length() - 6)));}
            }
            file.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return classes;
    }
}