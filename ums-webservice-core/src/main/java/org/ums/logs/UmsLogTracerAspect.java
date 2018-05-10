package org.ums.logs;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HeaderParam;

@Aspect
@Component
/**
 * UMS resource logging aspect
 */
public class UmsLogTracerAspect {

  Logger mLogger = LoggerFactory.getLogger(UmsLogTracerAspect.class);

  // ******* POST *******//
  /*
   * @Before(
   * "@annotation(log) && execution(* org.ums..*(..)) &&  args(pHttpServletRequest, pJsonArray,..)")
   * public void callAt(JoinPoint pJoinPoint, HttpServletRequest pHttpServletRequest, JsonArray
   * pJsonArray, PostLog log) { printLog(pHttpServletRequest, log.message(), pJsonArray.toString());
   * }
   * 
   * @Before(
   * "@annotation(log) && execution(* org.ums..*(..)) &&  args(pHttpServletRequest, pJsonObject,..)"
   * ) public void callAt(JoinPoint pJoinPoint, HttpServletRequest pHttpServletRequest, JsonObject
   * pJsonObject, PostLog log) { printLog(pHttpServletRequest, log.message(),
   * pJsonObject.toString()); }
   */

  @Before("@annotation(log) && execution(* org.ums..*(..)) &&  args(pHttpServletRequest,pObject,..)")
  public void callAt(JoinPoint pJoinPoint, HttpServletRequest pHttpServletRequest, Object pObject, PostLog log) {
    printLog(pHttpServletRequest, log.message(), pObject.toString());
  }

  // ******* PUT *******//
  // @Before("@annotation(log) && execution(* org.ums..*(..)) &&  args(pHttpServletRequest,pJsonObject,..)")
  // public void callAt(JoinPoint pJoinPoint, HttpServletRequest pHttpServletRequest, JsonObject
  // pJsonObject, PutLog log) {
  // printLog(pHttpServletRequest, log.message(), pJsonObject.toString());
  // }
  //
  // @Before("@annotation(log) && execution(* org.ums..*(..)) &&  args(pHttpServletRequest,pJsonArray,..)")
  // public void callAt(JoinPoint pJoinPoint, HttpServletRequest pHttpServletRequest, JsonArray
  // pJsonArray, PutLog log) {
  // printLog(pHttpServletRequest, log.message(), pJsonArray.toString());
  // }

  @Before("@annotation(log) && execution(* org.ums..*(..)) &&  args(pHttpServletRequest,pObject,..)")
  public void callAt(JoinPoint pJoinPoint, HttpServletRequest pHttpServletRequest, Object pObject, PutLog log) {
    printLog(pHttpServletRequest, log.message(), pObject.toString());
  }

  // ******* DELETE *******//
  // @Before("@annotation(log) && execution(* org.ums..*(..)) &&  args(pHttpServletRequest,pJsonObject,..)")
  // public void callAt(JoinPoint pJoinPoint, HttpServletRequest pHttpServletRequest, JsonObject
  // pJsonObject, DeleteLog log) {
  // printLog(pHttpServletRequest, log.message(), pJsonObject.toString());
  // }
  //
  // @Before("@annotation(log) && execution(* org.ums..*(..)) &&  args(pHttpServletRequest,pJsonArray,..)")
  // public void callAt(JoinPoint pJoinPoint, HttpServletRequest pHttpServletRequest, JsonArray
  // pJsonArray, DeleteLog log) {
  // printLog(pHttpServletRequest, log.message(), pJsonArray.toString());
  // }

  @Before("@annotation(log) && execution(* org.ums..*(..)) &&  args(pHttpServletRequest,pObject,..)")
  public void callAt(JoinPoint pJoinPoint, HttpServletRequest pHttpServletRequest, Object pObject, DeleteLog log) {
    printLog(pHttpServletRequest, log.message(), pObject.toString());
  }

  // ******* GET *******//
  @Before("@annotation(log) && execution(* org.ums..*(..)) &&  args(pHttpServletRequest,..)")
  public void callGet(JoinPoint pJoinPoint, HttpServletRequest pHttpServletRequest, GetLog log) {
    printLog(pHttpServletRequest, log.message(), null);
  }

  private void printLog(HttpServletRequest httpServletRequest, String message, String requestBody) {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    HttpServletRequest mapper = new ContentCachingRequestWrapper(httpServletRequest);
    if(requestBody != null)
      mLogger.info("[{}]: Resource: {}; URL: {}; Payload: {} ", userId, message, mapper.getRequestURI(), requestBody);
    else
      mLogger.info("[{}]: Resource: {}; URL:{} ", userId, message, mapper.getRequestURI());
  }

}
