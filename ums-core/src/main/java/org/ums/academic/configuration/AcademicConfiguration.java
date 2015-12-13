package org.ums.academic.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.academic.builder.Builder;
import org.ums.academic.builder.SemesterBuilder;
import org.ums.academic.dao.PersistentSemesterDao;
import org.ums.academic.dao.SemesterAccessControl;
import org.ums.academic.dao.SemesterCache;
import org.ums.academic.dao.SemesterTransaction;
import org.ums.domain.model.MutableSemester;
import org.ums.domain.model.Semester;
import org.ums.manager.SemesterManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class AcademicConfiguration {
  @Autowired
  DataSource mDataSource;

  PersistentSemesterDao getPersistentSemesterDao() {
    return new PersistentSemesterDao(new JdbcTemplate(mDataSource));
  }

  SemesterCache getSemesterCache() {
    SemesterCache semesterCache = new SemesterCache();
    semesterCache.setManager(getPersistentSemesterDao());
    return semesterCache;
  }

  SemesterAccessControl getSemesterAccessControl() {
    SemesterAccessControl semesterAccessControl = new SemesterAccessControl();
    semesterAccessControl.setManager(getSemesterCache());
    return semesterAccessControl;
  }


  @Bean
  SemesterManager getSemesterManager() {
    SemesterTransaction semesterTransaction = new SemesterTransaction();
    semesterTransaction.setManager(getSemesterAccessControl());
    return semesterTransaction;
  }

  @Bean
  Builder<Semester, MutableSemester> getSemesterBuilder() {
    return new SemesterBuilder();
  }

  @Bean
  List<Builder<Semester, MutableSemester>> getSemesterBuilders() {
    List<Builder<Semester, MutableSemester>> builders = new ArrayList<>();
    builders.add(new SemesterBuilder());
    return builders;
  }
}
