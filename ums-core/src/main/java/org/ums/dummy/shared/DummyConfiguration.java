package org.ums.dummy.shared;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class DummyConfiguration {
  @Bean
  @Scope("singleton")
  public DummyService getDummyService() {
    return new DummyServiceImpl();
  }
}
