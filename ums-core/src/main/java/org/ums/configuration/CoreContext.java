package org.ums.configuration;

import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.ums.bank.BankCache;
import org.ums.bank.BankDao;
import org.ums.bank.BankManager;
import org.ums.bank.branch.BranchCache;
import org.ums.bank.branch.BranchDao;
import org.ums.bank.branch.BranchManager;
import org.ums.bank.branch.user.BranchUserCache;
import org.ums.bank.branch.user.BranchUserDao;
import org.ums.bank.branch.user.BranchUserManager;
import org.ums.bank.branch.user.BranchUserPostTransaction;
import org.ums.bank.designation.BankDesignationCache;
import org.ums.bank.designation.BankDesignationDao;
import org.ums.bank.designation.BankDesignationManager;
import org.ums.cache.*;
import org.ums.cache.common.*;
import org.ums.ems.profilemanagement.academic.AcademicInformationManager;
import org.ums.ems.profilemanagement.academic.PersistentAcademicInformationDao;
import org.ums.ems.profilemanagement.additional.AdditionalInformationManager;
import org.ums.ems.profilemanagement.additional.AreaOfInterestInformationManager;
import org.ums.ems.profilemanagement.additional.PersistentAdditionalInformationDao;
import org.ums.ems.profilemanagement.additional.PersistentAreaOfInterestInformationDao;
import org.ums.ems.profilemanagement.award.AwardInformationManager;
import org.ums.ems.profilemanagement.award.PersistentAwardInformationDao;
import org.ums.ems.profilemanagement.experience.ExperienceInformationManager;
import org.ums.ems.profilemanagement.experience.PersistentExperienceInformationDao;
import org.ums.ems.profilemanagement.personal.PersistentPersonalInformationDao;
import org.ums.ems.profilemanagement.personal.PersonalInformationCache;
import org.ums.ems.profilemanagement.personal.PersonalInformationManager;
import org.ums.ems.profilemanagement.publication.PersistentPublicationInformationDao;
import org.ums.ems.profilemanagement.publication.PublicationInformationManager;
import org.ums.ems.profilemanagement.service.PersistentServiceInformationDao;
import org.ums.ems.profilemanagement.service.PersistentServiceInformationDetailDao;
import org.ums.ems.profilemanagement.service.ServiceInformationDetailManager;
import org.ums.ems.profilemanagement.service.ServiceInformationManager;
import org.ums.ems.profilemanagement.training.PersistentTrainingInformationDao;
import org.ums.ems.profilemanagement.training.TrainingInformationManager;
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
import org.ums.services.email.NewIUMSAccountInfoEmailService;
import org.ums.solr.repository.transaction.EmployeeTransaction;
import org.ums.statistics.*;
import org.ums.twofa.*;
import org.ums.usermanagement.application.ApplicationCache;
import org.ums.usermanagement.application.ApplicationDao;
import org.ums.usermanagement.application.ApplicationManager;
import org.ums.usermanagement.permission.*;
import org.ums.usermanagement.role.PersistentRoleDao;
import org.ums.usermanagement.role.RoleCache;
import org.ums.usermanagement.role.RoleManager;
import org.ums.usermanagement.role.RolePermissionResolver;
import org.ums.usermanagement.transformer.*;
import org.ums.usermanagement.user.PersistentUserDao;
import org.ums.usermanagement.user.UserCache;
import org.ums.usermanagement.user.UserManager;
import org.ums.usermanagement.userView.PersistentUserViewDao;
import org.ums.usermanagement.userView.UserViewCache;
import org.ums.usermanagement.userView.UserViewManager;
import org.ums.util.Constants;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CoreContext {
  @Autowired
  CacheFactory mCacheFactory;

  @Autowired
  JdbcTemplateFactory mTemplateFactory;

  @Autowired
  NamedParameterJdbcTemplateFactory mNamedParameterJdbcTemplateFactory;

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

  @Autowired
  @Qualifier("newIUMSAccountInfoEmailService")
  NewIUMSAccountInfoEmailService mNewIUMSAccountInfoEmailService;

  /*
   * @Bean(name = "ftpSessionFactory") public SessionFactory<FTPFile> ftpSessionFactory() {
   * DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory(); sf.setHost("localhost");
   * sf.setUsername("iums"); sf.setPassword("austig100"); return new
   * CachingSessionFactory<FTPFile>(sf); }
   */
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
  CompanyManager companyManager() {
    CompanyCache companyCache = new CompanyCache(mCacheFactory.getCacheManager());
    companyCache.setManager(new PersistentCompanyDao(mTemplateFactory.getJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getNamedParameterJdbcTemplate(), mIdGenerator));
    return companyCache;
  }

  @Bean
  CompanyBranchManager companyBranchManager() {
    CompanyBranchCache companyBranchCache = new CompanyBranchCache(mCacheFactory.getCacheManager());
    companyBranchCache.setManager(new PersistentCompanyBranchDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return companyBranchCache;
  }

  @Bean
  StudentManager studentManager() {
    StudentCache studentCache = new StudentCache(mCacheFactory.getCacheManager());
    studentCache.setManager(new PersistentStudentDao(mTemplateFactory.getJdbcTemplate()));
    return studentCache;
  }

  @Bean
  public UserManager userManager() {
    List<UserPropertyResolver> userPropertyResolvers = new ArrayList<>();
    userPropertyResolvers.add(new AdminUserResolver());
    userPropertyResolvers.add(new StudentUserResolver(studentManager()));
    userPropertyResolvers.add(new BankUserResolver(branchUserManager()));
    userPropertyResolvers.add(new EmployeeUserResolver(employeeManager()));
    UserCache userCache = new UserCache(mCacheFactory.getCacheManager());
    userCache.setManager(new PersistentUserDao(mTemplateFactory.getJdbcTemplate()));
    UserPropertyDecorator userPropertyDecorator =
        new UserPropertyDecorator(personalInformationManager(), studentManager(), userPropertyResolvers);
    userPropertyDecorator.setManager(userCache);
    return userPropertyDecorator;
  }

  @Bean
  AppSettingManager appSettingManager() {
    AppSettingCache appSettingCache = new AppSettingCache(mCacheFactory.getCacheManager());
    appSettingCache.setManager(new PersistentAppSettingDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return appSettingCache;
  }

  @Bean
  public AdditionalRolePermissionsManager additionalRolePermissionsManager() {
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
  public BearerAccessTokenManager bearerAccessTokenManager() {
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
  public PermissionManager permissionManager() {
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
  @Qualifier("genericDateFormat")
  DateFormat genericDateFormat() {
    return new DateFormat(Constants.DATE_FORMAT);
  }

  @Bean
  @Qualifier("genericDateFormat24")
  DateFormat genericDateTimeFormat24() {
    return new DateFormat(Constants.DATE_TIME_24H_FORMAT);
  }

  @Bean
  @Qualifier("genericDateFormat12")
  DateFormat genericDateTimeFormat12() {
    return new DateFormat(Constants.DATE_TIME_12H_FORMAT);
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
  EmployeeEarnedLeaveBalanceManager employeeEarnedLeaveBalanceManager() {
    EmployeeEarnedLeaveBalanceCache leaveBalanceCache =
        new EmployeeEarnedLeaveBalanceCache(mCacheFactory.getCacheManager());
    leaveBalanceCache.setManager(new PersistentEmployeeEarnedLeaveBalanceDao(mTemplateFactory.getJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getNamedParameterJdbcTemplate(), mIdGenerator));
    return leaveBalanceCache;
  }

  @Bean
  EmployeeEarnedLeaveBalanceHistoryManager employeeEarnedLeaveBalanceHistoryManager() {
    EmployeeEarnedLeaveBalanceHistoryCache leaveBalanceCache =
        new EmployeeEarnedLeaveBalanceHistoryCache(mCacheFactory.getCacheManager());
    leaveBalanceCache.setManager(new PersistentEmployeeEarnedLeaveBalanceHistoryDao(mTemplateFactory.getJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getNamedParameterJdbcTemplate(), mIdGenerator));
    return leaveBalanceCache;
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
    lmsApplicationCache.setManager(new PersistentLmsApplicationDao(mTemplateFactory.getJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getNamedParameterJdbcTemplate(), mIdGenerator));
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
  DegreeTitleManager degreeTitleManager() {
    DegreeTitleCache degreeTitleCache = new DegreeTitleCache(mCacheFactory.getCacheManager());
    degreeTitleCache.setManager(new PersistentDegreeTitleDao(mTemplateFactory.getJdbcTemplate()));
    return degreeTitleCache;
  }

  @Bean
  UserViewManager userViewManager() {
    UserViewCache userViewCache = new UserViewCache(mCacheFactory.getCacheManager());
    userViewCache.setManager(new PersistentUserViewDao(mTemplateFactory.getJdbcTemplate()));
    return userViewCache;
  }

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

  @Bean
  public UserRolePermissions userRolePermissions() {
    return new UserRolePermissionsImpl(additionalRolePermissionsManager(), permissionManager(), userManager(),
        roleManager());
  }

  @Bean
  ApplicationManager applicationManager() {
    ApplicationCache applicationCache = new ApplicationCache(mCacheFactory.getCacheManager());
    applicationCache.setManager(new ApplicationDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return applicationCache;
  }

  @Bean
  BankManager bankManager() {
    BankCache bankCache = new BankCache(mCacheFactory.getCacheManager());
    bankCache.setManager(new BankDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return bankCache;
  }

  @Bean
  BankDesignationManager bankDesignationManager() {
    BankDesignationCache bankDesignationCache = new BankDesignationCache(mCacheFactory.getCacheManager());
    bankDesignationCache.setManager(new BankDesignationDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return bankDesignationCache;
  }

  @Bean
  BranchManager branchManager() {
    BranchCache branchCache = new BranchCache(mCacheFactory.getCacheManager());
    branchCache.setManager(new BranchDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return branchCache;
  }

  @Bean
  @Qualifier("branchUserManager")
  BranchUserManager branchUserManager() {
    BranchUserDao branchDao = new BranchUserDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
    branchDao.setManager(new BranchUserPostTransaction(mNewIUMSAccountInfoEmailService, roleManager()));
    BranchUserCache branchUserCache = new BranchUserCache(mCacheFactory.getCacheManager());
    branchUserCache.setManager(branchDao);
    return branchUserCache;
  }

  @Bean
  FCMTokenManager fcmTokenManager() {
    return new PersistentFCMTokenDao(mTemplateFactory.getJdbcTemplate());
  }

}
