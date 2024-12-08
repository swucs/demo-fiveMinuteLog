package com.example.demofiveminutelog.common.tlo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TloLogVoThreadHolder {

    private final static ThreadLocal<TloLogVo> threadLocalTloLog = new ThreadLocal<>();

    public static void set(TloLogVo tloLoggerVO) {
        threadLocalTloLog.set(tloLoggerVO);
    }

    public static TloLogVo get() {
        return threadLocalTloLog.get();
    }

    public static void remove() {
        threadLocalTloLog.remove();
    }

}
