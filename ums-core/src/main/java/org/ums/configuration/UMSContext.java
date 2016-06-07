package org.ums.configuration;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.ums.cache.*;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.immutable.Examiner;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.domain.model.mutable.MutableExaminer;
import org.ums.manager.*;
import org.ums.persistent.dao.*;
import org.ums.security.authentication.UMSAuthenticationRealm;
import org.ums.services.LoginService;
import org.ums.statistics.DBLogger;
import org.ums.statistics.QueryLogger;
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
  JdbcTemplate mJdbcTemplate;

  @Autowired
  BinaryContentManager<byte[]> mBinaryContentManager;

  @Autowired
  UMSAuthenticationRealm mAuthenticationRealm;

  @Bean
  SemesterManager semesterManager() {
    SemesterCache semesterCache = new SemesterCache(mCacheFactory.getCacheManager());
    semesterCache.setManager(new PersistentSemesterDao(mJdbcTemplate, getGenericDateFormat()));
    return semesterCache;
  }

  @Bean
  EmployeeManager employeeManager() {
    EmployeeCache employeeCache = new EmployeeCache(mCacheFactory.getCacheManager());
    employeeCache.setManager(new PersistentEmployeeDao(mJdbcTemplate));
    return employeeCache;
  }

  @Bean
  SemesterWithDrawalManager semesterWithdrawalManager() {
    SemesterWithdrawalCache semesterWithdrawalCache = new SemesterWithdrawalCache(mCacheFactory.getCacheManager());
    semesterWithdrawalCache.setManager(new PersistentSemesterWithdrawalDao(mJdbcTemplate));
    return semesterWithdrawalCache;
  }

  @Bean
  SemesterWithdrawalLogManager semesterWithdrawalLogManager() {
    SemesterWithdrawalLogCache semesterWithdrawalLogCache = new SemesterWithdrawalLogCache(mCacheFactory.getCacheManager());
    semesterWithdrawalLogCache.setManager(new PersistentSemesterWithdrawalLogDao(mJdbcTemplate));
    return semesterWithdrawalLogCache;
  }

  @Bean
  SubGroupManager subGroupManager() {
    SubGroupCache subGroupCache = new SubGroupCache(mCacheFactory.getCacheManager());
    subGroupCache.setManager(new PersistentSubGroupDao(mJdbcTemplate));
    return subGroupCache;
  }

  @Bean
  SeatPlanManager seatPlanManager() {
    SeatPlanCache seatPlanCache = new SeatPlanCache(mCacheFactory.getCacheManager());
    seatPlanCache.setManager(new PersistentSeatPlanDao(mJdbcTemplate));
    return seatPlanCache;
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
    programTypeCache.setManager(new PersistentProgramTypeDao(mJdbcTemplate));
    return programTypeCache;
  }

  @Bean
  ProgramManager programManager() {
    ProgramCache programCache = new ProgramCache(mCacheFactory.getCacheManager());
    programCache.setManager(new PersistentProgramDao(mJdbcTemplate));
    return programCache;
  }

  @Bean
  DepartmentManager departmentManager() {
    DepartmentCache departmentCache = new DepartmentCache(mCacheFactory.getCacheManager());
    departmentCache.setManager(new PersistentDepartmentDao(mJdbcTemplate));
    return departmentCache;
  }

  @Bean
  SyllabusManager syllabusManager() {
    SyllabusCache syllabusCache = new SyllabusCache(mCacheFactory.getCacheManager());
    syllabusCache.setManager(new PersistentSyllabusDao(mJdbcTemplate));
    return syllabusCache;
  }

  @Bean
  CourseGroupManager courseGroupManager() {
    CourseGroupCache courseGroupCache = new CourseGroupCache(mCacheFactory.getCacheManager());
    courseGroupCache.setManager(new PersistentCourseGroupDao(mJdbcTemplate));
    return courseGroupCache;
  }

  @Bean
  CourseManager courseManager() {
    CourseCache courseCache = new CourseCache(mCacheFactory.getCacheManager());
    courseCache.setManager(new PersistentCourseDao(mJdbcTemplate));
    return courseCache;
  }


  @Bean
  RoleManager roleManager() {
    RoleCache roleCache = new RoleCache(mCacheFactory.getCacheManager());
    roleCache.setManager(new PersistentRoleDao(mJdbcTemplate));
    return roleCache;
  }

  @Bean
  StudentManager studentManager() {
    StudentCache studentCache = new StudentCache(mCacheFactory.getCacheManager());
    studentCache.setManager(new PersistentStudentDao(mJdbcTemplate, getGenericDateFormat()));
    return studentCache;
  }

  @Bean
  UserManager userManager() {
    UserCache userCache = new UserCache(mCacheFactory.getCacheManager());
    userCache.setManager(new PersistentUserDao(mJdbcTemplate));
    return userCache;
  }

  @Bean
  TeacherManager teacherManager() {
    TeacherCache teacherCache = new TeacherCache(mCacheFactory.getCacheManager());
    teacherCache.setManager(new PersistentTeacherDao(mJdbcTemplate));
    return teacherCache;
  }

  @Bean
  AssignedTeacherManager<CourseTeacher, MutableCourseTeacher, Integer> courseTeacherManager() {
    CourseTeacherCache courseTeacherCache = new CourseTeacherCache(mCacheFactory.getCacheManager());
    courseTeacherCache.setManager(new PersistentCourseTeacherDao(mJdbcTemplate));
    return courseTeacherCache;
  }

  @Bean
  AssignedTeacherManager<Examiner, MutableExaminer, Integer> examinerManager() {
    ExaminerCache examinerCache = new ExaminerCache(mCacheFactory.getCacheManager());
    examinerCache.setManager(new PersistentExaminerDao(mJdbcTemplate));
    return examinerCache;
  }

  @Bean
  LoggerEntryManager loggerEntryManager() {
    return new PersistentLoggerEntryDao(mJdbcTemplate);
  }

  @Bean
  SpStudentManager spStudentManager() {
    SpStudentCache spStudentCache = new SpStudentCache(mCacheFactory.getCacheManager());
    spStudentCache.setManager(new PersistentSpStudentDao(mJdbcTemplate));
    return spStudentCache;
  }

  @Bean
  PermissionManager permissionManager() {
    PermissionCache permissionCache = new PermissionCache(mCacheFactory.getCacheManager());
    permissionCache.setManager(new PersistentPermissionDao(mJdbcTemplate));
    return permissionCache;
  }

  @Bean
  SeatPlanGroupManager seatPlanGroupManager() {
    SeatPlanGroupCache seatPlanGroupCache = new SeatPlanGroupCache(mCacheFactory.getCacheManager());
    seatPlanGroupCache.setManager(new PersistentSeatPlanGroupDao(mJdbcTemplate));
    return seatPlanGroupCache;
  }

  @Bean
  NavigationManager navigationManager() {
    NavigationByPermissionResolver navigationByPermissionResolver = new NavigationByPermissionResolver(mAuthenticationRealm);
    navigationByPermissionResolver.setManager(new PersistentNavigationDao(mJdbcTemplate));
    return navigationByPermissionResolver;
  }

  @Bean
  AdditionalRolePermissionsManager additionalRolePermissionsManager() {
    AdditionalRolePermissionsCache additionalRolePermissionsCache = new AdditionalRolePermissionsCache(mCacheFactory.getCacheManager());
    additionalRolePermissionsCache.setManager(new AdditionalRolePermissionsDao(mJdbcTemplate, getGenericDateFormat()));
    return additionalRolePermissionsCache;
  }

  @Bean
  StudentRecordManager studentRecordManager() {
    StudentRecordCache studentRecordCache = new StudentRecordCache(mCacheFactory.getCacheManager());
    studentRecordCache.setManager(new PersistentStudentRecordDao(mJdbcTemplate));
    return studentRecordCache;
  }

  @Bean
  SemesterEnrollmentManager semesterEnrollmentManager() {
    SemesterEnrollmentCache semesterEnrollmentCache = new SemesterEnrollmentCache(mCacheFactory.getCacheManager());
    semesterEnrollmentCache.setManager(new PersistentSemesterEnrollmentDao(mJdbcTemplate, getGenericDateFormat()));
    return semesterEnrollmentCache;
  }

  @Bean
  EnrollmentFromToManager enrollmentFromToManager() {
    EnrollmentFromToCache enrollmentFromToCache = new EnrollmentFromToCache(mCacheFactory.getCacheManager());
    enrollmentFromToCache.setManager(new PersistentEnrollmentFromToDao(mJdbcTemplate));
    return enrollmentFromToCache;
  }

  ClassRoomManager getPersistentClassRoomDao() {
    return new PersistentClassRoomDao(mJdbcTemplate);
  }

  @Bean
  ClassRoomManager classRoomManager() {
    return new PersistentClassRoomDao(mJdbcTemplate);
  }

  @Bean
  ExamRoutineManager examRoutineManager() {
    return new PersistentExamRoutineDao(mJdbcTemplate);
  }

  @Bean
  PersistentOptionalCourseApplicationDao persistentOptionalCourseApplicationDao() {
    return new PersistentOptionalCourseApplicationDao(mJdbcTemplate);
  }

  @Bean
  PersistentSemesterWiseCrHrDao persistentSemesterWiseCrHrDao() {
    return new PersistentSemesterWiseCrHrDao(mJdbcTemplate);
  }

  @Bean
  LoginService loginService() {
    return new LoginService();
  }

  @Bean
  RoutineManager routineManager() {
    return new PersistentRoutineDao(mJdbcTemplate);
  }

  @Bean
  ParameterManager parameterManager() {
    return new PersistentParameterDao(mJdbcTemplate);
  }

  @Bean
  ParameterSettingManager parameterSettingManager() {

    return new PersistentParameterSettingDao(mJdbcTemplate);
  }

  @Bean
  BearerAccessTokenManager bearerAccessTokenManager() {
    BearerAccessTokenCache bearerAccessTokenCache = new BearerAccessTokenCache(mCacheFactory.getCacheManager());
    bearerAccessTokenCache.setManager(new BearerAccessTokenDao(mJdbcTemplate));
    return bearerAccessTokenCache;
  }

  @Bean
  ExamGradeManager examGradeManager() {
    return new PersistentExamGradeDao(mJdbcTemplate);
  }

  @Bean
  QueryLogger queryLogger() {
    return new DBLogger();
  }
}
