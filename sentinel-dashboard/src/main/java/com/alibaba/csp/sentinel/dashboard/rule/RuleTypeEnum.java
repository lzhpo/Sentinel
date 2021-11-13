package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayParamFlowItemEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * @author lzhpo
 */
public enum RuleTypeEnum {

    /**
     * {@link FlowRuleEntity}
     */
    FLOW("flow-rules", FlowRuleEntity.class),

    /**
     * {@link ParamFlowRuleEntity}
     */
    PARAM_FLOW("param-rules", ParamFlowRuleEntity.class),

    /**
     * {@link AuthorityRuleEntity}
     */
    AUTHORITY("authority-rules", AuthorityRuleEntity.class),

    /**
     * {@link DegradeRuleEntity}
     */
    DEGRADE("degrade-rules", DegradeRuleEntity.class),

    /**
     * {@link SystemRuleEntity}
     */
    SYSTEM("system-rules", SystemRuleEntity.class),

    /**
     * {@link GatewayFlowRuleEntity}
     */
    GATEWAY_FLOW("gateway-flow-rules", GatewayFlowRuleEntity.class),

    /**
     * {@link GatewayParamFlowItemEntity}
     */
    GATEWAY_PARAM_FLOW("gateway-param-flow-rules", GatewayParamFlowItemEntity.class);

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
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Illegal rule type: " + clazz));
    }
}