package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.rule.BaseStoreRuleApiClient;
import com.alibaba.csp.sentinel.dashboard.rule.DatasourceRuleProps;
import com.alibaba.csp.sentinel.dashboard.rule.RuleTypeEnum;
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
public class NacosStoreRuleApiClient<T> extends BaseStoreRuleApiClient<T> {

    @Autowired
    private ConfigService configService;

    @Autowired
    private DatasourceRuleProps datasourceRuleProps;

    @Override
    @SuppressWarnings({"unchecked"})
    public List<T> fetch(String app, RuleTypeEnum configType) throws Exception {
        String ruleName = this.getRuleConfigId(app, configType);
        String rulesJson = configService.getConfig(ruleName, datasourceRuleProps.getNacos().getGroup(), 3000);
        final Class<T> clazz = (Class<T>) configType.getClazz();
        return !StringUtil.isEmpty(rulesJson) ? JSON.parseArray(rulesJson, clazz) : Collections.emptyList();
    }

    @Override
    public void publish(String app, RuleTypeEnum configType, List<T> rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");
        if (rules != null) {
            String ruleName = this.getRuleConfigId(app, configType);
            String groupId = datasourceRuleProps.getNacos().getGroup();
            String rulesJson = JSON.toJSONString(rules, true);
            configService.publishConfig(ruleName, groupId, rulesJson);
        }
    }

    private String getRuleConfigId(String appName, RuleTypeEnum ruleFix) {
        return String.format("%s-%s", appName, ruleFix.getValue());
    }
}

