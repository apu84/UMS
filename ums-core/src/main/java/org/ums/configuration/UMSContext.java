package org.ums.configuration;

import javax.sql.DataSource;

import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.ums.cache.*;
import org.ums.cachewarmer.AutoCacheWarmer;
import org.ums.cachewarmer.CacheWarmerManagerImpl;
import org.ums.domain.model.immutable.Examiner;
import org.ums.domain.model.mutable.MutableExaminer;
import org.ums.formatter.DateFormat;
import org.ums.generator.JxlsGenerator;
import org.ums.generator.XlsGenerator;
import org.ums.manager.*;
import org.ums.message.MessageResource;
import org.ums.persistent.dao.*;
import org.ums.security.authentication.UMSAuthenticationRealm;
import org.ums.services.LoginService;
import org.ums.services.NotificationGenerator;
import org.ums.services.NotificationGeneratorImpl;
import org.ums.statistics.DBLogger;
import org.ums.statistics.JdbcTemplateFactory;
import org.ums.statistics.QueryLogger;
import org.ums.statistics.TextLogger;
import org.ums.util.Constants;

@Configuration
@EnableAsync
@EnableScheduling
public class UMSContext {
  @Autowired
  DataSource mDataSource;

  @Autowired
  CacheFactory mCacheFactory;

  @Autowired
  PasswordService mPasswordService;

  @Autowired
  JdbcTemplateFactory mTemplateFactory;

  @Autowired
  @Qualifier("fileContentManager")
  BinaryContentManager<byte[]> mBinaryContentManager;

  @Autowired
  UMSAuthenticationRealm mAuthenticationRealm;

  @Autowired
  UMSConfiguration mUMSConfiguration;

  @Autowired
  MessageResource mMessageResource;

  @Autowired
  @Qualifier("mongoTemplate")
  @Lazy
  MongoTemplate mMongoOperations;

  @Autowired
  @Qualifier("backendSecurityManager")
  SecurityManager mSecurityManager;

  @Bean
  LibraryManager libraryManager() {
    LibraryCache libraryCache = new LibraryCache(mCacheFactory.getCacheManager());
    libraryCache.setManager(new PersistentLibraryDao(mTemplateFactory.getJdbcTemplate()));
    return libraryCache;
  }

  @Bean
  SemesterManager semesterManager() {
    SemesterCache semesterCache = new SemesterCache(mCacheFactory.getCacheManager());
    semesterCache.setManager(new PersistentSemesterDao(mTemplateFactory.getJdbcTemplate()));
    return semesterCache;
  }

  @Bean
  AdmissionStudentManager admissionStudentManager() {
    AdmissionStudentCache admissionStudentCache =
        new AdmissionStudentCache(mCacheFactory.getCacheManager());
    admissionStudentCache.setManager(new PersistentAdmissionStudentDao(mTemplateFactory
        .getJdbcTemplate()));
    return admissionStudentCache;
  }

  @Bean
  AdmissionMeritListManager admissionMeritListManager() {
    AdmissionMeritListCache admissionMeritListCache =
        new AdmissionMeritListCache(mCacheFactory.getCacheManager());
    admissionMeritListCache.setManager(new PersistentAdmissionMeritListDao(mTemplateFactory
        .getJdbcTemplate()));
    return admissionMeritListCache;
  }

  @Bean
  FacultyManager facultyManager() {
    FacultyCache facultyCache = new FacultyCache(mCacheFactory.getCacheManager());
    facultyCache.setManager(new PersistentFacultyDao(mTemplateFactory.getJdbcTemplate()));
    return facultyCache;
  }

  @Bean
  EmployeeManager employeeManager() {
    EmployeeCache employeeCache = new EmployeeCache(mCacheFactory.getCacheManager());
    employeeCache.setManager(new PersistentEmployeeDao(mTemplateFactory.getJdbcTemplate()));
    return employeeCache;
  }

  @Bean
  SemesterWithDrawalManager semesterWithdrawalManager() {
    SemesterWithdrawalCache semesterWithdrawalCache =
        new SemesterWithdrawalCache(mCacheFactory.getCacheManager());
    semesterWithdrawalCache.setManager(new PersistentSemesterWithdrawalDao(mTemplateFactory
        .getJdbcTemplate()));
    return semesterWithdrawalCache;
  }

  @Bean
  SemesterWithdrawalLogManager semesterWithdrawalLogManager() {
    SemesterWithdrawalLogCache semesterWithdrawalLogCache =
        new SemesterWithdrawalLogCache(mCacheFactory.getCacheManager());
    semesterWithdrawalLogCache.setManager(new PersistentSemesterWithdrawalLogDao(mTemplateFactory
        .getJdbcTemplate()));
    return semesterWithdrawalLogCache;
  }

  @Bean
  SeatPlanPublishManager seatPlanPublishManager() {
    SeatPlanPublishCache seatPlanPublishCache =
        new SeatPlanPublishCache(mCacheFactory.getCacheManager());
    seatPlanPublishCache.setManager(new PersistentSeatPlanPublishDao(mTemplateFactory
        .getJdbcTemplate()));
    return seatPlanPublishCache;
  }

  @Bean
  SubGroupCCIManager subGroupCCIManager() {
    SubGroupCCICache subGroupCCICache = new SubGroupCCICache(mCacheFactory.getCacheManager());
    subGroupCCICache.setManager(new PersistentSubGroupCCIDao(mTemplateFactory.getJdbcTemplate()));
    return subGroupCCICache;
  }

  @Bean
  SubGroupManager subGroupManager() {
    SubGroupCache subGroupCache = new SubGroupCache(mCacheFactory.getCacheManager());
    subGroupCache.setManager(new PersistentSubGroupDao(mTemplateFactory.getJdbcTemplate()));
    return subGroupCache;
  }

  @Bean
  SeatPlanManager seatPlanManager() {
    SeatPlanCache seatPlanCache = new SeatPlanCache(mCacheFactory.getCacheManager());
    seatPlanCache.setManager(new PersistentSeatPlanDao(mTemplateFactory.getJdbcTemplate()));
    return seatPlanCache;
  }

  @Bean
  ApplicationCCIManager applicationCCIManager() {
    ApplicationCCICache applicationCCICache =
        new ApplicationCCICache(mCacheFactory.getCacheManager());
    applicationCCICache.setManager(new PersistentApplicationCCIDao(mTemplateFactory
        .getJdbcTemplate()));
    return applicationCCICache;
  }

  @Bean
  SemesterSyllabusMapManager semesterSyllabusMapManager() {
    SemesterSyllabusMapCache semesterSyllabusMapCache =
        new SemesterSyllabusMapCache(mCacheFactory.getCacheManager());
    semesterSyllabusMapCache.setManager(new PersistentSemesterSyllabusMapDao(new JdbcTemplate(
        mDataSource), syllabusManager()));
    return semesterSyllabusMapCache;
  }

  @Bean
  DateFormat getGenericDateFormat() {
    return new DateFormat(Constants.DATE_FORMAT);
  }

  @Bean
  ProgramTypeManager programTypeManager() {
    ProgramTypeCache programTypeCache = new ProgramTypeCache(mCacheFactory.getCacheManager());
    programTypeCache.setManager(new PersistentProgramTypeDao(mTemplateFactory.getJdbcTemplate()));
    return programTypeCache;
  }

  @Bean
  ProgramManager programManager() {
    ProgramCache programCache = new ProgramCache(mCacheFactory.getCacheManager());
    programCache.setManager(new PersistentProgramDao(mTemplateFactory.getJdbcTemplate()));
    return programCache;
  }

  @Bean
  DepartmentManager departmentManager() {
    DepartmentCache departmentCache = new DepartmentCache(mCacheFactory.getCacheManager());
    departmentCache.setManager(new PersistentDepartmentDao(mTemplateFactory.getJdbcTemplate()));
    return departmentCache;
  }

  @Bean
  SyllabusManager syllabusManager() {
    SyllabusCache syllabusCache = new SyllabusCache(mCacheFactory.getCacheManager());
    syllabusCache.setManager(new PersistentSyllabusDao(mTemplateFactory.getJdbcTemplate()));
    return syllabusCache;
  }

  @Bean
  CourseGroupManager courseGroupManager() {
    CourseGroupCache courseGroupCache = new CourseGroupCache(mCacheFactory.getCacheManager());
    courseGroupCache.setManager(new PersistentCourseGroupDao(mTemplateFactory.getJdbcTemplate()));
    return courseGroupCache;
  }

  @Bean
  CourseManager courseManager() {
    CourseCache courseCache = new CourseCache(mCacheFactory.getCacheManager());
    courseCache.setManager(new PersistentCourseDao(mTemplateFactory.getJdbcTemplate()));
    return courseCache;
  }

  @Bean
  RoleManager roleManager() {
    RoleCache roleCache = new RoleCache(mCacheFactory.getCacheManager());
    roleCache.setManager(new PersistentRoleDao(mTemplateFactory.getJdbcTemplate()));
    return roleCache;
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
    UserPropertyResolver userPropertyResolver =
        new UserPropertyResolver(employeeManager(), studentManager());
    userPropertyResolver.setManager(userCache);
    return userPropertyResolver;
  }

  @Bean
  TeacherManager teacherManager() {
    TeacherCache teacherCache = new TeacherCache(mCacheFactory.getCacheManager());
    teacherCache.setManager(new PersistentTeacherDao(mTemplateFactory.getJdbcTemplate()));
    return teacherCache;
  }

  @Bean
  AppSettingManager appSettingManager() {
    AppSettingCache appSettingCache = new AppSettingCache(mCacheFactory.getCacheManager());
    appSettingCache.setManager(new PersistentAppSettingDao(mTemplateFactory.getJdbcTemplate()));
    return appSettingCache;
  }

  @Bean
  CourseTeacherManager courseTeacherManager() {
    CourseTeacherCache courseTeacherCache = new CourseTeacherCache(mCacheFactory.getCacheManager());
    courseTeacherCache
        .setManager(new PersistentCourseTeacherDao(mTemplateFactory.getJdbcTemplate()));
    return courseTeacherCache;
  }

  @Bean
  AssignedTeacherManager<Examiner, MutableExaminer, Integer> examinerManager() {
    ExaminerCache examinerCache = new ExaminerCache(mCacheFactory.getCacheManager());
    examinerCache.setManager(new PersistentExaminerDao(mTemplateFactory.getJdbcTemplate()));
    return examinerCache;
  }

  @Bean
  SpStudentManager spStudentManager() {
    SpStudentCache spStudentCache = new SpStudentCache(mCacheFactory.getCacheManager());
    spStudentCache.setManager(new PersistentSpStudentDao(mTemplateFactory.getJdbcTemplate()));
    return spStudentCache;
  }

  @Bean
  PermissionManager permissionManager() {
    PermissionCache permissionCache = new PermissionCache(mCacheFactory.getCacheManager());
    permissionCache.setManager(new PersistentPermissionDao(mTemplateFactory.getJdbcTemplate()));
    return permissionCache;
  }

  @Bean
  SeatPlanGroupManager seatPlanGroupManager() {
    SeatPlanGroupCache seatPlanGroupCache = new SeatPlanGroupCache(mCacheFactory.getCacheManager());
    seatPlanGroupCache
        .setManager(new PersistentSeatPlanGroupDao(mTemplateFactory.getJdbcTemplate()));
    return seatPlanGroupCache;
  }

  @Bean
  NavigationManager navigationManager() {
    NavigationByPermissionResolver navigationByPermissionResolver =
        new NavigationByPermissionResolver(mAuthenticationRealm);
    NavigationCache navigationCache = new NavigationCache(mCacheFactory.getCacheManager());
    navigationCache.setManager(new PersistentNavigationDao(mTemplateFactory.getJdbcTemplate()));
    navigationByPermissionResolver.setManager(navigationCache);
    return navigationByPermissionResolver;
  }

  @Bean
  AdditionalRolePermissionsManager additionalRolePermissionsManager() {
    AdditionalRolePermissionsCache additionalRolePermissionsCache =
        new AdditionalRolePermissionsCache(mCacheFactory.getCacheManager());
    additionalRolePermissionsCache.setManager(new AdditionalRolePermissionsDao(mTemplateFactory
        .getJdbcTemplate()));
    return additionalRolePermissionsCache;
  }

  @Bean
  StudentRecordManager studentRecordManager() {
    StudentRecordCache studentRecordCache = new StudentRecordCache(mCacheFactory.getCacheManager());
    studentRecordCache
        .setManager(new PersistentStudentRecordDao(mTemplateFactory.getJdbcTemplate()));
    return studentRecordCache;
  }

  @Bean
  SemesterEnrollmentManager semesterEnrollmentManager() {
    SemesterEnrollmentCache semesterEnrollmentCache =
        new SemesterEnrollmentCache(mCacheFactory.getCacheManager());
    semesterEnrollmentCache.setManager(new PersistentSemesterEnrollmentDao(mTemplateFactory
        .getJdbcTemplate()));
    return semesterEnrollmentCache;
  }

  @Bean
  EnrollmentFromToManager enrollmentFromToManager() {
    EnrollmentFromToCache enrollmentFromToCache =
        new EnrollmentFromToCache(mCacheFactory.getCacheManager());
    enrollmentFromToCache.setManager(new PersistentEnrollmentFromToDao(mTemplateFactory
        .getJdbcTemplate()));
    return enrollmentFromToCache;
  }

  ClassRoomManager getPersistentClassRoomDao() {
    return new PersistentClassRoomDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  ClassRoomManager classRoomManager() {
    return new PersistentClassRoomDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  ExamRoutineManager examRoutineManager() {
    return new PersistentExamRoutineDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  PersistentOptionalCourseApplicationDao persistentOptionalCourseApplicationDao() {
    return new PersistentOptionalCourseApplicationDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  PersistentSemesterWiseCrHrDao persistentSemesterWiseCrHrDao() {
    return new PersistentSemesterWiseCrHrDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  LoginService loginService() {
    return new LoginService();
  }

  @Bean
  RoutineManager routineManager() {
    return new PersistentRoutineDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  ParameterManager parameterManager() {
    return new PersistentParameterDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  ParameterSettingManager parameterSettingManager() {

    return new PersistentParameterSettingDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  BearerAccessTokenManager bearerAccessTokenManager() {
    BearerAccessTokenCache bearerAccessTokenCache =
        new BearerAccessTokenCache(mCacheFactory.getCacheManager());
    bearerAccessTokenCache.setManager(new BearerAccessTokenDao(mTemplateFactory.getJdbcTemplate()));
    return bearerAccessTokenCache;
  }

  @Bean
  ExamGradeManager examGradeManager() {
    return new PersistentExamGradeDao(mTemplateFactory.getJdbcTemplate());
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
  UGRegistrationResultManager registrationResultManager() {
    UGRegistrationResultAggregator resultAggregator =
        new UGRegistrationResultAggregator(equivalentCourseManager(), taskStatusManager(),
            semesterManager());
    UGRegistrationResultCache registrationResultCache =
        new UGRegistrationResultCache(mCacheFactory.getCacheManager());
    registrationResultCache.setManager(new PersistentUGRegistrationResultDao(mTemplateFactory
        .getJdbcTemplate()));
    resultAggregator.setManager(registrationResultCache);
    return resultAggregator;
  }

  @Bean
  SeatPlanReportManager seatPlanReportManager() {
    return new PersistentSeatPlanReportDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  UGTheoryMarksManager theoryMarksManager() {
    return new PersistentUGTheoryMarksDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  UGSessionalMarksManager sessionalMarksManager() {
    return new PersistentUGSessionalMarksDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  XlsGenerator xlsGenerator() {
    return new JxlsGenerator();
  }

  @Bean
  @Lazy
  BinaryContentManager<byte[]> courseMaterialFileManagerForTeacher() {
    FileContentPermission fileContentPermission =
        new FileContentPermission(userManager(), bearerAccessTokenManager(), mUMSConfiguration,
            mMessageResource);
    CourseMaterialNotifier notifier =
        new CourseMaterialNotifier(userManager(), notificationGenerator(),
            registrationResultManager(), mUMSConfiguration, mMessageResource,
            courseTeacherManager(), bearerAccessTokenManager());
    fileContentPermission.setManager(notifier);
    notifier.setManager(mBinaryContentManager);
    return fileContentPermission;
  }

  @Bean
  @Lazy
  BinaryContentManager<byte[]> courseMaterialFileManagerForStudent() {
    StudentFileContentPermission fileContentPermission =
        new StudentFileContentPermission(userManager(), bearerAccessTokenManager(),
            mUMSConfiguration, mMessageResource, studentManager(), registrationResultManager(),
            courseTeacherManager());
    fileContentPermission.setManager(mBinaryContentManager);
    return fileContentPermission;
  }

  @Bean
  @Lazy
  NotificationManager notificationManager() {
    return mUMSConfiguration.isEnableObjectDb() ? new PersistentObjectNotificationDao(
        mMongoOperations) : new PersistentNotificationDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  @Lazy
  NotificationGenerator notificationGenerator() {
    return new NotificationGeneratorImpl(notificationManager());
  }

  @Bean
  EquivalentCourseManager equivalentCourseManager() {
    EquivalentCourseCache equivalentCourseCache =
        new EquivalentCourseCache(mCacheFactory.getCacheManager());
    equivalentCourseCache.setManager(new EquivalentCourseDao(mTemplateFactory.getJdbcTemplate()));
    return equivalentCourseCache;
  }

  @Bean
  MarksSubmissionStatusManager marksSubmissionStatusManager() {
    MarksSubmissionStatusCache cache =
        new MarksSubmissionStatusCache(mCacheFactory.getCacheManager());
    MarksSubmissionStatusAggregator aggregator = new MarksSubmissionStatusAggregator();
    cache.setManager(aggregator);
    aggregator
        .setManager(new PersistentMarkSubmissionStatusDao(mTemplateFactory.getJdbcTemplate()));
    return cache;
  }

  @Bean
  TaskStatusManager taskStatusManager() {
    return new PersistentTaskStatusDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  ResultPublishManager resultPublishManager() {
    ResultPublishValidator validator = new ResultPublishValidator(marksSubmissionStatusManager());
    ResultPublishImpl resultPublish = new ResultPublishImpl();
    validator.setManager(resultPublish);
    resultPublish.setManager(new ResultPublishDao(mTemplateFactory.getJdbcTemplate()));
    return validator;
  }

  @Bean
  ClassAttendanceManager classAttendanceManager() {
    return new PersistentClassAttendanceDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  UserGuideManager userGuideManager() {
    UserGuideCache userGuideCache = new UserGuideCache(mCacheFactory.getCacheManager());
    userGuideCache.setManager(new PersistentUserGuideDao(mTemplateFactory.getJdbcTemplate()));
    return userGuideCache;
  }

  @Bean
  CacheWarmerManager cacheWarmerManager() {
    return new CacheWarmerManagerImpl(mSecurityManager, mCacheFactory, mUMSConfiguration,
        departmentManager(), roleManager(), permissionManager(), bearerAccessTokenManager(),
        additionalRolePermissionsManager(), navigationManager(), employeeManager(),
        programTypeManager(), programManager(), semesterManager(), syllabusManager(),
        courseGroupManager(), equivalentCourseManager(), teacherManager(), courseTeacherManager(),
        examinerManager(), studentManager(), studentRecordManager(), classRoomManager(),
        courseManager(), marksSubmissionStatusManager(), userManager());
  }

  @Bean
  AutoCacheWarmer autoCacheWarmer() {
    return new AutoCacheWarmer(cacheWarmerManager());
  }

}
