package com.dhu.swimmingpool.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Consume {
  private long id;
  private long uid;
  private String cid;
  private double currentBalance;
  private String description;
  private double consumeSaveBalance;
  private java.sql.Timestamp createTime;
}
