package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.UGSessionalMarksDaoDecorator;
import org.ums.domain.model.immutable.UGSessionalMarks;
import org.ums.domain.model.mutable.MutableUGSessionalMarks;
import org.ums.generator.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersistentUGSessionalMarksDao extends UGSessionalMarksDaoDecorator {
  String INSERT_ALL =
      "INSERT INTO UG_SESSIONAL_MARKS(ID, STUDENT_ID, SEMESTER_ID, COURSE_ID, GL, EXAM_TYPE, REG_TYPE, LAST_MODIFIED)"
          + " VALUES(?, ?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + ")";
  String DELETE_BY_STUDENT_SEMESTER =
      "DELETE FROM UG_SESSIONAL_MARKS WHERE STUDENT_ID = ? AND SEMESTER_ID = ? AND EXAM_TYPE = ? AND STATUS = ?";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentUGSessionalMarksDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Long> create(List<MutableUGSessionalMarks> pMutableList) {
    List<Object[]> params = getInsertParamList(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_ALL, params);
    return params.stream().map(param -> (Long)param[0]).collect(Collectors.toCollection(ArrayList::new));
  }

  private List<Object[]> getInsertParamList(List<MutableUGSessionalMarks> pSessionalMarks) {
    List<Object[]> params = new ArrayList<>();
    for(UGSessionalMarks sessionalMarks : pSessionalMarks) {
      params.add(new Object[] {mIdGenerator.getNumericId(), sessionalMarks.getStudent().getId(),
          sessionalMarks.getSemester().getId(), sessionalMarks.getCourse().getId(), sessionalMarks.getGradeLetter(),
          sessionalMarks.getExamType().getId(), sessionalMarks.getType().getId()});
    }

    return params;
  }

  @Override
  public int delete(List<MutableUGSessionalMarks> pMutableList) {
    return mJdbcTemplate.batchUpdate(DELETE_BY_STUDENT_SEMESTER, getDeleteParamList(pMutableList)).length;
  }

  private List<Object[]> getDeleteParamList(List<MutableUGSessionalMarks> pSessionalMarkses) {
    List<Object[]> params = new ArrayList<>();
    for(UGSessionalMarks sessionalMarks : pSessionalMarkses) {
      params.add(new Object[] {sessionalMarks.getStudent().getId(), sessionalMarks.getSemester().getId(),
          sessionalMarks.getExamType().getId(), sessionalMarks.getType().getId()});
    }

    return params;
  }
}
