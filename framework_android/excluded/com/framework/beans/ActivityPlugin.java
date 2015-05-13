package com.framework.core.beans;

/**
 * Created by kyokuryou on 15-2-27.
 */
public class ActivityPlugin {
    private String label;
    private String packageName;
    private String prcessName;

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getPrcessName() {
        return prcessName;
    }

    public void setPrcessName(String prcessName) {
        this.prcessName = prcessName;
    }
}
