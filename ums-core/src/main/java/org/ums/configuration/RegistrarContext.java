package org.ums.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ums.cache.CacheFactory;
import org.ums.cache.DeptDesignationRoleMapCache;
import org.ums.cache.DesignationRoleMapCache;
import org.ums.cache.meeting.AgendaResolutionCache;
import org.ums.cache.meeting.ScheduleCache;
import org.ums.generator.IdGenerator;
import org.ums.manager.DeptDesignationRoleMapManager;
import org.ums.manager.DesignationRoleMapManager;
import org.ums.manager.meeting.AgendaResolutionManager;
import org.ums.manager.meeting.ScheduleManager;
import org.ums.persistent.dao.PersistentDeptDesignationRoleMapDao;
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
  DeptDesignationRoleMapManager deptDesignationMapManager() {
    DeptDesignationRoleMapCache deptDesignationRoleMapCache =
        new DeptDesignationRoleMapCache(mCacheFactory.getCacheManager());
    deptDesignationRoleMapCache.setManager(new PersistentDeptDesignationRoleMapDao(mTemplateFactory.getJdbcTemplate()));
    return deptDesignationRoleMapCache;
  }
}
