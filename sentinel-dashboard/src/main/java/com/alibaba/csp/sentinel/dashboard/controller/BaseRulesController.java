package com.alibaba.csp.sentinel.dashboard.controller;

import com.alibaba.csp.sentinel.dashboard.repository.rule.RuleRepository;
import com.alibaba.csp.sentinel.dashboard.rule.RuleTypeEnum;
import com.alibaba.csp.sentinel.dashboard.rule.DatasourceRuleProps;
import com.alibaba.csp.sentinel.dashboard.rule.SpringContextHolder;
import com.alibaba.csp.sentinel.dashboard.rule.StoreRuleApiClient;
import org.springframework.core.ResolvableType;

import java.util.List;
import java.util.Optional;

/**
 * @author lzhpo
 */
public interface BaseRulesController<T, S> {

    default boolean isUseMemoryRule() {
        return SpringContextHolder.getBean(DatasourceRuleProps.class).isUseMemoryRule();
    }

    default RuleTypeEnum getRuleConfigTypeEnum() {
        return Optional.of(ResolvableType.forClass(getClass()))
                .map(ResolvableType::getInterfaces)
                .map(x -> x[0])
                .map(ResolvableType::getGenerics)
                .map(x -> x[0])
                .map(ResolvableType::resolve)
                .map(RuleTypeEnum::match)
                .orElseThrow(() -> new IllegalArgumentException("Unable to get rule type"));
    }

    default boolean publishRules(RuleRepository<T, S> repository, StoreRuleApiClient<T> apiClient, String app) {
        List<T> rules = repository.findAllByApp(app);
        return apiClient.publishReturnBoolean(app, getRuleConfigTypeEnum(), rules);
    }
}
