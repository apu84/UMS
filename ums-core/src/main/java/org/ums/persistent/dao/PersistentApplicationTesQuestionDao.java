package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ApplicationTesQuestionDaoDecorator;
import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.immutable.ApplicationTesQuestions;
import org.ums.domain.model.mutable.MutableApplicationTES;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentApplicationTES;
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

  String SEMESTER_WISE_QUESTIONS = "SELECT ID,QUESTION,OBSERVATION_TYPE FROM TES_QUESTIONS";

  String SELECT_ALL_QUESTIONS =
      "SELECT a.ID,a.QUESTION,a.OBSERVATION_TYPE from TES_QUESTIONS a,TES_SET_QUESTIONS b WHERE  "
          + "a.ID=b.QUESTION_ID AND b.SEMESTER_ID=?";

  @Override
  public List<MutableApplicationTES> getMigrationQuestions(Integer pSemesterId) {
    String query = SELECT_ALL_QUESTIONS;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId}, new ApplicationTesRowMapperForQuestions());
  }

  @Override
  public List<MutableApplicationTES> getQuestions() {
    String query = SEMESTER_WISE_QUESTIONS;
    return mJdbcTemplate.query(query, new ApplicationTesRowMapperForQuestions());
  }

  @Override
  public List<ApplicationTES> getAllQuestions(Integer pSemesterId) {
    String query = SELECT_ALL_QUESTIONS;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId}, new ApplicationTesRowMapperForGetAllQuestions());
  }

  @Override
  public ApplicationTesQuestions get(Long pId) {

    String query = QUESTION_INFO;
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new questionInfoRowMapper());
  }

  class ApplicationTesRowMapperForQuestions implements RowMapper<MutableApplicationTES> {
    @Override
    public MutableApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setQuestionId(pResultSet.getLong("ID"));
      application.setQuestionDetails(pResultSet.getString("QUESTION"));
      application.setObservationType(pResultSet.getInt("OBSERVATION_TYPE"));
      return application;
    }
  }

  class ApplicationTesRowMapperForGetAllQuestions implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      getData(pResultSet, application);
      return application;
    }
  }

  private void getData(ResultSet pResultSet, PersistentApplicationTES application) throws SQLException {
    application.setQuestionId(pResultSet.getLong("ID"));
    application.setQuestionDetails(pResultSet.getString("QUESTION"));
    application.setObservationType((pResultSet.getInt("OBSERVATION_TYPE")));
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
