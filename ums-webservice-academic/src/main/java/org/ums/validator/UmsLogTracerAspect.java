package org.ums.validator;

/**
 * Created by Monjur-E-Morshed on 08-Aug-17.
 */

/*
 * @Aspect
 * 
 * @Component public class UmsLogTracerAspect {
 * 
 * Logger mLogger = LoggerFactory.getLogger(UmsLogTracerAspect.class);
 * 
 * @Autowired private KafkaTemplate<String, String> mKafkaTemplate;
 * 
 * @Pointcut("within(org.ums.manager.*)") public void inWebLayer() {}
 * 
 * @After("execution(* org.ums.manager..*(..))") public void generateLog(JoinPoint pJoinPoint) {
 * 
 * System.out.println("***************"); System.out.println("logger---->" +
 * pJoinPoint.getSignature().toString()); mLogger.trace("the logger******************");
 * 
 * mKafkaTemplate.send("ums_logger", "the method accessed --> ");
 * 
 * }
 * 
 * }
 */
