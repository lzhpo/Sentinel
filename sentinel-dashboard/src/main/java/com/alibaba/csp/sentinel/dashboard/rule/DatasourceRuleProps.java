package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.csp.sentinel.dashboard.rule.nacos.DatasourceNacosProps;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

/**
 * @author lzhpo
 */
@Configuration
@ConfigurationProperties(prefix = "datasource")
public class DatasourceRuleProps {

    private boolean useMemoryRule;

    private DatasourceNacosProps nacos;

    public boolean isUseMemoryRule() {
        return useMemoryRule;
    }

    public void setUseMemoryRule(boolean useMemoryRule) {
        this.useMemoryRule = useMemoryRule;
    }

    public DatasourceNacosProps getNacos() {
        return nacos;
    }

    public void setNacos(DatasourceNacosProps nacos) {
        this.nacos = !ObjectUtils.isEmpty(nacos) ? nacos : new DatasourceNacosProps();
    }
}
