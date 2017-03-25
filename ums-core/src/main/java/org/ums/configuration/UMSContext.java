package org.ums.configuration;

import org.apache.shiro.mgt.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.ums.cache.CacheFactory;
import org.ums.cachewarmer.AutoCacheWarmer;
import org.ums.cachewarmer.CacheWarmerManagerImpl;
import org.ums.manager.CacheWarmerManager;

@Configuration
@EnableAsync
@EnableScheduling
@EnableSolrRepositories(basePackages = "org.ums.solr.repository", multicoreSupport = true)
@Import({CoreContext.class, AcademicContext.class, AdmissionContext.class, FeeContext.class, LibraryContext.class,
    SolrContext.class})
public class UMSContext {
  @Autowired
  CacheFactory mCacheFactory;

  @Autowired
  UMSConfiguration mUMSConfiguration;

  @Autowired
  @Qualifier("backendSecurityManager")
  SecurityManager mSecurityManager;

  @Autowired
  CoreContext mCoreContext;

  @Autowired
  AcademicContext mAcademicContext;

  @Bean
  CacheWarmerManager cacheWarmerManager() {
    return new CacheWarmerManagerImpl(mSecurityManager, mCacheFactory, mUMSConfiguration,
        mAcademicContext.departmentManager(), mCoreContext.roleManager(), mCoreContext.permissionManager(),
        mCoreContext.bearerAccessTokenManager(), mCoreContext.additionalRolePermissionsManager(),
        mCoreContext.navigationManager(), mCoreContext.employeeManager(), mAcademicContext.programTypeManager(),
        mAcademicContext.programManager(), mAcademicContext.semesterManager(), mAcademicContext.syllabusManager(),
        mAcademicContext.courseGroupManager(), mAcademicContext.equivalentCourseManager(),
        mAcademicContext.teacherManager(), mAcademicContext.courseTeacherManager(), mAcademicContext.examinerManager(),
        mCoreContext.studentManager(), mAcademicContext.studentRecordManager(), mAcademicContext.classRoomManager(),
        mAcademicContext.courseManager(), mAcademicContext.marksSubmissionStatusManager(), mCoreContext.userManager());
  }

  @Bean
  AutoCacheWarmer autoCacheWarmer() {
    return new AutoCacheWarmer(cacheWarmerManager());
  }
}
