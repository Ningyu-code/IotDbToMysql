package com.hadwinling.lotdb.controller;


import com.alibaba.fastjson.JSONObject;
import com.hadwinling.lotdb.entity.CreateTableAndTabbleName;
import com.hadwinling.lotdb.entity.CustomTable;
import com.hadwinling.lotdb.entity.DeviceData;
import com.hadwinling.lotdb.mapper.CreateTableMapper;
import com.hadwinling.lotdb.service.ICreateTableService;
import com.hadwinling.lotdb.service.IDeviceDataService;
import lombok.var;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


/**
 * @description:
 * @author: hadwinling
 * @time: 2021/2/5 下午9:35
 */
@Controller
public class LotdbController {

    @Autowired
    IDeviceDataService deviceDataService;

    @Autowired
    ICreateTableService iCreateTableService;

    @GetMapping("/result")
    @ResponseBody
    public List<JSONObject> IotDbToMysql() throws IoTDBConnectionException, StatementExecutionException {
        List<JSONObject> deviceData = deviceDataService.IotDbToJsonResult("tem", "root.haha","align by device");
        //根据查到的IOTDB数据定制生成Mysql数据表信息
        CreateTableAndTabbleName customTable = deviceDataService.getCustomTable("tem", "root.haha","align by device");
        //生成表操作
        iCreateTableService.createCustomTable(customTable);
        //向生成表格插入数据
        iCreateTableService.insertData(customTable.getTableName(),deviceData);

        return deviceData;
        }

    @GetMapping("/param")
    @ResponseBody
    public List<JSONObject> IotDbToMysql(@RequestBody JSONObject jsonObject) throws IoTDBConnectionException, StatementExecutionException {
        List<JSONObject> deviceData = deviceDataService.IotDbToJsonResult( jsonObject.getString("deviceName"), jsonObject.getString("devicePath"),jsonObject.getString("whereClause"));
        //根据查到的IOTDB数据定制生成Mysql数据表信息
        CreateTableAndTabbleName customTable = deviceDataService.getCustomTable( jsonObject.getString("deviceName"), jsonObject.getString("devicePath"),jsonObject.getString("whereClause"));
        //生成表操作
        iCreateTableService.createCustomTable(customTable);
        //向生成表格插入数据
        iCreateTableService.insertData(customTable.getTableName(),deviceData);

        return deviceData;
    }

}

