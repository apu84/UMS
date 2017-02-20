package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ParameterDaoDecorator;
import org.ums.domain.model.immutable.Parameter;
import org.ums.domain.model.mutable.MutableParameter;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentParameter;

/**
 * Created by My Pc on 3/13/2016.
 */
public class PersistentParameterDao extends ParameterDaoDecorator {
  static String SELECT_ALL =
      "SELECT PARAMETER_ID,PARAMETER,SHORT_DESCRIPTION,LONG_DESCRIPTION,TYPE,LAST_MODIFIED FROM MST_PARAMETER";
  static String INSERT_ONE =
      "INSERT INTO MST_PARAMETER(PARAMETER_ID, PARAMETER,SHORT_DESCRIPTION,LONG_DESCRIPTION,TYPE,LAST_MODIFIED) VALUES (?, ?,?,"
          + getLastModifiedSql() + ")";
  static String UPDATE_ONE =
      "UPDATE MST_PARAMETER SET PARAMETER=?, SHORT_DESCRIPTION=?,LONG_DESCRIPTION=?,TYPE=?,LAST_MODIFIED = "
          + getLastModifiedSql() + " ";
  static String DELETE_ONE = "DELETE FROM MST_PARAMETER ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentParameterDao(final JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Parameter> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new ParameterRowMapper());
  }

  @Override
  public Parameter get(Long pId) {
    String query = SELECT_ALL + " WHERE PARAMETER_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new ParameterRowMapper());
  }

  @Override
  public int update(MutableParameter pMutable) {
    String query = UPDATE_ONE + " WHERE PARAMETER_ID=?";
    return mJdbcTemplate.update(query, pMutable.getParameter(), pMutable.getShortDescription(),
        pMutable.getLongDescription(), pMutable.getType(), pMutable.getId());
  }

  @Override
  public int delete(MutableParameter pMutable) {
    String query = DELETE_ONE + " WHERE PARAMETER_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableParameter pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ONE, id, pMutable.getParameter(), pMutable.getShortDescription(),
        pMutable.getLongDescription(), pMutable.getType());
    return id;
  }

  class ParameterRowMapper implements RowMapper<Parameter> {
    @Override
    public Parameter mapRow(ResultSet pResultSet, int pI) throws SQLException {

      PersistentParameter parameter = new PersistentParameter();
      parameter.setId(pResultSet.getLong("PARAMETER_ID"));
      parameter.setParameter(pResultSet.getString("PARAMETER"));
      parameter.setShortDescription(pResultSet.getString("SHORT_DESCRIPTION"));
      parameter.setLongDescription(pResultSet.getString("LONG_DESCRIPTION"));
      parameter.setType(pResultSet.getInt("TYPE"));
      parameter.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return parameter;
    }
  }
}
