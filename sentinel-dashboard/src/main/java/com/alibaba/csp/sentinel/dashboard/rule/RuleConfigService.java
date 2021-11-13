package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;

/**
 * @author lzhpo
 */
public interface RuleConfigService extends ConfigService {

    /**
     * Publish config.
     * <p>
     * 当前版本 1.3.0 是不能设置文件类型的，会设置为text
     * 1.4.1修复了这个问题：https://github.com/alibaba/nacos/releases/tag/1.4.1
     *
     * @param dataId  dataId
     * @param group   group
     * @param content content
     * @param type    type
     * @return Whether publish
     * @throws NacosException NacosException
     */
    boolean publishConfig(String dataId, String group, String content, String type) throws NacosException;
}
