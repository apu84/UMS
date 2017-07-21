package org.ums.fee.dues;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.filter.AbstractFilterQueryBuilder;
import org.ums.filter.ListFilter;
import org.ums.generator.IdGenerator;

import com.google.common.collect.Lists;

public class StudentDuesDao extends StudentDuesDaoDecorator {
  String SELECT_ALL = "SELECT ID, FEE_CATEGORY, DESCRIPTION, STUDENT_ID, AMOUNT, ADDED_ON, ADDED_BY, PAY_BEFORE, "
      + "TRANSACTION_ID, STATUS, LAST_MODIFIED FROM STUDENT_DUES ";
  String INSERT_ALL =
      "INSERT INTO STUDENT_DUES (ID, FEE_CATEGORY, DESCRIPTION, STUDENT_ID, AMOUNT, ADDED_ON, ADDED_BY, "
          + "PAY_BEFORE, STATUS, LAST_MODIFIED) VALUES (?, ?, ?, ?, ?, SYSDATE, ?, ?, ?, " + getLastModifiedSql() + ")";
  String UPDATE_ALL = "UPDATE STUDENT_DUES SET FEE_CATEGORY = ?, DESCRIPTION = ?, STUDENT_ID = ?,  AMOUNT = ?,"
      + " ADDED_BY = ?, PAY_BEFORE = ?, TRANSACTION_ID = ?, STATUS = ?, LAST_MODIFIED = " + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM STUDENT_DUES ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public StudentDuesDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<StudentDues> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new DuesRowMapper());
  }

  @Override
  public StudentDues get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new DuesRowMapper());
  }

  @Override
  public int update(MutableStudentDues pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getTransactionId(), pMutable.getId());
  }

  @Override
  public int update(List<MutableStudentDues> pMutableList) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParamList(pMutableList)).length;
  }

  @Override
  public int delete(MutableStudentDues pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableStudentDues pMutable) {
    return create(Lists.newArrayList(pMutable)).get(0);
  }

  @Override
  public List<Long> create(List<MutableStudentDues> pMutableList) {
    List<Object[]> params = getInsertParamArray(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_ALL, params);
    return params.stream().map(param -> (Long) param[0]).collect(Collectors.toList());
  }

  @Override
  public List<StudentDues> getByStudent(String pStudentId) {
    String query = SELECT_ALL + "WHERE STUDENT_ID = ? ORDER BY ADDED_ON DESC ";
    return mJdbcTemplate.query(query, new Object[] {pStudentId}, new DuesRowMapper());
  }

  @Override
  public List<StudentDues> paginatedList(int itemsPerPage, int pageNumber, List<ListFilter> pFilters) {
    FilterQueryBuilder queryBuilder = new FilterQueryBuilder(pFilters);
    int startIndex = (itemsPerPage * (pageNumber - 1)) + 1;
    int endIndex = startIndex + itemsPerPage - 1;
    String query =
        "SELECT TMP2.*, IND FROM (SELECT ROWNUM IND, TMP1.* FROM (" + SELECT_ALL + queryBuilder.getQuery()
            + " ORDER BY ADDED_ON DESC) TMP1) TMP2 WHERE IND >= ? and IND <= ?  ";
    List<Object> params = queryBuilder.getParameters();
    params.add(startIndex);
    params.add(endIndex);
    return mJdbcTemplate.query(query, params.toArray(), new DuesRowMapper());
  }

  private List<Object[]> getUpdateParamList(List<MutableStudentDues> pMutableStudentDues) {
    List<Object[]> params = new ArrayList<>();
    for(StudentDues dues : pMutableStudentDues) {
      params.add(new Object[] {dues.getFeeCategoryId(), dues.getDescription(), dues.getStudentId(), dues.getAmount(),
          dues.getUserId(), dues.getPayBefore(), dues.getTransactionId(), dues.getStatus().getId(), dues.getId()});
    }
    return params;
  }

  private List<Object[]> getInsertParamArray(List<MutableStudentDues> pMutableStudentDues) {
    List<Object[]> params = new ArrayList<>();
    for(StudentDues dues : pMutableStudentDues) {
      params.add(new Object[] {mIdGenerator.getNumericId(), dues.getFeeCategoryId(), dues.getDescription(),
          dues.getStudentId(), dues.getAmount(), dues.getUserId(), dues.getPayBefore(), dues.getStatus().getId()});
    }
    return params;
  }

  private class DuesRowMapper implements RowMapper<StudentDues> {
    @Override
    public StudentDues mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableStudentDues dues = new PersistentStudentDues();
      dues.setId(rs.getLong("ID"));
      dues.setFeeCategoryId(rs.getString("FEE_CATEGORY"));
      dues.setDescription(rs.getString("DESCRIPTION"));
      dues.setStudentId(rs.getString("STUDENT_ID"));
      dues.setAmount(new BigDecimal(rs.getDouble("AMOUNT")));
      dues.setAddedOn(rs.getTimestamp("ADDED_ON"));
      dues.setUserId(rs.getString("ADDED_BY"));
      dues.setPayBefore(rs.getTimestamp("PAY_BEFORE"));
      dues.setLastModified(rs.getString("LAST_MODIFIED"));
      dues.setTransactionId(rs.getString("TRANSACTION_ID"));
      dues.setStatus(StudentDues.Status.get(rs.getInt("STATUS")));
      AtomicReference<StudentDues> reference = new AtomicReference<>(dues);
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
      if(pFilter.getFilterName().equalsIgnoreCase(FilterCriteria.DUE_STATUS.toString())) {
        return " STATUS = ? ";
      }
      if(pFilter.getFilterName().equalsIgnoreCase(FilterCriteria.DUE_TYPE.toString())) {
        return " FEE_CATEGORY = ? ";
      }
      return "";
    }
  }
}
