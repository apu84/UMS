package org.ums.solr.indexer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;
import org.ums.solr.indexer.model.Index;
import org.ums.solr.indexer.model.MutableIndex;

import com.google.common.collect.Lists;

public class IndexDao extends IndexDaoDecorator {
  private String INSERT_ALL =
      "INSERT INTO INDEXER(ID, ENTITY_ID, ENTITY_TYPE, IS_DELETED, MODIFIED, LAST_MODIFIED) "
          + "VALUES(?, ?, ?, ?, SYSDATE, " + getLastModifiedSql() + ") ";
  private String SELECT_ALL =
      "SELECT ID, ENTITY_ID, ENTITY_TYPE, IS_DELETED, MODIFIED, LAST_MODIFIED FROM INDEXER ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public IndexDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Index get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new IndexerRowMapper());
  }

  @Override
  public List<Index> after(Date pDate) {
    String query = SELECT_ALL + "WHERE MODIFIED > ? ORDER BY MODIFIED DESC ";
    return mJdbcTemplate.query(query, new Object[] {pDate}, new IndexerRowMapper());
  }

  @Override
  public Long create(MutableIndex pMutable) {
    return create(Lists.newArrayList(pMutable)).get(0);
  }

  @Override
  public List<Long> create(List<MutableIndex> pMutableList) {
    List<Object[]> params = getInsertParamList(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_ALL, params);
    return params.stream().map(param -> (Long) param[0])
        .collect(Collectors.toList());
  }

  private List<Object[]> getInsertParamList(List<MutableIndex> pMutableIndexerList) {
    List<Object[]> params = new ArrayList<>();
    for(Index index : pMutableIndexerList) {
      params.add(new Object[] {mIdGenerator.getNumericId(), index.getEntityId(),
          index.getEntityType(), index.isDeleted() ? 1 : 0});
    }
    return params;
  }

  class IndexerRowMapper implements RowMapper<Index> {
    @Override
    public Index mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableIndex mutableIndexer = new PersistentIndex();
      mutableIndexer.setId(rs.getLong("ID"));
      mutableIndexer.setEntityId(rs.getString("ENTITY_ID"));
      mutableIndexer.setEntityType(rs.getString("ENTITY_TYPE"));
      mutableIndexer.setIsDeleted(rs.getBoolean("IS_DELETED"));
      mutableIndexer.setModified(rs.getTimestamp("MODIFIED"));
      mutableIndexer.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<MutableIndex> atomicReference = new AtomicReference<>(mutableIndexer);
      return atomicReference.get();
    }
  }
}
