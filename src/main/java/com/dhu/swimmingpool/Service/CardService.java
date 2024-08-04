package com.dhu.swimmingpool.Service;

import com.dhu.swimmingpool.Pojo.Blockedcardlistinfo;
import com.dhu.swimmingpool.Pojo.Card;

import java.util.ArrayList;
import java.util.Map;

public interface CardService {
    public boolean makeCard(Card card);

    public ArrayList<Map<String, Object>> getCardInfo(Long Uid, String Cid);

    public boolean ModifyMoney(Long Uid, String Cid, Double money);

    public boolean addBlockRecord(Blockedcardlistinfo blockedcardlistinfo);

    public boolean updateState(String cid, int state);

    public ArrayList<String> getCidWithPrefix(String prefix);

    public Long getCardNumber();

    public Map<String, Object> getCardsInfoWithPageHelper(int pageNum, int PageSize,
                                                                     String Cid, String Username,
                                                                     ArrayList<Integer> tids);

    public boolean  updateTidAndDiscount(String cid,Integer tid,double discount);
}
