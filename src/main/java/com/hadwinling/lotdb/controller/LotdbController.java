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
    private SessionPool iotdbSessionPool;

    @Autowired
    JdbcTemplate jdbcTemplate;

//    SessionPool sessionPool = new SessionPool("172.168.111.140",6667,"root","root",1000);

    @Autowired
    IDeviceDataService deviceDataService;

    @Autowired
    ICreateTableService iCreateTableService;

    @GetMapping("/result")
    @ResponseBody
    public List<JSONObject> IotDbToMysql() throws IoTDBConnectionException, StatementExecutionException {
        List<JSONObject> deviceData = deviceDataService.IotDbToMySql(iotdbSessionPool, "9", "root.test.device.all align by device");
        //定义表名
        String tablename =deviceData.get(1).getString("timeseries");
        CreateTableAndTabbleName tableInfo = new CreateTableAndTabbleName();
        tableInfo.setTableName(tablename);
        //定义字段数据类型
        List<CustomTable> customTables = deviceDataService.getCustomTables(iotdbSessionPool, "9", "root.test.device.all align by device");
        tableInfo.setCustomTables(customTables);
        iCreateTableService.createCustomTable(tableInfo);
        return deviceData;
        }

}

