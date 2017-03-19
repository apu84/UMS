package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.FacultyDaoDecorator;
import org.ums.domain.model.immutable.Faculty;
import org.ums.persistent.model.PersistentFaculty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Monjur-E-Morshed on 06-Dec-16.
 */
public class PersistentFacultyDao extends FacultyDaoDecorator {
  static String SELECT_ALL = "select f.ID,f.LONG_NAME, f.SHORT_NAME, f.LAST_MODIFIED  from MST_FACULTY f";

  private JdbcTemplate mJdbcTemplate;

  public PersistentFacultyDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<Faculty> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new FacultyRowMapper());
  }

  @Override
  public Faculty get(Integer pId) {
    String query = SELECT_ALL + " WHERE ID=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new FacultyRowMapper());
  }

  class FacultyRowMapper implements RowMapper<Faculty> {
    @Override
    public Faculty mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentFaculty faculty = new PersistentFaculty();
      faculty.setId(pResultSet.getInt("ID"));
      faculty.setLongName(pResultSet.getString("LONG_NAME"));
      faculty.setShortName(pResultSet.getString("SHORT_NAME"));
      faculty.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return faculty;
    }
  }
}
