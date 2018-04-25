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

  String QUESTION_INFO = "SELECT ID,QUESTION,OBSERVATION_TYPE,Inserted_On from TES_QUESTIONS WHERE ID=?";

  @Override
  public ApplicationTesQuestions get(Long pId) {

    String query = QUESTION_INFO;
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new questionInfoRowMapper());
  }

  class questionInfoRowMapper implements RowMapper<ApplicationTesQuestions> {
    @Override
    public ApplicationTesQuestions mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTesQuestion application = new PersistentApplicationTesQuestion();
      application.setQuestionId(pResultSet.getLong("ID"));
      application.setQuestionDetails(pResultSet.getString("QUESTION"));
      application.setObservationType(pResultSet.getInt("OBSERVATION_TYPE"));
      application.setInsertionDate(pResultSet.getString("Inserted_On"));
      return application;
    }
  }
}
