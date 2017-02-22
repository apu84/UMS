package org.ums.indexer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.indexer.model.Index;
import org.ums.indexer.model.MutableIndex;

import com.google.common.collect.Lists;

public class IndexDao extends IndexDaoDecorator {
  private String INSERT_ALL =
      "INSERT INTO INDEXER(ENTITY_ID, ENTITY_TYPE, IS_DELETED, MODIFIED, LAST_MODIFIED) "
          + "VALUES(?, ?, ?, ?, " + getLastModifiedSql() + ") ";
  private String SELECT_ALL =
      "SELECT ID, ENTITY_ID, ENTITY_TYPE, IS_DELETED, MODIFIED, LAST_MODIFIED FROM INDEXER ";

  private JdbcTemplate mJdbcTemplate;

  public IndexDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public Index get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new IndexerRowMapper());
  }

  @Override
  public List<Index> after(Long pId) {
    String query = SELECT_ALL + "WHERE ID > ?";
    return mJdbcTemplate.query(query, new Object[] {pId}, new IndexerRowMapper());
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
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private List<Object[]> getInsertParamList(List<MutableIndex> pMutableIndexerList) {
    List<Object[]> params = new ArrayList<>();
    for(Index index : pMutableIndexerList) {
      params.add(new Object[] {index.getEntityId(), index.getEntityType(),
          index.getIsDeleted() ? 1 : 0, index.getModified()});
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
      mutableIndexer.setModified(rs.getDate("MODIFIED"));
      mutableIndexer.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<MutableIndex> atomicReference = new AtomicReference<>(mutableIndexer);
      return atomicReference.get();
    }
  }
}
