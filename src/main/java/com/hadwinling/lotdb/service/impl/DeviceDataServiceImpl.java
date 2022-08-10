/*
 * Copyright (c) 2021-present HBIS Digital Technology Co.,Ltd. All rights reserved.
 */

package com.hadwinling.lotdb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : ningyu
 * @version : 1.0
 * @date : 2022-08-09 14:09
 */
@Service
public class DeviceDataServiceImpl extends ServiceImpl<DeviceDataMapper, DeviceData> implements IDeviceDataService {


    @Override
    public List<DeviceData> IotDbToMySql(SessionPool sessionPool, String deviceName, String DevicePath) throws IoTDBConnectionException, StatementExecutionException {
        String sql="select "+deviceName+" from "+DevicePath;
        SessionDataSetWrapper dataSet = sessionPool.executeQueryStatement(sql);
        dataSet.setBatchSize(1024);
        SessionDataSet.DataIterator dataIterator = dataSet.iterator();
        List<DeviceData> deviceData=new ArrayList<>();
        while (dataIterator.next()) {
            DeviceData deviceData1 = new DeviceData();
            Timestamp time=dataIterator.getTimestamp("Time");
            String value=dataIterator.getString(deviceName);
            String timeseries = dataIterator.getString("Device");
            deviceData1.setDevice(timeseries);
            deviceData1.setTime(time);
            deviceData1.setValue(value);
            deviceData.add(deviceData1);
        }
        dataSet.close();
        return deviceData;
    }
}
