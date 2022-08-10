package com.hadwinling.lotdb.controller;


import com.hadwinling.lotdb.entity.DeviceData;
import com.hadwinling.lotdb.service.IDeviceDataService;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

//    SessionPool sessionPool = new SessionPool("172.168.111.140",6667,"root","root",1000);

    @Autowired
    IDeviceDataService deviceDataService;

    @GetMapping("/result")
    @ResponseBody
    public void IotDbToMysql() throws IoTDBConnectionException, StatementExecutionException {
        List<DeviceData> deviceData= deviceDataService.IotDbToMySql(iotdbSessionPool,"1","root.test.device.all align by device");
        deviceDataService.saveBatch(deviceData);
    }


//    @GetMapping("/")
//    @ResponseBody
//    public String setStorageGroup(){
//        iotdbService.setStorageGroup("root.ning");
//        return  "over";
//    }

}

