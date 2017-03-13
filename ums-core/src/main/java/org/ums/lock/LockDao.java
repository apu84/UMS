package org.ums.lock;

import org.springframework.jdbc.core.JdbcTemplate;

public class LockDao extends LockDaoDecorator {
  private String INSERT_ALL = "INSERT INTO RESOURCE_LOCK(ID, LAST_MODIFIED) VALUES(?, "
      + getLastModifiedSql() + ") ";
  private String SELECT_ALL = "SELECT ID, LAST_MODIFIED FROM RESOURCE_LOCK ";
  private String DELETE_ALL = "DELETE FROM RESOURCE_LOCK ";

  private JdbcTemplate mJdbcTemplate;

  public LockDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int delete(MutableLock pMutable) {
    String query = DELETE_ALL + " WHERE ID = ? ";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public String create(MutableLock pMutable) {
    mJdbcTemplate.update(INSERT_ALL, new Object[] {pMutable.getId()});
    return pMutable.getId();
  }
}
