package org.ums.accounts.tracer;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Monjur-E-Morshed on 24-Feb-18.
 */
@Aspect
@Component
public class AccountAspect {
  Logger mLogger = LoggerFactory.getLogger(AccountAspect.class);

  // @After("execution(* org.ums.accounts.resource..*(..))")
  // public void generateLog(JoinPoint pJoinPoint) {
  // System.out.println("****");
  // }

  @Before("@annotation(log) && execution(* org.ums.accounts..*(..)) && args(pHttpServletRequest,..)")
  public void callAt(JoinPoint pJoinPoint, HttpServletRequest pHttpServletRequest, AccountLogMessage log)
      throws Throwable, Exception {
    System.out.println("****");
    System.out.println(log.message());
    System.out.println(pHttpServletRequest.getPathInfo());
    // System.out.println(pHttpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
    // pJoinPoint.proceed();
  }
}
