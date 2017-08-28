package org.ums.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.ums.context.AppContext;
import org.ums.domain.model.dto.logger.ActivityLogger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Aspect
public class UmsLogTracerAspect {

  // Logger mLogger = LoggerFactory.getLogger(UmsLogTracerAspect.class);
  ApplicationContext applicationContext = AppContext.getApplicationContext();

  KafkaTemplate<String, String> kafkaTemplate = applicationContext.getBean("kafkaTemplate", KafkaTemplate.class);

  // @Autowired
  // KafkaTemplate<String, String> kafkaTemplate;

  /*
   * @Autowired KafkaTemplate<String, String> kafkaTemplate;
   */

  /*
   * public static UmsLogTracerAspect aspectOf() { return
   * AppContext.getApplicationContext().getBean("umsLogTracerAspect", UmsLogTracerAspect.class); }
   */

  @After("execution(* org.ums.academic.resource..*.*(..)) && args(httpServletRequest, userAgent,..) ")
  public void generateLog(JoinPoint pJoinPoint, HttpServletRequest httpServletRequest, String userAgent)
      throws Exception {
    HttpServletRequest mHttpServletRequest = httpServletRequest;

    /*
     * System.out.println("***************"); System.out.println("logger---->" +
     * pJoinPoint.getSignature().toString());
     * 
     * System.out.println("Url: " + mHttpServletRequest.getRequestURI());
     * System.out.println("Authentication Type: " + mHttpServletRequest.getRemoteUser());
     * System.out.println("Location: " + mHttpServletRequest.getHeader("Location"));
     * System.out.println("Ip Address: " + mHttpServletRequest.getRemoteAddr());
     */

    ActivityLogger activityLogger = new ActivityLogger();
    activityLogger.setUserId(mHttpServletRequest.getRemoteUser());
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    activityLogger.setAccessTime(timestamp);
    activityLogger.setDevice(userAgent);
    activityLogger.setClassName(pJoinPoint.getTarget().toString());
    activityLogger.setMethodName(pJoinPoint.getSignature().toString());
    activityLogger.setIpAddress(mHttpServletRequest.getRemoteAddr());
    activityLogger.setException("");
    ObjectMapper mapper = new ObjectMapper();
    String jsonToString = mapper.writeValueAsString(activityLogger);
    kafkaTemplate.send("ums_logger", jsonToString);
  }

}
