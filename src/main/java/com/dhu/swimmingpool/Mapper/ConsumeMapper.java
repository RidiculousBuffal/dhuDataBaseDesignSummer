package com.dhu.swimmingpool.Mapper;

import com.dhu.swimmingpool.Pojo.Consume;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

@Mapper
@Transactional
public interface ConsumeMapper {

    @Insert("insert into consume (UID, CID, Consume_Save_Balance, CreateTime, currentBalance, description) " +
        "values (#{uid},#{cid},#{consumeSaveBalance},Now(),#{currentBalance},#{description})")
    public boolean addConsumeRecord(Consume consume);

    public ArrayList<Map<String, Object>> getConsumeInfo(Long uid,String cid, Timestamp startTime,
                                                         Timestamp endTime);

    @Select("select count(*) from consume")
    public Long getConsumeCount();
}
