package com.framework.core.boot;

import android.app.Application;
import org.apache.felix.framework.Felix;
import org.apache.felix.main.AutoActivator;
import org.osgi.framework.launch.Framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by kyokuryou on 15-3-2.
 */
public abstract class ApplicationBoot extends Application {

    @Override
    public final void onCreate() {


    }

    private void launch() throws Exception {
        Properties configProps = loadConfigProperties();
        Framework m_felix = new Felix(configProps);
        m_felix.start();
        m_felix.waitForStop(0L);
    }

    private Properties loadConfigProperties() {
        Properties map = new Properties();
        map.put("org.osgi.framework.startlevel.beginning", "1");
        map.put("org.osgi.framework.storage", "");
        map.put("felix.startlevel.bundle", "0");
        map.put("felix.log.level", "1");
        map.put("felix.auto.deploy.action", "install,start");
        map.put("felix.systembundle.activators", getActivators(map));
        return map;
    }

    private List getActivators(Properties map) {
        ArrayList list = new ArrayList();
        list.add(new AutoActivator(map));
        return list;
    }
}
