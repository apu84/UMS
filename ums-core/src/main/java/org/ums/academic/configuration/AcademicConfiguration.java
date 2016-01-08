package org.ums.academic.configuration;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.academic.builder.*;
import org.ums.academic.dao.*;
import org.ums.domain.model.mutable.*;
import org.ums.domain.model.regular.*;
import org.ums.manager.*;
import org.ums.util.Constants;

import javax.sql.DataSource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

  SemesterManager getPersistentSemesterDao() {
    return new PersistentSemesterDao(new JdbcTemplate(mDataSource), getGenericDateFormat());
  }
  SemesterSyllabusMapManager getPersistentSemesterSyllabusMapDao() {
    return new PersistentSemesterSyllabusMapDao(new JdbcTemplate(mDataSource));
  }

  SyllabusManager getPersistentSyllabusDao() {
    return new PersistentSyllabusDao(new JdbcTemplate(mDataSource));
  }

  @Bean
  ContentManager<Semester, MutableSemester, Integer> getSemesterCache() {
    ContentCache<Semester, MutableSemester, Integer> semesterCache = new SemesterCache(mLocalCacheManager);
    semesterCache.setManager(getPersistentSemesterDao());
    return semesterCache;
  }

  ContentManager<Semester, MutableSemester, Integer> getSemesterAccessControl() {
    ContentAccessControl<Semester, MutableSemester, Integer> semesterAccessControl = new ContentAccessControl<>();
    semesterAccessControl.setManager(getSemesterCache());
    return semesterAccessControl;
  }


  @Bean
  SemesterManager semesterManager() {
/*    ContentTransaction<Semester, MutableSemester, Integer> semesterTransaction = new ContentTransaction<>();
    semesterTransaction.setManager(getSemesterAccessControl());*/
    return getPersistentSemesterDao();
  }

  @Bean
  SemesterSyllabusMapManager semesterSyllabusMapManager(){
    return getPersistentSemesterSyllabusMapDao();
  }
  @Bean
  ContentManager<Student, MutableStudent, String> studentManager() {
    return new PersistentStudentDao(mJdbcTemplate, getGenericDateFormat());
  }


  @Bean
  DateFormat getGenericDateFormat() {
    return new SimpleDateFormat(Constants.DATE_FORMAT);
  }

  @Bean
  ContentManager<ProgramType, MutableProgramType, Integer> programTypeManager() {
    return new PersistentProgramTypeDao(new JdbcTemplate(mDataSource));
  }

  @Bean
  ContentManager<Program, MutableProgram, Integer> programManager() {
    return new PersistentProgramDao(new JdbcTemplate(mDataSource));
  }

  @Bean
  ContentManager<Department, MutableDepartment, Integer> departmentManager() {
    return new PersistentDepartmentDao(new JdbcTemplate(mDataSource));
  }

  @Bean
  SyllabusManager syllabusManager() {
    return getPersistentSyllabusDao();
  }

  @Bean
  CourseGroupManager courseGroupManager() {
    return new PersistentCourseGroupDao(new JdbcTemplate(mDataSource));
  }

  @Bean
  ContentManager<Course, MutableCourse, String> courseManager() {
    return new PersistentCourseDao(new JdbcTemplate(mDataSource));
  }

  @Bean
  ContentManager<Role, MutableRole, Integer> roleManager() {
    return new PersistentRoleDao(new JdbcTemplate(mDataSource));
  }

  @Bean
  ContentManager<User, MutableUser, String> userManager() {
    return new PersistentUserDao(mJdbcTemplate);
  }

  @Bean
  Builder<Program, MutableProgram> getProgramBuilder() {
    return new ProgramBuilder();
  }

  @Bean
  Builder<Department, MutableDepartment> getDepartmentBuilder() {
    return new DepartmentBuilder();
  }

  @Bean
  Builder<Syllabus, MutableSyllabus> getSyllabusBuilder() {
    return new SyllabusBuilder();
  }

  @Bean
  List<Builder<Program, MutableProgram>> getProgramBuilders() {
    return Arrays.asList(new ProgramBuilder());
  }

  @Bean
  List<Builder<Department, MutableDepartment>> getDepartmentBuilders() {
    return Arrays.asList(new DepartmentBuilder());
  }

  @Bean
  List<Builder<Syllabus, MutableSyllabus>> getSyllabusBuilders() {
    return Arrays.asList(new SyllabusBuilder());
  }

  @Bean
  Builder<ProgramType, MutableProgramType> getProgramTypeBuilder() {
    return new ProgramTypeBuilder();
  }

  @Bean
  List<Builder<ProgramType, MutableProgramType>> getProgramTypeBuilders() {
    return Arrays.asList(new ProgramTypeBuilder());
  }

  @Bean
  Builder<Semester, MutableSemester> getSemesterBuilder() {
    return new SemesterBuilder(getGenericDateFormat(), programTypeManager());
  }

  @Bean
  List<Builder<Semester, MutableSemester>> getSemesterBuilders() {
    List<Builder<Semester, MutableSemester>> builders = new ArrayList<>();
    builders.add(new SemesterBuilder(getGenericDateFormat(), programTypeManager()));
    return builders;
  }

  @Bean
  Builder<SemesterSyllabusMap, MutableSemesterSyllabusMap> getSemesterSyllabusMapBuilder() {
    return new SemesterSyllabusMapBuilder(semesterSyllabusMapManager());
  }

  @Bean
  List<Builder<SemesterSyllabusMap, MutableSemesterSyllabusMap>> getSemesterSyllabusMapBuilders() {
    List<Builder<SemesterSyllabusMap, MutableSemesterSyllabusMap>> builders = new ArrayList<>();
    builders.add(new SemesterSyllabusMapBuilder(semesterSyllabusMapManager()));
    return builders;
  }

  @Bean
  Builder<CourseGroup, MutableCourseGroup> getCourseGroupBuilder() {
    return new CourseGroupBuilder();
  }

  @Bean
  List<Builder<CourseGroup, MutableCourseGroup>> getCourseGroupBuilders() {
    return Arrays.asList(new CourseGroupBuilder());
  }

  @Bean
  Builder<Course, MutableCourse> getCourseBuilder() {
    return new CourseBuilder();
  }

  @Bean
  List<Builder<Course, MutableCourse>> getCourseBuilders() {
    return Arrays.asList(new CourseBuilder());
  }

  @Bean
  Builder<Student, MutableStudent> getStudentBuilder() {
    return new StudentBuilder(getGenericDateFormat());
  }

  @Bean
  List<Builder<Student, MutableStudent>> getStudentBuilders() {
    return Arrays.asList(new StudentBuilder(getGenericDateFormat()));
  }
}
