package org.ums.academic.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.academic.model.PersistentSemester;
import org.ums.domain.model.MutableSemester;
import org.ums.domain.model.Semester;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PersistentSemesterDao extends SemesterDaoDecorator {

  static String SELECT_ALL = "SELECT ID, NAME, STARTDATE, STATUS FROM SEMESTER ";
  static String UPDATE_ONE = "UPDATE SEMESTER SET NAME = ?, STARTDATE = ?, STATUS = ? ";
  static String DELETE_ONE = "DELETE FROM SEMESTER ";
  static String INSERT_ONE = "INSERT INTO SEMESTER(ID, NAME, STARTDATE, STATUS) VALUES(?, ?, STR_TO_DATE(?, '%d/%m/%Y'),?)";
  private JdbcTemplate mJdbcTemplate;

  public PersistentSemesterDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  public Semester get(final String pSemesterId) throws Exception {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pSemesterId}, new SemesterRowMapper());
  }

  public List<Semester> getAll() throws Exception {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new SemesterRowMapper());
  }

  public void update(final MutableSemester pSemester) throws Exception {
    String query = UPDATE_ONE + "WHERE ID = ?";
    mJdbcTemplate.update(query, pSemester.getName(), pSemester.getStartDate(), pSemester.getStatus(), pSemester.getId());
  }

  public void delete(final MutableSemester pSemester) throws Exception {
    String query = DELETE_ONE + "WHERE ID = ?";
    mJdbcTemplate.update(query, pSemester.getId());
  }

  public void create(final MutableSemester pSemester) throws Exception {
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    mJdbcTemplate.update(INSERT_ONE, pSemester.getId(), pSemester.getName(),format.format(pSemester.getStartDate()), pSemester.getStatus());
  }

  class SemesterRowMapper implements RowMapper<Semester> {
    @Override
    public Semester mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentSemester semester = new PersistentSemester();
      semester.setId(resultSet.getString("ID"));
      semester.setName(resultSet.getString("NAME"));
      semester.setStartDate(resultSet.getDate("STARTDATE"));
      semester.setStatus(resultSet.getBoolean("STATUS"));
      return semester;
    }
  }
}
