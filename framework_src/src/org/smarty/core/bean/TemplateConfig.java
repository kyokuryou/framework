package org.smarty.core.bean;

import java.io.OutputStream;

/**
 * 模板
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class TemplateConfig {
    /**
     * 模版名称
     */
    private String name;
    /**
     * 模版描述
     */
    private String description;
    /**
     * 模版路径
     */
    private String src;
    /**
     * 输出路径
     */
    private OutputStream target;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public OutputStream getTarget() {
        return target;
    }

    public void setTarget(OutputStream target) {
        this.target = target;
    }
}
