package org.ums.microservice;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.ums.microservice.configuration.ServiceContext;

public class Main {
  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(ServiceContext.class);
    if(args.length == 0) {
      Map<String, Service> serviceBeans = context.getBeansOfType(Service.class);
      serviceBeans.values().forEach(Service::start);
    }
    else {
      for(String serviceName : args) {
        context.getBean(serviceName, Service.class).start();
      }
    }
  }
}
