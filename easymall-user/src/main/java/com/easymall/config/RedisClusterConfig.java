package com.easymall.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@ConfigurationProperties("redis.cluster")
public class RedisClusterConfig {
    private List<String> nodes;
    private int maxTotal;
    private int maxIdle;
    private int minIdle;
    @Bean
    public JedisCluster initCluster(){
        Set<HostAndPort> hostAndPortSet=new HashSet<>();
        for (String node : nodes) {
            String host=node.split(":")[0];
            int port= Integer.parseInt(node.split(":")[1]);
            hostAndPortSet.add(new HostAndPort(host,port));
        }
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxTotal(maxTotal);
        return new JedisCluster(hostAndPortSet,jedisPoolConfig);
    }

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }
}
