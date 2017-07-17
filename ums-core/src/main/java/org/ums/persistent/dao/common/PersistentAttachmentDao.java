package org.ums.persistent.dao.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.common.AttachmentDaoDecorator;
import org.ums.domain.model.immutable.common.Attachment;
import org.ums.domain.model.mutable.common.MutableAttachment;
import org.ums.enums.ApplicationType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.common.PersistentAttachment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 11-Jul-17.
 */
public class PersistentAttachmentDao extends AttachmentDaoDecorator {
  private static final Logger mLogger = LoggerFactory.getLogger(PersistentAttachmentDao.class);

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentAttachmentDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  private String SELECT_ALL = "select * from attachments";
  private String INSERT_ONE = "insert into attachments(id,type,type_id,file_name,last_modified) values(?,?,?,?,"
      + getLastModifiedSql() + ")";

  @Override
  public List<Attachment> getAll() {
    return super.getAll();
  }

  @Override
  public Attachment get(Long pId) {
    return super.get(pId);
  }

  @Override
  public List<Attachment> getAttachments(ApplicationType pApplicationType, String pApplicationId) {
    String query = SELECT_ALL + " where type=? and type_id=?";
    return mJdbcTemplate.query(query, new Object[] {pApplicationType.getValue(), pApplicationId},
        new AttachmentRowMapper());
  }

  @Override
  public Attachment validate(Attachment pReadonly) {
    return super.validate(pReadonly);
  }

  @Override
  public int update(MutableAttachment pMutable) {
    return super.update(pMutable);
  }

  @Override
  public int update(List<MutableAttachment> pMutableList) {
    return super.update(pMutableList);
  }

  @Override
  public int delete(MutableAttachment pMutable) {
    return super.delete(pMutable);
  }

  @Override
  public int delete(List<MutableAttachment> pMutableList) {
    return super.delete(pMutableList);
  }

  @Override
  public Long create(MutableAttachment pMutable) {
    String query = INSERT_ONE;
    return super.create(pMutable);
  }

  @Override
  public List<Long> create(List<MutableAttachment> pMutableList) {
    String query = INSERT_ONE;
    mJdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        Attachment attachment = pMutableList.get(i);
        ps.setLong(1, mIdGenerator.getNumericId());
        ps.setInt(2, attachment.getApplicationType().getValue());
        ps.setString(3, attachment.getApplicationId());
        ps.setString(4, attachment.getFileName());
      }

      @Override
      public int getBatchSize() {
        return 0;
      }
    });
    return null;
  }

  @Override
  public boolean exists(Long pId) {
    return super.exists(pId);
  }

  class AttachmentRowMapper implements RowMapper<Attachment> {
    @Override
    public Attachment mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentAttachment attachment = new PersistentAttachment();
      attachment.setId(rs.getLong("id"));
      attachment.setApplicationType(ApplicationType.get(rs.getInt("type")));
      attachment.setApplicationId(rs.getString("type_id"));
      attachment.setFileName(rs.getString("file_name"));
      attachment.setLastModified(rs.getString("last_modified"));
      return attachment;
    }
  }

}
