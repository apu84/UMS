package org.ums.configuration;

import org.apache.shiro.authc.credential.PasswordService;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.cache.*;
import org.ums.manager.*;
import org.ums.persistent.dao.*;
import org.ums.security.authentication.UMSAuthenticationRealm;
import org.ums.services.LoginService;
import org.ums.util.Constants;

import javax.sql.DataSource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Configuration
public class UMSContext {
  @Autowired
  DataSource mDataSource;

  @Autowired
  @Qualifier("localCache")
  CacheManager mLocalCacheManager;

  @Autowired
  PasswordService mPasswordService;

  @Autowired
  JdbcTemplate mJdbcTemplate;

  @Autowired
  private VelocityEngine velocityEngine;

  @Autowired
  BinaryContentManager<byte[]> mBinaryContentManager;

  @Autowired
  UMSAuthenticationRealm mAuthenticationRealm;

  @Bean
  SemesterManager semesterManager() {
    SemesterCache semesterCache = new SemesterCache(mLocalCacheManager);
    semesterCache.setManager(new PersistentSemesterDao(mJdbcTemplate, getGenericDateFormat()));
    return semesterCache;
  }

  @Bean
  EmployeeManager employeeManager(){
    EmployeeCache employeeCache = new EmployeeCache(mLocalCacheManager);
    employeeCache.setManager(new PersistentEmployeeDao(mJdbcTemplate));
    return employeeCache;
  }

  @Bean
  SemesterWithDrawalManager semesterWithdrawalManager(){
    SemesterWithdrawalCache semesterWithdrawalCache = new SemesterWithdrawalCache(mLocalCacheManager);
    semesterWithdrawalCache.setManager(new PersistentSemesterWithdrawalDao(mJdbcTemplate));
    return semesterWithdrawalCache;
  }

  @Bean
  SemesterWithdrawalLogManager semesterWithdrawalLogManager(){
    SemesterWithdrawalLogCache semesterWithdrawalLogCache = new SemesterWithdrawalLogCache(mLocalCacheManager);
    semesterWithdrawalLogCache.setManager(new PersistentSemesterWithdrawalLogDao(mJdbcTemplate));
    return  semesterWithdrawalLogCache;
  }

  @Bean
  SemesterSyllabusMapManager semesterSyllabusMapManager() {
    SemesterSyllabusMapCache semesterSyllabusMapCache = new SemesterSyllabusMapCache(mLocalCacheManager);
    semesterSyllabusMapCache.setManager(new PersistentSemesterSyllabusMapDao(new JdbcTemplate(mDataSource), syllabusManager()));
    return semesterSyllabusMapCache;
  }

  @Bean
  DateFormat getGenericDateFormat() {
    return new SimpleDateFormat(Constants.DATE_FORMAT);
  }

  @Bean
  ProgramTypeManager programTypeManager() {
    ProgramTypeCache programTypeCache = new ProgramTypeCache(mLocalCacheManager);
    programTypeCache.setManager(new PersistentProgramTypeDao(mJdbcTemplate));
    return programTypeCache;
  }

  @Bean
  ProgramManager programManager() {
    ProgramCache programCache = new ProgramCache(mLocalCacheManager);
    programCache.setManager(new PersistentProgramDao(mJdbcTemplate));
    return programCache;
  }

  @Bean
  DepartmentManager departmentManager() {
    DepartmentCache departmentCache = new DepartmentCache(mLocalCacheManager);
    departmentCache.setManager(new PersistentDepartmentDao(mJdbcTemplate));
    return departmentCache;
  }

  @Bean
  SyllabusManager syllabusManager() {
    SyllabusCache syllabusCache = new SyllabusCache(mLocalCacheManager);
    syllabusCache.setManager(new PersistentSyllabusDao(mJdbcTemplate));
    return syllabusCache;
  }

  @Bean
  CourseGroupManager courseGroupManager() {
    CourseGroupCache courseGroupCache = new CourseGroupCache(mLocalCacheManager);
    courseGroupCache.setManager(new PersistentCourseGroupDao(mJdbcTemplate));
    return courseGroupCache;
  }

  @Bean
  CourseManager courseManager() {
    CourseCache courseCache = new CourseCache(mLocalCacheManager);
    courseCache.setManager(new PersistentCourseDao(mJdbcTemplate));
    return courseCache;
  }



  @Bean
  RoleManager roleManager() {
    RoleCache roleCache = new RoleCache(mLocalCacheManager);
    roleCache.setManager(new PersistentRoleDao(mJdbcTemplate));
    return roleCache;
  }

  @Bean
  StudentManager studentManager() {
    StudentCache studentCache = new StudentCache(mLocalCacheManager);
    studentCache.setManager(new PersistentStudentDao(mJdbcTemplate, getGenericDateFormat()));
    return studentCache;
  }

  @Bean
  UserManager userManager() {
    UserCache userCache = new UserCache(mLocalCacheManager);
    userCache.setManager(new PersistentUserDao(mJdbcTemplate));
    return userCache;
  }

  @Bean
  TeacherManager teacherManager() {
    TeacherCache teacherCache = new TeacherCache(mLocalCacheManager);
    teacherCache.setManager(new PersistentTeacherDao(mJdbcTemplate));
    return teacherCache;
  }

  @Bean
  CourseTeacherManager courseTeacherManager() {
    CourseTeacherCache courseTeacherCache = new CourseTeacherCache(mLocalCacheManager);
    courseTeacherCache.setManager(new PersistentCourseTeacherDao(mJdbcTemplate));
    return courseTeacherCache;
  }

  @Bean
  PermissionManager permissionManager() {
    PermissionCache permissionCache = new PermissionCache(mLocalCacheManager);
    permissionCache.setManager(new PersistentPermissionDao(mJdbcTemplate));
    return permissionCache;
  }

  @Bean
  NavigationManager navigationManager() {
    NavigationByPermissionResolver navigationByPermissionResolver = new NavigationByPermissionResolver(mAuthenticationRealm);
    navigationByPermissionResolver.setManager(new PersistentNavigationDao(mJdbcTemplate));
    return navigationByPermissionResolver;
  }

  @Bean
  AdditionalRolePermissionsManager additionalRolePermissionsManager() {
    AdditionalRolePermissionsCache additionalRolePermissionsCache = new AdditionalRolePermissionsCache(mLocalCacheManager);
    additionalRolePermissionsCache.setManager(new AdditionalRolePermissionsDao(mJdbcTemplate, getGenericDateFormat()));
    return additionalRolePermissionsCache;
  }

  @Bean
  StudentRecordManager studentRecordManager() {
    StudentRecordCache studentRecordCache = new StudentRecordCache(mLocalCacheManager);
    studentRecordCache.setManager(new PersistentStudentRecordDao(mJdbcTemplate));
    return studentRecordCache;
  }

  @Bean
  SemesterEnrollmentManager semesterEnrollmentManager() {
    SemesterEnrollmentCache semesterEnrollmentCache = new SemesterEnrollmentCache(mLocalCacheManager);
    semesterEnrollmentCache.setManager(new PersistentSemesterEnrollmentDao(mJdbcTemplate, getGenericDateFormat()));
    return semesterEnrollmentCache;
  }

  @Bean
  EnrollmentFromToManager enrollmentFromToManager() {
    EnrollmentFromToCache enrollmentFromToCache = new EnrollmentFromToCache(mLocalCacheManager);
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
  ParameterManager parameterManager(){
    return new PersistentParameterDao(mJdbcTemplate);
  }
  @Bean
  ParameterSettingManager parameterSettingManager(){

    return  new PersistentParameterSettingDao(mJdbcTemplate);
  }

  @Bean
  BearerAccessTokenManager bearerAccessTokenManager() {
    return new BearerAccessTokenDao(mJdbcTemplate);
  }
}
