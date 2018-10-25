package com.matrangola.gadgets.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LongSummaryStatistics;
import java.util.Map;

@Aspect
@Component
public class ProfileAspect {

    private Map<String, LongSummaryStatistics> methodStats = new HashMap<>();

    @Around("@within(Profiler) || @annotation(Profiler)")
    public Object profilerExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object retVal = joinPoint.proceed();
        long end = System.currentTimeMillis();

        String key = joinPoint.getSignature().toLongString();
        LongSummaryStatistics stat = methodStats.computeIfAbsent(key, s-> new LongSummaryStatistics());
        stat.accept(end - begin);

        System.out.println("Profiler: " + key + ": " + stat.getAverage());
        return retVal;
    }
}
