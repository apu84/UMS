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
import java.io.IOException;

@Aspect
@Component
public class UmsLogTracerAspect {

  Logger mLogger = LoggerFactory.getLogger(UmsLogTracerAspect.class);

  /*
   * This aspect is for post request
   */
  @Before("@annotation(log) && execution(* org.ums..*(..)) &&  args(pHttpServletRequest,pJsonArray,..)")
  public void callAt(JoinPoint pJoinPoint, HttpServletRequest pHttpServletRequest, JsonArray pJsonArray,
      UmsLogMessage log) throws Throwable, Exception, IOException {
    HttpServletRequest mapper = new ContentCachingRequestWrapper(pHttpServletRequest);
    mLogger.info("****UMS-Common-LOG****");
    mLogger.info(log.message());
    mLogger.info(mapper.getRequestURI());
    mLogger.info(pJsonArray.toString());
    mLogger.info(SecurityUtils.getSubject().getPrincipal().toString());
  }

  @Before("@annotation(log) && execution(* org.ums..*(..)) &&  args(pHttpServletRequest,pJsonObject,..)")
  public void callAt(JoinPoint pJoinPoint, HttpServletRequest pHttpServletRequest, JsonObject pJsonObject,
      UmsLogMessage log) throws Throwable, Exception, IOException {
    HttpServletRequest mapper = new ContentCachingRequestWrapper(pHttpServletRequest);
    mLogger.info("****UMS-Common-LOG****");
    mLogger.info(log.message());
    mLogger.info(mapper.getRequestURI());
    mLogger.info(pJsonObject.toString());
    mLogger.info(SecurityUtils.getSubject().getPrincipal().toString());
  }

  /*
   * This aspect for get request
   */
  @Before("@annotation(log) && execution(* org.ums..*(..)) &&  args(pHttpServletRequest,..)")
  public void callGet(JoinPoint pJoinPoint, HttpServletRequest pHttpServletRequest, UmsLogMessage log)
      throws Throwable, Exception, IOException {
    HttpServletRequest mapper = new ContentCachingRequestWrapper(pHttpServletRequest);
    mLogger.info("****UMS-Common-LOG****");
    mLogger.info(log.message());
    mLogger.info(mapper.getRequestURI());
    mLogger.info(SecurityUtils.getSubject().getPrincipal().toString());
  }

}
