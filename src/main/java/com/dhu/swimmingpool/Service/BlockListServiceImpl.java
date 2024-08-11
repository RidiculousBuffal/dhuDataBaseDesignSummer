package com.dhu.swimmingpool.Service;

import com.dhu.swimmingpool.Mapper.BlockListMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class BlockListServiceImpl implements BlockListService{
    @Autowired
    private BlockListMapper blockListMapper;
    @Scheduled(cron = "0 0 */2 * * ?")
    @Override
    //定时每两小时执行一次把封禁但是时间到的卡片恢复状态
    public void updateBlockedCardsStatus() {
        if(blockListMapper.updateCardStatus()){
            System.out.println("更新卡片状态成功");
        }
    }

    @Override
    public Map<String, Object> getBlockCardInfo(int PageNum, int PageSize, String cid, String username) {
        PageHelper.startPage(PageNum, PageSize);
        ArrayList<Map<String, Object>> cardsInfoWithPageHelper =
            blockListMapper.getBlockedCardUser(cid,username);
        PageInfo page = new PageInfo(cardsInfoWithPageHelper);
        Map<String,Object>  RES = new HashMap<String,Object>();
        RES.put("arr",cardsInfoWithPageHelper);
        RES.put("total",page.getTotal());
        return RES;
    }
}
