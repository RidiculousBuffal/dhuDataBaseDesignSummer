package com.dhu.swimmingpool.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class User {

    private long id;
    private long uid;
    private String uname;
    private String uphone;
    private String uidentity;
    private String uavatar;
    private String uDescription;
}
