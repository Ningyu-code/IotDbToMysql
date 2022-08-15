/*
 * Copyright (c) 2021-present HBIS Digital Technology Co.,Ltd. All rights reserved.
 */

package com.hadwinling.lotdb.entity;

import java.util.List;

/**
 * 创表实体
 *
 * @author : ningyu
 * @version : 1.0
 * @date : 2022-08-15 15:56
 */

public class CreateTableAndTabbleName {
    private String tableName;
    private List<CustomTable> customTables;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<CustomTable> getCustomTables() {
        return customTables;
    }

    public void setCustomTables(List<CustomTable> customTables) {
        this.customTables = customTables;
    }
}
