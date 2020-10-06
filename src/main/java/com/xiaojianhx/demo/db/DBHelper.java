package com.xiaojianhx.demo.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DB Helper
 * 
 * @author xiaojianhx
 * @version V1.0.0 $ 2020-06-14 14:56:17 ----xiaojianhx
 */
public final class DBHelper {

    private Connection conn;

    public static DBHelper build(String url, String user, String password) throws Exception {
        return new DBHelper(url, user, password);
    }

    public DBHelper(String url, String user, String password) throws Exception {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println(e);
            throw e;
        }
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println(e);
            throw e;
        }

        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println(e);
            throw e;
        }
    }

    public <T> List<T> query(Class<T> clazz, String sql, Object... params) throws Exception {

        var result = new ArrayList<T>();

        try {
            var ps = conn.prepareStatement(sql);

            addParams(ps, params);

            var rs = ps.executeQuery();

            var columnCount = rs.getMetaData().getColumnCount();

            while (rs.next()) {

                var t = (T) Class.forName(clazz.getName()).getDeclaredConstructor().newInstance();

                for (var i = 1; i <= columnCount; i++) {

                    var columnLable = rs.getMetaData().getColumnLabel(i);
                    columnLable = columnLable.substring(0, 1).toUpperCase() + columnLable.substring(1, columnLable.length());

                    var readMethod = t.getClass().getMethod("get" + columnLable);
                    var writeMethod = t.getClass().getMethod("set" + columnLable, readMethod.getReturnType());
                    writeMethod.invoke(t, new Object[] { getValueByType(rs, readMethod.getReturnType().getName(), columnLable) });
                }

                result.add(t);
            }
        } catch (SQLException e) {
            System.err.println(e);
            throw e;
        }

        return result;
    }

    public <T> T get(Class<T> clazz, String sql, Object... params) throws Exception {

        var result = query(clazz, sql, params);

        if (result.isEmpty()) {
            return null;
        }

        if (result.size() > 1) {
            throw new Exception("多余1条数据");
        }

        return result.get(0);
    }

    public int count(String sql, Object... params) throws Exception {

        try {
            var ps = conn.prepareStatement(sql);

            addParams(ps, params);

            var rs = ps.executeQuery();

            var count = 0;
            var idx = 0;

            while (rs.next()) {
                idx++;
                count = rs.getInt(1);
            }

            if (idx > 1) {
                throw new Exception("有" + idx + "条数据");
            }

            return count;
        } catch (SQLException e) {
            System.err.println(e);
            throw e;
        }
    }

    private Object getValueByType(ResultSet rs, String s, String s1) throws Exception {

        Class[] clazz = { java.lang.String.class };

        var type = TypeMappings.mapping.get(s);

        if (type == null) {
            return null;
        }

        try {
            return ResultSet.class.getDeclaredMethod(type, clazz).invoke(rs, new Object[] { s1 });
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public int executeUpdate(String sql, Object... params) throws Exception {

        var count = 0;

        try {
            var ps = conn.prepareStatement(sql);

            addParams(ps, params);

            count = ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.err.println(e);
            throw e;
        }

        return count;
    }

    public long insert(String sql, Object... params) throws Exception {

        try {
            var ps = conn.prepareStatement(sql);

            addParams(ps, params);
            ps.executeUpdate();

            var get = get(Insert.class, "SELECT LAST_INSERT_ID() AS id");
            conn.commit();
            return get.getId();
        } catch (SQLException e) {
            conn.rollback();
            System.err.println(e);
            throw e;
        }
    }

    private void addParams(PreparedStatement ps, Object... params) throws Exception {

        var idx = 1;

        for (var obj : params) {

            if (obj == null) {
                ps.setObject(idx++, null);

            } else if (obj instanceof Integer) {
                ps.setInt(idx++, (Integer) obj);

            } else if (obj instanceof Long) {
                ps.setLong(idx++, (Long) obj);

            } else if (obj instanceof String) {
                ps.setString(idx++, String.valueOf(obj));

            } else if (obj instanceof Timestamp) {
                ps.setTimestamp(idx++, (Timestamp) obj);

            } else if (obj instanceof Date) {
                ps.setDate(idx++, new java.sql.Date(((Date) obj).getTime()));

            } else if (obj instanceof BigDecimal) {
                ps.setBigDecimal(idx++, (BigDecimal) obj);

            } else if (obj instanceof Byte) {
                ps.setByte(idx++, (byte) obj);

            } else {
                throw new Exception("类型待补充" + obj.getClass());
            }
        }
    }

    public void close() throws Exception {

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println(e);
                throw e;
            }
        }
    }
}