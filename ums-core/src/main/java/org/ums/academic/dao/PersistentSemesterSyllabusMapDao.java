package org.ums.academic.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.academic.model.*;
import org.ums.domain.model.dto.SemesterSyllabusMapDto;
import org.ums.domain.model.mutable.MutableSemesterSyllabusMap;
import org.ums.domain.model.readOnly.SemesterSyllabusMap;
import org.ums.domain.model.readOnly.Syllabus;
import org.ums.manager.SyllabusManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentSemesterSyllabusMapDao extends SemesterSyllabusMapDaoDecorator {

  SyllabusManager mSyllabusManager;

  static String SELECT_BY_SEMESTER_PROGRAM = "Select tmp1.*,dept.short_name,semester.semester_name Syllabus,SEMESTER.SEMESTER_ID syllabus_semester_id From ( " +
      "Select MAPPING_ID,year,semester,program.dept_id,program.program_id,program.program_short_name,semester.semester_id,semester.semester_name,syllabus.syllabus_id " +
      "From SEMESTER_SYLLABUS_MAP ssmap,MST_PROGRAM program,MST_SEMESTER semester,MST_SYLLABUS syllabus " +
      "Where ssmap.PROGRAM_ID=program.PROGRAM_ID And ssmap.SEMESTER_ID=semester.SEMESTER_ID And ssmap.SYLLABUS_ID=syllabus.SYLLABUS_ID " +
      "And semester.Semester_Id=? and program.PROGRAM_ID=? )tmp1,MST_SYLLABUS syllabus,mst_semester semester,mst_dept_office dept   " +
      "Where tmp1.syllabus_id=+ syllabus.syllabus_id and semester.semester_id=syllabus.semester_id  And dept.dept_id=tmp1.dept_id  " +
      "Order by Year,Semester ";

  static String SELECT_SINGLE = "Select tmp1.*,dept.short_name,semester.semester_name Syllabus,SEMESTER.SEMESTER_ID syllabus_semester_id  From (  " +
      "Select MAPPING_ID,year,semester,program.dept_id,program.program_id,program.program_short_name,semester.semester_id,semester.semester_name,syllabus.syllabus_id  " +
      "From SEMESTER_SYLLABUS_MAP ssmap,MST_PROGRAM program,MST_SEMESTER semester,MST_SYLLABUS syllabus  " +
      "Where ssmap.PROGRAM_ID=program.PROGRAM_ID And ssmap.SEMESTER_ID=semester.SEMESTER_ID And ssmap.SYLLABUS_ID=syllabus.SYLLABUS_ID  " +
      "And MAPPING_ID=? )tmp1,MST_SYLLABUS syllabus,mst_semester semester,mst_dept_office dept  " +
      "Where tmp1.syllabus_id=+ syllabus.syllabus_id and semester.semester_id=syllabus.semester_id   " +
      "And dept.dept_id=tmp1.dept_id ";

  static String SELECT_SYLLABUS = "SELECT SYLLABUS_ID FROM SEMESTER_SYLLABUS_MAP WHERE PROGRAM_ID = ? AND SEMESTER_ID = ? ";

  static String UPDATE_ONE = "Update SEMESTER_SYLLABUS_MAP Set Syllabus_Id=? ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentSemesterSyllabusMapDao(final JdbcTemplate pJdbcTemplate, final SyllabusManager pSyllabusManager) {
    mJdbcTemplate = pJdbcTemplate;
    mSyllabusManager = pSyllabusManager;
  }

  @Override
  public List<SemesterSyllabusMap> getMapsByProgramSemester(final Integer pSemesterId, final Integer pProgramId) throws Exception {
    String query = SELECT_BY_SEMESTER_PROGRAM;
    return mJdbcTemplate.query(query, new Object[]{pProgramId, pSemesterId}, new SemesterSyllabusRowMapper());
  }

  @Override
  public SemesterSyllabusMap get(final Integer pMapId) throws Exception {
    String query = SELECT_SINGLE;
    return mJdbcTemplate.queryForObject(query, new Object[]{pMapId}, new SemesterSyllabusRowMapper());
  }

  public void update(final MutableSemesterSyllabusMap pSSMap) throws Exception {
    String query = UPDATE_ONE + " Where Mapping_Id=?";
    mJdbcTemplate.update(query,
        pSSMap.getSyllabus().getId(),
        pSSMap.getId());
  }

  public void copySyllabus(final SemesterSyllabusMapDto pSemesterSyllabusMapDto) throws Exception {

    String query = "insert into SEMESTER_SYLLABUS_MAP(mapping_id, program_id, year, semester, semester_id, syllabus_id)" +
        " select SQN_SSMAP_ID.nextVal, program_id, year, semester, ? , syllabus_id from SEMESTER_SYLLABUS_MAP t1 where semester_id = ? and program_id = ?";
    mJdbcTemplate.update(query,
        pSemesterSyllabusMapDto.getAcademicSemester().getId(),
        pSemesterSyllabusMapDto.getCopySemester().getId(),
        pSemesterSyllabusMapDto.getProgram().getId());
  }

  @Override
  public List<Syllabus> getSyllabusForSemester(Integer pProgramId, Integer pSemesterId) {
    return mJdbcTemplate.query(SELECT_SYLLABUS, new Object[]{pProgramId, pSemesterId}, new SyllabusRowMapper());
  }

  @Override
  public Syllabus getSyllabusForSemester(Integer pProgramId, Integer pSemesterId, Integer pYear, Integer pSemester) {
    String query = SELECT_SYLLABUS + " AND YEAR = ? AND SEMESTER = ?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pProgramId, pSemesterId}, new SyllabusRowMapper());
  }

  class SemesterSyllabusRowMapper implements RowMapper<SemesterSyllabusMap> {
    @Override
    public SemesterSyllabusMap mapRow(ResultSet resultSet, int i) throws SQLException {
      //TODO: Refactor this so it uses more simplified model.
      PersistentSemesterSyllabusMap pSSMap = new PersistentSemesterSyllabusMap();
      pSSMap.setId(resultSet.getInt("MAPPING_ID"));
      pSSMap.setYear(resultSet.getInt("YEAR"));
      pSSMap.setSemester(resultSet.getInt("SEMESTER"));

      PersistentSemester semester = new PersistentSemester();
      semester.setId(resultSet.getInt("SEMESTER_ID"));
      semester.setName(resultSet.getString("SEMESTER_NAME"));
      pSSMap.setAcademicSemester(semester);

      PersistentDepartment dept = new PersistentDepartment();
      dept.setId(resultSet.getString("DEPT_ID"));
      dept.setShortName(resultSet.getString("SHORT_NAME"));

      PersistentProgram program = new PersistentProgram();
      program.setDepartment(dept);
      program.setId(resultSet.getInt("PROGRAM_ID"));
      program.setShortName(resultSet.getString("PROGRAM_SHORT_NAME"));

      semester = new PersistentSemester();
      PersistentSyllabus syllabus = new PersistentSyllabus();
      semester.setName(resultSet.getString("SYLLABUS"));
      semester.setId(resultSet.getInt("syllabus_semester_id"));
      syllabus.setSemester(semester);
      syllabus.setId(resultSet.getString("SYLLABUS_ID"));

      pSSMap.setSyllabus(syllabus);
      pSSMap.setProgram(program);

      AtomicReference<SemesterSyllabusMap> atomicReference = new AtomicReference<>(pSSMap);
      return atomicReference.get();
    }
  }

  class SyllabusRowMapper implements RowMapper<Syllabus> {
    @Override
    public Syllabus mapRow(ResultSet rs, int rowNum) throws SQLException {
      String syllabusId = rs.getString("SYLLABUS_ID");
      Syllabus syllabus;
      try {
        syllabus = mSyllabusManager.get(syllabusId);
      } catch (Exception e) {
        throw new SQLException(e);
      }
      return syllabus;
    }
  }
}
