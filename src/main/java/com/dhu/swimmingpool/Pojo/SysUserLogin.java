package com.dhu.swimmingpool.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SysUserLogin {

  private long uid;
  private String userName;
  private String userPassWord;
  private int userState;
  private long rid;

}
