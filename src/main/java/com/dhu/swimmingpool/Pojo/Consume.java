package com.dhu.swimmingpool.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Consume {

  private long uid;
  private String cid;
  private double consumeSaveBalance;
  private double balance;
  private java.sql.Timestamp createTime;

}
