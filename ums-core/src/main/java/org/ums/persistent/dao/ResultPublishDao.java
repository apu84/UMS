package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ResultPublishDaoDecorator;
import org.ums.domain.model.immutable.ResultPublish;
import org.ums.domain.model.mutable.MutableResultPublish;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentResultPublish;

public class ResultPublishDao extends ResultPublishDaoDecorator {
  String SELECT_ALL =
      "SELECT ID, PROGRAM_ID, SEMESTER_ID, PUBLISH_DATE, LAST_MODIFIED FROM RESULT_PUBLISH ";
  String INSERT_ALL =
      "INSERT INTO RESULT_PUBLISH(ID, PROGRAM_ID, SEMESTER_ID, PUBLISH_DATE, LAST_MODIFIED) VALUES "
          + "(?, ?, ?, SYSDATE, " + getLastModifiedSql() + ") ";
  String UPDATE_ALL =
      "UPDATE RESULT_PUBLISH SET PROGRAM_ID = ?, SEMESTER_ID = ?, PUBLISH_DATE = SYSDATE, "
          + "LAST_MODIFIED = " + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM RESULT_PUBLISH ";

  String EXIST = "SELECT COUNT(ID) FROM RESULT_PUBLISH ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public ResultPublishDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<ResultPublish> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new ResultPublishRowMapper());
  }

  @Override
  public ResultPublish get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new ResultPublishRowMapper());
  }

  @Override
  public int update(MutableResultPublish pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getProgramId(), pMutable.getSemesterId(),
        pMutable.getId());
  }

  @Override
  public int delete(MutableResultPublish pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableResultPublish pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ALL, id, pMutable.getProgramId(), pMutable.getSemesterId());
    return id;
  }

  @Override
  public boolean isResultPublished(Integer pProgramId, Integer pSemesterId) {
    String query = EXIST + "WHERE PROGRAM_ID = ? AND SEMESTER_ID = ? ";
    return mJdbcTemplate.queryForObject(query, Boolean.class, pProgramId, pSemesterId);
  }

  class ResultPublishRowMapper implements RowMapper<ResultPublish> {
    @Override
    public ResultPublish mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableResultPublish resultPublish = new PersistentResultPublish();
      resultPublish.setId(rs.getLong("ID"));
      resultPublish.setProgramId(rs.getInt("PROGRAM_ID"));
      resultPublish.setSemesterId(rs.getInt("SEMESTER_ID"));
      resultPublish.setPublishDate(rs.getTimestamp("PUBLISH_DATE"));
      resultPublish.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<ResultPublish> atomicReference = new AtomicReference<>(resultPublish);
      return atomicReference.get();
    }
  }
}
