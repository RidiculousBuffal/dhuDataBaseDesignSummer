package com.dhu.swimmingpool.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auth {
  private long aid;
  private String title;
  private Integer PID;
  private String path;
  private String componentName;
  private String type;
}
