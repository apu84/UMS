package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.SemesterDaoDecorator;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableSemester;
import org.ums.enums.ProgramType;
import org.ums.enums.SemesterStatus;
import org.ums.persistent.model.PersistentSemester;

public class PersistentSemesterDao extends SemesterDaoDecorator {

  static String SELECT_ALL =
      "SELECT SEMESTER_ID, SEMESTER_NAME, START_DATE, END_DATE, PROGRAM_TYPE, STATUS, LAST_MODIFIED FROM MST_SEMESTER  ";

  static String UPDATE_ONE = "UPDATE MST_SEMESTER SET START_DATE = ?, " + "END_DATE = ?, LAST_MODIFIED = "
      + getLastModifiedSql() + ", Status= ? ";

  static String DELETE_ONE = "DELETE FROM MST_SEMESTER ";
  static String INSERT_ONE =
      "INSERT INTO MST_SEMESTER(SEMESTER_ID, SEMESTER_NAME, START_DATE, END_DATE, PROGRAM_TYPE, STATUS, LAST_MODIFIED) "
          + "VALUES(?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + ")";

  static String SELECT_SEMESTER_BY_STATUS =
      "SELECT SEMESTER_ID, SEMESTER_NAME, START_DATE, END_DATE, PROGRAM_TYPE, STATUS, LAST_MODIFIED FROM MST_SEMESTER ";

  String ENROLLED_SEMESTERS =
      "select a.SEMESTER_ID,b.SEMESTER_NAME,b.START_DATE,b.END_DATE,b.PROGRAM_TYPE,b.STATUS,b.LAST_MODIFIED from  STUDENT_RECORD a,mst_semester b WHERE "
          + "a.semester_id=b.semester_id AND "
          + "a.STUDENT_ID=? AND (a.REGISTRATION_TYPE='R' OR a.REGISTRATION_TYPE='RA')  ORDER BY b.start_date desc";

  private JdbcTemplate mJdbcTemplate;

  public PersistentSemesterDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  public Semester get(final Integer pSemesterId) {
    String query = SELECT_ALL + "WHERE SEMESTER_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pSemesterId}, new SemesterRowMapper());
  }

  @Override
  public List<Semester> getEnrolledSemesters(String pStudentId) {
    return mJdbcTemplate.query(ENROLLED_SEMESTERS, new Object[] {pStudentId}, new EnrolledSemestersRowMapper());
  }

  public Semester getSemesterByStatus(final ProgramType pProgramType, SemesterStatus status) {
    String sql = SELECT_SEMESTER_BY_STATUS + " WHERE  Program_Type=? AND STATUS=?";
    return mJdbcTemplate.queryForObject(sql, new Object[] {pProgramType.getValue(), status.getId()},
        new SemesterRowMapper());
  }

  @Override
  public List<Semester> getAll() {
    String query = SELECT_ALL + " order by START_DATE DESC ";
    return mJdbcTemplate.query(query, new SemesterRowMapper());
  }

  @Override
  public int update(final MutableSemester pSemester) {
    String query = UPDATE_ONE + " Where Semester_Id=? and Status In (1,2)";
    return mJdbcTemplate.update(query, pSemester.getStartDate(), pSemester.getEndDate(), pSemester.getStatus()
        .getValue(), pSemester.getId());
  }

  @Override
  public int delete(final MutableSemester pSemester) {
    String query = DELETE_ONE + "WHERE SEMESTER_ID = ?";
    return mJdbcTemplate.update(query, pSemester.getId());
  }

  @Override
  public Integer create(final MutableSemester pSemester) {
    mJdbcTemplate.update(INSERT_ONE, pSemester.getId(), pSemester.getName(), pSemester.getStartDate(),
        pSemester.getEndDate(), pSemester.getProgramType().getId(), pSemester.getStatus().getValue());
    return pSemester.getId();
  }

  @Override
  public List<Semester> getSemesters(Integer pProgramType, Integer pLimit) {
    String query = "Select * from ( " + SELECT_ALL + "WHERE PROGRAM_TYPE = ? Order By START_DATE desc) Where RowNum<=?";
    return mJdbcTemplate.query(query, new Object[] {pProgramType, pLimit}, new SemesterRowMapper());
  }

  @Override
  public Semester getPreviousSemester(Integer pSemesterId, Integer pProgramTypeId) {
    String query =
        SELECT_ALL + "WHERE START_DATE = " + "              (SELECT MAX (START_DATE) "
            + "                 FROM MST_SEMESTER " + "                WHERE START_DATE < "
            + "                         (SELECT START_DATE " + "                            FROM MST_SEMESTER "
            + "                           WHERE SEMESTER_ID = ? AND PROGRAM_TYPE = ?)) "
            + "       AND PROGRAM_TYPE = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pSemesterId, pProgramTypeId, pProgramTypeId},
        new SemesterRowMapper());
  }

  @Override
  public Semester getBySemesterName(String pSemesterName, Integer pProgramTypeId) {
    String query = SELECT_ALL + "WHERE SEMESTER_NAME = ? AND PROGRAM_TYPE = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pSemesterName, pProgramTypeId}, new SemesterRowMapper());
  }

  @Override
  public Semester getActiveSemester(Integer pProgramType) {
    String query = SELECT_ALL + " where  STATUS=1 AND PROGRAM_TYPE=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pProgramType}, new SemesterRowMapper());
  }

  @Override
  public List<Semester> getPreviousSemesters(Integer pSemesterId, Integer pProgramTypeId) {
    String query =
        SELECT_ALL + "WHERE START_DATE <= (SELECT START_DATE FROM MST_SEMESTER WHERE SEMESTER_ID = ? "
            + "AND PROGRAM_TYPE = ?) ORDER BY START_DATE DESC";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pProgramTypeId}, new SemesterRowMapper());
  }

  @Override
  public List<Semester> semestersAfter(Integer pStartSemester, Integer pEndSemester, Integer pProgramTypeId) {
    String query =
        SELECT_ALL
            + "WHERE START_DATE > (SELECT START_DATE FROM MST_SEMESTER WHERE SEMESTER_ID = ? AND PROGRAM_TYPE = ?) "
            + "AND START_DATE <= (SELECT START_DATE FROM MST_SEMESTER WHERE SEMESTER_ID = ? AND PROGRAM_TYPE = ?) "
            + "ORDER BY MST_SEMESTER.START_DATE DESC";
    return mJdbcTemplate.query(query, new Object[] {pStartSemester, pProgramTypeId, pEndSemester, pProgramTypeId},
        new SemesterRowMapper());
  }

  @Override
  public List<Semester> semestersAfter(Integer pStartSemester, Integer pProgramTypeId) {
    String query =
        SELECT_ALL
            + "WHERE START_DATE > (SELECT START_DATE FROM MST_SEMESTER WHERE SEMESTER_ID = ? AND PROGRAM_TYPE = ?) "
            + "ORDER BY MST_SEMESTER.START_DATE DESC";
    return mJdbcTemplate.query(query, new Object[] {pStartSemester, pProgramTypeId}, new SemesterRowMapper());
  }

  @Override
  public Semester closestSemester(Integer pCheckSemester, List<Integer> pCheckAgainstSemesters) {
    MapSqlParameterSource parameters = new MapSqlParameterSource();
    parameters.addValue("semesterIds", pCheckAgainstSemesters);
    parameters.addValue("semesterId", pCheckSemester);

    String query =
        SELECT_ALL + "WHERE SEMESTER_ID = (SELECT SEMESTER_ID"
            + "                        FROM (SELECT SEMESTER_ID, ROWNUM ind"
            + "                                FROM (  SELECT SEMESTER_ID, START_DATE"
            + "                                          FROM MST_SEMESTER"
            + "                                         WHERE     SEMESTER_ID IN"
            + "                                                      (:semesterIds)"
            + "                                      ORDER BY START_DATE DESC) TMP1"
            + "                               WHERE TMP1.START_DATE <="
            + "                                        (SELECT START_DATE"
            + "                                           FROM MST_SEMESTER"
            + "                                          WHERE SEMESTER_ID = :semesterId)) TMP2"
            + "                       WHERE TMP2.IND = 1)";
    NamedParameterJdbcTemplate namedParameterJdbcTemplate =
        new NamedParameterJdbcTemplate(mJdbcTemplate.getDataSource());
    return namedParameterJdbcTemplate.queryForObject(query, parameters, new SemesterRowMapper());
  }

  class EnrolledSemestersRowMapper implements RowMapper<Semester> {
    @Override
    public Semester mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentSemester enrolledSemester = new PersistentSemester();
      enrolledSemester.setId(pResultSet.getInt("SEMESTER_ID"));
      enrolledSemester.setName(pResultSet.getString("SEMESTER_NAME"));
      enrolledSemester.setStartDate(pResultSet.getDate("START_DATE"));
      enrolledSemester.setEndDate(pResultSet.getDate("END_DATE"));
      enrolledSemester.setProgramTypeId(pResultSet.getInt("PROGRAM_TYPE"));
      enrolledSemester.setStatus(Semester.Status.get(pResultSet.getInt("STATUS")));
      enrolledSemester.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return enrolledSemester;
    }
  }

  class SemesterRowMapper implements RowMapper<Semester> {
    @Override
    public Semester mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentSemester semester = new PersistentSemester();
      semester.setId(resultSet.getInt("SEMESTER_ID"));
      semester.setName(resultSet.getString("SEMESTER_NAME"));
      semester.setStartDate(resultSet.getDate("START_DATE"));
      semester.setEndDate(resultSet.getDate("END_DATE"));
      semester.setProgramTypeId(resultSet.getInt("PROGRAM_TYPE"));
      semester.setStatus(Semester.Status.get(resultSet.getInt("STATUS")));
      semester.setLastModified(resultSet.getString("LAST_MODIFIED"));
      AtomicReference<Semester> atomicReference = new AtomicReference<>(semester);
      return atomicReference.get();
    }
  }
}
