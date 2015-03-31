package org.core.bean;

/**
 * 模板
 */
public class TemplateConfig {
    /**
     * 错误页
     */
    public static final String ERROR_PAGE = "errorPage";
    /**
     * 错误页500
     */
    public static final String ERROR_PAGE_500 = "errorPage500";
    /**
     * 错误页404
     */
    public static final String ERROR_PAGE_404 = "errorPage404";
    /**
     * 错误页403
     */
    public static final String ERROR_PAGE_403 = "errorPage403";
    /**
     * 错误页权限不足
     */
    public static final String ERROR_PAGE_ACCESS = "errorPageAccess";

    /**
     * 模版名称
     */
    private String templateName;
    /**
     * 模版描述
     */
    private String templateDescription;
    /**
     * 模版路径
     */
    private String templateFilePath;
    /**
     * 输出html路径
     */
    private String htmlFilePath;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateDescription() {
        return templateDescription;
    }

    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    public String getTemplateFilePath() {
        return templateFilePath;
    }

    public void setTemplateFilePath(String templateFilePath) {
        this.templateFilePath = templateFilePath;
    }

    public String getHtmlFilePath() {
        return htmlFilePath;
    }

    public void setHtmlFilePath(String htmlFilePath) {
        this.htmlFilePath = htmlFilePath;
    }
}
