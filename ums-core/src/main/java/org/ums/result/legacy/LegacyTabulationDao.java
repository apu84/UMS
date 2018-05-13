package org.ums.result.legacy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class LegacyTabulationDao extends LegacyTabulationDaoDecorator {
  String SELECT_ALL =
      "SELECT RL, GPA, CGPA, REM, SEMESTER_ID, YEAR, SEMESTER, PRESCR FROM DB_AUST_LEGACY.AUST_TABULATION ";

  String UPDATE_ALL = "UPDATE DB_AUST_LEGACY.AUST_TABULATION SET CGPA = ? ";

  private JdbcTemplate mJdbcTemplate;

  public LegacyTabulationDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<LegacyTabulation> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new LegacyTabulationRowMapper());
  }

  @Override
  public int update(MutableLegacyTabulation pMutable) {
    String query = UPDATE_ALL + "WHERE RL = ? AND SEMESTER_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getCgpa(), pMutable.getStudentId(), pMutable.getSemesterId());
  }

  @Override
  public int update(List<MutableLegacyTabulation> pMutableList) {
    String query = UPDATE_ALL + "WHERE RL = ? AND SEMESTER_ID = ?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParamList(pMutableList)).length;
  }

  private List<Object[]> getUpdateParamList(List<MutableLegacyTabulation> pLegacyTabulations) {
    List<Object[]> params = new ArrayList<>();
    for(LegacyTabulation legacyTabulation : pLegacyTabulations) {
      params.add(new Object[] {legacyTabulation.getCgpa(), legacyTabulation.getStudentId(),
          legacyTabulation.getSemesterId()});
    }
    return params;
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
