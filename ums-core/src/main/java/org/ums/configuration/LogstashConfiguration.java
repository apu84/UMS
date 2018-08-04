// package org.ums.configuration;
//
// import ch.qos.logback.classic.AsyncAppender;
// import ch.qos.logback.classic.LoggerContext;
// import net.logstash.logback.appender.LogstashSocketAppender;
// import net.logstash.logback.appender.LogstashTcpSocketAppender;
// import net.logstash.logback.stacktrace.ShortenedThrowableConverter;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Configuration;
//
// import javax.annotation.PostConstruct;
//
// @Configuration
// public class LogstashConfiguration {
// private final Logger log = LoggerFactory.getLogger(LogstashConfiguration.class);
//
// private LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
//
// // @Value("${application.name}")
// private String appName = "iums";
//
// @Value("${application.logger.logstash.enabled}")
// private boolean enabled;
//
// @Value("${application.logger.logstash.host}")
// private String host;
// @Value("${application.logger.logstash.port}")
// private int port;
// @Value("${application.logger.logstash.queueSize}")
// private int queueSize;
//
// @PostConstruct
// private void init() {
// if(enabled) {
// addLogstashAppender();
// }
// }
//
// public void addLogstashAppender() {
// log.info("Initializing Logstash logging");
//
// LogstashSocketAppender logstashAppender = new LogstashSocketAppender();
// logstashAppender.setName("LOGSTASH");
// logstashAppender.setContext(context);
//
// String customFields = "{\"app_name\":\"" + appName + "\"}";
//
// // Set the Logstash appender config from properties
// logstashAppender.setSyslogHost(host);
// logstashAppender.setPort(port);
// logstashAppender.setCustomFields(customFields);
//
// // Limit the maximum length of the forwarded stacktrace so that it won't exceed the 8KB UDP
// // limit of logstash
// ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
// throwableConverter.setMaxLength(7500);
// throwableConverter.setRootCauseFirst(true);
// logstashAppender.setThrowableConverter(throwableConverter);
//
// logstashAppender.start();
//
// // Wrap the appender in an Async appender for performance
// AsyncAppender asyncLogstashAppender = new AsyncAppender();
// asyncLogstashAppender.setContext(context);
// asyncLogstashAppender.setName("ASYNC_LOGSTASH");
// asyncLogstashAppender.setQueueSize(queueSize);
// asyncLogstashAppender.addAppender(logstashAppender);
// asyncLogstashAppender.start();
// // for(Logger logger : context.getLoggerList()) {
// // System.out.println(logger.getName());
// // }
// context.getLogger("ROOT").addAppender(asyncLogstashAppender);
// }
// }
