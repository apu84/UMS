package org.ums.persistent.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.UGTheoryMarksDaoDecorator;
import org.ums.domain.model.immutable.UGTheoryMarks;
import org.ums.domain.model.mutable.MutableUGTheoryMarks;
import org.ums.generator.IdGenerator;

public class PersistentUGTheoryMarksDao extends UGTheoryMarksDaoDecorator {
  String INSERT_ALL =
      "INSERT INTO UG_THEORY_MARKS(ID, STUDENT_ID, SEMESTER_ID, COURSE_ID, GL, EXAM_TYPE, REG_TYPE, LAST_MODIFIED)"
          + " VALUES(?, ?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + ")";
  String DELETE_BY_STUDENT_SEMESTER =
      "DELETE FROM UG_THEORY_MARKS WHERE STUDENT_ID = ? AND SEMESTER_ID = ? AND EXAM_TYPE = ? AND STATUS = ?";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentUGTheoryMarksDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Long> create(List<MutableUGTheoryMarks> pMutableList) {
    List<Object[]> params = getInsertParamList(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_ALL, params);
    return params.stream().map(param -> (Long)param[0]).collect(Collectors.toCollection(ArrayList::new));
  }

  private List<Object[]> getInsertParamList(List<MutableUGTheoryMarks> pTheoryMarkses) {
    List<Object[]> params = new ArrayList<>();
    for(UGTheoryMarks theoryMarks : pTheoryMarkses) {
      params.add(new Object[] {mIdGenerator.getNumericId(), theoryMarks.getStudent().getId(),
          theoryMarks.getSemester().getId(), theoryMarks.getCourse().getId(),
          theoryMarks.getGradeLetter(), theoryMarks.getExamType().getId(),
          theoryMarks.getType().getId()});
    }

    return params;
  }

  @Override
  public int delete(List<MutableUGTheoryMarks> pMutableList) {
    return mJdbcTemplate.batchUpdate(DELETE_BY_STUDENT_SEMESTER, getDeleteParamList(pMutableList)).length;
  }

  private List<Object[]> getDeleteParamList(List<MutableUGTheoryMarks> pTheoryMarkses) {
    List<Object[]> params = new ArrayList<>();
    for(UGTheoryMarks theoryMarks : pTheoryMarkses) {
      params.add(new Object[] {theoryMarks.getStudent().getId(), theoryMarks.getSemester().getId(),
          theoryMarks.getExamType().getId(), theoryMarks.getType().getId()});
    }

    return params;
  }

}
