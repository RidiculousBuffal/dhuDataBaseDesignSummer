package com.dhu.swimmingpool.Service;

import java.util.Map;

public interface BlockListService {
    public void updateBlockedCardsStatus();

    public Map<String,Object> getBlockCardInfo(int PageNum,int PageSize,String cid,String username);
}
