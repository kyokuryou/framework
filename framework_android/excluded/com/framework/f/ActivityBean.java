package org.smarty.core.f;

import android.os.Bundle;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by kyokuryou on 15-3-12.
 */
public class ActivityBean {
    private String PACKAGE_PATH = "org.framework.core.test";
    private Map<String,Class<?>> classes = new LinkedHashMap<String, Class<?>>();

    protected void onCreate(Bundle savedInstanceState) {
        try {
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(PACKAGE_PATH.replace('.', '/'));
            while(dirs.hasMoreElements()){
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if("file".equals(protocol)){
                    File dir = new File(url.getPath());
                    File[] dirfiles = dir.listFiles(new FileFilter() {
                        // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
                        public boolean accept(File file) {
                            return file.getName().endsWith(".class");
                        }
                    });

                    for(File f : dirfiles){

                        String className = f.getName().substring(0,
                                f.getName().length() - 6);
                        Class cls = Class.forName(PACKAGE_PATH+"."+className);
                        ActivityMapping am = (ActivityMapping)cls.getAnnotation(ActivityMapping.class);
                       classes.put(am.name(),cls);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        initActivityBean();
    }

    public static void main(String[] args) {
        new ActivityBean().onCreate(null);
    }

    protected void initActivityBean() throws RuntimeException{

    }
}
