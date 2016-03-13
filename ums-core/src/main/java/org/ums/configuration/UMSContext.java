package org.ums.configuration;

import org.apache.shiro.authc.credential.PasswordService;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.authentication.UMSAuthenticationRealm;
import org.ums.cache.*;
import org.ums.manager.*;
import org.ums.navigation.NavigationByPermissionResolver;
import org.ums.persistent.dao.*;
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

  SemesterManager getPersistentSemesterDao() {
    return new PersistentSemesterDao(mJdbcTemplate, getGenericDateFormat());
  }

  SemesterSyllabusMapManager getPersistentSemesterSyllabusMapDao() {
    return new PersistentSemesterSyllabusMapDao(new JdbcTemplate(mDataSource), syllabusManager());
  }

  @Bean
  SemesterManager semesterManager() {
    SemesterCache semesterCache = new SemesterCache(mLocalCacheManager);
    semesterCache.setManager(getPersistentSemesterDao());
    return semesterCache;
  }

  @Bean
  SemesterSyllabusMapManager semesterSyllabusMapManager() {
    return getPersistentSemesterSyllabusMapDao();
  }

  @Bean
  DateFormat getGenericDateFormat() {
    return new SimpleDateFormat(Constants.DATE_FORMAT);
  }

  @Bean
  ProgramTypeManager programTypeManager() {
    return new PersistentProgramTypeDao(mJdbcTemplate);
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
    return new PersistentCourseGroupDao(mJdbcTemplate);
  }

  @Bean
  CourseManager courseManager() {
    CourseCache courseCache = new CourseCache(mLocalCacheManager);
    courseCache.setManager(new PersistentCourseDao(mJdbcTemplate));
    return courseCache;
  }

  @Bean
  RoleManager roleManager() {
    return new PersistentRoleDao(mJdbcTemplate);
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
    return new PersistentTeacherDao(mJdbcTemplate);
  }

  @Bean
  CourseTeacherManager courseTeacherManager() {
    return new PersistentCourseTeacherDao(mJdbcTemplate);
  }

  @Bean
  PermissionManager permissionManager() {
    return new PersistentPermissionDao(mJdbcTemplate);
  }

  @Bean
  NavigationManager navigationManager() {
    NavigationByPermissionResolver navigationByPermissionResolver = new NavigationByPermissionResolver(mAuthenticationRealm);
    navigationByPermissionResolver.setManager(new PersistentNavigationDao(mJdbcTemplate));
    return navigationByPermissionResolver;
  }

  @Bean
  AdditionalRolePermissionsManager additionalRolePermissionsManager() {
    return new AdditionalRolePermissionsDao(mJdbcTemplate, getGenericDateFormat());
  }

  @Bean
  StudentRecordManager studentRecordManager() {
    StudentRecordCache studentRecordCache = new StudentRecordCache(mLocalCacheManager);
    studentRecordCache.setManager(new PersistentStudentRecordDao(mJdbcTemplate));
    return studentRecordCache;
  }

  @Bean
  SemesterEnrollmentManager semesterEnrollmentManager() {
    return new PersistentSemesterEnrollmentDao(mJdbcTemplate, getGenericDateFormat());
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
  //@Bean(name = "classRoomManager")
  @Bean
  ClassRoomManager classRoomManager() {
    return new PersistentClassRoomDao(mJdbcTemplate);
  }
  //@Bean(name = "examRoutineManager")
  @Bean
  ExamRoutineManager examRoutineManager() {
    return new PersistentExamRoutineDao(mJdbcTemplate);
  }

  @Bean
  PersistentOptionalCourseApplicationDao persistentOptionalCourseApplicationDao() {
   return new PersistentOptionalCourseApplicationDao(mJdbcTemplate);
  }


  @Bean
  LoginService  loginService() {
    return new LoginService();
  }
}
