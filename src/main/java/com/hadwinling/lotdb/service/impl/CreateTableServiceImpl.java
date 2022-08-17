/*
 * Copyright (c) 2021-present HBIS Digital Technology Co.,Ltd. All rights reserved.
 */

package com.hadwinling.lotdb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hadwinling.lotdb.entity.CreateTableAndTabbleName;
import com.hadwinling.lotdb.mapper.CreateTableMapper;
import com.hadwinling.lotdb.service.ICreateTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现类
 *
 * @author : ningyu
 * @version : 1.0
 * @date : 2022-08-15 16:05
 */
@Service
public class CreateTableServiceImpl implements ICreateTableService {

    @Autowired
    CreateTableMapper createTableMapper;

    public void createCustomTable(CreateTableAndTabbleName createTableAndTabbleName){
        createTableMapper.createCustomTable(createTableAndTabbleName);
    }

    @Override
    public void insertData(String tableName,List<JSONObject> jsonObjectList) {
        createTableMapper.insertData(tableName,jsonObjectList);
    }
}
