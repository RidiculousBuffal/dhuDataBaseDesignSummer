package com.dhu.swimmingpool.Service;

import com.dhu.swimmingpool.Mapper.ConsumeMapper;
import com.dhu.swimmingpool.Pojo.Consume;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
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
    public Map<String, Object> getConsumeInfo(int pageNum, int PageSize, Long uid,
                                              String startTime, String endTime, String cid,
                                              String username) {
        PageHelper.startPage(pageNum, PageSize);
        var ST = startTime == null ? null : new Timestamp(Long.parseLong(startTime));
        var ET = endTime == null ? null : new Timestamp(Long.parseLong(endTime));
        ArrayList<Map<String, Object>> consumeInfoWithPageHelper =
            consumeMapper.getConsumeInfo(uid, cid, ST, ET,username);
        PageInfo page = new PageInfo(consumeInfoWithPageHelper);
        Map<String,Object>RES = new HashMap<String,Object>();
        RES.put("arr",consumeInfoWithPageHelper);
        RES.put("total",page.getTotal());
        return RES;
    }

    @Override
    public Long getConsumeNumber() {
        return consumeMapper.getConsumeCount();
    }
}
