package org.example.nbcam_addvanced_1.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimeCheckAop {

    @Around("execution(* org.example.nbcam_addvanced_1.user.service..*(..))")
    public Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // 실제 메서드 실행 -> Filter에서 doFilter 와 비슷함.

        long end = System.currentTimeMillis();
        log.info("[AOP] {} 실행됨 in {}ms" , joinPoint.getSignature() , end - start);

        return result;
    }
}
