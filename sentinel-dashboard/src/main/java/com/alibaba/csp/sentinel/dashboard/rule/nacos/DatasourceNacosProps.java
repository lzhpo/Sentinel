package com.alibaba.csp.sentinel.dashboard.rule.nacos;

/**
 * @author lzhpo
 */
public class DatasourceNacosProps {

    private String serverAddr = "localhost:8848";

    private String username = "nacos";

    private String password = "nacos";

    private String namespace = "";

    private String groupId = "SENTINEL_GROUP";

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
