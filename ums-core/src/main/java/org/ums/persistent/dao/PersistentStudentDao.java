package org.ums.persistent.dao;


import com.google.common.collect.Lists;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.persistent.model.PersistentStudent;
import org.ums.decorator.StudentDaoDecorator;
import org.ums.domain.model.mutable.MutableStudent;
import org.ums.domain.model.immutable.Student;
import org.ums.util.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class PersistentStudentDao extends StudentDaoDecorator {
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
      "  LAST_MODIFIED," +
      "  ENROLLMENT_TYPE," +
      "  CURR_YEAR," +
      "  CURR_SEMESTER" +
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
      "  LAST_MODIFIED = " + getLastModifiedSql() + ","+
      "  ENROLLMENT_TYPE = ?," +
      "  CURR_YEAR = ?," +
      "  CURR_SEMESTER = ? ";

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
      "  LAST_MODIFIED," +
      "  ENROLLMENT_TYPE," +
      "  CURR_YEAR, " +
      "  CURR_SEMESTER" +
      ") VALUES (?,?,?,?,?,?,TO_DATE(?, '" + Constants.DATE_FORMAT + "'),?,?,?,?,?,?,?,?,?,?,?,?," + getLastModifiedSql() + ",?, ?, ?)";

  private JdbcTemplate mJdbcTemplate;

  private DateFormat mDateFormat;


  public PersistentStudentDao(final JdbcTemplate pJdbcTemplate,
                              final DateFormat pDateFormat) {
    mJdbcTemplate = pJdbcTemplate;
    mDateFormat = pDateFormat;
  }

  @Override
  public int update(MutableStudent pMutable) throws Exception {
    String query = UPDATE_ALL + " WHERE STUDENT_ID = ?";
    return mJdbcTemplate.update(query, getUpdateParamArray(Lists.newArrayList(pMutable)).get(0));
  }

  @Override
  public int delete(MutableStudent pMutable) throws Exception {
    String query = DELETE_ALL + " WHERE STUDENT_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int create(MutableStudent pMutable) throws Exception {
    return mJdbcTemplate.update(CREATE_ALL,
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
        pMutable.getProgramId(),
        pMutable.getEnrollmentType().getValue(),
        pMutable.getCurrentYear(),
        pMutable.getCurrentAcademicSemester()
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

  @Override
  public int update(List<MutableStudent> pStudentList) throws Exception {
    String query = UPDATE_ALL + " WHERE STUDENT_ID = ?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParamArray(pStudentList)).length;
  }

  private List<Object[]> getUpdateParamArray(List<MutableStudent> pStudents) {
    List<Object[]> params = new ArrayList<>();
    for (Student student : pStudents) {
      params.add(new Object[]{
          student.getFullName(),
          student.getDepartmentId(),
          student.getSemesterId(),
          student.getFatherName(),
          student.getMotherName(),
          mDateFormat.format(student.getDateOfBirth()),
          student.getGender(),
          student.getPresentAddress(),
          student.getPermanentAddress(),
          student.getMobileNo(),
          student.getPhoneNo(),
          student.getBloodGroup(),
          student.getEmail(),
          student.getGuardianName(),
          student.getGuardianMobileNo(),
          student.getGuardianPhoneNo(),
          student.getGuardianEmail(),
          student.getEnrollmentType().getValue(),
          student.getCurrentYear(),
          student.getCurrentAcademicSemester(),
          student.getId()
      });
    }

    return params;
  }


  class StudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableStudent student = new PersistentStudent();
      student.setId(rs.getString("STUDENT_ID"));
      student.setFullName(rs.getString("FULL_NAME"));
      student.setDepartmentId(rs.getString("DEPT_ID"));
      student.setSemesterId(rs.getInt("SEMESTER_ID"));
      student.setFatherName(rs.getString("FATHER_NAME"));
      student.setMotherName(rs.getString("MOTHER_NAME"));
      student.setDateOfBirth(rs.getDate("BIRTH_DATE"));
      student.setGender(rs.getString("GENDER"));
      student.setPresentAddress(rs.getString("PRESENT_ADDRESS"));
      student.setPermanentAddress(rs.getString("PERMANENT_ADDRESS"));
      student.setMobileNo(rs.getString("MOBILE_NUMBER"));
      student.setPhoneNo(rs.getString("PHONE_NUMBER"));
      student.setBloodGroup(rs.getString("BLOOD_GROUP"));
      student.setEmail(rs.getString("EMAIL_ADDRESS"));
      student.setGuardianName(rs.getString("GUARDIAN_NAME"));
      student.setGuardianMobileNo(rs.getString("GUARDIAN_MOBILE"));
      student.setGuardianPhoneNo(rs.getString("GUARDIAN_PHONE"));
      student.setGuardianEmail("GUARDIAN_EMAIL");
      student.setProgramId(rs.getInt("PROGRAM_ID"));
      student.setLastModified(rs.getString("LAST_MODIFIED"));
      if (rs.getObject("ENROLLMENT_TYPE") != null) {
        student.setEnrollmentType(Student.EnrollmentType.get(rs.getInt("ENROLLMENT_TYPE")));
      }
      if (rs.getObject("CURR_YEAR") != null) {
        student.setCurrentYear(rs.getInt("CURR_YEAR"));
      }
      if (rs.getObject("CURR_SEMESTER") != null) {
        student.setCurrentAcademicSemester(rs.getInt("CURR_SEMESTER"));
      }
      return student;
    }
  }
}
