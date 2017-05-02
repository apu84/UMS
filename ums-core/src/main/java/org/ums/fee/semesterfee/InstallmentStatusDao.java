package org.ums.fee.semesterfee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

public class InstallmentStatusDao extends InstallmentStatusDaoDecorator {
  String SELECT_ALL =
      "SELECT STUDENT_ID, SEMESTER_ID, INSTALLMENT_ORDER, PAYMENT_COMPLETED, RECEIVED_ON, LAST_MODIFIED FROM INSTALLMENT_STATUS ";
  String INSERT_ALL = "INSERT INTO INSTALLMENT_STATUS(ID, STUDENT_ID, SEMESTER_ID, INSTALLMENT_ORDER, LAST_MODIFIED) "
      + "VALUES(?, ?, ?, ?, " + getLastModifiedSql() + ")";
  String UPDATE_ALL = "UPDATE INSTALLMENT_STATUS SET PAYMENT_COMPLETED = ?, RECEIVED_ON = SYSDATE, LAST_MODIFIED = "
      + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM INSTALLMENT_STATUS ";
  String ORDER_BY = "ORDER BY INSTALLMENT_ORDER";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public InstallmentStatusDao(JdbcTemplate mJdbcTemplate, IdGenerator mIdGenerator) {
    this.mJdbcTemplate = mJdbcTemplate;
    this.mIdGenerator = mIdGenerator;
  }

  @Override
  public List<InstallmentStatus> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new InstallmentStatusRowMapper());
  }

  @Override
  public InstallmentStatus get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ? " + ORDER_BY;
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new InstallmentStatusRowMapper());
  }

  @Override
  public int update(MutableInstallmentStatus pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.isPaymentCompleted(), pMutable.getId());
  }

  @Override
  public int delete(MutableInstallmentStatus pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableInstallmentStatus pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ALL, id, pMutable.getStudentId(), pMutable.getSemesterId(),
        pMutable.getInstallmentOrder());
    return id;
  }

  @Override
  public List<InstallmentStatus> getInstallmentStatus(String pStudentId, Integer pSemesterId) {
    String query = SELECT_ALL + "WHERE STUDENT_ID = ? AND SEMESTER_ID = ? " + ORDER_BY;
    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId}, new InstallmentStatusRowMapper());
  }

  class InstallmentStatusRowMapper implements RowMapper<InstallmentStatus> {
    @Override
    public InstallmentStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableInstallmentStatus installmentStatus = new PersistentInstallmentStatus();
      installmentStatus.setId(rs.getLong("ID"));
      installmentStatus.setStudentId(rs.getString("STUDENT_ID"));
      installmentStatus.setSemesterId(rs.getInt("SEMESTER_ID"));
      installmentStatus.setInstallmentOrder(rs.getInt("INSTALLMENT_ORDER"));
      installmentStatus.setPaymentCompleted(rs.getBoolean("PAYMENT_COMPLETED"));
      installmentStatus.setReceivedOn(rs.getTimestamp("RECEIVED_ON"));
      AtomicReference<InstallmentStatus> reference = new AtomicReference<>(installmentStatus);
      return reference.get();
    }
  }
}
