package org.ums.usermanagement.application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

public class ApplicationDao extends ApplicationDaoDecorator {
  String SELECT_ALL = "SELECT ID, NAME, DESCRIPTION, LAST_MODIFIED FROM USER_APPLICATION ";
  String UPDATE_ALL = "UPDATE USER_APPLICATION SET NAME = ?, DESCRIPTION = ?, LAST_MODIFIED = " + getLastModifiedSql()
      + " ";
  String DELETE_ALL = "DELETE FROM USER_APPLICATION ";
  String INSERT_ALL = "INSERT INTO USER_APPLICATION(ID, NAME, DESCRIPTION, LAST_MODIFIED) VALUES (?, ?, ?, "
      + getLastModifiedSql() + ")";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public ApplicationDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Application> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new ApplicationRowMapper());
  }

  @Override
  public Application get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new ApplicationRowMapper());
  }

  @Override
  public int update(MutableApplication pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getName(), pMutable.getDescription(), pMutable.getId());
  }

  @Override
  public int delete(MutableApplication pMutable) {
    String query = DELETE_ALL + "WEHRE ID = ?";
    return mJdbcTemplate.update(DELETE_ALL, pMutable.getId());
  }

  @Override
  public Long create(MutableApplication pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ALL, id, pMutable.getName(), pMutable.getDescription());
    return id;
  }

  class ApplicationRowMapper implements RowMapper<Application> {
    @Override
    public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableApplication application = new PersistentApplication();
      application.setId(rs.getLong("ID"));
      application.setName(rs.getString("NAME"));
      application.setDescription(rs.getString("DESCRIPTION"));
      application.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<Application> atomicReference = new AtomicReference<>(application);
      return atomicReference.get();
    }
  }
}
