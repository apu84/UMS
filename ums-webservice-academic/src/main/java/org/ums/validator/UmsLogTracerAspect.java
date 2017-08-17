package org.ums.validator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class UmsLogTracerAspect {

  Logger mLogger = LoggerFactory.getLogger(UmsLogTracerAspect.class);

  @Autowired
  private KafkaTemplate<String, String> mKafkaTemplate;

  @Pointcut("within(org.ums.manager.*)")
  public void inWebLayer() {}

  @After("execution(* org.ums.academic.resource..*.*(..)) && args(httpServletRequest,..) ")
  public void generateLog(JoinPoint pJoinPoint, HttpServletRequest httpServletRequest) {
    HttpServletRequest mHttpServletRequest = httpServletRequest;

    System.out.println("***************");
    System.out.println("logger---->" + pJoinPoint.getSignature().toString());

    System.out.println("Url: " + mHttpServletRequest.getRequestURI());
    System.out.println("Authentication Type: " + mHttpServletRequest.getAuthType());
    System.out.println("Ip Address: " + mHttpServletRequest.getRemoteAddr());

    // mKafkaTemplate.send("ums_logger", "the method accessed --> ");
    //
  }

}
