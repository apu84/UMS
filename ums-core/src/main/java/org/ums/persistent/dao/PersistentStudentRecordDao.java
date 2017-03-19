package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.StudentRecordDaoDecorator;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.mutable.MutableStudentRecord;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentStudentRecord;

import com.google.common.collect.Lists;

public class PersistentStudentRecordDao extends StudentRecordDaoDecorator {
  String SELECT_ALL =
      "SELECT STUDENT_ID, SEMESTER_ID, PROGRAM_ID, YEAR, SEMESTER, CGPA, GPA, REGISTRATION_TYPE, RESULT, LAST_MODIFIED, ID FROM STUDENT_RECORD ";
  String INSERT_ALL =
      "INSERT INTO STUDENT_RECORD(ID, STUDENT_ID, SEMESTER_ID, PROGRAM_ID, YEAR, SEMESTER, REGISTRATION_TYPE, RESULT, LAST_MODIFIED) VALUES("
          + "?, ?, ?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + ") ";
  String UPDATE_ALL = "UPDATE STUDENT_RECORD SET STUDENT_ID = ?, " + "SEMESTER_ID = ?, " + "YEAR = ?,"
      + "SEMESTER = ?," + "CGPA = ?," + "GPA = ?," + "REGISTRATION_TYPE = ?," + "RESULT = ?," + "LAST_MODIFIED = "
      + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM STUDENT_RECORD ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentStudentRecordDao(final JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public StudentRecord get(Long pId) {
    String query = SELECT_ALL + "WHERE ID=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new StudentRecordRowMapper());
  }

  @Override
  public List<StudentRecord> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new StudentRecordRowMapper());
  }

  @Override
  public int update(MutableStudentRecord pMutable) {
    String query = UPDATE_ALL + "WHERE ID=?";
    return mJdbcTemplate.update(query, getUpdateParamList(Lists.newArrayList(pMutable)));
  }

  @Override
  public int delete(MutableStudentRecord pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableStudentRecord pMutable) {
    List<Object[]> params = getInsertParamList(Lists.newArrayList(pMutable));
    mJdbcTemplate.update(INSERT_ALL, params.get(0));
    return (Long) params.get(0)[0];
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId) {
    String query = SELECT_ALL + " WHERE PROGRAM_ID = ? AND SEMESTER_ID = ? ORDER BY YEAR, SEMESTER";
    return mJdbcTemplate.query(query, new Object[] {pProgramId, pSemesterId}, new StudentRecordRowMapper());
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId, Integer pYear,
      Integer pAcademicSemester) {
    String query = SELECT_ALL + " WHERE PROGRAM_ID = ? AND SEMESTER_ID = ? AND YEAR = ? AND SEMESTER = ?";
    return mJdbcTemplate.query(query, new Object[] {pProgramId, pSemesterId, pYear, pAcademicSemester},
        new StudentRecordRowMapper());
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId, StudentRecord.Type pType) {
    String query = SELECT_ALL + " WHERE PROGRAM_ID = ? AND SEMESTER_ID = ? AND REGISTRATION_TYPE = ?";
    return mJdbcTemplate.query(query, new Object[] {pProgramId, pSemesterId, pType.getValue()},
        new StudentRecordRowMapper());
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId, Integer pYear,
      Integer pAcademicSemester, StudentRecord.Type pType) {
    String query =
        SELECT_ALL
            + " WHERE PROGRAM_ID = ? AND SEMESTER_ID = ? AND YEAR = ? AND SEMESTER = ? AND REGISTRATION_TYPE = ?";
    return mJdbcTemplate.query(query,
        new Object[] {pProgramId, pSemesterId, pYear, pAcademicSemester, pType.getValue()},
        new StudentRecordRowMapper());
  }

  @Override
  public List<StudentRecord> getStudentRecords(String pStudentId, Integer pSemesterId, Integer pYear,
      Integer pAcademicSemester) {
    String query = SELECT_ALL + " where  STUDENT_ID=? AND SEMESTER_ID=? AND YEAR=? AND SEMESTER=?";
    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId, pYear, pAcademicSemester},
        new StudentRecordRowMapper());
  }

  @Override
  public StudentRecord getStudentRecord(final String pStudentId, final Integer pSemesterId) {
    String query = SELECT_ALL + " where  STUDENT_ID=? AND SEMESTER_ID=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pStudentId, pSemesterId}, new StudentRecordRowMapper());
  }

  @Override
  public List<Long> create(List<MutableStudentRecord> pStudentRecordList) {
    List<Object[]> params = getInsertParamList(pStudentRecordList);
    mJdbcTemplate.batchUpdate(INSERT_ALL, params);
    return params.stream().map(param -> (Long) param[0])
        .collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public int update(List<MutableStudentRecord> pStudentRecordList) {
    String query = UPDATE_ALL + "WHERE ID=?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParamList(pStudentRecordList)).length;
  }

  private List<Object[]> getUpdateParamList(List<MutableStudentRecord> pStudentRecords) {
    List<Object[]> params = new ArrayList<>();
    for(StudentRecord studentRecord : pStudentRecords) {
      params.add(new Object[] {studentRecord.getStudentId(), studentRecord.getSemester().getId(),
          studentRecord.getYear(), studentRecord.getAcademicSemester(), studentRecord.getCGPA(),
          studentRecord.getGPA(), studentRecord.getType().getValue(), studentRecord.getStatus().getValue(),
          studentRecord.getId()});
    }

    return params;
  }

  private List<Object[]> getInsertParamList(List<MutableStudentRecord> pStudentRecords) {
    List<Object[]> params = new ArrayList<>();
    for(StudentRecord studentRecord : pStudentRecords) {
      params.add(new Object[] {mIdGenerator.getNumericId(), studentRecord.getStudent().getId(),
          studentRecord.getSemester().getId(), studentRecord.getProgram().getId(), studentRecord.getYear(),
          studentRecord.getAcademicSemester(), studentRecord.getType().getValue(),
          studentRecord.getStatus().getValue(),});
    }

    return params;
  }

  class StudentRecordRowMapper implements RowMapper<StudentRecord> {
    @Override
    public StudentRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableStudentRecord studentRecord = new PersistentStudentRecord();
      studentRecord.setId(rs.getLong("ID"));
      studentRecord.setStudentId(rs.getString("STUDENT_ID"));
      studentRecord.setSemesterId(rs.getInt("SEMESTER_ID"));
      studentRecord.setYear(rs.getInt("YEAR"));
      studentRecord.setAcademicSemester(rs.getInt("SEMESTER"));
      studentRecord.setCGPA(rs.getDouble("CGPA"));
      studentRecord.setGPA(rs.getDouble("GPA"));
      studentRecord.setType(StudentRecord.Type.get(rs.getString("REGISTRATION_TYPE")));
      studentRecord.setStatus(StudentRecord.Status.get(rs.getString("RESULT")));
      studentRecord.setLastModified(rs.getString("LAST_MODIFIED"));
      studentRecord.setProgramId(rs.getInt("PROGRAM_ID"));
      AtomicReference<StudentRecord> atomicReference = new AtomicReference<>(studentRecord);
      return atomicReference.get();
    }
  }
}
