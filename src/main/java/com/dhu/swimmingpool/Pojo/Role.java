package com.dhu.swimmingpool.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class Role {

  private long rid;
  private String description;
  private java.sql.Timestamp createTime;

}