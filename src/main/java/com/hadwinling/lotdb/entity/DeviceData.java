/*
 * Copyright (c) 2021-present HBIS Digital Technology Co.,Ltd. All rights reserved.
 */

package com.hadwinling.lotdb.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * 设备数据表
 *
 * @author : ningyu
 * @version : 1.0
 * @date : 2022-08-09 10:38
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_data")
public class DeviceData extends Model<DeviceData> {

    private Timestamp time;

    private String device;

    private String value;
}
