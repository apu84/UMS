package org.ums.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ums.cache.CacheFactory;
import org.ums.cache.common.DivisionCache;
import org.ums.cache.meeting.AgendaResolutionCache;
import org.ums.cache.meeting.ScheduleCache;
import org.ums.cache.registrar.*;
import org.ums.generator.IdGenerator;
import org.ums.manager.common.DivisionManager;
import org.ums.manager.meeting.AgendaResolutionManager;
import org.ums.manager.meeting.ScheduleManager;
import org.ums.manager.registrar.*;
import org.ums.persistent.dao.common.PersistentDivisionDao;
import org.ums.persistent.dao.meeting.PersistentAgendaResolutionDao;
import org.ums.persistent.dao.meeting.PersistentScheduleDao;
import org.ums.persistent.dao.registrar.*;
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
    return new PersistentPersonalInformationDao(mTemplateFactory.getJdbcTemplate());
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
    PersistentAgendaResolutionDao agendaResolutionDao =
        new PersistentAgendaResolutionDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
    agendaResolutionTransaction.setManager(agendaResolutionDao);

    AgendaResolutionCache agendaResolutionCache = new AgendaResolutionCache(mCacheFactory.getCacheManager());
    agendaResolutionCache.setManager(agendaResolutionTransaction);
    return agendaResolutionCache;
  }
}
