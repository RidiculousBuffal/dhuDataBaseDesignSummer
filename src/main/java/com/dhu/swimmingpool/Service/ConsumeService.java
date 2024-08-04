package com.dhu.swimmingpool.Service;

import com.dhu.swimmingpool.Pojo.Consume;

import java.util.ArrayList;
import java.util.Map;

public interface ConsumeService {
    public boolean addConsumeRecord(Consume consume);

    public ArrayList<Map<String, Object>> getConsumeInfo(int pageNum, int PageSize, Long uid, String startTime,
                                                         String endTime, String cid);
    public Long getConsumeNumber();
}
