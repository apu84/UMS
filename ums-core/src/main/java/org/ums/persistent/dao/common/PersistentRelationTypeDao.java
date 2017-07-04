package org.ums.persistent.dao.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.common.RelationTypeDaoDecorator;
import org.ums.domain.model.immutable.common.RelationType;
import org.ums.persistent.model.common.PersistentRelationType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentRelationTypeDao extends RelationTypeDaoDecorator {

  static String SELECT_ALL = "SELECT ID,NAME FROM MST_RELATION_TYPE";

  private JdbcTemplate mJdbcTemplate;

  public PersistentRelationTypeDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public RelationType get(final Integer pId) {
    String query = SELECT_ALL + " WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId},
        new PersistentRelationTypeDao.RelationTypeRowMapper());
  }

  @Override
  public List<RelationType> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentRelationTypeDao.RelationTypeRowMapper());
  }

  class RelationTypeRowMapper implements RowMapper<RelationType> {
    @Override
    public RelationType mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentRelationType persistentRelationType = new PersistentRelationType();
      persistentRelationType.setId(resultSet.getInt("id"));
      persistentRelationType.setRelationType(resultSet.getString("name"));
      return persistentRelationType;
    }
  }
}
