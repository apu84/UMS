package org.ums.academic.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.academic.model.PersistentSemester;
import org.ums.domain.model.MutableSemester;
import org.ums.domain.model.Semester;
import org.ums.util.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.List;

public class PersistentSemesterDao extends ContentDaoDecorator<Semester, MutableSemester, Integer> {

  static String SELECT_ALL = "SELECT SEMESTER_ID, SEMESTER_NAME, START_DATE, END_DATE, PROGRAM_TYPE, STATUS FROM MST_SEMESTER ";
  static String UPDATE_ONE = "UPDATE MST_SEMESTER SET SEMESTER_NAME = ?,START_DATE = TO_DATE(?, '" + Constants.DATE_FORMAT + "'), " +
      "END_DATE= TO_DATE(?, '" + Constants.DATE_FORMAT + "'), PROGRAM_TYPE = ?, STATUS = ? ";
  static String DELETE_ONE = "DELETE FROM MST_SEMESTER ";
  static String INSERT_ONE = "INSERT INTO MST_SEMESTER(SEMESTER_ID, SEMESTER_NAME, START_DATE, END_DATE, PROGRAM_TYPE, STATUS) " +
      "VALUES(?, ?, TO_DATE(?, '" + Constants.DATE_FORMAT + "'), TO_DATE(?, '" + Constants.DATE_FORMAT + "'), ?, ?)";

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

  public List<Semester> getAll() throws Exception {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new SemesterRowMapper());
  }

  public void update(final MutableSemester pSemester) throws Exception {
    String query = UPDATE_ONE + "WHERE SEMESTER_ID = ?";
    mJdbcTemplate.update(query,
        pSemester.getName(),
        mDateFormat.format(pSemester.getStartDate()),
        pSemester.getEndDate() == null ? "" : mDateFormat.format(pSemester.getEndDate()),
        pSemester.getProgramType().getId(),
        pSemester.getStatus(),
        pSemester.getId());
  }

  public void delete(final MutableSemester pSemester) throws Exception {
    String query = DELETE_ONE + "WHERE SEMESTER_ID = ?";
    mJdbcTemplate.update(query, pSemester.getId());
  }

  public void create(final MutableSemester pSemester) throws Exception {
    mJdbcTemplate.update(INSERT_ONE,
        pSemester.getId(),
        pSemester.getName(),
        mDateFormat.format(pSemester.getStartDate()),
        pSemester.getEndDate() == null ? "" : mDateFormat.format(pSemester.getEndDate()),
        pSemester.getProgramType().getId(),
        pSemester.getStatus());
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
      semester.setStatus(resultSet.getBoolean("STATUS"));
      return semester;
    }
  }
}
