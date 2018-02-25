package org.ums.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ums.cache.CacheFactory;
import org.ums.cache.DeptDesignationMapCache;
import org.ums.cache.DesignationRoleMapCache;
import org.ums.cache.meeting.AgendaResolutionCache;
import org.ums.cache.meeting.ScheduleCache;
import org.ums.employee.academic.AcademicInformationManager;
import org.ums.employee.academic.PersistentAcademicInformationDao;
import org.ums.employee.additional.AdditionalInformationManager;
import org.ums.employee.additional.AreaOfInterestInformationManager;
import org.ums.employee.additional.PersistentAdditionalInformationDao;
import org.ums.employee.additional.PersistentAreaOfInterestInformationDao;
import org.ums.employee.award.AwardInformationManager;
import org.ums.employee.award.PersistentAwardInformationDao;
import org.ums.employee.experience.ExperienceInformationManager;
import org.ums.employee.experience.PersistentExperienceInformationDao;
import org.ums.employee.personal.PersistentPersonalInformationDao;
import org.ums.employee.personal.PersonalInformationCache;
import org.ums.employee.personal.PersonalInformationManager;
import org.ums.employee.publication.PersistentPublicationInformationDao;
import org.ums.employee.publication.PublicationInformationManager;
import org.ums.employee.service.PersistentServiceInformationDao;
import org.ums.employee.service.PersistentServiceInformationDetailDao;
import org.ums.employee.service.ServiceInformationDetailManager;
import org.ums.employee.service.ServiceInformationManager;
import org.ums.employee.training.PersistentTrainingInformationDao;
import org.ums.employee.training.TrainingInformationManager;
import org.ums.generator.IdGenerator;
import org.ums.manager.DeptDesignationMapManager;
import org.ums.manager.DesignationRoleMapManager;
import org.ums.manager.meeting.AgendaResolutionManager;
import org.ums.manager.meeting.ScheduleManager;
import org.ums.persistent.dao.PersistentDeptDesignationMapDao;
import org.ums.persistent.dao.PersistentDesignationRoleMapDao;
import org.ums.persistent.dao.meeting.PersistentAgendaResolutionDao;
import org.ums.persistent.dao.meeting.PersistentScheduleDao;
import org.ums.solr.repository.transaction.meeting.AgendaResolutionTransaction;
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
    return new PersistentAcademicInformationDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  AwardInformationManager awardInformationManager() {
    return new PersistentAwardInformationDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  ServiceInformationManager serviceInformationManager() {
    return new PersistentServiceInformationDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  ServiceInformationDetailManager serviceInformationDetailManager() {
    return new PersistentServiceInformationDetailDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  ExperienceInformationManager experienceInformationManager() {
    return new PersistentExperienceInformationDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  PersonalInformationManager personalInformationManager() {
    PersonalInformationCache personalInformationCache = new PersonalInformationCache(mCacheFactory.getCacheManager());
    personalInformationCache.setManager(new PersistentPersonalInformationDao(mTemplateFactory.getJdbcTemplate()));
    return personalInformationCache;
  }

  @Bean
  PublicationInformationManager publicationInformationManager() {
    return new PersistentPublicationInformationDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  TrainingInformationManager trainingInformationManager() {
    return new PersistentTrainingInformationDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  AreaOfInterestInformationManager areaOfInterestInformationManager() {
    return new PersistentAreaOfInterestInformationDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  AdditionalInformationManager additionalInformationManager() {
    return new PersistentAdditionalInformationDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  ScheduleManager scheduleManager() {
    ScheduleCache scheduleCache = new ScheduleCache(mCacheFactory.getCacheManager());
    scheduleCache.setManager(new PersistentScheduleDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return scheduleCache;
  }

  @Bean
  AgendaResolutionManager agendaResolutionManager() {
    AgendaResolutionTransaction agendaResolutionTransaction = new AgendaResolutionTransaction();
    agendaResolutionTransaction.setManager(new PersistentAgendaResolutionDao(mTemplateFactory.getJdbcTemplate(),
        mIdGenerator));
    AgendaResolutionCache agendaResolutionCache = new AgendaResolutionCache(mCacheFactory.getCacheManager());
    agendaResolutionCache.setManager(agendaResolutionTransaction);
    return agendaResolutionCache;
  }

  @Bean
  DesignationRoleMapManager designationRoleMapManager() {
    DesignationRoleMapCache designationRoleMapCache = new DesignationRoleMapCache(mCacheFactory.getCacheManager());
    designationRoleMapCache.setManager(new PersistentDesignationRoleMapDao(mTemplateFactory.getJdbcTemplate()));
    return designationRoleMapCache;
  }

  @Bean
  DeptDesignationMapManager deptDesignationMapManager() {
    DeptDesignationMapCache deptDesignationMapCache = new DeptDesignationMapCache(mCacheFactory.getCacheManager());
    deptDesignationMapCache.setManager(new PersistentDeptDesignationMapDao(mTemplateFactory.getJdbcTemplate()));
    return deptDesignationMapCache;
  }
}
