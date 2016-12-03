package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.MarksSubmissionStatusDaoDecorator;
import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.mutable.MutableMarksSubmissionStatus;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.ExamType;
import org.ums.persistent.model.PersistentMarksSubmissionStatus;
import org.ums.util.Constants;

public class PersistentMarkSubmissionStatusDao extends MarksSubmissionStatusDaoDecorator {
  String SELECT_ALL =
      "SELECT ID, SEMESTER_ID, COURSE_ID, STATUS, EXAM_TYPE, LAST_SUBMISSION_DATE, TOTAL_PART, PART_A_TOTAL, "
          + "PART_B_TOTAL, YEAR, SEMESTER, LAST_MODIFIED FROM MARKS_SUBMISSION_STATUS ";
  String UPDATE_ALL =
      "UPDATE MARKS_SUBMISSION_STATUS SET SEMESTER_ID = ?, COURSE_ID = ?, STATUS = ?, EXAM_TYPE = ?, "
          + "LAST_SUBMISSION_DATE = ?, TOTAL_PART = ?, PART_A_TOTAL = ?, PART_B_TOTAL = ?, "
          + "YEAR = ?, SEMESTER = ?, LAST_MODIFIED = " + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM MARKS_SUBMISSION_STATUS ";
  String INSERT_ALL =
      "INSERT INTO MST_SEMESTER(SEMESTER_ID, COURSE_ID, STATUS, EXAM_TYPE, LAST_SUBMISSION_DATE, TOTAL_PART, "
          + "PART_A_TOTAL, PART_B_TOTAL, YEAR, SEMESTER, LAST_MODIFIED) "
          + "VALUES(?, ?, ?, ?  ?, ?, ?, ?, ?, " + getLastModifiedSql() + ")";

  private JdbcTemplate mJdbcTemplate;
  private DateFormat mDateFormat;

  public PersistentMarkSubmissionStatusDao(JdbcTemplate pJdbcTemplate, DateFormat pDateFormat) {
    mJdbcTemplate = pJdbcTemplate;
    mDateFormat = pDateFormat;
  }

  @Override
  public MarksSubmissionStatus get(Integer pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId},
        new MarksSubmissionStatusRowMapper());
  }

  @Override
  public int update(MutableMarksSubmissionStatus pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getSemesterId(), pMutable.getCourseId(), pMutable
        .getStatus().getId(), pMutable.getExamType().getId(), pMutable.getLastSubmissionDate(),
        pMutable.getTotalPart(), pMutable.getPartATotal(), pMutable.getPartBTotal(), pMutable
            .getYear(), pMutable.getAcademicSemester(), pMutable.getId());
  }

  @Override
  public int update(List<MutableMarksSubmissionStatus> pMutableList) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParamArray(pMutableList)).length;
  }

  @Override
  public int delete(MutableMarksSubmissionStatus pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int create(MutableMarksSubmissionStatus pMutable) {
    return mJdbcTemplate.update(INSERT_ALL, pMutable.getSemesterId(), pMutable.getCourseId(),
        pMutable.getStatus(), pMutable.getExamType(), pMutable.getLastSubmissionDate(),
        pMutable.getTotalPart(), pMutable.getPartATotal(), pMutable.getPartBTotal(),
        pMutable.getYear(), pMutable.getAcademicSemester());
  }

  @Override
  public MarksSubmissionStatus get(Integer pSemesterId, String pCourseId, ExamType pExamType) {
    String query = SELECT_ALL + "WHERE SEMESTER_ID = ? AND COURSE_ID = ? AND EXAM_TYPE = ?";
    return mJdbcTemplate.queryForObject(query,
        new Object[] {pSemesterId, pCourseId, pExamType.getId()},
        new MarksSubmissionStatusRowMapper());
  }

  @Override
  public List<MarksSubmissionStatus> get(Integer pProgramId, Integer pSemesterId) {
    String query = SELECT_ALL + "WHERE SEMESTER_ID = ? ORDER BY COURSE_ID, EXAM_TYPE DESC";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId},
        new MarksSubmissionStatusRowMapper());
  }

  private List<Object[]> getUpdateParamArray(
      List<MutableMarksSubmissionStatus> pMarksSubmissionStatusList) {
    List<Object[]> params = new ArrayList<>();
    for(MarksSubmissionStatus pMutable : pMarksSubmissionStatusList) {
      params.add(new Object[] {pMutable.getSemesterId(), pMutable.getCourseId(),
          pMutable.getStatus().getId(), pMutable.getExamType().getId(),
          pMutable.getLastSubmissionDate(), pMutable.getTotalPart(), pMutable.getPartATotal(),
          pMutable.getPartBTotal(), pMutable.getYear(), pMutable.getAcademicSemester(),
          pMutable.getId()});
    }

    return params;
  }

  class MarksSubmissionStatusRowMapper implements RowMapper<MarksSubmissionStatus> {
    @Override
    public MarksSubmissionStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableMarksSubmissionStatus marksSubmissionStatus = new PersistentMarksSubmissionStatus();
      marksSubmissionStatus.setId(rs.getInt("ID"));
      marksSubmissionStatus.setSemesterId(rs.getInt("SEMESTER_ID"));
      marksSubmissionStatus.setCourseId(rs.getString("COURSE_ID"));
      marksSubmissionStatus.setStatus(CourseMarksSubmissionStatus.get(rs.getInt("STATUS")));
      marksSubmissionStatus.setExamType(ExamType.get(rs.getInt("EXAM_TYPE")));
      Date date = new Date();
      if(rs.getObject("LAST_SUBMISSION_DATE") != null) {
        date.setTime(rs.getTimestamp("LAST_SUBMISSION_DATE").getTime());
        marksSubmissionStatus.setLastSubmissionDate(date);
      }
      marksSubmissionStatus.setTotalPart(rs.getInt("TOTAL_PART"));
      marksSubmissionStatus.setPartATotal(rs.getInt("PART_A_TOTAL"));
      marksSubmissionStatus.setPartBTotal(rs.getInt("PART_B_TOTAL"));
      marksSubmissionStatus.setYear(rs.getInt("YEAR"));
      marksSubmissionStatus.setAcademicSemester(rs.getInt("SEMESTER"));
      marksSubmissionStatus.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<MarksSubmissionStatus> atomicReference =
          new AtomicReference<>(marksSubmissionStatus);
      return atomicReference.get();
    }
  }
}
