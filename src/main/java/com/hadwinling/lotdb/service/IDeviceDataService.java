/*
 * Copyright (c) 2021-present HBIS Digital Technology Co.,Ltd. All rights reserved.
 */
package com.hadwinling.lotdb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hadwinling.lotdb.entity.DeviceData;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionPool;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备节点数据服务类
 *
 * @author : ningyu
 * @version : 1.0
 * @date : 2022-08-09 14:05
 */
public interface IDeviceDataService extends IService<DeviceData> {

    List<DeviceData> IotDbToMySql(SessionPool sessionPool, String deviceName, String DevicePath) throws IoTDBConnectionException, StatementExecutionException;

    }
