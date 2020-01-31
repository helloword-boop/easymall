package com.easymall.service;

import com.easymall.common.pojo.User;
import com.easymall.common.utils.MD5Util;
import com.easymall.common.utils.MapperUtil;
import com.easymall.common.vo.SysResult;
import com.easymall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisCluster jedisCluster;
    /*检查用户名是否存在*/
    public SysResult checkUserName(String userName) {
        int userNum=userMapper.checkUserByName(userName);
        if (userNum!=0){
            return new SysResult(201,"用户名已存在",null);
        }else {
            return SysResult.ok();
        }
    }
    /*注册用户*/
    public void addUser(User user) {
        //补齐userId
        user.setUserId(UUID.randomUUID().toString());
        //加密密码
        user.setUserPassword(MD5Util.md5(user.getUserPassword()));
        userMapper.addUser(user);
    }

    /*用户登录*/
    public String userLogin(User user) {
        user.setUserPassword(MD5Util.md5(user.getUserPassword()));
        User userExist = userMapper.queryUser(user);
        String ticket="";
        if(userExist==null){
            return "";
        }else {
            String userKey="login_"+userExist.getUserId();
            if (jedisCluster.exists(userKey)){
                jedisCluster.del(jedisCluster.get(userKey));
            }
            try {
                ticket="EM_TICKET"+System.currentTimeMillis()+userExist.getUserName();
                jedisCluster.set(userKey,ticket);
                userExist.setUserPassword(null);
                String jsonUser= MapperUtil.MP.writeValueAsString(userExist);
                jedisCluster.setex(ticket,60*60*2,jsonUser);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        return ticket;
    }

    /*查询用户登录信息,实现超时续租*/
    public SysResult queryUserLogin(String ticket) {
        if(jedisCluster.ttl(ticket)<30*60){
            jedisCluster.expire(ticket,2*60*60);
        }
        String userJson=jedisCluster.get(ticket);
        if(userJson==null){
            return SysResult.build(201,"可能超时",null);
        }else {
            return SysResult.build(200,"success",userJson);
        }
    }

}
