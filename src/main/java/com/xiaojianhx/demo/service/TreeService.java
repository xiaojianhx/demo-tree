package com.xiaojianhx.demo.service;

import com.xiaojianhx.demo.db.DBHelper;
import com.xiaojianhx.demo.db.Dao;
import com.xiaojianhx.demo.db.entity.Rlat;

public class TreeService {

    private static final String URL = "jdbc:mysql://192.168.1.80:3306/tree?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public void truncate() throws Exception {
        var dao = new Dao();

        var helper = DBHelper.build(URL, USER, PASSWORD);
        dao.truncate(helper);
    }

    public void save(long bookId, long pid, String content) throws Exception {

        var now = System.currentTimeMillis();

        var dao = new Dao();

        var helper = DBHelper.build(URL, USER, PASSWORD);

        var bid = dao.insertComment(helper, bookId, content);

        var root = dao.getRlatRootByRid(helper, bookId);

        if (root == null) {
            root = dao.getRaltById(helper, dao.insertRlatForRoot(helper, bookId));
        }

        var parent = dao.getRlatByBid(helper, pid);

        if (parent == null) {
            parent = root;
        }

        var rlat = new Rlat();
        rlat.setRid(bookId);

        if (pid == 0) {
            rlat.setPid(bid);
        } else {
            rlat.setPid(pid);
        }

        var lft = parent.getRght();
        var rght = lft + 1;

        rlat.setBid(bid);
        rlat.setLft(lft);
        rlat.setRght(rght);
        rlat.setSon(0);
        rlat.setChild(0);
        rlat.setLevel(parent.getLevel() + 1);
        rlat.setCreated(now);
        rlat.setUpdated(now);
        rlat.setDel_flg((byte) 0);

        dao.insertRlat(helper, rlat);

        dao.updateRlatAncestorForAddNode(helper, bookId, lft);
        dao.updateRlatParentForAddNode(helper, bookId, lft, rght, parent.getLevel());
        dao.updateRlatRghtForAddNode(helper, bookId, rght);
    }

    public void del(long id) throws Exception {

        var dao = new Dao();

        var helper = DBHelper.build(URL, USER, PASSWORD);

        dao.deleteComment(helper, id);

        var rlat = dao.getRlatByBid(helper, id);

        dao.updateRlatAncestorForDelNode(helper, rlat.getRid(), rlat.getLft(), rlat.getRght());
        dao.updateRlatParentForDelNode(helper, rlat.getRid(), rlat.getLft(), rlat.getRght(), rlat.getLevel() - 1);
        dao.updateRlatRghtForDelNode(helper, rlat.getRid(), rlat.getRght());

        dao.deleteRalt(helper, rlat.getId());
    }
}