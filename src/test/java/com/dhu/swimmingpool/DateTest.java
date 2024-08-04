package com.dhu.swimmingpool;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.sql.Timestamp;

public class DateTest {
    @Test
    public void SQLDateTest (){
       String x = "1724822055000";
       var st = x == null ? null : new Timestamp(Long.parseLong(x));
        System.out.println(st);

    }
}
