package com.xiaojianhx.demo.db;

import java.util.HashMap;
import java.util.Map;

/**
 * Type Mappings
 * 
 * @author xiaojianhx
 * @version V1.0.0 $ 2020-06-14 15:01:16 ----xiaojianhx
 */
public final class TypeMappings {

    public static final Map<String, String> mapping;

    static {

        mapping = new HashMap<String, String>();
        mapping.put("byte", "getByte");
        mapping.put("short", "getShort");
        mapping.put("int", "getInt");
        mapping.put("long", "getLong");
        mapping.put("float", "getFloat");
        mapping.put("double", "getDouble");
        mapping.put("boolean", "getBoolean");
        mapping.put("java.lang.Byte", "getByte");
        mapping.put("java.lang.Integer", "getInt");
        mapping.put("java.lang.String", "getString");
        mapping.put("java.sql.Timestamp", "getTimestamp");
        mapping.put("java.sql.Time", "getTime");
        mapping.put("java.sql.Date", "getDate");
        mapping.put("java.util.Date", "getDate");
        mapping.put("[B", "getObject");
        mapping.put("java.sql.Blob", "getBytes");
        mapping.put("java.sql.Clob", "getClob");
        mapping.put("java.math.BigDecimal", "getBigDecimal");
        mapping.put("java.sql.Date", "getDate");
        mapping.put("java.util.Date", "getDate");
    }
}