package com.dhu.swimmingpool.Mapper;

import com.dhu.swimmingpool.Pojo.Blockedcardlistinfo;
import com.dhu.swimmingpool.Pojo.Card;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;

@Transactional
@Mapper
public interface CardMapper {
    @Insert("insert into card values (#{cid},#{uid},#{balance}," +
        "#{tid},#{discount},#{status})")
    public boolean makeCard(Card card);

    @Update("update card set Balance = Balance+#{money} where UID = #{Uid} and CID = #{Cid}")
    public boolean modifyMoney(Long Uid, String Cid, Double money);

    public ArrayList<Map<String, Object>> getCardInfo(Long uid, String cid);

    @Insert("insert into blockedcardlistinfo (CID, BDescription, BCreateTime, BExpire) values " +
        "(#{cid},#{bDescription},Now(),#{bExpire});")
    public boolean addBlockRecord(Blockedcardlistinfo info);

    @Insert("update card set Status = #{state} where CID = #{cid}")
    public boolean updateCardStatus(String cid, int state);

    @Select("select card.CID from card where CID like CONCAT(#{prefix},'%') limit 5")
    public ArrayList<String> getCidWithPrefix(String prefix);

    @Select("select count(CID) from card")
    public Long getCardNumber();

    public ArrayList<Map<String,Object>> getCardsInfoWithPageHelper (String Cid,String Username,
                                                                     ArrayList<Integer>tids);

    @Update("update card set tid = #{tid},Discount=#{discount} where CID=#{cid}")
    public boolean  updateTidAndDiscount(String cid,Integer tid,double discount);
}
