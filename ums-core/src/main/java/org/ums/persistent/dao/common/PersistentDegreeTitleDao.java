package org.ums.persistent.dao.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.common.DegreeTitleDaoDecorator;
import org.ums.domain.model.immutable.common.DegreeTitle;
import org.ums.domain.model.mutable.common.MutableDegreeTitle;
import org.ums.persistent.model.common.PersistentDegreeTitle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentDegreeTitleDao extends DegreeTitleDaoDecorator {

  static String GET_ALL = "SELECT ID, TITLE, DEGREE_LEVEL, LAST_MODIFIED FROM MST_DEGREE_TITLE";

  static String INSERT_ONE = "INSERT INTO MST_DEGREE_TITLE(ID, TITLE, DEGREE_LEVEL, LAST_MODIFIED) VALUES "
      + " (?, ?, ?, " + getLastModifiedSql() + " )";

  private JdbcTemplate mJdbcTemplate;

  public PersistentDegreeTitleDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public DegreeTitle get(final Integer pId) {
    String query = GET_ALL + " WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId},
        new PersistentDegreeTitleDao.AcademicDegreeRowMapper());
  }

  @Override
  public List<DegreeTitle> getAll() {
    return mJdbcTemplate.query(GET_ALL, new PersistentDegreeTitleDao.AcademicDegreeRowMapper());
  }

  @Override
  public Integer create(MutableDegreeTitle pMutable) {
    Integer id = createId(pMutable.getDegreeLevelId());
    mJdbcTemplate.update(INSERT_ONE, id, pMutable.getTitle(), pMutable.getDegreeLevelId());
    return id;
  }

  private Integer createId(Integer pDegreeLevelId) {
    Integer id = getLastId(pDegreeLevelId);
    return id == null ? pDegreeLevelId + 1 : id + 1;
  }

  private Integer getLastId(Integer pDegreeLevelId) {
    String query = "SELECT MAX(ID) AS ID FROM MST_DEGREE_TITLE WHERE DEGREE_LEVEL = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pDegreeLevelId}, Integer.class);
  }

  class AcademicDegreeRowMapper implements RowMapper<DegreeTitle> {

    @Override
    public DegreeTitle mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentDegreeTitle persistentDegreeTitle = new PersistentDegreeTitle();
      persistentDegreeTitle.setId(resultSet.getInt("ID"));
      persistentDegreeTitle.setTitle(resultSet.getString("TITLE"));
      persistentDegreeTitle.setDegreeLevelId(resultSet.getInt("DEGREE_LEVEL"));
      return persistentDegreeTitle;
    }
  }
}
