package com.dhu.swimmingpool.Service;

import com.dhu.swimmingpool.Mapper.CardMapper;
import com.dhu.swimmingpool.Pojo.Blockedcardlistinfo;
import com.dhu.swimmingpool.Pojo.Card;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardMapper cardMapper;

    @Override
    public boolean makeCard(Card card) {
        return cardMapper.makeCard(card);
    }

    @Override
    public ArrayList<Map<String, Object>> getCardInfo(Long Uid, String Cid) {
        return cardMapper.getCardInfo(Uid, Cid);
    }

    @Override
    public boolean ModifyMoney(Long Uid, String Cid, Double money) {
        return cardMapper.modifyMoney(Uid, Cid, money);
    }

    @Override
    public boolean addBlockRecord(Blockedcardlistinfo blockedcardlistinfo) {
        return cardMapper.addBlockRecord(blockedcardlistinfo);
    }

    @Override
    public boolean updateState(String cid, int state) {
        return cardMapper.updateCardStatus(cid,state);
    }

    @Override
    public ArrayList<String> getCidWithPrefix(String prefix) {
        return cardMapper.getCidWithPrefix(prefix);
    }

    @Override
    public Long getCardNumber() {
        return cardMapper.getCardNumber();
    }

    @Override
    public Map<String, Object> getCardsInfoWithPageHelper(int pageNum, int PageSize,
                                                                     String Cid, String Username,
                                                                     ArrayList<Integer> tids) {
        PageHelper.startPage(pageNum, PageSize);
        ArrayList<Map<String, Object>> cardsInfoWithPageHelper = cardMapper.getCardsInfoWithPageHelper(Cid, Username, tids);
        PageInfo page = new PageInfo(cardsInfoWithPageHelper);
        Map<String,Object>  RES = new HashMap<String,Object>();
        RES.put("arr",cardsInfoWithPageHelper);
        RES.put("total",page.getTotal());
        return RES;
    }

    @Override
    public boolean updateTidAndDiscount(String cid, Integer tid, double discount) {
        return cardMapper.updateTidAndDiscount(cid,tid,discount);
    }
}
