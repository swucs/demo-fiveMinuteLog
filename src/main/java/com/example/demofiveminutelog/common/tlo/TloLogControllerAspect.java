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
public class TloLogControllerAspect {
    private final Logger tloLogger = LoggerFactory.getLogger("tloLogger");

    private final String SUCCESS_CDOE = "20000000";
    private final String FAIL_CDOE = "50000999";
    private final String PRE_USER_FAIL_CDOE = "4000";
    private final String PRE_SERVER_FAIL_CDOE = "5000";;
    private static final String HOST_NAME = "FIVE-MINUTE-LOG";

    @Pointcut("execution(* com.example.demofiveminutelog.controller.*Controller.*(..))")
    private void cut() {
    }

    ;

    @Around("cut()")
    public Object statisticsLog(ProceedingJoinPoint joinPoint) throws Throwable {
        TloLogMethod tloLogMethod = null;

        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            tloLogMethod = method.getAnnotation(TloLogMethod.class);
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        //통합통계용 API가 아닌 경우 종료
        if (tloLogMethod == null) {
            return joinPoint.proceed();
        }

        TloLogVo tloLog = new TloLogVo();
        tloLog.setSeqId();
        tloLog.setReqTime();
        tloLog.setHostName(HOST_NAME);
        tloLog.setLogType(TloLogVo.LOG_TYPE);
        tloLog.setScnName(TloLogVo.SCN_NAME);
        tloLog.setSvcName(TloLogVo.SVC_NAME);
        tloLog.setSvcClass(tloLogMethod.svcClass());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request;
        if (attributes != null) {
            request = attributes.getRequest();
            tloLog.setSid(StringUtils.defaultString(request.getHeader("sid")));
            tloLog.setClientIp(StringUtils.defaultString(request.getHeader("client-ip")));
            tloLog.setDevInfo(StringUtils.defaultString(request.getHeader("dev-info")));
            tloLog.setOsInfo(StringUtils.defaultString(request.getHeader("os-info")));
            tloLog.setNwInfo(StringUtils.defaultString(request.getHeader("network")));
            tloLog.setDevModel(StringUtils.defaultString(request.getHeader("dev-model")));
            tloLog.setCarrierType(StringUtils.defaultString(request.getHeader("carrier-type")));
        }

        TloLogVoThreadHolder.set(tloLog);


        // pointcut Start
        Object ret;
        try {
            ret = joinPoint.proceed();
        } catch (Exception e) {
            if (e instanceof InvalidRequestException) {
                String errorCode = ((InvalidRequestException) e).getErrorCode();
                tloLog.setResultCode(PRE_USER_FAIL_CDOE + errorCode);
            } else if (e instanceof ServerException) {
                String errorCode = ((ServerException) e).getErrorCode();
                tloLog.setResultCode(PRE_SERVER_FAIL_CDOE + errorCode);
            } else {
                tloLog.setResultCode(FAIL_CDOE);
            }
            tloLog.setRspTime();
            tloLog.setLogTime();

            //tlo_log 작성.
            tloLogger.info(tloLog.toString());
            throw e;
        }

        tloLog.setRspTime();
        tloLog.setLogTime();
        tloLog.setResultCode(SUCCESS_CDOE);

        //tlo_log 작성.
        tloLogger.info(tloLog.toString());

        TloLogVoThreadHolder.remove();
        return ret;
    }
}
