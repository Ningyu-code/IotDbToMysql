/*
 * Copyright (c) 2021-present HBIS Digital Technology Co.,Ltd. All rights reserved.
 */

package com.hadwinling.lotdb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hadwinling.lotdb.entity.CustomTable;
import com.hadwinling.lotdb.entity.DeviceData;
import com.hadwinling.lotdb.mapper.DeviceDataMapper;
import com.hadwinling.lotdb.service.IDeviceDataService;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.SessionDataSet;
import org.apache.iotdb.session.pool.SessionDataSetWrapper;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author : ningyu
 * @version : 1.0
 * @date : 2022-08-09 14:09
 */
@Service
public class DeviceDataServiceImpl extends ServiceImpl<DeviceDataMapper, DeviceData> implements IDeviceDataService {


    @Override
    public List<JSONObject> IotDbToMySql(SessionPool sessionPool, String deviceName, String DevicePath) throws IoTDBConnectionException, StatementExecutionException {
        String sql="select "+deviceName+" from "+DevicePath;
        SessionDataSetWrapper dataSet = sessionPool.executeQueryStatement(sql);
        dataSet.setBatchSize(1024);
        SessionDataSet.DataIterator dataIterator = dataSet.iterator();
        List<String> columnNames = dataSet.getColumnNames();
//        List<TSDataType> columnTypes = dataSet.getColumnTypes();
        System.out.println(columnNames);
//        System.out.println(columnTypes);
//        JSONObject lastResult = new JSONObject();
//        Map<String,Object> result = new HashMap<String,Object>();
        List<JSONObject> result = new ArrayList<>();
        JSONObject jasoncolumn = JSONObject.parseObject(JSONObject.toJSONString(columnNames));
        while (dataIterator.next()) {
            JSONObject data = new JSONObject();
            Timestamp time=dataIterator.getTimestamp("Time");
            String value=dataIterator.getString(deviceName);
            String timeseries = dataIterator.getString("Device");
            data.put("time",time);
            data.put("value",value);
            data.put("timeseries",timeseries+"."+deviceName);
            result.add(data);
        }
        dataSet.close();
        return result;
    }

    @Override
    public List<CustomTable> getCustomTables(SessionPool sessionPool, String deviceName, String DevicePath) throws IoTDBConnectionException, StatementExecutionException {
        String sql="select "+deviceName+" from "+DevicePath;
        SessionDataSetWrapper dataSet = sessionPool.executeQueryStatement(sql);
        dataSet.setBatchSize(1024);
        SessionDataSet.DataIterator dataIterator = dataSet.iterator();
        List<String> columnNames = dataSet.getColumnNames();
        List<CustomTable> customTables = new ArrayList<>();
        for(String columnName:columnNames){
            CustomTable customTable = new CustomTable();
            if (columnName.equals("Time"))
            {
                customTable.setCreateTableFiledName("time");
                customTable.setFieldType("timestamp");
            }else if (columnName.equals("Device")){
                customTable.setCreateTableFiledName("timeseries");
                customTable.setFieldType("varchar(100)");
            }else if (columnName.equals(deviceName)){
                customTable.setCreateTableFiledName(deviceName);
                customTable.setFieldType("varchar(100)");
            }
            customTables.add(customTable);
        }
        return customTables;
    }
}
