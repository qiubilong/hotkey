package com.jd.platform.sample.config;

import com.jd.platform.hotkey.client.ClientStarter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author wuweifeng wrote on 2020-01-14
 * @version 1.0
 */
@Component
public class HotKeyClientStarter {
    @Value("${etcd.server}")
    private String etcd;
    @Value("${spring.application.name}")
    private String appName;

    @PostConstruct
    public void init() {
        ClientStarter.Builder builder = new ClientStarter.Builder();
        // 注意，setAppName很重要，它和dashboard中相关规则是关联的。
        ClientStarter starter = builder.setAppName("myApp").setEtcdServer(etcd).build();
        starter.startPipeline();
    }

    public static void main(String[] args) {
        Map<String, HashSet<String>> totalSkuSet = new HashMap<>();
        System.out.println(totalSkuSet.get("a"));
    }

}
