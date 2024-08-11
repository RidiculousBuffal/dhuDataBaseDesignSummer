package com.dhu.swimmingpool.Controller;

import com.dhu.swimmingpool.Pojo.Result;
import com.dhu.swimmingpool.Service.BlockListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/blockList")
public class BlockListController {
    @Autowired
    private BlockListService blockListService;

    @PostMapping("/getInfo")
    public Result getInfo(@RequestParam int PageNum, @RequestParam int PageSize,
                          @RequestParam(required = false) String cid, @RequestParam(required
        = false) String username) {
        return Result.success(blockListService.getBlockCardInfo(PageNum, PageSize, cid, username));
    }
}
