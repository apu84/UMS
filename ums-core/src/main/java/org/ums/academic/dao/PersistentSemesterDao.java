package org.ums.academic.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.academic.model.PersistentSemester;
import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.readOnly.Semester;
import org.ums.util.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentSemesterDao extends SemesterDaoDecorator {

  static String SELECT_ALL = "SELECT SEMESTER_ID, SEMESTER_NAME, START_DATE, END_DATE, PROGRAM_TYPE, STATUS, LAST_MODIFIED FROM MST_SEMESTER  ";
  static String UPDATE_ONE = "UPDATE MST_SEMESTER SET SEMESTER_NAME = ?,START_DATE = TO_DATE(?, '" + Constants.DATE_FORMAT + "'), " +
      "END_DATE= TO_DATE(?, '" + Constants.DATE_FORMAT + "'), PROGRAM_TYPE = ?, STATUS = ?, LAST_MODIFIED = " + getLastModifiedSql() + " ";
  static String DELETE_ONE = "DELETE FROM MST_SEMESTER ";
  static String INSERT_ONE = "INSERT INTO MST_SEMESTER(SEMESTER_ID, SEMESTER_NAME, START_DATE, END_DATE, PROGRAM_TYPE, STATUS, LAST_MODIFIED) " +
      "VALUES(?, ?, TO_DATE(?, '" + Constants.DATE_FORMAT + "'), TO_DATE(?, '" + Constants.DATE_FORMAT + "'), ?, ?, " + getLastModifiedSql() + ")";

  private JdbcTemplate mJdbcTemplate;
  private DateFormat mDateFormat;

  public PersistentSemesterDao(final JdbcTemplate pJdbcTemplate,
                               final DateFormat pDateFormat) {
    mJdbcTemplate = pJdbcTemplate;
    mDateFormat = pDateFormat;
  }

  public Semester get(final Integer pSemesterId) throws Exception {
    String query = SELECT_ALL + "WHERE SEMESTER_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pSemesterId}, new SemesterRowMapper());
  }

  @Override
  public List<Semester> getAll() throws Exception {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new SemesterRowMapper());
  }

  @Override
  public int update(final MutableSemester pSemester) throws Exception {
    String query = UPDATE_ONE + "WHERE SEMESTER_ID = ?";
    return mJdbcTemplate.update(query,
        pSemester.getName(),
        mDateFormat.format(pSemester.getStartDate()),
        pSemester.getEndDate() == null ? "" : mDateFormat.format(pSemester.getEndDate()),
        pSemester.getProgramType().getId(),
        pSemester.getStatus(),
        pSemester.getId());
  }

  @Override
  public int delete(final MutableSemester pSemester) throws Exception {
    String query = DELETE_ONE + "WHERE SEMESTER_ID = ?";
    return mJdbcTemplate.update(query, pSemester.getId());
  }

  @Override
  public int create(final MutableSemester pSemester) throws Exception {
    return mJdbcTemplate.update(INSERT_ONE,
        pSemester.getId(),
        pSemester.getName(),
        mDateFormat.format(pSemester.getStartDate()),
        pSemester.getEndDate() == null ? "" : mDateFormat.format(pSemester.getEndDate()),
        pSemester.getProgramType().getId(),
        pSemester.getStatus());
  }

  @Override
  public List<Semester> getSemesters(Integer pProgramType, Integer pLimit) throws Exception {
    String query = SELECT_ALL + "WHERE PROGRAM_TYPE = ? Order By START_DATE desc";
    return mJdbcTemplate.query(query, new Object[]{pProgramType}, new SemesterRowMapper());
  }

  @Override
  public Semester getPreviousSemester(Integer pSemesterId, Integer pProgramTypeId) throws Exception {
    String query = SELECT_ALL + "WHERE START_DATE =\n" +
        "              (SELECT MAX (START_DATE)\n" +
        "                 FROM MST_SEMESTER\n" +
        "                WHERE START_DATE <\n" +
        "                         (SELECT START_DATE\n" +
        "                            FROM MST_SEMESTER\n" +
        "                           WHERE SEMESTER_ID = ? AND PROGRAM_TYPE = ?))\n" +
        "       AND PROGRAM_TYPE = ?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pSemesterId, pProgramTypeId, pProgramTypeId}, new SemesterRowMapper());
  }

  class SemesterRowMapper implements RowMapper<Semester> {
    @Override
    public Semester mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentSemester semester = new PersistentSemester();
      semester.setId(resultSet.getInt("SEMESTER_ID"));
      semester.setName(resultSet.getString("SEMESTER_NAME"));
      semester.setStartDate(resultSet.getDate("START_DATE"));
      semester.setEndDate(resultSet.getDate("END_DATE"));
      semester.setProgramTypeId(resultSet.getInt("PROGRAM_TYPE"));
      semester.setStatus(Semester.Status.get(resultSet.getInt("STATUS")));
      semester.setLastModified(resultSet.getString("LAST_MODIFIED"));
      AtomicReference<Semester> atomicReference = new AtomicReference<>(semester);
      return atomicReference.get();
    }
  }
}
