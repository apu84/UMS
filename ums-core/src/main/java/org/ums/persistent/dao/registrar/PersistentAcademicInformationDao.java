package org.ums.persistent.dao.registrar;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.configuration.AcademicContext;
import org.ums.decorator.registrar.AcademicInformationDaoDecorator;
import org.ums.domain.model.immutable.registrar.AcademicInformation;
import org.ums.domain.model.mutable.MutableAdmissionCertificatesOfStudent;
import org.ums.domain.model.mutable.registrar.MutableAcademicInformation;
import org.ums.persistent.model.registrar.PersistentAcademicInformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentAcademicInformationDao extends AcademicInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_ACADEMIC_INFO (EMPLOYEE_ID, DEGREE_NAME, DEGREE_INSTITUTE, DEGREE_PASSING_YEAR, LAST_MODIFIED) VALUES (? ,? ,?, ?,"
          + getLastModifiedSql() + ")";

  static String GET_ONE =
      "Select ID, EMPLOYEE_ID, DEGREE_NAME, DEGREE_INSTITUTE, DEGREE_PASSING_YEAR, LAST_MODIFIED From EMP_ACADEMIC_INFO ";

  static String UPDATE_ALL =
      "UPDATE EMP_ACADEMIC_INFO SET DEGREE_NAME = ?, DEGREE_INSTITUTE = ?, DEGREE_PASSING_YEAR = ?, LAST_MODIFIED ="
          + getLastModifiedSql() + " ";

  static String DELETE_ALL = "DELETE FROM EMP_ACADEMIC_INFO ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAcademicInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
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
      params.add(new Object[] {academicInformation.getEmployeeId(), academicInformation.getDegreeName(),
          academicInformation.getDegreeInstitute(), academicInformation.getDegreePassingYear()});

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
      params.add(new Object[] {pAcademicInformation.getDegreeName(), pAcademicInformation.getDegreeInstitute(),
          pAcademicInformation.getDegreePassingYear(), pAcademicInformation.getEmployeeId(),
          pAcademicInformation.getId()});
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
      MutableAcademicInformation academicInformation = new PersistentAcademicInformation();
      academicInformation.setId(resultSet.getInt("id"));
      academicInformation.setEmployeeId(resultSet.getString("employee_id"));
      academicInformation.setDegreeName(resultSet.getString("degree_name"));
      academicInformation.setDegreeInstitute(resultSet.getString("degree_institute"));
      academicInformation.setDegreePassingYear(resultSet.getString("degree_passing_year"));
      academicInformation.setLastModified(resultSet.getString("last_modified"));
      return academicInformation;
    }
  }
}
