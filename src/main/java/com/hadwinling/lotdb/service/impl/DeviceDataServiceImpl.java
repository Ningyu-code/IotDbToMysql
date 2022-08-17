/*
 * Copyright (c) 2021-present HBIS Digital Technology Co.,Ltd. All rights reserved.
 */

package com.hadwinling.lotdb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hadwinling.lotdb.entity.CreateTableAndTabbleName;
import com.hadwinling.lotdb.entity.CustomTable;
import com.hadwinling.lotdb.entity.DeviceData;
import com.hadwinling.lotdb.mapper.DeviceDataMapper;
import com.hadwinling.lotdb.service.IDeviceDataService;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.SessionDataSet;
import org.apache.iotdb.session.pool.SessionDataSetWrapper;
import org.apache.iotdb.session.pool.SessionPool;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SessionPool iotdbSessionPool;

//    @Override
//    public SessionDataSetWrapper getIotDbDataSet(String deviceName, String devicePath,String whereClause) throws IoTDBConnectionException, StatementExecutionException {
//        String sql="select "+deviceName+" from "+devicePath+" "+whereClause;
//        try {
//            SessionDataSetWrapper dataSet = iotdbSessionPool.executeQueryStatement(sql);
//            dataSet.setBatchSize(1024);
//        }catch (IoTDBConnectionException | StatementExecutionException e) {
//            e.printStackTrace();
//        } finally {
//            // remember to close data set finally!
//            sessionPool.closeResultSet(wrapper);
//        }
//        return dataSet;
//    }

    @Override
    public List<JSONObject> IotDbToJsonResult(String deviceName, String devicePath,String whereClause) throws IoTDBConnectionException, StatementExecutionException {
        String sql="select "+deviceName+" from "+devicePath+" "+whereClause;
        SessionDataSetWrapper dataSet = iotdbSessionPool.executeQueryStatement(sql);
        dataSet.setBatchSize(1024);
        SessionDataSet.DataIterator dataIterator = dataSet.iterator();
        List<JSONObject> result = new ArrayList<>();
        while (dataIterator.next()) {
            JSONObject data = new JSONObject();
            Timestamp time=dataIterator.getTimestamp("Time");
            String value=dataIterator.getString(deviceName);
            String device = dataIterator.getString("Device");
            data.put("time",time);
            data.put(deviceName,value);
            data.put("device",device);
            result.add(data);
        }
        dataSet.close();
        return result;
    }

    @Override
    public CreateTableAndTabbleName getCustomTable( String deviceName, String devicePath,String whereClause) throws IoTDBConnectionException, StatementExecutionException {
        String sql="select "+deviceName+" from "+ devicePath+" "+whereClause;
        SessionDataSetWrapper dataSet = iotdbSessionPool.executeQueryStatement(sql);
        dataSet.setBatchSize(1024);
        CreateTableAndTabbleName createTableAndTabbleName = new CreateTableAndTabbleName();
        String tableNameHavingDot = devicePath+"."+deviceName;
        String tableName = tableNameHavingDot.replace(".","_");
        List<String> columnNames = dataSet.getColumnNames();
        dataSet.close();
        List<CustomTable> customTables = new ArrayList<>();
        for(String columnName:columnNames){
            CustomTable customTable = new CustomTable();
            if (columnName.equals("Time"))
            {
                customTable.setCreateTableFiledName("time");
                customTable.setFieldType("varchar(100)");
            }else if (columnName.equals("Device")){
                customTable.setCreateTableFiledName("timeseries");
                customTable.setFieldType("varchar(100)");
            }else if (columnName.equals(deviceName)){
                customTable.setCreateTableFiledName(deviceName);
                customTable.setFieldType("varchar(100)");
            }
            customTable.setChoose(true);
            customTables.add(customTable);
        }
        createTableAndTabbleName.setTableName(tableName);
        createTableAndTabbleName.setCustomTables(customTables);
        return createTableAndTabbleName;
    }
}
