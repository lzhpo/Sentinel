package com.alibaba.csp.sentinel.dashboard.rule;

import java.util.List;

/**
 * @author lzhpo
 */
public interface StoreRuleApiClient<T> {

    List<T> fetch(String app, RuleTypeEnum configType) throws Exception;

    void publish(String app, RuleTypeEnum configType, List<T> rules) throws Exception;

    boolean publishReturnBoolean(String app, RuleTypeEnum configType, List<T> rules);
}
