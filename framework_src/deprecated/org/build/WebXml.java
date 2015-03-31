package org.core.build;

/**
 * Created with Liang Qu
 * Create User: Liang Qu
 * Update User: Liang Qu
 * Create Date: 2013/11/13
 * Update Date: 2013/11/13
 */
public class WebXml {
    private WebVersion version;
    private String xmlns;
    private String xmlnsXsi;
    private String xsiSchemaLocation;
    private String location;
    private String springFile;
    private String log4jFile;
    private String struts2;
    private String xfire;
    private String flex;
    private String welcome;
    private String errorPage;

    public WebVersion getVersion() {
        return version;
    }

    public void setVersion(WebVersion version) {
        this.version = version;
    }

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public String getXmlnsXsi() {
        return xmlnsXsi;
    }

    public void setXmlnsXsi(String xmlnsXsi) {
        this.xmlnsXsi = xmlnsXsi;
    }

    public String getXsiSchemaLocation() {
        return xsiSchemaLocation;
    }

    public void setXsiSchemaLocation(String xsiSchemaLocation) {
        this.xsiSchemaLocation = xsiSchemaLocation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpringFile() {
        return springFile;
    }

    public void setSpringFile(String springFile) {
        this.springFile = springFile;
    }

    public String getLog4jFile() {
        return log4jFile;
    }

    public void setLog4jFile(String log4jFile) {
        this.log4jFile = log4jFile;
    }

    public String getStruts2() {
        return struts2;
    }

    public void setStruts2(String struts2) {
        this.struts2 = struts2;
    }

    public String getXfire() {
        return xfire;
    }

    public void setXfire(String xfire) {
        this.xfire = xfire;
    }

    public String getFlex() {
        return flex;
    }

    public void setFlex(String flex) {
        this.flex = flex;
    }

    public String getWelcome() {
        return welcome;
    }

    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }

    public String getErrorPage() {
        return errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }
}
