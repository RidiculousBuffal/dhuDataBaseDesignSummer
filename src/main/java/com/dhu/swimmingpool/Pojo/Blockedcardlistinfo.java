package com.dhu.swimmingpool.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Blockedcardlistinfo {

  private long bid;
  private String cid;
  private String bDescription;
  private java.sql.Timestamp bCreateTime;
  private java.sql.Timestamp bExpire;
}
