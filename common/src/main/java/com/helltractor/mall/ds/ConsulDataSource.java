package com.helltractor.mall.ds;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;

@Configuration
public class ConsulDataSource {
    
    private static final String KEY = "TestResource";
    // consul host
    private static final String host = "localhost";
    // consul port
    private static final int port = 8500;
    // consul rule key
    private static final String ruleKey = "sentinel-flow";
    // consul timeout
    private static final int waitTimeoutInSecond = 10;
    // if change to true, should be config CONSUL_NAMESPACE_ID
    private static boolean isDemoNamespace = false;
    // fill your namespace id,if you want to use namespace. for example: 0f5c7314-4983-4022-ad5a-347de1d1057d,you can get it on consul's console
    private static final String CONSUL_NAMESPACE_ID = "${namespace}";
    
    public static void main(String[] args) {
        if (isDemoNamespace) {
            loadMyNamespaceRules();
        } else {
            loadRules();
        }
    }
    
    private static void loadRules() {
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new com.alibaba.csp.sentinel.datasource.consul.ConsulDataSource<>(host, port, ruleKey, waitTimeoutInSecond,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                }));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }
    
    private static void loadMyNamespaceRules() {
        Properties properties = new Properties();
        
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new com.alibaba.csp.sentinel.datasource.consul.ConsulDataSource<>(host, port, ruleKey, waitTimeoutInSecond,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                }));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }
}
