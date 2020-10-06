package com.xiaojianhx.demo.db.entity;

import java.io.Serializable;

public class Comment implements Serializable {

    private long id;
    private long bid;
    private String content;
    private long created;
    private long updated;
    private byte del_flg;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBid() {
        return bid;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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