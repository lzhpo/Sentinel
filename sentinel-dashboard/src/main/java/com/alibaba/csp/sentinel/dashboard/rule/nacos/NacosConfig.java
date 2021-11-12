package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;

/**
 * @author lzhpo
 */
@Configuration
public class NacosConfig {

    @Value("${nacos.address:127.0.0.1:8848}")
    private String address;

    @Value("${nacos.username:nacos}")
    private String username;

    @Value("${nacos.password:nacos}")
    private String password;

    @Value("${nacos.group:SENTINEL_GROUP}")
    private String group;

    @Value("${nacos.namespace:8929a628-6949-4efc-bd5d-c494f18f8aa3}")
    private String namespace;

    @Bean
    public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
        return flowRuleEntities -> JSON.toJSONString(flowRuleEntities, true);
    }

    @Bean
    public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
        return json -> JSON.parseArray(json, FlowRuleEntity.class);
    }

    // ------------------------------------------------

    @Bean
    public Converter<List<AuthorityRuleEntity>, String> authorityRuleEntityEncoder() {
        return authorityRuleEntities -> JSON.toJSONString(authorityRuleEntities, true);
    }

    @Bean
    public Converter<String, List<AuthorityRuleEntity>> authorityRuleEntityDecoder() {
        return json -> JSON.parseArray(json, AuthorityRuleEntity.class);
    }

    // ------------------------------------------------

    @Bean
    public Converter<List<DegradeRuleEntity>, String> degradeRuleEntityEncoder() {
        return degradeRuleEntitys -> JSON.toJSONString(degradeRuleEntitys, true);
    }

    @Bean
    public Converter<String, List<DegradeRuleEntity>> degradeRuleEntityDecoder() {
        return json -> JSON.parseArray(json, DegradeRuleEntity.class);
    }

    // ------------------------------------------------

    @Bean
    public Converter<List<ParamFlowRuleEntity>, String> paramFlowRuleEntityEncoder() {
        return paramFlowRuleEntitys -> JSON.toJSONString(paramFlowRuleEntitys, true);
    }

    @Bean
    public Converter<String, List<ParamFlowRuleEntity>> paramFlowRuleEntityDecoder() {
        return json -> JSON.parseArray(json, ParamFlowRuleEntity.class);
    }

    // ------------------------------------------------

    @Bean
    public Converter<List<SystemRuleEntity>, String> systemRuleEntityEncoder() {
        return systemRuleEntitys -> JSON.toJSONString(systemRuleEntitys, true);
    }

    @Bean
    public Converter<String, List<SystemRuleEntity>> systemRuleEntityDecoder() {
        return json -> JSON.parseArray(json, SystemRuleEntity.class);
    }

    // ------------------------------------------------

    @Bean
    public ConfigService nacosConfigService() throws NacosException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, address);
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
        properties.put(PropertyKeyConst.USERNAME, username);
        properties.put(PropertyKeyConst.PASSWORD, password);
        properties.setProperty("group", group);
        return ConfigFactory.createConfigService(properties);
    }
}
