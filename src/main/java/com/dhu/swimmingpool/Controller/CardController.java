package com.dhu.swimmingpool.Controller;

import com.dhu.swimmingpool.Pojo.*;
import com.dhu.swimmingpool.Service.CardService;
import com.dhu.swimmingpool.Service.ConsumeService;
import com.dhu.swimmingpool.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/card")
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private UserService userService;
    @Autowired
    private ConsumeService consumeService;

    @PostMapping("/makeCard")
    public Result makeCard(@RequestBody Card card) {
        card.setCid(UUID.randomUUID().toString().replace("-", ""));
        card.setStatus(1);
        if (cardService.makeCard(card)) {
            if ((Long) userService.getUserInfo(card.getUid()).get("UROLEID") == 3) {
                userService.updateUserRole(Long.parseLong("4"), card.getUid());
            }
            Consume consume = new Consume();
            consume.setCid(card.getCid());
            consume.setUid(card.getUid());
            consume.setCurrentBalance(card.getBalance());
            consume.setDescription("新卡办理");
            consume.setConsumeSaveBalance(card.getBalance());
            if (consumeService.addConsumeRecord(consume)) {
                return Result.success(card);
            }
        }
        return Result.error("办卡失败");
    }

    //确保卡的状态和卡的类型
    @PostMapping("/addMoney")
    public Result saveMoney(@RequestParam Long userId, @RequestParam String CID,
                            @RequestParam double Money,
                            @RequestParam(required = false, defaultValue = "普通存钱") String description) {
        System.out.println("hello!");
        ArrayList<Map<String, Object>> cardInfo = cardService.getCardInfo(userId, CID);
        Long TID = (Long) cardInfo.get(0).get("TID");
        Double restMoney = (Double) cardInfo.get(0).get("Balance");
        Integer status = (Integer) cardInfo.get(0).get("Status");
        if (TID == 1 && status == 1) {
            Consume consume = new Consume();
            consume.setUid(userId);
            consume.setCid(CID);
            consume.setConsumeSaveBalance(Money);
            consume.setDescription(description);
            consume.setCurrentBalance(restMoney + Money);
            if (cardService.ModifyMoney(userId, CID, Money) && consumeService.addConsumeRecord(consume)) {
                return Result.success();
            } else {
                return Result.error("储值失败");
            }
        }
        return Result.error("Card Type Error");
    }

    @PostMapping("/consumeMoney")
    //默认传进来的金额是正的
    public Result consumeMoney(@RequestParam Long userId, @RequestParam String CID,
                               @RequestParam double Money,
                               @RequestParam(required = false, defaultValue = "普通消费") String description) {
        ArrayList<Map<String, Object>> cardInfo = cardService.getCardInfo(userId, CID);
        Double discount = (Double) cardInfo.get(0).get("Discount");
        //消费
        Double restMoney = (Double) cardInfo.get(0).get("Balance");
        Integer status = (Integer) cardInfo.get(0).get("Status");
        if (restMoney - Money * discount >= 0 && status == 1) {
            Consume consume = new Consume();
            consume.setUid(userId);
            consume.setCid(CID);
            consume.setConsumeSaveBalance(-Money * discount);
            consume.setCurrentBalance(restMoney - Money * discount);
            consume.setDescription(description);
            if (cardService.ModifyMoney(userId, CID, -Money * discount) && consumeService.addConsumeRecord(consume)) {
                return Result.success("消费成功!");
            }
            return Result.error("系统出错");
        } else {
            return Result.error("余额不足!");
        }
    }

    @PostMapping("/blockcard")
    public Result cardBlock(@RequestBody Map<String, String> blockedcardlistinfo) {
        String cid = blockedcardlistinfo.get("cid");
        ArrayList<Map<String, Object>> cardInfo = cardService.getCardInfo(null, cid);
        if (!cardInfo.isEmpty() && (Integer) cardInfo.get(0).get("Status") == 1) {
            cardService.updateState(cid, 2);//调整状态
            Blockedcardlistinfo info = new Blockedcardlistinfo();
            info.setCid(cid);
            info.setBDescription(blockedcardlistinfo.get("bDescription"));
            info.setBExpire(new Timestamp(Long.parseLong(blockedcardlistinfo.get("bExpire"))));
            if (cardService.addBlockRecord(info)) {
                return Result.success();
            } else {
                return Result.error("封禁申请异常");
            }
        }
        return Result.error("未找到此卡或卡本身已在封禁状态");
    }

    @PostMapping("/setCardStatus")
    public Result setCardStatus(@RequestParam String cid, @RequestParam int status) {
        ArrayList<Map<String, Object>> cardInfo = cardService.getCardInfo(null, cid);
        if (!cardInfo.isEmpty() && (Integer) cardInfo.get(0).get("Status") != 3) {
            if (cardService.updateState(cid, status)) {
                return Result.success();
            } else {
                return Result.error("设置卡状态失败");
            }
        } else {
            return Result.error("该卡不存在或已经被退卡");
        }
    }

    @PostMapping("/getcardInfo")
    public Result getCardInfo(@RequestParam(required = false) Long uid, @RequestParam(required =
        false) String cid) {
        return Result.success(cardService.getCardInfo(uid, cid));
    }


    @PostMapping("/getCidWithPrefix")
    public Result getCidWithPrefix(@RequestParam String prefix) {
        return Result.success(cardService.getCidWithPrefix(prefix));
    }

    @PostMapping("/getCardCount")
    public Result getCardCount() {
        return Result.success(cardService.getCardNumber());
    }

    @PostMapping("/getCardsInfoWithPageHelper")
    public Result getCardsInfoWithPageHelper(
        @RequestParam(required = true, name = "pageNum") Integer pageNum,
        @RequestParam(required = true, name = "pageSize") Integer pageSize,
        @RequestParam(required = false, name = "cid") String cid,
        @RequestParam(required = false, name = "username") String username,
        @RequestParam(required = false, name = "tids") ArrayList<Integer> tids) {

        return Result.success(cardService.getCardsInfoWithPageHelper(pageNum, pageSize, cid,
            username, tids));
    }
    @PostMapping("/updateTidAndDiscount")
    public Result updateTidAndDiscount(String cid, Integer tid, Double discount){
        System.out.println(discount);
        if(cardService.updateTidAndDiscount(cid,tid,discount)){
            return Result.success("更新成功");
        }else{
            return Result.error("更新失败");
        }
    }
}
