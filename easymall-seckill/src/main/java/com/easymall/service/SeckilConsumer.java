package com.easymall.service;

import com.easymall.common.pojo.Success;
import com.easymall.mapper.SeckillMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.Date;

@Component
public class SeckilConsumer {
    @Autowired
    private SeckillMapper seckillMapper;
    @Autowired
    private JedisCluster jedisCluster;

    @RabbitListener(queues = "seckillQueue")
    public void consume(String msg) {
        System.out.println(msg);
        long userPhone = Long.parseLong(msg.split("/")[0]);
        long seckillId = Long.parseLong(msg.split("/")[1]);
        /*String seckillNum = jedisCluster.rpop("seckilllist");
        if (seckillNum == null) {
            System.out.println("用户:"+userPhone+" 秒杀:"+seckillId+" 失败");
            return;
        }*/
        int result = seckillMapper.reduceNum(seckillId);
        if (result == 0) {
            System.out.println("用户:"+userPhone+" 秒杀:"+seckillId+" 失败");
            return;
        }else {
            Success success = new Success();
            success.setUserPhone(userPhone);
            success.setSeckillId(seckillId);
            success.setState(0);
            success.setCreateTime(new Date());
            seckillMapper.saveSuccess(success);
        }


    }
}
