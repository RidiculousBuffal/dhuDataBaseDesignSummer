package com.dhu.swimmingpool.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;
import java.util.Map;

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

    public ArrayList<Map<String,Object>>getBlockedCardUser(String cid,String username);
}
