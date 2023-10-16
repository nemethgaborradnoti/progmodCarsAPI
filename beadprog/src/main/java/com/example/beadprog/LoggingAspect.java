package com.example.beadprog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logInfo = LoggerFactory.getLogger("fileLoggerInfo");
    private static final Logger logError = LoggerFactory.getLogger("fileLoggerError");

    @Before("execution(* com.example.beadprog.CarController.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        logInfo.info("Metódus futtatása a(z) " + methodName + " előtt a(z) " + className + " osztályban.");
    }

    @AfterReturning("execution(* com.example.beadprog.CarController.*(..))")
    public void logAfterReturning(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logInfo.info("Metódus futtatása befejeződött a(z) " + methodName + " metódus után");
    }

    @AfterThrowing(pointcut = "execution(* com.example.beadprog.CarController.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        logError.error("Kivétel dobása a(z) " + methodName + " metódusban. Kivétel: " + ex.getMessage());
    }
}