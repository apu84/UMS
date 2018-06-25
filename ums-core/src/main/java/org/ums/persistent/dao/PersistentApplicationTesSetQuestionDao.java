package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ApplicationTesSetQuestionDaoDecorator;
import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.immutable.ApplicationTesSetQuestions;
import org.ums.domain.model.mutable.MutableApplicationTesSetQuestions;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentApplicationTES;
import org.ums.persistent.model.PersistentApplicationTesSetQuestion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
public class PersistentApplicationTesSetQuestionDao extends ApplicationTesSetQuestionDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentApplicationTesSetQuestionDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  String SET_QUESTION_INFO = "SELECT QUESTION_ID,SEMESTER_ID from TES_SET_QUESTIONS WHERE ID=?";
  String SET_QUESTIONS =
      "Insert  into  TES_SET_QUESTIONS (ID,QUESTION_ID,SEMESTER_ID,INSERTED_ON)  values  (?,?,?,sysdate)";
  String QUESTION_SEMESTER_MAP = "select QUESTION_ID,SEMESTER_ID from TES_SET_QUESTIONS WHERE SEMESTER_ID=?";

  @Override
  public ApplicationTesSetQuestions get(Long pId) {
    String query = SET_QUESTION_INFO;
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new setQuestionInfoRowMapper());
  }

  @Override
  public List<ApplicationTES> getQuestionSemesterMap(Integer pSemesterId) {
    String query = QUESTION_SEMESTER_MAP;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId}, new ApplicationTesRowMapperForQuestionsMapping());
  }

  @Override
  public List<Long> create(List<MutableApplicationTesSetQuestions> pMutableList) {
    List<Object[]>  parameters  =  getInsertParamListForSetQuestions(pMutableList);
    mJdbcTemplate.batchUpdate(SET_QUESTIONS,  parameters);
    return  parameters.stream()
            .map(paramArray  ->  (Long)  paramArray[0])
            .collect(Collectors.toCollection(ArrayList::new));
  }

  private List<Object[]> getInsertParamListForSetQuestions(
      List<MutableApplicationTesSetQuestions> pMutableApplicationTES) {
    List<Object[]> params = new ArrayList<>();
    for(ApplicationTesSetQuestions app : pMutableApplicationTES) {
      params.add(new Object[] {mIdGenerator.getNumericId(), app.getQuestionId(), app.getSemesterId()});
    }
    return params;
  }

  class ApplicationTesRowMapperForQuestionsMapping implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setQuestionId(pResultSet.getLong("QUESTION_ID"));
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      return application;
    }
  }

  class setQuestionInfoRowMapper implements RowMapper<ApplicationTesSetQuestions> {
    @Override
    public ApplicationTesSetQuestions mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTesSetQuestion application = new PersistentApplicationTesSetQuestion();
      application.setQuestionId(pResultSet.getLong("QUESTION_ID"));
      application.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      return application;
    }
  }
}
