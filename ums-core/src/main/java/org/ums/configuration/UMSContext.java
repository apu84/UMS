package org.ums.configuration;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.ums.cache.*;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.immutable.Examiner;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.domain.model.mutable.MutableExaminer;
import org.ums.generator.JxlsGenerator;
import org.ums.generator.XlsGenerator;
import org.ums.manager.*;
import org.ums.message.MessageResource;
import org.ums.persistent.dao.*;
import org.ums.security.authentication.UMSAuthenticationRealm;
import org.ums.services.LoginService;
import org.ums.services.NotificationGenerator;
import org.ums.statistics.DBLogger;
import org.ums.statistics.JdbcTemplateFactory;
import org.ums.statistics.QueryLogger;
import org.ums.statistics.TextLogger;
import org.ums.util.Constants;

import javax.sql.DataSource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
  NotificationGenerator mNotificationGenerator;

  @Bean
  SemesterManager semesterManager() {
    SemesterCache semesterCache = new SemesterCache(mCacheFactory.getCacheManager());
    semesterCache.setManager(new PersistentSemesterDao(mTemplateFactory.getJdbcTemplate(), getGenericDateFormat()));
    return semesterCache;
  }

  @Bean
  EmployeeManager employeeManager() {
    EmployeeCache employeeCache = new EmployeeCache(mCacheFactory.getCacheManager());
    employeeCache.setManager(new PersistentEmployeeDao(mTemplateFactory.getJdbcTemplate()));
    return employeeCache;
  }

  @Bean
  SemesterWithDrawalManager semesterWithdrawalManager() {
    SemesterWithdrawalCache semesterWithdrawalCache = new SemesterWithdrawalCache(mCacheFactory.getCacheManager());
    semesterWithdrawalCache.setManager(new PersistentSemesterWithdrawalDao(mTemplateFactory.getJdbcTemplate()));
    return semesterWithdrawalCache;
  }

  @Bean
  SemesterWithdrawalLogManager semesterWithdrawalLogManager() {
    SemesterWithdrawalLogCache semesterWithdrawalLogCache = new SemesterWithdrawalLogCache(mCacheFactory.getCacheManager());
    semesterWithdrawalLogCache.setManager(new PersistentSemesterWithdrawalLogDao(mTemplateFactory.getJdbcTemplate()));
    return semesterWithdrawalLogCache;
  }

  @Bean
  SeatPlanPublishManager seatPlanPublishManager(){
    SeatPlanPublishCache seatPlanPublishCache = new SeatPlanPublishCache(mCacheFactory.getCacheManager());
    seatPlanPublishCache.setManager(new PersistentSeatPlanPublishDao(mTemplateFactory.getJdbcTemplate()));
    return seatPlanPublishCache;
  }

  @Bean
  SubGroupCCIManager subGroupCCIManager(){
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
  ApplicationCCIManager applicationCCIManager(){
    ApplicationCCICache applicationCCICache = new ApplicationCCICache(mCacheFactory.getCacheManager());
    applicationCCICache.setManager(new PersistentApplicationCCIDao(mTemplateFactory.getJdbcTemplate()));
    return applicationCCICache;
  }

  @Bean
  SemesterSyllabusMapManager semesterSyllabusMapManager() {
    SemesterSyllabusMapCache semesterSyllabusMapCache = new SemesterSyllabusMapCache(mCacheFactory.getCacheManager());
    semesterSyllabusMapCache.setManager(new PersistentSemesterSyllabusMapDao(new JdbcTemplate(mDataSource), syllabusManager()));
    return semesterSyllabusMapCache;
  }

  @Bean
  DateFormat getGenericDateFormat() {
    return new SimpleDateFormat(Constants.DATE_FORMAT);
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
    studentCache.setManager(new PersistentStudentDao(mTemplateFactory.getJdbcTemplate(), getGenericDateFormat()));
    return studentCache;
  }

  @Bean
  UserManager userManager() {
    UserCache userCache = new UserCache(mCacheFactory.getCacheManager());
    UserPropertyResolver userPropertyResolver = new UserPropertyResolver(employeeManager(), studentManager());
    userPropertyResolver.setManager(new PersistentUserDao(mTemplateFactory.getJdbcTemplate()));
    userCache.setManager(userPropertyResolver);
    return userCache;
  }

  @Bean
  TeacherManager teacherManager() {
    TeacherCache teacherCache = new TeacherCache(mCacheFactory.getCacheManager());
    teacherCache.setManager(new PersistentTeacherDao(mTemplateFactory.getJdbcTemplate()));
    return teacherCache;
  }

  @Bean
  AssignedTeacherManager<CourseTeacher, MutableCourseTeacher, Integer> courseTeacherManager() {
    CourseTeacherCache courseTeacherCache = new CourseTeacherCache(mCacheFactory.getCacheManager());
    courseTeacherCache.setManager(new PersistentCourseTeacherDao(mTemplateFactory.getJdbcTemplate()));
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
    seatPlanGroupCache.setManager(new PersistentSeatPlanGroupDao(mTemplateFactory.getJdbcTemplate()));
    return seatPlanGroupCache;
  }

  @Bean
  NavigationManager navigationManager() {
    NavigationByPermissionResolver navigationByPermissionResolver = new NavigationByPermissionResolver(mAuthenticationRealm);
    NavigationCache navigationCache = new NavigationCache(mCacheFactory.getCacheManager());
    navigationCache.setManager(new PersistentNavigationDao(mTemplateFactory.getJdbcTemplate()));
    navigationByPermissionResolver.setManager(navigationCache);
    return navigationByPermissionResolver;
  }

  @Bean
  AdditionalRolePermissionsManager additionalRolePermissionsManager() {
    AdditionalRolePermissionsCache additionalRolePermissionsCache = new AdditionalRolePermissionsCache(mCacheFactory.getCacheManager());
    additionalRolePermissionsCache.setManager(new AdditionalRolePermissionsDao(mTemplateFactory.getJdbcTemplate(), getGenericDateFormat()));
    return additionalRolePermissionsCache;
  }

  @Bean
  StudentRecordManager studentRecordManager() {
    StudentRecordCache studentRecordCache = new StudentRecordCache(mCacheFactory.getCacheManager());
    studentRecordCache.setManager(new PersistentStudentRecordDao(mTemplateFactory.getJdbcTemplate()));
    return studentRecordCache;
  }

  @Bean
  SemesterEnrollmentManager semesterEnrollmentManager() {
    SemesterEnrollmentCache semesterEnrollmentCache = new SemesterEnrollmentCache(mCacheFactory.getCacheManager());
    semesterEnrollmentCache.setManager(new PersistentSemesterEnrollmentDao(mTemplateFactory.getJdbcTemplate(), getGenericDateFormat()));
    return semesterEnrollmentCache;
  }

  @Bean
  EnrollmentFromToManager enrollmentFromToManager() {
    EnrollmentFromToCache enrollmentFromToCache = new EnrollmentFromToCache(mCacheFactory.getCacheManager());
    enrollmentFromToCache.setManager(new PersistentEnrollmentFromToDao(mTemplateFactory.getJdbcTemplate()));
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
    BearerAccessTokenCache bearerAccessTokenCache = new BearerAccessTokenCache(mCacheFactory.getCacheManager());
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
    return new PersistentUGRegistrationResultDao(mTemplateFactory.getJdbcTemplate());
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
    FileContentPermission fileContentPermission = new FileContentPermission(userManager(),
        bearerAccessTokenManager(), mUMSConfiguration, mMessageResource);
    CourseMaterialNotifier notifier = new CourseMaterialNotifier(userManager(), mNotificationGenerator,
        registrationResultManager(), mUMSConfiguration, mMessageResource);
    fileContentPermission.setManager(notifier);
    notifier.setManager(mBinaryContentManager);
    return fileContentPermission;
  }

  @Bean
  @Lazy
  BinaryContentManager<byte[]> courseMaterialFileManagerForStudent() {
    StudentFileContentPermission fileContentPermission = new StudentFileContentPermission(userManager(),
        bearerAccessTokenManager(), mUMSConfiguration, mMessageResource, studentManager(), semesterManager(),
        courseManager(), semesterSyllabusMapManager());
    fileContentPermission.setManager(mBinaryContentManager);
    return fileContentPermission;
  }

  @Bean
  NotificationManager notificationManager() {
    return new PersistentNotificationDao(mTemplateFactory.getJdbcTemplate(), getGenericDateFormat());
  }
}
