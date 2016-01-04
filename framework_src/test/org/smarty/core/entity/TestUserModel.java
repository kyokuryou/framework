package org.smarty.core.entity;

import org.smarty.core.io.ModelSerializable;

/**
 * @author qul
 * @since LVGG1.1
 */
public class TestUserModel implements ModelSerializable {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
