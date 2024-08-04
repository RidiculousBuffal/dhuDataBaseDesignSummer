package com.dhu.swimmingpool.Service;

import com.dhu.swimmingpool.Mapper.ConsumeMapper;
import com.dhu.swimmingpool.Pojo.Consume;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ConsumeServiceImpl implements ConsumeService {
    @Autowired
    ConsumeMapper consumeMapper;

    @Override
    public boolean addConsumeRecord(Consume consume) {
        return consumeMapper.addConsumeRecord(consume);
    }

    @Override
    public ArrayList<Map<String, Object>> getConsumeInfo(int pageNum, int PageSize, Long uid, String startTime, String endTime, String cid) {
        PageHelper.startPage(pageNum, PageSize);
        var ST = startTime == null ? null : new Timestamp(Long.parseLong(startTime));
        var ET = endTime == null ? null : new Timestamp(Long.parseLong(endTime));
        return consumeMapper.getConsumeInfo(uid, cid, ST, ET);
    }

    @Override
    public Long getConsumeNumber() {
        return consumeMapper.getConsumeCount();
    }
}
