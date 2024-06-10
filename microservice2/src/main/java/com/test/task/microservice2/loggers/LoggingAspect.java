package com.test.task.microservice2.loggers;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("(@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PatchMapping) || @annotation(org.springframework.web.bind.annotation.DeleteMapping)) " +
            "&& @annotation(com.test.task.microservice2.loggers.LogControllerMethod)")
    public Object logControllerMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("Starting execution method {} with args {}", methodName, Arrays.toString(joinPoint.getArgs()));
        Object result = joinPoint.proceed();
        log.info("Finished execution method {}", methodName);

        return result;
    }
}
