package org.ums.persistent.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.AdmissionStudentDaoDecorator;
import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.enums.MigrationStatus;
import org.ums.enums.QuotaType;
import org.ums.manager.ProgramManager;
import org.ums.manager.SemesterManager;
import org.ums.persistent.model.PersistentAdmissionStudent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 14-Dec-16.
 */
public class PersistentAdmissionStudentDao extends AdmissionStudentDaoDecorator {

  @Autowired
  private SemesterManager mSemesterManager;

  @Autowired
  private ProgramManager mProgramManager;

  String SELECT_ONE = "SELECT " + "  SEMESTER_ID, " + "  RECEIPT_ID, " + "  PIN, "
      + "  HSC_BOARD, " + "  HSC_ROLL, " + "  HSC_REGNO, " + "  HSC_YEAR, " + "  HSC_GROUP, "
      + "  SSC_BOARD, " + "  SSC_ROLL, " + "   " + "  SSC_YEAR, " + "  SSC_GROUP, " + "  GENDER, "
      + "  DATE_OF_BIRTH, " + "  STUDENT_NAME, " + "  FATHER_NAME, " + "  MOTHER_NAME, "
      + "  SSC_GPA, " + "  HSC_GPA, " + "  QUOTA, " + "  ADMISSION_ROLL, " + "  MERIT_SL_NO, "
      + "  STUDENT_ID, " + "  ALLOCATED_PROGRAM_ID, " + "  MIGRATION_STATUS, " + "  LAST_MODIFIED "
      + "FROM admission_students";

  String SELECT_ONE_TALETALK_DATA =
      "select SEMESTER_ID, RECEIPT_ID, PIN, HSC_BOARD, HSC_ROLL,       "
          + "                 HSC_REGNO, HSC_YEAR, HSC_GROUP, SSC_BOARD, SSC_ROLL,       "
          + "                 SSC_YEAR, SSC_GROUP, GENDER,to_char(DATE_OF_BIRTH,'dd/mm/yy') date_of_birth , STUDENT_NAME,       "
          + "                 FATHER_NAME, MOTHER_NAME, SSC_GPA, HSC_GPA, QUOTA , unit,      "
          + "                 LAST_MODIFIED from admission_students where SEMESTER_ID=? order by to_number(RECEIPT_ID)";

  String INSERT_ONE_TALETALK_DATA =
      "INSERT INTO admission_students (semester_id, "
          + "                                receipt_id, "
          + "                                pin, "
          + "                                hsc_board,hsc_roll, "
          + "                                hsc_regno, "
          + "                                hsc_year, "
          + "                                hsc_group, "
          + "                                ssc_board, "
          + "                                ssc_roll, "
          + "                                ssc_year, "
          + "                                ssc_group, "
          + "                                gender, "
          + "                                date_of_birth, "
          + "                                student_name, "
          + "                                father_name, "
          + "                                mother_name, "
          + "                                ssc_gpa, "
          + "                                hsc_gpa, "
          + "                                quota, "
          + "                                unit,last_Modified) "
          + "                                values(?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/MM/YY'),?,?,?,?,?,?,?,"
          + getLastModifiedSql() + ")";

  String INSERT_ONE = "INSERT INTO DB_IUMS.ADMISSION_STUDENTS  "
      + "(SEMESTER_ID, RECEIPT_ID, PIN, HSC_BOARD, HSC_ROLL,  "
      + " HSC_REGNO, HSC_YEAR, HSC_GROUP, SSC_BOARD, SSC_ROLL,  "
      + "  SSC_YEAR, SSC_GROUP, GENDER, DATE_OF_BIRTH,  "
      + " STUDENT_NAME, FATHER_NAME, MOTHER_NAME, SSC_GPA, HSC_GPA,  "
      + " QUOTA,UNIT, ADMISSION_ROLL, MERIT_SL_NO, STUDENT_ID, ALLOCATED_PROGRAM_ID,  "
      + " MIGRATION_STATUS, LAST_MODIFIED)  " + "VALUES  " + "  (?, ?, ?, ?, ?,?,  "
      + "      ?, ?, ?, ?, ?,  " + "      ?, ?, ?, ?, ?,  " + "         ?, ?, ?, ?, ?,  "
      + "   ?, ?, ?, ?, ?,  " + "   ?, " + getLastModifiedSql() + ")";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAdmissionStudentDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int create(List<MutableAdmissionStudent> pMutableList) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query, getAdmissionStudentParams(pMutableList)).length;
  }

  @Override
  public int saveTaletalkData(List<MutableAdmissionStudent> students) {
    String query = INSERT_ONE_TALETALK_DATA;
    return mJdbcTemplate.batchUpdate(query, getTaletalkDataParams(students)).length;
  }

  private List<Object[]> getAdmissionStudentParams(List<MutableAdmissionStudent> pStudents) {
    List<Object[]> params = new ArrayList<>();

    for(AdmissionStudent student : pStudents) {
      params.add(new Object[] {student.getSemester().getId(), student.getReceiptId(),
          student.getPin(), student.getHSCBoard(), student.getHSCRoll(), student.getHSCRegNo(),
          student.getHSCYear(), student.getHSCGroup(), student.getSSCBoard(), student.getSSCRoll(),
          student.getSSCYear(), student.getSSCGroup(), student.getGender(), student.getBirthDate(),
          student.getStudentName(), student.getFatherName(), student.getMotherName(),
          student.getSSCGpa(), student.getHSCGpa(), student.getQuota(), student.getUnit(),
          student.getAdmissionRoll(), student.getMeritSerialNo(), student.getStudentId(),
          student.getAllocatedProgram().getId(), student.getMigrationStatus().getId()});
    }
    return params;
  }

  private List<Object[]> getTaletalkDataParams(List<MutableAdmissionStudent> pStudents) {
    List<Object[]> params = new ArrayList<>();

    for(AdmissionStudent student : pStudents) {
      params.add(new Object[] {student.getSemester().getId(), student.getReceiptId(),
          student.getPin(), student.getHSCBoard(), student.getHSCRoll(), student.getHSCRegNo(),
          student.getHSCYear(), student.getHSCGroup(), student.getSSCBoard(), student.getSSCRoll(),
          student.getSSCYear(), student.getSSCGroup(), student.getGender(), student.getBirthDate(),
          student.getStudentName(), student.getFatherName(), student.getMotherName(),
          student.getSSCGpa(), student.getHSCGpa(), student.getQuota(), student.getUnit()});
    }
    return params;
  }

  @Override
  public List<AdmissionStudent> getTaletalkData(int pSemesterId) {
    String query = SELECT_ONE_TALETALK_DATA;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId},
        new AdmissionStudentRowMapperTaletalk());
  }

  @Override
  public int getDataSize(int pSemesterId) {
    String query = "select count(*) from admission_students where semester_id=?";
    return mJdbcTemplate.queryForObject(query, Integer.class, pSemesterId);
  }

  @Override
  public List<AdmissionStudent> getTaletalkData(int pSemesterId, QuotaType pQuotaType) {
    String query =
        "select semester_id,receipt_id, merit_sl_no, admission_roll,student_name,quota,  "
            + "last_modified from admission_students where semester_id=? and merit_sl_no!=null and admission_roll!=null  "
            + " and receipt_id in (select receipt_id from admission_students where ";
    if(pQuotaType.getId() == 0) {
      query = query + "  quota='FF' or quota='GA' or quota='RA')";
    }
    else if(pQuotaType.getId() == 1) {
      query = query + " quota='GA')";
    }
    else if(pQuotaType.getId() == 2) {
      query = query + " quota='FF')";
    }
    else if(pQuotaType.getId() == 3) {
      query = query + " quota='RA')";
    }
    else {
      query = query + " quota='EM')";
    }
    return mJdbcTemplate
        .query(query, new Object[] {pSemesterId}, new AdmissionMeritListRowMapper());
  }

  class AdmissionStudentRowMapper implements RowMapper<AdmissionStudent> {
    @Override
    public AdmissionStudent mapRow(ResultSet pResultSet, int pI) throws SQLException {
      MutableAdmissionStudent student = new PersistentAdmissionStudent();
      student.setId(pResultSet.getString("receipt_id"));
      student.setSemester(mSemesterManager.get(pResultSet.getInt("semester_id")));
      student.setPin(pResultSet.getString("pin"));
      student.setHSCBoard(pResultSet.getString("hsc_board"));
      student.setHSCRoll(pResultSet.getString("hsc_roll"));
      student.setHSCRegNo(pResultSet.getString("hsc_regno"));
      student.setHSCYear(pResultSet.getInt("hsc_Year"));
      student.setHSCGroup(pResultSet.getString("hsc_group"));
      student.setSSCBoard(pResultSet.getString("ssc_board"));
      student.setSSCRoll(pResultSet.getString("ssc_roll"));
      student.setSSCYear(pResultSet.getInt("ssc_year"));
      student.setSSCGroup(pResultSet.getString("ssc_group"));
      student.setGender(pResultSet.getString("gender"));
      student.setDateOfBirth(pResultSet.getString("date_of_birth"));
      student.setStudentName(pResultSet.getString("student_name"));
      student.setFatherName(pResultSet.getString("father_name"));
      student.setMotherName(pResultSet.getString("mother_name"));
      student.setSSCGpa(pResultSet.getDouble("ssc_gpa"));
      student.setHSCGpa(pResultSet.getDouble("hsc_gpa"));
      student.setQuota(pResultSet.getString("quota"));
      student.setUnit(pResultSet.getString("unit"));
      student.setAdmissionRoll(pResultSet.getString("admission_roll"));
      student.setMeritSerialNo(pResultSet.getInt("merit_sl_no"));
      student.setStudentId(pResultSet.getString("student_id"));
      student.setAllocatedProgram(mProgramManager.get(pResultSet.getInt("allocated_program_id")));
      student.setMigrationStatus(MigrationStatus.get(pResultSet.getInt("migration_status")));
      student.setLastModified(pResultSet.getString("last_modified"));
      return student;
    }
  }

  class AdmissionMeritListRowMapper implements RowMapper<AdmissionStudent> {
    @Override
    public AdmissionStudent mapRow(ResultSet pResultSet, int pI) throws SQLException {
      MutableAdmissionStudent student = new PersistentAdmissionStudent();
      student.setSemesterId(pResultSet.getInt("semester_id"));
      student.setId(pResultSet.getString("receipt_id"));
      student.setMeritSerialNo(pResultSet.getInt("merit_sl_no"));
      student.setAdmissionRoll(pResultSet.getString("admission_roll"));
      student.setStudentName(pResultSet.getString("student_name"));
      student.setQuota(pResultSet.getString("quota"));
      student.setLastModified(pResultSet.getString("last_modified"));
      return student;
    }
  }

  class AdmissionStudentRowMapperTaletalk implements RowMapper<AdmissionStudent> {
    @Override
    public AdmissionStudent mapRow(ResultSet pResultSet, int pI) throws SQLException {
      MutableAdmissionStudent student = new PersistentAdmissionStudent();
      student.setSemesterId(pResultSet.getInt("semester_id"));
      student.setId(pResultSet.getString("receipt_id"));
      student.setPin(pResultSet.getString("pin"));
      student.setHSCBoard(pResultSet.getString("hsc_board"));
      student.setHSCRoll(pResultSet.getString("hsc_roll"));
      student.setHSCRegNo(pResultSet.getString("hsc_regno"));
      student.setHSCYear(pResultSet.getInt("hsc_Year"));
      student.setHSCGroup(pResultSet.getString("hsc_group"));
      student.setSSCBoard(pResultSet.getString("ssc_board"));
      student.setSSCRoll(pResultSet.getString("ssc_roll"));
      student.setSSCYear(pResultSet.getInt("ssc_year"));
      student.setSSCGroup(pResultSet.getString("ssc_group"));
      student.setGender(pResultSet.getString("gender"));
      student.setDateOfBirth(pResultSet.getString("date_of_birth"));
      student.setStudentName(pResultSet.getString("student_name"));
      student.setFatherName(pResultSet.getString("father_name"));
      student.setMotherName(pResultSet.getString("mother_name"));
      student.setSSCGpa(pResultSet.getDouble("ssc_gpa"));
      student.setHSCGpa(pResultSet.getDouble("hsc_gpa"));
      student.setQuota(pResultSet.getString("quota"));
      student.setUnit(pResultSet.getString("unit"));
      student.setLastModified(pResultSet.getString("last_modified"));
      return student;
    }
  }

}
