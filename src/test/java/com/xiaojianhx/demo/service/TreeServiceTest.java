package com.xiaojianhx.demo.service;

import org.junit.Test;

public class TreeServiceTest {

//    @Test
    public void testSave() {

        var service = new TreeService();

        try {
            service.truncate();
            service.save(1, 0, "飞狐外传不错1");
            service.save(1, 0, "飞狐外传不错2");
            service.save(1, 0, "飞狐外传不错3");
            service.save(1, 1, "飞狐外传不错11");
            service.save(1, 2, "飞狐外传不错21");
            service.save(1, 5, "飞狐外传不错211");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDel0() {

        var service = new TreeService();

        try {
            service.del(6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}