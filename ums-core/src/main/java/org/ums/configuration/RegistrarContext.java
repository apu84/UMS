package org.ums.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ums.cache.CacheFactory;
import org.ums.cache.registrar.employee.*;
import org.ums.generator.IdGenerator;
import org.ums.manager.registrar.employee.*;
import org.ums.persistent.dao.registrar.employee.*;
import org.ums.statistics.JdbcTemplateFactory;

@Configuration
public class RegistrarContext {
  @Autowired
  CacheFactory mCacheFactory;

  @Autowired
  JdbcTemplateFactory mTemplateFactory;

  @Autowired
  IdGenerator mIdGenerator;

  @Bean
  AcademicInformationManager academicInformationManager() {
    AcademicInformationCache academicInformationCache = new AcademicInformationCache(mCacheFactory.getCacheManager());
    academicInformationCache.setManager(new PersistentAcademicInformationDao(mTemplateFactory.getJdbcTemplate()));
    return academicInformationCache;
  }

  @Bean
  AwardInformationManager awardInformationManager() {
    AwardInformationCache awardInformationCache = new AwardInformationCache(mCacheFactory.getCacheManager());
    awardInformationCache.setManager(new PersistentAwardInformationDao(mTemplateFactory.getJdbcTemplate()));
    return awardInformationCache;
  }

  @Bean
  EmployeeInformationManager employeeInformationManager() {
    EmployeeInformationCache employeeInformationCache = new EmployeeInformationCache(mCacheFactory.getCacheManager());
    employeeInformationCache.setManager(new PersistentEmployeeInformationDao(mTemplateFactory.getJdbcTemplate()));
    return employeeInformationCache;
  }

  @Bean
  ExperienceInformationManager experienceInformationManager() {
    ExperienceInformationCache experienceInformationCache =
        new ExperienceInformationCache(mCacheFactory.getCacheManager());
    experienceInformationCache.setManager(new PersistentExperienceInformationDao(mTemplateFactory.getJdbcTemplate()));
    return experienceInformationCache;
  }

  @Bean
  PersonalInformationManager personalInformationManager() {
    PersonalInformationCache personalInformationCache = new PersonalInformationCache(mCacheFactory.getCacheManager());
    personalInformationCache.setManager(new PersistentPersonalInformationDao(mTemplateFactory.getJdbcTemplate()));
    return personalInformationCache;
  }

  @Bean
  PublicationInformationManager publicationInformationManager() {
    PublicationInformationCache publicationInformationCache =
        new PublicationInformationCache(mCacheFactory.getCacheManager());
    publicationInformationCache.setManager(new PersistentPublicationInformationDao(mTemplateFactory.getJdbcTemplate()));
    return publicationInformationCache;
  }

  @Bean
  TrainingInformationManager trainingInformationManager() {
    TrainingInformationCache trainingInformationCache = new TrainingInformationCache(mCacheFactory.getCacheManager());
    trainingInformationCache.setManager(new PersistentTrainingInformationDao(mTemplateFactory.getJdbcTemplate()));
    return trainingInformationCache;
  }

}
