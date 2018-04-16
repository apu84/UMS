package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ApplicationTesQuestionDaoDecorator;
import org.ums.domain.model.immutable.ApplicationTesQuestions;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentApplicationTesQuestion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 4/16/2018.
 */
public class PersistentApplicationTesQuestionDao extends ApplicationTesQuestionDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentApplicationTesQuestionDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  String getQuestionInfo =
      "select QUESTION_DETAILS,OBSERVATION_TYPE,SEMESTER_ID from APPLICATION_TES_QUESTIONS WHERE QUESTION_ID=?";

  @Override
  public List<ApplicationTesQuestions> getQuestionInfo(Integer pQuestionId) {
    String query = getQuestionInfo;
    return mJdbcTemplate.query(query, new Object[] {pQuestionId}, new ApplicationTESRowMapperForQuestionWiseReport());
  }
}


class ApplicationTESRowMapperForQuestionWiseReport implements RowMapper<ApplicationTesQuestions> {
  @Override
  public ApplicationTesQuestions mapRow(ResultSet pResultSet, int pI) throws SQLException {
    PersistentApplicationTesQuestion application = new PersistentApplicationTesQuestion();
    application.setQuestionDetails(pResultSet.getString("QUESTION_DETAILS"));
    application.setObservationType(pResultSet.getInt("OBSERVATION_TYPE"));
    application.setSemester(pResultSet.getInt("SEMESTER_ID"));
    return application;
  }
}
