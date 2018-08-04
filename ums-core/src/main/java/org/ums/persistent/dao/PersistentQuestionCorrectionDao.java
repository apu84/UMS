package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.QuestionCorrectionInfoDaoDecorator;
import org.ums.domain.model.immutable.QuestionCorrectionInfo;
import org.ums.domain.model.mutable.MutableQuestionCorrectionInfo;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentQuestionCorrectionInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 7/11/2018.
 */
public class PersistentQuestionCorrectionDao extends QuestionCorrectionInfoDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentQuestionCorrectionDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  String INSERT_ALL =
      "Insert into DER_QUES_CORRECTION_INFO (ID,SEMESTER_ID,EXAM_TYPE,PROGRAM_ID,\"YEAR\",SEMESTER,COURSE_ID,INCORRECT_Q_NO,MISTAKE_TYPE,EXAM_DATE) values (?,?,?,?,?,?,?,?,?,TO_DATE(?,'DD-MM-YYYY'))";
  String SELECT_ALL =
      "SELECT SEMESTER_ID,EXAM_TYPE,PROGRAM_ID,\"YEAR\",SEMESTER,COURSE_ID,INCORRECT_Q_NO,MISTAKE_TYPE,to_char(EXAM_DATE,'DD-MM-YYYY') EXAM_DATE from DER_QUES_CORRECTION_INFO";
  String DELETE_ALL = "DELETE FROM DER_QUES_CORRECTION_INFO ";

  @Override
  public Long create(MutableQuestionCorrectionInfo pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ALL, id, pMutable.getSemesterId(), pMutable.getExamType(), pMutable.getProgramId(),
        pMutable.getYear(), pMutable.getSemester(), pMutable.getCourseId(), pMutable.getIncorrectQuestionNo(),
        pMutable.getTypeOfMistake(), pMutable.getExamDate());
    return id;
  }

  public int delete(List<MutableQuestionCorrectionInfo> pMutableList) {
    String query =
        DELETE_ALL
            + " WHERE  SEMESTER_ID=? AND EXAM_TYPE=? AND COURSE_ID=? AND EXAM_DATE=TO_DATE(?,'DD-MM-YYYY') AND PROGRAM_ID=?";
    List<Object[]> parameters = deleteParamList(pMutableList);
    return mJdbcTemplate.batchUpdate(query, parameters).length;
  }

  private List<Object[]> deleteParamList(List<MutableQuestionCorrectionInfo> pMutableApplicationQc) {
    List<Object[]> params = new ArrayList<>();
    for(QuestionCorrectionInfo app : pMutableApplicationQc) {
      params.add(new Object[] {app.getSemesterId(), app.getExamType(), app.getCourseId(), app.getExamDate(),
          app.getProgramId()});
    }
    return params;
  }

  @Override
  public List<QuestionCorrectionInfo> getInfoBySemesterIdAndExamType(Integer pSemesterId, Integer pExamType) {
    String query = SELECT_ALL + " WHERE SEMESTER_ID=? AND EXAM_TYPE=?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamType}, new QuestionCorrectionRowMapper());
  }

  @Override
  public List<QuestionCorrectionInfo> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new QuestionCorrectionRowMapper());
  }

  class QuestionCorrectionRowMapper implements RowMapper<QuestionCorrectionInfo> {
    @Override
    public QuestionCorrectionInfo mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentQuestionCorrectionInfo application = new PersistentQuestionCorrectionInfo();
      application.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      application.setExamType(pResultSet.getInt("EXAM_TYPE"));
      application.setProgramId(pResultSet.getInt("PROGRAM_ID"));
      application.setYear(pResultSet.getInt("YEAR"));
      application.setSemester(pResultSet.getInt("SEMESTER"));
      application.setCourseId(pResultSet.getString("COURSE_ID"));
      application.setIncorrectQuestionNo(pResultSet.getString("INCORRECT_Q_NO"));
      application.setTypeOfMistake(pResultSet.getString("MISTAKE_TYPE"));
      application.setExamDate(pResultSet.getString("EXAM_DATE"));
      return application;
    }
  }
}
