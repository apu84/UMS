package org.ums.configuration;

import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.ums.cache.*;
import org.ums.cache.common.*;
import org.ums.formatter.DateFormat;
import org.ums.generator.IdGenerator;
import org.ums.manager.*;
import org.ums.manager.common.*;
import org.ums.mapper.Mapper;
import org.ums.mapper.MapperDao;
import org.ums.mapper.MapperEntry;
import org.ums.persistent.dao.*;
import org.ums.persistent.dao.common.*;
import org.ums.services.NotificationGenerator;
import org.ums.services.NotificationGeneratorImpl;
import org.ums.solr.repository.transaction.EmployeeTransaction;
import org.ums.statistics.DBLogger;
import org.ums.statistics.JdbcTemplateFactory;
import org.ums.statistics.QueryLogger;
import org.ums.statistics.TextLogger;
import org.ums.twofa.*;
import org.ums.usermanagement.permission.*;
import org.ums.usermanagement.role.PersistentRoleDao;
import org.ums.usermanagement.role.RoleCache;
import org.ums.usermanagement.role.RoleManager;
import org.ums.usermanagement.role.RolePermissionResolver;
import org.ums.usermanagement.user.PersistentUserDao;
import org.ums.usermanagement.user.UserCache;
import org.ums.usermanagement.user.UserManager;
import org.ums.usermanagement.userView.PersistentUserViewDao;
import org.ums.usermanagement.userView.UserViewCache;
import org.ums.usermanagement.userView.UserViewManager;
import org.ums.util.Constants;

@Configuration
public class CoreContext {
  @Autowired
  CacheFactory mCacheFactory;

  @Autowired
  JdbcTemplateFactory mTemplateFactory;

  @Autowired
  IdGenerator mIdGenerator;

  @Autowired
  UMSConfiguration mUMSConfiguration;

  @Autowired
  @Qualifier("mongoTemplate")
  @Lazy
  MongoTemplate mMongoOperations;

  @Autowired
  @Qualifier("dummyEmail")
  String emailSender;

  @Bean
  @Lazy
  EmployeeManager employeeManager() {
    EmployeeTransaction employeeTransaction = new EmployeeTransaction();
    PersistentEmployeeDao persistentEmployeeDao = new PersistentEmployeeDao(mTemplateFactory.getJdbcTemplate());
    employeeTransaction.setManager(persistentEmployeeDao);
    EmployeeCache employeeCache = new EmployeeCache(mCacheFactory.getCacheManager());
    employeeCache.setManager(employeeTransaction);
    return employeeCache;
  }

  @Bean
  StudentManager studentManager() {
    StudentCache studentCache = new StudentCache(mCacheFactory.getCacheManager());
    studentCache.setManager(new PersistentStudentDao(mTemplateFactory.getJdbcTemplate()));
    return studentCache;
  }

  @Bean
  UserManager userManager() {
    UserCache userCache = new UserCache(mCacheFactory.getCacheManager());
    userCache.setManager(new PersistentUserDao(mTemplateFactory.getJdbcTemplate()));
    UserPropertyResolver userPropertyResolver = new UserPropertyResolver(employeeManager(), studentManager());
    userPropertyResolver.setManager(userCache);
    return userPropertyResolver;
  }

  @Bean
  AppSettingManager appSettingManager() {
    AppSettingCache appSettingCache = new AppSettingCache(mCacheFactory.getCacheManager());
    appSettingCache.setManager(new PersistentAppSettingDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return appSettingCache;
  }

  @Bean
  AdditionalRolePermissionsManager additionalRolePermissionsManager() {
    AdditionalRolePermissionsCache additionalRolePermissionsCache =
        new AdditionalRolePermissionsCache(mCacheFactory.getCacheManager());
    additionalRolePermissionsCache.setManager(new AdditionalRolePermissionsDao(mTemplateFactory.getJdbcTemplate(),
        mIdGenerator));
    return additionalRolePermissionsCache;
  }

  @Bean
  ParameterManager parameterManager() {
    return new PersistentParameterDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  ParameterSettingManager parameterSettingManager() {
    return new PersistentParameterSettingDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  BearerAccessTokenManager bearerAccessTokenManager() {
    BearerAccessTokenCache bearerAccessTokenCache = new BearerAccessTokenCache(mCacheFactory.getCacheManager());
    bearerAccessTokenCache.setManager(new BearerAccessTokenDao(mTemplateFactory.getJdbcTemplate()));
    return bearerAccessTokenCache;
  }

  @Bean
  QueryLogger dbLogger() {
    return new DBLogger();
  }

  @Bean
  QueryLogger textLogger() {
    return new TextLogger();
  }

  @Bean
  @Lazy
  NotificationManager notificationManager() {
    return mUMSConfiguration.isEnableObjectDb() ? new PersistentObjectNotificationDao(mMongoOperations)
        : new PersistentNotificationDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  @Lazy
  NotificationGenerator notificationGenerator() {
    return new NotificationGeneratorImpl(notificationManager(), mIdGenerator);
  }

  @Bean
  TaskStatusManager taskStatusManager() {
    return new PersistentTaskStatusDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  NavigationManager navigationManager() {
    NavigationByPermissionResolver navigationByPermissionResolver =
        new NavigationByPermissionResolver(permissionResolver());
    NavigationCache navigationCache = new NavigationCache(mCacheFactory.getCacheManager());
    navigationCache.setManager(new PersistentNavigationDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    navigationByPermissionResolver.setManager(navigationCache);
    return navigationByPermissionResolver;
  }

  @Bean
  @Lazy
  PermissionManager permissionManager() {
    PermissionCache permissionCache = new PermissionCache(mCacheFactory.getCacheManager());
    permissionCache.setManager(new PersistentPermissionDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return permissionCache;
  }

  @Bean
  UserGuideManager userGuideManager() {
    UserGuideCache userGuideCache = new UserGuideCache(mCacheFactory.getCacheManager());
    userGuideCache.setManager(new PersistentUserGuideDao(mTemplateFactory.getJdbcTemplate()));
    return userGuideCache;
  }

  @Bean
  RoleManager roleManager() {
    RoleCache roleCache = new RoleCache(mCacheFactory.getCacheManager());
    RolePermissionResolver rolePermissionResolver = new RolePermissionResolver(permissionResolver());
    rolePermissionResolver.setManager(new PersistentRoleDao(mTemplateFactory.getJdbcTemplate()));
    roleCache.setManager(rolePermissionResolver);
    return roleCache;
  }

  @Bean
  DateFormat getGenericDateFormat() {
    return new DateFormat(Constants.DATE_FORMAT);
  }

  @Bean
  CountryManager countryManager() {
    CountryCache countryCache = new CountryCache(mCacheFactory.getCacheManager());
    countryCache.setManager(new PersistentCountryDao(mTemplateFactory.getJdbcTemplate()));
    return countryCache;
  }

  @Bean
  DivisionManager divisionManager() {
    DivisionCache divisionCache = new DivisionCache(mCacheFactory.getCacheManager());
    divisionCache.setManager(new PersistentDivisionDao(mTemplateFactory.getJdbcTemplate()));
    return divisionCache;
  }

  @Bean
  DistrictManager districtManager() {
    DistrictCache districtCache = new DistrictCache(mCacheFactory.getCacheManager());
    districtCache.setManager(new PersistentDistrictDao(mTemplateFactory.getJdbcTemplate()));
    return districtCache;
  }

  @Bean
  LmsTypeManager lmsTypeManager() {
    LmsTypeCache lmsTypeCache = new LmsTypeCache(mCacheFactory.getCacheManager());
    lmsTypeCache.setManager(new PersistentLmsTypeDao(mTemplateFactory.getJdbcTemplate()));
    return lmsTypeCache;
  }

  @Bean
  AttachmentManager attachmentManager() {
    AttachmentCache attachmentCache = new AttachmentCache(mCacheFactory.getCacheManager());
    attachmentCache.setManager(new PersistentAttachmentDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return attachmentCache;
  }

  @Bean
  LmsApplicationManager lmsApplicationManager() {
    LmsApplicationCache lmsApplicationCache = new LmsApplicationCache(mCacheFactory.getCacheManager());
    lmsApplicationCache.setManager(new PersistentLmsApplicationDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return lmsApplicationCache;
  }

  @Bean
  LmsAppStatusManager lmsAppStatusManager() {
    LmsAppStatusCache lmsAppStatusCache = new LmsAppStatusCache(mCacheFactory.getCacheManager());
    lmsAppStatusCache.setManager(new PersistentLmsAppStatusDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return lmsAppStatusCache;
  }

  @Bean
  HolidayTypeManager holidayTypeManager() {
    HolidayTypeCache holidayTypeCache = new HolidayTypeCache(mCacheFactory.getCacheManager());
    holidayTypeCache.setManager(new PersistentHolidayTypeDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return holidayTypeCache;
  }

  @Bean
  HolidaysManager holidaysManager() {
    HolidaysCache holidaysCache = new HolidaysCache(mCacheFactory.getCacheManager());
    holidaysCache.setManager(new PersistentHolidaysDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return holidaysCache;
  }

  @Bean
  ThanaManager thanaManager() {
    ThanaCache thanaCache = new ThanaCache(mCacheFactory.getCacheManager());
    thanaCache.setManager(new PersistentThanaDao(mTemplateFactory.getJdbcTemplate()));
    return thanaCache;
  }

  @Bean
  EmploymentTypeManager employmentTypeManager() {
    EmploymentTypeCache employmentTypeCache = new EmploymentTypeCache(mCacheFactory.getCacheManager());
    employmentTypeCache.setManager(new PersistentEmploymentTypeDao(mTemplateFactory.getJdbcTemplate()));
    return employmentTypeCache;
  }

  @Bean
  DesignationManager designationManager() {
    DesignationCache designationCache = new DesignationCache(mCacheFactory.getCacheManager());
    designationCache.setManager(new PersistentDesignationDao(mTemplateFactory.getJdbcTemplate()));
    return designationCache;
  }

  @Bean
  BloodGroupManager bloodGroupManager() {
    BloodGroupCache bloodGroupCache = new BloodGroupCache(mCacheFactory.getCacheManager());
    bloodGroupCache.setManager(new PersistentBloodGroupDao(mTemplateFactory.getJdbcTemplate()));
    return bloodGroupCache;
  }

  @Bean
  NationalityManager nationalityManager() {
    NationalityCache nationalityCache = new NationalityCache(mCacheFactory.getCacheManager());
    nationalityCache.setManager(new PersistentNationalityDao(mTemplateFactory.getJdbcTemplate()));
    return nationalityCache;
  }

  @Bean
  ReligionManager religionManager() {
    ReligionCache religionCache = new ReligionCache(mCacheFactory.getCacheManager());
    religionCache.setManager(new PersistentReligionDao(mTemplateFactory.getJdbcTemplate()));
    return religionCache;
  }

  @Bean
  RelationTypeManager relationTypeManager() {
    RelationTypeCache relationTypeCache = new RelationTypeCache(mCacheFactory.getCacheManager());
    relationTypeCache.setManager(new PersistentRelationTypeDao(mTemplateFactory.getJdbcTemplate()));
    return relationTypeCache;
  }

  @Bean
  MaritalStatusManager maritalStatusManager() {
    MaritalStatusCache maritalStatusCache = new MaritalStatusCache(mCacheFactory.getCacheManager());
    maritalStatusCache.setManager(new PersistentMaritalStatusDao(mTemplateFactory.getJdbcTemplate()));
    return maritalStatusCache;
  }

  @Bean
  AreaOfInterestManager areaOfInterestManager() {
    AreaOfInterestCache areaOfInterestCache = new AreaOfInterestCache(mCacheFactory.getCacheManager());
    areaOfInterestCache.setManager(new PersistentAreaOfInterestDao(mTemplateFactory.getJdbcTemplate()));
    return areaOfInterestCache;
  }

  @Bean
  AcademicDegreeManager academicDegreeManager() {
    AcademicDegreeCache academicDegreeCache = new AcademicDegreeCache(mCacheFactory.getCacheManager());
    academicDegreeCache.setManager(new PersistentAcademicDegreeDao(mTemplateFactory.getJdbcTemplate()));
    return academicDegreeCache;
  }

  @Bean
  UserViewManager userViewManager() {
    UserViewCache userViewCache = new UserViewCache(mCacheFactory.getCacheManager());
    userViewCache.setManager(new PersistentUserViewDao(mTemplateFactory.getJdbcTemplate()));
    return userViewCache;
  }

  @Bean
  PermissionResolver permissionResolver() {
    return new WildcardPermissionResolver();
  }

  @Bean
  Mapper<String, MapperEntry> mapper() {
    return new MapperDao<String, MapperEntry>(mUMSConfiguration, "twofadata", "twofa");
  }

  @Bean
  TwoFATokenManager twoFATokenManager() {
    return new TwoFATokenDao(mTemplateFactory.getJdbcTemplate(), mUMSConfiguration, mIdGenerator);
  }

  @Bean
  TwoFATokenEmailSender twoFATokenEmailSender() {
    return new TwoFATokenEmailSender();
  }

  @Bean
  TwoFATokenGenerator twoFATokenGenerator() {
    return new TwoFATokenGeneratorImpl(twoFATokenManager(), twoFATokenEmailSender(), userManager());
  }
}
