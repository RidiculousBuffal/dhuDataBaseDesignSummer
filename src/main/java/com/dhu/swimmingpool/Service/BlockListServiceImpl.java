package com.dhu.swimmingpool.Service;

import com.dhu.swimmingpool.Mapper.BlockListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
}
