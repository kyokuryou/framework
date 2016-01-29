package org.smarty.core.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.smarty.core.io.ModelSerializable;

/**
 * @author qul
 * @since LVGG1.1
 */
public class TestModel implements ModelSerializable {

    private long id;
    private String gid;
    private String name;
    private int msgCnt;
    private TestUserModel user = new TestUserModel();
    private Map<String, String> map = new HashMap<String, String>();
    private List<String> list = new ArrayList<String>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMsgCnt() {
        return msgCnt;
    }

    public void setMsgCnt(int msgCnt) {
        this.msgCnt = msgCnt;
    }

    public TestUserModel getUser() {
        return user;
    }

    public void setUser(TestUserModel user) {
        this.user = user;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
