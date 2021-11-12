package com.alibaba.csp.sentinel.dashboard.rule.nacos.param;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author lzhpo
 */
@Component
public class ParamRuleNacosPublisher implements DynamicRulePublisher<List<ParamFlowRuleEntity>> {

    @Autowired
    private ConfigService configService;

    @Autowired
    private Converter<List<ParamFlowRuleEntity>, String> converter;

    @Override
    public void publish(String app, List<ParamFlowRuleEntity> rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");
        if (!ObjectUtils.isEmpty(rules)) {
            configService.publishConfig(
                    app + NacosConfigUtil.PARAM_FLOW_DATA_ID_POSTFIX,
                    NacosConfigUtil.GROUP_ID,
                    converter.convert(rules));
        }
    }
}
