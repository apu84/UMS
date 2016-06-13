package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.UGSessionalMarksDaoDecorator;
import org.ums.domain.model.immutable.UGSessionalMarks;
import org.ums.domain.model.mutable.MutableUGSessionalMarks;

import java.util.ArrayList;
import java.util.List;


public class PersistentUGSessionalMarksDao extends UGSessionalMarksDaoDecorator {
  String INSERT_ALL = "INSERT INTO UG_SESSIONAL_MARKS(STUDENT_ID, SEMESTER_ID, COURSE_ID, GL, EXAM_TYPE, STATUS, LAST_MODIFIED)" +
      " VALUES(?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + ")";
  String DELETE_BY_STUDENT_SEMESTER = "DELETE FROM UG_SESSIONAL_MARKS WHERE STUDENT_ID = ? AND SEMESTER_ID = ? AND EXAM_TYPE = ? AND STATUS = ?";

  private JdbcTemplate mJdbcTemplate;

  public PersistentUGSessionalMarksDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int create(List<MutableUGSessionalMarks> pMutableList) throws Exception {
    return mJdbcTemplate.batchUpdate(INSERT_ALL, getInsertParamList(pMutableList)).length;
  }

  private List<Object[]> getInsertParamList(List<MutableUGSessionalMarks> pSessionalMarkses) throws Exception {
    List<Object[]> params = new ArrayList<>();
    for (UGSessionalMarks sessionalMarks : pSessionalMarkses) {
      params.add(new Object[]{
          sessionalMarks.getStudent().getId(),
          sessionalMarks.getSemester().getId(),
          sessionalMarks.getCourse().getId(),
          sessionalMarks.getGradeLetter(),
          sessionalMarks.getExamType().getValue(),
          sessionalMarks.getStatus().getValue()
      });
    }

    return params;
  }

  @Override
  public int delete(List<MutableUGSessionalMarks> pMutableList) throws Exception {
    return mJdbcTemplate.batchUpdate(DELETE_BY_STUDENT_SEMESTER, getDeleteParamList(pMutableList)).length;
  }

  private List<Object[]> getDeleteParamList(List<MutableUGSessionalMarks> pSessionalMarkses) throws Exception {
    List<Object[]> params = new ArrayList<>();
    for (UGSessionalMarks sessionalMarks : pSessionalMarkses) {
      params.add(new Object[]{
          sessionalMarks.getStudent().getId(),
          sessionalMarks.getSemester().getId(),
          sessionalMarks.getExamType().getValue(),
          sessionalMarks.getStatus().getValue()
      });
    }

    return params;
  }
}
