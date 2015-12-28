package org.ums.academic.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.academic.model.PersistentSyllabus;
import org.ums.domain.model.MutableSyllabus;
import org.ums.domain.model.Syllabus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentSyllabusDao extends SyllabusDaoDecorator {
  static String SELECT_ALL = "SELECT SYLLABUS_ID, SEMESTER_ID, PROGRAM_ID, LAST_MODIFIED FROM MST_SYLLABUS ";
  static String UPDATE_ONE = "UPDATE MST_SYLLABUS SET SEMESTER_ID = ?, PROGRAM_ID = ?, LAST_MODIFIED = " + getLastModifiedSql() + " ";
  static String DELETE_ONE = "DELETE FROM MST_SYLLABUS ";
  static String INSERT_ONE = "INSERT INTO MST_SYLLABUS(SYLLABUS_ID, SEMESTER_ID, PROGRAM_ID, LAST_MODIFIED) " +
      "VALUES(?, ?, ?, " + getLastModifiedSql() + ")";

  private JdbcTemplate mJdbcTemplate;

  public PersistentSyllabusDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  public Syllabus get(final String pSyllabusId) throws Exception {
    String query = SELECT_ALL + "WHERE SYLLABUS_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pSyllabusId}, new SyllabusRowMapper());
  }

  public List<Syllabus> getAll() throws Exception {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new SyllabusRowMapper());
  }

  public void update(final MutableSyllabus pSyllabus) throws Exception {
    String query = UPDATE_ONE + "WHERE SYLLABUS_ID = ?";
    mJdbcTemplate.update(query,
        pSyllabus.getSemester().getId(),
        pSyllabus.getProgram().getId(),
        pSyllabus.getId());
  }

  public void delete(final MutableSyllabus pSyllabus) throws Exception {
    String query = DELETE_ONE + "WHERE SYLLABUS_ID = ?";
    mJdbcTemplate.update(query, pSyllabus.getId());
  }

  public void create(final MutableSyllabus pSyllabus) throws Exception {
    mJdbcTemplate.update(INSERT_ONE,
        pSyllabus.getId(),
        pSyllabus.getSemester().getId(),
        pSyllabus.getProgram().getId());
  }

  class SyllabusRowMapper implements RowMapper<Syllabus> {
    @Override
    public Syllabus mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentSyllabus syllabus = new PersistentSyllabus();
      syllabus.setId(resultSet.getString("SYLLABUS_ID"));
      syllabus.setProgramId(resultSet.getInt("PROGRAM_ID"));
      syllabus.setSemesterId(resultSet.getInt("SEMESTER_ID"));
      syllabus.setLastModified(resultSet.getString("LAST_MODIFIED"));
      AtomicReference<Syllabus> atomicReference = new AtomicReference<>(syllabus);
      return atomicReference.get();
    }
  }
}
