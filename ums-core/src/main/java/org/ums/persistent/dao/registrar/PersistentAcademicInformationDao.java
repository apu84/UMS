package org.ums.persistent.dao.registrar;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.registrar.AcademicInformationDaoDecorator;
import org.ums.domain.model.immutable.registrar.AcademicInformation;
import org.ums.domain.model.mutable.registrar.MutableAcademicInformation;
import org.ums.persistent.model.registrar.PersistentAcademicInformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentAcademicInformationDao extends AcademicInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_ACADEMIC_INFO (EMPLOYEE_ID, DEGREE_NAME, DEGREE_INSTITUTE, DEGREE_PASSING_YEAR) VALUES (? ,? ,?, ?)";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAcademicInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int saveAcademicInformation(List<MutableAcademicInformation> pMutableAcademicInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query, getEmployeeAcademicInformationParams(pMutableAcademicInformation)).length;
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

  class RoleRowMapper implements RowMapper<AcademicInformation> {
    @Override
    public AcademicInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      MutableAcademicInformation academicInformation = new PersistentAcademicInformation();
      academicInformation.setEmployeeId(resultSet.getInt("employee_id"));
      academicInformation.setDegreeName(resultSet.getString("degree_name"));
      academicInformation.setDegreeInstitute(resultSet.getString("degree_institution"));
      academicInformation.setDegreePassingYear(resultSet.getString("degree_passing_Year"));
      return academicInformation;
    }
  }
}
