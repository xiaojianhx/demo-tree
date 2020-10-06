package com.xiaojianhx.demo.db.entity;

import java.io.Serializable;

public class Rlat implements Serializable {

    private long id;
    private long rid;
    private long pid;
    private long bid;
    private int lft;
    private int rght;
    private int son;
    private int child;
    private int level;
    private int seq;
    private long created;
    private long updated;
    private byte del_flg;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getBid() {
        return bid;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }

    public int getLft() {
        return lft;
    }

    public void setLft(int lft) {
        this.lft = lft;
    }

    public int getRght() {
        return rght;
    }

    public void setRght(int rght) {
        this.rght = rght;
    }

    public int getSon() {
        return son;
    }

    public void setSon(int son) {
        this.son = son;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
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