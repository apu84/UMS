package org.ums.academic.configuration;

import org.apache.shiro.authc.credential.PasswordService;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.academic.dao.*;
import org.ums.authentication.UMSAuthenticationRealm;
import org.ums.manager.*;
import org.ums.services.LoginService;
import org.ums.util.Constants;

import javax.sql.DataSource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Configuration
public class AcademicConfiguration {
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

  SyllabusManager getPersistentSyllabusDao() {
    return new PersistentSyllabusDao(mJdbcTemplate);
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
    return new PersistentDepartmentDao(mJdbcTemplate);
  }

  @Bean
  SyllabusManager syllabusManager() {
    return getPersistentSyllabusDao();
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

  @Bean(name = "studentManager")
  StudentManager studentManager() {
    return new PersistentStudentDao(mJdbcTemplate, getGenericDateFormat());
  }

  @Bean(name = "userManager")
  UserManager userManager() {
    return new PersistentUserDao(mJdbcTemplate);
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
    return new PersistentStudentRecordDao(mJdbcTemplate);
  }

  @Bean
  SemesterEnrollmentManager semesterEnrollmentManager() {
    return new PersistentSemesterEnrollmentDao(mJdbcTemplate, getGenericDateFormat());
  }

  @Bean
  EnrollmentFromToManager enrollmentFromToManager() {
    return new PersistentEnrollmentFromToDao(mJdbcTemplate);
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
