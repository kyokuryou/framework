package org.smarty.core.support;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.smarty.core.bean.TemplateConfig;
import org.smarty.core.logger.RuntimeLogger;
import org.smarty.core.utils.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.*;

/**
 * 模板生成,修改
 */
public class TemplateManager {
    private static RuntimeLogger logger = new RuntimeLogger(TemplateManager.class);
    // 模板配置文件名称
    private FreemarkerManager freemarkerManager;

    private String configFileName;
    private String staticConfig;
    private String mailConfig;
    private String dynamicConfig;

    /**
     * 更新静态模板
     *
     * @param name            名字
     * @param templateContent 内容
     */
    public void updateHtmlPage(String name, String templateContent, Map<String, Object> data) {
        if (LogicUtil.isEmpty(name) || LogicUtil.isEmpty(templateContent)) {
            return;
        }
        TemplateConfig tsc = getTemplate(name, staticConfig);
        TemplateUtil.writeTemplateFileContent(tsc, templateContent);
        ServletContext sc = SpringUtil.getServletContext();
        freemarkerManager.getConfiguration(sc).clearTemplateCache();
        // 更新静态页面
        Map<String, Object> htmlData = getCommonData();
        if (!errorPageHtml(tsc, htmlData)) {
            htmlData.putAll(data);
            buildHtml(tsc, htmlData);
        }
    }

    /**
     * 更新mail模板
     *
     * @param name            名字
     * @param templateContent 内容
     */
    public void updateMailPage(String name, String templateContent) {
        if (LogicUtil.isEmpty(name) || LogicUtil.isEmpty(templateContent)) {
            return;
        }
        TemplateConfig tmc = getTemplate(name, mailConfig);
        TemplateUtil.writeTemplateFileContent(tmc, templateContent);
        ServletContext sc = SpringUtil.getServletContext();
        freemarkerManager.getConfiguration(sc).clearTemplateCache();
    }

    /**
     * 更新动态模板
     *
     * @param name            名字
     * @param templateContent 内容
     */
    public void updateDynamicPage(String name, String templateContent) {
        if (LogicUtil.isEmpty(name) || LogicUtil.isEmpty(templateContent)) {
            return;
        }
        TemplateConfig tdc = getTemplate(name, dynamicConfig);
        TemplateUtil.writeTemplateFileContent(tdc, templateContent);
        ServletContext sc = SpringUtil.getServletContext();
        freemarkerManager.getConfiguration(sc).clearTemplateCache();
    }

    /**
     * 生成htmlConfig节点下的所有模板
     *
     * @param htmlDatum 模板需要的数据(错误页除外)
     */
    public void allBuildHtml(Map<String, Map<String, Object>> htmlDatum) {
        List<TemplateConfig> staticConfigList = getTemplateList(staticConfig);
        Map<String, Object> data = getCommonData();
        for (TemplateConfig staticConfig : staticConfigList) {
            if (staticConfig == null) {
                continue;
            }
            Map<String, Object> htmlData = new HashMap<String, Object>(data);
            if (errorPageHtml(staticConfig, htmlData)) {
                continue;
            }
            if (LogicUtil.isNotEmptyMap(htmlDatum)) {
                htmlData.putAll(htmlDatum.get(staticConfig.getTemplateName()));
            }
            buildHtml(staticConfig, htmlData);
        }
    }

    /**
     * 错误页面生成
     *
     * @param staticConfig staticConfig
     * @return 生成true, 否则false
     */
    public boolean errorPageHtml(TemplateConfig staticConfig, Map<String, Object> data) {
        String name = staticConfig.getTemplateName();
        // 生成错误页面
        if (TemplateConfig.ERROR_PAGE.equals(name)) {
            data.put("errorContent", "系统出现异常，请与管理员联系！");
            buildHtml(staticConfig, data);
            return true;
        }
        // 生成403错误页面
        if (TemplateConfig.ERROR_PAGE_403.equals(name)) {
            data.put("errorContent", "系统出现异常，请与管理员联系！");
            buildHtml(staticConfig, data);
            return true;
        }
        // 生成404错误页面
        if (TemplateConfig.ERROR_PAGE_404.equals(name)) {
            data.put("errorContent", "您访问的页面不存在！");
            buildHtml(staticConfig, data);
            return true;
        }
        // 生成500错误页面
        if (TemplateConfig.ERROR_PAGE_500.equals(name)) {
            data.put("errorContent", "系统出现异常，请与管理员联系！");
            buildHtml(staticConfig, data);
            return true;
        }
        // 生成权限不足页面
        if (TemplateConfig.ERROR_PAGE_ACCESS.equals(name)) {
            data.put("errorContent", "您无此访问权限！");
            buildHtml(staticConfig, data);
            return true;
        }
        return false;
    }

    /**
     * 生成页面
     *
     * @param staticConfig staticConfig
     * @param data         数据
     */
    public void buildHtml(TemplateConfig staticConfig, Map<String, Object> data) {
        try {
            ServletContext sc = SpringUtil.getServletContext();
            Configuration configuration = freemarkerManager.getConfiguration(sc);
            Template template = configuration.getTemplate(staticConfig.getTemplateFilePath(), "UTF-8");
            File htmlFile = new File(sc.getRealPath(staticConfig.getHtmlFilePath()));
            File fileDirectory = htmlFile.getParentFile();
            if (!fileDirectory.exists()) {
                fileDirectory.mkdirs();
            }
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
            FileUtils.writeStringToFile(htmlFile, CompressorUtil.htmlCompressor(text), "UTF-8");
        } catch (Exception e) {
            logger.out(e);
        }
    }

    /**
     * 获取公共数据
     */
    public Map<String, Object> getCommonData() {
        ServletContext sc = SpringUtil.getServletContext();
        Map<String, Object> commonData = new HashMap<String, Object>();
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n");
            ResourceBundleModel resourceBundleModel = new ResourceBundleModel(resourceBundle, new BeansWrapper());
            commonData.put("bundle", resourceBundleModel);
        } catch (MissingResourceException e) {
            logger.out(e);
        }
        commonData.put("base", sc.getContextPath());
        commonData.put("systemConfig", SystemConfigUtil.getSystemConfig());
        return commonData;
    }

    /**
     * 获得静态模版
     *
     * @param name         名字
     * @param templateType 模板类型
     * @return 模板配置
     */
    private TemplateConfig getTemplate(String name, String templateType) {
        if (LogicUtil.isEmpty(name)) {
            return null;
        }
        String configFilePath = PathUtil.getResourceAsFile("").getParent() + configFileName;
        Document document = DocumentUtil.getDocument(new File(configFilePath));
        StringBuilder re = new StringBuilder("/template/");
        re.append(templateType).append("/").append(name).append("/");

        Node descriptionNode = document.selectSingleNode(re.toString() + "description");
        Node templateFilePathNode = document.selectSingleNode(re.toString() + "templateFilePath");
        Node htmlFilePathNode = document.selectSingleNode(re.toString() + "htmlFilePath");

        TemplateConfig tc = new TemplateConfig();
        tc.setTemplateName(name);
        tc.setTemplateDescription(descriptionNode.getText());
        tc.setTemplateFilePath(templateFilePathNode.getText());
        tc.setHtmlFilePath(htmlFilePathNode.getText());
        return tc;
    }

    /**
     * 获得静态模版
     *
     * @param templateType 模板类型
     * @return 模板配置
     */
    private List<TemplateConfig> getTemplateList(String templateType) {
        String configFilePath = PathUtil.getResourceAsFile("").getParent() + configFileName;
        Document document = DocumentUtil.getDocument(new File(configFilePath));

        List<TemplateConfig> staticConfigList = new ArrayList<TemplateConfig>();

        Element htmlConfigElement = (Element) document.selectSingleNode("/template/" + templateType);
        Iterator iterator = htmlConfigElement.elementIterator();

        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            String description = element.element("description").getTextTrim();
            String templateFilePath = element.element("templateFilePath").getTextTrim();
            String htmlFilePath = element.element("htmlFilePath").getTextTrim();
            TemplateConfig tc = new TemplateConfig();
            tc.setTemplateName(element.getName());
            tc.setTemplateDescription(description);
            tc.setTemplateFilePath(templateFilePath);
            tc.setHtmlFilePath(htmlFilePath);
            staticConfigList.add(tc);
        }
        return staticConfigList;
    }

    public void setFreemarkerManager(FreemarkerManager freemarkerManager) {
        this.freemarkerManager = freemarkerManager;
    }

    public void setConfigFileName(String configFileName) {
        this.configFileName = configFileName;
    }

    public void setStaticConfig(String staticConfig) {
        this.staticConfig = staticConfig;
    }

    public void setMailConfig(String mailConfig) {
        this.mailConfig = mailConfig;
    }

    public void setDynamicConfig(String dynamicConfig) {
        this.dynamicConfig = dynamicConfig;
    }
}
