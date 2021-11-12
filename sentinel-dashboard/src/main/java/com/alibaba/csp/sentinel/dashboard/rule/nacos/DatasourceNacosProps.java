package com.alibaba.csp.sentinel.dashboard.rule.nacos;

/**
 * @author lzhpo
 */
public class DatasourceNacosProps {

    private String serverAddress = "localhost:8848";

    private String username = "nacos";

    private String password = "nacos";

    private String namespace = "";

    private String group = "SENTINEL_GROUP";

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
