package org.smarty.core.utils;

import org.apache.commons.io.FileUtils;
import org.smarty.core.bean.TemplateConfig;
import org.smarty.core.logger.RuntimeLogger;
import org.lilystudio.smarty4j.Context;
import org.lilystudio.smarty4j.Engine;
import org.lilystudio.smarty4j.Template;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * 工具类 - 模板配置
 */
public class TemplateUtil {
    private static RuntimeLogger logger = new RuntimeLogger(TemplateUtil.class);

    private static Engine engine = new Engine();

    private TemplateUtil() {
    }

    static {
        engine.setDebug(true);
        engine.setEncoding("UTF-8");
        engine.setTemplatePath("/");
        engine.setLeftDelimiter("'");
        engine.setRightDelimiter("'");
    }

    /**
     * 渲染内容.
     *
     * @param source 模板内容.
     * @param model  变量Map.
     */
    public static String render(String source, Map<String, Object> model) {
        Template template;
        try {
            template = new Template(engine, source);
        } catch (Exception e) {
            logger.out(e);
            return null;
        }

        Context context = new Context();
        if (model != null) {
            context.putAll(model);
        }
        Writer result = new StringWriter();
        template.merge(context, result);
        return result.toString();
    }

    /**
     * 渲染内容.
     *
     * @param tc    模板信息.
     * @param model 变量Map.
     */
    public static void render(TemplateConfig tc, Map<String, Object> model) {
        Template template;
        try {
            template = engine.getTemplate(tc.getTemplateFilePath());
        } catch (Exception e) {
            logger.out(e);
            return;
        }
        Context context = new Context();
        context.putAll(model);
        try {
            template.merge(context, PathUtil.getResourceAsOutStream(tc.getHtmlFilePath()));
        } catch (Exception e) {
            logger.out(e);
        }
    }


    /**
     * 读取模板文件内容
     *
     * @return 模板文件内容
     */
    public static String readTemplateFileContent(TemplateConfig dynamicConfig) {
        File templateFile = PathUtil.getServletAsFile(dynamicConfig.getTemplateFilePath());
        String templateFileContent = null;
        try {
            templateFileContent = FileUtils.readFileToString(templateFile, "UTF-8");
        } catch (IOException e) {
            logger.out(e);
        }
        return templateFileContent;
    }

    /**
     * 写入模板文件内容
     */
    public static void writeTemplateFileContent(TemplateConfig dynamicConfig, String templateFileContent) {
        File templateFile = PathUtil.getServletAsFile(dynamicConfig.getTemplateFilePath());
        try {
            FileUtils.writeStringToFile(templateFile, templateFileContent, "UTF-8");
        } catch (IOException e) {
            logger.out(e);
        }
    }
}