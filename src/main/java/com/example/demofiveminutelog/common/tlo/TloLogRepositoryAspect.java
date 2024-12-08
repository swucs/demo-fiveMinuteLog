package com.example.demofiveminutelog.common.tlo;


import com.example.demofiveminutelog.common.exception.InvalidRequestException;
import com.example.demofiveminutelog.common.exception.ServerException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class TloLogRepositoryAspect {
    private final Logger tloLogger = LoggerFactory.getLogger("tloLogger");

    @Pointcut("execution(* com.example.demofiveminutelog.repository.*Repository.*(..))")
    private void cut() {
    }

    ;

    @Around("cut()")
    public Object statisticsLog(ProceedingJoinPoint joinPoint) throws Throwable {

        TloLogVo tloLog = TloLogVoThreadHolder.get();
        if (tloLog == null || StringUtils.isNotBlank(tloLog.getDbReqTime())) {
            return joinPoint.proceed();
        }

        tloLog.setDbReqTime();

        Object ret;
        try {
            ret = joinPoint.proceed();

            tloLog.setDbResultCode(TloLogVo.DB_SUCCESS_CODE);
            tloLog.setDbRspTime();
        } catch (Exception e) {
            tloLog.setDbResultCode(e.getClass().getSimpleName());
            tloLog.setDbRspTime();

            throw e;
        }

        return ret;
    }
}
