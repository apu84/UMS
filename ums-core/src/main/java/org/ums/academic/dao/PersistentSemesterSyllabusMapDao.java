  package org.ums.academic.dao;

    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.jdbc.core.RowMapper;
    import org.ums.academic.model.PersistentSemesterSyllabusMap;
    import org.ums.domain.model.common.MutableIdentifier;
    import org.ums.domain.model.regular.SemesterSyllabusMap;

    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.List;
    import java.util.concurrent.atomic.AtomicReference;

public class PersistentSemesterSyllabusMapDao  extends SemesterSyllabusMapDaoDecorator {

  static String SELECT_BY_SEMESTER_PROGRAM= "Select tmp1.*,semester.semester_name Syllabus From ( " +
      "Select year,semester,program.program_id,semester.semester_id,semester.semester_name,syllabus.syllabus_id " +
      "From SEMESTER_SYLLABUS_MAP ssmap,MST_PROGRAM program,MST_SEMESTER semester,MST_SYLLABUS syllabus " +
      "Where ssmap.PROGRAM_ID=program.PROGRAM_ID And ssmap.SEMESTER_ID=semester.SEMESTER_ID And ssmap.SYLLABUS_ID=syllabus.SYLLABUS_ID " +
      "And semester.Semester_Id=? and program.PROGRAM_ID=? )tmp1,MST_SYLLABUS syllabus,mst_semester semester " +
      "Where tmp1.syllabus_id=+ syllabus.syllabus_id and semester.semester_id=syllabus.semester_id " +
      "Order by Year,Semester ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentSemesterSyllabusMapDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public  List<SemesterSyllabusMap> getMapsByProgramSemester(final Integer pSemesterId,final Integer pProgramId) throws Exception {
    String query = SELECT_BY_SEMESTER_PROGRAM ;
    return mJdbcTemplate.query(query, new Object[]{pProgramId, pSemesterId}, new SemesterSyllabusRowMapper());
  }

  class SemesterSyllabusRowMapper implements RowMapper<SemesterSyllabusMap> {
    @Override
    public SemesterSyllabusMap mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentSemesterSyllabusMap pSSMap = new PersistentSemesterSyllabusMap();
      pSSMap.setYear(resultSet.getInt("YEAR"));
      pSSMap.setSemester(resultSet.getInt("SEMESTER"));

      AtomicReference<SemesterSyllabusMap> atomicReference = new AtomicReference<>(pSSMap);
      return atomicReference.get();
    }
  }
}
