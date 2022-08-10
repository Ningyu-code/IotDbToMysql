package com.hadwinling.lotdb.config;

import lombok.Data;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.session.Session;
import org.apache.iotdb.session.pool.SessionPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: ningyu
 * @time: 2022/8/10 下午2:08
 */
@Configuration
public class LOTDBConfig {

    @Value("${iotdb.sessionPool.ip}")
    private String hort;

    @Value("${iotdb.sessionPool.port}")
    private int port;

    @Value("${iotdb.sessionPool.user}")
    private String username;

    @Value("${iotdb.sessionPool.password}")
    private String password;

    private static SessionPool sessionPool;

    @Bean
    public SessionPool iotdbSessionPool() {
        if (sessionPool == null) {
            sessionPool = new SessionPool(hort, port, username, password, 1000);
        }
        return sessionPool;
    }


}
