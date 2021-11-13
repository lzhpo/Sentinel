package com.alibaba.csp.sentinel.dashboard.controller;

import com.alibaba.csp.sentinel.dashboard.repository.rule.RuleRepository;
import com.alibaba.csp.sentinel.dashboard.rule.DatasourceRuleProps;
import com.alibaba.csp.sentinel.dashboard.rule.RuleTypeEnum;
import com.alibaba.csp.sentinel.dashboard.rule.SpringContextHolder;
import com.alibaba.csp.sentinel.dashboard.rule.StoreRuleApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ResolvableType;

import java.util.List;
import java.util.Optional;

/**
 * @author lzhpo
 */
public interface BaseRulesController<T, S> {

    Logger logger = LoggerFactory.getLogger(BaseRulesController.class);

    /**
     * Whether to use memory-based storage rules
     *
     * @return boolean
     */
    default boolean isUseMemoryRule() {
        return SpringContextHolder.getBean(DatasourceRuleProps.class).isUseMemoryRule();
    }

    /**
     * Get the generic type on the interface and parse out {@link RuleTypeEnum}
     *
     * @return {@link RuleTypeEnum}
     */
    default RuleTypeEnum getRuleTypeEnum() {
        return Optional.of(ResolvableType.forClass(getClass()))
                .map(ResolvableType::getInterfaces)
                .map(x -> x[0])
                .map(ResolvableType::getGenerics)
                .map(x -> x[0])
                .map(ResolvableType::resolve)
                .map(RuleTypeEnum::match)
                .orElseThrow(() -> new IllegalArgumentException("Unable to get rule type"));
    }

    /**
     * Publish rules to configuration center
     *
     * @param repository {@link RuleRepository}
     * @param apiClient  {@link StoreRuleApiClient}
     * @param app        app
     * @return boolean
     */
    default boolean publishRules(RuleRepository<T, S> repository, StoreRuleApiClient<T> apiClient, String app) {
        List<T> rules = repository.findAllByApp(app);
        try {
            return apiClient.publish(app, getRuleTypeEnum(), rules);
        } catch (Exception e) {
            logger.error("publishRules error", e);
        }

        return false;
    }
}
