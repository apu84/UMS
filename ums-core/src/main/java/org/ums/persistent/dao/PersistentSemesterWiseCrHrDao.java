package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.domain.model.dto.OptionalCourseApplicationStatDto;
import org.ums.domain.model.dto.SemesterWiseCrHrDto;
import org.ums.domain.model.immutable.Semester;
import org.ums.persistent.model.PersistentSemester;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ifti on 26-Mar-16.
 */
public class PersistentSemesterWiseCrHrDao {

  private JdbcTemplate mJdbcTemplate;

  public PersistentSemesterWiseCrHrDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  public SemesterWiseCrHrDto getCrHrInfoByYearSemester(String pSyllabusId, Integer pYear,
      Integer pSemester) throws Exception {
    String query =
        "Select * From SEMESTER_WISE_CRHR Where Syllabus_Id=? and Year=? and Semester=? ";

    return mJdbcTemplate.queryForObject(query, new Object[] {pSyllabusId, pYear, pSemester},
        new SemesterWiseCrHrRowMapper());
  }

  class SemesterWiseCrHrRowMapper implements RowMapper<SemesterWiseCrHrDto> {
    @Override
    public SemesterWiseCrHrDto mapRow(ResultSet resultSet, int i) throws SQLException {
      SemesterWiseCrHrDto cRhR = new SemesterWiseCrHrDto();
      cRhR.setSyllabus_id(resultSet.getString("SYLLABUS_ID"));
      cRhR.setYear(resultSet.getInt("YEAR"));
      cRhR.setSemester(resultSet.getInt("SEMESTER"));
      cRhR.setTotalCrHr(resultSet.getFloat("TOTAL_CRHR"));
      cRhR.setTheoryChHr(resultSet.getFloat("THEORY_CRHR"));
      cRhR.setSessionalCrHr(resultSet.getFloat("SESSIONAL_CRHR"));
      cRhR.setOptionalCrHr(resultSet.getFloat("OPT_CRHR"));
      cRhR.setOptionalTheoryCrHr(resultSet.getFloat("OPT_THEORY_CRHR"));
      cRhR.setOptionalSessionalCrHr(resultSet.getFloat("OPT_SESSIONAL_CRHR"));
      AtomicReference<SemesterWiseCrHrDto> atomicReference = new AtomicReference<>(cRhR);
      return atomicReference.get();
    }
  }

}
