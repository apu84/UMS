package org.ums.result.legacy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class LegacyTabulationDao extends LegacyTabulationDaoDecorator {
  String SELECT_ALL = "SELECT RL, GPA, CGPA, REM, SEMESTER_ID, YEAR, SEMESTER, PRESCR FROM DB_AUST_LEGACY.AUST_TABULATION";

  private JdbcTemplate mJdbcTemplate;

  public LegacyTabulationDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<LegacyTabulation> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new LegacyTabulationRowMapper());
  }

  class LegacyTabulationRowMapper implements RowMapper<LegacyTabulation> {
    @Override
    public LegacyTabulation mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableLegacyTabulation legacyTabulation = new PersistentLegacyTabulation();
      legacyTabulation.setStudentId(rs.getString("RL"));
      legacyTabulation.setSemesterId(rs.getInt("SEMESTER_ID"));
      legacyTabulation.setYear(rs.getInt("YEAR"));
      legacyTabulation.setAcademicSemester(rs.getInt("SEMESTER"));
      legacyTabulation.setGpa(rs.getDouble("GPA"));
      legacyTabulation.setCgpa(rs.getDouble("CGPA"));
      legacyTabulation.setComment(rs.getString("REM"));
      legacyTabulation.setCompletedCrHr(rs.getDouble("PRESCR"));
      AtomicReference<LegacyTabulation> reference = new AtomicReference<>(legacyTabulation);
      return reference.get();
    }
  }
}
