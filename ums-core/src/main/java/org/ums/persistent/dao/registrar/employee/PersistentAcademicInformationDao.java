package org.ums.persistent.dao.registrar.employee;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.registrar.employee.AcademicInformationDaoDecorator;
import org.ums.domain.model.immutable.registrar.employee.AcademicInformation;
import org.ums.domain.model.mutable.registrar.employee.MutableAcademicInformation;
import org.ums.persistent.model.registrar.employee.PersistentAcademicInformation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentAcademicInformationDao extends AcademicInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_ACADEMIC_INFO (EMPLOYEE_ID, DEGREE_NAME, DEGREE_INSTITUTE, DEGREE_PASSING_YEAR) VALUES (? ,? ,?, ?)";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAcademicInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int saveAcademicInformation(MutableAcademicInformation pMutableAcademicInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, pMutableAcademicInformation.getEmployeeId(),
        pMutableAcademicInformation.getDegreeName(), pMutableAcademicInformation.getDegreeInstitute(),
        pMutableAcademicInformation.getDegreePassingYear());
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
