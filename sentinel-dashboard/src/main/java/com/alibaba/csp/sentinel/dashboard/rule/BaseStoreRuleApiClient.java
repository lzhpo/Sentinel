package com.alibaba.csp.sentinel.dashboard.rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author lzhpo
 */
public abstract class BaseStoreRuleApiClient<T> implements StoreRuleApiClient<T> {

    private final Logger logger = LoggerFactory.getLogger(BaseStoreRuleApiClient.class);

    @Override
    public boolean publishReturnBoolean(String app, RuleTypeEnum configType, List<T> rules) {
        try {
            publish(app, configType, rules);
            return true;
        } catch (Exception ex) {
            logger.error("setRules API failed: {}", configType.getValue(), ex);
            return false;
        }
    }
}
