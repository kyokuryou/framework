package org.smarty.core.config;

import java.io.IOException;
import java.util.Properties;
import org.smarty.core.config.files.FileConfig;
import org.smarty.core.config.files.FlexConfig;
import org.smarty.core.config.files.LocalConfig;
import org.smarty.core.config.files.Log4jConfig;
import org.smarty.core.config.files.SpringConfig;
import org.smarty.core.config.files.Struts2Config;
import org.smarty.core.config.files.WebConfig;
import org.smarty.core.config.files.WsConfig;

/**
 * 配置文件创建者
 * Created with Liang Qu
 * Create User: Liang Qu
 * Update User: Liang Qu
 * Create Date: 2013/11/12
 * Update Date: 2013/11/12
 */
public class ConfigBuilder {

    public static void main_test(String[] args) throws IOException {
        String[] ars = new String[]{"true", "D:/work/framework/testModule", "D:/work/framework/testModule/src", "D:/work/framework/testModule/web", "V2_4", "struts2,xfire,flex", "test>jdbc", "org.test"};
        // main1(ars);
    }

    /**
     * 创建入口
     *
     * @param args -
     *             0->是否启用web端应用;true启动,false禁用
     *             1->发布目录
     *             2->java源码目录
     *             3->web源码目录
     *             4->web版本
     *             5->依赖列表(compressor,flex,image,poi,struts2,xfire,mail,jcaptcha)
     *             6->数据库连接名(datasource名>方式.如:test>jdbc或test>jndi)
     *             7->包目录
     */
    public static void main(String[] args) throws IOException {
        if (args == null || args.length < 5) {
            throw new ArrayIndexOutOfBoundsException("参数数量错误");
        }
        ConfigBuilder cb = new ConfigBuilder();
        Properties pr = cb.setProperties(args);
        FileConfig[] fcs = {new FlexConfig(), new LocalConfig(), new Log4jConfig(), new SpringConfig(), new Struts2Config(), new WebConfig(), new WsConfig()};
        for (FileConfig fc : fcs) {
            fc.buildFile(pr);
        }
    }

    /**
     * 创建环境变量
     *
     * @param args 参数
     * @return 环境变量
     */
    private Properties setProperties(String[] args) {
        Properties pro = new Properties();
        pro.setProperty("enabledWeb", args[0]);
        pro.setProperty("targetBasic", args[1]);
        pro.setProperty("targetSrc", args[2]);
        pro.setProperty("targetWeb", args[3]);
        pro.setProperty("webVersion", args[4]);
        pro.setProperty("component", args[5]);
        pro.setProperty("dbconnection", args[6]);
        pro.setProperty("packageinfo", args[7]);
        return pro;
    }
}
