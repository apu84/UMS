package org.ums.persistent.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.UGRegistrationResultDaoDecorator;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;

import java.util.ArrayList;
import java.util.List;

public class PersistentUGRegistrationResultDao extends UGRegistrationResultDaoDecorator {
  String INSERT_ALL = "INSERT INTO UG_REGISTRATION_RESULT(STUDENT_ID, SEMESTER_ID, COURSE_ID, GL, EXAM_TYPE, STATUS, LAST_MODIFIED)" +
      " VALUES(?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + ")";
  String DELETE_BY_STUDENT_SEMESTER = "DELETE FROM UG_REGISTRATION_RESULT WHERE STUDENT_ID = ? AND SEMESTER_ID = ? AND EXAM_TYPE = ? AND STATUS = ?";

  private JdbcTemplate mJdbcTemplate;

  public PersistentUGRegistrationResultDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int create(List<MutableUGRegistrationResult> pMutableList) throws Exception {
    return mJdbcTemplate.batchUpdate(INSERT_ALL, getInsertParamList(pMutableList)).length;
  }

  private List<Object[]> getInsertParamList(List<MutableUGRegistrationResult> pRegistrationResults) throws Exception {
    List<Object[]> params = new ArrayList<>();
    for (UGRegistrationResult registrationResult : pRegistrationResults) {
      params.add(new Object[]{
          registrationResult.getStudent().getId(),
          registrationResult.getSemester().getId(),
          registrationResult.getCourse().getId(),
          registrationResult.getGradeLetter(),
          registrationResult.getExamType().getValue(),
          registrationResult.getStatus().getValue()
      });
    }

    return params;
  }

  @Override
  public int delete(List<MutableUGRegistrationResult> pMutableList) throws Exception {
    return mJdbcTemplate.batchUpdate(DELETE_BY_STUDENT_SEMESTER, getDeleteParamList(pMutableList)).length;
  }

  private List<Object[]> getDeleteParamList(List<MutableUGRegistrationResult> pRegistrationResults) throws Exception {
    List<Object[]> params = new ArrayList<>();
    for (UGRegistrationResult registrationResult : pRegistrationResults) {
      params.add(new Object[]{
          registrationResult.getStudent().getId(),
          registrationResult.getSemester().getId(),
          registrationResult.getExamType().getValue(),
          registrationResult.getStatus().getValue()
      });
    }

    return params;
  }

}
