package com.dhu.swimmingpool.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Card {
  private String cid;
  private long uid;
  private double balance;
  private long tid;
  private double discount;
  private int status;//1 正常使用,2 被封禁，3 被退卡 4. 被挂失
}
