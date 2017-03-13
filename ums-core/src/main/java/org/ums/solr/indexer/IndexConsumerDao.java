package org.ums.solr.indexer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;
import org.ums.solr.indexer.model.IndexConsumer;
import org.ums.solr.indexer.model.MutableIndexConsumer;

public class IndexConsumerDao extends IndexConsumerDaoDecorator {
  private String INSERT_ALL =
      "INSERT INTO INDEX_CONSUMER(ID, HOST, INSTANCE, HEAD, LAST_CHECKED, LAST_MODIFIED) "
          + "VALUES (?, ?, ?, ?, SYSDATE, " + getLastModifiedSql() + ")";
  private String SELECT_ALL =
      "SELECT ID, HOST, INSTANCE, HEAD, LAST_CHECKED, LAST_MODIFIED FROM INDEX_CONSUMER ";
  private String DELETE_ALL = "DELETE FROM INDEX_CONSUMER ";
  private String EXISTS = "SELECT COUNT(ID) FROM INDEX_CONSUMER ";
  private String UPDATE_ALL = "UPDATE INDEX_CONSUMER SET HOST = ?, INSTANCE = ?, HEAD = ?, "
      + "LAST_CHECKED = sysdate, LAST_MODIFIED = " + getLastModifiedSql() + " ";
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public IndexConsumerDao(JdbcTemplate mJdbcTemplate, IdGenerator mIdGenerator) {
    this.mJdbcTemplate = mJdbcTemplate;
    this.mIdGenerator = mIdGenerator;
  }

  @Override
  public IndexConsumer get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new IndexConsumerRowMapper());
  }

  @Override
  public IndexConsumer get(String pHost, String pPort) {
    String query = SELECT_ALL + "WHERE HOST = ? AND INSTANCE = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pHost, pPort},
        new IndexConsumerRowMapper());
  }

  @Override
  public Long create(MutableIndexConsumer pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ALL, id, pMutable.getHost(), pMutable.getInstance(),
        pMutable.getHead());
    return id;
  }

  @Override
  public int update(MutableIndexConsumer pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getHost(), pMutable.getInstance(),
        pMutable.getHead(), pMutable.getId());
  }

  @Override
  public int delete(MutableIndexConsumer pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public boolean exists(String pHost, String pPort) {
    String query = EXISTS + "WHERE HOST = ? AND INSTANCE = ? ";
    return mJdbcTemplate.queryForObject(query, Boolean.class, new Object[] {pHost, pPort});
  }

  class IndexConsumerRowMapper implements RowMapper<IndexConsumer> {
    @Override
    public IndexConsumer mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableIndexConsumer indexConsumer = new PersistentIndexConsumer();
      indexConsumer.setId(rs.getLong("ID"));
      indexConsumer.setHost(rs.getString("HOST"));
      indexConsumer.setInstance(rs.getString("INSTANCE"));
      indexConsumer.setHead(rs.getTimestamp("HEAD"));
      indexConsumer.setLastChecked(rs.getDate("LAST_CHECKED"));
      indexConsumer.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<IndexConsumer> atomicReference = new AtomicReference<>(indexConsumer);
      return atomicReference.get();
    }
  }
}
