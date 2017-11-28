package org.ums.persistent.dao.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.common.AcademicDegreeDaoDecorator;
import org.ums.domain.model.immutable.common.AcademicDegree;
import org.ums.persistent.model.common.PersistentAcademicDegree;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentAcademicDegreeDao extends AcademicDegreeDaoDecorator {

  static String SELECT_ALL = "SELECT ID, TYPE, NAME, SHORT_NAME FROM MST_ACADEMIC_DEGREE_TYPE";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAcademicDegreeDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public AcademicDegree get(final Integer pId) {
    String query = SELECT_ALL + " WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId},
        new PersistentAcademicDegreeDao.AcademicDegreeRowMapper());
  }

  @Override
  public List<AcademicDegree> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentAcademicDegreeDao.AcademicDegreeRowMapper());
  }

  class AcademicDegreeRowMapper implements RowMapper<AcademicDegree> {

    @Override
    public AcademicDegree mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentAcademicDegree persistentAcademicDegree = new PersistentAcademicDegree();
      persistentAcademicDegree.setId(resultSet.getInt("ID"));
      persistentAcademicDegree.setDegreeType(resultSet.getInt("TYPE"));
      persistentAcademicDegree.setDegreeName(resultSet.getString("NAME"));
      persistentAcademicDegree.setDegreeShortName(resultSet.getString("SHORT_NAME"));
      return persistentAcademicDegree;
    }
  }
}
