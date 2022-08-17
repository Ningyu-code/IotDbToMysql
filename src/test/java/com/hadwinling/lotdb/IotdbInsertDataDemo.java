/*
 * Copyright (c) 2021-present HBIS Digital Technology Co.,Ltd. All rights reserved.
 */

package com.hadwinling.lotdb;

import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.Session;
import org.apache.iotdb.session.pool.SessionDataSetWrapper;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.iotdb.tsfile.read.common.Field;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.ws.soap.Addressing;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * 测试
 *
 * @author : ningyu
 * @version : 1.0
 * @date : 2022-06-23 14:32
 */

public class IotdbInsertDataDemo {

    @Autowired
    private Session iotdbSession;


    private static String username="root";

    private static String password="root";

    private static String ip="172.168.111.140";

    private static int port=6667;

    private static int fetchSize=100000;

    private static SessionPool session;

    private static final String ROOT_TEST_DEVICE = "root.haha";

    public static SessionPool getSession(){
        if (session == null) {
            session = new SessionPool(ip, port, username, password, fetchSize);
        }
        return session;
    }

    @Test
    void deleteStorageGroup() throws StatementExecutionException, IoTDBConnectionException {
//        getSession();
        iotdbSession.deleteStorageGroup("root.ling");
        iotdbSession.close();
    }

        /**
         *  插入数据 10000条
         */
        @Test
        void insertRecord () throws StatementExecutionException, IoTDBConnectionException, InterruptedException {
            getSession();
            String tableName = ROOT_TEST_DEVICE;
            int num = 10;
            List<String> keys = new ArrayList<>();
            keys.add("tem");
//            for (int i = 0; i < num; i++) {
//                keys.add(i + "");
//            }
            Random random = new Random();
            List<String> values = new ArrayList<>();
//            for (int j = 0; j < num; j++) {
                values.add(String.valueOf(random.nextDouble()));
//            }
            System.out.println(values);
            long start = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                session.insertRecord(tableName, System.currentTimeMillis(), keys, values);
            }
            long timeMillis = System.currentTimeMillis() - start;
            System.out.println(timeMillis);
            session.close();
        }
    }