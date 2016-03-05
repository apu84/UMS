package org.ums.academic.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.academic.model.PersistentStudentRecord;
import org.ums.domain.model.mutable.MutableStudentRecord;
import org.ums.domain.model.readOnly.StudentRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentStudentRecordDao extends StudentRecordDaoDecorator {
  String SELECT_ALL = "SELECT STUDENT_ID, SEMESTER_ID, YEAR, SEMESTER, CGPA, GPA, TYPE, STATUS, LAST_MODIFIED, ID FROM STUDENT_RECORD ";
  String INSERT_ALL = "INSERT INTO STUDENT_RECORD(STUDENT_ID, SEMESTER_ID, YEAR, SEMESTER, CGPA, GPA, TYPE, STATUS, LAST_MODIFIED) VALUES(" +
      "?, ?, ?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + ") ";
  String UPDATE_ALL = "UPDATE STUDENT_RECORD SET STUDENT_ID = ?, " +
      "SEMESTER_ID = ?, " +
      "YEAR = ?," +
      "SEMESTER = ?," +
      "CGPA = ?," +
      "GPA = ?," +
      "TYPE = ?," +
      "STATUS = ?," +
      "LAST_MODIFIED = " + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM STUDENT_RECORD ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentStudentRecordDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public StudentRecord get(Integer pId) throws Exception {
    String query = SELECT_ALL + "WHERE ID=?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, new StudentRecordRowMapper());
  }

  @Override
  public List<StudentRecord> getAll() throws Exception {
    return mJdbcTemplate.query(SELECT_ALL, new StudentRecordRowMapper());
  }

  @Override
  public int update(MutableStudentRecord pMutable) throws Exception {
    String query = UPDATE_ALL + "WHERE ID=?";
    return mJdbcTemplate.update(query, pMutable.getStudent().getId(),
        pMutable.getSemester().getId(),
        pMutable.getYear(),
        pMutable.getAcademicSemester(),
        pMutable.getCGPA(),
        pMutable.getGPA(),
        pMutable.getType().getValue(),
        pMutable.getStatus().getValue(),
        pMutable.getId());
  }

  @Override
  public int delete(MutableStudentRecord pMutable) throws Exception {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int create(MutableStudentRecord pMutable) throws Exception {
    return mJdbcTemplate.update(INSERT_ALL, pMutable.getStudent().getId(),
        pMutable.getStudent().getId(),
        pMutable.getYear(),
        pMutable.getAcademicSemester(),
        pMutable.getCGPA(),
        pMutable.getGPA(),
        pMutable.getType().getValue(),
        pMutable.getStatus().getValue());
  }

  class StudentRecordRowMapper implements RowMapper<StudentRecord> {
    @Override
    public StudentRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableStudentRecord studentRecord = new PersistentStudentRecord();
      studentRecord.setId(rs.getInt("ID"));
      studentRecord.setStudentId(rs.getString("STUDENT_ID"));
      studentRecord.setSemesterId(rs.getInt("SEMESTER_ID"));
      studentRecord.setYear(rs.getInt("YEAR"));
      studentRecord.setAcademicSemester(rs.getInt("SEMESTER"));
      studentRecord.setCGPA(rs.getFloat("CGPA"));
      studentRecord.setGPA(rs.getFloat("GPA"));
      studentRecord.setType(StudentRecord.Type.get(rs.getString("TYPE")));
      studentRecord.setStatus(StudentRecord.Status.get(rs.getString("STATUS")));
      studentRecord.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<StudentRecord> atomicReference = new AtomicReference<>(studentRecord);
      return atomicReference.get();
    }
  }
}
