package org.smarty.core.test.beans;

import org.smarty.core.beans.Entry;

/**
 * Created by kyokuryou on 15-3-16.
 */
public class SiteEntry implements Entry {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
