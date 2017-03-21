package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.AdmissionStudentDaoDecorator;
import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.enums.*;
import org.ums.manager.ProgramManager;
import org.ums.manager.SemesterManager;
import org.ums.persistent.model.PersistentAdmissionStudent;

/**
 * Created by Monjur-E-Morshed on 14-Dec-16.
 */
public class PersistentAdmissionStudentDao extends AdmissionStudentDaoDecorator {

  @Autowired
  private SemesterManager mSemesterManager;

  @Autowired
  private ProgramManager mProgramManager;

  String SELECT_ONE =
      " select SEMESTER_ID, RECEIPT_ID, PIN, HSC_BOARD, HSC_ROLL,  "
          + "    HSC_REGNO, HSC_YEAR, HSC_GROUP, SSC_BOARD, SSC_ROLL,  "
          + "    SSC_YEAR, SSC_GROUP, GENDER, to_char(DATE_OF_BIRTH,'dd/mm/yy') DATE_OF_BIRTH, STUDENT_NAME,  "
          + "    FATHER_NAME, MOTHER_NAME, SSC_GPA, HSC_GPA, QUOTA,  "
          + "    ADMISSION_ROLL, MERIT_SL_NO, STUDENT_ID, ALLOCATED_PROGRAM_ID, MIGRATION_STATUS,  "
          + "    LAST_MODIFIED, UNIT, PROGRAM_TYPE, NID, BIRTH_REG,  "
          + "    PASSPORT, PROGRAM_ID_BY_MERIT, PROGRAM_ID_BY_TRANSFER, PRESENT_STATUS, to_char(DEADLINE,'dd/mm/yyyy') deadline,  "
          + "    VERIFICATION_STATUS, MIGRATED_FROM, to_char(UNDERTAKEN_DEADLINE,'dd/mm/yyyy') undertaken_deadline from admission_students ";

  String SELECT_ONE_TALETALK_DATA =
      "select SEMESTER_ID, RECEIPT_ID, PIN, HSC_BOARD, HSC_ROLL,       "
          + "                 HSC_REGNO, HSC_YEAR, HSC_GROUP, SSC_BOARD, SSC_ROLL,       "
          + "                 SSC_YEAR, SSC_GROUP, GENDER,to_char(DATE_OF_BIRTH,'dd/mm/yyyy') date_of_birth , STUDENT_NAME,       "
          + "                 FATHER_NAME, MOTHER_NAME, SSC_GPA, HSC_GPA, QUOTA , unit,      "
          + "                 LAST_MODIFIED from admission_students where SEMESTER_ID=? and program_type=? order by to_number(RECEIPT_ID)";

  String INSERT_ONE_TALETALK_DATA = "INSERT INTO admission_students (semester_id, "
      + "                                receipt_id, " + "                                pin, "
      + "                                hsc_board,hsc_roll, " + "                                hsc_regno, "
      + "                                hsc_year, " + "                                hsc_group, "
      + "                                ssc_board, " + "                                ssc_roll, "
      + "                                ssc_year, " + "                                ssc_group, "
      + "                                gender, " + "                                date_of_birth, "
      + "                                student_name, " + "                                father_name, "
      + "                                mother_name, " + "                                ssc_gpa, "
      + "                                hsc_gpa, " + "                                quota, "
      + "                                unit, program_type,last_Modified) "
      + "                                values(?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/MM/YY'),?,?,?,?,?,?,?,?,"
      + getLastModifiedSql() + ")";

  String INSERT_ONE = "INSERT INTO DB_IUMS.ADMISSION_STUDENTS  "
      + "(SEMESTER_ID, RECEIPT_ID, PIN, HSC_BOARD, HSC_ROLL,  "
      + " HSC_REGNO, HSC_YEAR, HSC_GROUP, SSC_BOARD, SSC_ROLL,  " + "  SSC_YEAR, SSC_GROUP, GENDER, DATE_OF_BIRTH,  "
      + " STUDENT_NAME, FATHER_NAME, MOTHER_NAME, SSC_GPA, HSC_GPA,  "
      + " QUOTA,UNIT, ADMISSION_ROLL, MERIT_SL_NO, STUDENT_ID, ALLOCATED_PROGRAM_ID,  "
      + " MIGRATION_STATUS, LAST_MODIFIED)  " + "VALUES  " + "  (?, ?, ?, ?, ?,?,  " + "      ?, ?, ?, ?, ?,  "
      + "      ?, ?, ?, ?, ?,  " + "         ?, ?, ?, ?, ?,  " + "   ?, ?, ?, ?, ?,  " + "   ?, "
      + getLastModifiedSql() + ")";

  String GET_ONE = "SELECT SEMESTER_ID, RECEIPT_ID, PIN, HSC_BOARD, HSC_ROLL,  "
      + "    HSC_REGNO, HSC_YEAR, HSC_GROUP, SSC_BOARD, SSC_ROLL,  "
      + "    SSC_YEAR, SSC_GROUP, GENDER, to_char(DATE_OF_BIRTH,'dd/mm/yy') date_of_birth, STUDENT_NAME,  "
      + "    FATHER_NAME, MOTHER_NAME, SSC_GPA, HSC_GPA, QUOTA,  "
      + "    ADMISSION_ROLL, MERIT_SL_NO, STUDENT_ID, ALLOCATED_PROGRAM_ID, MIGRATION_STATUS,  "
      + "    LAST_MODIFIED, UNIT from admission_students ";

  String GET_BY_STUDENTID = "SELECT RECEIPT_ID FROM ADMISSION_STUDENTS ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAdmissionStudentDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<String> create(List<MutableAdmissionStudent> pMutableList) {
    mJdbcTemplate.batchUpdate(INSERT_ONE, getAdmissionStudentParams(pMutableList));
    return pMutableList.stream().map(pMutable -> pMutable.getReceiptId())
        .collect(Collectors.toList());
  }

  @Override
  public int saveTaletalkData(List<MutableAdmissionStudent> students) {
    String query = INSERT_ONE_TALETALK_DATA;
    return mJdbcTemplate.batchUpdate(query, getTaletalkDataParams(students)).length;
  }

  @Override
  public int updateDepartmentSelection(MutableAdmissionStudent pStudent,
      DepartmentSelectionType pDepartmentSelectionType) {
    String query = "";
    if(pDepartmentSelectionType.ABSENT.equals(pDepartmentSelectionType)) {
      query =
          "update admission_students set present_status=0,last_modified=" + getLastModifiedSql()
              + " where semester_id=? and receipt_id=?";
      return mJdbcTemplate.update(query, pStudent.getSemester().getId(), pStudent.getReceiptId());
    }
    else if(pDepartmentSelectionType.MERIT_PROGRAM_SELECTED.equals(pDepartmentSelectionType)) {
      query =
          "update admission_students set program_id_by_merit=?,present_status=1,deadline=to_date(?,'dd/mm/yyyy'),last_modified="
              + getLastModifiedSql() + " where semester_id=? and receipt_id=?";
      return mJdbcTemplate.update(query, pStudent.getProgramByMerit().getId(), pStudent.getDeadline(), pStudent
          .getSemester().getId(), pStudent.getReceiptId());
    }
    else if(pDepartmentSelectionType.MERIT_WAITING_PROGRAMS_SELECTED.equals(pDepartmentSelectionType)) {
      query =
          "update admission_students set program_id_by_merit=?, program_id_by_transfer=?, deadline=to_date(?,'dd/mm/yyyy'),present_status=1,last_modified="
              + getLastModifiedSql() + " where semester_id=? and receipt_id=?";
      return mJdbcTemplate.update(query, pStudent.getProgramByMerit().getId(), pStudent.getProgramByTransfer().getId(),
          pStudent.getDeadline(), pStudent.getSemester().getId(), pStudent.getReceiptId());
    }
    else {
      query =
          "update admission_students set program_id_by_transfer=?,present_status=1,last_modified="
              + getLastModifiedSql() + " where semester_id=? and receipt_id=?";
      return mJdbcTemplate.update(query, pStudent.getProgramByTransfer().getId(), pStudent.getSemester().getId(),
          pStudent.getReceiptId());
    }
  }

  @Override
  public int updateStudentsAllocatedProgram(AdmissionStudent pAdmissionStudent, MigrationStatus pMigrationStatus) {
    int migrationStatus = MigrationStatus.NOT_MIGRATED.getId();
    if(pAdmissionStudent.getUnit().equals("BBA")) {
      // todo assign deadline information for bba students
      String query =
          "update admission_students set allocated_program_id=?, student_id=?, MIGRATION_STATUS=? where receipt_id=? and semester_id=?";
      return mJdbcTemplate.update(query, pAdmissionStudent.getAllocatedProgram().getId(),
          pAdmissionStudent.getStudentId(), MigrationStatus.NOT_MIGRATED.getId(), pAdmissionStudent.getReceiptId(),
          pAdmissionStudent.getSemester().getId());
    }
    else {
      if(pMigrationStatus == MigrationStatus.NOT_MIGRATED) {
        String query =
            "update ADMISSION_STUDENTS set ALLOCATED_PROGRAM_ID=?, STUDENT_ID=?, MIGRATION_STATUS=? where RECEIPT_ID=? and SEMESTER_ID=? and deadline>=sysdate";
        return mJdbcTemplate.update(query, pAdmissionStudent.getAllocatedProgram().getId(),
            pAdmissionStudent.getStudentId(), pMigrationStatus.getId(), pAdmissionStudent.getReceiptId(),
            pAdmissionStudent.getSemester().getId());
      }
      else {
        String query =
            "update ADMISSION_STUDENTS set ALLOCATED_PROGRAM_ID=?, STUDENT_ID=?, MIGRATION_STATUS=?,MIGRATED_FROM where RECEIPT_ID=? and SEMESTER_ID=? and deadline>=sysdate";
        return mJdbcTemplate.update(query, pAdmissionStudent.getAllocatedProgram().getId(),
            pAdmissionStudent.getStudentId(), pMigrationStatus.getId(), pAdmissionStudent.getMigratedFrom(),
            pAdmissionStudent.getReceiptId(), pAdmissionStudent.getSemester().getId());
      }
    }

  }

  @Override
  public AdmissionStudent getNextStudentForDepartmentSelection(int pSemesterId, ProgramType pProgramType, String pUnit,
      String pQuota, int pMeritSerialNo) {
    String query =
        "SELECT * "
            + "FROM "
            + "  (SELECT * "
            + "   FROM ADMISSION_STUDENTS "
            + "   WHERE SEMESTER_ID = ? AND QUOTA=? AND  PROGRAM_TYPE = ? AND unit = ? AND PRESENT_STATUS IS NULL AND MERIT_SL_NO>? "
            + "   ORDER BY MERIT_SL_NO) s " + "WHERE rownum <= 1";
    return mJdbcTemplate.queryForObject(query, new Object[] {pSemesterId, pQuota, pProgramType.getValue(), pUnit,
        pMeritSerialNo}, new AdmissionStudentRowMapper());
  }

  @Override
  public int saveMeritList(List<MutableAdmissionStudent> pStudents) {
    String query =
        "update admission_students set merit_sl_no=? , admission_roll=? where semester_id=? and receipt_id=?";
    return mJdbcTemplate.batchUpdate(query, getMeritListParams(pStudents)).length;
  }

  @Override
  public int updateAdmissionMigrationStatus(List<MutableAdmissionStudent> pStudents) {
    String query =
        "update admission_students set migration_status=?, deadline=to_date(?,'dd/mm/yyyy') where semester_id=? and receipt_id=?";
    return mJdbcTemplate.batchUpdate(query, getAdmissionStudentsWithMigrationStatusParams(pStudents)).length;
  }

  private List<Object[]> getAdmissionStudentParams(List<MutableAdmissionStudent> pStudents) {
    List<Object[]> params = new ArrayList<>();

    for(AdmissionStudent student : pStudents) {
      params.add(new Object[] {student.getSemester().getId(), student.getReceiptId(), student.getPin(),
          student.getHSCBoard(), student.getHSCRoll(), student.getHSCRegNo(), student.getHSCYear(),
          student.getHSCGroup(), student.getSSCBoard(), student.getSSCRoll(), student.getSSCYear(),
          student.getSSCGroup(), student.getGender(), student.getBirthDate(), student.getStudentName(),
          student.getFatherName(), student.getMotherName(), student.getSSCGpa(), student.getHSCGpa(),
          student.getQuota(), student.getUnit(), student.getAdmissionRoll(), student.getMeritSerialNo(),
          student.getStudentId(), student.getAllocatedProgram().getId(), student.getMigrationStatus().getId()});
    }
    return params;
  }

  private List<Object[]> getAdmissionStudentsWithMigrationStatusParams(List<MutableAdmissionStudent> pStudents) {
    List<Object[]> params = new ArrayList<>();
    for(AdmissionStudent student : pStudents) {
      params.add(new Object[] {student.getMigrationStatus().getId(), student.getDeadline(),
          student.getSemester().getId(), student.getReceiptId()});
    }
    return params;
  }

  private List<Object[]> getMeritListParams(List<MutableAdmissionStudent> pStudents) {
    List<Object[]> params = new ArrayList<>();

    for(AdmissionStudent student : pStudents) {
      params.add(new Object[] {student.getMeritSerialNo(), student.getAdmissionRoll(), student.getSemester().getId(),
          student.getReceiptId()});
    }
    return params;
  }

  private List<Object[]> getTaletalkDataParams(List<MutableAdmissionStudent> pStudents) {
    List<Object[]> params = new ArrayList<>();

    for(AdmissionStudent student : pStudents) {
      params.add(new Object[] {student.getSemester().getId(), student.getReceiptId(), student.getPin(),
          student.getHSCBoard(), student.getHSCRoll(), student.getHSCRegNo(), student.getHSCYear(),
          student.getHSCGroup(), student.getSSCBoard(), student.getSSCRoll(), student.getSSCYear(),
          student.getSSCGroup(), student.getGender(), student.getBirthDate(), student.getStudentName(),
          student.getFatherName(), student.getMotherName(), student.getSSCGpa(), student.getHSCGpa(),
          student.getQuota(), student.getUnit(), student.getProgramType().getValue()});
    }
    return params;
  }

  @Override
  public List<AdmissionStudent> getTaletalkData(int pSemesterId, ProgramType pProgramType) {
    int pType = pProgramType.getValue();
    String query = SELECT_ONE + " where semester_id=? ";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId}, new AdmissionStudentRowMapper());
  }

  @Override
  public List<AdmissionStudent> getAdmissionStudent(int pSemesterId, MigrationStatus pMigrationStatus) {
    String query = SELECT_ONE + " WHERE SEMESTER_ID=? AND MIGRATION_STATUS>=?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pMigrationStatus.getId()},
        new AdmissionStudentRowMapper());
  }

  @Override
  public int getDataSize(int pSemesterId, ProgramType pProgramType) {
    String query = "select count(*) from admission_students where semester_id=? and program_type=?";
    return mJdbcTemplate.queryForObject(query, Integer.class, pSemesterId, pProgramType.getValue());
  }

  @Override
  public List<AdmissionStudent> getMeritList(int pSemesterId, QuotaType pQuotaType, String pUnit,
      ProgramType pProgramType) {

    int pTYpe = pProgramType.getValue();
    String query =
        SELECT_ONE
            + " where semester_id=? and program_type=? and unit=? and merit_sl_no is not null and admission_roll is not null  "
            + " and receipt_id in (select receipt_id from admission_students where ";
    query = getQuotaSql(pQuotaType, query);
    query = query + ") order by merit_sl_no";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pProgramType.getValue(), pUnit},
        new AdmissionStudentRowMapper());
  }

  @Override
  public List<AdmissionStudent> getTaletalkData(int pSemesterId, QuotaType pQuotaType, String unit,
      ProgramType pProgramType) {
    String query = SELECT_ONE + " where semester_id=? and program_type=? and unit=? and ";
    query = getQuotaSql(pQuotaType, query);
    query = query + " order by to_number(receipt_id)";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pProgramType.getValue(), unit},
        new AdmissionStudentRowMapper());
  }

  private String getQuotaSql(QuotaType pQuotaType, String pQuery) {
    if(pQuotaType.getId() == 0) {
      pQuery = pQuery + "  quota='FF' or quota='GL' or quota='RA' or quota='GCE' ";
    }
    else if(pQuotaType.getId() == 1) {
      pQuery = pQuery + " quota='GL'";
    }
    else if(pQuotaType.getId() == 2) {
      pQuery = pQuery + " quota='FF'";
    }
    else if(pQuotaType.getId() == 3) {
      pQuery = pQuery + " quota='RA'";
    }
    else {
      pQuery = pQuery + " quota='GCE'";
    }
    return pQuery;
  }

  @Override
  public AdmissionStudent getAdmissionStudent(int pSemesterId, QuotaType pQuotaType, int pMeritSerialNo) {
    String quota = getQuotaForDb(pQuotaType);
    String query = SELECT_ONE + " WHERE  SEMESTER_ID=? AND QUOTA=? AND MERIT_SL_NO=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pSemesterId, quota, pMeritSerialNo},
        new AdmissionStudentRowMapper());
  }

  private String getQuotaForDb(QuotaType pQuotaType) {
    String quota = "";
    if(pQuotaType == QuotaType.GENERAL) {
      quota = "GL";
    }
    else if(pQuotaType == QuotaType.FREEDOM_FIGHTER) {
      quota = "FF";
    }
    else if(pQuotaType == QuotaType.REMOTE_AREA) {
      quota = "RA";
    }
    else {
      quota = "GCE";
    }
    return quota;
  }

  @Override
  public List<AdmissionStudent> getTaletalkData(int pSemesterId, QuotaType pQuotaType, int fromMeritSerialNumber,
      int toMeritSerialNumber) {
    String quota = getQuotaForDb(pQuotaType);
    String query =
        SELECT_ONE + " where SEMESTER_ID=? and QUOTA=? and MERIT_SL_NO>=? and MERIT_SL_NO<=? order by merit_sl_no";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, quota, fromMeritSerialNumber, toMeritSerialNumber},
        new AdmissionStudentRowMapper());
    /*
     * SELECT_ONE +
     * " where SEMESTER_ID=? and QUOTA=? and MERIT_SL_NO>=? and MERIT_SL_NO<=? order by merit_sl_no"
     * ; return mJdbcTemplate.query(query, new Object[] {pSemesterId, quota, fromMeritSerialNumber,
     * toMeritSerialNumber}, new AdmissionStudentRowMapper());
     */
  }

  // kawsurilu

  public int setVerificationStatusAndUndertakenDate(MutableAdmissionStudent pStudent) {
    MutableAdmissionStudent student = pStudent;
    String query =
        "update admission_students set verification_status=? , undertaken_deadline = to_date(?,'dd/mm/yyyy') where program_type=? and semester_id=? and receipt_id=?";
    return mJdbcTemplate.update(query, student.getVerificationStatus(), student.getUndertakenDeadline(), student
        .getProgramType().getValue(), student.getSemester().getId(), student.getReceiptId());
  }

  public List<AdmissionStudent> getAllCandidates(ProgramType pProgramType, int pSemesterId) {
    String query = SELECT_ONE + " WHERE PROGRAM_TYPE=? AND SEMESTER_ID=? ORDER BY to_number(RECEIPT_ID)";
    return mJdbcTemplate.query(query, new Object[] {pProgramType.getValue(), pSemesterId},
        new AdmissionStudentRowMapper());
  }

  //

  @Override
  public AdmissionStudent getAdmissionStudent(int pSemesterId, ProgramType pProgramType, String pReceiptId) {
    String query = SELECT_ONE + "  where semester_id=? and program_type=? and receipt_id=? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pSemesterId, pProgramType.getValue(), pReceiptId},
        new AdmissionStudentRowMapper());
  }

  class AdmissionStudentRowMapper implements RowMapper<AdmissionStudent> {
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
      student.setAdmissionRoll(pResultSet.getString("admission_roll"));
      student.setMeritSerialNo(pResultSet.getInt("merit_sl_no"));
      student.setStudentId(pResultSet.getString("student_id"));
      student.setAllocatedProgramId(pResultSet.getInt("allocated_program_id"));
      int migrationStatusS = pResultSet.getInt("migration_status");
      MigrationStatus mStatus = MigrationStatus.get(migrationStatusS);
      student.setMigrationStatus(MigrationStatus.get(pResultSet.getInt("migration_status")));
      student.setLastModified(pResultSet.getString("last_modified"));
      student.setUnit(pResultSet.getString("unit"));
      student.setProgramType(ProgramType.get(pResultSet.getInt("PROGRAM_TYPE")));
      student.setNID(pResultSet.getString("nid"));
      student.setBirthReg(pResultSet.getString("birth_reg"));
      student.setPassportNo(pResultSet.getString("passport"));
      student.setProgramIdByMerit(pResultSet.getInt("program_id_by_merit"));
      student.setProgramIdByTransfer(pResultSet.getInt("program_id_by_transfer"));
      student.setPresentStatus(PresentStatus.get(pResultSet.getInt("present_status")));
      student.setDeadline(pResultSet.getString("deadline"));
      student.setVerificationStatus(pResultSet.getInt("verification_status"));
      student.setMigratedFrom(pResultSet.getString("migrated_from"));
      student.setUndertakenDeadline(pResultSet.getString("undertaken_deadline"));
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
