package org.smarty.core.test.beans;

import org.smarty.core.beans.Entry;
import org.smarty.core.beans.EntryType;

import java.util.List;

/**
 * Created by kyokuryou on 15-3-16.
 */
public class TestList implements Entry {

    @EntryType(cls = SiteEntry.class)
    private List<SiteEntry> siteList;

    public List<SiteEntry> getSiteList() {
        return siteList;
    }

    public void setSiteList(List<SiteEntry> siteList) {
        this.siteList = siteList;
    }
}
