package com.alibaba.csp.sentinel.dashboard.rule.nacos.flow;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author lzhpo
 */
@Component
public class FlowRuleNacosProvider implements DynamicRuleProvider<List<FlowRuleEntity>> {

    private final Logger logger = LoggerFactory.getLogger(FlowRuleNacosProvider.class);

    @Autowired
    private ConfigService configService;

    @Autowired
    private Converter<String, List<FlowRuleEntity>> converter;

    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {
        return Optional.ofNullable(appName)
                .filter(StringUtils::hasText)
                .map(x -> x + NacosConfigUtil.FLOW_DATA_ID_POSTFIX)
                .map(
                        dataId -> {
                            try {
                                return configService.getConfig(dataId, NacosConfigUtil.GROUP_ID, 3000);
                            } catch (NacosException e) {
                                logger.error(e.getErrMsg(), e);
                            }

                            return null;
                        })
                .filter(StringUtils::hasText)
                .map(converter::convert)
                .orElse(Collections.emptyList());
    }
}
