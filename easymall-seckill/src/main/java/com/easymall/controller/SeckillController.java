package com.easymall.controller;

import com.easymall.common.pojo.Seckill;
import com.easymall.common.vo.SysResult;
import com.easymall.service.SeckillService;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("seckill/manage")
public class SeckillController {
    @Autowired
    private SeckillService seckillService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("list")
    public List<Seckill> querySeckillList() {
        return seckillService.querySeckillList();
    }

    @RequestMapping("detail")
    public Seckill queryOneSeckill(Long seckillId) {
        return seckillService.queryOneSeckill(seckillId);
    }

    @RequestMapping("send")
    public String send(String msg) {
        rabbitTemplate.convertAndSend("seckillEx","seckill",msg);
        return "success";
    }

    @RequestMapping("{seckillId}")
    public SysResult startSeckill(@PathVariable Long seckillId) {
        String usrPhone = "1394561" + RandomUtils.nextInt(9999);
        String msg=usrPhone+"/"+seckillId;
        rabbitTemplate.convertAndSend("seckillEx","seckill",msg);
        return SysResult.ok();
    }
}
