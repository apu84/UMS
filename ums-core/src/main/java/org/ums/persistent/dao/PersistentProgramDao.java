package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.persistent.model.PersistentProgram;
import org.ums.decorator.ProgramDaoDecorator;
import org.ums.domain.model.immutable.Program;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentProgramDao extends ProgramDaoDecorator {
  static String SELECT_ALL =
      "SELECT PROGRAM_ID, TYPE_ID, PROGRAM_SHORT_NAME, PROGRAM_LONG_NAME, DEPT_ID, FACULTY_ID, LAST_MODIFIED FROM MST_PROGRAM ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentProgramDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  public Program get(final Integer pId) {
    String query = SELECT_ALL + "WHERE PROGRAM_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new ProgramRowMapper());
  }

  public List<Program> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new ProgramRowMapper());
  }

  @Override
  public List<Program> getProgramByDepartmentId(String pDepartmentId) {
    String query = SELECT_ALL + " WHERE DEPT_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pDepartmentId}, new ProgramRowMapper());
  }

  class ProgramRowMapper implements RowMapper<Program> {
    @Override
    public Program mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentProgram program = new PersistentProgram();
      program.setId(resultSet.getInt("PROGRAM_ID"));
      program.setProgramTypeId(resultSet.getInt("TYPE_ID"));
      program.setDepartmentId(resultSet.getString("DEPT_ID"));
      program.setShortName(resultSet.getString("PROGRAM_SHORT_NAME"));
      program.setLongName(resultSet.getString("PROGRAM_LONG_NAME"));
      program.setFacultyId(resultSet.getInt("FACULTY_ID"));
      program.setLastModified(resultSet.getString("LAST_MODIFIED"));
      AtomicReference<Program> atomicReference = new AtomicReference<>(program);
      return atomicReference.get();
    }
  }
}
