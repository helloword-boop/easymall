package com.easymall.service;

import com.easymall.common.pojo.Seckill;
import com.easymall.mapper.SeckillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillService {
    @Autowired
    private SeckillMapper seckillMapper;
    public List<Seckill> querySeckillList() {
        return seckillMapper.querySeckillList();
    }

    public Seckill queryOneSeckill(Long seckillId) {
        return seckillMapper.queryOneSeckill(seckillId);
    }
}
