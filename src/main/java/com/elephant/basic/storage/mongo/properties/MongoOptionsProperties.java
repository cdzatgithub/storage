package com.elephant.basic.storage.mongo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chendongzhi
 * @date 19:482019/6/25 0025
 * @description mongo options 参数配置
 */

@ConfigurationProperties(prefix = "spring.data.mongodb.options")
public class MongoOptionsProperties {
    /**
     * 每个节点最小连接数，默认0
     */
    private int minConnectionsPerHost=0;
    /**
     * 每个节点最大连接数，默认100 ，超过限制将阻塞
     */
    private int maxConnectionsPerHost=100;
    /**
     * 最大等待线程数，默认5，超过限制将直接异常退出
     */
    private int threadsAllowedToBlockForConnectionMultiplier=5;
    /**
     * 服务选择超时时间默认30,000 ms
     */
    private int serverSelectionTimeout=30000;
    /**
     * 等待链接可用超时时间默认120,000ms
     */
    private int maxWaitTime=120000;
    /**
     * 链接空闲超时时间，默认0，即不限制
     */
    private int maxConnectionIdleTime=0;
    /**
     * 生命周期时间默认0 ，即不限制
     */
    private int maxConnectionLifeTime=0;

    /**
     * 链接超时时间 默认10,000ms
     */
    private int connectTimeout=10000;
    /**
     * socket 读写超时时间，默认0，即不加限制
     */
    private int socketTimeout=0;
    /**
     * 是否使用ssl，默认false
     */
    private boolean sslEnabled=false;
    /**
     * 当允许ssl链接时，是否允许非法域名，默认 false
     */
    private boolean sslInvalidHostNameAllowed=false;
    /**
     * if JMX beans should always be MBeans 默认false
     */
    private boolean alwaysUseMBeans=false;
    /**
     * 心跳间隔，默认10,000 ms
     */
    private int heartbeatFrequency=10000;
    /**
     * 心跳重试，最小时间，默认500ms
     */
    private int minHeartbeatFrequency=500;
    /**
     * 心跳链接超时时间：默认20,000ms
     */
    private int heartbeatConnectTimeout=20000;
    /**
     * 心跳读写超时时间，默认：20,000ms
     */
    private int heartbeatSocketTimeout=20000;
    /**
     * ping阈值指数，默认15，选择ping时间小于最小时间加上该值的servers
     */
    private int localThreshold=15;

    /**
     * replicaSet 名称，默认空
     */
    private String requiredReplicaSetName;


    public int getMinConnectionsPerHost() {
        return minConnectionsPerHost;
    }

    public void setMinConnectionsPerHost(int minConnectionsPerHost) {
        this.minConnectionsPerHost = minConnectionsPerHost;
    }

    public int getMaxConnectionsPerHost() {
        return maxConnectionsPerHost;
    }

    public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
        this.maxConnectionsPerHost = maxConnectionsPerHost;
    }

    public int getThreadsAllowedToBlockForConnectionMultiplier() {
        return threadsAllowedToBlockForConnectionMultiplier;
    }

    public void setThreadsAllowedToBlockForConnectionMultiplier(int threadsAllowedToBlockForConnectionMultiplier) {
        this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
    }

    public int getServerSelectionTimeout() {
        return serverSelectionTimeout;
    }

    public void setServerSelectionTimeout(int serverSelectionTimeout) {
        this.serverSelectionTimeout = serverSelectionTimeout;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public int getMaxConnectionIdleTime() {
        return maxConnectionIdleTime;
    }

    public void setMaxConnectionIdleTime(int maxConnectionIdleTime) {
        this.maxConnectionIdleTime = maxConnectionIdleTime;
    }

    public int getMaxConnectionLifeTime() {
        return maxConnectionLifeTime;
    }

    public void setMaxConnectionLifeTime(int maxConnectionLifeTime) {
        this.maxConnectionLifeTime = maxConnectionLifeTime;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public boolean isSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public boolean isSslInvalidHostNameAllowed() {
        return sslInvalidHostNameAllowed;
    }

    public void setSslInvalidHostNameAllowed(boolean sslInvalidHostNameAllowed) {
        this.sslInvalidHostNameAllowed = sslInvalidHostNameAllowed;
    }

    public boolean isAlwaysUseMBeans() {
        return alwaysUseMBeans;
    }

    public void setAlwaysUseMBeans(boolean alwaysUseMBeans) {
        this.alwaysUseMBeans = alwaysUseMBeans;
    }

    public int getHeartbeatFrequency() {
        return heartbeatFrequency;
    }

    public void setHeartbeatFrequency(int heartbeatFrequency) {
        this.heartbeatFrequency = heartbeatFrequency;
    }

    public int getMinHeartbeatFrequency() {
        return minHeartbeatFrequency;
    }

    public void setMinHeartbeatFrequency(int minHeartbeatFrequency) {
        this.minHeartbeatFrequency = minHeartbeatFrequency;
    }

    public int getHeartbeatConnectTimeout() {
        return heartbeatConnectTimeout;
    }

    public void setHeartbeatConnectTimeout(int heartbeatConnectTimeout) {
        this.heartbeatConnectTimeout = heartbeatConnectTimeout;
    }

    public int getHeartbeatSocketTimeout() {
        return heartbeatSocketTimeout;
    }

    public void setHeartbeatSocketTimeout(int heartbeatSocketTimeout) {
        this.heartbeatSocketTimeout = heartbeatSocketTimeout;
    }

    public int getLocalThreshold() {
        return localThreshold;
    }

    public void setLocalThreshold(int localThreshold) {
        this.localThreshold = localThreshold;
    }

    public String getRequiredReplicaSetName() {
        return requiredReplicaSetName;
    }

    public void setRequiredReplicaSetName(String requiredReplicaSetName) {
        this.requiredReplicaSetName = requiredReplicaSetName;
    }
}
