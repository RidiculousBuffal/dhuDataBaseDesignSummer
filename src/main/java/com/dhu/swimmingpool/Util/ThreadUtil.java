package com.dhu.swimmingpool.Util;

public class ThreadUtil {
    private static final ThreadLocal THREAD_LOCAL  = new ThreadLocal();


    public static<T> T Thread_Local_get(){
        return (T) THREAD_LOCAL.get();
    }

    public static void THREAD_LOCAL_set (Object value){
        THREAD_LOCAL.set(value);
    }

    public static void THREAD_LOCAL_remove(){
        THREAD_LOCAL.remove();
    }

}
