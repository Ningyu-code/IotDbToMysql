/*
 * Copyright (c) 2021-present HBIS Digital Technology Co.,Ltd. All rights reserved.
 */
package com.hadwinling.lotdb.service;

import com.alibaba.fastjson.JSONObject;
import com.hadwinling.lotdb.entity.CreateTableAndTabbleName;

import java.util.List;

/**
 * @author : ningyu
 * @version : 1.0
 * @date : 2022-08-15 16:05
 */
public interface ICreateTableService {

    void createCustomTable(CreateTableAndTabbleName createTableAndTabbleName);

    void insertData(String tableName,List<JSONObject> jsonObjectList);
}
