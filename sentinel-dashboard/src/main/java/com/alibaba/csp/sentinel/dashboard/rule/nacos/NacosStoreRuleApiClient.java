package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.rule.DatasourceRuleProps;
import com.alibaba.csp.sentinel.dashboard.rule.RuleTypeEnum;
import com.alibaba.csp.sentinel.dashboard.rule.StoreRuleApiClient;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * @author lzhpo
 */
public class NacosStoreRuleApiClient<T> implements StoreRuleApiClient<T> {

    @Autowired
    private ConfigService configService;

    @Autowired
    private DatasourceRuleProps datasourceRuleProps;

    @Override
    @SuppressWarnings({"unchecked"})
    public List<T> fetch(String app, RuleTypeEnum configType) throws Exception {
        String ruleName = this.getRuleConfigId(app, configType);
        String rulesJson = configService.getConfig(ruleName, datasourceRuleProps.getNacos().getGroupId(), 3000);
        final Class<T> clazz = (Class<T>) configType.getClazz();
        return !StringUtil.isEmpty(rulesJson) ? JSON.parseArray(rulesJson, clazz) : Collections.emptyList();
    }

    @Override
    public boolean publish(String app, RuleTypeEnum configType, List<T> rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");
        if (rules != null) {
            String ruleName = this.getRuleConfigId(app, configType);
            String groupId = datasourceRuleProps.getNacos().getGroupId();
            String rulesJson = JSON.toJSONString(rules, true);
            // 当前版本 1.3.0 是不能设置文件类型的
            // 1.4.1修复了这个问题：https://github.com/alibaba/nacos/releases/tag/1.4.1
            return configService.publishConfig(ruleName, groupId, rulesJson, "json");
        }

        return false;
    }

    private String getRuleConfigId(String appName, RuleTypeEnum ruleFix) {
        return String.format("%s-%s", appName, ruleFix.getValue());
    }
}

