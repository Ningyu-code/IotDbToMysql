/*
 * Copyright (c) 2021-present HBIS Digital Technology Co.,Ltd. All rights reserved.
 */
package com.hadwinling.lotdb.mapper;

import com.hadwinling.lotdb.entity.CreateTableAndTabbleName;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : ningyu
 * @version : 1.0
 * @date : 2022-08-15 16:07
 */
@Mapper
public interface CreateTableMapper {

    void createCustomTable(CreateTableAndTabbleName createTableAndTabbleName);
}
