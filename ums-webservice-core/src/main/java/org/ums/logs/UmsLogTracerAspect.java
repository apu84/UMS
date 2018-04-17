package org.ums.logs;

/*
 * Created by Monjur-E-Morshed on 08-Aug-17.
 */

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
/**
 * UMS resource logging aspect
 */
public class UmsLogTracerAspect {

  Logger mLogger = LoggerFactory.getLogger(UmsLogTracerAspect.class);

  /*
   * This aspect is for post request
   */
  @Before("@annotation(log) && execution(* org.ums..*(..)) &&  args(pHttpServletRequest,pJsonArray,..)")
  public void callAt(JoinPoint pJoinPoint, HttpServletRequest pHttpServletRequest, JsonArray pJsonArray,
      UmsLogMessage log) {
    printLog(pHttpServletRequest, log.message(), pJsonArray.toString());
  }

  @Before("@annotation(log) && execution(* org.ums..*(..)) &&  args(pHttpServletRequest,pJsonObject,..)")
  public void callAt(JoinPoint pJoinPoint, HttpServletRequest pHttpServletRequest, JsonObject pJsonObject,
      UmsLogMessage log) {
    printLog(pHttpServletRequest, log.message(), pJsonObject.toString());
  }

  /*
   * This aspect for get request
   */
  @Before("@annotation(log) && execution(* org.ums..*(..)) &&  args(pHttpServletRequest,..)")
  public void callGet(JoinPoint pJoinPoint, HttpServletRequest pHttpServletRequest, UmsLogMessage log) {
    printLog(pHttpServletRequest, log.message(), null);
  }

  private void printLog(HttpServletRequest httpServletRequest, String message, String requestBody) {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    HttpServletRequest mapper = new ContentCachingRequestWrapper(httpServletRequest);
    //mLogger.info("[{}]: {} ", userId, message);
    if(requestBody != null)
      mLogger.info("[{}]: Resource: {}; URL: {}; Payload: {} ", userId,message, mapper.getRequestURI(), requestBody);
    else
      mLogger.info("[{}]: Resource: {}; URL:{} ", userId,message, mapper.getRequestURI());
  }

}
