package org.ums.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.ums.annotations.TwoFA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ifti on 16-Dec-17.
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class TwoFaAspect {
  private static final Logger logger = LoggerFactory.getLogger(TwoFaAspect.class);

  // for any method with @LogDuration, no matter what the return type, name, or arguments are, call
  // this method
  // @Before("execution(@org.ums.annotations.TwoFA * *(..)) && @annotation(twoFA)")
  @Before("@annotation(org.ums.annotations.TwoFA)")
  public void logDuration(ProceedingJoinPoint joinPoint) throws Throwable {

    // capture the start time
    long startTime = System.currentTimeMillis();

    // execute the method and get the result
    Object result = joinPoint.proceed();

    // capture the end time
    long endTime = System.currentTimeMillis();

    // calculate the duration and print results
    long duration = endTime - startTime;
    // System.out.println(logDurationAnnotation.value()+": "+duration+"ms"); //you should use a
    // logger

    logger.info("Hello how are you.......");
    System.out.println("Execution goes here.......");

    // return the result to the caller
    // return result;
  }

}
