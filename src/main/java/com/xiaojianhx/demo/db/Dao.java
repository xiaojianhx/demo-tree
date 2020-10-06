package com.xiaojianhx.demo.db;

import com.xiaojianhx.demo.db.entity.Rlat;

public class Dao {

    private static final String INSERT_FOR_RLAT = """
            INSERT INTO rlats (
                RID, PID, BID, LFT, RGHT, SON, CHILD, LEVEL, SEQ, CREATED, UPDATED, DEL_FLG
            ) VALUE (
                ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
            )
            """;

    public void truncate(DBHelper helper) throws Exception {
        helper.executeUpdate("TRUNCATE comments");
        helper.executeUpdate("TRUNCATE rlats");
    }

    public long insertComment(DBHelper helper, long bookId, String content) throws Exception {

        var now = System.currentTimeMillis();
        var sql = """
                INSERT INTO comments (
                    BID, CONTENT, CREATED, UPDATED, DEL_FLG
                ) VALUE (
                    ?, ?, ?, ?, ?
                )
                """;
        return helper.insert(sql, bookId, content, now, now, 0);
    }

    public long insertRlatForRoot(DBHelper helper, long id) throws Exception {
        var now = System.currentTimeMillis();
        return helper.insert(INSERT_FOR_RLAT, id, 0, 0, 1, 2, 0, 0, 0, 0, now, now, 0);
    }

    public long insertRlat(DBHelper helper, Rlat rlat) throws Exception {
        return helper.insert(INSERT_FOR_RLAT, rlat.getRid(), rlat.getPid(), rlat.getBid(), rlat.getLft(), rlat.getRght(), rlat.getSon(),
                rlat.getChild(), rlat.getLevel(), rlat.getSeq(), rlat.getCreated(), rlat.getUpdated(), rlat.getDel_flg());
    }

    public int deleteComment(DBHelper helper, long id) throws Exception {
        return helper.executeUpdate("DELETE FROM comments WHERE ID = ?", id);
    }

    public int deleteRalt(DBHelper helper, long id) throws Exception {
        return helper.executeUpdate("DELETE FROM rlats WHERE ID = ?", id);
    }

    public int updateRlatAncestorForAddNode(DBHelper helper, long rid, int lft) throws Exception {
        var sql = """
                UPDATE rlats SET
                    RGHT = RGHT + 2,
                    CHILD = CHILD + 1
                WHERE RID = ?
                AND LFT < ?
                AND RGHT >= ?
                """;
        return helper.executeUpdate(sql, rid, lft, lft);
    }

    public int updateRlatParentForAddNode(DBHelper helper, long rid, int lft, int rght, int level) throws Exception {
        var sql = """
                UPDATE rlats SET
                    SON = SON + 1
                WHERE RID = ?
                AND LFT < ?
                AND RGHT > ?
                AND LEVEL = ?
                """;
        return helper.executeUpdate(sql, rid, lft, rght, level);
    }

    public int updateRlatRghtForAddNode(DBHelper helper, long rid, int rght) throws Exception {
        var sql = """
                UPDATE rlats SET
                    LFT = LFT + 2,
                    RGHT = RGHT + 2
                WHERE RID = ?
                AND LFT >= ?
                """;
        return helper.executeUpdate(sql, rid, rght);
    }

    public int updateRlatAncestorForDelNode(DBHelper helper, long rid, int lft, int rght) throws Exception {
        var sql = """
                UPDATE rlats SET
                    RGHT = RGHT - 2,
                    CHILD = CHILD - 1
                WHERE RID = ?
                AND LFT < ?
                AND RGHT > ?
                """;
        return helper.executeUpdate(sql, rid, lft, lft);
    }

    public int updateRlatParentForDelNode(DBHelper helper, long rid, int lft, int rght, int level) throws Exception {
        var sql = """
                UPDATE rlats SET
                    SON = SON - 1
                WHERE RID = ?
                AND LFT < ?
                AND RGHT > ?
                AND LEVEL = ?
                """;
        return helper.executeUpdate(sql, rid, lft, rght, level);
    }

    public int updateRlatRghtForDelNode(DBHelper helper, long rid, int rght) throws Exception {
        var sql = """
                UPDATE rlats SET
                    LFT = LFT - 2,
                    RGHT = RGHT - 2
                WHERE RID = ?
                AND LFT > ?
                """;
        return helper.executeUpdate(sql, rid, rght);
    }

    public Rlat getRlatRootByRid(DBHelper helper, long rid) throws Exception {
        return helper.get(Rlat.class, "SELECT * FROM rlats WHERE RID = ? AND PID = 0 AND BID = 0", rid);
    }

    public Rlat getRaltById(DBHelper helper, long id) throws Exception {
        return helper.get(Rlat.class, "SELECT * FROM rlats WHERE ID = ?", id);
    }

    public Rlat getRlatByBid(DBHelper helper, long bid) throws Exception {
        return helper.get(Rlat.class, "SELECT * FROM rlats WHERE BID = ?", bid);
    }
}