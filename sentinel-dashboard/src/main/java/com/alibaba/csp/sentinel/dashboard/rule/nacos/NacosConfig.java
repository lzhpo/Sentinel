package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.rule.DatasourceRuleProps;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Properties;

/**
 * @author lzhpo
 */
@Configuration
public class NacosConfig {

    private final DatasourceRuleProps ruleProps;

    public NacosConfig(DatasourceRuleProps ruleProps) {
        this.ruleProps = ruleProps;
    }

    @Bean
    public ConfigService nacosConfigService() throws NacosException {
        final DatasourceNacosProps nacosProps = ruleProps.getNacos();

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, nacosProps.getServerAddress());
        properties.put(PropertyKeyConst.USERNAME, nacosProps.getUsername());
        properties.put(PropertyKeyConst.PASSWORD, nacosProps.getPassword());

        final String namespace = nacosProps.getNamespace();
        if (StringUtils.hasText(namespace)) {
            properties.put(PropertyKeyConst.NAMESPACE, namespace);
        }

        final String groupId = nacosProps.getGroup();
        if (StringUtils.hasText(groupId)) {
            properties.setProperty("group", groupId);
        }

        return ConfigFactory.createConfigService(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    @SuppressWarnings({"rawtypes"})
    public NacosStoreRuleApiClient nacosStoreRuleApiClient() {
        return new NacosStoreRuleApiClient();
    }
}
