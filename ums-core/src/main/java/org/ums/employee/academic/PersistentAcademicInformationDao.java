package org.ums.employee.academic;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentAcademicInformationDao extends AcademicInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_ACADEMIC_INFO (ID, EMPLOYEE_ID, DEGREE_LEVEL_ID, DEGREE_TITLE_ID, INSTITUTE, PASSING_YEAR, RESULT, MAJOR, DURATION, LAST_MODIFIED) VALUES (?, ?, ?, ? ,? ,? ,?, ?, ?,"
          + getLastModifiedSql() + ")";

  static String GET_ALL =
      "SELECT ID, EMPLOYEE_ID, DEGREE_LEVEL_ID, DEGREE_TITLE_ID, INSTITUTE, PASSING_YEAR, RESULT, MAJOR, DURATION, LAST_MODIFIED FROM EMP_ACADEMIC_INFO ";

  static String UPDATE_ONE =
      "UPDATE EMP_ACADEMIC_INFO SET DEGREE_LEVEL_ID = ?, DEGREE_TITLE_ID = ?, INSTITUTE = ?, PASSING_YEAR = ?, RESULT = ?, MAJOR = ?, DURATION = ?, LAST_MODIFIED ="
          + getLastModifiedSql() + " ";

  static String DELETE_ONE = "DELETE FROM EMP_ACADEMIC_INFO ";

  static String EXISTS_ONE = "SELECT COUNT(EMPLOYEE_ID) FROM EMP_ACADEMIC_INFO ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentAcademicInformationDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Long create(MutableAcademicInformation pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ONE, id, pMutable.getEmployeeId(), pMutable.getDegreeLevelId(),
        pMutable.getDegreeTitleId(), pMutable.getInstitute(), pMutable.getPassingYear(), pMutable.getResult(),
        pMutable.getMajor(), pMutable.getDuration());
    return id;
  }

  @Override
  public AcademicInformation get(final Long pId) {
    String query = GET_ALL + " WHERE ID = ? ";
    return mJdbcTemplate
        .queryForObject(query, new Object[] {pId}, new PersistentAcademicInformationDao.RoleRowMapper());
  }

  @Override
  public List<AcademicInformation> get(final String pEmployeeId) {
    String query = GET_ALL + " WHERE EMPLOYEE_ID = ? ORDER BY PASSING_YEAR DESC ";
    return mJdbcTemplate.query(query, new Object[] {pEmployeeId}, new PersistentAcademicInformationDao.RoleRowMapper());
  }

  @Override
  public int update(MutableAcademicInformation pMutable) {
    String query = UPDATE_ONE + " WHERE ID = ? AND EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getDegreeLevelId(), pMutable.getDegreeTitleId(),
        pMutable.getInstitute(), pMutable.getPassingYear(), pMutable.getResult(), pMutable.getMajor(),
        pMutable.getDuration(), pMutable.getId(), pMutable.getEmployeeId());
  }

  @Override
  public int delete(MutableAcademicInformation pMutable) {
    String query = DELETE_ONE + " WHERE ID = ? AND EMPLOYEE_ID = ? ";
    return mJdbcTemplate.update(query, pMutable.getId(), pMutable.getEmployeeId());
  }

  @Override
  public boolean exists(String pEmployeeId) {
    String query = EXISTS_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pEmployeeId}, Boolean.class);
  }

  class RoleRowMapper implements RowMapper<AcademicInformation> {
    @Override
    public AcademicInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentAcademicInformation academicInformation = new PersistentAcademicInformation();
      academicInformation.setId(resultSet.getLong("ID"));
      academicInformation.setEmployeeId(resultSet.getString("EMPLOYEE_ID"));
      academicInformation.setDegreeLevelId(resultSet.getInt("DEGREE_LEVEL_ID"));
      academicInformation.setDegreeTitleId(resultSet.getInt("DEGREE_TITLE_ID"));
      academicInformation.setInstitute(resultSet.getString("INSTITUTE"));
      academicInformation.setPassingYear(resultSet.getInt("PASSING_YEAR"));
      academicInformation.setResult(resultSet.getString("RESULT"));
      academicInformation.setMajor(resultSet.getString("MAJOR"));
      academicInformation.setDuration(resultSet.getInt("DURATION"));
      academicInformation.setLastModified(resultSet.getString("LAST_MODIFIED"));
      return academicInformation;
    }
  }
}
