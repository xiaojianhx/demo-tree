package com.xiaojianhx.demo.db.entity;

import java.io.Serializable;

public class Book implements Serializable {

    private long id;
    private String name;
    private long created;
    private long updated;
    private byte del_flg;

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

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public byte getDel_flg() {
        return del_flg;
    }

    public void setDel_flg(byte del_flg) {
        this.del_flg = del_flg;
    }
}