package com.dhu.swimmingpool.Controller;

import com.dhu.swimmingpool.Pojo.Result;
import com.dhu.swimmingpool.Service.ConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/consume")

public class ConsumeController {
    @Autowired
    private ConsumeService consumeService;

    @PostMapping("/getInfo")
    public Result getConsumeInfo(@RequestParam(required = true) int pageNum,
                                 @RequestParam(required = true) int PageSize,
                                 @RequestParam(required = false) Long uid,
                                 @RequestParam(required = false) String cid,
                                 @RequestParam(required = false) String startTime,
                                 @RequestParam(required = false) String endTime) {
        return Result.success(
            consumeService.getConsumeInfo
                (pageNum, PageSize, uid, startTime, endTime, cid)
        );
    }
    @PostMapping("/getConsumeCount")
    public Result getConsumeCount(){
        return Result.success(consumeService.getConsumeNumber());
    }
}
