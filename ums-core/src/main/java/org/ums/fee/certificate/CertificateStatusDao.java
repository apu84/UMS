package org.ums.fee.certificate;

import com.google.common.collect.Lists;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import org.ums.filter.AbstractFilterQueryBuilder;
import org.ums.filter.ListFilter;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CertificateStatusDao extends CertificateStatusDaoDecorator {
  String SELECT_ALL = "SELECT ID, STUDENT_ID, SEMESTER_ID, FEE_CATEGORY, TRANSACTION_ID, STATUS, PROCESSED_ON, "
      + "PROCESSED_BY, LAST_MODIFIED FROM CERTIFICATE_STATUS ";
  String INSERT_ALL =
      "INSERT INTO CERTIFICATE_STATUS(ID, STUDENT_ID, SEMESTER_ID, FEE_CATEGORY, TRANSACTION_ID, STATUS,PROCESSED_ON,PROCESSED_BY,"
          + "LAST_MODIFIED) VALUES (?, ?, ?, ?, ?, ?, ?, ?," + getLastModifiedSql() + ") ";
  String UPDATE_ALL =
      "UPDATE CERTIFICATE_STATUS SET STATUS = ?, PROCESSED_ON = SYSDATE, PROCESSED_BY = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM CERTIFICATE_STATUS ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public CertificateStatusDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<CertificateStatus> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new CertificateStatusRowMapper());
  }

  @Override
  public CertificateStatus get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new CertificateStatusRowMapper());
  }

  @Override
  public int update(MutableCertificateStatus pMutable) {
    return update(Lists.newArrayList(pMutable));
  }

  @Override
  @Transactional
  public int update(List<MutableCertificateStatus> pMutableList) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    mJdbcTemplate.batchUpdate(query, getUpdateParamList(pMutableList));
    return super.update(pMutableList);
  }

  @Override
  public int delete(MutableCertificateStatus pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableCertificateStatus pMutable) {
    return create(Lists.newArrayList(pMutable)).get(0);
  }

  @Override
  @Transactional
  public List<Long> create(List<MutableCertificateStatus> pMutableList) {
    List<Object[]> params = getCreateParamList(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_ALL, params);
    super.create(pMutableList);
    return params.stream().map(param -> (Long) param[0]).collect(Collectors.toList());
  }

  @Override
  public List<CertificateStatus> paginatedList(int itemsPerPage, int pageNumber) {
    int startIndex = (itemsPerPage * (pageNumber - 1)) + 1;
    int endIndex = startIndex + itemsPerPage - 1;
    String query =
        "SELECT TMP2.*, IND FROM (SELECT ROWNUM IND, TMP1.* FROM (" + SELECT_ALL
            + " ORDER BY LAST_MODIFIED DESC) TMP1) TMP2 WHERE IND >= ? and IND <= ?  ";
    return mJdbcTemplate.query(query, new Object[] {startIndex, endIndex}, new CertificateStatusRowMapper());
  }

  @Override
  public List<CertificateStatus> paginatedFilteredList(int itemsPerPage, int pageNumber, List<ListFilter> pFilters) {
    FilterQueryBuilder queryBuilder = new FilterQueryBuilder(pFilters);
    int startIndex = (itemsPerPage * (pageNumber - 1)) + 1;
    int endIndex = startIndex + itemsPerPage - 1;
    String query =
        "SELECT TMP2.*, IND FROM (SELECT ROWNUM IND, TMP1.* FROM (" + SELECT_ALL + queryBuilder.getQuery()
            + " ORDER BY LAST_MODIFIED DESC) TMP1) TMP2 WHERE IND >= ? and IND <= ?  ";
    List<Object> params = queryBuilder.getParameters();
    params.add(startIndex);
    params.add(endIndex);
    return mJdbcTemplate.query(query, params.toArray(), new CertificateStatusRowMapper());
  }

  @Override
  public List<CertificateStatus> getByStudent(String pStudentId) {
    String query = SELECT_ALL + "WHERE STUDENT_ID = ? ORDER BY LAST_MODIFIED DESC";
    return mJdbcTemplate.query(query, new Object[] {pStudentId}, new CertificateStatusRowMapper());
  }

  private List<Object[]> getUpdateParamList(List<MutableCertificateStatus> pMutableCertificateStatuses) {
    List<Object[]> params = new ArrayList<>();
    for(CertificateStatus certificateStatus : pMutableCertificateStatuses) {
      params.add(new Object[] {certificateStatus.getStatus().getId(), certificateStatus.getUser().getId(),
          certificateStatus.getId()});
    }
    return params;
  }

  private List<Object[]> getCreateParamList(List<MutableCertificateStatus> pMutableCertificateStatuses) {
    List<Object[]> params = new ArrayList<>();
    for(CertificateStatus certificateStatus : pMutableCertificateStatuses) {
      params.add(new Object[] {mIdGenerator.getNumericId(), certificateStatus.getStudentId(),
          certificateStatus.getSemesterId(), certificateStatus.getFeeCategoryId(),
          certificateStatus.getTransactionId(), certificateStatus.getStatus().getId(),
          certificateStatus.getProcessedOn(), certificateStatus.getUserId()});
    }
    return params;
  }

  class CertificateStatusRowMapper implements RowMapper<CertificateStatus> {
    @Override
    public CertificateStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableCertificateStatus status = new PersistentCertificateStatus();
      status.setId(rs.getLong("ID"));
      status.setStudentId(rs.getString("STUDENT_ID"));
      status.setSemesterId(rs.getInt("SEMESTER_ID"));
      status.setFeeCategoryId(rs.getString("FEE_CATEGORY"));
      status.setTransactionId(rs.getString("TRANSACTION_ID"));
      status.setStatus(CertificateStatus.Status.get(rs.getInt("STATUS")));
      status.setProcessedOn(rs.getTimestamp("PROCESSED_ON"));
      status.setUserId(rs.getString("PROCESSED_BY"));
      status.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<CertificateStatus> reference = new AtomicReference<>(status);
      return reference.get();
    }
  }

  private class FilterQueryBuilder extends AbstractFilterQueryBuilder {
    FilterQueryBuilder(List<ListFilter> pFilters) {
      super(pFilters);
    }

    @Override
    protected String getQueryString(ListFilter pFilter) {
      if(pFilter.getFilterName().equalsIgnoreCase(FilterCriteria.STUDENT_ID.toString())) {
        return " STUDENT_ID = ? ";
      }
      if(pFilter.getFilterName().equalsIgnoreCase(FilterCriteria.STATUS.toString())) {
        return " STATUS = ? ";
      }
      return "";
    }
  }
}
