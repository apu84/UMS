package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.AreaOfInterestDaoDecorator;
import org.ums.domain.model.immutable.AreaOfInterest;
import org.ums.persistent.model.PersistentAreaOfInterest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentAreaOfInterestDao extends AreaOfInterestDaoDecorator {

  static String SELECT_ALL = "SELECT ID, NAME, LAST_MODIFIED FROM AREA_OF_INTERESTS";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAreaOfInterestDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<AreaOfInterest> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentAreaOfInterestDao.RoleRowMapper());
  }

  class RoleRowMapper implements RowMapper<AreaOfInterest> {

    @Override
    public AreaOfInterest mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentAreaOfInterest persistentAreaOfInterest = new PersistentAreaOfInterest();
      persistentAreaOfInterest.setId(resultSet.getInt("id"));
      persistentAreaOfInterest.seTAreaOfInterest(resultSet.getString("name"));
      persistentAreaOfInterest.setLastModified(resultSet.getString("last_modified"));

      return persistentAreaOfInterest;
    }
  }
}
