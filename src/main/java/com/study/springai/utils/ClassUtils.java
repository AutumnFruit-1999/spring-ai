package com.study.springai.utils;


import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *  copy from internet
 */
public class ClassUtils {


    private static final Logger log = LoggerFactory.getLogger(ClassUtils.class);


    /**
     * 获取类包下的所有子类
     */
    public static <T> Set<Class<? extends T>> getSubTypesOf(Class<T> type) {
        Set<Class<?>> classes = getClasses(type.getPackage().getName());
        Set<Class<? extends T>> collect = new HashSet<>();
        for (Class<?> aClass : classes) {
            if (!type.equals(aClass) && type.isAssignableFrom(aClass) && !aClass.isInterface() && !Modifier.isAbstract(aClass.getModifiers())) {
                //noinspection unchecked
                collect.add((Class<? extends T>) aClass);
            }
        }

        return collect;
    }

    /**
     * 获取某个包下的所有类
     */
    public static Set<Class<?>> getClasses(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();
        try {
            String sourcePath = packageName.replace(".", "/");
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(sourcePath);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(classSet, packagePath, packageName);
                    } else if ("jar".equals(protocol)) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.contains(sourcePath) && jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取子类失败", e);
        }

        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(file -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    /**
     * 加载类
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 加载类（默认将初始化类）
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }


    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }
}