package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * @author lzhpo
 */
public enum RuleTypeEnum {

    /**
     * 流控规则
     */
    FLOW("flow-rules", FlowRuleEntity.class),

    /**
     * 热点规则
     */
    PARAM_FLOW("param-rules", ParamFlowRuleEntity.class),

    /**
     * 授权规则
     */
    AUTHORITY("authority-rules", AuthorityRuleEntity.class),

    /**
     * 熔断规则（网关也通用）
     */
    DEGRADE("degrade-rules", DegradeRuleEntity.class),

    /**
     * 系统规则（网关也通用）
     */
    SYSTEM("system-rules", SystemRuleEntity.class),

    /**
     * 网关API管理
     */
    GATEWAY_DEFINITION("gateway-api-definition-rules", ApiDefinitionEntity.class),

    /**
     * 网关流控规则
     */
    GATEWAY_FLOW("gateway-flow-rules", GatewayFlowRuleEntity.class);

    private final String value;
    private final Class<?> clazz;

    RuleTypeEnum(String value, Class<?> clazz) {
        this.value = value;
        this.clazz = clazz;
    }

    public String getValue() {
        return this.value;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    public static RuleTypeEnum match(Class<?> clazz) {
        Assert.notNull(clazz, "Rule type class cannot null");
        return Arrays.stream(RuleTypeEnum.values())
                .filter(x -> clazz.isAssignableFrom(x.getClazz()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Illegal rule type: " + clazz));
    }
}