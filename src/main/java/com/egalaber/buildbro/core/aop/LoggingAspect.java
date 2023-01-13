package com.egalaber.buildbro.core.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Slf4j
@Aspect
public class LoggingAspect {

    @Pointcut("within(com.egalaber.buildbro.core.service.* || com.egalaber.buildbro.api.v1.impl.*)")
    void loggingPointcut() {
    }

    @AfterThrowing(pointcut = "loggingPointcut()", throwing = "e")
    void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("{}.{}() throws: {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), e.getClass().getName());
    }

    @Around("loggingPointcut()")
    Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        try {
            Object result = joinPoint.proceed();
            log.info("{}.{}(): {}", joinPoint.getSignature().getDeclaringTypeName(), methodName, result);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getDeclaringTypeName(), methodName);
            throw e;
        }
    }

}
