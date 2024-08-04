package com.dhu.swimmingpool.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BlockListMapper {
    @Update("update card " +
        "set Status = 1 " +
        "where Status!=1 " +
        "and CID in " +
        "(select CID " +
        "from blockedcardlistinfo " +
        "WHERE " +
        "BExpire > NOW() ) ")
    public boolean updateCardStatus();
}
