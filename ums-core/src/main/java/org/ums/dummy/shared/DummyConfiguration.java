package org.ums.dummy.shared;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.ums.dummy.shared.dao.UserDao;
import org.ums.dummy.shared.dao.UserDaoImpl;

import javax.sql.DataSource;

@Configuration
public class DummyConfiguration {

  @Autowired
  DataSource dataSource;

  @Bean
  @Scope("singleton")
  public DummyService getDummyService() {
    return new DummyServiceImpl();
  }

  @Bean
  public UserDao getUserDao() {
    return new UserDaoImpl(dataSource);
  }


}
