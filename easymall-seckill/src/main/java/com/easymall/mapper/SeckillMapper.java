package com.easymall.mapper;

import com.easymall.common.pojo.Seckill;
import com.easymall.common.pojo.Success;

import java.util.List;

public interface SeckillMapper {
    List<Seckill> querySeckillList();

    Seckill queryOneSeckill(Long seckillId);

    int reduceNum(long seckillId);

    void saveSuccess(Success success);
}
