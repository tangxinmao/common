package com.socool.soft.common.aspect;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 业务层日志记录
 */
@Component
@Aspect
public class ServiceLogAspect {
    private static Logger log = Logger.getLogger(ServiceLogAspect.class);

    // 切面
    private final String POINT_CUT = "execution(* com.socool.soft.service..*.*(..))";

    @Around(value = POINT_CUT)
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    	String uuid = UUID.randomUUID().toString();
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(uuid).append(")")
        	.append("Service start -> ").append(className)
        	.append(" @ ").append(methodName)
        	.append(", params: ");
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
        	sb.append(JSONObject.toJSONString(arg) + ",");
        }
        log.info(sb);
        
        long begin = System.currentTimeMillis();
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
            return result;
        } catch (Throwable e) {
            throw e;
        } finally {
        	long end = System.currentTimeMillis();
	        sb = new StringBuilder();
	        sb.append("(").append(uuid).append(")")
        		.append("Service end <- execution time: ").append(end - begin)
        		.append("ms, result: ").append(JSONObject.toJSONString(result));
	        log.info(sb);
        }
    }
}
