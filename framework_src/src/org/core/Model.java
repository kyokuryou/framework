package org.core;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * 模型基类
 */
public class Model implements Serializable {
    // 主键
    private String pkId;
    // 创建时间
    private Timestamp createDate;
    // 更新时间
    private Timestamp modifyDate;

    private Boolean isDelete;

    // 扩展参数
    private Map<String, Object> paramMaps = new HashMap<String, Object>(0);

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Timestamp modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Map<String, Object> getParamMaps() {
        return paramMaps;
    }

    public void clearParamMaps() {
        paramMaps.clear();
    }

    public void setParamMaps(String key, Object value) {
        if (paramMaps.containsKey(key)) {
            return;
        }
        paramMaps.put(key, value);
    }
}
