package org.ums.academic.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.academic.model.PersistentStudent;
import org.ums.domain.model.MutableStudent;
import org.ums.domain.model.Student;
import org.ums.util.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.List;

public class PersistentStudentDao extends ContentDaoDecorator<Student, MutableStudent, String> {
  static String SELECT_ALL = "SELECT" +
      "  STUDENT_ID," +
      "  FULL_NAME," +
      "  DEPT_ID," +
      "  SEMESTER_ID," +
      "  FATHER_NAME," +
      "  MOTHER_NAME," +
      "  BIRTH_DATE," +
      "  GENDER," +
      "  PRESENT_ADDRESS," +
      "  PERMANENT_ADDRESS," +
      "  MOBILE_NUMBER," +
      "  PHONE_NUMBER," +
      "  BLOOD_GROUP," +
      "  EMAIL_ADDRESS," +
      "  GUARDIAN_NAME," +
      "  GUARDIAN_MOBILE," +
      "  GUARDIAN_PHONE," +
      "  GUARDIAN_EMAIL," +
      "  PROGRAM_ID," +
      "  LAST_MODIFIED" +
      "  FROM STUDENTS ";

  static String UPDATE_ALL = "UPDATE STUDENTS SET" +
      "  FULL_NAME = ?," +
      "  DEPT_ID = ?," +
      "  SEMESTER_ID = ?," +
      "  FATHER_NAME = ?," +
      "  MOTHER_NAME = ?," +
      "  BIRTH_DATE = TO_DATE(?, '" + Constants.DATE_FORMAT + "')," +
      "  GENDER = ?," +
      "  PRESENT_ADDRESS = ?," +
      "  PERMANENT_ADDRESS = ?," +
      "  MOBILE_NUMBER = ?," +
      "  PHONE_NUMBER = ?," +
      "  BLOOD_GROUP = ?," +
      "  EMAIL_ADDRESS = ?," +
      "  GUARDIAN_NAME = ?," +
      "  GUARDIAN_MOBILE = ?," +
      "  GUARDIAN_PHONE = ?," +
      "  GUARDIAN_EMAIL = ?," +
      "  PROGRAM_ID = ?," +
      "  LAST_MODIFIED = " + getLastModifiedSql();

  static String DELETE_ALL = "DELETE FROM STUDENTS";
  static String CREATE_ALL = "INSERT INTO STUDENTS(" +
      "  STUDENT_ID," +
      "  FULL_NAME," +
      "  DEPT_ID," +
      "  SEMESTER_ID," +
      "  FATHER_NAME," +
      "  MOTHER_NAME," +
      "  BIRTH_DATE," +
      "  GENDER," +
      "  PRESENT_ADDRESS," +
      "  PERMANENT_ADDRESS," +
      "  MOBILE_NUMBER," +
      "  PHONE_NUMBER," +
      "  BLOOD_GROUP," +
      "  EMAIL_ADDRESS," +
      "  GUARDIAN_NAME," +
      "  GUARDIAN_MOBILE," +
      "  GUARDIAN_PHONE," +
      "  GUARDIAN_EMAIL," +
      "  PROGRAM_ID," +
      "  LAST_MODIFIED" +
      ") VALUES (?,?,?,?,?,?,TO_DATE(?, '" + Constants.DATE_FORMAT + "'),?,?,?,?,?,?,?,?,?,?,?,?," + getLastModifiedSql() + ")";

  private JdbcTemplate mJdbcTemplate;
  private DateFormat mDateFormat;


  public PersistentStudentDao(final JdbcTemplate pJdbcTemplate,
                              final DateFormat pDateFormat) {
    mJdbcTemplate = pJdbcTemplate;
    mDateFormat = pDateFormat;
  }

  @Override
  public void update(MutableStudent pMutable) throws Exception {
    String query = UPDATE_ALL + " WHERE STUDENT_ID = ?";
    mJdbcTemplate.update(query,
        pMutable.getFullName(),
        pMutable.getDepartmentId(),
        pMutable.getSemesterId(),
        pMutable.getFatherName(),
        pMutable.getMotherName(),
        mDateFormat.format(pMutable.getDateOfBirth()),
        pMutable.getGender(),
        pMutable.getPresentAddress(),
        pMutable.getPermanentAddress(),
        pMutable.getMobileNo(),
        pMutable.getPhoneNo(),
        pMutable.getBloodGroup(),
        pMutable.getEmail(),
        pMutable.getGuardianName(),
        pMutable.getGuardianMobileNo(),
        pMutable.getGuardianPhoneNo(),
        pMutable.getGuardianEmail(),
        pMutable.getProgramId(),
        pMutable.getId()
    );
  }

  @Override
  public void delete(MutableStudent pMutable) throws Exception {
    String query = DELETE_ALL + " WHERE STUDENT_ID = ?";
    mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public void create(MutableStudent pMutable) throws Exception {
    mJdbcTemplate.update(CREATE_ALL,
        pMutable.getId(),
        pMutable.getFullName(),
        pMutable.getDepartmentId(),
        pMutable.getSemesterId(),
        pMutable.getFatherName(),
        pMutable.getMotherName(),
        mDateFormat.format(pMutable.getDateOfBirth()),
        pMutable.getGender(),
        pMutable.getPresentAddress(),
        pMutable.getPermanentAddress(),
        pMutable.getMobileNo(),
        pMutable.getPhoneNo(),
        pMutable.getBloodGroup(),
        pMutable.getEmail(),
        pMutable.getGuardianName(),
        pMutable.getGuardianMobileNo(),
        pMutable.getGuardianPhoneNo(),
        pMutable.getGuardianEmail(),
        pMutable.getProgramId()
    );
  }

  @Override
  public Student get(String pId) throws Exception {
    String query = SELECT_ALL + " WHERE STUDENT_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, new StudentRowMapper());
  }

  @Override
  public List<Student> getAll() throws Exception {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new StudentRowMapper());
  }

  class StudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableStudent student = new PersistentStudent();
      student.setId(rs.getString("STUDENT_ID"));
      student.setFullName(rs.getString("FULL_NAME"));
      student.setDepartmentId(rs.getInt("DEPT_ID"));
      student.setSemesterId(rs.getInt("SEMESTER_ID"));
      student.setFatherName(rs.getString("FATHER_NAME"));
      student.setMotherName(rs.getString("MOTHER_NAME"));
      student.setDateOfBirth(rs.getDate("BIRTH_DATE"));
      student.setGender(rs.getString("GENDER"));
      student.setPresentAddress(rs.getString("PRESENT_ADDRESS"));
      student.setPermanentAddress(rs.getString("PERMANENT_ADDRESS"));
      student.setMobileNo(rs.getString("MOBILE_NO"));
      student.setPhoneNo(rs.getString("PHONE_NO"));
      student.setBloodGroup(rs.getString("BLOOD_GROUP"));
      student.setEmail(rs.getString("EMAIL_ADDRESS"));
      student.setGuardianName(rs.getString("GUARDIAN_NAME"));
      student.setGuardianMobileNo(rs.getString("GUARDIAN_MOBILE"));
      student.setGuardianPhoneNo(rs.getString("GUARDIAN_PHONE"));
      student.setGuardianEmail("GUARDIAN_EMAIL");
      student.setProgramId(rs.getInt("PROGRAM_ID"));
      student.setLastModified(rs.getString("LAST_MODIFIED"));
      return null;
    }
  }
}
