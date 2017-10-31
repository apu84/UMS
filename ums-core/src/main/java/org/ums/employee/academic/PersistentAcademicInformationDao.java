package org.ums.employee.academic;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentAcademicInformationDao extends AcademicInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_ACADEMIC_INFO (ID, EMPLOYEE_ID, DEGREE, INSTITUTE, PASSING_YEAR, RESULT, LAST_MODIFIED) VALUES (? ,? ,? ,?, ?, ?,"
          + getLastModifiedSql() + ")";

  static String GET_ONE =
      "Select ID, EMPLOYEE_ID, DEGREE, INSTITUTE, PASSING_YEAR, RESULT, LAST_MODIFIED From EMP_ACADEMIC_INFO ";

  static String UPDATE_ALL =
      "UPDATE EMP_ACADEMIC_INFO SET DEGREE = ?, INSTITUTE = ?, PASSING_YEAR = ?, RESULT = ?, LAST_MODIFIED ="
          + getLastModifiedSql() + " ";

  static String DELETE_ALL = "DELETE FROM EMP_ACADEMIC_INFO ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentAcademicInformationDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public int saveAcademicInformation(List<MutableAcademicInformation> pMutableAcademicInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query, getEmployeeAcademicInformationParams(pMutableAcademicInformation)).length;
  }

  @Override
  public int deleteAcademicInformation(String pEmployeeId) {
    String query = DELETE_ALL + " WHERE EMPLOYEE_ID=?";
    return mJdbcTemplate.update(query, pEmployeeId);
  }

  private List<Object[]> getEmployeeAcademicInformationParams(
      List<MutableAcademicInformation> pMutableAcademicInformation) {
    List<Object[]> params = new ArrayList<>();
    for(AcademicInformation academicInformation : pMutableAcademicInformation) {
      params.add(new Object[] {mIdGenerator.getNumericId(), academicInformation.getEmployeeId(),
          academicInformation.getDegreeId(), academicInformation.getInstitute(), academicInformation.getPassingYear(),
          academicInformation.getResult()});
    }
    return params;
  }

  @Override
  public List<AcademicInformation> getEmployeeAcademicInformation(final String pEmployeeId) {
    String query = GET_ONE + " Where EMPLOYEE_ID=?";
    return mJdbcTemplate.query(query, new Object[] {pEmployeeId}, new PersistentAcademicInformationDao.RoleRowMapper());
  }

  @Override
  public int updateAcademicInformation(List<MutableAcademicInformation> pMutableAcademicInformation) {
    String query = UPDATE_ALL + "WHERE EMPLOYEE_ID = ? and ID = ? ";
    return mJdbcTemplate.batchUpdate(query, getUpdateParams(pMutableAcademicInformation)).length;
  }

  private List<Object[]> getUpdateParams(List<MutableAcademicInformation> pMutableAcademicInformation) {
    List<Object[]> params = new ArrayList<>();
    for(AcademicInformation pAcademicInformation : pMutableAcademicInformation) {
      params.add(new Object[] {pAcademicInformation.getDegreeId(), pAcademicInformation.getInstitute(),
          pAcademicInformation.getPassingYear(), pAcademicInformation.getResult(),
          pAcademicInformation.getEmployeeId(), pAcademicInformation.getId()});
    }
    return params;
  }

  @Override
  public int deleteAcademicInformation(List<MutableAcademicInformation> pMutableAcademicInformation) {
    String query = DELETE_ALL + "WHERE ID=? AND EMPLOYEE_ID=?";
    return mJdbcTemplate.batchUpdate(query, getDeleteParams(pMutableAcademicInformation)).length;
  }

  private List<Object[]> getDeleteParams(List<MutableAcademicInformation> pMutableAcademicInformation) {
    List<Object[]> params = new ArrayList<>();
    for(AcademicInformation pAcademicInformation : pMutableAcademicInformation) {
      params.add(new Object[] {pAcademicInformation.getId(), pAcademicInformation.getEmployeeId()});
    }
    return params;
  }

  class RoleRowMapper implements RowMapper<AcademicInformation> {
    @Override
    public AcademicInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentAcademicInformation academicInformation = new PersistentAcademicInformation();
      academicInformation.setId(resultSet.getLong("ID"));
      academicInformation.setEmployeeId(resultSet.getString("EMPLOYEE_ID"));
      academicInformation.setDegreeId(resultSet.getInt("DEGREE"));
      academicInformation.setInstitute(resultSet.getString("INSTITUTE"));
      academicInformation.setPassingYear(resultSet.getString("PASSING_YEAR"));
      academicInformation.setResult(resultSet.getString("RESULT"));
      academicInformation.setLastModified(resultSet.getString("LAST_MODIFIED"));
      return academicInformation;
    }
  }
}
